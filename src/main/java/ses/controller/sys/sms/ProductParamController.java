package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.ProductParam;
import ses.model.sms.Supplier;
import ses.service.sms.ProductParamService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/product_param")
public class ProductParamController {
	
	@Autowired
	private ProductParamService productParamService;
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@RequestMapping(value = "save_or_update_param")
	public String saveOrUpdateParam(HttpServletRequest request, ProductParam productParam) {
		productParamService.saveOrUpdateParam(productParam);
		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
		supplier = supplierService.get(supplier.getId());
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_products")
	public String backToEngfessional(HttpServletRequest request) {
		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
		supplier = supplierService.get(supplier.getId());
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
}
