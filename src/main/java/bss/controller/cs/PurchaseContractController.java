package bss.controller.cs;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.service.sms.SupplierService;
import ses.util.ValidateUtils;
import com.github.pagehelper.PageInfo;
import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Project;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.ProjectService;

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
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private ContractRequiredService contractRequiredService;
	
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
		Project project = projectService.selectById(ids);
		model.addAttribute("project", project);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/commonContract";
	}
	
	@RequestMapping("/createDetailContract")
	public String createDetailContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		Project project = projectService.selectById(ids);
		Map<String, Object> requMap = new HashMap<String, Object>();
		requMap.put("planNo", project.getRequieredId());
		requMap.put("isMaster", '2');
		List<PurchaseRequired> requList = purchaseRequiredService.getByMap(requMap);
		model.addAttribute("requList", requList);
		model.addAttribute("ids", ids);
		return "bss/cs/purchaseContract/detailContract";
	}
	
	@RequestMapping("/createTextContract")
	public String createTextContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		String planNos = "";
		Project project = projectService.selectById(ids);
		Map<String, Object> requMap = new HashMap<String, Object>();
		requMap.put("planNo", project.getRequieredId());
		requMap.put("isMaster", '2');
		List<PurchaseRequired> requList = purchaseRequiredService.getByMap(requMap);
		
		Map<String, Object> requMainMap = new HashMap<String, Object>();
		requMainMap.put("planNo", project.getRequieredId());
		requMainMap.put("isMaster", '1');
		List<PurchaseRequired> requMainList = purchaseRequiredService.getByMap(requMainMap);
		for(PurchaseRequired pur:requMainList){
			planNos += pur.getPlanNo()+",";
		}
		model.addAttribute("project", project);
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
			String planNos = "";
			Project project = projectService.selectById(ids);
			Map<String, Object> requMap = new HashMap<String, Object>();
			requMap.put("planNo", project.getRequieredId());
			requMap.put("isMaster", '2');
			List<PurchaseRequired> requList = purchaseRequiredService.getByMap(requMap);
			
			Map<String, Object> requMainMap = new HashMap<String, Object>();
			requMainMap.put("planNo", project.getRequieredId());
			requMainMap.put("isMaster", '1');
			List<PurchaseRequired> requMainList = purchaseRequiredService.getByMap(requMainMap);
			for(PurchaseRequired pur:requMainList){
				planNos += pur.getPlanNo()+",";
			}
			model.addAttribute("project", project);
			model.addAttribute("requList", requList);
			model.addAttribute("planNos", planNos);
			model.addAttribute("ids", ids);
			url = "bss/cs/purchaseContract/textContract";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
			purCon.setYear(new BigDecimal(sdf.format(new Date())));
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
	public String selectDraftContract(HttpServletRequest request,Model model) throws Exception{
		List<PurchaseContract> draftConList = purchaseContractService.selectDraftContract();
		model.addAttribute("draftConList", draftConList);
		return "bss/cs/purchaseContract/draftlist";
	}
	
	@RequestMapping("/createDraftContract")
	public String createDraftContract(HttpServletRequest request,Model model) throws Exception{
		String ids = request.getParameter("ids");
		PurchaseContract draftCon = purchaseContractService.selectDraftById(ids);
		List<ContractRequired> conRequList = contractRequiredService.selectConRequeByContractId(draftCon.getId());
		draftCon.setContractReList(conRequList);
		model.addAttribute("draftCon", draftCon);
		return "bss/cs/purchaseContract/draftContract";
	}
	
	@RequestMapping("/updateDraftContract")
	public String updateDraftContract(HttpServletRequest request,Model model) throws Exception{
		return "";
	}
}
