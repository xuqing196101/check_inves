package yggc.controller.sys.iss.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.bms.Article;
import yggc.model.iss.ArticleType;
import yggc.model.iss.IndexEntity;
import yggc.service.bms.ArticleService;
import yggc.service.iss.ArticleTypeService;
import yggc.service.iss.IndexNewsService;
import yggc.service.iss.SolrNewsService;
import yggc.util.SolrContext;

/**
 * 
 *<p>Title:IndexNewsController</p>
 *<p>Description:首页信息控制类</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-29上午8:50:21
 */
@Controller
@Scope("prototype")
@RequestMapping("/index")
public class IndexNewsController {
	
	@Autowired
	private IndexNewsService indexNewsService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SolrNewsService solrNewsService;
	
	/**
	 * 
	* @Title: selectIndexNews
	* @author Mrlovablee 
	* @date 2016-8-29 上午8:50:02  
	* @Description: 查询全部首页信息 
	* @param @return      
	* @return List<Article>
	 */
	@RequestMapping("/selectIndexNews")
	public String selectIndexNews(Model model) throws Exception{
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		List<ArticleType> articleTypeList = articleTypeService.selectArticleType();
		for(int i=0;i<articleTypeList.size();i++){
			List<Article> indexNewsList = indexNewsService.selectNewsByArticleTypeId(articleTypeList.get(i).getId());
			if(indexNewsList!=null){
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
			}else{
				List<Article> indexNews = new ArrayList<Article>();
				Article article = new Article();
				article.setArticleType(articleTypeList.get(i));
				indexNews.add(article);
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
			}
		}
		model.addAttribute("indexMapper", indexMapper);
		return "index/index";
	};
	
	/**
	 * 
	* @Title: selectIndexNewsByTypeId
	* @author Mrlovablee 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectIndexNewsByTypeId")
	public String selectIndexNewsByTypeId(ArticleType articleType,Model model) throws Exception{
		List<Article> indexNewsList = indexNewsService.selectNewsByArticleTypeId(articleType.getId());
		model.addAttribute("indexList", indexNewsList);
		model.addAttribute("typeName", indexNewsList.get(0).getArticleType().getName());
		return "index/index_two";
	}
	
	/**
	 * 
	* @Title: selectArticleNewsById
	* @author Mrlovablee 
	* @date 2016-8-31 下午5:11:48  
	* @Description: 详情页信息 
	* @param @param article
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectArticleNewsById")
	public String selectArticleNewsById(Article article,Model model) throws Exception{
		Article articleDetail = articleService.selectArticleById(article.getId());
		model.addAttribute("articleDetail", articleDetail);
		return "index/index_details";
	}
	
	/**
	 * 
	* @Title: solrSearch
	* @author Mrlovablee 
	* @date 2016-9-6 上午9:56:32  
	* @Description: 全文索引查询 
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/solrSearch")
	public String solrSearch(Model model,HttpServletRequest request) throws Exception{
		String condition = request.getParameter("condition");
		Map<String, Object> map = solrNewsService.findByIndex(condition);
		model.addAttribute("solrMap",map);
		model.addAttribute("oldCondition", condition);
		return "index/index_solr";
	}
}
