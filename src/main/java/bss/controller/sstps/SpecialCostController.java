package bss.controller.sstps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.util.ValidateUtils;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.OutsourcingCon;
import bss.model.sstps.SpecialCost;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.SpecialCostList;
import bss.service.sstps.SpecialCostService;

@Controller
@Scope
@RequestMapping("/specialCost")
public class SpecialCostController extends BaseSupplierController {
	
	@Autowired
	private SpecialCostService specialCostService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
	
	public void initSpecialCost(ContractProduct contractProduct){
		List<HashMap<String,String[]>> lists=new ArrayList<HashMap<String,String[]>>();
		HashMap<String,String[]> hashMap=null;
		hashMap=new HashMap<String, String[]>();
		hashMap.put("专项试验费", new String[]{"项目1","项目2"});
		lists.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("专用材料试验费", new String[]{"材料理化试验费","材料测试试验费","材料工艺试验费"});
		lists.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("工装设备费", new String[]{"专用工装、测试设备费","五万元以下零星仪器设备"});
		lists.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("售后服务等费用", new String[]{"油封、包装费","工具、备件、资料费","运输费","售后服务费"});
		lists.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("一次性专用费用", new String[]{"会议费","专家咨询费","图纸、资料费"});
		lists.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("其他费用", new String[]{});
		lists.add(hashMap);
		SpecialCost specialCost=null;
		int j=1;
		for(int i=0;i<lists.size();i++){
			HashMap<String, String[]> hashMap2 = lists.get(i);
			for (String key : hashMap2.keySet()) {
				String[] strings = hashMap2.get(key);
				String id=UUID.randomUUID().toString().replaceAll("-", "");
				specialCost=new SpecialCost();
				specialCost.setId(id);
				specialCost.setProjectName(key);
				specialCost.setContractProduct(contractProduct);
				specialCost.setCreatedAt(new Date());
				specialCost.setParentId("0");
				specialCost.setSerialNumber(j+"");
				specialCostService.insert(specialCost);
				if(strings.length>0){
					int k=1;
					for(String str:strings){
						specialCost=new SpecialCost();
						String ids=UUID.randomUUID().toString().replaceAll("-", "");
						specialCost.setId(ids);
						specialCost.setProductDetal(str);
						specialCost.setContractProduct(contractProduct);
						specialCost.setCreatedAt(new Date());
						specialCost.setParentId(id);
						specialCost.setSerialNumber(j+"."+k);
						specialCostService.insert(specialCost);
						k++;
					}
				}else{
					String ids=UUID.randomUUID().toString().replaceAll("-", "");
					specialCost=new SpecialCost();
					specialCost.setId(ids);
					specialCost.setProjectName(key);
					specialCost.setContractProduct(contractProduct);
					specialCost.setCreatedAt(new Date());
					specialCost.setParentId(id);
					specialCost.setSerialNumber(j+"."+1);
					specialCostService.insert(specialCost);
				}
				j++;
			}
		}
	}
	/**
	* @Title: select
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08  
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
		if(list==null||list.size()<=0){
			initSpecialCost(contractProduct);
			List<SpecialCost> lists=specialCostService.selectProduct(specialCost);
			model.addAttribute("list", lists);
		}else{
			model.addAttribute("list", list);
		}
		
		model.addAttribute("proId", proId);
		/*if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("外协部件");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
		return "bss/sstps/offer/supplier/specialCost/list";
	}
	
	/**
	* @Title: view
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08   
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
	@RequestMapping("/findAllProjectName")
	public void findAllProjectName(String proId,HttpServletResponse response){
		
		List<SpecialCost> list = specialCostService.selectProjectNameByProId(proId);
		super.writeJson(response, list);
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
		return "bss/sstps/offer/supplier/specialCost/add";
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
		SpecialCost specialCost = specialCostService.selectById(id);
		model.addAttribute("sc", specialCost);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/specialCost/edit";
	}
	public HashMap<String, Object> VerificationSave(Model model,SpecialCost specialCost){
		Boolean flg=true;
		HashMap<String, Object> hashMap=new HashMap<String, Object>();
		if(ValidateUtils.isNull(specialCost.getProjectName())){
			flg = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getProductDetal())){
			flg = false;
			model.addAttribute("ERR_productDetal", "项目明细不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getName())){
			flg = false;
			model.addAttribute("ERR_name", "名称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getNorm())){
			flg = false;
			model.addAttribute("ERR_norm", "规格型号称不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getMeasuringUnit())){
			flg = false;
			model.addAttribute("ERR_measuringUnit", "计量单位不能为空");
		}
		if(ValidateUtils.isNull(specialCost.getAmount())){
			flg = false;
			model.addAttribute("ERR_amout", "数量不能为空");
		}else{
			if(!ValidateUtils.Number(specialCost.getAmount()+"")){
				flg = false;
				model.addAttribute("ERR_amout", "数量不是数字");
			}
		}
		if(ValidateUtils.isNull(specialCost.getProportionAmout())){
			flg = false;
			model.addAttribute("ERR_proportionAmout", "分摊数量不能为空");
		}else{
			if(!ValidateUtils.Number(specialCost.getProportionAmout()+"")){
				flg = false;
				model.addAttribute("ERR_proportionAmout", "分摊数量不是数字");
			}
		}
		if(ValidateUtils.isNull(specialCost.getProportionPrice())){
			flg = false;
			model.addAttribute("ERR_proportionPrice", "分摊额不能为空");
		}else{
			if(!ValidateUtils.Money(specialCost.getProportionPrice()+"")){
				flg = false;
				model.addAttribute("ERR_proportionPrice", "分摊额输入错误");
			}
		}
		if(ValidateUtils.isNull(specialCost.getPrice())){
			flg = false;
			model.addAttribute("ERR_price", "单价不能为空");
		}else{
			if(!ValidateUtils.Money(specialCost.getPrice()+"")){
				flg = false;
				model.addAttribute("ERR_price", "单价输入错误");
			}
		}
		if(ValidateUtils.isNull(specialCost.getMoney())){
			flg = false;
			model.addAttribute("ERR_money", "金额不能为空");
		}else{
			if(!ValidateUtils.Money(specialCost.getMoney()+"")){
				flg = false;
				model.addAttribute("ERR_money", "金额输入错误");
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
	* @param @param specialCost
	* @param @return      
	* @return String
	 */
	/*@RequestMapping("/save")
	public String save(Model model,@Valid SpecialCost specialCost,BindingResult result){
		String proId = specialCost.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		HashMap<String, Object> verificationSave = VerificationSave(model, specialCost);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
		if(flag==false){
			model.addAttribute("sc", specialCost);
			url = "bss/sstps/offer/supplier/specialCost/add";
		}else{
			if(ValidateUtils.isNull(specialCost.getMoney())){
				specialCost.setMoney(0);
			}
			specialCost.setCreatedAt(new Date());
			specialCost.setUpdatedAt(new Date());
			specialCostService.insert(specialCost);
			List<SpecialCost> list = specialCostService.selectProduct(specialCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/specialCost/list";
		}
		return url;
	}*/
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listSpec, HttpServletRequest request,HttpServletResponse response){
		List<SpecialCost> listSpecialCost = listSpec.getListSpec();
		for(SpecialCost specialCost:listSpecialCost){
			if(specialCost.getProjectName()!=null||specialCost.getProductDetal()!=null){
				if(specialCost.getId()!=null){
					specialCost.setUpdatedAt(new Date());
					specialCostService.update(specialCost);
				}else{
					String id=UUID.randomUUID().toString().replaceAll("-", "");
					specialCost.setId(id);
					specialCost.setCreatedAt(new Date());
					specialCost.setUpdatedAt(new Date());
					specialCostService.insert(specialCost);
				}
			}
			
		}
		super.writeJson(response, "ok");
	}
	
	/**
	 * 
	* @Title: update
	* @author Li WanLin 
	* @date 2017-04-06 下午5:36:08  
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
		HashMap<String, Object> verificationSave = VerificationSave(model, specialCost);
	    Boolean flag=(Boolean) verificationSave.get("flg");
	    model=(Model) verificationSave.get("model");
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
		SpecialCost specialCost = null;
		ContractProduct contractProduct = new ContractProduct();
		List<SpecialCost> specialCosts=null;
		contractProduct.setId(proId);
		for(String str : id){
			specialCosts= specialCostService.selectByIdAndParentId(str);
			specialCostService.delete(str);
			if(specialCosts!=null&&specialCosts.size()==1){
				specialCost=new SpecialCost();
				String newId=UUID.randomUUID().toString().replaceAll("-", "");
				specialCost.setId(newId);
				specialCost.setParentId(specialCosts.get(0).getParentId());
				specialCost.setContractProduct(contractProduct);
				specialCost.setSerialNumber(specialCosts.get(0).getSerialNumber());
				specialCostService.insert(specialCost);
			}
		}
		specialCost=new SpecialCost();
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/specialCost/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		SpecialCost specialCost = new SpecialCost();
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/specialCost_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,SpecialCostList SpecialCostList,String productId){
		List<SpecialCost> SpecialCosts = SpecialCostList.getSpecialCosts();
		if(SpecialCosts!=null){
			for (SpecialCost specialCost : SpecialCosts) {
				specialCost.setUpdatedAt(new Date());
				specialCostService.update(specialCost);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/burningPower/userGetAll.html?productId="+productId;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		SpecialCost specialCost = new SpecialCost();
		specialCost.setContractProduct(contractProduct);
		List<SpecialCost> list = specialCostService.selectProduct(specialCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/specialCost_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,SpecialCostList SpecialCostList,String productId){
		List<SpecialCost> SpecialCosts = SpecialCostList.getSpecialCosts();
		if(SpecialCosts!=null){
			for (SpecialCost specialCost : SpecialCosts) {
				specialCost.setUpdatedAt(new Date());
				specialCostService.update(specialCost);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/burningPower/userGetAllCheck.html?productId="+productId;
	}
}
