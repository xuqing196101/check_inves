package bss.controller.sstps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.TreeMap;
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
import bss.echarts.Data;
import bss.model.sstps.BurningPower;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.BurningPowerList;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.TrialPriceBean;
import bss.service.sstps.BurningPowerService;
import bss.service.sstps.ComprehensiveCostService;

@Controller
@Scope
@RequestMapping("/burningPower")
public class BurningPowerController extends BaseSupplierController {
	
	@Autowired
	private BurningPowerService burningPowerService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
	
	public void initBurningPower(ContractProduct contractProduct){
		Hashtable<String, Hashtable<String, String[]>> map1=null;
		Hashtable<String, String[]> map2=null;
		List<Hashtable<String, Hashtable<String,String[]>>> hashtables=new ArrayList<Hashtable<String,Hashtable<String,String[]>>>();
		map2=new Hashtable<String, String[]>();
		map1=new Hashtable<String, Hashtable<String,String[]>>();
		map2.put("外购动力开支", new String[]{"电","水","煤","油","气"});
		map2.put("自制动力开支", new String[]{});
		map1.put("燃料动力费", map2);
		hashtables.add(map1);
		map2=new Hashtable<String, String[]>();
		map1=new Hashtable<String, Hashtable<String,String[]>>();
		map2.put("计入基本生产成本", new String[]{});
		map2.put("计入制造费用", new String[]{});
		map2.put("计入管理费用", new String[]{});
		map2.put("计入其他", new String[]{});
		map1.put("燃动费分配", map2);
		hashtables.add(map1);
		BurningPower burningPower=null;
		int i=1;
		for(int h=0;h<hashtables.size();h++){
			Hashtable<String, Hashtable<String, String[]>> hashtable = hashtables.get(h);
		 for (String key : hashtable.keySet()) {
			String parentId=UUID.randomUUID().toString().replaceAll("-", "");
			burningPower=new BurningPower();
			burningPower.setId(parentId);
			burningPower.setParentId("0");
			burningPower.setFirsetProduct(key);
			burningPower.setContractProduct(contractProduct);
			burningPower.setParentLevel(1);
			burningPower.setCreatedAt(new Date());
			burningPower.setSerialNumber(""+i);
			burningPowerService.insert(burningPower);
			Hashtable<String, String[]> hashMap = hashtable.get(key);
			if(hashMap!=null){
				int j=1;
				for (String keys : hashMap.keySet()) {
					String[] strings = hashMap.get(keys);
					String parentIds=UUID.randomUUID().toString().replaceAll("-", "");
					burningPower=new BurningPower();
					burningPower.setId(parentIds);
					burningPower.setParentId(parentId);
					burningPower.setFirsetProduct(keys);
					burningPower.setContractProduct(contractProduct);
					burningPower.setParentLevel(2);
					burningPower.setCreatedAt(new Date());
					burningPower.setSerialNumber(i+"."+j);
					burningPowerService.insert(burningPower);
					if(strings!=null){
						int k=1;
						for(String str:strings){
							String parentIdDetal=UUID.randomUUID().toString().replaceAll("-", "");
							burningPower=new BurningPower();
							burningPower.setId(parentIdDetal);
							burningPower.setParentId(parentIds);
							burningPower.setFirsetProduct(str);
							burningPower.setContractProduct(contractProduct);
							burningPower.setParentLevel(3);
							burningPower.setCreatedAt(new Date());
							burningPower.setSerialNumber(i+"."+j+"."+k);
							burningPowerService.insert(burningPower);
							k++;
						}
					}
					j++;
				}
			}
			i++;
		}
		}
		
	}
	
	
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
		if(list==null||list.size()<=0){
			initBurningPower(contractProduct);
			List<BurningPower> lists = burningPowerService.selectProduct(burningPower);
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
			comprehensiveCost.setSecondProject("专用费用");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
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
	/*@RequestMapping("/save")
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
			if(ValidateUtils.isNull(burningPower.getTyaMoney())){
				burningPower.setTyaMoney(0);
			}
			if(ValidateUtils.isNull(burningPower.getOyaMoney())){
				burningPower.setOyaMoney(0);
			}
			if(ValidateUtils.isNull(burningPower.getNewMoney())){
				burningPower.setNewMoney(0);
			}
			burningPower.setCreatedAt(new Date());
			burningPower.setUpdatedAt(new Date());
			burningPowerService.insert(burningPower);
			List<BurningPower> list = burningPowerService.selectProduct(burningPower);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/burningPower/list";
		}
		return url;
	}*/
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listBurn, HttpServletRequest request,HttpServletResponse response){
		List<BurningPower> listBurningPower = listBurn.getListBurn();
		for(BurningPower burningPower:listBurningPower){
			if(burningPower.getFirsetProduct()!=null){
				if(burningPower.getId()!=null){
					burningPower.setUpdatedAt(new Date());
					burningPowerService.update(burningPower);
				}else{
					String id=UUID.randomUUID().toString().replaceAll("-", "");
					burningPower.setId(id);
					burningPower.setCreatedAt(new Date());
					burningPower.setUpdatedAt(new Date());
					burningPowerService.insert(burningPower);
				}
			}
		}
		super.writeJson(response, "ok");
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

	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		BurningPower burningPower = new BurningPower();
		burningPower.setContractProduct(contractProduct);
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/burningPower_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,BurningPowerList BurningPowerList,String productId){
		List<BurningPower> BurningPowers = BurningPowerList.getBurningPowers();
		if(BurningPowers!=null){
			for (BurningPower burningPower : BurningPowers) {
				burningPower.setUpdatedAt(new Date());
				burningPowerService.update(burningPower);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/wagesPayable/userGetAll.html?productId="+productId;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		BurningPower burningPower = new BurningPower();
		burningPower.setContractProduct(contractProduct);
		List<BurningPower> list = burningPowerService.selectProduct(burningPower);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/burningPower_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,BurningPowerList BurningPowerList,String productId){
		List<BurningPower> BurningPowers = BurningPowerList.getBurningPowers();
		if(BurningPowers!=null){
			for (BurningPower burningPower : BurningPowers) {
				burningPower.setUpdatedAt(new Date());
				burningPowerService.update(burningPower);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/wagesPayable/userGetAllCheck.html?productId="+productId;
	}
}
