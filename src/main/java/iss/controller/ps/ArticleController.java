package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;
import iss.service.ps.SolrNewsService;

import java.util.HashMap;
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
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

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
  
  @Autowired
  private UploadService uploadService;

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
  public String getAll(Model model,Integer page,HttpServletRequest request){
	String saveNews = request.getParameter("news");
	if(saveNews!=null){
	if(saveNews.equals("1")){
		model.addAttribute("saveNews", saveNews);
	}
	}
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
    
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
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
    String id = request.getParameter("id");
    String url = "";
    boolean flag = true;

    if(ValidateUtils.isNull(article.getName())){
      model.addAttribute("ERR_name", "标题名称不能为空");
      flag = false;
    }else 
      if(article.getName().length()>50){
      model.addAttribute("ERR_name", "标题名称不能超过50字符");
      flag = false;
    }
    List<Article> art = articleService.selectAllArticle(null,1);
    if(art!=null){
      for(Article ar:art){
        if(ar.getName().equals(article.getName())){
          flag = false;
          model.addAttribute("ERR_name", "标题名称不能重复");
        }
      }
    }
    String contype = request.getParameter("articleType.id");
    if(ValidateUtils.isNull(contype)){
      flag = false;
      model.addAttribute("ERR_typeId", "信息栏目不能为空");
    }
//    String isPicShow = request.getParameter("isPicShow");
//    if(isPicShow!=null&&!isPicShow.equals("")){
//      articleService.updateisPicShow(isPicShow);
//    }
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
    
    if(article.getSource() != null  && article.getSource().length()>50){
      model.addAttribute("ERR_source", "文章来源不能超过50字符");
      flag = false;
    }
    
    if(article.getSourceLink() != null  && article.getSourceLink().length()>100){
      model.addAttribute("ERR_sourceLink", "连接来源不能超过100字符");
      flag = false;
    }
    
    if(ValidateUtils.isNull(article.getContent())){
      flag = false;
      model.addAttribute("ERR_content", "信息正文不能为空");
    }
    
    if(article.getSecondArticleTypeId()!=null){
    	if(article.getSecondArticleTypeId().equals("111")){
    		List<UploadFile> gzdt= uploadService.findBybusinessId(id,Constant.FORUM_SYS_KEY);
    		if(gzdt.size()<1){
    			flag = false;
    			model.addAttribute("ERR_auditPic", "请上传图片!");
    		}
    	}
    }
    
    List<UploadFile> auditDoc = uploadService.findBybusinessId(id,Constant.EXPERT_SYS_KEY);
//	if(auditDoc.size()<1){
//		flag = false;
//		model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
//	}

    if(flag==false){
      model.addAttribute("article", article);
      model.addAttribute("articleId", id);
      url = "iss/ps/article/add";
    }else{
      if(ValidateUtils.isNull(article.getFourArticleTypeId())){
    	  if(ValidateUtils.isNull(article.getThreeArticleTypeId())){
    		  if(ValidateUtils.isNull(article.getSecondArticleTypeId())){
    			  article.setLastArticleTypeId(contype);
    		  }else{
    			  article.setLastArticleTypeId(article.getSecondArticleTypeId());
    		  }
    	  }else{
    		  article.setLastArticleTypeId(article.getThreeArticleTypeId());
    	  }
      }else{
    	  article.setLastArticleTypeId(article.getFourArticleTypeId());
      }
      User user = (User) request.getSession().getAttribute("loginUser");
      article.setUser(user);
      article.setCreatedAt(new Date());
      article.setUpdatedAt(new Date());
      article.setIsDeleted(0);
      article.setShowCount(0);
      article.setDownloadCount(0);
      articleService.addArticle(article);
      url = "redirect:getAll.html?news=1";
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
    //图片
    DictionaryData dd=new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if(lists.size()>0){
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    model.addAttribute("articleId", article.getId());
    //附件上传
    DictionaryData da=new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
    if(dlists.size()>0){
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }
    //审价文件
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    return "iss/ps/article/edit";
  }
  
  /**
  * @Title: auditEdit
  * @author Shen Zhenfei 
  * @date 2016-12-22 下午6:00:27  
  * @Description: 管理人员编辑信息页面
  * @param @param model
  * @param @param id
  * @param @param request
  * @param @return      
  * @return String
   */
  @RequestMapping("/auditEdit")
  public String auditEdit(Model model,String id,HttpServletRequest request){
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article",article);
    //图片
    DictionaryData dd=new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if(lists.size()>0){
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    model.addAttribute("articleId", article.getId());
    //附件上传
    DictionaryData da=new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
    if(dlists.size()>0){
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }
    //审价文件
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    return "iss/ps/article/audit/edit";
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
    if(article.getName().length()>50){
      model.addAttribute("ERR_name", "标题名称不得超过50字符");
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
    
    if(article.getSecondArticleTypeId()!=null){
    	if(article.getSecondArticleTypeId().equals("111")){
    		List<UploadFile> gzdt= uploadService.findBybusinessId(article.getId(),Constant.FORUM_SYS_KEY);
    		if(gzdt.size()<1){
    			model.addAttribute("ERR_auditPic", "请上传图片!");
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
    }
    
    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),Constant.EXPERT_SYS_KEY);
//	if(auditDoc.size()<1){
//        model.addAttribute("article.id", article.getId());
//        model.addAttribute("article.name", name);
//        Article artc = articleService.selectArticleById(article.getId());
//        List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
//        artc.setArticleAttachments(articleAttaList);
//        model.addAttribute("article",article);
//        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
//        model.addAttribute("list", list);
//        model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
//        return "iss/ps/article/edit";
//	}

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
  
    if(ValidateUtils.isNull(article.getContent())){
      model.addAttribute("ERR_content", "信息正文不能为空");
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
//      solrNewsService.deleteIndex(article.getId());
    }

//    String isPicShow = request.getParameter("isPicShow");
//    if(isPicShow!=null&&!isPicShow.equals("")){
//      articleService.updateisPicShow(isPicShow);
//    }

    if(ValidateUtils.isNull(article.getFourArticleTypeId())){
  	  if(ValidateUtils.isNull(article.getThreeArticleTypeId())){
  		  if(ValidateUtils.isNull(article.getSecondArticleTypeId())){
  			  article.setLastArticleTypeId(article.getArticleType().getId());
  		  }else{
  			  article.setLastArticleTypeId(article.getSecondArticleTypeId());
  		  }
  	  }else{
  		  article.setLastArticleTypeId(article.getThreeArticleTypeId());
  	  }
    }else{
  	  article.setLastArticleTypeId(article.getFourArticleTypeId());
    }
    article.setUpdatedAt(new Date());
    articleService.update(article);
    return "redirect:getAll.html?news=1";
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
//        solrNewsService.deleteIndex(article.getId());
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
    
    if(article.getSecondArticleTypeId()!=null){
    	ArticleType second = articleTypeService.selectTypeByPrimaryKey(article.getSecondArticleTypeId());
        model.addAttribute("second", second.getName());
    }
    if(article.getThreeArticleTypeId()!=null){
    	ArticleType three = articleTypeService.selectTypeByPrimaryKey(article.getThreeArticleTypeId());
        model.addAttribute("three", three.getName());
    }
    if(article.getFourArticleTypeId()!=null){
    	ArticleType four = articleTypeService.selectTypeByPrimaryKey(article.getFourArticleTypeId());
        model.addAttribute("four", four.getName());
    }
    DictionaryData dd=new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if(lists.size()>0){
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    return "iss/ps/article/look";
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
  public String auditlist(Model model,Integer status,Integer range,Integer page,HttpServletRequest request){
    Article article = new Article();
    ArticleType articleType = new ArticleType();

    String name = request.getParameter("name");
    String articleTypeId = request.getParameter("articleType.id");

    HashMap<String,Object> map = new HashMap<String,Object>();

    map.put("status",status);

    if(name!=null && !name.equals("")){
      map.put("name","%"+name+"%");
    }
    if(range!=null && !range.equals("")){
      map.put("range",range);
    }

    if(articleTypeId!=null && !articleTypeId.equals("")){
      articleType.setId(articleTypeId);
      article.setArticleType(articleType);
      map.put("articleType",article.getArticleType());
    }
    if(page==null){
      page = 1;
    }
    map.put("page", page.toString());
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));

    List<Article> list = articleService.selectArticleByStatus(map);
    
    
    model.addAttribute("articleId", article.getId());
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    
    model.addAttribute("list", new PageInfo<Article>(list));
    model.addAttribute("articleName", name);
    model.addAttribute("articlesRange", range);
    model.addAttribute("articlesStatus", status);
    model.addAttribute("article", article);
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
      articleService.updateStatus(article);
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
    
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
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
  public String audit(String id,Article article,HttpServletRequest request,Model model,Integer page) throws Exception{
    //Article findOneArticle = articleService.selectArticleById(article.getId());
	 article.setUpdatedAt(new Date());
    if(article.getStatus()==2){
    	article.setReason("");
    	article.setStatus(2);
      User user = (User) request.getSession().getAttribute("loginUser");
      article.setPublishedName(user.getRelName());
      article.setPublishedAt(new Date());
//      ArticleType articleType = findOneArticle.getLastArticleType();
//      Integer showNum = Integer.parseInt(articleType.getShowNum())+1;
//      articleType.setShowNum(showNum.toString());
//      articleTypeService.updateByPrimaryKey(articleType);
//      solrNewsService.addIndex(findOneArticle);
      articleService.update(article);
    }
    if(article.getStatus()==3){
      articleService.updateStatus(article);
    }

    List<Article> list = articleService.selectAllArticle(null, page==null?1:page);
    model.addAttribute("list", new PageInfo<Article>(list));

    model.addAttribute("status", "1");
    return "redirect:auditlist.html";
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
  public String serch(Integer range,Integer status,Integer page,Model model,HttpServletRequest request){
    Article article = new Article();
    ArticleType articleType = new ArticleType();

    String name = request.getParameter("name");
    String articleTypeId = request.getParameter("articleType.id");

    HashMap<String,Object> map = new HashMap<String,Object>();
    if(name!=null && !name.equals("")){
      map.put("name","%"+name+"%");
    }
    if(range!=null && !range.equals("")){
      map.put("range",range);
    }
    if(status!=null && !status.equals("")){
      map.put("status",status);
    }
    if(articleTypeId!=null && !articleTypeId.equals("")){
      articleType.setId(articleTypeId);
      article.setArticleType(articleType);
      map.put("articleType",article.getArticleType());
    }
    if(page==null){
      page = 1;
    }
    map.put("page", page.toString());
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(page,Integer.parseInt(config.getString("pageSizeArticle")));

    List<Article> list = articleService.selectArticleByName(map);
    model.addAttribute("list", new PageInfo<Article>(list));
    model.addAttribute("articleName", name);
    model.addAttribute("articlesRange", range);
    model.addAttribute("articlesStatus", status);
    model.addAttribute("article", article);
    return "iss/ps/article/list";
  }
  
  
  /**
  * @Title: apply
  * @author Shen Zhenfei 
  * @date 2016-12-22 上午9:16:15  
  * @Description: 发布按钮
  * @param @param request
  * @param @param ids
  * @param @return      
  * @return String
   */
  @RequestMapping("apply")
  public String apply(HttpServletRequest request,String ids){
    String[] id=ids.split(",");
    Article article = new Article();
    article.setStatus(2);
    for (String str : id) {
      article.setId(str);
      articleService.updateStatus(article);
      Article findOneArticle = articleService.selectArticleById(str);
//      solrNewsService.addIndex(findOneArticle);
    }
    return "redirect:getAll.html";
  }
  
  /**
  * @Title: withdraw
  * @author Shen Zhenfei 
  * @date 2016-12-22 上午9:16:27  
  * @Description: 撤回按钮
  * @param @param request
  * @param @param ids
  * @param @return      
  * @return String
   */
  @RequestMapping("withdraw")
  public String withdraw(HttpServletRequest request,String ids){
	  String[] id=ids.split(",");
	    Article article = new Article();
	    article.setStatus(4);
	    for (String str : id) {
	      article.setId(str);
	      articleService.updateStatus(article);
//	      solrNewsService.deleteIndex(str);
	    }
    return "redirect:getAll.html";
  }
  
  
  @RequestMapping("/updateApply")
  public String updateApply(String[] ranges,HttpServletRequest request,
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
      return "iss/ps/article/audit/edit";
    }
    if(article.getName().length()>50){
      model.addAttribute("ERR_name", "标题名称不得超过50字符");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article",artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
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
        return "iss/ps/article/audit/edit";
      }
    }
    
    if(article.getSecondArticleTypeId()!=null){
    	if(article.getSecondArticleTypeId().equals("111")){
    		List<UploadFile> gzdt= uploadService.findBybusinessId(article.getId(),Constant.FORUM_SYS_KEY);
    		if(gzdt.size()<1){
    			model.addAttribute("ERR_auditPic", "请上传图片!");
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
    }
    
    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),Constant.EXPERT_SYS_KEY);
	if(auditDoc.size()<1){
        model.addAttribute("article.id", article.getId());
        model.addAttribute("article.name", name);
        Article artc = articleService.selectArticleById(article.getId());
        List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
        artc.setArticleAttachments(articleAttaList);
        model.addAttribute("article",article);
        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", list);
        model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
        return "iss/ps/article/audit/edit";
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
      return "iss/ps/article/audit/edit";
    }
    if(ValidateUtils.isNull(article.getContent())){
      model.addAttribute("ERR_content", "信息正文不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article",article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
    }
    if(article.getStatus()!=null&&article.getStatus()==2){
//      solrNewsService.deleteIndex(article.getId());
    }

//    String isPicShow = request.getParameter("isPicShow");
//    if(isPicShow!=null&&!isPicShow.equals("")){
//      articleService.updateisPicShow(isPicShow);
//    }

    article.setPublishedAt(new Date());
    article.setUpdatedAt(new Date());
    article.setStatus(2);
    articleService.update(article);
    
    Article findOneArticle = articleService.selectArticleById(article.getId());
//    solrNewsService.addIndex(findOneArticle);
    
    return "redirect:auditlist.html";
  }
  
  /**
  * @Title: editor
  * @author Shen Zhenfei 
  * @date 2016-12-22 下午6:01:14  
  * @Description: 编辑人员编辑页面
  * @param @param model
  * @param @param id
  * @param @param request
  * @param @return      
  * @return String
   */
  @RequestMapping("/editor")
  public String editor(Model model,String id,HttpServletRequest request){
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article",article);
    //图片
    DictionaryData dd=new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if(lists.size()>0){
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    model.addAttribute("articleId", article.getId());
    //附件上传
    DictionaryData da=new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
    if(dlists.size()>0){
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }
    //审价文件
    DictionaryData sj=new DictionaryData();
    sj.setCode("SHWJ");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if(secrets.size()>0){
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    return "iss/ps/article/editor";
  }
  
  
  @RequestMapping("/updateEditor")
  public String updateEditor(String[] ranges,HttpServletRequest request,
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
      return "iss/ps/article/editor";
    }
    if(article.getName().length()>50){
      model.addAttribute("ERR_name", "标题名称不得超过50字符");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article",artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }
    
    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),Constant.EXPERT_SYS_KEY);
	if(auditDoc.size()<1){
        model.addAttribute("article.id", article.getId());
        model.addAttribute("article.name", name);
        Article artc = articleService.selectArticleById(article.getId());
        List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
        artc.setArticleAttachments(articleAttaList);
        model.addAttribute("article",article);
        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", list);
        model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
        return "iss/ps/article/editor";
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
        return "iss/ps/article/editor";
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
      return "iss/ps/article/editor";
    }
    if(ValidateUtils.isNull(article.getContent())){
      model.addAttribute("ERR_content", "信息正文不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService.selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article",article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }
    if(article.getStatus()!=null&&article.getStatus()==2){
//      solrNewsService.deleteIndex(article.getId());
    }

//    String isPicShow = request.getParameter("isPicShow");
//    if(isPicShow!=null&&!isPicShow.equals("")){
//      articleService.updateisPicShow(isPicShow);
//    }

    article.setUpdatedAt(new Date());
    article.setStatus(0);
    articleService.update(article);
    return "redirect:getAll.html";
  }
  
  
  /**
  * @Title: selectAritcleType
  * @author Shen Zhenfei 
  * @date 2016-12-26 上午10:36:17  
  * @Description: 查询栏目节点
  * @param @param response
  * @param @param request
  * @param @throws Exception      
  * @return void
   */
  @RequestMapping(value="/aritcleTypeParentId",produces="application/json;charest=utf-8")
  public void aritcleTypeParentId(HttpServletResponse response,String parentId,HttpServletRequest request) throws Exception{
    List<ArticleType> list = articleTypeService.selectByParentId(parentId);
    super.writeJson(response, list);
  }
  
  

}
