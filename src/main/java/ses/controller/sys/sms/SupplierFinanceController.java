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

import common.constant.Constant;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierFinance;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_finance")
public class SupplierFinanceController extends BaseSupplierController {
	
	@Autowired
	private SupplierFinanceService supplierFinanceService;// 供应商财务信息
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@RequestMapping(value = "add_finance")
	public String addCertEng(Model model, SupplierFinance supplierFinance) {
		model.addAttribute("supplierFinance", supplierFinance);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
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
		if (multipartResolver.isMultipart(request)) {// 检查form中是否有enctype="multipart/form-data"
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;// 将request变成多部分request
			Iterator<String> its = multiRequest.getFileNames();// 获取multiRequest 中所有的文件名
			while (its.hasNext()) {// 循环遍历
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				String fileName = file.getOriginalFilename();
				if (file != null && file.getSize() > 0) {
					String path = super.getStashPath(request) + fileName;// 获取暂存路径
					file.transferTo(new File(path));// 暂存
					FtpUtil.connectFtp(PropUtil.getProperty("file.upload.path.supplier"));// 连接 ftp 服务器
					String newfileName = FtpUtil.upload(new File(path));// 上传到 ftp 服务器, 获取新的文件名
					FtpUtil.closeFtp();// 关闭 ftp
					super.removeStash(request, fileName);// 移除暂存
					
					// 上面代码固定, 下面封装名字到对象
					if (str.equals("auditOpinionFile")) {
						supplierFinance.setAuditOpinion(newfileName);
					} else if (str.equals("liabilitiesListFile")) {
						supplierFinance.setLiabilitiesList(newfileName);
					} else if (str.equals("profitListFile")) {
						supplierFinance.setProfitList(newfileName);
					} else if (str.equals("cashFlowStatementFile")) {
						supplierFinance.setCashFlowStatement(newfileName);
					} else if (str.equals("changeListFile")) {
						supplierFinance.setChangeList(newfileName);
					}
				}
			}
		}
	}
}
