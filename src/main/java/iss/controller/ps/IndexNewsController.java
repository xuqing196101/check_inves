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
import bss.model.ppms.Packages;

import com.github.pagehelper.PageInfo;

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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "110");
		List<Article> article110List = articleService.selectJob(map);
		indexMapper.put("select110List", article110List);
		map.clear();
		map.put("parId","3");
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> article3List = articleService.selectArticleByParId(map);
		indexMapper.put("select3List", article3List);
		map.clear();
		map.put("parId","8");
		List<Article> article8List = articleService.selectArticleByParId(map);
		indexMapper.put("select8List", article8List);
		map.clear();
		map.put("parId","13");
		List<Article> article13List = articleService.selectArticleByParId(map);
		indexMapper.put("select13List", article13List);
		map.clear();
		map.put("parId","18");
		List<Article> article18List = articleService.selectArticleByParId(map);
		indexMapper.put("select18List", article18List);
		map.clear();
		map.put("parId","24");
		List<Article> article24List = articleService.selectArticleByParId(map);
		indexMapper.put("select24List", article24List);
		map.clear();
		map.put("parId","29");
		List<Article> article29List = articleService.selectArticleByParId(map);
		indexMapper.put("select29List", article29List);
		map.clear();
		map.put("parId","34");
		List<Article> article34List = articleService.selectArticleByParId(map);
		indexMapper.put("select34List", article34List);
		map.clear();
		map.put("parId","39");
		List<Article> article39List = articleService.selectArticleByParId(map);
		indexMapper.put("select39List", article39List);
		map.clear();
		map.put("parId","46");
		List<Article> article46List = articleService.selectArticleByParId(map);
		indexMapper.put("select46List", article46List);
		map.clear();
		map.put("parId","51");
		List<Article> article51List = articleService.selectArticleByParId(map);
		indexMapper.put("select51List", article51List);
		map.clear();
		map.put("parId","56");
		List<Article> article56List = articleService.selectArticleByParId(map);
		indexMapper.put("select56List", article56List);
		map.clear();
		map.put("parId","61");
		List<Article> article61List = articleService.selectArticleByParId(map);
		indexMapper.put("select61List", article61List);
		map.clear();
		map.put("parId","67");
		List<Article> article67List = articleService.selectArticleByParId(map);
		indexMapper.put("select67List", article67List);
		map.clear();
		map.put("parId","72");
		List<Article> article72List = articleService.selectArticleByParId(map);
		indexMapper.put("select72List", article72List);
		map.clear();
		map.put("parId","77");
		List<Article> article77List = articleService.selectArticleByParId(map);
		indexMapper.put("select77List", article77List);
		map.clear();
		map.put("parId","82");
		List<Article> article82List = articleService.selectArticleByParId(map);
		indexMapper.put("select82List", article82List);
		map.clear();
		map.put("typeId","89");
		List<Article> article89List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select89List", article89List);
		map.clear();
		map.put("typeId","90");
		List<Article> article90List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select90List", article90List);
		map.clear();
		map.put("typeId","91");
		List<Article> article91List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select91List", article91List);
		map.clear();
		map.put("typeId","92");
		List<Article> article92List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select92List", article92List);
		map.clear();
		map.put("typeId","94");
		List<Article> article94List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select94List", article94List);
		map.clear();
		map.put("typeId","95");
		List<Article> article95List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select95List", article95List);
		map.clear();
		map.put("typeId","96");
		List<Article> article96List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select96List", article96List);
		map.clear();
		map.put("typeId","97");
		List<Article> article97List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select97List", article97List);
		map.clear();
		map.put("typeId","103");
		List<Article> article103List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select103List", article103List);
		map.clear();
		map.put("typeId","104");
		List<Article> article104List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select104List", article104List);
		map.clear();
		map.put("typeId","105");
		List<Article> article105List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select105List", article105List);
		map.clear();
		map.put("typeId","107");
		List<Article> article107List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select107List", article107List);
		map.clear();
		map.put("typeId","108");
		List<Article> article108List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select108List", article108List);
		map.clear();
		map.put("typeId","109");
		List<Article> article109List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select109List", article109List);
		map.clear();
		map.put("typeId","112");
		List<Article> article112List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select112List", article112List);
		map.clear();
		map.put("typeId","113");
		List<Article> article113List = articleService.selectArticleByArticleType(map);
		indexMapper.put("select113List", article113List);
		List<Article> xinxicaiwuziList = new ArrayList<Article>();
		xinxicaiwuziList.addAll(article3List);
		xinxicaiwuziList.addAll(article24List);
		indexMapper.put("xinxicaiwuziList", xinxicaiwuziList);
		List<Article> xinxicaigongchengList = new ArrayList<Article>();
		xinxicaigongchengList.addAll(article8List);
		xinxicaigongchengList.addAll(article29List);
		indexMapper.put("xinxicaigongchengList", xinxicaigongchengList);
		List<Article> xinxicaifuwuList = new ArrayList<Article>();
		xinxicaifuwuList.addAll(article13List);
		xinxicaifuwuList.addAll(article34List);
		indexMapper.put("xinxicaifuwuList", xinxicaifuwuList);
		List<Article> xinxicaijinkouList = new ArrayList<Article>();
		xinxicaijinkouList.addAll(article18List);
		xinxicaijinkouList.addAll(article39List);
		indexMapper.put("xinxicaijinkouList", xinxicaijinkouList);
		List<Article> xinxizhongwuziList = new ArrayList<Article>();
		xinxizhongwuziList.addAll(article46List);
		xinxizhongwuziList.addAll(article67List);
		indexMapper.put("xinxizhongwuziList", xinxizhongwuziList);
		List<Article> xinxizhonggongchengList = new ArrayList<Article>();
		xinxizhonggongchengList.addAll(article51List);
		xinxizhonggongchengList.addAll(article72List);
		indexMapper.put("xinxizhonggongchengList", xinxizhonggongchengList);
		List<Article> xinxizhongfuwuList = new ArrayList<Article>();
		xinxizhongfuwuList.addAll(article56List);
		xinxizhongfuwuList.addAll(article77List);
		indexMapper.put("xinxizhongfuwuList", xinxizhongfuwuList);
		List<Article> xinxizhongjinkouList = new ArrayList<Article>();
		xinxizhongjinkouList.addAll(article61List);
		xinxizhongjinkouList.addAll(article82List);
		indexMapper.put("xinxizhongjinkouList", xinxizhongjinkouList);
		List<Article> xinxidanwuziList = new ArrayList<Article>();
		xinxidanwuziList.addAll(article89List);
		xinxidanwuziList.addAll(article94List);
		indexMapper.put("xinxidanwuziList", xinxidanwuziList);
		List<Article> xinxidangongchengList = new ArrayList<Article>();
		xinxidangongchengList.addAll(article90List);
		xinxidangongchengList.addAll(article95List);
		indexMapper.put("xinxidangongchengList", xinxidangongchengList);
		List<Article> xinxidanfuwuList = new ArrayList<Article>();
		xinxidanfuwuList.addAll(article91List);
		xinxidanfuwuList.addAll(article96List);
		indexMapper.put("xinxidanfuwuList", xinxidanfuwuList);
		List<Article> xinxidanjinkouList = new ArrayList<Article>();
		xinxidanjinkouList.addAll(article92List);
		xinxidanjinkouList.addAll(article97List);
		indexMapper.put("xinxidanjinkouList", xinxidanjinkouList);
		List<Article> faguiList = new ArrayList<Article>();
		faguiList.addAll(article107List);
		faguiList.addAll(article108List);
		indexMapper.put("faguiList", faguiList);
		
		map.clear();
		List<Article> picList = articleService.selectPics();
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
//		for(int i=0;i<articleTypeList.size();i++){
//			List<Article> indexNewsList = null;
//			if(articleTypeList.get(i).getName().equals("工作动态")){
//				indexNewsList = indexNewsService.selectNewsForJob(articleTypeList.get(i).getId());
//			}else{
//				indexNewsList = indexNewsService.selectNews(articleTypeList.get(i).getId());
//			}
////			if(!indexNewsList.isEmpty()){
//				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNewsList);
////			}else{
////				List<Article> indexNews = new ArrayList<Article>();
////				Article article = new Article();
////				article.setArticleType(articleTypeList.get(i));
////				indexNews.add(article);
////				indexMapper.put("select"+articleTypeList.get(i).getId()+"List", indexNews);
////			}
//		}
		model.addAttribute("indexMapper", indexMapper);
