﻿
package yggc.controller.sys.bms.article;

import java.util.List;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;


import yggc.controller.sys.bms.LoginController;
import yggc.model.bms.Article;
import yggc.model.bms.User;
import yggc.model.iss.ArticleType;
import yggc.service.bms.ArticleService;
import yggc.service.iss.ArticleTypeService;


/**
 * <p>Title:ArticleController </p>
 * <p>Description: 信息管理</p>
 * @author Shen Zhenfei
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
	* @author Shen Zhenfei
	* @date 2016-9-1 下午1:55:31  
	* @Description: 查询全部信息
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
	* @author Shen Zhenfei
	* @date 2016-9-1 下午1:57:04  
	* @Description: 跳转新增页面
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
	* @author Shen Zhenfei
	* @date 2016-9-1 下午2:00:40  
	* @Description: 保存
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Article article){
		String[] ranges = request.getParameterValues("range");
		if(ranges.length>1){
			article.setRange(2);
		}else{
			for(int i=0;i<ranges.length;i++){
				article.setRange(Integer.valueOf(ranges[i]));
			}
		}
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
	* @author Shen Zhenfei
	* @date 2016-9-1 下午2:01:32  
	* @Description: 跳转修改页面
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
	* @author Shen Zhenfei
	* @date 2016-9-1 下午2:05:08  
	* @Description: 修改
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Article article){
		String[] ranges = request.getParameterValues("range");
		if(ranges.length>1){
			article.setRange(2);
		}else{
			for(int i=0;i<ranges.length;i++){
				article.setRange(Integer.valueOf(ranges[i]));
			}
		}
		article.setUpdatedAt(new Date());
		articleService.update(article);
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei
	* @date 2016-9-2 上午10:52:42  
	* @Description: 假删除
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
	* @author Shen Zhenfei
	* @date 2016-9-5 下午1:18:50  
	* @Description: 查看信息
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
	* @author Shen Zhenfei
	* @date 2016-9-5 下午3:37:20  
	* @Description: 提交页面列表
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/sublist")
	public String sublist(Model model,Article article){
		List<Article> list = articleService.selectArticleByStatus(article);
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/sub/list";
	}
	
	/**
	* @Title: sublist
	* @author Shen Zhenfei
	* @date 2016-9-5 下午3:37:46  
	* @Description: 审核列表 
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/auditlist")
	public String auditlist(Model model,Article article){
		List<Article> list = articleService.selectArticleByStatus(article);
		model.addAttribute("list", list);
		logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
		return "article/audit/list";
	}
	
	/**
	* @Title: sumbit
	* @author Shen Zhenfei
	* @date 2016-9-5 下午1:55:35  
	* @Description: 提交、审核、退回
	* @param @param request
	* @param @param article
	* @param @return      
	* @return String
	 */
	@RequestMapping("/sumbit")
	public String sumbit(HttpServletRequest request, String ids){
		Article article = new Article();
		article.setUpdatedAt(new Date());

			String[] id=ids.split(",");
			for (String str : id) {
				article.setId(str);
				article.setStatus(1);
				articleService.update(article);
			}	
		
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: auditInfo
	* @author Shen Zhenfei
	* @date 2016-9-5 下午4:26:14  
	* @Description: 查看审核信息
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
	* @author Shen Zhenfei
	* @date 2016-9-5 下午4:59:59  
	* @Description: 信息审核
	* @param @param model
	* @param @param id
	* @param @param id
	* @param @return      
	* @return String
	 * @throws Exception 
	 */
	@RequestMapping("/audit")
	public  String audit(String id,Article article) throws Exception{
		
		if(article.getStatus()==2){
			article.setReason("");
			articleService.update(article);
		}else if(article.getStatus()==3){
			String reason = new String((article.getReason()).getBytes("ISO-8859-1") , "UTF-8");
			article.setReason(reason);
			articleService.update(article);
		}
		
		return "redirect:getAll.do";
	}
	
	/**
	* @Title: checkName
	* @author Shen Zhenfei
	* @date 2016-9-7 上午9:15:45  
	* @Description: 验证信息标题是否重复
	* @param @return      
	* @return boolean
	 */
	@ResponseBody
	@RequestMapping("/check")
	public boolean checkName(String name){
		boolean check = false;
		return check;
	}
	
	
}
