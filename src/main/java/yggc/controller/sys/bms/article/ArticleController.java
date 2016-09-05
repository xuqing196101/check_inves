
package yggc.controller.sys.bms.article;

import java.util.List;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSON;

import yggc.controller.sys.bms.LoginController;
import yggc.model.bms.Article;
import yggc.model.bms.User;
import yggc.model.iss.ArticleType;
import yggc.service.bms.ArticleService;
import yggc.service.bms.UserServiceI;
import yggc.service.iss.ArticleTypeService;


/**
 * <p>Title:ArticleController </p>
 * <p>Description: 信息管理</p>
 * <p>Company: yggc </p> 
 * @author szf
 * @date 2016-9-1上午9:48:48
 */
@Controller
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	/**
	* @Title: getAll
	* @author szf
	* @date 2016-9-1 下午1:55:31  
	* @Description: TODO 查询全部信息
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<Article> list = articleService.selectAllArticle();
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/list";
	}

	/**
	* @Title: add
	* @author szf
	* @date 2016-9-1 下午1:57:04  
	* @Description: TODO 跳转新增页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model){
		List<ArticleType> list = articleTypeService.selectArticleType();
		model.addAttribute("list", list);
		return "article/add";
	}
	
	/**
	* @Title: save
	* @author szf
	* @date 2016-9-1 下午2:00:40  
	* @Description: TODO 保存
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Article article){
		User user = new User();
		//user.setId(15);
		article.setUser(user);
		article.setCreatedAt(new Date());
		article.setUpdatedAt(new Date());
		article.setIsDeleted(0);
		articleService.addArticle(article);
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: exit
	* @author szf
	* @date 2016-9-1 下午2:01:32  
	* @Description: TODO 跳转修改页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String id){
		Article article = articleService.selectArticleById(id);
		model.addAttribute("article",article);
		return "article/edit";
	}

	/**
	* @Title: update
	* @author szf
	* @date 2016-9-1 下午2:05:08  
	* @Description: TODO 修改
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Article article){
		article.setUpdatedAt(new Date());
		articleService.update(article);
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: delete
	* @author szf
	* @date 2016-9-2 上午10:52:42  
	* @Description: TODO 假删除
	* @param @param request
	* @param @param id      
	* @return void
	 */
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			articleService.delete(str);
		}
		return "redirect:getAll.do";
	}
	
	
}
