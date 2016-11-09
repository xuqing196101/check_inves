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

import bss.model.sstps.BurningPower;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.service.sstps.BurningPowerService;
import bss.service.sstps.ComprehensiveCostService;

@Controller
@Scope
@RequestMapping("/burningPower")
public class BurningPowerController {
	
	@Autowired
	private BurningPowerService burningPowerService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
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
	public String select(Model model,String proId,BurningPower burningPower,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		burningPower.setContractProduct(contractProduct);
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("专用费用");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/burningPower/list";
	}
	
	
	@RequestMapping("/view")
	public String view(Model model,String proId,BurningPower burningPower){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		burningPower.setContractProduct(contractProduct);
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/burningPower_list";
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
	public String save(Model model,@Valid BurningPower burningPower,BindingResult result){
		String proId = burningPower.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(burningPower.getFirsetProduct())){
			flag = false;
			model.addAttribute("ERR_firsetProduct", "一级项目不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getSecondProduct())){
			flag = false;
			model.addAttribute("ERR_secondProduct", "二级项目不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getThirdProduct())){
			flag = false;
			model.addAttribute("ERR_thirdProduct", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getUnit())){
			flag = false;
			model.addAttribute("ERR_unit", "计量单位不能为空");
		}
		if(flag==false){
			model.addAttribute("burningPower", burningPower);
			url = "bss/sstps/offer/supplier/burningPower/add";
		}else{
			burningPower.setCreatedAt(new Date());
			burningPower.setUpdatedAt(new Date());
			burningPowerService.insert(burningPower);
			List<BurningPower> list = burningPowerService.selectProduct(burningPower);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/burningPower/list";
		}
		return url;
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
	public String update(Model model,@Valid BurningPower burningPower,BindingResult result){
		String proId = burningPower.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(burningPower.getFirsetProduct())){
			flag = false;
			model.addAttribute("ERR_firsetProduct", "一级项目不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getSecondProduct())){
			flag = false;
			model.addAttribute("ERR_secondProduct", "二级项目不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getThirdProduct())){
			flag = false;
			model.addAttribute("ERR_thirdProduct", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(burningPower.getUnit())){
			flag = false;
			model.addAttribute("ERR_unit", "计量单位不能为空");
		}
		if(flag==false){
			model.addAttribute("burningPower", burningPower);
			url = "bss/sstps/offer/supplier/burningPower/edit";
		}else{
			burningPower.setUpdatedAt(new Date());
			burningPowerService.update(burningPower);
			List<BurningPower> list = burningPowerService.selectProduct(burningPower);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/burningPower/list";
		}
		return url;
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
