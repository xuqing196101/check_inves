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

import bss.model.sstps.AccessoriesCon;
import bss.model.sstps.AccessoriesConList;
import bss.model.sstps.ContractProduct;
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
	public String save(Model model,@Valid AccessoriesCon accessoriesCon,BindingResult result,HttpServletRequest request){
		Boolean flag = true;
		String url = "";
		String proId = accessoriesCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		if(ValidateUtils.isNull(accessoriesCon.getProductNature())){
			flag = false;
			model.addAttribute("ERR_productNature", "材料性质不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getStuffName())){
			flag = false;
			model.addAttribute("ERR_stuffName", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "规格型号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getSupplyUnit())){
			flag = false;
			model.addAttribute("ERR_supplyUnit", "供货单位不能为空");
		}
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
			List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/accessories/list";
		}
		return url;
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
	* @Description: 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid AccessoriesCon accessoriesCon,BindingResult result,HttpServletRequest request,String id){
		String proId = accessoriesCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		Boolean flag = true;
		String url = "";
		
		if(ValidateUtils.isNull(accessoriesCon.getProductNature())){
			flag = false;
			model.addAttribute("ERR_productNature", "材料性质不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getStuffName())){
			flag = false;
			model.addAttribute("ERR_stuffName", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getNorm())){
			flag = false;
			model.addAttribute("ERR_norm", "规格型号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getPaperCode())){
			flag = false;
			model.addAttribute("ERR_paperCode", "图纸位置号不能为空");
		}
		if(ValidateUtils.isNull(accessoriesCon.getSupplyUnit())){
			flag = false;
			model.addAttribute("ERR_supplyUnit", "供货单位不能为空");
		}
		if(flag==false){
			model.addAttribute("acc", accessoriesCon);
			url="bss/sstps/offer/supplier/accessories/edit";
		}else{
			accessoriesCon.setUpdatedAt(new Date());
			accessoriesConService.update(accessoriesCon);
			List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
			model.addAttribute("list", list);
			url="bss/sstps/offer/supplier/accessories/list";
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
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		AccessoriesCon accessoriesCon = new AccessoriesCon();
		accessoriesCon.setContractProduct(contractProduct);
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/userAppraisal/list/accessories_list";
	}
	
	/**
	 * 
	 * @Title: userUpdate
	 * @author Liyi 
	 * @date 2016-10-26 下午3:36:22  
	 * @Description:审价人员审减金额修改
	 * @param:     
	 * @return:
	 */
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,AccessoriesConList AccessoriesConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<AccessoriesCon> accessoriesCons = AccessoriesConList.getAccessoriesCons();
		if(accessoriesCons!=null){
			for (AccessoriesCon accessoriesCon : accessoriesCons) {
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
	 * @author Liyi 
	 * @date 2016-10-26 下午3:36:22  
	 * @Description:审价人员审减金额修改
	 * @param:     
	 * @return:
	 */
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,AccessoriesConList AccessoriesConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<AccessoriesCon> accessoriesCons = AccessoriesConList.getAccessoriesCons();
		if(accessoriesCons!=null){
			for (AccessoriesCon accessoriesCon : accessoriesCons) {
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
		List<AccessoriesCon> list = accessoriesConService.selectProduct(accessoriesCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/checkAppraisal/list/accessories_list";
	}
}
