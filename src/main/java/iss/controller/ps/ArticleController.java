package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;
import iss.service.ps.SolrNewsService;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.bms.LoginController;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import ses.util.ValidateUtils;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;

/**
 * @Title:ArticleController
 * @Description: 信息管理
 * @author Shen Zhenfei
 * @date 2016-9-1上午9:48:48
 */
@Controller
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController extends BaseSupplierController{
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleAttachmentsService articleAttachmentsService;
	
	@Autowired
	private SolrNewsService solrNewsService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
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
		return "iss/ps/article/list";
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
	public String add(Model model,HttpServletRequest request){
		String articleuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("articleId", articleuuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(lists.size()>0){
			model.addAttribute("attachTypeId", lists.get(0).getId());
		}
		DictionaryData da=new DictionaryData();
		da.setCode("GGWJ");
		List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
		request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
		if(dlists.size()>0){
			model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
		}
		return "iss/ps/article/add";
	}
	
	/**
	* @Title: selectAritcleType
	* @author Shen Zhenfei 
	* @date 2016-11-11 下午2:05:19  
	* @Description: select2查询栏目
	* @param @param response
	* @param @param request
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/selectAritcleType",produces="application/json;charest=utf-8")
	public void selectAritcleType(HttpServletResponse response,HttpServletRequest request) throws Exception{
		List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
		super.writeJson(response, list);
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
	public String save(String[] ranges,HttpServletRequest request, HttpServletResponse response,Article article,Model model){
		List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
		model.addAttribute("list", list);
		
		String url = "";
		boolean flag = true;
		
		if(ValidateUtils.isNull(article.getName())){
			model.addAttribute("ERR_name", "标题名称不能为空");
			flag = false;
		}
		List<Article> art = articleService.selectAllArticle(null,1);
		if(art!=null){
			for(Article ar:art){
				if(ar.getName().equals(article.getName())){
					flag = false;
					model.addAttribute("ERR_name", "标题名称不能重复");
					//return "iss/ps/article/add";
				}
			}
		}
		String contype = request.getParameter("articleType.id");
		if(ValidateUtils.isNull(contype)){
			flag = false;
			model.addAttribute("ERR_typeId", "信息类型不能为空");
		}
		String isPicShow = request.getParameter("isPicShow");
		if(isPicShow!=null&&!isPicShow.equals("")){
			articleService.updateisPicShow(isPicShow);
		}
		if(ranges!=null&&!ranges.equals("")){
			if(ranges.length>1){
				article.setRange(2);
			}else{
				for(int i=0;i<ranges.length;i++){
					article.setRange(Integer.valueOf(ranges[i]));
				}
			}
		}else{
			flag = false;
			model.addAttribute("ERR_range", "发布范围不能为空");
		}
		
		if(flag==false){
			model.addAttribute("article", article);
			url = "iss/ps/article/add";
		}else{
			User user = (User) request.getSession().getAttribute("loginUser");
			article.setUser(user);
			article.setCreatedAt(new Date());
			article.setUpdatedAt(new Date());
			article.setIsDeleted(0);
			article.setStatus(0);
			article.setShowCount(0);
			article.setDownloadCount(0);
			articleService.addArticle(article);
			url = "redirect:getAll.html";
		}
		return url;
	}
	
	/**
	 * 
	* @Title: uploadFile
	* @author QuJie 
	* @date 2016-9-9 下午1:36:34  
	* @Description: 上传的公共方法 
	* @param @param article
	* @param @param request
	* @param @param attaattach      
	* @return void
	 */
	public void uploadFile(Article article,HttpServletRequest request,MultipartFile[] attaattach){
		if(attaattach!=null){
			for(int i=0;i<attaattach.length;i++){
				if(attaattach[i].getOriginalFilename()!=null && attaattach[i].getOriginalFilename()!=""){
					String fileName = attaattach[i].getOriginalFilename();
					String path = (request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/").replace("\\", "/")+fileName;
					try {
						attaattach[i].transferTo(new File(path));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					FtpUtil.connectFtp(PropUtil.getProperty("file.upload.path.articlenews"));
					Map<String, Object> map = FtpUtil.uploadReturnUrl(new File(path));
					FtpUtil.closeFtp();
					File file = new File(path);
					if(file.isFile()){
						file.delete();
					}
//			        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
//			        /** 创建文件夹 */
//					File rootfile = new File(rootpath);
//					if (!rootfile.exists()) {
//						rootfile.mkdirs();
//					}
//			        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
//			        String filePath = rootpath+fileName;
//			        File file = new File(filePath);
//			        try {
//						attaattach[i].transferTo(file);
//					} catch (IllegalStateException e) {
//						e.printStackTrace();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
					ArticleAttachments attachment=new ArticleAttachments();
					attachment.setArticle(new Article(article.getId()));
					attachment.setFileName((String)map.get("fileName"));
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setContentType(attaattach[i].getContentType());
					attachment.setFileSize((float)attaattach[i].getSize());
					attachment.setAttachmentPath((String)map.get("url"));
					articleAttachmentsService.insert(attachment);
				}
			}
		}
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
	public String edit(Model model,String id,HttpServletRequest request){
		Article article = articleService.selectArticleById(id);
		List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(article.getId());
		article.setArticleAttachments(articleAttaList);
		model.addAttribute("article",article);
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(lists.size()>0){
			model.addAttribute("attachTypeId", lists.get(0).getId());
		}
		
		model.addAttribute("articleId", article.getId());
		DictionaryData da=new DictionaryData();
		da.setCode("GGWJ");
		List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
		request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
		if(dlists.size()>0){
			model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
		}
		return "iss/ps/article/edit";
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
	public String update(String[] ranges,HttpServletRequest request,
			HttpServletResponse response,Article article,Model model){
		String name = request.getParameter("name");
		String ids = request.getParameter("ids");
		if(ids!=null && ids!=""){
			String[] attaids = ids.split(",");
			for(String id : attaids){
				articleAttachmentsService.softDeleteAtta(id);
			}
		}
		if(ValidateUtils.isNull(article.getName())){
			model.addAttribute("ERR_name", "标题名称不能为空");
			model.addAttribute("article.id", article.getId());
			Article artc = articleService.selectArticleById(article.getId());
			List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
			artc.setArticleAttachments(articleAttaList);
			model.addAttribute("article",artc);
			List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
			model.addAttribute("list", list);
			return "iss/ps/article/edit";
		}
		
		List<Article> check = articleService.checkName(article);
		for(Article ar:check){
			if(ar.getName().equals(name)){
				model.addAttribute("ERR_name", "标题名称不能重复");
				model.addAttribute("article.id", article.getId());
				model.addAttribute("article.name", name);
				Article artc = articleService.selectArticleById(article.getId());
				List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
				artc.setArticleAttachments(articleAttaList);
				model.addAttribute("article",article);
				List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
				model.addAttribute("list", list);
				return "iss/ps/article/edit";
			}
		}
		
		if(ranges!=null&&!ranges.equals("")){
			if(ranges.length>1){
				article.setRange(2);
			}else{
				for(int i=0;i<ranges.length;i++){
					article.setRange(Integer.valueOf(ranges[i]));
				}
			}
		}else{
			model.addAttribute("ERR_range", "发布范围不能为空");
			model.addAttribute("article.id", article.getId());
			Article artc = articleService.selectArticleById(article.getId());
			List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
			artc.setArticleAttachments(articleAttaList);
			model.addAttribute("article",article);
			List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
			model.addAttribute("list", list);
			return "iss/ps/article/edit";
		}
		if(article.getStatus()!=null&&article.getStatus()==2){
			article.setStatus(0);
			solrNewsService.deleteIndex(article.getId());
		}
		
		String isPicShow = request.getParameter("isPicShow");
		if(isPicShow!=null&&!isPicShow.equals("")){
			articleService.updateisPicShow(isPicShow);
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
			Article article = articleService.selectArticleById(str);
			if(article.getStatus()==2){
				article.setStatus(0);
				solrNewsService.deleteIndex(article.getId());
				articleService.update(article);
			}
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
	public String view(Model model,String id,HttpServletRequest request){
		Article article = articleService.selectArticleById(id);
		List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(article.getId());
		article.setArticleAttachments(articleAttaList);
		model.addAttribute("article",article);
		List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
		model.addAttribute("list", list);
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(lists.size()>0){
			model.addAttribute("attachTypeId", lists.get(0).getId());
		}
		return "iss/ps/article/look";
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
		return "iss/ps/article/sub/list";
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
		return "iss/ps/article/audit/list";
	}
	
	/**
	* @Title: sumbit
	* @author Shen Zhenfei
	* @date 2016-9-5 下午1:55:35 
	* @Description: 提交
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
	public  String auditInfo(Model model,String id,HttpServletRequest request){
		Article article = articleService.selectArticleById(id);
		List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(article.getId());
		article.setArticleAttachments(articleAttaList);
		model.addAttribute("article",article);
		List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
		model.addAttribute("list", list);
		
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(lists.size()>0){
			model.addAttribute("attachTypeId", lists.get(0).getId());
		}
		
		model.addAttribute("articleId", article.getId());
		DictionaryData da=new DictionaryData();
		da.setCode("GGWJ");
		List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
		request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
		if(dlists.size()>0){
			model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
		}
		return "iss/ps/article/audit/audit";
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
	public String audit(String id,Article article,HttpServletRequest request) throws Exception{
		article.setUpdatedAt(new Date());
		if(article.getStatus()==2){
			article.setReason("");
			User user = (User) request.getSession().getAttribute("loginUser");
			article.setPublishedName(user.getRelName());
			article.setPublishedAt(new Date());
	//		solrNewsService.addIndex(article);
			articleService.update(article);
		}else if(article.getStatus()==3){
			String reason = new String((article.getReason()).getBytes("ISO-8859-1") , "UTF-8");
			article.setReason(reason);
			articleService.update(article);
		}
		
		return "redirect:getAll.html";
	}
	
	/**
	* @Title: serch
	* @author Shen Zhenfei 
	* @date 2016-9-18 下午2:02:05  
	* @Description: 根据标题查询列表
	* @param @param kname
	* @param @param page
	* @param @param stauts
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/serch")
	public String serch(String kname,Integer page,Integer status,Model model){
		Article article = new Article();
		if(kname!=null){
			article.setName("%"+kname+"%");
		}
		if(status!=null){
			if(status==0){
				article.setStatus(status);
				List<Article> list = articleService.selectArticleByName(article, page==null?1:page);
				model.addAttribute("list", new PageInfo<Article>(list));
				model.addAttribute("name", kname);
				return "iss/ps/article/sub/list";
			}else if(status==1){
				article.setStatus(status);
				List<Article> list = articleService.selectArticleByName(article, page==null?1:page);
				model.addAttribute("list", new PageInfo<Article>(list));
				model.addAttribute("name", kname);
				return "iss/ps/article/audit/list";
			}
		}else{
			List<Article> list = articleService.selectArticleByName(article, page==null?1:page);
			model.addAttribute("list", new PageInfo<Article>(list));
			model.addAttribute("name", kname);
			return "iss/ps/article/list";
		}
		return kname;
		
	}
	
}
