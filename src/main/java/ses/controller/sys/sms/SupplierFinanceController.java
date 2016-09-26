package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierFinance;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_finance")
public class SupplierFinanceController {
	
	@Autowired
	private SupplierFinanceService supplierFinanceService;// 供应商财务信息
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@RequestMapping(value = "add_finance")
	public String addCertEng(Model model, String supplierId) {
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_finance";
	}
	
	@RequestMapping(value = "save_or_update_finance")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierFinance supplierFinance, String supplierId) {
		supplierFinanceService.saveOrUpdateFinance(supplierFinance);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_finance")
	public String deleteCertEng(HttpServletRequest request, String financeIds, String supplierId) {
		supplierFinanceService.deleteFinance(financeIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
}
