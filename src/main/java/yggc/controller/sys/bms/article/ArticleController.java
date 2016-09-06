
package yggc.controller.sys.bms.article;

import java.io.UnsupportedEncodingException;
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
		user.setId("be9bf4e9-6fa3-4fe6-ad4a-cc67272816a2");
		article.setUser(user);
		article.setCreatedAt(new Date());
		article.setUpdatedAt(new Date());
		article.setIsDeleted(0);
		article.setStatus(0);
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
	
	/**
	* @Title: view
	* @author szf
	* @date 2016-9-5 下午1:18:50  
	* @Description: TODO 查看信息
	* @param @param model
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String id){
		Article article = articleService.selectArticleById(id);
		model.addAttribute("article",article);
		return "article/look";
	}
	
	/**
	* @Title: sublist
	* @author szf
	* @date 2016-9-5 下午3:37:20  
	* @Description: TODO 提交页面列表
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/sublist")
	public String sublist(Model model){
		List<Article> list = articleService.selectAllArticle();
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/sub/list";
	}
	
	/**
	* @Title: sublist
	* @author szf
	* @date 2016-9-5 下午3:37:46  
	* @Description: TODO 
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/auditlist")
	public String auditlist(Model model){
		List<Article> list = articleService.selectAllArticle();
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/audit/list";
	}
	
	/**
	* @Title: sumbit
	* @author szf
	* @date 2016-9-5 下午1:55:35  
	* @Description: TODO 提交、审核、退回
	* @param @param request
	* @param @param article
	* @param @return      
	* @return String
	 */
	@RequestMapping("/sumbit")
	public String sumbit(HttpServletRequest request, String ids){
		Article article = new Article();
		article.setUpdatedAt(new Date());
//		if(status==1){
			//提交
			String[] id=ids.split(",");
			for (String str : id) {
				article.setId(str);
				article.setStatus(1);
				articleService.update(article);
			}
//		}else if(status==2){
//			//审核
//			article.setStatus(status);
//			articleService.update(article);
//		}else if(status==3){
//			//退回
//			article.setStatus(status);
//			articleService.update(article);
//		}
		
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: auditInfo
	* @author szf
	* @date 2016-9-5 下午4:26:14  
	* @Description: TODO 查看审核信息
	* @param @param model
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/auditInfo")
	public  String auditInfo(Model model,String id){
		Article article = articleService.selectArticleById(id);
		model.addAttribute("article",article);
		return "article/audit/audit";
	}
	
	/**
	* @Title: audit
	* @author szf
	* @date 2016-9-5 下午4:59:59  
	* @Description: TODO 信息审核
	* @param @param model
	* @param @param id
	* @param @param id
	* @param @return      
	* @return String
	 * @throws Exception 
	 */
	@RequestMapping("/audit")
	public  String audit(HttpServletRequest request,String id,Article article) throws Exception{
		
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("id:"+id);
		String a = request.getParameter("reason");
		System.out.println("dfdfdf dfd f fd :"+a);
		System.out.println("iudhfjd:"+article.getId()+"考了几分好的尽快恢复的："+article.getReason());
		
//		if(article.getStatus()==2){
//			articleService.update(article);
//		}else if(article.getStatus()==3){
//			articleService.update(article);
//		}
		
		return "redirect:getAll.do";
	}
	
	
}
