package bss.controller.sstps;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import bss.model.sstps.PeriodCost;
import bss.model.sstps.TrialPriceBean;
import bss.model.sstps.YearPlan;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.YearPlanService;

@Controller
@Scope
@RequestMapping("/yearPlan")
public class YearPlanController extends BaseSupplierController {
	
	@Autowired
	private YearPlanService yearPlanService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	
	
	public void initYearPlan(ContractProduct contractProduct){
		List<Map<String, String[]>> list=new ArrayList<Map<String,String[]>>();
		Map<String, String[]> map=null;
		map=new HashMap<String, String[]>();
		map.put("一、民品项目", new String[]{});
		list.add(map);
		map=new HashMap<String, String[]>();
		map.put("二、军品项目", new String[]{});
		list.add(map);
		map=new HashMap<String, String[]>();
		map.put("三、其他项目", new String[]{});
		list.add(map);
		YearPlan yearPlan=null;
		int j=1;
		for(int i=0;i<list.size();i++){
			Map<String, String[]> map2 = list.get(i);
			for(String key:map2.keySet()){
				yearPlan=new YearPlan();
				String id=UUID.randomUUID().toString().replaceAll("-", "");
				yearPlan.setId(id);
				yearPlan.setParentId("0");
				yearPlan.setProjectName(key);
				yearPlan.setSerialNumber(j+"");
				yearPlan.setCreatedAt(new Date());
				yearPlan.setUpdatedAt(new Date());
				yearPlan.setContractProduct(contractProduct);
				yearPlanService.insert(yearPlan);
				j++;
			}
		}
		
		
	}
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:27  
	* @Description: 列表页面
	* @param @param model
	* @param @param proId
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,YearPlan yearPlan,Integer total){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		yearPlan.setContractProduct(contractProduct);
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		if(list!=null&&list.size()>0){
			model.addAttribute("list", list);
		}else{
			initYearPlan(contractProduct);
			List<YearPlan> lists = yearPlanService.selectProduct(yearPlan);
			model.addAttribute("list", lists);
		}
		model.addAttribute("proId", proId);
		/*if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("期间费用");
			comprehensiveCost.setSecondProject("合计");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午9:03:26  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,YearPlan yearPlan){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		yearPlan.setContractProduct(contractProduct);
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/yearPlan_list";
	}
	
	/**
	* @Title: add
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:39  
	* @Description: 新增页面
	* @param @param model
	* @param @param proId
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Model model,String proId){
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/yearPlan/add";
	}
	
	/**
	* @Title: edit
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:49  
	* @Description: 修改页面
	* @param @param model
	* @param @param proId
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(Model model,String proId,String id){
		YearPlan yearPlan = yearPlanService.selectById(id);
		model.addAttribute("yp", yearPlan);
		model.addAttribute("proId", proId);
		return "bss/sstps/offer/supplier/yearPlan/edit";
	}
	
	/**
	* @Title: save
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:19:59  
	* @Description: 保存 
	* @param @param model
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	/*@RequestMapping("/save")
	public String save(Model model,@Valid YearPlan yearPlan,BindingResult result){
		String proId = yearPlan.getContractProduct().getId();
		model.addAttribute("proId",proId);
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(yearPlan.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(yearPlan.getProductName())){
			flag = false;
			model.addAttribute("ERR_productName", "产品单位不能为空");
		}
		if(ValidateUtils.isNull(yearPlan.getMeasuringUnit())){
			flag = false;
			model.addAttribute("ERR_measuringUnit", "计量单位不能为空");
		}
		if(flag==false){
			model.addAttribute("yp", yearPlan);
			url = "bss/sstps/offer/supplier/yearPlan/add";
		}else{
			if(ValidateUtils.isNull(yearPlan.getTyaHourTotal())){
				yearPlan.setTyaHourTotal(0);
			}
			if(ValidateUtils.isNull(yearPlan.getOyaHourTotal())){
				yearPlan.setOyaHourTotal(0);
			}
			if(ValidateUtils.isNull(yearPlan.getNewHourTotal())){
				yearPlan.setNewHourTotal(0);
			}
			yearPlan.setCreatedAt(new Date());
			yearPlan.setUpdatedAt(new Date());
			yearPlanService.insert(yearPlan);
			List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/yearPlan/list";
		}
		return url;
	}*/
	
	
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listYear, HttpServletRequest request,HttpServletResponse response){
		List<YearPlan> listYearPlan = listYear.getListYear();
		for(YearPlan yearPlan:listYearPlan){
			if(yearPlan.getProjectName()!=null){
				if(yearPlan.getId()!=null){
					yearPlan.setUpdatedAt(new Date());
					yearPlanService.update(yearPlan);
				}else{ 
					String id=UUID.randomUUID().toString().replaceAll("-", "");
					yearPlan.setId(id);
					yearPlan.setCreatedAt(new Date());
					yearPlan.setUpdatedAt(new Date());
					yearPlanService.insert(yearPlan);
				}
			}
		}
		super.writeJson(response, "ok");
	}
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:20:06  
	* @Description: 新增
	* @param @param model
	* @param @param yearPlan
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,@Valid YearPlan yearPlan,BindingResult result){
		String proId = yearPlan.getContractProduct().getId();
		model.addAttribute("proId",proId);
		
		String url = "";
		boolean flag = true;
		if(ValidateUtils.isNull(yearPlan.getProjectName())){
			flag = false;
			model.addAttribute("ERR_projectName", "项目名称不能为空");
		}
		if(ValidateUtils.isNull(yearPlan.getProductName())){
			flag = false;
			model.addAttribute("ERR_productName", "产品单位不能为空");
		}
		if(ValidateUtils.isNull(yearPlan.getMeasuringUnit())){
			flag = false;
			model.addAttribute("ERR_measuringUnit", "计量单位不能为空");
		}
		if(flag==false){
			model.addAttribute("yp", yearPlan);
			url = "bss/sstps/offer/supplier/yearPlan/add";
		}else{
			yearPlan.setUpdatedAt(new Date());
			yearPlanService.update(yearPlan);
			List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/yearPlan/list";
		}
		return url;
	}
	
	/**
	* @Title: delete
	* @author Shen Zhenfei 
	* @date 2016-10-17 下午4:20:12  
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
			yearPlanService.delete(str);
		}
		
		YearPlan yearPlan = new YearPlan();
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		yearPlan.setContractProduct(contractProduct);
		
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId",proId);
		return "bss/sstps/offer/supplier/yearPlan/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		YearPlan yearPlan = new YearPlan();
		yearPlan.setContractProduct(contractProduct);
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/yearPlan_list";
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		YearPlan yearPlan = new YearPlan();
		yearPlan.setContractProduct(contractProduct);
		List<YearPlan> list = yearPlanService.selectProduct(yearPlan);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/yearPlan_list";
	}
	
}
