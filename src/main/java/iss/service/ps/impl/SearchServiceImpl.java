package iss.service.ps.impl;

import iss.dao.ps.ArticleExtMapper;
import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleExt;
import iss.service.ps.SearchService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.utils.JdcgResult;

/**
 * 
 * @ClassName: SearchService
 * @Description: 搜索接口的实现
 * @author Easong
 * @date 2017年3月1日 下午5:49:40
 * 
 */
@Service("searchServiceImpl")
public class SearchServiceImpl implements SearchService {

	private static Logger log = LoggerFactory
			.getLogger(SearchServiceImpl.class);

	// 当前页
	private final static Integer CURR_PAGE = 1;
	// 每页显示的条数
	private final static Integer PAGE_SIZE = 10;

	@Autowired
	private ArticleExtMapper mapper;

	// 注入solr服务
	@Autowired
	private SolrServer solrServer;

	@Autowired
	private ArticleMapper articleMapper;

	/**
	 * 
	 * @Title: importAll
	 * @Description: 全量导入索引库
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult importAll() {
		// 1、 根据sql查询商品数据
		List<ArticleExt> list = mapper.selectAllArticleAddSolr();
		// 2、 创建SolrServer
		// 3、 将查询结果数据，转成SolrInputDocument对象
		List<SolrInputDocument> docs = new ArrayList<>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SolrInputDocument doc;
		Map<String, Object> map = new HashMap<String, Object>();
		for (ArticleExt article : list) {
			map.put("index_value", 1);
			map.put("id", article.getId());
			mapper.updateIndex(map);
			doc = new SolrInputDocument();
			// 文章id
			doc.addField("id", article.getId());
			// 文章标题
			doc.addField("article_name", article.getName());
			// 文章内容
			doc.addField("article_content", article.getContent());
			Date date = article.getPublishedAt();
			String dateFormate = dateFormat.format(date);
			// 发布时间
			doc.addField("published_at", dateFormate);
			docs.add(doc);
			map.clear();
		}
		try {
			// 4、 调用solrServer将SolrInputDocument对象添加到索引库
			solrServer.add(docs);
			// 5、 提交
			solrServer.commit();
		} catch (Exception e) {
			log.info("Solr服务连接异常，添加索引失败");
			return JdcgResult.ok("添加索引失败");
		}
		// 6、 返回JdcgResult
		return JdcgResult.ok("导入成功!");
	}

	/**
	 * 
	 * @Title: search
	 * @Description: 全文检索
	 * @author Easong
	 * @param @param queryKey
	 * @param @param page
	 * @param @return
	 * @param @throws Exception 设定文件
	 * @throws
	 */
	@Override
	public Map<String, Object> search(String queryKey, Integer page)
			throws Exception {
		// 创建查询条件
		SolrQuery query = new SolrQuery();
		// 设置查询条件
		if (StringUtils.isNotEmpty(queryKey)) {
			query.setQuery(queryKey);
			// 设置权重
			query.set("defType", "dismax");
			// 首先在标题中查询，查询不到则到内容中查询
			query.set("qf", "article_name^2 article_content^0.2");

		} else {
			// 设置默认域
			query.setQuery("*:*");
			query.set("df", "article_keywords");
			// 设置排序
			query.setSort("published_at", ORDER.desc);
		}

		// 设置分页
		if (page == null) {
			page = CURR_PAGE;
		}
		// 设置起始索引
		query.setStart((page - 1) * PAGE_SIZE);
		// 设置每页显示的条数
		query.setRows(PAGE_SIZE);

		// 设置排序
		// query.setSort("published_at", ORDER.desc);
		// 设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("article_name");
		query.setHighlightSimplePre("<font style='color:red'>");
		query.setHighlightSimplePost("</font>");
		// 执行搜索并返回搜索结果
		QueryResponse queryResponse = solrServer.query(query);
		SolrDocumentList results = queryResponse.getResults();

		Map<String, Map<String, List<String>>> highlighting = queryResponse
				.getHighlighting();
		// 对搜索结果进行处理
		List<ArticleExt> list = new ArrayList<ArticleExt>();
		ArticleExt articleExt = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (SolrDocument solrDocument : results) {
			articleExt = new ArticleExt();
			// 设置文章的id
			articleExt.setId(solrDocument.get("id").toString());
			// 设置文章的name
			List<String> names = highlighting.get(solrDocument.get("id")).get(
					"article_name");
			if (names != null && names.size() > 0) {
				articleExt.setName(names.get(0));
			} else {
				articleExt.setName(solrDocument.get("article_name").toString());
			}
			// 设置文章的发布时间
			articleExt.setPublishedAt(dateFormat.parse(solrDocument.get(
					"published_at").toString()));
			list.add(articleExt);
		}

		// 封装map
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置查询的内容
		map.put("indexList", list);
		// 获取总记录数
		long totalRecords = results.getNumFound();
		map.put("total", totalRecords);
		// 计算总页数
		int totalPages = (int) (totalRecords / PAGE_SIZE);
		if (totalRecords % PAGE_SIZE > 0) {
			totalPages++;
		}
		// 总页数
		map.put("pages", totalPages);
		//
		map.put("startRow", (page - 1) * PAGE_SIZE + 1);
		map.put("endRow", (page - 1) * PAGE_SIZE + list.size());
		return map;

	}

