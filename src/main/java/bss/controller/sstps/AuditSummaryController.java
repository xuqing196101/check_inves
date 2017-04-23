package bss.controller.sstps;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.cs.PurchaseContract;
import bss.model.dms.ProbationaryArchive;
import bss.model.ppms.Project;
import bss.model.sstps.AppraisalContract;
import bss.model.sstps.AuditOpinion;
import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;
import bss.model.sstps.PlComprehensiveCost;
import bss.service.cs.PurchaseContractService;
import bss.service.ppms.ProjectService;
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
	private ProjectService projectService;
	
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
		
		ContractProduct contract = contractProductService.selectById(proId);
		//遍历项目所有条目是否已审核
		/*List<ContractProduct> ContractProducts = contractProductService.selectProjectList(contract);
		boolean flag = true;
		for (ContractProduct cp : ContractProducts) {
			if(cp.getAuditOffer()!=2){
				flag=false;
			}
		}
		if (flag) {
			AppraisalContract appraisalContract = new AppraisalContract();
			appraisalContract.setId(contract.getAppraisalContract().getId());
			appraisalContract.setAppraisal(2);
			appraisalContract.setUpdatedAt(new Date());
			appraisalContractService.update(appraisalContract);
		}
		*/
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
	
	@RequestMapping("/userGetAll")
	public String userGetAll(Model model,HttpServletRequest request,String productId){ 

			ContractProduct contractProduct = new ContractProduct();
			contractProduct.setId(productId);
			
			AuditOpinion ap = new AuditOpinion();
			ap.setContractProduct(contractProduct);
			ap = auditOpinionService.selectProduct(ap);
			
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			
			List<ComprehensiveCost> list = comprehensiveCostService.select(comprehensiveCost);
			model.addAttribute("list", list);
		
			
			model.addAttribute("ap", ap);
			model.addAttribute("proId", productId);
			
			return "bss/sstps/offer/userAppraisal/list/productOffer_list";
	}

	@RequestMapping("/userUpdate")
	public String userUpdate(Model model,String productId,PlComprehensiveCost plcc,AuditOpinion auditOpinion){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		
		auditOpinion.setUpdatedAt(new Date());
		auditOpinionService.update(auditOpinion);
		
		List<ComprehensiveCost> csc = plcc.getPlcc();
		if(csc!=null){
			for(ComprehensiveCost cd:csc){
				cd.setUpdatedAt(new Date());
				cd.setContractProduct(contractProduct);
				comprehensiveCostService.update(cd);
			}
		}
		//更改条目为已审核
				contractProduct.setAuditOffer(1);
				contractProductService.update(contractProduct);
				contractProduct = contractProductService.selectById(productId);
				//遍历项目所有条目是否已审核
				List<ContractProduct> ContractProducts = contractProductService.selectProjectList(contractProduct);
				boolean flag = true;
				for (ContractProduct cp : ContractProducts) {
					if(cp.getOffer()==1){ //如果已报价
						if (cp.getAuditOffer()!=1) {//如果条目已审核
							flag=false;
							break;
						}
					}else {
						flag = false;
						break;
					}
				}
				if (flag) {
					AppraisalContract appraisalContract = new AppraisalContract();
					appraisalContract.setId(contractProduct.getAppraisalContract().getId());
					appraisalContract.setAppraisal(2);
					appraisalContract.setUpdatedAt(new Date());
					appraisalContractService.update(appraisalContract);
					appraisalContract = appraisalContractService.selectContractInfo(contractProduct.getAppraisalContract().getId());//获取目标单一来源合同
					PurchaseContract purchaseContract = appraisalContract.getPurchaseContract();//获取目标采购合同
					Project project  = projectService.selectById(purchaseContract.getProjectId());//获取目标项目
					//插入预备档案表
					ProbationaryArchive probationaryArchive = new ProbationaryArchive();
					probationaryArchive.setContractCode(appraisalContract.getCode());//采购合同编号
					probationaryArchive.setProjectCode(project.getProjectNumber());//项目编号
					probationaryArchive.setYear(purchaseContract.getYear());//预算年度
					probationaryArchive.setPurchaseDep(appraisalContract.getPurchaseDepName());//采购机构
					probationaryArchive.setPurchaseType(appraisalContract.getPurchaseType());//采购方式
					//产品名称
					probationaryArchive.setSupplierName(appraisalContract.getSupplierName());//供应商名称
					probationaryArchive.setProjectId(project.getId());//项目id
					probationaryArchive.setDraftGitAt(purchaseContract.getDraftGitAt());//合同草案上报时间
					probationaryArchive.setDraftReviewedAt(purchaseContract.getDraftReviewedAt());//合同草案批复时间
					probationaryArchive.setFormalGitAt(purchaseContract.getFormalGitAt());//正式合同上报时间
					probationaryArchive.setFormalReviewedAt(purchaseContract.getFormalReviewedAt());//正式合同批复时间
					//采购文件上传时间
					//采购文件批复时间
					probationaryArchive.setCreatedAt(new Date());//创建时间
					probationaryArchive.setContractCode(purchaseContract.getId());//单一来源合同ID
					//插入预备档案表
				}
				
		Integer page=1;
		List<AppraisalContract> list = appraisalContractService.selectDistribution(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		
		String url = "bss/sstps/offer/userAppraisal/list";
		return url;
	}
	
	@RequestMapping("/userGetAllCheck")
	public String userGetAllCheck(Model model,HttpServletRequest request,String productId){ 

			ContractProduct contractProduct = new ContractProduct();
			contractProduct.setId(productId);
			
			AuditOpinion ap = new AuditOpinion();
			ap.setContractProduct(contractProduct);
			ap = auditOpinionService.selectProduct(ap);
			
			ComprehensiveCost comprehensiveCost = new ComprehensiveCost();
			comprehensiveCost.setContractProduct(contractProduct);
			
			List<ComprehensiveCost> list = comprehensiveCostService.select(comprehensiveCost);
			model.addAttribute("list", list);
		
			
			model.addAttribute("ap", ap);
			model.addAttribute("proId", productId);
			
			return "bss/sstps/offer/checkAppraisal/list/productOffer_list";
	}

	@RequestMapping("/userUpdateCheck")
	public String userUpdateCheck(Model model,String productId,PlComprehensiveCost plcc,AuditOpinion auditOpinion){
		
		ContractProduct contractProduct = new ContractProduct();
		contractProduct.setId(productId);
		
		auditOpinion.setUpdatedAt(new Date());
		auditOpinionService.update(auditOpinion);
		
		List<ComprehensiveCost> csc = plcc.getPlcc();
		if(csc!=null){
			for(ComprehensiveCost cd:csc){
				cd.setUpdatedAt(new Date());
				cd.setContractProduct(contractProduct);
				comprehensiveCostService.update(cd);
			}
		}
		
		contractProduct.setAuditOffer(2);
		contractProductService.update(contractProduct);
		contractProduct = contractProductService.selectById(productId);

		
		Integer page=1;
		List<AppraisalContract> list = appraisalContractService.selectAppraisal(null,page==null?1:page);
		model.addAttribute("list", new PageInfo<AppraisalContract>(list));
		
		String url = "bss/sstps/offer/checkAppraisal/list";
		return url;
	}
}
