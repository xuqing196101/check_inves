package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.model.ps.DownloadUser;
import iss.model.ps.IndexEntity;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;
import iss.service.ps.DownloadUserService;
import iss.service.ps.IndexNewsService;
import iss.service.ps.SolrNewsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;


/*
 *@Title:IndexNewsController
 *@Description:首页信息控制类
 *@author QuJie
 *@date 2016-8-29上午8:50:21
 */
@Controller
@Scope("prototype")
@RequestMapping("/index")
public class IndexNewsController extends BaseSupplierController{
	
	@Autowired
	private IndexNewsService indexNewsService;
	
	@Autowired
	private ArticleTypeService articleTypeService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private SolrNewsService solrNewsService;
	
	@Autowired
	private ArticleAttachmentsService articleAttachmentsService;
	
	@Autowired
	private DownloadUserService downloadUserService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private UploadService uploadService;
	/**
	 * 
	* @Title: sign
	* @author Peng Zhongjun
	* @date 2016-11-10 上午8:50:09  
	* @Description: 跳转登录页面 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/sign")
	public String sign(){
		return "iss/ps/index/sign";
	}
	/**
	 * 
	* @Title: selectIndexNews
	* @author QuJie 
	* @date 2016-8-29 上午8:50:02  
	* @Description: 查询全部首页信息 
	* @param @return      
	* @return List<Article>
	 */
	@RequestMapping("/selectIndexNews")
	public String selectIndexNews(Model model) throws Exception{
		Map<String, Object> indexMapper = new HashMap<String, Object>();
		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> picList = articleService.selectPic();
		List<Article> indexPics = new ArrayList<Article>();
		for(Article article : picList){
			DictionaryData dd=new DictionaryData();
			dd.setCode("POST_ATTACHMENT");
			List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
			String sysKey = Constant.FORUM_SYS_KEY.toString();
			String attachTypeId=null;
			if(lists.size()>0){
				attachTypeId = lists.get(0).getId();
			}
			List<UploadFile> uploadList = uploadService.getFilesOther(article.getId(), attachTypeId, sysKey);
			if(uploadList.size()>0){
				article.setPic(uploadList.get(0).getPath());
			}
			indexPics.add(article);
		}
		indexMapper.put("picList", indexPics);
		for(int i=0;i<articleTypeList.size();i++){
			List<Article> indexNewsList = null;
			if(articleTypeList.get(i).getName().equals("工作动态")){
				indexNewsList = indexNewsService.selectNewsForJob(articleTypeList.get(i).getId());
			}else{
				indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
			}
//			if(!indexNewsList.isEmpty()){
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
//			}else{
//				List<Article> indexNews = new ArrayList<Article>();
//				Article article = new Article();
//				article.setArticleType(articleTypeList.get(i));
//				indexNews.add(article);
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
//			}
		}
		model.addAttribute("indexMapper", indexMapper);
		model.addAttribute("isIndex", "true");
		return "iss/ps/index/index";
	};
	
