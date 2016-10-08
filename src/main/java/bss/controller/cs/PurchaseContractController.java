package bss.controller.cs;

import iss.model.ps.ArticleType;

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
	public String selectAllPurchaseContract(Integer page,Model model,Project project) throws Exception{
		List<Project> projectList = projectService.selectSuccessProject(page==null?1:page,project);
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
		return "bss/cs/purchaseContract/textContract";
	}

	@RequestMapping("/addPurchaseContract")
	public String addPurchaseContract(@Valid PurchaseContract purCon,BindingResult result,ProList proList,HttpServletRequest request,Model model){
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
		}
		purCon.setStatus(1);
		purchaseContractService.insertSelective(purCon);
		String id = purCon.getId();
		List<ContractRequired> requList = proList.getProList();
		for(ContractRequired conRequ:requList){
			conRequ.setContractId(id);
			contractRequiredService.insertSelective(conRequ);
		}
		return "redirect:selectAllPuCon.html";
	}
}
