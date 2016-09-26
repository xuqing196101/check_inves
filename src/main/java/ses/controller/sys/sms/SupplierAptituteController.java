package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_aptitute")
public class SupplierAptituteController {
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierAptituteService supplierAptituteService;
	
	@RequestMapping(value = "add_aptitute")
	public String addAptitute(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_aptitute";
	}

	@RequestMapping(value = "save_or_update_aptitute")
	public String saveOrUpdateAptitute(HttpServletRequest request, SupplierAptitute supplierAptitute, String supplierId) {
		supplierAptituteService.saveOrUpdateAptitute(supplierAptitute);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "delete_aptitute")
	public String deleteAptitute(HttpServletRequest request, String aptituteIds, String supplierId) {
		supplierAptituteService.deleteAptitute(aptituteIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
}
