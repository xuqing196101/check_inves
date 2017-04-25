package bss.controller.sstps;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import ses.controller.sys.sms.BaseSupplierController;
import ses.util.ValidateUtils;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.PeriodCost;
import bss.model.sstps.ProductQuota;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.ComCostDisService;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.ProductQuotaList;
import bss.service.sstps.ProductQuotaService;

@Controller
@Scope
@RequestMapping("/productQuota")
public class ProductQuotaController extends BaseSupplierController {
	
	@Autowired
	private ProductQuotaService productQuotaService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	@Autowired
	private ComCostDisService comCostDisService;
	
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-18 上午9:42:52  
	* @Description: 列表页面
	* @param @param model
	* @param @param proId
	* @param @param ProductQuota
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,ProductQuota productQuota,Integer total){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
//		if(total!=null){
//			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
//			comprehensiveCost.setContractProduct(contractProduct);
//			comprehensiveCost.setSingleOffer(total);
//			comprehensiveCost.setProjectName("专项试验费");
//			comprehensiveCost.setSecondProject("制造费用");
//			comprehensiveCostService.updateInfo(comprehensiveCost);
//		}
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
	/*@RequestMapping("/save")
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
			if(ValidateUtils.isNull(productQuota.getSubtotalOffer())){
				productQuota.setSubtotalOffer(0);
			}
			if(ValidateUtils.isNull(productQuota.getApprovedOffer())){
				productQuota.setApprovedOffer(0);
			}
			productQuota.setCreatedAt(new Date());
			productQuota.setUpdatedAt(new Date());
			productQuotaService.insert(productQuota);
			List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/productQuota/list";
		}
		return url;
	}*/
	
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listPro, HttpServletRequest request,HttpServletResponse response){
		List<ProductQuota> listProductQuota = listPro.getListPro();
		for(ProductQuota productQuota:listProductQuota){
			if(productQuota.getPartsName()!=null){
				if(productQuota.getId()!=null){
					productQuota.setUpdatedAt(new Date());
					productQuotaService.update(productQuota);
				}else{ 
					productQuota.setCreatedAt(new Date());
					productQuota.setUpdatedAt(new Date());
					productQuotaService.insert(productQuota);
				}
			}
		}
		super.writeJson(response, "ok");
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

	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		ProductQuota productQuota = new ProductQuota();
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/productQuota_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,TrialPriceBean listPro,String productId){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		List<ProductQuota> listProductQuota = listPro.getListPro();
		for(ProductQuota productQuota:listProductQuota){
				if(productQuota.getId()!=null){
					productQuota.setUpdatedAt(new Date());
					productQuotaService.update(productQuota);
				}
		}
		comCostDisService.appendSumApprovedComCostDis(contractProduct);
		model.addAttribute("proId",productId);
		return "redirect:/comCostDis/userGetAll.html?productId="+productId;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		ProductQuota productQuota = new ProductQuota();
		productQuota.setContractProduct(contractProduct);
		List<ProductQuota> list = productQuotaService.selectProduct(productQuota);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/productQuota_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,TrialPriceBean listPro,String productId){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		List<ProductQuota> listProductQuota = listPro.getListPro();
		for(ProductQuota productQuota:listProductQuota){
				if(productQuota.getId()!=null){
					productQuota.setUpdatedAt(new Date());
					productQuotaService.update(productQuota);
				}
		}
		comCostDisService.appendSumCheckComCostDis(contractProduct);
		model.addAttribute("proId",productId);
		return "redirect:/comCostDis/userGetAllCheck.html?productId="+productId;
	}
	
}
