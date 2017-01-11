package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.Templet;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.util.AjaxJsonData;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TempletService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.model.ppms.Negotiation;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.NegotiationReportService;
import bss.service.ppms.NegotiationService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageExpertService;
import bss.service.prms.PackageFirstAuditService;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;


/**
 * 版权：(C) 版权所有 
 * 公开招标实施
 * 公开招标实施中的请求控制
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/open_bidding")
public class OpenBiddingController {
    
    private final static Short NUMBER_TWO = 2;
    
    /** ONE */
    private static final String ONE = "1";
    /** TWO */
    private static final String TWO = "2";
    
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private ProjectService projectService;
    
    /**
     * @Fields articelService :  引用文章业务实现接口
     */
    @Autowired
    private ArticleService articelService;
    
    /**
     * @Fields articelTypeService : 引用文章类型业务实现接口
     */
    @Autowired
    private ArticleTypeService articelTypeService;
    
    @Autowired
    private SupplierExtUserServicel extUserServicel; //模板引入
    
    /**
     * @Fields detailService : 引用项目详细业务接口
     */
    @Autowired
    private ProjectDetailService detailService;
    
    /**
     * @Fields packageService : 引用分包业务逻辑接口
     */
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    /**
     * @Fields auditService : 引用初审项业务接口
     */
    @Autowired
    private FirstAuditService auditService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private DownloadService downloadService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired FlowMangeService flowMangeService;
    
    @Autowired
    private TempletService templetService;
    
    @Autowired
    private PackageExpertService packageExpertService;
    
    /** 包  **/
    @Autowired
    private PackageService packagesService; 
    
    @Autowired
    private NegotiationService negotiationService;
    
    @Autowired
    private NegotiationReportService reportService;
    
    /**
     * 符合性审查服务接口
     */
    @Autowired
    private FirstAuditService firstAuditService;
    
    /**
     * 字典表服务层
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    /**
     * 评分、模型服务层
     */
    @Autowired
    private ScoreModelService scoreModelService;
    
    /**
     * 成交供应商服务接口
     */
    @Autowired
    private SupplierCheckPassService supplierCheckPassService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    /**
     * @Fields jsonData : ajax返回数据封装类
     */
    private AjaxJsonData jsonData = new AjaxJsonData();
    
    /** 采购类型 - 公开招标*/
    private static final String OPEN_BID = "open";
    /** 采购类型 - 邀请招标 */
    private static final String INVITE_BID = "invite";
    /** 采购类型 - 询价采购*/
    private static final String ENQUIRY_BID = "enquiry";
    /** 采购类型 - 单一来源 */
    private static final String SINGLE_SOURCE_BID = "single_source";
    /** 采购类型 - 竞争性谈判*/
    private static final String  COMPETITIVE_NEGOTIATION= "competitive_negotiation";
    /** 公告类型 - 采购公告*/
    private static final String  PURCHASE_NOTICE= "purchase";
    /** 公告类型 - 中标公告*/
    private static final String  WIN_NOTICE= "win";
    
    /**
     *〈简述〉 进入招标文件页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param id 项目id
     * @param model
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("/bidFile")
    public String bidFile(HttpServletRequest request, String id, Model model, HttpServletResponse response, String flowDefineId) throws Exception{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", id);
        List<Packages> packages = packageService.findPackageById(map);
        String msg = "";
        for (Packages p : packages) {
          //判断各包符合性审查项是否编辑完成
          FirstAudit firstAudit = new FirstAudit();
          firstAudit.setPackageId(p.getId());
          List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
          if (fas == null || fas.size() <= 0) {
            msg = "noFirst";
            return "redirect:/firstAudit/toAdd.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
          }
          //获取资格性审查项内容
          ScoreModel smMap = new ScoreModel();
          smMap.setPackageId(p.getId());
          List<ScoreModel> sms = scoreModelService.findListByScoreModel(smMap);
          if (sms == null || sms.size() <= 0) {
            msg = "noSecond";
            return "redirect:/intelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
          }
        }
        Project project = projectService.selectById(id);
        //判断是否上传招标文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
          model.addAttribute("fileId", files.get(0).getId());
        } else {
          if (project != null){
            String filePath = extUserServicel.downLoadBiddingDoc(request,id);
            if (StringUtils.isNotBlank(filePath)){
              model.addAttribute("filePath", filePath);
            }
          }
          model.addAttribute("fileId", "0");
        }
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("project", project);
        model.addAttribute("ope", "add");
        model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
        model.addAttribute("typeId", DictionaryDataUtil.getId("BID_FILE_AUDIT"));
        return "bss/ppms/open_bidding/bid_file/add_file";
    }
    
    @RequestMapping("/bidFileView")
    public String bidFileView(HttpServletRequest request, String id, Model model, HttpServletResponse response, String flowDefineId){
        Project project = projectService.selectById(id);
        //判断是否上传招标文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            model.addAttribute("fileId", files.get(0).getId());
        } else {
            model.addAttribute("fileId", "0");
        }
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("project", project);
        model.addAttribute("ope", "view");
        return "bss/ppms/open_bidding/bid_file/add_file";
    }
    
    /**
     *〈简述〉下载附件
     *〈详细描述〉
     * @author Ye MaoLin
     * @param request
     * @param fileId 附件id
     * @param response
     */
    @RequestMapping("/loadFile")
    public void loadFile(HttpServletRequest request, String fileId, HttpServletResponse response){
        downloadService.downloadOther(request, response, fileId, Constant.TENDER_SYS_KEY+"");
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param request
     * @param fileId
     * @param response
     */
    @RequestMapping("/downloadFile")
    public void downLoadFile(HttpServletRequest request, String filePath, HttpServletResponse response){
        downloadService.downLoadFile(request, response, filePath);
    }
    
    /**
     *〈简述〉跳转到招标公告(采购公告)页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/bidNotice")
    public String bidNotice(@CurrentUser User user, String projectId, Model model, String flowDefineId){
        Project project = projectService.selectById(projectId);
        String id = DictionaryDataUtil.getId("DYLY");
        if(project.getPurchaseType().equals(id)){
            return makeNotices(user, projectId, PURCHASE_NOTICE, model, flowDefineId);
        }else{
            return makeNotice(projectId, PURCHASE_NOTICE, model, flowDefineId);
        }
         
    }
    
    
    public String makeNotices(@CurrentUser User user, String projectId, String noticeType, Model model, String flowDefineId){
        Project project = projectService.selectById(projectId);
        Article article = new Article();
        //单一来源公告
        if (PURCHASE_NOTICE.equals(noticeType)) {
            article.setArticleType(articelTypeService.selectArticleTypeByCode("single_source_notice"));
            if("0".equals(user.getPublishType()) || user.getPublishType() == null){
              //集中采购
                ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized");
                if (articleType2 != null) {
                    article.setSecondArticleTypeId(articleType2.getId());
                }
                //货物/物资
                if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_quotas");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                    
                } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                    //工程  
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_plumbing");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                    //服务 
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_service");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                }
            }
            if("1".equals(user.getPublishType())){
                //部队采购
                ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("single_source_notice_military");
                if (articleType2 != null) {
                    article.setSecondArticleTypeId(articleType2.getId());
                }
                //货物/物资
                if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("single_source_notice_military_quotas");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                    //工程  
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode(" single_source_notice_military_plumbing");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                    //服务 
                    ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("single_source_notice_military_service");
                    if (articleType3 != null) {
                      article.setThreeArticleTypeId(articleType3.getId());
                      article.setLastArticleType(articleType3);
                    }
                    Article article1 = negotiationService.getDefaultTemplates(projectId, article);
                    model.addAttribute("article1", article1);
                }
            }
            
        }
       /* //如果是拟制中标公告
        if (WIN_NOTICE.equals(noticeType)) {
            //中标公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
            //集中采购
            ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("success_notice_centralized");
            if (articleType2 != null) {
                article.setSecondArticleTypeId(articleType2.getId());
            }
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_matarials");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_engineering");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_service");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            }
        }*/
        article.setProjectId(projectId);
        //查询公告列表中是否有该项目的招标公告
        List<Article> articles = articelService.selectArticleByProjectId(article);
        //判断该项目是否已经存在该类型公告
        if (articles != null && articles.size() > 0) {
            //判断该项目的公告是否提交
            if (articles.get(0).getStatus() == 1 || articles.get(0).getStatus() == 2){
                //已提交或发布公告
                model.addAttribute("article", articles.get(0));
                model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
                model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
                model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
                if (WIN_NOTICE.equals(noticeType)) {
                    model.addAttribute("typeId_examine", DictionaryDataUtil.getId("WIN_BID_ADUIT"));
                }
                if (PURCHASE_NOTICE.equals(noticeType)) {
                    model.addAttribute("typeId_examine", DictionaryDataUtil.getId("PROJECT_BID_ADUIT"));
                }
                
                return "bss/ppms/open_bidding/bid_notice/view";
            } else {
                //暂存或退回状态
                model.addAttribute("project", project);
                model.addAttribute("article", articles.get(0));
                model.addAttribute("articleId", articles.get(0).getId());
                model.addAttribute("projectId", projectId);
                model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
                model.addAttribute("noticeType", noticeType);
                model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
                model.addAttribute("flowDefineId", flowDefineId);
                model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
                return "bss/ppms/open_bidding/bid_notice/add";
            }
        } else {
            model.addAttribute("article", article);
            model.addAttribute("project", project);
            String articleId = WfUtil.createUUID();
            model.addAttribute("articleId",articleId);
            model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            model.addAttribute("projectId", projectId);
            model.addAttribute("noticeType", noticeType);
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
            if (WIN_NOTICE.equals(noticeType)) {
                model.addAttribute("typeId_examine", DictionaryDataUtil.getId("WIN_BID_ADUIT"));
            }
            if (PURCHASE_NOTICE.equals(noticeType)) {
                model.addAttribute("typeId_examine", DictionaryDataUtil.getId("PROJECT_BID_ADUIT"));
            }
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("articleId", articleId);
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            
            return "bss/ppms/open_bidding/bid_notice/add";
        }
    }
    
    /**
     *〈简述〉拟制中标公告
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId项目id
     * @param model
     * @return 
     */
    @RequestMapping("/winNotice")
    public String winNotice(String projectId, Model model, String flowDefineId){
        return makeNotice(projectId, WIN_NOTICE, model, flowDefineId);
    }
    
    @RequestMapping("/showTime")
    @ResponseBody
    public long showTime(String projectId){
        Project project=projectService.selectById(projectId);
        //开标时间
        long bidDate=0;
        if(project.getBidDate()==null){
        }else{
            bidDate=project.getBidDate().getTime();
        }
        long nowDate=new Date().getTime();
        long date=bidDate-nowDate;
        return date;
    }
    
    /**
     * Description: 保存招标公告
     * 
     * @author Ye MaoLin
     * @version 2016-10-18
     * @param request
     * @param article
     * @return AjaxJsonData
     * @throws Exception
     * @exception IOException
     */
    @RequestMapping("saveBidNotice")
    @ResponseBody 
    public AjaxJsonData saveBidNotice(HttpServletRequest request, Article article, String articleTypeId, String lastArticleTypeId, String flowDefineId, Integer flag) throws Exception{
        try {
            String[] ranges = request.getParameterValues("ranges");
            int count = 0;
            String msg = "请填写";
            if (article.getName() == null || "".equals(article.getName())) {
                msg += "公告名称";
                count ++;
            }
            if (ranges == null || ranges.length == 0) {
                if (count > 0) {
                    msg += "和发布范围";
                } else {
                    msg += "发布范围";
                }
                count ++;
            }
            if ("".equals(article.getContent()) || article.getContent() == null) {
                if (count > 0) {
                    msg += "和公告内容";
                } else {
                    msg += "公告内容";
                }
                count ++;
            }
            if (count > 0) {
                jsonData.setSuccess(false);
                jsonData.setMessage(msg);
                return jsonData;
            }
            if (count == 0) {
                User currUser = (User) request.getSession().getAttribute("loginUser");
                article.setUser(currUser);
                Timestamp ts = new Timestamp(new Date().getTime());
                article.setCreatedAt(ts);
                Timestamp ts1 = new Timestamp(new Date().getTime());
                article.setUpdatedAt(ts1);
                ArticleType at = articelTypeService.selectTypeByPrimaryKey(articleTypeId);
                article.setArticleType(at);
                article.setLastArticleType(articelTypeService.selectTypeByPrimaryKey(lastArticleTypeId));
                if (flag == 0) {
                  //暂存
                  article.setStatus(0);
                  article.setUpdatedAt(new Date());
                  jsonData.setMessage("暂存成功");
                }
                if (flag == 1) {
                  //提交
                  article.setStatus(1);
                  article.setUpdatedAt(new Date());
                  article.setSubmitAt(new Date());
                  jsonData.setMessage("提交成功");
                }
                if (ranges != null && ranges.length > 0){
                    if (ranges.length > 1){
                        article.setRange(2);
                    } else {
                        for(int i=0;i<ranges.length;i++){
                            article.setRange(Integer.valueOf(ranges[i]));
                        }
                    }
                }
                //查询公告列表中是否有该项目的招标公告
                Article art = articelService.selectArticleById(article.getId());
                if (art != null ){
                    articelService.update(article);
                    //该环节设置为执行中状态
                    flowMangeService.flowExe(request, flowDefineId, article.getProjectId(), 2);
                } else {
                    articelService.addArticle(article);
                    //该环节设置为执行中状态
                    flowMangeService.flowExe(request, flowDefineId, article.getProjectId(), 2);
                }
                jsonData.setSuccess(true);
                
                jsonData.setObj(article);
                return jsonData;
            }
            jsonData.setSuccess(false);
            jsonData.setMessage("保存失败");
            return jsonData;
        } catch (Exception e) {
            throw new Exception("保存失败！");
        }
    }
    
    /**
     * Description: 打印预览
     * 
     * @author Ye MaoLin
     * @version 2016-10-17
     * @param request
     * @param model
     * @return String
     * @exception IOException
     */
    @RequestMapping("/printView")
    public String printView(String content, String name, String projectId, HttpServletRequest request, Model model){
        model.addAttribute("content", content);
        model.addAttribute("name", name);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_notice/print_view";
    }
    
    
    /**
     *〈简述〉
     *〈详细描述〉
     * @author yggc
     * @param request
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("/export")
    public void export(HttpServletRequest request, HttpServletResponse resp) throws IOException{
        String articleName = request.getParameter("name");;
        String name = request.getParameter("name");
        if(name != null && !"".equals(name)){
            articleName = name;
        }
        String content = request.getParameter("content");
        resp.reset();  
        resp.setContentType("application/vnd.ms-word;charset=UTF-8"); 
        String guessCharset = "gb2312";  
        String strFileName = new String(articleName.getBytes(guessCharset), "ISO8859-1"); 
        resp.setHeader("Content-Disposition", "attachment;filename=" + strFileName + ".doc"); 
        OutputStream os = resp.getOutputStream();  
        os.write(content.getBytes(), 0, content.getBytes().length);  
        os.flush();  
        os.close(); 
    }
    
    /**
     *〈简述〉弹出发布
     *〈详细描述〉上传招标公告批文
     * @author Ye MaoLin
     * @param model
     * @param id 公告id
     * @return
     */
    @RequestMapping("/publishEdit")
    public String publishEdit(Model model, String id, String noticeType, String flowDefineId){
        if (WIN_NOTICE.equals(noticeType)) {
            model.addAttribute("typeId", DictionaryDataUtil.getId("WIN_BID_ADUIT"));
        }
        if (PURCHASE_NOTICE.equals(noticeType)) {
            model.addAttribute("typeId", DictionaryDataUtil.getId("PROJECT_BID_ADUIT"));
        }
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("articleId", id);
        model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
        
        return "bss/ppms/open_bidding/bid_notice/publish_edit";
    }
    
    /**
     *〈简述〉发布公告
     *〈详细描述〉
     * @author Ye MaoLin
     * @param files
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/publish")
    @ResponseBody
    public void publish(HttpServletResponse response, HttpServletRequest request, Article art, String flowDefineId) throws Exception{
        try {
            Article article = articelService.selectArticleById(art.getId());
            Timestamp ts = new Timestamp(new Date().getTime());
            article.setPublishedAt(ts);
            User user = (User) request.getSession().getAttribute("loginUser");
            article.setPublishedName(user.getRelName());
            article.setStatus(1);
            articelService.update(article);
            //该环节设置为已执行状态
            flowMangeService.flowExe(request, flowDefineId, article.getProjectId(), 1);
            String msg = "提交成功";
            String projectId = article.getProjectId();
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ", \"projectId\": \"" + projectId + "\", \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    /**
     *〈简述〉保存招标文件到服务器
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/saveBidFile")
    public void saveBidFile(HttpServletRequest req, String projectId, Model model, String flowDefineId, String flag) throws IOException{
        String result = "保存失败";
        //判断该项目是否上传过招标文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            //删除 ,表中数据假删除
            uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
            //flag：1，招标文件为提交状态
            if ("1".equals(flag)) {
              //
              Project project = projectService.selectById(projectId);
              project.setConfirmFile(1);
              projectService.update(project);
              //该环节设置为执行完状态
              flowMangeService.flowExe(req, flowDefineId, projectId, 1);
            }
            //flag：0，招标文件为暂存状态
            if ("0".equals(flag)) {
              //该环节设置为执行中状态
              flowMangeService.flowExe(req, flowDefineId, projectId, 2);
            }
        } else {
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
            //flag：1，招标文件为提交状态
            if ("1".equals(flag)) {
              Project project = projectService.selectById(projectId);
              project.setConfirmFile(1);
              projectService.update(project);
              //该环节设置为执行完状态
              flowMangeService.flowExe(req, flowDefineId, projectId, 1);
            }
            //flag：0，招标文件为暂存状态
            if ("0".equals(flag)) {
              //该环节设置为执行中状态
              flowMangeService.flowExe(req, flowDefineId, projectId, 2);
            }
        }
        System.out.println(result);
    }
    
    /**
     *〈简述〉判断是否上传招标文件审批文件
     *〈详细描述〉
     * @author Ye MaoLin
     * @param response
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/isLoadAuditFile")
    public void isLoadAuditFile(String projectId, HttpServletResponse response) throws IOException{
      try {
         String msg = "";
        //判断该项目是否上传过招标文件
        String typeId = DictionaryDataUtil.getId("BID_FILE_AUDIT");
        List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
        if (files == null || files.size() == 0) {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + false + ", \"msg\": \"" + msg
              + "\"}");
        } else {
          response.setContentType("text/html;charset=utf-8");
          response.getWriter()
          .print("{\"success\": " + true + ", \"msg\": \"" + msg
              + "\"}");
        }
        response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
    }
    
    /**
     *〈简述〉进入确认中标公告页面
     *〈详细描述〉
     * @author yggc
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/firstAduitView")
    public String firstAduitView(String projectId, Model model, String flowDefineId){
        try {
            //初审项信息
            List<FirstAudit> list = auditService.getListByProjectId(projectId);
            model.addAttribute("list", list);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flowDefineId", flowDefineId);
            Project project=projectService.selectById(projectId);
            model.addAttribute("project", project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/open_bidding/bid_file/bid_file_view";
    }
    
    /**
     *〈简述〉初审项关联查询
     *〈详细描述〉
     * @author yggc
     * @param projectId 项目id
     * @param flag
     * @param model
     * @return
     */
    @RequestMapping("/packageFirstAuditView")
    public String packageFirstAuditView(String projectId, Model model, String flowDefineId){
        try {
            //项目分包信息
            HashMap<String,Object> pack = new HashMap<String,Object>();
            pack.put("projectId", projectId);
            List<Packages> packages = packageService.findPackageById(pack);
            Map<String,Object> list = new HashMap<String,Object>();
            //关联表集合
            List<PackageFirstAudit> idList = new ArrayList<>();
            Map<String,Object> mapSearch = new HashMap<String,Object>(); 
            for(Packages ps:packages){
                list.put("pack"+ps.getId(),ps);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(map);
                ps.setProjectDetails(detailList);
                //设置查询条件
                mapSearch.put("projectId", projectId);
                mapSearch.put("packageId", ps.getId());
                List<PackageFirstAudit> selectList = packageFirstAuditService.selectList(mapSearch);
                idList.addAll(selectList);
            }
            model.addAttribute("idList",idList);
            model.addAttribute("packageList", packages);
            //初审项信息
            List<FirstAudit> list2 = auditService.getListByProjectId(projectId);
            model.addAttribute("list", list2);
            model.addAttribute("projectId", projectId);
            model.addAttribute("flowDefineId", flowDefineId);
            Project project=projectService.selectById(projectId);
            model.addAttribute("project", project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/open_bidding/bid_file/package_first_audit_view";
    }
    
    /**
     *〈简述〉将初审项、初审项关联、详细评审、招标文件改为确认状态
     *〈详细描述〉
     * @author Ye MaoLin
     * @param response
     * @param projectId 项目id
     * @throws Exception
     */
    @RequestMapping("/confirmOk")
    @ResponseBody 
    public void confirmOk(HttpServletRequest request, HttpServletResponse response, String projectId, String flowDefineId) throws Exception{
        try {
            Project project = projectService.selectById(projectId);
            project.setConfirmFile(1);
            projectService.update(project);
            //该环节设置为执行完状态
            flowMangeService.flowExe(request, flowDefineId, projectId, 1);
            String msg = "确认成功";
            response.setContentType("text/html;charset=utf-8");
            response.getWriter()
                    .print("{\"success\": " + true + ", \"msg\": \"" + msg
                            + "\"}");
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
    }
    
    @RequestMapping("/changtotal")
    public String changbiao(String projectId, Model model ,HttpServletRequest req) throws ParseException{
        Project project = projectService.selectById(projectId);
        DictionaryData dictionaryData = null;
        if (project != null && project.getPurchaseType() != null ){
            dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        //去saletender查出项目对应的所有的包
        List<String> packageIds = saleTenderService.getPackageIds(projectId);
        //这里用这个是因为hashMap是无序的
        TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
        //文件上传类型
        DictionaryData dd = new DictionaryData();
        dd.setCode("OPEN_FILE");
        List<DictionaryData > list = dictionaryDataServiceI.find(dd);
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        if (list.size() > 0){
            model.addAttribute("typeId", list.get(0).getId());
        }
        SaleTender condition = new SaleTender();
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        StringBuilder groupUpload = new StringBuilder("");
        StringBuilder groupShow = new StringBuilder("");
        Quote quote2 = new Quote();
        Quote quote3 = new Quote();
        boolean flag = false;
        if (packageIds != null) {
            Integer num = 0;
            for (String packageId : packageIds) {
                condition.setProjectId(projectId);
                condition.setPackages(packageId);
                condition.setStatusBid(NUMBER_TWO);
                condition.setStatusBond(NUMBER_TWO);
                List<SaleTender> stList = saleTenderService.find(condition);
                map1.put("packageId", packageId);
                map1.put("projectId", projectId);
                List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
                BigDecimal projectBudget = BigDecimal.ZERO;
                for (ProjectDetail projectDetail : detailList) {
                    projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
                }
                
                quote3.setProjectId(projectId);
                quote3.setPackageId(packageId);
                List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote3);
                List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
                if (listDate1 != null && listDate1.size() > 1) {
                    //给第二次报价的数据查到
                    if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
                        quote2.setProjectId(projectId);
                        quote2.setPackageId(packageId);
                        quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
                        listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
                    }
                } 
                
                //再次点击 查看
                for (SaleTender saleTender : stList) {
                    if (list.size() > 0){
                        List<UploadFile> blist = uploadService.getFilesOther(saleTender.getId(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
                        if (blist != null && blist.size() > 0) {
                            saleTender.setJudgeBidFile(1);
                            saleTender.setBidFileName(blist.get(0).getName());
                            saleTender.setBidFileId(blist.get(0).getId());
                        }
                    }
                   
                    Quote quote = new Quote();
                    quote.setProjectId(projectId);
                    quote.setPackageId(packageId);
                    quote.setSupplierId(saleTender.getSupplierId());
                    if (listDate1 != null && listDate1.size() > 0) {
                        quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
                    }
                    List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                    if (allQuote != null && allQuote.size() > 0) {
                        flag = true;
                    }
                    if (allQuote != null && allQuote.size() > 0) {
                        for (Quote conditionQuote : allQuote) {
                            if (conditionQuote.getSupplier()!=null&&conditionQuote.getSupplier().getId().equals(saleTender.getSuppliers().getId()) &&
                                conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                                  for (Quote qp : listQuotebyPackage1) {
                                      if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                                          conditionQuote.setTotal(qp.getTotal());
                                          conditionQuote.setQuotePrice(qp.getQuotePrice());
                                          conditionQuote.setRemark(qp.getRemark());
                                          conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                                          conditionQuote.setIsTurnUp(qp.getIsTurnUp());
                                      }
                                  }
                                saleTender.setTotal(conditionQuote.getTotal());
                                saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                                saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                                saleTender.setQuoteId(conditionQuote.getId());
                            }
                        }
                    }
                }
                //这里是动态生成页面上传文件的groups
                for (SaleTender saleTender : stList) {
                    num ++;
                    groupUpload = groupUpload.append("bidFileUpload" + num +",");
                    groupShow = groupShow.append("bidFileShow" + num +",");
                    saleTender.setGroupsUpload("bidFileUpload"+num);
                    saleTender.setGroupShow("bidFileShow"+num);
                }
                map.put("id", packageId);
                List<Packages> pack = packageService.findPackageById(map);
                if (pack != null && pack.size() > 0) {
                    treeMap.put(pack.get(0).getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
                } else {
                    treeMap.put("", stList);
                };
            }
        }
        String groupUploadId =  "";
        String groupShowId = "";
        if (!"".equals(groupUpload.toString())) {
             groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length()-1);
        }
        if (!"".equals(groupShow.toString())) {
             groupShowId = groupShow.toString().substring(0, groupShow.toString().length()-1);
        }
        for (List<SaleTender> stList : treeMap.values()) {
            for (SaleTender st : stList) {
                st.setGroupsUploadId(groupUploadId);
                st.setGroupShowId(groupShowId);
            }
        }
        model.addAttribute("flag", flag);
        model.addAttribute("treeMap", treeMap);
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_file/chang_total";
    }
    
    @RequestMapping("/changbiao")
    public String chooseChangBiaoType(String projectId, Model model) {
        //开标时间
        Project project = projectService.selectById(projectId);
        long bidDate = 0;
        if (project.getBidDate() != null) {
            bidDate = project.getBidDate().getTime();
        }
        long nowDate = new Date().getTime();
        long date = bidDate - nowDate;
        model.addAttribute("date", date);
        model.addAttribute("project", project);
        
        if (date < 0) {
            //去saletender查出项目对应的所有的包
            List<String> packageIds = saleTenderService.getPackageIds(projectId);
            if (packageIds != null && packageIds.size() > 0) {
                SaleTender condition = new SaleTender();
                condition.setProjectId(projectId);
                condition.setPackages(packageIds.get(0));
                condition.setStatusBid(NUMBER_TWO);
                condition.setStatusBond(NUMBER_TWO);
                List<SaleTender> stList = saleTenderService.find(condition);
                if (stList != null && stList.size() > 0) {
                    Quote quote = new Quote();
                    quote.setProjectId(projectId);
                    quote.setPackageId(packageIds.get(0));
                    quote.setSupplierId(stList.get(0).getSupplierId());
                    List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                    if (allQuote != null && allQuote.size() > 0) {
                        if (allQuote.get(0).getQuotePrice() == null) {
                            return "redirect:quotetab1.html?projectId=" + projectId;
                        } else {
                            return "redirect:quotetab2.html?projectId=" + projectId;
                        }
                    }
                }
            }
        }
        return "bss/ppms/open_bidding/bid_file/cb";
    }
    
    @RequestMapping("/openNewWidow")
    public String openNewWidow(String projectId, Model model) {
        //开标时间
        Project project = projectService.selectById(projectId);
        long bidDate = 0;
        if (project.getBidDate() != null) {
            bidDate = project.getBidDate().getTime();
        }
        long nowDate = new Date().getTime();
        long date = bidDate - nowDate;
        model.addAttribute("date", date);
        model.addAttribute("project", project);
        
        if (date < 0) {
            //去saletender查出项目对应的所有的包
            List<String> packageIds = saleTenderService.getPackageIds(projectId);
            if (packageIds != null && packageIds.size() > 0) {
                SaleTender condition = new SaleTender();
                condition.setProjectId(projectId);
                condition.setPackages(packageIds.get(0));
                condition.setStatusBid(NUMBER_TWO);
                condition.setStatusBond(NUMBER_TWO);
                List<SaleTender> stList = saleTenderService.find(condition);
                if (stList != null && stList.size() > 0) {
                    Quote quote = new Quote();
                    quote.setProjectId(projectId);
                    quote.setPackageId(packageIds.get(0));
                    quote.setSupplierId(stList.get(0).getSupplierId());
                    List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                    if (allQuote != null && allQuote.size() > 0) {
                        if (allQuote.get(0).getQuotePrice() == null) {
                            return "redirect:quotetab1.html?projectId=" + projectId;
                        } else {
                            return "redirect:quotetab2.html?projectId=" + projectId;
                        }
                    }
                }
            }
        }
        return "bss/ppms/open_bidding/bid_file/new_window";
    }
    
    @RequestMapping("/save")
    @ResponseBody
    public void save(BigDecimal total ,String deliveryTime ,Integer isTurnUp ,String supplierId, String projectId, String packageId ,String quoteId) throws Exception{
        List<Quote> quoteList = new ArrayList<Quote>();
        Quote quote = new Quote();
        quote.setSupplierId(supplierId);
        quote.setTotal(total);
        quote.setCreatedAt(new Timestamp(new Date().getTime()));
        quote.setPackageId(packageId);
        quote.setProjectId(projectId);
        quote.setIsTurnUp(isTurnUp);
        if (deliveryTime != null && !"".equals(deliveryTime)) {
            quote.setDeliveryTime(URLDecoder.decode(deliveryTime, "UTF-8"));
        }
        quoteList.add(quote);
        supplierQuoteService.insert(quoteList);  
        Project project = projectService.selectById(projectId);
        if(project != null){
            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
            if("DYLY".equals(findById.getCode())){
                SupplierCheckPass pass = new SupplierCheckPass();
                pass.setPackageId(packageId);
                List<SupplierCheckPass> listCheckPass = supplierCheckPassService.listCheckPass(pass);
                if(listCheckPass != null && listCheckPass.size() > 0){
                    for (SupplierCheckPass supplierCheckPass : listCheckPass) {
                        if(supplierCheckPass != null){
                            supplierCheckPassService.delete(supplierCheckPass.getId());
                        }
                    }
                    
                }
                
                SupplierCheckPass record = new SupplierCheckPass();
                record.setId(WfUtil.createUUID());
                record.setPackageId(packageId);
                record.setProjectId(projectId);
                record.setSupplierId(supplierId);
                record.setTotalPrice(total);
                record.setRanking(1);
                record.setIsWonBid((short)1);
                SupplierCheckPass checkPass = new SupplierCheckPass();
                checkPass.setPackageId(packageId);
                supplierCheckPassService.insert(record);
                
                
                
            }
        }
       /* if (quoteId == null || "".equals(quoteId)) {
            supplierQuoteService.insert(quoteList);    
        } else {
            quote.setId(quoteId);
            supplierQuoteService.update(quoteList);
        }*/
        
    }
    
    @RequestMapping("/selectSupplierByProject")
    public String selectSupplierByProject(String projectId, Model model) throws ParseException{
        //文件上传类型
        DictionaryData dd = new DictionaryData();
        dd.setCode("OPEN_FILE");
        List<DictionaryData > list = dictionaryDataServiceI.find(dd);
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        if (list.size() > 0){
            model.addAttribute("typeId", list.get(0).getId());
        }
        List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
        Integer num = 0;
        StringBuilder groupUpload = new StringBuilder("");
        StringBuilder groupShow = new StringBuilder("");
        for (Supplier supplier : listSupplier) {
            num ++;
            groupUpload = groupUpload.append("bidFileUpload" + num +",");
            groupShow = groupShow.append("bidFileShow" + num +",");
            supplier.setGroupsUpload("bidFileUpload"+num);
            supplier.setGroupShow("bidFileShow"+num);
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            st.setSupplierId(supplier.getId());
            List<SaleTender> findList = saleTenderService.find(st);
            if(findList != null && findList.size() > 0) {
                supplier.setProSupFile(findList.get(0).getId());
            }
        }
        String groupUploadId =  "";
        String groupShowId = "";
        if (!"".equals(groupUpload.toString())) {
             groupUploadId = groupUpload.toString().substring(0, groupUpload.toString().length()-1);
        }
        if (!"".equals(groupShow.toString())) {
             groupShowId = groupShow.toString().substring(0, groupShow.toString().length()-1);
        }
        for (Supplier supplier : listSupplier) {
            supplier.setGroupsUploadId(groupUploadId);
            supplier.setGroupShowId(groupShowId);
            List<UploadFile> blist = uploadService.getFilesOther(supplier.getProSupFile(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
            if (blist != null && blist.size() > 0) {
                supplier.setBidFileName(blist.get(0).getName());
                supplier.setBidFileId(blist.get(0).getId());
            }
        }
        model.addAttribute("supplierList", listSupplier);
        return "bss/ppms/open_bidding/bid_file/supplier_project";
    }
    
    
    /**
     * @Title: changbiao
     * @author Song Biaowei
     * @date 2016-11-7 下午8:38:08  
     * @Description: 唱标
     * @param @param projectId
     * @param @param supplierStr
     * @param @param model
     * @param @return      
     * @return String
     * @throws ParseException 
     */
    @RequestMapping("/changmingxi")
    public String cb(String projectId, Model model ,HttpServletRequest req) throws ParseException{
        Project project = projectService.selectById(projectId);
        DictionaryData dd = null;
        if (project != null && project.getPurchaseType() != null ){
            dd = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        model.addAttribute("project", project);
        //参与项目的所有供应商
        /*List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
        String supplierStr="";
        for(Supplier sup:listSupplier){
            supplierStr+=sup.getId()+",";
        }*/
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("purchaseType",DictionaryDataUtil.getId("GKZB"));
        //每个供应商的报价明细产品
        //List<List<Quote>> listQuoteList=new ArrayList<List<Quote>>();
        //List<String> listsupplierId=Arrays.asList(supplierStr.split(","));
        //boolean flag = false;
        boolean flag = true;
        boolean flagButton = false;
       /* if(listsupplierId.get(0).length() > 30){
            for(String str:listsupplierId){
                Quote quotes = new Quote();
                quotes.setProjectId(projectId);
                quotes.setSupplierId(str);
                List<Date> listDate = supplierQuoteService.selectQuoteCount(quotes);
                if (listDate.size() == 0 || project.getBidDate().getTime() < new Date().getTime()) {
                    flag = true;
                    break;
                }
                Quote quote=new Quote();
                quote.setSupplierId(str);
                quote.setCreatedAt(new Timestamp(listDate.get(listDate.size() - 1).getTime()));
                List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                BigDecimal totalMoney=new BigDecimal(0);
                for(Quote q: listQuote){
                    totalMoney=totalMoney.add(q.getTotal());
                }
                if(listQuote != null && listQuote.size() > 0){
                    listQuote.get(0).setTotalMoney(totalMoney);
                    listQuote.get(0).setTotalMoneyNames(CnUpperCaser.getCnString(totalMoney.doubleValue()));
                }
                listQuoteList.add(listQuote);
            }
            model.addAttribute("listQuoteList", listQuoteList);
        }*/
         //已开标 就是线下报价  -第一次进来的时候
        Quote quotes = new Quote();
        Quote quote1 = new Quote();
         if(flag==true) {
            HashMap<String, Object> map1 = new HashMap<String, Object>();
            map1.put("projectId", projectId);
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            StringBuilder sb = new StringBuilder("");
            List<SaleTender> saleTenderList = saleTenderService.find(st);
            for (SaleTender saleTender : saleTenderList) {
                sb.append(saleTender.getPackages());
            }
            List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map1, null);
            List<Packages> listPackage = new ArrayList<Packages>();
            for (Packages packages : listPack) {
                if (sb.toString().indexOf(packages.getId()) != -1) {
                    listPackage.add(packages);
                }
            }
            //开始循环包
            List<HashMap<List<Supplier>,List<ProjectDetail>>> listPd = new ArrayList<HashMap<List<Supplier>,List<ProjectDetail>>>();
            for (Packages pk:listPackage) {
                HashMap<List<Supplier>,List<ProjectDetail>> hashMap = new HashMap<List<Supplier>,List<ProjectDetail>>();
                List<Supplier> supplierList = new ArrayList<Supplier>();
                map1.put("packageId", pk.getId());
                //
                quotes.setProjectId(projectId);
                quotes.setPackageId(pk.getId());
                List<Date> listDate =  supplierQuoteService.selectQuoteCount(quotes);
                List<Quote> listQuotebyPackage = new ArrayList<Quote>();
                if (listDate != null && listDate.size() > 1) {
                    if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
                         quote1.setProjectId(projectId);
                         quote1.setPackageId(pk.getId());
                         quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                         listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
                    } 
                }
                List<ProjectDetail> detailList = null;
                if (listDate.size() != 0) {
                    Quote quote=new Quote();
                    quote.setProjectId(projectId);
                    if (listDate != null && listDate.size() > 0) {
                        quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
                    }
                    List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                    detailList = detailService.selectByCondition(map1, null);
                    
                    BigDecimal projectBudget = BigDecimal.ZERO;
                    for (ProjectDetail projectDetail : detailList) {
                        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
                    }
                    pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
                    
                    if (listQuote != null && listQuote.size() > 0 && detailList != null && detailList.size() > 0) {
                        flagButton = true;
                    }
                    List<ProjectDetail> detailList1 = new ArrayList<ProjectDetail>();
                    for (Quote q : listQuote) {
                        for (Quote qp : listQuotebyPackage) {
                            if (qp.getPackageId().equals(q.getPackageId()) && qp.getSupplierId().equals(q.getSupplierId())) {
                                q.setTotal(qp.getTotal());
                                q.setQuotePrice(qp.getQuotePrice());
                                q.setRemark(qp.getRemark());
                                q.setDeliveryTime(qp.getDeliveryTime());
                                q.setIsTurnUp(qp.getIsTurnUp());
                            }
                        }
                        
                        for (ProjectDetail projectDetail : detailList) {
                            if (q.getProjectDetail().getId().equals(projectDetail.getId())) {
                                ProjectDetail pd = new ProjectDetail();
                                pd.setId(projectDetail.getId());
                                pd.setGoodsName(projectDetail.getGoodsName());
                                pd.setSerialNumber(projectDetail.getSerialNumber());
                                pd.setStand(projectDetail.getStand());
                                pd.setQualitStand(projectDetail.getQualitStand());
                                pd.setItem(projectDetail.getItem());
                                pd.setPurchaseCount(projectDetail.getPurchaseCount());
                                pd.setTotal(q.getTotal());
                                pd.setDeliveryTime(q.getDeliveryTime());
                                pd.setRemark(q.getRemark());
                                pd.setQuotePrice(q.getQuotePrice());
                                pd.setSupplierId(q.getSupplierId());
                                pd.setIsTurnUp(q.getIsTurnUp());
                                detailList1.add(pd);
                            }
                        }
                    }
                    detailList = detailList1;
                } else {
                    detailList = detailService.selectByCondition(map1, null);
                    BigDecimal projectBudget = BigDecimal.ZERO;
                    for (ProjectDetail projectDetail : detailList) {
                        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
                    }
                    pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
                }
                //
                for (SaleTender saleTender : saleTenderList) {
                    if (saleTender.getPackages().indexOf(pk.getId()) != -1) {
                        Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                        Quote quote=new Quote();
                        quote.setProjectId(projectId);
                        quote.setPackageId(pk.getId());
                        quote.setSupplierId(supplier.getId());
                        List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                        if (listQuote != null && listQuote.size() >0) {
                            supplier.setIsturnUp(listQuote.get(0).getIsTurnUp().toString());
                        }
                        supplierList.add(supplier);
                    }
                }
                hashMap.put(supplierList, detailList);
                listPd.add(hashMap);
            }
            model.addAttribute("flagButton", flagButton);
            model.addAttribute("listPd", listPd);
            model.addAttribute("listPackage", listPackage);
            model.addAttribute("projectId", projectId);
        }
        model.addAttribute("flag", flag);
        return "bss/ppms/open_bidding/bid_file/changbiao";
    }
    
    @RequestMapping(value="quoteAgainTotal")
    public String quoteAgain(String projectId, String packId, String flowDefineId, Model model) throws ParseException{
        TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
        SaleTender condition = new SaleTender();
        //唱总价
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        Quote quote2 = new Quote();
        condition.setProjectId(projectId);
        condition.setPackages(packId);
        condition.setStatusBid(NUMBER_TWO);
        condition.setStatusBond(NUMBER_TWO);
        List<SaleTender> stList = saleTenderService.find(condition);
        map1.put("packageId", packId);
        map1.put("projectId", projectId);
        List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
        BigDecimal projectBudget = BigDecimal.ZERO;
        for (ProjectDetail projectDetail : detailList) {
            projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
        }
        
        
        
        quote2.setProjectId(projectId);
        quote2.setPackageId(packId);
        List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote2);
        List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
        if (listDate1 != null && listDate1.size() > 1) {
          //给第二次报价的数据查到
            quote2.setProjectId(projectId);
            quote2.setPackageId(packId);
            quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
            listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
            
        } 
        //再次点击 查看
        for (SaleTender saleTender : stList) {
            Quote quote = new Quote();
            quote.setProjectId(projectId);
            quote.setPackageId(packId);
            quote.setSupplierId(saleTender.getSupplierId());
            quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
            List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
            if (allQuote != null && allQuote.size() > 0) {
                for (Quote conditionQuote : allQuote) {
                    if (conditionQuote.getSupplier()!=null&&conditionQuote.getSupplier().getId().equals(saleTender.getSuppliers().getId()) &&
                        conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                          for (Quote qp : listQuotebyPackage1) {
                              if (qp.getPackageId().equals(conditionQuote.getPackageId())) {
                                  conditionQuote.setTotal(qp.getTotal());
                                  conditionQuote.setQuotePrice(qp.getQuotePrice());
                                  conditionQuote.setRemark(qp.getRemark());
                                  conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                                  conditionQuote.setIsTurnUp(qp.getIsTurnUp());
                              }
                          }
                        saleTender.setTotal(conditionQuote.getTotal());
                        saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                        saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                        saleTender.setQuoteId(conditionQuote.getId());
                    }
                }
            }
        }
        map.put("id", packId);
        List<Packages> pack = packageService.findPackageById(map);
        if (pack != null && pack.size() > 0) {
            treeMap.put(pack.get(0).getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
        } else {
            treeMap.put("", stList);
        };
        model.addAttribute("projectId", projectId);
        model.addAttribute("treeMap", treeMap);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/open_bidding/bid_file/quote_again";
    }
    
    @RequestMapping(value="viewquoteAgainTotal")
    public String viewquoteAgainTotal(String projectId, String flowDefineId, String packId, String timestamp, Model model) throws ParseException{
        TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
        SaleTender condition = new SaleTender();
        //唱总价
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        Quote quote2 = new Quote();
        condition.setProjectId(projectId);
        condition.setPackages(packId);
        condition.setStatusBid(NUMBER_TWO);
        condition.setStatusBond(NUMBER_TWO);
        List<SaleTender> stList = saleTenderService.find(condition);
        map1.put("packageId", packId);
        map1.put("projectId", projectId);
        List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
        BigDecimal projectBudget = BigDecimal.ZERO;
        for (ProjectDetail projectDetail : detailList) {
            projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
        }
        quote2.setProjectId(projectId);
        //再次点击 查看
        for (SaleTender saleTender : stList) {
            Quote quote = new Quote();
            quote.setProjectId(projectId);
            quote.setPackageId(packId);
            quote.setSupplierId(saleTender.getSupplierId());
            quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
            List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
            if (allQuote != null && allQuote.size() > 0) {
                for (Quote conditionQuote : allQuote) {
                    if (conditionQuote.getSupplier()!=null&&conditionQuote.getSupplier().getId().equals(saleTender.getSuppliers().getId()) &&
                        conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                        saleTender.setTotal(conditionQuote.getTotal());
                        saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                        saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                        saleTender.setQuoteId(conditionQuote.getId());
                    }
                }
            }
        }
        map.put("id", packId);
        List<Packages> pack = packageService.findPackageById(map);
        if (pack != null && pack.size() > 0) {
            treeMap.put(pack.get(0).getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
        } else {
            treeMap.put("", stList);
        };
        model.addAttribute("projectId", projectId);
        model.addAttribute("treeMap", treeMap);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/open_bidding/bid_file/view_quote_again";
    }
    
    @RequestMapping(value="quoteAgainMingxi")
    public String quoteAgainMingxi(String projectId, String packId, String flowDefineId, Model model) throws ParseException{
        Project project = projectService.selectById(projectId);
        BigDecimal projectBudget = BigDecimal.ZERO;
        DictionaryData dd = null;
        if (project != null && project.getPurchaseType() != null ){
            dd = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        //唱明细
        Quote quotes = new Quote();
        Quote quote1 = new Quote();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("projectId", projectId);
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        
        //开始循环包
        List<HashMap<List<Supplier>,List<ProjectDetail>>> listPd = new ArrayList<HashMap<List<Supplier>,List<ProjectDetail>>>();
        HashMap<List<Supplier>,List<ProjectDetail>> hashMap = new HashMap<List<Supplier>,List<ProjectDetail>>();
        List<Supplier> supplierList = new ArrayList<Supplier>();
        map1.put("packageId", packId);
        //
        quotes.setProjectId(projectId);
        quotes.setPackageId(packId);
        List<Date> listDate =  supplierQuoteService.selectQuoteCount(quotes);
        List<Quote> listQuotebyPackage = new ArrayList<Quote>();
        if (listDate != null && listDate.size() >0) {
            if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
                quote1.setProjectId(projectId);
                quote1.setPackageId(packId);
                quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
           } 
        }
        List<ProjectDetail> detailList = null;
        if (listDate.size() != 0) {
            Quote quote=new Quote();
            quote.setProjectId(projectId);
            quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
            List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
            detailList = detailService.selectByCondition(map1, null);
            for (ProjectDetail projectDetail : detailList) {
                projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
            }
            List<ProjectDetail> detailList1 = new ArrayList<ProjectDetail>();
            for (Quote q : listQuote) {
                for (Quote qp : listQuotebyPackage) {
                    if (qp.getPackageId().equals(q.getPackageId())) {
                        q.setTotal(qp.getTotal());
                        q.setQuotePrice(qp.getQuotePrice());
                        q.setRemark(qp.getRemark());
                        q.setDeliveryTime(qp.getDeliveryTime());
                        q.setIsTurnUp(qp.getIsTurnUp());
                    }
                }
                
                for (ProjectDetail projectDetail : detailList) {
                    if (q.getProjectDetail().getId().equals(projectDetail.getId())) {
                        ProjectDetail pd = new ProjectDetail();
                        pd.setId(projectDetail.getId());
                        pd.setGoodsName(projectDetail.getGoodsName());
                        pd.setSerialNumber(projectDetail.getSerialNumber());
                        pd.setStand(projectDetail.getStand());
                        pd.setQualitStand(projectDetail.getQualitStand());
                        pd.setItem(projectDetail.getItem());
                        pd.setPurchaseCount(projectDetail.getPurchaseCount());
                        pd.setTotal(q.getTotal());
                        pd.setDeliveryTime(q.getDeliveryTime());
                        pd.setRemark(q.getRemark());
                        pd.setQuotePrice(q.getQuotePrice());
                        pd.setSupplierId(q.getSupplierId());
                        pd.setIsTurnUp(q.getIsTurnUp());
                        detailList1.add(pd);
                    }
                }
            }
            detailList = detailList1;
        } else {
            detailList = detailService.selectByCondition(map1, null);
            for (ProjectDetail projectDetail : detailList) {
                projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
            }
        }
        //
        for (SaleTender saleTender : saleTenderList) {
            if (saleTender.getPackages().indexOf(packId) != -1) {
                Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                Quote quote=new Quote();
                quote.setProjectId(projectId);
                quote.setPackageId(packId);
                quote.setSupplierId(supplier.getId());
                List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                if (listQuote != null && listQuote.size() >0) {
                    supplier.setIsturnUp(listQuote.get(0).getIsTurnUp().toString());
                }
                supplierList.add(supplier);
            }
        }
        hashMap.put(supplierList, detailList);
        listPd.add(hashMap);
        model.addAttribute("listPd", listPd);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("listPd", listPd);
        model.addAttribute("packId", packId);
        HashMap<String, Object> condition = new HashMap<String, Object>();
        condition.put("id", packId);
        List<Packages> pList = packageService.findPackageById(condition);
        if (pList != null && pList.size() > 0) {
            pList.get(0).setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
        }
        model.addAttribute("listPackage", pList);
        return "bss/ppms/open_bidding/bid_file/quote_again_mingxi";
    }
    
    @RequestMapping(value="viewquoteAgainTotalMingxi")
    public String viewquoteAgainTotalMingxi(String projectId, String flowDefineId, String packId, String timestamp, Model model) throws ParseException{
        //唱明细
        Quote quotes = new Quote();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("projectId", projectId);
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        
        //开始循环包
        List<HashMap<List<Supplier>,List<ProjectDetail>>> listPd = new ArrayList<HashMap<List<Supplier>,List<ProjectDetail>>>();
        HashMap<List<Supplier>,List<ProjectDetail>> hashMap = new HashMap<List<Supplier>,List<ProjectDetail>>();
        List<Supplier> supplierList = new ArrayList<Supplier>();
        map1.put("packageId", packId);
        //
        quotes.setProjectId(projectId);
        quotes.setPackageId(packId);
        List<Date> listDate =  supplierQuoteService.selectQuoteCount(quotes);
        List<ProjectDetail> detailList = null;
        if (listDate.size() != 0) {
            Quote quote=new Quote();
            quote.setProjectId(projectId);
            quote.setPackageId(packId);
            quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
            List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
            detailList = detailService.selectByCondition(map1, null);
            List<ProjectDetail> detailList1 = new ArrayList<ProjectDetail>();
            for (Quote q : listQuote) {
                for (ProjectDetail projectDetail : detailList) {
                    if (q.getProjectDetail().getId().equals(projectDetail.getId())) {
                        ProjectDetail pd = new ProjectDetail();
                        pd.setId(projectDetail.getId());
                        pd.setGoodsName(projectDetail.getGoodsName());
                        pd.setSerialNumber(projectDetail.getSerialNumber());
                        pd.setStand(projectDetail.getStand());
                        pd.setQualitStand(projectDetail.getQualitStand());
                        pd.setItem(projectDetail.getItem());
                        pd.setPurchaseCount(projectDetail.getPurchaseCount());
                        pd.setTotal(q.getTotal());
                        pd.setDeliveryTime(q.getDeliveryTime());
                        pd.setRemark(q.getRemark());
                        pd.setQuotePrice(q.getQuotePrice());
                        pd.setSupplierId(q.getSupplierId());
                        pd.setIsTurnUp(q.getIsTurnUp());
                        detailList1.add(pd);
                    }
                }
            }
            detailList = detailList1;
        } else {
            detailList = detailService.selectByCondition(map1, null);
        }
        for (SaleTender saleTender : saleTenderList) {
            if (saleTender.getPackages().indexOf(packId) != -1) {
                Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                Quote quote=new Quote();
                quote.setProjectId(projectId);
                quote.setPackageId(packId);
                quote.setSupplierId(supplier.getId());
                List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                if (listQuote != null && listQuote.size() >0) {
                    supplier.setIsturnUp(listQuote.get(0).getIsTurnUp().toString());
                }
                supplierList.add(supplier);
            }
        }
        hashMap.put(supplierList, detailList);
        listPd.add(hashMap);
        model.addAttribute("listPd", listPd);
        model.addAttribute("projectId", projectId);
        model.addAttribute("listPd", listPd);
        model.addAttribute("packId", packId);
        HashMap<String, Object> condition = new HashMap<String, Object>();
        condition.put("id", packId);
        List<Packages> pList = packageService.findPackageById(condition);
        model.addAttribute("listPackage", pList);
        model.addAttribute("flowDefineId", flowDefineId);
        return "bss/ppms/open_bidding/bid_file/view_quote_again_mingxi";
    }
    
    @RequestMapping(value="quotetab1")
    public String quotetab1(String projectId, Model model){
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_file/quote_tab1";
    }
    
    @RequestMapping(value="quotetab2")
    public String quotetab2(String projectId, Model model){
        model.addAttribute("projectId", projectId);
        return "bss/ppms/open_bidding/bid_file/quote_tab2";
    }
    
    /**
     *〈简述〉保存报价信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param quote 报价实体类
     * @param model 模型
     * @param priceStr 前台的所有报价数据组成的字符串
     * @return String
     * @throws ParseException 异常处理
     */
    @RequestMapping(value = "/savemingxi")
    public String saves(HttpServletRequest req, Quote quote, Model model, String priceStr, String packId) throws Exception {
        List<String> listBd = Arrays.asList(priceStr.split(","));
        User user = (User) req.getSession().getAttribute("loginUser");
        List<Quote> listQuote = new ArrayList<Quote>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", quote.getProjectId());
        List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map, null);
        //
        SaleTender st = new SaleTender();
        st.setProjectId(quote.getProjectId());
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        List<Packages> listPackage = new ArrayList<Packages>();
        for (Packages packages : listPack) {
            if (sb.toString().indexOf(packages.getId()) != -1) {
                if (packId != null && !"".equals(packId) && !packId.equals("undefined")) {
                    if (!packages.getId().equals(packId)) {
                        continue;
                    }
                }
                listPackage.add(packages);
            }
        }
        
        //循环次数
        Integer count = 0;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        for (Packages pk:listPackage) {
            Integer count1 = 0;
            map.put("packageId", pk.getId());
            List<ProjectDetail> detailList = detailService.selectByCondition(map, 0);
            SaleTender str = new SaleTender();
            str.setProjectId(quote.getProjectId());
            List<SaleTender> stl= saleTenderService.find(str);
            for (SaleTender saleTender : stl) {
                if (saleTender.getPackages().indexOf(pk.getId()) != -1) {
                    count1++;
                }
            }
            Integer count2 = detailList.size();
            //因为暂时没有想到怎么传包ID  所以当一个包下有多个供应商的时候 会导致存数据过少。我是按照物资明细来遍历的，一个供应商就只有一倍 ，两个就俩倍
            if (count1 != 1){
                count2 =count2*count1;
            }
            for (int i= 0; i < count2; i++) {
                Quote qt = new Quote();
                count++;
                qt.setProjectId(quote.getProjectId());
                qt.setSupplierId(listBd.get(count*7 -3));
                qt.setPackageId(pk.getId());
                qt.setIsTurnUp(Integer.parseInt(listBd.get(count * 7 - 1)));
                qt.setProductId(listBd.get(count*7 -2));
                qt.setQuotePrice(new BigDecimal(listBd.get(count * 7 - 7)));
                qt.setTotal(new BigDecimal(listBd.get(count * 7 - 6)));
                qt.setDeliveryTime(URLDecoder.decode(listBd.get(count * 7 - 5), "UTF-8"));
                qt.setRemark(URLDecoder.decode(listBd.get(count * 7 - 4), "UTF-8").equals("null") ? "" : URLDecoder.decode(listBd.get(count * 7 - 4), "UTF-8"));
                qt.setCreatedAt(timestamp);
                listQuote.add(qt);
            }
        }
        try {
            supplierQuoteService.insert(listQuote);
            //修改状态
            SaleTender saleTender = new SaleTender();
            saleTender.setProjectId(quote.getProjectId());
            saleTender.setSupplierId(user.getTypeId());
            List<SaleTender> sts = saleTenderService.find(saleTender);
            SaleTender std = sts.get(0);
            std.setBidFinish((short) 3);
            saleTenderService.update(std);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:changmingxi.html?projectId="+quote.getProjectId();
    }
    
    /**
     * @Title: toubiao
     * @author Song Biaowei
     * @date 2016-11-22 下午7:48:44  
     * @Description: 开标投标
     * @param @param projectId
     * @param @param model
     * @param @return      
     * @return String
     */
    @RequestMapping("/toubiao")
    public String toubiao(String projectId, Model model ){
        //项目信息
        Project project=projectService.selectById(projectId);
        //参与项目的所有供应商
        List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
        if(listSupplier.size()>0){
            for(Supplier sup:listSupplier){
                SaleTender saleTender=new SaleTender();
                saleTender.setSupplierId(sup.getId());
                List<SaleTender> st=saleTenderService.list(saleTender, 1);
                if(st!=null){
                    if(st.get(0).getBidFinish()==0||st.get(0).getBidFinish()==1){
                        sup.setBidFinish("未上传");
                    }else{
                        sup.setBidFinish("已上传");
                    }
                }
            }
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        List<Packages> list = packageService.findPackageById(map);
        if(list != null && list.size()>0){
            for(Packages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("packageList", list);
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("project", project);
        //开标时间
        long bidDate=0;
        if(project.getBidDate()==null){
        }else{
            bidDate=project.getBidDate().getTime();
        }
        long nowDate=new Date().getTime();
        long date=bidDate-nowDate;
        if(date<0){
            //存一条数据到后台
        }
        model.addAttribute("date", date);
        return "bss/ppms/open_bidding/bid_file/open_bid";
    }
    
    
    /**
     * 
     *〈谈判记录〉
     *〈详细描述〉
     * @author Fengtian
     * @param projectId
     * @param model
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/negotiation")
    public String negotiation(String projectId, Model model, String flowDefineId){
        Project project = projectService.selectById(projectId);
        if (StringUtils.isNotBlank(project.getId())){
            Project projectNew = new Project();
            projectNew.setId(projectId);
            projectNew.setStatus(DictionaryDataUtil.getId("CQPSZJZ"));
            projectService.update(projectNew);
            
           //根据包获取抽取出的专家
            List<Packages> listResultExpert = packagesService.listProjectExtract(projectId);
            for (int i = 0; i < listResultExpert.size(); i++ ) {
                Negotiation negotiation  =  negotiationService.selectByPackageId(listResultExpert.get(i).getId());
                if (negotiation != null) {
                    listResultExpert.get(i).setNegotiation(negotiation);
                }else{
                    listResultExpert.get(i).setNegotiation(new Negotiation());
                    model.addAttribute("uuId", WfUtil.createUUID());
                }
            }
            
            model.addAttribute("project", project);
            model.addAttribute("projectId", projectId);
            model.addAttribute("listResultExpert", listResultExpert);
        }
        
       /* Negotiation negotiation = negotiationService.selectByProjectId(project.getId());
        model.addAttribute("project", project);
        if(negotiation != null){
            model.addAttribute("negotiation", negotiation);
        }else{
            model.addAttribute("uuId", WfUtil.createUUID());
        }
        model.addAttribute("dataId", DictionaryDataUtil.getId("NEGOTIATION_RECORD"));*/
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("dataId", DictionaryDataUtil.getId("NEGOTIATION_RECORD"));
        return "bss/ppms/open_bidding/bid_file/negotiation";
    }
    
    /**
     * 
     *〈去重〉
     *〈详细描述〉
     * @author FengTian
     * @param list
     */
    public void removeSame(List<PackageExpert> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getExpertId().equals(list.get(i).getExpertId())) {
                    list.remove(j);
                }
            }
        }
    }
    
    /**
     * 
     *〈保存谈判记录〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param createdAt
     * @param nuter
     * @param net
     */
    @RequestMapping("/saveNet")
    @ResponseBody
    public String saveNet(String negId, String uuId, String projectId, String createdAt, String nuter, String net, String packageId){
        Date date = new Date(); 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        Negotiation negotiation = new Negotiation();
        negotiation.setProjectId(projectId);
        negotiation.setNegotiationRecord(net);
        negotiation.setNuter(nuter);
        try {   
            date = sdf.parse(createdAt); 
            negotiation.setCreatedAt(date);
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        Negotiation negotiation2 = negotiationService.selectByPackageId(packageId);
        if(negotiation2 == null){
            negotiation.setId(WfUtil.createUUID());
            negotiation.setPackageId(packageId);
            negotiationService.save(negotiation);
            return ONE;
        }else{
            negotiation.setId(negotiation2.getId());
            negotiationService.update(negotiation);
            return TWO;
        }
        
    }
    
    @RequestMapping("/negotiationReport")
    public String NegotiationReport(String projectId, Model model, String flowDefineId){
        if (StringUtils.isNotBlank(projectId)){
            Project project = projectService.selectById(projectId);
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2.put("projectId", projectId);
            List<PackageExpert> expertSigneds = packageExpertService.selectList(map2);
            if (expertSigneds != null && expertSigneds.size() > 0) {
                List<Packages> packages = packageService.listProjectExtract(projectId);
                for (int i = 0; i < packages.size(); i++ ) {
                    NegotiationReport report =  reportService.selectByPackageId(packages.get(i).getId());
                    SupplierCheckPass checkPass = new SupplierCheckPass();
                    checkPass.setIsWonBid((short)1);
                    checkPass.setPackageId(packages.get(i).getId());
                    List<SupplierCheckPass> listCheckPass = supplierCheckPassService.listCheckPass(checkPass);
                    if(listCheckPass != null && listCheckPass.size() > 0){
                        packages.get(i).setSupplierList(listCheckPass);
                    }else{
                        packages.get(i).setSupplierList(new ArrayList<SupplierCheckPass>());
                    }
                    
                    if (report != null) {
                        packages.get(i).setNegotiationReport(report);
                    }else{
                        packages.get(i).setNegotiationReport(new NegotiationReport());
                    }
                }
                
                model.addAttribute("expertSigneds", expertSigneds);
                model.addAttribute("packages", packages);
                model.addAttribute("project", project);
                model.addAttribute("projectId", projectId);
                model.addAttribute("flowDefineId", flowDefineId);
            }
        }
        return "bss/ppms/open_bidding/bid_file/negotiation_report";
    }
    
    @RequestMapping("/saveNetReport")
    @ResponseBody
    public String saveNetReport(String projectId, String packageId, String reviewTime, String reviewSite, String finalOffer, String talks){
        Date date = new Date(); 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
        NegotiationReport report = new NegotiationReport();
        report.setProjectId(projectId);
        report.setReviewSite(reviewSite);
        report.setTalks(talks);
        try {   
            date = sdf.parse(reviewTime); 
            report.setReviewTime(date);
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        NegotiationReport negotiation2 = reportService.selectByPackageId(packageId);
        if(negotiation2 == null){
            report.setId(WfUtil.createUUID());
            report.setPackageId(packageId);
            reportService.save(report);
            return ONE;
        }else{
            report.setId(negotiation2.getId());
            reportService.update(report);
            return TWO;
        }
        
    }
    
    @RequestMapping("/educe")
    public ResponseEntity<byte[]> educe(String projectId, String createdAt, String nuter, String net,  HttpServletRequest request) throws Exception{
        Project project = projectService.selectById(projectId);
        List<Packages> listResultExpert = packagesService.listProjectExtract(projectId);
        String downFileName = null;
        // 文件存储地址
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
        String fileName = createWordMethod(project, createdAt, nuter, net, listResultExpert,request);
        downFileName = new String("谈判记录表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        return projectService.downloadFile(fileName, filePath, downFileName);
    }
    
    @RequestMapping("/educes")
    public ResponseEntity<byte[]> educes(String projectId, String reviewTime, String reviewSite, String finalOffer, String talks, String supperName, String packageId, HttpServletRequest request) throws Exception{
        Project project = projectService.selectById(projectId);
        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("projectId", projectId);
        map2.put("packageId", packageId);
        List<PackageExpert> expertSigneds = packageExpertService.selectList(map2);
        if (expertSigneds != null && expertSigneds.size() > 0) {
            List<Packages> packages = packageService.listProjectExtract(projectId);
            for (int i = 0; i < packages.size(); i++ ) {
                NegotiationReport report =  reportService.selectByPackageId(packages.get(i).getId());
                SupplierCheckPass checkPass = new SupplierCheckPass();
                checkPass.setIsWonBid((short)1);
                checkPass.setPackageId(packages.get(i).getId());
                List<SupplierCheckPass> listCheckPass = supplierCheckPassService.listCheckPass(checkPass);
                if(listCheckPass != null && listCheckPass.size() > 0){
                    packages.get(i).setSupplierList(listCheckPass);
                }else{
                    packages.get(i).setSupplierList(new ArrayList<SupplierCheckPass>());
                }
                
                if (report != null) {
                    packages.get(i).setNegotiationReport(report);
                }else{
                    packages.get(i).setNegotiationReport(new NegotiationReport());
                }
            }
        }
        String downFileName = null;
        // 文件存储地址
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
        String fileName = createWordMethods(project, reviewTime, reviewSite, finalOffer, talks, supperName, expertSigneds, request);
        downFileName = new String("谈判报告表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        return projectService.downloadFile(fileName, filePath, downFileName);
    }
    
    /**
     * 
     *〈freeMarker〉
     *〈详细描述〉
     * @author FengTian
     * @param project
     * @param createdAt
     * @param nuter
     * @param net
     * @param selectList
     * @param request
     * @return
     * @throws Exception
     */
    private String createWordMethod(Project project, String createdAt, String nuter, String net, List<Packages> selectList, HttpServletRequest request) throws Exception {
        Date date = new Date(); 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {   
            date = sdf.parse(createdAt); 
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", project.getName() == null ? "" : project.getName());
        dataMap.put("projectNumber", project.getProjectNumber() == null ? "" : project.getProjectNumber());
        dataMap.put("address", project.getBidAddress() == null ? "" : project.getBidAddress());
        dataMap.put("date", date == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        dataMap.put("nuter", nuter == null ? "" : nuter);
        dataMap.put("net", net == null ? "" : net);
        dataMap.put("selectList", selectList == null ? "" : selectList);
        String newFileName = null;
        // 文件名称
        String fileName = new String(("谈判记录表.doc").getBytes("UTF-8"), "UTF-8");
        /** 生成word 返回文件名 */
        newFileName = WordUtil.createWord(dataMap, "negotiation.ftl", fileName, request);
        return newFileName;
    
    }
    
    private String createWordMethods(Project project, String reviewTime, String reviewSite,String supperName, String finalOffer, String talks, List<PackageExpert> selectList, HttpServletRequest request) throws Exception {
        Date date = new Date(); 
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {   
            date = sdf.parse(reviewTime); 
        } catch (Exception e) {   
            e.printStackTrace();   
        }
        /** 用于组装word页面需要的数据 */
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("projectName", project.getName() == null ? "" : project.getName());
        dataMap.put("projectNumber", project.getProjectNumber() == null ? "" : project.getProjectNumber());
        dataMap.put("date", date == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        dataMap.put("reviewSite", reviewSite == null ? "" : reviewSite);
        dataMap.put("finalOffer", finalOffer == null ? "" : finalOffer);
        dataMap.put("talks", talks == null ? "" : talks);
        dataMap.put("expertSigneds", selectList == null ? "" : selectList);
        dataMap.put("supplierName", supperName == null ? "" : supperName);
        String newFileName = null;
        // 文件名称
        String fileName = new String(("谈判报告表.doc").getBytes("UTF-8"), "UTF-8");
        /** 生成word 返回文件名 */
        newFileName = WordUtil.createWord(dataMap, "report.ftl", fileName, request);
        return newFileName;
    
    }
    
    /**
     *〈简述〉编制公告
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param noticeType 公告类型
     * @param model
     * @return
     */
    public String makeNotice(String projectId, String noticeType, Model model, String flowDefineId){
        Project project = projectService.selectById(projectId);
        ArticleType articleType = new ArticleType();
        Article article = new Article();
        //采购方式数据字典
        DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
        //如果是单一来源
        
        //如果不是单一来源
        //如果是拟制招标公告
        if (PURCHASE_NOTICE.equals(noticeType)) {
            //采购公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("purchase_notice"));
            //集中采购
            ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrlized");
            if (articleType2 != null) {
                article.setSecondArticleTypeId(articleType2.getId());
            }
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_enquiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_engineering");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_enquiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_service");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_enqiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            }
        }
        //如果是拟制中标公告
        if (WIN_NOTICE.equals(noticeType)) {
            //中标公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
            //集中采购
            ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("success_notice_centralized");
            if (articleType2 != null) {
                article.setSecondArticleTypeId(articleType2.getId());
            }
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_matarials");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_enquiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_engineering");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_enquiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                ArticleType articleType3 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_service");
                if (articleType3 != null) {
                  article.setThreeArticleTypeId(articleType3.getId());
                }
                if (dd != null) {
                  if ("GKZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_open");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("XJCG".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_enquiry");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("JZXTP".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_competitive");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                  if ("YQZB".equals(dd.getCode())) {
                    ArticleType articleType4 = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_invitation");
                    if (articleType4 != null) {
                      article.setFourArticleTypeId(articleType4.getId());
                      article.setLastArticleType(articleType4);
                    }
                  };
                }
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            }
        }
        Article art = new Article();
        article.setProjectId(projectId);
        //查询公告列表中是否有该项目的招标公告
        List<Article> articles = articelService.selectArticleByProjectId(article);
        //判断该项目是否已经存在该类型公告
        if (articles != null && articles.size() > 0) {
            //判断该项目的公告是否提交
            if (articles.get(0).getStatus() == 1 || articles.get(0).getStatus() == 2){
                //已提交或发布公告
                model.addAttribute("article", articles.get(0));
                model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
                model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
                model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
                if (WIN_NOTICE.equals(noticeType)) {
                    model.addAttribute("typeId_examine", DictionaryDataUtil.getId("WIN_BID_ADUIT"));
                }
                if (PURCHASE_NOTICE.equals(noticeType)) {
                    model.addAttribute("typeId_examine", DictionaryDataUtil.getId("PROJECT_BID_ADUIT"));
                }
                
                return "bss/ppms/open_bidding/bid_notice/view";
            } else {
                //暂存或退回状态
                model.addAttribute("project", project);
                model.addAttribute("article", articles.get(0));
                model.addAttribute("articleId", articles.get(0).getId());
                model.addAttribute("projectId", projectId);
                model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
                model.addAttribute("noticeType", noticeType);
                model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
                model.addAttribute("flowDefineId", flowDefineId);
                model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
                return "bss/ppms/open_bidding/bid_notice/add";
            }
        } else {
            model.addAttribute("article", article);
            model.addAttribute("project", project);
            String articleId = WfUtil.createUUID();
            model.addAttribute("articleId",articleId);
            model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            model.addAttribute("projectId", projectId);
            model.addAttribute("noticeType", noticeType);
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
            if (WIN_NOTICE.equals(noticeType)) {
                model.addAttribute("typeId_examine", DictionaryDataUtil.getId("WIN_BID_ADUIT"));
            }
            if (PURCHASE_NOTICE.equals(noticeType)) {
                model.addAttribute("typeId_examine", DictionaryDataUtil.getId("PROJECT_BID_ADUIT"));
            }
            model.addAttribute("flowDefineId", flowDefineId);
            model.addAttribute("articleId", articleId);
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            
            return "bss/ppms/open_bidding/bid_notice/add";
        }
    }
    
    public String getContent(String projectId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> list = packageService.findPackageById(map);
        if(list != null && list.size()>0){
            for(Packages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<ProjectDetail> detailList = detailService.selectById(packageId);
                ps.setProjectDetails(detailList);
            }
        }
        StringBuilder sb = articelService.getContent(list);
        return sb.toString();
    }
    
    public void getDefaultTemplate(String projectId, Model model, String type) {
        
        Project project = projectService.selectById(projectId);
        //采购方式数据字典
        DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
        List<Templet> templets = null;
        if (type.equals(PURCHASE_NOTICE)) {
          Templet templet = new Templet();
          if ("GKZB".equals(dd.getCode())) {
            templet.setTemType("0");
          }
          if ("XJCG".equals(dd.getCode())){
            templet.setTemType("2");
          }
          if ("JZXTP".equals(dd.getCode())){
            templet.setTemType("3");
          }
          if ("YQZB".equals(dd.getCode())){
            templet.setTemType("1");
          }
          templets = templetService.search(1, templet);
        }
        if (type.equals(WIN_NOTICE)) {
          Templet templet = new Templet();
          if ("GKZB".equals(dd.getCode())) {
            templet.setTemType("5");
          }
          if ("XJCG".equals(dd.getCode())){
            templet.setTemType("7");
          }
          if ("JZXTP".equals(dd.getCode())){
            templet.setTemType("8");
          }
          if ("YQZB".equals(dd.getCode())){
            templet.setTemType("6");
          }
          templets = templetService.search(1, templet);
        }
        if (templets != null && templets.size() > 0) {
            String content = templets.get(0).getContent();
            Article article1 = new Article();
            String table = getContent(projectId);
            Project p = projectService.selectById(projectId);
            String purchaseTypeName = "";
            StringBuilder auditResult = new StringBuilder();
            auditResult.append("");
            //评分结果
            HashMap<String ,Object> map = new HashMap<String ,Object>();
            map.put("projectId", projectId);
            //查询包信息
            List<Packages> packageList = packageService.findPackageById(map);
            for (Packages packages : packageList) {
              SupplierCheckPass checkPass = new SupplierCheckPass();
              checkPass.setPackageId(packages.getId());
              List<SupplierCheckPass> supplierCheckPasses = supplierCheckPassService.listCheckPass(checkPass);
              auditResult.append("<p>"+packages.getName()+"参加供应商排名:</p>");
              if (supplierCheckPasses != null && supplierCheckPasses.size() > 0) {
                for (int i = 1; i < supplierCheckPasses.size()+1; i++) {
                  Supplier supplier = supplierCheckPasses.get(i-1).getSupplier();
                  if (supplier != null) {
                    auditResult.append("<p>&nbsp;&nbsp;第"+i+"名:"+supplier.getSupplierName()+"</p>");
                  }
                }
              }
            }
            if (p.getPurchaseType() != null) {
               purchaseTypeName = DictionaryDataUtil.findById(p.getPurchaseType()).getName();
            }
            String purchaserName = "";
            PurchaseDep pd = null;
            if (p != null) {
                String purchaseDepId = p.getPurchaseDepId();
                Orgnization org = orgnizationService.getOrgByPrimaryKey(purchaseDepId);
                if (org != null) {
                  purchaserName = org.getName();
                }
            }
            StringBuilder builder = new StringBuilder();
            if (p != null) {
                List<SaleTender> selectListByProjectId = saleTenderService.selectListByProjectId(p.getId());
                for (SaleTender saleTender : selectListByProjectId) {
                    Packages packages = packageService.selectByPrimaryKeyId(saleTender.getPackages());
                    builder.append("<p>"+packages.getName()+"供应商名称:</p>");
                    Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                    if(supplier != null){
                        builder.append("<p>&nbsp;&nbsp;"+supplier.getSupplierName()+"</p>");
                    }
                    
                }
                
            }
            String contact = "";
            String contactTelephone = "";
            String contactAddress = "";
            String fax = "";
            String bank = "";
            String bidDate = "";
            if (p.getBidDate() != null) {
              bidDate = new SimpleDateFormat("yyyy年MM月dd日").format(p.getBidDate());
            }
            if (pd != null) {
                 contact = pd.getContact();
                 purchaserName = pd.getDepName();
                 contactTelephone = pd.getContactTelephone();
                 contactAddress = pd.getContactAddress();
                 fax = pd.getFax();
                 bank = pd.getBank();
            }
            content = content.replace("projectDetail", table).replace("projectName", p.getName()).replace("projectNum", p.getProjectNumber()).replace("purchaseType", purchaseTypeName);
            content = content.replace("bidDate", bidDate).replace("contact", contact);
            content = content.replace("purchaserName", purchaserName).replace("telephone", contactTelephone);
            content = content.replace("address", contactAddress).replace("fax", fax).replace("bank", bank).replace("auditResult", auditResult.toString());
            content = content.replace("supplier", builder.toString());
            article1.setContent(content);
            model.addAttribute("article1", article1);
        }
    
    }
}
