package bss.controller.sstps;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.util.ValidateUtils;
import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutproductCon;
import bss.model.sstps.ProductInfo;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.OutproductConList;
import bss.service.sstps.OutproductConService;

@Controller
@Scope
@RequestMapping("/outproductCon")
public class OutproductConController extends BaseSupplierController {
	
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
		/*if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("原辅材料");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
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
	
	
	public HashMap<String, Object> VerificationSave(Model model,OutproductCon outproductCon){
		Boolean flg=true;
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(ValidateUtils.isNull(outproductCon.getFinishedName())){
			flg = false;
			model.addAttribute("ERR_finishedName", "成品件名称不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getNorm())){
			flg = false;
			model.addAttribute("ERR_norm", "规格型号不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getSupplyUnit())){
			flg = false;
			model.addAttribute("ERR_supplyUnit", "供货单位不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getPaperCode())){
			flg = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(ValidateUtils.isNull(outproductCon.getWorkAmout())){
			flg = false;
			model.addAttribute("ERR_workAmout", "数量不能为空");
		}else{
			if(!ValidateUtils.Number(outproductCon.getWorkAmout()+"")){
				flg = false;
				model.addAttribute("ERR_workAmout", "数量不是数字");
			}
		}
		if(ValidateUtils.isNull(outproductCon.getWorkWeight())){
			flg = false;
			model.addAttribute("ERR_workWeight", "单件重不能为空");
		}else{
			if(!ValidateUtils.Money(outproductCon.getWorkWeight()+"")){
				flg = false;
				model.addAttribute("ERR_workWeight", "单件重输入错误");
			}
		}
		if(ValidateUtils.isNull(outproductCon.getWorkWeightTotal())){
			flg = false;
			model.addAttribute("ERR_workWeightTotal", "重量小计不能为空");
		}else{
			if(!ValidateUtils.Money(outproductCon.getWorkWeightTotal()+"")){
				flg = false;
				model.addAttribute("ERR_workWeightTotal", "重量小计输入错误");
			}
		}
		if(ValidateUtils.isNull(outproductCon.getWorkPrice())){
			flg = false;
			model.addAttribute("ERR_workPrice", "单价不能为空");
		}else{
			if(!ValidateUtils.Money(outproductCon.getWorkPrice()+"")){
				flg = false;
				model.addAttribute("ERR_workPrice", "单价输入错误");
			}
		}
		if(ValidateUtils.isNull(outproductCon.getWorkMoney())){
			flg = false;
			model.addAttribute("ERR_workMoney", "金额不能为空");
		}else{
			if(!ValidateUtils.Money(outproductCon.getWorkMoney()+"")){
				flg = false;
				model.addAttribute("ERR_workMoney", "金额输入错误");
			}
		}
		hashMap.put("model", model);
		hashMap.put("flg", flg);
		return hashMap;
	}
	/*@RequestMapping("/save")
	public String save(Model model,@Valid OutproductCon outproductCon,BindingResult result,HttpServletRequest request){
		String proId = outproductCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, outproductCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("out", outproductCon);
			url = "bss/sstps/offer/supplier/outproduct/add";
		}else{
			if(ValidateUtils.isNull(outproductCon.getWorkMoney())){
				outproductCon.setWorkMoney(0);
			}
			if(ValidateUtils.isNull(outproductCon.getConsumeMoney())){
				outproductCon.setConsumeMoney(0);
			}
			outproductCon.setCreatedAt(new Date());
			outproductCon.setUpdatedAt(new Date());
			outproductConService.insert(outproductCon);
			List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
			model.addAttribute("list", list);
			url="bss/sstps/offer/supplier/outproduct/list";
		}
		return url;
	}*/
	
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listOutPro, HttpServletRequest request,HttpServletResponse response){
		List<OutproductCon> listOutproductCon=listOutPro.getListOutPro();
		for(OutproductCon outproductCon:listOutproductCon){
			if(outproductCon.getFinishedName()!=null){
				if(outproductCon.getId()!=null){
					outproductCon.setUpdatedAt(new Date());
					outproductConService.update(outproductCon);
				}else{
					outproductCon.setCreatedAt(new Date());
					outproductCon.setUpdatedAt(new Date());
					outproductConService.insert(outproductCon);
				}
			}
			
		}
		super.writeJson(response, "ok");
	}
	/**
	* @Title: update
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08  
	* @Description:  
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid OutproductCon outproductCon,BindingResult result,HttpServletRequest request,String id){
		String proId = outproductCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, outproductCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
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
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08   
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
		if(outproductCons!=null){
			for (OutproductCon outproductCon : outproductCons) {
				outproductCon.setUpdatedAt(new Date());
				outproductConService.update(outproductCon);
			}
		}
		model.addAttribute("proId",proID);
		return "redirect:/outsourcingCon/userGetAll.html?productId="+proID;
	}
	
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		OutproductCon outproductCon=new OutproductCon();
		outproductCon.setContractProduct(contractProduct);
		List<OutproductCon> list = outproductConService.selectProduct(outproductCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/checkAppraisal/list/outproduct_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,OutproductConList OutproductConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<OutproductCon> outproductCons = OutproductConList.getOutproductCons();
		if(outproductCons!=null){	
			for (OutproductCon outproductCon : outproductCons) {
				outproductCon.setUpdatedAt(new Date());
				outproductConService.update(outproductCon);
			}
		}
		model.addAttribute("proId",proID);
		return "redirect:/outsourcingCon/userGetAllCheck.html?productId="+proID;
	}

}
