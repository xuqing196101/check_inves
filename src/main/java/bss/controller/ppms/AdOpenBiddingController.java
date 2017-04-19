package bss.controller.ppms;

import iss.service.ps.ArticleService;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import ses.model.bms.DictionaryData;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseOrg;
import ses.model.sms.Quote;
import ses.service.bms.TodosService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.Reason;
import bss.model.ppms.ScoreModel;
import bss.model.prms.FirstAudit;
import bss.model.prms.PackageFirstAudit;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.MarkTermService;
import bss.service.ppms.ScoreModelService;
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
    
    /**
     * @Fields packageService : 引用分包业务逻辑接口
     */
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
    
}
