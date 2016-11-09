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
import bss.model.sstps.ManufacturingCost;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.ManufacturingCostList;
import bss.service.sstps.ManufacturingCostService;

@Controller
@Scope
@RequestMapping("/manufacturingCost")
public class ManufacturingCostController {

	@Autowired
	private ManufacturingCostService manufacturingCostService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:49:48  
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param wagesPayable
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,ManufacturingCost manufacturingCost,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		manufacturingCost.setContractProduct(contractProduct);
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("直接人工");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/manufacturingCost/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午8:59:25  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param manufacturingCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,ManufacturingCost manufacturingCost){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		manufacturingCost.setContractProduct(contractProduct);
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/manufacturingCost_list";
	}
	
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:52:25  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/manufacturingCost/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:52:37  
	* @Description:	修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		ManufacturingCost manufacturingCost = manufacturingCostService.selectById(id);
		model.addAttribute("mc", manufacturingCost);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/manufacturingCost/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:52:47  
	* @Description: 保存
	* @param @param model
	* @param @param manufacturingCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,@Valid ManufacturingCost manufacturingCost,BindingResult result){
		String proId = manufacturingCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(manufacturingCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("mc", manufacturingCost);
			url = "bss/sstps/offer/supplier/manufacturingCost/add";
		}else{
			manufacturingCost.setCreatedAt(new Date());
			manufacturingCost.setUpdatedAt(new Date());
			manufacturingCostService.insert(manufacturingCost);
			List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/manufacturingCost/list";
		}
		return url;
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:52:55  
	* @Description: 修改
	* @param @param model
	* @param @param manufacturingCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid ManufacturingCost manufacturingCost,BindingResult result){
		String proId = manufacturingCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(manufacturingCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("mc", manufacturingCost);
			url = "bss/sstps/offer/supplier/manufacturingCost/edit";
		}else{
			manufacturingCost.setUpdatedAt(new Date());
			manufacturingCostService.update(manufacturingCost);
			List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/manufacturingCost/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午2:53:01  
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
			manufacturingCostService.delete(str);
		}
		
		ManufacturingCost manufacturingCost = new ManufacturingCost();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		manufacturingCost.setContractProduct(contractProduct);
		
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/manufacturingCost/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		ManufacturingCost manufacturingCost = new ManufacturingCost();
		manufacturingCost.setContractProduct(contractProduct);
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/manufacturingCost_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,ManufacturingCostList ManufacturingCostList,String productId){
		List<ManufacturingCost> ManufacturingCosts = ManufacturingCostList.getManufacturingCosts();
		for (ManufacturingCost manufacturingCost : ManufacturingCosts) {
			manufacturingCost.setUpdatedAt(new Date());
			manufacturingCostService.update(manufacturingCost);
		}
		model.addAttribute("proId",productId);
		return "redirect:/periodCost/userGetAll.html?productId="+productId;
	}
	
	
}
