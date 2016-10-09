package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCredit;
import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;
import ses.service.sms.SupplierCreditService;
import ses.service.sms.SupplierLevelService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_level")
public class SupplierLevelController extends BaseSupplierController {
	
	@Autowired
	private SupplierLevelService supplierLevelService;
	
	@Autowired
	private SupplierCreditService supplierCreditService;
	
	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
	
	@RequestMapping(value = "list")
	public String list(Model model, Supplier supplier, Integer page) {
		List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		return "ses/sms/supplier_level/list";
	}
	
	@RequestMapping(value = "change_score")
	public String changeScore(Model model, Supplier supplier) {
		List<SupplierCredit> listSupplierCredits = supplierCreditService.findSupplierCredit(new SupplierCredit());
		model.addAttribute("supplier", supplier);
		model.addAttribute("listSupplierCredits", listSupplierCredits);
		return "ses/sms/supplier_level/change_score";
	}
	
	@RequestMapping(value = "find_supplier_credit_ctnt")
	public void findSupplierCreditCtnt(HttpServletResponse response, SupplierCreditCtnt supplierCreditCtnt) {
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntService.findCreditCtnt(supplierCreditCtnt);
		super.writeJson(response, listSupplierCreditCtnts);
	}
	
	@RequestMapping(value = "update_score")
	public String updateScore(Supplier supplier, String scores) {
		supplierLevelService.updateScore(supplier, scores);
		return "redirect:list.html";
	}

}
