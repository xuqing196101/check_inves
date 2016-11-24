package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.ValidateUtils;

import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.WagesPayable;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.WagesPayableService;

/**
* @Title:WagesPayableController 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-17上午10:55:46
 */
@Controller
@Scope
@RequestMapping("/wagesPayable")
public class WagesPayableController {
	
	@Autowired
	private WagesPayableService wagesPayableService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午10:59:11  
	* @Description: 列表展现
	* @param @param model
	* @param @param proId
	* @param @param wagesPayable
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,WagesPayable wagesPayable,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		wagesPayable.setContractProduct(contractProduct);
		List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("燃料动力");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/wagesPayable/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午8:56:07  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param wagesPayable
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,WagesPayable wagesPayable){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		wagesPayable.setContractProduct(contractProduct);
		List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/wagesPayable_list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午10:59:14  
	* @Description: 新增页面 
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/wagesPayable/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午10:59:20  
	* @Description: 修改 页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		WagesPayable wagesPayable = wagesPayableService.selectById(id);
		model.addAttribute("wp", wagesPayable);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/wagesPayable/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午11:00:05  
	* @Description: 保存
	* @param @param model
	* @param @param wagesPayable
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,@Valid WagesPayable wagesPayable,BindingResult result){
		String proId = wagesPayable.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(wagesPayable.getDepartment())){
			flag = false;
			model.addAttribute("ERR_department", "部门不能为空");
		}
		if(ValidateUtils.isNull(wagesPayable.getFirsetProduct())){
			flag = false;
			model.addAttribute("ERR_firsetProduct", "上级项目不能为空");
		}
		if(ValidateUtils.isNull(wagesPayable.getSecondProduct())){
			flag = false;
			model.addAttribute("ERR_secondProduct", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("wp", wagesPayable);
			url = "bss/sstps/offer/supplier/wagesPayable/add";
		}else{
			wagesPayable.setCreatedAt(new Date());
			wagesPayable.setUpdatedAt(new Date());
			wagesPayableService.insert(wagesPayable);
			List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/wagesPayable/list";
		}
		return url;
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午11:00:01  
	* @Description: 修改
	* @param @param model
	* @param @param wagesPayable
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid WagesPayable wagesPayable,BindingResult result){
		String proId = wagesPayable.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(wagesPayable.getDepartment())){
			flag = false;
			model.addAttribute("ERR_department", "部门不能为空");
		}
		if(ValidateUtils.isNull(wagesPayable.getFirsetProduct())){
			flag = false;
			model.addAttribute("ERR_firsetProduct", "上级项目不能为空");
		}
		if(ValidateUtils.isNull(wagesPayable.getSecondProduct())){
			flag = false;
			model.addAttribute("ERR_secondProduct", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("wp", wagesPayable);
			url = "bss/sstps/offer/supplier/wagesPayable/edit";
		}else{
			wagesPayable.setUpdatedAt(new Date());
			wagesPayableService.update(wagesPayable);
			List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
			model.addAttribute("list", list);
			model.addAttribute("proId",proId);
			url = "bss/sstps/offer/supplier/wagesPayable/list";
		}
		return url;
	}
	
	/**
	 * 
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-17 上午11:00:15  
	* @Description: 删除
	* @param @param model
	* @param @param proId
	* @param @param ids
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String proId,String ids){
		
		String[] id=ids.split(",");
		
		for(String str : id){
			wagesPayableService.delete(str);
		}
		
		WagesPayable wagesPayable = new WagesPayable();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		wagesPayable.setContractProduct(contractProduct);
		
		List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/wagesPayable/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		WagesPayable wagesPayable = new WagesPayable();
		wagesPayable.setContractProduct(contractProduct);
		List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/wagesPayable_list";
	}
	
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		WagesPayable wagesPayable = new WagesPayable();
		wagesPayable.setContractProduct(contractProduct);
		List<WagesPayable> list = wagesPayableService.selectProduct(wagesPayable);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/wagesPayable_list";
	}	
}
