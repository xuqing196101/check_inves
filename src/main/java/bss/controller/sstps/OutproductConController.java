package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import bss.model.sstps.OutproductCon;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.OutproductConList;
import bss.service.sstps.OutproductConService;

@Controller
@Scope
@RequestMapping("/outproductCon")
public class OutproductConController {
	
	@Autowired
	private OutproductConService outproductConService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	

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
	public String select(Model model,String proId,OutproductCon outproductCon,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("原辅材料");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}
		return "bss/sstps/offer/supplier/outproduct/list";
	}
	
	
	
	@RequestMapping("/view")
	public String view(Model model,String proId,OutproductCon outproductCon){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/list/outproduct_list";
	}
	
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outproduct/add";
	}
	
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		OutproductCon outproductCon = outproductConService.selectById(id);
		model.addAttribute("out", outproductCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outproduct/edit";
	}
	
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		OutproductCon outproductCon=new OutproductCon();
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/userAppraisal/list/outproduct_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,OutproductConList OutproductConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<OutproductCon> outproductCons = OutproductConList.getOutproductCons();
		for (OutproductCon outproductCon : outproductCons) {
			outproductCon.setUpdatedAt(new Date());
			outproductConService.update(outproductCon);
		}
		model.addAttribute("proId",proID);
		return "redirect:/outsourcingCon/userGetAll.html?productId="+proID;
	}
	
	@RequestMapping("/save")
	public String save(Model model,@Valid OutproductCon outproductCon,BindingResult result,HttpServletRequest request){
		String proId = outproductCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(outproductCon.getFinishedName())){
			flag = false;
			model.addAttribute("ERR_finishedName", "成品件名称不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(flag==false){
			model.addAttribute("out", outproductCon);
			url = "bss/sstps/offer/supplier/outproduct/add";
		}else{
			outproductCon.setCreatedAt(new Date());
			outproductCon.setUpdatedAt(new Date());
			outproductConService.insert(outproductCon);
			List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
			model.addAttribute("list", list);
			url="bss/sstps/offer/supplier/outproduct/list";
		}
		return url;
	}
	
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-13 下午5:36:08  
	* @Description:  
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid OutproductCon outproductCon,BindingResult result,HttpServletRequest request,String id){
		String proId = outproductCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		Boolean flag = true;
		String url = "";
		if(ValidateUtils.isNull(outproductCon.getFinishedName())){
			flag = false;
			model.addAttribute("ERR_finishedName", "成品件名称不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(flag==false){
			model.addAttribute("out", outproductCon);
			url = "bss/sstps/offer/supplier/outproduct/edit";
		}else{
			outproductCon.setUpdatedAt(new Date());
			outproductConService.update(outproductCon);
			List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
			model.addAttribute("list", list);
			url ="bss/sstps/offer/supplier/outproduct/list";
		}
		return url;
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
			outproductConService.delete(str);
		}
		OutproductCon outproductCon = new OutproductCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/outproduct/list";
	}
	

}
