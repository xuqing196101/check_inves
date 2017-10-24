package ses.controller.sys.sms;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.LoginLogService;
import common.service.UploadService;
import common.utils.Arith;
import common.utils.IDCardUtil;
import common.utils.RSAEncrypt;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import ses.constants.SupplierConstants;
import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.DeleteLog;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.NoticeDocumentService;
import ses.service.bms.QualificationLevelService;
import ses.service.bms.QualificationService;
import ses.service.bms.UserServiceI;
import ses.service.ems.DeleteLogService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAfterSaleDepService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditNotService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSeService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierPorjectQuaService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 供应商控制类
 * @author hxg
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplier")
public class SupplierController extends BaseSupplierController {
	
	private static Logger logger = Logger.getLogger(SupplierController.class);
	
	@Autowired
	private SupplierService supplierService; // 供应商基本信息

	@Autowired
	private SupplierMatProService supplierMatProService; // 供应商物资生产专业信息

	@Autowired
	private SupplierMatSellService supplierMatSellService; // 供应商物资销售专业信息
	
	@Autowired
	private SupplierMatSeService supplierMatSeService; // 供应商服务专业信息

	@Autowired
	private SupplierMatEngService supplierMatEngService; // 供应商工程专业信息
	
    @Autowired
    private SupplierCertEngService supplierCertEngService;// 工程证书
    
    @Autowired
	private SupplierAptituteService supplierAptituteService;// 工程证书详细信息
	
	@Autowired
	private QualificationLevelService qualificationLevelService; // 供应商物资销售专业信息

	@Autowired
	private OrgnizationServiceI orgnizationServiceI; // 机构
	
	@Autowired
	private QualificationService qualificationService; // 资质类型

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;// 数据字典

	@Autowired
	private NoticeDocumentService noticeDocumentService;

	@Autowired
	private CategoryService categoryService;// 目录

	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;// 供应商类型关联

	@Autowired
	private UploadService uploadService;// 上传

	@Autowired
	private SupplierItemService supplierItemService;// 供应商品目

	@Autowired
	private SupplierAfterSaleDepService supplierAfterSaleDepService; //售后服务机构

	@Autowired
	private AreaServiceI areaService;// 地区

	@Autowired
	private SupplierAddressService supplierAddressService;// 地址

	@Autowired
	private SupplierBranchService supplierBranchService;// 境外分支

	@Autowired
	private SupplierAuditService supplierAuditService;// 供应商审核

	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;// 采购机构

	@Autowired
	private UserServiceI userService;// 用户
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	@Autowired
    private SupplierAuditNotService supplierAuditNotService;
	
    @Autowired
    private LoginLogService loginLogService;// 登录日志
    
    @Autowired
    private DeleteLogService deleteLogService;// 删除日志
    
    /**
     * 基本信息（第一步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/basicInfo")
    public String basicInfo(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 1);
		
    	// 初始化常量
		initBasicConstants(model, supplier);
		
		// 初始化审核不通过字段
		initBasicAudit(model, supplier);
    	
    	return "ses/sms/supplier_register/basic_info";
    }
    /**
     * 供应商类型（第二步）
     * @param model
     * @param suppId
     * @return
     * @throws InterruptedException 
     */
    @RequestMapping("/supplierType")
    public String supplierType(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	if(supplier != null && supplier.getStatus() == 2){
    		// 设置审核不通过的品目
        	List<SupplierItem> itemList_product = supplierItemService.removeAuditNotItems(null, suppId, "PRODUCT");
        	List<SupplierItem> itemList_sales = supplierItemService.removeAuditNotItems(null, suppId, "SALES");
        	List<SupplierItem> itemList_project = supplierItemService.removeAuditNotItems(null, suppId, "PROJECT");
        	List<SupplierItem> itemList_service = supplierItemService.removeAuditNotItems(null, suppId, "SERVICE");
        	
        	// 随后设置供应商类型（如果所选产品目录全部被退回，则去掉对应所属的供应商类型）
        	if(itemList_product == null || itemList_product.isEmpty()){
        		//supplierTypeRelateService.delete(suppId, "PRODUCT");
        	}
        	if(itemList_sales == null || itemList_sales.isEmpty()){
        		//supplierTypeRelateService.delete(suppId, "SALES");
        	}
        	if(itemList_project == null || itemList_project.isEmpty()){
        		//supplierTypeRelateService.delete(suppId, "PROJECT");
        	}
        	if(itemList_service == null || itemList_service.isEmpty()){
        		//supplierTypeRelateService.delete(suppId, "SERVICE");
        	}
        	
        	// 如果供应商类型被退回，自动去掉勾选
        	SupplierAudit supplierAudit = new SupplierAudit();
        	supplierAudit.setSupplierId(suppId);
        	supplierAudit.setAuditType("supplierType_page");
        	List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{2});
        	if(auditList != null && auditList.size() > 0){
        		for(SupplierAudit audit : auditList){
        			String id = audit.getAuditField();
        			DictionaryData dd = dictionaryDataServiceI.getDictionaryData(id);
        			if(dd != null){
        				supplierTypeRelateService.delete(suppId, dd.getCode());
        			}
        		}
        	}
    	}
    	
    	supplier = supplierService.get(suppId, 2);
    	
		// 初始化常量
		initSupplierTypeConstants(model, supplier);
		
		// 初始化审核不通过字段
		initSupplierTypeAudit(model, supplier);

