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

import com.alibaba.fastjson.JSON;

import ses.model.bms.DictionaryData;
import ses.model.bms.Templet;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.AjaxJsonData;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.TempletService;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertService;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import ses.util.WordUtil;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Negotiation;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Reason;
import bss.model.ppms.SaleTender;
import bss.model.ppms.ScoreModel;
import bss.model.ppms.SupplierCheckPass;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.NegotiationService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.ScoreModelService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.prms.FirstAuditService;
import bss.service.prms.PackageFirstAuditService;
import bss.util.PropUtil;
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
@RequestMapping("/Adopen_bidding")
public class AdOpenBiddingController {
    
    /**
     * @Fields projectService : 引用项目业务实现接口
     */
    @Autowired
    private AdvancedProjectService projectService;
    
    /**
     * @Fields articelService :  引用文章业务实现接口
     */
    @Autowired
    private ArticleService articelService;
    
    /**
     * @Fields detailService : 引用项目详细业务接口
     */
    @Autowired
    private AdvancedDetailService detailService;
    
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private SupplierExtUserServicel extUserServicel;
    
    @Autowired
    private MarkTermService markTermService;
    
    /**
     * @Fields auditService : 引用初审项业务接口
     */
    @Autowired
    private FirstAuditService auditService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private DownloadService downloadService;
    
    @Autowired FlowMangeService flowMangeService;
    
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationService;
    
    /**
     * 推送待办
     */
    @Autowired
    private TodosService todosService;
    
    /**
     * 符合性审查服务接口
     */
    @Autowired
    private FirstAuditService firstAuditService;
    
    /**
     * 评分、模型服务层
     */
    @Autowired
    private ScoreModelService scoreModelService;
    
    @Autowired
    private BidMethodService bidMethodService;
    
    @Autowired
    private ArticleTypeService articelTypeService;
    
    @Autowired
    private TempletService templetService;
    
    @Autowired
    private SupplierCheckPassService supplierCheckPassService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private PurChaseDepOrgService purChaseDepOrgService;
    
    @Autowired
    private NegotiationService negotiationService;
    
    @Autowired
    private ExpertService expertService;
    
    /** 公告类型 - 采购公告*/
    private static final String  PURCHASE_NOTICE= "purchase";
    /** 公告类型 - 中标公告*/
    private static final String  WIN_NOTICE= "win";
    
