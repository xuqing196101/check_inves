package ses.controller.sys.sms;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.service.sms.SupplierBlacklistService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_blacklist")
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
	public String listBlacklist(Model model, SupplierBlacklist supplierBlacklist, Integer page,@CurrentUser User user) {
		String supplierName = supplierBlacklist.getSupplierName();
		//权限验证 登陆状态 角色只能是资源服务中心
		if(null!=user && "4".equals(user.getTypeName())){
			List<SupplierBlacklist> listSupplierBlacklists = supplierBlacklistService.findSupplierBlacklist(supplierBlacklist, page == null ? 1 : page);
			model.addAttribute("listSupplierBlacklists", new PageInfo<SupplierBlacklist>(listSupplierBlacklists));
			model.addAttribute("supplierName", supplierName);
			if (supplierBlacklist.getStartTime() != null) {
				model.addAttribute("startTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getStartTime()));
			}
			if (supplierBlacklist.getEndTime() != null) {
				model.addAttribute("endTime", new SimpleDateFormat("yyyy-MM-dd").format(supplierBlacklist.getEndTime()));
			}
		}else{
			model.addAttribute("listSupplierBlacklists", new PageInfo<SupplierBlacklist>() );
			model.addAttribute("supplierName", supplierName);
			model.addAttribute("startTime", "");
			model.addAttribute("endTime", "");
		}
		return "ses/sms/supplier_blacklist/blacklist";
	}

	@RequestMapping(value = "save_or_update_supplier_black")
	public String saveSupplierBlack(Model model,HttpServletRequest request, SupplierBlacklist supplierBlacklist) {
		User user = (User) request.getSession().getAttribute("loginUser");
		Supplier supplier = new Supplier();
		boolean flag = true;
		if(supplierBlacklist != null){
			supplier.setId(supplierBlacklist.getSupplierId());
			supplier.setSupplierName(supplierBlacklist.getSupplierName());
			if(null == supplierBlacklist.getSupplierId() || "".equals(supplierBlacklist.getSupplierId())){
				flag = false;
				model.addAttribute("error_supplier", "供应商不能为空");
			}
			if(null == supplierBlacklist.getStartTime()){
				flag = false;
				model.addAttribute("error_startTime", "起始时间不能为空");
			}
			if(supplierBlacklist.getStatus()!= null && 2 == supplierBlacklist.getStatus()){
				flag = false;
				model.addAttribute("error_supplier", "手动移除不能修改");
			}
			if(null == supplierBlacklist.getReason() || "".equals(supplierBlacklist.getReason())){
				flag = false;
				model.addAttribute("error_reason", "理由不能为空");
			}else{
				if(supplierBlacklist.getReason().length() > 800){
					flag = false;
					model.addAttribute("error_reason", "不能超过800字");
				}
			}
		}
		if(flag == true){
			supplierBlacklistService.saveOrUpdateSupplierBlack(supplierBlacklist, user);
			return "redirect:list_blacklist.html";
		}else{
			model.addAttribute("supplierBlacklist", supplierBlacklist);
			model.addAttribute("supplier", supplier);
			return "ses/sms/supplier_blacklist/add_supplier";
		}
	}

	@RequestMapping(value = "list_supplier")
	public String listSupplier(Model model, Supplier supplier, Integer page,SupplierBlacklist supplierBlacklist) {
		List<Supplier> listSuppliers = supplierBlacklistService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		return "ses/sms/supplier_blacklist/dialog_supplier";
	}
	
	@RequestMapping(value = "operator_remove")
	public String operatorRemove(HttpServletRequest request, String ids) {
		User user = (User) request.getSession().getAttribute("loginUser");
		supplierBlacklistService.operatorRemove(ids, user);
		return "redirect:list_blacklist.html";
	}
}
