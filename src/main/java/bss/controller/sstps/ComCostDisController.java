package bss.controller.sstps;

import java.util.Date;
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
import bss.service.sstps.AuditOpinionService;
import bss.service.sstps.ComCostDisService;
import bss.service.sstps.ComprehensiveCostService;

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
		List<ComCostDis> list = comCostDisService.selectProduct(comCostDis);
		
		if(list.size()>0){
		//	List<ComCostDis> list1 = comCostDisService.selectProduct(comCostDis);
			model.addAttribute("list", list);
		}else{
			comCostDis.setCreatedAt(new Date());
			comCostDis.setUpdatedAt(new Date());
			String[] name = {"外购动力开支","直接人工费","制造费用","管理费用","财务费用"};
			for(int i = 0;i<name.length;i++){
				comCostDis.setProjectName(name[i]);
				comCostDis.setStatus(0);
				comCostDisService.insert(comCostDis);
			}
			String[] names= {"制度总工时","计划任务总工时"};
			for(int j=0;j<names.length;j++){
				comCostDis.setStatus(1);
				comCostDis.setProjectName(names[j]);
				comCostDisService.insert(comCostDis);
			}
			
			List<ComCostDis> listN = comCostDisService.selectProduct(comCostDis);
			model.addAttribute("list", listN);
		}
		
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/comCostDis/list";
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
	//	ComCostDis ccd = new ComCostDis();
		
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
		AuditOpinion ap = new AuditOpinion();
		ap = auditOpinionService.selectProduct(auditOpinion);
		if(ap==null){
			auditOpinionService.insert(auditOpinion);
			ap = auditOpinionService.selectProduct(auditOpinion);
		}
		
		comprehensiveCost.setContractProduct(contractProduct);
		List<ComprehensiveCost> list = comprehensiveCostService.select(comprehensiveCost);
		if(list.size()>0){
			model.addAttribute("list", list);
		}else{
			String[] name1={"原辅材料","外购成件","外协部件","燃料动力","直接人工","专用费用","制造费用","合计"};
			for(int i =0;i<name1.length;i++){
				comprehensiveCost.setProjectName("专项试验费");
				comprehensiveCost.setSecondProject(name1[i]);
				comprehensiveCost.setStatus(0);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name2={"管理费用","财务费用","销售费用","合计"};
			for(int i =0;i<name2.length;i++){
				comprehensiveCost.setProjectName("期间费用");
				comprehensiveCost.setSecondProject(name2[i]);
				comprehensiveCost.setStatus(1);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name3={"成本","利润","税金","价格"};
			for(int i =0;i<name3.length;i++){
				comprehensiveCost.setProjectName("价格方案");
				comprehensiveCost.setSecondProject(name3[i]);
				comprehensiveCost.setStatus(2);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			String[] name4={"本产品定额工时","工时分配率合计","直接人工","燃料动力","制造费用","期间费用"};
			for(int i =0;i<name4.length;i++){
				comprehensiveCost.setProjectName("工时及分配率");
				comprehensiveCost.setSecondProject(name4[i]);
				comprehensiveCost.setStatus(3);
				comprehensiveCostService.insert(comprehensiveCost);
			}
			
			List<ComprehensiveCost> list2 = comprehensiveCostService.select(comprehensiveCost);
			model.addAttribute("list", list2);
		}
		
		model.addAttribute("ap", ap);
		model.addAttribute("proId", proId);
		
		return "bss/sstps/offer/supplier/productOffer/list";
	}
	

}
