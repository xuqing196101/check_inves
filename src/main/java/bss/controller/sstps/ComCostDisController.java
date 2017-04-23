package bss.controller.sstps;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.AuditOpinion;
import bss.model.sstps.ComCostDis;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.PlComCostDis;
import bss.model.sstps.WagesPayable;
import bss.model.sstps.YearPlan;
import bss.service.sstps.AuditOpinionService;
import bss.service.sstps.BurningPowerService;
import bss.service.sstps.ComCostDisService;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.WagesPayableService;
import bss.service.sstps.YearPlanService;

@Controller
@Scope
@RequestMapping("/comCostDis")
public class ComCostDisController {
	
	@Autowired
	private ComCostDisService comCostDisService;
	
	@Autowired
	private AuditOpinionService auditOpinionService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	@Autowired
	private WagesPayableService wagesPayableService;
	@Autowired
	private YearPlanService yearPlanService;
	@Autowired
	private BurningPowerService burningPowerService;
	
	
	
	
	public static Date addSecond(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
		}
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-19 上午10:01:17  
	* @Description: 列表
	* @param @param model
	* @param @param proId
	* @param @param comCostDis
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	public String select(Model model,String proId,ComCostDis comCostDis){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		comCostDis.setContractProduct(contractProduct);
		comCostDisService.appendSumComCostDis(contractProduct);
		List<ComCostDis> list = comCostDisService.selectProduct(comCostDis);
		if(list.size()>0){
			model.addAttribute("list", list);
		}else{
			String[] name = {"燃料动力费","直接人工费","制造费用","管理费用","财务费用"};
			Date date=new Date();
			for(int i = 0;i<name.length;i++){
				comCostDis.setCreatedAt(addSecond(date,i));
				comCostDis.setUpdatedAt(addSecond(date,i));
				comCostDis.setProjectName(name[i]);
				comCostDis.setStatus(0);
				comCostDisService.insert(comCostDis);
			}
			String[] names= {"制度总工时","计划任务总工时"};
			for(int j=0;j<names.length;j++){
				comCostDis.setStatus(1);
				comCostDis.setCreatedAt(addSecond(date,j));
				comCostDis.setUpdatedAt(addSecond(date,j));
				comCostDis.setProjectName(names[j]);
				comCostDisService.insert(comCostDis);
			}
			comCostDisService.appendSumComCostDis(contractProduct);
			List<ComCostDis> listN = comCostDisService.selectProduct(comCostDis);
			model.addAttribute("list", listN);
		}
		
		
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/comCostDis/list";
	}
	
	/**
	* @Title: select
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午9:08:05  
	* @Description: 查看
	* @param @param model
	* @param @param proId
	* @param @param comCostDis
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(Model model,String proId,ComCostDis comCostDis){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		comCostDis.setContractProduct(contractProduct);
		List<ComCostDis> list = comCostDisService.selectProduct(comCostDis);
		
		model.addAttribute("list", list);
		
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/list/comCostDis_list";
	}
	
	/**
	* @Title: insert
	* @author Shen Zhenfei 
	* @date 2016-10-18 下午3:15:57  
	* @Description: 默认新增固定数据
	* @param       
	* @return void
	 */
	@RequestMapping("/insert")
	public void insert(String proId,ComCostDis comCostDis){
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		comCostDis.setContractProduct(contractProduct);
	}
	
	@RequestMapping("/update")
	public String update(HttpServletRequest request,String proId,PlComCostDis plComCostDis){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		
		List<ComCostDis> comCostDis = plComCostDis.getPlccd();
		for(ComCostDis cd:comCostDis){
			cd.setUpdatedAt(new Date());
			cd.setContractProduct(contractProduct);
			comCostDisService.update(cd);
		}
		
		String url = "redirect:next.html?proId="+proId;
		return url;
	}
	
	public void initcomprehensiveCost(ContractProduct contractProduct){
		String[] name1={"原辅材料","外购成件","外协部件","燃料动力","直接人工","专用费用","制造费用","小计"};
		ComprehensiveCost comprehensiveCost=null;
		Date date=new Date();
		for(int i =0;i<name1.length;i++){
			date=addSecond(date, i+1);
			comprehensiveCost=new ComprehensiveCost();
			comprehensiveCost.setProjectName("生产成本");
			comprehensiveCost.setSecondProject(name1[i]);
			comprehensiveCost.setStatus(0);
			comprehensiveCost.setCreatedAt(date);
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCostService.insert(comprehensiveCost);
		}
		String[] name2={"管理费用","财务费用","销售费用","小计"};
		for(int i =0;i<name2.length;i++){
			date=addSecond(date, i+1);
			comprehensiveCost=new ComprehensiveCost();
			comprehensiveCost.setProjectName("期间费用");
			comprehensiveCost.setSecondProject(name2[i]);
			comprehensiveCost.setStatus(1);
			comprehensiveCost.setCreatedAt(date);
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCostService.insert(comprehensiveCost);
		}
		String[] name3={"成本","利润","税金","价格"};
		for(int i =0;i<name3.length;i++){
			date=addSecond(date, i+1);
			comprehensiveCost=new ComprehensiveCost();
			comprehensiveCost.setProjectName("价格方案");
			comprehensiveCost.setSecondProject(name3[i]);
			comprehensiveCost.setStatus(2);
			comprehensiveCost.setCreatedAt(date);
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCostService.insert(comprehensiveCost);
		}
		String[] name4={"本产品定额工时","工时分配率合计","直接人工","燃料动力","制造费用","期间费用"};
		for(int i =0;i<name4.length;i++){
			date=addSecond(date, i+1);
			comprehensiveCost=new ComprehensiveCost();
			comprehensiveCost.setProjectName("工时及分配率");
			comprehensiveCost.setSecondProject(name4[i]);
			comprehensiveCost.setStatus(3);
			comprehensiveCost.setCreatedAt(date);
			comprehensiveCost.setContractProduct(contractProduct);
			comprehensiveCostService.insert(comprehensiveCost);
		}
	}
	
