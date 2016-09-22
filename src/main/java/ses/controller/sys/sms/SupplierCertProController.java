package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertPro;
import ses.service.sms.SupplierCertProService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_cert_pro")
public class SupplierCertProController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private SupplierCertProService supplierCertProService;
	
	@RequestMapping(value = "add_cert_pro")
	public String addCertPro(Model model, String matProId, String supplierId) {
		model.addAttribute("matProId", matProId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_cert_pro";
	}
	
	@RequestMapping(value = "save_or_update_cert_pro")
	public String saveOrUpdateCertPro(HttpServletRequest request, SupplierCertPro supplierCertPro, String supplierId) {
		supplierCertProService.saveOrUpdateCertPro(supplierCertPro);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-1");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToProfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-1");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_cert_pro")
	public String deleteCertPro(HttpServletRequest request, String certProIds, String supplierId) {
		supplierCertProService.deleteCertPro(certProIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-1");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

}
