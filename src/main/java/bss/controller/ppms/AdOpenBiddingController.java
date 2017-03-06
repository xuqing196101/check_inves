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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import ses.model.bms.DictionaryData;
import ses.model.bms.Templet;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.model.oms.util.AjaxJsonData;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TempletService;
import ses.service.bms.TodosService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.CnUpperCaser;
import ses.util.DictionaryDataUtil;
import ses.util.WfUtil;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Project;
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
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
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
    
    private final static Short NUMBER_TWO = 2;
    
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
     * @Fields articelTypeService : 引用文章类型业务实现接口
     */
    @Autowired
    private ArticleTypeService articelTypeService;
    
    /**
     * @Fields detailService : 引用项目详细业务接口
     */
    @Autowired
    private AdvancedDetailService detailService;
    
    /**
     * @Fields packageService : 引用分包业务逻辑接口
     */
    @Autowired
    private AdvancedPackageService packageService;
    
    @Autowired
    private PackageFirstAuditService packageFirstAuditService;
    
    @Autowired
    private SupplierExtUserServicel extUserServicel;
    
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
    
    
    @Autowired
    private BidMethodService bidMethodService;
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
    public String bidFile(@CurrentUser User user,HttpServletRequest request, String id, Model model, HttpServletResponse response, Integer process) throws Exception{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", id);
        List<AdvancedPackages> packages = packageService.selectByAll(map);
        String msg = "";
        for (AdvancedPackages p : packages) {
          //判断各包符合性审查项是否编辑完成
            FirstAudit firstAudit = new FirstAudit();
            firstAudit.setPackageId(p.getId());
            List<FirstAudit> fas = firstAuditService.findBykind(firstAudit);
            if (fas == null || fas.size() <= 0) {
              msg = "noFirst";
              return "redirect:/adFirstAudit/toAdd.html?projectId="+id+"&msg="+msg;
            }
            //获取资格性审查项内容
            //获取评分办法数据字典编码     
            String methodCode = bidMethodService.getMethod(id, p.getId());
            if (methodCode != null && !"".equals(methodCode)) {
              if ("PBFF_JZJF".equals(methodCode) || "PBFF_ZDJF".equals(methodCode)) {
                FirstAudit firstAudit2 = new FirstAudit();
                firstAudit2.setPackageId(p.getId());
                firstAudit2.setIsConfirm((short)1);
                List<FirstAudit> fas2 = firstAuditService.findBykind(firstAudit2);
                if (fas2 == null || fas2.size() <= 0) {
                  msg = "noSecond";
                  return "redirect:/adIntelligentScore/packageList.html?projectId="+id+"&msg="+msg;
                }
              }
              if ("OPEN_ZHPFF".equals(methodCode)) {
                ScoreModel smMap = new ScoreModel();
                smMap.setPackageId(p.getId());
                List<ScoreModel> sms = scoreModelService.findListByScoreModel(smMap);
                if (sms == null || sms.size() <= 0) {
                  msg = "noSecond";
                  return "redirect:/adIntelligentScore/packageList.html?projectId="+id+"&msg="+msg;
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
        
        if (user.getId().equals(project.getPrincipal())) {
            model.addAttribute("isAdmin", 1);
          }else{
            model.addAttribute("isAdmin", 2);
          }
          model.addAttribute("project", project);
          model.addAttribute("reasons", JSON.parseObject(project.getAuditReason(), Reason.class));
          model.addAttribute("pStatus",DictionaryDataUtil.findById(project.getStatus()).getCode());
          model.addAttribute("ope", "add");
          model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
          model.addAttribute("typeId", DictionaryDataUtil.getId("BID_FILE_AUDIT"));
        
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
    public String bidFileView(HttpServletRequest request, String id, Model model, HttpServletResponse response){
        AdvancedProject project = projectService.selectById(id);
        //判断是否上传招标文件
        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
        List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            model.addAttribute("fileId", files.get(0).getId());
        } else {
            model.addAttribute("fileId", "0");
        }
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
    public String bidNotice(String projectId, Model model, String flowDefineId){
          return makeNotice(projectId, PURCHASE_NOTICE, model, flowDefineId);
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
    public AjaxJsonData saveBidNotice(HttpServletRequest request, Article article, String articleTypeId, String flowDefineId, Integer flag) throws Exception{
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
                if (flag == 0) {
                  //暂存
                  article.setStatus(0);
                  jsonData.setMessage("暂存成功");
                }
                if (flag == 1) {
                  //提交
                  article.setStatus(1);
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
    public void saveBidFile(@CurrentUser User user,HttpServletResponse response,HttpServletRequest req, String projectId, Model model, String flag) throws IOException{
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
                        AdvancedProject project = projectService.selectById(projectId);
                        project.setConfirmFile(1);
                        project.setAuditReason(null);
                        project.setApprovalTime(new Date());
                        //修改项目状态
                        project.setStatus(DictionaryDataUtil.getId("ZBWJYTJ"));
                        projectService.update(project);
                       //推送待办
                        push(user,project.getId());
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
    public String packageFirstAuditView(String projectId, Model model){
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
    public void confirmOk(HttpServletRequest request, HttpServletResponse response, String projectId) throws Exception{
        try {
            AdvancedProject project = projectService.selectById(projectId);
            project.setConfirmFile(1);
            projectService.update(project);
            //该环节设置为执行完状态
            //flowMangeService.flowExe(request, flowDefineId, projectId, 1);
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
        if (quoteId == null || "".equals(quoteId)) {
            supplierQuoteService.insert(quoteList);    
        } else {
            quote.setId(quoteId);
            supplierQuoteService.update(quoteList);
        }
        
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
        }
        for (Supplier supplier : listSupplier) {
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
        AdvancedProject project = projectService.selectById(projectId);
        model.addAttribute("project", project);
        //参与项目的所有供应商
        List<Supplier> listSupplier=supplierService.selectSupplierByProjectId(projectId);
        String supplierStr="";
        for(Supplier sup:listSupplier){
            supplierStr+=sup.getId()+",";
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId",projectId);
        map.put("purchaseType",DictionaryDataUtil.getId("GKZB"));
        //每个供应商的报价明细产品
        List<List<Quote>> listQuoteList=new ArrayList<List<Quote>>();
        List<String> listsupplierId=Arrays.asList(supplierStr.split(","));
        boolean flag = false;
        boolean flagButton = false;
        if(listsupplierId.get(0).length() > 30){
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
        }
         //已开标 就是线下报价  -第一次进来的时候
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
            List<AdvancedPackages> listPack = packageService.selectByAll(map1);
            List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
            for (AdvancedPackages packages : listPack) {
                if (sb.toString().indexOf(packages.getId()) != -1) {
                    listPackage.add(packages);
                }
            }
            //开始循环包
            List<HashMap<List<Supplier>,List<AdvancedDetail>>> listPd = new ArrayList<HashMap<List<Supplier>,List<AdvancedDetail>>>();
            for (AdvancedPackages pk:listPackage) {
                HashMap<List<Supplier>,List<AdvancedDetail>> hashMap = new HashMap<List<Supplier>,List<AdvancedDetail>>();
                List<Supplier> supplierList = new ArrayList<Supplier>();
                map1.put("packageId", pk.getId());
                //
                Quote quotes = new Quote();
                quotes.setProjectId(projectId);
                List<Date> listDate = supplierQuoteService.selectQuoteCount(quotes);
                List<AdvancedDetail> detailList = null;
                if (listDate.size() != 0) {
                    Quote quote=new Quote();
                    quote.setProjectId(projectId);
                    quote.setCreatedAt(new Timestamp(listDate.get(listDate.size() - 1).getTime()));
                    List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
                    detailList = detailService.selectByCondition(map1);
                    if (listQuote != null && listQuote.size() > 0 && detailList != null && detailList.size() > 0) {
                        flagButton = true;
                    }
                    List<AdvancedDetail> detailList1 = new ArrayList<AdvancedDetail>();
                    for (Quote q : listQuote) {
                        for (AdvancedDetail projectDetail : detailList) {
                            if (q.getProjectDetail().getId().equals(projectDetail.getId())) {
                                AdvancedDetail pd = new AdvancedDetail();
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
                    detailList = detailService.selectByCondition(map1);
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
    public String saves(HttpServletRequest req, Quote quote, Model model, String priceStr) throws Exception {
        List<String> listBd = Arrays.asList(priceStr.split(","));
        User user = (User) req.getSession().getAttribute("loginUser");
        List<Quote> listQuote = new ArrayList<Quote>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", quote.getProjectId());
        List<AdvancedPackages> listPack = packageService.selectByAll(map);
        //
        SaleTender st = new SaleTender();
        st.setProjectId(quote.getProjectId());
        StringBuilder sb = new StringBuilder("");
        List<SaleTender> saleTenderList = saleTenderService.find(st);
        for (SaleTender saleTender : saleTenderList) {
            sb.append(saleTender.getPackages());
        }
        List<AdvancedPackages> listPackage = new ArrayList<AdvancedPackages>();
        for (AdvancedPackages packages : listPack) {
            if (sb.toString().indexOf(packages.getId()) != -1) {
                listPackage.add(packages);
            }
        }
        //循环次数
        Integer count = 0;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        for (AdvancedPackages pk:listPackage) {
            Integer count1 = 0;
            map.put("packageId", pk.getId());
            List<AdvancedDetail> detailList = detailService.selectByCondition(map);
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
        AdvancedProject project=projectService.selectById(projectId);
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
        List<AdvancedPackages> list = packageService.selectByAll(map);
        if(list != null && list.size()>0){
            for(AdvancedPackages ps:list){
                HashMap<String,Object> packageId = new HashMap<String,Object>();
                packageId.put("packageId", ps.getId());
                List<AdvancedDetail> detailList = detailService.selectByAll(packageId);
                ps.setAdvancedDetails(detailList);
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
     *〈简述〉编制公告
     *〈详细描述〉
     * @author Ye MaoLin
     * @param projectId 项目id
     * @param noticeType 公告类型
     * @param model
     * @return
     */
    public String makeNotice(String projectId, String noticeType, Model model, String flowDefineId){
        AdvancedProject project = projectService.selectById(projectId);
        ArticleType articleType = new ArticleType();
        Article article = new Article();
        //如果是拟制招标公告
        if (PURCHASE_NOTICE.equals(noticeType)) {
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro_pro_notice_matarials");
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_engineering");
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro__pronotice_service");
                getDefaultTemplate(projectId, model, PURCHASE_NOTICE);
            }
        }
        //如果是拟制中标公告
        if (WIN_NOTICE.equals(noticeType)) {
            //货物/物资
            if (DictionaryDataUtil.getId("GOODS").equals(project.getPlanType())) { 
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_matarials");
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("PROJECT").equals(project.getPlanType())){
                //工程  
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_engineering");
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            } else if (DictionaryDataUtil.getId("SERVICE").equals(project.getPlanType())){
                //服务 
                articleType = articelTypeService.selectArticleTypeByCode("centralized_pro_deal_notice_service");
                getDefaultTemplate(projectId, model, WIN_NOTICE);
            }
        }
        article.setProjectId(projectId);
        if (articleType.getId() != null){
            article.setArticleType(articleType);
        }
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
                model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
                model.addAttribute("noticeType", noticeType);
                model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
                model.addAttribute("flowDefineId", flowDefineId);
                model.addAttribute("security", DictionaryDataUtil.getId("SECURITY_COMMITTEE"));
                return "bss/ppms/open_bidding/bid_notice/add";
            }
        } else {
            model.addAttribute("articleType", articleType);
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
    
    public void getDefaultTemplate(String projectId, Model model, String type) {
        List<Templet> templets = null;
        if (type.equals(PURCHASE_NOTICE)) {
          Templet templet = new Templet();
          templet.setTemType("招标公告");
          templets = templetService.search(1, templet);
        }
        if (type.equals(WIN_NOTICE)) {
          Templet templet = new Templet();
          templet.setTemType("中标公告");
          templets = templetService.search(1, templet);
        }
        if (templets != null && templets.size() > 0) {
            String content = templets.get(0).getContent();
            Article article1 = new Article();
            String table = getContent(projectId);
            AdvancedProject p = projectService.selectById(projectId);
            String purchaseTypeName = "";
            StringBuilder auditResult = new StringBuilder();
            auditResult.append("");
            //评分结果
            HashMap<String ,Object> map = new HashMap<String ,Object>();
            map.put("projectId", projectId);
            //查询包信息
            List<AdvancedPackages> packageList = packageService.selectByAll(map);
            for (AdvancedPackages packages : packageList) {
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
            article1.setContent(content);
            model.addAttribute("article", article1);
        }
    }
}
