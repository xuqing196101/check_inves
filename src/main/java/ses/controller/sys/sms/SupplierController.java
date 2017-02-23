package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ses.dao.sms.SupplierAfterSaleDepMapper;
import ses.dao.sms.SupplierFinanceMapper;
import ses.dao.sms.SupplierMapper;
import ses.dao.sms.SupplierStockholderMapper;
import ses.formbean.ContractBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.QualificationLevelService;
import ses.service.bms.QualificationService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAfterSaleDepService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.IdentityCode;
import ses.util.PathUtil;
import ses.util.PropUtil;
import ses.util.SupplierLevelUtil;
import ses.util.ValidateUtils;
import ses.util.WfUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.constant.StaticVariables;
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
	private SupplierService supplierService; // 供应商基本信息

	@Autowired
	private SupplierMatProService supplierMatProService; // 供应商物资生产专业信息

	@Autowired
	private SupplierMatSellService supplierMatSellService; // 供应商物资销售专业信息
	
	@Autowired
	private QualificationLevelService qualificationLevelService; // 供应商物资销售专业信息

	@Autowired
	private SupplierMatSeService supplierMatSeService; // 供应商服务专业信息

	@Autowired
	private SupplierMatEngService supplierMatEngService; // 供应商工程专业信息

	@Autowired
	private OrgnizationServiceI orgnizationServiceI; // 机构
	
	@Autowired
	private QualificationService qualificationService; // 资质类型

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
	private SupplierFinanceMapper supplierFinanceMapper; // 供应商财务信息

	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper; //股东信息
	
	@Autowired
	private SupplierAfterSaleDepMapper supplierAfterSaleDepMapper; //售后服务机构
	
	@Autowired
	private SupplierAfterSaleDepService supplierAfterSaleDepService; //售后服务机构

	@Autowired
	private SupplierFinanceService supplierFinanceService;

	@Autowired
	private SupplierMapper supplierMapper;

	@Autowired
	private AreaServiceI areaService;

	@Autowired
	private ExpertService expertService;

	@Autowired
	private SupplierAddressService supplierAddressService;

	@Autowired
	private SupplierBranchService supplierBranchService;

	@Autowired
	private SupplierAuditService supplierAuditService;

	@Autowired
	private SupplierHistoryService supplierHistoryService;

	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;

	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private SupplierModifyService supplierModifyService;
	
	@Autowired
	private SupplierCertEngService supplierCertEngService;

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
		DictionaryData dd = DictionaryDataUtil.get("SUPPLIER_REGISTER_NOTICE");
		if(dd != null) {
			Map < String, Object > param = new HashMap < String, Object > ();
			param.put("docType", dd.getId());
			model.addAttribute("doc", noticeDocumentService.findDocByMap(param));
		}
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
		String id = WfUtil.createUUID();
		request.setAttribute("id", id);
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
		//未注册供应商
		if(sup == null) {
			boolean flag = validateRegister(request, model, supplier);
			if(flag) {
				supplier = supplierService.register(supplier);
				List < SupplierFinance > list = supplierFinanceService.getYear();
				// 对年份进行排序
				Collections.sort(list, new Comparator < SupplierFinance > () {
					public int compare(SupplierFinance finance1, SupplierFinance finance2) {
						// 按照SupplierFinance的年份进行升序排列  
						if(Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
							return 1;
						}
						if(finance1.getYear().equals(finance2.getYear())) {
							return 0;
						} else {
							return -1;
						}
					}
				});
				supplier.setListSupplierFinances(list);
				initCompanyType(model, supplier);
				return "ses/sms/supplier_register/basic_info";
			}
		}
		//已注册供应商
		if(sup != null) {
			// 所有的不通过字段的名字
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(sup.getId());;
			s.setAuditType("basic_page");
			List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);
			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("audit", errorField);
			//初始化近三年的财务信息
			initFinance(sup);
			//股东信息
			List < SupplierStockholder > stock = supplierStockholderMapper.findStockholderBySupplierId(sup.getId());
			if(stock != null && stock.size() > 0) {
				sup.setListSupplierStockholders(stock);
			}
			//售后服务机构
			List < SupplierAfterSaleDep > afterSaleDep = supplierAfterSaleDepMapper.findAfterSaleDepBySupplierId(sup.getId());
			if(afterSaleDep != null && afterSaleDep.size() > 0) {
			    sup.setListSupplierAfterSaleDep(afterSaleDep);
			}

			//供应商地址信息
			if(sup.getAddress() != null) {
				Area area = areaService.listById(sup.getAddress());
				List < Area > city = areaService.findAreaByParentId(area.getParentId());
				model.addAttribute("city", city);
				model.addAttribute("area", area);
			}

			//生产经营地址
			if(sup.getAddressList() != null && sup.getAddressList().size() > 0) {
				for(SupplierAddress b: supplier.getAddressList()) {
					if(StringUtils.isNotBlank(b.getProvinceId())) {
						List < Area > city = areaService.findAreaByParentId(b.getProvinceId());
						b.setAreaList(city);
					}
				}
			} else {
				List < SupplierAddress > addressList = new ArrayList < SupplierAddress > ();
				SupplierAddress address = new SupplierAddress();
				addressList.add(address);
				sup.setAddressList(addressList);
			}

			//省份
			if(sup.getConcatProvince() != null) {
				List < Area > concity = areaService.findAreaByParentId(sup.getConcatProvince());
				sup.setConcatCityList(concity);
			}
			if(sup.getArmyBuinessProvince() != null) {
				List < Area > armcity = areaService.findAreaByParentId(sup.getArmyBuinessProvince());
				sup.setArmyCity(armcity);
			}
			//境外分支
			List < SupplierBranch > branchList = supplierBranchService.findSupplierBranch(supplier.getId());
			if(branchList != null && branchList.size() > 0) {
				sup.setBranchList(branchList);
			} else {
				branchList = new ArrayList < SupplierBranch > ();
				SupplierBranch branch = new SupplierBranch();
				branchList.add(branch);
				sup.setBranchList(branchList);
			}
			List < SupplierAddress > addressList = supplierAddressService.getBySupplierId(sup.getId());
			if(addressList.size() > 0) {
				for(SupplierAddress b: addressList) {
					if(StringUtils.isNotBlank(b.getProvinceId())) {
						List < Area > city = areaService.findAreaByParentId(b.getProvinceId());
						b.setAreaList(city);
					}
				}
				sup.setAddressList(addressList);
			} else {
				SupplierAddress address = new SupplierAddress();
				address.setId(WfUtil.createUUID());
				addressList.add(address);
				sup.setAddressList(addressList);
			}
			initCompanyType(model, sup);
			return "ses/sms/supplier_register/basic_info";
		}
		String id = WfUtil.createUUID();
		request.setAttribute("id", id);
		return "ses/sms/supplier_register/register";
	}

	/**
	 * 
	 *〈简述〉初始化近三年的财务信息
	 *〈详细描述〉
	 * @author myc
	 * @param sup {@link Supplier}
	 */
	private void initFinance(Supplier sup) {
		List < SupplierFinance > finace = supplierFinanceMapper.findFinanceBySupplierId(sup.getId());
		if(finace != null && finace.size() > 0) {
			List < SupplierFinance > finaceList = supplierFinanceService.getList(finace);
			Collections.sort(finaceList, new Comparator < SupplierFinance > () {
				public int compare(SupplierFinance finance1, SupplierFinance finance2) {
					// 按照SupplierFinance的年份进行升序排列  
					if(Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
						return 1;
					}
					if(finance1.getYear().equals(finance2.getYear())) {
						return 0;
					} else {
						return -1;
					}
				}
			});
			sup.setListSupplierFinances(finaceList);
		} else {
			List < SupplierFinance > list = supplierFinanceService.getYear();
			Collections.sort(list, new Comparator < SupplierFinance > () {
				public int compare(SupplierFinance finance1, SupplierFinance finance2) {
					// 按照SupplierFinance的年份进行升序排列  
					if(Integer.parseInt(finance1.getYear()) > Integer.parseInt(finance2.getYear())) {
						return 1;
					}
					if(finance1.getYear().equals(finance2.getYear())) {
						return 0;
					} else {
						return -1;
					}
				}
			});
			sup.setListSupplierFinances(list);
		}
	}

	/**
	 * 
	 *〈简述〉初始化常量
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param supplier 供应商
	 */
	private void initCompanyType(Model model, Supplier supplier) {
		//初始化省份
		List < Area > privnce = areaService.findRootArea();
		model.addAttribute("privnce", privnce);
		//初始化当前供应商
		List<SupplierStockholder> stockList = supplier.getListSupplierStockholders();
		if (stockList == null || stockList.size() == 0) {
		    SupplierStockholder stock = new SupplierStockholder();
		    stock.setId(WfUtil.createUUID());
		    stockList.add(stock);
		    stock.setSupplierId(supplier.getId());
		    supplier.setListSupplierStockholders(stockList);
		}
		List<SupplierAfterSaleDep> afterSaleDep = supplier.getListSupplierAfterSaleDep();
        if (afterSaleDep == null || afterSaleDep.size() == 0) {
            SupplierAfterSaleDep stock = new SupplierAfterSaleDep();
            stock.setId(WfUtil.createUUID());
            afterSaleDep.add(stock);
            stock.setSupplierId(supplier.getId());
            supplier.setListSupplierAfterSaleDep(afterSaleDep);
        }
		model.addAttribute("currSupplier", supplier);
		//初始化供应商注册附件类型
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		//初始化公司性质
		model.addAttribute("company", DictionaryDataUtil.find(17));
		model.addAttribute("nature", DictionaryDataUtil.find(32));
		//初始化所在国家
		model.addAttribute("foregin", DictionaryDataUtil.find(24));
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
	public String searchOrg(HttpServletRequest request, String pid, String cid, Model model) {
		HashMap < String, Object > map = new HashMap < String, Object > ();
		map.put("provinceId", pid);
		map.put("cityId", cid);
		List < Orgnization > listOrgnizations1 = orgnizationServiceI.findOrgnizationList(map);
		model.addAttribute("listOrgnizations1", listOrgnizations1);
		HashMap < String, Object > map1 = new HashMap < String, Object > ();
		map1.put("typeName", "1");
		List < PurchaseDep > list = purchaseOrgnizationService.findPurchaseDepList(map1);
		for(PurchaseDep purchaseDep: list) {
			for(Orgnization org: listOrgnizations1) {
				if(purchaseDep.getOrgnization().getId().equals(org.getId())) {
					list.remove(org);
				}
			}
		}
		model.addAttribute("allPurList", list);
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
		if(fileName != null && !"".equals(fileName)) {
			super.download(request, response, fileName);
		} else {
			super.alert(request, response, "无附件下载 !", true);
		}
		super.removeStash(request, fileName);
	}

	/**
	 * 
	 *〈简述〉临时保存
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param supplier {@link Supplier}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/temporarySave", produces = "html/text;charset=UTF-8")
	public String temporarySave(HttpServletRequest request, Supplier supplier, String flag) {
		String res = StaticVariables.SUCCESS;

		//如果是附件上传页面
		if(flag != null && flag.equals("file")) {
			res = StaticVariables.SUCCESS;
		} else if(flag != null && flag.equals("1")) {
			//保存审核采购机构
			try {
				supplierService.updateSupplierProcurementDep(supplier);
			} catch(Exception e) {
				res = StaticVariables.FAILED;
				e.printStackTrace();
			}
		} else {
			//保存基本信息
			try {
				Supplier before = supplierService.get(supplier.getId());
				if(before.getStatus().equals(2)) {
					record("", before, supplier, supplier.getId()); //记录供应商退回修改的内容
				}
				supplierService.perfectBasic(supplier);
			} catch(Exception e) {
				res = StaticVariables.FAILED;
				e.printStackTrace();
			}
		}
		return res;
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
	 * @throws Exception 
	 */
	@RequestMapping(value = "perfect_basic")
	public String perfectBasic(HttpServletRequest request, Model model, Supplier supplier) throws Exception {
		
		
		// 非空处理
	    List < SupplierAddress > addressList = supplier.getAddressList();
	    for(int i = 0; i < addressList.size(); i++) {
	        SupplierAddress address = addressList.get(i);
	        if(address != null && address.getId() == null) {
	            addressList.remove(i);
	        }
	    }
	    supplier.setAddressList(addressList);
		List < SupplierStockholder > stockHolders = supplier.getListSupplierStockholders();
		for(int i = 0; i < stockHolders.size(); i++) {
			SupplierStockholder stocker = stockHolders.get(i);
			if(stocker != null && stocker.getSupplierId() == null) {
				stockHolders.remove(i);
			}
		}
		supplier.setListSupplierStockholders(stockHolders);
		List < SupplierAfterSaleDep > afterSaleDep = supplier.getListSupplierAfterSaleDep();
		List < SupplierAfterSaleDep > afterSaleList = new ArrayList<SupplierAfterSaleDep>();
		for(int i = 0; i < afterSaleDep.size(); i++) {
		    SupplierAfterSaleDep afterSale = afterSaleDep.get(i);
		    if(afterSale.getId() != null) {
		        afterSaleList.add(afterSale);
		    }
		}
		supplier.setListSupplierAfterSaleDep(afterSaleList);
		boolean info = validateBasicInfo(request, model, supplier);
		List < SupplierTypeRelate > relate = supplierTypeRelateService.queryBySupplier(supplier.getId());
		model.addAttribute("relate", relate);

		if(supplier.getAddress() != null) {
			Area area = areaService.listById(supplier.getAddress());
			List < Area > city = areaService.findAreaByParentId(area.getParentId());
			model.addAttribute("city", city);
			model.addAttribute("area", area);
		}

		if(info) {
		    Supplier before = supplierService.get(supplier.getId());
		    // 判断是否满足条件
		    BigDecimal score = supplierService.getScoreBySupplierId(supplier.getId());
		    if (score.compareTo(BigDecimal.valueOf(100)) != 1) {
	            initCompanyType(model, before);
	            request.setAttribute("notPass", "notPass");
	            return "ses/sms/supplier_register/basic_info";
		    }
			if(before.getStatus().equals(2)) {
				record("", before, supplier, supplier.getId()); //记录供应商退回修改的内容
			}
			supplierService.perfectBasic(supplier);
			supplier = supplierService.get(supplier.getId());

			List < DictionaryData > list = DictionaryDataUtil.find(6);
			for(int i = 0; i < list.size(); i++) {
				String code = list.get(i).getCode();
				if(code.equals("GOODS")) {
					list.remove(list.get(i));
				}
			}
			model.addAttribute("supplieType", list);
			List < DictionaryData > wlist = DictionaryDataUtil.find(8);
			model.addAttribute("wlist", wlist);
			//物资生产类型的必须有的证书
			if(supplier.getSupplierMatPro() == null) {
				supplier.setSupplierMatPro(supplierMatProService.init());
			}
			if(supplier.getSupplierMatSell() == null) {
			    supplier.setSupplierMatSell(supplierMatSellService.init());
			}
			if(supplier.getSupplierMatEng() == null) {
			    supplier.setSupplierMatEng(supplierMatEngService.init());
			}
			if(supplier.getSupplierMatSe() == null) {
			    supplier.setSupplierMatSe(supplierMatSeService.init());
			}
			String attid = DictionaryDataUtil.getId("SUPPLIER_PRODUCT");
			model.addAttribute("currSupplier", supplier);
			Map < String, Object > map = supplierService.getCategory(supplier.getId());
			model.addAttribute("server", map.get("server"));
			model.addAttribute("product", map.get("product"));
			model.addAttribute("sale", map.get("sale"));
			model.addAttribute("project", map.get("project"));
			model.addAttribute("attid", attid);
			List < DictionaryData > company = DictionaryDataUtil.find(17);
			model.addAttribute("company", company);
	        model.addAttribute("nature", DictionaryDataUtil.find(32));
			List < Area > privnce = areaService.findRootArea();
			model.addAttribute("privnce", privnce);

			/**
			 * 查询不通过的理由
			 */
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());;
			//供应商勾选的类型
			StringBuffer typePageField = new StringBuffer();
			supplierAudit.setAuditType("supplierType_page");
			List < SupplierAudit > typeAuditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			for(SupplierAudit audit: typeAuditList) {
				typePageField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("typePageField", typePageField);
			
			//生产
			StringBuffer proPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_pro_page");
			List < SupplierAudit > proAuditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			for(SupplierAudit audit: proAuditList) {
				proPageField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("proPageField", proPageField);
			//销售
			StringBuffer sellPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_sell_page");
			List < SupplierAudit > sellAuditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			for(SupplierAudit audit: sellAuditList) {
				sellPageField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("sellPageField", sellPageField);
			//工程
			StringBuffer engPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_eng_page");
			List < SupplierAudit > engAuditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			for(SupplierAudit audit: engAuditList) {
				engPageField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("engPageField", engPageField);
			//服务
			StringBuffer servePageField = new StringBuffer();
			supplierAudit.setAuditType("mat_serve_page");
			List < SupplierAudit > serveAuditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			for(SupplierAudit audit: serveAuditList) {
				servePageField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("servePageField", servePageField);
			
			//初始化供应商注册附件类型
			model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
			model.addAttribute("rootArea", areaService.findRootArea());
			model.addAttribute("typeList", qualificationService.findList(null, null, 4));
			return "ses/sms/supplier_register/supplier_type";
		} else {
			Supplier supplier2 = supplierService.get(supplier.getId());
			if(supplier2.getListSupplierFinances() != null && supplier2.getListSupplierFinances().size() > 0) {
				supplier.setListSupplierFinances(supplier2.getListSupplierFinances());
			}
			if(supplier2.getListSupplierStockholders() != null && supplier2.getListSupplierStockholders().size() > 0) {
				supplier.setListSupplierStockholders(supplier2.getListSupplierStockholders());
			}
			List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplier2.getListSupplierAfterSaleDep();
			if(listSupplierAfterSaleDep != null && listSupplierAfterSaleDep.size() > 0) {
			    for (int i = 1; i < listSupplierAfterSaleDep.size(); i++) {
			        SupplierAfterSaleDep afterSale = listSupplierAfterSaleDep.get(i);
                    if (afterSale.getId() == null) {
                        listSupplierAfterSaleDep.remove(i);
                    }
                }
			    supplier2.setListSupplierAfterSaleDep(supplier2.getListSupplierAfterSaleDep());
			}
			if(supplier2.getAddressList() != null && supplier2.getAddressList().size() > 0) {
				for(SupplierAddress b: supplier2.getAddressList()) {
					if(StringUtils.isNotBlank(b.getProvinceId())) {
						List < Area > city = areaService.findAreaByParentId(b.getProvinceId());
						b.setAreaList(city);
					}
				}
			}
			if(supplier2.getConcatProvince() != null) {
				List < Area > concity = areaService.findAreaByParentId(supplier2.getConcatProvince());
				supplier2.setConcatCityList(concity);
			}
			if(supplier2.getArmyBuinessProvince() != null) {
				List < Area > armcity = areaService.findAreaByParentId(supplier2.getArmyBuinessProvince());
				supplier2.setArmyCity(armcity);
			}
			initCompanyType(model, supplier2);
			return "ses/sms/supplier_register/basic_info";
		}
	}

	/**
	 * 
	 *〈简述〉ajax保存供应商类型
	 *〈详细描述〉
	 * @author myc
	 * @param supplier {@link Supplier}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSupplierType", produces = "html/text;charset=UTF-8")
	public String saveSupplierType(Supplier supplier, Model model) {
		if(supplier != null) {
			if(StringUtils.isNotBlank(supplier.getSupplierTypeIds())) {
				String[] supplierTypeArray = supplier.getSupplierTypeIds().trim().split(",");
				for(String supplierType: supplierTypeArray) {
					if(supplierType.equals("PRODUCT")) {
						supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
					}
					if(supplierType.equals("SALES")) {
						supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
					}
					if(supplierType.equals("PROJECT")) {
						supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
					}
					if(supplierType.equals("SERVICE")) {
						supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
					}
				}
			}
			supplierTypeRelateService.saveSupplierTypeRelate(supplier);
		}
		//model.addAttribute("currSupplier", supplier);
		StringBuffer idSb = new StringBuffer();
		idSb.append(supplierMatProService.getMatProIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatSellService.getMatSellIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatEngService.getMatEngIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatSeService.getMatSeIdBySupplierId(supplier.getId()) + ",");
		return idSb.toString();
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
	public String perfectProfessional(HttpServletRequest request, Supplier supplier, String flag, Model model) throws IOException {
		boolean info = true;
		boolean sale = true;
		boolean pro = true;
		boolean server = true;
		boolean project = true;
		String[] str = supplier.getSupplierTypeIds().trim().split(",");
		
		List<Area> areaList = areaService.findRootArea();
		for(String s: str) {
			if(s.equals("PRODUCT")) {
				pro = validatePro(request, supplier.getSupplierMatPro(), model);
				if(info == true) {
					supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
				}
			}
			if(s.equals("SALES")) {
				sale = validateSale(request, supplier.getSupplierMatSell(), model);
				if(info == true) {
					supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
				}
			}
			if(s.equals("PROJECT")) {
				project = validateEng(request, supplier.getSupplierMatEng(), model, areaList);
				if(info == true) {
					supplierMatEngService.saveOrUpdateSupplierMatPro(supplier);
				}
			}
			if(s.equals("SERVICE")) {
				server = validateServer(request, supplier.getSupplierMatSe(), model);
				if(info == true) {
					supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
				}
			}
		}
		supplierTypeRelateService.saveSupplierTypeRelate(supplier);
		String[] split = supplier.getSupplierTypeIds().split(",");
		int length = split.length;
		model.addAttribute("length", length);
		model.addAttribute("supplierTypeIds", supplier.getSupplierTypeIds());
		model.addAttribute("currSupplier", supplier);
		if(pro == true && server == true && project == true && sale == true) {
			return "ses/sms/supplier_register/items";
		} else {
			List < DictionaryData > list = DictionaryDataUtil.find(6);
			for(int i = 0; i < list.size(); i++) {
				String code = list.get(i).getCode();
				if(code.equals("GOODS")) {
					list.remove(list.get(i));
				}
			}
			model.addAttribute("pro", pro);
			model.addAttribute("server", server);
			model.addAttribute("project", project);
			model.addAttribute("sale", sale);
			model.addAttribute("supplieType", list);
			List < DictionaryData > wlist = DictionaryDataUtil.find(8);
			model.addAttribute("wlist", wlist);
			//初始化供应商注册附件类型
			model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
            model.addAttribute("rootArea", areaList);
            model.addAttribute("typeList", qualificationService.findList(null, null, 4));
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
	public String perfectDep(HttpServletRequest request, Supplier supplier, String flag, Model model, String supplierTypeIds) {

		if(flag.equals("next")) {
			supplierService.updateSupplierProcurementDep(supplier);
			supplier = supplierService.get(supplier.getId());
			model.addAttribute("currSupplier", supplier);
			model.addAttribute("supplierTypeIds", supplierTypeIds);
			
			// 所有的不通过字段的名字
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplier.getId());;
			s.setAuditType("download_page");
			List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);
			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("audit", errorField);
			
			return "ses/sms/supplier_register/template_download";
		} else if(flag.equals("store")) {
			supplierService.updateSupplierProcurementDep(supplier);
			supplier = supplierService.get(supplier.getId());
			String orgId = supplier.getProcurementDepId();
			model.addAttribute("currSupplier", supplier);
			HashMap < String, Object > map1 = new HashMap < String, Object > ();
			map1.put("typeName", "1");
			List < PurchaseDep > list = purchaseOrgnizationService.findPurchaseDepList(map1);
			for(PurchaseDep purchaseDep: list) {
				if(purchaseDep.equals(orgId)) {
					list.remove(purchaseDep);
				}
			}
			model.addAttribute("allPurList", list);
			return "ses/sms/supplier_register/procurement_dep";
		} else {
			supplier = supplierService.get(supplier.getId());
			model.addAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/items";
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
	public String perfectDownload(HttpServletRequest request, Supplier supplier, String jsp, String flag, Model model, String supplierTypeIds) {
		supplier = supplierService.get(supplier.getId());

		if("next".equals(flag)) {
			Integer sysKey = Constant.SUPPLIER_SYS_KEY;
			model.addAttribute("sysKey", sysKey);
			model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
			model.addAttribute("currSupplier", supplier);

			model.addAttribute("supplierTypeIds", supplierTypeIds);

			return "ses/sms/supplier_register/template_upload";
		} else {
			supplier = supplierService.get(supplier.getId());
			HashMap < String, Object > map = new HashMap < String, Object > ();
			// 采购机构
			List < PurchaseDep > depList = null;
			if(supplier.getProcurementDepId() != null) {
				map.put("id", supplier.getProcurementDepId());
				map.put("typeName", "1");

				depList = purchaseOrgnizationService.findPurchaseDepList(map);

				if(depList != null && depList.size() > 0) {
					Orgnization orgnization = depList.get(0);
					List < Area > city = areaService.findAreaByParentId(orgnization.getProvinceId());
					model.addAttribute("orgnization", orgnization);
					model.addAttribute("city", city);
					model.addAttribute("listOrgnizations1", depList);
				}
			}
			List < Area > privnce = areaService.findRootArea();
			model.addAttribute("privnce", privnce);

			model.addAttribute("currSupplier", supplier);
			model.addAttribute("supplierTypeIds", supplierTypeIds);

			HashMap < String, Object > map1 = new HashMap < String, Object > ();
			map1.put("typeName", "1");

			List < PurchaseDep > list = purchaseOrgnizationService.findPurchaseDepList(map1);
			if(depList != null && depList.size() != 0) {
				for(PurchaseDep purchaseDep: list) {
					for(Orgnization org: depList) {
						if(purchaseDep.getOrgnization() != null) {
							if(purchaseDep.getOrgnization().getId().equals(org.getId())) {
								list.remove(org);
							}
						}
					}
				}
			}
			model.addAttribute("allPurList", list);
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
	public String perfectUpload(HttpServletRequest request, Supplier supplier, String jsp, String flag, Model model, String supplierTypeIds) throws IOException {
		boolean bool = validateUpload(model, supplier.getId());

		if(!"commit".equals(jsp)) {
			supplierService.perfectBasic(supplier);
			supplier = supplierService.get(supplier.getId());
			model.addAttribute("currSupplier", supplier);
			model.addAttribute("supplierTypeIds", supplierTypeIds);
			model.addAttribute("jump.page", jsp);
			return "ses/sms/supplier_register/template_download";
		}
		if(bool != true) {
			return "ses/sms/supplier_register/template_upload";
		}
		supplierService.commit(supplier);
		//更新审核时间
		supplier.setAuditDate(new Date());
		//刪除上次的審核記錄
		supplierAuditService.deleteBySupplierId(supplier.getId());
				
		supplierAuditService.updateStatus(supplier);
		request.getSession().removeAttribute("currSupplier");
		request.getSession().removeAttribute("sysKey");
		request.getSession().removeAttribute("supplierDictionaryData");
		request.getSession().removeAttribute("listOrgnizations1");
		request.getSession().removeAttribute("listOrgnizations2");
		return "redirect:../index/selectIndexNews.html";
	}

	@ResponseBody
	@RequestMapping("/isCommit")
	public String isCommit(Model model, Supplier supplier) {
		boolean bool = validateUpload(model, supplier.getId());
		if(bool != true) {
			// 返回
			return "1";
		} else {
			return "0";
		}
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
		if(page == null || "".equals(page)) {
			page = "registration";
		}
		return "ses/sms/supplier_register/" + page;
	}

	@RequestMapping(value = "check_login_name")
	public void checkLoginName(HttpServletResponse response, String loginName) {
		boolean flag = supplierService.checkLoginName(loginName);
		String msg = "";
		if(flag) {
			msg = "{\"msg\":\"success\"}";
		} else {
			msg = "{\"msg\":\"fail\"}";
		}
		super.writeJson(response, msg);
	}

	@RequestMapping(value = "return_edit")
	public String returnEdit(HttpServletRequest request, Supplier supplier, Model model) {
		supplier = supplierService.get(supplier.getId());
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		List<SupplierStockholder> stockList = supplier.getListSupplierStockholders();
        if (stockList == null || stockList.size() == 0) {
            SupplierStockholder stock = new SupplierStockholder();
            stock.setId(WfUtil.createUUID());
            stockList.add(stock);
            stock.setSupplierId(supplier.getId());
            supplier.setListSupplierStockholders(stockList);
        }
        List<SupplierAfterSaleDep> afterSaleDep = supplier.getListSupplierAfterSaleDep();
        if (afterSaleDep == null || afterSaleDep.size() == 0) {
            SupplierAfterSaleDep stock = new SupplierAfterSaleDep();
            stock.setId(WfUtil.createUUID());
            afterSaleDep.add(stock);
            stock.setSupplierId(supplier.getId());
            supplier.setListSupplierAfterSaleDep(afterSaleDep);
        }
		model.addAttribute("currSupplier", supplier);
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
		String serverName = request.getServerName(); // 获取主机名
		int serverPort = request.getServerPort(); // 获取端口号
		String contextPath = request.getContextPath(); // 获取项目路径
		String url = "http://" + serverName + ":" + serverPort + contextPath + spaceAndRequest;
		if(referer != null && url.equals(referer)) {
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

	//注册登记校验
	public boolean validateRegister(HttpServletRequest request, Model model, Supplier supplier) {
		//String identifyCode = (String) request.getSession().getAttribute("img-identity-code");// 验证码
		int count = 0;
		if(supplier.getLoginName() == null || !supplier.getLoginName().matches("^\\w{6,20}$")) {
			model.addAttribute("err_msg_loginName", "登录名由6-20位字母数字和下划线组成 !");
			count++;
		}
		if(supplier.getPassword() == null || supplier.getPassword().length() < 6 || supplier.getPassword().length() > 20) {
			model.addAttribute("err_msg_password", "密码长度为6-20位!");
			count++;
		}
		if(supplier.getConfirmPassword() == null || !supplier.getPassword().equals(supplier.getConfirmPassword())) {
			model.addAttribute("err_msg_ConfirmPassword", "密码和重复密码不一致 !");
			count++;
		}
		if(supplier.getMobile() == null || !supplier.getMobile().matches("^1[0-9]{10}$")) {
			model.addAttribute("err_msg_mobile", "手机格式不正确 !");
			count++;
		}

		/*if (supplier.getMobileCode() == null) {
		   model.addAttribute("err_msg_mobileCode", "手机验证码错误 !");
		   count++;
		 }*/
		/*if (supplier.getIdentifyCode() == null || !supplier.getIdentifyCode().equals(identifyCode)) {
		   model.addAttribute("err_msg_code", "验证码错误 !");
		   count++;
		 }*/
		if(StringUtils.isNotBlank(supplier.getMobile())) {
			Boolean ajaxMoblie = userService.ajaxMoblie(supplier.getMobile(), null);
			if(!ajaxMoblie) {
				count++;
				model.addAttribute("err_msg_mobile", "手机号已存在 !");
			}
		}
		if(count > 0) {
			return false;
		}
		return true;
	}

	//基本信息校验
	public boolean validateBasicInfo(HttpServletRequest request, Model model, Supplier supplier) {
		int count = 0;
		if(supplier.getSupplierName() == null || !supplier.getSupplierName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_supplierName", "不能为空!");
			count++;
		}
		//		if (supplier.getWebsite() == null || !ValidateUtils.Url(supplier.getWebsite())) {
		//			model.addAttribute("err_msg_website", "格式错误 !");
		//			count++;
		//		}
		if(supplier.getFoundDate() == null) {
			model.addAttribute("err_msg_foundDate", "不能为空 !");
			count++;
		}
		if(supplier.getFoundDate() != null) {
			Date date = supplierService.addDate(supplier.getFoundDate(), 1, 3);
			Date now = new Date();
			if(date.getTime() > now.getTime()) {
				model.addAttribute("err_msg_foundDate", "成立日期必须大于三年!");
				count++;
			}
		}

		//		supplierService.addDate(supplier.getFoundDate(), 1, -3);
		if(supplier.getAddress() == null) {
			model.addAttribute("err_msg_address", "不能为空!");
			count++;
		}
		if(supplier.getBankName() == null || !supplier.getBankName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_bankName", "不能为空 !");
			count++;
		}
		if(supplier.getBankAccount() == null) {
			model.addAttribute("err_msg_bankAccount", "格式不正确 !");
			count++;
		}
		if(supplier.getPostCode() == null || supplier.getPostCode().length() != 6) {
			model.addAttribute("err_msg_postCode", "格式不正确 !");
			count++;
		}
		if(supplier.getDetailAddress() == null || supplier.getDetailAddress().length() > 80) {
			model.addAttribute("err_detailAddress", "详细地址不能为空!");
			count++;
		}

		if(supplier.getLegalName() == null || supplier.getLegalName().length() > 20) {
			model.addAttribute("err_legalName", "不能为空 或者名字过长!");
			count++;
		}
		if(supplier.getLegalIdCard() == null) {
			model.addAttribute("err_legalCard", "不能为空 !");
			count++;
		}
		if(supplier.getLegalIdCard() != null && !supplier.getLegalIdCard().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")) {
			model.addAttribute("err_legalCard", "身份证号码格式不正确 !");
			count++;
		}
		if(supplier.getConcatCity() == null) {
			model.addAttribute("err_city", "地址不能为空!");
			count++;
		}
		if(supplier.getArmyBusinessName() == null) {
			model.addAttribute("err_armName", "不能为空!");
			count++;
		}
		if(supplier.getArmyBusinessFax() == null || "".equals(supplier.getArmyBusinessFax()) || (supplier.getArmyBusinessFax() != null && !supplier.getArmyBusinessFax().matches("^(\\d{3,4}-{0,1})?\\d{7,8}$"))) {
			model.addAttribute("err_armFax", "传真不能为空或者格式不正确!");
			count++;
		}
		if(supplier.getArmyBuinessMobile() == null || "".equals(supplier.getArmyBuinessMobile()) || (supplier.getArmyBuinessMobile() != null && !supplier.getArmyBuinessMobile().matches("^(\\d{3,4}-{0,1})?\\d{7,8}$"))) {
			model.addAttribute("err_armMobile", "固定电话不能为空或者格式不正确!");
			count++;
		}
		if(supplier.getArmyBuinessTelephone() == null) {
			model.addAttribute("err_armTelephone", "不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessEmail() == null) {
			model.addAttribute("err_armEmail", "不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessCity() == null) {
			model.addAttribute("err_armCity", "不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessAddress() == null) {
			model.addAttribute("err_armAddress", "不能为空!");
			count++;
		}

		//		supplier2.setLegalIdCard(supplier.getLegalIdCard());
		//		map.put("legalIdCard", supplier.getLegalIdCard());
		//		List<Supplier> list3 = supplierService.query(map);
		//		if(list3!=null&&list3.size()>0){
		//			model.addAttribute("err_legalCard", "身份证号码已存在");
		//			count++;
		//		}
		if(supplier.getLegalMobile() == null || "".equals(supplier.getLegalMobile()) || (supplier.getLegalMobile() != null && !supplier.getLegalMobile().matches("^(\\d{3,4}-{0,1})?\\d{7,8}$"))) {
			model.addAttribute("err_legalMobile", "固定电话不能为空或者格式不正确!");
			count++;
		}
		/*	if(supplier.getLegalMobile()!=null&&!supplier.getLegalMobile().matches("^(0[1-9]{2})-\\d{8}$|^(0[1-9]{3}-(\\d{7,8}))$")){
            model.addAttribute("err_legalMobile", "固话格式不正确 !");
  			count++;
  		}*/
		if(supplier.getLegalTelephone() == null || !supplier.getLegalTelephone().matches("^1[0-9]{10}$") || supplier.getLegalTelephone().length() > 11) {
			model.addAttribute("err_legalPhone", "格式不正确 !");
			count++;
		}
		//		map.put("legalTelephone", supplier.getLegalTelephone());
		//		List<Supplier> list4= supplierService.query(map);
		//		if(list4!=null&&list4.size()>1){
		//			model.addAttribute("err_legalCard", "身份证号码已存在");
		//			count++;
		//		}

		if(supplier.getContactName() == null || supplier.getContactName().length() > 20) {
			model.addAttribute("err_conName", "不能为空 或者字符串过长!");
			count++;
		}

		if(supplier.getContactFax() == null || "".equals(supplier.getContactFax()) || (supplier.getContactFax() != null && !supplier.getContactFax().matches("^(\\d{3,4}-{0,1})?\\d{7,8}$"))) {
			model.addAttribute("err_fax", "传真不能为空或者格式不正确!");
			count++;
		}

		if(supplier.getContactMobile() == null || "".equals(supplier.getContactMobile())) {
			model.addAttribute("err_catMobile", "填写固定电话!");
			count++;
		}
		if(supplier.getContactMobile() != null && !supplier.getContactMobile().matches("^(\\d{3,4}-{0,1})?\\d{7,8}$")) {
			model.addAttribute("err_catMobile", "固定电话格式不正确!");
			count++;
		}
		//		if(supplier.getContactTelephone()==null||!supplier.getContactTelephone().matches("^1[0-9]{10}$")||supplier.getContactTelephone().length()>12){
		//			model.addAttribute("err_catTelphone", "格式不正确 !");
		//			count++;
		//		}
		if(supplier.getContactEmail() == null || !supplier.getContactEmail().matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")) {
			model.addAttribute("err_catEmail", "格式不正确 !");
			count++;
		}
		/*	if(supplier.getContactAddress()==null||supplier.getContactAddress().length()>35){
  			model.addAttribute("err_conAddress", "不能为空或是字符过长!");
  			count++;
  		}*/

		List < Supplier > tempList = supplierService.validateCreditCode(supplier.getCreditCode());
		if(supplier.getCreditCode() == null || supplier.getCreditCode().length() > 36) {
			model.addAttribute("err_creditCide", "不能为空或是字符过长!");
			count++;
		}
		if(supplier.getCreditCode() != null && supplier.getCreditCode().length() != 18) {
			model.addAttribute("err_creditCide", "格式错误!");
		}
		if(tempList != null && tempList.size() > 0) {
			for(Supplier supp: tempList) {
				if(!supplier.getId().equals(supp.getId())) {
					model.addAttribute("err_creditCide", "社会统一信用代码已被占用!");
					count++;
					break;
				}
			}
		}

		if(supplier.getRegistAuthority() == null || supplier.getRegistAuthority().length() > 20) {
			model.addAttribute("err_reAuthoy", "不能为空 或是编码过长!");
			count++;
		}
		if(supplier.getRegistFund() == null) {
			model.addAttribute("err_fund", "不能为空 !");
			count++;
		}
		if(supplier.getRegistFund() != null && !supplier.getRegistFund().toString().matches("^[0-9].*$")) {
			model.addAttribute("err_fund", "资金不能小于0或者是格式不正确 !");
			count++;
		}
		if(supplier.getBusinessStartDate() == null && supplier.getBranchName() == null) {
			model.addAttribute("err_sDate", "营业有效期不能为空 !");
			//count++;
		}
		/*	if(supplier.getBusinessEndDate()==null){
  			model.addAttribute("err_eDate", "营业截至时间不能为空 !");
  			count++;
  		}*/
		//		if(supplier.getBusinessAddress()==null){
		//			model.addAttribute("err_bAddress", "经营地址不能为空!");
		//			count++;
		//		}
		/*if(supplier.getBusinessPostCode() == null) {
			model.addAttribute("err_bCode", "不能为空!");
			count++;
		}*/
		if(supplier.getBusinessPostCode() != null && !ValidateUtils.Zipcode(supplier.getBusinessPostCode().toString())) {
			model.addAttribute("err_bCode", "邮编格式不正确!");
			count++;
		}
		if(supplier.getBranchCountry() != null && supplier.getBusinessScope().length() > 12) {
			model.addAttribute("err_country", "字符串不超过12个");
			count++;
		}
		if(supplier.getBranchAddress() != null) {
			model.addAttribute("err_address", "字符串不超过80个");
			count++;
		}
		if(supplier.getBranchName() != null && supplier.getBranchName().length() > 12) {
			model.addAttribute("err_BranName", "字符串不超过12个");
			count++;
		}

		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
		//* 近三个月完税凭证
		List < UploadFile > tlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierTaxCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(tlist != null && tlist.size() <= 0) {
			count++;
			model.addAttribute("err_taxCert", "请上传文件!");
		}
		//* 基本账户开户许可证
        List < UploadFile > supplierBank = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBank(), Constant.SUPPLIER_SYS_KEY.toString());
        if(supplierBank != null && supplierBank.size() <= 0) {
            count++;
            model.addAttribute("err_supplierBank", "请上传文件!");
        }

		//* 近三年银行基本账户年末对账单
		List < UploadFile > blist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBillCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(blist != null && blist.size() <= 0) {
			count++;
			model.addAttribute("err_bil", "请上传文件!");
		}
		//近三个月缴纳社会保险金凭证
		List < UploadFile > slist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierSecurityCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(slist != null && slist.size() <= 0) {
			count++;
			model.addAttribute("err_security", "请上传文件!");
		}
		//国家或军队保密证书
		if (supplier.getIsHavingConCert() != null && supplier.getIsHavingConCert().equals("1")) {
		    List < UploadFile > bearchlist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBearchCert(), Constant.SUPPLIER_SYS_KEY.toString());
		    if(bearchlist != null && bearchlist.size() <= 0) {
		        count++;
		        model.addAttribute("err_bearch", "请上传文件!");
		    }
		}
		if (supplier.getIsHavingConCert() == null || supplier.getIsHavingConCert().equals("")) {
		    count++;
            model.addAttribute("err_isHavingConCert", "请选择有或者无！");
		}
		if (supplier.getIsIllegal() == null || supplier.getIsIllegal().equals("")) {
            count++;
            model.addAttribute("err_isIllegal", "请选择有或者无！");
        }
		//近三年财务信息
		List < UploadFile > branchlist = new ArrayList < UploadFile > ();
		List < SupplierFinance > listSupplierFinances = supplier.getListSupplierFinances();
		for(SupplierFinance supplierFinance: listSupplierFinances) {
			branchlist.addAll(uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierProfit(), Constant.SUPPLIER_SYS_KEY.toString()));
			branchlist.addAll(uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierAuditOpinion(), Constant.SUPPLIER_SYS_KEY.toString()));
			branchlist.addAll(uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierLiabilities(), Constant.SUPPLIER_SYS_KEY.toString()));
			branchlist.addAll(uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierCashFlow(), Constant.SUPPLIER_SYS_KEY.toString()));
			//branchlist.addAll(uploadService.getFilesOther(supplierFinance.getId(), supplierDictionary.getSupplierOwnerChange(), Constant.SUPPLIER_SYS_KEY.toString()));
		}
		if(branchlist.size() < 12) {
			count++;
			model.addAttribute("err_bearchFile", "请上传文件!");
		}
		//供应商执照
		List < UploadFile > list = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierBusinessCert(), Constant.SUPPLIER_SYS_KEY.toString());
		if(list != null && list.size() <= 0) {
			count++;
			model.addAttribute("err_business", "请上传文件!");
		}
		//身份证
		List < UploadFile > ilist = uploadService.getFilesOther(supplier.getId(), supplierDictionary.getSupplierIdentityUp(), Constant.SUPPLIER_SYS_KEY.toString());
		if(ilist != null && ilist.size() <= 0) {
		    count++;
		    model.addAttribute("err_identityUp", "请上传文件!");
		}
		if(supplier.getListSupplierStockholders() == null || supplier.getListSupplierStockholders().size() < 1) {
			count++;
			model.addAttribute("stock", "请添加股东信息!");
		}
		if(supplier.getListSupplierStockholders() != null && supplier.getListSupplierStockholders().size() > 0) {
			List < SupplierStockholder > stockList = supplier.getListSupplierStockholders();
			for(SupplierStockholder stocksHolder: stockList) {
				if(stocksHolder.getName() == null || stocksHolder.getName() == "") {
					count++;
					model.addAttribute("stock", "出资人名称或姓名不能为空！");
				}
				if(stocksHolder.getIdentity() == null || stocksHolder.getIdentity() == "" || stocksHolder.getIdentity().length() != 18) {
					count++;
					model.addAttribute("stock", "统一社会信用代码或身份证号码为空或者格式不正确!");
				}
				if(stocksHolder.getShares() == null || stocksHolder.getShares() == "") {
					count++;
					model.addAttribute("stock", "出资金额或股份不能为空！");
				}
				if(stocksHolder.getProportion() == null || stocksHolder.getProportion() == "") {
					count++;
					model.addAttribute("stock", "比例不能为空！");
				}
			}
		}
		// 售后服务机构
        if(supplier.getListSupplierAfterSaleDep() == null || supplier.getListSupplierAfterSaleDep().size() < 1) {
            count++;
            model.addAttribute("afterSale", "请添加售后服务机构信息!");
        }
        if(supplier.getListSupplierAfterSaleDep() != null && supplier.getListSupplierAfterSaleDep().size() > 0) {
            List < SupplierAfterSaleDep > afterSaleList = supplier.getListSupplierAfterSaleDep();
            for(SupplierAfterSaleDep afterSale : afterSaleList) {
                if(afterSale.getName() == null || afterSale.getName() == "") {
                    count++;
                    model.addAttribute("afterSale", "售后服务机构名称不能为空！");
                }
                if(afterSale.getAddress() == null || afterSale.getAddress() == "") {
                    count++;
                    model.addAttribute("afterSale", "售后服务机构地址不能为空！");
                }
                if(afterSale.getLeadName() == null || afterSale.getLeadName() == "") {
                    count++;
                    model.addAttribute("afterSale", "售后服务机构负责人不能为空！");
                }
                if(afterSale.getMobile() == null || afterSale.getMobile() == "") {
                    count++;
                    model.addAttribute("afterSale", "售后服务机构联系方式不能为空！");
                }
            }
        }

		if(count > 0) {
			return false;
		}
		return true;
	}

	//生产信息校验
	public boolean validatePro(HttpServletRequest request, SupplierMatPro supplierMatPro, Model model) {
		/*if(supplierMatPro.getOrgName() == null || supplierMatPro.getOrgName().length() > 12) {
			model.addAttribute("org", "不能为空或者字符串过长");
			bool = false;
		}
		if(supplierMatPro.getTotalPerson() == null) {
			model.addAttribute("person", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalPerson() != null && !supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")) {
			model.addAttribute("person", "人员必须是整数");
			bool = false;
		}
		if(supplierMatPro.getTotalMange() == null) {
			model.addAttribute("mange", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalMange() != null && !supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")) {
			model.addAttribute("mange", "人员必须是整数");
			bool = false;
		}
		if(supplierMatPro.getTotalTech() == null) {
			model.addAttribute("tech", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalTech() != null && !supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")) {
			model.addAttribute("tech", "格式不正确");
			bool = false;
		}
		if(supplierMatPro.getTotalWorker() == null) {
			model.addAttribute("work", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalWorker() != null && !supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")) {
			model.addAttribute("work", "格式不正确");
			bool = false;
		}*/
		if(supplierMatPro.getScaleTech() == null) {
			model.addAttribute("stech", "不能为空");
            return false;
		}
		if(supplierMatPro.getScaleTech() != null && !supplierMatPro.getScaleTech().matches("^[-+]?\\d+(\\.\\d+)?$")) {
			model.addAttribute("stech", "格式不正确");
            return false;
		}
		if(supplierMatPro.getScaleHeightTech() == null) {
			model.addAttribute("height", "格式不正确");
            return false;
		}
		if(supplierMatPro.getScaleHeightTech() != null && !supplierMatPro.getScaleHeightTech().matches("^[-+]?\\d+(\\.\\d+)?$")) {
			model.addAttribute("height", "格式不正确");
            return false;
		}
		if(supplierMatPro.getResearchName() == null) {
			model.addAttribute("reName", "不能为空");
            return false;
		}
		if(supplierMatPro.getTotalResearch() == null) {
			model.addAttribute("tRe", "不能为空");
            return false;
		}

		if(supplierMatPro.getTotalResearch() != null && !supplierMatPro.getTotalResearch().toString().matches("^[0-9]*$")) {
			model.addAttribute("tRe", "只能输入整数");
            return false;
		}
		if(supplierMatPro.getResearchLead() == null || supplierMatPro.getResearchLead().length() > 12) {
			model.addAttribute("leader", "不能为空或者字符串过长");
            return false;
		}
		/*if(supplierMatPro.getTotalBeltline() == null) {
			model.addAttribute("line", "不能为空或者字符串过长");
			bool = false;
		}
		if(supplierMatPro.getTotalBeltline() != null && !supplierMatPro.getTotalBeltline().toString().matches("^[0-9]*$")) {
			model.addAttribute("line", "只能输入整数");
			bool = false;
		}
		if(supplierMatPro.getTotalDevice() == null) {
			model.addAttribute("device", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalDevice() != null && !supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")) {
			model.addAttribute("device", "格式正确");
			bool = false;
		}
		if(supplierMatPro.getQcName() == null || supplierMatPro.getQcName().length() > 12) {
			model.addAttribute("qcName", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalQc() == null) {
			model.addAttribute("tQc", "不能为空");
			bool = false;
		}*/
		/*	if(supplierMatPro.getTotalQc()!=null&&!supplierMatPro.getTotalDevice().toString().matches("^[0-9]*$")){
  			model.addAttribute("tQc", "格式不正确");
  			bool=false;	
  		}*/
		/*if(supplierMatPro.getQcLead() == null || supplierMatPro.getQcLead().length() > 12) {
			model.addAttribute("tqcLead", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getQcDevice() == null || supplierMatPro.getQcLead().length() > 12) {
			model.addAttribute("tqcDevice", "不能为空");
			bool = false;
		}*/
		List<SupplierCertPro> list = supplierMatPro.getListSupplierCertPros();
		if(list == null || list.size() < 1){
		    model.addAttribute("cert_pro", "请添加资质证书信息");
		    return false;
		} else {
		    for (SupplierCertPro cert : list) {
	            List < UploadFile > filelist = uploadService.getFilesOther(cert.getId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierProCert(), Constant.SUPPLIER_SYS_KEY.toString());
	            if(filelist != null && filelist.size() <= 0) {
	                model.addAttribute("cert_pro", "还有证书图片未上传!");
	                return false;
	            }
            }
		}
		return true;
	}

	//销售信息校验
	public boolean validateSale(HttpServletRequest request, SupplierMatSell supplierMatPro, Model model) {
			boolean bool = true;
			/*if(supplierMatPro.getOrgName() == null || supplierMatPro.getOrgName().length() > 12) {
				model.addAttribute("sale_org", "不能为空或者字符串过长");
				bool = false;
			}
			if(supplierMatPro.getTotalPerson() == null) {
				model.addAttribute("sale_person", "不能为空");
				bool = false;
			}
			if(supplierMatPro.getTotalPerson() != null && !supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")) {
				model.addAttribute("sale_person", "人员必须是整数");
				bool = false;
			}
			if(supplierMatPro.getTotalMange() == null) {
				model.addAttribute("sale_mange", "不能为空");
				bool = false;
			}
			if(supplierMatPro.getTotalMange() != null && !supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")) {
				model.addAttribute("sale_mange", "人员必须是整数");
				bool = false;
			}
			if(supplierMatPro.getTotalTech() == null) {
				model.addAttribute("sale_tech", "不能为空");
				bool = false;
			}
			if(supplierMatPro.getTotalTech() != null && !supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")) {
				model.addAttribute("sale_tech", "格式不正确");
				bool = false;
			}
			if(supplierMatPro.getTotalWorker() == null) {
				model.addAttribute("sale_work", "不能为空");
				bool = false;
			}
			if(supplierMatPro.getTotalWorker() != null && !supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")) {
				model.addAttribute("sale_work", "格式不正确");
				bool = false;
			}
			List<SupplierCertSell> list = supplierMatPro.getListSupplierCertSells();
			if(list==null||list.size()<1){
    			model.addAttribute("sale_cert", "资质证书不能为空");
    			bool=false;
			}*/
			return bool;
		}
	//工程信息校验
	public boolean validateEng(HttpServletRequest request, SupplierMatEng supplierMatPro, Model model, List<Area> areaList) {
		boolean bool = true;
		if(supplierMatPro.getIsHavingConAchi() != null && supplierMatPro.getIsHavingConAchi().equals("1")) {
		    List < UploadFile > tlist = uploadService.getFilesOther(supplierMatPro.getSupplierId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierConAch(), Constant.SUPPLIER_SYS_KEY.toString());
            if(tlist != null && tlist.size() <= 0) {
                bool = false;
                model.addAttribute("err_conAch", "请上传文件!");
            }
        }
		String businessScope = supplierMatPro.getBusinessScope();
		if (businessScope != null) {
		    String[] scope = businessScope.split(",");
		    for (String areaId : scope) {
		        Area recond = areaService.listById(areaId);
		        if (recond != null) {
		            List < UploadFile > list = uploadService.getFilesOther(supplierMatPro.getSupplierId() + "_" + recond.getId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierProContract(), Constant.SUPPLIER_SYS_KEY.toString());
		            if(list != null && list.size() <= 0) {
		                bool = false;
		                for (Area area : areaList) {
		                    if (area.getId().equals(recond.getId())) {
		                        area.setErrInfo("请上传文件！");
		                    }
		                }
		                break;
		            }
		        }
            }
		}
		List<SupplierCertEng> listSupplierCertEngs = supplierMatPro.getListSupplierCertEngs();
		if (listSupplierCertEngs != null && listSupplierCertEngs.size() > 0) {
		    for (SupplierCertEng supplierCertEng : listSupplierCertEngs) {
                if (supplierCertEng.getId() != null && supplierCertEng.getCertCode() != null) {
                    boolean flag = supplierCertEngService.validateCertCode(supplierCertEng);
                    if (!flag) {
                        bool = false;
                        model.addAttribute("eng_cert", "证书编号已被占用！");
                        break;
                    }
                }
            }
		}
		
		// 校验详细信息表里的编号和名称是否和证书信息表的匹配
		List<SupplierAptitute> listSupplierAptitutes = supplierMatPro.getListSupplierAptitutes();
		if (listSupplierAptitutes != null && listSupplierAptitutes.size() > 0) {
		    outer: for (SupplierAptitute supplierAptitute : listSupplierAptitutes) {
                boolean flag = false;
		        if (supplierAptitute.getCertName() != null && supplierAptitute.getCertCode() != null) {
                    inner: for (SupplierCertEng supplierCertEng : listSupplierCertEngs) {
                        if (supplierAptitute.getCertName().equals(supplierCertEng.getCertType()) && supplierAptitute.getCertCode().equals(supplierCertEng.getCertCode())) {
                            flag = true;
                            break inner;
                        }
                    }
                }
		        if (!flag) {
		            bool = false;
                    model.addAttribute("eng_aptitutes", "证书编号、名称与资质（认证）证书不匹配！");
                    break outer;
		        }
		    }
		}
		return bool;
	}
	//服务信息校验
	public boolean validateServer(HttpServletRequest request, SupplierMatServe supplierMatPro, Model model) {
		boolean bool = true;
		/*if(supplierMatPro.getOrgName() == null || supplierMatPro.getOrgName().length() > 12) {
			model.addAttribute("fw_org", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalPerson() == null) {
			model.addAttribute("fw_person", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalPerson() != null && !supplierMatPro.getTotalPerson().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_person", "人员必须是整数");
			bool = false;
		}
		if(supplierMatPro.getTotalMange() == null) {
			model.addAttribute("fw_mange", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalMange() != null && !supplierMatPro.getTotalMange().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_mange", "人员必须是整数");
			bool = false;
		}
		if(supplierMatPro.getTotalTech() == null) {
			model.addAttribute("fw_tech", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalTech() != null && !supplierMatPro.getTotalTech().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_tech", "格式不正确");
			bool = false;
		}
		if(supplierMatPro.getTotalWorker() == null) {
			model.addAttribute("fw_work", "不能为空");
			bool = false;
		}
		if(supplierMatPro.getTotalWorker() != null && !supplierMatPro.getTotalWorker().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_work", "格式不正确");
			bool = false;
		}*/
		//		List<SupplierCertServe> list = supplierMatPro.getListSupplierCertSes();
		//		if(list==null||list.size()<1){
		//			model.addAttribute("fw_cert", "请添加服务证书信息");
		//			bool=false;
		//		}

		return bool;
	}

	public boolean validateUpload(Model model, String supplierId) {
			boolean bool = true;
			SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
			//* 近三个月完税凭证
			List < UploadFile > tlist = uploadService.getFilesOther(supplierId, supplierDictionary.getSupplierRegList(), Constant.SUPPLIER_SYS_KEY.toString());
			if(tlist != null && tlist.size() <= 0) {
				bool = false;
				model.addAttribute("err_geglist", "请上传文件!");
			}
			List < UploadFile > plist = uploadService.getFilesOther(supplierId, supplierDictionary.getSupplierPledge(), Constant.SUPPLIER_SYS_KEY.toString());
			if(plist != null && plist.size() <= 0) {
				bool = false;
				model.addAttribute("err_pledge", "请上传文件!");
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
	public String queryByPid(String id, Model model, String sid, Integer page) {
		Map < String, Object > map = new HashMap < String, Object > ();
		map.put("isDeleted", 0);
		map.put("isPublish", 0);
		map.put("paramStatus", 4);
		List < Category > cateList = new LinkedList < Category > ();
		if(id.equals("PRODUCT")) {
			map.put("product", "s");
			List < Category > list = categoryService.findCategory(map, page == null ? 1 : page);
			cateList.addAll(list);
		}
		if(id.equals("SALES")) {
			map.put("sale", "s");
			List < Category > list = categoryService.findCategory(map, page == null ? 1 : page);
			cateList.addAll(list);
		}
		if(id.equals("SERVICE")) {
			String pid = DictionaryDataUtil.getId(id);
			map.put("parentId", pid);
			List < Category > list = categoryService.findCategory(map, page == null ? 1 : page);
			cateList.addAll(list);
		}
		if(id.equals("PROJECT")) {
			String pid = DictionaryDataUtil.getId(id);
			map.put("parentId", pid);
			List < Category > list = categoryService.findCategory(map, page == null ? 1 : page);
			cateList.addAll(list);
		}

		//		String[] str = id.split(",");
		//		if(str.length>0){
		//			for(String s:str){
		//				String pid = DictionaryDataUtil.getId(s);
		//				 PageHelper.startPage(page==null?1:page,30);
		//				List<Category> list = categoryService.listByParent(pid);
		//				
		//				cateList.addAll(list);
		//			}
		//		}
		List < SupplierItem > itemList = supplierItemService.getSupplierId(sid);

		List < Category > chose = new LinkedList < Category > ();
		//List<String> choseId=new LinkedList<String>();
		StringBuffer sb = new StringBuffer();
		String pid = DictionaryDataUtil.getId(id);
		if(itemList != null && itemList.size() > 0) {
			for(SupplierItem s: itemList) {
				if(s.getSupplierTypeRelateId().equals(pid)) {
					Category category = categoryService.selectByPrimaryKey(s.getCategoryId());
					chose.add(category);
					//					choseId.add(category.getId());
					sb.append(category.getId()).append(",");
				}
			}
		}

		PageInfo < Category > info = new PageInfo < > (cateList);

		String cid = DictionaryDataUtil.getId(id);
		model.addAttribute("info", info);
		model.addAttribute("sid", sid);
		model.addAttribute("code", cid);
		model.addAttribute("chose", chose);
		model.addAttribute("choseId", sb);
		model.addAttribute("id", id);
		return "ses/sms/supplier_register/category";
	}

	@RequestMapping("login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model, String name) throws IOException {
	    
        String user = (String) request.getSession().getAttribute("loginName");
        response.setContentType("textml;charset=utf-8");
        if(user==null){
            String path = request.getContextPath();
            String basePath =  request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort()+path+"/";
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<HTML><HEAD>");
            builder.append("<script language='javascript' type='text/javascript' src='"+basePath+"/public/backend/js/jquery.min.js'></script>");
            builder.append("<script language='javascript' type='text/javascript' src='"+basePath+"/public/layer/layer.js'></script>");
            builder.append("<link href='"+basePath+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
            builder.append("</HEAD>");
            builder.append("<script type=\"text/javascript\">"); 
            builder.append("$(function() {");
            builder.append("layer.confirm('您未登陆，请登录！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
            builder.append("window.top.location.href='"); 
            builder.append(basePath+"index/sign.html");  
            builder.append("';"); 
            builder.append("});");
            builder.append("});");
            builder.append("</script>");  
            builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
            out.print(builder.toString());
            out.flush();  
            out.close(); 
        }
     
        if(!user.equals(name)){
            String path = request.getContextPath();
            String basePath =  request.getScheme()+"://"+ request.getServerName()+":"+ request.getServerPort()+path+"/";
            PrintWriter out = response.getWriter();
            StringBuilder builder = new StringBuilder();
            builder.append("<HTML><HEAD>");
            builder.append("<script language='javascript' type='text/javascript' src='"+basePath+"/public/backend/js/jquery.min.js'></script>");
            builder.append("<script language='javascript' type='text/javascript' src='"+basePath+"/public/layer/layer.js'></script>");
            builder.append("<link href='"+basePath+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
            builder.append("</HEAD>");
            builder.append("<script type=\"text/javascript\">"); 
            builder.append("$(function() {");
            builder.append("layer.confirm('不是当前操作人，请登录修改！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
            builder.append("window.top.location.href='"); 
            builder.append(basePath+"index/sign.html");  
            builder.append("';"); 
            builder.append("});");
            builder.append("});");
            builder.append("</script>");  
            builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
            out.print(builder.toString());
            out.flush();  
            out.close(); 
        }
	    
		Supplier supp = supplierMapper.queryByName(name);
		Supplier supplier = supplierService.get(supp.getId());

		if(supplier.getAddress() != null) {
			Area area = areaService.listById(supplier.getAddress());
			List < Area > city = areaService.findAreaByParentId(area.getParentId());
			model.addAttribute("city", city);
			model.addAttribute("area", area);
		}
		List < DictionaryData > foregin = DictionaryDataUtil.find(24);

		List < Area > privnce = areaService.findRootArea();
		if(supplier.getListSupplierFinances() != null && supplier.getListSupplierFinances().size() < 1) {
			List < SupplierFinance > list = supplierFinanceService.getYear();
			supplier.setListSupplierFinances(list);
		} else {

			SupplierFinance finance1 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(oneYear()));
			if(finance1 == null) {
				SupplierFinance fin1 = new SupplierFinance();
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				fin1.setId(id);
				fin1.setYear(String.valueOf(threeYear()));
				supplier.getListSupplierFinances().add(fin1);
			}
			SupplierFinance finance2 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(twoYear()));
			if(finance2 == null) {
				SupplierFinance fin2 = new SupplierFinance();
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				fin2.setId(id);
				fin2.setYear(String.valueOf(twoYear()));
				supplier.getListSupplierFinances().add(fin2);
			}
			SupplierFinance finance3 = supplierFinanceService.getFinance(supplier.getId(), String.valueOf(threeYear()));
			if(finance3 == null) {
				SupplierFinance fin3 = new SupplierFinance();
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				fin3.setId(id);
				fin3.setYear(String.valueOf(oneYear()));
				supplier.getListSupplierFinances().add(fin3);
			}
		}

		model.addAttribute("company", DictionaryDataUtil.find(17));
        model.addAttribute("nature", DictionaryDataUtil.find(32));
		List<SupplierStockholder> stockList = supplier.getListSupplierStockholders();
        if (stockList == null || stockList.size() == 0) {
            SupplierStockholder stock = new SupplierStockholder();
            stock.setId(WfUtil.createUUID());
            stockList.add(stock);
            stock.setSupplierId(supplier.getId());
            supplier.setListSupplierStockholders(stockList);
        }
        List<SupplierAfterSaleDep> afterSaleDep = supplier.getListSupplierAfterSaleDep();
        if (afterSaleDep == null || afterSaleDep.size() == 0) {
            SupplierAfterSaleDep stock = new SupplierAfterSaleDep();
            stock.setId(WfUtil.createUUID());
            afterSaleDep.add(stock);
            stock.setSupplierId(supplier.getId());
            supplier.setListSupplierAfterSaleDep(afterSaleDep);
        }
		model.addAttribute("currSupplier", supplier);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("supplierId", supplier.getId());
		model.addAttribute("privnce", privnce);

		// 所有的不通过字段的名字
		SupplierAudit s = new SupplierAudit();
		s.setSupplierId(supplier.getId());;
		s.setAuditType("basic_page");
		List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);

		StringBuffer errorField = new StringBuffer();
		for(SupplierAudit audit: auditLists) {
			errorField.append(audit.getAuditField() + ",");
		}

		model.addAttribute("foregin", foregin);
		model.addAttribute("audit", errorField);
		return "ses/sms/supplier_register/basic_info";
	}

	/**
	 * 
	 *〈简述〉加载品目树
	 *〈详细描述〉
	 * @author myc
	 * @param id 当前节点Id
	 * @param code 编码
	 * @param supplierId 供应商Id
	 * @param status 状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/category_type", produces = "application/json;charset=UTF-8")
	public List < CategoryTree > getCategory(String id, String code, String supplierId, Integer status, String stype, String shenhe) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		List < CategoryTree > cateList = new ArrayList < CategoryTree > ();
		String typeId = "";
		//初始化跟节点
		if(StringUtils.isEmpty(id)) {
			if(StringUtils.isNotBlank(code)) {
				DictionaryData type = DictionaryDataUtil.get(code);
				CategoryTree ct = new CategoryTree();
				if(type != null) {
					if(type.getCode().equals("PRODUCT")) {
						DictionaryData dd = DictionaryDataUtil.get("GOODS");
						ct.setCode("PRODUCT");
						typeId = dd.getId();
					} else if(type.getCode().equals("SALES")) {
						DictionaryData dd = DictionaryDataUtil.get("GOODS");
						ct.setCode("SALES");
						typeId = dd.getId();
					} else {
						ct.setCode(code);
						typeId = type.getId();
					}
				}

				ct.setName(type.getName());
				ct.setId(typeId);
				List < SupplierItem > s = supplierItemService.getSupplierIdCategoryId(supplierId, typeId, code);
				if(s != null && s.size() > 0) {
					ct.setChecked(true);
				}
				ct.setIsParent("true");
				categoryList.add(ct);
			}
		}
		//加载子集节点
		if(StringUtils.isNotBlank(id)) {
			List < Category > child = categoryService.findPublishTree(id, status);
			Integer level = SupplierLevelUtil.getLevel(supplierId, code);
			if (level != null) {
			    for (int i = 0; i < child.size(); i++) {
			        Category cate = child.get(i);
			        if (cate.getLevel() != null && Integer.parseInt(cate.getLevel()) < level) {
			            child.remove(i);
			        }
			    }
			}
			for(Category c: child) {
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(c.getName());
				ct1.setParentId(c.getParentId());
				ct1.setId(c.getId());
				List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(), code);
				if(items != null && items.size() > 0) {
					ct1.setChecked(true);
				}
				List < Category > cList = categoryService.findTreeByPid(c.getId());
				if(cList != null && cList.size() > 0) {
					ct1.setIsParent("true");
				} else {
					ct1.setIsParent("false");
				}
				categoryList.add(ct1);
			}
		}
		for(CategoryTree catet: categoryList) {
			if(catet.getChecked() == true) {
				cateList.add(catet);
			}
		}
		if("true".equals(shenhe)) {
			return cateList;
		} else {
			return categoryList;
		}
	}

	@RequestMapping("/audit_org")
	public String audit_org(Model model, String name) {
		Supplier supp = supplierMapper.queryByName(name);
		Supplier supplier = supplierService.get(supp.getId());
		Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(supplier.getProcurementDepId());
		model.addAttribute("purchaseDep", orgnization);
		return "ses/sms/supplier_register/audit_org";
	}

	public Integer oneYear() {
		//	List<Integer> yearList=new ArrayList<Integer>();

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int year2 = year - 2; //2014
		return year2;
	}

	public Integer twoYear() {
		//	List<Integer> yearList=new ArrayList<Integer>();

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int year3 = year - 3; //2013
		return year3;
	}

	public Integer threeYear() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String mont = sdf.format(date).split("-")[1];
		Integer month = Integer.valueOf(mont);
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		Integer yearThree = 0;
		if(month < 6) {
			yearThree = year - 4; //2012

		} else {
			yearThree = year - 1; //2015

		}
		return yearThree;
	}

	@RequestMapping(value = "/audit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String auditMsg(SupplierAudit supplierAudit) {
		List < SupplierAudit > list = supplierAuditService.selectByPrimaryKey(supplierAudit);
		return JSON.toJSONString(list.get(0));
	}

	public List < String > getAuditFiled(String id) {
		List < String > list = new LinkedList < String > ();
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(id);
		List < SupplierAudit > audit = supplierAuditService.selectByPrimaryKey(supplierAudit);
		for(SupplierAudit s: audit) {
			list.add(s.getAuditField());
		}
		return list;
	}

	/**
	 *〈简述〉去重
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param list
	 */
	public void removeSame(List < Category > list) {
		for(int i = 0; i < list.size() - 1; i++) {
			for(int j = list.size() - 1; j > i; j--) {
				if(list.get(j).getId().equals(list.get(i).getId())) {
					list.remove(j);
				}
			}
		}
	}

	/**
	 *〈简述〉异步加载品目合同上传
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierId 供应商Id
	 * @param model Model对象
	 * @param supplierTypeid 供应商类型Id
	 * @param flag
	 * @return
	 */
	@RequestMapping("/ajaxContract")
	public String ajaxContract(String supplierId, Model model, String supplierTypeId, Integer pageNum) {
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");

		List < Category > category = new ArrayList < Category > ();
		List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		for(SupplierItem item: itemsList) {
			Category cate = categoryService.findById(item.getCategoryId());
			cate.setId(item.getId());
			category.add(cate);
		}
		// 查询品目合同信息
		List < ContractBean > contract = supplierService.getContract(category);
		for(ContractBean con: contract) {
			con.setOneContract(id1);
			con.setTwoContract(id2);
			con.setThreeContract(id3);
			con.setOneBil(id4);
			con.setTwoBil(id5);
			con.setThreeBil(id6);
		}
		// 分页,pageSize == 10
		PageInfo < SupplierItem > pageInfo = new PageInfo < SupplierItem > (itemsList);
		model.addAttribute("result", pageInfo);
		model.addAttribute("contract", contract);
		// 年份
		List < Integer > years = supplierService.getThressYear();
		model.addAttribute("years", years);
		model.addAttribute("supplierTypeId", supplierTypeId);
		model.addAttribute("supplierId", supplierId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		// 不通过字段的名字
		SupplierAudit s = new SupplierAudit();
		s.setSupplierId(supplierId);;
		s.setAuditType("contract_page");
		List < SupplierAudit > auditLists = supplierAuditService.selectByPrimaryKey(s);

		StringBuffer errorField = new StringBuffer();
		for(SupplierAudit audit: auditLists) {
			errorField.append(audit.getAuditField() + ",");
		}
		model.addAttribute("audit", errorField);
		
		return "ses/sms/supplier_register/ajax_contract";
	}

	/**
	 * 
	 * @Title: contractUp
	 * @Description: 品目合同上传
	 * author: Li Xiaoxiao 
	 * @param @return     
	 * @return String     
	 * @throws
	 */
	@RequestMapping(value = "/contract")
	public String contractUp(String supplierId, Model model, String supplierTypeIds, String flag) {
		if(flag.equals("1")) {
			Supplier supplier = supplierService.get(supplierId);
			model.addAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/items";
		}
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_register/contract";
	}

	public void record(String operationInfo, Object obj1, Object obj2, String supplierId) throws Exception {
		if(obj1 != null && obj2 != null) {
			Class < ? extends Object > clazz1 = obj1.getClass();
			Field[] fields = clazz1.getDeclaredFields();
			StringBuffer sb = new StringBuffer();
			sb.append("");
			Method m = null;
			Method m2 = null;
			String upperCase = null;
			for(Field f: fields) {
				String str = "";
				if(!f.getName().contains("serialVersionUID") && !f.getName().contains("list") && !f.getName().contains("List") && !f.getName().contains("Mat") && !f.getName().contains("supplierTypeIds") && !f.getName().contains("item") && !f.getName().contains("itemType") && !f.getName().contains("categoryParam") && !f.getName().contains("ParamVleu") && !f.getName().contains("armyCity") && !f.getName().contains("user")) {
					upperCase = "get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
					m = (Method) obj1.getClass().getMethod(upperCase);
					m2 = (Method) obj2.getClass().getMethod(upperCase);
					if(m.equals(m2)) {
						Object obj3 = m.invoke(obj1);
						Object obj4 = m2.invoke(obj2);
						if(obj3 != null && obj4 != null) {
							if(!obj3.toString().equals(obj4.toString())) {
								str = f.getName() + "," + obj3 + "," + obj4 + ";";
							}
						}
						sb.append(str);
					}
				}
			}
			String[] spl = sb.toString().split(";");
			if(spl[0].trim().length() != 0) {
				for(String sss: spl) {
					SupplierModify supplierModify = new SupplierModify();
					String[] ss = sss.split(",");
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					supplierModify.setId(id);
					supplierModify.setSupplierId(supplierId);
					supplierModify.setBeforeField(ss[0]);
					supplierModify.setBeforeContent(ss[1]);
					// sh.setAfterContent(ss[1]);
					/*sh.setCreatedAt(new Date());*/
					supplierModify.setmodifyType("basic_page");
					supplierModify.setListType(0);
					SupplierModify mo = supplierModifyService.findBySupplierId(supplierModify);
					// 删除之前的记录
					 if(mo != null) {
						 supplierModifyService.delete(mo);
					}
					supplierModifyService.add(supplierModify);
				}
			}
		}
	}

	/**
	 *〈简述〉
	 * 判断提交审核后有没有超过45天以及查询初审机构信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "validateAuditTime", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String validateAuditTime(String userId) throws Exception {
		HashMap < String, Object > allInfo = new HashMap < String, Object > ();
		// 根据userId查询出Expert
		Supplier supplier = supplierService.selectById(userService.getUserById(userId).getTypeId());
		Date submitDate = supplier.getSubmitAt();
		allInfo.put("submitDate", new SimpleDateFormat("yyyy年MM月dd日").format(submitDate));
		// 判断有没有超过45天
		String isok;
		int betweenDays = expertService.daysBetween(submitDate);
		if(betweenDays > 45) {
			isok = "0";
		} else {
			isok = "1";
		}
		allInfo.put("isok", isok);
		// 查询初审机构信息
		HashMap < String, Object > map = new HashMap < String, Object > ();
		map.put("id", supplier.getProcurementDepId());
		map.put("typeName", "1");
		List < PurchaseDep > depList = purchaseOrgnizationService.findPurchaseDepList(map);
		if(depList != null && depList.size() > 0) {
			PurchaseDep purchaseDep = depList.get(0);
			allInfo.put("contact", purchaseDep.getContact() == null ? "暂无" : purchaseDep.getContact());
			allInfo.put("contactTelephone", purchaseDep.getContactTelephone() == null ? "暂无" : purchaseDep.getContactTelephone());
		}
		return JSON.toJSONString(allInfo);
	}

	@ResponseBody
	@RequestMapping("/getUUID")
	public String getUUID() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}

	@ResponseBody
	@RequestMapping("/validateCreditCode")
	public String validateCreditCode(String creditCode, String supplierId) {
		List < Supplier > list = supplierService.validateCreditCode(creditCode);
		boolean flag = true;
		if(list != null && list.size() > 0) {
			for(Supplier supplier: list) {
				if(!supplierId.equals(supplier.getId())) {
					flag = false;
					break;
				}
			}
		}
		return flag ? "0" : "1";
	}

	/**
	 *〈简述〉异步删除供应商地址信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delAddress")
	public String delAddress(String id) {
		supplierAddressService.delAddressByPrimaryId(id);
		return "ok";
	}

	/**
	 *〈简述〉新增物资生产信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param number
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addProductCert")
	public ModelAndView addProductCert(String number, Model model) {
		model.addAttribute("certProNumber", number);
		model.addAttribute("id", WfUtil.createUUID());
		//初始化供应商注册附件类型
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierProCert());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return new ModelAndView("ses/sms/supplier_register/add_product_cert");
	}

	/**
	 *〈简述〉新增物资销售信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param number
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSaleCert")
	public ModelAndView addSaleCert(String number, Model model) {
		model.addAttribute("certSaleNumber", number);
		model.addAttribute("id", UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
		//初始化供应商注册附件类型
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierSellCert());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return new ModelAndView("ses/sms/supplier_register/add_sale_cert");
	}

	/**
	 *〈简述〉添加供应商工程证书信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param number
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addEngCert")
	public ModelAndView addEngCert(String number, Model model) {
		model.addAttribute("certEngNumber", number);
		model.addAttribute("id", UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
		//初始化供应商注册附件类型
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return new ModelAndView("ses/sms/supplier_register/add_eng_cert");
	}

	/**
	 *〈简述〉添加供应商资质资格信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param number
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addAptCert")
	public ModelAndView addAptCert(String number, Model model) {
		model.addAttribute("certAptNumber", number);
		model.addAttribute("id", UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
		//初始化供应商注册附件类型
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCertFile());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("typeList", qualificationService.findList(null, null, 4));
		return new ModelAndView("ses/sms/supplier_register/add_apt_cert");
	}

	/**
	 *〈简述〉添加供应商资服务质证书
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param number
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addSeCert")
	public ModelAndView addSeCert(String number, Model model) {
		model.addAttribute("certSeNumber", number);
		model.addAttribute("id", UUID.randomUUID().toString().toUpperCase().replaceAll("-", ""));
		//初始化供应商注册附件类型
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierServeCert());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return new ModelAndView("ses/sms/supplier_register/add_se_cert");
	}

	/**
	 * 品目树下载
	 * @param request
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/download_category")
	public ResponseEntity < byte[] > download(HttpServletRequest request, String filename) throws IOException {
		//	    	filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		String path = PathUtil.getWebRoot() + "excel/产品目录.xls";;
		File file = new File(path);

		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("产品目录.xls".getBytes("UTF-8"), "iso-8859-1"); //为了解决中文名称乱码问题  
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity < byte[] > (FileUtils.readFileToByteArray(file),
			headers, HttpStatus.CREATED);
	}
	
    /**
     *〈简述〉
     * 异步删除售后服务机构
     *〈详细描述〉
     * @author WangHuijie
     * @param request
     * @param stockholderIds
     * @param supplierId
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAfterSaleDep")
    public void deleteCertEng(String afterSaleDepIds) {
        supplierAfterSaleDepService.deleteAfterSaleDep(afterSaleDepIds);
    }
    
    /**
     *〈简述〉根据证书编号获取附件信息
     *〈详细描述〉
     * @author WangHuijie
     * @param certCode
     * @param supplierId
     * @param Model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFileByCode")
    public ModelAndView addSeCert(String certCode, String supplierId, Model model, String number) {
        List<SupplierCertEng> certEng = supplierCertEngService.selectCertEngByCode(certCode, supplierId);
        if (certEng != null && certEng.size() > 0) {
            model.addAttribute("number", number);
            //初始化供应商注册附件类型
            model.addAttribute("id", certEng.get(0).getId());
            model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
            model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
            return new ModelAndView("ses/sms/supplier_register/supplier_eng_file");
        }
        return null;
    }
    
    @ResponseBody
    @RequestMapping("/saveItemsInfo")
    public void saveItemsInfo(Supplier supplier) {
        supplierItemService.updateByPrimaryKeySelective(supplier.getListSupplierItems());
    }
    
    /**
     *〈简述〉
     * 异步判断供应商是否满足下载条件
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierId
     * @return
     */
    @ResponseBody
    @RequestMapping("/isPass")
    public String isPass(String supplierId) {
        BigDecimal score = supplierService.getScoreBySupplierId(supplierId);
        List <SupplierTypeRelate> relate = supplierTypeRelateService.queryBySupplier(supplierId);
        for (SupplierTypeRelate type : relate) {
            if (type.getSupplierTypeId().equals("SALES")) {
                if (score.compareTo(BigDecimal.valueOf(3000)) != 1) {
                    return "0";
                }
            }
        }
        return "1";
    }

    /**
     *〈简述〉根据类型获取等级
     *〈详细描述〉
     * @author WangHuijie
     * @param typeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getAptLevel", produces = "application/json;charset=utf-8")
    public String getAptLevel(String typeId) {
        List<DictionaryData> data = qualificationLevelService.getByQuaId(typeId);
        if (data != null) {
            return JSON.toJSONString(data);
        }
        return null;
    }
    
    /**
     *〈简述〉根据类型和证书编号获取等级
     *〈详细描述〉
     * @author WangHuijie
     * @param typeId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getLevel", produces = "application/json;charset=utf-8")
    public String getAptLevel(String typeId, String certCode, String supplierId) {
        Supplier supplier = supplierService.get(supplierId);
        String level = supplierCertEngService.getLevel(typeId, certCode, supplier.getSupplierMatEng().getId());
        if (level != null) {
            return JSON.toJSONString(DictionaryDataUtil.findById(level));
        }
        return null;
    }
}