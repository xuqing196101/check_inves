package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierStars;
import ses.service.sms.SupplierStarsService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_stars")
public class SupplierStarsController {

	@Autowired
	private SupplierStarsService supplierStarsService;
	
	@RequestMapping(value = "list")
	public String list(Model model) {
		List<SupplierStars> list = supplierStarsService.findSupplierStars();
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				str += ",";
			}
			str += list.get(i).getStatus();
		}
		if (str.contains("1")) {
			model.addAttribute("status", "fail");
		}
		
		model.addAttribute("list", list);
		return "ses/sms/supplier_stars/list";
	}
	
	@RequestMapping(value = "add")
	public String add(Model model, SupplierStars supplierStars) {
		String id = supplierStars.getId();
		if (id != null && !"".equals(id)) {
			supplierStars = supplierStarsService.get(id);
			model.addAttribute("supplierStars", supplierStars);
		}
		return "ses/sms/supplier_stars/add_stars";
	}
	
	@RequestMapping(value = "save_or_update_supplier_stars")
	public String saveOrUpdateSupplierStars(SupplierStars supplierStars) {
		supplierStarsService.saveOrUpdateSupplierStars(supplierStars);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "update_status")
	public String updateStatus(SupplierStars supplierStars) {
		supplierStarsService.updateStatus(supplierStars);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids) {
		supplierStarsService.delete(ids);
		return "redirect:list.html";
	}

}
