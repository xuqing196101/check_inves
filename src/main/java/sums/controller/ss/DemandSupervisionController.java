package sums.controller.ss;

import java.math.BigDecimal;
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

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import sums.service.ss.SupervisionService;
import bss.controller.base.BaseController;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.AdvancedProject;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.AdvancedDetailService;
import bss.service.ppms.AdvancedPackageService;
import bss.service.ppms.AdvancedProjectService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/* 
 *@Title:DemandSupervisionController
 *@Description:采购采购需求监督控制类
 *@author tian zhiqiang 
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
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
    private UserServiceI userService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    
    @Autowired
    private ProjectDetailService projectDetailService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private PackageService packageService;
    
    @Autowired
    private ContractRequiredService contractRequiredService;
    
    @Autowired
    private PurchaseContractService contractService;
    
    @Autowired
    private SupplierService supplierService;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private AdvancedDetailService advancedDetailService;
    
    @Autowired
    private AdvancedProjectService advancedProjectService;
    
    @Autowired
    private AdvancedPackageService advancedPackageService;
    
	/**
	 * 
	 * 〈简述〉 〈需求列表〉
	 * 
	 * @author tian zhiqiang
	 * @date 2016-11-11 下午2:48:57
	 * @Description: 查询需求列表
	 * @param @param model
	 * @param @param 分页
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/demandSupervisionList", produces = "text/html;charset=UTF-8")
	public String demandSupervisionList(@CurrentUser User user, PurchaseRequired purchaseRequired, Integer page, Model model)
			throws Exception {
		//是否是详细，1是主要，不是1为明细
		purchaseRequired.setIsMaster(1);
        
        if(purchaseRequired.getStatus()==null){
            purchaseRequired.setStatus("total");
        } else if(purchaseRequired.getStatus().equals("5")){
            purchaseRequired.setSign("5");
        }
        if(purchaseRequired.getStatus().equals("total")){
            purchaseRequired.setStatus(null);
        }
        if (page == null ){
            page = StaticVariables.DEFAULT_PAGE;
        }
        List<Role> roles = user.getRoles();
        boolean bool=false;
        if(roles!=null&&roles.size()>0){
            for(Role r:roles){
                if(r.getCode().equals("NEED_M")){
                    bool=true;
                }
            }
        }
        if(bool!=true){
            purchaseRequired.setUserId(user.getId());
        } 
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page);
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
		model.addAttribute("purchaseRequired", purchaseRequired);
		return "sums/ss/demandSupervision/list";
	}
	
	 /**
     * 〈查看需求进度页面〉
     * 
     * @author Tian zhiqiang
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/demandSupervisionView")
    public String view(String id, String type, Model model) {
        if(StringUtils.isNotBlank(id)){
            PurchaseRequired required = purchaseRequiredService.queryById(id);
            if(required != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("fileId", required.getFileId());
                List<PurchaseDetail> details = purchaseDetailService.getByMap(map);
                if(details != null && details.size() > 0){
                    //获取采购计划
                    CollectPlan collectPlan = collectPlanService.queryById(details.get(0).getUniqueId());
                    Integer progressBarPlan = supervisionService.progressBarPlan(collectPlan.getStatus());
                    model.addAttribute("planStatus", progressBarPlan);
                    model.addAttribute("details", details);
                    
                    //获取采购项目
                    HashSet<String> set = new HashSet<>(); 
                    List<Integer> statusContract = new ArrayList<Integer>();
                    for (PurchaseDetail purchaseDetail : details) {
                        if(!"1".equals(purchaseDetail.getParentId())){
                            HashMap<String, Object> maps = new HashMap<>();
                            maps.put("requiredId", purchaseDetail.getId());
                            List<ProjectDetail> selectById = projectDetailService.selectById(maps);
                            if(selectById != null && selectById.size() > 0){
                                List<ContractRequired> contractRequireds = contractRequiredService.selectConRequByDetailId(selectById.get(0).getId());
                                if(contractRequireds != null && contractRequireds.size()>0){
                                    PurchaseContract purchaseContract = contractService.selectById(contractRequireds.get(0).getContractId());
                                    Integer progressBarContract = supervisionService.progressBarContract(purchaseContract.getStatus());
                                    statusContract.add(progressBarContract);
                                    model.addAttribute("contractRequireds", contractRequireds);
                                }
                                set.add(selectById.get(0).getProject().getId());
                            }
                        }
                    }
                    //根据set集合获取不同的项目
                    if(set != null && set.size() > 0){
                        List<String> status = new ArrayList<String>();
                        for (String string : set) {
                            Project project = projectService.selectById(string);
                            if(!"4".equals(project.getStatus())){
                                String projectStatus = supervisionService.progressBarProject(project.getStatus());
                                status.add(projectStatus);
                                model.addAttribute("project", project);
                            }
                        }
                        if(status != null && status.size() > 0){
                            Integer num = 0;
                            for (String string : status) {
                                double number = Integer.valueOf(string)/status.size();
                                BigDecimal b = new BigDecimal(number);
                                double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                                num += (int)total;
                            }
                            model.addAttribute("projectStatus", num);
                        }
                    }
                    
                    if(statusContract != null && statusContract.size() > 0){
                        Integer num = 0;
                        for (Integer integer : statusContract) {
                            double number = integer/statusContract.size();
                            BigDecimal b = new BigDecimal(number);
                            double total = b.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                            num += (int)total;
                        }
                        model.addAttribute("contractStatus", num);
                    }
                } else {
                    //如果采购计划为空的话，查一下有没有预研
                    AdvancedDetail detail = advancedDetailService.selectByRequiredId(required.getId());
                    if(detail != null){
                        AdvancedProject project = advancedProjectService.selectById(detail.getAdvancedProject());
                        if(project != null && !"0".equals(project.getStatus())){
                            String projectStatus = supervisionService.progressBarProject(project.getStatus());
                            if(StringUtils.isNotBlank(projectStatus)){
                                model.addAttribute("projectStatus", projectStatus);
                            }
                            model.addAttribute("project", project);
                        }
                    }
                }
            }
            model.addAttribute("requiredId", id);
        }
    	return "sums/ss/demandSupervision/demand_view";
    }
    
    
    /**
     * 
     *〈查看需求明细〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/demandDetail")
    public String demandDetail(String requiredId,String type, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                 User user = userService.getUserById(required.getUserId());
                 required.setUserId(user.getRelName());
             }
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", required.getId());
            List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
            for (PurchaseRequired purchaseRequired : list) {
                if(purchaseRequired.getPrice() != null){
                    DictionaryData findById = DictionaryDataUtil.findById(purchaseRequired.getPurchaseType());
                    String[] progressBarPlan = supervisionService.progressBar(purchaseRequired.getId());
                    purchaseRequired.setProgressBar(progressBarPlan[0]);
                    purchaseRequired.setStatus(progressBarPlan[1]);
                    model.addAttribute("code", findById.getCode());
                } else {
                    purchaseRequired.setPurchaseType(null);
                    purchaseRequired.setStatus(null);
                }
            }
            model.addAttribute("list", list);
            model.addAttribute("type", type);
            model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
            model.addAttribute("demand", required);
        }
    	
		return "sums/ss/demandSupervision/detail_view";
    }
    
    /**
     * 
     *〈查询计划明细〉
     *〈详细描述〉
     * @author FengTian
     * @param requiredId
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/planDetail")
    public String planDetail(String requiredId,String type, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                HashMap<String, Object> map = new HashMap<>();
                map.put("fileId", required.getFileId());
                List<PurchaseDetail> detail = purchaseDetailService.getByMap(map);
                if(detail != null && detail.size() > 0){
                    CollectPlan collectPlan = collectPlanService.queryById(detail.get(0).getUniqueId());
                    User user = userService.getUserById(collectPlan.getUserId());
                    collectPlan.setUserId(user.getRelName());
                    model.addAttribute("collectPlan", collectPlan);
                    
                    for (PurchaseDetail purchaseDetail : detail) {
                        if(purchaseDetail.getPrice() != null){
                            DictionaryData findById = DictionaryDataUtil.findById(purchaseDetail.getPurchaseType());
                            String[] progressBarPlan = supervisionService.progressBar(purchaseDetail.getId());
                            purchaseDetail.setProgressBar(progressBarPlan[0]);
                            purchaseDetail.setStatus(progressBarPlan[1]);
                            model.addAttribute("code", findById.getCode());
                        } else {
                            purchaseDetail.setPurchaseType(null);
                            purchaseDetail.setStatus(null);
                        }
                    }
                }
                model.addAttribute("list", detail);
                model.addAttribute("kind", DictionaryDataUtil.find(5));
                model.addAttribute("type", type);
            }
        }
    	return "sums/ss/demandSupervision/detail_view";
    }
    
    /**
     * 
     *〈查看项目〉
     *〈详细描述〉
     * @author Administrator
     * @param requiredId
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/viewProject")
    public String viewProject(String requiredId,String type, Model model){
    	HashMap<String, Object> map=new HashMap<String, Object>();
    	map.put("id", requiredId);
    	List<PurchaseRequired> requireds = purchaseRequiredService.selectByParentId(map);
    	if(requireds != null && requireds.size()>0){
    	    HashSet<String> set = new HashSet<>();
    	    List<Project> list = new ArrayList<>();
    	    List<AdvancedProject> lists = new ArrayList<>();
    	    //根据采购需求ID可能会有N个项目
    	    for (PurchaseRequired purchaseRequired : requireds) {
    	        if(purchaseRequired.getPrice() != null){
    	            HashMap<String, Object> maps = new HashMap<String, Object>();
                    maps.put("requiredId", purchaseRequired.getId());
                    List<ProjectDetail> selectById = projectDetailService.selectById(maps);
                    if(selectById != null && selectById.size() > 0){
                        for (ProjectDetail projectDetail : selectById) {
                            set.add(projectDetail.getProject().getId());
                        }
                    } else {
                        AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(purchaseRequired.getId());
                        if(advancedDetail != null){
                            set.add(advancedDetail.getAdvancedProject());
                        }
                    }
    	        }
            }
    	    for (String string : set) {
                Project project = projectService.selectById(string);
                if(project != null){
                    User user = userService.getUserById(project.getAppointMan());
                    project.setAppointMan(user.getRelName());
                    Orgnization org = orgnizationService.getOrgByPrimaryKey(project.getPurchaseDepId());
                    project.setPurchaseDepId(org.getName());
                    DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                    project.setStatus(findById.getName());
                    list.add(project);
                    
                } else {
                    AdvancedProject advancedProject = advancedProjectService.selectById(string);
                    if(advancedProject != null && !"0".equals(advancedProject.getStatus())){
                        User user = userService.getUserById(advancedProject.getAppointMan());
                        advancedProject.setAppointMan(user.getRelName());
                        Orgnization org = orgnizationService.getOrgByPrimaryKey(advancedProject.getPurchaseDepId());
                        advancedProject.setPurchaseDepId(org.getName());
                        DictionaryData findById = DictionaryDataUtil.findById(advancedProject.getStatus());
                        advancedProject.setStatus(findById.getName());
                        lists.add(advancedProject);
                    }
                    model.addAttribute("list", lists);
                }
                if(list != null && list.size() > 0){
                    model.addAttribute("list", list);
                }
            }
    	    model.addAttribute("requiredId", requiredId);
    	    model.addAttribute("kind", DictionaryDataUtil.find(5));
    	}
    	
    	return "sums/ss/demandSupervision/project_view";
    }
    
    /**
     * 
     *〈查看项目分包〉
     *〈详细描述〉
     * @author FengTian
     * @param id
     * @param requiredId
     * @param model
     * @return
     */
    @RequestMapping("/viewPack")
    public String viewPack(String id, String requiredId, Model model){
        if(StringUtils.isNotBlank(id)){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", requiredId);
            List<PurchaseRequired> requireds = purchaseRequiredService.selectByParentId(map);
            if(requireds != null && requireds.size()>0){
                HashMap<String, Object> mapDetail = new HashMap<String, Object>();
                List<ProjectDetail> details = new ArrayList<ProjectDetail>();
                List<AdvancedDetail> adList = new ArrayList<AdvancedDetail>();
                //根据采购需求ID可能会有N个项目
                for (PurchaseRequired purchaseRequired : requireds) {
                    mapDetail.put("id", id);
                    mapDetail.put("requiredId", purchaseRequired.getId());
                    List<ProjectDetail> selectById = projectDetailService.selectById(mapDetail);
                    if(selectById != null && selectById.size() > 0){
                        details.addAll(selectById);
                    } else {
                        List<AdvancedDetail> advancedDetails = advancedDetailService.selectByAll(mapDetail);
                        if(advancedDetails != null && advancedDetails.size() > 0){
                            adList.addAll(advancedDetails);
                        }
                    }
                }
                if(details != null && details.size() > 0){
                    //正式项目
                    HashMap<String, Object> maps = new HashMap<String, Object>();
                    maps.put("projectId", id);
                    List<Packages> packages = packageService.findByID(maps);
                    List<Packages> lists = new ArrayList<Packages>();
                    //判断有没有分包，没有分包进else
                    if(packages != null && packages.size() > 0){
                        for (Packages packages2 : packages) {
                            List<ProjectDetail> list = new ArrayList<ProjectDetail>();
                            for (int i = 0; i < details.size(); i++ ) {
                                if(packages2.getId().equals(details.get(i).getPackageId())){
                                    DictionaryData findById = DictionaryDataUtil.findById(details.get(i).getPurchaseType());
                                    details.get(i).setPurchaseType(findById.getName());
                                    String[] progressBarPlan = supervisionService.progressBar(details.get(i).getRequiredId());
                                    details.get(i).setProgressBar(progressBarPlan[0]);
                                    details.get(i).setStatus(progressBarPlan[1]);
                                    list.add(details.get(i));
                                }
                                sort(list);//进行排序
                                packages2.setProjectDetails(list);
                            }
                        }
                        for (int i = 0; i < packages.size(); i++ ) {
                            if(packages.get(i).getProjectDetails().size() > 0){
                                lists.add(packages.get(i));
                            }
                        }
                        model.addAttribute("packages", lists);
                        Project project = projectService.selectById(id);
                        model.addAttribute("code", DictionaryDataUtil.findById(project.getPurchaseType()).getCode());
                    } else {
                        for (ProjectDetail detail : details) {
                            if(detail.getPrice() != null){
                                DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                                detail.setPurchaseType(findById.getName());
                                String[] progressBarPlan = supervisionService.progressBar(detail.getRequiredId());
                                detail.setProgressBar(progressBarPlan[0]);
                                detail.setStatus(progressBarPlan[1]);
                            } else {
                                detail.setPurchaseType(null);
                                detail.setStatus(null);
                            }
                            
                        }
                        model.addAttribute("details", details);
                    }
                    
                    Project project = projectService.selectById(id);
                    if(project != null){
                        DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                        project.setStatus(findById.getName());
                        User user = userService.getUserById(project.getAppointMan());
                        project.setAppointMan(user.getRelName());
                        model.addAttribute("project", project);
                    }
                    return "sums/ss/planSupervision/package_view";
                } else {
                    //如果没有正式项目，查一下又没有预研项目
                    HashMap<String, Object> maps = new HashMap<String, Object>();
                    maps.put("projectId", id);
                    List<AdvancedPackages> packages = advancedPackageService.selectByAll(maps);
                    List<AdvancedPackages> lists = new ArrayList<AdvancedPackages>();
                    //判断有没有分包，没有分包进else
                    if(packages != null && packages.size() > 0){
                        for (AdvancedPackages packages2 : packages) {
                            List<AdvancedDetail> list = new ArrayList<AdvancedDetail>();
                            for (int i = 0; i < adList.size(); i++ ) {
                                if(packages2.getId().equals(adList.get(i).getPackageId())){
                                    DictionaryData findById = DictionaryDataUtil.findById(adList.get(i).getPurchaseType());
                                    adList.get(i).setPurchaseType(findById.getName());
                                    String[] progressBarPlan = supervisionService.adProgressBar(adList.get(i).getRequiredId());
                                    adList.get(i).setProgressBar(progressBarPlan[0]);
                                    adList.get(i).setStatus(progressBarPlan[1]);
                                    list.add(adList.get(i));
                                }
                                //sort(list);//进行排序
                                packages2.setAdvancedDetails(list);
                            }
                        }
                        for (int i = 0; i < packages.size(); i++ ) {
                            if(packages.get(i).getAdvancedDetails().size() > 0){
                                lists.add(packages.get(i));
                            }
                        }
                        model.addAttribute("packages", lists);
                        AdvancedProject project = advancedProjectService.selectById(id);
                        model.addAttribute("code", DictionaryDataUtil.findById(project.getPurchaseType()).getCode());
                    } else {
                        for (AdvancedDetail detail : adList) {
                            if(detail.getPrice() != null){
                                DictionaryData findById = DictionaryDataUtil.findById(detail.getPurchaseType());
                                detail.setPurchaseType(findById.getName());
                                String[] progressBarPlan = supervisionService.adProgressBar(detail.getRequiredId());
                                detail.setProgressBar(progressBarPlan[0]);
                                detail.setStatus(progressBarPlan[1]);
                            } else {
                                detail.setPurchaseType(null);
                                detail.setStatus(null);
                            }
                            
                        }
                        model.addAttribute("details", adList);
                    }
                    AdvancedProject project = advancedProjectService.selectById(id);
                    if(project != null){
                        DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                        project.setStatus(findById.getName());
                        User user = userService.getUserById(project.getAppointMan());
                        project.setAppointMan(user.getRelName());
                        model.addAttribute("project", project);
                    }
                    return "sums/ss/planSupervision/adPackage_view";
                }
            }
        }
        return "sums/ss/planSupervision/package_view";
    }
    
    /**
     * 
     *〈查看合同〉
     *〈详细描述〉
     * @author FengTian
     * @param requiredId
     * @param model
     * @return
     */
    @RequestMapping("/viewContract")
    public String viewContract(String requiredId, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", requiredId);
            List<PurchaseRequired> requireds = purchaseRequiredService.selectByParentId(map);
            if(requireds != null && requireds.size() > 0){
                HashMap<String, Object> mapDetail = new HashMap<String, Object>();
                List<ContractRequired> contractRequired = new ArrayList<ContractRequired>();
                //根据采购需求ID可能会有N个项目
                for (PurchaseRequired purchaseRequired : requireds) {
                    mapDetail.put("requiredId", purchaseRequired.getId());
                    List<ProjectDetail> selectById = projectDetailService.selectById(mapDetail);
                    if(selectById != null && selectById.size() > 0){
                        for (ProjectDetail projectDetail : selectById) {
                            List<ContractRequired> contractRequireds = contractRequiredService.selectConRequByDetailId(projectDetail.getId());
                            if(contractRequireds != null && contractRequireds.size() > 0){
                                contractRequired.addAll(contractRequireds);
                            }
                        }
                    }
                }
                List<PurchaseContract> contracts = new ArrayList<>();
                if(contractRequired != null && contractRequired.size()>0){
                    for (ContractRequired required : contractRequired) {
                        PurchaseContract purchaseContract = contractService.selectById(required.getContractId());
                        Supplier su = null;
                        Orgnization org = null;
                        if(purchaseContract.getSupplierDepName()!=null){
                            su = supplierService.selectOne(purchaseContract.getSupplierDepName());
                        }
                        if(purchaseContract.getPurchaseDepName()!=null){
                            org = orgnizationService.getOrgByPrimaryKey(purchaseContract.getPurchaseDepName());
                        }
                        if(org!=null){
                            if(org.getName()==null){
                                purchaseContract.setShowDemandSector("");
                            }else{
                                purchaseContract.setShowDemandSector(org.getName());
                            }
                        }
                        if(su!=null){
                            if(su.getSupplierName()!=null){
                                purchaseContract.setShowSupplierDepName(su.getSupplierName());
                            }else{
                                purchaseContract.setShowSupplierDepName("");
                            }
                        }
                        contracts.add(purchaseContract);
                    }
                } 
                model.addAttribute("listContract", contracts);
            }
        }
        return "sums/ss/planSupervision/contract_view";
    }
    
    /**
     * 
     *〈项目重新排序〉
     *〈详细描述〉
     * @author FengTian
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
