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

import ses.util.PathUtil;
import ses.util.ValidateUtils;
import com.github.pagehelper.PageInfo;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
import bss.service.ppms.TaskService;

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
		List<Project> projectList = projectService.selectSuccessProject(map);
		model.addAttribute("list", new PageInfo<Project>(projectList));
		model.addAttribute("projectList", projectList);
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
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方银行账号");
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
			Project project = projectService.selectById(ids);
			HashMap<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("id", project.getId());
			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
			
//			HashMap<String, Object> requMainMap = new HashMap<String, Object>();
//			requMainMap.put("id", project.getId());
//			List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
			String planNos = "";
			HashMap<String, Object> taskMap = new HashMap<String, Object>();
			taskMap.put("projectId",project.getId());
			List<ProjectTask> taskList = projectTaskService.queryByNo(taskMap);
			for(ProjectTask pur:taskList){
				Task task = taskService.selectById(pur.getTaskId());
				planNos+=task.getDocumentNumber()+",";
			}
			model.addAttribute("project", project);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", planNos);
			model.addAttribute("ids", ids);
			return "bss/cs/purchaseContract/textContract";
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
		String ids = request.getParameter("ids");
		String[] id = ids.split(",");
		Project project = projectService.selectById(id[0]);
		model.addAttribute("project", project);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/commonContract";
	}
	
	@RequestMapping("/createDetailContract")
	public String createDetailContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		String[] id = ids.split(",");
		HashMap<String, Object> requMap = new HashMap<String, Object>();
		requMap.put("idArray", id);
		List<ProjectDetail> requList = projectDetailService.selectByProjectIds(requMap);
		model.addAttribute("requList", requList);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/detailContract";
	}
	
	@RequestMapping("/createTextContract")
	public String createTextContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		List<Project> projectList = new ArrayList<Project>();
		String[] id = ids.split(",");
		for(int i=0;i<id.length;i++){
			Project project = projectService.selectById(ids);
			projectList.add(project);
		}
		HashMap<String, Object> requMap = new HashMap<String, Object>();
		requMap.put("idArray", id);
		List<ProjectDetail> requList = projectDetailService.selectByProjectIds(requMap);
//		HashMap<String, Object> requMainMap = new HashMap<String, Object>();
//		requMainMap.put("id", project.getId());
//		List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
		String planNos = "";
		HashMap<String, Object> taskMap = new HashMap<String, Object>();
		taskMap.put("idArray",id);
		List<ProjectTask> taskList = projectTaskService.queryByProjectNos(taskMap);
		for(ProjectTask pur:taskList){
			Task task = taskService.selectById(pur.getTaskId());
			planNos+=task.getDocumentNumber()+",";
		}
		model.addAttribute("project", projectList.get(0));
		model.addAttribute("requList", requList);
		model.addAttribute("planNos", planNos);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/textContract";
	}

	@RequestMapping("/addPurchaseContract")
	public String addPurchaseContract(@Valid PurchaseContract purCon,BindingResult result,ProList proList,HttpServletRequest request,Model model) throws Exception{
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
			Project project = projectService.selectById(ids);
			HashMap<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("id", project.getId());
			List<ProjectDetail> requList = projectDetailService.selectById(requMap);
			
//			HashMap<String, Object> requMainMap = new HashMap<String, Object>();
//			requMainMap.put("id", project.getId());
//			List<ProjectDetail> requMainList = projectDetailService.selectById(requMainMap);
			String planNos = "";
			HashMap<String, Object> taskMap = new HashMap<String, Object>();
			taskMap.put("projectId",project.getId());
			List<ProjectTask> taskList = projectTaskService.queryByNo(taskMap);
			for(ProjectTask pur:taskList){
				Task task = taskService.selectById(pur.getTaskId());
				planNos+=task.getDocumentNumber()+",";
			}
			model.addAttribute("project", project);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", planNos);
			model.addAttribute("ids", ids);
			return "bss/cs/purchaseContract/textContract";
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
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方银行账号");
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
				conRequ.setContractId(id);
				contractRequiredService.insertSelective(conRequ);
			}
			purchaseContractService.createWord(purCon, requList);
			url = "redirect:selectDraftContract.html";
		}
		return url;
	}
	
	@RequestMapping("/updateDraftById")
	public String updateDraftById(@RequestParam("agrfile") MultipartFile agrfile,String ids,String apN,String status,HttpServletRequest request) throws Exception{
		String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
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
		PurchaseContract purCon = new PurchaseContract();
		purCon.setId(ids);
		purCon.setApprovalNumber(apN);
		purCon.setApprovePic("picupload/"+fileName);
		purCon.setStatus(Integer.parseInt(status));
		purCon.setUpdatedAt(new Date());
		List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
		PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
		purchaseContractService.updateByPrimaryKeySelective(purCon);
		purchaseContractService.createWord(pur, requList);
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
}
