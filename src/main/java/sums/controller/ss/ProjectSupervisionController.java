package sums.controller.ss;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import ses.model.oms.PurchaseOrg;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import common.annotation.CurrentUser;
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


/**
 * 版权：(C) 版权所有 <采购项目监督> <详细描述>
 * 
 * @author FengTian
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
    
    @Autowired
    private SupplierCheckPassService checkPassService;
    
    @Autowired
    private PurchaseContractService contractService;
    @Autowired
	private SupplierService supplierService;
    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    /**
     * 〈列表〉 〈详细描述〉
     * 
     * @author FengTian
     * @param model
     * @param user
     * @param project
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser
    User user, Project project, Integer page) {
        if (user != null && user.getOrg() != null) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(project.getName())) {
                map.put("name", project.getName());
            }
            if (StringUtils.isNotBlank(project.getProjectNumber())) {
                map.put("projectNumber", project.getProjectNumber());
            }
            if (StringUtils.isNotBlank(project.getStatus())) {
                map.put("status", project.getStatus());
            }
            if (StringUtils.isNotBlank(project.getPurchaseType())) {
                map.put("purchaseType", project.getPurchaseType());
            }
            map.put("purchaseDepId", user.getOrg().getId());
            if (page == null) {
                page = 1;
            }
            PageHelper.startPage(page, Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<Project> list = projectService.selectProjectsByConition(map);
            for (int i = 0; i < list.size(); i++ ) {
                Orgnization org = orgnizationService.getOrgByPrimaryKey(list.get(i).getPurchaseDepId());
                if(org != null && StringUtils.isNotBlank(org.getName())){
                    list.get(i).setPurchaseDepId(org.getName());
                }else{
                    list.get(i).setPurchaseDepId("");
                }
                
                if(StringUtils.isNotBlank(list.get(i).getAppointMan())){
                    User users = userService.getUserById(list.get(i).getAppointMan());
                    if(users != null && StringUtils.isNotBlank(users.getRelName())){
                        list.get(i).setAppointMan(users.getRelName());
                    }
                }
            }
            model.addAttribute("info", new PageInfo<Project>(list));
            model.addAttribute("kind", DictionaryDataUtil.find(5));// 获取数据字典数据
            model.addAttribute("status", DictionaryDataUtil.find(2));// 获取数据字典数据
            model.addAttribute("project", project);
        }
        return "sums/ss/projectSupervision/list";
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
        /*if (StringUtils.isNotBlank(id)) {
            Project project = projectService.selectById(id);
            // 根据ID查询项目
            if (project != null) {
                User users = userService.getUserById(project.getAppointMan());
                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                project.setStatus(findById.getName());
                project.setAppointMan(users.getRelName());
                model.addAttribute("project", project);
                model.addAttribute("kind", DictionaryDataUtil.find(5));// 获取数据字典数据
            }
            // 根据项目ID查询中间表，然后查询采购计划表
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", id);
            List<CollectPlan> list = new ArrayList<CollectPlan>();
            List<PurchaseRequired> list2 = new ArrayList<PurchaseRequired>();
            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
            if (projectTasks != null && projectTasks.size() > 0) {
                for (ProjectTask projectTask : projectTasks) {
                    Task task = taskService.selectById(projectTask.getTaskId());
                    CollectPlan collectPlan = collectPlanService.queryById(task.getCollectId());
                    collectPlan.setUpdatedAt(task.getGiveTime());
                     List<PurchaseDetail> details = purchaseDetailService.getUnique(task.getCollectId());
                      for (PurchaseDetail detail : details) { 
                          if("1".equals(detail.getParentId())){
                              PurchaseRequired required = requiredService.queryById(detail.getId());
                              list2.add(required);
                              break;
                          } 
                      }
                     
                    list.add(collectPlan);
                }
            }
            // 将获取到的集合用逗号隔开显示
            if (list != null && list.size() > 0) {
                String name = null;
                String number = null;
                String org = null;
                for (int i = 0; i < list.size(); i++ ) {
                    User user = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(user.getRelName());
                    list.get(i).setPurchaseId(user.getOrgName());
                    if (StringUtils.isNotBlank(name)) {
                        name = name + "," + list.get(i).getFileName();
                    } else {
                        name = list.get(i).getFileName();
                    }
                    if (StringUtils.isNotBlank(number)) {
                        number = number + "," + list.get(i).getPlanNo();
                    } else {
                        number = list.get(i).getPlanNo();
                    }
                    if (StringUtils.isNotBlank(org)) {
                        org = org + "," + list.get(i).getUserId();
                    } else {
                        org = list.get(i).getUserId();
                    }
                }
                model.addAttribute("name", name);
                model.addAttribute("number", number);
                model.addAttribute("org", org);
                model.addAttribute("list", list);
            }
            if (list2 != null && list2.size() > 0) {
                for (int i = 0; i < list2.size(); i++ ) {
                    try {
                        User user = userService.getUserById(list2.get(i).getUserId());
                        list2.get(i).setUserId(user.getRelName());
                    } catch (Exception e) {
                        list2.get(i).setUserId("");
                    }
                }
                model.addAttribute("lists", list2);
            }
            // 根据项目ID查询包
            List<Packages> packages = packageService.findByID(map);
            if (packages != null && packages.size() > 0) {
                HashMap<String, Object> map2 = new HashMap<>();
                for (Packages packages2 : packages) {
                    map2.put("packageId", packages2.getId());
                    List<ProjectDetail> selectById = detailService.selectById(map2);
                    List<CollectPlan> collectPlans = new ArrayList<CollectPlan>();
                    if (selectById != null && selectById.size() > 0) {
                        for (int i = 0; i < selectById.size(); i++ ) {
                            PurchaseDetail queryById = purchaseDetailService.queryById(selectById.get(0).getRequiredId());
                            if (queryById != null) {
                                CollectPlan collectPlan = collectPlanService.queryById(queryById.getUniqueId());
                                if (collectPlan != null) {
                                    collectPlans.add(collectPlan);
                                }
                            }
                        }
                    }
                    // 因为查询出来的数据有重复，去重
                    removeCollectPlan(collectPlans);
                    // 将采购计划放到包里面
                    packages2.setCollectPlan(collectPlans);
                }
                model.addAttribute("packages", packages);
            }
        }
        // 0跳转信息总览页面反之跳转项目进度页面
        if ("0".equals(type)) {
            return "sums/ss/projectSupervision/overview";
        } else {
            return "sums/ss/projectSupervision/schedule_view";
        }*/
    	
    	model.addAttribute("projectId", id);
    	return "sums/ss/projectSupervision/schedule_view";

    }
    
    /**
     * 
     *〈合同列表〉
     *〈详细描述〉
     * @author FengTian
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/contractList")
    public String contractList(String projectId,Model model) {
    	if(StringUtils.isNotBlank(projectId)){
    	    SupplierCheckPass checkPass = new SupplierCheckPass();
            checkPass.setProjectId(projectId);
            checkPass.setIsWonBid((short)1);
            List<SupplierCheckPass> checkPasses = checkPassService.listCheckPass(checkPass);
            List<PurchaseContract> listContract = new ArrayList<PurchaseContract>();
            if(checkPasses != null && checkPasses.size() > 0){
                for (SupplierCheckPass supplierCheckPass : checkPasses) {
                    PurchaseContract contract = contractService.selectById(supplierCheckPass.getContractId());
                    if(contract != null){
                        Supplier su = null;
                        Orgnization org = null;
                        if(contract.getSupplierDepName()!=null){
                            su = supplierService.selectOne(contract.getSupplierDepName());
                        }
                        if(contract.getPurchaseDepName()!=null){
                            org = orgnizationService.getOrgByPrimaryKey(contract.getPurchaseDepName());
                        }
                        if(org!=null){
                            if(org.getName()==null){
                                contract.setShowDemandSector("");
                            }else{
                                contract.setShowDemandSector(org.getName());
                            }
                        }
                        if(su!=null){
                            if(su.getSupplierName()!=null){
                                contract.setShowSupplierDepName(su.getSupplierName());
                            }else{
                                contract.setShowSupplierDepName("");
                            }
                        }
                    }
                }
            }
            model.addAttribute("listContract", listContract);
    	}
    	return "sums/ss/planSupervision/contract_view";
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
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            //项目信息
            if (project != null) {
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
                model.addAttribute("project", project);
            }
            
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", id);
            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
            if(projectTasks != null && projectTasks.size() > 0){
                List<Task> listTask = new ArrayList<Task>();
                List<CollectPlan> listCollect = new ArrayList<CollectPlan>();
                List<PurchaseRequired> listRequired = new ArrayList<PurchaseRequired>();
                for (ProjectTask projectTask : projectTasks) {
                    //任务信息
                    Task task = taskService.selectById(projectTask.getTaskId());
                    
                    //采购计划信息
                    CollectPlan collectPlan = collectPlanService.queryById(task.getCollectId());
                    User user = userService.getUserById(collectPlan.getUserId());
                    collectPlan.setUserId(user.getRelName());
                    collectPlan.setUpdatedAt(task.getGiveTime());
                    listCollect.add(collectPlan);
                    task.setPassWord(String.valueOf(collectPlan.getBudget()));
                   /* User users = userService.getUserById(task.getCreaterId());
                    task.setCreaterId(users.getRelName());*/
                    listTask.add(task);
                    //需求计划信息
                    List<PurchaseDetail> details = purchaseDetailService.getUnique(task.getCollectId());
                    for (PurchaseDetail detail : details) { 
                        if("1".equals(detail.getParentId())){
                            PurchaseRequired required = requiredService.queryById(detail.getId());
                            User user1 = userService.getUserById(required.getUserId());
                            required.setUserId(user1.getRelName());
                            listRequired.add(required);
                            break;
                        } 
                    }
                }
                model.addAttribute("listTask", listTask);
                model.addAttribute("listCollect", listCollect);
                model.addAttribute("listRequired", listRequired);
            }
            
            SupplierCheckPass checkPass = new SupplierCheckPass();
            checkPass.setProjectId(id);
            checkPass.setIsWonBid((short)1);
            List<SupplierCheckPass> checkPasses = checkPassService.listCheckPass(checkPass);
            if(checkPasses != null && checkPasses.size() > 0){
                List<PurchaseContract> listContract = new ArrayList<PurchaseContract>();
                for (SupplierCheckPass supplierCheckPass : checkPasses) {
                    PurchaseContract contract = contractService.selectById(supplierCheckPass.getContractId());
                    listContract.add(contract);
                }
                model.addAttribute("listContract", listContract);
            }
            
        }
        return "sums/ss/projectSupervision/task_view";
    }
    
    /**
     * 
     *〈进度列表跳转页面〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param id
     * @param type
     * @return
     */
    @RequestMapping("/viewOver")
    public String viewOver(Model model, String projectId, String type){
    	Project project=null;
    	if(projectId!=null){
    		project = projectService.selectById(projectId);
    		if(project!=null){
    			User user = userService.getUserById(project.getAppointMan());
    			if(user!=null){
    				project.setAppointMan(user.getRelName());
    			}else{
    				project.setAppointMan("");
    			}
    			List<PurchaseOrg> list = purchaseOrgnizationServiceI.getByPurchaseDepId(user.getOrg().getId());
    			DictionaryData dictionaryData = dictionaryDataServiceI.getDictionaryData(project.getStatus());
    			if(dictionaryData!=null){
    				project.setStatus(dictionaryData.getName());
    			}else{
    				project.setStatus("");
    			}
    			
    			String orgs="";
    			if(list!=null&&list.size()>0){
    				for(PurchaseOrg org:list){
    					Orgnization orgnization = orgnizationService.findByCategoryId(org.getOrgId());
    					orgs+=orgnization.getName()+",";
    				}
    			}
    			if(orgs.length()>0){
    				orgs=orgs.substring(0, orgs.length()-1);
    			}
    			model.addAttribute("org",orgs);
    			HashMap<String, Object> map=new HashMap<String, Object>();
    			map.put("projectId", projectId);
    			List<Packages> packagess = packageService.selectByProjectKey(map);
    			if(packagess!=null&&packagess.size()>0){
    				for(Packages packa:packagess){
    					List<ProjectDetail> projectDetails = detailService.selectByPackageId(packa.getId());
    					  if(projectDetails!=null&&projectDetails.size()>0){
    						  for(ProjectDetail projectDeta:projectDetails){
    							  DictionaryData types = dictionaryDataServiceI.getDictionaryData(projectDeta.getPurchaseType());
    	  							if(types!=null){
    	  								projectDeta.setPurchaseType(types.getName());
    	  						    }else{
    	  						    	projectDeta.setPurchaseType("");
    	  						    } 
    						  }
    					  }
    					packa.setProjectDetails(projectDetails);
    				}
    			}
    			project.setPackagesList(packagess);
    	     }
    	}
    	model.addAttribute("project",project);
    	 return "sums/ss/projectSupervision/project_detail";
    }
    
    
    @RequestMapping("/viewPack")
    public String viewPack(String projectId, Model model){
        if(StringUtils.isNotBlank(projectId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", projectId);
            List<ProjectDetail> selectById = detailService.selectById(map);
            if(selectById != null && selectById.size() > 0){
                map.put("projectId", projectId);
                List<Packages> packages = packageService.findByID(map);
                //判断有没有分包，没有分包进else
                if(packages != null && packages.size() > 0){
                    for (Packages packages2 : packages) {
                        List<ProjectDetail> list = new ArrayList<ProjectDetail>();
                        for (int i = 0; i < selectById.size(); i++ ) {
                            if(packages2.getId().equals(selectById.get(i).getPackageId())){
                                DictionaryData findById = DictionaryDataUtil.findById(selectById.get(i).getPurchaseType());
                                selectById.get(i).setPurchaseType(findById.getName());
                                list.add(selectById.get(i));
                            }
                            sort(list);//进行排序
                            packages2.setProjectDetails(list);
                        }
                    }
                    model.addAttribute("packages", packages);
                }
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈查看采购计划列表〉
     *〈详细描述〉
     * @author Administrator
     * @param model
     * @param projectId
     * @return
     */
    @RequestMapping("/planList")
    public String planList(Model model, String projectId){
        if(StringUtils.isNotBlank(projectId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("projectId", projectId);
            List<ProjectTask> projectTasks = projectTaskService.queryByNo(map);
            List<CollectPlan> list = new ArrayList<CollectPlan>();
            if(projectTasks != null && projectTasks.size() > 0){
                for (ProjectTask projectTask : projectTasks) {
                    Task task = taskService.selectById(projectTask.getTaskId());
                    CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
                    User user = userService.getUserById(queryById.getUserId());
                    queryById.setUserId(user.getRelName());
                    list.add(queryById);
                }
            }
            model.addAttribute("list", list);
        }
    	return "sums/ss/projectSupervision/plan_view";
    }
    
    /**
     * 
     *〈查看采购需求列表〉
     *〈详细描述〉
     * @author FengTian
     * @param model
     * @param projectId
     * @return
     */
    @RequestMapping("/demandList")
    public String demandList(Model model, String projectId){
        if(StringUtils.isNotBlank(projectId)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", projectId);
            List<ProjectDetail> selectById = detailService.selectById(map);
            if(selectById != null && selectById.size() > 0){
                HashSet<String> set = new HashSet<>();
                for (ProjectDetail projectDetail : selectById) {
                    PurchaseDetail purchaseDetail = purchaseDetailService.queryById(projectDetail.getRequiredId());
                    set.add(purchaseDetail.getFileId());
                }
                HashMap<String, Object> maps = new HashMap<>();
                List<PurchaseRequired> requireds = new ArrayList<PurchaseRequired>();
                for (String string : set) {
                    maps.put("fileId", string);
                    List<PurchaseRequired> byMap = requiredService.getByMap(maps);
                    if(byMap != null && byMap.size() > 0){
                        for (PurchaseRequired purchaseRequired : byMap) {
                            if("1".equals(purchaseRequired.getParentId())){
                                User user = userService.getUserById(purchaseRequired.getUserId());
                                purchaseRequired.setUserId(user.getRelName());
                                requireds.add(purchaseRequired);
                                break;
                            }
                        }
                    }
                }
                model.addAttribute("listRequired", requireds);
            }
            
        }
    	return "sums/ss/projectSupervision/demand_view";
    }
    
    @RequestMapping("/demandDateil")
    public String demandDateil(Model model, String id, String projectId){
    	 PurchaseRequired required = requiredService.queryById(id);
		 if(required!=null){
			 User user = userService.getUserById(required.getUserId());
			 required.setUserId(user.getRelName());
		 }
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		List<PurchaseRequired> data=new ArrayList<PurchaseRequired>();
		List<PurchaseDetail> details=new ArrayList<PurchaseDetail>();
		List<PurchaseRequired> purchaseRequireds = requiredService.selectByParentId(map);
		List<ProjectDetail> projectDetails = detailService.selectNotEmptyPackageOfDetail(projectId);
		if(projectDetails!=null&&projectDetails.size()>0){
			for(ProjectDetail projectDetail:projectDetails){
				PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
                details.add(queryById);
			}
		}
		
		if(purchaseRequireds != null && purchaseRequireds.size() > 0&&details!=null&&details.size()>0){
        	for(int i=0;i<purchaseRequireds.size();i++){
        		for(int j=0;j<details.size();j++){
        			if("1".equals(purchaseRequireds.get(i).getParentId())){
        				data.add(purchaseRequireds.get(i));
        			}
        			if(purchaseRequireds.get(i).getId().equals(details.get(j).getId())){
        				data.add(purchaseRequireds.get(i));
        			}
        		}
        	}
        }
		
		for (int i = 0; i < data.size() - 1; i++ ) {
            for (int j = data.size() - 1; j > i; j-- ) {
                if (data.get(j).getId().equals(data.get(i).getId())) {
                	data.remove(j);
                }
            }
        }
		model.addAttribute("list", data);
		model.addAttribute("demand", required);
    	return "sums/ss/contractSupervision/demandDateil";
    }
    @RequestMapping("/viewDetail")
    public String viewDetail(Model model, String id, String type){
        if(StringUtils.isNotBlank(id)){
            List<PurchaseDetail> details = purchaseDetailService.getUnique(id);
            if(details != null && details.size() > 0){
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 0; i < details.size(); i++ ) {
                    map.put("id", details.get(i).getId());
                    map.put("projectId", id);
                    List<PurchaseDetail> list = purchaseDetailService.selectByParentId(map);
                    if(list.size() > 1){
                        details.get(i).setPurchaseType(null);
                    }
                }
                model.addAttribute("list", details);
                model.addAttribute("kind", DictionaryDataUtil.find(5));
            }
        }
        model.addAttribute("type", type);
        return "sums/ss/projectSupervision/detail_view";
    }

    /**
     * 〈采购计划去重〉 〈详细描述〉
     * 
     * @author Administrator
     * @param list
     */
    public void removeCollectPlan(List<CollectPlan> list) {
        for (int i = 0; i < list.size() - 1; i++ ) {
            for (int j = list.size() - 1; j > i; j-- ) {
                if (list.get(j).getId().equals(list.get(i).getId())) {
                    list.remove(j);
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
    
    /**
     * 
     *〈项目明细排序〉
     *〈详细描述〉
     * @author FengTian
     * @param newDetails
     * @param id
     * @return
     */
    public List<ProjectDetail> rank(List<ProjectDetail> newDetails, String id){
        HashMap<String, Object> map = new HashMap<>();
        int serialoneOne = 1;
        int serialtwoTwo = 1;
        int serialthreeThree = 1;
        int serialfourFour = 1;
        int serialfiveFive = 0;
        int serialOne = 1;
        int serialTwo = 1;
        int serialThree = 1;
        int serialFour = 1;
        int serialSix = 0;
        int serialFive = 0;
        List<String> newParentId = new ArrayList<>();
        List<String> oneParentId = new ArrayList<>();
        List<String> twoParentId = new ArrayList<>();
        List<String> threeParentId = new ArrayList<>();
        List<String> fourParentId = new ArrayList<>();
        List<String> fiveParentId = new ArrayList<>();
        for(int i=0;i<newDetails.size();i++){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",newDetails.get(i).getRequiredId());
            detailMap.put("projectId", id);
            List<ProjectDetail> dlist = detailService.selectByParentId(detailMap);
            List<ProjectDetail> plist = detailService.selectByParent(detailMap);
            if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                if(!oneParentId.contains(newDetails.get(i).getParentId())){
                    oneParentId.add(newDetails.get(i).getParentId());
                    serialoneOne = 1;
                }
                newDetails.get(i).setSerialNumber(test(serialoneOne));
                serialoneOne ++;
            }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                if(!twoParentId.contains(newDetails.get(i).getParentId())){
                    twoParentId.add(newDetails.get(i).getParentId());
                    serialtwoTwo = 1;
                }
                newDetails.get(i).setSerialNumber("（"+test(serialtwoTwo)+"）");
                serialtwoTwo ++;
            }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                if(!threeParentId.contains(newDetails.get(i).getParentId())){
                    threeParentId.add(newDetails.get(i).getParentId());
                    serialthreeThree = 1;
                }
                newDetails.get(i).setSerialNumber(String.valueOf(serialthreeThree));
                serialthreeThree ++;
            }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                if(!fourParentId.contains(newDetails.get(i).getParentId())){
                    fourParentId.add(newDetails.get(i).getParentId());
                    serialfourFour = 1;
                }
                newDetails.get(i).setSerialNumber("（"+String.valueOf(serialfourFour)+"）");
                serialfourFour ++;
            }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                if(!fiveParentId.contains(newDetails.get(i).getParentId())){
                    fiveParentId.add(newDetails.get(i).getParentId());
                    serialfiveFive = 0;
                }
                char serialNum = (char) (97 + serialfiveFive);
                newDetails.get(i).setSerialNumber(String.valueOf(serialNum));
                serialfiveFive++;
            }
            if(dlist.size()==1){
                map.put("projectId", id);
                map.put("id", newDetails.get(i).getRequiredId());
                List<ProjectDetail> list = detailService.selectByParent(map);
                if(!newParentId.contains(newDetails.get(i).getParentId())){
                    serialOne = 1;
                    serialTwo = 1;
                    serialThree = 1;
                    serialFour = 1;
                    serialFive = 0;
                    serialSix = 0;
                    newParentId.add(newDetails.get(i).getParentId());
                }
                if(list.size()==1){
                    newDetails.get(i).setSerialNumber(test(serialOne));
                    serialOne ++;
                }else if(list.size()==2){
                    newDetails.get(i).setSerialNumber("（"+test(serialTwo)+"）");
                    serialTwo ++;
                }else if(list.size()==3){
                    newDetails.get(i).setSerialNumber(String.valueOf(serialThree));
                    serialThree ++;
                }else if(list.size()==4){
                    newDetails.get(i).setSerialNumber("（"+String.valueOf(serialFour)+"）");
                    serialFour ++;
                }else if(list.size()==5){
                    char serialNum = (char) (97 + serialFive);
                    newDetails.get(i).setSerialNumber(String.valueOf(serialNum));
                    serialFive ++;
                }else if(list.size()==6){
                    char serialNum = (char) (97 + serialSix);
                    newDetails.get(i).setSerialNumber("（"+serialNum+"）");
                    serialSix ++;
                }
            }
        }
        return newDetails;
    }
    
    private static String[] hanArr = { "零", "一", "二", "三", "四", "五", "六", "七","八", "九" };
    private static String[] unitArr = { "十", "百", "千", "万", "十", "白", "千", "亿","十", "百", "千" };
    
    /**
     * 
     *〈序号〉
     *〈详细描述〉
     * @author FengTian
     * @param number
     * @return
     */
    public String test(int number) {
        String numStr = number + "";
        String result = "";
        int numLen = numStr.length();
        for (int i = 0; i < numLen; i++) {
            int num = numStr.charAt(i) - 48;
            if (i != numLen - 1 && num != 0) {
                result += hanArr[num] + unitArr[numLen - 2 - i];
                if (number >= 10 && number < 20) {
                    result = result.substring(1);
                }
            } else {
                if (!(number >= 10 && number % 10 == 0)) {
                    result += hanArr[num];
                }
            }
        }
        return result;
    }
    
    
    public List<PurchaseDetail> listdata(List<PurchaseDetail> pdetails,List<PurchaseDetail> details){
		List<PurchaseDetail> deta=new ArrayList<PurchaseDetail>();
		if(pdetails != null && pdetails.size() > 0){
        	for(int i=0;i<pdetails.size();i++){
        		for(int j=0;j<details.size();j++){
        			if(pdetails.get(i).getPurchaseCount()==null){
        				deta.add(pdetails.get(i));
        			}
        			if(pdetails.get(i).getId().equals(details.get(j).getId())){
        				deta.add(pdetails.get(i));
        			}
        		}
        	}
        }
        for (int i = 0; i < deta.size() - 1; i++ ) {
            for (int j = deta.size() - 1; j > i; j-- ) {
                if (deta.get(j).getId().equals(deta.get(i).getId())) {
                	deta.remove(j);
                }
            }
        }
        for(int i=0;i<deta.size();i++){
        	if(i==0){
        		continue;
        	}
        	if(i==1&&deta.get(i+1).getPurchaseCount()==null){
        		deta.remove(i);
        	}
        	if(i!=(deta.size()-1)){
        		if(deta.get(i).getPurchaseCount()==null&&deta.get(i+1).getPurchaseCount()==null){
        			deta.remove(i);
	        	}
        	}
        		if(deta.get(deta.size()-1).getPurchaseCount()==null){
        			deta.remove(deta.size()-1);
        		}
        }
        return deta;
	}
}
