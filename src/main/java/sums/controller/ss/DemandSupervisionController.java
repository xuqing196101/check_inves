package sums.controller.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import sums.service.ss.DemandSupervisionService;
import sums.service.ss.SupervisionService;
import bss.controller.base.BaseController;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.TaskService;

import com.github.pagehelper.PageHelper;
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
    private DemandSupervisionService demandSupervisionService;
    
    @Autowired
    private TaskService taskService;
    
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
                //采购计划状态
                Integer planStatus = demandSupervisionService.planStatus(required.getFileId());
                //项目状态
                Integer projectStatus = demandSupervisionService.projectStatus(required.getFileId());
                //合同状态
                Integer contractStatus = demandSupervisionService.contractStatus(required.getFileId());
                model.addAttribute("planStatus", planStatus);
                model.addAttribute("projectStatus", projectStatus);
                model.addAttribute("contractStatus", contractStatus);
                model.addAttribute("requiredId", required.getId());
            }
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
            model.addAttribute("type", type);
            model.addAttribute("demand", required);
            model.addAttribute("planId", required.getUniqueId());
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
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("collectId", collectPlan.getId());
                    List<Task> listBycollect = taskService.listBycollect(maps);
                    if(listBycollect != null && listBycollect.size() > 0){
                        collectPlan.setTaskId(listBycollect.get(0).getDocumentNumber());
                    }
                    model.addAttribute("collectPlan", collectPlan);
                    model.addAttribute("planId", collectPlan.getId());
                }
                model.addAttribute("type", type);
                model.addAttribute("demand", required);
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
    public String viewProject(String requiredId, Model model){
        if(StringUtils.isNotBlank(requiredId)){
            PurchaseRequired required = purchaseRequiredService.queryById(requiredId);
            if(required != null){
                List<Project> list = demandSupervisionService.viewProject(required.getId());
                model.addAttribute("requiredId", required.getId());
                model.addAttribute("kind", DictionaryDataUtil.find(5));
                model.addAttribute("list", list);
            }
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
                //HashMap<String, Object> mapAdetail = new HashMap<String, Object>();
                List<ProjectDetail> details = new ArrayList<ProjectDetail>();
                //List<AdvancedDetail> adList = new ArrayList<AdvancedDetail>();
                //根据采购需求ID可能会有N个项目
                for (PurchaseRequired purchaseRequired : requireds) {
                    mapDetail.put("id", id);
                    mapDetail.put("requiredId", purchaseRequired.getId());
                    List<ProjectDetail> selectById = projectDetailService.selectById(mapDetail);
                    if(selectById != null && selectById.size() > 0){
                        details.addAll(selectById);
                    } else {
                        /*mapAdetail.put("advancedProject", id);
                        mapAdetail.put("requiredId", purchaseRequired.getId());
                        List<AdvancedDetail> advancedDetails = advancedDetailService.selectByAll(mapAdetail);
                        if(advancedDetails != null && advancedDetails.size() > 0){
                            adList.addAll(advancedDetails);
                        }*/
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
                        List<ProjectDetail> list = new ArrayList<ProjectDetail>();
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
                    /*HashMap<String, Object> maps = new HashMap<String, Object>();
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
                    return "sums/ss/planSupervision/adPackage_view";*/
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
    
    
    @RequestMapping(value="/paixu",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String paixu(Model model, String id, String fileId,Integer page){
        JSONObject jsonObj = new JSONObject();
        HashMap<String, Object> map = new HashMap<>();
        map.put("fileId", fileId);
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
        List<PurchaseDetail> details = purchaseDetailService.getByMap(map);
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
            List<PurchaseRequired> purchaseRequireds = purchaseRequiredService.getUnique(id);
            if(purchaseRequireds != null && purchaseRequireds.size() > 0){
                for (PurchaseRequired purchaseRequired : purchaseRequireds) {
                    HashMap<String, Object> maps = new HashMap<>();
                    maps.put("id", purchaseRequired.getId());
                    List<PurchaseRequired> purchaseDetails = purchaseRequiredService.selectByParentId(maps);
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
