package iss.service.ps.impl;

import iss.model.ps.Article;
import iss.model.ps.IndexEntity;
import iss.service.ps.ArticleService;
import iss.service.ps.SolrNewsService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.SolrContext;


/* 
 *@Title:SolrNewsServiceImpl
 *@Description:solr查询service实现类
 *@author QuJie
 *@date 2016-9-7下午6:29:23
 */
@Service(value="solrNewsService")
public class SolrNewsServiceImpl implements SolrNewsService {
	
	@Autowired
	private ArticleService articleService;
	
	/**
	 * 新增索引
	 */
	@Override
	public void addIndex(Article article) {
		try {
			IndexEntity indexEntity = new IndexEntity();
			indexEntity.setArticlename(article.getArticleType().getName());
			indexEntity.setId(article.getId());
			indexEntity.setPublishtime(article.getPublishedAt());
			indexEntity.setTitle(article.getName());
			String context=article.getContent();
//			int startIndex=context.indexOf("<");
//			int lastIndex=context.lastIndexOf(">");
//			String newContext=context.substring(startIndex+3, lastIndex-3);
			Document doc = Jsoup.parse(context);
			String text = doc.text();
			// remove extra white space
			StringBuilder builder = new StringBuilder(text);
			int index = 0;
			while(builder.length()>index){
				char tmp = builder.charAt(index);
				if(Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)){
					builder.setCharAt(index, ' ');
				}
				index++;
			}
			text = builder.toString().replaceAll(" +", " ").trim();
			indexEntity.setContext(text);
			SolrContext.getServer().addBean(indexEntity);
			SolrContext.getServer().commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 删除索引
	 */
	@Override
	public void deleteIndex(String id) {
		try {
			SolrContext.getServer().deleteById(id);
			SolrContext.getServer().commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改索引
	 */
	@Override
	public void updateIndex(Article article) {
		try {
			SolrContext.getServer().deleteById(article.getId());
			IndexEntity indexEntity = new IndexEntity();
			indexEntity.setArticlename(article.getArticleType().getName());
			indexEntity.setId(article.getId());
			indexEntity.setPublishtime(article.getPublishedAt());
			indexEntity.setTitle(article.getName());
			String context=article.getContent();
			int startIndex=context.indexOf("<");
			int lastIndex=context.lastIndexOf(">");
			String newContext=context.substring(startIndex+3, lastIndex-3);
			indexEntity.setContext(newContext);
			SolrContext.getServer().addBean(indexEntity);
			SolrContext.getServer().commit();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据条件查找
	 */
	@Override
	public Map<String, Object> findByIndex(String condition,Integer page,Integer pageSize) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<IndexEntity> indexList=new ArrayList<IndexEntity>();
		try {
			int pageOffset=(page-1)*pageSize;
			if(condition.isEmpty() || condition==null){
				condition="";
			}
			SolrQuery query=new SolrQuery(condition);
			query.setHighlight(true); // 开启高亮组件
			query.setQuery("title:"+condition+"AND context:"+condition);
			query.setParam("hl.fl", "title context");
            query.setHighlightSimplePre("<font color=\"red\">");// 标记
            query.setHighlightSimplePost("</font>");
            query.setHighlight(true).setHighlightSnippets(1);
            query.setHighlightFragsize(30);
			query.setStart(pageOffset);
			query.setRows(pageSize);
			SolrDocumentList sdl = SolrContext.getServer().query(query).getResults();
			Map<String,Map<String,List<String>>> highlighting = null;
			try {
	            QueryResponse response = SolrContext.getServer().query(query);
	            // 声明获取高亮的内容变量
	            highlighting = response.getHighlighting();
	            // documentList文档中的内容对wareName进行高亮显示,获取对应的高亮的内容，替换掉原来的内容
	            for(SolrDocument doc:sdl) {
	            	IndexEntity index=new IndexEntity();
	                //获取document中的id
	                String id = doc.getFieldValue("id").toString();
	                //获取当前id对应的高亮的文档
	                Map<String, List<String>> highlighMap = highlighting.get(id);
	                //获取document中的id对应的wareName，并对当前内容wareName重新赋值
	                if(highlighMap.get("title")!=null){
	                	doc.setField("title", highlighMap.get("title").get(0));
						index.setTitle((String)((doc.getFieldValue("title"))));
						index.setId((String)doc.getFieldValue("id"));
						index.setArticlename((String)(doc.getFieldValue("articlename")));
						index.setPublishtime((Date)(doc.getFieldValue("publishtime")));
						indexList.add(index);
	                }else if(highlighMap.get("context")!=null){
	                	doc.setField("context", highlighMap.get("context").get(0));
	                	index.setTitle((String)((doc.getFieldValue("context"))));
	                	index.setId((String)doc.getFieldValue("id"));
						index.setArticlename((String)(doc.getFieldValue("articlename")));
						index.setPublishtime((Date)(doc.getFieldValue("publishtime")));
						indexList.add(index);
	                }
	            }
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("indexList", indexList);
			map.put("tdsTotal", (int)(sdl.getNumFound()));
		}  catch (SolrServerException e) {
			e.printStackTrace();
		} 
		return map ;
	}
	
	/**
	 * 初始化索引
	 */
	@Override
	public void initIndex() {
		List<Article> nList = articleService.selectAllArticleToSolr();
		if(nList.size()>0){
			for(Article n:nList){
				addIndex(n);
			}
		}
	}
	
	/**
	 * 删除所有索引
	 */
	@Override
	public void deleteAll() {}
}
