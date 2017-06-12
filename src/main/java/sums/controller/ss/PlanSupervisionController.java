package sums.controller.ss;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import bss.formbean.Jzjf;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.model.prms.PackageExpert;
import bss.model.prms.SupplierRank;
import bss.model.prms.ext.ExpertSuppScore;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.BidMethodService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.NegotiationReportService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.pqims.PqInfoService;
import bss.service.prms.ExpertScoreService;
import bss.service.prms.PackageExpertService;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.ems.ExpExtCondition;
import ses.model.ems.ExpExtractRecord;
import ses.model.ems.Expert;
import ses.model.ems.ProExtSupervise;
import ses.model.oms.Orgnization;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtUser;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpExtConditionService;
import ses.service.ems.ExpExtractRecordService;
import ses.service.ems.ExpertService;
import ses.service.ems.ProjectSupervisorServicel;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierConditionService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierExtractsService;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.ss.PlanSupervisionService;
import sums.service.ss.SupervisionService;

/**
 * 
 * 版权：(C) 版权所有 
 * <采购计划监督>
 * <详细描述>
 * @author   Administrator
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/planSupervision")
public class PlanSupervisionController {
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private PurchaseDetailService detailService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private ProjectTaskService projectTaskService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private SupplierCheckPassService checkPassService;
    
    @Autowired
    private PurchaseContractService contractService;
    
    @Autowired
    private PurchaseRequiredService requiredService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private ProjectDetailService projectDetailService;
    
    @Autowired
    private PurchaseManagementService managementService;
    
    @Autowired
    private AuditPersonService auditPersonService;
    
    @Autowired
    private AdvancedDetailService advancedDetailService;
    
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @Autowired
    private UploadService uploadService;
    
    @Autowired
    private FlowMangeService flowMangeService;
    
    @Autowired
    private ArticleTypeService articelTypeService;
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private SaleTenderService saleTenderService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private PackageExpertService packageExpertService;
    
    @Autowired
    private ExpertService expertService;
    
    @Autowired
    private NegotiationReportService reportService;
    
    @Autowired
    private ContractRequiredService contractRequiredService;
    
    @Autowired
    private PqInfoService pqInfoService;
    
    @Autowired 
    private BidMethodService bidMethodService; 
    
    @Autowired
    private ExpertScoreService expertScoreService;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private SupplierExtUserServicel extUserService;
    
    @Autowired
    private ProjectSupervisorServicel projectSupervisorService;
    
    @Autowired
    private ExpExtractRecordService expExtractRecordService;
    
    @Autowired
    ExpExtConditionService conditionService;
    
    @Autowired
    private SupplierExtractsService supplierExtractsService;
    
    @Autowired
    private SupplierConditionService supplierConditionService;
    
    @Autowired
    private DictionaryDataServiceI dataService;
    
    @Autowired
    private AdvancedPackageService advancedPackageService;
    
    @Autowired
    private PlanSupervisionService planSupervisionService;
    
    
    /**
     * 
     *〈计划监督列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param user
     * @param collectPlan
     * @param page
     * @return
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user, CollectPlan collectPlan, Integer page){
        if(user != null && user.getOrg() != null){
            if(collectPlan.getStatus() == null){
                collectPlan.setSign("0");
                collectPlan.setStatus(null);
            }else if(collectPlan.getStatus() == 8){
                collectPlan.setSign("8");
                collectPlan.setStatus(null);
            } else if(collectPlan.getStatus() == 12){
                collectPlan.setSign("12");
                collectPlan.setStatus(null);
            }
            if("".equals(collectPlan.getFileName())){
                collectPlan.setFileName(null);
            }
            List<Role> roles = user.getRoles();
            boolean bool=false;
            if(roles!=null&&roles.size()>0){
                for(Role r:roles){
                    if(r.getCode().equals("MANAGE_M")){
                        bool=true;
                    }
                }
            }
            if(bool!=true){
                collectPlan.setUserId(user.getId());
            }
            List<CollectPlan> list = collectPlanService.querySupervision(collectPlan, page==null?1:page);
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User users = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(users.getRelName());
                } catch (Exception e) {
                    list.get(i).setUserId("");
                }
            }
            PageInfo<CollectPlan> info = new PageInfo<>(list);
            model.addAttribute("info", info);
            model.addAttribute("collectPlan", collectPlan);
        }
        return "sums/ss/planSupervision/list";
    }
    
    
    /**
     * 
     *〈查看跳转页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            CollectPlan collectPlan = collectPlanService.queryById(id);
            if(collectPlan != null){
                //计划状态
                Integer planStatus = supervisionService.progressBarPlan(collectPlan.getStatus());
                //项目状态
                Integer projectStatus = planSupervisionService.projectStatus(collectPlan.getId());
                //合同状态
                Integer contractStatus = planSupervisionService.contractStatus(collectPlan.getId());
                model.addAttribute("projectStatus", projectStatus);
                model.addAttribute("planStatus", planStatus);
                model.addAttribute("collectPlan", collectPlan);
                model.addAttribute("contractStatus", contractStatus);
            }
        }
        
        return "sums/ss/planSupervision/schedule_view";
    }
    
    
    /**
     * 
     *〈查看需求列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewDeand")
    public String viewDeand(Model model,String id){
        if(StringUtils.isNotBlank(id)){
            List<PurchaseRequired> details = planSupervisionService.viewDemand(id);
            model.addAttribute("listRequired", details);
        }
        return "sums/ss/planSupervision/demand_view";
    }
    
    /**
     * 
     *〈查看计划明细〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewPlan")
    public String viewPlan(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            CollectPlan collectPlan = collectPlanService.queryById(id);
            if(collectPlan != null){
                HashMap<String, Object> map = new HashMap<>();
                User user = userService.getUserById(collectPlan.getUserId());
                
                map.put("collectId", collectPlan.getId());
                List<Task> listBycollect = taskService.listBycollect(map);
                if(listBycollect != null && listBycollect.size() > 0){
                    collectPlan.setOrderAt(listBycollect.get(0).getGiveTime());
                    collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                }
                collectPlan.setUserId(user.getRelName());
                collectPlan.setPurchaseId(user.getOrgName());
                model.addAttribute("collectPlan", collectPlan);
                model.addAttribute("type", "1");
                model.addAttribute("planId", id);
            }
        }
        return "sums/ss/planSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看项目列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewProject")
    public String viewProject(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            List<Project> list = planSupervisionService.viewProject(id);
            model.addAttribute("listProject", list);
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
        }
        return "sums/ss/planSupervision/project_view";
    }
    
    /**
     * 
     *〈查看合同列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/viewContract")
    public String viewContract(Model model, String id){
        if(StringUtils.isNotBlank(id)){
            List<PurchaseContract> listContract = planSupervisionService.viewContract(id);
            model.addAttribute("listContract", listContract);
        }
        return "sums/ss/planSupervision/contract_view";
    }
    
    /**
     * 
     *〈查看需求计划〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewDetail")
    public String viewDetail(String id, String projectId, Model model){
        if(StringUtils.isNotBlank(id)){
            model.addAttribute("type", "0");
            model.addAttribute("planId", id);
        }
        return "sums/ss/planSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看分包信息〉
     *〈详细描述〉
     * @author Administrator
     * @param user
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewPack")
    public String viewPack(String id, String planId, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            if(project != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("projectId", project.getId());
                List<Packages> packages = packageService.findByID(map);
                if(packages != null && packages.size() > 0){
                    List<Packages> list = planSupervisionService.viewPack(project.getId());
                    model.addAttribute("packages", list);
                } else {
                    List<ProjectDetail> list = new ArrayList<ProjectDetail>();
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", project.getId());
                    List<ProjectDetail> details = projectDetailService.selectById(maps);
                    for (ProjectDetail detail : details) {
                        if(detail.getPrice() != null){
                            DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                            detail.setPurchaseType(findById.getName());
                            String[] progressBarPlan = supervisionService.progressBar(detail.getRequiredId());
                            detail.setProgressBar(progressBarPlan[0]);
                            detail.setStatus(progressBarPlan[1]);
                            list.add(detail);
                        } else {
                            detail.setPurchaseType(null);
                            detail.setStatus(null);
                        }
                    }
                    model.addAttribute("details", list);
                }
                
                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                project.setStatus(findById.getName());
                User users = userService.getUserById(project.getAppointMan());
                project.setAppointMan(users.getRelName());
                model.addAttribute("project", project);
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈项目信息总览页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     * @throws Exception 
     */
    @RequestMapping("/overview")
    public String overview(String id, Model model, HttpServletRequest request) throws Exception{
        if(StringUtils.isNotBlank(id)){
            ProjectDetail projectDetail = projectDetailService.selectByPrimaryKey(id);
            PurchaseDetail detail = null;
            if(projectDetail != null){
                detail = detailService.queryById(projectDetail.getRequiredId());
            }else{
                detail = detailService.queryById(id);
            }
            
            if(detail != null){
                HashMap<String, Object> map = new HashMap<>();
                //计划信息
                CollectPlan collectPlan = collectPlanService.queryById(detail.getUniqueId());
                if(collectPlan != null){
                    User user = userService.getUserById(collectPlan.getUserId());
                    collectPlan.setUserId(user.getRelName());
                    collectPlan.setPurchaseId(user.getOrgName());
                    HashMap<String, Object> mapTask = new HashMap<>();
                    mapTask.put("collectId", collectPlan.getId());
                    List<Task> listBycollect = taskService.listBycollect(mapTask);
                    if(listBycollect != null && listBycollect.size() > 0){
                        collectPlan.setUpdatedAt(listBycollect.get(0).getGiveTime());
                        collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                    }
                    model.addAttribute("collectPlan", collectPlan);
                    
                    
                    map.put("collectId", collectPlan.getId());
                    List<AuditPerson> listAuditPerson = auditPersonService.selectByMap(map);
                    if(listAuditPerson != null && listAuditPerson.size() > 0){
                        model.addAttribute("listAuditPerson", listAuditPerson);
                    }
                    
                }
                
                
                //任务信息
                HashMap<String, Object> taskMap = new HashMap<>();
                taskMap.put("collectId", detail.getUniqueId());
                taskMap.put("purchaseId", detail.getOrganization());
                List<Task> task = taskService.listBycollect(taskMap);
                if(task != null && task.size() > 0){
                    Orgnization org = orgnizationService.getOrgByPrimaryKey(task.get(0).getPurchaseId());
                    if(task.get(0).getUserId() != null){
                        User user = userService.getUserById(task.get(0).getUserId());
                        task.get(0).setUserId(user.getRelName());
                    }
                    if(task.get(0).getCreaterId() != null){
                        User user = userService.getUserById(task.get(0).getCreaterId());
                        task.get(0).setCreaterId(user.getRelName());
                        task.get(0).setMaterialsType(user.getMobile());
                    }
                    task.get(0).setPurchaseId(org.getShortName());
                    model.addAttribute("task", task.get(0));
                }
                
                //项目信息
                map.put("requiredId", detail.getId());
                List<ProjectDetail> selectById = projectDetailService.selectById(map);
                if(selectById != null && selectById.size() > 0){
                    Project project = projectService.selectById(selectById.get(0).getProject().getId());
                    if(project != null){
                        if(StringUtils.isNotBlank(project.getAppointMan())){
                            User user = userService.getUserById(project.getAppointMan());
                            project.setAppointMan(user.getRelName());
                            project.setIpone(user.getMobile());
                        }
                        if(StringUtils.isNotBlank(project.getPrincipal())){
                            User user = userService.getUserById(project.getPrincipal());
                            project.setPrincipal(user.getRelName());
                        }
                        Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                        project.setPurchaseDepName(org.getShortName());
                        project.setStatus(DictionaryDataUtil.findById(project.getStatus()).getName());
                        String id2 = DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS");
                        List<UploadFile> list = uploadService.getFilesOther(project.getId(), id2, "2");
                        if(list != null && list.size() > 0){
                            model.addAttribute("uploadFile", "0");
                        }else{
                            model.addAttribute("uploadFile", "1");
                        }
                        model.addAttribute("uploadId", id2); //项目审批文件
                        //判断是否上传招标文件
                        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                        List<UploadFile> files = uploadService.getFilesOther(project.getId(), typeId, Constant.TENDER_SYS_KEY+"");
                        if(files != null && files.size() > 0){
                          //调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
                            String filePath = extUserService.downLoadBiddingDoc(request,project.getId(),1,null);
                            if (StringUtils.isNotBlank(filePath)){
                              model.addAttribute("filePath", filePath);
                            }
                            model.addAttribute("fileId", files.get(0).getId());
                            model.addAttribute("fileName", files.get(0).getName());
                        }
                        
                        if(!"DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
                            //获取采购文件的编制人
                            FlowDefine define = new FlowDefine();
                            define.setPurchaseTypeId(project.getPurchaseType());
                            define.setCode("NZCGWJ");
                            List<FlowDefine> find = flowMangeService.find(define);
                            FlowExecute execute = new FlowExecute();
                            execute.setProjectId(project.getId());
                            execute.setFlowDefineId(find.get(0).getId());
                            execute.setStatus(1);
                            List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
                            if(findFlowExecute != null && findFlowExecute.size() > 0){
                                model.addAttribute("operatorName", findFlowExecute.get(0).getOperatorName());
                            } else {
                                execute.setStatus(3);
                                List<FlowExecute> findFlowExecutes = flowMangeService.findFlowExecute(execute);
                                if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                                    model.addAttribute("operatorName", findFlowExecutes.get(0).getOperatorName());
                                } else {
                                    execute.setStatus(2);
                                    List<FlowExecute> executes = flowMangeService.findFlowExecute(execute);
                                    if(executes != null && executes.size() > 0){
                                        model.addAttribute("operatorName", executes.get(0).getOperatorName());
                                    }
                                }
                            }
                            
                            
                            //获取发售标书的操作人
                            FlowDefine defines = new FlowDefine();
                            defines.setPurchaseTypeId(project.getPurchaseType());
                            defines.setCode("FSBS");
                            List<FlowDefine> finds = flowMangeService.find(defines);
                            FlowExecute executes = new FlowExecute();
                            executes.setProjectId(project.getId());
                            executes.setFlowDefineId(finds.get(0).getId());
                            executes.setStatus(1);
                            List<FlowExecute> findFlowExecutes = flowMangeService.findFlowExecute(executes);
                            if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                                model.addAttribute("operatorNames", findFlowExecutes.get(0).getOperatorName());
                            } else {
                                executes.setStatus(3);
                                List<FlowExecute> findFlowE = flowMangeService.findFlowExecute(executes);
                                if(findFlowE != null && findFlowE.size() > 0){
                                    model.addAttribute("operatorNames", findFlowE.get(0).getOperatorName());
                                } else {
                                    executes.setStatus(2);
                                    List<FlowExecute> execut = flowMangeService.findFlowExecute(executes);
                                    if(execut != null && execut.size() > 0){
                                        model.addAttribute("operatorNames", execut.get(0).getOperatorName());
                                    }
                                }
                            }
                        }
                        
                        
                        //获取开标人
                        FlowDefine defines = new FlowDefine();
                        defines.setPurchaseTypeId(project.getPurchaseType());
                        defines.setCode("KBCB");
                        List<FlowDefine> finds = flowMangeService.find(defines);
                        FlowExecute executes = new FlowExecute();
                        executes.setProjectId(project.getId());
                        executes.setFlowDefineId(finds.get(0).getId());
                        executes.setStatus(1);
                        List<FlowExecute> findFlowExecutes = flowMangeService.findFlowExecute(executes);
                        if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                            model.addAttribute("operName", findFlowExecutes.get(0).getOperatorName());
                        } else {
                            executes.setStatus(3);
                            List<FlowExecute> findFlowE = flowMangeService.findFlowExecute(executes);
                            if(findFlowE != null && findFlowE.size() > 0){
                                model.addAttribute("operName", findFlowE.get(0).getOperatorName());
                            } else {
                                executes.setStatus(2);
                                List<FlowExecute> execut = flowMangeService.findFlowExecute(executes);
                                if(execut != null && execut.size() > 0){
                                    model.addAttribute("operName", execut.get(0).getOperatorName());
                                }
                            }
                        }
                        
                        //获取中标供应商操作人
                        FlowDefine define = new FlowDefine();
                        define.setPurchaseTypeId(project.getPurchaseType());
                        define.setCode("QRZBGYS");
                        List<FlowDefine> find = flowMangeService.find(define);
                        FlowExecute execute = new FlowExecute();
                        execute.setProjectId(project.getId());
                        execute.setFlowDefineId(find.get(0).getId());
                        execute.setStatus(1);
                        List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
                        if(findFlowExecute != null && findFlowExecute.size() > 0){
                            model.addAttribute("operatorName1", findFlowExecute.get(0).getOperatorName());
                        } else {
                            execute.setStatus(3);
                            List<FlowExecute> findFlowExecutes1 = flowMangeService.findFlowExecute(execute);
                            if(findFlowExecutes1 != null && findFlowExecutes1.size() > 0){
                                model.addAttribute("operatorName1", findFlowExecutes1.get(0).getOperatorName());
                            } else {
                                execute.setStatus(2);
                                List<FlowExecute> executes1 = flowMangeService.findFlowExecute(execute);
                                if(executes1 != null && executes1.size() > 0){
                                    model.addAttribute("operatorName1", executes1.get(0).getOperatorName());
                                }
                            }
                        }
                        
                        
                        //获取采购公告
                        Article article = new Article();
                        article.setArticleType(articelTypeService.selectArticleTypeByCode("purchase_notice"));
                        article.setProjectId(project.getId());
                        List<Article> articles = articleService.selectArticleByProjectId(article);
                        if(articles != null && articles.size() > 0){
                            User user2 = userService.getUserById(articles.get(0).getUser().getId());
                            articles.get(0).setUserId(user2.getRelName());
                            model.addAttribute("articles",articles.get(0));
                        }
                        
                        //获取中标公示
                        Article article2 = new Article();
                        article2.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
                        article2.setProjectId(project.getId());
                        List<Article> articleList= articleService.selectArticleByProjectId(article2);
                        if(articleList != null && articleList.size() > 0){
                            User user2 = userService.getUserById(articleList.get(0).getUser().getId());
                            articleList.get(0).setUserId(user2.getRelName());
                            model.addAttribute("articleList",articleList.get(0));
                        }
                        
                        //文件发售时间
                        if(StringUtils.isNotBlank(selectById.get(0).getPackageId())){
                            SaleTender saleTender = new SaleTender();
                            saleTender.setPackages(selectById.get(0).getPackageId());
                            List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
                            TreeSet<Long> set = new TreeSet<Long>();
                            for (SaleTender saleTender2 : saleTenderList) {
                                if(saleTender2.getCreatedAt() != null){
                                    Date createdAt = saleTender2.getCreatedAt();
                                    try {
                                        long simp=new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(createdAt)).getTime();
                                        set.add(simp);
                                     } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            if(set != null && set.size() > 0){
                                Iterator<Long> it = set.iterator();
                                if(set.size()==1){
                                    model.addAttribute("begin", new SimpleDateFormat("yyyy-MM-dd").format(it.next()));//需求编报
                                }else{
                                    int sun=0;
                                    while (it.hasNext()) {
                                        if(sun==0){
                                            model.addAttribute("begin", new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
                                        }
                                        if(sun==(set.size()-1)){
                                            model.addAttribute("end", new SimpleDateFormat("yyyy-MM-dd").format(it.next()));
                                        }
                                        sun++;
                                    }
                                }
                            }
                            
                            //资格性符合性检查
                            Map<String, Object> packageExpertmap = new HashMap<String, Object>();
                            packageExpertmap.put("packageId", selectById.get(0).getPackageId());
                            packageExpertmap.put("projectId", project.getId());
                            //查询专家
                            List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
                            List<Expert> experts = new ArrayList<Expert>();
                            for (PackageExpert packageExpert : expertIdList) {
                                Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
                                packageExpert.setExpertId(expert.getRelName());
                                experts.add(expert);
                            }
                            if("DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
                                NegotiationReport report =  reportService.selectByPackageId(selectById.get(0).getPackageId());
                                if(report != null){
                                    model.addAttribute("reviewTime", report.getReviewTime());
                                }
                                model.addAttribute("DYLY", "1");
                            }
                            
                            //获取抽取供应商监督人员
                            DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
                            if(!"GKZB".equals(findById.getCode()) && !"DYLY".equals(findById.getCode())){
                                List<SupplierExtUser>  listUsers = extUserService.list(new SupplierExtUser(project.getId()));
                                String userNames = "";
                                if (listUsers != null && listUsers.size() != 0){
                                    for (SupplierExtUser ps : listUsers) {
                                        if (ps != null ){
                                            userNames += ps.getRelName()+ ",";
                                        }
                                    }
                                    if(!"".equals(userNames)){
                                        model.addAttribute("extUserName", userNames.substring(0, userNames.length()-1));
                                        model.addAttribute("extUserDate", listUsers.get(0).getCreatedAt());
                                    }
                                    
                                    //获取供应商抽取人
                                    FlowDefine fd = new FlowDefine();
                                    fd.setPurchaseTypeId(project.getPurchaseType());
                                    fd.setCode("CQGYS");
                                    List<FlowDefine> fds = flowMangeService.find(fd);
                                    FlowExecute fe = new FlowExecute();
                                    fe.setProjectId(project.getId());
                                    fe.setFlowDefineId(fds.get(0).getId());
                                    fe.setStatus(1);
                                    List<FlowExecute> fes = flowMangeService.findFlowExecute(fe);
                                    if(fes != null && fes.size() > 0){
                                        model.addAttribute("extUserNames", fes.get(0).getOperatorName());
                                    } else {
                                        fe.setStatus(3);
                                        List<FlowExecute> findFlowExecutes1 = flowMangeService.findFlowExecute(fe);
                                        if(findFlowExecutes1 != null && findFlowExecutes1.size() > 0){
                                            model.addAttribute("extUserNames", findFlowExecutes1.get(0).getOperatorName());
                                        } else {
                                            execute.setStatus(2);
                                            List<FlowExecute> executes1 = flowMangeService.findFlowExecute(execute);
                                            if(executes1 != null && executes1.size() > 0){
                                                model.addAttribute("extUserNames", executes1.get(0).getOperatorName());
                                            }
                                        }
                                    }
                                }
                            }
                            
                            model.addAttribute("code", findById);
                            
                            //获取抽取专家监督人
                            List<ProExtSupervise>  listUser = projectSupervisorService.list(new ProExtSupervise(project.getId()));
                            String userName = "";
                            if(listUser != null && listUser.size() > 0){
                                for (ProExtSupervise ps : listUser) {
                                    if (ps != null ){
                                      userName += ps.getRelName()+ ",";
                                    }
                                  }
                                  if(!"".equals(userName)){
                                    model.addAttribute("userName", userName.substring(0, userName.length()-1));
                                    model.addAttribute("userDate", listUser.get(0).getCreatedAt());
                                  }
                                  
                                  //获取专家抽取人
                                  FlowDefine fd = new FlowDefine();
                                  fd.setPurchaseTypeId(project.getPurchaseType());
                                  fd.setCode("ZZZJPS");
                                  List<FlowDefine> fds = flowMangeService.find(fd);
                                  FlowExecute fe = new FlowExecute();
                                  fe.setProjectId(project.getId());
                                  fe.setFlowDefineId(fds.get(0).getId());
                                  fe.setStatus(1);
                                  List<FlowExecute> fes = flowMangeService.findFlowExecute(fe);
                                  if(fes != null && fes.size() > 0){
                                      model.addAttribute("userNames", fes.get(0).getOperatorName());
                                  } else {
                                      fe.setStatus(3);
                                      List<FlowExecute> findFlowExecutes1 = flowMangeService.findFlowExecute(fe);
                                      if(findFlowExecutes1 != null && findFlowExecutes1.size() > 0){
                                          model.addAttribute("userNames", findFlowExecutes1.get(0).getOperatorName());
                                      } else {
                                          execute.setStatus(2);
                                          List<FlowExecute> executes1 = flowMangeService.findFlowExecute(execute);
                                          if(executes1 != null && executes1.size() > 0){
                                              model.addAttribute("userNames", executes1.get(0).getOperatorName());
                                          }
                                      }
                                  }
                            }
                            
                            
                            
                            FlowDefine fd = new FlowDefine();
                            fd.setPurchaseTypeId(project.getPurchaseType());
                            fd.setCode("ZZZJPS");
                            List<FlowDefine> fds = flowMangeService.find(fd);
                            if(fds != null && fds.size() > 0){
                                FlowExecute fe = new FlowExecute();
                                fe.setProjectId(project.getId());
                                fe.setFlowDefineId(fds.get(0).getId());
                                fe.setStatus(3);
                                List<FlowExecute> fes = flowMangeService.findFlowExecute(fe);
                                if(fes != null && fes.size() > 0){
                                    model.addAttribute("fes", "0");
                                } else {
                                    model.addAttribute("fes", "1");
                                }
                            }
                            
                            //确认中标供应商
                            SupplierCheckPass pass = new SupplierCheckPass();
                            pass.setPackageId(selectById.get(0).getPackageId());
                            pass.setIsWonBid((short)1);
                            pass.setProjectId(project.getId());
                            List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(pass);
                            if(listCheckPass != null && listCheckPass.size() > 0){
                                for (SupplierCheckPass supplierCheckPass : listCheckPass) {
                                    Supplier supplier = supplierService.selectById(supplierCheckPass.getSupplierId());
                                    if(supplier != null){
                                        supplierCheckPass.setSupplierId(supplier.getSupplierName());
                                    }
                                }
                                model.addAttribute("listCheckPass", listCheckPass);
                            }
                            
                            
                            
                            //合同信息
                            List<ContractRequired> contractRequireds = contractRequiredService.selectConRequByDetailId(selectById.get(0).getId());
                            if(contractRequireds!=null&&contractRequireds.size()>0){
                                PurchaseContract purchaseContract = contractService.selectById(contractRequireds.get(0).getContractId());
                                if(purchaseContract.getPurchaseDepName()!=null){
                                    Orgnization org1 = orgnizationService.getOrgByPrimaryKey(purchaseContract.getPurchaseDepName());
                                    if(org1!=null){
                                        purchaseContract.setPurchaseDepName(org1.getShortName());
                                        purchaseContract.setPurchaseBankAccount_string(org1.getFax());
                                    }else{
                                        purchaseContract.setPurchaseDepName("");
                                    }
                                    
                                }
                                if(purchaseContract.getSupplierDepName()!=null){
                                     Supplier supplier = supplierService.selectById(purchaseContract.getSupplierDepName());
                                     if(supplier!=null){
                                         purchaseContract.setSupplierDepName(supplier.getSupplierName());
                                         purchaseContract.setSupplierBankAccount_string(supplier.getContactFax());
                                     }else{
                                         purchaseContract.setSupplierDepName("");
                                     }
                                     
                                }
                                HashMap<String, Object> mapPq = new HashMap<>();
                                mapPq.put("contract", purchaseContract);
                                List<PqInfo> selectByCondition = pqInfoService.selectByContract(mapPq);
                                if(selectByCondition != null && selectByCondition.size() > 0){
                                    model.addAttribute("PqInfo", selectByCondition.get(0));
                                }
                                model.addAttribute("purchaseContract", purchaseContract);
                            }
                            
                            Packages packages = packageService.selectByPrimaryKeyId(selectById.get(0).getPackageId());
                            model.addAttribute("expertIdList", expertIdList);
                            model.addAttribute("experts", experts);
                            model.addAttribute("packageId", selectById.get(0).getPackageId());
                            model.addAttribute("packages", packages);
                        }
                                  
                        
                        model.addAttribute("project", project);
                        model.addAttribute("status", "0");
                    }
                } else {/*
                    //如果有采购计划但没有立项还是查看预研项目
                    AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(id);
                    if(advancedDetail != null){
                        //预研项目
                        AdvancedProject project = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
                        if(project != null && !"0".equals(project.getStatus())){
                            DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                            Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                            User user = userService.getUserById(project.getPrincipal());
                            project.setAppointMan(user.getRelName());
                            project.setIpone(user.getMobile());
                            project.setPurchaseDepName(org.getShortName());
                            project.setStatus(findById.getName());
                            //项目审批文件
                            String id2 = DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS");
                            List<UploadFile> list = uploadService.getFilesOther(project.getId(), id2, "2");
                            if(list != null && list.size() > 0){
                                model.addAttribute("uploadFile", "0");
                            }else{
                                model.addAttribute("uploadFile", "1");
                            }
                            model.addAttribute("uploadId", id2); 
                            model.addAttribute("code", findById);
                            model.addAttribute("project", project);
                            model.addAttribute("status","1");
                            
                            
                            //根据预研项目ID查询预研任务
                            HashMap<String, Object> mapTask = new HashMap<>();
                            mapTask.put("projectId", project.getId());
                            List<ProjectTask> projectTasks = projectTaskService.queryByNo(mapTask);
                            if(projectTasks != null && projectTasks.size() > 0){
                                Task task2 = taskService.selectById(projectTasks.get(0).getTaskId());
                                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task2.getPurchaseId());
                                task2.setPurchaseId(orgnization.getShortName());
                                User user2 = userService.getUserById(task2.getUserId());
                                task2.setUserId(user2.getRelName());
                                model.addAttribute("task", task2);
                            }
                            
                            //根据预研项目ID查询采购文件
                            //判断是否上传招标文件
                            String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                            List<UploadFile> files = uploadService.getFilesOther(project.getId(), typeId, Constant.TENDER_SYS_KEY+"");
                            if(files != null && files.size() > 0){
                              //调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
                                String filePath = extUserService.downLoadBiddingDocs(request,project.getId(),1,null);
                                if (StringUtils.isNotBlank(filePath)){
                                  model.addAttribute("filePath", filePath);
                                }
                                model.addAttribute("fileId", files.get(0).getId());
                                model.addAttribute("fileName", files.get(0).getName());
                            }
                            
                            if(!"DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
                                //获取采购文件的编制人
                                DictionaryData dd = new DictionaryData();
                                dd.setPosition(3);
                                dd.setCode(project.getPurchaseType());
                                List<DictionaryData> lists = dataService.list(dd);
                                FlowExecute execute = new FlowExecute();
                                execute.setProjectId(project.getId());
                                execute.setFlowDefineId(lists.get(0).getId());
                                execute.setStatus(1);
                                List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
                                if(findFlowExecute != null && findFlowExecute.size() > 0){
                                    model.addAttribute("operatorName", findFlowExecute.get(0).getOperatorName());
                                } else {
                                    execute.setStatus(3);
                                    List<FlowExecute> findFlowExecutes = flowMangeService.findFlowExecute(execute);
                                    if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                                        model.addAttribute("operatorName", findFlowExecutes.get(0).getOperatorName());
                                    } else {
                                        execute.setStatus(2);
                                        List<FlowExecute> executes = flowMangeService.findFlowExecute(execute);
                                        if(executes != null && executes.size() > 0){
                                            model.addAttribute("operatorName", executes.get(0).getOperatorName());
                                        }
                                    }
                                }
                            }
                        }
                    }
                */}
                
            } else {/*

                //假如没有采购计划，查一下有没有预研项目
                AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(id);
                if(advancedDetail != null){
                    //预研项目
                    AdvancedProject project = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
                    if(project != null && !"0".equals(project.getStatus())){
                        DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                        Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                        User user = userService.getUserById(project.getPrincipal());
                        project.setAppointMan(user.getRelName());
                        project.setIpone(user.getMobile());
                        project.setPurchaseDepName(org.getShortName());
                        project.setStatus(findById.getName());
                        //项目审批文件
                        String id2 = DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS");
                        List<UploadFile> list = uploadService.getFilesOther(project.getId(), id2, "2");
                        if(list != null && list.size() > 0){
                            model.addAttribute("uploadFile", "0");
                        }else{
                            model.addAttribute("uploadFile", "1");
                        }
                        model.addAttribute("uploadId", id2); 
                        model.addAttribute("code", findById);
                        model.addAttribute("project", project);
                        model.addAttribute("status","1");
                        
                        
                        //根据预研项目ID查询预研任务
                        HashMap<String, Object> mapTask = new HashMap<>();
                        mapTask.put("projectId", project.getId());
                        List<ProjectTask> projectTasks = projectTaskService.queryByNo(mapTask);
                        if(projectTasks != null && projectTasks.size() > 0){
                            Task task2 = taskService.selectById(projectTasks.get(0).getTaskId());
                            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task2.getPurchaseId());
                            task2.setPurchaseId(orgnization.getShortName());
                            User user2 = userService.getUserById(task2.getUserId());
                            task2.setUserId(user2.getRelName());
                            model.addAttribute("task", task2);
                        }
                        
                        //根据预研项目ID查询采购文件
                        //判断是否上传招标文件
                        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                        List<UploadFile> files = uploadService.getFilesOther(project.getId(), typeId, Constant.TENDER_SYS_KEY+"");
                        if(files != null && files.size() > 0){
                          //调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
                            String filePath = extUserService.downLoadBiddingDoc(request,project.getId(),1,null);
                            if (StringUtils.isNotBlank(filePath)){
                              model.addAttribute("filePath", filePath);
                            }
                            model.addAttribute("fileId", files.get(0).getId());
                            model.addAttribute("fileName", files.get(0).getName());
                        }
                        
                        if(!"DYLY".equals(DictionaryDataUtil.findById(project.getPurchaseType()).getCode())){
                            //获取采购文件的编制人
                            DictionaryData dd = new DictionaryData();
                            dd.setPosition(3);
                            dd.setCode(project.getPurchaseType());
                            List<DictionaryData> lists = dataService.list(dd);
                            FlowExecute execute = new FlowExecute();
                            execute.setProjectId(project.getId());
                            execute.setFlowDefineId(lists.get(0).getId());
                            execute.setStatus(1);
                            List<FlowExecute> findFlowExecute = flowMangeService.findFlowExecute(execute);
                            if(findFlowExecute != null && findFlowExecute.size() > 0){
                                model.addAttribute("operatorName", findFlowExecute.get(0).getOperatorName());
                            } else {
                                execute.setStatus(3);
                                List<FlowExecute> findFlowExecutes = flowMangeService.findFlowExecute(execute);
                                if(findFlowExecutes != null && findFlowExecutes.size() > 0){
                                    model.addAttribute("operatorName", findFlowExecutes.get(0).getOperatorName());
                                } else {
                                    execute.setStatus(2);
                                    List<FlowExecute> executes = flowMangeService.findFlowExecute(execute);
                                    if(executes != null && executes.size() > 0){
                                        model.addAttribute("operatorName", executes.get(0).getOperatorName());
                                    }
                                }
                            }
                        }
                    }
                }
            
            */}
            
            //需求监督进到这里
            PurchaseRequired required = null;
            if(detail != null){
                required = requiredService.queryById(detail.getId());
            }else{
                required = requiredService.queryById(id);
            }
            
            if(required != null){
                //查询采购需求
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", required.getId());
                List<PurchaseRequired> requireds = requiredService.selectByParent(map);
                if(requireds != null && requireds.size() > 0){
                    for (PurchaseRequired purchaseRequired : requireds) {
                        if("1".equals(purchaseRequired.getParentId())){
                            User user = userService.getUserById(purchaseRequired.getUserId());
                            if(user != null){
                                purchaseRequired.setUserId(user.getRelName());
                                purchaseRequired.setCode(user.getMobile());
                            }
                            model.addAttribute("purchaseRequired", purchaseRequired);
                            break;
                        }
                    }
                }
                
                
                if(StringUtils.isNotBlank(required.getOrganization())){
                    Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(required.getOrganization());
                    required.setOrganization(orgnization.getShortName());
                }
                required.setPurchaseType(DictionaryDataUtil.findById(required.getPurchaseType()).getName());
                
                List<PurchaseManagement> queryByPid = managementService.queryByPid(required.getUniqueId());
                if(queryByPid != null && queryByPid.size() > 0){
                    Orgnization org= orgnizationService.getOrgByPrimaryKey(queryByPid.get(0).getManagementId());
                    model.addAttribute("management", org.getShortName());//管理部门
                }
                HashMap<String, Object> mapAudit = new HashMap<>();
                mapAudit.put("collectId", required.getUniqueId());
                mapAudit.put("type", "4");
                List<AuditPerson> selectByMap = auditPersonService.selectByMap(mapAudit);
                if(selectByMap != null && selectByMap.size() > 0){
                    User user = userService.getUserById(selectByMap.get(0).getUserId());
                    selectByMap.get(0).setUserId(user.getRelName());
                    model.addAttribute("auditPerson", selectByMap.get(0));//受理人和受理时间
                }
                
                model.addAttribute("required", required);
                
                
                
                //预研信息
                AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(required.getId());
                if(advancedDetail != null){
                    AdvancedProject advancedProject = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
                    if(advancedProject != null && !"0".equals(advancedProject.getStatus())){
                        //预研任务
                        HashMap<String, Object> maps = new HashMap<>();
                        maps.put("projectId", advancedProject.getId());
                        List<ProjectTask> queryByNo = projectTaskService.queryByNo(maps);
                        Task task = taskService.selectById(queryByNo.get(0).getTaskId());
                        if(task != null){
                            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task.getOrgId());
                            Orgnization org = orgnizationService.getOrgByPrimaryKey(task.getPurchaseId());
                            User user = userService.getUserById(task.getCreaterId());
                            if(StringUtils.isNotBlank(task.getUserId())){
                                User users = userService.getUserById(task.getUserId());
                                task.setUserId(users.getRelName());
                            }
                            task.setPurchaseId(org.getShortName());
                            task.setOrgName(orgnization.getShortName());
                            task.setCreaterId(user.getRelName());
                            
                            model.addAttribute("tasks", task);
                        }
                        
                        //预研项目
                        if(StringUtils.isNotBlank(advancedProject.getPurchaseDepId())){
                            Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(advancedProject.getPurchaseDepId());
                            DictionaryData findById = DictionaryDataUtil.findById(advancedProject.getStatus());
                            if(!"YYYBYY".equals(findById.getCode())){
                                model.addAttribute("YYYBYY", "0");
                            }else{
                                model.addAttribute("YYYBYY", "1");
                            }
                            advancedProject.setPurchaseDepName(orgnization.getShortName());
                        }
                        if(StringUtils.isNotBlank(advancedProject.getAppointMan())){
                            User user = userService.getUserById(advancedProject.getAppointMan());
                            advancedProject.setAppointMan(user.getRelName());
                        }
                        model.addAttribute("advancedProject", advancedProject);
                    }
                }
            } else {/*
                //预研明细ID
                AdvancedDetail selectById = advancedDetailService.selectByRequiredId(id);
                PurchaseRequired requireds = requiredService.queryById(selectById.getRequiredId());
                if(requireds != null){
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", requireds.getId());
                    List<PurchaseRequired> req = requiredService.selectByParent(maps);
                    for (PurchaseRequired purchaseRequired : req) {
                        if("1".equals(purchaseRequired.getParentId())){
                            User user = userService.getUserById(purchaseRequired.getUserId());
                            purchaseRequired.setUserId(user.getRelName());
                            purchaseRequired.setCode(user.getMobile());
                            model.addAttribute("purchaseRequired", purchaseRequired);
                            break;
                        }
                    }
                    
                    if(StringUtils.isNotBlank(requireds.getOrganization())){
                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(requireds.getOrganization());
                        requireds.setOrganization(orgnization.getShortName());
                    }
                    requireds.setPurchaseType(DictionaryDataUtil.findById(requireds.getPurchaseType()).getName());
                    
                    List<PurchaseManagement> queryByPid = managementService.queryByPid(requireds.getUniqueId());
                    if(queryByPid != null && queryByPid.size() > 0){
                        Orgnization org= orgnizationService.getOrgByPrimaryKey(queryByPid.get(0).getManagementId());
                        model.addAttribute("management", org.getShortName());//管理部门
                    }
                    HashMap<String, Object> mapAudit = new HashMap<>();
                    mapAudit.put("collectId", requireds.getUniqueId());
                    mapAudit.put("type", "4");
                    List<AuditPerson> selectByMap = auditPersonService.selectByMap(mapAudit);
                    if(selectByMap != null && selectByMap.size() > 0){
                        User user = userService.getUserById(selectByMap.get(0).getUserId());
                        selectByMap.get(0).setUserId(user.getRelName());
                        model.addAttribute("auditPerson", selectByMap.get(0));//受理人和受理时间
                    }
                    
                    model.addAttribute("required", requireds);
                }
                
                
                //预研项目信息
                AdvancedProject advancedProject = advancedProjectService.selectById(selectById.getAdvancedProject());
                if(advancedProject != null){
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("projectId", advancedProject.getId());
                    List<ProjectTask> queryByNo = projectTaskService.queryByNo(maps);
                    Task task = taskService.selectById(queryByNo.get(0).getTaskId());
                    if(task != null){
                        Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(task.getOrgId());
                        task.setOrgName(orgnization.getShortName());
                        User user = userService.getUserById(task.getCreaterId());
                        task.setCreaterId(user.getRelName());
                        model.addAttribute("tasks", task);//任务
                    }
                    model.addAttribute("advancedProject", advancedProject);//预研项目
                }
            */}
            
            String adviceId = DictionaryDataUtil.getId("ADVANCED_ADVICE");
            
            model.addAttribute("adviceId", adviceId);//预研通知书
            model.addAttribute("detailId", detail.getId());
            model.addAttribute("detail", detail);
        }
        
        model.addAttribute("number", "1");
        
        return "sums/ss/planSupervision/overview";
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param type 1 跳转文件发售 2 跳转投标记录
     * @param model
     * @return
     */
    @RequestMapping("/viewSell")
    public String viewSell(String packageId, String type, Model model){
        if(StringUtils.isNotBlank(packageId)){
            String fileId = DictionaryDataUtil.getId("OPEN_FILE");
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
            for (SaleTender saleTender2 : saleTenderList) {
                Supplier supplier = supplierService.selectById(saleTender2.getSuppliers().getId());
                supplier.setProSupFile(saleTender2.getId());
                if(saleTender2.getIsTurnUp() != null){
                    supplier.setIsturnUp(saleTender2.getIsTurnUp().toString());
                }
                List<UploadFile> blist = uploadService.getFilesOther(supplier.getProSupFile(), fileId,  Constant.SUPPLIER_SYS_KEY.toString());
                if (blist != null && blist.size() > 0) {
                    supplier.setBidFileName(blist.get(0).getName());
                    supplier.setBidFileId(blist.get(0).getId());
                  }
            }
            packages.setSaleTenderList(saleTenderList);
            model.addAttribute("packages", packages);
        }
        if(StringUtils.isNotBlank(type) && "1".equals(type)){
            return "sums/ss/planSupervision/viewSell";
        }else{
            return "sums/ss/planSupervision/viewSupplier";
        }
        
    }
    
    
    /**
     * 
     *〈查看唱标〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/bidAnnouncement")
    public String bidAnnouncement(String packageId, Model model){
        if(StringUtils.isNotBlank(packageId)){
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            saleTender.setStatusBid((short)2);
            saleTender.setStatusBond((short)2);
            saleTender.setIsTurnUp(0);
            List<SaleTender> stList = saleTenderService.find(saleTender);
            if(stList != null && stList.size() > 0){
                Quote quote = new Quote();
                quote.setPackageId(packageId);
                quote.setSupplierId(stList.get(0).getSupplierId());
                List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                if (allQuote != null && allQuote.size() > 0) {
                  if (allQuote.get(0).getQuotePrice() == null) {
                    return "redirect:changtotal.html?packageId=" + packageId;
                  } else {
                    return "redirect:changmingxi.html?packageId=" + packageId;
                  }
                }
            }
        }
        return null;
    }
    
    /**
     * 
     *〈查看唱总价〉
     *〈详细描述〉
     * @author Administrator
     * @param packageId
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/changtotal")
    public String changtotal(String packageId, Model model) throws ParseException{
        if(StringUtils.isNotBlank(packageId)){
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            Project project = projectService.selectById(packages.getProjectId());
            DictionaryData dictionaryData = null;
            if (project != null && project.getPurchaseType() != null ){
                dictionaryData = DictionaryDataUtil.findById(project.getPurchaseType());
            }
            HashMap<String, Object> map = new HashMap<>();
            //这里用这个是因为hashMap是无序的
            TreeMap<String ,List<SaleTender>> treeMap = new TreeMap<String ,List<SaleTender>>();
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            saleTender.setStatusBid((short)2);
            saleTender.setStatusBond((short)2);
            saleTender.setIsTurnUp(0);
            List<SaleTender> stList = saleTenderService.find(saleTender);
            
            map.put("packageId", packageId);
            List<ProjectDetail> details = projectDetailService.selectById(map);
            BigDecimal projectBudget = BigDecimal.ZERO;
            for (ProjectDetail projectDetail : details) {
                projectBudget = projectBudget.add(new BigDecimal(projectDetail.getBudget()));
            }
            
            Quote quote1 = new Quote();
            Quote quote2 = new Quote();
            quote1.setPackageId(packageId);
            List<Date> listDate1 = supplierQuoteService.selectQuoteCount(quote1);
            
            List<Quote> listQuotebyPackage1 = new ArrayList<Quote>();
            if (listDate1 != null && listDate1.size() > 1) {
              //给第二次报价的数据查到
              if ("JZXTP".equals(dictionaryData.getCode()) || "DYLY".equals(dictionaryData.getCode())) {
                quote2.setPackageId(packageId);
                quote2.setCreatedAt(new Timestamp(listDate1.get(listDate1.size()-1).getTime()));
                listQuotebyPackage1 = supplierQuoteService.selectQuoteHistoryList(quote2);
              }
            }
            
            for (SaleTender tender : stList) {
                Quote quote = new Quote();
                quote.setPackageId(packageId);
                quote.setSupplierId(saleTender.getSupplierId());
                if (listDate1 != null && listDate1.size() > 0) {
                  quote.setCreatedAt(new Timestamp(listDate1.get(0).getTime()));
                }
                if ("0".equals(saleTender.getIsRemoved())) {
                    tender.setIsRemoved("正常");
                }
                if ("2".equals(saleTender.getIsRemoved())) {
                    tender.setIsRemoved("放弃报价");
                }
                
                List<Quote> allQuote = supplierQuoteService.getAllQuote(quote, 1);
                if(allQuote != null && allQuote.size() > 0){
                    for (Quote conditionQuote : allQuote) {
                        if (conditionQuote.getSupplierId().equals(tender.getSuppliers().getId()) &&
                            conditionQuote.getProjectId().equals(tender.getProject().getId()) && tender.getPackages().equals(conditionQuote.getPackageId())) {
                            for (Quote qp : listQuotebyPackage1) {
                                if (qp.getPackageId().equals(conditionQuote.getPackageId()) && qp.getSupplierId().equals(conditionQuote.getSupplierId())) {
                                    conditionQuote.setTotal(qp.getTotal());
                                    conditionQuote.setQuotePrice(qp.getQuotePrice());
                                    conditionQuote.setRemark(qp.getRemark());
                                    conditionQuote.setDeliveryTime(qp.getDeliveryTime());
                                }
                            }
                            tender.setTotal(conditionQuote.getTotal());
                            tender.setDeliveryTime(conditionQuote.getDeliveryTime());
                            tender.setIsTurnUp(conditionQuote.getIsTurnUp());
                            tender.setQuoteId(conditionQuote.getId());
                            break;
                        }
                    }
                
                }
            }
            
            if (stList != null && stList.size() > 0) {
                treeMap.put(packages.getName()+"|"+projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP), stList);
            }
            model.addAttribute("treeMap", treeMap);
        }
        return "sums/ss/planSupervision/view_chang_total";
    }
    
    /**
     * 
     *〈查看唱明细〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param model
     * @return
     * @throws ParseException 
     */
    @RequestMapping("/changmingxi")
    public String changmingxi(String packageId, Model model) throws ParseException{
        if(StringUtils.isNotBlank(packageId)){
            Packages packages = packageService.selectByPrimaryKeyId(packageId);
            Project project = projectService.selectById(packages.getProjectId());
            DictionaryData dd = null;
            if (project != null && project.getPurchaseType() != null ){
              dd = DictionaryDataUtil.findById(project.getPurchaseType());
            }
            
            Quote quote = new Quote();
            Quote quote1 = new Quote();
            Quote quote2 = new Quote();
            quote2.setPackageId(packageId);
            List<Date> listDate =  supplierQuoteService.selectQuoteCount(quote2);
            List<Quote> listQuotebyPackage = new ArrayList<Quote>();
            if (listDate != null && listDate.size() > 1) {
                if ("JZXTP".equals(dd.getCode()) || "DYLY".equals(dd.getCode())) {
                  quote1.setPackageId(packageId);
                  quote1.setCreatedAt(new Timestamp(listDate.get(listDate.size()-1).getTime()));
                  listQuotebyPackage = supplierQuoteService.selectQuoteHistoryList(quote1);
                } 
            }
            if (listDate != null && listDate.size() > 0) {
                quote.setCreatedAt(new Timestamp(listDate.get(0).getTime()));
            }
            quote.setPackageId(packageId);
            List<Quote> listQuote=supplierQuoteService.selectQuoteHistoryList(quote);
            List<Supplier> suList = setField(listQuote);
            packages.setSuList(suList);
            BigDecimal projectBudget = BigDecimal.ZERO;
            if (packages.getSuList() != null && packages.getSuList().size() > 0) {
              for (Quote q : packages.getSuList().get(0).getQuoteList()) {
                if (q.getProjectDetail() != null) {
                  projectBudget = projectBudget.add(new BigDecimal(q.getProjectDetail().getBudget()));
                }
              }
            }
            //项目预算
            packages.setProjectBudget(projectBudget.setScale(4, BigDecimal.ROUND_HALF_UP));
            setNewQuote(listQuote, listQuotebyPackage);
            model.addAttribute("packages", packages);
        }
        return "sums/ss/planSupervision/view_changbiao";
    }
    
    /**
     * 
     *〈查看单一来源谈判记录〉
     *〈详细描述〉
     * @author FengTian
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("/report")
    public String report(String packageId, Model model){
        if(StringUtils.isNotBlank(packageId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("packageId", packageId);
            List<PackageExpert> expertSigneds = packageExpertService.selectList(map); 
            if(expertSigneds != null && expertSigneds.size() > 0){
                Packages packages = packageService.selectByPrimaryKeyId(packageId);
                Project project = projectService.selectById(packages.getProjectId());
                NegotiationReport report =  reportService.selectByPackageId(packageId);
                SupplierCheckPass checkPass = new SupplierCheckPass();
                checkPass.setIsWonBid((short)1);
                checkPass.setPackageId(packageId);
                List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(checkPass);
                if(listCheckPass != null && listCheckPass.size() > 0){
                  packages.setSupplierList(listCheckPass);
                }else{
                  packages.setSupplierList(new ArrayList<SupplierCheckPass>());
                }

                if (report != null) {
                  packages.setNegotiationReport(report);
                }else{
                  packages.setNegotiationReport(new NegotiationReport());
                }
                model.addAttribute("packages", packages);
                model.addAttribute("project", project);
                model.addAttribute("expertSigneds", expertSigneds);
            }
        }
        return "sums/ss/planSupervision/negotiation_report";
    }
    
    
    /**
     * 
     *〈获取采购计划审核意见〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewAuditPerson")
    public String viewAuditPerson(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            PurchaseDetail detail = detailService.queryById(id);
            if(detail != null){
                if(StringUtils.isNotBlank(type) && "1".equals(type)){
                    model.addAttribute("advice", detail.getOneAdvice());
                } else if (StringUtils.isNotBlank(type) && "2".equals(type)){
                    model.addAttribute("advice", detail.getTwoAdvice());
                } else if (StringUtils.isNotBlank(type) && "3".equals(type)){
                    model.addAttribute("advice", detail.getThreeAdvice());
                }
            }
        }
        return "sums/ss/planSupervision/viewAuditPerson";
    }
    
    @RequestMapping("/bidFileView")
    public String bidFileView(HttpServletRequest request, String id, Model model, HttpServletResponse response){
      Project project = projectService.selectById(id);
      //判断是否上传招标文件
      String typeId = DictionaryDataUtil.getId("PROJECT_BID");
      List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
      if (files != null && files.size() > 0){
        model.addAttribute("fileId", files.get(0).getId());
      } else {
        model.addAttribute("fileId", "0");
      }
      model.addAttribute("project", project);
      return "sums/ss/planSupervision/add_file";
    }
    
    /**
     * 
     *〈查看中标供应商评分排序〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param packageId
     * @param model
     * @return
     */
    @RequestMapping("/graded")
    public String graded(String id, String packageId, Model model){
        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(packageId)){
            Packages pack = packageService.selectByPrimaryKeyId(packageId);
            Supplier supplier = new Supplier();
            supplier.setId(id);
            SaleTender saleTender = new SaleTender();
            saleTender.setSuppliers(supplier);
            saleTender.setPackages(packageId);
            saleTender.setIsFirstPass(1);
            saleTender.setIsRemoved("0");
            saleTender.setIsTurnUp(0);
            List<SaleTender> findByCon = saleTenderService.findByCon(saleTender);
            List<SaleTender> suppList = new ArrayList<SaleTender>();
            if(findByCon != null && findByCon.size() > 0){
                String methodCode = bidMethodService.getMethod(pack.getProjectId(), findByCon.get(0).getPackages());
                if (methodCode != null && !"".equals(methodCode)) {
                    if (("PBFF_JZJF".equals(methodCode))) {
                        net.sf.json.JSONObject obj = net.sf.json.JSONObject.fromObject(findByCon.get(0).getReviewResult());
                        Jzjf jzjf = (Jzjf) net.sf.json.JSONObject.toBean(obj,Jzjf.class);
                        findByCon.get(0).setJzjf(jzjf);
                    }
                    if (!"OPEN_ZHPFF".equals(methodCode)) {
                        BigDecimal pass = new BigDecimal(100);
                        //合格的供应商
                        if (findByCon.get(0).getEconomicScore() != null && findByCon.get(0).getTechnologyScore() != null && findByCon.get(0).getEconomicScore().compareTo(pass) == 0 && findByCon.get(0).getTechnologyScore().compareTo(pass) == 0) {
                            suppList.add(findByCon.get(0));
                        }
                    } else {
                        suppList.add(findByCon.get(0));
                    }
                    
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("packageId", pack.getId());
                    List<PackageExpert> selectList = packageExpertService.selectList(map);
                    // 将专家进行排序,先经济,后技术
                    List<PackageExpert> expertList = new ArrayList<PackageExpert>();
                    for (PackageExpert packageExpert : selectList) {
                        DictionaryData findById = DictionaryDataUtil.findById(packageExpert.getReviewTypeId());
                        if (findById != null && "ECONOMY".equals(findById.getCode())) {
                            expertList.add(packageExpert);
                        }
                    }
                    for (PackageExpert exp : selectList) {
                        DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                        if (data != null && "TECHNOLOGY".equals(data.getCode())) {
                            expertList.add(exp);
                        }
                    }
                    
                    // 获取经济类型的个数
                    int count = 0;
                    // 该包内的专家总数
                    int sumCount = 0;
                    for (PackageExpert exp : expertList) {
                        if (pack.getId().equals(exp.getPackageId())) {
                            sumCount++;
                            DictionaryData data = DictionaryDataUtil.findById(exp.getReviewTypeId());
                            if (data != null && "ECONOMY".equals(data.getCode())) {
                                count++;
                            }
                        }
                    }
                    // 给指定位置设置rowspan
                    int flag = 0;
                    for (PackageExpert exp : expertList) {
                        if (pack.getId().equals(exp.getPackageId())) {
                            if (count == 0 && flag == 0) {
                                // 如果没有经济类型,只有技术类型
                                exp.setCount(sumCount);
                            } else if (count == sumCount && flag == 0) {
                                // 如果全是经济类型
                                exp.setCount(sumCount);
                            } else if (count < sumCount && count > 0) {
                                // 都有
                                if (flag == 0) {
                                    // 设置第一个rowspan为经济的个数
                                    exp.setCount(count);
                                } else if (flag == count) {
                                    // 设置第一个技术类型的rowspan为全部减去经济的个数
                                    exp.setCount(sumCount - count);
                                } else {
                                    exp.setCount(0);
                                };
                            } else {
                                exp.setCount(0);
                            }
                            flag++;
                        }
                    }
                    
                    // 将reviewTypeId的值改为name
                    for (PackageExpert expert : expertList) {
                        DictionaryData data = DictionaryDataUtil.findById(expert.getReviewTypeId());
                        if (data != null) {
                            expert.setReviewTypeId(data.getName());
                        }
                    }
                    HashMap<String, Object> searchMap = new HashMap<>();
                    // 专家给每个供应商打得分
                    searchMap.put("projectId", pack.getProjectId());
                    searchMap.put("packageId", pack.getId());
                    searchMap.put("supplierId", suppList.get(0).getSuppliers().getId());
                    List<ExpertSuppScore> expertScoreList = expertScoreService.getScoreByMap(searchMap);
                    model.addAttribute("expertScoreList", expertScoreList);
                    model.addAttribute("expertList", expertList);
                    
                    
                    // 供应商经济总分,技术总分,总分
                    SupplierRank rank = new SupplierRank();
                    rank.setSupplierId(suppList.get(0).getSuppliers().getId());
                    rank.setPackageId(suppList.get(0).getPackages());
                    BigDecimal es = suppList.get(0).getEconomicScore();
                    if (es == null) {
                      rank.setEconScore(null);
                    } else {
                      rank.setEconScore(es);
                    }
                    BigDecimal ts = suppList.get(0).getTechnologyScore();
                    if (ts == null) {
                      rank.setTechScore(null);
                    } else {
                      rank.setTechScore(ts);
                    }
                    if (es == null || ts == null) {
                      rank.setSumScore(null);
                    } else {
                      rank.setSumScore(suppList.get(0).getEconomicScore().add(suppList.get(0).getTechnologyScore()));
                    }
                    
                    
                    // 判断review_result是否不为空
                    SaleTender saleTend = new SaleTender();
                    saleTend.setPackages(rank.getPackageId());
                    Supplier suppliers = new Supplier();
                    suppliers.setId(rank.getSupplierId());
                    saleTend.setSuppliers(suppliers);
                    String reviewResult = saleTenderService.findByCon(saleTend).get(0).getReviewResult();
                    rank.setReviewResult(reviewResult);
                    
                    model.addAttribute("rank", rank);
                }
                model.addAttribute("methodCode", methodCode);
            }
            model.addAttribute("supplier", suppList.get(0));
            model.addAttribute("pack", pack);
        }
        return "sums/ss/planSupervision/view_graded";
    }
    
    /**
     * 
     *〈获取专家抽取记录〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param projectId
     * @param packageId
     * @param typeclassId
     * @return
     */
    @RequestMapping("/showRecord")
    public String showRecord(Model model,String projectId,String packageId){
      //专家类型
      model.addAttribute("ddList", expExtractRecordService.ddList());
      model.addAttribute("find", DictionaryDataUtil.find(12));
      model.addAttribute("projectId", projectId);
      model.addAttribute("packageId", packageId);
      //获取抽取记录
      ExpExtractRecord showExpExtractRecord = null;
      ExpExtractRecord record = new ExpExtractRecord();
      record.setProjectId(projectId);
      showExpExtractRecord =  expExtractRecordService.listExtractRecord(record,0).get(0);
      

      if (packageId != null && !"".equals(packageId)){
        //已抽取
        String[] packageIds =  packageId.split(",");
        if(packageIds.length != 0 ){
          ExpExtCondition con = null;
          for (String pckId : packageIds) {
            if(pckId != null && !"".equals(pckId)){
              con = new ExpExtCondition();
              con.setProjectId(pckId);
              con.setStatus((short)2);
              conditionService.update(con);
            }
          }
        }
      }

      model.addAttribute("ExpExtractRecord", showExpExtractRecord);
      if(showExpExtractRecord !=null){
        //抽取条件
        List<Packages> list = new ArrayList<Packages>();
        List<Packages> conList = packageService.listExpExtCondition(showExpExtractRecord.getProjectId());
        if(conList != null && conList.size() > 0){
            for (Packages packages : conList) {
                if(packageId.equals(packages.getId())){
                    list.add(packages);
                }
            }
        }
        model.addAttribute("conditionList", list);
        //获取监督人员
        List<ProExtSupervise>  listUser = projectSupervisorService.list(new ProExtSupervise(showExpExtractRecord.getProjectId()));
        model.addAttribute("listUser", listUser);
      }
      return "ses/ems/exam/expert/extract/extract_expert_word";
    }
    
    /**
     * 
     *〈供应商抽取记录〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param id
     * @param projectId
     * @param packageId
     * @return
     */
    @RequestMapping("/showRecords")
    public String showRecords(Model model, String projectId,String packageId){
        model.addAttribute("projectId", projectId);
        model.addAttribute("packageId", packageId);
        SupplierExtracts showExpExtractRecord=null;
        if (projectId != null && projectId != null){
            //获取抽取记录
            SupplierExtracts extracts = new SupplierExtracts();
            extracts.setProjectId(projectId);
            List<SupplierExtracts> listExtractRecord = supplierExtractsService.listExtractRecord(extracts,0);
            if(listExtractRecord != null && listExtractRecord.size() !=0){
                showExpExtractRecord = listExtractRecord.get(0);
                model.addAttribute("ExpExtractRecord", showExpExtractRecord);
                //获取监督人员
                List<SupplierExtUser> listUser = extUserService.list(new SupplierExtUser(showExpExtractRecord.getProjectId()));
                model.addAttribute("listUser", listUser);
                //抽取条件
                List<Packages> list = new ArrayList<Packages>();
                List<Packages> conList = packageService.listExpExtCondition(showExpExtractRecord.getProjectId());
                if(conList != null && conList.size() > 0){
                    for (Packages packages : conList) {
                        if(packageId.equals(packages.getId())){
                            list.add(packages);
                        }
                    }
                }
                model.addAttribute("conditionList", list);

                if (packageId != null && !"".equals(packageId)){
                    //已抽取
                    String[] packageIds =  packageId.split(",");
                    if(packageIds.length != 0 ){
                        SupplierCondition con = null;
                        for (String pckId : packageIds) {
                            if(pckId != null && !"".equals(pckId)){
                                con = new SupplierCondition();
                                con.setProjectId(pckId);
                                con.setStatus((short)2);
                                supplierConditionService.update(con);
                            }
                        }
                    }
                }

            }

        }
        return "ses/sms/supplier_extracts/extract_supervise_word";
    }
    
    /**
     * 
     *〈查看公告〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewArticle")
    public String viewArticle(String id, Model model){
        if(StringUtils.isNotBlank(id)){
            Article article = articleService.selectArticleById(id);
            model.addAttribute("article", article);
            model.addAttribute("sysKey", Constant.TENDER_SYS_KEY);
            model.addAttribute("typeId", DictionaryDataUtil.getId("GGWJ"));
        }
        return "sums/ss/planSupervision/view_article";
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
     * 
     *〈项目重新排序〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void sort(List<ProjectDetail> list){
        Collections.sort(list, new Comparator<ProjectDetail>(){
           @Override
           public int compare(ProjectDetail o1, ProjectDetail o2) {
              Integer i = o1.getPosition() - o2.getPosition();
              return i;
           }
        });
    }
    
    @RequestMapping(value="/paixu",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String paixu(Model model, String id, Integer page){
        JSONObject jsonObj = new JSONObject();
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<PurchaseDetail> details = detailService.getUnique(id,null,null);
        if(details != null && details.size() > 0){
            for (PurchaseDetail detail : details) { 
                if(detail.getPrice() == null){
                    detail.setPurchaseType("");
                    detail.setStatus(null);
                }else{
                    DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                    if(findById != null){
                        detail.setPurchaseType(findById.getName());
                    }
                    String[] progressBarPlan = supervisionService.progressBar(detail.getId());
                    detail.setProgressBar(progressBarPlan[0]);
                    detail.setStatus(progressBarPlan[1]);
                    detail.setOneAdvice(findById.getCode());
                }
            }
            PageInfo<PurchaseDetail> pageInfo = new PageInfo<PurchaseDetail>(details);
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
        } else {
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<PurchaseRequired> purchaseRequireds = requiredService.getUnique(id);
            if(purchaseRequireds != null && purchaseRequireds.size() > 0){
                for (PurchaseRequired purchaseRequired : purchaseRequireds) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", purchaseRequired.getId());
                    List<PurchaseRequired> purchaseDetails = requiredService.selectByParentId(map);
                    if(purchaseDetails.size() > 1){
                        purchaseRequired.setPurchaseType("");
                        purchaseRequired.setStatus(null);
                    }else{
                        DictionaryData findById = DictionaryDataUtil.findById(purchaseRequired.getPurchaseType());
                        if(findById != null){
                            purchaseRequired.setPurchaseType(findById.getName());
                        }
                        String[] progressBarPlan = supervisionService.progressBar(purchaseRequired.getId());
                        purchaseRequired.setProgressBar(progressBarPlan[0]);
                        purchaseRequired.setStatus(progressBarPlan[1]);
                        purchaseRequired.setOneAdvice(findById.getCode());
                    }
                }
            }
            PageInfo<PurchaseRequired> pageInfo = new PageInfo<PurchaseRequired>(purchaseRequireds);
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
        }
        
        return jsonObj.toString();
    }

}
