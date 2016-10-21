package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutsourcingCon;
import bss.service.sstps.OutsourcingConService;

@Controller
@Scope
@RequestMapping("/outsourcingCon")
public class OutsourcingConController {
	
	@Autowired
	private OutsourcingConService outsourcingConService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:31  
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,OutsourcingCon outsourcingCon){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:48  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outsourcing/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:23:57  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		OutsourcingCon outsourcingCon = outsourcingConService.selectById(id);
		model.addAttribute("out", outsourcingCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outsourcing/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:15  
	* @Description: 保存
	* @param @param model
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,OutsourcingCon outsourcingCon){
		String proId = outsourcingCon.getContractProduct().getId();
		outsourcingCon.setCreatedAt(new Date());
		outsourcingCon.setUpdatedAt(new Date());
		outsourcingConService.insert(outsourcingCon);
		
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		
		model.addAttribute("proId",proId);
		
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:21  
	* @Description: 修改 
	* @param @param model
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,OutsourcingCon outsourcingCon){
		String proId = outsourcingCon.getContractProduct().getId();
		
		outsourcingCon.setUpdatedAt(new Date());
		outsourcingConService.update(outsourcingCon);
		
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午2:24:28  
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
			outsourcingConService.delete(str);
		}
		
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	

}
