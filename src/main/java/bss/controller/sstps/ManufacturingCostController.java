package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.ContractProduct;
import bss.model.sstps.ManufacturingCost;
import bss.service.sstps.ManufacturingCostService;

@Controller
@Scope
@RequestMapping("/manufacturingCost")
public class ManufacturingCostController {

	@Autowired
	private ManufacturingCostService manufacturingCostService;
	
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
	public String select(Model model,String proId,ManufacturingCost manufacturingCost){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		manufacturingCost.setContractProduct(contractProduct);
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/manufacturingCost/list";
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
	public String save(Model model,ManufacturingCost manufacturingCost){
		String proId = manufacturingCost.getContractProduct().getId();
		manufacturingCost.setCreatedAt(new Date());
		manufacturingCost.setUpdatedAt(new Date());
		manufacturingCostService.insert(manufacturingCost);
		
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		
		model.addAttribute("proId",proId);
		
		return "bss/sstps/offer/supplier/manufacturingCost/list";
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
	public String update(Model model,ManufacturingCost manufacturingCost){
		String proId = manufacturingCost.getContractProduct().getId();
		
		manufacturingCost.setUpdatedAt(new Date());
		manufacturingCostService.update(manufacturingCost);
		
		List<ManufacturingCost> list = manufacturingCostService.selectProduct(manufacturingCost);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/manufacturingCost/list";
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
	
	
}
