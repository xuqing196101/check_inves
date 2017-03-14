package sums.controller.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import common.annotation.CurrentUser;

import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;
import bss.service.prms.PackageExpertService;

/**
 * 
 * 版权：(C) 版权所有 
 * <采购项目监督>
 * <详细描述>
 * @author   FengTian
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/projectSupervision")
public class ProjectSupervisionController {
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private OrgnizationServiceI orgnizationService;
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private ProjectTaskService projectTaskService;
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private ProjectDetailService detailService;
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private PurchaseRequiredService requiredService;
    
    /**
     * 
     *〈列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param user
     * @param project
     * @param page
     * @return
     */
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user,Project project,Integer page){
        if(user != null && user.getOrg() != null){
            HashMap<String,Object> map = new HashMap<String,Object>();
            if(StringUtils.isNotBlank(project.getName())){
                map.put("name", project.getName());
            }
            if(StringUtils.isNotBlank(project.getProjectNumber())){
                map.put("projectNumber", project.getProjectNumber());
            }
            if(StringUtils.isNotBlank(project.getStatus())){
                map.put("status", project.getStatus());
            }
            if(StringUtils.isNotBlank(project.getPurchaseType())){
                map.put("purchaseType", project.getPurchaseType());
            }
            map.put("purchaseDepId", user.getOrg().getId());
            if(page==null){
                page = 1;
            }
            PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<Project> list = projectService.selectProjectsByConition(map);
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    Orgnization org = orgnizationService.getOrgByPrimaryKey(list.get(i).getPurchaseDepId());
                    User users = userService.getUserById(list.get(i).getAppointMan());
                    list.get(i).setPurchaseDepId(org.getName());
                    list.get(i).setAppointMan(users.getRelName());
                } catch (Exception e) {
                    list.get(i).setPurchaseDepId("");
                    list.get(i).setAppointMan("");
                }
            }
            model.addAttribute("info", new PageInfo<Project>(list));
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));//获取数据字典数据
            model.addAttribute("project", project);
        }
        return "sums/ss/projectSupervision/list";
    }
    
    
    
    /**
     * 
     *〈查看跳转〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, String type, Model model){
        //0跳转信息总览页面反之跳转项目进度页面
        if("0".equals(type)){
            if(StringUtils.isNotBlank(id)){
                Project project = projectService.selectById(id);
                //根据ID查询项目
                if(project != null){
                    User users = userService.getUserById(project.getAppointMan());
                    DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                    project.setStatus(findById.getName());
                    project.setAppointMan(users.getRelName());
                    model.addAttribute("project", project);
                    model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
                }
                //根据项目ID查询中间表，然后查询采购计划表
                HashMap<String, Object> map = new HashMap<>();
                map.put("projectId", id);
                List<CollectPlan> list = new ArrayList<CollectPlan>();
                //List<PurchaseRequired> list2 = new ArrayList<PurchaseRequired>();
                List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
                if(projectTasks != null && projectTasks.size() > 0){
                    for (ProjectTask projectTask : projectTasks) {
                        Task task = taskService.selectById(projectTask.getTaskId());
                        CollectPlan collectPlan = collectPlanService.queryById(task.getCollectId());
                        /*List<PurchaseRequired> unique = requiredService.getUnique(task.getCollectId());
                        for (PurchaseRequired purchaseRequired : unique) {
                            if("1".equals(purchaseRequired.getParentId())){
                                list2.add(purchaseRequired);
                            }
                        }*/
                        list.add(collectPlan);
                    }
                }
                //将获取到的集合用逗号隔开显示
                if(list != null && list.size() > 0){
                    String name = null;
                    String number = null;
                    String org = null;
                    for (int i = 0; i < list.size(); i++ ) {
                        User user = userService.getUserById(list.get(i).getUserId());
                        list.get(i).setUserId(user.getRelName());
                        if(StringUtils.isNotBlank(name)){
                            name = name + "," + list.get(i).getFileName();
                        }else{
                            name = list.get(i).getFileName();
                        }
                        if(StringUtils.isNotBlank(number)){
                            number = number + "," + list.get(i).getPlanNo();
                        }else{
                            number = list.get(i).getPlanNo();
                        }
                        if(StringUtils.isNotBlank(org)){
                            org = org + "," + list.get(i).getUserId();
                        }else{
                            org = list.get(i).getUserId();
                        }
                    }
                    model.addAttribute("name", name);
                    model.addAttribute("number", number);
                    model.addAttribute("org", org);
                }
                //根据项目ID查询包
                List<Packages> packages = packageService.findByID(map);
                if(packages != null && packages.size() > 0){
                    HashMap<String, Object> map2 = new HashMap<>();
                    for (Packages packages2 : packages) {
                        map2.put("packageId", packages2.getId());
                        List<ProjectDetail> selectById = detailService.selectById(map2);
                        List<CollectPlan> collectPlans = new ArrayList<CollectPlan>();
                        if(selectById != null && selectById.size() > 0){
                            for (int i = 0; i < selectById.size(); i++ ) {
                                PurchaseDetail queryById = purchaseDetailService.queryById(selectById.get(0).getRequiredId());
                                if(queryById != null){
                                    CollectPlan collectPlan = collectPlanService.queryById(queryById.getUniqueId());
                                    if(collectPlan != null){
                                        collectPlans.add(collectPlan);
                                    }
                                }
                            }
                        }
                        //因为查询出来的数据有重复，去重
                        removeCollectPlan(collectPlans);
                        //将采购计划放到包里面
                        packages2.setCollectPlan(collectPlans);
                    }
                    model.addAttribute("packages", packages);
                }
                
                
            }
            return "sums/ss/projectSupervision/overview";
        }else{
            return null;
        }
        
    }
    
    /**
     * 
     *〈采购计划去重〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void removeCollectPlan(List<CollectPlan> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
                }
            }
        }
    }
}
