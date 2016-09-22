package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertSe;
import ses.service.sms.SupplierCertSeService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_cert_se")
public class SupplierCertSeController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierCertSeService supplierCertSeService;
	
	@RequestMapping(value = "add_cert_se")
	public String addCertSell(Model model, String matSeId, String supplierId) {
		model.addAttribute("matSeId", matSeId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_cert_sell";
	}
	
	@RequestMapping(value = "save_or_update_cert_se")
	public String saveOrUpdateCertSell(HttpServletRequest request, SupplierCertSe supplierCertSe, String supplierId) {
		supplierCertSeService.saveOrUpdateCertSe(supplierCertSe);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-4");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-4");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_cert_se")
	public String deleteCertSell(HttpServletRequest request, String certSeIds, String supplierId) {
		supplierCertSeService.deleteCertSe(certSeIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-4");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
}
