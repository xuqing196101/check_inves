package yggc.controller.sys.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.sms.SupplierInfo;
import yggc.service.sms.SupplierInfoService;

@Controller
@Scope("prototype")
@RequestMapping("/supplierInfo")
public class SupplierInfoController {
	
	@Autowired
	private SupplierInfoService supplierInfoService;
	
	@RequestMapping("register.do")
	public String register(SupplierInfo supplierInfo, Model model) {
		Integer id = supplierInfoService.register(supplierInfo);
		model.addAttribute("supplierInfoId", id);
		return "index";
	}
}
