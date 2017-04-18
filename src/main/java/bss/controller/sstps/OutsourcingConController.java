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
import bss.model.sstps.OutsourcingCon;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.OutsourcingConList;
import bss.service.sstps.OutsourcingConService;

@Controller
@Scope
@RequestMapping("/outsourcingCon")
public class OutsourcingConController extends BaseSupplierController {
	
	@Autowired
	private OutsourcingConService outsourcingConService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	/**
	* @Title: select
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08    
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,OutsourcingCon outsourcingCon,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		/*if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("外购成件");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	/**
	* @Title: view
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08    
	* @Description: 查看
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,OutsourcingCon outsourcingCon){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/list/outsourcing_list";
	}
	
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
		return "bss/sstps/offer/supplier/outsourcing/add";
	}
	
	/**
	* @Title: edit
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08    
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		OutsourcingCon outsourcingCon = outsourcingConService.selectById(id);
		model.addAttribute("out", outsourcingCon);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/outsourcing/edit";
	}
	public HashMap<String, Object> VerificationSave(Model model,OutsourcingCon outsourcingCon){
		Boolean flg=true;
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(ValidateUtils.isNull(outsourcingCon.getOutsourcingName())){
			flg = false;
			model.addAttribute("ERR_outsourcingName", "外协加工件名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getNorm())){
			flg = false;
			model.addAttribute("ERR_norm", "材料名称不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getPaperCode())){
			flg = false;
			model.addAttribute("ERR_paperCode", "图纸位置号(代号)不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getSupplyUnit())){
			flg = false;
			model.addAttribute("ERR_supplyUnit", "供货单位不能为空");
		}
		if(ValidateUtils.isNull(outsourcingCon.getWorkAmout())){
			flg = false;
			model.addAttribute("ERR_workAmout", "数量不能为空");
		}else{
			if(!ValidateUtils.Number(outsourcingCon.getWorkAmout()+"")){
				flg = false;
				model.addAttribute("ERR_workAmout", "数量不是数字");
			}
		}
		if(ValidateUtils.isNull(outsourcingCon.getWorkWeight())){
			flg = false;
			model.addAttribute("ERR_workWeight", "单件重不能为空");
		}else{
			if(!ValidateUtils.Money(outsourcingCon.getWorkWeight()+"")){
				flg = false;
				model.addAttribute("ERR_workWeight", "单件重输入错误");
			}
		}
		if(ValidateUtils.isNull(outsourcingCon.getWorkWeightTotal())){
			flg = false;
			model.addAttribute("ERR_workWeightTotal", "重量小计不能为空");
		}else{
			if(!ValidateUtils.Money(outsourcingCon.getWorkWeightTotal()+"")){
				flg = false;
				model.addAttribute("ERR_workWeightTotal", "重量小计输入错误");
			}
		}
		if(ValidateUtils.isNull(outsourcingCon.getWorkPrice())){
			flg = false;
			model.addAttribute("ERR_workPrice", "单价不能为空");
		}else{
			if(!ValidateUtils.Money(outsourcingCon.getWorkPrice()+"")){
				flg = false;
				model.addAttribute("ERR_workPrice", "单价输入错误");
			}
		}
		if(ValidateUtils.isNull(outsourcingCon.getWorkMoney())){
			flg = false;
			model.addAttribute("ERR_workMoney", "金额不能为空");
		}else{
			if(!ValidateUtils.Money(outsourcingCon.getWorkMoney()+"")){
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
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	/*@RequestMapping("/save")
	public String save(Model model,@Valid OutsourcingCon outsourcingCon,BindingResult result,HttpServletRequest request){
		String proId = outsourcingCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, outsourcingCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("out", outsourcingCon);
			url = "bss/sstps/offer/supplier/outsourcing/add";
		}else{
			if(ValidateUtils.isNull(outsourcingCon.getWorkMoney())){
				outsourcingCon.setWorkMoney(0);
			}
			if(ValidateUtils.isNull(outsourcingCon.getConsumeMoney())){
				outsourcingCon.setConsumeMoney(0);
			}
			outsourcingCon.setCreatedAt(new Date());
			outsourcingCon.setUpdatedAt(new Date());
			outsourcingConService.insert(outsourcingCon);
			List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/outsourcing/list";
		}
		return url;
	}*/
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listOutSou, HttpServletRequest request,HttpServletResponse response){
		List<OutsourcingCon> listOutsourcingCon = listOutSou.getListOutSou();
		for(OutsourcingCon outsourcingCon:listOutsourcingCon){
			if(outsourcingCon.getOutsourcingName()!=null){
				if(outsourcingCon.getId()!=null){
					outsourcingCon.setUpdatedAt(new Date());
					outsourcingConService.update(outsourcingCon);
				}else{
					outsourcingCon.setCreatedAt(new Date());
					outsourcingCon.setUpdatedAt(new Date());
					outsourcingConService.insert(outsourcingCon);
				}
			}
			
		}
		super.writeJson(response, "ok");
	}
	/**
	* @Title: update
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08   
	* @Description: 修改 
	* @param @param model
	* @param @param outsourcingCon
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid OutsourcingCon outsourcingCon,BindingResult result,HttpServletRequest request,String id){
		String proId = outsourcingCon.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, outsourcingCon);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("out", outsourcingCon);
			url = "bss/sstps/offer/supplier/outsourcing/edit";
			
		}else{
			outsourcingCon.setUpdatedAt(new Date());
			outsourcingConService.update(outsourcingCon);
			List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/outsourcing/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08    
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
			outsourcingConService.delete(str);
		}
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/outsourcing/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/userAppraisal/list/outsourcing_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,OutsourcingConList OutsourcingConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<OutsourcingCon> outsourcingCons = OutsourcingConList.getOutsourcingCons();
		if(outsourcingCons!=null){
			for (OutsourcingCon outsourcingCon : outsourcingCons) {
				outsourcingCon.setUpdatedAt(new Date());
				outsourcingConService.update(outsourcingCon);
			}
			
		}
		model.addAttribute("proId",proID);
		return "redirect:/specialCost/userGetAll.html?productId="+proID;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		OutsourcingCon outsourcingCon = new OutsourcingCon();
		outsourcingCon.setContractProduct(contractProduct);
		List<OutsourcingCon> list = outsourcingConService.selectProduct(outsourcingCon);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		
		return "bss/sstps/offer/checkAppraisal/list/outsourcing_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,OutsourcingConList OutsourcingConList,HttpServletRequest request){
		String proID = request.getParameter("productId");
		List<OutsourcingCon> outsourcingCons = OutsourcingConList.getOutsourcingCons();
		if(outsourcingCons!=null){
			for (OutsourcingCon outsourcingCon : outsourcingCons) {
				outsourcingCon.setUpdatedAt(new Date());
				outsourcingConService.update(outsourcingCon);
			}
		}
		model.addAttribute("proId",proID);
		return "redirect:/specialCost/userGetAllCheck.html?productId="+proID;
	}
}
