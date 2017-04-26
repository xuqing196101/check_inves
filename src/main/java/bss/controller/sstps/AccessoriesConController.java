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
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.AccessoriesConList;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.AccessoriesConService;
import bss.service.sstps.ComprehensiveCostService;

/**
* @Title:AccessoriesConController 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-13下午2:27:00
 */
@Controller
@Scope
@RequestMapping("/accessoriesCon")
public class AccessoriesConController extends BaseSupplierController {
	
	@Autowired
	private AccessoriesConService accessoriesConService;
	
	
	
	
	/**
	* @Title: add
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08   
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
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08  
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
	
	public HashMap<String, Object> VerificationSave(Model model,AccessoriesCon accessoriesCon){
		Boolean flg=true;
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(ValidateUtils.isNull(accessoriesCon.getProductNature())){
			flg = false;
			model.addAttribute("ERR_productNature", "材料性质不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getStuffName())){
			flg = false;
			model.addAttribute("ERR_stuffName", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getNorm())){
			flg = false;
			model.addAttribute("ERR_norm", "规格型号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getPaperCode())){
			flg = false;
			model.addAttribute("ERR_paperCode", "图纸位置号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getSupplyUnit())){
			flg = false;
			model.addAttribute("ERR_supplyUnit", "供货单位不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getWorkAmout())){
			flg = false;
			model.addAttribute("ERR_workAmout", "数量不能为空");
		}else{
			if(!ValidateUtils.Number(accessoriesCon.getWorkAmout()+"")){
				flg = false;
				model.addAttribute("ERR_workAmout", "数量不是数字");
			}
		}
		if(ValidateUtils.isNull(accessoriesCon.getWorkWeight())){
			flg = false;
			model.addAttribute("ERR_workWeight", "单件重不能为空");
		}else{
			if(!ValidateUtils.Money(accessoriesCon.getWorkWeight()+"")){
				flg = false;
				model.addAttribute("ERR_workWeight", "单件重输入错误");
			}
		}
		if(ValidateUtils.isNull(accessoriesCon.getWorkWeightTotal())){
			flg = false;
			model.addAttribute("ERR_workWeightTotal", "重量小计不能为空");
		}else{
			if(!ValidateUtils.Money(accessoriesCon.getWorkWeightTotal()+"")){
				flg = false;
				model.addAttribute("ERR_workWeightTotal", "重量小计输入错误");
			}
		}
		if(ValidateUtils.isNull(accessoriesCon.getWorkPrice())){
			flg = false;
			model.addAttribute("ERR_workPrice", "单价不能为空");
		}else{
			if(!ValidateUtils.Money(accessoriesCon.getWorkPrice()+"")){
				flg = false;
				model.addAttribute("ERR_workPrice", "单价输入错误");
			}
		}
		if(ValidateUtils.isNull(accessoriesCon.getWorkMoney())){
			flg = false;
			model.addAttribute("ERR_workMoney", "金额不能为空");
		}else{
			if(!ValidateUtils.Money(accessoriesCon.getWorkMoney()+"")){
				flg = false;
				model.addAttribute("ERR_workMoney", "金额输入错误");
			}
		}
		hashMap.put("model", model);
		hashMap.put("flg", flg);
		return hashMap;
		
	}
	/**
	* @Title: save
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08    
	* @Description: 保存
	* @param @param model
	* @param @param accessoriesCon
	* @param @return      
	* @return String
	 */
	/*@RequestMapping("/save")
	public String save(Model model,@Valid AccessoriesCon accessoriesCon,BindingResult result,HttpServletRequest request){
		
		String url = "";
		String proId = accessoriesCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		HashMap<String, Object> verificationSave = VerificationSave(model, accessoriesCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("acc", accessoriesCon);
			url="bss/sstps/offer/supplier/accessories/add";
		}else{
			if(ValidateUtils.isNull(accessoriesCon.getWorkMoney())){
				accessoriesCon.setWorkMoney(0);
			}
			if(ValidateUtils.isNull(accessoriesCon.getConsumeMoney())){
				accessoriesCon.setConsumeMoney(0);
			}
			accessoriesCon.setCreatedAt(new Date());
			accessoriesCon.setUpdatedAt(new Date());
			accessoriesConService.insert(accessoriesCon);
			accessoriesCon.setProductNature(0);
			List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list0", list);
			accessoriesCon.setProductNature(1);
			List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list1", list1);
			url = "bss/sstps/offer/supplier/accessories/list";
		}
		return url;
	}
	*/
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listAcc, HttpServletRequest request,HttpServletResponse response){
		List<AccessoriesCon> listAccessoriesCon = listAcc.getListAcc();
		for(AccessoriesCon accessoriesCon:listAccessoriesCon){
			if(accessoriesCon.getProductNature()!=null){
				if(accessoriesCon.getId()!=null){
					accessoriesCon.setUpdatedAt(new Date());
					accessoriesConService.update(accessoriesCon);
				}else{
					accessoriesCon.setCreatedAt(new Date());
					accessoriesCon.setUpdatedAt(new Date());
					accessoriesConService.insert(accessoriesCon);
				}
			}
			
		}
		super.writeJson(response, "ok");
	}
	@RequestMapping("/select")
	public String select(Model model,String proId,AccessoriesCon accessoriesCon){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		accessoriesCon.setProductNature(0);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list0", list);
		accessoriesCon.setProductNature(1);
		List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list1", list1);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/accessories/list";
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
	public String update(Model model,@Valid AccessoriesCon accessoriesCon,BindingResult result,HttpServletRequest request,String id){
		String proId = accessoriesCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, accessoriesCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("acc", accessoriesCon);
			url="bss/sstps/offer/supplier/accessories/edit";
		}else{
			accessoriesCon.setUpdatedAt(new Date());
			accessoriesConService.update(accessoriesCon);
			accessoriesCon.setProductNature(0);
			List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list0", list);
			accessoriesCon.setProductNature(1);
			List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list1", list1);
			url="bss/sstps/offer/supplier/accessories/list";
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
			accessoriesConService.delete(str);
		}
		
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		accessoriesCon.setContractProduct(contractProduct);
		accessoriesCon.setProductNature(0);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list0", list);
		accessoriesCon.setProductNature(1);
		List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list1", list1);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/accessories/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		accessoriesCon.setContractProduct(contractProduct);
		accessoriesCon.setProductNature(0);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list0", list);
		accessoriesCon.setProductNature(1);
		List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list1", list1);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/userAppraisal/list/accessories_list";
	}
	
	/**
	 * 
	 * @Title: userUpdate
	 * @author Li WanLin 
	 * @date 2017-04-06 下午5:36:08  
	 * @Description:审价人员审减金额修改
	 * @param:     
	 * @return:
	 */
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,TrialPriceBean listAcc,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<AccessoriesCon> listAccessoriesCon = listAcc.getListAcc();
		for(AccessoriesCon accessoriesCon:listAccessoriesCon){
				if(accessoriesCon.getId()!=null){
					accessoriesCon.setUpdatedAt(new Date());
					accessoriesConService.update(accessoriesCon);
				}
		}
		model.addAttribute("proId",proID);
		return "redirect:/outproductCon/userGetAll.html?productId="+proID;
	}
	
	/**
	 * 
	 * @Title: userUpdateCheck
	 * @author Li WanLin 
	 * @date 2017-04-06 下午5:36:08  
	 * @Description:审价人员审减金额修改
	 * @param:     
	 * @return:
	 */
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,TrialPriceBean listAcc,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<AccessoriesCon> listAccessoriesCon = listAcc.getListAcc();
		for(AccessoriesCon accessoriesCon:listAccessoriesCon){
				if(accessoriesCon.getId()!=null){
					accessoriesCon.setUpdatedAt(new Date());
					accessoriesConService.update(accessoriesCon);
				}
		}
		model.addAttribute("proId",proID);
		return "redirect:/outproductCon/userGetAllCheck.html?productId="+proID;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		accessoriesCon.setContractProduct(contractProduct);
		accessoriesCon.setProductNature(0);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list0", list);
		accessoriesCon.setProductNature(1);
		List<AccessoriesCon> list1 = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list1", list1);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/checkAppraisal/list/accessories_list";
	}
}
