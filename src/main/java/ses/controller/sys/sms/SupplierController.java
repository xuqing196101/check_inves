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

import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.IdentityCode;
import ses.util.PropUtil;
import ses.util.ValidateUtils;

import common.constant.Constant;

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
	private SupplierMatProService supplierMatProService;// 供应商物资生产专业信息

	@Autowired
	private SupplierMatSellService supplierMatSellService;// 供应商物资销售专业信息

	@Autowired
	private SupplierMatSeService supplierMatSeService;// 供应商服务专业信息

	@Autowired
	private SupplierMatEngService supplierMatEngService;// 供应商工程专业信息

	@Autowired
	private OrgnizationServiceI orgnizationServiceI;// 机构
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private NoticeDocumentService noticeDocumentService;
	
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
	
	/**
	 * @Title: registrationPage
	 * @author: Wang Zhaohua
	 * @date: 2016-9-2 下午4:49:18
	 * @Description: 跳转到注册须知页面
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping("registration_page")
	public String registrationPage(Model model) {
		model.addAttribute("doc", noticeDocumentService.findSupplierDoc());
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
	public String register(HttpServletRequest request, Model model, Supplier supplier) {
		if (this.validateRegister(request, model, supplier)) {
			supplier = supplierService.register(supplier);
			request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.getSession().setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
			request.getSession().setAttribute("jump.page", "basic_info");
			request.getSession().setAttribute("currSupplier", supplier);
			return "redirect:page_jump.html";
		}
		model.addAttribute("supplier", supplier);
		return "ses/sms/supplier_register/register";
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
	 * @throws IOException
	 */
	@RequestMapping(value = "perfect_basic")
	public String perfectBasic(HttpServletRequest request, Supplier supplier, String jsp, String defaultPage) throws IOException {
		// this.setSupplierUpload(request, supplier);
		supplierService.perfectBasic(supplier);// 保存供应商详细信息
		supplier = supplierService.get(supplier.getId());

		if ("basic_info".equals(jsp))
			request.getSession().setAttribute("defaultPage", defaultPage);
		else
			request.getSession().removeAttribute("defaultPage");

		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:page_jump.html";

	}

	/**
	 * @Title: perfectProfessional
	 * @author: Wang Zhaohua
	 * @date: 2016-10-31 下午3:11:06
	 * @Description: 完善专业信息
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param jsp
	 * @param: @param defaultPage
	 * @param: @return
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "perfect_professional")
	public String perfectProfessional(HttpServletRequest request, Supplier supplier, String jsp, String defaultPage) throws IOException {
		request.getSession().removeAttribute("defaultPage");

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
		supplier = supplierService.get(supplier.getId());
		
		if ("professional_info".equals(jsp))
			request.getSession().setAttribute("defaultPage", defaultPage);
		else
			request.getSession().removeAttribute("defaultPage");

		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:page_jump.html";

	}
	
	/**
	 * @Title: perfectDep
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:28:53
	 * @Description: 完善审核机构信息
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param jsp
	 * @param: @return
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "perfect_dep")
	public String perfectDep(HttpServletRequest request, Supplier supplier, String jsp) {
		supplierService.updateSupplierProcurementDep(supplier);
		supplier = supplierService.get(supplier.getId());
		
		if ("template_download".equals(jsp)) {
			// 这里查询模板信息...
		}
		
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:page_jump.html";
	}
	
	/**
	 * @Title: perfectDownload
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:42:17
	 * @Description: 模板下载
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param jsp
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "perfect_download")
	public String perfectDownload(HttpServletRequest request, Supplier supplier, String jsp) {
		supplier = supplierService.get(supplier.getId());
		
		if ("template_upload".equals(jsp)) {
			Integer sysKey = Constant.SUPPLIER_SYS_KEY;
			String typeId = "";
			DictionaryData dictionaryData = new DictionaryData();
			dictionaryData.setCode("SUPPLIER_LEVEL");
			List<DictionaryData> list = dictionaryDataServiceI.find(dictionaryData);
			for (DictionaryData dd : list) {
				typeId = dd.getId();
			}
			request.getSession().setAttribute("sysKey", sysKey);
			request.getSession().setAttribute("typeId", typeId);
		}
		
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:page_jump.html";
	}
	
	/**
	 * @Title: perfectUpload
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:43:20
	 * @Description: TODO
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @param jsp
	 * @param: @return
	 * @return: String
	 * @throws IOException 
	 */
	@RequestMapping(value = "perfect_upload")
	public String perfectUpload(HttpServletRequest request, Supplier supplier, String jsp) throws IOException {
		this.setSupplierUpload(request, supplier);
		if (!"commit".equals(jsp)) {
			supplierService.perfectBasic(supplier);
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", jsp);
			return "redirect:page_jump.html";
		}
		supplierService.commit(supplier);
		request.getSession().removeAttribute("currSupplier");
		request.getSession().removeAttribute("jump.page");
		request.getSession().removeAttribute("sysKey");
		request.getSession().removeAttribute("supplierDictionaryData");
		request.getSession().removeAttribute("listOrgnizations1");
		request.getSession().removeAttribute("listOrgnizations2");
		return "redirect:../index/selectIndexNews.html";
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
		if (page == null || "".equals(page)) {
			page = "registration";
		}
		return "ses/sms/supplier_register/" + page;
	}
	
	@RequestMapping(value = "check_login_name")
	public void checkLoginName(HttpServletResponse response, String loginName) {
		boolean flag = supplierService.checkLoginName(loginName);
		String msg = "";
		if (flag) {
			msg = "{\"msg\":\"success\"}";
		} else {
			msg = "{\"msg\":\"fail\"}";
		}
		super.writeJson(response, msg);
	}
	
	@RequestMapping(value = "return_edit")
	public String returnEdit(HttpServletRequest request, Supplier supplier) {
		supplier = supplierService.get(supplier.getId());
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:page_jump.html";
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

	public boolean validateRegister(HttpServletRequest request, Model model, Supplier supplier) {
		String identifyCode = (String) request.getSession().getAttribute("img-identity-code");// 验证码
		int count = 0;
		if (supplier.getLoginName() == null || !supplier.getLoginName().matches("^\\w{6,20}$")) {
			model.addAttribute("err_msg_loginName", "登录名由6-20位字母数字和下划线组成 !");
			count++;
		}
		if (supplier.getPassword() == null || !supplier.getPassword().matches("^\\w{6,20}$")) {
			model.addAttribute("err_msg_password", "密码由6-20位字母数字和下划线组成 !");
			count++;
		}
		if (!supplier.getPassword().equals(supplier.getConfirmPassword())) {
			model.addAttribute("err_msg_ConfirmPassword", "密码和重复密码不一致 !");
			count++;
		}
		if (supplier.getMobile() == null || !supplier.getMobile().matches("^1[0-9]{10}$")) {
			model.addAttribute("err_msg_mobile", "手机格式不正确 !");
			count++;
		}
		if (supplier.getMobileCode() == null) {
			model.addAttribute("err_msg_mobileCode", "手机验证码错误 !");
			count++;
		}
		if (supplier.getIdentifyCode() == null || !supplier.getIdentifyCode().equals(identifyCode)) {
			model.addAttribute("err_msg_code", "验证码错误 !");
			count++;
		}
		if (count > 0) {
			return false;
		}
		return true;
	}

	public boolean validateBasicInfo(HttpServletRequest request, Model model, Supplier supplier) {
		int count = 0;
		if (supplier.getSupplierName() == null || !supplier.getSupplierName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_supplierName", "必填项 !");
			count++;
		}
		if (supplier.getWebsite() == null || !ValidateUtils.Url(supplier.getWebsite())) {
			model.addAttribute("err_msg_website", "格式错误 !");
			count++;
		}
		if (supplier.getFoundDate() == null) {
			model.addAttribute("err_msg_foundDate", "必填项 !");
			count++;
		}
		if (supplier.getAddress() == null || supplier.getAddress().split(",").length != 2) {
			model.addAttribute("err_msg_address", "必填项 !");
			count++;
		}
		if (supplier.getBankName() == null || !supplier.getBankName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_bankName", "必填项 !");
			count++;
		}
		if (supplier.getBankAccount() == null || !supplier.getBankAccount().matches("^\\d{16}||\\d{19}$")) {
			model.addAttribute("err_msg_bankAccount", "必填项 !");
			count++;
		}
		if (supplier.getPostCode() == null || !ValidateUtils.Zipcode(supplier.getPostCode())) {
			model.addAttribute("err_msg_postCode", "格式错误 !");
			count++;
		}
		if (count > 0) {
			return false;
		}
		return true;
	}

}