    private final static Short NUMBER_TWO = 2;
    
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
    public String bidFile(@CurrentUser User user,HttpServletRequest request, String id, Model model, HttpServletResponse response, String flowDefineId, Integer process) throws Exception{
        //类别是否是在流程中展示 process 1不在流程中  2在流程中  
        model.addAttribute("process", process);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", id);
        List<AdvancedPackages> packages = packageService.selectByAll(map);
        String msg = "";
        if (process != null && process == 1) {
          //审核页面不用校验是否完成
        } else {
          for (AdvancedPackages p : packages) {
            //判断各包符合性审查项是否编辑完成
              FirstAudit firstAudit = new FirstAudit();
              firstAudit.setProjectId(id);
              firstAudit.setPackageId(p.getId());
              firstAudit.setIsConfirm((short)0);
              List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
              if (fas == null || fas.size() <= 0) {
                msg = "noFirst";
                return "redirect:/adFirstAudit/toAdd.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
              }
              //获取资格性审查项内容
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
                    return "redirect:/adIntelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
                  }
                }
                if ("OPEN_ZHPFF".equals(methodCode)) {
                  ScoreModel smMap = new ScoreModel();
                  smMap.setPackageId(p.getId());
                  smMap.setProjectId(id);
                  List<ScoreModel> sms = scoreModelService.findListByScoreModel(smMap);
                  if (sms == null || sms.size() <= 0) {
                    msg = "noSecond";
                    return "redirect:/adIntelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
                  }
                  if (sms != null && sms.size() >0) {
                    List<DictionaryData> ddList = DictionaryDataUtil.find(23);
                    int checkCount = 0;
                    for (DictionaryData dictionaryData : ddList) {
                      MarkTerm mt = new MarkTerm();
                      mt.setTypeName(dictionaryData.getId());
                      mt.setProjectId(id);
                      mt.setPackageId(p.getId());
                      //默认顶级节点为0
                      mt.setPid("0");
                      List<MarkTerm> mtList = markTermService.findListByMarkTerm(mt);
                      for (MarkTerm mtKey : mtList) {
                        MarkTerm mt1 = new MarkTerm();
                        mt1.setPid(mtKey.getId());
                        mt1.setProjectId(id);
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
                      return "redirect:/adIntelligentScore/packageList.html?projectId="+id+"&flowDefineId="+flowDefineId+"&msg="+msg;
                    }
                  }
                }
              }
          }
        }
        AdvancedProject project = projectService.selectById(id);
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
                   String filePath = extUserServicel.downLoadBiddingDocs(request,id,1,null);
                   if (StringUtils.isNotBlank(filePath)){
                     model.addAttribute("filePath", filePath);
                   }
             //调用数据存储模板
             model.addAttribute("fileId", files.get(0).getId());
         }else{
            //重新生成模板
             model.addAttribute("fileId", "0");
           //调用生成word模板 传入标识1 只是生成 总模板
             String filePath = extUserServicel.downLoadBiddingDocs(request,id,0,null);
             if (StringUtils.isNotBlank(filePath)){
               model.addAttribute("filePath", filePath);
             }
         }
        
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
        return "bss/ppms/advanced_project/advanced_bid_file/add_file";
    }
    
    
    private boolean isExist(String orgId,String userOrgId){
        //拿到当前的采购机构获取到组织机构
        //添加 purchaseOrgnizationServiceI.getByPurchaseDepId 方法
        List<PurchaseOrg> list = purchaseOrgnizationService.getByPurchaseDepId(orgId);
        for (PurchaseOrg purchaseOrg : list) {
          if(userOrgId.equals(purchaseOrg.getOrgId())){
            return true;
          }
        }
        return false;

      }
    
    @RequestMapping("/bidFileView")
    public String bidFileView(HttpServletRequest request, String id, Model model, String flowDefineId, HttpServletResponse response){
        AdvancedProject project = projectService.selectById(id);
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
        return "bss/ppms/advanced_project/advanced_bid_file/add_file";
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
     *〈简述〉获取下一流程环节
     *〈详细描述〉
     * @author FengTian
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
     *〈简述〉变更当前环节经办人
     *〈详细描述〉
     * @author FengTian
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
     * @author FengTian
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
    * @author FengTian
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
    
    @RequestMapping("/showTime")
    @ResponseBody
    public long showTime(String projectId){
    	AdvancedProject project=projectService.selectById(projectId);
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
     *〈简述〉跳转到招标公告(采购公告)页面
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param model
     * @return
     */
    @RequestMapping("/bidNotice")
    public String bidNotice(@CurrentUser User user, String projectId, Model model, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        if (project != null) {
            DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPlanType());
            if (dictionaryData != null) {
                model.addAttribute("rootCode", dictionaryData.getCode());
            }
        }
        return makeNotice(user, projectId, PURCHASE_NOTICE, model, flowDefineId);
    }
    
    /**
     * 
     *〈公告〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param noticeType
     * @param model
     * @param flowDefineId
     * @return
     */
    public String makeNotice(User user, String projectId, String noticeType, Model model, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        Article article = new Article();
        //采购方式数据字典
        DictionaryData data = DictionaryDataUtil.findById(project.getPurchaseType());
        //如果是拟制预研采购公告
        if(StringUtils.isNotBlank(noticeType) && PURCHASE_NOTICE.equals(noticeType) && !"DYLY".equals(data.getCode())){
            //采购公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("purchase_notice"));
            ArticleType articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrlized");
            if (articleType != null) {
              article.setSecondArticleTypeId(articleType.getId());
            }
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                if (data != null) {
                    purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                }
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())) {
                //工程  
                if (data != null) {
                    purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                }
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())) {
                //服务 
                if (data != null) {
                    purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                }
            }
            getDefaultTemplate(project, data, model, PURCHASE_NOTICE);
        } else if (StringUtils.isNotBlank(noticeType) && PURCHASE_NOTICE.equals(noticeType) && "DYLY".equals(data.getCode())) {
            //单一来源公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("single_source_notice"));
            if(user.getPublishType() != null && user.getPublishType() == 1){
                //部队采购
                ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("single_source_notice_military");
                if (articleType2 != null) {
                    article.setSecondArticleTypeId(articleType2.getId());
                }
                //货物/物资
                if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                    purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())) {
                    //工程  
                    if (data != null) {
                        purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                    }
                } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())) {
                    //服务 
                    if (data != null) {
                        purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                    }
                }
            } else {
                //集中采购
                ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized");
                if (articleType2 != null) {
                    article.setSecondArticleTypeId(articleType2.getId());
                }
              //货物/物资
                if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                    purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())) {
                    //工程  
                    if (data != null) {
                        purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                    }
                } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())) {
                    //服务 
                    if (data != null) {
                        purchaseNoticeArticleType(user, data.getCode(), article, project.getPlanType());
                    }
                }
            }
            //getDefaultTemplate(project, data, model, PURCHASE_NOTICE);
        } else if (StringUtils.isNotBlank(noticeType) && WIN_NOTICE.equals(noticeType) && !"DYLY".equals(data.getCode())) {
            //中标公告
            article.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
            //集中采购
            ArticleType articleType2 = articelTypeService.selectArticleTypeByCode("success_notice_centralized");
            if (articleType2 != null) {
              article.setSecondArticleTypeId(articleType2.getId());
            }
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                if (data != null) {
                    winNoticeArticleType(data.getCode(), article, project.getPlanType());
                }
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                if (data != null) {
                    winNoticeArticleType(data.getCode(), article, project.getPlanType());
                }
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                if (data != null) {
                    winNoticeArticleType(data.getCode(), article, project.getPlanType());
                }
            }
            getDefaultTemplate(project, data, model, WIN_NOTICE);
        }
        article.setProjectId(projectId);
        //查询公告列表中是否有该项目的招标公告
        List<Article> articles = articelService.selectArticleByProjectId(article);
        //判断该项目是否已经存在该类型公告
        if(articles != null && articles.size() > 0){
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
                return "bss/ppms/advanced_project/bid_notice/view";
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
                return "bss/ppms/advanced_project/bid_notice/add";
            }
        } else {
            String articleId = WfUtil.createUUID();
            model.addAttribute("article", article);
            model.addAttribute("project", project);
            model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
            model.addAttribute("projectId", projectId);
            model.addAttribute("noticeType", noticeType);
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
            return "bss/ppms/advanced_project/bid_notice/add";
          }
    }
    
    public void getDefaultTemplate(AdvancedProject project, DictionaryData data, Model model, String type) {
        List<Templet> templets = null;
        Templet temType = temType(type, data.getCode());
        templets = templetService.search(1, temType);
        if (templets != null && templets.size() > 0) {
            String content = templets.get(0).getContent();
            Article article = new Article();
            String table = getContent(project.getId()) == null || "".equals(getContent(project.getId())) ? "" : getContent(project.getId());
            StringBuilder auditResult = new StringBuilder();
            auditResult.append("");
            //评分结果
            HashMap<String ,Object> map = new HashMap<String ,Object>();
            map.put("projectId", project.getId());
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
            if(selectByAll != null && selectByAll.size() > 0){
                for (AdvancedPackages packages : selectByAll) {
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
            }
            StringBuilder builder = new StringBuilder();
            List<SaleTender> selectListByProjectId = saleTenderService.selectListByProjectId(project.getId());
            if(selectListByProjectId != null && selectListByProjectId.size() > 0){
                for (SaleTender saleTender : selectListByProjectId) {
                    AdvancedPackages packages = packageService.selectById(saleTender.getPackages());
                    builder.append("<p>"+packages.getName()+"供应商名称:</p>");
                    if(saleTender.getSuppliers() != null){
                        Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                        if(supplier != null){
                            builder.append("<p>&nbsp;&nbsp;"+supplier.getSupplierName()+"</p>");
                        }
                    }
                }
            }
            PurchaseDep purchaseDep = purChaseDepOrgService.findByOrgId(project.getPurchaseDepId());
            content = content.replace("projectDetail", table).replace("projectName", project.getName() == null ? "" : project.getName());
            content = content.replace("projectNum", project.getProjectNumber() == null ? "" : project.getProjectNumber() );
            content = content.replace("purchaseType", DictionaryDataUtil.findById(project.getPurchaseType()).getName() ==  null ? "" : DictionaryDataUtil.findById(project.getPurchaseType()).getName());
            content = content.replace("deadline", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(project.getDeadline())).replace("bidAddress", project.getBidAddress() == null ? "" : project.getBidAddress());
            content = content.replace("bidDate", new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(project.getBidDate()) == null ? "" : new SimpleDateFormat("yyyy年MM月dd日 HH时mm分").format(project.getBidDate()));
            content = content.replace("contact", purchaseDep.getContact() == null ? "" : purchaseDep.getContact()).replace("telephone", purchaseDep.getContactTelephone() == null ? "" : purchaseDep.getContactTelephone());
            content = content.replace("purchaserName", purchaseDep.getName() == null ? "" : purchaseDep.getName()).replace("address", purchaseDep.getContactAddress() == null ? "" : purchaseDep.getContactAddress());
            content = content.replace("Account", purchaseDep.getBankAccount().toString() == null ? "" : purchaseDep.getBankAccount().toString()).replace("fax", purchaseDep.getFax() == null ? "" : purchaseDep.getFax());
            content = content.replace("bank", purchaseDep.getBank() == null ? "" : purchaseDep.getBank()).replace("accountName", purchaseDep.getAccountName() == null ? "":purchaseDep.getAccountName());
            content = content.replace("unitPostCode", purchaseDep.getUnitPostCode().toString() == null ? "" : purchaseDep.getUnitPostCode().toString()).replace("auditResult", auditResult.toString() == null ? "" : auditResult.toString());
            content = content.replace("supplier", builder.toString() == null ? "" : builder.toString());
            article.setContent(content);
            model.addAttribute("article1", article);
        }
    }
    
    /**
     * 
     *〈采购公告类型〉
     *〈详细描述〉
     * @author FengTian
     * @param code
     * @param article
     */
    public void purchaseNoticeArticleType(User user, String code, Article article , String planType){
        ArticleType articleType = null;
        ArticleType articleType2 = null;
        DictionaryData data = DictionaryDataUtil.findById(planType);
        if ("GOODS".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_enquiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_quotas_invitation");
            } else {
                if(user.getPublishType() != null && user.getPublishType() == 1){
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_military_quotas");
                } else {
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_quotas");
                }
            }
        } else if ("PROJECT".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_engineering");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_enquiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_plumbing_invitation");
            } else {
                if(user.getPublishType() != null && user.getPublishType() == 1){
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_military_plumbing");
                } else {
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_plumbing");
                }
            }
        } else if ("SERVICE".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_service");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_enqiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("purchase_notice_centrliazed_service_invitation");
            } else {
                if(user.getPublishType() != null && user.getPublishType() == 1){
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_military_service");
                } else {
                    articleType = articelTypeService.selectArticleTypeByCode("single_source_notice_centralized_service");
                }
            }
        }
        if (articleType != null) {
            article.setFourArticleTypeId(articleType.getId());
            article.setLastArticleType(articleType);
        }
        if (articleType2 != null) {
            article.setThreeArticleTypeId(articleType2.getId());
        }
    }
    
    /**
     * 
     *〈中标公告类型〉
     *〈详细描述〉
     * @author FengTian
     * @param code
     * @param article
     * @param planType
     */
    public void winNoticeArticleType(String code, Article article , String planType){
        ArticleType articleType = null;
        ArticleType articleType2 = null;
        DictionaryData data = DictionaryDataUtil.findById(planType);
        if ("GOODS".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_matarials");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_enquiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_quotas_invitation");
            }
        } else if ("PROJECT".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_engineering");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_enquiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_plumbing_invitation");
            }
        } else if ("SERVICE".equals(data.getCode())) {
            articleType2 = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_service");
            if ("GKZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_open");
            } else if ("XJCG".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_enquiry");
            } else if ("JZXTP".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_competitive");
            } else if ("YQZB".equals(code)) {
                articleType = articelTypeService.selectArticleTypeByCode("success_notice_centralized_service_invitation");
            }
        }
        if (articleType != null) {
            article.setFourArticleTypeId(articleType.getId());
            article.setLastArticleType(articleType);
        }
        if (articleType2 != null) {
            article.setThreeArticleTypeId(articleType2.getId());
        }
    }
    
    public Templet temType(String type, String code){
        Templet templet = new Templet();
        if (type.equals(PURCHASE_NOTICE)) {
            if ("GKZB".equals(code)) {
                templet.setTemType("0");
            } else if ("XJCG".equals(code)){
                templet.setTemType("2");
            } else if ("JZXTP".equals(code)){
                templet.setTemType("3");
            } else if ("YQZB".equals(code)){
                templet.setTemType("1");
            }
        } else if (type.equals(WIN_NOTICE)) {
            if ("GKZB".equals(code)) {
                templet.setTemType("5");
            } else if ("XJCG".equals(code)){
                templet.setTemType("7");
            } else if ("JZXTP".equals(code)){
                templet.setTemType("8");
            } else if ("YQZB".equals(code)){
                templet.setTemType("6");
            }
        }
        return templet;
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
          AjaxJsonData jsonData = new AjaxJsonData();
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
                      AdvancedProject project = projectService.selectById(article.getProjectId());
                      project.setStatus(DictionaryDataUtil.getId("ZBGGNZZ"));
                      projectService.update(project);
              
                      //获取包信息
                      /*HashMap<String, Object> map = new HashMap<>();
                      map.put("projectId", project.getId());
                      List<AdvancedPackages> findById = packageService.selectByAll(map);
                      if(findById != null && findById.size() > 0){
                          for (AdvancedPackages packages : findById) {
                              packages.setProjectStatus(DictionaryDataUtil.getId("ZBGGNZZ"));
                              packageService.updateByPrimaryKeySelective(packages);
                          }
                      }*/
                  }
                  //如果是拟制中标公告，更新项目状态为中标公示编制
                  if ("win".equals(noticeType)) {
                      AdvancedProject project = projectService.selectById(article.getProjectId());
                      project.setStatus(DictionaryDataUtil.getId("NZZBGG"));
                      projectService.update(project);
              
                      //获取包信息
                      /*HashMap<String, Object> map = new HashMap<>();
                      map.put("projectId", project.getId());
                      List<Packages> findById = packageService.findByID(map);
                      if(findById != null && findById.size() > 0){
                          for (Packages packages : findById) {
                              packages.setProjectStatus(DictionaryDataUtil.getId("NZZBGG"));
                              packageService.updateByPrimaryKeySelective(packages);
                          }
                      }*/
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
                      AdvancedProject project = projectService.selectById(article.getProjectId());
                      String puchaseTypeCode = DictionaryDataUtil.findById(project.getPurchaseType()).getCode();
                      if ("GKZB".equals(puchaseTypeCode)) {
                          //如果是公开招标更新项目状态为发售标书
                          project.setStatus(DictionaryDataUtil.getId("FSBSZ"));
                          projectService.update(project);
                  
                          /*HashMap<String, Object> map = new HashMap<>();
                          map.put("projectId", project.getId());
                          List<Packages> findById = packageService.findByID(map);
                          if(findById != null && findById.size() > 0){
                              for (Packages packages : findById) {
                                  packages.setProjectStatus(DictionaryDataUtil.getId("FSBSZ"));
                                  packageService.updateByPrimaryKeySelective(packages);
                              }
                          }*/
                      } else if ("JZXTP".equals(puchaseTypeCode)) {
                
                      } else {
                          //如果是询价、邀请招标、竞争性谈判更新项目状态为抽取供应商
                          project.setStatus(DictionaryDataUtil.getId("GYSCQZ"));
                          projectService.update(project);
                          
                          /*HashMap<String, Object> map = new HashMap<>();
                          map.put("projectId", project.getId());
                          List<Packages> findById = packageService.findByID(map);
                          if(findById != null && findById.size() > 0){
                              for (Packages packages : findById) {
                                  packages.setProjectStatus(DictionaryDataUtil.getId("GYSCQZ"));
                                  packageService.updateByPrimaryKeySelective(packages);
                              }
                          }*/
                      }
                  }
                  if ("win".equals(noticeType)) {
                      AdvancedProject project = projectService.selectById(article.getProjectId());
                      String puchaseTypeCode = DictionaryDataUtil.findById(project.getPurchaseType()).getCode();
                      if ("JZXTP".equals(puchaseTypeCode)) {
                          
                      } else {
                          //如果不是单一来源，项目状态为确认中标供应商
                          project.setStatus(DictionaryDataUtil.getId("QRZBGYS"));
                          projectService.update(project);
                  
                          /*HashMap<String, Object> map = new HashMap<>();
                          map.put("projectId", project.getId());
                          List<Packages> findById = packageService.findByID(map);
                          if(findById != null && findById.size() > 0){
                              for (Packages packages : findById) {
                                  packages.setProjectStatus(DictionaryDataUtil.getId("QRZBGYS"));
                                  packageService.updateByPrimaryKeySelective(packages);
                              }
                          }*/
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
     *〈简述〉拟制中标公告
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId项目id
     * @param model
     * @return 
     */
    @RequestMapping("/winNotice")
    public String winNotice(@CurrentUser User user, String projectId, Model model, String flowDefineId){
      AdvancedProject project = projectService.selectById(projectId);
      if (project != null) {
          DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPlanType());
          if (dictionaryData != null) {
            model.addAttribute("rootCode", dictionaryData.getCode());
          }
      }
      return makeNotice(user, projectId, WIN_NOTICE, model, flowDefineId);
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
     *〈简述〉保存招标文件到服务器
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/saveBidFile")
    public void saveBidFile(@CurrentUser User user,HttpServletResponse response,HttpServletRequest req, String projectId, String flowDefineId, Model model, String flag) throws IOException{
        try {
            String result = "保存失败";
            if("2".equals(flag)){
                //保存参与包的文件上传  并返回路径
                result = uploadService.uploadNTKO(req);
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(result);
                response.getWriter().flush();
            }else{
              //修改代办为已办
                todosService.updateIsFinish("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
                //判断该项目是否上传过招标文件
                String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
                if (files != null && files.size() > 0){
                    //删除 ,表中数据假删除
                    uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
                    result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
                    //flag：1，招标文件为提交状态
                    if ("1".equals(flag)) {
                        AdvancedProject project = projectService.selectById(projectId);
                        project.setConfirmFile(1);
                        project.setAuditReason(null);
                        project.setApprovalTime(new Date());
                        //修改项目状态
                        project.setStatus(DictionaryDataUtil.getId("ZBWJYTJ"));
                        projectService.update(project);
                        //推送待办
                        push(user,project.getId());
                       //该环节设置为执行完状态
                        flowMangeService.flowExes(req, flowDefineId, projectId, 1);
                    }
                    //flag：0，招标文件为暂存状态
                    if ("0".equals(flag)) {
                      //该环节设置为执行中状态
                      flowMangeService.flowExes(req, flowDefineId, projectId, 2);
                    }
                }else{
                    result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
                    if ("1".equals(flag)) {
                        AdvancedProject project = projectService.selectById(projectId);
                        project.setConfirmFile(1);
                        project.setAuditReason(null);
                        project.setApprovalTime(new Date());
                        //修改项目状态
                        project.setStatus(DictionaryDataUtil.getId("ZBWJYTJ"));
                        projectService.update(project);
                        //推待办
                        push(user,project.getId());
                        //该环节设置为执行完状态
                        flowMangeService.flowExes(req, flowDefineId, projectId, 1);
                    }
                    //flag：0，招标文件为暂存状态
                    if ("0".equals(flag)) {
                      //该环节设置为执行中状态
                      flowMangeService.flowExe(req, flowDefineId, projectId, 2);
                    }
                }
                  
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            response.getWriter().close();
        }
    }
    
    private void push(User user,String projectId) {
        AdvancedProject selectById = projectService.selectById(projectId);
        if (selectById != null) {
          List<PurchaseOrg> list = purchaseOrgnizationService.get(selectById.getPurchaseDepId());
          for (PurchaseOrg purchaseOrg : list) {
            //推送待办
            Todos todos = new Todos();
            todos.setName(selectById.getName()+"招标文件审核");
            todos.setOrgId(purchaseOrg.getOrgId());
            todos.setSenderId(user.getId());
            todos.setUndoType((short)3);
            todos.setPowerId(PropUtil.getProperty("zbwjsh"));
            todos.setUrl("Adopen_bidding/bidFile.html?id=" + projectId + "&process=1");
            todosService.insert(todos);
          }
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
            AdvancedProject project=projectService.selectById(projectId);
            model.addAttribute("project", project);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "bss/ppms/advanced_project/advanced_bid_file/bid_file_view";
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
    public String packageFirstAuditView(String projectId, String flowDefineId, Model model){
        try {
            //项目分包信息
            HashMap<String,Object> pack = new HashMap<String,Object>();
            pack.put("projectId", projectId);
            List<AdvancedPackages> packages = packageService.selectByAll(pack);
            Map<String,Object> list = new HashMap<String,Object>();
            //关联表集合
            List<PackageFirstAudit> idList = new ArrayList<>();
            Map<String,Object> mapSearch = new HashMap<String,Object>(); 
            for(AdvancedPackages ps:packages){
                list.put("pack"+ps.getId(),ps);
                HashMap<String,Object> map = new HashMap<String,Object>();
                map.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(map);
                ps.setAdvancedDetails(detailList);
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
            AdvancedProject project=projectService.selectById(projectId);
            model.addAttribute("flowDefineId", flowDefineId);
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
            AdvancedProject project = projectService.selectById(projectId);
            project.setConfirmFile(1);
            projectService.update(project);
            //该环节设置为执行完状态
            flowMangeService.flowExes(request, flowDefineId, projectId, 1);
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
            if (StringUtils.isNotBlank(jsonQuote.getString("total"))) {
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
            AdvancedProject project = projectService.selectById(quote.getProjectId());
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
    
    /**
     * 
     *〈供应商签到〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param flowDefineId
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/selectSupplierByProject")
    public String selectSupplierByProject(String projectId, String flowDefineId, Model model) throws ParseException{
        //文件上传类型
        boolean flag = false;
        String typeId = DictionaryDataUtil.getId("OPEN_FILE");
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("typeId", typeId);
        List<Supplier> listSupplier = supplierService.selectSupplierByProjectId(projectId);
        if(listSupplier != null && listSupplier.size() > 0){
            SaleTender condition = new SaleTender();
            condition.setProjectId(projectId);
            condition.setStatusBid(NUMBER_TWO);
            condition.setStatusBond(NUMBER_TWO);
            StringBuilder sb = new StringBuilder("");
            for (Supplier supplier : listSupplier) {
                condition.setSupplierId(supplier.getId());
                List<SaleTender> stList = saleTenderService.find(condition);
                if(stList != null && stList.size() > 0){
                    for (SaleTender saleTender : stList) {
                        AdvancedPackages packages = packageService.selectById(saleTender.getPackages());
                        if(packages != null){
                            sb.append(packages.getName());
                        }
                    }
                    if (stList.get(0).getIsTurnUp() != null) {
                        supplier.setIsturnUp(stList.get(0).getIsTurnUp().toString());
                        flag = true;
                    }
                    supplier.setPackageName(sb.toString());
                    sb.delete(0, sb.length());
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
        for (Supplier supplier : listSupplier) {
            supplier.setGroupsUploadId(groupUploadId);
            supplier.setGroupShowId(groupShowId);
            List<UploadFile> blist = uploadService.getFilesOther(supplier.getProSupFile(), typeId,  Constant.SUPPLIER_SYS_KEY.toString());
            if (blist != null && blist.size() >0) {
                supplier.setBidFileName(blist.get(0).getName());
                supplier.setBidFileId(blist.get(0).getId());
            }
        }
        model.addAttribute("supplierList", listSupplier);
        model.addAttribute("projectId", projectId);
        model.addAttribute("flag", flag);
        return "bss/ppms/advanced_project/advanced_bid_file/supplier_project";
    }
    
    
    @RequestMapping("/changbiao")
    public String chooseChangBiaoType(String projectId, String flowDefineId, Model model) {
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
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
                Integer quotePrice = packageService.quotePrice(projectId);
                if(quotePrice == null){
                    return "bss/ppms/advanced_project/advanced_bid_file/cb";
                } else if (quotePrice == 0) {
                    return "redirect:changmingxi.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
                } else {
                    return "redirect:changtotal.html?projectId=" + projectId + "&flowDefineId=" + flowDefineId;
                }
            }
        }
        return "bss/ppms/advanced_project/advanced_bid_file/cb";
    }
    
    /**
     * 
     *〈唱总价〉
     *〈详细描述〉
     * @author Administrator
     * @param projectId
     * @param packId
     * @param model
     * @param count
     * @param flowDefineId
     * @return
     * @throws ParseException
     */
    @RequestMapping("/changtotal")
    public String changtotal(String projectId, String packId, Model model, String count, String flowDefineId, HttpServletRequest request) throws ParseException {
        Quote quoteCondition = new Quote();
        quoteCondition.setProjectId(projectId);
        List<Date> listDate =  supplierQuoteService.selectQuoteCount(quoteCondition);
        if (listDate != null && listDate.size() > 0 && StringUtils.isBlank(packId)) {
            //如果有明细就是查看了
            return "redirect:viewChangtotal.html?projectId=" + projectId;
        }
        //记录报价的轮次
        Integer countBid = 0;
        List<AdvancedPackages> packageId = packageService.getPackageId(projectId);
        if (StringUtils.isNotBlank(packId)) {
            //显示第几轮次报价
            quoteCondition.setPackageId(packId);
            List<Date> listDate1 =  supplierQuoteService.selectQuoteCount(quoteCondition);
            if (listDate1 != null) {
                countBid = listDate1.size();
                model.addAttribute("count", listDate1.size());
            }
            List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
            if(packageId != null && packageId.size() > 0){
                for (AdvancedPackages advancedPackages : packageId) {
                    if (advancedPackages.getId().equals(packId)) {
                        listPackage.add(advancedPackages);
                    }
                }
                packageId = listPackage;
            }
        }
        if (StringUtils.isNotBlank(count) && "1".equals(count)) {
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            map2.put("projectId", projectId);
            List<AdvancedPackages> selectByAll = packageService.selectByAll(map2);
            if (selectByAll != null && selectByAll.size() > 0) {
                model.addAttribute("count1", selectByAll.size());
            }
        }
        
        if(packageId != null && packageId.size() > 0){
            Map<String, String> mapPackageName = new HashMap<String, String>();
            HashMap<String, Object> hashMap = new HashMap<>();
            SaleTender condition = new SaleTender();
            TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
            for (AdvancedPackages advancedPackages : packageId) {
                AdvancedPackages packages = packageService.selectById(advancedPackages.getId());
                if(packages != null && StringUtils.isNotBlank(packages.getProjectStatus())){
                    DictionaryData findById = DictionaryDataUtil.findById(packages.getProjectStatus());
                    mapPackageName.put(packages.getName(), findById.getCode());
                }
                condition.setProjectId(projectId);
                condition.setPackages(advancedPackages.getId());
                condition.setStatusBid(NUMBER_TWO);
                condition.setStatusBond(NUMBER_TWO);
                condition.setIsTurnUp(0);
                if(countBid > 0){
                  condition.setIsFirstPass(1);
                }
                List<SaleTender> stList = saleTenderService.find(condition);
                List<SaleTender> stList1 = new ArrayList<SaleTender>();
                stList1.addAll(stList);
                for (SaleTender st1 : stList1) {
                    if ("2".equals(st1.getIsRemoved())) {
                        stList.remove(st1);
                    }
                }
                hashMap.put("advancedProject", projectId);
                hashMap.put("packageId", advancedPackages.getId());
                List<AdvancedDetail> selectByAll = detailService.selectByAll(hashMap);
                BigDecimal projectBudget = BigDecimal.ZERO;
                if(selectByAll != null && selectByAll.size() > 0){
                    for (AdvancedDetail advancedDetail : selectByAll) {
                        projectBudget = projectBudget.add(advancedDetail.getBudget());
                    }
                }
                if (stList != null && stList.size() > 0) {
                    treeMap.put(packages.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
                }
            }
            model.addAttribute("treeMap", treeMap);
            model.addAttribute("mapPackageName", mapPackageName);
        }
        
        model.addAttribute("projectId", projectId);
        model.addAttribute("packId", packId);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //该环节设置为执行中状态
        flowMangeService.flowExe(request, flowDefineId, projectId, 2);
        return "bss/ppms/advanced_project/advanced_bid_file/chang_total";
    }
    
    @RequestMapping("/viewChangtotal")
    public String viewChangtotal(String projectId, String packId, String timestamp, Model model, HttpServletRequest req) throws ParseException{
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            DictionaryData dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
            List<AdvancedPackages> packageId = packageService.getPackageId(projectId);
            if(packageId != null && packageId.size() > 0){
                TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
                SaleTender condition = new SaleTender();
                Map<String, String> mapPackageName=new HashMap<String, String>();
                HashMap<String, Object> map = new HashMap<String, Object>();
                HashMap<String, Object> map1 = new HashMap<String, Object>();
                Quote quote2 = new Quote();
                Quote quote3 = new Quote();
                List<AdvancedPackages> listPackage1 = new ArrayList<AdvancedPackages>();
                if(StringUtils.isNotBlank(packId)){
                    for (AdvancedPackages pack : packageId) {
                        if (pack.getId().equals(packId)) {
                          listPackage1.add(pack);
                        }
                    }
                    packageId = listPackage1;
                }
                
                for (AdvancedPackages pack : packageId) {
                    AdvancedPackages ps = packageService.selectById(pack.getId());
                    if(ps != null && StringUtils.isNotBlank(ps.getProjectStatus())){
                        DictionaryData findById = DictionaryDataUtil.findById(ps.getProjectStatus());
                        mapPackageName.put(ps.getName(), findById.getCode());
                    }
                    condition.setProjectId(projectId);
                    condition.setPackages(pack.getId());
                    condition.setStatusBid(NUMBER_TWO);
                    condition.setStatusBond(NUMBER_TWO);
                    condition.setIsTurnUp(0);
                    List<SaleTender> stList = saleTenderService.find(condition);
                    map1.put("packageId", pack.getId());
                    map1.put("advancedProject", projectId);
                    List<AdvancedDetail> detailList = detailService.selectByAll(map1);
                    BigDecimal projectBudget = BigDecimal.ZERO;
                    for (AdvancedDetail detail : detailList) {
                        projectBudget = projectBudget.add(detail.getBudget());
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
                        quote.setSupplierId(saleTender.getSuppliers().getId());
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
                                    conditionQuote.getProjectId().equals(saleTender.getProjectId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                                    for (Quote qp : listQuotebyPackage1) {
                                        if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplier().getId().equals(conditionQuote.getSupplierId())) {
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
                    if (stList != null && stList.size() > 0) {
                        treeMap.put(ps.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
                    }
                }
                model.addAttribute("treeMap", treeMap);
                model.addAttribute("mapPackageName", mapPackageName);
            } else if (packageId != null && packageId.size() == 1 && StringUtils.isNotBlank(packId)) {
                model.addAttribute("listLength", 1);
            }
            model.addAttribute("dd", dictionaryData);
        }
        model.addAttribute("projectId", projectId);
        return "bss/ppms/advanced_project/advanced_bid_file/view_chang_total";
    }
    
    @RequestMapping("/changTotalWord")
    public String changTotalWord(String projectId, String packId, String timestamp, Model model, HttpServletRequest req) throws ParseException{
        AdvancedProject project = projectService.selectById(projectId);
        DictionaryData dictionaryData = null;
        if (project != null && project.getPurchaseType() != null ){
            dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        //去saletender查出项目对应的所有的包
        List<AdvancedPackages> packList = packageService.getPackageId(projectId);
        //这里用这个是因为hashMap是无序的
        TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
        SaleTender condition = new SaleTender();
        HashMap<String, Object> map = new HashMap<String, Object>();
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        Quote quote2 = new Quote();
        Quote quote3 = new Quote();
        List<AdvancedPackages> listPackage1 = new ArrayList<AdvancedPackages>();
        if (StringUtils.isNotBlank(packId)) {
            for (AdvancedPackages pack : packList) {
                if (pack.getId().equals(packId)) {
                    listPackage1.add(pack);
                }
            }
            packList = listPackage1;
        }
        if (packList != null && packList.size() == 1 && packId != null) {
            model.addAttribute("listLength", 1);
        }
        for (AdvancedPackages pack : packList) {
            AdvancedPackages ps = packageService.selectById(pack.getId());
            condition.setProjectId(projectId);
            condition.setPackages(pack.getId());
            condition.setStatusBid(NUMBER_TWO);
            condition.setStatusBond(NUMBER_TWO);
            condition.setIsTurnUp(0);
            List<SaleTender> stList = saleTenderService.find(condition);
            map1.put("packageId", pack.getId());
            map1.put("advancedProject", projectId);
            List<AdvancedDetail> detailList = detailService.selectByAll(map1);
            BigDecimal projectBudget = BigDecimal.ZERO;
            for (AdvancedDetail detail : detailList) {
                projectBudget = projectBudget.add(detail.getBudget());
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
                quote.setSupplierId(saleTender.getSuppliers().getId());
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
                            conditionQuote.getProjectId().equals(saleTender.getProjectId()) && saleTender.getPackages().equals(conditionQuote.getPackageId())) {
                            for (Quote qp : listQuotebyPackage1) {
                                if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplier().getId().equals(conditionQuote.getSupplierId())) {
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
                treeMap.put(ps.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
            }
        }
        model.addAttribute("treeMap", treeMap);
        model.addAttribute("projectId", projectId);
        model.addAttribute("project", project);
        model.addAttribute("dd", dictionaryData);
        HashMap<String, Object> mapId = new HashMap<String, Object>();
        mapId.put("projectId", projectId);
        mapId.put("id", packId);
        List<AdvancedPackages> selectByAll = packageService.selectByAll(mapId);
        if (selectByAll != null && selectByAll.size() > 0) {
            model.addAttribute("pack", selectByAll.get(0));
        }
        return "bss/ppms/open_bidding/bid_file/view_chang_total_word";
    }
    
    @RequestMapping("/changmingxi")
    public String changmingxi(String projectId, String packId, Model model, String count, String flowDefineId, HttpServletRequest req) throws ParseException{
        Quote condition = new Quote();
        condition.setProjectId(projectId);
        List<Date> listDate =  supplierQuoteService.selectQuoteCount(condition);
        //packId代再次报价
        if (listDate != null && listDate.size() > 0  && StringUtils.isBlank(packId)) {
            //如果有明细就是查看了
            return "redirect:viewMingxi.html?projectId=" + projectId;
        }
        Integer countBid = 0;
        if (listDate != null && listDate.size() > 0) {
            countBid = listDate.size();
            model.addAttribute("listDate", listDate.size());
        }
        Quote quote2 = new Quote();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        if(countBid > 0){
            st.setIsFirstPass(1);
        }
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
        List<AdvancedPackages> listPackage1 = new ArrayList<AdvancedPackages>();
        for (AdvancedPackages packages : selectByAll) {
            if (sb.toString().indexOf(packages.getId()) != -1) {
                listPackage.add(packages);
            }
        }
        if ("1".equals(count)) {
            HashMap<String, Object> map2 = new HashMap<String, Object>();
            map2.put("projectId", projectId);
            List<AdvancedPackages> pl = packageService.selectByAll(map2);
            if (pl != null) {
                model.addAttribute("count", pl.size());
            }
        }
        if(StringUtils.isNotBlank(packId)){
            for (AdvancedPackages pack : listPackage) {
                if (pack.getId().equals(packId)) {
                    listPackage1.add(pack);
                }
            }
            listPackage = listPackage1;
        }
        for (AdvancedPackages pk : listPackage) {
            map.put("packageId", pk.getId());
            quote2.setProjectId(projectId);
            quote2.setPackageId(pk.getId());
            List<Supplier> suList = new ArrayList<Supplier>();
            List<AdvancedDetail> advancedDetails = detailService.selectByAll(map);
            BigDecimal projectBudget = BigDecimal.ZERO;
            for (AdvancedDetail detail : advancedDetails) {
                projectBudget = projectBudget.add(detail.getBudget());
            }
            pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
            for (SaleTender saleTender : saleTenderList) {
                if (saleTender.getPackages().indexOf(pk.getId()) != -1 && saleTender.getIsTurnUp() != null && saleTender.getIsTurnUp() == 0 && saleTender.getIsRemoved().equals("0")) {
                    Supplier supplier = supplierService.get(saleTender.getSuppliers().getId());
                    supplier.setDetails(advancedDetails);
                    Supplier supplierNew = new Supplier();
                    supplierNew.setSupplierName(supplier.getSupplierName());
                    supplierNew.setId(supplier.getId());
                    supplierNew.setDetails(advancedDetails);
                    suList.add(supplierNew);
                }
            }
            pk.setSuppliers(suList);
        }
        model.addAttribute("listPackage", listPackage);
        model.addAttribute("projectId", projectId);
        model.addAttribute("packId", packId);
        model.addAttribute("flowDefineId", flowDefineId);
        //该环节设置为执行中状态
        flowMangeService.flowExe(req, flowDefineId, projectId, 2);
        return "bss/ppms/advanced_project/advanced_bid_file/changbiao";
    }
    
    @ResponseBody
    @RequestMapping(value = "/savemingxi")
    public String savemingxi(@CurrentUser User user, HttpServletRequest req, Quote quote, Model model, String priceStr, String flowDefineId, String packId, String quoteList) throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", quote.getProjectId());
        List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
        SaleTender st = new SaleTender();
        st.setProjectId(quote.getProjectId());
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
        for (AdvancedPackages packages : selectByAll) {
            if (sb.toString().indexOf(packages.getId()) != -1) {
                if (StringUtils.isNotBlank(packId)) {
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
        HashMap<String, Object> hashMap = new HashMap<>();
        List<Quote> listQuote = new ArrayList<Quote>();
        for (AdvancedPackages pk : listPackage) {
            Integer count1 = 0;
            hashMap.put("advancedProject", quote.getProjectId());
            hashMap.put("packageId", pk.getId());
            List<AdvancedDetail> detailList = detailService.selectByAll(map);
            SaleTender str = new SaleTender();
            str.setProjectId(quote.getProjectId());
            List<SaleTender> stl= saleTenderService.find(str);
            Quote condition = new Quote();
            condition.setProjectId(quote.getProjectId());
            List<Date> listDate =  supplierQuoteService.selectQuoteCount(condition);
            for (SaleTender saleTender : stl) {
                if (saleTender.getPackages().indexOf(pk.getId()) != -1 && saleTender.getIsTurnUp() != null) {
                    if(listDate != null && listDate.size() > 0){
                        //判断是否为第一次报价
                        if(saleTender.getIsFirstPass() != null && saleTender.getIsFirstPass() == 1 && saleTender.getIsRemoved().equals("0")){
                            count1++;
                        }
                    }else{
                        count1++;
                    }
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
            for (Quote quote2 : listQuote) {
                bud += quote2.getTotal().intValue();
            }
            AdvancedProject project = projectService.selectById(quote.getProjectId());
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
        //该环节设置为执行完毕
        flowMangeService.flowExe(req, flowDefineId, quote.getProjectId(), 1);
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
    
    @RequestMapping("/viewMingxi")
    public String viewMingxi(String projectId, String packId, String timestamp, Model model ,HttpServletRequest req) throws ParseException{
        AdvancedProject project = projectService.selectById(projectId);
        if(project != null){
            DictionaryData dd = DictionaryDataUtil.findById(project.getPurchaseType());
            Quote quote2 = new Quote();
            Quote quote1 = new Quote();
            Quote quote=new Quote();
            SaleTender st = new SaleTender();
            st.setProjectId(projectId);
            StringBuilder sb = new StringBuilder("");
            List<SaleTender> saleTenderList = saleTenderService.find(st);
            if(saleTenderList != null && saleTenderList.size() > 0){
                for (SaleTender saleTender : saleTenderList) {
                    sb.append(saleTender.getPackages());
                }
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("projectId", projectId);
                List<AdvancedPackages> selectByAll = packageService.selectByAll(map);
                List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
                List<AdvancedPackages> listPackage1 = new ArrayList<AdvancedPackages>();
                if(selectByAll != null && selectByAll.size() > 0){
                    for (AdvancedPackages packages : selectByAll) {
                        if (sb.toString().indexOf(packages.getId()) != -1) {
                            listPackage.add(packages);
                        }
                    }
                    if (StringUtils.isNotBlank(packId)) {
                        for (AdvancedPackages pack : listPackage) {
                            if (pack.getId().equals(packId)) {
                                listPackage1.add(pack);
                            }
                        }
                        listPackage = listPackage1;
                    }
                }
              //开始循环包
                for (AdvancedPackages pk:listPackage) {
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
                                quote1.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
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
                    List<Supplier> suList = new ArrayList<Supplier>();
                    //判断每轮报价的供应商z
                    if(listDate != null && listDate.size() > 1){
                        suList = setField(listQuotebyPackage);
                    }else{
                        suList = setField(listQuote);
                    }
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
                    pk.setSuppliers(suListNew);
                    BigDecimal projectBudget = BigDecimal.ZERO;
                    if (pk.getSuppliers() != null && pk.getSuppliers().size() > 0) {
                        for (Quote q : pk.getSuppliers().get(0).getQuoteList()) {
                            if (q.getAdvancedDetail() != null) {
                                projectBudget = projectBudget.add(q.getAdvancedDetail().getBudget());
                            }
                        }
                    }
                    //项目预算
                    pk.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
                    setNewQuote(listQuote, listQuotebyPackage);
                }
                model.addAttribute("listPackage", listPackage);
                model.addAttribute("projectId", projectId);
            }
        }
        return "bss/ppms/advanced_project/advanced_bid_file/view_changbiao";
    }
    
    @RequestMapping("/changmingxiWord")
    public String changmingxiWord(String projectId, String packId, String timestamp, Model model ,HttpServletRequest req) throws ParseException{
        AdvancedProject project = projectService.selectById(projectId);
        DictionaryData dd = null;
        if (project != null && StringUtils.isNotBlank(project.getPurchaseType())){
            dd = DictionaryDataUtil.findById(project.getPurchaseType());
        }
        Quote quote2 = new Quote();
        Quote quote1 = new Quote();
        Quote quote=new Quote();
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> listPack = packageService.selectByAll(map);
        List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
        List<AdvancedPackages> listPackage1 = new ArrayList<AdvancedPackages>();
        for (AdvancedPackages packages : listPack) {
            if (sb.toString().indexOf(packages.getId()) != -1) {
                listPackage.add(packages);
            }
        }
        if (StringUtils.isNotBlank(packId)) {
            for (AdvancedPackages pack : listPackage) {
                if (pack.getId().equals(packId)) {
                    listPackage1.add(pack);
                }
            }
            listPackage = listPackage1;
        }
        //开始循环包
        for (AdvancedPackages pk : listPackage) {
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
            pk.setSuppliers(suListNew);
            BigDecimal projectBudget = BigDecimal.ZERO;
            if (pk.getSuppliers() != null && pk.getSuppliers().size() > 0) {
                for (Quote q : pk.getSuppliers().get(0).getQuoteList()) {
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
        List<AdvancedPackages> pack = packageService.selectByAll(mapId);
        if (pack != null && pack.size() > 0) {
            model.addAttribute("pack", pack.get(0));
        }
        return "bss/ppms//advanced_project/advanced_bid_file/view_changbiao_word";
    }
    
    public String getContent(String projectId) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && list.size()>0){
            for(AdvancedPackages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                ps.setAdvancedDetails(detailList);
            }
        }
        StringBuilder sb = articelService.getContents(list);
        return sb.toString();
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
                            if (quote2.getAdvancedDetail().getId().equals(quote1.getAdvancedDetail().getId())) {
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
    
    /**
     * 
     *〈谈判记录〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param model
     * @param flowDefineId
     * @return
     */
    @RequestMapping("/negotiation")
    public String negotiation(String projectId, Model model, String flowDefineId){
        List<AdvancedPackages> listProjectExtract = packageService.listProjectExtract(projectId);
        if(listProjectExtract != null && !listProjectExtract.isEmpty()){
            for (AdvancedPackages packages : listProjectExtract) {
                Negotiation negotiation = negotiationService.selectByPackageId(packages.getId());
                if(negotiation != null){
                    packages.setNegotiation(negotiation);
                } else {
                    packages.setNegotiation(new Negotiation());
                    model.addAttribute("uuId", WfUtil.createUUID());
                }
            }
            model.addAttribute("listResultExpert", listProjectExtract);
        }
        AdvancedProject project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("flowDefineId", flowDefineId);
        model.addAttribute("dataId", DictionaryDataUtil.getId("NEGOTIATION_RECORD"));
        model.addAttribute("projectId", projectId);
        return "bss/ppms/advanced_project/advanced_bid_file/negotiation";
    }
    
    @RequestMapping("/getpackage")
    @ResponseBody
    public String getpackage(String projectId){
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && !list.isEmpty()){
            return JSON.toJSONString(list);
        } else {
            return null;
        }
    }
    
    @RequestMapping("/educe")
    public ResponseEntity<byte[]> educe(String projectId, String createdAt, String nuter, String net,  HttpServletRequest request) throws Exception{
        AdvancedProject project = projectService.selectById(projectId);
        List<AdvancedPackages> listProjectExtract = packageService.listProjectExtract(projectId);
        String downFileName = null;
        // 文件存储地址
        String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
        String fileName = createWordMethod(project, createdAt, nuter, net, listProjectExtract,request);
        downFileName = new String("谈判记录表.doc".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        return expertService.downloadFile(fileName, filePath, downFileName);
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
    private String createWordMethod(AdvancedProject project, String createdAt, String nuter, String net, List<AdvancedPackages> selectList, HttpServletRequest request) throws Exception {
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
}
