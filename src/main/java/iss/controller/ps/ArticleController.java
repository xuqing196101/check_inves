package iss.controller.ps;

import iss.model.ps.Article;
import iss.model.ps.ArticleAttachments;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleAttachmentsService;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import ses.controller.sys.bms.LoginController;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;
import ses.util.ValidateUtils;
import app.service.IndexAppService;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import dss.model.rids.ArticleAnalyzeVo;

/**
 *  @Title:ArticleController  @Description: 信息管理  @author Shen Zhenfei  @date 2016-9-1上午9:48:48
 */
@Controller
@Scope("prototype")
@RequestMapping("/article")
public class ArticleController extends BaseSupplierController {

  @Autowired
  private ArticleService articleService;

  @Autowired
  private ArticleTypeService articleTypeService;

  @Autowired
  private ArticleAttachmentsService articleAttachmentsService;

  @Autowired
  private DictionaryDataServiceI dictionaryDataServiceI;

  @Autowired
  private UploadService uploadService;
  
  @Autowired
  private ProjectService projectService;
  
  @Autowired
  private CategoryService categoryService;
  
  //App接口Service注入
  @Autowired
  private IndexAppService indexAppService;

  //app公告图片生成所需参数
  private Article articless;
  private HttpServletRequest hsrequest;
  
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
  public String getAll(@CurrentUser User user, Model model, Integer page, HttpServletRequest request) {
    String saveNews = request.getParameter("news");
    if (saveNews != null) {
      if (saveNews.equals("1")) {
        model.addAttribute("saveNews", saveNews);
      }
      if (saveNews.equals("0")) {
        model.addAttribute("saveNews", saveNews);
      }
    }
    if (page == null) {
      page = 1;
    }
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("userId", user.getId());
    map.put("page", page);
    map.put("status", null);
    List<Article> list = articleService.selectByJurisDiction(map);
    model.addAttribute("list", new PageInfo<Article>(list));
    logger.info(JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss"));
    model.addAttribute("articlesStatus", "");
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
  public String add(@CurrentUser User user, Model model, HttpServletRequest request) {
    String articleuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
    model.addAttribute("articleId", articleuuid);
    DictionaryData dd = new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
    if (lists.size() > 0) {
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    DictionaryData da = new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    if (dlists.size() > 0) {
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }

    DictionaryData sj = new DictionaryData();
    sj.setCode("SECURITY_COMMITTEE");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    if (secrets.size() > 0) {
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    model.addAttribute("curUser", user);
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
  @RequestMapping(value = "/selectAritcleType", produces = "application/json;charest=utf-8")
  public void selectAritcleType(HttpServletResponse response, HttpServletRequest request)
      throws Exception {
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
  public String save(String[] ranges, HttpServletRequest request, HttpServletResponse response,
      Article article, Model model) {
    List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
    model.addAttribute("list", list);
    String id = request.getParameter("id");
    String url = "";
    boolean flag = true;
    //产品类别
    String categoryIds = request.getParameter("categoryId");
    if (ValidateUtils.isNull(article.getName())) {
      model.addAttribute("ERR_name", "标题名称不能为空");
      flag = false;
    } else if (article.getName().length() > 200) {
      model.addAttribute("ERR_name", "标题名称不能超过200个文字");
      flag = false;
    }
    /*List<Article> art = articleService.selectAllArticle(null, 1);
    if (art != null) {
      for (Article ar : art) {
        if (ar.getName().equals(article.getName())) {
          flag = false;
          model.addAttribute("ERR_name", "标题名称不能重复");
        }
      }
    }*/
    String contype = request.getParameter("articleType.id");
    if (ValidateUtils.isNull(contype)) {
      flag = false;
      model.addAttribute("ERR_typeId", "信息栏目不能为空");
    }
    // String isPicShow = request.getParameter("isPicShow");
    // if(isPicShow!=null&&!isPicShow.equals("")){
    // articleService.updateisPicShow(isPicShow);
    // }
    if (ranges != null && !ranges.equals("")) {
      if (ranges.length > 1) {
        article.setRange(2);
      } else {
        for (int i = 0; i < ranges.length; i++) {
          article.setRange(Integer.valueOf(ranges[i]));
        }
      }
    } else {
      flag = false;
      model.addAttribute("ERR_range", "发布范围不能为空");
    }

    if (article.getSource() != null && article.getSource().length() > 50) {
      model.addAttribute("ERR_source", "文章来源不能超过50文字");
      flag = false;
    }

    if (article.getSourceLink() != null && article.getSourceLink().length() > 100) {
      model.addAttribute("ERR_sourceLink", "连接来源不能超过100文字");
      flag = false;
    }

    if (ValidateUtils.isNull(article.getContent())) {
      flag = false;
      model.addAttribute("ERR_content", "信息正文不能为空");
    }

    if (article.getSecondArticleTypeId() != null) {
      if (article.getSecondArticleTypeId().equals("111")) {
        List<UploadFile> gzdt = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
        if (gzdt.size() < 1) {
          flag = false;
          model.addAttribute("ERR_auditPic", "请上传图片!");
        }
      }
    }

    List<UploadFile> auditDoc = uploadService.findBybusinessId(id, Constant.EXPERT_SYS_KEY);
    // if(auditDoc.size()<1){
    // flag = false;
    // model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
    // }
    
    if (flag == false) {
      model.addAttribute("article", article);
      model.addAttribute("articleId", id);
      DictionaryData dd = new DictionaryData();
      dd.setCode("POST_ATTACHMENT");
      List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
      model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
      if (lists.size() > 0) {
        model.addAttribute("attachTypeId", lists.get(0).getId());
      }
      DictionaryData da = new DictionaryData();
      da.setCode("GGWJ");
      List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
      if (dlists.size() > 0) {
        model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
      }

      DictionaryData sj = new DictionaryData();
      sj.setCode("SECURITY_COMMITTEE");
      List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
      if (secrets.size() > 0) {
        model.addAttribute("secretTypeId", secrets.get(0).getId());
      }
      if (categoryIds != null && !"".equals(categoryIds)) {
        Category category = categoryService.findById(categoryIds);
        model.addAttribute("categoryIds", category.getId());
        model.addAttribute("categoryNames", category.getName());
      }
      url = "iss/ps/article/add";
    } else {
      if (ValidateUtils.isNull(article.getFourArticleTypeId())) {
        if (ValidateUtils.isNull(article.getThreeArticleTypeId())) {
          if (ValidateUtils.isNull(article.getSecondArticleTypeId())) {
            article.setLastArticleTypeId(contype);
          } else {
            article.setLastArticleTypeId(article.getSecondArticleTypeId());
          }
        } else {
          article.setLastArticleTypeId(article.getThreeArticleTypeId());
        }
      } else {
        article.setLastArticleTypeId(article.getFourArticleTypeId());
      }
      User user = (User) request.getSession().getAttribute("loginUser");
      article.setUser(user);
      article.setCreatedAt(new Date());
      article.setUpdatedAt(new Date());
      article.setIsDeleted(0);
      // 设置IS_INDEX【是否索引】
      article.setIsIndex(0);
      
      article.setShowCount(0);
      article.setDownloadCount(0);
      if (article.getStatus() == 1) {
        article.setSubmitAt(new Date());
      }
      articleService.saveArtCategory(id, categoryIds);
      articleService.addArticle(article);
      if (article.getStatus() == 1) {
        url = "redirect:getAll.html?news=0";
      } else {
        url = "redirect:getAll.html?news=1";
      }
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
  public void uploadFile(Article article, HttpServletRequest request, MultipartFile[] attaattach) {
    if (attaattach != null) {
      for (int i = 0; i < attaattach.length; i++) {
        if (attaattach[i].getOriginalFilename() != null
            && attaattach[i].getOriginalFilename() != "") {
          String fileName = attaattach[i].getOriginalFilename();
          String path = (request.getSession().getServletContext().getRealPath("/")
              + PropUtil.getProperty("file.stashPath") + "/").replace("\\", "/")
              + fileName;
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
          if (file.isFile()) {
            file.delete();
          }
          // String rootpath =
          // (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\",
          // "/");
          // /** 创建文件夹 */
          // File rootfile = new File(rootpath);
          // if (!rootfile.exists()) {
          // rootfile.mkdirs();
          // }
          // String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_"
          // + attaattach[i].getOriginalFilename();
          // String filePath = rootpath+fileName;
          // File file = new File(filePath);
          // try {
          // attaattach[i].transferTo(file);
          // } catch (IllegalStateException e) {
          // e.printStackTrace();
          // } catch (IOException e) {
          // e.printStackTrace();
          // }
          ArticleAttachments attachment = new ArticleAttachments();
          attachment.setArticle(new Article(article.getId()));
          attachment.setFileName((String) map.get("fileName"));
          attachment.setCreatedAt(new Date());
          attachment.setUpdatedAt(new Date());
          attachment.setContentType(attaattach[i].getContentType());
          attachment.setFileSize((float) attaattach[i].getSize());
          attachment.setAttachmentPath((String) map.get("url"));
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
  public String edit(Model model, String id, HttpServletRequest request) {
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService
        .selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article", article);
    DictionaryData dd = new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
    if (lists.size() > 0) {
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    DictionaryData da = new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    if (dlists.size() > 0) {
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }

    DictionaryData sj = new DictionaryData();
    sj.setCode("SECURITY_COMMITTEE");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    if (secrets.size() > 0) {
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    
    //查询关联品目
    articleService.getArticleCategory(id, model);
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
  public String auditEdit(Model model, String id, HttpServletRequest request) {
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService
        .selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article", article);
    // 图片
    DictionaryData dd = new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if (lists.size() > 0) {
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    model.addAttribute("articleId", article.getId());
    // 附件上传
    DictionaryData da = new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
    if (dlists.size() > 0) {
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }
    // 审价文件
    DictionaryData sj = new DictionaryData();
    sj.setCode("SECURITY_COMMITTEE");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if (secrets.size() > 0) {
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
  public String update(String[] ranges, HttpServletRequest request, HttpServletResponse response, Article article, Model model) {
    List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
    model.addAttribute("list", list);
    String id = request.getParameter("id");
    String url = "";
    boolean flag = true;
    //产品类别
    String categoryIds = request.getParameter("categoryId");
    if (ValidateUtils.isNull(article.getName())) {
      model.addAttribute("ERR_name", "标题名称不能为空");
      flag = false;
    } else if (article.getName().length() > 200) {
      model.addAttribute("ERR_name", "标题名称不能超过200个文字");
      flag = false;
    }
    /*List<Article> art = articleService.selectAllArticle(null, 1);
    if (art != null) {
      for (Article ar : art) {
        if (ar.getName().equals(article.getName())) {
          flag = false;
          model.addAttribute("ERR_name", "标题名称不能重复");
        }
      }
    }*/
    String contype = request.getParameter("articleType.id");
    if (ValidateUtils.isNull(contype)) {
      flag = false;
      model.addAttribute("ERR_typeId", "信息栏目不能为空");
    }
    if (ranges != null && !"".equals(ranges)) {
      if (ranges.length > 1) {
        article.setRange(2);
      } else {
        for (int i = 0; i < ranges.length; i++) {
          article.setRange(Integer.valueOf(ranges[i]));
        }
      }
    } else {
      flag = false;
      model.addAttribute("ERR_range", "发布范围不能为空");
    }

    if (article.getSource() != null && article.getSource().length() > 50) {
      model.addAttribute("ERR_source", "文章来源不能超过50文字");
      flag = false;
    }

    if (article.getSourceLink() != null && article.getSourceLink().length() > 100) {
      model.addAttribute("ERR_sourceLink", "连接来源不能超过100文字");
      flag = false;
    }

    if (ValidateUtils.isNull(article.getContent())) {
      flag = false;
      model.addAttribute("ERR_content", "信息正文不能为空");
    }

    if (article.getSecondArticleTypeId() != null) {
      if (article.getSecondArticleTypeId().equals("111")) {
        List<UploadFile> gzdt = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
        if (gzdt.size() < 1) {
          flag = false;
          model.addAttribute("ERR_auditPic", "请上传图片!");
        }
      }
    }
    
    if (flag == false) {
      model.addAttribute("article", article);
      model.addAttribute("articleId", id);
      DictionaryData dd = new DictionaryData();
      dd.setCode("POST_ATTACHMENT");
      List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
      model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
      if (lists.size() > 0) {
        model.addAttribute("attachTypeId", lists.get(0).getId());
      }
      DictionaryData da = new DictionaryData();
      da.setCode("GGWJ");
      List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
      if (dlists.size() > 0) {
        model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
      }

      DictionaryData sj = new DictionaryData();
      sj.setCode("SECURITY_COMMITTEE");
      List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
      if (secrets.size() > 0) {
        model.addAttribute("secretTypeId", secrets.get(0).getId());
      }
      //回显关联品目  有问题
      //articleService.backArtCategory(categoryIds, model);
      if (categoryIds != null && !"".equals(categoryIds)) {
        Category category = categoryService.findById(categoryIds);
        model.addAttribute("categoryIds", category.getId());
        model.addAttribute("categoryNames", category.getName());
      }
      url = "iss/ps/article/edit";
    } else {
      if (ValidateUtils.isNull(article.getFourArticleTypeId())) {
        if (ValidateUtils.isNull(article.getThreeArticleTypeId())) {
          if (ValidateUtils.isNull(article.getSecondArticleTypeId())) {
            article.setLastArticleTypeId(contype);
          } else {
            article.setLastArticleTypeId(article.getSecondArticleTypeId());
          }
        } else {
          article.setLastArticleTypeId(article.getThreeArticleTypeId());
        }
      } else {
        article.setLastArticleTypeId(article.getFourArticleTypeId());
      }
      article.setUpdatedAt(new Date());
      if (article.getStatus() == 1) {
        article.setSubmitAt(new Date());
      }
      articleService.saveArtCategory(id, categoryIds);
      articleService.update(article);
      if (article.getStatus() == 1) {
        url = "redirect:getAll.html?news=0";
      } else {
        url = "redirect:getAll.html?news=1";
      }
    }
    return url;
  }
  
  
  /**
   * @Title: update
   * @author Shen Zhenfei
   * @date 2016-9-1 下午2:05:08
   * @Description: 修改
   * @param @return
   * @return String
   */
  @RequestMapping("/update1")
  public String update1(String[] ranges, HttpServletRequest request, HttpServletResponse response,
      Article article, Model model) {
    String name = request.getParameter("name");
    String ids = request.getParameter("ids");
    if (ids != null && ids != "") {
      String[] attaids = ids.split(",");
      for (String id : attaids) {
        articleAttachmentsService.softDeleteAtta(id);
      }
    }
    if (ValidateUtils.isNull(article.getName())) {
      model.addAttribute("ERR_name", "标题名称不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/edit";
    }
    if (article.getName().length() > 200) {
      model.addAttribute("ERR_name", "标题名称不得超过200个文字");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/edit";
    }
    List<Article> check = articleService.checkName(article);
    for (Article ar : check) {
      if (ar.getName().equals(name)) {
        model.addAttribute("ERR_name", "标题名称不能重复");
        model.addAttribute("article.id", article.getId());
        model.addAttribute("article.name", name);
        Article artc = articleService.selectArticleById(article.getId());
        List<ArticleAttachments> articleAttaList = articleAttachmentsService
            .selectAllArticleAttachments(artc.getId());
        artc.setArticleAttachments(articleAttaList);
        model.addAttribute("article", article);
        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", list);
        return "iss/ps/article/edit";
      }
    }

    if (article.getSecondArticleTypeId() != null) {
      if (article.getSecondArticleTypeId().equals("111")) {
        List<UploadFile> gzdt = uploadService.findBybusinessId(article.getId(),
            Constant.FORUM_SYS_KEY);
        if (gzdt.size() < 1) {
          model.addAttribute("ERR_auditPic", "请上传图片!");
          model.addAttribute("article.id", article.getId());
          model.addAttribute("article.name", name);
          Article artc = articleService.selectArticleById(article.getId());
          List<ArticleAttachments> articleAttaList = articleAttachmentsService
              .selectAllArticleAttachments(artc.getId());
          artc.setArticleAttachments(articleAttaList);
          model.addAttribute("article", article);
          List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
          model.addAttribute("list", list);
          return "iss/ps/article/edit";
        }
      }
    }

    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),
        Constant.EXPERT_SYS_KEY);
    // if(auditDoc.size()<1){
    // model.addAttribute("article.id", article.getId());
    // model.addAttribute("article.name", name);
    // Article artc = articleService.selectArticleById(article.getId());
    // List<ArticleAttachments> articleAttaList =
    // articleAttachmentsService.selectAllArticleAttachments(artc.getId());
    // artc.setArticleAttachments(articleAttaList);
    // model.addAttribute("article",article);
    // List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
    // model.addAttribute("list", list);
    // model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
    // return "iss/ps/article/edit";
    // }

    if (ranges != null && !ranges.equals("")) {
      if (ranges.length > 1) {
        article.setRange(2);
      } else {
        for (int i = 0; i < ranges.length; i++) {
          article.setRange(Integer.valueOf(ranges[i]));
        }
      }
    } else {
      model.addAttribute("ERR_range", "发布范围不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/edit";
    }

    if (ValidateUtils.isNull(article.getContent())) {
      model.addAttribute("ERR_content", "信息正文不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/edit";
    }
    if (article.getStatus() != null && article.getStatus() == 2) {
      article.setStatus(0);
      // solrNewsService.deleteIndex(article.getId());
    }

    // String isPicShow = request.getParameter("isPicShow");
    // if(isPicShow!=null&&!isPicShow.equals("")){
    // articleService.updateisPicShow(isPicShow);
    // }

    if (ValidateUtils.isNull(article.getFourArticleTypeId())) {
      if (ValidateUtils.isNull(article.getThreeArticleTypeId())) {
        if (ValidateUtils.isNull(article.getSecondArticleTypeId())) {
          article.setLastArticleTypeId(article.getArticleType().getId());
        } else {
          article.setLastArticleTypeId(article.getSecondArticleTypeId());
        }
      } else {
        article.setLastArticleTypeId(article.getThreeArticleTypeId());
      }
    } else {
      article.setLastArticleTypeId(article.getFourArticleTypeId());
    }
    if (article.getStatus() == 1) {
      article.setSubmitAt(new Date());
    }
    article.setUpdatedAt(new Date());
    articleService.update(article);
    String url = "";
    if (article.getStatus() == 1) {
      url = "redirect:getAll.html?news=0";
    } else {
      url = "redirect:getAll.html?news=1";
    }
    return url;
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
  public String delete(HttpServletRequest request, String ids) {
    String[] id = ids.split(",");
    for (String str : id) {
      Article article = articleService.selectArticleById(str);
      if (article.getStatus() == 2) {
        article.setStatus(0);
        // solrNewsService.deleteIndex(article.getId());
        articleService.update(article);
      }
      articleService.delete(str);
    }
    return "redirect:getAll.html";
  }
  
  @RequestMapping("auditDelete")
  public String auditDelete(@CurrentUser User user,HttpServletRequest request, String ids) {
    if(null != user && "4".equals(user.getTypeName())){
	    //判断是否 是资源服务中心 
	    String[] id = ids.split(",");
	    for (String str : id) {
	      articleService.delete(str);
	    }
	    return "redirect:auditlist.html?status=1";
    }
    return "";
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
  public String view(Model model, String id, HttpServletRequest request, String articleTypeId, Integer range,Integer status, String title, Integer curpage) {
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService
        .selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article", article);

    if (article.getSecondArticleTypeId() != null) {
      ArticleType second = articleTypeService.selectTypeByPrimaryKey(article
          .getSecondArticleTypeId());
      model.addAttribute("second", second.getName());
    }
    if (article.getThreeArticleTypeId() != null) {
      ArticleType three = articleTypeService
          .selectTypeByPrimaryKey(article.getThreeArticleTypeId());
      model.addAttribute("three", three.getName());
    }
    if (article.getFourArticleTypeId() != null) {
      ArticleType four = articleTypeService.selectTypeByPrimaryKey(article.getFourArticleTypeId());
      model.addAttribute("four", four.getName());
    }
    DictionaryData dd = new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
    if (lists.size() > 0) {
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    DictionaryData da = new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    if (dlists.size() > 0) {
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }

    DictionaryData sj = new DictionaryData();
    sj.setCode("SECURITY_COMMITTEE");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    if (secrets.size() > 0) {
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    //查询关联品目
    articleService.getArticleCategory(id, model);
    model.addAttribute("status", status);
    model.addAttribute("curpage", curpage);
    model.addAttribute("articleTypeId", articleTypeId);
    model.addAttribute("title", title);
    model.addAttribute("range", range);
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
  public String auditlist(@CurrentUser User user,Model model, String articleTypeId, String secondArticleTypeId, Integer status, Integer range, Integer page, Date publishStartDate, Date publishEndDate, HttpServletRequest request) {
	//声明标识是否是资源服务中心
    String authType = null;
    if(null != user && "4".equals(user.getTypeName())){
	    //判断是否 是资源服务中心 
	    authType = "4";
	    model.addAttribute("authType", authType);
	    Article article = new Article();
	    ArticleType articleType = new ArticleType();
	    String name = request.getParameter("name");
	    
	
	    HashMap<String, Object> map = new HashMap<String, Object>();
	
	    // map.put("status",status);
	
	    if (name != null && !name.equals("")) {
	      map.put("name", "%" + name + "%");
	    }
	    if (range != null && !range.equals("")) {
	      map.put("range", range);
	    }
	
	    if (articleTypeId != null && !"".equals(articleTypeId)) {
	      articleType.setId(articleTypeId);
	      article.setArticleType(articleType);
	      map.put("articleType", article.getArticleType());
	    }
	    
	    if (secondArticleTypeId != null && !"null".equals(secondArticleTypeId) && !"".equals(secondArticleTypeId)) {
	      map.put("secondArticleTypeId", secondArticleTypeId);
	    }
	    
	    if (publishStartDate != null) {
	      String startDate = new SimpleDateFormat("yyyy-MM-dd").format(publishStartDate);
	      map.put("publishStartDate", startDate);
	    }
	    
	    if (publishEndDate != null) {
	      String endDate = new SimpleDateFormat("yyyy-MM-dd").format(publishEndDate);
	      map.put("publishEndDate", endDate);
	    }
	    
	    if (page == null) {
	      page = 1;
	    }
	    map.put("page", page.toString());
	    PropertiesUtil config = new PropertiesUtil("config.properties");
	    PageHelper.startPage(page, Integer.parseInt(config.getString("pageSizeArticle")));
	    if (status != null && !status.equals("")) {
	      map.put("status", status);
	    } 
	    List<Article> list = articleService.selectArticleByStatus(map);
	
	    model.addAttribute("articleId", article.getId());
	    DictionaryData sj = new DictionaryData();
	    sj.setCode("SECURITY_COMMITTEE");
	    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
	    request.getSession().setAttribute("sysKey", Constant.TENDER_SYS_KEY);
	    if (secrets.size() > 0) {
	      model.addAttribute("secretTypeId", secrets.get(0).getId());
	    }
	    Integer num = 0;
	    StringBuilder groupUpload = new StringBuilder("");
	    StringBuilder groupShow = new StringBuilder("");
	    for (Article a : list) {
	      num++;
	      groupUpload = groupUpload.append("artice_secret_show" + num + ",");
	      groupShow = groupShow.append("artice_secret_show" + num + ",");
	      a.setGroupsUpload("artice_secret_show" + num);
	      a.setGroupShow("artice_secret_show" + num);
	    }
	    String groupUploadId = "";
	    String groupShowId = "";
	    if (!"".equals(groupUpload.toString())) {
	      groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length() - 1);
	    }
	    if (!"".equals(groupShow.toString())) {
	      groupShowId = groupShow.toString().substring(0, groupShow.toString().length() - 1);
	    }
	    for (Article act : list) {
	      act.setGroupsUploadId(groupUploadId);
	      act.setGroupShowId(groupShowId);
	    }
	    model.addAttribute("list", new PageInfo<Article>(list));
	    model.addAttribute("articleName", name);
	    model.addAttribute("articlesRange", range);
	    model.addAttribute("articlesStatus", status);
	    model.addAttribute("articlesArticleTypeId", articleTypeId);
	    model.addAttribute("curpage", page);
	    model.addAttribute("article", article);
	    model.addAttribute("publishStartDate", publishStartDate);
	    model.addAttribute("publishEndDate", publishEndDate);
	    model.addAttribute("secondArticleTypeId", secondArticleTypeId);
    }
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
  public String sumbit(HttpServletRequest request, String ids) {
    Article article = new Article();
    article.setUpdatedAt(new Date());
    article.setSubmitAt(new Date());

    String[] id = ids.split(",");
    for (String str : id) {
      article.setId(str);
      article.setStatus(1);
      article.setUpdatedAt(new Date());
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
  public String auditInfo(@CurrentUser User user,Model model, String id, HttpServletRequest request) {
    if(null != user && "4".equals(user.getTypeName())){
	    //判断是否 是资源服务中心 
	    Article article = articleService.selectArticleById(id);
	    List<ArticleAttachments> articleAttaList = articleAttachmentsService
	        .selectAllArticleAttachments(article.getId());
	    article.setArticleAttachments(articleAttaList);
	    model.addAttribute("article", article);
	    List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
	    model.addAttribute("list", list);
	
	    DictionaryData dd = new DictionaryData();
	    dd.setCode("POST_ATTACHMENT");
	    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
	    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
	    if (lists.size() > 0) {
	      model.addAttribute("attachTypeId", lists.get(0).getId());
	    }
	
	    model.addAttribute("articleId", article.getId());
	    DictionaryData da = new DictionaryData();
	    da.setCode("GGWJ");
	    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
	    if (dlists.size() > 0) {
	      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
	    }
	
	    DictionaryData sj = new DictionaryData();
	    sj.setCode("SECURITY_COMMITTEE");
	    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
	    if (secrets.size() > 0) {
	      model.addAttribute("secretTypeId", secrets.get(0).getId());
	    }
	    //查询关联品目
	    articleService.getArticleCategory(id, model);
	    return "iss/ps/article/audit/audit";
    }
    return "";
  }

  @RequestMapping("/showaudit")
  public String showaudit(@CurrentUser User user,Model model, String id, HttpServletRequest request, ArticleAnalyzeVo articleAnalyzeVo, String reqType, String status, String articleTypeId, Integer curpage, Integer range, String title, String secondArticleTypeId, Date startDate, Date endDate) {
    if(null != user && "4".equals(user.getTypeName())){
	    //判断是否 是资源服务中心 
	    Article article = articleService.selectArticleById(id);
	    List<ArticleAttachments> articleAttaList = articleAttachmentsService
	        .selectAllArticleAttachments(article.getId());
	    article.setArticleAttachments(articleAttaList);
	    model.addAttribute("article", article);
	    List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
	    model.addAttribute("list", list);
	
	    DictionaryData dd = new DictionaryData();
	    dd.setCode("POST_ATTACHMENT");
	    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
	    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
	    if (lists.size() > 0) {
	      model.addAttribute("attachTypeId", lists.get(0).getId());
	    }
	
	    model.addAttribute("articleId", article.getId());
	    DictionaryData da = new DictionaryData();
	    da.setCode("GGWJ");
	    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
	    if (dlists.size() > 0) {
	      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
	    }
	
	    DictionaryData sj = new DictionaryData();
	    sj.setCode("SECURITY_COMMITTEE");
	    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
	    if (secrets.size() > 0) {
	      model.addAttribute("secretTypeId", secrets.get(0).getId());
	    }
	    //查询关联品目
	    articleService.getArticleCategory(id, model);
	    model.addAttribute("articleName", title);
	    model.addAttribute("articlesRange", range);
	    model.addAttribute("articlesStatus", status);
	    model.addAttribute("articlesArticleTypeId", articleTypeId);
	    model.addAttribute("publishStartDate", endDate);
	    model.addAttribute("publishEndDate", startDate);
	    model.addAttribute("secondArticleTypeId", secondArticleTypeId);
	    model.addAttribute("curpage", curpage);
	    model.addAttribute("articleAnalyzeVo", articleAnalyzeVo);
	    model.addAttribute("reqType", reqType);
	    return "iss/ps/article/audit/showaudit";
    }
    return "";
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
  public String audit(String[] ranges, String id, Article article, HttpServletRequest request,
      Model model, Integer page) throws Exception {
    // Article findOneArticle = articleService.selectArticleById(article.getId());
    Article temp = articleService.selectArticleById(article.getId());
    article.setSubmitAt(temp.getSubmitAt());
    article.setUpdatedAt(new Date());
    String url = "";
    //产品类别
    String categoryIds = request.getParameter("categoryId");
    
    if (article.getStatus() == 2) {
      article.setReason("");
      article.setStatus(2);
      User user = (User) request.getSession().getAttribute("loginUser");
      List<ArticleType> listType = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", listType);
      boolean flag = true;

      if (ValidateUtils.isNull(article.getName())) {
        model.addAttribute("ERR_name", "标题名称不能为空");
        flag = false;
      } else if (article.getName().length() > 200) {
        model.addAttribute("ERR_name", "标题名称不能超过200汉字");
        flag = false;
      }
      /*List<Article> art = articleService.selectAllArticle(null, 1);
      if (art != null) {
        for (Article ar : art) {
          if (ar.getName().equals(article.getName())) {
            if (ar.getId().equals(article.getId())) {
              continue;
            } else {
              flag = false;
              model.addAttribute("ERR_name", "标题名称不能重复");
            }
          }
        }
      }*/
      String contype = request.getParameter("articleType.id");
      if (ValidateUtils.isNull(contype)) {
        flag = false;
        model.addAttribute("ERR_typeId", "信息栏目不能为空");
      }
      // String isPicShow = request.getParameter("isPicShow");
      // if(isPicShow!=null&&!isPicShow.equals("")){
      // articleService.updateisPicShow(isPicShow);
      // }
      if (ranges != null && !ranges.equals("")) {
        if (ranges.length > 1) {
          article.setRange(2);
        } else {
          for (int i = 0; i < ranges.length; i++) {
            article.setRange(Integer.valueOf(ranges[i]));
          }
        }
      } else {
        flag = false;
        model.addAttribute("ERR_range", "发布范围不能为空");
      }

      if (article.getSource() != null && article.getSource().length() > 50) {
        model.addAttribute("ERR_source", "文章来源不能超过50字符");
        flag = false;
      }

      if (article.getSourceLink() != null && article.getSourceLink().length() > 100) {
        model.addAttribute("ERR_sourceLink", "连接来源不能超过100字符");
        flag = false;
      }

      if (ValidateUtils.isNull(article.getContent())) {
        flag = false;
        model.addAttribute("ERR_content", "信息正文不能为空");
      }

      if (article.getSecondArticleTypeId() != null) {
        if (article.getSecondArticleTypeId().equals("111")) {
          List<UploadFile> gzdt = uploadService.findBybusinessId(id, Constant.TENDER_SYS_KEY);
          if (gzdt.size() < 1) {
            flag = false;
            model.addAttribute("ERR_auditPic", "请上传图片!");
          }
        }
      }

      List<UploadFile> auditDoc = uploadService.findBybusinessId(id, Constant.EXPERT_SYS_KEY);
      // if(auditDoc.size()<1){
      // flag = false;
      // model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
      // }

      DictionaryData dd = new DictionaryData();
      dd.setCode("POST_ATTACHMENT");
      List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
      model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
      if (lists.size() > 0) {
        model.addAttribute("attachTypeId", lists.get(0).getId());
      }

      model.addAttribute("articleId", article.getId());
      DictionaryData da = new DictionaryData();
      da.setCode("GGWJ");
      List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
      if (dlists.size() > 0) {
        model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
      }

      DictionaryData sj = new DictionaryData();
      sj.setCode("SECURITY_COMMITTEE");
      List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
      if (secrets.size() > 0) {
        model.addAttribute("secretTypeId", secrets.get(0).getId());
      }

      if (flag == false) {
        model.addAttribute("article", article);
        model.addAttribute("articleId", id);
        List<ArticleType> articleList = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", articleList);
        //查询关联品目
        //articleService.getArticleCategory(id, model);
        if (categoryIds != null && !"".equals(categoryIds)) {
          Category category = categoryService.findById(categoryIds);
          model.addAttribute("categoryIds", category.getId());
          model.addAttribute("categoryNames", category.getName());
        }
        url = "iss/ps/article/audit/audit";
      } else {
        article.setPublishedName(user.getRelName());
        article.setPublishedAt(new Date());
        // ArticleType articleType = findOneArticle.getLastArticleType();
        // Integer showNum = Integer.parseInt(articleType.getShowNum())+1;
        // articleType.setShowNum(showNum.toString());
        // articleTypeService.updateByPrimaryKey(articleType);
        // solrNewsService.addIndex(findOneArticle);
        if (ValidateUtils.isNull(article.getFourArticleTypeId())) {
          if (ValidateUtils.isNull(article.getThreeArticleTypeId())) {
            if (ValidateUtils.isNull(article.getSecondArticleTypeId())) {
              article.setLastArticleTypeId(contype);
            } else {
              article.setLastArticleTypeId(article.getSecondArticleTypeId());
            }
          } else {
            article.setLastArticleTypeId(article.getThreeArticleTypeId());
          }
        } else {
          article.setLastArticleTypeId(article.getFourArticleTypeId());
        }
        
        //更新项目中的公告发布时间
        String projectId = temp.getProjectId();
        if (projectId != null && !"".equals(projectId)) {
            Project project = projectService.selectById(projectId);
              if (project != null) {
                  project.setAppTime(new Date());
                  projectService.update(project);
              }
        }
        
        articleService.saveArtCategory(id, categoryIds);
        articleService.update(article);

        //判断为外网发布的公告   生成app公告查看图片
        PropertiesUtil config = new PropertiesUtil("config.properties");
        if("1".equals(config.getString("ipAddressType"))){
        	articless = article;
            hsrequest = request;
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
            cachedThreadPool.submit(new Runnable() {
    			@Override
    			public void run() {
    				indexAppService.getContentImg(articless, hsrequest);
    			}
    		});
        }
        
        List<Article> list = articleService.selectAllArticle(null, page == null ? 1 : page);
        model.addAttribute("list", new PageInfo<Article>(list));

        // model.addAttribute("status", "1");
        url = "redirect:auditlist.html?status=1";
      }
    } else if (article.getStatus() == 3) {
      if (ranges != null && !ranges.equals("")) {
        if (ranges.length > 1) {
          article.setRange(2);
        } else {
          for (int i = 0; i < ranges.length; i++) {
            article.setRange(Integer.valueOf(ranges[i]));
          }
        }
      }
      if (article.getReason().length() > 300) {
        model.addAttribute("ERR_reason", "退回理由不能超过300个汉字");
        model.addAttribute("article", article);
        model.addAttribute("articleId", id);
        List<ArticleType> articleList = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", articleList);
        //查询关联品目
        articleService.getArticleCategory(id, model);
        url = "iss/ps/article/audit/audit";
      }else {
        articleService.update(article);
        url = "redirect:auditlist.html?status=1";
      }
    }
    return url;
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
  public String serch(@CurrentUser User user, String articleTypeId, Integer range, Integer status,
      Integer page, Model model, HttpServletRequest request) {
    Article article = new Article();
    ArticleType articleType = new ArticleType();

    String name = request.getParameter("name");

    HashMap<String, Object> map = new HashMap<String, Object>();
    if (name != null && !name.equals("")) {
      map.put("name", "%" + name + "%");
    }
    if (range != null && !range.equals("")) {
      map.put("range", range);
    }
    if (status != null && !status.equals("")) {
      map.put("status", status);
    }
    if (articleTypeId != null && !articleTypeId.equals("")) {
      articleType.setId(articleTypeId);
      article.setArticleType(articleType);
      map.put("articleType", article.getArticleType());
    }
    if (page == null) {
      page = 1;
    }
    map.put("page", page);
    map.put("userId", user.getId());
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(page, Integer.parseInt(config.getString("pageSizeArticle")));

    List<Article> list = articleService.selectByJurisDiction(map);
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
  public String apply(HttpServletRequest request, String ids) {
    String[] id = ids.split(",");
    Article article = new Article();
    article.setStatus(2);
    for (String str : id) {
      article.setId(str);
      articleService.updateStatus(article);
      Article findOneArticle = articleService.selectArticleById(str);
      // solrNewsService.addIndex(findOneArticle);
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
  public String withdraw(@CurrentUser User user,HttpServletRequest request, String ids) {
    if(null != user && "4".equals(user.getTypeName())){
	    //判断是否 是资源服务中心 
	    String[] id = ids.split(",");
	    Article article = new Article();
	    article.setStatus(4);
	    article.setShowCount(0);
	    for (String str : id) {
	      article.setId(str);
	      article.setUpdatedAt(new Date());
	      article.setCancelPublishAt(new Date());
	      articleService.updateStatus(article);
	      // solrNewsService.deleteIndex(str);
	    }
	    return "redirect:auditlist.html?status=1";
    }
    return "";
  }

  @RequestMapping("/updateApply")
  public String updateApply(String[] ranges, HttpServletRequest request,
      HttpServletResponse response, Article article, Model model) {
    String name = request.getParameter("name");
    String ids = request.getParameter("ids");
    if (ids != null && ids != "") {
      String[] attaids = ids.split(",");
      for (String id : attaids) {
        articleAttachmentsService.softDeleteAtta(id);
      }
    }
    if (ValidateUtils.isNull(article.getName())) {
      model.addAttribute("ERR_name", "标题名称不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
    }
    if (article.getName().length() > 150) {
      model.addAttribute("ERR_name", "标题名称不得超过150字符");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
    }

    List<Article> check = articleService.checkName(article);
    for (Article ar : check) {
      if (ar.getName().equals(name)) {
        model.addAttribute("ERR_name", "标题名称不能重复");
        model.addAttribute("article.id", article.getId());
        model.addAttribute("article.name", name);
        Article artc = articleService.selectArticleById(article.getId());
        List<ArticleAttachments> articleAttaList = articleAttachmentsService
            .selectAllArticleAttachments(artc.getId());
        artc.setArticleAttachments(articleAttaList);
        model.addAttribute("article", article);
        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", list);
        return "iss/ps/article/audit/edit";
      }
    }

    if (article.getSecondArticleTypeId() != null) {
      if (article.getSecondArticleTypeId().equals("111")) {
        List<UploadFile> gzdt = uploadService.findBybusinessId(article.getId(),
            Constant.FORUM_SYS_KEY);
        if (gzdt.size() < 1) {
          model.addAttribute("ERR_auditPic", "请上传图片!");
          model.addAttribute("article.id", article.getId());
          model.addAttribute("article.name", name);
          Article artc = articleService.selectArticleById(article.getId());
          List<ArticleAttachments> articleAttaList = articleAttachmentsService
              .selectAllArticleAttachments(artc.getId());
          artc.setArticleAttachments(articleAttaList);
          model.addAttribute("article", article);
          List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
          model.addAttribute("list", list);
          return "iss/ps/article/edit";
        }
      }
    }

    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),
        Constant.EXPERT_SYS_KEY);
    if (auditDoc.size() < 1) {
      model.addAttribute("article.id", article.getId());
      model.addAttribute("article.name", name);
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
      return "iss/ps/article/audit/edit";
    }

    if (ranges != null && !ranges.equals("")) {
      if (ranges.length > 1) {
        article.setRange(2);
      } else {
        for (int i = 0; i < ranges.length; i++) {
          article.setRange(Integer.valueOf(ranges[i]));
        }
      }
    } else {
      model.addAttribute("ERR_range", "发布范围不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
    }
    if (ValidateUtils.isNull(article.getContent())) {
      model.addAttribute("ERR_content", "信息正文不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/audit/edit";
    }
    if (article.getStatus() != null && article.getStatus() == 2) {
      // solrNewsService.deleteIndex(article.getId());
    }

    // String isPicShow = request.getParameter("isPicShow");
    // if(isPicShow!=null&&!isPicShow.equals("")){
    // articleService.updateisPicShow(isPicShow);
    // }

    article.setPublishedAt(new Date());
    article.setUpdatedAt(new Date());
    article.setStatus(2);
    articleService.update(article);

    Article findOneArticle = articleService.selectArticleById(article.getId());
    // solrNewsService.addIndex(findOneArticle);

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
  public String editor(Model model, String id, HttpServletRequest request) {
    Article article = articleService.selectArticleById(id);
    List<ArticleAttachments> articleAttaList = articleAttachmentsService
        .selectAllArticleAttachments(article.getId());
    article.setArticleAttachments(articleAttaList);
    model.addAttribute("article", article);
    // 图片
    DictionaryData dd = new DictionaryData();
    dd.setCode("POST_ATTACHMENT");
    List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
    request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
    if (lists.size() > 0) {
      model.addAttribute("attachTypeId", lists.get(0).getId());
    }
    model.addAttribute("articleId", article.getId());
    // 附件上传
    DictionaryData da = new DictionaryData();
    da.setCode("GGWJ");
    List<DictionaryData> dlists = dictionaryDataServiceI.find(da);
    request.getSession().setAttribute("articleSysKey", Constant.TENDER_SYS_KEY);
    if (dlists.size() > 0) {
      model.addAttribute("artiAttachTypeId", dlists.get(0).getId());
    }
    // 审价文件
    DictionaryData sj = new DictionaryData();
    sj.setCode("SECURITY_COMMITTEE");
    List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
    request.getSession().setAttribute("secretSysKey", Constant.EXPERT_SYS_KEY);
    if (secrets.size() > 0) {
      model.addAttribute("secretTypeId", secrets.get(0).getId());
    }
    return "iss/ps/article/editor";
  }

  @RequestMapping("/updateEditor")
  public String updateEditor(String[] ranges, HttpServletRequest request,
      HttpServletResponse response, Article article, Model model) {
    String name = request.getParameter("name");
    String ids = request.getParameter("ids");
    if (ids != null && ids != "") {
      String[] attaids = ids.split(",");
      for (String id : attaids) {
        articleAttachmentsService.softDeleteAtta(id);
      }
    }
    if (ValidateUtils.isNull(article.getName())) {
      model.addAttribute("ERR_name", "标题名称不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }
    if (article.getName().length() > 150) {
      model.addAttribute("ERR_name", "标题名称不得超过150字符");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", artc);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }

    List<UploadFile> auditDoc = uploadService.findBybusinessId(article.getId(),
        Constant.EXPERT_SYS_KEY);
    if (auditDoc.size() < 1) {
      model.addAttribute("article.id", article.getId());
      model.addAttribute("article.name", name);
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      model.addAttribute("ERR_auditDoc", "请上传单位及保密委员会审核表!");
      return "iss/ps/article/editor";
    }

    List<Article> check = articleService.checkName(article);
    for (Article ar : check) {
      if (ar.getName().equals(name)) {
        model.addAttribute("ERR_name", "标题名称不能重复");
        model.addAttribute("article.id", article.getId());
        model.addAttribute("article.name", name);
        Article artc = articleService.selectArticleById(article.getId());
        List<ArticleAttachments> articleAttaList = articleAttachmentsService
            .selectAllArticleAttachments(artc.getId());
        artc.setArticleAttachments(articleAttaList);
        model.addAttribute("article", article);
        List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
        model.addAttribute("list", list);
        return "iss/ps/article/editor";
      }
    }

    if (ranges != null && !ranges.equals("")) {
      if (ranges.length > 1) {
        article.setRange(2);
      } else {
        for (int i = 0; i < ranges.length; i++) {
          article.setRange(Integer.valueOf(ranges[i]));
        }
      }
    } else {
      model.addAttribute("ERR_range", "发布范围不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }
    if (ValidateUtils.isNull(article.getContent())) {
      model.addAttribute("ERR_content", "信息正文不能为空");
      model.addAttribute("article.id", article.getId());
      Article artc = articleService.selectArticleById(article.getId());
      List<ArticleAttachments> articleAttaList = articleAttachmentsService
          .selectAllArticleAttachments(artc.getId());
      artc.setArticleAttachments(articleAttaList);
      model.addAttribute("article", article);
      List<ArticleType> list = articleTypeService.selectAllArticleTypeForSolr();
      model.addAttribute("list", list);
      return "iss/ps/article/editor";
    }
    if (article.getStatus() != null && article.getStatus() == 2) {
      // solrNewsService.deleteIndex(article.getId());
    }

    // String isPicShow = request.getParameter("isPicShow");
    // if(isPicShow!=null&&!isPicShow.equals("")){
    // articleService.updateisPicShow(isPicShow);
    // }

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
  @RequestMapping(value = "/aritcleTypeParentId", produces = "application/json;charest=utf-8")
  public void aritcleTypeParentId(HttpServletResponse response, String parentId,
      HttpServletRequest request, String type) throws Exception {
    User user = (User) request.getSession().getAttribute("loginUser");
    List<ArticleType> list = new ArrayList<ArticleType>();
    if (parentId != null) {
      ArticleType articleType = articleTypeService.selectTypeByPrimaryKey(parentId);
      if (!"1".equals(type) && articleType != null) {
        //如果栏目是采购公告、中标公告、单一来源公告
        if ("purchase_notice".equals(articleType.getCode())) {
          if (user.getPublishType() != null && user.getPublishType() == 0) {
            ArticleType type1 = articleTypeService.selectArticleTypeByCode("purchase_notice_centrlized");
            list.add(type1);
          } else if (user.getPublishType() != null && user.getPublishType() == 1) {
            ArticleType type2 = articleTypeService.selectArticleTypeByCode("purchase_notice_military");
            list.add(type2);
          } else {
            list = articleTypeService.selectByParentId(parentId);
          }
        } else if ("success_notice".equals(articleType.getCode())) {
          if (user.getPublishType() != null && user.getPublishType() == 0) {
            ArticleType type1 = articleTypeService.selectArticleTypeByCode("success_notice_centralized");
            list.add(type1);
          } else if (user.getPublishType() != null && user.getPublishType() == 1) {
            ArticleType type2 = articleTypeService.selectArticleTypeByCode("success_notice_military");
            list.add(type2);
          } else {
            list = articleTypeService.selectByParentId(parentId);
          }
        } else if ("single_source_notice".equals(articleType.getCode())) {
          if (user.getPublishType() != null && user.getPublishType() == 0) {
            ArticleType type1 = articleTypeService.selectArticleTypeByCode("single_source_notice_centralized");
            list.add(type1);
          } else if (user.getPublishType() != null && user.getPublishType() == 1) {
            ArticleType type2 = articleTypeService.selectArticleTypeByCode("single_source_notice_military");
            list.add(type2);
          } else {
            list = articleTypeService.selectByParentId(parentId);
          }
        } else {
          list = articleTypeService.selectByParentId(parentId);
        }
      } else {
        list = articleTypeService.selectByParentId(parentId);
      }
      super.writeJson(response, list);
    }
  }
  
  /**
   *〈简述〉产品目录
   *〈详细描述〉
   * @author Ye MaoLin
   * @param articleId
   * @param parentId
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "/categoryTree", produces = "application/json;charset=UTF-8")
  public String categoryTree(String articleId, String id, String backCategoryIds, String rootCode){
      List < CategoryTree > allCategories = new ArrayList < CategoryTree > ();
      /*//回显数据库保存
      if (id == null) {
          if (backCategoryIds != null && !"".equals(backCategoryIds)) {
            //后台校验返回时回显选中
            articleTypeService.backTree(id, backCategoryIds, allCategories, "root");
          } else {
              DictionaryData data=new DictionaryData();
              data.setKind(6);
              List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
              for (DictionaryData dictionaryData : listByPage) {
                CategoryTree ct=new CategoryTree();
                ct.setId(dictionaryData.getId());
                ct.setName(dictionaryData.getName());
                ct.setIsParent("true");
                ct.setClassify(dictionaryData.getCode());
                // 设置是否被选中
                ct.setChecked(articleService.isCheckCategory(articleId, dictionaryData.getId()));
                allCategories.add(ct);
              }
          }
      } else {
          if (backCategoryIds != null && !"".equals(backCategoryIds)) {
              //后台校验返回时回显选中
              articleTypeService.backTree(id, backCategoryIds, allCategories, null);
          } else {
              List < Category > tempNodes = articleService.getCategoryIsPublish(id);
              for (Category category : tempNodes) {
                CategoryTree ct = new CategoryTree();
                ct.setName(category.getName());
                ct.setId(category.getId());
                ct.setParentId(category.getParentId());
                // 判断是否为父级节点
                List < Category > nodesList = articleService.getCategoryIsPublish(category.getId());
                if(nodesList != null && nodesList.size() > 0) {
                  ct.setIsParent("true");
                }
                // 判断是否被选中
                ct.setChecked(articleService.isCheckCategory(articleId, category.getId()));
                allCategories.add(ct);
              }
          }
      }*/
      if (rootCode != null && !"".equals(rootCode)) {
          if (id == null) {
            DictionaryData dictionaryData = DictionaryDataUtil.get(rootCode);
            CategoryTree ct=new CategoryTree();
            ct.setId(dictionaryData.getId());
            ct.setName(dictionaryData.getName());
            ct.setIsParent("true");
            ct.setClassify(dictionaryData.getCode());
            // 设置是否被选中
            ct.setChecked(articleService.isCheckCategory(articleId, dictionaryData.getId()));
            allCategories.add(ct);
          } else {
            List < Category > tempNodes = articleService.getCategoryIsPublish(id);
            for (Category category : tempNodes) {
              CategoryTree ct = new CategoryTree();
              ct.setName(category.getName());
              ct.setId(category.getId());
              ct.setParentId(category.getParentId());
              // 判断是否为父级节点
              List < Category > nodesList = articleService.getCategoryIsPublish(category.getId());
              if(nodesList != null && nodesList.size() > 0) {
                ct.setIsParent("true");
              }
              // 判断是否被选中
              ct.setChecked(articleService.isCheckCategory(articleId, category.getId()));
              allCategories.add(ct);
            }
          }
      }
      
      return JSON.toJSONString(allCategories);
  }

    @ResponseBody
    @RequestMapping(value = "/saveArtCategory")
    public String saveArtCategory(String categoryIds, String categoryNames, String articleId, String categoryId, String type) throws UnsupportedEncodingException{
        JSONObject jsonObj = new JSONObject();
        if (categoryNames != null && !"".equals(categoryNames)) {
          categoryNames = URLDecoder.decode(categoryNames,"UTF-8");
        }
        if ("1".equals(type)) {
          //如果是选中
          //获取选中节点以及所有父节点
          List < Category > list = articleService.getAllParent(categoryId);
          jsonObj = articleService.saveArtCategory2(categoryIds, categoryNames, articleId, list);
        } else if("0".equals(type)) {
          //如果是取消选中
          jsonObj = articleService.cancelArtCategory(categoryIds, categoryNames, articleId, categoryId);
          
        }
        return jsonObj.toString();
    }
  
    @ResponseBody
    @RequestMapping(value = "/searchCategory")
    public String searchCategory(String name, String rootCode) throws UnsupportedEncodingException{
        List<CategoryTree> jList = new ArrayList<CategoryTree>();
        name = URLDecoder.decode(name,"UTF-8");
        articleService.searchCategory(jList, name, rootCode);
        
        return JSON.toJSONString(jList);
    }
    
    
    /**
     * 
     * Description: 统计采购公告只读列表
     * 
     * @author Easong
     * @version 2017年6月12日
     * @param model
     * @param articleTypeId
     * @param secondArticleTypeId
     * @param status
     * @param range
     * @param page
     * @param publishStartDate
     * @param publishEndDate
     * @param request
     * @return
     */
    @RequestMapping("/readOnlyList")
    public String readOnlyList(Model model, String articleTypeId, String secondArticleTypeId, Integer status, Integer range, Integer page, Date publishStartDate, Date publishEndDate, ArticleAnalyzeVo articleAnalyzeVo, HttpServletRequest request
    		,String reqType) {
      Article article = new Article();
      ArticleType articleType = new ArticleType();
      String name = request.getParameter("name");
      HashMap<String, Object> map = new HashMap<String, Object>();
      // 查询已发布的状态公告
      map.put("status",status);

      if (name != null && !name.equals("")) {
        map.put("name", "%" + name + "%");
      }
      if (range != null && !range.equals("")) {
        map.put("range", range);
      }

      if (articleTypeId != null && !"".equals(articleTypeId)) {
        articleType.setId(articleTypeId);
        article.setArticleType(articleType);
        map.put("articleType", article.getArticleType());
      }
      
      if (secondArticleTypeId != null && !"null".equals(secondArticleTypeId) && !"".equals(secondArticleTypeId)) {
        map.put("secondArticleTypeId", secondArticleTypeId);
      }
      
      if (publishStartDate != null) {
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(publishStartDate);
        map.put("publishStartDate", startDate);
      }
      if (StringUtils.isNotEmpty(articleAnalyzeVo.getPublishYear())) {
    	  map.put("publishYear", articleAnalyzeVo.getPublishYear());
      }
      if (StringUtils.isNotEmpty(articleAnalyzeVo.getCategoryId())) {
    	  map.put("categoryId", articleAnalyzeVo.getCategoryId());
      }
      if (StringUtils.isNotEmpty(articleAnalyzeVo.getThreeArticleTypeId())) {
    	  map.put("ThreeArticleTypeId", articleAnalyzeVo.getThreeArticleTypeId());
      }
      if (StringUtils.isNotEmpty(articleAnalyzeVo.getFourArticleTypeId())) {
    	  map.put("fourArticleTypeId", articleAnalyzeVo.getFourArticleTypeId());
      }
      if (publishEndDate != null) {
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(publishEndDate);
        map.put("publishEndDate", endDate);
      }
      
      if (page == null) {
        page = 1;
      }
      map.put("page", page.toString());
      PropertiesUtil config = new PropertiesUtil("config.properties");
      PageHelper.startPage(page, Integer.parseInt(config.getString("pageSizeArticle")));
      if (status != null && !status.equals("")) {
        map.put("status", status);
      } 
      List<Article> list = articleService.selectArticleByStatus(map);

      model.addAttribute("articleId", article.getId());
      DictionaryData sj = new DictionaryData();
      sj.setCode("SECURITY_COMMITTEE");
      List<DictionaryData> secrets = dictionaryDataServiceI.find(sj);
      request.getSession().setAttribute("sysKey", Constant.TENDER_SYS_KEY);
      if (secrets.size() > 0) {
        model.addAttribute("secretTypeId", secrets.get(0).getId());
      }
      Integer num = 0;
      StringBuilder groupUpload = new StringBuilder("");
      StringBuilder groupShow = new StringBuilder("");
      for (Article a : list) {
        num++;
        groupUpload = groupUpload.append("artice_secret_show" + num + ",");
        groupShow = groupShow.append("artice_secret_show" + num + ",");
        a.setGroupsUpload("artice_secret_show" + num);
        a.setGroupShow("artice_secret_show" + num);
      }
      String groupUploadId = "";
      String groupShowId = "";
      if (!"".equals(groupUpload.toString())) {
        groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length() - 1);
      }
      if (!"".equals(groupShow.toString())) {
        groupShowId = groupShow.toString().substring(0, groupShow.toString().length() - 1);
      }
      for (Article act : list) {
        act.setGroupsUploadId(groupUploadId);
        act.setGroupShowId(groupShowId);
      }
      model.addAttribute("list", new PageInfo<Article>(list));
      model.addAttribute("articleName", name);
      model.addAttribute("articlesRange", range);
      model.addAttribute("articlesStatus", status);
      model.addAttribute("articlesArticleTypeId", articleTypeId);
      model.addAttribute("curpage", page);
      model.addAttribute("article", article);
      model.addAttribute("publishStartDate", publishStartDate);
      model.addAttribute("publishEndDate", publishEndDate);
      model.addAttribute("secondArticleTypeId", secondArticleTypeId);
      model.addAttribute("articleAnalyzeVo", articleAnalyzeVo);
      return "dss/rids/list/purchaseNoticeList";

    }
}