	/**
	 * 
	* @Title: selectIndexNewsByTypeId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectIndexNewsByTypeId")
	public String selectIndexNewsByTypeId(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> countMap = new HashMap<String, Object>();
		Map<String,Object> indexMapper = new HashMap<String, Object>();
		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		for(int i=0;i<26;i++){
			List<Article> indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
			if(!indexNewsList.isEmpty()){
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
			}else{
				List<Article> indexNews = new ArrayList<Article>();
				Article article = new Article();
				article.setArticleType(articleTypeList.get(i));
				indexNews.add(article);
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
			}
		}
		PropertiesUtil config = new PropertiesUtil("config.properties");
		String pageSize = config.getString("pageSize");
		if(page==null){
			page=1;
		}
		String articleTypeId = request.getParameter("id");
		map.put("articleTypeId", articleTypeId);
		map.put("page", page);
		map.put("pageSize", pageSize);
		countMap.put("articleTypeId", articleTypeId);
		List<Article> indexNewsList = indexNewsService.selectNewsByArticleTypeId(map);
		ArticleType articleTypeOne = articleTypeService.selectTypeByPrimaryKey(articleTypeId);
		Integer pages = indexNewsService.selectCount(countMap);
		Integer startRow = (page-1)*Integer.parseInt(pageSize)+1;
		Integer endRow = 0;
		if(indexNewsList!=null){
			endRow = (page-1)+indexNewsList.size();
		}
		model.addAttribute("total", pages);
		model.addAttribute("startRow", startRow);
		model.addAttribute("endRow", endRow);
		model.addAttribute("pages", Math.ceil((double)pages/Integer.parseInt(pageSize)));
		model.addAttribute("indexList", indexNewsList);
		model.addAttribute("typeName", articleTypeOne.getName());
		model.addAttribute("articleTypeId", articleTypeId);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/index_two";
	}
	
	/**
	 * 
	* @Title: selectArticleNewsById
	* @author QuJie 
	* @date 2016-8-31 下午5:11:48  
	* @Description: 详情页信息 
	* @param @param article
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectArticleNewsById")
	public String selectArticleNewsById(Article article,Model model,HttpServletRequest request) throws Exception{
		Article articleDetail = articleService.selectArticleById(article.getId());
		Integer showCount = articleDetail.getShowCount();
		articleDetail.setShowCount(showCount+1);
		articleService.update(articleDetail);
		Map<String,Object> indexMapper = new HashMap<String, Object>();
		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		for(int i=0;i<26;i++){
			List<Article> indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
			if(!indexNewsList.isEmpty()){
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
			}else{
				List<Article> indexNews = new ArrayList<Article>();
				Article articless = new Article();
				articless.setArticleType(articleTypeList.get(i));
				indexNews.add(articless);
				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
			}
		}
		model.addAttribute("articleId", article.getId());
		DictionaryData da=new DictionaryData();
		da.setCode("GGWJ");
		List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
		request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
		if(dlists.size()>0){
			model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
		}
//		List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(articleDetail.getId());
//		articleDetail.setArticleAttachments(articleAttaList);
		model.addAttribute("articleDetail", articleDetail);
		model.addAttribute("indexMapper", indexMapper);
		return "iss/ps/index/index_details";
	}
	
	/**
	 * 
	* @Title: solrSearch
	* @author QuJie 
	* @date 2016-9-6 上午9:56:32  
	* @Description: 全文索引查询 
	* @param @param model
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/solrSearch")
	public String solrSearch(Model model,HttpServletRequest request,Integer page) throws Exception{
		String condition = request.getParameter("condition");
		PropertiesUtil config = new PropertiesUtil("config.properties");
		String pageSize = config.getString("pageSize");
		if(page==null){
			page=1;
		}
		Map<String, Object> map = solrNewsService.findByIndex(condition,page,Integer.parseInt(pageSize));
		Integer pages = (Integer)map.get("tdsTotal");
		Integer startRow = (page-1)*Integer.parseInt(pageSize)+1;
		Integer endRow = startRow+(((List<IndexEntity>)map.get("indexList")).size()-1);
		model.addAttribute("total", pages);
		model.addAttribute("startRow", startRow);
		model.addAttribute("endRow", endRow);
		model.addAttribute("solrMap",map);
		model.addAttribute("oldCondition", condition);
		model.addAttribute("pages", Math.ceil((double)pages/Integer.parseInt(pageSize)));
		return "iss/ps/index/index_solr";
	}
	
	/**
	 * 
	* @Title: downloadArticleAtta
	* @author QuJie 
	* @date 2016-9-8 上午9:05:53  
	* @Description: 详情页下载附件 
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/downloadArticleAtta")
	public void downloadArticleAtta(HttpServletRequest request,ArticleAttachments articleAttachments,HttpServletResponse response) throws Exception{
		ArticleAttachments articleAtta = articleAttachmentsService.selectArticleAttaById(articleAttachments.getId());
//		String filePath = articleAtta.getAttachmentPath();
//		File file = new File(filePath);
//		if(file == null || !file.exists()){
//			return;
//		}
		Article article = articleService.selectArticleById(articleAtta.getArticle().getId());
		String floadername = PropUtil.getProperty("file.upload.path.articlenews");
		String path = (request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/").replace("\\", "/");
		String fileName = articleAtta.getFileName();
		FtpUtil.startDownFile(path, floadername, fileName);
		FtpUtil.closeFtp();
		if (fileName != null && !"".equals(fileName)) {
			super.download(request, response, fileName);
		}

		super.removeStash(request, fileName);
//		String fileName = (articleAtta.getFileName().split("_"))[1];
//		response.reset();
//		response.setContentType(articleAtta.getContentType()+"; charset=utf-8");
//		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//		OutputStream out = response.getOutputStream();
//		out.write(FileUtils.readFileToByteArray(file));
//		out.flush();
		DownloadUser downloadUser = new DownloadUser();
		downloadUser.setCreatedAt(new Date());
		downloadUser.setArticle(article);
		downloadUser.setIsDeleted(0);
		downloadUser.setUpdatedAt(new Date());
		User creater = (User) request.getSession().getAttribute("loginUser");
		downloadUser.setUserName(creater.getLoginName());
//		downloadUser.setUser("1231231");//死数据
		downloadUserService.addDownloadUser(downloadUser);
		article.setDownloadCount(article.getDownloadCount()+1);
		articleService.update(article);
//		if(out !=  null){
//			out.close();
//		}
	}
}
