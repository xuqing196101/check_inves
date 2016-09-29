package ses.controller.sys.sms;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.service.sms.SupplierBlacklistService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping(value = "supplier_blacklist")
public class SupplierBlacklistController {

	@Autowired
	private SupplierBlacklistService supplierBlacklistService;

	@RequestMapping(value = "add_supplier")
	public String addSupplier(Model model, Supplier supplier, String supplierBlacklistId) {
		if (supplierBlacklistId != null && !"".equals(supplierBlacklistId)) {
			SupplierBlacklist supplierBlacklist = supplierBlacklistService.getSupplierBlacklist(supplierBlacklistId);
			model.addAttribute("supplierBlacklist", supplierBlacklist);
			supplier.setId(supplierBlacklist.getSupplierId());
			supplier.setSupplierName(supplierBlacklist.getSupplierName());
		}
		model.addAttribute("supplier", supplier);
		return "ses/sms/supplier_blacklist/add_supplier";
	}

	@RequestMapping(value = "list_blacklist")
	public String listBlacklist(Model model, SupplierBlacklist supplierBlacklist, Integer page) {
		List<SupplierBlacklist> listSupplierBlacklists = supplierBlacklistService.findSupplierBlacklist(supplierBlacklist, page == null ? 1 : page);
		model.addAttribute("listSupplierBlacklists", new PageInfo<SupplierBlacklist>(listSupplierBlacklists));
		model.addAttribute("supplierName", supplierBlacklist.getSupplierName());
		if (supplierBlacklist.getStartTime() != null) {
			model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getStartTime()));
		}
		if (supplierBlacklist.getEndTime() != null) {
			model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getEndTime()));
		}
		return "ses/sms/supplier_blacklist/blacklist";
	}

	@RequestMapping(value = "save_or_update_supplier_black")
	public String saveSupplierBlack(SupplierBlacklist supplierBlacklist) {
		supplierBlacklistService.saveOrUpdateSupplierBlack(supplierBlacklist);
		return "redirect:list_blacklist.html";
	}

	@RequestMapping(value = "list_supplier")
	public String listSupplier(Model model, Supplier supplier, Integer page) {
		List<Supplier> listSuppliers = supplierBlacklistService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		return "ses/sms/supplier_blacklist/dialog_supplier";
	}
}
