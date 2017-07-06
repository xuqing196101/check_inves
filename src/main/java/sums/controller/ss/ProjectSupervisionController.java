package sums.controller.ss;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.ss.ProjectSupervisionService;
import sums.service.ss.SupervisionService;
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
    private SupervisionService supervisionService;
    
    @Autowired
    private ProjectSupervisionService projectSupervisionService;
    
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
            map.put("userId", user.getId());
            map.put("principal", user.getId());
            if (page == null) {
                page = 1;
            }
            PageHelper.startPage(page, Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
            List<Project> list = projectService.selectByConition(map);
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
        if(StringUtils.isNotBlank(id)){
            Project project = projectService.selectById(id);
            if(project != null){
                 //项目状态
            	 String projectStatus = supervisionService.progressBarProject(project.getStatus());
            	 //合同状态
            	 Integer contractStatus = projectSupervisionService.contractStatus(project.getId());
            	 
                 model.addAttribute("projectStatus", Integer.valueOf(projectStatus));
                 model.addAttribute("contractStatus", contractStatus);
                 model.addAttribute("project", project);
            }
        }
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
                    listContract.add(contract);
                }
            }
            model.addAttribute("listContract", listContract);
    	}
    	return "sums/ss/planSupervision/contract_view";
    }
    
    /**
     * 
    * @Title: viewPack
    * @author FengTian 
    * @date 2017-6-1 上午9:45:22  
    * @Description: 项目明细 
    * @param @param projectId
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping("/viewPack")
    public String viewPack(String projectId, Model model){
        if(StringUtils.isNotBlank(projectId)){
        	Project project = projectService.selectById(projectId);
        	if(project != null){
        		List<Packages> list = projectSupervisionService.viewPack(project.getId());
        		if(list != null && list.size() > 0){
        			model.addAttribute("packages", list);
        		} else {
        			List<ProjectDetail> lists = new ArrayList<ProjectDetail>();
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", project.getId());
                    List<ProjectDetail> details = detailService.selectById(maps);
                    for (ProjectDetail detail : details) {
                        if(detail.getPrice() != null){
                            DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                            detail.setPurchaseType(findById.getName());
                            String[] progressBarPlan = supervisionService.progressBar(detail.getRequiredId(), project.getId());
                            detail.setProgressBar(progressBarPlan[0]);
                            detail.setStatus(progressBarPlan[1]);
                        } else {
                            detail.setPurchaseType(null);
                            detail.setStatus(null);
                        }
                        lists.add(detail);
                    }
                    model.addAttribute("details", lists);
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
        	Project project = projectService.selectById(projectId);
        	HashMap<String, Object> map = new HashMap<>();
        	if(StringUtils.isNotBlank(project.getParentId()) && !"1".equals(project.getParentId())){
        		map.put("projectId", project.getParentId());
        	} else {
        		map.put("projectId", project.getId());
        	}
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
            model.addAttribute("projectId", projectId);
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
                    PurchaseRequired required = requiredService.queryById(projectDetail.getRequiredId());
                    if(!"1".equals(required.getParentId())){
                        set.add(required.getFileId());
                    }
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
                model.addAttribute("projectId", projectId);
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
    
    /**
     * 
     *〈查看需求明细〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param type
     * @param projectId
     * @param model
     * @return
     */
    @RequestMapping("/viewDetail")
    public String viewDetail(String id, String projectId, Model model){
        if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(projectId)){
            List<PurchaseRequired> details = projectSupervisionService.viewDeandDetail(projectId, id);
            if(details != null && details.size() > 0){
                model.addAttribute("list", details);
            }
            model.addAttribute("planId", id);
            model.addAttribute("type", "0");
            model.addAttribute("projectId", projectId);
        }
        return "sums/ss/projectSupervision/detail_view";
    }
    
    /**
     * 
    * @Title: viewDetails
    * @author FengTian 
    * @date 2017-6-1 上午10:19:16  
    * @Description: 查询采购明细 
    * @param @param id
    * @param @param projectId
    * @param @param model
    * @param @return      
    * @return String
     */
    @RequestMapping("/viewDetails")
    public String viewDetails(String id, String projectId, Model model){
    	if(StringUtils.isNotBlank(id) && StringUtils.isNotBlank(projectId)){
    		List<PurchaseDetail> viewDetail = projectSupervisionService.viewPlanDetail(projectId, id);
    		if(viewDetail != null && viewDetail.size() > 0){
    			model.addAttribute("list", viewDetail);
    		}
            CollectPlan collectPlan = collectPlanService.queryById(id);
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
            model.addAttribute("planId", collectPlan.getId());
            model.addAttribute("projectId", projectId);
            model.addAttribute("type", "1");
        }
        return "sums/ss/projectSupervision/detail_view";
    }
    
    
    /*@RequestMapping(value="/paixu",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String paixu(Model model, String id, String projectId, Integer page){
        JSONObject jsonObj = new JSONObject();
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<PurchaseDetail> details = purchaseDetailService.getUnique(id,null,null);
        if(details != null && details.size() > 0){
            List<PurchaseDetail> list = new ArrayList<PurchaseDetail>();
            HashMap<String, Object> map = new HashMap<>();
            for (int i = 0; i < details.size(); i++ ) {
                map.put("id", projectId);
                List<ProjectDetail> projectDetails = detailService.selectById(map);
                for (ProjectDetail projectDetail : projectDetails) {
                    if(details.get(i).getId().equals(projectDetail.getRequiredId())){
                        list.add(details.get(i));
                    }
                }
            }
            for (PurchaseDetail detail : list) {
                if(detail.getPrice() != null){
                    DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                    detail.setPurchaseType(findById.getName());
                    String[] progressBarPlan = supervisionService.progressBar(detail.getId());
                    detail.setProgressBar(progressBarPlan[0]);
                    detail.setStatus(progressBarPlan[1]);
                    model.addAttribute("code", findById.getCode());
                }else{
                    detail.setPurchaseType(null);
                    detail.setStatus(null);
                }
            }
            PageInfo<PurchaseDetail> pageInfo = new PageInfo<PurchaseDetail>(list);
            jsonObj.put("pages", pageInfo.getPages());
            jsonObj.put("data", pageInfo.getList());
        } else {
            List<PurchaseRequired> purchaseRequireds = requiredService.getUnique(id);
            if(purchaseRequireds != null && purchaseRequireds.size() > 0){
                List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
                for (PurchaseRequired purchaseRequired : purchaseRequireds) {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", projectId);
                    List<ProjectDetail> projectDetails = detailService.selectById(map);
                    for (ProjectDetail projectDetail : projectDetails) {
                        if(purchaseRequired.getId().equals(projectDetail.getRequiredId())){
                            list.add(purchaseRequired);
                        }
                    }
                }
                
                for (PurchaseRequired detail : list) {
                    if(detail.getPrice() != null){
                        DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                        detail.setPurchaseType(findById.getName());
                        String[] progressBarPlan = supervisionService.progressBar(detail.getId());
                        detail.setProgressBar(progressBarPlan[0]);
                        detail.setStatus(progressBarPlan[1]);
                        model.addAttribute("code", findById.getCode());
                    }else{
                        detail.setPurchaseType(null);
                        detail.setStatus(null);
                    }
                }
                PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
                PageInfo<PurchaseRequired> pageInfo = new PageInfo<PurchaseRequired>(list);
                jsonObj.put("pages", pageInfo.getPages());
                jsonObj.put("data", pageInfo.getList());
            }
        }
        
        return jsonObj.toString();
    }*/

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
    
    
    public List<PurchaseDetail> ranks(List<PurchaseDetail> lists){
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
        for(int i=0;i<lists.size();i++){
            HashMap<String,Object> detailMap = new HashMap<>();
            detailMap.put("id",lists.get(i).getId());
            List<PurchaseDetail> dlist = purchaseDetailService.selectByParentId(detailMap);
            List<PurchaseDetail> plist = purchaseDetailService.selectByParent(detailMap);
            if(dlist.size()>1){
                lists.get(i).setDetailStatus(0);
            }
            if(plist.size()==1&&plist.get(0).getPurchaseCount()==null){
                if(!oneParentId.contains(lists.get(i).getParentId())){
                    oneParentId.add(lists.get(i).getParentId());
                    serialoneOne = 1;
                }
                lists.get(i).setSeq(test(serialoneOne));
                serialoneOne ++;
            }else if(plist.size()==2&&plist.get(1).getPurchaseCount()==null){
                if(!twoParentId.contains(lists.get(i).getParentId())){
                    twoParentId.add(lists.get(i).getParentId());
                    serialtwoTwo = 1;
                }
                lists.get(i).setSeq("（"+test(serialtwoTwo)+"）");
                serialtwoTwo ++;
            }else if(plist.size()==3&&plist.get(2).getPurchaseCount()==null){
                if(!threeParentId.contains(lists.get(i).getParentId())){
                    threeParentId.add(lists.get(i).getParentId());
                    serialthreeThree = 1;
                }
                lists.get(i).setSeq(String.valueOf(serialthreeThree));
                serialthreeThree ++;
            }else if(plist.size()==4&&plist.get(3).getPurchaseCount()==null){
                if(!fourParentId.contains(lists.get(i).getParentId())){
                    fourParentId.add(lists.get(i).getParentId());
                    serialfourFour = 1;
                }
                lists.get(i).setSeq("（"+String.valueOf(serialfourFour)+"）");
                serialfourFour ++;
            }else if(plist.size()==5&&plist.get(4).getPurchaseCount()==null){
                if(!fiveParentId.contains(lists.get(i).getParentId())){
                    fiveParentId.add(lists.get(i).getParentId());
                    serialfiveFive = 0;
                }
                char serialNum = (char) (97 + serialfiveFive);
                lists.get(i).setSeq(String.valueOf(serialNum));
                serialfiveFive++;
            }
            if(dlist.size()==1){
                map.put("id", lists.get(i).getId());
                List<PurchaseDetail> list = purchaseDetailService.selectByParent(map);
                if(!newParentId.contains(lists.get(i).getParentId())){
                    serialOne = 1;
                    serialTwo = 1;
                    serialThree = 1;
                    serialFour = 1;
                    serialFive = 0;
                    serialSix = 0;
                    newParentId.add(lists.get(i).getParentId());
                }
                if(list.size()==1){
                    lists.get(i).setSeq(test(serialOne));
                    serialOne ++;
                }else if(list.size()==2){
                    lists.get(i).setSeq("（"+test(serialTwo)+"）");
                    serialTwo ++;
                }else if(list.size()==3){
                    lists.get(i).setSeq(String.valueOf(serialThree));
                    serialThree ++;
                }else if(list.size()==4){
                    lists.get(i).setSeq("（"+String.valueOf(serialFour)+"）");
                    serialFour ++;
                }else if(list.size()==5){
                    char serialNum = (char) (97 + serialFive);
                    lists.get(i).setSeq(String.valueOf(serialNum));
                    serialFive ++;
                }else if(list.size()==6){
                    char serialNum = (char) (97 + serialSix);
                    lists.get(i).setSeq("（"+serialNum+"）");
                    serialSix ++;
                }
            }
        
        }
        return lists;
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