    	return "ses/sms/supplier_register/supplier_type";
    }
    /**
     * 产品类别（第三步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/items")
    public String items(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 3);
    	
    	model.addAttribute("currSupplier", supplier);
    	
    	return "ses/sms/supplier_register/items";
    }
    /**
     * 资质文件（第四步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/aptitude")
    public String aptitude(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 4);
    	
		initAptitudeConstants(model, supplier);
		initAptitudeAudit(model, supplier);

    	return "ses/sms/supplier_register/aptitude";
    }
    /**
     * 销售合同（第五步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/contract")
    public String contract(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 5);
    	
    	model.addAttribute("supplierTypeIds", supplier.getSupplierTypeIds());
		model.addAttribute("supplierId", suppId);
		model.addAttribute("currSupplier", supplier);
		
    	return "ses/sms/supplier_register/contract";
    }
    /**
     * 采购机构（第六步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/procurement_dep")
    public String procurement_dep(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 6);
    	
		HashMap < String, Object > map = new HashMap < String, Object > ();
		// 采购机构
		List < PurchaseDep > depList = null;
		if(supplier.getProcurementDepId() != null) {
			map.put("id", supplier.getProcurementDepId());
			map.put("typeName", "1");

			depList = purchaseOrgnizationService.findPurchaseDepList(map);

			if(depList != null && depList.size() > 0) {
				Orgnization orgnization = depList.get(0);
				model.addAttribute("orgnization", orgnization);
			}
		}

		model.addAttribute("currSupplier", supplier);

		HashMap < String, Object > map1 = new HashMap < String, Object > ();
		map1.put("typeName", "1");

		List < PurchaseDep > list = purchaseOrgnizationService.findPurchaseDepList(map1);
        List < PurchaseDep > purList = new ArrayList < PurchaseDep > ();
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
		for(PurchaseDep purchaseDep: list) {
		    if (purchaseDep.getIsAuditSupplier() == 1) {
                Area prov = areaService.listById(purchaseDep.getProvinceId());
                Area city = areaService.listById(purchaseDep.getCityId());
                if(prov != null && city != null) {
                    purchaseDep.setAddress(prov.getName() + city.getName());
                }
                // 统计待审核供应商数量
                int pendingAuditCount = supplierService.countAuditByPurchaseDepId(purchaseDep.getId());
                purchaseDep.setPendingAuditCount(pendingAuditCount);
                purList.add(purchaseDep);
		    }
		}
		model.addAttribute("allPurList", purList);
    	
    	return "ses/sms/supplier_register/procurement_dep";
    }
    /**
     * 下载承诺书和申请表（第七步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/template_download")
    public String template_download(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 7);
    	
		model.addAttribute("currSupplier", supplier);
    	
    	return "ses/sms/supplier_register/template_download";
    }
    /**
     * 上传承诺书和申请表（第八步）
     * @param model
     * @param suppId
     * @return
     */
    @RequestMapping("/template_upload")
    public String template_upload(Model model, String suppId){
    	
    	/*boolean bool = checkSupplier(suppId);
    	if(!bool){
    		return null;
    	}*/
    	Supplier supplier = checkSupplier(suppId);
    	if(supplier == null){
    		return null;
    	}
    	
    	supplier = supplierService.get(suppId, 8);
    	
    	initUploadConstants(model, supplier);
		initUploadAudit(model, supplier);
		
    	return "ses/sms/supplier_register/template_upload";
    }

    /**
     * 供应商注册步骤
     * @param step
     * @param supplierId
     * @param model
     * @return
     */
    @RequestMapping("/updateStep")
    public String updateStep(String step, String supplierId, Model model) {
        int stepNumber = Integer.parseInt(step);
        model.addAttribute("suppId", supplierId);
        if (stepNumber == 1) {
            return "redirect:/supplier/basicInfo.html";
        } else if (stepNumber == 2) {
        	return "redirect:/supplier/supplierType.html";
        } else if (stepNumber == 3) {
        	return "redirect:/supplier/items.html";
        } else if (stepNumber == 4) {
        	return "redirect:/supplier/aptitude.html";
        } else if (stepNumber == 5) {
        	return "redirect:/supplier/contract.html";
        } else if (stepNumber == 6) {
        	return "redirect:/supplier/procurement_dep.html";
        } else if (stepNumber == 7) {
        	return "redirect:/supplier/template_download.html";
        }
        return null;
    }
	
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
	 * @return: String
	 */
	@RequestMapping("registration_page")
	public String registrationPage(Model model) {
		DictionaryData dd = DictionaryDataUtil.get("SUPPLIER_REGISTER_NOTICE");
		if(dd != null) {
			Map < String, Object > param = new HashMap < String, Object > ();
			param.put("docType", dd.getId());
			model.addAttribute("doc", noticeDocumentService.findDocByMap(param));
			model.addAttribute("docName", noticeDocumentService.findDocNameByMap(param));
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
	 * @return: String
	 * @throws Exception 
	 */
	@RequestMapping(value = "register")
	public String register(HttpServletRequest request, Model model, Supplier supplier) throws Exception {
		//获取供应商 输入的密码
		String pwd = supplier.getPassword();
		//获取供应商 输入的确定密码
		String cpwd = supplier.getConfirmPassword();
		if(pwd != null){
			//获取私钥 解密 输入密码
			supplier.setPassword(RSAEncrypt.decryptPrivate(pwd));
		}
		if(cpwd != null){
			//获取私钥解密方法 解密确定密码
			supplier.setConfirmPassword(RSAEncrypt.decryptPrivate(cpwd));
		}
		boolean flag = validateRegister(request, model, supplier);
		if(flag) {
			supplier = supplierService.register(supplier);
			session.setAttribute("loginName", supplier.getLoginName());
			model.addAttribute("suppId", supplier.getId());
			return "redirect:/supplier/basicInfo.html";
		}
		return "ses/sms/supplier_register/register";
	}
	
	/**
	 * 供应商登录
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping("login")
	public String login(Model model, String name) {
	    
        String user = (String) request.getSession().getAttribute("loginName");
        
        if(user == null){
        	alertLogin("您未登录，请登录！");
        }
     
        if(null != user && !user.equals(name)){
        	alertLogin("不是当前操作人，请登录修改！");
        	return null;
        }else{
			Supplier supp = supplierService.queryByName(name);
			//Supplier supplier = supplierService.get(supp.getId(),1);
			// 通过supplierId查询用户信息
			if(supp != null){
				User existsUser = userService.findByTypeId(supp.getId());
				// 将用户信息存入登录日志
				loginLogService.saveOnlineUser(existsUser, request);
			}
			
			model.addAttribute("suppId", supp.getId());
			return "redirect:/supplier/basicInfo.html";
		}
	}

	/**
	 * 查询采购机构
	 * @param request
	 * @param pid
	 * @param cid
	 * @param model
	 * @return
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
		List < PurchaseDep > purList = new ArrayList < PurchaseDep > ();
		if(null != list && !list.isEmpty()){
            for(PurchaseDep purchaseDep: list) {
                for(Orgnization org: listOrgnizations1) {
                    if(purchaseDep.getOrgnization().getId().equals(org.getId())) {
                        list.remove(org);
                    }
                }
            }
            for(PurchaseDep purchaseDep: list) {
                if (purchaseDep.getIsAuditSupplier() == 1) {
                    Area pro = areaService.listById(purchaseDep.getProvinceId());
                    Area city = areaService.listById(purchaseDep.getCityId());
                    if(pro != null && city != null) {
                        purchaseDep.setAddress(pro.getName() + city.getName());
                    }
                    purList.add(purchaseDep);
                }
            }
        }
		model.addAttribute("allPurList", purList);
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
	 *〈简述〉临时保存
	 *〈详细描述〉
	 * @author myc
	 * @param request {@link HttpServletRequest}
	 * @param supplier {@link Supplier}
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/temporarySave", produces = "html/text;charset=UTF-8")
	public String temporarySave(HttpServletRequest request, Supplier supplier) {
		String res = StaticVariables.SUCCESS;
		try {
        	// 供应商名称校验：供应商库（除去临时供应商）
			if(StringUtils.isNotBlank(supplier.getSupplierName())){
				boolean boolSupplierName = supplierService.checkSupplierName(supplier.getId(), supplier.getSupplierName());
				if(!boolSupplierName){
					return "supplierNameExists";
				}
			}
			// 统一社会信用代码校验：供应商库（除去临时供应商）
			if(StringUtils.isNotBlank(supplier.getCreditCode())){
				boolean boolCreditCode = supplierService.checkCreditCode(supplier.getId(), supplier.getCreditCode());
				if(!boolCreditCode){
					return "creditCodeExists";
				}
//				//注销
//                DeleteLog deleteLog = deleteLogService.queryByTypeId(null, supplier.getCreditCode());
//                if(null != deleteLog && null != deleteLog.getCreateAt()){
//                    int betweenDays = supplierService.daysBetween(deleteLog.getCreateAt());
//                    if(betweenDays > 180){
//                    	return "disabled_180";
//                    }
//                }
//                //审核不通过
//                SupplierAuditNot supplierAuditNot = supplierAuditNotService.selectByCreditCode(supplier.getCreditCode());
//                if(null != supplierAuditNot && null != supplierAuditNot.getCreatedAt()){
//                    int betweenDays = supplierService.daysBetween(supplierAuditNot.getCreatedAt());
//                    if(betweenDays > 180){
//                    	return "disabled_180";
//                    }
//                }
			}else{
                //supplier.setCreditCode("");
            }
		
			// 出资人信息校验
			List<SupplierStockholder> stockholders = supplier.getListSupplierStockholders();
			int count=0;
			Set<String> set=new HashSet<String>();
			if(stockholders!=null&&stockholders.size()>1){
				for(SupplierStockholder s:stockholders){
					if(s.getIdentity()!=null&&s.getIdentity().trim().length()!=0){
						set.add(s.getIdentity());
						count++;
					}
				}
				if(count!=set.size()){
					return "errIdentity";
				}
			}
			
			Supplier before = supplierService.get(supplier.getId(), 1);
			if(before != null && before.getStatus() != null && before.getStatus() == 2){
				supplierService.record("", before, supplier, supplier.getId()); //记录供应商退回修改的内容
			}
			
			supplierService.perfectBasic(supplier);
			
			// 近三年财务信息
			List<SupplierFinance> finances = supplier.getListSupplierFinances();
			if(finances != null && finances.size() >= 3){
				if (finances.get(0).getTotalNetAssets() != null
						&& finances.get(1).getTotalNetAssets() != null
						&& finances.get(2).getTotalNetAssets() != null) {
					// 判断注册资金是否足够
					// BigDecimal score = supplierService.getScoreBySupplierId(supplier.getId());
					BigDecimal score = supplierService.getScoreByFinances(supplier.getListSupplierFinances());
					if (score.compareTo(BigDecimal.valueOf(100)) == -1) {
						res = "notPass";
					}
				}
			}
		} catch(Exception e) {
			res = StaticVariables.FAILED;
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * 保存采购机构
	 * @param request
	 * @param supplier
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveDep", produces = "html/text;charset=UTF-8")
	public String saveDep(HttpServletRequest request, Supplier supplier) {
		String res = StaticVariables.SUCCESS;
		try {
			supplierService.updateSupplierProcurementDep(supplier);
		} catch(Exception e) {
			res = StaticVariables.FAILED;
			e.printStackTrace();
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
	 * @return: String
	 * @throws Exception
	 */
	@RequestMapping(value = "perfect_basic",method = RequestMethod.POST)
	public String perfectBasic(HttpServletRequest request, Model model, Supplier supplier) throws Exception {
		
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		
		boolean bool = validateBasicInfo(request, model, supplier);

		if(bool) {
			Supplier before = supplierService.get(supplier.getId(), 1);
			if(before != null && before.getStatus() != null && before.getStatus() == 2){
				supplierService.record("", before, supplier, supplier.getId()); //记录供应商退回修改的内容
			}
			supplierService.perfectBasic(supplier);
			model.addAttribute("suppId", supplier.getId());
			return "redirect:/supplier/supplierType.html";
		} else {
			
			initBasicConstants(model, checkSupplier);
			
			initBasicAudit(model, checkSupplier);
			
			//校验未通过，信息回传
			returnInfo(model, checkSupplier, supplier);
			
			return "ses/sms/supplier_register/basic_info";
		}
	}
	
	private void returnInfo(Model model, Supplier persistentSup, Supplier supplier){
		//BeanUtilsExt.copyPropertiesIgnoreNull(persistentSup, supplier);
		supplier.setStatus(persistentSup.getStatus());
		
		if(supplier.getConcatProvince() != null) {
			List < Area > concity = areaService.findAreaByParentId(supplier.getConcatProvince());
			supplier.setConcatCityList(concity);
		}
		if(supplier.getArmyBuinessProvince() != null) {
			List < Area > armcity = areaService.findAreaByParentId(supplier.getArmyBuinessProvince());
			supplier.setArmyCity(armcity);
		}
		if(supplier.getAddressList() != null && supplier.getAddressList().size() > 0) {
			for(SupplierAddress b: supplier.getAddressList()) {
				if(StringUtils.isNotBlank(b.getProvinceId())) {
					List < Area > city = areaService.findAreaByParentId(b.getProvinceId());
					b.setAreaList(city);
				}
			}
		}
		
		// 去掉空的生产经营地址
		List<SupplierAddress> addressList = supplier.getAddressList();
		if(addressList != null && addressList.size() > 0) {
			Iterator<SupplierAddress> itr = addressList.iterator();
			while(itr.hasNext()){
				SupplierAddress address = itr.next();
				if (address.getId() == null) {
					itr.remove();
				}
			}
		}
		
		// 去掉空的境外分支
		List<SupplierBranch> branchList = supplier.getBranchList();
		if(branchList != null && branchList.size() > 0) {
		    Iterator<SupplierBranch> itr = branchList.iterator();
			while(itr.hasNext()){
				SupplierBranch branch = itr.next();
				if (branch.getId() == null) {
					itr.remove();
				}
			}
		}
		
		// 去掉空的股东信息
		List<SupplierStockholder> listSupplierStockholders = supplier.getListSupplierStockholders();
		if(listSupplierStockholders != null && listSupplierStockholders.size() > 0) {
		    Iterator<SupplierStockholder> itr = listSupplierStockholders.iterator();
			while(itr.hasNext()){
				SupplierStockholder stockholder = itr.next();
				if (stockholder.getId() == null) {
					itr.remove();
				}
			}
		}
		
		// 去掉空的售后服务机构信息
		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplier.getListSupplierAfterSaleDep();
		if(listSupplierAfterSaleDep != null && listSupplierAfterSaleDep.size() > 0) {
            Iterator<SupplierAfterSaleDep> itr = listSupplierAfterSaleDep.iterator();
			while(itr.hasNext()){
				SupplierAfterSaleDep afterSale = itr.next();
				if (afterSale.getId() == null) {
					itr.remove();
				}
			}
		}
		
		model.addAttribute("currSupplier", supplier);
	}
	
	/**
	 *〈简述〉初始化基本信息常量
	 *〈详细描述〉
	 * @author myc
	 * @param model
	 * @param supplier 供应商
	 */
	private void initBasicConstants(Model model, Supplier supplier) {
		//初始化省份
		List < Area > province = areaService.findRootArea();
		model.addAttribute("province", province);
		//初始化供应商注册附件类型
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		//初始化公司性质
		model.addAttribute("company", DictionaryDataUtil.find(17));
		model.addAttribute("nature", DictionaryDataUtil.find(32));
		//初始化所在国家
		model.addAttribute("foregin", DictionaryDataUtil.find(24));
		//初始化地址
		Area area = supplier.getArea();
		if(area != null){
			List < Area > city = areaService.findAreaByParentId(area.getParentId());
			model.addAttribute("city", city);
			model.addAttribute("area", area);
		}else{
			if(supplier.getAddress() != null) {
				area = areaService.listById(supplier.getAddress());
				List < Area > city = areaService.findAreaByParentId(area.getParentId());
				model.addAttribute("city", city);
				model.addAttribute("area", area);
			}
		}
		//当前供应商
		model.addAttribute("currSupplier", supplier);
	}
	
	/**
	 * 初始化供应商类型常量
	 * @param model
	 * @param supplier
	 */
	private void initSupplierTypeConstants(Model model, Supplier supplier){
		List < DictionaryData > gcfwList = DictionaryDataUtil.find(6);// 物资/工程/服务
		if(gcfwList != null && !gcfwList.isEmpty()){
			for(int i = 0; i < gcfwList.size(); i++) {
				DictionaryData dd = gcfwList.get(i);
				String code = dd.getCode();
				if(code.equals("GOODS")) {// 除去物资
					gcfwList.remove(dd);
				}
			}
		}
		
		model.addAttribute("gcfwList", gcfwList);
		List < DictionaryData > scxsList = DictionaryDataUtil.find(8);// 物资生产/物资销售
		model.addAttribute("scxsList", scxsList);
		
		//初始化供应商注册附件类型
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("rootArea", areaService.findRootArea());
		//资质类型
		List<Qualification> quaList = qualificationService.findList(null, Integer.MAX_VALUE, null, 4);
		// 去掉下面的代码（只要后台维护的资质，不要供应商自己添加的资质）
		/*List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, supplier.getId());
		if(supplierQua != null && !supplierQua.isEmpty()){
			for(SupplierPorjectQua qua : supplierQua){
            	Qualification q = new Qualification();
            	q.setId(qua.getName());
            	q.setName(qua.getName());
            	quaList.add(q);
            }
		}*/
		model.addAttribute("quaList", quaList);
		model.addAttribute("quaListJson", JSON.toJSONString(quaList));
		// 物资销售是否满足条件
		//String isSalePass = isPass(supplier.getId(), "SALES");
		String isSalePass = "1";
		Supplier basicSupplier = supplierService.get(supplier.getId(), 1);
		BigDecimal saleScore = supplierService.getScoreByFinances(basicSupplier.getListSupplierFinances());
		if (saleScore.compareTo(BigDecimal.valueOf(3000)) == -1) {
			isSalePass = "0";
		}
		model.addAttribute("isSalePass", isSalePass);
		
		model.addAttribute("currSupplier", supplier);
	}
	
	/**
	 * 初始化附件上传常量
	 * @param model
	 * @param supplier
	 */
	private void initUploadConstants(Model model, Supplier supplier){
		Integer sysKey = Constant.SUPPLIER_SYS_KEY;
		model.addAttribute("sysKey", sysKey);
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("currSupplier", supplier);
	}
	
	/**
	 * 初始化资质文件常量
	 * @param model
	 * @param supplier
	 */
	private void initAptitudeConstants(Model model, Supplier supplier){
		
		// 查询品目资质文件相关
		Map<String, Object> aptitudeMap = supplierItemService.getAptitude(supplier.getId(), supplier.getSupplierTypeIds());
		
		/*model.addAttribute("saleUp", aptitudeMap.get("saleUp"));
		model.addAttribute("saleShow", aptitudeMap.get("saleShow"));
		model.addAttribute("proQua", aptitudeMap.get("proQua"));
		model.addAttribute("saleQua", aptitudeMap.get("saleQua"));
		model.addAttribute("serviceQua", aptitudeMap.get("serviceQua"));*/
		model.addAllAttributes(aptitudeMap);
		
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("businessId", supplier.getId());
		String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		model.addAttribute("typeId", id);
		
		// 工程
		boolean isEng = false;
		String suppTypeIds = supplier.getSupplierTypeIds();
		if(suppTypeIds != null){
			String[] typeIds = suppTypeIds.split(",");
			for(String type: typeIds) {
				if(type.equals("PROJECT")) {
					isEng = true;
					break;
				}
			}
		}
		
		if(isEng) {
			// 获取工程资质
			Map<String, Object> engAptituteMap = supplierItemService.getEngAptitute(supplier.getId());
			model.addAttribute("modifiedCertCodes", engAptituteMap.get("modifiedCertCodes"));
			model.addAttribute("allTreeList", engAptituteMap.get("allTreeList"));
			model.addAttribute("engTypeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		}
		
		model.addAttribute("currSupplier", supplier);
	}
	
	/**
	 * 初始化基本信息的审核不通过字段
	 * @param model
	 * @param supplier
	 */
	private void initBasicAudit(Model model, Supplier supplier){
		if(supplier != null && supplier.getStatus() != null && supplier.getStatus() == 2){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());
			supplierAudit.setAuditType("basic_page");
			List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);

			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}

			model.addAttribute("audit", errorField);
		}
	}
	
	/**
	 * 初始化供应商类型的审核不通过字段
	 * @param model
	 * @param supplier
	 */
	private void initSupplierTypeAudit(Model model, Supplier supplier){
		if(supplier != null && supplier.getStatus() != null && supplier.getStatus() == 2){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());;
			//供应商勾选的类型
			StringBuffer typePageField = new StringBuffer();
			supplierAudit.setAuditType("supplierType_page");
			List < SupplierAudit > typeAuditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(typeAuditList != null && !typeAuditList.isEmpty()){
				for(SupplierAudit audit: typeAuditList) {
					typePageField.append(audit.getAuditField() + ",");
				}
			}
			model.addAttribute("typePageField", typePageField);
			
			//生产
			StringBuffer proPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_pro_page");
			List < SupplierAudit > proAuditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(proAuditList != null && !proAuditList.isEmpty()){
				for(SupplierAudit audit: proAuditList) {
					proPageField.append(audit.getAuditField() + ",");
				}
			}
			model.addAttribute("proPageField", proPageField);
			//销售
			StringBuffer sellPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_sell_page");
			List < SupplierAudit > sellAuditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(sellAuditList != null && !sellAuditList.isEmpty()){
				for(SupplierAudit audit: sellAuditList) {
					sellPageField.append(audit.getAuditField() + ",");
				}
			}
			model.addAttribute("sellPageField", sellPageField);
			//工程
			StringBuffer engPageField = new StringBuffer();
			supplierAudit.setAuditType("mat_eng_page");
			List < SupplierAudit > engAuditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(engAuditList != null && !engAuditList.isEmpty()){
				for(SupplierAudit audit: engAuditList) {
					engPageField.append(audit.getAuditField() + ",");
				}
			}
			model.addAttribute("engPageField", engPageField);
			//服务
			StringBuffer servePageField = new StringBuffer();
			supplierAudit.setAuditType("mat_serve_page");
			List < SupplierAudit > serveAuditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			if(serveAuditList != null && !serveAuditList.isEmpty()){
				for(SupplierAudit audit: serveAuditList) {
					servePageField.append(audit.getAuditField() + ",");
				}
			}
			model.addAttribute("servePageField", servePageField);
		}
	}
	
	/**
	 * 初始化申请表附件上传审核不通过字段
	 * @param model
	 * @param supplier
	 */
	private void initUploadAudit(Model model, Supplier supplier){
		if(supplier != null && supplier.getStatus() != null && supplier.getStatus() == 2){
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplier.getId());
			s.setAuditType("download_page");
			List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("audit", errorField);
		}
	}
	
	/**
	 * 初始化资质文件审核不通过字段
	 * @param model
	 * @param supplier
	 */
	private void initAptitudeAudit(Model model, Supplier supplier){
		if(supplier != null && supplier.getStatus() != null && supplier.getStatus() == 2){
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplier.getId());
			//s.setAuditType("aptitude_page");
			String typeIds = supplier.getSupplierTypeIds();
			if(typeIds != null){
				Map<String, String> auditTypeMap = new HashMap<String, String>();
				String[] typeIdAry = typeIds.split(",");
				StringBuffer errorField = new StringBuffer();
				for(String typeId : typeIdAry){
					if(ses.util.Constant.SUPPLIER_PRODUCT.equals(typeId)){
						s.setAuditType(ses.util.Constant.APTITUDE_PRODUCT_PAGE);
					}
					else if(ses.util.Constant.SUPPLIER_SALES.equals(typeId)){
						s.setAuditType(ses.util.Constant.APTITUDE_SALES_PAGE);
					}else{
						s.setAuditType(ses.util.Constant.APTITUDE_PRODUCT_PAGE);
					}
					auditTypeMap.put(typeId, s.getAuditType());
					List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);

					for(SupplierAudit audit: auditLists) {
						errorField.append(audit.getAuditField() + ",");
					}
				}
				model.addAttribute("audit", errorField);
				model.addAttribute("auditTypeMap", auditTypeMap);
			}
		}
	}
	
	private void returnSupplierTypeInfo(Model model, Supplier persistentSup, Supplier supplier){
		if(supplier != null && persistentSup != null){
			//BeanUtilsExt.copyPropertiesIgnoreNull(persistentSup, supplier);
			supplier.setStatus(persistentSup.getStatus());
			// 工程
			SupplierMatEng matEng = supplier.getSupplierMatEng();
			if(matEng != null){
				// 去掉空的注册资质人员信息
				List<SupplierRegPerson> listRegPersons = matEng.getListSupplierRegPersons();
				if(listRegPersons != null && listRegPersons.size() > 0){
					Iterator<SupplierRegPerson> itr = listRegPersons.iterator();
					while(itr.hasNext()){
						SupplierRegPerson regPerson = itr.next();
						if(regPerson.getId() == null){
							itr.remove();
						}
					}
				}
				// 去掉空的供应商资质（认证）证书信息
				List<SupplierCertEng> listCertEngs = matEng.getListSupplierCertEngs();
				if(listCertEngs != null && listCertEngs.size() > 0){
					Iterator<SupplierCertEng> itr = listCertEngs.iterator();
					while(itr.hasNext()){
						SupplierCertEng certEng = itr.next();
						if(certEng.getId() == null){
							itr.remove();
						}
					}
				}
				// 去掉空的供应商资质证书详细信息
				List<SupplierAptitute> listAptitutes = matEng.getListSupplierAptitutes();
				if(listAptitutes != null && listAptitutes.size() > 0){
					Iterator<SupplierAptitute> itr = listAptitutes.iterator();
					while(itr.hasNext()){
						SupplierAptitute aptitute = itr.next();
						if(aptitute.getId() == null){
							itr.remove();
						}
					}
				}
			}
			// 生产
			SupplierMatPro matPro = supplier.getSupplierMatPro();
			if(matPro != null){
				// 去掉空的生产资质证书信息
				List<SupplierCertPro> listCertPros = matPro.getListSupplierCertPros();
				if(listCertPros != null && listCertPros.size() > 0){
					Iterator<SupplierCertPro> itr = listCertPros.iterator();
					while(itr.hasNext()){
						SupplierCertPro certPro = itr.next();
						if(certPro.getId() == null){
							itr.remove();
						}
					}
				}
			}
			// 销售
			SupplierMatSell matSell = supplier.getSupplierMatSell();
			if(matSell != null){
				// 去掉空的销售资质证书信息
				List<SupplierCertSell> listCertSells = matSell.getListSupplierCertSells();
				if(listCertSells != null && listCertSells.size() > 0){
					Iterator<SupplierCertSell> itr = listCertSells.iterator();
					while(itr.hasNext()){
						SupplierCertSell certSell = itr.next();
						if(certSell.getId() == null){
							itr.remove();
						}
					}
				}
			}
			// 服务
			SupplierMatServe matServe = supplier.getSupplierMatSe();
			if(matServe != null){
				// 去掉空的服务资质证书信息
				List<SupplierCertServe> listCertSes = matServe.getListSupplierCertSes();
				if(listCertSes != null && listCertSes.size() > 0){
					Iterator<SupplierCertServe> itr = listCertSes.iterator();
					while(itr.hasNext()){
						SupplierCertServe certSe = itr.next();
						if(certSe.getId() == null){
							itr.remove();
						}
					}
				}
			}
			// 返回当前供应商
			model.addAttribute("currSupplier", supplier);
		}
	}

	/**
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
			String supplierTypeIds = supplier.getSupplierTypeIds();
			if(StringUtils.isNotBlank(supplierTypeIds)) {
				String[] supplierTypeArray = supplierTypeIds.trim().split(",");
				for(String supplierType: supplierTypeArray) {
					if(supplierType.equals("PRODUCT")) {
						supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
					}
					if(supplierType.equals("SALES")) {
						supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
					}
					if(supplierType.equals("PROJECT")) {
						supplierMatEngService.saveOrUpdateSupplierMatEng(supplier);
					}
					if(supplierType.equals("SERVICE")) {
						supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
					}
				}
				supplierTypeRelateService.saveSupplierTypeRelate(supplier);
			}
		}
		StringBuffer idSb = new StringBuffer();
		idSb.append(supplierMatProService.getMatProIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatSellService.getMatSellIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatEngService.getMatEngIdBySupplierId(supplier.getId()) + ",");
		idSb.append(supplierMatSeService.getMatSeIdBySupplierId(supplier.getId()) + ",");
		return idSb.toString();
	}
	
	/**
	 * 保存供应商类型关系
	 * @param supplierTypeIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSupplierTypeRelate", produces = "html/text;charset=UTF-8")
	public String saveSupplierTypeRelate(String supplierId, String supplierTypeIds) {
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		supplier.setSupplierTypeIds(supplierTypeIds);
		supplierTypeRelateService.saveSupplierTypeRelate(supplier);
		return "";
	}

	/**
	 * @Title: perfectProfessional
	 * @author: Wang Zhaohua
	 * @date: 2016-10-31 下午3:11:06
	 * @Description: 完善专业信息
	 * @param: @param request
	 * @param: @param supplier
	 * @param: @throws IOException
	 * @return: String
	 */
	@RequestMapping(value = "perfect_professional",method = RequestMethod.POST)
	public String perfectProfessional(HttpServletRequest request, Model model, Supplier supplier, String old) throws IOException {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		boolean type = true;
		boolean sale = true;
		boolean pro = true;
		boolean server = true;
		boolean project = true;
		try{
			String supplierTypeIds = supplier.getSupplierTypeIds();
			if(StringUtils.isNotBlank(supplierTypeIds)){
				String[] str = supplierTypeIds.trim().split(",");
				if(str != null && str.length > 0){
					for(String s: str) {
				        if(s.equals("PRODUCT")) {
				            pro = validatePro(request, supplier.getSupplierMatPro(), model);
				            if(pro == true) {
				                supplierMatProService.saveOrUpdateSupplierMatPro(supplier);
				            }
				        }
				        if(s.equals("SALES")) {
				            sale = validateSale(request, supplier.getSupplierMatSell(), model);
				            if(sale == true) {
				                supplierMatSellService.saveOrUpdateSupplierMatSell(supplier);
				            }
				        }
				        if(s.equals("PROJECT")) {
				            project = validateEng(request, supplier.getSupplierMatEng(), model);
				            if(project == true) {
				                supplierMatEngService.saveOrUpdateSupplierMatEng(supplier);
				            }
				        }
				        if(s.equals("SERVICE")) {
				            server = validateServer(request, supplier.getSupplierMatSe(), model);
				            if(server == true) {
				                supplierMatSeService.saveOrUpdateSupplierMatSe(supplier);
				            }
				        }
				    }
				}
				supplierTypeRelateService.saveSupplierTypeRelate(supplier);
				if(old!=null&&old.equals("old")){
				    supplierTypeRelateService.delete(supplier.getId(), "SALES");
				}
			}else{
				type = false;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		if(type == true && pro == true && server == true && project == true && sale == true) {
			model.addAttribute("suppId", supplier.getId());
			return "redirect:/supplier/items.html";
		} else {
			model.addAttribute("type", type);
			model.addAttribute("pro", pro);
			model.addAttribute("sale", sale);
			model.addAttribute("project", project);
			model.addAttribute("server", server);
			initSupplierTypeConstants(model, supplier);
			initSupplierTypeAudit(model, checkSupplier);
			returnSupplierTypeInfo(model, checkSupplier, supplier);
			return "ses/sms/supplier_register/supplier_type";
		}
	}
	
	/**
	 * 完善品目信息
	 * @param request
	 * @param model
	 * @param supplierId
	 * @param supplierTypeIds
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "perfect_items",method = RequestMethod.POST)
	public String perfectItems(HttpServletRequest request, Model model, String supplierId, String supplierTypeIds) {
		Supplier checkSupplier = checkSupplier(supplierId);
		if(checkSupplier == null){
			return null;
		}
		boolean bool = true;
		if(supplierTypeIds != null && supplierTypeIds.trim().length()!=0){
			String[] types = supplierTypeIds.split(",");
			for(String s:types){
//				List<SupplierItem> items = supplierItemService.queryBySupplierAndType(supplierId, s);
				List<SupplierItem> items = supplierItemService.getItemList(supplierId, s, (byte)0, null);
				if("PRODUCT".equals(s) && (items == null || items.size() == 0)){
					model.addAttribute("productError", "productError");
					bool = false;
					break;
				}
				if("PROJECT".equals(s) && (items == null || items.size() == 0)){
					model.addAttribute("projectError", "projectError");
					bool = false;
					break;
				}
				if("SALES".equals(s) && (items == null || items.size() == 0)){
					model.addAttribute("sellError", "sellError");
					bool = false;
					break;
				}
				if("SERVICE".equals(s) && (items == null || items.size() == 0)){
					model.addAttribute("serverError", "serverError");
					bool = false;
					break;
				}
				/*if(items!=null&&items.size()<1&&s.equals("PRODUCT")){
					model.addAttribute("productError", "productError");
					bool = false;
					break;
				}
				if(items!=null&&items.size()<1&&s.equals("PROJECT")){
					model.addAttribute("projectError", "projectError");
					bool = false;
					break;
				}
				if(items!=null&&items.size()<1&&s.equals("SALES")){
					model.addAttribute("sellError", "sellError");
					bool = false;
					break;
				}
				if(items!=null&&items.size()<1&&s.equals("SERVICE")){
					model.addAttribute("serverError", "serverError");
					bool = false;
					break;
				}*/
			}
		}
		if(!bool){
			Supplier supplier = supplierService.get(supplierId, 3);
			model.addAttribute("currSupplier", supplier);
			return "ses/sms/supplier_register/items";
		}else{
			model.addAttribute("suppId", supplierId);
			return "redirect:/supplier/aptitude.html";
		}
	}
	
	/**
	 * 完善资质文件信息
	 * @param request
	 * @param model
	 * @param supplier
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "perfect_aptitude",method = RequestMethod.POST)
	public String perfectAptitude(HttpServletRequest request, Model model, Supplier supplier) {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		// 查询品目资质文件相关
		Map<String, Object> aptitudeMap = supplierItemService.getAptitude(supplier.getId(), supplier.getSupplierTypeIds());
		
		List<QualificationBean> list = new ArrayList<QualificationBean>();
		Object proQua = aptitudeMap.get("proQua");
		Object saleQua = aptitudeMap.get("saleQua");
		Object serviceQua = aptitudeMap.get("serviceQua");
		if(proQua != null){
			list.addAll((List<QualificationBean>)proQua);
		}
		if(saleQua != null){
			list.addAll((List<QualificationBean>)saleQua);
		}
		if(serviceQua != null){
			list.addAll((List<QualificationBean>)serviceQua);
		}
		
		int count = 0;
		List<Qualification> qlist = new ArrayList<Qualification>();
		for(QualificationBean qb : list){
			List<Qualification> qbs = qb.getList();
			for(Qualification q : qbs){
				qlist.add(q);
				String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
				List<UploadFile> files = uploadService.getFilesOther(q.getFlag(), id, "1");
				if(files!=null&&files.size()>0){
					count++;
				}
			}
		}
		if(count != qlist.size()){// 资质文件没有上传完毕
			model.addAttribute("aptitude_error", "notComplete");
			initAptitudeConstants(model, supplier);
			initAptitudeAudit(model, checkSupplier);
			return "ses/sms/supplier_register/aptitude";
		}else{
			model.addAttribute("suppId", supplier.getId());
			return "redirect:/supplier/contract.html";
		}
	}
	
	/**
	 * 完善合同信息
	 * @param request
	 * @param model
	 * @param supplierId
	 * @param supplierTypeIds
	 * @return
	 */
	@RequestMapping(value = "perfect_contract",method = RequestMethod.POST)
	public String perfectContract(HttpServletRequest request, Model model, String supplierId, String supplierTypeIds) {
		Supplier checkSupplier = checkSupplier(supplierId);
		if(checkSupplier == null){
			return null;
		}
		// 判断品目合同有没有全部上传
		String[] typeIds = supplierTypeIds.split(",");
		// 总数量
		List < SupplierItem > itemsList = new ArrayList < SupplierItem > ();
		for(String type: typeIds) {
			if(!type.equals("PROJECT")) {
				//itemsList.addAll(supplierItemService.findCategoryList(supplierId, type, null));
				itemsList.addAll(supplierItemService.getItemList(supplierId, type, (byte)0, null));
			}
		}
		// 实际上传数量
		List < UploadFile > filesList;
		boolean isOk = true;
		String errContractFiles = "";
		for(SupplierItem item: itemsList) {
			String supplierType = item.getSupplierTypeRelateId();
			switch (supplierType) {
			case "PRODUCT":
				errContractFiles = "还有物资生产合同附件未上传!";
				break;
			case "SALES":
				errContractFiles = "还有物资销售合同附件未上传!";
				break;
			case "SERVICE":
				errContractFiles = "还有服务合同附件未上传!";
				break;
			default:
				errContractFiles = "还有合同附件未上传!";
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_ONE_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_TWO_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_THREE_YEAR"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CTAEGORY_ONE_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CTAEGORY_TWO_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
			filesList = uploadService.getFilesOther(item.getId(), DictionaryDataUtil.getId("CATEGORY_THREE_BIL"), Constant.SUPPLIER_SYS_KEY.toString());
			if(filesList.size() == 0) {
				isOk = false;
				break;
			}
		}
		if(!isOk) {
			//model.addAttribute("err_contract_files", "还有附件未上传!");
			model.addAttribute("err_contract_files", errContractFiles);
			model.addAttribute("supplierTypeIds", supplierTypeIds);
			model.addAttribute("supplierId", supplierId);
			return "ses/sms/supplier_register/contract";
		}else{
			model.addAttribute("suppId", supplierId);
			return "redirect:/supplier/procurement_dep.html";
		}
	}


	/**
	 * @Title: perfectDep
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:28:53
	 * @Description: 完善审核机构信息
	 * @param: @param request
	 * @param: @param supplier
	 * @return: String
	 */
	@RequestMapping(value = "perfect_dep")
	public String perfectDep(HttpServletRequest request, Supplier supplier, Model model) {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		supplierService.updateSupplierProcurementDep(supplier);
		model.addAttribute("suppId", supplier.getId());
		return "redirect:/supplier/template_download.html";
	}

	/**
	 * @Title: perfectDownload
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:42:17
	 * @Description: 模板下载
	 * @param: @param request
	 * @param: @param supplier
	 * @return: String
	 */
	@RequestMapping(value = "perfect_download")
	public String perfectDownload(HttpServletRequest request, Supplier supplier, Model model) {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		model.addAttribute("suppId", supplier.getId());
		return "redirect:/supplier/template_upload.html";
	}

	/**
	 * @Title: perfectUpload
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午4:43:20
	 * @Description: 附件上传
	 * @param: @param request
	 * @param: @param supplier
	 * @return: String
	 */
	@RequestMapping(value = "perfect_upload")
	public String perfectUpload(HttpServletRequest request, Supplier supplier, Model model) {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
	    boolean bool = validateUpload(model, supplier.getId());
		if(!bool) {
			initUploadConstants(model, supplier);
			initUploadAudit(model, checkSupplier);
			return "ses/sms/supplier_register/template_upload";
		}
		supplierService.commit(supplier);
		//刪除上次的审核记录
		/*supplierAuditService.deleteBySupplierId(supplier.getId());*/
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(supplier.getId());
		supplierAuditService.updateIsDeleteBySupplierId(supplierAudit);
		//清空审核人
		supplier.setAuditor("");
		supplierAuditService.updateStatus(supplier);
		
		request.getSession().removeAttribute("currSupplier");
		request.getSession().removeAttribute("sysKey");
		request.getSession().removeAttribute("supplierDictionaryData");
		request.getSession().removeAttribute("listOrgnizations1");
		request.getSession().removeAttribute("listOrgnizations2");
		return "redirect:../index/selectIndexNews.html";
	}

	@ResponseBody
	@RequestMapping(value="/isCommit",produces = "text/html;charset=UTF-8")
	public String isCommit(Model model, Supplier supplier) {
		Supplier checkSupplier = checkSupplier(supplier.getId());
		if(checkSupplier == null){
			return null;
		}
		boolean bool = validateUpload(model, supplier.getId());
		Supplier supp = supplierService.selectOne(supplier.getId());
		// 删除审核不通过的品目
		supplierItemService.deleteItemsBySupplierId(supplier.getId(), (byte)1);
        //校验是否在规定时间未提交审核,如时间>0说明不符合规定则注销信息
//        try {
//            int validateDay = supplierService.logoutSupplierByDay(supp);
//            if(0==validateDay) {//通过审核时间校验
                PurchaseDep dep = purchaseOrgnizationService.selectPurchaseById(supp.getProcurementDepId());
                String json = JSON.toJSONString(dep);
                if(bool != true) {
                    // 返回
                    return "1";
                } else {
                    return json;
                }
//            }else if(0 < validateDay) {//未按规定时间提交审核,注销信息
//                return "supplier_logout," + validateDay;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return "0";
//        }
//        return "0";
	}

	/**
	 * @Title: page_jump
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午5:43:49
	 * @Description: page_jump
	 * @param: @param request
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
	
	@RequestMapping(value = "check_mobile")
	public void checkMobile(HttpServletResponse response, String mobile) {
		boolean flag = supplierService.checkMobile(mobile);
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
			model.addAttribute("err_msg_loginName", "登录名由6-20位字母数字和下划线组成！");
			count++;
		}
		if(StringUtils.isNotBlank(supplier.getLoginName())){
			boolean flag = supplierService.checkLoginName(supplier.getLoginName());
			if(!flag){
				model.addAttribute("err_msg_loginName", "用户名已被使用，请更换重试！");
				count++;
			}
		}
		if(supplier.getPassword() == null){
			model.addAttribute("err_msg_password", "密码不能为空");
			count++;
		}else{
			String pwd=supplier.getPassword();
			supplier.setPassword(pwd.replaceAll("\\s",""));
		}
		if(supplier.getPassword().length() < 6 || supplier.getPassword().length() > 20) {
			model.addAttribute("err_msg_password", "密码不能出现空格，密码长度为6-20位！");
			count++;
		}
		if(supplier.getConfirmPassword() == null || !supplier.getPassword().equals(supplier.getConfirmPassword())) {
			model.addAttribute("err_msg_ConfirmPassword", "密码和确认密码不一致！");
			count++;
		}
		if(supplier.getMobile() == null || !supplier.getMobile().matches("^1[0-9]{10}$")) {
			model.addAttribute("err_msg_mobile", "手机格式不正确！");
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
			// 手机号校验：供应商库+专家库（除去临时供应商和临时专家）
			boolean bool = supplierService.checkMobile(supplier.getMobile());
			if(!bool) {
				count++;
				model.addAttribute("err_msg_mobile", "手机号已被使用，请更换重试！");
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
			model.addAttribute("err_msg_supplierName", "不能为空或名称过长!");
			count++;
		}
		/*Supplier before = supplierService.get(supplier.getId());
		//校验供应商名称是否存在(去除临时供应商)
		//List<Supplier> suppliers = supplierService.selByName(supplier.getSupplierName());
		List<Supplier> suppliers = supplierService.selByNameWithoutProvisional(supplier.getSupplierName());
        if(null != suppliers && !suppliers.isEmpty()){
            if(before != null && !before.getSupplierName().equals(suppliers.get(0).getSupplierName())){
            	model.addAttribute("err_msg_supplierName", "供应商名称已存在，请重新填写！");
    			count++;
            }
        }*/
		// 供应商名称校验：供应商库（除去临时供应商）
		String sname = supplier.getSupplierName();
		//去除   全部空白符号
		sname = sname.replaceAll("\\s*", "");
		if(StringUtils.isNotBlank(sname)){
			boolean boolSupplierName = supplierService.checkSupplierName(supplier.getId(), sname);
			if(!boolSupplierName){
				model.addAttribute("err_msg_supplierName", "供应商名称已存在，请重新填写！");
				count++;
			}
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
		if(supplier.getBranchName() == null && supplier.getBusinessStartDate() == null){
			model.addAttribute("err_sDate", "经营期限不能为空!");
			count++;
		}
		if(supplier.getAddress() == null) {
			model.addAttribute("err_msg_address", "不能为空!");
			count++;
		}
		if(supplier.getBankName() == null) {
			model.addAttribute("err_msg_bankName", "不能为空 !");
			count++;
		}
		if(StringUtils.isNotBlank(supplier.getBankName()) && !supplier.getBankName().trim().matches("^.{1,80}$")) {
			model.addAttribute("err_msg_bankName", "格式不正确 !");
			count++;
		}
		if(supplier.getBankAccount() == null) {
			model.addAttribute("err_msg_bankAccount", "不能为空 !");
			count++;
		}
		if(supplier.getPostCode() == null || !ValidateUtils.Zipcode(supplier.getPostCode())) {
			model.addAttribute("err_msg_postCode", "不能为空或格式不正确 !");
			count++;
		}
		if(supplier.getDetailAddress() == null || supplier.getDetailAddress().length() > 80) {
			model.addAttribute("err_detailAddress", "详细地址不能为空或过长!");
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
		// 身份证号码校验
		if(StringUtils.isNotBlank(supplier.getLegalIdCard()) && !IDCardUtil.isIDCard(supplier.getLegalIdCard())){
			model.addAttribute("err_legalCard", "身份证号码错误！请按实际身份证号码填写。");
			count++;
		}
		// 身份证号校验：供应商库+专家库（除去临时供应商和临时专家）
        /*if(StringUtils.isNotBlank(supplier.getLegalIdCard())){
        	boolean boolIdCard = supplierService.checkIdCard(supplier.getId(), supplier.getLegalIdCard());
    		if(!boolIdCard){
    			model.addAttribute("err_legalCard", "身份证号码已被占用!");
                count++;
    		}
        }*/
		if(supplier.getConcatCity() == null) {
			model.addAttribute("err_city", "地址不能为空!");
			count++;
		}
		if(supplier.getArmyBusinessName() == null) {
			model.addAttribute("err_armName", "不能为空!");
			count++;
		}
		if(supplier.getArmyBusinessFax() == null || "".equals(supplier.getArmyBusinessFax())) {
			model.addAttribute("err_armFax", "传真不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessMobile() == null || "".equals(supplier.getArmyBuinessMobile())) {
			model.addAttribute("err_armMobile", "固定电话不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessTelephone() == null) {
			model.addAttribute("err_armTelephone", "不能为空!");
			count++;
		}
		if(supplier.getArmyBuinessTelephone() != null && supplier.getArmyBuinessTelephone().length()!=11 ) {
			model.addAttribute("err_armTelephone", "格式不正确!");
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
		if(supplier.getLegalMobile() == null || "".equals(supplier.getLegalMobile())) {
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

		if(supplier.getContactFax() == null || "".equals(supplier.getContactFax())) {
			model.addAttribute("err_fax", "传真不能为空 !");
			count++;
		}

		if(supplier.getContactMobile() == null || "".equals(supplier.getContactMobile())) {
			model.addAttribute("err_catMobile", "固定电话不能为空 !");
			count++;
		}
		//		if(supplier.getContactTelephone()==null||!supplier.getContactTelephone().matches("^1[0-9]{10}$")||supplier.getContactTelephone().length()>12){
		//			model.addAttribute("err_catTelphone", "格式不正确 !");
		//			count++;
		//		}
		if(supplier.getContactEmail() == null || "".equals(supplier.getContactEmail())) {// || !supplier.getContactEmail().matches("^([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$")
			model.addAttribute("err_catEmail", "邮箱不能为空 !");
			count++;
		}
		/*	if(supplier.getContactAddress()==null||supplier.getContactAddress().length()>35){
  			model.addAttribute("err_conAddress", "不能为空或是字符过长!");
  			count++;
  		}*/

		// 统一社会信用代码校验
		if(supplier.getCreditCode() == null || supplier.getCreditCode().trim().length() != 18) {
			model.addAttribute("err_creditCide", "不能为空或是格式不正确 !");
			count++;
		}
		
		String creditCode = supplier.getCreditCode();
		if(creditCode != null){
			if(creditCode.matches("^([a-zA-Z0-9]){18}$")){// 18位数字+字母
				if(creditCode.matches("^([a-zA-Z])+$")){// 排除全字母
					model.addAttribute("err_creditCide", "信用代码18位，请按照实际社会信用代码填写 !");
					count++;
				}
			}else{// 非18位数字+字母
				model.addAttribute("err_creditCide", "信用代码18位，请按照实际社会信用代码填写 !");
				count++;
			}
		}
		
        //根据供应商统一社会信用代码判断是否注销或审核不通过且180天内再次注册
        try{
		    if(StringUtils.isNotBlank(supplier.getCreditCode())){
                //注销
                DeleteLog deleteLog = deleteLogService.queryByTypeId(null, supplier.getCreditCode());
                if(null != deleteLog && null != deleteLog.getCreateAt()){
                    int betweenDays = supplierService.daysBetween(deleteLog.getCreateAt());
                    if(betweenDays > 180){
                        model.addAttribute("err_creditCide", "统一社会信用代码在180天内禁止注册!");
                        count++;
                    }
                }
                //审核不通过
                SupplierAuditNot supplierAuditNot = supplierAuditNotService.selectByCreditCode(supplier.getCreditCode());
                if(null != supplierAuditNot && null != supplierAuditNot.getCreatedAt()){
                    int betweenDays = supplierService.daysBetween(supplierAuditNot.getCreatedAt());
                    if(betweenDays > 180){
                        model.addAttribute("err_creditCide", "统一社会信用代码在180天内禁止注册!");
                        count++;
                    }
                }
            }
        }catch (Exception e){
		    e.printStackTrace();
        }
        
        /*List < Supplier > tempList = supplierService.validateCreditCode(supplier.getCreditCode());
        if(tempList != null && tempList.size() > 0) {
            for(Supplier supp: tempList) {
                if(!supplier.getId().equals(supp.getId())) {
                    model.addAttribute("err_creditCide", "统一社会信用代码已被占用!");
                    count++;
                    break;
                }
            }
        }*/
        // 统一社会信用代码校验：供应商库（除去临时供应商）
        if(StringUtils.isNotBlank(supplier.getCreditCode())){
        	boolean boolCreditCode = supplierService.checkCreditCode(supplier.getId(), supplier.getCreditCode());
    		if(!boolCreditCode){
    			model.addAttribute("err_creditCide", "统一社会信用代码已被占用!");
                count++;
    		}
        }
		if(supplier.getRegistAuthority() == null || supplier.getRegistAuthority().length() > 20) {
			model.addAttribute("err_reAuthoy", "不能为空或是编码过长!");
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
		//		if(supplier.getBusinessAddress()==null){
		//			model.addAttribute("err_bAddress", "经营地址不能为空!");
		//			count++;
		//		}
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
		//地址信息-房产证明或租赁协议
        List<SupplierAddress> addressList = supplier.getAddressList();
		if(null != addressList && !addressList.isEmpty()){
		    for(int i=0;i<addressList.size();i++){
		    	if(StringUtils.isNotBlank(addressList.get(i).getCode()) && !ValidateUtils.Zipcode(addressList.get(i).getCode())){
		    		count++;
                    model.addAttribute("err_address_token", "邮政编码格式不正确");
		    	}
		        if(StringUtils.isEmpty(addressList.get(i).getCode())){
                    count++;
                    model.addAttribute("err_address_token", "邮政编码不能为空");
                }
                if(StringUtils.isEmpty(addressList.get(i).getAddress())){
                    count++;
                    model.addAttribute("err_address_token", "不能为空");
                }
                if(StringUtils.isEmpty(addressList.get(i).getDetailAddress())){
                    count++;
                    model.addAttribute("err_address_token", "不能为空");
                }
                List < UploadFile > houseList = uploadService.getFilesOther(addressList.get(i).getId(), supplierDictionary.getSupplierHousePoperty(), Constant.SUPPLIER_SYS_KEY.toString());
                if(houseList != null && houseList.size() <= 0) {
                    count++;
                    model.addAttribute("err_address_token", "请上传文件!");
                    model.addAttribute("err_house_token", i);
                    break;
                }
            }
        }
		// 股东信息
		List < SupplierStockholder > stockList = supplier.getListSupplierStockholders();
		if(stockList == null || stockList.isEmpty()) {
			count++;
			model.addAttribute("stock", "请添加股东信息!");
		}
		if(stockList != null && !stockList.isEmpty()) {
			int identityCount = 0;
			Set<String> identitySet = new HashSet<String>();
			//float proportionTotal = 0.00f;// 出资比例之和
			double proportionTotal = 0.00d;// 出资比例之和
			int stockholderCount = 0;// 股东数量
			String errorIdentity = "";// 错误信用代码或身份证号码
			for(SupplierStockholder stocksHolder: stockList) {
				if(stocksHolder.getIdentity() != null){
					identitySet.add(stocksHolder.getIdentity());
					identityCount++;
				}
				if(stocksHolder.getName() == null || stocksHolder.getName() == "") {
					count++;
					model.addAttribute("stock", "出资人名称或姓名不能为空！");
				}
				/*if(stocksHolder.getIdentity() == null || stocksHolder.getIdentity() == "" || stocksHolder.getIdentity().length() != 18) {
					count++;
					model.addAttribute("stock", "统一社会信用代码或身份证号码不能为空或者格式不正确！");
				}*/
				// 统一社会信用代码或身份证号码校验
				String identity = stocksHolder.getIdentity();
				if(StringUtils.isBlank(identity)) {
					count++;
					model.addAttribute("stock", "统一社会信用代码或身份证号码不能为空！");
				}
				if("1".equals(stocksHolder.getNature()) && "1".equals(stocksHolder.getIdentityType()+"")){
					// 统一社会信用代码校验
					if(StringUtils.isNotBlank(identity)){
						if(identity.matches("^([a-zA-Z0-9]){18}$")){// 18位数字+字母
							if(identity.matches("^([a-zA-Z])+$")){// 排除全字母
								errorIdentity += identity + "、";
								errorIdentity = errorIdentity.substring(0, errorIdentity.lastIndexOf("、"));
								model.addAttribute("stock", "信用代码18位，请按照实际社会信用代码填写！错误代码：【"+errorIdentity+"】");
								count++;
							}
						}else{// 非18位数字+字母
							errorIdentity += identity + "、";
							errorIdentity = errorIdentity.substring(0, errorIdentity.lastIndexOf("、"));
							model.addAttribute("stock", "信用代码18位，请按照实际社会信用代码填写！错误代码：【"+errorIdentity+"】");
							count++;
						}
						/*boolean boolCreditCode = supplierService.checkCreditCode(supplier.getId(), identity);
						if(!boolCreditCode){
							model.addAttribute("stock", "统一社会信用代码被占用！");
							count++;
						}*/
					}
				}
				if("2".equals(stocksHolder.getNature()) && "1".equals(stocksHolder.getIdentityType()+"")){
					// 身份证号码校验
					if(StringUtils.isNotBlank(identity)){
						if(!IDCardUtil.isIDCard(identity)){
							errorIdentity += identity + "、";
							errorIdentity = errorIdentity.substring(0, errorIdentity.lastIndexOf("、"));
							model.addAttribute("stock", "身份证号码错误！请按实际身份证号码填写。错误号码：【"+errorIdentity+"】");
							count++;
						}
						/*boolean boolIdCard = supplierService.checkIdCard(supplier.getId(), identity);
						if(!boolIdCard){
							model.addAttribute("stock", "身份证号码被占用！");
							count++;
						}*/
					}
				}
				if(stocksHolder.getShares() == null || stocksHolder.getShares() == "") {
					count++;
					model.addAttribute("stock", "出资金额或股份不能为空！");
				}
				if(stocksHolder.getProportion() == null || stocksHolder.getProportion() == "") {
					count++;
					model.addAttribute("stock", "比例不能为空！");
				}else{
					String regex = "^(([1-9]\\d{0,1}|0|100)(\\.\\d{1,2})?)?$";
					if(!stocksHolder.getProportion().matches(regex)){
						count++;
						model.addAttribute("stock", "百分比格式不对，正确格式为0-100的数字，最多两位小数！");
					}else{
						//proportionTotal += Float.parseFloat(stocksHolder.getProportion());// 这样丢失精度
						proportionTotal = Arith.add(proportionTotal, Double.parseDouble(stocksHolder.getProportion()));
						stockholderCount++;
					}
				}
			}
			// 如果数量不超过10个，那占比必须100%，如果数量超过10个，那占比必须高于50%
			if(proportionTotal !=0 && stockholderCount != 0){
				if(stockholderCount >= 10 && proportionTotal < 50){
					count++;
					model.addAttribute("stock", "出资人10个或以上，出资比例之和要高于50%！");
				}
				if(stockholderCount < 10 && proportionTotal != 100){
					count++;
					model.addAttribute("stock", "出资人不超过10个，出资比例之和必须为100%！");
				}
			}
			if(identitySet.size() != identityCount){
				count++;
				model.addAttribute("stock", "统一社会信用代码或身份证号码重复！");
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
			model.addAttribute("status", "0");
			return false;
		}else{
			// 判断财务信息是否满足条件
		    BigDecimal score = supplierService.getScoreByFinances(supplier.getListSupplierFinances());
		    if (score.compareTo(BigDecimal.valueOf(100)) == -1) {
		    	model.addAttribute("notPass", "notPass");
		    	return false;
		    }
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
		double scaleTech = Double.valueOf(supplierMatPro.getScaleTech());
		if(scaleTech<0 || scaleTech>100) {
			model.addAttribute("stech", "百分比格式不对，正确格式为0-100的数字");
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
		double scaleHeightTech = Double.valueOf(supplierMatPro.getScaleHeightTech());
		if(scaleHeightTech<0 || scaleHeightTech>100) {
			model.addAttribute("height", "百分比格式不对，正确格式为0-100的数字");
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
//			Set<String> codeSet = new HashSet<>();
//			int codeCount = 0;
		    for (SupplierCertPro cert : list) {
	            List < UploadFile > filelist = uploadService.getFilesOther(cert.getId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierProCert(), Constant.SUPPLIER_SYS_KEY.toString());
	            if(filelist != null && filelist.size() <= 0) {
	                model.addAttribute("cert_pro", "还有证书图片未上传!");
	                return false;
	            }
//	            if(StringUtils.isNotBlank(cert.getCode())){
//	            	codeSet.add(cert.getCode());
//	            	codeCount++;
//	            }
            }
//		    if(codeSet.size() != codeCount){
//		    	model.addAttribute("cert_pro", "证书编号重复!");
//		    	return false;
//		    }
		}
		return true;
	}

	//销售信息校验
	public boolean validateSale(HttpServletRequest request, SupplierMatSell supplierMatSell, Model model) {
		boolean bool = true;
		/*if(supplierMatSell.getOrgName() == null || supplierMatSell.getOrgName().length() > 12) {
			model.addAttribute("sale_org", "不能为空或者字符串过长");
			bool = false;
		}
		if(supplierMatSell.getTotalPerson() == null) {
			model.addAttribute("sale_person", "不能为空");
			bool = false;
		}
		if(supplierMatSell.getTotalPerson() != null && !supplierMatSell.getTotalPerson().toString().matches("^[0-9]*$")) {
			model.addAttribute("sale_person", "人员必须是整数");
			bool = false;
		}
		if(supplierMatSell.getTotalMange() == null) {
			model.addAttribute("sale_mange", "不能为空");
			bool = false;
		}
		if(supplierMatSell.getTotalMange() != null && !supplierMatSell.getTotalMange().toString().matches("^[0-9]*$")) {
			model.addAttribute("sale_mange", "人员必须是整数");
			bool = false;
		}
		if(supplierMatSell.getTotalTech() == null) {
			model.addAttribute("sale_tech", "不能为空");
			bool = false;
		}
		if(supplierMatSell.getTotalTech() != null && !supplierMatSell.getTotalTech().toString().matches("^[0-9]*$")) {
			model.addAttribute("sale_tech", "格式不正确");
			bool = false;
		}
		if(supplierMatSell.getTotalWorker() == null) {
			model.addAttribute("sale_work", "不能为空");
			bool = false;
		}
		if(supplierMatSell.getTotalWorker() != null && !supplierMatSell.getTotalWorker().toString().matches("^[0-9]*$")) {
			model.addAttribute("sale_work", "格式不正确");
			bool = false;
		}
		List<SupplierCertSell> list = supplierMatSell.getListSupplierCertSells();
		if(list==null||list.size()<1){
			model.addAttribute("sale_cert", "资质证书不能为空");
			bool=false;
		}*/
		/*List<SupplierCertSell> list = supplierMatSell.getListSupplierCertSells();
		if(list != null && list.size() > 0){
			Set<String> codeSet = new HashSet<>();
			int codeCount = 0;
			for (SupplierCertSell sell : list) {
	            if(StringUtils.isNotBlank(sell.getCode())){
	            	codeSet.add(sell.getCode());
	            	codeCount++;
	            }
            }
		    if(codeSet.size() != codeCount){
		    	model.addAttribute("sale_cert", "证书编号重复!");
                bool = false;
		    }
		}*/
		return bool;
	}
	//工程信息校验
	public boolean validateEng(HttpServletRequest request, SupplierMatEng supplierMatEng, Model model) {
		boolean bool = true;
		if(supplierMatEng.getIsHavingConAchi() != null && supplierMatEng.getIsHavingConAchi().equals("1")) {
		    List < UploadFile > tlist = uploadService.getFilesOther(supplierMatEng.getSupplierId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierConAch(), Constant.SUPPLIER_SYS_KEY.toString());
            if(tlist != null && tlist.size() <= 0) {
                bool = false;
                model.addAttribute("err_conAch", "请上传文件!");
            }
        	if(supplierMatEng.getConfidentialAchievement()==null){
   			 model.addAttribute("secret", "请填写国家或军队保密工程业绩!");
   			 bool = false;
   		   }
        }
		if(supplierMatEng.getBusinessScope() == null){
			 model.addAttribute("province", "至少选择一个省市!");
			 bool = false;
		}
		String businessScope = supplierMatEng.getBusinessScope();
		if (businessScope != null) {
		    String[] scope = businessScope.split(",");
		    for (String areaId : scope) {
		        Area area = areaService.listById(areaId);
		        if (area != null) {
		            List < UploadFile > list = uploadService.getFilesOther(supplierMatEng.getSupplierId() + "_" + area.getId(), dictionaryDataServiceI.getSupplierDictionary().getSupplierProContract(), Constant.SUPPLIER_SYS_KEY.toString());
		            if(list != null && list.size() <= 0) {
		                bool = false;
		                area.setErrInfo("请上传文件！");
		            }
		            supplierMatEng.getBusinessScopeAreas().add(area);
		        }
            }
		}
		List<SupplierCertEng> listSupplierCertEngs = supplierMatEng.getListSupplierCertEngs();
		if (listSupplierCertEngs != null && listSupplierCertEngs.size() > 0) {
		    for (SupplierCertEng supplierCertEng : listSupplierCertEngs) {
                if (supplierCertEng.getId() != null && supplierCertEng.getCertCode() != null) {
                    boolean flag = supplierCertEngService.validateCertCode(supplierCertEng);
                    if (!flag) {
                        bool = false;
                        model.addAttribute("eng_cert", "证书编号【"+supplierCertEng.getCertCode()+"】已被占用！");
                        break;
                    }
                }
            }
		}
		
		// 校验详细信息表里的编号和名称是否和证书信息表的匹配
		List<SupplierAptitute> listSupplierAptitutes = supplierMatEng.getListSupplierAptitutes();
		if (listSupplierAptitutes != null && listSupplierAptitutes.size() > 0) {
		    outer: for (SupplierAptitute supplierAptitute : listSupplierAptitutes) {
				if(supplierAptitute != null && supplierAptitute.getId() != null){
					boolean flag = false;
				    String certCode = "";
				    String parentCertCode = "";
				    if (supplierAptitute.getCertName() != null && supplierAptitute.getCertCode() != null) {
				        parentCertCode = "【";
				        inner: for (SupplierCertEng supplierCertEng : listSupplierCertEngs) {
				            if (supplierAptitute.getCertCode().equals(supplierCertEng.getCertCode())) {
				                flag = true;
				                break inner;
				            }else{
				                parentCertCode += supplierCertEng.getCertCode() + ",";
				            }
				        }
				        if(!StringUtils.isEmpty(parentCertCode)) parentCertCode = parentCertCode.substring(0,parentCertCode.length()-1)+"】";
				        certCode = "【"+supplierAptitute.getCertCode()+"】";
				    }
				    if (!flag) {
				        bool = false;
				        model.addAttribute("eng_aptitutes", "证书编号"+certCode+"与资质（认证）证书"+parentCertCode+"不匹配！");
				        break outer;
				    }
				}
		    }
		}
		
		Integer count=0;
    	for(SupplierAptitute sa:listSupplierAptitutes){
    		String id = DictionaryDataUtil.getId("SUPPLIER_ENG_CERT");
			List<UploadFile> files = uploadService.getFilesOther(sa.getId(), id, "1");
			if(files!=null&&files.size()>0){
				count++;
			}
    	}
    	Integer size=listSupplierAptitutes.size();
    	if(!count.equals(size)){
    		bool=false;
    		model.addAttribute("eng_aptitutes", "请上传文件！");
    	}
    	
		List<SupplierCertEng> engList = supplierMatEng.getListSupplierCertEngs();
		if(engList != null && engList.size() > 0){
			Set<String> codeSet = new HashSet<>();
			int codeCount = 0;
			for (SupplierCertEng eng : engList) {
				if(StringUtils.isNotBlank(eng.getCertCode())){
					codeSet.add(eng.getCertCode());
					codeCount++;
				}
		    }
		    if(codeSet.size() != codeCount){
				model.addAttribute("eng_cert", "证书编号重复！");
				bool = false;
		    }
		}
		
		List<SupplierAptitute> aptitudeList = supplierMatEng.getListSupplierAptitutes();
		if(aptitudeList != null && aptitudeList.size() > 0){
			/*Set<String> codeSet = new HashSet<>();
			int codeCount = 0;
			for (SupplierAptitute aptitude : aptitudeList) {
				if(StringUtils.isNotBlank(aptitude.getCertCode())){
					codeSet.add(aptitude.getCertCode());
					codeCount++;
				}
			}
			if(codeSet.size() != codeCount){
				model.addAttribute("eng_aptitutes", "证书编号重复！");
				bool = false;
			}*/
			Set<String> certTypeSet = new HashSet<>();
			int certTypeCount = 0;
			StringBuffer levelSb = new StringBuffer();
			boolean levelBool = true;
			for (SupplierAptitute aptitude : aptitudeList) {
				if(StringUtils.isNotBlank(aptitude.getCertType())){
					certTypeSet.add(aptitude.getCertType());
					certTypeCount++;
					// 校验资质等级
					Qualification qualification = qualificationService.getQualification(aptitude.getCertType());// 根据id查询资质类型
					if(qualification != null && StringUtils.isNotBlank(aptitude.getAptituteLevel())){
						int countByQuaIdAndLevel = qualificationLevelService.countByQuaIdAndLevel(aptitude.getCertType(), aptitude.getAptituteLevel());
						if(countByQuaIdAndLevel == 0){
							levelSb.append(qualification.getName() + "-" + aptitude.getAptituteLevel() + ",");
							levelBool = false;
						}
					}
				}
			}
			if(certTypeSet.size() != certTypeCount){
				model.addAttribute("eng_aptitutes", "资质类型重复！");
				bool = false;
			}
			if(!levelBool){
				String levelStr = levelSb.substring(0, levelSb.lastIndexOf(","));
				model.addAttribute("eng_aptitutes", "资质等级"+"【"+levelStr+"】"+"不存在！请选择");
				bool = false;
			}
		}
    	
		return bool;
	}
	
	//服务信息校验
	public boolean validateServer(HttpServletRequest request, SupplierMatServe supplierMatServe, Model model) {
		boolean bool = true;
		/*if(supplierMatServe.getOrgName() == null || supplierMatServe.getOrgName().length() > 12) {
			model.addAttribute("fw_org", "不能为空");
			bool = false;
		}
		if(supplierMatServe.getTotalPerson() == null) {
			model.addAttribute("fw_person", "不能为空");
			bool = false;
		}
		if(supplierMatServe.getTotalPerson() != null && !supplierMatServe.getTotalPerson().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_person", "人员必须是整数");
			bool = false;
		}
		if(supplierMatServe.getTotalMange() == null) {
			model.addAttribute("fw_mange", "不能为空");
			bool = false;
		}
		if(supplierMatServe.getTotalMange() != null && !supplierMatServe.getTotalMange().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_mange", "人员必须是整数");
			bool = false;
		}
		if(supplierMatServe.getTotalTech() == null) {
			model.addAttribute("fw_tech", "不能为空");
			bool = false;
		}
		if(supplierMatServe.getTotalTech() != null && !supplierMatServe.getTotalTech().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_tech", "格式不正确");
			bool = false;
		}
		if(supplierMatServe.getTotalWorker() == null) {
			model.addAttribute("fw_work", "不能为空");
			bool = false;
		}
		if(supplierMatServe.getTotalWorker() != null && !supplierMatServe.getTotalWorker().toString().matches("^[0-9]*$")) {
			model.addAttribute("fw_work", "格式不正确");
			bool = false;
		}*/
		//		List<SupplierCertServe> list = supplierMatServe.getListSupplierCertSes();
		//		if(list==null||list.size()<1){
		//			model.addAttribute("fw_cert", "请添加服务证书信息");
		//			bool=false;
		//		}
		/*List<SupplierCertServe> list = supplierMatServe.getListSupplierCertSes();
		if(list != null && list.size() > 0){
			Set<String> codeSet = new HashSet<>();
			int codeCount = 0;
			for (SupplierCertServe se : list) {
	            if(StringUtils.isNotBlank(se.getCode())){
	            	codeSet.add(se.getCode());
	            	codeCount++;
	            }
            }
		    if(codeSet.size() != codeCount){
		    	model.addAttribute("fw_cert", "证书编号重复!");
                bool = false;
		    }
		}*/
		return bool;
	}

	public boolean validateUpload(Model model, String supplierId) {
		boolean bool = true;
		SupplierDictionaryData supplierDictionary = dictionaryDataServiceI.getSupplierDictionary();
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
	 * @Title: queryByPid
	 * @Description: TODO
	 * author: Li Xiaoxiao
	 * @param @param id
	 * @param @param model
	 * @return String
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
					//choseId.add(category.getId());
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

	/**
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
				List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, typeId, code);
				//List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, typeId, code);
				// 去掉审核不通过的品目
				//items = supplierItemService.removeAuditNotItems(items, supplierId, code);
				if(items != null && items.size() > 0) {
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
			        if (cate.getLevel() != null && cate.getLevel() < level) {
			            child.remove(i);
			        }
			    }
			}
			for(Category c: child) {
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(c.getName());
				ct1.setParentId(c.getParentId());
				ct1.setId(c.getId());
                ct1.setCode(c.getCode());
                List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(), code);
				//List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(), code);
				// 去掉审核不通过的品目
				//items = supplierItemService.removeAuditNotItems(items, supplierId, code);
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

    @ResponseBody
    @RequestMapping(value = "/loadCategory", produces = "application/json;charset=UTF-8")
    public String loadCategory(HttpServletRequest request){
	    JSONArray jsonArray = new JSONArray();
	    String id = request.getParameter("id");
        String typeId = "";
        String code = request.getParameter("code");
        String supplierId = request.getParameter("supplierId");
        String status = request.getParameter("status");
        Integer statusInt = null;
        if(!StringUtils.isEmpty(status)){
            statusInt = Integer.parseInt(status);
        }
        //初始化根节点
        if(StringUtils.isEmpty(id)) {
            if(StringUtils.isNotBlank(code)) {
                JSONObject jsonObject = new JSONObject();
                DictionaryData type = DictionaryDataUtil.get(code);
                if(type != null) {
                    if(type.getCode().equals("PRODUCT")) {
                        DictionaryData dd = DictionaryDataUtil.get("GOODS");
                        jsonObject.put("code","PRODUCT");
                        typeId = dd.getId();
                    } else if(type.getCode().equals("SALES")) {
                        DictionaryData dd = DictionaryDataUtil.get("GOODS");
                        jsonObject.put("code","SALES");
                        typeId = dd.getId();
                    } else {
                        jsonObject.put("code",code);
                        typeId = type.getId();
                    }
                }
                jsonObject.put("name",type.getName());
                jsonObject.put("id", typeId);
                jsonObject.put("open",true);//默认打开根节点
                List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, typeId, code);
                //List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, typeId, code);
                // 去掉审核不通过的品目
                //items = supplierItemService.removeAuditNotItems(items, supplierId, code);
                if(items != null && items.size() > 0) {
                    jsonObject.put("checked", true);
                }
                jsonObject.put("isParent", true);
                jsonObject.put("children", loadChildCategory(typeId, statusInt, supplierId, code));
                jsonArray.add(jsonObject);
            }
        }
	    return jsonArray.toString();
    }
    public JSONArray loadChildCategory(String id, Integer status, String supplierId, String code){
        JSONArray jsonArray = new JSONArray();
        List < Category > child = categoryService.findPublishTree(id, status);
        if(null == child || child.isEmpty()){
            return jsonArray;
        }
        Integer level = SupplierLevelUtil.getLevel(supplierId, code);
        if (level != null) {
            for (int i = 0; i < child.size(); i++) {
                Category cate = child.get(i);
                if (cate.getLevel() != null && cate.getLevel() < level) {
                    child.remove(i);
                }
            }
        }
        for(Category c: child) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", c.getName());
            jsonObject.put("parentId", c.getParentId());
            jsonObject.put("id", c.getId());
            jsonObject.put("code", c.getCode());
            List < SupplierItem > items = supplierItemService.getBySupplierIdCategoryIdIsNotReturned(supplierId, c.getId(), code);
            //List < SupplierItem > items = supplierItemService.getSupplierIdCategoryId(supplierId, c.getId(), code);
            // 去掉审核不通过的品目
            //items = supplierItemService.removeAuditNotItems(items, supplierId, code);
            if(items != null && items.size() > 0) {
                jsonObject.put("checked", true);
            }
            List < Category > cList = categoryService.findTreeByPid(c.getId());
            if(cList != null && cList.size() > 0) {
                jsonObject.put("isParent", true);
                jsonObject.put("children", loadChildCategory(c.getId(), status, supplierId, code));
            } else {
                jsonObject.put("isParent", false);
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @RequestMapping("/audit_org")
	public String audit_org(Model model, String name) {
		Supplier supp = supplierService.queryByName(name);
		Supplier supplier = supplierService.get(supp.getId());
		Orgnization orgnization = orgnizationServiceI.getOrgByPrimaryKey(supplier.getProcurementDepId());
		model.addAttribute("purchaseDep", orgnization);
		return "ses/sms/supplier_register/audit_org";
	}

	@RequestMapping(value = "/audit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String auditMsg(SupplierAudit supplierAudit) {
		List < SupplierAudit > list = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
		if(list != null && list.size() > 0){
			return JSON.toJSONString(list.get(0));
		}
		return null;
	}

	public List < String > getAuditFiled(String id) {
		List < String > list = new LinkedList < String > ();
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(id);
		List < SupplierAudit > audit = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
		for(SupplierAudit s: audit) {
			list.add(s.getAuditField());
		}
		return list;
	}

	/**
	 *〈简述〉异步加载品目合同上传
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierId 供应商Id
	 * @param model Model对象
	 * @param supplierTypeId 供应商类型Id
	 * @param pageNum
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

		/*List < Category > categoryList = new ArrayList < Category > ();
		List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		for(SupplierItem item: itemsList) {
			Category cate = categoryService.findById(item.getCategoryId());
			if(cate!=null){
				cate.setId(item.getId());
				categoryList.add(cate);
			}
			
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
		}*/
		//List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		// 去掉审核不通过的品目(由于是分页，不好处理，这里直接查询SupplierItem的isReturned不为1的记录)
		//itemsList = supplierItemService.removeAuditNotItems(itemsList, supplierId, supplierTypeId);
		List < SupplierItem > itemsList = supplierItemService.getItemList(supplierId, supplierTypeId, (byte)0, pageNum == null ? 1 : pageNum);
		List<ContractBean> contractList = new ArrayList<ContractBean>();
		for (SupplierItem item : itemsList) {
			ContractBean con = new ContractBean();
		    con.setId(item.getId());
		    Category cate = categoryService.findById(item.getCategoryId());
			if(cate!=null){
				con.setName(cate.getName());
				con.setCategoryId(cate.getId());
			}
		    
		    con.setOneContract(id1);
			con.setTwoContract(id2);
			con.setThreeContract(id3);
			con.setOneBil(id4);
			con.setTwoBil(id5);
			con.setThreeBil(id6);
			
			contractList.add(con);
		}
		// 分页,pageSize == 10
		PageInfo < SupplierItem > pageInfo = new PageInfo < SupplierItem > (itemsList);
		model.addAttribute("result", pageInfo);
		model.addAttribute("contract", contractList);
		// 年份
		List < Integer > years = supplierService.getThressYear();
		model.addAttribute("years", years);
		model.addAttribute("supplierTypeId", supplierTypeId);
		model.addAttribute("supplierId", supplierId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		// 查询供应商get(supplierId)
		Supplier currSupplier = supplierService.selectById(supplierId);
		model.addAttribute("currSupplier", currSupplier);
		
		if(currSupplier != null && currSupplier.getStatus() != null && currSupplier.getStatus() == 2){
			// 不通过字段的名字
			SupplierAudit s = new SupplierAudit();
			s.setSupplierId(supplierId);;
			//s.setAuditType("contract_page");
			s.setAuditType(ses.util.Constant.CONTRACT_PRODUCT_PAGE);
			if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supplierTypeId)){
				s.setAuditType(ses.util.Constant.CONTRACT_PRODUCT_PAGE);
			}
			if(ses.util.Constant.SUPPLIER_SALES.equals(supplierTypeId)){
				s.setAuditType(ses.util.Constant.CONTRACT_SALES_PAGE);
			}
			List < SupplierAudit > auditLists = supplierAuditService.getAuditRecords(s, SupplierConstants.AUDIT_RETURN_STATUS);

			StringBuffer errorField = new StringBuffer();
			for(SupplierAudit audit: auditLists) {
				errorField.append(audit.getAuditField() + ",");
			}
			model.addAttribute("audit", errorField);
			model.addAttribute("auditType", s.getAuditType());
		}
		
		return "ses/sms/supplier_register/ajax_contract";
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
		int betweenDays = supplierService.daysBetween(submitDate);
		if(betweenDays > 45) {
			isok = "0";
		} else {
			isok = "1";
		}
		
		PurchaseDep dep =null;
		if("".equals(supplier.getProcurementDepId())||supplier.getProcurementDepId()==null){
			logger.error("验证失败");
			isok="3";
		}else{
			dep =purchaseOrgnizationService.selectPurchaseById(supplier.getProcurementDepId());
		}
		allInfo.put("isok", isok);
		 
		if(dep!=null){
			/*allInfo.put("name", dep.getShortName());
			allInfo.put("concat", dep.getSupplierContact());
			allInfo.put("phone", dep.getSupplierPhone());
			allInfo.put("address", dep.getSupplierAddress());
			allInfo.put("code", dep.getSupplierPostcode());*/
			allInfo.put("purchaseDep", dep);
		}

		// 查询初审机构信息
//		HashMap < String, Object > map = new HashMap < String, Object > ();
//		map.put("id", supplier.getProcurementDepId());
//		map.put("typeName", "1");
//		List < PurchaseDep > depList = purchaseOrgnizationService.findPurchaseDepList(map);
//		if(depList != null && depList.size() > 0) {
//			PurchaseDep purchaseDep = depList.get(0);
//			allInfo.put("contact", purchaseDep.getContact() == null ? "暂无" : purchaseDep.getContact());
//			allInfo.put("contactTelephone", purchaseDep.getContactTelephone() == null ? "暂无" : purchaseDep.getContactTelephone());
//		}
		return JSON.toJSONString(allInfo);
	}
	
    @RequestMapping("/addAddress")
    public ModelAndView addAddress(HttpServletRequest request, Model model){
	    String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
	    String ind = request.getParameter("ind");
        //初始化省份
        List < Area > province = areaService.findRootArea();
        //初始化供应商注册附件类型
        model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierHousePoperty());
        model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("province", province);
        model.addAttribute("id", id);
        model.addAttribute("ind", ind);
	    return new ModelAndView("ses/sms/supplier_register/add_house_addr");
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
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delAddress")
	public String delAddress(String ids) {
        boolean isOk = supplierAddressService.deleteAddressByIds(ids);
        if(isOk){
        	return "ok";
        }
        return "fail";
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
		model.addAttribute("typeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE, null, 4));
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
//    	filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		String path = PathUtil.getWebRoot() + "excel/军队物资工程服务采购评审专家参评产品分类目录.xlsx";;
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("军队物资工程服务采购评审专家参评产品分类目录.xlsx".getBytes("UTF-8"), "iso-8859-1"); //为了解决中文名称乱码问题  
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
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteAfterSaleDep")
    public String deleteAfterSaleDep(String ids) {
        boolean isOk = supplierAfterSaleDepService.deleteAfterSaleDepByIds(ids);
        if(isOk){
        	return "ok";
        }
        return "fail";
    }
    
    /**
     *〈简述〉根据证书编号获取附件信息
     *〈详细描述〉
     * @author WangHuijie
     * @param certCode
     * @param supplierId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getFileByCode")
    public ModelAndView addSeCert(String certCode, String supplierId, Model model, String number,String professType) {
//        List<SupplierCertEng> certEng = supplierCertEngService.selectCertEngByCode(certCode, supplierId);
        SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
        List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null, matEng.getId(), certCode, professType);
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
    public String saveItemsInfo(Supplier supplier) {
        supplierItemService.updateByPrimaryKeySelective(supplier.getListSupplierItems());
        return "ok";
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
    public String isPass(String supplierId,String stype) {
		// BigDecimal score = supplierService.getScoreBySupplierId(supplierId);
		Supplier supplier = supplierService.get(supplierId, 1);
		if (supplier == null) {
			return "-1";
		}
		BigDecimal score = supplierService.getScoreByFinances(supplier.getListSupplierFinances());
		List<SupplierTypeRelate> relate = supplierTypeRelateService.queryBySupplier(supplierId);
		if (stype != null && stype.trim().length() != 0) {
			if (score.compareTo(BigDecimal.valueOf(3000)) == -1) {
				return "0";
			}
		}

		for (SupplierTypeRelate type : relate) {
			if (type.getSupplierTypeId().equals("SALES")) {
				if (score.compareTo(BigDecimal.valueOf(3000)) == -1) {
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
    public String getAptLevel(String typeId,String supplierId) {
        List<DictionaryData> data = qualificationLevelService.getByQuaId(typeId);
        List<DictionaryData> list = new ArrayList<DictionaryData>();
        if (data != null&&data.size()>0) {
            return JSON.toJSONString(data);
        }else if(data.size()<1){
        	List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(typeId, supplierId);
        	if(projectData!=null&&projectData.size()>0){
        		DictionaryData dictionaryData = DictionaryDataUtil.findById(projectData.get(0).getCertLevel());
            	if(dictionaryData!=null){
            		list.add(dictionaryData);
            		return JSON.toJSONString(list);
            	}else{
            		DictionaryData dd=new DictionaryData();	
            		dd.setId(projectData.get(0).getCertLevel());
                	dd.setName(projectData.get(0).getCertLevel());
                	list.add(dd);
                	return JSON.toJSONString(list);
            	}
        	}
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
    public String getAptLevel(String typeId, String certCode, String supplierId,String professType) {
		SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
		List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(typeId, matEng.getId(), certCode, professType);
		if (certEng != null && certEng.size() > 0) {
			String level = certEng.get(0).getAptituteLevel();
			DictionaryData data = DictionaryDataUtil.findById(level);
			if (data != null) {
				return JSON.toJSONString(data);
			}
		}

		List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(typeId, supplierId);
		if (projectData != null && projectData.size() > 0) {
			Qualification qualification = qualificationService.getQualification(projectData.get(0).getName());
			DictionaryData dd = new DictionaryData();
			dd.setId(projectData.get(0).getId());
			if (null != qualification) {
				dd.setName(qualification.getName());
			} else {
				dd.setName(projectData.get(0).getCertLevel());
			}
			return JSON.toJSONString(dd);
		}
		return null;
    }
    
    /**
     * @Title: delteBranch
     * @Description: 删除境外分支
     * author: Li Xiaoxiao
     * @return String
     */
    @RequestMapping("/deleteBranch")
    @ResponseBody
    public String delteBranch(String id){
    	supplierBranchService.delete(id);
    	return "";
    }
    
    /**
     * @Title: oldData
     * @Description: 判断以前注册的供应商是否有销售类型，如果有就清楚数据，如果没有就下一步
     * author: Li Xiaoxiao
     * @param supplierId
     * @return String
     */
    @RequestMapping("/deleteOld")
    @ResponseBody
    public String oldData(String supplierId){
    	supplierTypeRelateService.delete(supplierId, "SALES");
    	return "0";
    }
    
	@RequestMapping("/download_report")
	public ResponseEntity<byte[]> download(HttpServletRequest request) throws IOException {
		// filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
		String path = PathUtil.getWebRoot() + "excel/申明.pdf";
		File file = new File(path);

		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("申明.pdf".getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
	}

    @RequestMapping(value="/getProType",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getProType(String typeId, String certCode, String supplierId){
    	List<String> list = null;
    	if(null!= certCode){
    		SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
    		list = supplierAptituteService.getPorType( typeId,matEng.getId(),certCode);
    	}
    	String string = JSON.toJSONString(list);
    	return string;
    }
    
	@ResponseBody
	@RequestMapping("/getUUID")
	public String getUUID() {
		return UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
    
    /**
     * @Title: getRandomId
     * @Description:获取随机生成的ID
     * author: Li Xiaoxiao 
     * @return String     
     */
    @RequestMapping("/getId")
    @ResponseBody
    public String getRandomId(){
    	String id = UUID.randomUUID().toString().replaceAll("-", "");
    	return id;
    }
    
    public boolean supplierAptituteService(List<SupplierAptitute> list){
    	boolean bool=true;
    	Integer count=0;
    	for(SupplierAptitute sa:list){
    		String id = DictionaryDataUtil.getId("SUPPLIER_ENG_CERT");
			List<UploadFile> files = uploadService.getFilesOther(sa.getId(), id, "1");
			if(files!=null&&files.size()>0){
				count++;
			}
    	}
    	Integer size=list.size();
    	if(count.equals(size)){
    		bool=false;
    	}
    	return bool;
    }
    
    private void alertLogin(String msg){
        StringBuilder builder = new StringBuilder();
        builder.append("<HTML><HEAD>");
        builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/backend/js/jquery.min.js'></script>");
        builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/layer/layer.js'></script>");
        builder.append("<link href='"+request.getContextPath()+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
        builder.append("</HEAD>");
        builder.append("<script type=\"text/javascript\">"); 
        builder.append("$(function() {");
        //builder.append("layer.confirm('您未登录，请登录！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
        builder.append("layer.alert('"+msg+"',{ closeBtn: 0,title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
        builder.append("window.top.location.href='"); 
        builder.append(request.getContextPath()+"/index/sign.html");  
        builder.append("';"); 
        builder.append("});");
        builder.append("});");
        builder.append("</script>");  
        builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
        printOutMsg(response, builder.toString());
    }
    
    private void alertInfo(String msg, String url){
    	StringBuilder builder = new StringBuilder();
    	builder.append("<HTML><HEAD>");
    	builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/backend/js/jquery.min.js'></script>");
    	builder.append("<script language='javascript' type='text/javascript' src='"+request.getContextPath()+"/public/layer/layer.js'></script>");
    	builder.append("<link href='"+request.getContextPath()+"/public/backend/css/common.css' media='screen' rel='stylesheet'>");
    	builder.append("</HEAD>");
    	builder.append("<script type=\"text/javascript\">"); 
    	builder.append("$(function() {");
    	//builder.append("layer.confirm('您未登录，请登录！',{ btn: ['确定'],title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
    	builder.append("layer.alert('"+msg+"',{ closeBtn: 0,title:'提示',area : '240px',offset: '30px',shade:0.01 },function(){");  
    	builder.append("window.top.location.href='"); 
    	builder.append(url);  
    	builder.append("';"); 
    	builder.append("});");
    	builder.append("});");
    	builder.append("</script>");  
    	builder.append("<BODY><div style='width:1000px; height: 1000px;'></div></BODY></HTML>");
    	printOutMsg(response, builder.toString());
    }
    
    private Supplier checkSupplier(String suppId){
		// 非登录供应商访问
		Object loginName = session.getAttribute("loginName");
		if(loginName == null){
			alertLogin("请登录！");
			return null;
		}
		if(suppId == null){
			alertLogin("供应商不存在！");
			return null;
		}
		Supplier supplier = supplierService.selectById(suppId);
		if(supplier == null){
			alertLogin("供应商不存在！");
			return null;
		}
		if(supplier != null && !supplier.getLoginName().equals(loginName)){
			alertLogin("您无权访问！");
			return null;
		}
		if(supplier != null && supplier.getStatus() != null 
				&& supplier.getStatus() != SupplierConstants.Status.TEMPORARY.getValue()
				&& supplier.getStatus() != SupplierConstants.Status.RETURN.getValue()){
			alertInfo("您现在的状态是："+SupplierConstants.STATUSMAP.get(supplier.getStatus()), request.getContextPath()+"/");
			return null;
		}
		String referer = request.getHeader("referer");
		if(referer == null){// 如果是浏览器地址栏直接访问，则跳转至基本信息
			String basicInfoUrl = request.getContextPath() + "/supplier/basicInfo.html?suppId="+suppId;
			printOutMsg(response, "<script>location.href='"+basicInfoUrl+"';</script>");
			return null;
		}
		return supplier;
    }
    
}