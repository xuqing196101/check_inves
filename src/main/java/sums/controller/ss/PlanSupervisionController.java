package sums.controller.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;

import common.annotation.CurrentUser;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
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
                User user = userService.getUserById(collectPlan.getUserId());
                Task task = taskService.selectByCollectId(id);
                if(task != null){
                    collectPlan.setOrderAt(task.getGiveTime());
                }
                collectPlan.setUserId(user.getRelName());
                collectPlan.setPurchaseId(user.getOrgName());
                
                //需求计划信息
                List<PurchaseDetail> details = detailService.getUnique(id);
                if(details != null && details.size() > 0){
                    List<PurchaseRequired> listRequired = new ArrayList<PurchaseRequired>();
                    for (PurchaseDetail detail : details) { 
                        if("1".equals(detail.getParentId())){
                            PurchaseRequired required = requiredService.queryById(detail.getId());
                            User users = userService.getUserById(required.getUserId());
                            required.setUserId(users.getRelName());
                            listRequired.add(required);
                            break;
                        } 
                    }
                    model.addAttribute("listRequired", listRequired);
                }
                
                if(task != null){
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("taskId", task.getId());
                    List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
                    if(projectTasks != null && projectTasks.size() > 0){
                        List<Project> listProject = new ArrayList<Project>();
                        for (ProjectTask projectTask : projectTasks) {
                            Project project = projectService.selectById(projectTask.getProjectId());
                            listProject.add(project);
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
                            //项目集合，任务和项目是多对多的关系
                            model.addAttribute("listProject", listProject);
                            //合同集合
                            model.addAttribute("listContract", listContract);
                        }
                        
                    }
                }
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
                User user = userService.getUserById(collectPlan.getUserId());
                collectPlan.setUserId(user.getRelName());
                model.addAttribute("collectPlan", collectPlan);
            }
            
            //需求计划信息
            List<PurchaseDetail> details = detailService.getUnique(id);
            if(details != null && details.size() > 0){
                HashMap<String, Object> map = new HashMap<>();
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
                List<PurchaseRequired> listRequired = new ArrayList<PurchaseRequired>();
                for (PurchaseDetail detail : details) { 
                    if("1".equals(detail.getParentId())){
                        PurchaseRequired required = requiredService.queryById(detail.getId());
                        if(required != null){
                            User users = userService.getUserById(required.getUserId());
                            required.setUserId(users.getRelName());
                            listRequired.add(required);
                        }
                        break;
                    } 
                }
                model.addAttribute("listRequired", listRequired);
                model.addAttribute("list", details);
            }
            
            //任务信息
            Task task = taskService.selectByCollectId(id);
            if(task != null){
                /*User user = userService.getUserById(task.getCreaterId());
                task.setCreaterId(user.getRelName());*/
                if(collectPlan.getBudget() != null){
                    task.setPassWord(String.valueOf(collectPlan.getBudget()));
                }
                
                
                HashMap<String, Object> map = new HashMap<>();
                map.put("taskId", task.getId());
                List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
                if(projectTasks != null && projectTasks.size() > 0){
                    List<Project> listProject = new ArrayList<Project>();
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
                    map.put("projectId", id);
                    List<Packages> packages = packageService.findByID(map);
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
                        model.addAttribute("packages", packages);
                    }else{
                        model.addAttribute("details", details);
                    }
                    
                }
                
            }
        }
        return "sums/ss/planSupervision/package_view";
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
