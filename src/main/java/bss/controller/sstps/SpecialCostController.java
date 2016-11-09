package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.util.ValidateUtils;

import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.SpecialCost;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.SpecialCostService;

@Controller
@Scope
@RequestMapping("/specialCost")
public class SpecialCostController {
	
	@Autowired
	private SpecialCostService specialCostService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:34:36  
	* @Description: 列表查询
	* @param @param model
	* @param @param proId
	* @param @param specialCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,SpecialCost specialCost,Integer total){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("外协部件");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/specialCost/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-22 下午2:50:47  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param specialCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,SpecialCost specialCost){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/specialCost_list";
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
		return "bss/sstps/offer/supplier/specialCost/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:35:25  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		SpecialCost specialCost = specialCostService.selectById(id);
		model.addAttribute("sc", specialCost);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/specialCost/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:35:40  
	* @Description: 保存
	* @param @param model
	* @param @param specialCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/save")
	public String save(Model model,@Valid SpecialCost specialCost,BindingResult result){
		String proId = specialCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(specialCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getProductDetal())){
			flag = false;
			model.addAttribute("ERR_productDetal", "项目明细不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getName())){
			flag = false;
			model.addAttribute("ERR_name", "名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "规格型号称不能为空");
		}
		if(flag==false){
			model.addAttribute("sc", specialCost);
			url = "bss/sstps/offer/supplier/specialCost/add";
		}else{
			specialCost.setCreatedAt(new Date());
			specialCost.setUpdatedAt(new Date());
			specialCostService.insert(specialCost);
			List<SpecialCost> list = specialCostService.selectProduct(specialCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/specialCost/list";
		}
		return url;
	}
	
	/**
	 * 
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:35:47  
	* @Description: 修改
	* @param @param model
	* @param @param specialCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid SpecialCost specialCost,BindingResult result){
		String proId = specialCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(specialCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getProductDetal())){
			flag = false;
			model.addAttribute("ERR_productDetal", "项目明细不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getName())){
			flag = false;
			model.addAttribute("ERR_name", "名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "规格型号称不能为空");
		}
		if(flag==false){
			model.addAttribute("sc", specialCost);
			url = "bss/sstps/offer/supplier/specialCost/edit";
		}else{
			specialCost.setUpdatedAt(new Date());
			specialCostService.update(specialCost);
			List<SpecialCost> list = specialCostService.selectProduct(specialCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/specialCost/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-14 下午3:35:54  
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
			specialCostService.delete(str);
		}
		SpecialCost specialCost = new SpecialCost();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/specialCost/list";
	}
	

}
