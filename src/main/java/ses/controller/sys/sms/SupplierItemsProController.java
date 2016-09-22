package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierItemsPro;
import ses.service.sms.SupplierItemsProService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_items_pro")
public class SupplierItemsProController {
	
	@Autowired
	private SupplierItemsProService supplierItemsProService;
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@RequestMapping(value = "add_items_pro")
	public String addItemPro(Model model, String matProId, String supplierId) {
		model.addAttribute("matProId", matProId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_items_pro";
	}
	
	@RequestMapping(value = "save_or_update_items_pro")
	public String saveOrUpdateCertPro(HttpServletRequest request, SupplierItemsPro supplierItemsPro, String supplierId) {
		supplierItemsProService.saveOrUpdateItemsPro(supplierItemsPro);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_professional")
	public String backToProfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_items_pro")
	public String deleteCertPro(HttpServletRequest request, String itemsProIds, String supplierId) {
		supplierItemsProService.deleteItemsPro(itemsProIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
}
