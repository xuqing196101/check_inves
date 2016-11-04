package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ses.util.ValidateUtils;

import bss.model.sstps.ContractProduct;
import bss.model.sstps.ProductQuota;
import bss.service.sstps.ProductQuotaService;

@Controller
@Scope
@RequestMapping("/productQuota")
public class ProductQuotaController {
	
	@Autowired
	private ProductQuotaService productQuotaService;
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:42:52  
	* @Description: 列表页面
	* @param @param model
	* @param @param proId
	* @param @param periodCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,ProductQuota productQuota){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/productQuota/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午9:05:26  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param productQuota
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,ProductQuota productQuota){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/productQuota_list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:43:04  
	* @Description: 新增页面 
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/productQuota/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:43:12  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		ProductQuota productQuota = productQuotaService.selectById(id);
		model.addAttribute("pq", productQuota);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/productQuota/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:43:24  
	* @Description: 保存
	* @param @param model
	* @param @param periodCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,@Valid ProductQuota productQuota,BindingResult result){
		String proId = productQuota.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(productQuota.getPartsName())){
			flag = false;
			model.addAttribute("ERR_partsName", "零件部件名称不能为空");
		}
		if(ValidateUtils.isNull(productQuota.getPartsDrawingCode())){
			flag = false;
			model.addAttribute("ERR_partsDrawingCode", "零件部件图号不能为空");
		}
		if(ValidateUtils.isNull(productQuota.getProcessName())){
			flag = false;
			model.addAttribute("ERR_processName", "工序名称不能为空");
		}
		if(flag==false){
			model.addAttribute("pq", productQuota);
			url = "bss/sstps/offer/supplier/productQuota/add";
		}else{
			productQuota.setCreatedAt(new Date());
			productQuota.setUpdatedAt(new Date());
			productQuotaService.insert(productQuota);
			List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/productQuota/list";
		}
		return url;
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:43:31  
	* @Description: 修改
	* @param @param model
	* @param @param periodCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid ProductQuota productQuota,BindingResult result){
		String proId = productQuota.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(productQuota.getPartsName())){
			flag = false;
			model.addAttribute("ERR_partsName", "零件部件名称不能为空");
		}
		if(ValidateUtils.isNull(productQuota.getPartsDrawingCode())){
			flag = false;
			model.addAttribute("ERR_partsDrawingCode", "零件部件图号不能为空");
		}
		if(ValidateUtils.isNull(productQuota.getProcessName())){
			flag = false;
			model.addAttribute("ERR_processName", "工序名称不能为空");
		}
		if(flag==false){
			model.addAttribute("pq", productQuota);
			url = "bss/sstps/offer/supplier/productQuota/edit";
		}else{
			productQuota.setUpdatedAt(new Date());
			productQuotaService.update(productQuota);
			List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/productQuota/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:43:41  
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
			productQuotaService.delete(str);
		}
		ProductQuota productQuota = new ProductQuota();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/productQuota/list";
	}

}
