package bss.controller.cs;

import java.io.File;
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
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
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
import common.constant.Constant;

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
				if(su.getSupplier()!=null){
					supplierNames+=su.getSupplier().getSupplierName()+",";
				}
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
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午2:49:35  
	* @Description: 打印草稿合同
	* @param @param purCon 采购合同实体类
	* @param @param proList 前台穿的list
	* @param @param result
	* @param @param request
	* @param @param model
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/printContract")
	public String printContract(PurchaseContract purCon,ProList proList,BindingResult result,HttpServletRequest request,Model model) throws Exception{
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
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
		List<SupplierCheckPass> chePList = supplierCheckPassService.listSupplierCheckPass(suppliercheckpass);
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
		List<SupplierCheckPass> chePList = supplierCheckPassService.listSupplierCheckPass(suppliercheckpass);
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
		Map<String, Object> map = valid(model,purCon);
		model = (Model)map.get("model");
		Boolean flag = (boolean)map.get("flag");
		String url = "";
//		if(result.hasErrors()){
//			flag = false;
//			List<FieldError> errors = result.getFieldErrors();
//			for(FieldError fieldError:errors){
//				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
//			}
//		}
		if(flag == false){
			String ids = request.getParameter("ids");
			List<ContractRequired> requList = proList.getProList();
			model.addAttribute("purCon", purCon);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", purCon.getDocumentNumber());
			model.addAttribute("ids", ids);
			url = "bss/cs/purchaseContract/textErrContract";
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
		if(ValidateUtils.isNull(purCon.getSupplierBankAccount())){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "乙方账号不能为空");
		}else if(!ValidateUtils.BANK_ACCOUNT(purCon.getSupplierBankAccount().toString()) == false){
			flag = false;
			model.addAttribute("ERR_supplierBankAccount", "请输入正确的乙方账号");
		}
		if(ValidateUtils.isNull(purCon.getName())){
			flag = false;
			model.addAttribute("ERR_name", "合同名称不能为空");
		}
		if(ValidateUtils.isNull(purCon.getDemandSector())){
			flag = false;
			model.addAttribute("ERR_demandSector", "需求部门不能为空");
		}
		if(ValidateUtils.isNull(purCon.getCode())){
			flag = false;
			model.addAttribute("ERR_code", "合同编号不能为空");
		}
		if(ValidateUtils.isNull(purCon.getBudgetSubjectItem())){
			flag = false;
			model.addAttribute("ERR_budgetSubjectItem", "项级预算科目不能为空");
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
		if(ValidateUtils.isNull(purCon.getPurchaseBankAccount())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "甲方账号不能为空");
		}else if(!ValidateUtils.BANK_ACCOUNT(purCon.getPurchaseBankAccount().toString())){
			flag = false;
			model.addAttribute("ERR_purchaseBankAccount", "请输入正确的甲方账号");
		}
		if(ValidateUtils.isNull(purCon.getMoney())){
			flag = false;
			model.addAttribute("ERR_money", "合同金额不能为空");
		}else if(!ValidateUtils.Money(purCon.getMoney().toString())){
			flag = false;
			model.addAttribute("ERR_money", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getBudget())){
			flag = false;
			model.addAttribute("ERR_budget", "合同预算不能为空");
		}else if(!ValidateUtils.Money(purCon.getBudget().toString())){
			flag = false;
			model.addAttribute("ERR_budget", "请输入正确金额");
		}
		if(ValidateUtils.isNull(purCon.getSupplierDepName())){
			flag = false;
			model.addAttribute("ERR_supplierDepName", "乙方单位不能为空");
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
		}else if(!ValidateUtils.Zipcode(purCon.getPurchaseUnitpostCode())){
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
		if(ValidateUtils.isNull(purCon.getContent())){
			flag = false;
			model.addAttribute("ERR_content", "合同正文不能为空");
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
	* @Description: 查询草稿合同列表 
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
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		
		String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		model.addAttribute("attachuuid", uuid);
		DictionaryData dd=new DictionaryData();
		dd.setCode("CONTRACT_APPROVE_ATTACH");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("attachsysKey", Constant.TENDER_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("attachtypeId", datas.get(0).getId());
		}
		
		model.addAttribute("draftCon", draftCon);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/draftContract";
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
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
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
	* @date 2016-11-11 下午3:02:44  
	* @Description: 修改合同草稿 
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
	public String updateDraftContract(HttpServletRequest request,PurchaseContract purCon,ProList proList,Model model) throws Exception{
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
			String rootpath = (PathUtil.getWebRoot() + "picupload/").replace("\\", "/");
			File rootFile = new File(rootpath);
			if(!rootFile.exists()){
				rootFile.mkdirs();
			}
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
			purCon.setUpdatedAt(new Date());
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
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:03:56  
	* @Description: 通过id修改草稿合同 
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
		if(flag){
			purCon.setUpdatedAt(new Date());
			List<ContractRequired> requList = contractRequiredService.selectConRequeByContractId(purCon.getId());
			PurchaseContract pur = purchaseContractService.selectById(purCon.getId());
			purchaseContractService.updateByPrimaryKeySelective(purCon);
			purchaseContractService.createWord(pur, requList,request);
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
			url="bss/cs/purchaseContract/transFormaTional";
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
		String ids = request.getParameter("ids");
		purchaseContractService.deleteDraftByPrimaryKey(ids);
		return "redirect:selectDraftContract.html";
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
	public String createTransFormal(HttpServletRequest request,Model model) throws Exception{
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
		
		return "bss/cs/purchaseContract/transFormaTional";
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
		if(ValidateUtils.isNull(conRe.getPurchaseCount())){
			flag=false;
			map.put("sl", "数量不能为空");
		}else if(!ValidateUtils.Z_index(conRe.getPurchaseCount().toString())){
			flag=false;
			map.put("sl", "请输入正整数");
		}
		if(ValidateUtils.isNull(conRe.getPrice())){
			flag=false;
			map.put("dj", "单价不能为空");
		}else if(!ValidateUtils.Z_index(conRe.getPurchaseCount().toString())){
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
}
