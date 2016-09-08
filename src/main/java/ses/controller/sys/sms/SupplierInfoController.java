package ses.controller.sys.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.SupplierInfo;
import ses.service.sms.SupplierInfoService;


/**
 * @Title: SupplierInfoController
 * @Description: 供应商信息 Controller
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午1:39:22
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierInfo")
public class SupplierInfoController {

	@Autowired
	private SupplierInfoService supplierInfoService;

	/**
	 * @Title: instruct
	 * @author: Wang Zhaohua
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
	 * @author: Wang Zhaohua
	 * @date: 2016-9-2 下午4:49:34
	 * @Description: 跳转到注册页面
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("register_page")
	public String registerPage(HttpServletRequest request) {
		boolean flag = this.checkReferer(request, "/supplierInfo/registration_page.html");
		if (flag) {
			return "sms/register";
		}
		return "redirect:registration_page.html";
	}

	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:37:39
	 * @Description: 供应商注册
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "register")
	public String register(HttpServletRequest request, SupplierInfo supplierInfo) {
		String id = supplierInfoService.register(supplierInfo);
		request.getSession().setAttribute("jump.page", "basic_info");
		request.getSession().setAttribute("supplierId", id);
		return "redirect:pageJump.html";
	}
	
	
	/**
	 * @Title: basic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午4:47:37
	 * @Description: 完善基本信息
	 * @param: @param request
	 * @param: @param supplierInfo
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "perfect_basic")
	public String perfectBasic(HttpServletRequest request, SupplierInfo supplierInfo) {
		String id = (String) request.getSession().getAttribute("supplierId");
		supplierInfo.setId(id);
		return "redirect:pageJump.html";
	}
	
	/**
	 * @Title: pageJump
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:43:49
	 * @Description: pageJump
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "pageJump")
	public String pageJump(HttpServletRequest request) {
		String page = (String) request.getSession().getAttribute("jump.page");
		return "sms/" + page;
	}

	/**
	 * @Title: checkReferer
	 * @author: Wang Zhaohua
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
	
	/**
	 * @Title: initBinder
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:44:05
	 * @Description: initBinder
	 * @param: @param binder
	 * @return: void
	 */
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}
}
