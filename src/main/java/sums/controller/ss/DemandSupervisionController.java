package sums.controller.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.controller.base.BaseController;
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
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/* 
 *@Title:DemandSupervisionController
 *@Description:采购采购需求监督控制类
 *@author zhiqiang tian
 *@date 2017-03-06下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/supervision")
public class DemandSupervisionController extends BaseController{

	// 需求计划服务
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;

	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;
	@Autowired
	private OrgnizationMapper oargnizationMapper;
	 @Autowired
	private OrgnizationServiceI orgnizationService;
	@Autowired
	private PurChaseDepOrgService chaseDepOrgService;
	
	@Autowired
    private UserServiceI userService;
    
    @Autowired
    private PurchaseDetailService detailService;
    
    @Autowired
    private CollectPlanService collectPlanService;

	/**
	 * 
	 * 〈简述〉 〈详细描述〉
	 * 
	 * @author tzq
	 * @date 2016-11-11 下午2:48:57
	 * @Description: 查询已完成的包
	 * @param @param model
	 * @param @param 分页
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/demandSupervisionList", produces = "text/html;charset=UTF-8")
	public String demandSupervisionList(@CurrentUser User user,
			PurchaseRequired purchaseRequired, Integer page, Model model)
			throws Exception {
		//是否是详细，1是主要，2是明细
		purchaseRequired.setIsMaster(1);
		
		List<PurchaseRequired> list = purchaseRequiredService.query(
				purchaseRequired, page == null ? 1 : page);
		//获取用户的真实姓名
         for (int i = 0; i < list.size(); i++ ) {
             try {
                 User users = userService.getUserById(list.get(i).getUserId());
                 list.get(i).setUserName(users.getRelName());
             } catch (Exception e) {
                  list.get(i).setUserName("");
             }
         }
		model.addAttribute("list", new PageInfo<PurchaseRequired>(list));
		model.addAttribute("info", purchaseRequired);
		return "sums/ss/demandSupervision/list";
	}
	
	 /**
     * 〈查看跳转〉 〈详细描述〉
     * 
     * @author FengTian
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/view")
    public String view(String id, String type, Model model) {
//        if (StringUtils.isNotBlank(id)) {
//            Project project = projectService.selectById(id);
//            // 根据ID查询项目
//            if (project != null) {
//                User users = userService.getUserById(project.getAppointMan());
//                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
//                project.setStatus(findById.getName());
//                project.setAppointMan(users.getRelName());
//                model.addAttribute("project", project);
//                model.addAttribute("kind", DictionaryDataUtil.find(5));// 获取数据字典数据
//            }
//            // 根据项目ID查询中间表，然后查询采购计划表
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("projectId", id);
//            List<CollectPlan> list = new ArrayList<CollectPlan>();
//            List<PurchaseRequired> list2 = new ArrayList<PurchaseRequired>();
//            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
//            if (projectTasks != null && projectTasks.size() > 0) {
//                for (ProjectTask projectTask : projectTasks) {
//                    Task task = taskService.selectById(projectTask.getTaskId());
//                    CollectPlan collectPlan = collectPlanService.queryById(task.getCollectId());
//                    collectPlan.setUpdatedAt(task.getGiveTime());
//                     List<PurchaseDetail> details = purchaseDetailService.getUnique(task.getCollectId());
//                      for (PurchaseDetail detail : details) { 
//                          if("1".equals(detail.getParentId())){
//                              PurchaseRequired required = requiredService.queryById(detail.getId());
//                              list2.add(required);
//                              break;
//                          } 
//                      }
//                     
//                    list.add(collectPlan);
//                }
//            }
//            // 将获取到的集合用逗号隔开显示
//            if (list != null && list.size() > 0) {
//                String name = null;
//                String number = null;
//                String org = null;
//                for (int i = 0; i < list.size(); i++ ) {
//                    User user = userService.getUserById(list.get(i).getUserId());
//                    list.get(i).setUserId(user.getRelName());
//                    list.get(i).setPurchaseId(user.getOrgName());
//                    if (StringUtils.isNotBlank(name)) {
//                        name = name + "," + list.get(i).getFileName();
//                    } else {
//                        name = list.get(i).getFileName();
//                    }
//                    if (StringUtils.isNotBlank(number)) {
//                        number = number + "," + list.get(i).getPlanNo();
//                    } else {
//                        number = list.get(i).getPlanNo();
//                    }
//                    if (StringUtils.isNotBlank(org)) {
//                        org = org + "," + list.get(i).getUserId();
//                    } else {
//                        org = list.get(i).getUserId();
//                    }
//                }
//                model.addAttribute("name", name);
//                model.addAttribute("number", number);
//                model.addAttribute("org", org);
//                model.addAttribute("list", list);
//            }
//            if (list2 != null && list2.size() > 0) {
//                for (int i = 0; i < list2.size(); i++ ) {
//                    try {
//                        User user = userService.getUserById(list2.get(i).getUserId());
//                        list2.get(i).setUserId(user.getRelName());
//                    } catch (Exception e) {
//                        list2.get(i).setUserId("");
//                    }
//                }
//                model.addAttribute("lists", list2);
//            }
//            // 根据项目ID查询包
//            List<Packages> packages = packageService.findByID(map);
//            if (packages != null && packages.size() > 0) {
//                HashMap<String, Object> map2 = new HashMap<>();
//                for (Packages packages2 : packages) {
//                    map2.put("packageId", packages2.getId());
//                    List<ProjectDetail> selectById = detailService.selectById(map2);
//                    List<CollectPlan> collectPlans = new ArrayList<CollectPlan>();
//                    if (selectById != null && selectById.size() > 0) {
//                        for (int i = 0; i < selectById.size(); i++ ) {
//                            PurchaseDetail queryById = purchaseDetailService.queryById(selectById.get(0).getRequiredId());
//                            if (queryById != null) {
//                                CollectPlan collectPlan = collectPlanService.queryById(queryById.getUniqueId());
//                                if (collectPlan != null) {
//                                    collectPlans.add(collectPlan);
//                                }
//                            }
//                        }
//                    }
//                    // 因为查询出来的数据有重复，去重
//                    removeCollectPlan(collectPlans);
//                    // 将采购计划放到包里面
//                    packages2.setCollectPlan(collectPlans);
//                }
//                model.addAttribute("packages", packages);
//            }
//        }
        // 0跳转信息总览页面反之跳转项目进度页面
        if ("0".equals(type)) {
            return "sums/ss/projectSupervision/overview";
        } else {
            return "sums/ss/projectSupervision/schedule_view";
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
    public String viewTask(String id, Model model){
//        if(StringUtils.isNotBlank(id)){
//            Project project = projectService.selectById(id);
//            //项目信息
//            if (project != null) {
//                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
//                if(StringUtils.isNotBlank(project.getPrincipal())){
//                    User users = userService.getUserById(project.getPrincipal());
//                    project.setAppointMan(users.getRelName());
//                    project.setAddress(users.getAddress());
//                }
//                if(StringUtils.isNotBlank(project.getPurchaseDepId())){
//                    Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
//                    project.setPurchaseDepId(org.getName());
//                }
//                project.setStatus(findById.getName());
//                model.addAttribute("project", project);
//                model.addAttribute("kind", DictionaryDataUtil.find(5));// 获取数据字典数据
//                model.addAttribute("project", project);
//            }
//            
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("projectId", id);
//            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
//            if(projectTasks != null && projectTasks.size() > 0){
//                List<Task> listTask = new ArrayList<Task>();
//                List<CollectPlan> listCollect = new ArrayList<CollectPlan>();
//                List<PurchaseRequired> listRequired = new ArrayList<PurchaseRequired>();
//                for (ProjectTask projectTask : projectTasks) {
//                    //任务信息
//                    Task task = taskService.selectById(projectTask.getTaskId());
//                    
//                    //采购计划信息
//                    CollectPlan collectPlan = collectPlanService.queryById(task.getCollectId());
//                    User user = userService.getUserById(collectPlan.getUserId());
//                    collectPlan.setUserId(user.getRelName());
//                    collectPlan.setUpdatedAt(task.getGiveTime());
//                    listCollect.add(collectPlan);
//                    task.setPassWord(String.valueOf(collectPlan.getBudget()));
//                    listTask.add(task);
//                    //需求计划信息
//                    List<PurchaseDetail> details = purchaseDetailService.getUnique(task.getCollectId());
//                    for (PurchaseDetail detail : details) { 
//                        if("1".equals(detail.getParentId())){
//                            PurchaseRequired required = requiredService.queryById(detail.getId());
//                            User users = userService.getUserById(required.getUserId());
//                            required.setUserId(users.getRelName());
//                            listRequired.add(required);
//                            break;
//                        } 
//                    }
//                }
//                model.addAttribute("listTask", listTask);
//                model.addAttribute("listCollect", listCollect);
//                model.addAttribute("listRequired", listRequired);
//            }
//            
//            SupplierCheckPass checkPass = new SupplierCheckPass();
//            checkPass.setProjectId(id);
//            checkPass.setIsWonBid((short)1);
//            List<SupplierCheckPass> checkPasses = checkPassService.listCheckPass(checkPass);
//            if(checkPasses != null && checkPasses.size() > 0){
//                List<PurchaseContract> listContract = new ArrayList<PurchaseContract>();
//                for (SupplierCheckPass supplierCheckPass : checkPasses) {
//                    PurchaseContract contract = contractService.selectById(supplierCheckPass.getContractId());
//                    listContract.add(contract);
//                }
//                model.addAttribute("listContract", listContract);
//            }
//            
//        }
        return "sums/ss/projectSupervision/task_view";
    }
    

}
