package yggc.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	@RequestMapping("registration_page")
	public String registrationPage() {
		return "sms/registration";
	}

	/**
	 * @Title: register
	 * @author: Poppet_Brook
	 * @date: 2016-9-2 下午4:49:34
	 * @Description: 跳转到注册页面
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("register_page")
	public String registerPage(HttpServletRequest request) {
		boolean flag = this.checkReferer(request, "/supplierInfo/registration_page.do");
		if (flag) {
			return "sms/register";
		}
		return "redirect:registration_page.do";
	}

	/**
	 * @Title: register
	 * @author: Poppet_Brook
	 * @date: 2016-9-5 下午4:37:39
	 * @Description: 供应商注册
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "register")
	public String register(HttpServletRequest request, SupplierInfo supplierInfo, Model model) {
		// supplierInfoService.register(supplierInfo);
		// model.addAttribute("supplierId", supplierInfoService.selectLastInsertId());
		return "sms/basic_info";
	}
	
	@RequestMapping(value = "pageJumap")
	public String pageJumap(String page) {
		return "sms/" + page;
	}

	/**
	 * @Title: checkReferer
	 * @author: Poppet_Brook
	 * @date: 2016-9-5 下午4:55:59
	 * @Description: 检查 referer
	 * @param: @param request
	 * @param: @param spaceAndRequest
	 * @param: @return
	 * @return: boolean
	 */
	public boolean checkReferer(HttpServletRequest request, String spaceAndRequest) {
		String referer = request.getHeader("referer");
		String serverName = request.getServerName();// 获取主机名
		int serverPort = request.getServerPort();// 获取端口号
		String contextPath = request.getContextPath();// 获取项目路径
		String url = "http://" + serverName + ":" + serverPort + contextPath + spaceAndRequest;
		if (referer != null && url.equals(referer)) {
			return true;
		}
		return false;
	}
}
