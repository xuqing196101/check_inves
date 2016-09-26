package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierCertEng;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_cert_eng")
public class SupplierCertEngController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierCertEngService supplierCertEngService;
	
	@RequestMapping(value = "add_cert_eng")
	public String addCertEng(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_cert_eng";
	}
	
	@RequestMapping(value = "save_or_update_cert_eng")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierCertEng supplierCertEng, String supplierId) {
		supplierCertEngService.saveOrUpdateCertEng(supplierCertEng);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_cert_eng")
	public String deleteCertEng(HttpServletRequest request, String certEngIds, String supplierId) {
		supplierCertEngService.deleteCertEng(certEngIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
}
