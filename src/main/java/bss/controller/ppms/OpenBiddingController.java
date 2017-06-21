package bss.controller.ppms;

import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.AjaxJsonData;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TempletService;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Negotiation;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Reason;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageExpert;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
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
import bss.util.PropUtil;

import com.alibaba.fastjson.JSON;
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


  @Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;

  /**
   * 推送待办
   */
  @Autowired
  private  TodosService todosService;


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
   * 机构
   */
  @Autowired
  private PurChaseDepOrgService purChaseDepOrgService;


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

  @Autowired
  private BidMethodService bidMethodService;
  
  @Autowired
  private PurchaseServiceI purchaseService;
  
  @Autowired
  private UserServiceI userService;
  @Autowired
  private MarkTermService markTermService;
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
  public String bidFile(@CurrentUser User user,HttpServletRequest request, String id, Model model, HttpServletResponse response, String flowDefineId,Integer process) throws Exception{
    //类别是否是在流程中展示 process 1不在流程中  2在流程中  
    model.addAttribute("process", process);  


    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", id);
    List<Packages> packages = packageService.findPackageById(map);
    String msg = "";
    if (process != null && process == 1) {
      //审核页面不用校验是否完成
    } else {
      for (Packages p : packages) {
        //判断各包符合性审查项是否编辑完成
        FirstAudit firstAudit = new FirstAudit();
        firstAudit.setProjectId(id);
        firstAudit.setPackageId(p.getId());
        firstAudit.setIsConfirm((short)0);
        List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
        if (fas == null || fas.size() <= 0) {
          msg = "noFirst";
          return "redirect:/firstAudit/toAdd.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
        }
        //获取经济技术审查项内容
        //获取评分办法数据字典编码     
        String methodCode = bidMethodService.getMethod(id, p.getId());
        if (methodCode != null && !"".equals(methodCode)) {
          if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
            FirstAudit firstAudit2 = new FirstAudit();
            firstAudit2.setPackageId(p.getId());
            firstAudit2.setProjectId(id);
            firstAudit2.setIsConfirm((short)1);
            List<FirstAudit> fas2 = firstAuditService.findBykind(firstAudit2);
            if (fas2 == null || fas2.size() <= 0) {
              msg = "noSecond";
              return "redirect:/intelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
            }
          }
          if ("OPEN_ZHPFF".equals(methodCode)) {
            ScoreModel smMap = new ScoreModel();
            smMap.setPackageId(p.getId());
            smMap.setProjectId(id);
            List<ScoreModel> sms = scoreModelService.findListByScoreModel(smMap);
            if (sms == null || sms.size() <= 0) {
              msg = "noSecond";
              return "redirect:/intelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
            }
            if (sms != null && sms.size() >0) {
              List<DictionaryData> ddList = DictionaryDataUtil.find(23);
              int checkCount = 0;
              for (DictionaryData dictionaryData : ddList) {
                MarkTerm mt = new MarkTerm();
                mt.setTypeName(dictionaryData.getId());
                mt.setProjectId(p.getProjectId());
                mt.setPackageId(p.getId());
                //默认顶级节点为0
                mt.setPid("0");
                List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
                for (MarkTerm mtKey : mtList) {
                  MarkTerm mt1 = new MarkTerm();
                  mt1.setPid(mtKey.getId());
                  mt1.setProjectId(p.getProjectId());
                  mt1.setPackageId(p.getId());
                  List<MarkTerm> mtValue = markTermService.findListByMarkTerm(mt1);
                  for (MarkTerm markTerm : mtValue) {
                    if ("1".equals(markTerm.isChecked())) {
                      checkCount ++;
                    }
                  }
                }
              }
              if (checkCount == 0 || checkCount > 1) {
                msg = "noThired";
                return "redirect:/intelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
              }
            }
          }
        } else {
          msg = "noSecond";
          return "redirect:/intelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
        }
      }
    }
    Project project = projectService.selectById(id);
    boolean exist = isExist(project.getPurchaseDepId(),user.getOrg().getId());
    model.addAttribute("exist", exist);
    //判断是否上传招标文件
    String typeId = DictionaryDataUtil.getId("PROJECT_BID");
    List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
    /**
     * 1.如果上传过 跳过生成模板
     * 2.如果 上传过 判断 file.getIsDelete标识 是否删除 如果删除只是生成 拆分部分模板
     * 3.如果没有上传 那么生成新模板
     */
    if (files != null && files.size() > 0 && project != null){
    		//调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
    	       String filePath = extUserServicel.downLoadBiddingDoc(request,id,1,null);
    	       if (StringUtils.isNotBlank(filePath)){
    	         model.addAttribute("filePath", filePath);
    	       }
    	 //调用数据存储模板
    	 model.addAttribute("fileId", files.get(0).getId());
     }else{
    	//重新生成模板
         model.addAttribute("fileId", "0");
       //调用生成word模板 传入标识1 只是生成 总模板
         String filePath = extUserServicel.downLoadBiddingDoc(request,id,0,null);
         if (StringUtils.isNotBlank(filePath)){
           model.addAttribute("filePath", filePath);
         }
     }
   	  
    
   /*// 注释 为分包之前的 业务逻辑
     if (files != null && files.size() > 0){
      model.addAttribute("fileId", files.get(0).getId());
    } else {
      if (project != null){
    	  //调用生成word模板
        String filePath = extUserServicel.downLoadBiddingDoc(request,id);
        if (StringUtils.isNotBlank(filePath)){
          model.addAttribute("filePath", filePath);
        }
      }
      model.addAttribute("fileId", "0");
    }*/
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("project", project);
    String jsonReason = project.getAuditReason();
    if (jsonReason != null && !"".equals(jsonReason)) {
        model.addAttribute("reasons", JSON.parseObject(jsonReason, Reason.class));
    }
    model.addAttribute("pStatus",DictionaryDataUtil.findById(project.getStatus()).getCode());
    model.addAttribute("ope", "add");
    model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
    model.addAttribute("typeId", DictionaryDataUtil.getId("BID_FILE_AUDIT"));
    //采购管理部门审核意见附件
    model.addAttribute("pcTypeId", DictionaryDataUtil.getId("PC_REASON"));
    //事业部门审核意见附件
    model.addAttribute("causeTypeId", DictionaryDataUtil.getId("CAUSE_REASON"));
    //财务部门审核意见附件
    model.addAttribute("financeTypeId", DictionaryDataUtil.getId("FINANCE_REASON"));
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
   * 
   *〈简述〉是否是当前项目的监管部门
   *〈详细描述〉
   * @author Wang Wenshuai
   */
  private boolean isExist(String orgId,String userOrgId){
    //拿到当前的采购机构获取到组织机构
    //添加 purchaseOrgnizationServiceI.getByPurchaseDepId 方法
    List<PurchaseOrg> list = purchaseOrgnizationServiceI.getByPurchaseDepId(orgId);
    for (PurchaseOrg purchaseOrg : list) {
      if(userOrgId.equals(purchaseOrg.getOrgId())){
        return true;
      }
    }
    return false;

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
    if (project != null) {
        DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPlanType());
        if (dictionaryData != null) {
          model.addAttribute("rootCode", dictionaryData.getCode());
        }
    }
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
      if(user.getPublishType() == null || user.getPublishType() == 0){
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
        //查询关联品目
        articelService.getArticleCategory(articles.get(0).getId(), model);
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
        //查询关联品目
        articelService.getArticleCategory(articles.get(0).getId(), model);
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
      //查询关联品目
      articelService.getArticleCategory(articleId, model);
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
    Project project = projectService.selectById(projectId);
    if (project != null) {
        DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPlanType());
        if (dictionaryData != null) {
          model.addAttribute("rootCode", dictionaryData.getCode());
        }
    }
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
   * Description: 保存公告
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
        String categoryIds = request.getParameter("categoryIds");
        articelService.saveArtCategory(article.getId(), categoryIds);
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
          
          //更新项目状态
          String noticeType = request.getParameter("noticeType");
          //如果是拟制采购公告，更新项目状态为采购公告编制
          if ("purchase".equals(noticeType)) {
            Project project = projectService.selectById(article.getProjectId());
            String code = "ZBGGNZZ";
            projectService.updateStatus(project, code);
          }
          //如果是拟制中标公告，更新项目状态为中标公示编制
          if ("win".equals(noticeType)) {
            Project project = projectService.selectById(article.getProjectId());
            String code = "NZZBGG";
            projectService.updateStatus(project, code);
          }
        }
        if (flag == 1) {
          //提交
          article.setStatus(1);
          article.setUpdatedAt(new Date());
          article.setSubmitAt(new Date());
          jsonData.setMessage("提交成功");
          //更新项目状态
          String noticeType = request.getParameter("noticeType");
          if ("purchase".equals(noticeType)) {
            Project project = projectService.selectById(article.getProjectId());
            String puchaseTypeCode = DictionaryDataUtil.findById(project.getPurchaseType()).getCode();
            if ("GKZB".equals(puchaseTypeCode)) {
                //如果是公开招标更新项目状态为发售标书
                String code = "FSBSZ";
                projectService.updateStatus(project, code);
            } else if ("JZXTP".equals(puchaseTypeCode)) {
              
            } else {
                //如果是询价、邀请招标、竞争性谈判更新项目状态为抽取供应商
                String code = "GYSCQZ";
                projectService.updateStatus(project, code);
            }
          }
          if ("win".equals(noticeType)) {
            Project project = projectService.selectById(article.getProjectId());
            String puchaseTypeCode = DictionaryDataUtil.findById(project.getPurchaseType()).getCode();
            if ("JZXTP".equals(puchaseTypeCode)) {
              
            } else {
                //如果不是单一来源，项目状态为确认中标供应商
                String code = "QRZBGYS";
                projectService.updateStatus(project, code);
            }
          }
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
        //查询公告列表中是否有该项目的公告
        Article art = articelService.selectArticleById(article.getId());
        if (art != null ){
          articelService.update(article);
          //该环节设置为执行中状态
          flowMangeService.flowExe(request, flowDefineId, article.getProjectId(), 2);
        } else {
          articelService.addArticle(article);
          //该环节设置为执行中状态
          flowMangeService.flowExe(request, flowDefineId, article.getProjectId(), 1);
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
  public void saveBidFile(@CurrentUser User user,HttpServletResponse response,HttpServletRequest req, String projectId, Model model, String flowDefineId, String flag) throws IOException{
	 try{
	  String result = "保存失败";
	  if("2".equals(flag)){
		  //保存参与包的文件上传  并返回路径
		  result = uploadService.uploadNTKO(req);
		  response.setContentType("text/html;charset=utf-8");
	      response.getWriter().print(result);
	      response.getWriter().flush();
	  }else{
    //修改代办为已办
    todosService.updateIsFinish("open_bidding/bidFile.html?id=" + projectId + "&process=1");
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
        project.setAuditReason(null);
        project.setApprovalTime(new Date());
        //修改项目状态
        project.setStatus(DictionaryDataUtil.getId("ZBWJYTJ"));
        projectService.update(project);
        //推送待办
        push(user,project.getId());
        //该环节设置为执行中状态
        flowMangeService.flowExe(req, flowDefineId, projectId, 2);
      }
      //flag：0，招标文件为暂存状态
      if ("0".equals(flag)) {
        Project project = projectService.selectById(projectId);
        project.setConfirmFile(0);
        projectService.update(project);
        //该环节设置为执行中状态
        flowMangeService.flowExe(req, flowDefineId, projectId, 2);
      }
    } else {
      result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
      //flag：1，招标文件为提交状态
      if ("1".equals(flag)) {
        Project project = projectService.selectById(projectId);
        project.setConfirmFile(1);
        project.setAuditReason(null);
        project.setApprovalTime(new Date());
        //修改项目状态
        project.setStatus(DictionaryDataUtil.getId("ZBWJYTJ"));
        projectService.update(project);
        //推待办
        push(user,project.getId());
        //该环节设置为执行中状态
        flowMangeService.flowExe(req, flowDefineId, projectId, 2);
      }
      //flag：0，招标文件为暂存状态
      if ("0".equals(flag)) {
        Project project = projectService.selectById(projectId);
        project.setConfirmFile(0);
        projectService.update(project);
        //该环节设置为执行中状态
        flowMangeService.flowExe(req, flowDefineId, projectId, 2);
      }
    }
	  }
	  System.out.println(result);
	 }catch (Exception e) {
		// TODO: handle exception
		 e.printStackTrace();
	}finally{
		response.getWriter().close();
	}
  }


  /**
   * 
   *〈简述〉推送待办消息
   *〈详细描述〉
   * @author Wang Wenshuai
   */
  private void push(User user,String projectId) {
    Project selectById = projectService.selectById(projectId);
    if (selectById != null) {
      List<PurchaseOrg> list = purchaseOrgnizationServiceI.get(selectById.getPurchaseDepId());
      for (PurchaseOrg purchaseOrg : list) {
        //推送待办
        Todos todos = new Todos();
        todos.setName(selectById.getName()+"招标文件审核");
        todos.setOrgId(purchaseOrg.getOrgId());
        todos.setSenderId(user.getId());
        todos.setUndoType((short)3);
        todos.setPowerId(PropUtil.getProperty("zbwjsh"));
        todos.setUrl("open_bidding/bidFile.html?id=" + projectId + "&process=1");
        todosService.insert(todos);
      }
    }

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
  public String changtotal(String projectId, String packId, Model model, String count, String flowDefineId, HttpServletRequest req) throws ParseException{
    Quote quoteCondition = new Quote();
    quoteCondition.setProjectId(projectId);
    List<Date> listDate =  supplierQuoteService.selectQuoteCount(quoteCondition);
    if (listDate != null && listDate.size() > 0 && packId == null) {
      //如果有明细就是查看了
      return "redirect:viewChangtotal.html?projectId=" + projectId;
    }
    if (packId != null) {
      //显示第几轮次报价
      quoteCondition.setPackageId(packId);
      List<Date> listDate1 =  supplierQuoteService.selectQuoteCount(quoteCondition);
      if (listDate1 != null) {
        model.addAttribute("count", listDate1.size());
      }
    }
    //该环节设置为执行中状态
    flowMangeService.flowExe(req, flowDefineId, projectId, 2);
    //去saletender查出项目对应的所有的包
    List<Packages> packList = saleTenderService.getPackageIds(projectId);
    List<Packages> listPackage1 = new ArrayList<Packages>();
    //这里用这个是因为hashMap是无序的
    TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
    SaleTender condition = new SaleTender();
    HashMap<String, Object> map = new HashMap<String, Object>();
    HashMap<String, Object> map1 = new HashMap<String, Object>();
    if ("1".equals(count)) {
      HashMap<String, Object> map2 = new HashMap<String, Object>();
      map2.put("projectId", projectId);
      List<Packages> pl = packageService.findPackageById(map2);
      if (pl != null) {
          model.addAttribute("count1", pl.size());
      }
    }
    if (packId != null) {
      for (Packages pack : packList) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      packList = listPackage1;
    }

    for (Packages pack : packList) {
      condition.setProjectId(projectId);
      condition.setPackages(pack.getId());
      condition.setStatusBid(NUMBER_TWO);
      condition.setStatusBond(NUMBER_TWO);
      condition.setIsTurnUp(0);
      condition.setIsFirstPass(1);
      List<SaleTender> stList = saleTenderService.find(condition);
      List<SaleTender> stList1 = new ArrayList<SaleTender>();
      stList1.addAll(stList);
      for (SaleTender st1 : stList1) {
        if (st1.getIsRemoved().equals("2")) {
          stList.remove(st1);
        }
      }
      map1.put("packageId", pack.getId());
      map1.put("projectId", projectId);
      List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
      BigDecimal projectBudget = BigDecimal.ZERO;
      for (ProjectDetail projectDetail : detailList) {
        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
      }
      map.put("id", pack.getId());
      if (stList != null && stList.size() > 0) {
        treeMap.put(pack.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
      }
    }
    model.addAttribute("treeMap", treeMap);
    model.addAttribute("projectId", projectId);
    model.addAttribute("packId", packId);
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    return "bss/ppms/open_bidding/bid_file/chang_total";
  }

  @RequestMapping("/viewChangtotal")
  public String viewChangtotal(String projectId, String packId, String timestamp, Model model, HttpServletRequest req) throws ParseException{
    Project project = projectService.selectById(projectId);
    DictionaryData dictionaryData = null;
    if (project != null && project.getPurchaseType() != null ){
      dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
    }
    //去saletender查出项目对应的所有的包
    List<Packages> packList = saleTenderService.getPackageIds(projectId);
    //这里用这个是因为hashMap是无序的
    TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
    SaleTender condition = new SaleTender();
    HashMap<String, Object> map = new HashMap<String, Object>();
    HashMap<String, Object> map1 = new HashMap<String, Object>();
    Quote quote2 = new Quote();
    Quote quote3 = new Quote();
    List<Packages> listPackage1 = new ArrayList<Packages>();
    if (packId != null) {
      for (Packages pack : packList) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      packList = listPackage1;
    }
    if (packList != null && packList.size() == 1 && packId != null) {
      model.addAttribute("listLength", 1);
    }
    for (Packages pack : packList) {
      condition.setProjectId(projectId);
      condition.setPackages(pack.getId());
      condition.setStatusBid(NUMBER_TWO);
      condition.setStatusBond(NUMBER_TWO);
      condition.setIsTurnUp(0);
      List<SaleTender> stList = saleTenderService.find(condition);
      map1.put("packageId", pack.getId());
      map1.put("projectId", projectId);
      List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
      BigDecimal projectBudget = BigDecimal.ZERO;
      for (ProjectDetail projectDetail : detailList) {
        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
      }

      quote3.setProjectId(projectId);
      quote3.setPackageId(pack.getId());
      List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote3);
      List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
      if (listDate1 != null && listDate1.size() > 1) {
        //给第二次报价的数据查到
        if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
          quote2.setProjectId(projectId);
          quote2.setPackageId(pack.getId());
          if (timestamp == null) {
            quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
          } else {
            quote2.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
          }
          listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
        }
      } 

      for (SaleTender saleTender : stList) {
        Quote quote = new Quote();
        quote.setProjectId(projectId);
        quote.setPackageId(pack.getId());
        quote.setSupplierId(saleTender.getSupplierId());
        if (listDate1 != null && listDate1.size() > 0) {
          quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
        }
        if ("0".equals(saleTender.getIsRemoved())) {
          saleTender.setIsRemoved("正常");
        }
        if ("2".equals(saleTender.getIsRemoved())) {
          saleTender.setIsRemoved("放弃报价");
        }
        List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
        if (allQuote != null && allQuote.size() > 0) {
            for (Quote conditionQuote : allQuote) {
                if (conditionQuote.getSupplierId().equals(saleTender.getSuppliers().getId()) &&
                    conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                    for (Quote qp : listQuotebyPackage1) {
                        if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                            conditionQuote.setTotal(qp.getTotal());
                            conditionQuote.setQuotePrice(qp.getQuotePrice());
                            conditionQuote.setRemark(qp.getRemark());
                            conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                        }
                    }
                    saleTender.setTotal(conditionQuote.getTotal());
                    saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                    saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                    saleTender.setQuoteId(conditionQuote.getId());
                    break;
                }
            }
        }
      }
      map.put("id", pack.getId());
      if (stList != null && stList.size() > 0) {
        treeMap.put(pack.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
      }
    }
    model.addAttribute("treeMap", treeMap);
    model.addAttribute("projectId", projectId);
    model.addAttribute("dd", dictionaryData);
    return "bss/ppms/open_bidding/bid_file/view_chang_total";
  }
  
  @RequestMapping("/changTotalWord")
  public String changTotalWord(String projectId, String packId, String timestamp, Model model, HttpServletRequest req) throws ParseException{
    Project project = projectService.selectById(projectId);
    DictionaryData dictionaryData = null;
    if (project != null && project.getPurchaseType() != null ){
      dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
    }
    //去saletender查出项目对应的所有的包
    List<Packages> packList = saleTenderService.getPackageIds(projectId);
    //这里用这个是因为hashMap是无序的
    TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
    SaleTender condition = new SaleTender();
    HashMap<String, Object> map = new HashMap<String, Object>();
    HashMap<String, Object> map1 = new HashMap<String, Object>();
    Quote quote2 = new Quote();
    Quote quote3 = new Quote();
    List<Packages> listPackage1 = new ArrayList<Packages>();
    if (packId != null) {
      for (Packages pack : packList) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      packList = listPackage1;
    }
    if (packList != null && packList.size() == 1 && packId != null) {
      model.addAttribute("listLength", 1);
    }
    for (Packages pack : packList) {
      condition.setProjectId(projectId);
      condition.setPackages(pack.getId());
      condition.setStatusBid(NUMBER_TWO);
      condition.setStatusBond(NUMBER_TWO);
      condition.setIsTurnUp(0);
      List<SaleTender> stList = saleTenderService.find(condition);
      map1.put("packageId", pack.getId());
      map1.put("projectId", projectId);
      List<ProjectDetail> detailList = detailService.selectByCondition(map1, null);
      BigDecimal projectBudget = BigDecimal.ZERO;
      for (ProjectDetail projectDetail : detailList) {
        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
      }

      quote3.setProjectId(projectId);
      quote3.setPackageId(pack.getId());
      List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote3);
      List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
      if (listDate1 != null && listDate1.size() > 1) {
        //给第二次报价的数据查到
        if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
          quote2.setProjectId(projectId);
          quote2.setPackageId(pack.getId());
          if (timestamp == null) {
            quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
          } else {
            quote2.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
          }
          listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
        }
      } 

      for (SaleTender saleTender : stList) {
        Quote quote = new Quote();
        quote.setProjectId(projectId);
        quote.setPackageId(pack.getId());
        quote.setSupplierId(saleTender.getSupplierId());
        if (listDate1 != null && listDate1.size() > 0) {
          quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
        }
        if ("0".equals(saleTender.getIsRemoved())) {
          saleTender.setIsRemoved("正常");
        }
        if ("2".equals(saleTender.getIsRemoved())) {
          saleTender.setIsRemoved("放弃报价");
        }
        List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
        if (allQuote != null && allQuote.size() > 0) {
            for (Quote conditionQuote : allQuote) {
                if (conditionQuote.getSupplierId().equals(saleTender.getSuppliers().getId()) &&
                    conditionQuote.getProjectId().equals(saleTender.getProject().getId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                    for (Quote qp : listQuotebyPackage1) {
                        if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                            conditionQuote.setTotal(qp.getTotal());
                            conditionQuote.setQuotePrice(qp.getQuotePrice());
                            conditionQuote.setRemark(qp.getRemark());
                            conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                        }
                    }
                    saleTender.setTotal(conditionQuote.getTotal());
                    saleTender.setDeliveryTime(conditionQuote.getDeliveryTime());
                    saleTender.setIsTurnUp(conditionQuote.getIsTurnUp());
                    saleTender.setQuoteId(conditionQuote.getId());
                    break;
                }
            }
        }
      }
      map.put("id", pack.getId());
      if (stList != null && stList.size() > 0) {
        treeMap.put(pack.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
      }
    }
    model.addAttribute("treeMap", treeMap);
    model.addAttribute("projectId", projectId);
    model.addAttribute("project", project);
    model.addAttribute("dd", dictionaryData);
    HashMap<String, Object> mapId = new HashMap<String, Object>();
    mapId.put("projectId", projectId);
    mapId.put("id", packId);
    List<Packages> pack = packageService.findPackageById(mapId);
    if (pack != null && pack.size() > 0) {
      model.addAttribute("pack", pack.get(0));
    }
    return "bss/ppms/open_bidding/bid_file/view_chang_total_word";
  }

  @RequestMapping("/viewChangtotalByPackId")
  public String viewChangtotalByPackId(String projectId, String packId, String timestamp, Model model, HttpServletRequest req) throws ParseException{
    Packages pack = packageService.selectByPrimaryKeyId(packId);
    TreeMap<String, List<SaleTender>> treeMap = new TreeMap<String, List<SaleTender>>();
    SaleTender condition = new SaleTender();
    HashMap<String, Object> map = new HashMap<String, Object>();
    condition.setProjectId(projectId);
    condition.setPackages(pack.getId());
    condition.setStatusBid(NUMBER_TWO);
    condition.setStatusBond(NUMBER_TWO);
    condition.setIsTurnUp(0);
    List<SaleTender> stList = saleTenderService.find(condition);
    map.put("packageId", pack.getId());
    map.put("projectId", projectId);
    List<ProjectDetail> detailList = detailService.selectByCondition(map, null);
    BigDecimal projectBudget = BigDecimal.ZERO;
    for (ProjectDetail projectDetail : detailList) {
      projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
    }
    Quote quote = new Quote();
    quote.setProjectId(projectId);
    quote.setPackageId(pack.getId());
    quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
    List<Quote> listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote);
    for (SaleTender saleTender : stList) {
      for (Quote qp : listQuotebyPackage) {
        if (qp.getSupplierId().equals(saleTender.getSuppliers().getId())) {
          saleTender.setTotal(qp.getTotal());
          saleTender.setDeliveryTime(qp.getDeliveryTime());
          saleTender.setQuoteId(qp.getId());
          saleTender.setRemovedReason(qp.getGiveUpReason());
          if (qp.getIsRemove() == null) {
            saleTender.setIsRemoved("正常");
          } else {
            saleTender.setIsRemoved("放弃报价");
          }  
        }
      }
    }
    if (stList != null && stList.size() > 0) {
      treeMap.put(pack.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
    }
    model.addAttribute("treeMap", treeMap);
    model.addAttribute("projectId", projectId);
    return "bss/ppms/open_bidding/bid_file/view_chang_total_by_packId";
  }



  @RequestMapping("/changbiao")
  public String chooseChangBiaoType(String projectId, String flowDefineId, Model model) {
      Project project = projectService.selectById(projectId);
      
      //开标时间
      long bidDate = 0;
      if (project.getBidDate() != null) {
          bidDate = project.getBidDate().getTime();
      }
      long nowDate = new Date().getTime();
      long date = bidDate - nowDate;
      model.addAttribute("date", date);
      model.addAttribute("project", project);
      model.addAttribute("flowDefineId", flowDefineId);

      if (date < 0) {
          //去saletender查出项目对应的所有的包
          List<Packages> packList = saleTenderService.getPackageIds(projectId);
          if (packList != null && packList.size() > 0) {
              SaleTender condition = new SaleTender();
              condition.setProjectId(projectId);
              condition.setPackages(packList.get(0).getId());
              condition.setStatusBid(NUMBER_TWO);
              condition.setStatusBond(NUMBER_TWO);
              condition.setIsTurnUp(0);
              List<SaleTender> stList = saleTenderService.find(condition);
              if (stList != null && stList.size() > 0) {
                  Quote quote = new Quote();
                  quote.setProjectId(projectId);
                  quote.setPackageId(packList.get(0).getId());
                  quote.setSupplierId(stList.get(0).getSupplierId());
                  List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                  if (allQuote != null && allQuote.size() > 0) {
                      if (allQuote.get(0).getQuotePrice() == null) {
                          return "redirect:changtotal.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
                      } else {
                          return "redirect:changmingxi.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
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
      List<Packages> packList = saleTenderService.getPackageIds(projectId);
      if (packList != null && packList.size() > 0) {
        SaleTender condition = new SaleTender();
        condition.setProjectId(projectId);
        condition.setPackages(packList.get(0).getId());
        condition.setStatusBid(NUMBER_TWO);
        condition.setStatusBond(NUMBER_TWO);
        condition.setIsTurnUp(0);
        List<SaleTender> stList = saleTenderService.find(condition);
        if (stList != null && stList.size() > 0) {
          Quote quote = new Quote();
          quote.setProjectId(projectId);
          quote.setPackageId(packList.get(0).getId());
          quote.setSupplierId(stList.get(0).getSupplierId());
          List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
          if (allQuote != null && allQuote.size() > 0) {
            if (allQuote.get(0).getQuotePrice() == null) {
              return "redirect:changtotal.html?projectId=" + projectId;
            } else {
              return "redirect:changmingxi.html?projectId=" + projectId;
            }
          }
        }
      }
    }
    return "bss/ppms/open_bidding/bid_file/new_window";
  }

  @RequestMapping("/save")
  @ResponseBody
  public String save(HttpServletRequest request, String quoteList) throws Exception{
    List<Quote> quoteLists = new ArrayList<Quote>();
    JSONArray json=JSONArray.fromObject(quoteList);
    JSONObject jsonQuote = new JSONObject();
    for (int i = 0; i < json.size(); i++) {
      Quote quote = new Quote();
      jsonQuote = json.getJSONObject(i); 
      quote.setSupplierId(jsonQuote.getString("supplierId"));
      if (!"".equals(jsonQuote.getString("total"))) {
        quote.setTotal(new BigDecimal(jsonQuote.getString("total")));
      }
      quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonQuote.getString("date")).getTime()));
      quote.setPackageId(jsonQuote.getString("packageId"));
      quote.setProjectId(jsonQuote.getString("projectId"));
      quote.setDeliveryTime(jsonQuote.getString("deliveryTime"));
      if (!"".equals(jsonQuote.opt("isGiveUp")) && jsonQuote.opt("isGiveUp") != null ) {
        quote.setIsRemove(Integer.parseInt(jsonQuote.getString("isGiveUp")));
        //放弃报价需要修改saletender这个表的isRemoved这个字段为2
        SaleTender condition = new SaleTender();
        condition.setProjectId(quote.getProjectId());
        condition.setPackages(quote.getPackageId());
        condition.setSupplierId(quote.getSupplierId());
        condition.setStatusBid(NUMBER_TWO);
        condition.setStatusBond(NUMBER_TWO);
        condition.setIsTurnUp(0);
        List<SaleTender> stList = saleTenderService.find(condition);
        if (stList != null && stList.size() > 0) {
          stList.get(0).setIsRemoved("2");
          saleTenderService.update(stList.get(0));
        }
      }
      if (jsonQuote.opt("auditReason") != null) {
        quote.setGiveUpReason(jsonQuote.getString("auditReason"));
      }
      quoteLists.add(quote);
      
      //查是否是单一来源的项目
      Project project = projectService.selectById(quote.getProjectId());
      if(project != null){
        DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
        if("DYLY".equals(findById.getCode())){
          SupplierCheckPass pass = new SupplierCheckPass();
          pass.setPackageId(quote.getPackageId());
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
          record.setPackageId(quote.getPackageId());
          record.setProjectId(quote.getProjectId());
          record.setSupplierId(jsonQuote.getString("supplierId"));
          record.setTotalPrice(quote.getTotal());
          record.setRanking(1);
          record.setIsWonBid((short)0);
          supplierCheckPassService.insert(record);
        }
      }
      
    }
    supplierQuoteService.insert(quoteLists); 
    //该环节设置为执行完毕
    flowMangeService.flowExe(request, jsonQuote.getString("flowDefineId"), jsonQuote.getString("projectId"), 1);
    return "true";
  }


  @ResponseBody
  @RequestMapping("/isTurnUp")
  public String isTurnUp(String projectId, String isTurnUp) throws ParseException{
    DictionaryData dd = new DictionaryData();
    dd.setCode("OPEN_FILE");
    List<DictionaryData > list = dictionaryDataServiceI.find(dd);
    //查出项目的所有包、然后全部修改状态
    SaleTender condition = new SaleTender();
    condition.setProjectId(projectId);
    condition.setStatusBid(NUMBER_TWO);
    condition.setStatusBond(NUMBER_TWO);
    List<SaleTender> stList = saleTenderService.find(condition);
    List<String> strList = new ArrayList<String>();
    JSONArray json=JSONArray.fromObject(isTurnUp);
    JSONObject jsonQuote = new JSONObject();
    int count = 0;
    //这里写两遍是因为文件上传用的是saletender的id，上传的是供应商的投标文件，但是saletender里面有可能有两个供应商在不同的包下面，所以判断文件上传就会有点麻烦，因为供应商相同的saletender数据有多条，但是只是其中一个有值
    for (int i = 0; i < json.size(); i++) {
      jsonQuote = json.getJSONObject(i); 
      for (SaleTender st : stList) {
          if (st.getSuppliers().getId().equals(jsonQuote.getString("supplierId"))) {
              if (list != null && list.size() > 0) {
                  List<UploadFile> blist1 = uploadService.getFilesOther(st.getId(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
                  if (blist1 != null && blist1.size() > 0) {
                      strList.add(st.getSuppliers().getId());
                  }
              }
              st.setIsTurnUp(Integer.parseInt(jsonQuote.getString("isTurnUp")));
          }
      }
    }
    for (int i = 0; i < json.size(); i++) {
        jsonQuote = json.getJSONObject(i); 
        for (SaleTender st : stList) {
            if (st.getSuppliers().getId().equals(jsonQuote.getString("supplierId"))) {
                if (list != null && list.size() > 0) {
                   /* List<UploadFile> blist1 = uploadService.getFilesOther(st.getId(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
                    if (blist1 != null && blist1.size() == 0) {
                        if (!strList.contains(st.getSuppliers().getId()) && Integer.parseInt(jsonQuote.getString("isTurnUp")) == 0) {
                          count ++ ;
                          break labe;
                        }
                    }*/
                }
                st.setIsTurnUp(Integer.parseInt(jsonQuote.getString("isTurnUp")));
            }
        }
      }
    
    /*if (count > 0) {
      return "false";
    } else {*/
      //批量更新、项目所有的包
      saleTenderService.batchUpdate(stList);
      return "true";
    //}
  }

  @ResponseBody
  @RequestMapping("/checkIsQuote")
  public String checkIsQuote(String projectId) throws ParseException{
    String flag = "";
    Quote quoteByType = new Quote();
    quoteByType.setProjectId(projectId);
    List<Quote> listQuote=supplierQuoteService.getAllQuote(quoteByType, 1);
    if (listQuote != null && listQuote.size() == 0) {
      flag = "0";
    } else if (listQuote.get(0).getQuotePrice() == null) {
      flag = "1";
    } else {
      flag = "2";
    }
    return flag;
  }


  @RequestMapping("/selectSupplierByProject")
  public String selectSupplierByProject(String projectId, Model model) throws ParseException{
    //文件上传类型
    boolean flag = false;
    DictionaryData dd = new DictionaryData();
    dd.setCode("OPEN_FILE");
    List<DictionaryData > list = dictionaryDataServiceI.find(dd);
    model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
    if (list.size() > 0){
      model.addAttribute("typeId", list.get(0).getId());
    }
    List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
    SaleTender condition = new SaleTender();
    condition.setProjectId(projectId);
    condition.setStatusBid(NUMBER_TWO);
    condition.setStatusBond(NUMBER_TWO);
    StringBuilder sb = new StringBuilder("");
    for (Supplier supplier : listSupplier) {
      condition.setSupplierId(supplier.getId());
      List<SaleTender> stList = saleTenderService.find(condition);
      for (SaleTender st : stList) {
        Packages pack = packageService.selectByPrimaryKeyId(st.getPackages());
        if (pack != null) {
          sb.append(pack.getName());
        }
      }
      supplier.setPackageName(sb.toString());
      sb.delete( 0, sb.length() );
      if (stList != null && stList.size() > 0) {
        if (stList.get(0).getIsTurnUp() != null) {
          supplier.setIsturnUp(stList.get(0).getIsTurnUp().toString());
          flag = true;
        }
      }
    }

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
      groupUploadId ="flUpload" +  groupUpload.toString().substring(0, groupUpload.toString().length()-1);
    }
    if (!"".equals(groupShow.toString())) {
      groupShowId ="flshow" +  groupShow.toString().substring(0, groupShow.toString().length()-1);
    }
    SaleTender condition1 = new SaleTender();
    condition1.setProjectId(projectId);
    condition1.setStatusBid(NUMBER_TWO);
    condition1.setStatusBond(NUMBER_TWO);
    //这里是批量上传全局唯一.调用的upload.js也是改了的
    List<UploadFile> blist1 = uploadService.getFilesOther("1234567890-1234567890-1234567890", list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
    for (Supplier supplier : listSupplier) {
      supplier.setGroupsUploadId(groupUploadId);
      supplier.setGroupShowId(groupShowId);
      List<UploadFile> blist = uploadService.getFilesOther(supplier.getProSupFile(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
      //则为批量上传
      if (blist != null && blist.size() == 0) {
          if (blist1 != null && blist1.size() > 0) {
              for (UploadFile up : blist1) {
                  if (up.getName().substring(0, up.getName().lastIndexOf(".")).equals(supplier.getSupplierName())) {
                      condition1.setSupplierId(supplier.getId());
                      List<SaleTender> stList = saleTenderService.find(condition1);
                      for (SaleTender st : stList) {
                          if (st.getSuppliers().getId().equals(supplier.getId())) {
                              up.setBusinessId(st.getId());
                              uploadService.updateFile(up, Constant.SUPPLIER_SYS_KEY);
                          }
                      }
                  }
              }
          }
      }
      blist = uploadService.getFilesOther(supplier.getProSupFile(), list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
      if (blist != null && blist.size() >0) {
        supplier.setBidFileName(blist.get(0).getName());
        supplier.setBidFileId(blist.get(0).getId());
      }
    }
    blist1 = uploadService.getFilesOther("1234567890-1234567890-1234567890", list.get(0).getId(),  Constant.SUPPLIER_SYS_KEY.toString());
    for (UploadFile uploadFile : blist1) {
      uploadFile.setIsDelete(1);
      uploadService.updateFile(uploadFile, Constant.SUPPLIER_SYS_KEY);
    }
    model.addAttribute("supplierList", listSupplier);
    model.addAttribute("projectId", projectId);
    model.addAttribute("flag", flag);
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
  public String changmingxi(String projectId, String packId, Model model, String count, String flowDefineId, HttpServletRequest req) throws ParseException{
    Quote condition = new Quote();
    condition.setProjectId(projectId);
    List<Date> listDate =  supplierQuoteService.selectQuoteCount(condition);
    //packId代再次报价
    if (listDate != null && listDate.size() > 0  && packId == null) {
        //如果有明细就是查看了
      return "redirect:viewMingxi.html?projectId=" + projectId;
    }
    if (listDate != null && listDate.size() > 0) {
        model.addAttribute("listDate", listDate.size());
    }
    //该环节设置为执行中状态
    flowMangeService.flowExe(req, flowDefineId, projectId, 2);
    Quote quote2 = new Quote();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", projectId);
    SaleTender st = new SaleTender();
    st.setProjectId(projectId);
    StringBuilder sb = new StringBuilder("");
    List<SaleTender> saleTenderList = saleTenderService.find(st);
    for (SaleTender saleTender : saleTenderList) {
      sb.append(saleTender.getPackages());
    }
    List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map, null);
    List<Packages> listPackage = new ArrayList<Packages>();
    List<Packages> listPackage1 = new ArrayList<Packages>();
    for (Packages packages : listPack) {
      if (sb.toString().indexOf(packages.getId()) != -1) {
        listPackage.add(packages);
      }
    }
    if ("1".equals(count)) {
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("projectId", projectId);
        List<Packages> pl = packageService.findPackageById(map2);
        if (pl != null) {
            model.addAttribute("count", pl.size());
        }
      
    }
    if (packId != null) {
      for (Packages pack : listPackage) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      listPackage = listPackage1;
    }
    //开始循环包
    for (Packages pk:listPackage) {
      map.put("packageId", pk.getId());
      quote2.setProjectId(projectId);
      quote2.setPackageId(pk.getId());
      List<Supplier> suList = new ArrayList<Supplier>();
      List<ProjectDetail> detailList = detailService.selectByCondition(map, null);
      BigDecimal projectBudget = BigDecimal.ZERO;
      for (ProjectDetail projectDetail : detailList) {
        projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
      }
      pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
      for (SaleTender saleTender : saleTenderList) {
        if (saleTender.getPackages().indexOf(pk.getId()) != -1 && saleTender.getIsTurnUp() != null && saleTender.getIsTurnUp() == 0) {
          Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
          supplier.setPdList(detailList);
          Supplier supplierNew = new Supplier();
          supplierNew.setSupplierName(supplier.getSupplierName());
          supplierNew.setId(supplier.getId());
          supplierNew.setPdList(detailList);
          suList.add(supplierNew);
        }
      }
      pk.setSuList(suList);
    }
    model.addAttribute("listPackage", listPackage);
    model.addAttribute("projectId", projectId);
    model.addAttribute("packId", packId);
    model.addAttribute("flowDefineId", flowDefineId);
    return "bss/ppms/open_bidding/bid_file/changbiao";
  }

  @RequestMapping("/changmingxiWord")
  public String changmingxiWord(String projectId, String packId, String timestamp, Model model ,HttpServletRequest req) throws ParseException{
    Project project = projectService.selectById(projectId);
    DictionaryData dd = null;
    if (project != null && project.getPurchaseType() != null ){
      dd = DictionaryDataUtil.findById(project.getPurchaseType());
    }
    Quote quote2 = new Quote();
    Quote quote1 = new Quote();
    Quote quote=new Quote();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", projectId);
    SaleTender st = new SaleTender();
    st.setProjectId(projectId);
    StringBuilder sb = new StringBuilder("");
    List<SaleTender> saleTenderList = saleTenderService.find(st);
    for (SaleTender saleTender : saleTenderList) {
      sb.append(saleTender.getPackages());
    }
    List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map, null);
    List<Packages> listPackage = new ArrayList<Packages>();
    List<Packages> listPackage1 = new ArrayList<Packages>();
    for (Packages packages : listPack) {
      if (sb.toString().indexOf(packages.getId()) != -1) {
        listPackage.add(packages);
      }
    }
    if (packId != null) {
      for (Packages pack : listPackage) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      listPackage = listPackage1;
    }
    //开始循环包
    for (Packages pk:listPackage) {
      map.put("packageId", pk.getId());
      quote2.setProjectId(projectId);
      quote2.setPackageId(pk.getId());
      List<Date> listDate =  supplierQuoteService.selectQuoteCount(quote2);
      List<Quote> listQuotebyPackage = new ArrayList<Quote>();
      if (listDate != null && listDate.size() > 1) {
        if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
          quote1.setProjectId(projectId);
          quote1.setPackageId(pk.getId());
          if (timestamp == null) {
            quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
          } else {
            quote1.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
          }
          listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
        } 
      }
      quote.setProjectId(projectId);
      if (listDate != null && listDate.size() > 0) {
        quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
      }
      quote.setPackageId(pk.getId());
      List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
      List<Supplier> suList = setField(listQuote);
      //每个包有几个供应商
      List<Supplier> suListNew = new ArrayList<Supplier>();
      for (Supplier supplier : suList) {
          Supplier sup = new Supplier();
          sup.setId(supplier.getId());
          sup.setSupplierName(supplier.getSupplierName());
          sup.setQuoteList(supplier.getQuoteList());
          suListNew.add(sup);
      }
      //每个包有几个供应商
      pk.setSuList(suListNew);
      BigDecimal projectBudget = BigDecimal.ZERO;
      if (pk.getSuList() != null && pk.getSuList().size() > 0) {
        for (Quote q : pk.getSuList().get(0).getQuoteList()) {
          if (q.getProjectDetail() != null) {
            projectBudget = projectBudget.add(new BigDecimal(q.getProjectDetail().getBudget()));
          }
        }
      }
      //项目预算
      pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
      setNewQuote(listQuote, listQuotebyPackage);
    }
    model.addAttribute("listPackage", listPackage);
    model.addAttribute("projectId", projectId);
    model.addAttribute("project", project);
    HashMap<String, Object> mapId = new HashMap<String, Object>();
    mapId.put("projectId", projectId);
    mapId.put("id", packId);
    List<Packages> pack = packageService.findPackageById(mapId);
    if (pack != null && pack.size() > 0) {
      model.addAttribute("pack", pack.get(0));
    }
    return "bss/ppms/open_bidding/bid_file/view_changbiao_word";
  }
  
  @RequestMapping("/viewMingxi")
  public String viewMingxi(String projectId, String packId, String timestamp, Model model ,HttpServletRequest req) throws ParseException{
    Project project = projectService.selectById(projectId);
    DictionaryData dd = null;
    if (project != null && project.getPurchaseType() != null ){
      dd = DictionaryDataUtil.findById(project.getPurchaseType());
    }
    
    Quote quote2 = new Quote();
    Quote quote1 = new Quote();
    Quote quote=new Quote();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", projectId);
    SaleTender st = new SaleTender();
    st.setProjectId(projectId);
    StringBuilder sb = new StringBuilder("");
    List<SaleTender> saleTenderList = saleTenderService.find(st);
    for (SaleTender saleTender : saleTenderList) {
      sb.append(saleTender.getPackages());
    }
    List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map, null);
    List<Packages> listPackage = new ArrayList<Packages>();
    List<Packages> listPackage1 = new ArrayList<Packages>();
    for (Packages packages : listPack) {
      if (sb.toString().indexOf(packages.getId()) != -1) {
        listPackage.add(packages);
      }
    }
    if (packId != null) {
      for (Packages pack : listPackage) {
        if (pack.getId().equals(packId)) {
          listPackage1.add(pack);
        }
      }
      listPackage = listPackage1;
    }
    //开始循环包
    for (Packages pk:listPackage) {
      map.put("packageId", pk.getId());
      quote2.setProjectId(projectId);
      quote2.setPackageId(pk.getId());
      List<Date> listDate =  supplierQuoteService.selectQuoteCount(quote2);
      List<Quote> listQuotebyPackage = new ArrayList<Quote>();
      if (listDate != null && listDate.size() > 1) {
        if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
          quote1.setProjectId(projectId);
          quote1.setPackageId(pk.getId());
          if (timestamp == null) {
            quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
          } else {
            quote1.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
          }
          listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
        } 
      }
      quote.setProjectId(projectId);
      if (listDate != null && listDate.size() > 0) {
        quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
      }
      quote.setPackageId(pk.getId());
      List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
      List<Supplier> suList = setField(listQuote);
      //每个包有几个供应商
      List<Supplier> suListNew = new ArrayList<Supplier>();
      for (Supplier supplier : suList) {
          Supplier sup = new Supplier();
          sup.setId(supplier.getId());
          sup.setSupplierName(supplier.getSupplierName());
          sup.setQuoteList(supplier.getQuoteList());
          suListNew.add(sup);
      }
      //每个包有几个供应商
      pk.setSuList(suListNew);
      BigDecimal projectBudget = BigDecimal.ZERO;
      if (pk.getSuList() != null && pk.getSuList().size() > 0) {
        for (Quote q : pk.getSuList().get(0).getQuoteList()) {
          if (q.getProjectDetail() != null) {
            projectBudget = projectBudget.add(new BigDecimal(q.getProjectDetail().getBudget()));
          }
        }
      }
      //项目预算
      pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
      setNewQuote(listQuote, listQuotebyPackage);
    }
    model.addAttribute("listPackage", listPackage);
    model.addAttribute("projectId", projectId);
    return "bss/ppms/open_bidding/bid_file/view_changbiao";
  }

  //给每个包的供应商，和物资明细赋值
  public List<Supplier> setField(List<Quote> listQuote) {
    List<Supplier> suList1 = new ArrayList<Supplier>();
    List<Supplier> suList2 = new ArrayList<Supplier>();
    for (Quote quoteSupplier : listQuote) {
      if (suList1.size() > 0) {
        suList1.add(quoteSupplier.getSupplier());
      } else {
        suList1.add(quoteSupplier.getSupplier());
      }
    }
    //去重
    for (int i = 0; i < suList1.size(); i++) {
      if (i == 0) {
        suList2.add(suList1.get(i));
      } else {
        if (!suList2.contains(suList1.get(i))) {
          suList2.add(suList1.get(i));
        }
      }
    }

    for (Supplier sup : suList2) {
      List<Quote> quoteList = new ArrayList<Quote>();
      for (Quote quote1 : listQuote) {
        if (quote1.getSupplier().getId().equals(sup.getId())) {
          if (quoteList.size() > 0) {
            for (Quote quote2 : quoteList) {
              if (quote2.getProjectDetail().getId().equals(quote1.getProjectDetail().getId())) {
                continue;
              } else {
                quoteList.add(quote1);
                break;
              }
            }
          } else {
            quoteList.add(quote1);
          }
        }
      }
      //每个供应商的明细
      sup.setQuoteList(quoteList);
    }
    return suList2;
  }

//给最新的报价赋值
  public void setNewQuote(List<Quote> listQuote, List<Quote> listQuotebyPackage) {
    for (Quote q : listQuote) {
      for (Quote qp : listQuotebyPackage) {
        if (qp.getPackageId().equals(q.getPackageId()) && qp.getSupplierId().equals(q.getSupplierId()) && qp.getProductId().equals(q.getProductId())) {
          q.setTotal(qp.getTotal());
          q.setQuotePrice(qp.getQuotePrice());
          q.setRemark(qp.getRemark());
          q.setDeliveryTime(qp.getDeliveryTime());
        }
      }
    }
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
  @ResponseBody
  @RequestMapping(value = "/savemingxi")
  public String savemingxi(HttpServletRequest req, Quote quote, Model model, String priceStr, String flowDefineId, String packId, String quoteList) throws Exception {
    // List<String> listBd = Arrays.asList(priceStr.split(","));
    User user = (User) req.getSession().getAttribute("loginUser");
    List<Quote> listQuote = new ArrayList<Quote>();
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", quote.getProjectId());
    List<Packages> listPack = supplierQuoteService.selectByPrimaryKey(map, null);
    //
    //该环节设置为执行完毕
    flowMangeService.flowExe(req, flowDefineId, quote.getProjectId(), 1);
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
      JSONArray json=JSONArray.fromObject(quoteList);
      JSONObject jsonQuote = new JSONObject();
      for (int i = 0; i < count2; i++) {
        jsonQuote = json.getJSONObject(count); 
        count ++ ;
        Quote quoteInsert = new Quote();
        quoteInsert.setProjectId(quote.getProjectId());
        quoteInsert.setSupplierId(jsonQuote.getString("supplierId"));
        quoteInsert.setPackageId(pk.getId());
        quoteInsert.setProductId(jsonQuote.getString("productId"));
        quoteInsert.setQuotePrice(new BigDecimal(jsonQuote.getString("price")));
        quoteInsert.setTotal(new BigDecimal(jsonQuote.getString("total")));
        if (!jsonQuote.getString("remark").equals("null")) {
          quoteInsert.setRemark(jsonQuote.getString("remark"));
        }
        quoteInsert.setCreatedAt(timestamp);
        quoteInsert.setDeliveryTime(jsonQuote.getString("deliveryTime"));
        listQuote.add(quoteInsert);
      }
      int bud = 0;
      //List<Quote> list = (List) JSONArray.toCollection(json, Quote.class); 
      for (Quote quote2 : listQuote) {
          bud += quote2.getTotal().intValue();
      }
      
      
      Project project = projectService.selectById(quote.getProjectId());
      if(project != null){
        DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
        if("DYLY".equals(findById.getCode())){
          SupplierCheckPass pass = new SupplierCheckPass();
          pass.setPackageId(pk.getId());
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
          record.setPackageId(pk.getId());
          record.setProjectId(quote.getProjectId());
          record.setSupplierId(jsonQuote.getString("supplierId"));
          record.setTotalPrice(new BigDecimal(bud));
          record.setRanking(1);
          record.setIsWonBid((short)0);
          SupplierCheckPass checkPass = new SupplierCheckPass();
          checkPass.setPackageId(pk.getId());
          supplierCheckPassService.insert(record);
        }
      }
    }
    
    try {
      supplierQuoteService.insert(listQuote);
      //修改状态
      SaleTender saleTender = new SaleTender();
      saleTender.setProjectId(quote.getProjectId());
      saleTender.setSupplierId(user.getTypeId());
      List<SaleTender> sts = saleTenderService.find(saleTender);
      if (sts != null && sts.size() > 0) {
        SaleTender std = sts.get(0);
        std.setBidFinish((short) 3);
        saleTenderService.update(std);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "true";
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
    String fileName = createWordMethods(project, reviewTime, reviewSite, supperName, finalOffer, talks, expertSigneds, request);
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
        //查询关联品目
        articelService.getArticleCategory(articles.get(0).getId(), model);
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
        //查询关联品目
        articelService.getArticleCategory(articles.get(0).getId(), model);
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
      //查询关联品目
      articelService.getArticleCategory(articleId, model);
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

  @SuppressWarnings("unused")
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
      String table = getContent(projectId) == null || "".equals(getContent(projectId)) ? "" : getContent(projectId);
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
        //采购机构信息
        pd = purChaseDepOrgService.findByOrgId(project.getPurchaseDepId());
        purchaserName = pd.getName();
      }
      StringBuilder builder = new StringBuilder();
      if (p != null) {
        List<SaleTender> selectListByProjectId = saleTenderService.selectListByProjectId(p.getId());
        for (SaleTender saleTender : selectListByProjectId) {
          Packages packages = packageService.selectByPrimaryKeyId(saleTender.getPackages());
          builder.append("<p>"+packages.getName()+"供应商名称:</p>");
          Supplier supplier = null;
          if(saleTender.getSuppliers() != null){
            supplier = supplierService.get(saleTender.getSuppliers().getId());
          }
          if(supplier != null){
            builder.append("<p>&nbsp;&nbsp;"+supplier.getSupplierName()+"</p>");
          }

        }

      }
      String contact = "";
      String contactTelephone = "";
      String contactAddress = "";
      String fax = "";
      String bank = "";//开户银行
      String bidDate = ""; //开标时间
      String accountName = "";//开户名称
      String unitPostCode = "";//邮政编码
      String bankAccount = "";//银行账号
      if (p.getBidDate() != null) {
        bidDate = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(p.getBidDate());
      }
      if (pd != null) {
        contact = pd.getContact();
        //        purchaserName = pd.getDepName();
        contactTelephone = pd.getContactTelephone();
        contactAddress = pd.getContactAddress();
        fax = pd.getFax() == null ? "" : pd.getFax();
        bank = pd.getBank();
        accountName = pd.getAccountName() == null ?"":pd.getAccountName();
        if (pd.getUnitPostCode() != null) {
            unitPostCode = pd.getUnitPostCode().toString();
        }
        if (pd.getBankAccount() != null) {
            bankAccount = pd.getBankAccount().toString();
        }
      }
      String deadline = "";
      if(p.getDeadline() != null){
        deadline = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(p.getDeadline());
      }
      content = content.replace("projectDetail", table).replace("projectName", p.getName() == null ? "" : p.getName()).replace("projectNum", p.getProjectNumber() == null ? "" : p.getProjectNumber() ).replace("purchaseType", purchaseTypeName ==  null ? "" : purchaseTypeName).replace("deadline", deadline).replace("bidAddress", p.getBidAddress() == null ? "" : p.getBidAddress());

      if(bidDate != null){
        content = content.replace("bidDate", bidDate);
      }else{
        content = content.replace("bidDate", "");
      }
      if (contact != null) {
        content.replace("contact", contact);
      } else {
        content = content.replace("contact", "");
      }
      if (contactTelephone != null) {
        content.replace("telephone", contactTelephone);
      } else {
        content = content.replace("telephone", "");
      }
      if(purchaserName != null){
        content = content.replace("purchaserName", purchaserName);
      }else{
        content = content.replace("purchaserName", "");
      }
      if(contactAddress != null){
        content = content.replace("address", contactAddress);
      } else {
        content = content.replace("address", "");
      }
      if (bankAccount != null) {
        content = content.replace("Account", bankAccount);
      } else {
        content = content.replace("Account", "");
      }
      if (fax != null) {
        content = content.replace("fax", fax);
      } else {
        content = content.replace("fax", "");
      }
      if (bank != null) {
        content = content.replace("bank", bank);
      } else {
        content = content.replace("bank", "");
      }
      if (accountName != null) {
        content = content.replace("accountName", accountName);
      } else {
        content = content.replace("accountName", "");
      }
      if (unitPostCode != null) {
        content = content.replace("unitPostCode", unitPostCode);
      } else {
        content = content.replace("unitPostCode", "");
      }
      if (auditResult != null) {
        content = content.replace("auditResult", auditResult.toString());
      } else {
        content = content.replace("auditResult", "");
      }
      if(builder != null){
        content = content.replace("supplier", builder.toString());
      }else{
        content = content.replace("supplier","");
      }
      article1.setContent(content);
      model.addAttribute("article1", article1);
    }

  }
  
  /**
   *〈简述〉获取下一流程环节
   *〈详细描述〉
   * @author Ye MaoLin
   * @param response
   * @param request
   * @param art
   * @param flowDefineId
   * @throws Exception
   */
  @RequestMapping("/getNextFd")
  @ResponseBody
  public void getNextFd(@CurrentUser User user, HttpServletResponse response, HttpServletRequest request, String projectId, String flowDefineId) throws Exception{
      try {
          JSONObject jsonObj = projectService.getNextFlow(user, projectId, flowDefineId);
          response.getWriter().print(jsonObj.toString());
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
  }
  
  /**
   *〈简述〉提交下一环节经办人
   *〈详细描述〉
   * @author Ye MaoLin
   * @param request
   * @param response
   * @param projectId
   * @throws IOException
   */
  @RequestMapping("/updateOperator")
  @ResponseBody
  public void updateOperator (HttpServletRequest request, HttpServletResponse response, String projectId) throws IOException {
      try {
          String flowDefineId = request.getParameter("huanjieId");
          String userId = request.getParameter("principal");
          User user = userService.getUserById(userId);
          FlowExecute flowExecute = new FlowExecute();
          flowExecute.setFlowDefineId(flowDefineId);
          flowExecute.setIsDeleted(0);
          flowExecute.setProjectId(projectId);
          flowExecute.setStatus(0);
          List<FlowExecute> flowExecutes = flowMangeService.findFlowExecute(flowExecute);
          if (flowExecutes != null && flowExecutes.size() > 0) {
            FlowExecute execute = flowExecutes.get(0);
            execute.setOperatorId(userId);
            if (user != null) {
                execute.setOperatorName(user.getRelName());
            }
            flowMangeService.updateExecute(execute);
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("success", true);
            response.getWriter().print(jsonObj.toString());
          }
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
  }
  
  /**
   *〈简述〉变更当前环节经办人
   *〈详细描述〉
   * @author Ye MaoLin
   * @param request
   * @param response
   * @param currFlowDefineId
   * @param currUpdateUserId
   * @throws IOException 
   */
  @RequestMapping("/updateCurrOperator")
  @ResponseBody
  public void updateCurrOperator (@CurrentUser User currLoginUser, HttpServletRequest request, HttpServletResponse response, String currFlowDefineId, String currUpdateUserId, String projectId) throws IOException{
      try {
          JSONObject jsonObj = projectService.updateCurrOperator(currLoginUser, projectId, currFlowDefineId, currUpdateUserId);
          response.getWriter().print(jsonObj.toString());
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
  }
  
  /**
   *〈简述〉校验是否可提交
   *〈详细描述〉
   * @author Ye MaoLin
   * @param request
   * @param response
   * @param currFlowDefineId
   * @throws IOException 
   */
  @RequestMapping("/isSubmit")
  @ResponseBody
  public void isSubmit(HttpServletRequest request, HttpServletResponse response, String currFlowDefineId, String projectId) throws IOException{
      try {
          JSONObject jsonObj = projectService.isSubmit(projectId, currFlowDefineId);
          response.getWriter().print(jsonObj.toString());
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
  }
  
  /**
   *〈简述〉提交环节
   *〈详细描述〉
   * @author Ye MaoLin
   * @param request
   * @param response
   * @param currFlowDefineId
   * @throws IOException 
   */
  @RequestMapping("/submitHuanjie")
  @ResponseBody
  public void submitHuanjie(@CurrentUser User currLoginUser, HttpServletRequest request, HttpServletResponse response, String currFlowDefineId, String projectId) throws IOException{
      try {
          JSONObject jsonObj = projectService.submitHuanjie(currLoginUser, projectId, currFlowDefineId);
          response.getWriter().print(jsonObj.toString());
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }
  }
  
  
  /**
   * 
   *〈简述〉查看开标前所有流程是否完成
   *〈详细描述〉
   * @author FengTian
   * @param flowDefineId
   * @param url
   * @param projectId
   * @return
   * @throws IOException 
   */
  @RequestMapping("/getNextKb")
  @ResponseBody
  public void getNextKb(HttpServletResponse response, String flowDefineId, String projectId) throws IOException{
      try {
          JSONObject jsonObj = projectService.getNext(projectId, flowDefineId);
          response.getWriter().print(jsonObj.toString());
          response.getWriter().flush();
      } catch (Exception e) {
          e.printStackTrace();
      } finally{
          response.getWriter().close();
      }   
  }
  
  
}
