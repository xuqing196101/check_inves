
package ses.controller.sys.bms.article;


import iss.model.article.ArticleType;
import iss.service.article.ArticleTypeService;

import java.util.List;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.bms.LoginController;
import ses.model.bms.Article;
import ses.model.bms.ArticleAttachments;
import ses.model.bms.User;
import ses.service.bms.ArticleAttachmentsService;
import ses.service.bms.ArticleService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;


/**
 * @Title:ArticleController
 * @Description: 信息管理
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
	
	@Autowired
	private ArticleAttachmentsService articleAttachmentsService;
	
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
	public String getAll(Model model,Integer page){
		List<Article> list = articleService.selectAllArticle(null, page==null?1:page);
		model.addAttribute("list", new PageInfo<Article>(list));
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
		List<ArticleType> list = articleTypeService.selectAllArticleType(null);
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
	public String save(@RequestParam("attaattach") MultipartFile[] attaattach,
            HttpServletRequest request, HttpServletResponse response,Article article){
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
		if(attaattach!=null){
			for(int i=0;i<attaattach.length;i++){
		        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
		        /** 创建文件夹 */
				File rootfile = new File(rootpath);
				if (!rootfile.exists()) {
					rootfile.mkdirs();
				}
		        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
		        String filePath = rootpath+fileName;
		        File file = new File(filePath);
		        try {
					attaattach[i].transferTo(file);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				ArticleAttachments attachment=new ArticleAttachments();
				attachment.setArticle(new Article(article.getId()));
				attachment.setFileName(fileName);
				attachment.setCreatedAt(new Date());
				attachment.setUpdatedAt(new Date());
				attachment.setContentType(attaattach[i].getContentType());
				attachment.setFileSize((float)attaattach[i].getSize());
				attachment.setAttachmentPath(filePath);
				articleAttachmentsService.insert(attachment);
			}
		}
		return "redirect:getAll.html";
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
		return "redirect:getAll.html";
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
		return "redirect:getAll.html";
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
	public String sublist(Model model,Article article,Integer page){
		List<Article> list = articleService.selectArticleByStatus(article,page==null?1:page);
		model.addAttribute("list", new PageInfo<Article>(list));
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
	public String auditlist(Model model,Article article,Integer page){
		List<Article> list = articleService.selectArticleByStatus(article,page==null?1:page);
		model.addAttribute("list", new PageInfo<Article>(list));
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
		
		return "redirect:getAll.html";
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
		
		return "redirect:getAll.html";
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
