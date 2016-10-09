package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierCredit;
import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;
import ses.service.sms.SupplierCreditService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_credit_ctnt")
public class SupplierCreditCtntController {

	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
	
	@Autowired
	private SupplierCreditService supplierCreditService;
	
	@RequestMapping(value = "list")
	public String list(Model model, SupplierCreditCtnt supplierCreditCtnt, Integer page) {
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntService.findCreditCtnt(supplierCreditCtnt,  page == null ? 1 : page);
		model.addAttribute("listSupplierCreditCtnts", new PageInfo<SupplierCreditCtnt>(listSupplierCreditCtnts));
		model.addAttribute("name", supplierCreditCtnt.getName());
		return "ses/sms/supplier_credit_ctnt/list";
	}
	
	@RequestMapping(value = "add_credit_ctnt")
	public String addCreditCtnt(Model model, SupplierCreditCtnt supplierCreditCtnt) {
		if (supplierCreditCtnt.getId() != null && !"".equals(supplierCreditCtnt.getId())) {
			model.addAttribute("supplierCreditCtnt", supplierCreditCtnt);
		}
		List<SupplierCredit> listSupplierCredits = supplierCreditService.findSupplierCredit(new SupplierCredit());
		model.addAttribute("listSupplierCredits", listSupplierCredits);
		return "ses/sms/supplier_credit_ctnt/add_credit_ctnt";
	}
	
	@RequestMapping(value = "save_or_update_supplier_credit_ctnt")
	public String saveOrUpdateSupplierCredit(SupplierCreditCtnt supplierCreditCtnt) {
		supplierCreditCtntService.saveOrUpdateSupplierCreditCtnt(supplierCreditCtnt);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids) {
		supplierCreditCtntService.delete(ids);
		return "redirect:list.html";
	}
	
}
