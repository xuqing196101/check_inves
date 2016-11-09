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
import bss.model.sstps.PeriodCost;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.PeriodCostService;

/**
* @Title:PeriodCostController 
* @Description: 期间
* @author Shen Zhenfei
* @date 2016-10-18上午9:36:08
 */
@Controller
@Scope
@RequestMapping("/periodCost")
public class PeriodCostController {
	
	@Autowired
	private PeriodCostService periodCostService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
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
	public String select(Model model,String proId,PeriodCost periodCost,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		periodCost.setContractProduct(contractProduct);
		List<PeriodCost> list = periodCostService.selectProduct(periodCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("制造费用");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/periodCost/list";
	}
	
	/**
	* @Title: view
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午9:01:30  
	* @Description: 查看 
	* @param @param model
	* @param @param proId
	* @param @param periodCost
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,PeriodCost periodCost){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		periodCost.setContractProduct(contractProduct);
		List<PeriodCost> list = periodCostService.selectProduct(periodCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/periodCost_list";
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
		return "bss/sstps/offer/supplier/periodCost/add";
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
		PeriodCost periodCost = periodCostService.selectById(id);
		model.addAttribute("pc", periodCost);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/periodCost/edit";
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
	public String save(Model model,@Valid PeriodCost periodCost,BindingResult result){
		String proId = periodCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(periodCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("pc", periodCost);
			url = "bss/sstps/offer/supplier/periodCost/add";
		}else{
			periodCost.setCreatedAt(new Date());
			periodCost.setUpdatedAt(new Date());
			periodCostService.insert(periodCost);
			List<PeriodCost> list = periodCostService.selectProduct(periodCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/periodCost/list";
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
	public String update(Model model,@Valid PeriodCost periodCost,BindingResult result){
		String proId = periodCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(periodCost.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(flag==false){
			model.addAttribute("pc", periodCost);
			url = "bss/sstps/offer/supplier/periodCost/edit";
		}else{
			periodCost.setUpdatedAt(new Date());
			periodCostService.update(periodCost);
			List<PeriodCost> list = periodCostService.selectProduct(periodCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/periodCost/list";
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
			periodCostService.delete(str);
		}
		
		PeriodCost periodCost = new PeriodCost();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		periodCost.setContractProduct(contractProduct);
		
		List<PeriodCost> list = periodCostService.selectProduct(periodCost);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/periodCost/list";
	}
	
}
