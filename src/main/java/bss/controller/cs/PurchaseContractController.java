package bss.controller.cs;

import java.io.File;
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
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
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

import com.github.pagehelper.PageInfo;

/* 
 *@Title:PurchaseContractController
 *@Description:采购合同控制类
 *@author QuJie
 *@date 2016-9-23下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseContract")
public class PurchaseContractController {
	
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
	
	@RequestMapping("/selectAllPuCon")
	public String selectAllPurchaseContract(Model model,Integer page,HttpServletRequest request) throws Exception{
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
		for(Packages pa:packList){
			Project project = projectService.selectById(pa.getProjectId());
			pa.setProject(project);
			SupplierCheckPass sucp = new SupplierCheckPass();
			sucp.setPackageId(pa.getId());
			sucp.setIsWonBid((short)1);
			List<SupplierCheckPass> suList = supplierCheckPassService.listSupplierCheckPass(sucp);
			String supplierNames = "";
			for(SupplierCheckPass su:suList){
				supplierNames+=su.getSupplier().getSupplierName()+",";
			}
			pa.setSupplierNames(supplierNames);
			pacList.add(pa);
		}
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
//				List<SupplierCheckPass> chList = supplierCheckPassService.listSupplierCheckPass(supch);
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
		return "bss/cs/purchaseContract/list";
	}
	
	@RequestMapping("/printContract")
	public String printContract(@Valid PurchaseContract purCon,BindingResult result,ProList proList,HttpServletRequest request,Model model) throws Exception{
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(purCon.getContractType()!=null){
			flag = true;
		}else{
			flag = false;
			model.addAttribute("ERR_contractType", "合同类型不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(ValidateUtils.Money(purCon.getMoney().toString()) == false){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(ValidateUtils.Money(purCon.getBudget().toString()) == false){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(result.hasErrors()){
			flag = false;
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		if(flag == false){
			String ids = request.getParameter("ids");
//			Project project = projectService.selectById(ids);
//			HashMap<String, Object> requMap = new HashMap<String, Object>();
//			requMap.put("id", project.getId());
//			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
//			
//			HashMap<String, Object> requMainMap = new HashMap<String, Object>();
//			requMainMap.put("id", project.getId());
//			List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
//			String planNos = "";
//			HashMap<String, Object> taskMap = new HashMap<String, Object>();
//			taskMap.put("projectId",project.getId());
//			List<ProjectTask> taskList = projectTaskService.queryByNo(taskMap);
//			for(ProjectTask pur:taskList){
//				Task task = taskService.selectById(pur.getTaskId());
//				planNos+=task.getDocumentNumber()+",";
//			}
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("purCon", purCon);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", purCon.getDocumentNumber());
			model.addAttribute("ids", ids);
			return "bss/cs/purchaseContract/textErrContract";
		}else{
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("requList", requList);
			model.addAttribute("purCon", purCon);
			url = "bss/cs/purchaseContract/printModel";
		}
		return url;
	}
	
	@RequestMapping(value="/createAllCommonContract",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String createAllCommonContract(HttpServletRequest request) throws Exception{
		String id = request.getParameter("ids");
		String[] ids = id.split(",");
		String flag = "true";
		String news = "";
		List<String> supIdList = new ArrayList<String>();
		for(int i=0;i<ids.length;i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", ids[i]);
			Packages pack = packageService.findPackageById(map).get(0);
			SupplierCheckPass suchp = new SupplierCheckPass();
			suchp.setPackageId(pack.getId());
			suchp.setIsWonBid((short)1);
			List<SupplierCheckPass> chList = supplierCheckPassService.listSupplierCheckPass(suchp);
			if(chList.size()>1){
				flag="false";
				news = "";
				news+="有多个供应商，无法合并";
				break;
			}else{
				supIdList.add(chList.get(0).getSupplier().getId());
			}
		}
		
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
		
		if(flag.equals("true")){
			return "true=";
		}else{
			return "false="+news;
		}
	}
	
	@RequestMapping(value="/selectSuppliers",produces = "text/html; charset=utf-8")
	@ResponseBody
	public String  selectSuppliers(HttpServletRequest request){
		String packageId = request.getParameter("packageId");
		String[] packIds = packageId.split(",");
		SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
		suppliercheckpass.setPackageId(packIds[0]);
		suppliercheckpass.setIsWonBid((short)1);
		List<SupplierCheckPass> chePList = supplierCheckPassService.listSupplierCheckPass(suppliercheckpass);
		String options = "";
		for(SupplierCheckPass su:chePList){
			String option = "<option value='"+su.getSupplier().getId()+"'>"+su.getSupplier().getSupplierName()+"</option>";
			options+=option;
		}
		return options;
	}
	
	@RequestMapping("/selectSupplierByPId")
	@ResponseBody
	public String  selectSupplierByPId(HttpServletRequest request){
		String packageId = request.getParameter("packageId");
		String[] packIds = packageId.split(",");
		SupplierCheckPass suppliercheckpass = new SupplierCheckPass();
		suppliercheckpass.setPackageId(packIds[0]);
		suppliercheckpass.setIsWonBid((short)1);
		List<SupplierCheckPass> chePList = supplierCheckPassService.listSupplierCheckPass(suppliercheckpass);
		String supid = "";
		for(SupplierCheckPass su:chePList){
			supid += su.getSupplier().getId();
		}
		return supid;
	}
	
	@RequestMapping("/printDraftContract")
	public String printDraftContract(@Valid PurchaseContract purCon,BindingResult result,ProList proList,HttpServletRequest request,Model model) throws Exception{
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(purCon.getContractType()!=null){
			flag = true;
		}else{
			flag = false;
			model.addAttribute("ERR_contractType", "合同类型不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(ValidateUtils.Money(purCon.getMoney().toString()) == false){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(ValidateUtils.Money(purCon.getBudget().toString()) == false){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(result.hasErrors()){
			flag = false;
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		if(flag == false){
			String ids = request.getParameter("ids");
			PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
			List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
			draftCon.setContractReList(conRequList);
			model.addAttribute("draftCon", draftCon);
			model.addAttribute("ids", ids);
			url = "bss/cs/purchaseContract/draftContract";
		}else{
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("requList", requList);
			model.addAttribute("purCon", purCon);
			url = "bss/cs/purchaseContract/printModel";
		}
		return url;
	}
	
	@RequestMapping("/printFormalContract")
	public String printFormalContract(Model model,HttpServletRequest request){
		String ids = request.getParameter("ids");
		PurchaseContract purCon = purchaseContractService.selectById(ids);
		List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(ids);
		model.addAttribute("requList", requList);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/printModel";
	}
	
	@ResponseBody
	@RequestMapping("/selectByCode")
	public PurchaseContract selectByCode(HttpServletRequest request) throws Exception{
		String code = request.getParameter("code");
		PurchaseContract purchaseCon = purchaseContractService.selectByCode(code);
		return purchaseCon;
	}
	
	@RequestMapping("/createCommonContract")
	public String createCommonContract(HttpServletRequest request,Model model) throws Exception{
		String id = request.getParameter("id");
		String[] ids = id.split(",");
		String supid = request.getParameter("supid");
		Supplier supplier = supplierService.get(supid);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", ids[0]);
		Packages pack = packageService.findPackageById(map).get(0);
		Project project = projectService.selectById(pack.getProjectId());
		project.setDealSupplier(supplier);
		model.addAttribute("project", project);
		model.addAttribute("id", id);
		model.addAttribute("supid", supid);
		return "bss/cs/purchaseContract/commonContract";
	}
	
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

	@RequestMapping("/addPurchaseContract")
	public String addPurchaseContract(@Valid PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(purCon.getContractType()!=null){
			flag = true;
		}else{
			flag = false;
			model.addAttribute("ERR_contractType", "合同类型不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(ValidateUtils.Money(purCon.getMoney().toString()) == false){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(ValidateUtils.Money(purCon.getBudget().toString()) == false){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(result.hasErrors()){
			flag = false;
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		if(flag == false){
			String ids = request.getParameter("ids");
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("purCon", purCon);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", purCon.getDocumentNumber());
			model.addAttribute("ids", ids);
			return "bss/cs/purchaseContract/textErrContract";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setCreatedAt(new Date());
			purCon.setUpdatedAt(new Date());
			purchaseContractService.insertSelective(purCon);
			String id = purCon.getId();
			List<ContractRequired> requList = proList.getProList();
			for(ContractRequired conRequ:requList){
				conRequ.setContractId(id);
				contractRequiredService.insertSelective(conRequ);
			}
			url = "redirect:selectAllPuCon.html";
		}
		return url;
	}
	
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
		if(purCon.getYear()!=null){
			map.put("year", purCon.getYear());
		}
		if(purCon.getBudgetSubjectItem()!=null){
			map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
		}
		List<PurchaseContract> draftConList = purchaseContractService.selectDraftContract(map);
		model.addAttribute("list", new PageInfo<PurchaseContract>(draftConList));
		model.addAttribute("draftConList", draftConList);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/draftlist";
	}
	
	@RequestMapping("/createDraftContract")
	public String createDraftContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/draftContract";
	}
	
	@RequestMapping("/showDraftContract")
	public String showDraftContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		return "bss/cs/purchaseContract/showDraftContract";
	}
	
	@RequestMapping("/showFormalContract")
	public String showFormalContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectFormalById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		return "bss/cs/purchaseContract/showFormalContract";
	}
	
	@RequestMapping("/updateDraftContract")
	public String updateDraftContract(@RequestParam("agrfile") MultipartFile agrfile,HttpServletRequest request,@Valid PurchaseContract purCon,BindingResult result,ProList proList,Model model) throws Exception{
		String ids = request.getParameter("ids");
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(purCon.getContractType()!=null){
			flag = true;
		}else{
			flag = false;
			model.addAttribute("ERR_contractType", "合同类型不能为空");
		}
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(ValidateUtils.Money(purCon.getMoney().toString()) == false){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(ValidateUtils.Money(purCon.getBudget().toString()) == false){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(result.hasErrors()){
			flag = false;
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		if(flag == false){
			PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
			List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
			draftCon.setContractReList(conRequList);
			model.addAttribute("draftCon", draftCon);
			model.addAttribute("ids", ids);
			url = "bss/cs/purchaseContract/draftContract";
		}else{
			String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
			File rootFile = new File(rootpath);
			if(!rootFile.exists()){
				rootFile.mkdirs();
			}
			/** 创建文件夹 */
	        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + agrfile.getOriginalFilename();		        
	        String filePath = rootpath+fileName;
	        File file = new File(filePath);
	        try {
	        	agrfile.transferTo(file);
			} catch (IllegalStateException e) {
				e.printStackTrace();				
				} catch (IOException e) {
			e.printStackTrace();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setUpdatedAt(new Date());
			purCon.setApprovePic(filePath);
			purchaseContractService.updateByPrimaryKeySelective(purCon);
			String id = purCon.getId();
			contractRequiredService.deleteByContractId(id);
			List<ContractRequired> requList = proList.getProList();
			for(ContractRequired conRequ:requList){
				if(conRequ.getGoodsName()==null){
					break;
				}
				if(conRequ.getId()==null){
					conRequ.setContractId(id);
					contractRequiredService.insertSelective(conRequ);
				}else{
					contractRequiredService.updateByPrimaryKeySelective(conRequ);
				}
			}
			if(purCon.getStatus()==2){
				purchaseContractService.createWord(purCon, requList,request);
			}
			url = "redirect:selectDraftContract.html";
		}
		return url;
	}
	
	@RequestMapping("/updateDraftById")
	public String updateDraftById(@RequestParam("agrfile") MultipartFile agrfile,PurchaseContract purCon,HttpServletRequest request) throws Exception{
		String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
		/** 创建文件夹 */
		File rootFile = new File(rootpath);
		if(!rootFile.exists()){
			rootFile.mkdirs();
		}
        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + agrfile.getOriginalFilename();		        
        String filePath = rootpath+fileName;
        File file = new File(filePath);
        try {
        	agrfile.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();				
			} catch (IOException e) {
		e.printStackTrace();
		}
		purCon.setApprovePic("picupload/"+fileName);
		purCon.setUpdatedAt(new Date());
		List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
		PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
		purchaseContractService.updateByPrimaryKeySelective(purCon);
		purchaseContractService.createWord(pur, requList,request);
		return "redirect:selectDraftContract.html";
	}
	
	@RequestMapping("/deleteDraft")
	public String deleteDraft(HttpServletRequest request) throws Exception{
		String ids = request.getParameter("ids");
		purchaseContractService.deleteDraftByPrimaryKey(ids);
		return "redirect:selectDraftContract.html";
	}
	
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
		if(purCon.getYear()!=null){
			map.put("year", purCon.getYear());
		}
		if(purCon.getBudgetSubjectItem()!=null){
			map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
		}
		List<PurchaseContract> formalConList = purchaseContractService.selectFormalContract(map);
		model.addAttribute("list", new PageInfo<PurchaseContract>(formalConList));
		model.addAttribute("formalConList", formalConList);
		model.addAttribute("purCon", purCon);
		return "bss/cs/purchaseContract/formallist";
	}
	
	/**
	 * @Title: list
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午8:05:07
	 * @Description: 合同列表
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "list")
	public String list(Model model, PurchaseContract purchaseContract, Integer page) {
		List<PurchaseContract> list = purchaseContractService.findPurchaseContractByMap(purchaseContract, page == null ? 1 : page);
		model.addAttribute("pager", new PageInfo<PurchaseContract>(list));
		model.addAttribute("projectName", purchaseContract.getProjectName());
		model.addAttribute("isDeclare", purchaseContract.getIsDeclare());
		return "bss/iacs/import_project/list";
	}
}