	/**
	 * 
	 * @Title: clearIndex
	 * @Description: 全量清空索引库
	 * @author Easong
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult clearIndex() {
		try {
			// 创建查询条件
			SolrQuery query = new SolrQuery();
			ModifiableSolrParams params = new ModifiableSolrParams(); 
			// 查询关键词，*:*代表所有属性、所有值，即所有index
			// 设置查询条件
			params.set("q", "*:*"); 
			params.set("start", 0); 
			//该参数就是控制条数 
			params.set("rows", Integer.MAX_VALUE);
			// 查询
			QueryResponse response = solrServer.query(params);
			SolrDocumentList results = response.getResults();
			// 索引库存在值
			Map<String, Object> map = new HashMap<String, Object>();
			if (results != null && results.size() > 0) {
				for (SolrDocument doc : results) {
					String id = doc.get("id").toString();
					Article article = articleMapper.selectById(id);
					if(article != null){
						map.put("index_value", 0);
						map.put("id", article.getId());
						// 修改为0【未建索引】
						mapper.updateIndex(map);
						map.clear();
					}
				}
				solrServer.deleteByQuery("*:*");
				solrServer.commit(false, false);
				return JdcgResult.ok("索引库清除成功!");
			}
		} catch (Exception e) {
			log.info("Solr服务连接异常，删除文章索引库失败");
			return JdcgResult.ok("删除文章索引库失败");
		}
		// 不存在则返回
		return JdcgResult.ok("索引库数据为空");
	}

	/**
	 * 
	 * @Title: clearIndex
	 * @Description: 批量清空或者单个清空
	 * @author SongDong
	 * @param @return 设定文件
	 * @return JdcgResult 返回类型
	 * @throws
	 */
	@Override
	public JdcgResult deleteSignalIndex(String[] ids) {
		Article article = null;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuffer querySB = new StringBuffer();
			String idString = ids[0];
			querySB.append("id" + ":" + idString);
			// 根据id查询文章
			article = articleMapper.selectById(idString);
			map.put("index_value", 0);
			map.put("id", article.getId());
			// 修改为0【未建索引】
			mapper.updateIndex(map);
			map.clear();
			for (int i = 1; i < ids.length; i++) {
				// 拼接查询条件
				querySB.append(" OR " + "id" + ":" + ids[i]);
				// 修改索引状态
				article = articleMapper.selectById(ids[i]);
				map.put("index_value", 0);
				map.put("id", article.getId());
				// 修改为0【未建索引】
				mapper.updateIndex(map);
				map.clear();
			}
			// 执行删除索引
			solrServer.deleteByQuery(querySB.toString());
			solrServer.commit(false, false);
		} catch (Exception e) {
			log.info("Solr服务连接异常，清除文章索引库失败");
			return JdcgResult.ok("清除文章索引库失败");
		}
		return JdcgResult.ok("清除文章索引库成功！");
	}

	/**
	 * 
	 * @Title: addSignalIndex
	 * @Description: 将指定的标题添加到索引库
	 * @author Easong
	 * @param @param ids
	 * @param @return 设定文件
	 * @throws
	 */
	@Override
	public JdcgResult addSignalIndex(String[] ids) {
		Article article = null;
		try {
			ArrayList<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			SolrInputDocument doc = null;
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < ids.length; i++) {
				// 根据id查询文章
				article = articleMapper.selectById(ids[i]);
				map.put("index_value", 1);
				map.put("id", article.getId());
				mapper.updateIndex(map);
				doc = new SolrInputDocument();
				// 文章id
				doc.addField("id", article.getId());
				// 文章标题
				doc.addField("article_name", article.getName());
				// 文章内容
				doc.addField("article_content", article.getContent());
				Date date = article.getPublishedAt();
				String dateFormate = dateFormat.format(date);
				// 发布时间
				doc.addField("published_at", dateFormate);
				docs.add(doc);
				map.clear();
			}
			solrServer.add(docs);
			solrServer.commit();
		} catch (Exception e) {
			log.info("Solr服务连接异常，添加索引【" + article.getName() + "】失败");
			return JdcgResult.ok("添加索引【" + article.getName() + "】失败");
		}
		// 6、 返回JdcgResult
		return JdcgResult.ok("添加到索引库成功！");
	}
}
