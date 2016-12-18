package bss.controller.cs;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONSerializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.ValidateUtils;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.TaskService;
import bss.service.sstps.AppraisalContractService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

/* 
 *@Title:PurchaseContractController
 *@Description:采购合同控制类
 *@author QuJie
 *@date 2016-9-23下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseContract")
public class PurchaseContractController extends BaseSupplierController{
	
	@Autowired
	private PurchaseContractService purchaseContractService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectDetailService projectDetailService;
	
	@Autowired
	private ProjectTaskService projectTaskService;
	
	@Autowired
	private ContractRequiredService contractRequiredService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private PackageService packageService;
	
	@Autowired
	private SupplierCheckPassService supplierCheckPassService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private UserServiceI userServiceI;
	
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:48:57  
	* @Description: 查询已完成的包 
	* @param @param model
	* @param @param 分页
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectAllPuCon")
	public String selectAllPurchaseContract(@CurrentUser User user,Model model,Integer page,HttpServletRequest request) throws Exception{
		String projectName = request.getParameter("projectName");
		String projectCode = request.getParameter("projectCode");
		String purchaseDep = request.getParameter("purchaseDep");
		if(page==null){
			page=1;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("projectName", projectName);
		map.put("projectCode", projectCode);
		map.put("purchaseDep", purchaseDep);
		map.put("page", page);
		List<Packages> packList = packageService.selectAllByIsWon(map);
		model.addAttribute("list", new PageInfo<Packages>(packList));
		List<Packages> pacList = new ArrayList<Packages>();
		Orgnization or = user.getOrg();
		
//		boolean isRole = false;
//		for(Role r:roleList){
//			if(r.getCode().equals("PURCHASE_ORG_R")||r.getCode().equals("ADMIN_R")){
//				isRole = true;
//			}
//		}
//		if(isRole){
			for(Packages pa:packList){
				boolean flag = true;
				Project project = projectService.selectById(pa.getProjectId());
				if(or.getTypeName().equals("0")){
					if(!or.getDepId().equals(project.getPurchaseDep().getId())){
						flag = false;
					}
				}
//				if(or.getTypeName().equals("0")&&or.getDepId().equals(project.getPurchaseDep().getId())
//						||or.getTypeName().equals("1")&&or.getDepId().equals(project.getPurchaseDep().getId())
//						||or.getTypeName().equals("2")&&or.getId().equals(project.getSectorOfDemand())){
					pa.setProject(project);
					SupplierCheckPass sucp = new SupplierCheckPass();
					sucp.setPackageId(pa.getId());
					sucp.setIsWonBid((short)1);
					List<SupplierCheckPass> suList = supplierCheckPassService.listCheckPass(sucp);
	//				String supplierNames = "";
					for(SupplierCheckPass su:suList){
						if(su.getIsCreateContract()==0){
							if(su.getSupplier()!=null){
		//						supplierNames+=su.getSupplier().getSupplierName()+",";
		//						supplierNames+=su.getSupplier().getSupplierName();
								pa.setSupplier(su.getSupplier());
								pa.setSupplierCheckPassId(su.getId());
								pacList.add(pa);
							}
						}
					}
//				}
			}
//		}else{
//			model.addAttribute("list", new PageInfo<Packages>(pacList));
//		}
//		List<Project> projectList = projectService.selectSuccessProject(map);
//		List<Project> proList = new ArrayList<Project>();
//		for(Project pro:projectList){
//			HashMap<String,Object> proMap = new HashMap<String, Object>();
//			proMap.put("projectId", pro.getId());
//			List<Packages> packagesList = packageService.findPackageById(proMap);
//			List<Packages> packList = new ArrayList<Packages>();
//			for(Packages pack:packagesList){
//				SupplierCheckPass supch = new SupplierCheckPass();
//				supch.setPackageId(pack.getId());
//				supch.setIsWonBid((short)1);
//				List<SupplierCheckPass> chList = supplierCheckPassService.listCheckPass(supch);
//				List<Supplier> suList = new ArrayList<Supplier>();
//				for(SupplierCheckPass sucp:chList){
//					suList.add(sucp.getSupplier());
//				}
//				pack.setSuppList(suList);
//				packList.add(pack);
//			}
//			pro.setPackagesList(packList);
//			proList.add(pro);
//		}
		model.addAttribute("packageList", pacList);
		model.addAttribute("projectName", projectName);
		model.addAttribute("projectCode", projectCode);
		model.addAttribute("purchaseDep", purchaseDep);
		return "bss/cs/purchaseContract/list";
	}
	
//	/**
//	 * 
//	* 〈简述〉 〈详细描述〉
//	* 
//	* @author QuJie 
//	* @date 2016-11-11 下午2:49:35  
//	* @Description: 打印草稿合同
//	* @param @param purCon 采购合同实体类
//	* @param @param proList 前台穿的list
//	* @param @param result
//	* @param @param request
//	* @param @param model
//	* @param @return
//	* @param @throws Exception      
//	* @return String
//	 */
//	@RequestMapping("/printContract")
//	public String printContract(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
//		String ids = request.getParameter("ids");
//		String supcheckid = request.getParameter("supcheckid");
//		purCon.setId(ids);
//		Map<String, Object> map = valid(model,purCon);
//		model = (Model)map.get("model");
//		Boolean flag = (boolean)map.get("flag");
//		String url = "";
//		if(flag == false){
////			Project project = projectService.selectById(ids);
////			HashMap<String, Object> requMap = new HashMap<String, Object>();
////			requMap.put("id", project.getId());
////			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
////			
////			HashMap<String, Object> requMainMap = new HashMap<String, Object>();
////			requMainMap.put("id", project.getId());
////			List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
////			String planNos = "";
////			HashMap<String, Object> taskMap = new HashMap<String, Object>();
////			taskMap.put("projectId",project.getId());
////			List<ProjectTask> taskList = projectTaskService.queryByNo(taskMap);
////			for(ProjectTask pur:taskList){
////				Task task = taskService.selectById(pur.getTaskId());
////				planNos+=task.getDocumentNumber()+",";
////			}
//			List<ContractRequired> requList = proList.getProList();
//			model.addAttribute("purCon", purCon);
//			model.addAttribute("requList", requList);
//			model.addAttribute("planNos", purCon.getDocumentNumber());
//			model.addAttribute("id", ids);
//			model.addAttribute("supcheckid", supcheckid);
//			return "bss/cs/purchaseContract/errContract";
//		}else{
//			List<ContractRequired> requList = proList.getProList();
//			model.addAttribute("requList", requList);
//			model.addAttribute("purCon", purCon);
//			url = "bss/cs/purchaseContract/printModel";
//		}
//		return url;
//	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:50:43  
	* @Description: 打印正式合同 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/printFormalContract")
	public String printFormalContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract purCon = purchaseContractService.selectById(ids);
		List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(ids);
		model.addAttribute("requList", requList);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/printModel";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:51:00  
	* @Description: 合并生成合同 
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping(value="/createAllCommonContract",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String createAllCommonContract(HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		String flag = "true";
		String news = "";
		List<String> supIdList = new ArrayList<String>();
		List<Project> proList = new ArrayList<Project>();
		for(int i=0;i<ids.length;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", ids[i]);
			Packages pack = packageService.findPackageById(map).get(0);
			Project pro = projectService.selectById(pack.getProjectId());
			proList.add(pro);
			SupplierCheckPass suchp = new SupplierCheckPass();
			suchp.setPackageId(pack.getId());
			suchp.setIsWonBid((short)1);
			List<SupplierCheckPass> chList = supplierCheckPassService.listCheckPass(suchp);
			if(chList.size()>1){
				flag="false";
				news = "";
				news+="有多个供应商，无法合并";
				break;
			}else{
				supIdList.add(chList.get(0).getSupplier().getId());
			}
		}
		
		if(flag.equals("true")){
			out:for(int i=0;i<proList.size()-1;i++){
				for(int j=i+1;j<proList.size();j++){
					if(!proList.get(i).getId().equals(proList.get(j).getId())){
						flag="false";
						news = "";
						news+="不是同一个项目";
						break out;
					}
				}
			}
		}
		
		if(flag.equals("true")){
			if(!supIdList.isEmpty()){
				for(int j=0;j<supIdList.size()-1;j++){
					for(int m=j+1;m<supIdList.size();m++){
						if(!supIdList.get(j).equals(supIdList.get(m))){
							flag="false";
							news = "";
							news+="供应商不一致";
						}
					}
				}
			}
		}
		
		if(flag.equals("true")){
			return "true=";
		}else{
			return "false="+news;
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:51:25  
	* @Description: 查询选择的项目的成交供应商 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping(value="/selectSuppliers",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String  selectSuppliers(HttpServletRequest request){
		String packageId = request.getParameter("packageId");
		String[] packIds = packageId.split(",");
		SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
		suppliercheckpass.setPackageId(packIds[0]);
		suppliercheckpass.setIsWonBid((short)1);
		List<SupplierCheckPass> chePList = supplierCheckPassService.listCheckPass(suppliercheckpass);
		String options = "";
		for(SupplierCheckPass su:chePList){
			String option = "<option value='"+su.getSupplier().getId()+"'>"+su.getSupplier().getSupplierName()+"</option>";
			options+=option;
		}
		return options;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:51:58  
	* @Description: 通过包id查询成交供应商 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping(value="/selectSupplierByPId",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String  selectSupplierByPId(HttpServletRequest request){
		String packageId = request.getParameter("packageId");
		String[] packIds = packageId.split(",");
		SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
		suppliercheckpass.setPackageId(packIds[0]);
		suppliercheckpass.setIsWonBid((short)1);
		List<SupplierCheckPass> chePList = supplierCheckPassService.listCheckPass(suppliercheckpass);
		String supid = "";
		for(SupplierCheckPass su:chePList){
			supid += su.getSupplier().getId();
		}
		return supid;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:52:32  
	* @Description: 打印草稿合同 
	* @param @param purCon 合同实体类
	* @param @param proList 前台传的list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/printDraftContract")
	public String printDraftContract(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
		if(flag == false){
			String ids = request.getParameter("ids");
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("draftCon", purCon);
			model.addAttribute("requList", requList);
			model.addAttribute("ids", ids);
			return "bss/cs/purchaseContract/draftContract";
		}else{
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("requList", requList);
			model.addAttribute("purCon", purCon);
			url = "bss/cs/purchaseContract/printModel";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:53:44  
	* @Description: 根据合同编号查合同 
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return PurchaseContract
	 */
	@ResponseBody
	@RequestMapping("/selectByCode")
	public PurchaseContract selectByCode(HttpServletRequest request) throws Exception{
		String code = request.getParameter("code");
		PurchaseContract purchaseCon = purchaseContractService.selectByCode(code);
		if(purchaseCon==null){
			purchaseCon=new PurchaseContract();
			purchaseCon.setCode("ErrCode");
		}
		return purchaseCon;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:54:10  
	* @Description: 创建合同基本信息页面 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createCommonContract")
	public String createCommonContract(HttpServletRequest request,Model model) throws Exception{
		String supcheckid = request.getParameter("supcheckid");
		String contractuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("attachuuid", contractuuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("DRAFT_REVIEWED");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		model.addAttribute("kinds", DictionaryDataUtil.find(5));
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String supid = request.getParameter("supid");
		List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
		for(int i=0;i<ids.length;i++){
			HashMap<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("packageId",ids[i]);
			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
			allList.addAll(requList);
		}
		model.addAttribute("requList", allList);
		Supplier supplier = supplierService.selectById(supid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", ids[0]);
		Packages pack = packageService.findPackageById(map).get(0);
		Project project = projectService.selectById(pack.getProjectId());
		project.setDealSupplier(supplier);
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(project.getSectorOfDemand());
		project.setOrgnization(org);
		model.addAttribute("project", project);
		model.addAttribute("id", contractuuid);
		model.addAttribute("supcheckid",supcheckid);
		return "bss/cs/purchaseContract/newContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:55:57  
	* @Description: 创建合同明细信息 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createDetailContract")
	public String createDetailContract(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String supid = request.getParameter("supid");
		List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
		for(int i=0;i<ids.length;i++){
			HashMap<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("packageId",ids[i]);
			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
			allList.addAll(requList);
		}
		model.addAttribute("requList", allList);
		model.addAttribute("id", id);
		model.addAttribute("supid", supid);
		return "bss/cs/purchaseContract/detailContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:56:21  
	* @Description: 创建合同文本信息 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createTextContract")
	public String createTextContract(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		String supid = request.getParameter("supid");
		Supplier supplier = supplierService.get(supid);
		String[] ids = id.split(",");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", ids[0]);
		Packages pack = packageService.findPackageById(map).get(0);
		Project project = projectService.selectById(pack.getProjectId());
		project.setDealSupplier(supplier);
		List<ProjectDetail> allList = new ArrayList<ProjectDetail>();
		for(int i=0;i<ids.length;i++){
			HashMap<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("packageId",ids[i]);
			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
			allList.addAll(requList);
		}
		model.addAttribute("requList", allList);
//		HashMap<String, Object> requMainMap = new HashMap<String, Object>();
//		requMainMap.put("id", project.getId());
//		List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
		String planNos = "";
		HashMap<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("idArray",ids);
		List<ProjectTask> taskList = projectTaskService.queryByProjectNos(taskMap);
		for(ProjectTask pur:taskList){
			Task task = taskService.selectById(pur.getTaskId());
			planNos+=task.getDocumentNumber()+",";
		}
		model.addAttribute("project", project);
		model.addAttribute("planNos", planNos);
		model.addAttribute("ids", id);
		return "bss/cs/purchaseContract/textContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:56:42  
	* @Description: 生成合同草稿 
	* @param @param purCon 合同实体类
	* @param @param proList 明细list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/addPurchaseContract")
	public String addPurchaseContract(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		String dga = request.getParameter("dga");
		String dra = request.getParameter("dra");
		String supcheckid = request.getParameter("supcheckid");
		String[] supcheckids = supcheckid.split(",");
		Date draftGitAt = null;
		Date draftReAt = null;
		if(!dga.equals("")){
			draftGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dga);
		}
		if(!dga.equals("")){
			draftReAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dra);
		}
		purCon.setId(ids);
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
		if(flag == false){
			List<ContractRequired> requList = proList.getProList();
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
			}
			model.addAttribute("supcheckid", supcheckid);
			model.addAttribute("kinds", DictionaryDataUtil.find(5));
			model.addAttribute("purCon", purCon);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", purCon.getDocumentNumber());
			model.addAttribute("id", ids);
			url = "bss/cs/purchaseContract/errContract";
		}else{
			if(draftGitAt!=null){
				purCon.setDraftGitAt(draftGitAt);
			}
			if(draftReAt!=null){
				purCon.setDraftReviewedAt(draftReAt);
			}
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setCreatedAt(new Date());
			purCon.setUpdatedAt(new Date());
			purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
			purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
			purCon.setSupplierBankAccount(new BigDecimal(purCon.getSupplierBankAccount_string()));
			purCon.setPurchaseBankAccount(new BigDecimal(purCon.getPurchaseBankAccount_string()));
			purchaseContractService.insertSelective(purCon);
			String id = purCon.getId();
			List<ContractRequired> requList = proList.getProList();
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
				for(ContractRequired conRequ:requList){
					conRequ.setContractId(id);
					contractRequiredService.insertSelective(conRequ);
				}
			}
			for(String supchid:supcheckids){
				SupplierCheckPass sup = new SupplierCheckPass();
				sup.setId(supchid);
				sup.setIsCreateContract(1);
				supplierCheckPassService.update(sup);
			}
			url = "redirect:selectAllPuCon.html";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:56:42  
	* @Description: 生成合同草稿 
	* @param @param purCon 合同实体类
	* @param @param proList 明细list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/addStraightContract")
	public String addStraightContract(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		String dga = request.getParameter("dga");
		String dra = request.getParameter("dra");
		String fga = request.getParameter("fga");
		String fra = request.getParameter("fra");
		Map<String, Object> map = valid(model,purCon);
		Boolean flag = (boolean)map.get("flag");
		model = (Model)map.get("model");
		if(purCon.getDraftGitAt()==null){
			flag=false;
			model.addAttribute("ERR_draftGitAt", "草案上报时间不可为空");
		}
		if(purCon.getDraftReviewedAt()==null){
			flag=false;
			model.addAttribute("ERR_draftReviewedAt", "草案报批时间不可为空");
		}
		if(purCon.getFormalGitAt()==null){
			flag=false;
			model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
		}
		if(purCon.getFormalReviewedAt()==null){
			flag=false;
			model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
		}
		if(ValidateUtils.isNull(purCon.getApprovalNumber())){
			flag=false;
			model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
		}
		if(ValidateUtils.isNull(purCon.getQuaCode())){
			flag=false;
			model.addAttribute("ERR_quaCode", "资格证号不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierPurId())){
			flag=false;
			model.addAttribute("ERR_supplierPurId", "组织机构代码不能为空");
		}
		if(purCon.getFormalGitAt()!=null && purCon.getFormalReviewedAt()!=null && purCon.getDraftGitAt()!=null && purCon.getDraftReviewedAt()!=null){
			Date draftGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dga);
			Date draftRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dra);
			Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
			Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
			purCon.setDraftGitAt(draftGitAt);
			purCon.setDraftReviewedAt(draftRAt);
			purCon.setFormalGitAt(formalGitAt);
			purCon.setFormalReviewedAt(formalRAt);
			if(draftGitAt.getTime()>draftRAt.getTime() || 
					draftGitAt.getTime()>draftRAt.getTime() ||
					draftRAt.getTime()>formalRAt.getTime() ||
					draftRAt.getTime()>formalGitAt.getTime()){
				flag=false;
				model.addAttribute("ERR_draftGitAt", "正式合同时间不可早于草稿合同时间");
				model.addAttribute("ERR_draftReviewedAt", "正式合同时间不可早于草稿合同时间");
				model.addAttribute("ERR_formalGitAt", "正式合同时间不可早于草稿合同时间");
				model.addAttribute("ERR_formalReviewedAt", "正式合同时间不可早于草稿合同时间");
			}else if(purCon.getDraftGitAt().getTime()>purCon.getDraftReviewedAt().getTime()){
				flag=false;
				model.addAttribute("ERR_draftGitAt", "草案报批时间应晚于提报时间");
				model.addAttribute("ERR_draftReviewedAt", "草案报批时间应晚于提报时间");
			}else if(purCon.getFormalGitAt().getTime()>purCon.getFormalReviewedAt().getTime()){
				flag=false;
				model.addAttribute("ERR_formalGitAt", "正式合同报批时间应晚于提报时间");
				model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间应晚于提报时间");
			}
		}
		String url = "";
		List<ContractRequired> requList = proList.getProList();
		if(flag == false){
			model.addAttribute("purCon", purCon);
			model.addAttribute("requList", requList);
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
			}
			model.addAttribute("attachuuid", purCon.getId());
			DictionaryData dd=new DictionaryData();
			dd.setCode("CONTRACT_APPROVE_ATTACH");
			List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
			if(datas.size()>0){
				model.addAttribute("attachtypeId", datas.get(0).getId());
			}
			model.addAttribute("kinds", DictionaryDataUtil.find(5));
			url = "bss/cs/purchaseContract/straightContract";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setCreatedAt(new Date());
			purCon.setUpdatedAt(new Date());
			purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
			purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
			purCon.setSupplierBankAccount(new BigDecimal(purCon.getSupplierBankAccount_string()));
			purCon.setPurchaseBankAccount(new BigDecimal(purCon.getPurchaseBankAccount_string()));
			purchaseContractService.insertSelectiveById(purCon);
			purchaseContractService.createWord(purCon, requList,request);
			appraisalContractService.insertPurchaseContract(purCon);
			String id = purCon.getId();
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
				for(ContractRequired conRequ:requList){
					conRequ.setContractId(id);
					contractRequiredService.insertSelective(conRequ);
				}
			}
			url = "redirect:selectAllPuCon.html";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:56:42  
	* @Description: 生成合同草稿 
	* @param @param purCon 合同实体类
	* @param @param proList 明细list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/toValidRoughContract")
	public void toValidRoughContract(PurchaseContract purCon,HttpServletResponse response) throws Exception{
		Boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		if(purCon.getDraftGitAt()==null){
			flag = false;
			map.put("gitAt", "提报时间不能为空");
		}
		if(purCon.getDraftReviewedAt()==null){
			flag = false;
			map.put("reviewAt", "报批时间不能为空");
		}
		if(flag && purCon.getDraftGitAt().getTime()>purCon.getDraftReviewedAt().getTime()){
			flag=false;
			map.put("gitAt", "报批时间不能早于提报时间");
			map.put("reviewAt", "报批时间不能早于提报时间");
		}
		if(flag){
			super.writeJson(response, 1);
		}else{
			super.writeJson(response, JSONSerializer.toJSON(map).toString());
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:56:42  
	* @Description: 生成合同草稿案
	* @param @param purCon 合同实体类
	* @param @param proList 明细list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/toRoughContract")
	public String toRoughContract(PurchaseContract purCon) throws Exception{
			purCon.setUpdatedAt(new Date());
			purchaseContractService.updateByPrimaryKeySelective(purCon);
			return "redirect:selectDraftContract.html";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:02:44  
	* @Description: 修改合同草案 
	* @param @param agrfile
	* @param @param request
	* @param @param purCon
	* @param @param result
	* @param @param proList
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/updateDraftContract")
	public String updateDraftContract(PurchaseContract purCon,ProList proList,HttpServletRequest request,Model model) throws Exception{
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
		if(flag == false){
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("purCon", purCon);
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
			}
			purCon.setContractReList(requList);
			url = "bss/cs/purchaseContract/updateErrContract";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setUpdatedAt(new Date());
			purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
			purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
			purCon.setSupplierBankAccount(new BigDecimal(purCon.getSupplierBankAccount_string()));
			purCon.setPurchaseBankAccount(new BigDecimal(purCon.getPurchaseBankAccount_string()));
			purchaseContractService.updateByPrimaryKeySelective(purCon);
			String id = purCon.getId();
			contractRequiredService.deleteByContractId(id);
			List<ContractRequired> requList = proList.getProList();
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
				for(ContractRequired conRequ:requList){
					if(conRequ.getId()==null){
						conRequ.setContractId(id);
						contractRequiredService.insertSelective(conRequ);
					}else{
						contractRequiredService.updateByPrimaryKeySelective(conRequ);
					}
				}
			}
			url = "redirect:selectDraftContract.html";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:57:23  
	* @Description: 校验公共方法 
	* @param @param model
	* @param @param purCon 合同实体类
	* @param @return      
	* @return Map
	 */
	public Map valid(Model model,PurchaseContract purCon){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean flag = true;
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount_string())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(!ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount_string())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(ValidateUtils.isNull(purCon.getName())){
			flag = false;
			model.addAttribute("ERR_name", "合同名称不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseType())){
			flag = false;
			model.addAttribute("ERR_purchaseType", "采购方式不能为空");
		}
		if(ValidateUtils.isNull(purCon.getBingDepName())){
			flag = false;
			model.addAttribute("ERR_bingDepName", "需求部门不能为空");
		}else{
			PurchaseDep purDep = purchaseOrgnizationServiceI.selectPurchaseById(purCon.getBingDepName());
			if(ValidateUtils.isNull(purDep.getDepName())){
				flag = false;
				model.addAttribute("ERR_bingDepName", "需求部门不能为空");
			}
		}
		if(ValidateUtils.isNull(purCon.getCode())){
			flag = false;
			model.addAttribute("ERR_code", "合同编号不能为空");
		}else{
			List<PurchaseContract> contractList = purchaseContractService.selectAllContract();
			for(int i=0;i<contractList.size();i++){
				if(purCon.getId().equals(contractList.get(i).getId())){
					contractList.remove(i);
				}
			}
			for(PurchaseContract con:contractList){
				if(con.getCode().equals(purCon.getCode())){
					flag = false;
					model.addAttribute("ERR_code", "合同编号不可重复");
				}
			}
		}
		if(ValidateUtils.isNull(purCon.getDocumentNumber())){
			flag = false;
			model.addAttribute("ERR_documentNumber", "计划任务文号不能为空");
		}
		if(ValidateUtils.isNull(purCon.getQuaCode())){
			flag = false;
			model.addAttribute("ERR_quaCode", "采购机构文号不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseDepName())){
			flag = false;
			model.addAttribute("ERR_purchaseDepName", "甲方单位不能为空");
		}else{
			Orgnization org =  orgnizationServiceI.getOrgByPrimaryKey(purCon.getPurchaseDepName());
			if(ValidateUtils.isNull(org.getName())){
				flag = false;
				model.addAttribute("ERR_purchaseDepName", "甲方单位不能为空");
			}
		}
		if(ValidateUtils.isNull(purCon.getPurchaseLegal())){
			flag = false;
			model.addAttribute("ERR_purchaseLegal", "甲方法人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseAgent())){
			flag = false;
			model.addAttribute("ERR_purchaseAgent", "甲方委托代理人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseContact())){
			flag = false;
			model.addAttribute("ERR_purchaseContact", "甲方联系人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseContactTelephone())){
			flag = false;
			model.addAttribute("ERR_purchaseContactTelephone", "甲方联系电话不能为空");
		}else if(!ValidateUtils.Tele(purCon.getPurchaseContactTelephone())){
			flag = false;
			model.addAttribute("ERR_purchaseContactTelephone", "请输入正确的联系电话");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseContactAddress())){
			flag = false;
			model.addAttribute("ERR_purchaseContactAddress", "甲方地址不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_purchaseUnitpostCode", "甲方邮编不能为空");
		}else if(!ValidateUtils.Zipcode(purCon.getPurchaseUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_purchaseUnitpostCode", "请输入正确的邮编");
		}
		if(ValidateUtils.isNull(purCon.getPurchasePayDep())){
			flag = false;
			model.addAttribute("ERR_purchasePayDep", "甲方付款单位不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBank())){
			flag = false;
			model.addAttribute("ERR_purchaseBank", "甲方开户银行不能为空");
		}
		if(purCon.getContractType()==null){
			flag = false;
			model.addAttribute("ERR_contractType", "合同类型不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount_string())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(!ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount_string())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney_string())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(!ValidateUtils.Money(purCon.getMoney_string())){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget_string())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(!ValidateUtils.Money(purCon.getBudget_string())){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getSupplierDepName())){
			flag = false;
			model.addAttribute("ERR_supplierDepName", "乙方单位不能为空");
		}else{
			Supplier su = supplierService.selectOne(purCon.getSupplierDepName());
			if(ValidateUtils.isNull(su.getSupplierName())){
				flag = false;
				model.addAttribute("ERR_supplierDepName", "乙方单位不能为空");
			}
		}
		if(ValidateUtils.isNull(purCon.getSupplierLegal())){
			flag = false;
			model.addAttribute("ERR_supplierLegal", "乙方法人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierAgent())){
			flag = false;
			model.addAttribute("ERR_supplierAgent", "乙方委托代理人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierContact())){
			flag = false;
			model.addAttribute("ERR_supplierContact", "乙方联系人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierContactTelephone())){
			flag = false;
			model.addAttribute("ERR_supplierContactTelephone", "乙方联系电话不能为空");
		}else if(!ValidateUtils.Tele(purCon.getSupplierContactTelephone())){
			flag = false;
			model.addAttribute("ERR_supplierContactTelephone", "请输入正确的联系电话");
		}
		if(ValidateUtils.isNull(purCon.getSupplierContactAddress())){
			flag = false;
			model.addAttribute("ERR_supplierContactAddress", "乙方地址不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_supplierUnitpostCode", "乙方邮编不能为空");
		}else if(!ValidateUtils.Zipcode(purCon.getSupplierUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_supplierUnitpostCode", "请输入正确的邮编");
		}
		if(ValidateUtils.isNull(purCon.getSupplierBank())){
			flag = false;
			model.addAttribute("ERR_supplierBank", "乙方开户银行不能为空");
		}
		if(ValidateUtils.isNull(purCon.getSupplierBankName())){
			flag = false;
			model.addAttribute("ERR_supplierBankName", "乙方开户名称不能为空");
		}
		if(ValidateUtils.isNull(purCon.getBingContact())){
			flag = false;
			model.addAttribute("ERR_bingContact", "丙方联系人不能为空");
		}
		if(ValidateUtils.isNull(purCon.getBingContactTelephone())){
			flag = false;
			model.addAttribute("ERR_bingContactTelephone", "丙方联系电话不能为空");
		}else if(!ValidateUtils.Tele(purCon.getBingContactTelephone())){
			flag = false;
			model.addAttribute("ERR_bingContactTelephone", "请输入正确的联系电话");
		}
		if(ValidateUtils.isNull(purCon.getBingContactAddress())){
			flag = false;
			model.addAttribute("ERR_bingContactAddress", "丙方地址不能为空");
		}
		if(ValidateUtils.isNull(purCon.getBingUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_bingUnitpostCode", "丙方邮编不能为空");
		}else if(!ValidateUtils.Zipcode(purCon.getBingUnitpostCode())){
			flag = false;
			model.addAttribute("ERR_bingUnitpostCode", "请输入正确的邮编");
		}
		map.put("flag", flag);
		map.put("model", model);
		return map;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:58:05  
	* @Description: 查询草案合同列表 
	* @param @param request
	* @param @param page 分页
	* @param @param model
	* @param @param purCon 合同实体类
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectDraftContract")
	public String selectDraftContract(HttpServletRequest request,Integer page,Model model,PurchaseContract purCon) throws Exception{
		if(page==null){
			page=1;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("page", page);
		if(purCon.getProjectName()!=null){
			map.put("projectName", purCon.getProjectName());
		}
		if(purCon.getCode()!=null){
			map.put("code", purCon.getCode());
		}
		if(purCon.getSupplierDepName()!=null){
			map.put("supplierDepName", purCon.getSupplierDepName());
		}
		if(purCon.getPurchaseDepName()!=null){
			map.put("purchaseDepName", purCon.getPurchaseDepName());
		}
		if(purCon.getDemandSector()!=null){
			map.put("demandSector", purCon.getDemandSector());
		}
		if(purCon.getDocumentNumber()!=null){
			map.put("documentNumber", purCon.getDocumentNumber());
		}
		if(purCon.getYear_string()!=null){
			if(ValidateUtils.Integer(purCon.getYear_string())){
				map.put("year", new BigDecimal(purCon.getYear_string()));
			}else{
				map.put("year", 1234);
			}
		}
		if(purCon.getBudgetSubjectItem()!=null){
			map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
		}
		if(purCon.getStatus()!=null){
			map.put("status", purCon.getStatus());
		}
		List<PurchaseContract> draftConList = new ArrayList<PurchaseContract>();
		User user = (User) request.getSession().getAttribute("loginUser");
		List<Role> roleList = user.getRoles();
		boolean isRole = false;
		for(Role r:roleList){
			if(r.getCode().equals("PURCHASE_ORG_R")||r.getCode().equals("ADMIN_R")){
				isRole = true;
			}
		}
		if(isRole){
			draftConList = purchaseContractService.selectAllContractByStatus(map);
			for(PurchaseContract pur:draftConList){
				Supplier su = supplierService.selectOne(pur.getSupplierDepName());
				PurchaseDep purdep = purchaseOrgnizationServiceI.selectPurchaseById(pur.getBingDepName());
				Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(pur.getPurchaseDepName());
				if(org.getName()==null){
					pur.setShowDemandSector("");
				}else{
					pur.setShowDemandSector(org.getName());
				}
				if(su.getSupplierName()!=null){
					pur.setShowSupplierDepName(su.getSupplierName());
				}else{
					pur.setShowSupplierDepName("");
				}
				if(purdep.getDepName()!=null){
					pur.setShowPurchaseDepName(su.getSupplierName());
				}else{
					pur.setShowPurchaseDepName("");
				}
			}
		}
		BigDecimal contractSum = new BigDecimal(0);
		if(draftConList.size()>0){
			for(int i=0;i<draftConList.size();i++){
				if(draftConList.get(i)!=null){
					if(draftConList.get(i).getMoney()!=null){
						contractSum = contractSum.add(draftConList.get(i).getMoney());
					}
				}
			}
		}
		DictionaryData dd=new DictionaryData();
		dd.setCode("DRAFT_REVIEWED");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		model.addAttribute("list", new PageInfo<PurchaseContract>(draftConList));
		model.addAttribute("draftConList", draftConList);
		model.addAttribute("contractSum",contractSum);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/draftlist";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:58:05  
	* @Description: 查询草稿合同列表 
	* @param @param request
	* @param @param page 分页
	* @param @param model
	* @param @param purCon 合同实体类
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectRoughContract")
	public String selectRoughContract(HttpServletRequest request,Integer page,Model model,PurchaseContract purCon) throws Exception{
		if(page==null){
			page=1;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("page", page);
		if(purCon.getProjectName()!=null){
			map.put("projectName", purCon.getProjectName());
		}
		if(purCon.getCode()!=null){
			map.put("code", purCon.getCode());
		}
		if(purCon.getSupplierDepName()!=null){
			map.put("supplierDepName", purCon.getSupplierDepName());
		}
		if(purCon.getPurchaseDepName()!=null){
			map.put("purchaseDepName", purCon.getPurchaseDepName());
		}
		if(purCon.getDemandSector()!=null){
			map.put("demandSector", purCon.getDemandSector());
		}
		if(purCon.getDocumentNumber()!=null){
			map.put("documentNumber", purCon.getDocumentNumber());
		}
		if(purCon.getYear_string()!=null){
			if(ValidateUtils.Integer(purCon.getYear_string())){
				map.put("year", new BigDecimal(purCon.getYear_string()));
			}else{
				map.put("year", 1234);
			}
		}
		if(purCon.getBudgetSubjectItem()!=null){
			map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
		}
		List<PurchaseContract> roughConList = new ArrayList<PurchaseContract>();
		User user = (User) request.getSession().getAttribute("loginUser");
		List<Role> roleList = user.getRoles();
		boolean isRole = false;
		for(Role r:roleList){
			if(r.getCode().equals("PURCHASE_ORG_R")||r.getCode().equals("ADMIN_R")){
				isRole = true;
			}
		}
		if(isRole){
//			roughConList = purchaseContractService.selectRoughContract(map);
			roughConList = purchaseContractService.selectAllContractByStatus(map);
		}
		BigDecimal contractSum = new BigDecimal(0);
		if(roughConList.size()>0){
			for(int i=0;i<roughConList.size();i++){
				if(roughConList.get(i)!=null){
					if(roughConList.get(i).getMoney()!=null){
						contractSum = contractSum.add(roughConList.get(i).getMoney());
					}
				}
			}
		}
		model.addAttribute("list", new PageInfo<PurchaseContract>(roughConList));
		model.addAttribute("roughConList", roughConList);
		model.addAttribute("contractSum", contractSum);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/roughlist";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:58:38  
	* @Description: 创建草稿合同页面 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createDraftContract")
	public String createDraftContract(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectDraftById(id);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
//		
//		String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
//		model.addAttribute("attachuuid", uuid);
//		DictionaryData dd=new DictionaryData();
//		dd.setCode("CONTRACT_APPROVE_ATTACH");
//		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
//		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
//		if(datas.size()>0){
//			model.addAttribute("attachtypeId", datas.get(0).getId());
//		}
		if(draftCon.getMoney()!=null){
			draftCon.setMoney_string(draftCon.getMoney().toString());
		}
		if(draftCon.getBudget()!=null){
			draftCon.setBudget_string(draftCon.getBudget().toString());
		}
		if(draftCon.getSupplierBankAccount()!=null){
			draftCon.setSupplierBankAccount_string(draftCon.getSupplierBankAccount().toString());
		}
		if(draftCon.getPurchaseBankAccount()!=null){
			draftCon.setPurchaseBankAccount_string(draftCon.getPurchaseBankAccount().toString());
		}
		model.addAttribute("purCon", draftCon);
		model.addAttribute("kinds", DictionaryDataUtil.find(5));
		model.addAttribute("id", id);
		model.addAttribute("requList", conRequList);
		return "bss/cs/purchaseContract/updateContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:58:38  
	* @Description: 创建草稿合同页面 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createRoughContract")
	public String createRoughContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract roughCon = purchaseContractService.selectRoughById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(roughCon.getId());
		roughCon.setContractReList(conRequList);
//		
//		String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
//		model.addAttribute("attachuuid", uuid);
//		DictionaryData dd=new DictionaryData();
//		dd.setCode("CONTRACT_APPROVE_ATTACH");
//		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
//		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
//		if(datas.size()>0){
//			model.addAttribute("attachtypeId", datas.get(0).getId());
//		}
		if(roughCon.getMoney()!=null){
			roughCon.setMoney_string(roughCon.getMoney().toString());
		}
		if(roughCon.getBudget()!=null){
			roughCon.setBudget_string(roughCon.getBudget().toString());
		}
		if(roughCon.getSupplierBankAccount()!=null){
			roughCon.setSupplierBankAccount_string(roughCon.getSupplierBankAccount().toString());
		}
		if(roughCon.getPurchaseBankAccount()!=null){
			roughCon.setPurchaseBankAccount_string(roughCon.getPurchaseBankAccount().toString());
		}
		model.addAttribute("draftCon", roughCon);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/roughContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:59:07  
	* @Description: 展示合同草稿 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/showDraftContract")
	public String showDraftContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		String status = request.getParameter("status");
		String url = "";
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		Supplier su = supplierService.selectOne(draftCon.getSupplierDepName());
		PurchaseDep purdep = purchaseOrgnizationServiceI.selectPurchaseById(draftCon.getBingDepName());
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(draftCon.getPurchaseDepName());
		draftCon.setShowDemandSector(org.getName());
		draftCon.setShowSupplierDepName(su.getSupplierName());
		draftCon.setShowPurchaseDepName(purdep.getDepName());
		model.addAttribute("draftCon", draftCon);
		model.addAttribute("attachuuid", ids);
		DictionaryData dd=new DictionaryData();
		dd.setCode("DRAFT_REVIEWED");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		DictionaryData dds=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datass = dictionaryDataServiceI.find(dds);
		request.getSession().setAttribute("contractattachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("contractattachId", datass.get(0).getId());
		}
		if(status.equals("0")){
			url = "bss/cs/purchaseContract/showRoughContract";
		}else if(status.equals("1")){
			url = "bss/cs/purchaseContract/showDraftContract";
		}else if(status.equals("2")){
			url = "bss/cs/purchaseContract/showFormalContract";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:59:07  
	* @Description: 展示合同草稿 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/showRoughContract")
	public String showRoughContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectRoughById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		return "bss/cs/purchaseContract/showDraftContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:01:37  
	* @Description: 展示正式合同 
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/showFormalContract")
	public String showFormalContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		
		model.addAttribute("attachuuid", ids);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		
		PurchaseContract draftCon = purchaseContractService.selectFormalById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		return "bss/cs/purchaseContract/showFormalContract";
	}
	
	
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:03:56  
	* @Description: 生成正式合同 
	* @param @param agrfile
	* @param @param purCon
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/updateDraftById")
	public String updateDraftById(PurchaseContract purCon,HttpServletRequest request,Model model) throws Exception{
		Boolean flag = true;
		String url = "";
		String fga = request.getParameter("fga");
		String fra = request.getParameter("fra");
		if(purCon.getApprovalNumber()==null || purCon.getApprovalNumber().equals("")){
			flag=false;
			model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
		}
		if(purCon.getFormalGitAt()==null){
			flag=false;
			model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
		}
		if(purCon.getFormalReviewedAt()==null){
			flag=false;
			model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
		}
		if(purCon.getFormalGitAt()!=null && purCon.getFormalReviewedAt()!=null){
			Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
			Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
			purCon.setFormalGitAt(formalGitAt);
			purCon.setFormalReviewedAt(formalRAt);
			if(formalGitAt.getTime()>formalRAt.getTime()){
				flag=false;
				model.addAttribute("ERR_formalGitAt", "报批时间不能早于提报时间");
				model.addAttribute("ERR_formalReviewedAt", "报批时间不能早于提报时间");
			}
		}
		if(flag){
			purCon.setUpdatedAt(new Date());
			List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
			PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
			purchaseContractService.updateByPrimaryKeySelective(purCon);
//			purchaseContractService.createWord(pur, requList,request);
			appraisalContractService.insertPurchaseContract(pur);
			url="redirect:selectAllPuCon.html";
		}else{
			model.addAttribute("attachuuid", purCon.getId());
			DictionaryData dd=new DictionaryData();
			dd.setCode("CONTRACT_APPROVE_ATTACH");
			List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
			if(datas.size()>0){
				model.addAttribute("attachtypeId", datas.get(0).getId());
			}
			model.addAttribute("purCon", purCon);
			url="bss/cs/purchaseContract/transFormaTional";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:03:56  
	* @Description: 生成正式合同 
	* @param @param agrfile
	* @param @param purCon
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/toCreateFormalContract")
	public String toCreateFormalContract(PurchaseContract purCon,HttpServletRequest request,Model model) throws Exception{
		Boolean flag = true;
		String url = "";
		String fga = request.getParameter("fga");
		String fra = request.getParameter("fra");
		if(purCon.getApprovalNumber()==null || purCon.getApprovalNumber().equals("")){
			flag=false;
			model.addAttribute("ERR_approvalNumber", "合同批准文号不可为空");
		}
		if(purCon.getFormalGitAt()==null){
			flag=false;
			model.addAttribute("ERR_formalGitAt", "正式合同上报时间不可为空");
		}
		if(purCon.getFormalReviewedAt()==null){
			flag=false;
			model.addAttribute("ERR_formalReviewedAt", "正式合同报批时间不可为空");
		}
		if(purCon.getFormalGitAt()!=null && purCon.getFormalReviewedAt()!=null){
			Date formalGitAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fga);
			Date formalRAt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fra);
			purCon.setFormalGitAt(formalGitAt);
			purCon.setFormalReviewedAt(formalRAt);
			if(formalGitAt.getTime()>formalRAt.getTime()){
				flag=false;
				model.addAttribute("ERR_formalGitAt", "报批时间不能早于提报时间");
				model.addAttribute("ERR_formalReviewedAt", "报批时间不能早于提报时间");
			}
		}
		if(flag){
			purCon.setUpdatedAt(new Date());
			List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
			PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
			purchaseContractService.updateByPrimaryKeySelective(purCon);
			purchaseContractService.createWord(pur, requList,request);
			appraisalContractService.insertPurchaseContract(pur);
			url="redirect:selectDraftContract.html";
		}else{
			model.addAttribute("attachuuid", purCon.getId());
			DictionaryData dd=new DictionaryData();
			dd.setCode("CONTRACT_APPROVE_ATTACH");
			List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
			if(datas.size()>0){
				model.addAttribute("attachtypeId", datas.get(0).getId());
			}
			model.addAttribute("purCon", purCon);
			url="bss/cs/purchaseContract/toFormalContract";
		}
		return url;
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:05:57  
	* @Description: 删除合同草稿 
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/deleteDraft")
	public String deleteDraft(HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			purchaseContractService.deleteDraftByPrimaryKey(ids[i]);
		}
		return "redirect:selectDraftContract.html";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:05:57  
	* @Description: 删除合同草稿 
	* @param @param request
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/deleteRoughDraft")
	public String deleteRoughDraft(HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		for(int i=0;i<ids.length;i++){
			purchaseContractService.deleteRoughByPrimaryKey(ids[i]);
		}
		return "redirect:selectRoughContract.html";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:06:13  
	* @Description: 查询正式合同 
	* @param @param request
	* @param @param page 分页
	* @param @param model
	* @param @param purCon 合同实体类
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/selectFormalContract")
	public String selectFormalContract(HttpServletRequest request,Integer page,Model model,PurchaseContract purCon) throws Exception{
		if(page==null){
			page=1;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("page", page);
		if(purCon.getProjectName()!=null){
			map.put("projectName", purCon.getProjectName());
		}
		if(purCon.getCode()!=null){
			map.put("code", purCon.getCode());
		}
		if(purCon.getSupplierDepName()!=null){
			map.put("supplierDepName", purCon.getSupplierDepName());
		}
		if(purCon.getPurchaseDepName()!=null){
			map.put("purchaseDepName", purCon.getPurchaseDepName());
		}
		if(purCon.getDemandSector()!=null){
			map.put("demandSector", purCon.getDemandSector());
		}
		if(purCon.getDocumentNumber()!=null){
			map.put("documentNumber", purCon.getDocumentNumber());
		}
		if(purCon.getYear_string()!=null){
			if(ValidateUtils.Integer(purCon.getYear_string())){
				map.put("year", new BigDecimal(purCon.getYear_string()));
			}else{
				map.put("year", 1234);
			}
		}
		if(purCon.getBudgetSubjectItem()!=null){
			map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
		}
		List<PurchaseContract> formalConList = new ArrayList<PurchaseContract>();
		User user = (User) request.getSession().getAttribute("loginUser");
		List<Role> roleList = user.getRoles();
		boolean isRole = false;
		for(Role r:roleList){
			if(r.getCode().equals("PURCHASE_ORG_R")||r.getCode().equals("ADMIN_R")){
				isRole = true;
			}
		}
		if(isRole){
			formalConList = purchaseContractService.selectFormalContract(map);
		}
		PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(formalConList);
		model.addAttribute("list", list);
		BigDecimal contractSum = new BigDecimal(0);
		if(formalConList.size()>0){
			for(int i=0;i<formalConList.size();i++){
				if(formalConList.get(i)!=null){
					if(formalConList.get(i).getMoney()!=null){
						contractSum = contractSum.add(formalConList.get(i).getMoney());
					}
				}
			}
		}
		model.addAttribute("formalConList", formalConList);
		model.addAttribute("contractSum", contractSum);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/formallist";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-15 下午2:54:43  
	* @Description: 跳转生成正式合同页面 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createTransFormal")
	public String createTransFormal(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("ids");
		String supcheckid = request.getParameter("supcheckid");
		String[] supcheckids = supcheckid.split(",");
		purCon.setId(id);
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
		if(flag == false){
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("purCon", purCon);
			model.addAttribute("kinds", DictionaryDataUtil.find(5));
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
			}
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", purCon.getDocumentNumber());
			model.addAttribute("id", purCon.getId());
			model.addAttribute("supcheckid", supcheckid);
			url = "bss/cs/purchaseContract/errContract";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setCreatedAt(new Date());
			purCon.setUpdatedAt(new Date());
			purCon.setMoney(new BigDecimal(purCon.getMoney_string()));
			purCon.setBudget(new BigDecimal(purCon.getBudget_string()));
			purCon.setSupplierBankAccount(new BigDecimal(purCon.getSupplierBankAccount_string()));
			purCon.setPurchaseBankAccount(new BigDecimal(purCon.getPurchaseBankAccount_string()));
			purchaseContractService.insertSelective(purCon);
			List<ContractRequired> requList = proList.getProList();
			if(requList!=null){
				for(int i=0;i<requList.size();i++){
					if(requList.get(i).getPlanNo()==null){
						requList.remove(i);
					}
				}
				for(ContractRequired conRequ:requList){
					conRequ.setContractId(purCon.getId());
					contractRequiredService.insertSelective(conRequ);
				}
			}
			model.addAttribute("attachuuid", id);
			DictionaryData dd=new DictionaryData();
			dd.setCode("CONTRACT_APPROVE_ATTACH");
			List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
			if(datas.size()>0){
				model.addAttribute("attachtypeId", datas.get(0).getId());
			}
			for(String supchid:supcheckids){
				SupplierCheckPass sup = new SupplierCheckPass();
				sup.setId(supchid);
				sup.setIsCreateContract(1);
				supplierCheckPassService.update(sup);
			}
			model.addAttribute("id", id);
			url = "bss/cs/purchaseContract/transFormaTional";
//		model.addAttribute("id", id);
//		
//		model.addAttribute("attachuuid", id);
//		DictionaryData dd=new DictionaryData();
//		dd.setCode("CONTRACT_APPROVE_ATTACH");
//		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
//		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
//		if(datas.size()>0){
//			model.addAttribute("attachtypeId", datas.get(0).getId());
//		}
//		List<ContractRequired> requList = proList.getProList();
//		if(requList!=null){
//			for(int i=0;i<requList.size();i++){
//				if(requList.get(i).getPlanNo()==null){
//					requList.remove(i);
//				}
//			}
//		}
//		return "bss/cs/purchaseContract/transFormaTional";
		}
		return url;
	}
	
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-15 下午2:54:43  
	* @Description: 跳转生成正式合同页面 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/toFormalContract")
	public String toFormalContract(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		model.addAttribute("attachuuid", id);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		
		return "bss/cs/purchaseContract/toFormalContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-15 下午2:54:43  
	* @Description: 跳转生成正式合同页面 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createerrContractPage")
	public String createerrContractPage(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("ids");
		PurchaseContract purCon = purchaseContractService.selectById(id);
		purCon.setBudget_string(purCon.getBudget().toString());
		purCon.setMoney_string(purCon.getMoney().toString());
		purCon.setPurchaseBankAccount_string(purCon.getPurchaseBankAccount().toString());
		purCon.setSupplierBankAccount_string(purCon.getSupplierBankAccount().toString());
		List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(id);
		model.addAttribute("purCon", purCon);
		model.addAttribute("id", id);
		model.addAttribute("kinds", DictionaryDataUtil.find(5));
		model.addAttribute("requList", requList);
		model.addAttribute("planNos", purCon.getDocumentNumber());
		purchaseContractService.deleteRoughByPrimaryKey(id);
		return "bss/cs/purchaseContract/errContract";
	}
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-15 下午2:54:43  
	* @Description: 跳转生成正式合同页面 
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/createStraightContract")
	public String createStraightContract(HttpServletRequest request,Model model) throws Exception{
		String contractuuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("attachuuid", contractuuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		model.addAttribute("kinds", DictionaryDataUtil.find(5));
		return "bss/cs/purchaseContract/straightContract";
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:05:30  
	* @Description: 新增明细验证 
	* @param @param conRe
	* @param @param response
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/validAddRe")
	public void validAddRe(ContractRequired conRe,HttpServletResponse response) throws Exception{
		boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		if(ValidateUtils.isNull(conRe.getGoodsName())){
			flag=false;
			map.put("wzmc", "物资名称不能为空");
		}
		if(ValidateUtils.isNull(conRe.getPlanNo())){
			flag=false;
			map.put("bh", "编号不能为空");
		}
		if(ValidateUtils.isNull(conRe.getDeliverDate())){
			flag=false;
			map.put("jfsj", "交付时间不能为空");
		}
		if(ValidateUtils.isNull(conRe.getBrand())){
			flag=false;
			map.put("ppsb", "品牌商标不能为空");
		}
		if(ValidateUtils.isNull(conRe.getStand())){
			flag=false;
			map.put("ggxh", "规格型号不能为空");
		}
		if(ValidateUtils.isNull(conRe.getItem())){
			flag=false;
			map.put("jldw", "计量单位不能为空");
		}
		if(ValidateUtils.isNull(conRe.getPurchaseCount_string())){
			flag=false;
			map.put("sl", "数量不能为空");
		}else if(!ValidateUtils.Z_index(conRe.getPurchaseCount_string())){
			flag=false;
			map.put("sl", "请输入正整数");
		}
		if(ValidateUtils.isNull(conRe.getPrice_string())){
			flag=false;
			map.put("dj", "单价不能为空");
		}else if(!ValidateUtils.Z_index(conRe.getPurchaseCount_string())){
			flag=false;
			map.put("dj", "请输入正整数");
		}
		
		if(flag){
			super.writeJson(response, 1);
		}else{
			super.writeJson(response, JSONSerializer.toJSON(map).toString());
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 验证选择的供应商 
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/isChoiceSupplier")
	public void isChoiceSupplier(String delSupplier,HttpServletResponse response) throws Exception{
		Boolean flag = true;
		Map<String, Object> map = new HashMap<String, Object>();
		if(ValidateUtils.isNull(delSupplier)){
			flag = false;
			map.put("delsuerr", "请先选择供应商");
		}
		if(flag){
			super.writeJson(response, 1);
		}else{
			super.writeJson(response, JSONSerializer.toJSON(map).toString());
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 查询所有可用需求部门
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/findAllUsefulOrg",produces="application/json;charest=utf-8")
	public void findAllUsefulOrg(HttpServletResponse response,HttpServletRequest request) throws Exception{
		List<Orgnization> list = purchaseContractService.findAllUsefulOrg();
		super.writeJson(response, list);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 查询所有可用供应商
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/findAllUsefulSupplier",produces="application/json;charest=utf-8")
	public void findAllUsefulSupplier(HttpServletResponse response,HttpServletRequest request) throws Exception{
		List<Supplier> list = supplierService.findAllUsefulSupplier();
		super.writeJson(response, list);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 查询所有可用采购部门
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/findAllUsefulPurDep",produces="application/json;charest=utf-8")
	public void findAllUsefulPurDep(HttpServletResponse response,HttpServletRequest request) throws Exception{
		List<PurchaseDep> list = purchaseOrgnizationServiceI.findAllUsefulPurchaseDep();
		super.writeJson(response, list);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 选择需求部门后回显
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/changeXuqiu",produces="application/json;charest=utf-8")
	public void changeXuqiu(String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(id);
		super.writeJson(response, org);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 选择供应商后回显
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/changeSupplierDep",produces="application/json;charest=utf-8")
	public void changeSupplierDep(String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		Supplier su = supplierService.selectById(id);
		super.writeJson(response, su);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-20 上午9:06:41  
	* @Description: 选择供应商后回显
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/changePurDep",produces="application/json;charest=utf-8")
	public void changePurDep(String id,HttpServletResponse response,HttpServletRequest request) throws Exception{
		PurchaseDep purDep = purchaseOrgnizationServiceI.selectPurchaseById(id);
		super.writeJson(response, purDep);
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-21 上午10:58:44  
	* @Description: 验证草稿提报时间 
	* @param @param purCon
	* @param @param response
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/addDraftGit")
	public void addDraftGit(HttpServletResponse response,HttpServletRequest request) throws Exception{
		Boolean flag = true;
		String draftGitAt = request.getParameter("draftGitAt");
		String draftReviewedAt = request.getParameter("draftReviewedAt");
		Date dga = null;
		Date dra = null;
		if(!draftGitAt.equals("")){
			dga = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(draftGitAt);
		}
		if(!draftReviewedAt.equals("")){
			dra = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(draftReviewedAt);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(dga==null){
			flag = false;
			map.put("gitAt", "提报时间不能为空");
		}
		if(dra==null){
			flag = false;
			map.put("reviewAt", "报批时间不能为空");
		}
		if(flag && dga.getTime()>dra.getTime()){
			flag=false;
			map.put("gitAt", "报批时间不能早于提报时间");
			map.put("reviewAt", "报批时间不能早于提报时间");
		}
		String gitStr = "";
		String reviewedStr = "";
		if(dga!=null){
			gitStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dga);
		}
		if(dra!=null){
			reviewedStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dra);
		}
		map.put("gitStr", gitStr);
		map.put("reviewedStr", reviewedStr);
		if(flag){
			super.writeJson(response, 1);
		}else{
			super.writeJson(response, JSONSerializer.toJSON(map).toString());
		}
	}
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-21 上午10:58:44  
	* @Description: 跳转至打印页面
	* @param @param purCon
	* @param @param response
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping(value="/createPrintPage",produces="application/json;charest=utf-8")
	public void createPrintPage(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletResponse response,HttpServletRequest request)throws Exception{
		String fileName = purchaseContractService.createWord(purCon, proList.getProList(), request);
		super.writeJson(response, fileName);
	}
	
	/**
     *〈简述〉保存招标文件到服务器
     *〈详细描述〉
     * @author Qu Jie
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/saveContractFile")
    public void saveContractFile(HttpServletRequest req, String projectId, Model model) throws IOException{
        String result = "保存失败";
        //判断该项目是否上传过招标文件
        String typeId = DictionaryDataUtil.getId("CONTRACT_FILE");
        List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
        if (files != null && files.size() > 0){
            //删除 ,表中数据假删除
            uploadService.updateFileOther(files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
        } else {
            result = uploadService.saveOnlineFile(req, projectId, typeId, Constant.TENDER_SYS_KEY+"");
        }
        System.out.println(result);
    }
    
    /**
     *〈简述〉跳转到打印页面
     *〈详细描述〉
     * @author Qu Jie
     * @param req
     * @param projectId 项目id
     * @throws IOException
     */
    @RequestMapping("/printContract")
    public String printContract(HttpServletRequest req,Model model) throws IOException{
        String id = req.getParameter("id");
        String status = req.getParameter("status");
        String url = "";
        PurchaseContract pur = purchaseContractService.selectById(id);
        List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(pur.getId());
        String fileName = purchaseContractService.createWord(pur, requList, req);
        model.addAttribute("id", id);
        model.addAttribute("fileName", fileName);
        if(status.equals("0")){
        	url = "bss/cs/purchaseContract/printDraft";
        }else if(status.equals("1")){
        	url = "bss/cs/purchaseContract/printDraft";
        }else if(status.equals("2")){
        	url = "bss/cs/purchaseContract/printformal";
        }
        return url;
    }
}
