package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_type_relate")
public class SupplierTypeRelateController {
	
	/** 供应商关联类型 */
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	
	/** 供应商 */
	@Autowired
	private SupplierService supplierService;
	
	
	@RequestMapping(value = "perfect_type")
	public String perfectType(HttpServletRequest request, Supplier supplier, String jsp) {
		supplierTypeRelateService.saveSupplierTypeRelate(supplier);
		
		supplier = supplierService.get(supplier.getId());
		
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:../supplier/page_jump.html";
	}
	
}
