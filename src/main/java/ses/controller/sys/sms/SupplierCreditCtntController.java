package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_credit_ctnt")
public class SupplierCreditCtntController {

	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
	
	
	@RequestMapping(value = "list_by_credit_id")
	public String listByCreditId(Model model, SupplierCreditCtnt supplierCreditCtnt, Integer page) {
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntService.findCreditCtntByCreditId(supplierCreditCtnt,  page == null ? 1 : page);
		model.addAttribute("listSupplierCreditCtnts", new PageInfo<SupplierCreditCtnt>(listSupplierCreditCtnts));
		model.addAttribute("supplierCreditId", supplierCreditCtnt.getSupplierCreditId());
		return "ses/sms/supplier_credit_ctnt/list";
	}
	
	@RequestMapping(value = "add_credit_ctnt")
	public String addCreditCtnt(Model model, SupplierCreditCtnt supplierCreditCtnt) throws Exception {
		if(supplierCreditCtnt.getName() != null){
			supplierCreditCtnt.setName(new String(supplierCreditCtnt.getName().getBytes("ISO8859-1"), "UTF-8"));
		}
		model.addAttribute("supplierCreditCtnt", supplierCreditCtnt);
		
		return "ses/sms/supplier_credit_ctnt/add_credit_ctnt";
	}
	
	@RequestMapping(value = "save_or_update_supplier_credit_ctnt")
	public String saveOrUpdateSupplierCredit(SupplierCreditCtnt supplierCreditCtnt) {
		supplierCreditCtntService.saveOrUpdateSupplierCreditCtnt(supplierCreditCtnt);
		return "redirect:list_by_credit_id.html?supplierCreditId=" + supplierCreditCtnt.getSupplierCreditId();
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids, String supplierCreditId) {
		supplierCreditCtntService.delete(ids);
		return "redirect:list_by_credit_id.html?supplierCreditId=" + supplierCreditId;
	}
	
}
