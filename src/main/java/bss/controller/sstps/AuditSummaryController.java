package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.sstps.AppraisalContract;
import bss.model.sstps.AuditOpinion;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.PlComprehensiveCost;
import bss.service.sstps.AppraisalContractService;
import bss.service.sstps.AuditOpinionService;
import bss.service.sstps.ComprehensiveCostService;
import bss.service.sstps.ContractProductService;

import com.github.pagehelper.PageInfo;


@Controller
@Scope
@RequestMapping("/auditSummary")
public class AuditSummaryController {
	
	@Autowired
	private AuditOpinionService auditOpinionService;
	
	@Autowired
	private ComprehensiveCostService comprehensiveCostService;
	
	@Autowired
	private ContractProductService contractProductService;
	
	@Autowired
	private AppraisalContractService appraisalContractService;
	
	@RequestMapping("/view")
	public String select(Model model,String proId,AuditOpinion auditOpinion,ComprehensiveCost comprehensiveCost){

		ContractProduct contractProduct = new ContractProduct();
			contractProduct.setId(proId);
			
			auditOpinion.setContractProduct(contractProduct);
			AuditOpinion ap = new AuditOpinion();
			ap = auditOpinionService.selectProduct(auditOpinion);
			
			comprehensiveCost.setContractProduct(contractProduct);
			List<ComprehensiveCost> list = comprehensiveCostService.select(comprehensiveCost);
			model.addAttribute("list", list);
		
			
			model.addAttribute("ap", ap);
			model.addAttribute("proId", proId);
			
			return "bss/sstps/offer/supplier/list/productOffer_list";
	}
	
	/**
	* @Title: update
	* @author Shen Zhenfei 
	* @date 2016-10-24 上午9:21:56  
	* @Description: 修改
	* @param @param model
	* @param @param proId
	* @param @param auditOpinion
	* @param @param plcc
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(Model model,String proId,AuditOpinion auditOpinion,PlComprehensiveCost plcc){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(auditOpinion.getContractProduct().getId());
		
		auditOpinion.setUpdatedAt(new Date());
		auditOpinionService.update(auditOpinion);
		
		List<ComprehensiveCost> csc = plcc.getPlcc();
		for(ComprehensiveCost cd:csc){
			cd.setUpdatedAt(new Date());
			cd.setContractProduct(contractProduct);
			comprehensiveCostService.update(cd);
		}
		
		contractProduct.setOffer(1);
		contractProductService.update(contractProduct);
		
		Integer page=1;
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		
		String url = "bss/sstps/offer/supplier/list";
		return url;
	}
	
	
	@RequestMapping("/cancel")
	public String cancel(Model model){
		Integer page=1;
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		return "bss/sstps/offer/supplier/list";
	}
	
	

}
