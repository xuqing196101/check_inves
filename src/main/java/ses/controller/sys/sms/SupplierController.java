package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.IdentityCode;
import ses.util.PropUtil;
import ses.util.ValidateUtils;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;

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
	
	@Autowired
	private CategoryService categoryService;
	
	
	/** 供应商关联类型 */
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;
	
	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private SupplierItemService supplierItemService;
	
	
	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;// 供应商财务信息
	
	
	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;//股东信息
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
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("docType", "供应商须知文档");
		model.addAttribute("doc", noticeDocumentService.findDocByMap(param));
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
 
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		request.setAttribute("id",id);
		
		return "ses/sms/supplier_register/register";
 
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
		Supplier sup = supplierService.selectById(supplier.getId());
		boolean bool = validateRegister(request, model, supplier);
		if (bool==true&&sup==null) {
			supplier = supplierService.register(supplier);
			request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
 
			request.setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/basic_info";
		}
		if(sup!=null){
			List<SupplierFinance> finace = supplierFinanceMapper.findFinanceBySupplierId(supplier.getId());
			if(finace!=null&&finace.size()>0){
				supplier.setListSupplierFinances(finace);
			}
			List<SupplierStockholder> stock = supplierStockholderMapper.findStockholderBySupplierId(supplier.getId());
			if(stock!=null&&stock.size()>0){
				supplier.setListSupplierStockholders(stock);
			}
			request.setAttribute("currSupplier", supplier);
			request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
			return "ses/sms/supplier_register/basic_info";
		}
		else{
			List<SupplierFinance> finace = supplierFinanceMapper.findFinanceBySupplierId(supplier.getId());
			if(finace!=null&&finace.size()>0){
				supplier.setListSupplierFinances(finace);
			}
			List<SupplierStockholder> stock = supplierStockholderMapper.findStockholderBySupplierId(supplier.getId());
			if(stock!=null&&stock.size()>0){
				supplier.setListSupplierStockholders(stock);
			}
			request.setAttribute("id",supplier.getId());
			// Supplier supp = supplierService.get(supplier.getId());
			model.addAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/register";
		}
	
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
	public String searchOrg(HttpServletRequest request, String id) {
		Supplier supplier = (Supplier) request.getSession().getAttribute("currSupplier");
		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("name", "%" + supplier.getAddress().split(",")[0] + "%");
		map.put("provinceId",id);
		List<Orgnization> listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
//		map.clear();
//		map.put("notName", "%" + supplier.getAddress().split(",")[0] + "%");
//		map.put("isName", "%" + isName + "%");
//		List<Orgnization> listOrgnizations2 = orgnizationServiceI.findOrgnizationList(map);
		request.getSession().setAttribute("listOrgnizations1", listOrgnizations1);
//		request.getSession().setAttribute("listOrgnizations2", listOrgnizations2);
//		request.getSession().setAttribute("jump.page", "procurement_dep");
		return "ses/sms/supplier_register/procurement_dep";
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
	public String perfectBasic(HttpServletRequest request,Model model, Supplier supplier,String flag) throws IOException {
		// this.setSupplierUpload(request, supplier);
	//	supplierService.perfectBasic(supplier);// 保存供应商详细信息
//		supplier = supplierService.get(supplier.getId());
		boolean info = validateBasicInfo(request,model,supplier);
		List<SupplierTypeRelate> relate = supplierTypeRelateService.queryBySupplier(supplier.getId());
		request.getSession().setAttribute("relate", relate);
		if(flag==null){
			flag="3";
		}
		if(flag.equals("1")&&info==true){
			supplierService.perfectBasic(supplier);
			supplier = supplierService.get(supplier.getId());
			DictionaryData dd=new DictionaryData();
			dd.setKind(6);
			List<DictionaryData> list = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("supplieType", list);
			DictionaryData dd2=new DictionaryData();
			dd2.setKind(8);
			List<DictionaryData> wlist = dictionaryDataServiceI.find(dd2);
			request.getSession().setAttribute("wlist", wlist);
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/supplier_type";
			
		}else if(flag.equals("2")){
			supplierService.perfectBasic(supplier);
//			supplier = supplierService.get(supplier.getId());
			supplier = supplierService.get(supplier.getId());
			DictionaryData dd=new DictionaryData();
			dd.setKind(6);
			List<DictionaryData> list = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("supplieType", list);
			DictionaryData dd2=new DictionaryData();
			dd2.setKind(8);
			List<DictionaryData> wlist = dictionaryDataServiceI.find(dd2);
			request.getSession().setAttribute("wlist", wlist);
			request.getSession().setAttribute("currSupplier", supplier);
			
			request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
			
			return "ses/sms/supplier_register/basic_info";
		}else{
			// supplier = supplierService.get(supplier.getId());
			DictionaryData dd=new DictionaryData();
			dd.setKind(6);
			List<DictionaryData> list = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("supplieType", list);
			DictionaryData dd2=new DictionaryData();
			dd2.setKind(8);
			List<DictionaryData> wlist = dictionaryDataServiceI.find(dd2);
			request.getSession().setAttribute("wlist", wlist);
			request.getSession().setAttribute("currSupplier", supplier);
			
			request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
			return "ses/sms/supplier_register/basic_info";
		}
 

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
	public String perfectProfessional(HttpServletRequest request, Supplier supplier, String flag,Model model) throws IOException {
	 
	
		boolean info=true;
		boolean sale=true;
		boolean pro=true;
		boolean server=true;
		boolean project=true;
		if(flag==null){
			flag="refesh";
		}
		if(flag.equals("3")){
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/basic_info";
		}
			
		
		String[] str = supplier.getSupplierTypeIds().trim().split(",");
		for(String s:str){
			if (s.equals("PRODUCT")) {
				pro = validatePro(request, supplier.getSupplierMatPro(), model);
				  if(info==true){
					  supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
				  }
			} 
			if (s.equals("SALES")) {
				sale = validateSale(request, supplier.getSupplierMatSell(), model);
				  if(info==true){
					  supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
				  }
			}
			if (s.equals("PROJECT")) {
				project = validateEng(request, supplier.getSupplierMatEng(), model);
				  if(info==true){
					  supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
				  }
			}
			if (s.equals("SERVICE")) {
				server = validateServer(request, supplier.getSupplierMatSe(), model);
				  if(info==true){
					  supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
				  }
			}
		}
		supplierTypeRelateService.saveSupplierTypeRelate(supplier);
		supplier = supplierService.get(supplier.getId());
 

		request.getSession().setAttribute("currSupplier", supplier);
 
		if(pro==true&&server==true&&project==true&&sale==true){
			if(flag.equals("3")){
				return "ses/sms/supplier_register/basic_info";
			}
			else if(flag.equals("2")){
				return "ses/sms/supplier_register/supplier_type";	
			}else{
				return "ses/sms/supplier_register/products";
			}
		}else{
			return "ses/sms/supplier_register/supplier_type";	
		}
		
		
		

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
	public String perfectDep(HttpServletRequest request, Supplier supplier, String flag) {
	
//		request.getSession().setAttribute("jump.page", jsp);
		if(flag.equals("next")){
			supplierService.updateSupplierProcurementDep(supplier);
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/template_download";
		}else if(flag.equals("store")){
			supplierService.updateSupplierProcurementDep(supplier);
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/procurement_dep";
		}else{
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/products";
		}
		
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
	public String perfectDownload(HttpServletRequest request, Supplier supplier, String jsp,String flag) {
		supplier = supplierService.get(supplier.getId());
		
		if ("next".equals(flag)) {
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
			return "ses/sms/supplier_register/template_upload";
		} else{
			request.getSession().setAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/procurement_dep";
		}
		
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
	public String perfectUpload(HttpServletRequest request, Supplier supplier, String jsp,String flag) throws IOException {
		this.setSupplierUpload(request, supplier);
		if (!"commit".equals(jsp)) {
			supplierService.perfectBasic(supplier);
			supplier = supplierService.get(supplier.getId());
			request.getSession().setAttribute("currSupplier", supplier);
			request.getSession().setAttribute("jump.page", jsp);
			return "ses/sms/supplier_register/template_upload";
		}
		supplierService.commit(supplier);
		request.getSession().removeAttribute("currSupplier");
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

	//注册登记校验
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
		if (supplier.getConfirmPassword()==null||!supplier.getPassword().equals(supplier.getConfirmPassword())) {
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

	 //基本信息校验
	public boolean validateBasicInfo(HttpServletRequest request, Model model, Supplier supplier) {
		int count = 0;
		if (supplier.getSupplierName() == null || !supplier.getSupplierName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_supplierName", "不能为空!");
			count++;
		}
		if (supplier.getWebsite() == null || !ValidateUtils.Url(supplier.getWebsite())) {
			model.addAttribute("err_msg_website", "格式错误 !");
			count++;
		}
		if (supplier.getFoundDate() == null) {
			model.addAttribute("err_msg_foundDate", "不能为空 !");
			count++;
		}
		if (supplier.getAddress() == null) {
			model.addAttribute("err_msg_address", "不能为空!");
			count++;
		}
		if (supplier.getBankName() == null || !supplier.getBankName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_bankName", "不能为空 !");
			count++;
		}
		if (supplier.getBankAccount() == null || !supplier.getBankAccount().matches("^\\d{16}||\\d{19}$")) {
			model.addAttribute("err_msg_bankAccount", "格式不正确 !");
			count++;
		}
		if (supplier.getPostCode() == null || !ValidateUtils.Zipcode(supplier.getPostCode())) {
			model.addAttribute("err_msg_postCode", "格式不正确 !");
			count++;
		}
		if(supplier.getLegalName()==null){
			model.addAttribute("err_legalName", "不能为空 !");
			count++;
		}
		if(supplier.getLegalIdCard()==null ){
			model.addAttribute("err_legalCard", "不能为空 !");
			count++;
		}
		if(supplier.getLegalIdCard()!=null && !supplier.getLegalIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")){
			model.addAttribute("err_legalCard", "身份证号码格式不正确 !");
			count++;
		}
		if(supplier.getLegalMobile()==null){
			model.addAttribute("err_legalMobile", "不能为空 !");
			count++;
		}
	/*	if(supplier.getLegalMobile()!=null&&!supplier.getLegalMobile().matches("^(0[1-9]{2})-\\d{8}$|^(0[1-9]{3}-(\\d{7,8}))$")){
			model.addAttribute("err_legalMobile", "固话格式不正确 !");
			count++;
		}*/
		if(supplier.getLegalTelephone()==null||!supplier.getLegalTelephone().matches("^1[0-9]{10}$")){
			model.addAttribute("err_legalPhone", "格式不正确 !");
			count++;
		}
		if(supplier.getContactName()==null){
			model.addAttribute("err_conName", "不能为空 !");
			count++;
		}
		
		if(supplier.getContactFax()==null){
			model.addAttribute("err_fax", "格式不正确 !");
			count++;
		}
		
		if(supplier.getContactMobile()==null){
			model.addAttribute("err_catMobile", "格式不正确 !");
			count++;
		}
		if(supplier.getContactTelephone()==null||!supplier.getContactTelephone().matches("^1[0-9]{10}$")){
			model.addAttribute("err_catTelphone", "格式不正确 !");
			count++;
		}
		if(supplier.getContactEmail()==null||!supplier.getContactEmail().matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")){
			model.addAttribute("err_catEmail", "格式不正确 !");
			count++;
		}
		if(supplier.getContactAddress()==null){
			model.addAttribute("err_conAddress", "不能为空!");
			count++;
		}
		if(supplier.getCreditCode()==null){
			model.addAttribute("err_creditCide", "不能为空!");
			count++;
		}
		
		if(supplier.getRegistAuthority()==null){
			model.addAttribute("err_reAuthoy", "不能为空 !");
			count++;
		}
		if(supplier.getRegistFund()==null){
			model.addAttribute("err_fund", "不能为空 !");
			count++;
		}
		if(supplier.getRegistFund()!=null&&!supplier.getRegistFund().toString().matches("^\\d+?\\d+(\\.\\d+)?$")){
			model.addAttribute("err_fund", "资金不能小于0或者是格式不正确 !");
			count++;
		}
		if(supplier.getBusinessStartDate()==null){
			model.addAttribute("err_sDate", "营业开始时间不能为空 !");
			count++;
		}
		if(supplier.getBusinessEndDate()==null){
			model.addAttribute("err_eDate", "营业截至时间不能为空 !");
			count++;
		}
		if(supplier.getBusinessAddress()==null){
			model.addAttribute("err_bAddress", "经营地址不能为空!");
			count++;
		}
		if(supplier.getBusinessPostCode()==null){
			model.addAttribute("err_bCode", "不能为空!");
			count++;
		}
		if(supplier!=null&&supplier.getBusinessPostCode()!=null&&!ValidateUtils.Zipcode(supplier.getBusinessPostCode().toString())){
			model.addAttribute("err_bCode", "邮编格式不正确!");
			count++;
		}
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		//* 近三个月完税凭证
		List<UploadFile> tlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierTaxCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(tlist!=null&&tlist.size()<=0){
			count++;
			model.addAttribute("err_taxCert", "请上传文件!");
		}
		//* 近三年银行基本账户年末对账单
		List<UploadFile> blist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBillCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(blist!=null&&blist.size()<=0){
			 count++;
			model.addAttribute("err_bil", "请上传文件!");
		}
		//近三个月缴纳社会保险金凭证
		List<UploadFile> slist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierSecurityCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(slist!=null&&slist.size()<=0){
		 	count++;
			model.addAttribute("err_security", "请上传文件!");
		}
		//近三年内无重大违法记录声明
		List<UploadFile> bearlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBearchCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(bearlist!=null&&bearlist.size()<=0){
			 count++;
			model.addAttribute("err_bearch", "请上传文件!");
		}
		
		//供应商执照
		List<UploadFile> list = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBusinessCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(list!=null&&list.size()<=0){
			 count++;
			model.addAttribute("err_business", "请上传文件!");
		}
		List<SupplierFinance> finace = supplierFinanceMapper.findFinanceBySupplierId(supplier.getId());
		if(finace!=null&&finace.size()<1){
			    count++;
				model.addAttribute("finace", "请添加财务信息!");
		}
		List<SupplierStockholder> stock = supplierStockholderMapper.findStockholderBySupplierId(supplier.getId());
		if(stock!=null&&stock.size()<1){
		    count++;
			model.addAttribute("stock", "请添加股东信息!");
	}
		
		if (count > 0) {
			return false;
		}
		return true;
	}

	//生产信息校验
	public boolean validatePro(HttpServletRequest request,SupplierMatPro supplierMatPro,Model model){
		boolean bool=true;
		if(supplierMatPro.getOrgName()==null){
			model.addAttribute("org", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()==null){
			model.addAttribute("person", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
			model.addAttribute("person", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()==null){
			model.addAttribute("mange", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
			model.addAttribute("mange", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()==null){
			model.addAttribute("tech", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
			model.addAttribute("tech", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()==null){
			model.addAttribute("work", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
			model.addAttribute("work", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getScaleTech()==null){
			model.addAttribute("stech", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getScaleTech()!=null&&!supplierMatPro.getScaleTech().matches("^[-+]?\\d+(\\.\\d+)?$")){
			model.addAttribute("stech", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getScaleHeightTech()==null){
			model.addAttribute("height", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getScaleHeightTech()!=null&&!supplierMatPro.getScaleHeightTech().matches("^[-+]?\\d+(\\.\\d+)?$")){
			model.addAttribute("height", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getResearchName()==null){
			model.addAttribute("reName", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalResearch()==null){
			model.addAttribute("tRe", "不能为空");
			bool=false;
		}
		
		if(supplierMatPro.getTotalResearch()!=null&&!supplierMatPro.getTotalResearch().toString().matches("^[0-9]*$")){
			model.addAttribute("tRe", "只能输入整数");
			bool=false;
		}
		if(supplierMatPro.getResearchLead()==null){
			model.addAttribute("leader", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getCountryPro()==null){
			model.addAttribute("contry", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getCountryReward()==null){
			model.addAttribute("reward", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalBeltline()==null){
			model.addAttribute("line", "不能为空");
			bool=false;	
		}
		if(supplierMatPro.getTotalBeltline()!=null&&!supplierMatPro.getTotalBeltline().toString().matches("^[0-9]*$")){
			model.addAttribute("line", "只能输入整数");
			bool=false;	
		}
		if(supplierMatPro.getTotalDevice()==null){
			model.addAttribute("device", "不能为空");
			bool=false;	
		}
		if(supplierMatPro.getTotalDevice()!=null&&!supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")){
			model.addAttribute("device", "格式正确");
			bool=false;	
		}
		if(supplierMatPro.getQcName()==null){
			model.addAttribute("qcName", "不能为空");
			bool=false;	
		}
		if(supplierMatPro.getTotalQc()==null){
			model.addAttribute("tQc", "不能为空");
			bool=false;	
		}
		if(supplierMatPro.getTotalQc()!=null&&!supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")){
			model.addAttribute("tQc", "格式不正确");
			bool=false;	
		}
		if(supplierMatPro.getQcLead()==null){
			model.addAttribute("tqcLead", "不能为空");
			bool=false;	
		}
		if(supplierMatPro.getQcDevice()==null){
			model.addAttribute("tqcDevice", "不能为空");
			bool=false;	
		}
		
		return bool;
	}
	
	//销售信息校验
	public boolean validateSale(HttpServletRequest request,SupplierMatSell supplierMatPro,Model model){
		boolean bool=true;
		if(supplierMatPro.getOrgName()==null){
			model.addAttribute("sale_org", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()==null){
			model.addAttribute("sale_person", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
			model.addAttribute("sale_person", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()==null){
			model.addAttribute("sale_mange", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
			model.addAttribute("sale_mange", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()==null){
			model.addAttribute("sale_tech", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
			model.addAttribute("sale_tech", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()==null){
			model.addAttribute("sale_work", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
			model.addAttribute("sale_work", "格式不正确");
			bool=false;
		}
		
		return bool;
	}
	//工程信息校验
	public boolean validateEng(HttpServletRequest request,SupplierMatEng supplierMatPro,Model model){
		boolean bool=true;
		if(supplierMatPro.getOrgName()==null){
			model.addAttribute("eng_org", "不能为空");
			bool=false;
		}
	   if(supplierMatPro.getTotalTech()==null||!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
			model.addAttribute("eng_tech", "不能为空或者不是数字类型");
			bool=false;
	   }
	   if(supplierMatPro.getTotalGlNormal()==null||!supplierMatPro.getTotalGlNormal().toString().matches("^[0-9]*$")){
		   model.addAttribute("eng_normal", "不能为空或者不是数字类型");
			bool=false;
	   }
	   
	   if(supplierMatPro.getTotalMange()==null||!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
		   model.addAttribute("eng_manage", "不能为空或者不是数字类型");
			bool=false;
	   }
	   if(supplierMatPro.getTotalTechWorker()==null||!supplierMatPro.getTotalTechWorker().toString().matches("^[0-9]*$")){
		   model.addAttribute("eng_worker", "不能为空或者不是数字类型");
			bool=false;
	   }
		
		return bool;
	}
	//服务信息校验
	public boolean validateServer(HttpServletRequest request,SupplierMatServe supplierMatPro,Model model){
		boolean bool=true;
		if(supplierMatPro.getOrgName()==null){
			model.addAttribute("fw_org", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()==null){
			model.addAttribute("fw_person", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalPerson()!=null&&!supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")){
			model.addAttribute("fw_person", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()==null){
			model.addAttribute("fw_mange", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalMange()!=null&&!supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")){
			model.addAttribute("fw_mange", "人员必须是整数");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()==null){
			model.addAttribute("fw_tech", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalTech()!=null&&!supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")){
			model.addAttribute("fw_tech", "格式不正确");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()==null){
			model.addAttribute("fw_work", "不能为空");
			bool=false;
		}
		if(supplierMatPro.getTotalWorker()!=null&&!supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")){
			model.addAttribute("fw_work", "格式不正确");
			bool=false;
		}
		
		
		return bool;
	}
	
	/**
	 * 
	* @Title: queryByPid
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/category")
	public String  queryByPid(String id,Model model,String sid){
		List<Category> cateList=new LinkedList<Category>();
		String[] str = id.split(",");
		if(str.length>0){
			for(String s:str){
				String pid = DictionaryDataUtil.getId(s);
				List<Category> list = categoryService.listByParent(pid);
				cateList.addAll(list);
			}
		}
		List<SupplierItem> itemList = supplierItemService.getSupplierId(sid);
		List<Category> chose=new LinkedList<Category>();
		if(itemList!=null&&itemList.size()>0){
			for(SupplierItem s:itemList){
				Category category = categoryService.selectByPrimaryKey(s.getCategoryId());
				chose.add(category);
			}
		}
		String cid = DictionaryDataUtil.getId(id);
		model.addAttribute("list", cateList);
		model.addAttribute("sid", sid);
		model.addAttribute("code", cid);
		model.addAttribute("chose", chose);
		return "ses/sms/supplier_register/category";	
	}
	
	
//	回头删掉
	
	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		Supplier supplier = supplierService.get("46131978161b491d9f7816ddd034dfd6");
 
		model.addAttribute("currSupplier", supplier);
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.getSession().setAttribute("supplierId", supplier.getId());
		return "ses/sms/supplier_register/basic_info";
	}
}
