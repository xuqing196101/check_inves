package ses.controller.sys.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierStockholderService;
import ses.service.sms.SupplierTypeRelateService;

/**
 * @Title: supplierController
 * @Description: 供应商信息 Controller
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午1:39:22
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplier")
public class SupplierController extends BaseSupplierController {

	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierFinanceService supplierFinanceService;// 供应商财务信息

	@Autowired
	private SupplierStockholderService supplierStockholderService;// 供应商股东信息

	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;// 供应商类型关联
	
	@Autowired
	private SupplierMatProService supplierMatProService;// 供应商物资生产专业信息
	
	@Autowired
	private SupplierMatSellService supplierMatSellService;

	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		Supplier supplier = supplierService.get("53BF9E64B38B46228914807B92BAE812");
		model.addAttribute("currSupplier", supplier);
		if (supplier.getListSupplierFinances() != null) {
			model.addAttribute("financeSize", supplier.getListSupplierFinances().size());
		}
		if (supplier.getListSupplierStockholders() != null) {
			model.addAttribute("stockholderSize", supplier.getListSupplierStockholders().size());
		}
		request.getSession().setAttribute("supplierId", supplier.getId());
		return "ses/sms/supplier_register/basic_info";
	}

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
		return "ses/sms/supplier_register/registration";
	}

	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-2 下午4:49:34
	 * @Description: 跳转到注册页面
	 * @param: @param supplier
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("register_page")
	public String registerPage(HttpServletRequest request) {
		boolean flag = this.checkReferer(request, "/supplier/registration_page.html");
		if (flag) {
			return "ses/sms/supplier_register/register";
		}
		return "redirect:registration_page.html";
	}

	/**
	 * @Title: register
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:37:39
	 * @Description: 供应商注册
	 * @param: @param supplier
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "register")
	public String register(HttpServletRequest request, Supplier supplier) {
		supplier = supplierService.register(supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		request.getSession().setAttribute("currSupplier", supplier);
		return "redirect:page_jump.html";
	}

	/**
	 * @Title: prevStep
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:58:40
	 * @Description: 供应商信息完善上一步
	 * @param: @param page
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "prev_step")
	public String prevStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) {
		if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 查询供应商基本信息
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			if (supplier.getListSupplierFinances() != null) {
				request.getSession().setAttribute("financeSize", supplier.getListSupplierFinances().size());
			}
			if (supplier.getListSupplierStockholders() != null) {
				request.getSession().setAttribute("stockholderSize", supplier.getListSupplierStockholders().size());
			}

			// 跳转页面
			request.getSession().setAttribute("jump.page", "basic_info");
			return "redirect:page_jump.html";
		}
		return null;
	}

	/**
	 * @Title: stashStep
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:57:52
	 * @Description: 供应商信息完善暂存当前步
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "stash_step")
	public String stashStep(HttpServletRequest request, Integer sign, String defaultPage, Supplier supplier) {
		request.getSession().setAttribute("defaultPage", defaultPage);
		if (sign == 2) {
			// 保持供应商基本信息
			String id = supplier.getId();
			supplierService.perfectBasic(supplier);// 保存供应商详细信息
			supplierFinanceService.saveFinance(supplier);// 保存供应商财务信息
			supplierStockholderService.saveStockholder(supplier);// 保存供应商股东信息

			// 查询供应商基本信息
			supplier = supplierService.get(id);
			request.getSession().setAttribute("currSupplier", supplier);
			if (supplier.getListSupplierFinances() != null) {
				request.getSession().setAttribute("financeSize", supplier.getListSupplierFinances().size());
			}
			if (supplier.getListSupplierStockholders() != null) {
				request.getSession().setAttribute("stockholderSize", supplier.getListSupplierStockholders().size());
			}

			// 页面跳转
			request.getSession().setAttribute("jump.page", "basic_info");
			return "redirect:page_jump.html";
		} else if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 跳转页面
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商物资生产专业信息
			supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			
			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());
			
			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		}
		return null;
	}

	/**
	 * @Title: next_step
	 * @author: Wang Zhaohua
	 * @date: 2016-9-12 下午2:57:06
	 * @Description: 供应商信息完善下一步
	 * @param: @param page
	 * @param: @param sign
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "next_step")
	public String nextStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) {
		if (sign == 2) {// 保持供应商基本信息
			supplierService.perfectBasic(supplier);// 保存供应商详细信息
			supplierFinanceService.saveFinance(supplier);// 保存供应商财务信息
			supplierStockholderService.saveStockholder(supplier);// 保存供应商股东信息
			
			// Ajax 查询供应商类型树, 这里不用写了

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);
			
			// 查询专业信息
			supplier = supplierService.get(supplier.getId());
			
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商专业信息
			
			// Ajax 查询品目树, 这里不用写了
			
			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		}
		return null;
	}
	
	/**
	 * @Title: basic
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午4:47:37
	 * @Description: 完善基本信息
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param model
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "perfect_basic")
	public String perfectBasic(HttpServletRequest request, Supplier supplier) {

		String id = supplier.getId();
		if (id == null || "".equals(id)) {
			id = (String) request.getSession().getAttribute("supplierId");
			supplier.setId(id);
		}
		supplierService.perfectBasic(supplier);// 保存供应商详细信息
		supplierFinanceService.saveFinance(supplier);// 保存供应商财务信息
		supplierStockholderService.saveStockholder(supplier);// 保存供应商股东信息
		request.getSession().setAttribute("supplierId", id);
		request.getSession().setAttribute("jump.page", "supplier_type");
		return "redirect:page_jump.html";
	}

	/**
	 * @Title: page_jump
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:43:49
	 * @Description: page_jump
	 * @param: @param request
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "page_jump")
	public String pageJump(HttpServletRequest request) {
		String page = (String) request.getSession().getAttribute("jump.page");
		return "ses/sms/supplier_register/" + page;
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

	public void checkSupplier(Supplier supplier) {
		/*
		 * List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances(); for (SupplierFinance supplierFinance : listSupplierFinances) { boolean flag1 = supplierFinance.getName() == null || "".equals(supplierFinance.getName()); boolean flag2 = supplierFinance.getTelephone() == null || "".equals(supplierFinance.getTelephone()); boolean flag3 = supplierFinance.getAuditors() == null || "".equals(supplierFinance.getAuditors()); boolean flag4 = supplierFinance.getQuota() == null || "".equals(supplierFinance.getQuota()); boolean flag5 = supplierFinance.getTotalAssets() == null || "".equals(supplierFinance.getTotalAssets()); boolean flag6 = supplierFinance.getTotalLiabilities() == null || "".equals(supplierFinance.getTotalLiabilities()); boolean flag7 = supplierFinance.getName() == null || "".equals(supplierFinance.getName());
		 * 
		 * }
		 */
	}
}