	/**
	* @Title: next
	* @author Shen Zhenfei 
	* @date 2016-10-20 上午8:56:42  
	* @Description: 下一步
	* @param @param model
	* @param @param proId
	* @param @param comCostDis
	* @param @return      
	* @return String
	 */
	@RequestMapping("/next")
	public String next(Model model,String proId,AuditOpinion auditOpinion,ComprehensiveCost comprehensiveCost){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(proId);
		auditOpinion.setContractProduct(contractProduct);
		AuditOpinion ap = auditOpinionService.selectProduct(auditOpinion);
		if(ap==null){
			auditOpinionService.insert(auditOpinion);
			ap = auditOpinionService.selectProduct(auditOpinion);
		}
		comprehensiveCost.setContractProduct(contractProduct);
		comprehensiveCostService.appendSumComprehensiveCost(contractProduct);
		List<ComprehensiveCost> list = comprehensiveCostService.select(comprehensiveCost);
		if(list==null||list.size()<=0){
			initcomprehensiveCost(contractProduct);
			comprehensiveCostService.appendSumComprehensiveCost(contractProduct);
			list = comprehensiveCostService.select(comprehensiveCost);
		}
		
		
		
		//if(list.size()>0){
			model.addAttribute("list", list);
//		}else{
//			String[] name1={"原辅材料","外购成件","外协部件","燃料动力","直接人工","专用费用","制造费用","合计"};
//			for(int i =0;i<name1.length;i++){
//				comprehensiveCost.setProjectName("专项试验费");
//				comprehensiveCost.setSecondProject(name1[i]);
//				comprehensiveCost.setStatus(0);
//				comprehensiveCostService.insert(comprehensiveCost);
//			}
//			String[] name2={"管理费用","财务费用","销售费用","合计"};
//			for(int i =0;i<name2.length;i++){
//				comprehensiveCost.setProjectName("期间费用");
//				comprehensiveCost.setSecondProject(name2[i]);
//				comprehensiveCost.setStatus(1);
//				comprehensiveCostService.insert(comprehensiveCost);
//			}
//			String[] name3={"成本","利润","税金","价格"};
//			for(int i =0;i<name3.length;i++){
//				comprehensiveCost.setProjectName("价格方案");
//				comprehensiveCost.setSecondProject(name3[i]);
//				comprehensiveCost.setStatus(2);
//				comprehensiveCostService.insert(comprehensiveCost);
//			}
//			String[] name4={"本产品定额工时","工时分配率合计","直接人工","燃料动力","制造费用","期间费用"};
//			for(int i =0;i<name4.length;i++){
//				comprehensiveCost.setProjectName("工时及分配率");
//				comprehensiveCost.setSecondProject(name4[i]);
//				comprehensiveCost.setStatus(3);
//				comprehensiveCostService.insert(comprehensiveCost);
//			}
//			
//			List<ComprehensiveCost> list2 = comprehensiveCostService.select(comprehensiveCost);
//			model.addAttribute("list", list2);
//		}
		
		model.addAttribute("ap", ap);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/productOffer/list";
	}
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		ComCostDis comCostDis = new ComCostDis();
		comCostDis.setContractProduct(contractProduct);
		List<ComCostDis> list = comCostDisService.selectProduct(comCostDis);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/userAppraisal/list/comCostDis_list";
	}
	
	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,PlComCostDis plccd,String productId){
		List<ComCostDis> ComCostDis = plccd.getPlccd();
		if(ComCostDis!=null){
			for (ComCostDis ccd : ComCostDis) {
				ccd.setUpdatedAt(new Date());
				comCostDisService.update(ccd);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/auditSummary/userGetAll.html?productId="+productId;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		ComCostDis comCostDis = new ComCostDis();
		comCostDis.setContractProduct(contractProduct);
		List<ComCostDis> list = comCostDisService.selectProduct(comCostDis);
		model.addAttribute("list", list);
		model.addAttribute("proId", productId);
		return "bss/sstps/offer/checkAppraisal/list/comCostDis_list";
	}
	
	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,PlComCostDis plccd,String productId){
		List<ComCostDis> ComCostDis = plccd.getPlccd();
		if(ComCostDis!=null){
			for (ComCostDis ccd : ComCostDis) {
				ccd.setUpdatedAt(new Date());
				comCostDisService.update(ccd);
			}
		}
		model.addAttribute("proId",productId);
		return "redirect:/auditSummary/userGetAllCheck.html?productId="+productId;
	}
}
