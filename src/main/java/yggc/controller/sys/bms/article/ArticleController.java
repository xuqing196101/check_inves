package yggc.controller.sys.bms.article;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import yggc.model.bms.Article;
import yggc.model.bms.User;
import yggc.model.iss.ArticleType;
import yggc.service.bms.ArticleService;
import yggc.service.bms.UserServiceI;


/**
 * 
 *<p>Title:ArticleController</p>
 *<p>Description:信息管理接口类</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-10上午10:35:15
 */
@Controller
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private UserServiceI userService;
	
	private Logger logger = Logger.getLogger(ArticleController.class);

	/**
	 * 
	* @Title: newArticle
	* @author Mrlovablee 
	* @date 2016-8-22 下午3:26:20  
	* @Description: 创建信息发布新增页面
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/addArticlePage")
	public String addArticlePage() throws Exception{
		return "articles/addArticle";
	}
	
	/**
	 * 
	* @Title: articleList
	* @author Mrlovablee 
	* @date 2016-8-23 下午2:17:59  
	* @Description: 信息发布列表页 
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/articleList")
	public String articleList(Model model) throws Exception{
		List<Article> allArticleList = articleService.selectAllArticle();
		model.addAttribute("allArticleList",allArticleList);logger.info(JSON.toJSONStringWithDateFormat(allArticleList, "yyyy-MM-dd HH:mm:ss"));
		return "articles/newsList";
	}
	
	/**
	 * 
	* @Title: addArticle
	* @author Mrlovablee 
	* @date 2016-8-10 上午10:35:44  
	* @Description: 新增信息方法 
	* @param @param article
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addArticle")
	public void addArticle(Article article) throws Exception{
		ArticleType articleType = new ArticleType();//死数据
		List<User> userList = userService.getAll();
		article.setUser(userList.get(0));
		article.setArticleType(articleType);
		article.setCreatedAt(new Date());
		article.setUpdatedAt(new Date());
		articleService.addArticle(article);
	}
	
	/**
	 * 
	* @Title: editArticle
	* @author Mrlovablee 
	* @date 2016-8-23 下午2:51:09  
	* @Description: 修改信息方法 
	* @param @param article
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/editArticle")
	public void editArticle(Article article) throws Exception{
		articleService.editArticle(article);
	}
	
	/**
	 * 
	* @Title: editArticlePage
	* @author Mrlovablee 
	* @date 2016-8-23 下午2:53:58  
	* @Description: 跳转至修改信息页面 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("editArticlePage")
	public String editArticlePage() throws Exception{
		return "article/editArticle";
	}
	
	/**
	 * 
	* @Title: delArticle
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:01:33  
	* @Description: 删除信息 
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("delArticle")
	public void delArticle(Article article) throws Exception{
		articleService.delArticleById(article.getId());
	}
}
