package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;
import ses.service.sms.SupplierProductsService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_products")
public class SupplierProductsController extends BaseSupplierController {
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private SupplierProductsService supplierProductsService;
	
	@RequestMapping(value = "add_products")
	public String addCertEng(Model model, SupplierProducts supplierProducts) {
		supplierProducts = supplierProductsService.get(supplierProducts.getId());
		model.addAttribute("supplierProducts", supplierProducts);
		return "ses/sms/supplier_register/add_products";
	}
	
	@RequestMapping(value = "save_or_update_products")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierProducts supplierProducts) throws IOException {
		this.setUpload(request, supplierProducts);
		supplierProductsService.saveOrUpdateProducts(supplierProducts);
		Supplier supplier = supplierService.get(supplierProducts.getSupplierId());
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_products")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "products");
		return "redirect:../supplier/page_jump.html";
	}
	
	public void setUpload(HttpServletRequest request, SupplierProducts supplierProducts) throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获取multiRequest 中所有的文件名
			Iterator<String> its = multiRequest.getFileNames();
			while (its.hasNext()) {
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				String fileName = file.getOriginalFilename();
				fileName = UUID.randomUUID().toString().replace("-", "").toUpperCase().toString() + "_" + fileName;
				if (file != null && file.getSize() > 0) {
					String path = super.getFilePath(request) + fileName;
					file.transferTo(new File(path));
					if (str.equals("productPicFile")) {
						supplierProducts.setProductPic(fileName);
					} else if (str.equals("qrCodeFile")) {
						supplierProducts.setQrCode(fileName);
					}
				}
			}
		}
	}
}
