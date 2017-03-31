package sums.controller.ss;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.AuditPerson;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.FlowDefine;
import bss.model.ppms.FlowExecute;
import bss.model.ppms.NegotiationReport;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Reason;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.pqims.PqInfo;
import bss.model.prms.PackageExpert;
import bss.model.prms.ReviewProgress;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.AuditPersonService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedProjectService;
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
import bss.service.prms.PackageExpertService;
import bss.service.prms.ReviewProgressService;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.oms.Orgnization;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtracts;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;

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
            List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
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
                model.addAttribute("collectPlan", collectPlan);
            }
        }
        if ("0".equals(type)) {
            return "sums/ss/planSupervision/plan_view";
        } else {
            return "sums/ss/planSupervision/schedule_view";
        }
    }
    
    /**
     * 
     *〈跳转任务汇总页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/viewTask")
    public String viewTask(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            CollectPlan collectPlan = collectPlanService.queryById(id);
            if(collectPlan != null){
                HashMap<String, Object> map = new HashMap<>();
                User user = userService.getUserById(collectPlan.getUserId());
                map.put("collectId", id);
                List<Task> listBycollect = taskService.listBycollect(map);
                if(listBycollect != null){
                    collectPlan.setOrderAt(listBycollect.get(0).getGiveTime());
                }
                collectPlan.setUserId(user.getRelName());
                collectPlan.setPurchaseId(user.getOrgName());
                model.addAttribute("collectPlan", collectPlan);
            }
            
            //需求计划信息
            HashSet<String> set = new HashSet<>();
            List<PurchaseDetail> details = detailService.getUnique(id);
            HashMap<String, Object> maps = new HashMap<>();
            List<PurchaseRequired> listRequired = new ArrayList<>();
            if(details != null && details.size() > 0){
                for (PurchaseDetail detail : details) { 
                    maps.put("id", detail.getId());
                    List<PurchaseDetail> purchaseDetails = detailService.selectByParentId(maps);
                    if(purchaseDetails.size() > 1){
                        detail.setPurchaseType("");
                    }else{
                        DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                        detail.setPurchaseType(findById.getName());
                    }
                    set.add(detail.getFileId());
                }
                
                for (String string : set) {
                    maps.put("fileId", string);
                    List<PurchaseRequired> details3 = requiredService.getByMap(maps);
                    if(details3 != null && details3.size() > 0){
                        for (PurchaseRequired purchaseRequired : details3) {
                            if("1".equals(purchaseRequired.getParentId())){
                                User user = userService.getUserById(purchaseRequired.getUserId());
                                purchaseRequired.setUserId(user.getRelName());
                                listRequired.add(purchaseRequired);
                                break;
                            }
                        }
                    }
                }
                model.addAttribute("listRequired", listRequired);
                model.addAttribute("list", details);
            }
            
            //任务信息
            HashMap<String, Object> mapTask = new HashMap<>();
            mapTask.put("collectId", id);
            List<Task> task = taskService.listBycollect(mapTask);
            List<Project> listProject = new ArrayList<Project>();
            if(task != null && task.size() > 0){
                for (Task task2 : task) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("taskId", task2.getId());
                    List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
                    if(projectTasks != null && projectTasks.size() > 0){
                        for (ProjectTask projectTask : projectTasks) {
                            Project project = projectService.selectById(projectTask.getProjectId());
                            if(project != null){
                                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                                if(StringUtils.isNotBlank(project.getPrincipal())){
                                    User users = userService.getUserById(project.getPrincipal());
                                    project.setAppointMan(users.getRelName());
                                    project.setAddress(users.getAddress());
                                }
                                if(StringUtils.isNotBlank(project.getPurchaseDepId())){
                                    Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                                    project.setPurchaseDepId(org.getName());
                                }
                                project.setStatus(findById.getName());
                                listProject.add(project);
                            }
                        }
                    }
                    if(listProject != null && listProject.size() > 0){
                        List<PurchaseContract> listContract = new ArrayList<PurchaseContract>();
                        for (Project project : listProject) {
                            SupplierCheckPass checkPass = new SupplierCheckPass();
                            checkPass.setProjectId(project.getId());
                            checkPass.setIsWonBid((short)1);
                            List<SupplierCheckPass> checkPasses = checkPassService.listCheckPass(checkPass);
                            if(checkPasses != null && checkPasses.size() > 0){
                                for (SupplierCheckPass supplierCheckPass : checkPasses) {
                                    PurchaseContract contract = contractService.selectById(supplierCheckPass.getContractId());
                                    if(contract != null){
                                        listContract.add(contract);
                                    }
                                }
                            }
                        }
                        if(listContract != null && listContract.size() > 0){
                            for (PurchaseContract pur : listContract) {
                                Supplier su = null;
                                Orgnization org = null;
                                if(pur.getSupplierDepName()!=null){
                                    su = supplierService.selectOne(pur.getSupplierDepName());
                                }
                                if(pur.getPurchaseDepName()!=null){
                                    org = orgnizationService.getOrgByPrimaryKey(pur.getPurchaseDepName());
                                }
                                if(org!=null){
                                    if(org.getName()==null){
                                        pur.setShowDemandSector("");
                                    }else{
                                        pur.setShowDemandSector(org.getName());
                                    }
                                }
                                if(su!=null){
                                    if(su.getSupplierName()!=null){
                                        pur.setShowSupplierDepName(su.getSupplierName());
                                    }else{
                                        pur.setShowSupplierDepName("");
                                    }
                                }
                            }
                        }
                        model.addAttribute("listContract", listContract);
                        model.addAttribute("listProject", listProject);
                        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
                    }
                }
                model.addAttribute("task", task);
            }
            model.addAttribute("planId", id);
            model.addAttribute("type", type);
        }
        if(StringUtils.isNotBlank(type) && "0".equals(type)){
            return "sums/ss/planSupervision/task_view";
        }else if(StringUtils.isNotBlank(type) && "1".equals(type)){
            return "sums/ss/planSupervision/demand_view";
        }else if(StringUtils.isNotBlank(type) && "2".equals(type)){
            return "sums/ss/planSupervision/detail_view";
        }else if(StringUtils.isNotBlank(type) && "3".equals(type)){
            return "sums/ss/planSupervision/project_view";
        }else if(StringUtils.isNotBlank(type) && "4".equals(type)){
            return "sums/ss/planSupervision/contract_view";
        }
        return null;
    }
    
    /**
     * 
     *〈跳转明细页面〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewDetail")
    public String viewDetail(String id, String type, Model model){
        if(StringUtils.isNotBlank(id)){
            //类型为1的是采购计划明细，反之为需求计划明细
            if(StringUtils.isNotBlank(type) && "1".equals(type)){
                List<PurchaseDetail> details = detailService.getUnique(id);
                if(details != null && details.size() > 0){
                    HashMap<String, Object> map = new HashMap<>();
                    for (int i = 0; i < details.size(); i++ ) {
                        map.put("id", details.get(i).getId());
                        List<PurchaseDetail> purchaseDetails = detailService.selectByParentId(map);
                        if(purchaseDetails.size() > 1){
                            details.get(i).setPurchaseType("");
                        }else{
                            DictionaryData findById = DictionaryDataUtil.findById(details.get(i).getPurchaseType());
                            details.get(i).setPurchaseType(findById.getName());
                        }
                    }
                    model.addAttribute("list", details);
                }
            }else{
                List<PurchaseRequired> details = requiredService.getUnique(id);
                HashMap<String, Object> map = new HashMap<>();
                if(details != null && details.size() > 0){
                    for (int i = 0; i < details.size(); i++ ) {
                        map.put("id", details.get(i).getId());
                        List<PurchaseRequired> purchaseDetails = requiredService.selectByParentId(map);
                        if(purchaseDetails.size() > 1){
                            details.get(i).setPurchaseType("");
                        }else{
                            DictionaryData findById = DictionaryDataUtil.findById(details.get(i).getPurchaseType());
                            details.get(i).setPurchaseType(findById.getName());
                        }
                    }
                    model.addAttribute("list", details);
                }
            }
            model.addAttribute("type", type);
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
    public String viewPack(@CurrentUser User user, String id, String planId, String type, Model model){
        if(user != null && user.getOrg() != null){
            if(StringUtils.isNotBlank(type) && "1".equals(type)){
                HashMap<String, Object> map = new HashMap<>();
                List<PurchaseDetail> purchaseDetails = detailService.getUnique(planId);
                //查询计划明细顶级节点的ID
                String pid = null;
                if(purchaseDetails != null && purchaseDetails.size() > 0){
                    for (int i = 0; i < purchaseDetails.size(); i++ ) {
                        if("1".equals(purchaseDetails.get(i).getParentId())){
                            pid = purchaseDetails.get(i).getId();
                            break;
                        }
                    }
                }
                if(StringUtils.isNotBlank(pid)){
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", pid);
                    maps.put("projectId", id);
                    //根据项目ID和计划明细ID递归查询出所有的明细
                    List<ProjectDetail> details = projectDetailService.selectByParentId(maps);
                    if(details != null && details.size() > 0){
                        map.put("projectId", id);
                        List<Packages> packages = packageService.findByID(map);
                        List<Packages> lists = new ArrayList<Packages>();
                        //判断有没有分包，没有分包进else
                        if(packages != null && packages.size() > 0){
                            for (Packages packages2 : packages) {
                                List<ProjectDetail> list = new ArrayList<ProjectDetail>();
                                for (int i = 0; i < details.size(); i++ ) {
                                    if(packages2.getId().equals(details.get(i).getPackageId())){
                                        DictionaryData findById = DictionaryDataUtil.findById(details.get(i).getPurchaseType());
                                        details.get(i).setPurchaseType(findById.getName());
                                        list.add(details.get(i));
                                    }
                                    sort(list);//进行排序
                                    packages2.setProjectDetails(list);
                                }
                            }
                            for (int i = 0; i < packages.size(); i++ ) {
                                if(packages.get(i).getProjectDetails().size() > 1){
                                    lists.add(packages.get(i));
                                }
                            }
                            model.addAttribute("packages", lists);
                        }else{
                            model.addAttribute("details", details);
                        }
                    }
                }
                
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈项目信息总览页面〉
     *〈详细描述〉
     * @author Administrator
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/overview")
    public String overview(String id, Model model){
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
                    if(listBycollect != null){
                        collectPlan.setUpdatedAt(listBycollect.get(0).getGiveTime());
                    }
                    model.addAttribute("collectPlan", collectPlan);
                    
                    
                    map.put("collectId", collectPlan.getId());
                    List<AuditPerson> listAuditPerson = auditPersonService.selectByMap(map);
                    if(listAuditPerson != null && listAuditPerson.size() > 0){
                        model.addAttribute("listAuditPerson", listAuditPerson);
                    }
                    
                }
                
                
                //任务信息
                Task task = taskService.selectByCollectId(detail.getUniqueId());
                if(task != null){
                    Orgnization org = orgnizationService.getOrgByPrimaryKey(task.getPurchaseId());
                    /*User user = userService.getUserById(task.getUserId());
                    task.setUserId(user.getRelName());*/
                    task.setPurchaseId(org.getName());
                    model.addAttribute("task", task);
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
                        }
                        Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                        project.setPurchaseDepName(org.getName());
                        project.setStatus(DictionaryDataUtil.findById(project.getStatus()).getName());
                        model.addAttribute("uploadId", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS")); //项目审批文件
                        //判断是否上传招标文件
                        String typeId = DictionaryDataUtil.getId("PROJECT_BID");
                        List<UploadFile> files = uploadService.getFilesOther(project.getId(), typeId, Constant.TENDER_SYS_KEY+"");
                        if(files != null && files.size() > 0){
                            model.addAttribute("fileId", files.get(0).getId());
                            model.addAttribute("fileName", files.get(0).getName());
                        }
                        //获取采购文件的编制人
                        FlowDefine define = new FlowDefine();
                        define.setPurchaseTypeId(project.getPurchaseType());
                        define.setStep(3);
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
                            model.addAttribute("DYLY", "1");
                        }
                        
                        //确认中标供应商
                        SupplierCheckPass pass = new SupplierCheckPass();
                        pass.setPackageId(selectById.get(0).getPackageId());
                        pass.setIsWonBid((short)1);
                        pass.setProjectId(project.getId());
                        List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(pass);
                        if(listCheckPass != null && listCheckPass.size() > 0){
                            for (SupplierCheckPass supplierCheckPass : listCheckPass) {
                                Supplier supplier = supplierService.selectById(supplierCheckPass.getSupplier().getId());
                                supplierCheckPass.setSupplierId(supplier.getName());
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
                                    purchaseContract.setPurchaseDepName(org1.getName());
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
                            PqInfo pqInfo = new PqInfo();
                            pqInfo.setContract(purchaseContract);
                            List<PqInfo> selectByCondition = pqInfoService.selectByCondition(pqInfo, 0);
                            if(selectByCondition != null && selectByCondition.size() > 0){
                                model.addAttribute("PqInfo", selectByCondition.get(0).getClass());
                            }
                            model.addAttribute("purchaseContract", purchaseContract);
                        }          

                        model.addAttribute("expertIdList", expertIdList);
                        model.addAttribute("experts", experts);
                        model.addAttribute("project", project);
                        model.addAttribute("status", "0");
                        model.addAttribute("packageId", selectById.get(0).getPackageId());
                    }
                }
                
            }
            
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            List<PurchaseRequired> requireds = requiredService.selectByParent(map);
            for (PurchaseRequired purchaseRequired : requireds) {
                if("1".equals(purchaseRequired.getParentId())){
                    User user = userService.getUserById(purchaseRequired.getUserId());
                    purchaseRequired.setUserId(user.getRelName());
                    model.addAttribute("purchaseRequired", purchaseRequired);
                    break;
                }
            }
            
            PurchaseRequired required = requiredService.queryById(id);
            if(required != null){
                Orgnization orgnization = orgnizationService.getOrgByPrimaryKey(required.getOrganization());
                required.setOrganization(orgnization.getName());
                required.setPurchaseType(DictionaryDataUtil.findById(required.getPurchaseType()).getName());
                
                List<PurchaseManagement> queryByPid = managementService.queryByPid(required.getUniqueId());
                Orgnization org= orgnizationService.getOrgByPrimaryKey(queryByPid.get(0).getManagementId());
                map.put("collectId", id);
                map.put("type", "4");
                List<AuditPerson> selectByMap = auditPersonService.selectByMap(map);
                if(selectByMap != null && selectByMap.size() > 0){
                    User user = userService.getUserById(selectByMap.get(0).getUserId());
                    selectByMap.get(0).setUserId(user.getRelName());
                    model.addAttribute("auditPerson", selectByMap.get(0).getClass());//受理人和受理时间
                }
                model.addAttribute("management", org.getName());//管理部门
                model.addAttribute("required", required);//计划明细
            }
            
            
            //预研信息
            AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(id);
            if(advancedDetail != null){
                AdvancedProject advancedProject = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
                if(advancedProject != null){
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("projectId", advancedProject.getId());
                    List<ProjectTask> queryByNo = projectTaskService.queryByNo(map);
                    Task task = taskService.selectById(queryByNo.get(0).getTaskId());
                    if(task != null){
                        model.addAttribute("task", task);//任务
                    }
                    model.addAttribute("advancedProject", advancedProject);//预研项目
                    model.addAttribute("status","1");
                }
                model.addAttribute("advancedProjectId", advancedDetail.getAdvancedProject());//预研项目ID
            }
            String adviceId = DictionaryDataUtil.getId("ADVANCED_ADVICE");
            
            model.addAttribute("adviceId", adviceId);//预研通知书
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
            SaleTender saleTender = new SaleTender();
            saleTender.setPackages(packageId);
            List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
            for (SaleTender saleTender2 : saleTenderList) {
                Supplier supplier = supplierService.selectById(saleTender2.getSuppliers().getId());
                supplier.setProSupFile(saleTender2.getId());
                supplier.setIsturnUp(saleTender2.getIsTurnUp().toString());
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

}
