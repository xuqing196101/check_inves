package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierRegPerson;
import ses.service.sms.SupplierRegPersonService;
import ses.service.sms.SupplierService;
@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_reg_person")
public class SupplierRegPersonController {
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierRegPersonService supplierRegPersonService;
	
	@RequestMapping(value = "add_reg_person")
	public String addRegPerson(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_reg_person";
	}

	@RequestMapping(value = "save_or_update_reg_person")
	public String saveOrUpdateRegPerson(HttpServletRequest request, SupplierRegPerson supplierRegPerson, String supplierId) {
		supplierRegPersonService.saveOrUpdateRegPerson(supplierRegPerson);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
//		return "redirect:../supplier/page_jump.html";
		return "ses/sms/supplier_register/supplier_type";
	}

	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "delete_reg_person")
	public String deleteRegPerson(HttpServletRequest request, String regPersonIds, String supplierId) {
		supplierRegPersonService.deleteRegPerson(regPersonIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
}
