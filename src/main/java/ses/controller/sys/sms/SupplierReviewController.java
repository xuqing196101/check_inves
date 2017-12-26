package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.sms.Supplier;
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
	private SupplierReviewService ssupplierReviewService;
	
	/**
	 * 复核列表
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(Supplier supplier, Integer page, Model model){
		List<Supplier> supplierList = ssupplierReviewService.selectReviewList(supplier, page);
		PageInfo<Supplier> pageInfo = new PageInfo <Supplier> (supplierList);
		model.addAttribute("result", pageInfo);
		return "ses/sms/supplier_review/list";
	}
	
	
	@RequestMapping(value = "/review")
	public String review(){
		return "ses/sms/supplier_review/review";
	}
}
