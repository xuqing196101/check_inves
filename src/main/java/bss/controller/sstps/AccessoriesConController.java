package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.ContractProduct;
import bss.service.sstps.AccessoriesConService;

/**
* @Title:AccessoriesConController 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-13下午2:27:00
 */
@Controller
@Scope
@RequestMapping("/accessoriesCon")
public class AccessoriesConController {
	
	@Autowired
	private AccessoriesConService accessoriesConService;
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-13 下午3:47:08  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/accessories/add";
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
		
		AccessoriesCon accessoriesCon = accessoriesConService.selectById(id);
		
		model.addAttribute("acc", accessoriesCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/accessories/edit";
	}
	

	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-13 下午3:47:38  
	* @Description: 保存
	* @param @param model
	* @param @param accessoriesCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,AccessoriesCon accessoriesCon){
		String proId = accessoriesCon.getContractProduct().getId();
		accessoriesCon.setCreatedAt(new Date());
		accessoriesCon.setUpdatedAt(new Date());
		accessoriesConService.insert(accessoriesCon);
		
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		
		model.addAttribute("proId",proId);
		
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	
	@RequestMapping("/select")
	public String select(Model model,String proId,AccessoriesCon accessoriesCon){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-13 下午5:36:08  
	* @Description: TODO 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,AccessoriesCon accessoriesCon ){
		String proId = accessoriesCon.getContractProduct().getId();
		
		accessoriesCon.setUpdatedAt(new Date());
		accessoriesConService.update(accessoriesCon);
		
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-14 上午8:35:27  
	* @Description: 删除
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delete")
	public String delete(Model model,String proId,String ids){
		
		String[] id=ids.split(",");
		
		for(String str : id){
			accessoriesConService.delete(str);
		}
		
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	
}
