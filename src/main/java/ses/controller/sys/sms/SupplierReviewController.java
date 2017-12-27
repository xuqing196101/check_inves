package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAuditOpinion;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierReviewService;

/**
 * 供应商复核
 * @return
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierReview")
public class SupplierReviewController {
	
	@Autowired
	private SupplierReviewService supplierReviewService;
	
	@Autowired
	private SupplierAuditOpinionService supplierAuditOpinionService;
	
	/**
	 * 复核列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Supplier supplier, Integer page, Model model){
		List<Supplier> supplierList = supplierReviewService.selectReviewList(supplier, page);
		PageInfo<Supplier> pageInfo = new PageInfo <Supplier> (supplierList);
		model.addAttribute("result", pageInfo);
		return "ses/sms/supplier_review/list";
	}
	
	@RequestMapping(value = "/review")
	public String review(String supplierId, Integer supplierStatus, Model model){
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierStatus);
		model.addAttribute("sign", 2);
		
		//查询意见
		SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 1);
		model.addAttribute("auditOpinion", supplierAuditOpinion == null? "" : supplierAuditOpinion.getOpinion());
		model.addAttribute("flagAduit", supplierAuditOpinion == null? "" : supplierAuditOpinion.getFlagAduit());
		if(supplierAuditOpinion !=null){
			model.addAttribute("isRecord", "yes");
		}else{
			model.addAttribute("isRecord", "no");
		}
		return "ses/sms/supplier_review/review";
	}
	
	@RequestMapping(value = "/saveOpinion")
	@ResponseBody
	public String saveOpinion(SupplierAuditOpinion supplierAuditOpinion){
		String msg = supplierAuditOpinionService.saveOpinion(supplierAuditOpinion);
		return msg;
	}
}
