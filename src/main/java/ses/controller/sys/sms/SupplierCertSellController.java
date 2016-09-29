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
import ses.model.sms.SupplierCertSell;
import ses.service.sms.SupplierCertSellService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_cert_sell")
public class SupplierCertSellController extends BaseSupplierController {

	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierCertSellService supplierCertSellService;

	@RequestMapping(value = "add_cert_sell")
	public String addCertSell(Model model, String matSellId, String supplierId) {
		model.addAttribute("matSellId", matSellId);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_cert_sell";
	}

	@RequestMapping(value = "save_or_update_cert_sell")
	public String saveOrUpdateCertSell(HttpServletRequest request, SupplierCertSell supplierCertSell, String supplierId) throws IOException {
		this.setCertSellUpload(request, supplierCertSell);
		supplierCertSellService.saveOrUpdateCertSell(supplierCertSell);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "delete_cert_sell")
	public String deleteCertSell(HttpServletRequest request, String certSellIds, String supplierId) {
		supplierCertSellService.deleteCertSell(certSellIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	public void setCertSellUpload(HttpServletRequest request, SupplierCertSell supplierCertSell) throws IOException {
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
					if (str.equals("attachFile")) {
						supplierCertSell.setAttach(fileName);
					} 
				}
			}
		}
	}
}
