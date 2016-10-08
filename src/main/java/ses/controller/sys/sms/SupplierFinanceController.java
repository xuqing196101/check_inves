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
import ses.model.sms.SupplierFinance;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_finance")
public class SupplierFinanceController extends BaseSupplierController {
	
	@Autowired
	private SupplierFinanceService supplierFinanceService;// 供应商财务信息
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@RequestMapping(value = "add_finance")
	public String addCertEng(Model model, String supplierId) {
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/add_finance";
	}
	
	@RequestMapping(value = "save_or_update_finance")
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierFinance supplierFinance, String supplierId) throws IOException {
		this.setFinanceUpload(request, supplierFinance);
		supplierFinanceService.saveOrUpdateFinance(supplierFinance);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_finance")
	public String deleteCertEng(HttpServletRequest request, String financeIds, String supplierId) {
		supplierFinanceService.deleteFinance(financeIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-2");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	public void setFinanceUpload(HttpServletRequest request, SupplierFinance supplierFinance) throws IOException {
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
					if (str.equals("auditOpinionFile")) {
						supplierFinance.setAuditOpinion(fileName);
					} else if (str.equals("liabilitiesListFile")) {
						supplierFinance.setLiabilitiesList(fileName);
					} else if (str.equals("profitListFile")) {
						supplierFinance.setProfitList(fileName);
					} else if (str.equals("cashFlowStatementFile")) {
						supplierFinance.setCashFlowStatement(fileName);
					} else if (str.equals("changeListFile")) {
						supplierFinance.setChangeList(fileName);
					}
				}
			}
		}
	}
}
