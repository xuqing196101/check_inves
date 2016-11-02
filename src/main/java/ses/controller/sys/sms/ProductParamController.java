package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String saveOrUpdateParam(HttpServletRequest request, ProductParam productParam, String productsId, String categoryId) {
		productParam.setSupplierProductsId(productsId);
		productParamService.saveOrUpdateParam(productParam);
		return "redirect:../categoryparam/list_by_category_id_and_products_id.html?categoryId=" + categoryId + "&productsId=" + productsId;
	}
	
	@RequestMapping(value = "back_to_products")
	public String backToProducts(HttpServletRequest request) {
		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
		supplier = supplierService.get(supplier.getId());
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "list")
	public String list(Model model, String productsId, String categoryId) {
		List<ProductParam> list = productParamService.findProductParam(productsId);
		model.addAttribute("list", list);
		model.addAttribute("productsId", productsId);
		model.addAttribute("categoryId", categoryId);
		return "ses/sms/supplier_register/list_product";
	}
}
