package bss.controller.sstps;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutproductCon;
import bss.service.sstps.OutproductConService;

@Controller
@Scope
@RequestMapping("/outproductCon")
public class OutproductConController {
	
	@Autowired
	private OutproductConService outproductConService;

	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-14 上午10:27:31  
	* @Description: 列表查询
	* @param @param model
	* @param @param proId
	* @param @param outproductCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,OutproductCon outproductCon){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		
		return "bss/sstps/offer/supplier/outproduct_Con";
	}
	
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outproduct/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-13 下午3:47:21  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		
		OutproductCon outproductCon = outproductConService.selectById(id);
		
		model.addAttribute("acc", outproductCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outproduct/edit";
	}
	
	
	
	

}
