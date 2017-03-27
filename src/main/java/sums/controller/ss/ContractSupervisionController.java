package sums.controller.ss;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.ValidateUtils;
import bss.model.cs.ContractRequired;
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
import bss.service.cs.ContractRequiredService;
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
import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
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
    private DownloadService downloadService;
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
    private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    @Autowired
    private PackageService packageService;
    @Autowired
    private ProjectDetailService detailService;
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
        List<PurchaseContract> draftConList = purchaseContractService.selectAllContractByCode(map);
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
		model.addAttribute("contract",purchaseContract);
		List<SupplierCheckPass> SupplierCheckPass = supplierCheckPassService.getByContractId(purchaseContract.getId());
		Project project=null;
		if(SupplierCheckPass!=null&&SupplierCheckPass.size()>0){
			for(SupplierCheckPass pass:SupplierCheckPass){
			}
			String projectId = SupplierCheckPass.get(0).getProjectId();
			project = projectService.selectById(projectId);
			model.addAttribute("project",project);
		}
		 // 根据项目ID查询中间表，然后查询采购计划表
        /*HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", project.getId());
        //采购计划
        List<CollectPlan> list = new ArrayList<CollectPlan>();
        //采购需求
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
        
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User user = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(user.getRelName());
                    list.get(i).setPurchaseId(user.getOrgName());
                } catch (Exception e) {
                	list.get(i).setUserId("");
                }
            }
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
           
        }
        model.addAttribute("list", list);
        model.addAttribute("lists", list2);*/
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
			for(SupplierCheckPass pass:SupplierCheckPass){
			}
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
	@SuppressWarnings("unused")
	@RequestMapping(value="projectDateil",produces="text/html;charset=UTF-8")
	public String projectList(Model model, String id,String contractId){
		List<Packages> packages=null;
		Project project = projectService.selectById(id);
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
					Orgnization orgnization = orgnizationServiceI.findByCategoryId(org.getOrgId());
					orgs+=orgnization.getName()+",";
				}
			}
			if(orgs.length()>0){
				orgs=orgs.substring(0, orgs.length()-1);
			}
			model.addAttribute("org",orgs);
			model.addAttribute("project",project);
			List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
			
			if(checkPass!=null&&checkPass.size()>0){
				packages=new ArrayList<Packages>();
				for(SupplierCheckPass chp:checkPass){
					HashMap<String, Object> hashMap=new HashMap<String, Object>();
					Packages pack = packageService.selectByPrimaryKeyId(chp.getPackageId());
					hashMap.put("packageId", pack.getId());
					List<ProjectDetail> projectDetails = detailService.selectByPackageId(pack.getId());
					if(projectDetails!=null&&projectDetails.size()>0){
						for(ProjectDetail projectDetail:projectDetails){
							DictionaryData type = dictionaryDataServiceI.getDictionaryData(projectDetail.getPurchaseType());
							if(type!=null){
						    	projectDetail.setPurchaseType(type.getName());
						    }else{
						    	projectDetail.setPurchaseType("");
						    }
						}
					}
					pack.setProjectDetails(projectDetails);
					packages.add(pack);
				}
			}
		}
		
		
		model.addAttribute("listPackages",packages);
		return "sums/ss/contractSupervision/projectdateil";
	}
	
	@RequestMapping(value="planList",produces="text/html;charset=UTF-8")
	public String planList(Model model, String id,String contractId){
		List<CollectPlan> list = new ArrayList<CollectPlan>();
		List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
		Set<String> set=new HashSet<String>();   
		if(checkPass!=null&&checkPass.size()>0){
			   for(SupplierCheckPass pass:checkPass){
				   HashMap<String, Object> hashMap=new HashMap<String, Object>();
				   hashMap.put("packageId", pass.getPackageId());
					List<ProjectDetail> projectDetails = detailService.selectById(hashMap);
					if(projectDetails!=null&&projectDetails.size()>0){
						for(ProjectDetail projectDetail:projectDetails){
							PurchaseDetail queryById = purchaseDetailService.queryById(projectDetail.getRequiredId());
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
			  CollectPlan collectPlan = collectPlanService.queryById(str);
			  list.add(collectPlan);
			}  
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User user = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(user.getRelName());
                    list.get(i).setPurchaseId(user.getOrgName());
                } catch (Exception e) {
                	list.get(i).setUserId("");
                }
            }
        }
        model.addAttribute("list", list);
        model.addAttribute("contractId", contractId);
		return "sums/ss/contractSupervision/planList";
	}
	
	@RequestMapping(value="planDateil",produces="text/html;charset=UTF-8")
	public String planDateil(Model model, String id,String contractId){
		CollectPlan collectPlan = collectPlanService.queryById(id);
		User user = userService.getUserById(collectPlan.getUserId());
		collectPlan.setUserId(user.getRelName());
		List<SupplierCheckPass> checkPass = supplierCheckPassService.getByContractId(contractId);
		List<PurchaseDetail> details=new ArrayList<PurchaseDetail>();
		List<PurchaseDetail> deta=new ArrayList<PurchaseDetail>();
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
			   
			List<PurchaseDetail> pdetails = purchaseDetailService.getUnique(id);
			deta=listdata(pdetails, details);
            model.addAttribute("list", deta);
            model.addAttribute("kind", DictionaryDataUtil.find(5));
            model.addAttribute("plan", collectPlan);
		}
		return "sums/ss/contractSupervision/planDateil";
	}
	@RequestMapping(value="demandList",produces="text/html;charset=UTF-8")
	public String demandList(Model model, String id,String contractId){
		List<CollectPlan> list = new ArrayList<CollectPlan>();
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
			  List<PurchaseDetail> pdetails = purchaseDetailService.getUnique(str);
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
	@RequestMapping(value="projectView",produces="text/html;charset=UTF-8")
	public String projectView(Model model, String id){
		
		
		return "sums/ss/contractSupervision/projectView";
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
        	if(i==0||i==1){
        		continue;
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