//		model.addAttribute("isIndex", "true");
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
	@RequestMapping("/selectIndexNewsByParId")
	public String selectIndexNewsByParId(Model model,Integer page,String id,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(page==null){
			page=1;
		}
		map.put("parId",id);
		map.put("page", page);
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = articleService.selectArticleByParIdTwo(map);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		return "iss/ps/index/parindex_two";
	}
	
	/**
	 * 
	* @Title: selectsumByParId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumByParId")
	public String selectsumByParId(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String[] idArray = new String[2];
		idArray[0] = "107";
		idArray[1] = "108";
		if(page==null){
			page=1;
		}
		map.put("idArray",idArray);
		map.put("page", page);
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = articleService.selectsumByParId(map);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		return "iss/ps/index/parindex_two";
	}
	
	/**
	 * 
	* @Title: selectsumByParId
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumBynews")
	public String selectsumBynews(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String twoid = request.getParameter("twoid");
		if(page==null){
			page=1;
		}
		map.put("id",id);
		map.put("twoid", twoid);
		map.put("page", page);
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = articleService.selectsumBynews(map);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		return "iss/ps/index/parindex_two";
	}
	
	/**
	 * 
	* @Title: selectsumBydanNews
	* @author QuJie 
	* @date 2016-8-31 下午1:22:28  
	* @Description: 根据指定的typeid来获取数据 
	* @param @param id
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectsumBydanNews")
	public String selectsumBydanNews(Model model,Integer page,HttpServletRequest request) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String twoid = request.getParameter("twoid");
		if(page==null){
			page=1;
		}
		map.put("id",id);
		map.put("twoid", twoid);
		map.put("page", page);
//		List<ArticleType> articleTypeList = articleTypeService.selectAllArticleTypeForSolr();
		List<Article> articleList = articleService.selectsumBydanNews(map);
		model.addAttribute("list", new PageInfo<Article>(articleList));
		model.addAttribute("indexList", articleList);
		return "iss/ps/index/parindex_two";
	}
	
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
		Integer startRow = 0;
		Integer endRow = 0;
		if(indexNewsList!=null){
			if(indexNewsList.size()>0){
				startRow = (page-1)*Integer.parseInt(pageSize)+1;
			}
			if(indexNewsList.size()>0){
				endRow = startRow+(indexNewsList.size()-1);
			}
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
		Integer pages=0;
		Integer startRow=0;
		Integer endRow=0;
		Map<String, Object> map = null;
		if(condition!=null && !condition.equals("")){
			map = solrNewsService.findByIndex(condition,page,Integer.parseInt(pageSize));
			pages = (Integer)map.get("tdsTotal");
			startRow = (page-1)*Integer.parseInt(pageSize)+1;
			endRow = startRow+(((List<IndexEntity>)map.get("indexList")).size()-1);
		}
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
	
	@RequestMapping("/init")
	public void init(){
		solrNewsService.initIndex();
	}
}
