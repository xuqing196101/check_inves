package sums.controller.ss;

import iss.model.ps.Article;
import iss.service.ps.ArticleService;
import iss.service.ps.ArticleTypeService;

import java.math.BigDecimal;
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
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.ValidateUtils;
import sums.service.ss.SupervisionService;
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
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SaleTender;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.model.prms.PackageExpert;
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
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SaleTenderService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.prms.PackageExpertService;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

/* 
 *@Title:ContractSupervisionController
 *@Description:采购合同监督
 *@author wanlin li
 *@date 2017-03-10下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/contractSupervision")
public class ContractSupervisionController {
	@Autowired
    private PurchaseContractService purchaseContractService;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@Autowired
	private SupplierService supplierService;
	@Autowired
    private UploadService uploadService;
    @Autowired
    private SupplierCheckPassService supplierCheckPassService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectTaskService projectTaskService;
    @Autowired
    private PurchaseRequiredService requiredService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private CollectPlanService collectPlanService;
    @Autowired
    private PurchaseDetailService purchaseDetailService;
    @Autowired
    private UserServiceI userService;
    @Autowired
    private ContractRequiredService contractRequiredService;
    @Autowired
    private PackageService packageService;
    @Autowired
    private ProjectDetailService detailService;
    @Autowired
    private PurchaseManagementService managementService;
    @Autowired
    private AuditPersonService auditPersonService;
    @Autowired
    private AdvancedDetailService advancedDetailService;
    @Autowired
    private AdvancedProjectService advancedProjectService;
    @Autowired
    private FlowMangeService flowMangeService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeService articelTypeService;
    @Autowired
    private SaleTenderService saleTenderService;
    @Autowired
    private PackageExpertService packageExpertService;
    
    @Autowired
    private SupervisionService supervisionService;
    
    @Autowired
    private ExpertService expertService;
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user,PurchaseContract purCon,Integer page){
		if(page==null){
            page=1;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if(purCon.getProjectName()!=null&&!"".equals(purCon.getProjectName())){
            map.put("projectName", purCon.getProjectName());
        }
        if(purCon.getCode()!=null&&!"".equals(purCon.getCode())){
            map.put("code", purCon.getCode());
        }
        if(purCon.getSupplierDepName()!=null&&!"".equals(purCon.getSupplierDepName())){
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if(purCon.getPurchaseDepName()!=null&&!"".equals(purCon.getPurchaseDepName())){
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if(purCon.getDemandSector()!=null&&!"".equals(purCon.getDemandSector())){
            map.put("demandSector", purCon.getDemandSector());
        }
        if(purCon.getDocumentNumber()!=null&&!"".equals(purCon.getDocumentNumber())){
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if(purCon.getYear_string()!=null&&!"".equals(purCon.getYear_string())){
            if(ValidateUtils.Integer(purCon.getYear_string())){
                map.put("year", new BigDecimal(purCon.getYear_string()));
            }else{
                map.put("year", 1234);
            }
        }
        if(purCon.getBudgetSubjectItem()!=null){
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        Orgnization orgnization = orgnizationServiceI.findByCategoryId(user.getOrg().getId());
        List<PurchaseContract> draftConList = new ArrayList<PurchaseContract>();
        if("1".equals(orgnization.getTypeName())){
            map.put("purchaseDepName", user.getOrg().getId());
            if(purCon.getStatus()!=null){
                draftConList = purchaseContractService.selectAllContractByStatus(map);
            }else{
                draftConList = purchaseContractService.selectAllContractByCode(map);
            }
          
        }
        for(PurchaseContract pur:draftConList){
        	Supplier su = null;
        	Orgnization org = null;
        	if(pur.getSupplierDepName()!=null){
        		su = supplierService.selectOne(pur.getSupplierDepName());
        	}
            //				PurchaseDep purdep = purchaseOrgnizationServiceI.selectPurchaseById(pur.getBingDepName());
        	if(pur.getPurchaseDepName()!=null){
        		org = orgnizationServiceI.getOrgByPrimaryKey(pur.getPurchaseDepName());
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
        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(draftConList);
        model.addAttribute("list", list);
        model.addAttribute("draftConList", draftConList);
        model.addAttribute("purCon", purCon);
		return "sums/ss/contractSupervision/draftlist";
	}
	@RequestMapping(value="/contSupervision",produces = "text/html;charset=UTF-8")
	public String detailContract(Model model, PurchaseContract purCon,Integer page){
		PurchaseContract purchaseContract = purchaseContractService.selectById(purCon.getId());
		Integer contractStatus = supervisionService.progressBarContract(purchaseContract.getStatus());
		model.addAttribute("contractStatus", contractStatus);
		model.addAttribute("contract",purchaseContract);
		List<SupplierCheckPass> SupplierCheckPass = supplierCheckPassService.getByContractId(purchaseContract.getId());
		Project project=null;
		if(SupplierCheckPass!=null&&SupplierCheckPass.size()>0){
			String projectId = SupplierCheckPass.get(0).getProjectId();
			project = projectService.selectById(projectId);
			model.addAttribute("project",project);
		}
		return "sums/ss/contractSupervision/contractSupervision";
	}
	@RequestMapping(value="/filePage",produces = "text/html;charset=UTF-8")
	public String filePage(Model model,String id){
		String typeId = DictionaryDataUtil.getId("CONTRACT_FILE");
    	List<UploadFile> files = uploadService.getFilesOther(id, typeId, Constant.TENDER_SYS_KEY+"");
    	if(files!=null&&files.size()>0){
    		model.addAttribute("status", "ok");
    	}else{
    		model.addAttribute("status", "no");
    	}
    	model.addAttribute("id", id);
		return "sums/ss/contractSupervision/filePage";
	}
	@RequestMapping(value="/contractDateil",produces="text/html;charset=UTF-8")
	public String contractDateil(Model model,String id){
		//根据合同id查询合同信息
		PurchaseContract purchaseContract = purchaseContractService.selectById(id);
		if(purchaseContract.getPurchaseDepName()!=null){
			Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(purchaseContract.getPurchaseDepName());
			if(org!=null){
				purchaseContract.setPurchaseDepName(org.getName());
				purchaseContract.setPurchaseBankAccount_string(org.getFax());
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
		
		model.addAttribute("contract",purchaseContract);
		//根据合同id查询中标供应商，包，项目信息,如果合并生成合同则有两条相同的合同id
		List<SupplierCheckPass> SupplierCheckPass = supplierCheckPassService.getByContractId(purchaseContract.getId());
		if(SupplierCheckPass!=null&&SupplierCheckPass.size()>0){
			String projectId = SupplierCheckPass.get(0).getProjectId();
			Project project = projectService.selectById(projectId);
			//项目负责人
			User user = userService.getUserById(project.getPrincipal());
			if(user!=null){
			project.setPrincipal(user.getRelName());
			}else{
				project.setPrincipal("");
			}
			//项目联系人
			if(project.getLinkman()!=null){
			User users = userService.getUserById(project.getLinkman());
			if(users!=null){
				project.setLinkman(users.getRelName());
				}else{
					project.setLinkman("");
				}
			}
			model.addAttribute("project",project);
		}
		
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(purchaseContract.getId());
		model.addAttribute("conRequList",conRequList);
		return "sums/ss/contractSupervision/contractdateil";
	}
	
	/**
	 * 
	 *〈根据合同查项目〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param model
	 * @param id
	 * @param contractId
	 * @return
	 */
	@RequestMapping(value="projectDateil",produces="text/html;charset=UTF-8")
	public String projectList(Model model, String contractId){
	    if(StringUtils.isNotBlank(contractId)){
	        HashSet<String> set = new HashSet<>();
	        List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
	        if(checkPass != null && checkPass.size() > 0){
	            for (SupplierCheckPass supplierCheckPass : checkPass) {
	                set.add(supplierCheckPass.getProjectId());
                }
	        }
	        List<Project> listProject = new ArrayList<Project>();
	        for (String string : set) {
                Project project = projectService.selectById(string);
                String status = DictionaryDataUtil.findById(project.getStatus()).getName();
                project.setStatus(status);
                User user = userService.getUserById(project.getAppointMan());
                project.setAppointMan(user.getRelName());
                Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(project.getPurchaseDepId());
                project.setPurchaseDepName(orgnization.getName());
                listProject.add(project);
            }
	        model.addAttribute("contractId", contractId);
	        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
	        model.addAttribute("listProject", listProject);
	    }
	    return "sums/ss/contractSupervision/project_view";
	}
	
	/**
	 * 
	 *〈查看项目分包页面〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param id
	 * @param contractId
	 * @param model
	 * @return
	 */
	@RequestMapping("/viewPack")
	public String viewPack(String id, String contractId, Model model){
	    List<ContractRequired> findContractRequiredByConId = contractRequiredService.findContractRequiredByConId(contractId);
	    List<ProjectDetail> details = new ArrayList<ProjectDetail>();
	    if(findContractRequiredByConId != null && findContractRequiredByConId.size() > 0){
	        for (ContractRequired required : findContractRequiredByConId) {
	            ProjectDetail detail = detailService.selectByPrimaryKey(required.getDetailId());
	            details.add(detail);
            }
	    }
	    HashMap<String, Object> map = new HashMap<>();
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
            Project project = projectService.selectById(id);
            if(project != null){
                DictionaryData findById = DictionaryDataUtil.findById(project.getStatus());
                project.setStatus(findById.getName());
                User user = userService.getUserById(project.getAppointMan());
                project.setAppointMan(user.getRelName());
                model.addAttribute("project", project);
            }
            model.addAttribute("code", DictionaryDataUtil.findById(project.getPurchaseType()).getCode());
            model.addAttribute("packages", lists);
        }
        return "sums/ss/planSupervision/package_view";
	}
	
	/**
	 * 
	 *〈查看需求列表〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param model
	 * @param contractId
	 * @return
	 */
	@RequestMapping("/viewDemand")
	public String viewDemand(Model model, String contractId){
	    if(StringUtils.isNotBlank(contractId)){
	        List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
	        Set<String> set = new HashSet<String>();
	        if(checkPass != null && checkPass.size() > 0){
	            for (SupplierCheckPass supplierCheckPass : checkPass) {
	                set.add(supplierCheckPass.getProjectId());
                }
	        }
	        HashMap<String, Object> map = new HashMap<>();
	        Set<String> sets = new HashSet<String>();
	        for (String string : set) {
	            map.put("id", string);
	            List<ProjectDetail> selectById = detailService.selectById(map);
	            if(selectById != null && selectById.size() > 0){
	                for (ProjectDetail projectDetail : selectById) {
	                    PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
	                    sets.add(queryById.getFileId());
                    }
	            }
            }
	        List<PurchaseRequired> requireds = new ArrayList<PurchaseRequired>();
	        for (String string : sets) {
	            HashMap<String, Object> maps = new HashMap<>();
	            maps.put("fileId", string);
	            List<PurchaseRequired> byMap = requiredService.getByMap(maps);
	            if(byMap != null && byMap.size() > 0){
	                for (PurchaseRequired purchaseRequired : byMap) {
                        if("1".equals(purchaseRequired.getParentId())){
                            User user = userService.getUserById(purchaseRequired.getUserId());
                            purchaseRequired.setUserId(user.getRelName());
                            requireds.add(purchaseRequired);
                        }
                    }
	            }
            }
	        model.addAttribute("listRequired", requireds);
	        model.addAttribute("contractId", contractId);
	    }
	    return "sums/ss/contractSupervision/demand_view";
	}
	
	/**
	 * 
	 *〈查看采购需求明细〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param model
	 * @param id
	 * @param contractId
	 * @param type
	 * @return
	 */
	@RequestMapping("/viewDetail")
	public String viewDetail(Model model, String id, String contractId, String type){
	    if(StringUtils.isNotBlank(contractId)){
	        List<ContractRequired> findContractRequiredByConId = contractRequiredService.findContractRequiredByConId(contractId);
	        List<PurchaseRequired> requireds = new ArrayList<PurchaseRequired>();
	        if(findContractRequiredByConId != null && findContractRequiredByConId.size() > 0){
	            for (ContractRequired required : findContractRequiredByConId) {
	                ProjectDetail detail = detailService.selectByPrimaryKey(required.getDetailId());
	                PurchaseRequired required2 = requiredService.queryById(detail.getRequiredId());
	                if(id.equals(required2.getUniqueId())){
	                    requireds.add(required2);
	                }
	            }
	        }
	        if(requireds != null && requireds.size() > 0){
	            for (PurchaseRequired purchaseRequired : requireds) {
	                DictionaryData findById = DictionaryDataUtil.findById(purchaseRequired.getPurchaseType());
	                purchaseRequired.setPurchaseType(findById.getName());
	                String[] progressBarPlan = supervisionService.progressBar(purchaseRequired.getId());
	                purchaseRequired.setProgressBar(progressBarPlan[0]);
	                purchaseRequired.setStatus(progressBarPlan[1]);
	                model.addAttribute("code", findById.getCode());
                }
	            model.addAttribute("list", requireds);
	            model.addAttribute("planId", requireds.get(0).getUniqueId());
	        }
	        model.addAttribute("type", type);
	    }
	    return "sums/ss/planSupervision/detail_view";
	}
	
	
	/**
	 * 
	 *〈查看采购计划列表〉
	 *〈详细描述〉
	 * @author FengTian
	 * @param model
	 * @param contractId
	 * @return
	 */
	@RequestMapping(value="planList",produces="text/html;charset=UTF-8")
	public String planList(Model model, String contractId){
		if(StringUtils.isNotBlank(contractId)){
		    List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
            Set<String> set = new HashSet<String>();
            if(checkPass != null && checkPass.size() > 0){
                for (SupplierCheckPass supplierCheckPass : checkPass) {
                    set.add(supplierCheckPass.getProjectId());
                }
            }
            HashMap<String, Object> map = new HashMap<>();
            HashSet<String> sets = new HashSet<>();
            for (String string : set) {
                map.put("id", string);
                List<ProjectDetail> selectById = detailService.selectById(map);
                if(selectById != null && selectById.size() > 0){
                    for (ProjectDetail projectDetail : selectById) {
                        PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
                        sets.add(queryById.getUniqueId());
                    }
                }
            }
            List<CollectPlan> list = new ArrayList<CollectPlan>();
            for (String string : sets) {
                CollectPlan collectPlan = collectPlanService.queryById(string);
                User user = userService.getUserById(collectPlan.getUserId());
                collectPlan.setUserId(user.getRelName());
                list.add(collectPlan);
            }
            model.addAttribute("list", list);
            model.addAttribute("contractId", contractId);
		}
		return "sums/ss/contractSupervision/planList";
	}
	
	@RequestMapping(value="planDateil",produces="text/html;charset=UTF-8")
	public String planDateil(Model model, String id,String contractId, String type){
		if(StringUtils.isNotBlank(contractId)){
		    List<ContractRequired> findContractRequiredByConId = contractRequiredService.findContractRequiredByConId(contractId);
            List<PurchaseDetail> details = new ArrayList<PurchaseDetail>();
            if(findContractRequiredByConId != null && findContractRequiredByConId.size() > 0){
                for (ContractRequired required : findContractRequiredByConId) {
                    ProjectDetail detail = detailService.selectByPrimaryKey(required.getDetailId());
                    PurchaseDetail purchaseDetail = purchaseDetailService.queryById(detail.getRequiredId());
                    if(id.equals(purchaseDetail.getUniqueId())){
                        DictionaryData findById = DictionaryDataUtil.findById(purchaseDetail.getPurchaseType());
                        purchaseDetail.setPurchaseType(findById.getName());
                        String[] progressBarPlan = supervisionService.progressBar(purchaseDetail.getId());
                        purchaseDetail.setProgressBar(progressBarPlan[0]);
                        purchaseDetail.setStatus(progressBarPlan[1]);
                        details.add(purchaseDetail);
                        model.addAttribute("code", findById.getCode());
                    }
                }
                if(details != null && details.size() > 0){
                    sorts(details);
                    model.addAttribute("list", details);
                    model.addAttribute("planId", details.get(0).getUniqueId());
                }
            }
            if(StringUtils.isNotBlank(id)){
                CollectPlan collectPlan = collectPlanService.queryById(id);
                HashMap<String, Object> map = new HashMap<>();
                map.put("collectId", id);
                List<Task> listBycollect = taskService.listBycollect(map);
                if(listBycollect != null && listBycollect.size() > 0){
                    collectPlan.setOrderAt(listBycollect.get(0).getGiveTime());
                }
                User user = userService.getUserById(collectPlan.getUserId());
                collectPlan.setUserId(user.getRelName());
                collectPlan.setPurchaseId(user.getOrgName());
                model.addAttribute("collectPlan", collectPlan);
            }
            model.addAttribute("type", type);
		}
		return "sums/ss/planSupervision/detail_view";
	}
	@RequestMapping(value="demandList",produces="text/html;charset=UTF-8")
	public String demandList(Model model, String id,String contractId){
		List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
		List<PurchaseDetail> details=new ArrayList<PurchaseDetail>();
		List<PurchaseDetail> deta=new ArrayList<PurchaseDetail>();
		List<PurchaseRequired> purchaseRequireds = new ArrayList<PurchaseRequired>();
		Set<String> set=new HashSet<String>();   
		if(checkPass!=null&&checkPass.size()>0){
			   for(SupplierCheckPass pass:checkPass){
				   HashMap<String, Object> hashMap=new HashMap<String, Object>();
				   hashMap.put("packageId", pass.getPackageId());
					List<ProjectDetail> projectDetails = detailService.selectById(hashMap);
					if(projectDetails!=null&&projectDetails.size()>0){
						for(ProjectDetail projectDetail:projectDetails){
							PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
							details.add(queryById);
							if(queryById!=null){
								set.add(queryById.getUniqueId());
							}
						}
					}
			   }
		   }
		Iterator<String> it=set.iterator();
		   while (it.hasNext()) {  
			  String str = it.next();  
			  List<PurchaseDetail> pdetails = purchaseDetailService.getUnique(str,null,null);
			  deta.addAll(listdata(pdetails, details));
			}  
        for(int i=0;i<deta.size();i++){
        	 if("1".equals(deta.get(i).getParentId())){
                 PurchaseRequired required = requiredService.queryById(deta.get(i).getId());
                 purchaseRequireds.add(required);
             } 
        }
        if (purchaseRequireds != null && purchaseRequireds.size() > 0) {
            for (int i = 0; i < purchaseRequireds.size(); i++ ) {
                try {
                    User user = userService.getUserById(purchaseRequireds.get(i).getUserId());
                    purchaseRequireds.get(i).setUserId(user.getRelName());
                } catch (Exception e) {
                	purchaseRequireds.get(i).setUserId("");
                }
            }
        }
        model.addAttribute("list", purchaseRequireds);
        model.addAttribute("contractId", contractId);
		return "sums/ss/contractSupervision/demandList";
	}
	@RequestMapping(value="demandDateil",produces="text/html;charset=UTF-8")
	public String demandDateil(Model model, String id,String contractId){
		 PurchaseRequired required = requiredService.queryById(id);
		 if(required!=null){
			 User user = userService.getUserById(required.getUserId());
			 required.setUserId(user.getRelName());
		 }
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("id", id);
		List<PurchaseRequired> purchaseRequireds = requiredService.selectByParentId(map);
		List<PurchaseRequired> data=new ArrayList<PurchaseRequired>();
		List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
		List<PurchaseDetail> details=new ArrayList<PurchaseDetail>();
		if(checkPass!=null&&checkPass.size()>0){
			   for(SupplierCheckPass pass:checkPass){
				   HashMap<String, Object> hashMap=new HashMap<String, Object>();
				   hashMap.put("packageId", pass.getPackageId());
					List<ProjectDetail> projectDetails = detailService.selectById(hashMap);
					if(projectDetails!=null&&projectDetails.size()>0){
						for(ProjectDetail projectDetail:projectDetails){
							PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
							details.add(queryById);
						}
					}
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
	@SuppressWarnings("unused")
	@RequestMapping(value="projectView",produces="text/html;charset=UTF-8")
	public String projectView(Model model, String id){
		
		long begindate=System.currentTimeMillis();
		ProjectDetail projectDetail = detailService.selectByPrimaryKey(id);
		//需求编报
		PurchaseRequired purchaseRequired=null;
		//需求受理
		AuditPerson auditPerson=null;
		//预研任务
		Task task=null;
		//采购计划审核
		List<AuditPerson> auditPersonsList=null;
		//采购计划任务下达
		CollectPlan collectPlans=null;
		//采购任务受领
		Task pltask=null;
		
		//项目立项
		Project project=null;
		
		
		if(projectDetail!=null){
			PurchaseDetail purchaseDetail = purchaseDetailService.queryById(projectDetail.getRequiredId());
			if(purchaseDetail!=null){
				HashMap<String, Object> map=new HashMap<String, Object>();
				map.put("id", purchaseDetail.getId());
				List<PurchaseRequired> purchaseRequireds= requiredService.selectByParent(map);
				if(purchaseRequireds!=null&&purchaseRequireds.size()>0){
					for(PurchaseRequired prd:purchaseRequireds){
						if("1".equals(prd.getParentId())){
							purchaseRequired=prd;
							if(purchaseRequired!=null){
								 User user = userService.getUserById(purchaseRequired.getUserId());
								 purchaseRequired.setUserId(user.getRelName());
							 }
						}
						
					}
				}
			}
		}
		//需求受理
		if(purchaseRequired!=null){
			List<PurchaseManagement> queryByPid = managementService.queryByPid(purchaseRequired.getUniqueId());
            Orgnization org= orgnizationServiceI.getOrgByPrimaryKey(queryByPid.get(0).getManagementId());
            model.addAttribute("org", org);//需求受理管理部门
            HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("collectId",purchaseRequired.getId());
			map.put("type",4);
			List<AuditPerson> auditPersons = auditPersonService.selectByMap(map);
			if(auditPersons!=null&&auditPersons.size()>0){
				 User user = userService.getUserById(auditPersons.get(0).getUserId());
				 auditPersons.get(0).setUserId(user.getRelName());
				 auditPerson=auditPersons.get(0);
                 
			}
		}
		//预研任务
		if(purchaseRequired!=null){
		    PurchaseDetail purchaseDetail = purchaseDetailService.queryById(projectDetail.getRequiredId());
			AdvancedDetail advancedDetail = advancedDetailService.selectByRequiredId(purchaseDetail.getId());
	        if(advancedDetail != null){
	            AdvancedProject advancedProject = advancedProjectService.selectById(advancedDetail.getAdvancedProject());
	            if(advancedProject != null){
	                HashMap<String, Object> map = new HashMap<>();
	                map.put("projectId", advancedProject.getId());
	                List<ProjectTask> queryByNo = projectTaskService.queryByNo(map);
	                Task tasks = taskService.selectById(queryByNo.get(0).getTaskId());
	                if(task != null){
	                	task=tasks;
	                }
	                model.addAttribute("advancedProject", advancedProject);//预研项目
	            }
	            model.addAttribute("advancedProjectId", advancedDetail.getAdvancedProject());//预研项目ID
	        }
	        String adviceId = DictionaryDataUtil.getId("ADVANCED_ADVICE");
	        model.addAttribute("adviceId", adviceId);//预研通知书
		}
		//采购计划审核
		if(purchaseRequired!=null){
			 PurchaseDetail purchaseDetail = purchaseDetailService.queryById(projectDetail.getRequiredId());
			 CollectPlan collectPlan = collectPlanService.queryById(purchaseDetail.getUniqueId());
			 if(collectPlan != null){
                 User user = userService.getUserById(collectPlan.getUserId());
                 collectPlan.setUserId(user.getRelName());
                 collectPlan.setPurchaseId(user.getOrgName());
                 Task tasks = taskService.selectByCollectId(collectPlan.getId());
                 if(tasks != null){
                     collectPlan.setUpdatedAt(tasks.getGiveTime());
                 }
                 collectPlans=collectPlan;
                 HashMap<String, Object> map=new HashMap<String, Object>();
                 map.put("collectId", collectPlan.getId());
                 List<AuditPerson> listAuditPersons = auditPersonService.selectByMap(map);
                 if(listAuditPersons != null && listAuditPersons.size() > 0){
                	 auditPersonsList=listAuditPersons;
                 }
                 
             }
		}
		
		//采购任务受领
		
		if(purchaseRequired!=null){
			 PurchaseDetail purchaseDetail = purchaseDetailService.queryById(projectDetail.getRequiredId());
             Task tasks = taskService.selectByCollectId(purchaseDetail.getUniqueId());
	        if(tasks != null){
	            Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(tasks.getPurchaseId());
	            /*User user = userService.getUserById(task.getUserId());
	            task.setUserId(user.getRelName());*/
	            tasks.setPurchaseId(org.getName());
	            pltask=tasks;
	        }
		}
		
		//采购计划审核
		if(purchaseRequired!=null){
			Project projects = projectService.selectById(projectDetail.getProject().getId());
            if(projects!=null){
            	User user = userService.getUserById(projects.getAppointMan());
            	projects.setAppointMan(user.getRelName());
                Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(projects.getPurchaseDepId());
                projects.setPurchaseDepName(org.getName());
                projects.setStatus(DictionaryDataUtil.findById(projects.getStatus()).getName());
            }
            model.addAttribute("uploadId", DictionaryDataUtil.getId("PROJECT_APPROVAL_DOCUMENTS")); //项目审批文件
          //判断是否上传招标文件
            String typeId = DictionaryDataUtil.getId("PROJECT_BID");
            List<UploadFile> files = uploadService.getFilesOther(projects.getId(), typeId, Constant.TENDER_SYS_KEY+"");
            if(files != null && files.size() > 0){
                model.addAttribute("fileId", files.get(0).getId());
                model.addAttribute("fileName", files.get(0).getName());
            }
          //获取采购文件的编制人
            FlowDefine define = new FlowDefine();
            define.setPurchaseTypeId(projects.getPurchaseType());
            define.setStep(3);
            List<FlowDefine> find = flowMangeService.find(define);
            FlowExecute execute = new FlowExecute();
            execute.setProjectId(projects.getId());
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
            
            //获取采购公告
            Article article = new Article();
            article.setArticleType(articelTypeService.selectArticleTypeByCode("purchase_notice"));
            article.setProjectId(projects.getId());
            List<Article> articles = articleService.selectArticleByProjectId(article);
            if(articles != null && articles.size() > 0){
                User user2 = userService.getUserById(articles.get(0).getUser().getId());
                articles.get(0).setUserId(user2.getRelName());
                model.addAttribute("articles",articles.get(0));
            }
            
          //资格性符合性检查
            Map<String, Object> packageExpertmap = new HashMap<String, Object>();
            packageExpertmap.put("packageId",projectDetail.getPackageId());
            packageExpertmap.put("projectId", projects.getId());
            //查询专家
            List<PackageExpert> expertIdList = packageExpertService.selectList(packageExpertmap);
            List<Expert> experts = new ArrayList<Expert>();
            for (PackageExpert packageExpert : expertIdList) {
                Expert expert = expertService.selectByPrimaryKey(packageExpert.getExpertId());
                packageExpert.setExpertId(expert.getRelName());
                experts.add(expert);
            }
            model.addAttribute("expertIdList", expertIdList);
            model.addAttribute("experts", experts);
            model.addAttribute("project", project);
            model.addAttribute("status", "0");
            model.addAttribute("packageId", projectDetail.getPackageId());//包id
            project=projects;
		}
		
		
		//合同
		if(projectDetail!=null){
			List<ContractRequired> contractRequireds = contractRequiredService.selectConRequByDetailId(projectDetail.getId());
			if(contractRequireds!=null&&contractRequireds.size()>0){
				PurchaseContract purchaseContract = purchaseContractService.selectById(contractRequireds.get(0).getContractId());
				if(purchaseContract.getPurchaseDepName()!=null){
					Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(purchaseContract.getPurchaseDepName());
					if(org!=null){
						purchaseContract.setPurchaseDepName(org.getName());
						purchaseContract.setPurchaseBankAccount_string(org.getFax());
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
				model.addAttribute("purchaseContract", purchaseContract);//合同
			}
		}
		//中标供应商
		
		if(projectDetail!=null){
			List<SupplierCheckPass> supplierCheckPasss = supplierCheckPassService.selectPackageIdWonBid(projectDetail.getPackageId());
			if(supplierCheckPasss!=null&&supplierCheckPasss.size()>0){
				for(SupplierCheckPass pass:supplierCheckPasss){
					Supplier supplier = supplierService.selectById(pass.getSupplierId());
					if(supplier!=null){
						pass.setSupplier(supplier);
					}
				}
			}
			model.addAttribute("pass", supplierCheckPasss);
			
		}
		
		//获取中标公示
		if(projectDetail!=null){
	        Article article2 = new Article();
	        article2.setArticleType(articelTypeService.selectArticleTypeByCode("success_notice"));
	        article2.setProjectId(projectDetail.getProject().getId());
	        List<Article> articleList= articleService.selectArticleByProjectId(article2);
	        if(articleList != null && articleList.size() > 0){
	            User user2 = userService.getUserById(articleList.get(0).getUser().getId());
	            articleList.get(0).setUserId(user2.getRelName());
	            model.addAttribute("articleList",articleList.get(0));
	        }
	      //采购文件发售
	        Packages packages = getPackages(projectDetail.getPackageId());
	        List<SaleTender> saleTenderList = packages.getSaleTenderList();
	        Set<Long> set=new TreeSet<Long>();
	        for(SaleTender saleTender:saleTenderList){
	        	if(saleTender.getCreatedAt()!=null&&!"".equals(saleTender.getCreatedAt())){
	        		Date createdAt = saleTender.getCreatedAt();
	        		try {
						long simp=new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(createdAt)).getTime();
						set.add(simp);
	        		 } catch (ParseException e) {
						e.printStackTrace();
					}
	        	}
	        }
	        if(set!=null&&set.size()>0){
	        	Iterator it=set.iterator();
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
		}
		
		
		model.addAttribute("purchaseRequired", purchaseRequired);//需求编报
		model.addAttribute("auditPerson", auditPerson);//需求受理
		model.addAttribute("task", task);//任务
		model.addAttribute("listAuditPerson", auditPersonsList);//采购计划审核
		model.addAttribute("collectPlan", collectPlans);//采购计划任务下达
		model.addAttribute("collectPlan", collectPlans);//采购计划任务下达
		model.addAttribute("pltask", pltask);//采购计划任务下达
		model.addAttribute("project", project);//采购项目立项
		long enddate=System.currentTimeMillis();
		System.out.println((enddate-begindate)/1000+"-=-=-=-=-");
		return "sums/ss/contractSupervision/projectView";
	}
	
	
	public Packages getPackages(String packageId){
		Packages packages=null;
		if(StringUtils.isNotBlank(packageId)){
			packages= packageService.selectByPrimaryKeyId(packageId);
            String fileId = DictionaryDataUtil.getId("OPEN_FILE");
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
        }
		return  packages;
	}
	@RequestMapping("/viewSell")
    public String viewSell(String packageId, String type, Model model){
		    Packages packages = getPackages(packageId);
            model.addAttribute("packages", packages);
        if(StringUtils.isNotBlank(type) && "1".equals(type)){
            return "sums/ss/contractSupervision/viewSell";
        }else{
            return "sums/ss/contractSupervision/viewSupplier";
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
	
	/**
     * 
     *〈计划重新排序〉
     *〈详细描述〉
     * @author Administrator
     * @param list
     */
    public void sorts(List<PurchaseDetail> list){
        Collections.sort(list, new Comparator<PurchaseDetail>(){
           @Override
           public int compare(PurchaseDetail o1, PurchaseDetail o2) {
              Integer i = o1.getIsMaster() - o2.getIsMaster();
              return i;
           }
        });
    }
}
