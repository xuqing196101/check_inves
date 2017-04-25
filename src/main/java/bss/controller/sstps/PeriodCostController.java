package bss.controller.sstps;

import java.util.ArrayList;
import java.util.Calendar;
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
import bss.echarts.Data;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.PeriodCost;
import bss.model.sstps.TrialPriceBean;
import bss.model.sstps.WagesPayable;
import bss.service.sstps.ComprehensiveCostService;
import bss.model.sstps.PeriodCostList;
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
public class PeriodCostController extends BaseSupplierController {
	
	@Autowired
	private PeriodCostService periodCostService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	public static Date addSecond(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
		}
	public void initPeriodCost(ContractProduct contractProduct){
		List<HashMap<String,String[]>> hashMaps=new ArrayList<HashMap<String,String[]>>();
		HashMap<String,String[]> hashMap=null;
	    hashMap=new HashMap<String, String[]>();
		hashMap.put("管理费用合计", new String[]{"工资","提取的职工福利费","差旅费","水电费","办公费","折旧费","修理费","物料消耗","低值易耗品摊销","工会经费",
				"职工教育经费","劳动保险费","待业保险费","咨询费","审计费","排污费","绿化费","税金","业务招待费","存货盘亏、毁损和报废","运输费"});
		hashMaps.add(hashMap);
		hashMap=new HashMap<String, String[]>();
		hashMap.put("财务费用合计", new String[]{"利息支出","利息收入","金融机构手续费"});
		hashMaps.add(hashMap);
		PeriodCost periodCost=null;
		int i=1;
		int h=1;
		Date date=new Date();
		for(int k=0;k<hashMaps.size();k++){
			HashMap<String, String[]> hashMap2 = hashMaps.get(k);
			for(String key:hashMap2.keySet() ){
				periodCost=new PeriodCost();
				String id=UUID.randomUUID().toString().replaceAll("-", "");
				periodCost.setId(id);
				periodCost.setProjectName(key);
				periodCost.setParentId("0");
				periodCost.setCreatedAt(new Date());
				periodCost.setUpdatedAt(new Date());
				periodCost.setContractProduct(contractProduct);
				periodCost.setSerialNumber(i+"");
				periodCostService.insert(periodCost);
				String[] strings = hashMap2.get(key);
				if(strings!=null){
					int j=1;
					for(String str:strings){
						periodCost=new PeriodCost();
						String ids=UUID.randomUUID().toString().replaceAll("-", "");
						periodCost.setId(ids);
						periodCost.setProjectName(str);
						periodCost.setParentId(id);
						periodCost.setCreatedAt(addSecond(date,h));
						periodCost.setUpdatedAt(new Date());
						periodCost.setContractProduct(contractProduct);
						periodCost.setSerialNumber(i+"."+j);
						periodCostService.insert(periodCost);
						j++;
						h++;
					}
				}
				i++;
			}
	   }
		
		
	}
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
		if(list!=null&&list.size()>0){
			model.addAttribute("list", list);
		}else{
			initPeriodCost(contractProduct);
			List<PeriodCost> lists = periodCostService.selectProduct(periodCost);
			model.addAttribute("list", lists);
		}
		model.addAttribute("proId", proId);
		/*if(total!=null){
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCost.setSingleOffer(total);
			comprehensiveCost.setProjectName("专项试验费");
			comprehensiveCost.setSecondProject("制造费用");
			comprehensiveCostService.updateInfo(comprehensiveCost);
		}*/
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
	/*@RequestMapping("/save")
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
			if(ValidateUtils.isNull(periodCost.getTyaQuoteprice())){
				periodCost.setTyaQuoteprice(0);
			}
			if(ValidateUtils.isNull(periodCost.getOyaQuoteprice())){
				periodCost.setOyaQuoteprice(0);
			}
			if(ValidateUtils.isNull(periodCost.getNewQuoteprice())){
				periodCost.setNewQuoteprice(0);
			}
			periodCost.setCreatedAt(new Date());
			periodCost.setUpdatedAt(new Date());
			periodCostService.insert(periodCost);
			List<PeriodCost> list = periodCostService.selectProduct(periodCost);
			model.addAttribute("list", list);
			url = "bss/sstps/offer/supplier/periodCost/list";
		}
		return url;
	}*/
	@RequestMapping("/save")
	public void save(Model model,TrialPriceBean listPerio, HttpServletRequest request,HttpServletResponse response){
		List<PeriodCost> listPeriodCost = listPerio.getListPerio();
		for(PeriodCost periodCost:listPeriodCost){
			if(periodCost.getProjectName()!=null){
				if(periodCost.getId()!=null){
					periodCost.setUpdatedAt(new Date());
					periodCostService.update(periodCost);
				}else{ 
					String id=UUID.randomUUID().toString().replaceAll("-", "");
					periodCost.setId(id);
					periodCost.setCreatedAt(new Date());
					periodCost.setUpdatedAt(new Date());
					periodCostService.insert(periodCost);
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
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		PeriodCost periodCost = new PeriodCost();
		periodCost.setContractProduct(contractProduct);
		List<PeriodCost> list = periodCostService.selectProduct(periodCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/periodCost_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,TrialPriceBean listPerio,String productId){
		List<PeriodCost> listPeriodCost = listPerio.getListPerio();
		for(PeriodCost periodCost:listPeriodCost){
				if(periodCost.getId()!=null){
					periodCost.setUpdatedAt(new Date());
					periodCostService.update(periodCost);
				}
		}
		model.addAttribute("proId",productId);
		return "redirect:/yearPlan/userGetAll.html?productId="+productId;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		PeriodCost periodCost = new PeriodCost();
		periodCost.setContractProduct(contractProduct);
		List<PeriodCost> list = periodCostService.selectProduct(periodCost);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/periodCost_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,TrialPriceBean listPerio,String productId){
		List<PeriodCost> listPeriodCost = listPerio.getListPerio();
		for(PeriodCost periodCost:listPeriodCost){
				if(periodCost.getId()!=null){
					periodCost.setUpdatedAt(new Date());
					periodCostService.update(periodCost);
				}
		}
		model.addAttribute("proId",productId);
		return "redirect:/yearPlan/userGetAllCheck.html?productId="+productId;
	}
}
