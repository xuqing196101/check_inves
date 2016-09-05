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
	
	/**
	 * @Title: instruct
	 * @author: Poppet_Brook
	 * @date: 2016-9-2 下午4:49:18
	 * @Description: 跳转到注册须知页面
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("registration_page.do")
	public String registrationPage() {
		return "sms/registration";
	}
	
	/**
	 * @Title: register
	 * @author: Poppet_Brook
	 * @date: 2016-9-2 下午4:49:34
	 * @Description: 跳转到主页页面
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("register_page.do")
	public String registerPage() {
		return "sms/register";
	}
	
	@RequestMapping("register.do")
	public String register(SupplierInfo supplierInfo, Model model) {
		//Integer id = supplierInfoService.register(supplierInfo);
		//model.addAttribute("supplierInfoId", id);
		return "sms/basic_info";
	}
}
