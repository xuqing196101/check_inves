package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierStockholderService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_stockholder")
public class SupplierStockholderController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierStockholderService supplierStockholderService;
	
	@RequestMapping(value = "add_stockholder")
	public String addCertEng(Model model, String supplierId) {
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_stockholder";
	}
	
	@RequestMapping(value = "save_or_update_stockholder")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierStockholder supplierStockholder, String supplierId) {
		supplierStockholderService.saveOrUpdateStockholder(supplierStockholder);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_stockholder")
	public String deleteCertEng(HttpServletRequest request, String stockholderIds, String supplierId) {
		supplierStockholderService.deleteStockholder(stockholderIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
}
