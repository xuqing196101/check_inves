package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.BurningPower;
import bss.model.sstps.ContractProduct;
import bss.service.sstps.BurningPowerService;

@Controller
@Scope
@RequestMapping("/burningPower")
public class BurningPowerController {
	
	@Autowired
	private BurningPowerService burningPowerService;
	
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午4:51:52  
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param burningPower
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,BurningPower burningPower){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		burningPower.setContractProduct(contractProduct);
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/burningPower/list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:35:09  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/burningPower/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午4:52:01  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		BurningPower burningPower = burningPowerService.selectById(id);
		model.addAttribute("burningPower", burningPower);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/burningPower/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午4:52:09  
	* @Description: 保存
	* @param @param model
	* @param @param burningPower
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,BurningPower burningPower){
		String proId = burningPower.getContractProduct().getId();
		burningPower.setCreatedAt(new Date());
		burningPower.setUpdatedAt(new Date());
		burningPowerService.insert(burningPower);
		
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		
		model.addAttribute("proId",proId);
		
		return "bss/sstps/offer/supplier/burningPower/list";
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午4:52:31  
	* @Description: 修改
	* @param @param model
	* @param @param burningPower
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,BurningPower burningPower){
		String proId = burningPower.getContractProduct().getId();
		
		burningPower.setUpdatedAt(new Date());
		burningPowerService.update(burningPower);
		
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/burningPower/list";
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午4:52:39  
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
			burningPowerService.delete(str);
		}
		
		BurningPower burningPower = new BurningPower();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		burningPower.setContractProduct(contractProduct);
		
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/burningPower/list";
	}

}
