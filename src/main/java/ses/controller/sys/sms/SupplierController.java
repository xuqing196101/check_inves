package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierProductsService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.FtpUtil;
import ses.util.IdentityCode;
import ses.util.PropUtil;

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
	private SupplierTypeRelateService supplierTypeRelateService;// 供应商类型关联

	@Autowired
	private SupplierMatProService supplierMatProService;// 供应商物资生产专业信息

	@Autowired
	private SupplierMatSellService supplierMatSellService;// 供应商物资销售专业信息

	@Autowired
	private SupplierMatSeService supplierMatSeService;// 供应商服务专业信息

	@Autowired
	private SupplierMatEngService supplierMatEngService;// 供应商工程专业信息

	@Autowired
	private SupplierItemService supplierItemService;// 供应商品目
	
	@Autowired
	private SupplierProductsService supplierProductsService;// 供应商产品
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	/**
	 * @Title: getIdentity
	 * @author: Wang Zhaohua
	 * @date: 2016-10-11 下午4:59:01
	 * @Description: 获取验证码
	 * @param: @param request
	 * @param: @param response
	 * @param: @throws IOException
	 * @return: void
	 */
	@RequestMapping(value = "get_identity")
	public void getIdentity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		IdentityCode identityCode = new IdentityCode(96, 28, 4, 5);
		identityCode.write(request, response);
	}
	
	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		Supplier supplier = supplierService.get("8BE39E5BF23846EC93EED74F57ACF1F4");
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
	 * @Title: registrationPage
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
	 * @throws IOException 
	 */
	@RequestMapping(value = "prev_step")
	public String prevStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) throws IOException {
		request.getSession().removeAttribute("defaultPage");
		if (sign == 3) {
			// 保存供应商类型
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);

			// 查询供应商基本信息
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);

			// 跳转页面
			request.getSession().setAttribute("jump.page", "basic_info");
			return "redirect:page_jump.html";
		} else if (sign == 4) {
			// 保存供应商专业信息
			if (supplier.getSupplierMatPro() != null) {
				supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSell() != null) {
				supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			}
			if (supplier.getSupplierMatEng() != null) {
				supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSe() != null) {
				supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			}

			// ajax 查询供应商类型树

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "supplier_type");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息
			supplierItemService.saveSupplierItem(supplier);

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息

			// ajax查询品目树信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 8) {
			// 这里不用保存申请书模板

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
			map.clear();
			map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
			request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 9) {
			// 保存供应商附件
			this.setSupplierUpload(request, supplier);
			// 查询供应商申请表

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_download");
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
	 * @throws IOException 
	 */
	@RequestMapping(value = "stash_step")
	public String stashStep(HttpServletRequest request, Integer sign, String defaultPage, Supplier supplier) throws IOException {
		request.getSession().setAttribute("defaultPage", defaultPage);
		if (sign == 2) {
			// 保存供应商基本信息
			this.setSupplierUpload(request, supplier);
			supplierService.perfectBasic(supplier);// 保存供应商详细信息

			// 查询供应商基本信息
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);

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
			// 保存供应商专业信息
			if (supplier.getSupplierMatPro() != null) {
				supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSell() != null) {
				supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			}
			if (supplier.getSupplierMatEng() != null) {
				supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSe() != null) {
				supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			}

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "professional_info");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息
			supplierItemService.saveSupplierItem(supplier);

			// ajax 查询采购品目
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息
			

			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询机构信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
			map.clear();
			map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
			request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 9) {
			// 保存供应商附件
			this.setSupplierUpload(request, supplier);
			supplierService.perfectBasic(supplier);

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_upload");
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
	 * @throws IOException 
	 */
	@RequestMapping(value = "next_step")
	public String nextStep(HttpServletRequest request, String page, Integer sign, Supplier supplier) throws IOException {
		String realPath = request.getServletContext().getRealPath("/");
		System.out.println(realPath);
		request.getSession().removeAttribute("defaultPage");
		if (sign == 2) {// 保持供应商基本信息
			supplierService.perfectBasic(supplier);// 保存供应商详细信息

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
			if (supplier.getSupplierMatPro() != null) {
				supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSell() != null) {
				supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
			}
			if (supplier.getSupplierMatEng() != null) {
				supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
			}
			if (supplier.getSupplierMatSe() != null) {
				supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
			}
			
			// Ajax 查询品目树, 这里不用写了
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "items");
			return "redirect:page_jump.html";
		} else if (sign == 5) {
			// 保存供应商品目信息
			supplierItemService.saveSupplierItem(supplier);
			supplierProductsService.checkProducts(supplier);
			
			// 查询产品信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "products");
			return "redirect:page_jump.html";
		} else if (sign == 6) {
			// 保存供应商产品信息

			// 查询机构信息
			supplier = supplierService.get(supplier.getId());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
			map.clear();
			map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
			List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
			request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
			request.getSession().setAttribute("jump.page", "procurement_dep");
			return "redirect:page_jump.html";
		} else if (sign == 7) {
			// 保存供应商初审机构
			supplierService.updateSupplierProcurementDep(supplier);

			// 查询申请表信息

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_download");
			return "redirect:page_jump.html";
		} else if (sign == 8) {
			// 这里不用保存申请书模板

			// 查询供应商信息
			supplier = supplierService.get(supplier.getId());

			// 页面跳转
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", "template_upload");
			return "redirect:page_jump.html";
		} else if (sign == 9) {
			this.setSupplierUpload(request, supplier);
			User user = (User) request.getSession().getAttribute("loginUser");
			supplierService.commit(supplier, user);
			
			request.getSession().removeAttribute("currSupplier");
			request.getSession().removeAttribute("jump.page");
			request.getSession().removeAttribute("listOrgnizations1");
			request.getSession().removeAttribute("listOrgnizations2");
			
			return "redirect:../index/selectIndexNews.html";
		}
		return null;
	}
	
	/**
	 * @Title: searchOrg
	 * @author: Wang Zhaohua
	 * @date: 2016-10-19 下午2:28:42
	 * @Description: 查询采购机构
	 * @param: @param request
	 * @param: @param isName
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "search_org")
	public String searchOrg(HttpServletRequest request, String isName) {
		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
		List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
		map.clear();
		map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
		map.put("isName", "%" + isName + "%");
		List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);
		request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
		request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
		request.getSession().setAttribute("jump.page", "procurement_dep");
		return "redirect:page_jump.html";
	}
	
	/**
	 * @Title: download
	 * @author: Wang Zhaohua
	 * @date: 2016-10-11 下午5:01:10
	 * @Description: 文件下载
	 * @param: @param request
	 * @param: @param response
	 * @param: @param fileName
	 * @return: void
	 */
	@RequestMapping(value = "download")
	public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String stashPath = super.getStashPath(request);
		FtpUtil.startDownFile(stashPath, PropUtil.getProperty("file.upload.path.supplier"), fileName);
		FtpUtil.closeFtp();
		if (fileName != null && !"".equals(fileName)) {
			super.download(request, response, fileName);
		} else {
			super.alert(request, response, "无附件下载 !", true);
		}
		super.removeStash(request, fileName);
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
	 * @Title: setSupplierUpload
	 * @author: Wang Zhaohua
	 * @date: 2016-10-11 下午5:01:10
	 * @Description: 设置文件上传
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @throws IOException
	 * @return: void
	 */
	public void setSupplierUpload(HttpServletRequest request, Supplier supplier) throws IOException {
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
					if (str.equals("taxCertFile")) {
						supplier.setTaxCert(newfileName);
					} else if (str.equals("billCertFile")) {
						supplier.setBillCert(newfileName);
					} else if (str.equals("securityCertFile")) {
						supplier.setSecurityCert(newfileName);
					} else if (str.equals("breachCertFile")) {
						supplier.setBreachCert(newfileName);
					} else if (str.equals("supplierLevelFile")) {
						supplier.setSupplierLevel(newfileName);
					} else if (str.equals("supplierPledgeFile")) {
						supplier.setSupplierPledge(newfileName);
					} else if (str.equals("supplierRegListFile")) {
						supplier.setSupplierRegList(newfileName);
					} else if (str.equals("supplierExtractsListFile")) {
						supplier.setSupplierExtractsList(newfileName);
					} else if (str.equals("supplierInspectListFile")) {
						supplier.setSupplierInspectList(newfileName);
					} else if (str.equals("supplierReviewListFile")) {
						supplier.setSupplierReviewList(newfileName);
					} else if (str.equals("supplierChangeListFile")) {
						supplier.setSupplierChangeList(newfileName);
					} else if (str.equals("supplierExitListFile")) {
						supplier.setSupplierExitList(newfileName);
					} else if (str.equals("businessCertFile")) {
						supplier.setBusinessCert(newfileName);
					} 
				}
			}
		}
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
