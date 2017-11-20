package ses.controller.sys.sms;

import bss.formbean.PurchaseRequiredFormBean;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.service.UploadService;
import common.utils.JdcgResult;
import common.utils.ListSortUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import ses.constants.SupplierConstants;
import ses.controller.sys.ems.ExpertAuditController;
import ses.dao.sms.SupplierCertServeMapper;
import ses.dao.sms.SupplierMatServeMapper;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCategoryOpinion;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEngQua;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.SupplierPublicity;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierSignature;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.EngCategoryService;
import ses.service.bms.QualificationService;
import ses.service.bms.TodosService;
import ses.service.ems.ExpertCategoryService;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditNotService;
import ses.service.sms.SupplierAuditOpinionService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierCertProService;
import ses.service.sms.SupplierCertSellService;
import ses.service.sms.SupplierFinanceService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
import ses.service.sms.SupplierMatProService;
import ses.service.sms.SupplierMatSellService;
import ses.service.sms.SupplierModifyService;
import ses.service.sms.SupplierPorjectQuaService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierSignatureService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;
import ses.util.SupplierLevelUtil;
import ses.util.WordUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title:SupplierAuditController </p>
 * <p>Description: 供应商审核控制类</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:14:36
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAudit")
public class SupplierAuditController extends BaseSupplierController {

	@Autowired
	private SupplierAuditService supplierAuditService;

	/**
	 * 供应商
	 */
	@Autowired
	private SupplierService supplierService;

	/**
	 * 品目
	 */
	@Autowired
	private CategoryService categoryService;

	/**
	 * 待办接口
	 */
	@Autowired
	private TodosService todosService;

	/**
	 * 数据字典
	 */
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;

	/**
	 * 境外分支
	 */
	@Autowired
	private SupplierBranchService supplierBranchService;

	/**
	 * 地区
	 */
	@Autowired
	private AreaServiceI areaService;

	/**
	 * 生产经营地址
	 */
	@Autowired
	private SupplierAddressService supplierAddressService;

	@Autowired
	private SupplierItemService supplierItemService;

	@Autowired
	private SupplierHistoryService supplierHistoryService;

	/** 供应商关联类型 */
	@Autowired
	private SupplierTypeRelateService supplierTypeRelateService;

	/** 采购机构 **/
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationService;

	/** 审核不通过 **/
	@Autowired
	private SupplierAuditNotService supplierAuditNotService;

	@Autowired
	private UploadService uploadService;
	
	@Autowired
	private SupplierModifyService supplierModifyService;
	
	
	@Autowired
	private SupplierSignatureService supplierSignatureService;
	/**
	 * 资质类型
	 */
	@Autowired
	private QualificationService qualificationService; 
	
	@Autowired
	private EngCategoryService engCategoryService;
	
	@Autowired
	private  SupplierMatEngService supplierMatEngService;
	
	@Autowired
	private SupplierAptituteService supplierAptituteService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	/**
	 * 意见
	 */
	@Autowired
	private SupplierAuditOpinionService supplierAuditOpinionService;
	
	@Autowired
	private ExpertCategoryService expertCategoryService;
	
	/**
	 * 财务信息
	 */
	@Autowired
	private SupplierFinanceService supplierFinanceService;
	
	@Autowired
	private SupplierMatProService supplierMatProService;
	
	@Autowired
	private SupplierCertProService supplierCertProService;
	
	//物资销售  生产专业信息
	@Autowired
	private SupplierMatSellService supplierMatSellService;
	
	//物资销售   资质证书
	@Autowired
	private SupplierCertSellService supplierCertSellService;
	
	//供应商服务专业信息
	@Autowired
	private SupplierMatServeMapper supplierMatServeMapper;
	
	//服务资质证书
	@Autowired
	private SupplierCertServeMapper supplierCertServeMapper;

	@Autowired
	private PurChaseDepOrgService purChaseDepOrgService;
	
	/**
	 * @Title: essentialInformation
	 * @author Xu Qing
	 * @date 2016-9-12 下午7:14:09  
	 * @Description: 基本信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(@CurrentUser User user, HttpServletRequest request, Supplier supplier, String supplierId, Integer sign) {
		
		supplier = supplierService.get(supplierId, 1);
		
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return null;
		}
		/*if(supplier.getAuditor() != null && !supplier.getAuditor().equals(user.getRelName())){
			return null;
		}*/
		
		request.setAttribute("sign", sign);
		
		Integer supplierStatus = supplier.getStatus();
		String loginName = user.getLoginName();
		
		SupplierModify supplierModify = new SupplierModify();
		supplierModify.setSupplierId(supplierId);
		
		if(SupplierConstants.isStatusToAudit(supplierStatus)){
			//先删除对比的旧数据
			supplierModifyService.deleteByType(supplierModify);
			// 插入对比后的数据
			supplierModifyService.insertModifyRecord(supplierModify);
			// 更新审核记录状态
			supplierAuditService.updateReturnStatus(supplierId);
		}
		
		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//在数据字典里查询营业执照类型
		List < DictionaryData > list = DictionaryDataUtil.find(17);
		for(int i = 0; i < list.size(); i++) {
			if(supplier.getBusinessType().equals(list.get(i).getId())) {
				String businessType = list.get(i).getName();
				supplier.setBusinessType(businessType);
			}
		}
		
		//在数据字典里查询企业性质
		List < DictionaryData > businessList = DictionaryDataUtil.find(32);
		String businessNature = supplier.getBusinessNature();
		if(businessNature !=null){
			for(int i = 0; i < businessList.size(); i++) {
				if(businessNature.equals(businessList.get(i).getId())) {
					String business = businessList.get(i).getName();
					supplier.setBusinessNature(business);
				}
			}
		}
		request.setAttribute("suppliers", supplier);
//		List < SupplierBranch > supplierBranchList = supplierBranchService.findSupplierBranch(supplierId);
		List < SupplierBranch > supplierBranchList = supplier.getBranchList();
		if(!supplierBranchList.isEmpty() && supplierBranchList.size()>0){
			for(SupplierBranch sb:supplierBranchList){
				if(sb !=null && sb.getCountry()!=null){
					DictionaryData dd = DictionaryDataUtil.findById(sb.getCountry());
					if(dd !=null){
						sb.setCountryName(dd.getName());
					}
				}
			}
		}
		
		request.setAttribute("supplierBranchList", supplierBranchList);

		/**
		 * 查询地址
		 */
		List < Area > province = areaService.findRootArea();
		request.setAttribute("province", province);
		Area area = new Area();
		//地址信息里地址
		area = areaService.listById(supplier.getAddress());
		String sonAddress = area.getName();
		request.setAttribute("sonAddress", sonAddress);
		for(int i = 0; i < province.size(); i++) {
			if(area.getParentId().equals(province.get(i).getId())) {
				String parentAddress = province.get(i).getName();
				request.setAttribute("parentAddress", parentAddress);
			}
		}

		//注册联系人里地址
		area = areaService.listById(supplier.getConcatCity());
		String sonConcatProvince = area.getName();
		request.setAttribute("sonConcatProvince", sonConcatProvince);
		for(int i = 0; i < province.size(); i++) {
			if(area.getParentId().equals(province.get(i).getId())) {
				String parentConcatProvince = province.get(i).getName();
				request.setAttribute("parentConcatProvince", parentConcatProvince);
			}
		}

		//军队业务联系人里地址
		area = areaService.listById(supplier.getArmyBuinessCity());
		String sonArmyBuinessProvince = area.getName();
		request.setAttribute("sonArmyBuinessProvince", sonArmyBuinessProvince);
		for(int i = 0; i < province.size(); i++) {
			if(province.get(i).getId() !=null && area.getParentId().equals(province.get(i).getId())) {
				String parentArmyBuinessProvince = province.get(i).getName();
				request.setAttribute("parentArmyBuinessProvince", parentArmyBuinessProvince);
			}
		}

		//生产经营地址
		List < SupplierAddress > supplierAddress = supplierAddressService.queryBySupplierId(supplierId);
//		List < SupplierAddress > supplierAddress = supplier.getAddressList();
		if(!supplierAddress.isEmpty() && supplierAddress.size() > 0 ){
			for(Area a: province) {
				for(SupplierAddress s: supplierAddress) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
		}
		
		request.setAttribute("supplierAddress", supplierAddress);
		
		//售后服务机构一览表
		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplier.getListSupplierAfterSaleDep();
		request.setAttribute("listSupplierAfterSaleDep",listSupplierAfterSaleDep);
		
		/**
		 * 查出修改前的信息
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			//地址信息
			supplierModify.setListType(1);
			supplierModify.setRelationId(null);
			supplierModify.setId(null);
			supplierModify.setBeforeField(null);
			supplierModify.setBeforeContent(null);
			List < SupplierModify > fieldAddressList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldAddress = new StringBuffer();
			for(int i = 0; i < fieldAddressList.size(); i++) {
				String beforeField = fieldAddressList.get(i).getRelationId() +"_"+ fieldAddressList.get(i).getBeforeField();
				fieldAddress.append(beforeField + ",");
			}
			request.setAttribute("fieldAddress", fieldAddress);
			
			//基本信息
			supplierModify.setListType(0);
			List < SupplierModify > fieldList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer field = new StringBuffer();
			for(int i = 0; i < fieldList.size(); i++) {
				String beforeField = fieldList.get(i).getBeforeField();
				field.append(beforeField + ",");
			}
			request.setAttribute("field", field);
			
			//售后服务机构一览表修改前的信息
			supplierModify.setListType(11);
			List < SupplierModify > afterSaleDepList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldAfterSaleDep = new StringBuffer();
			for(int i = 0; i < afterSaleDepList.size(); i++) {
				String beforeField = afterSaleDepList.get(i).getRelationId() +"_"+ afterSaleDepList.get(i).getBeforeField();
				fieldAfterSaleDep.append(beforeField + ",");
			}
			request.setAttribute("fieldAfterSaleDep", fieldAfterSaleDep);
			
			//境外分支
			supplierModify.setListType(2);
			List < SupplierModify > branchList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldBranch = new StringBuffer();
			for(int i = 0; i < branchList.size(); i++) {
				String beforeField = branchList.get(i).getRelationId() +"_"+ branchList.get(i).getBeforeField();
				fieldBranch.append(beforeField + ",");
			}
			request.setAttribute("fieldBranch", fieldBranch);
		
		
			/**
			 * 基本信息退回修改后的附件
			 */
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				fileModifyField.append(m.getBeforeField() + ",");
			}
			request.setAttribute("fileModifyField", fileModifyField);
			
			/**
			 * 房屋证明退回修改后的附件
			 */
			StringBuffer houseFileModifyField = new StringBuffer();
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null){
					if(m.getRelationId() !=null){
						houseFileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
					}
				}
			}
			request.setAttribute("houseFileModifyField", houseFileModifyField);
		}

		//回显未通过字段
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
//			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditField = new StringBuffer();
			StringBuffer unableField = new StringBuffer();
			if(reasonsList != null && !reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					auditField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditField", auditField);
			request.setAttribute("unableField", unableField);
		}
		return "ses/sms/supplier_audit/essential";
	}

	/**
	 * @Title: financialInformation
	 * @author Xu Qing
	 * @date 2016-9-13 上午10:51:15  
	 * @Description:财务信息
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("financial")
	public String financialInformation(@CurrentUser User user, HttpServletRequest request, String supplierId, Integer supplierStatus, Integer sign) {
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("supplierStatus", supplierStatus);
		request.setAttribute("sign", sign);

		//文件
		if(supplierId != null) {
//			List < SupplierFinance > supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
			List < SupplierFinance > supplierFinance = supplierService.get(supplierId, 1).getListSupplierFinances();
			
			/**
			 * 只要近三年财务
			 */
			if(supplierFinance != null && supplierFinance.size() > 0){
				// 排序
				ListSortUtil<SupplierFinance> sortList = new ListSortUtil<SupplierFinance>();
				sortList.sort(supplierFinance, "year", "asc");
				// 如果近三年财务信息超过三年，则取最近三年
				if(supplierFinance.size() > 3){
					Iterator<SupplierFinance> it = supplierFinance.iterator();
					int i = supplierFinance.size();
					while(it.hasNext()){
						it.next();
						if(i > 3){
							it.remove();
						}
						i--;
					}
				}
			}
			
			request.setAttribute("financial", supplierFinance);
		}

		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		String loginName = user.getLoginName();
		//查出财务修改前的信息
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("finance_page");
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer field = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				field.append(beforeField + ",");
			}
			request.setAttribute("field", field);
		}
		
		//回显未通过字段
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
//			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditField = new StringBuffer();
			StringBuffer unableField = new StringBuffer();
			if(reasonsList != null && !reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					auditField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditField", auditField);
			request.setAttribute("unableField", unableField);
		}
		
		/**
		 * 退回修改后的附件
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null && m.getBeforeField() != null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
				}
			}
			request.setAttribute("fileModifyField", fileModifyField);
		}
		return "ses/sms/supplier_audit/financial";
	}

	/**
	 * @Title: shareholderInformation
	 * @author Xu Qing
	 * @date 2016-9-13 上午11:19:37  
	 * @Description: 股东信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("shareholder")
	public String shareholderInformation(@CurrentUser User user, HttpServletRequest request, SupplierStockholder supplierStockholder, Integer supplierStatus, Integer sign) {
		String supplierId = supplierStockholder.getSupplierId();
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("supplierStatus", supplierStatus);
		request.setAttribute("sign", sign);
		
		List < SupplierStockholder > list = supplierAuditService.ShareholderBySupplierId(supplierId);
		request.setAttribute("shareholder", list);
		
		String loginName = user.getLoginName();
		
		/**
		 * 查出股东修改前的信息
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("shareholder_page");
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer field = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				field.append(beforeField + ",");
			}
			request.setAttribute("field", field);
		}
		
		/*//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("生产")) {
			url = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else if(supplierTypeName.contains("销售") && url == null) {
			url = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("工程") && url == null) {
			url = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("服务") && url == null) {
			url = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else {
			url = request.getContextPath() + "/supplierAudit/items.html";
		}
		request.setAttribute("url", url);*/
		
		//回显未通过字段
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
//			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditField = new StringBuffer();
			StringBuffer unableField = new StringBuffer();
			if(reasonsList != null && !reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					auditField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditField", auditField);
			request.setAttribute("unableField", unableField);
		}
		return "ses/sms/supplier_audit/shareholder";
	}

	/**
	 * @Title: materialProduction
	 * @author Xu Qing
	 * @date 2016-9-13 下午4:32:12  
	 * @Description: 物资生产型专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialProduction")
	public String materialProduction(HttpServletRequest request, SupplierMatPro supplierMatPro) {
		String supplierId = supplierMatPro.getSupplierId();
		/*List<SupplierCertPro> materialProduction = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();*/
		//资质资格证书信息
		List < SupplierCertPro > materialProduction = supplierAuditService.findBySupplierId(supplierId);
		//供应商组织机构人员,产品研发能力,产品生产能力,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
		supplierMatPro = supplierService.get(supplierId, 2).getSupplierMatPro();
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("materialProduction", materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);

		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List < SupplierHistory > editList = supplierHistoryService.selectAllBySupplierId(supplierHistory);
		StringBuffer field = new StringBuffer();
		for(int i = 0; i < editList.size(); i++) {
			String beforeField = editList.get(i).getBeforeField();
			field.append(beforeField + ",");
		}
		request.setAttribute("field", field);

		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("销售")) {
			url = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("工程") && url == null) {
			url = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("服务") && url == null) {
			url = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else {
			url = request.getContextPath() + "/supplierAudit/items.html";
		}
		request.setAttribute("url", url);
		return "ses/sms/supplier_audit/material_production";
	}

	/**
	 * @Title: materialSales
	 * @author Xu Qing
	 * @date 2016-9-18 下午8:05:15  
	 * @Description: 物资销售专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("materialSales")
	public String materialSales(HttpServletRequest request, SupplierMatSell supplierMatSell) {
		String supplierId = supplierMatSell.getSupplierId();
		//资质资格证书
		List < SupplierCertSell > supplierCertSell = supplierAuditService.findCertSellBySupplierId(supplierId);
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId, 2).getSupplierMatSell();
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);

		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List < SupplierHistory > editList = supplierHistoryService.selectAllBySupplierId(supplierHistory);
		StringBuffer field = new StringBuffer();
		for(int i = 0; i < editList.size(); i++) {
			String beforeField = editList.get(i).getBeforeField();
			field.append(beforeField + ",");
		}
		request.setAttribute("field", field);

		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("工程")) {
			url = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("服务") && url == null) {
			url = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else {
			url = request.getContextPath() + "/supplierAudit/items.html";
		}
		request.setAttribute("url", url);

		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("生产")) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else {
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);

		return "ses/sms/supplier_audit/material_sales";
	}

	/**
	 * @Title: engineeringInformation
	 * @author Xu Qing
	 * @date 2016-9-18 下午8:13:24  
	 * @Description: 工程专业信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("engineering")
	public String engineeringInformation(HttpServletRequest request, SupplierMatEng supplierMatEng) {
		String supplierId = supplierMatEng.getSupplierId();
		if(supplierId != null) {
			//资质资格证书信息
			List < SupplierCertEng > supplierCertEng = supplierAuditService.findCertEngBySupplierId(supplierId);
			request.setAttribute("supplierCertEng", supplierCertEng);

			//资质资格信息
			List < SupplierAptitute > supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
			request.setAttribute("supplierAptitutes", supplierAptitute);

			//组织结构
			supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
			request.setAttribute("supplierMatEngs", supplierMatEng);

			//注册人人员
			List < SupplierRegPerson > listSupplierRegPersons = supplierService.get(supplierId, 2).getSupplierMatEng().getListSupplierRegPersons();
			request.setAttribute("listRegPerson", listSupplierRegPersons);
		}

		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		request.setAttribute("supplierId", supplierId);

		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List < SupplierHistory > editList = supplierHistoryService.selectAllBySupplierId(supplierHistory);
		StringBuffer field = new StringBuffer();
		for(int i = 0; i < editList.size(); i++) {
			String beforeField = editList.get(i).getBeforeField();
			field.append(beforeField + ",");
		}
		request.setAttribute("field", field);

		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("服务")) {
			url = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else {
			url = request.getContextPath() + "/supplierAudit/items.html";
		}
		request.setAttribute("url", url);

		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("销售")) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("生产") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else {
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);

		return "ses/sms/supplier_audit/engineering";
	}

	/**
	 * @Title: serviceInformation
	 * @author Xu Qing
	 * @date 2016-9-28 上午11:01:53  
	 * @Description: 服务专业信息 
	 * @param @param request
	 * @param @param supplierMatEng
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("serviceInformation")
	public String serviceInformation(HttpServletRequest request, SupplierMatServe supplierMatSe, SupplierMatSell supplierMatSell) {
		String supplierId = supplierMatSe.getSupplierId();
		request.setAttribute("supplierId", supplierId);
		//资质证书信息
		List < SupplierCertServe > supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierMatSes", supplierMatSe);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List < SupplierHistory > editList = supplierHistoryService.selectAllBySupplierId(supplierHistory);
		StringBuffer field = new StringBuffer();
		for(int i = 0; i < editList.size(); i++) {
			String beforeField = editList.get(i).getBeforeField();
			field.append(beforeField + ",");
		}
		request.setAttribute("field", field);

		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("工程")) {
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("销售") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("生产") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else {
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);

		return "ses/sms/supplier_audit/service_information";
	}

	/**
	 * 供应商类型
	 * @param request
	 * @param supplierMatSell
	 * @param supplierMatPro
	 * @param supplierMatEng
	 * @param supplierMatSe
	 * @param supplierId
	 * @param supplierStatus
	 * @param sign
	 * @return
	 */
	@RequestMapping("supplierType")
	public String supplierType(@CurrentUser User user, HttpServletRequest request, SupplierMatSell supplierMatSell, SupplierMatPro supplierMatPro, SupplierMatEng supplierMatEng, SupplierMatServe supplierMatSe, String supplierId, Integer supplierStatus, Integer sign) {
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("supplierStatus", supplierStatus);
		request.setAttribute("sign", sign);

		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
//		Supplier supplier = supplierAuditService.supplierById(supplierId);
		Supplier supplier = supplierService.get(supplierId, 2);
		if(supplier != null){
			supplierStatus = supplier.getStatus();
			/**
			 * 供应商类型
			 */
			//供应商类型code
			/*List < SupplierTypeRelate > typeIds = supplierTypeRelateService.queryBySupplier(supplierId);
			String supplierTypeCode = "";
			for(SupplierTypeRelate s: typeIds) {
				supplierTypeCode += s.getSupplierTypeId() + ",";
			}*/
			// 勾选的供应商类型
			request.setAttribute("supplierTypeCode", supplier.getSupplierTypeIds());
			
			List < DictionaryData > gcfwList = DictionaryDataUtil.find(6);// 物资/工程/服务
			for(int i = 0; i < gcfwList.size(); i++) {
				DictionaryData dd = gcfwList.get(i);
				String code = dd.getCode();
				if(code.equals("GOODS")) {// 除去物资
					gcfwList.remove(dd);
				}
			}
			request.setAttribute("gcfwList", gcfwList);
			List < DictionaryData > scxsList = DictionaryDataUtil.find(8);// 物资生产/物资销售
			request.setAttribute("scxsList", scxsList);
			
			/**
			 * 生产
			 */
			supplierMatPro = supplier.getSupplierMatPro();
			if(supplierMatPro != null){
				//资质资格证书信息
//				List < SupplierCertPro > materialProduction = supplierAuditService.findBySupplierId(supplierId);
				List < SupplierCertPro > materialProduction = supplierMatPro.getListSupplierCertPros();
				for(int i = 0; i < materialProduction.size() - 1; i++) {
					for(int j = materialProduction.size() - 1; j > i; j--) {
						if(materialProduction.get(j).getId().equals(materialProduction.get(i).getId())) {
							materialProduction.remove(j);
						}
					}
				}
				request.setAttribute("materialProduction", materialProduction);
				request.setAttribute("supplierMatPros", supplierMatPro);
			}

			/**
			 * 销售
			 */
			//组织机构和人员
			supplierMatSell = supplier.getSupplierMatSell();
			if(supplierMatSell != null){
				//资质资格证书
//				List < SupplierCertSell > supplierCertSell = supplierAuditService.findCertSellBySupplierId(supplierId);
				List < SupplierCertSell > supplierCertSell = supplierMatSell.getListSupplierCertSells();
				for(int i = 0; i < supplierCertSell.size() - 1; i++) {
					for(int j = supplierCertSell.size() - 1; j > i; j--) {
						if(supplierCertSell.get(j).getId().equals(supplierCertSell.get(i).getId())) {
							supplierCertSell.remove(j);
						}
					}
				}
				request.setAttribute("supplierCertSell", supplierCertSell);
				request.setAttribute("supplierMatSells", supplierMatSell);
			}
			
			/**
			 * 工程
			 */
			//组织结构
//			supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
			supplierMatEng = supplier.getSupplierMatEng();
			if(supplierMatEng != null){
				request.setAttribute("supplierMatEngs", supplierMatEng);
				//资质证书信息
				List < SupplierEngQua > supplierEngQuas = supplierMatEng.getListSupplierEngQuas();
				for(int i = 0; i < supplierEngQuas.size() - 1; i++) {
					for(int j = supplierEngQuas.size() - 1; j > i; j--) {
						if(supplierEngQuas.get(j).getId().equals(supplierEngQuas.get(i).getId())) {
							supplierEngQuas.remove(j);
						}
					}
				}
				request.setAttribute("supplierEngQuas", supplierEngQuas);
				//资质资格证书信息
//				List < SupplierCertEng > supplierCertEngs = supplierAuditService.findCertEngBySupplierId(supplierId);
				List < SupplierCertEng > supplierCertEngs = supplierMatEng.getListSupplierCertEngs();
				for(int i = 0; i < supplierCertEngs.size() - 1; i++) {
					for(int j = supplierCertEngs.size() - 1; j > i; j--) {
						if(supplierCertEngs.get(j).getId().equals(supplierCertEngs.get(i).getId())) {
							supplierCertEngs.remove(j);
						}
					}
				}
				request.setAttribute("supplierCertEngs", supplierCertEngs);

				//资质资格信息
//				List < SupplierAptitute > supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
				List < SupplierAptitute > supplierAptitute = supplierMatEng.getListSupplierAptitutes();
				for(int i = 0; i < supplierAptitute.size() - 1; i++) {
					for(int j = supplierAptitute.size() - 1; j > i; j--) {
						if(supplierAptitute.get(j).getId().equals(supplierAptitute.get(i).getId())) {
							supplierAptitute.remove(j);
						}
					}
				}
				request.setAttribute("supplierAptitutes", supplierAptitute);
				//资质类型
				request.setAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE,null, 4));
				//资质登记
				List < DictionaryData > businessList = DictionaryDataUtil.find(31);
				for(DictionaryData data : businessList){
					for(SupplierAptitute a : supplierAptitute){
						if(data.getId().equals(a.getAptituteLevel())){
							a.setAptituteLevel(data.getName());
						}
					}
				}

				//注册人员
				List < SupplierRegPerson > listSupplierRegPersons = supplierMatEng.getListSupplierRegPersons();
				request.setAttribute("listRegPerson", listSupplierRegPersons);
				
				//承揽业务范围
				String businessScope = supplierMatEng.getBusinessScope();
				if(StringUtils.isNotBlank(businessScope)){
//					SupplierDictionaryData dictionary = dictionaryDataServiceI.getSupplierDictionary();
//					String typeId =  dictionary.getSupplierProContract();
					List<Area> existenceArea = new ArrayList<>();
					String[] areaIds = businessScope.split(",");
					for(String areaId : areaIds){
//						String businessId = supplierId + "_" + areaId;
//						List<UploadFile> listUpload = uploadService.getFilesOther(businessId, typeId, "1");
//						if(!listUpload.isEmpty()){
							Area area = areaService.listById(areaId);
							existenceArea.add(area);
//						}
					}
					request.setAttribute("areas", existenceArea);
				}
			}
			
			/**
			 * 服务
			 */
			//组织结构和人员
//			supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
			supplierMatSe = supplier.getSupplierMatSe();
			if(supplierMatSe != null){
				//资质证书信息
//				List < SupplierCertServe > supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
				List < SupplierCertServe > supplierCertSe = supplierMatSe.getListSupplierCertSes();
				for(int i = 0; i < supplierCertSe.size() - 1; i++) {
					for(int j = supplierCertSe.size() - 1; j > i; j--) {
						if(supplierCertSe.get(j).getId().equals(supplierCertSe.get(i).getId())) {
							supplierCertSe.remove(j);
						}
					}
				}
				request.setAttribute("supplierCertSes", supplierCertSe);
				request.setAttribute("supplierMatSes", supplierMatSe);
			}
		}
		
		String loginName = user.getLoginName();
		
		/**
		 * 审核内容
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			List<SupplierAudit> auditList = null;
			
			//供应商类型==============================
			supplierAudit.setAuditType("supplierType_page");
//			auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			auditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditTypeField = new StringBuffer();
			StringBuffer unableTypeField = new StringBuffer();
			if(auditList != null && !auditList.isEmpty()){
				for(SupplierAudit a : auditList){
					auditTypeField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableTypeField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditTypeField", auditTypeField);
			request.setAttribute("unableTypeField", unableTypeField);
			
			//物资生产==============================
			supplierAudit.setAuditType("mat_pro_page");
//			auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			auditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditProField = new StringBuffer();
			StringBuffer unableProField = new StringBuffer();
			if(auditList != null && !auditList.isEmpty()){
				for(SupplierAudit a : auditList){
					auditProField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableProField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditProField", auditProField);
			request.setAttribute("unableProField", unableProField);
			
			//物资销售==============================
			supplierAudit.setAuditType("mat_sell_page");
//			auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			auditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditSellField = new StringBuffer();
			StringBuffer unableSellField = new StringBuffer();
			if(auditList != null && !auditList.isEmpty()){
				for(SupplierAudit a : auditList){
					auditSellField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableSellField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditSellField", auditSellField);
			request.setAttribute("unableSellField", unableSellField);
			
			//工程==============================
			supplierAudit.setAuditType("mat_eng_page");
//			auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			auditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditEngField = new StringBuffer();
			StringBuffer unableEngField = new StringBuffer();
			if(auditList != null && !auditList.isEmpty()){
				for(SupplierAudit a : auditList){
					auditEngField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableEngField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditEngField", auditEngField);
			request.setAttribute("unableEngField", unableEngField);
			
			//服务==============================
			supplierAudit.setAuditType("mat_serve_page");
//			auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			auditList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditServeField = new StringBuffer();
			StringBuffer unableServeField = new StringBuffer();
			if(auditList != null && !auditList.isEmpty()){
				for(SupplierAudit a : auditList){
					auditServeField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableServeField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditServeField", auditServeField);
			request.setAttribute("unableServeField", unableServeField);
		}
		
		/**
		 * 退回修改的内容
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			List<SupplierModify> modifyList = null;
			
			//供应商类型==============================
			supplierModify.setModifyType("supplier_type");
			StringBuffer fieldType = new StringBuffer();
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			for(SupplierModify m : modifyList){
				fieldType.append(m.getBeforeField() + ",");
			}
			request.setAttribute("fieldType", fieldType);
			
			//物资生产==============================
			supplierModify.setModifyType("mat_pro_page");
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			//产品研发能力...
			StringBuffer fieldProOne = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getBeforeField();
				fieldProOne.append(beforeField + ",");
			}
			request.setAttribute("fieldProOne", fieldProOne);
			//资质证书
			StringBuffer fieldProTwo = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldProTwo.append(beforeField + ",");
			}
			request.setAttribute("fieldProTwo", fieldProTwo);
			
			//物资销售==============================
			supplierModify.setModifyType("mat_sell_page");
			supplierModify.setListType(6);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldSell = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldSell.append(beforeField + ",");
			}
			request.setAttribute("fieldSell", fieldSell);
			
			//工程==============================
			supplierModify.setModifyType("mat_eng_page");
			//保密工程业绩...
			supplierModify.setListType(5);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldSecrecy = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldSecrecy.append(beforeField + ",");
			}
			request.setAttribute("fieldSecrecy", fieldSecrecy);
			//资质证书
			supplierModify.setListType(13);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldEngQuas = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldEngQuas.append(beforeField + ",");
			}
			request.setAttribute("fieldEngQuas", fieldEngQuas);
			//注册人员
			supplierModify.setListType(7);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldRegPersons = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldRegPersons.append(beforeField + ",");
			}
			request.setAttribute("fieldRegPersons", fieldRegPersons);
			//资质（认证）证书
			supplierModify.setListType(8);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldCertEngs = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldCertEngs.append(beforeField + ",");
			}
			request.setAttribute("fieldCertEngs", fieldCertEngs);
			//证书详情
			supplierModify.setListType(9);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldAptitutes = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldAptitutes.append(beforeField + ",");
			}
			request.setAttribute("fieldAptitutes", fieldAptitutes);
			
			//服务==============================
			supplierModify.setModifyType("mat_serve_page");
			supplierModify.setListType(10);
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldServe = new StringBuffer();
			for(int i = 0; i < modifyList.size(); i++) {
				String beforeField = modifyList.get(i).getRelationId() +"_"+ modifyList.get(i).getBeforeField();
				fieldServe.append(beforeField + ",");
			}
			request.setAttribute("fieldServe", fieldServe);
			
			//附件==============================
			supplierModify.setModifyType("file");
			supplierModify.setListType(null);
			StringBuffer fileModifyField = new StringBuffer();
			modifyList = supplierModifyService.selectBySupplierId(supplierModify);
			for(SupplierModify m : modifyList){
				if(m.getRelationId() != null && m.getBeforeField() != null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + "," );
				}
				if(m.getRelationId() == null && m.getBeforeField() != null){
					fileModifyField.append(m.getBeforeField() + "," );
				}
			}
			request.setAttribute("fileModifyField", fileModifyField);
		}
		
		return "ses/sms/supplier_audit/supplierType";
	}

	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:55:44  
	 * @Description: 记录审核原因
	 * @param @param supplierAudit      
	 * @return void
	 * @throws IOException 
	 */

	@RequestMapping("auditReasons")
	@ResponseBody
	public JdcgResult auditReasons(SupplierAudit supplierAudit, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String supplierId = supplierAudit.getSupplierId();
//		Supplier supplier = supplierAuditService.supplierById(id);
		Supplier supplier = supplierService.selectById(supplierId);
		
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(501, "对不起，您没有登录或登录失效！", null);
		}
		// 校验审核人权限
		User user = (User) loginUserSession;
		/*if(supplier.getAuditor() != null && !supplier.getAuditor().equals(user.getRelName())){
			return new JdcgResult(501, "对不起，您没有审核权限！", null);
		}*/
		
		String suggest = supplierAudit.getSuggest().trim();
		if(suggest.length() > 900){
			return new JdcgResult(504, "审核内容长度过长", null);
		}
		// 唯一检验
		// 退回修改/审核不通过/未修改 的记录不能再次审核
		//int auditCount = supplierAuditService.countAuditRecords(supplierAudit, new Integer[]{1,2,4});
		supplierAudit.setIsDeleted(1);// 历史审核记录
		int auditCount = supplierAuditService.countAuditRecords(supplierAudit, new Integer[]{0,2,4});
		if(auditCount == 0) {
			String auditFlag = "add";
			supplierAudit.setIsDeleted(0);
			List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,1,2});
			if(auditList != null && auditList.size() > 0){
				supplierAudit.setId(auditList.get(0).getId());
				auditFlag = "update";
			}
			supplierAudit.setStatus(supplier.getStatus());
			supplierAudit.setCreatedAt(new Date());
			supplierAudit.setUserId(user.getId());
			int i=supplierAuditService.auditReasons(supplierAudit);
			if(i>0){
				// 审核完后更新审核人/审核状态
				updateSupplierAuditStatus(user, supplier);
				return new JdcgResult(500, "审核成功", auditFlag);
			}else{
				return new JdcgResult(502, "审核失败", null);
			}
		} else {
			return new JdcgResult(503, "已审核", null);
		}
	}
	
	/**
	 * 
	 * Description:批量 插入审核
	 * 
	 * @author YangHongLiang
	 * @version 2017-7-21
	 * @param supplierAuditList
	 * @param request
	 * @param supplier
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("auditReasonsMulti")
	@ResponseBody
	public JdcgResult auditReasonsMulti(@RequestBody List<SupplierAudit> supplierAuditList) {
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			return new JdcgResult(501, "登录超时", null);
		}
		String supplierId = supplierAuditList.get(0).getSupplierId();
		Supplier supplier = supplierService.selectById(supplierId);
		/*if(supplier.getAuditor() != null && !supplier.getAuditor().equals(user.getRelName())){
			return new JdcgResult(501, "对不起，您没有审核权限！", null);
		}*/
		if(null != supplierAuditList && !supplierAuditList.isEmpty()){
			String suggest=supplierAuditList.get(0).getSuggest().trim();
			if(suggest.length()>900){
				return new JdcgResult(504, "审核内容长度过长", null);
			}
			SupplierAudit audit=new SupplierAudit();
			audit.setAuditType(supplierAuditList.get(0).getAuditType());
			audit.setSupplierId(supplierAuditList.get(0).getSupplierId());
//			List<SupplierAudit> alist=supplierAuditService.findByTypeId(audit);
			audit.setIsDeleted(1);
			List<SupplierAudit> alist=supplierAuditService.getAuditRecords(audit, new Integer[]{0,2});
			alist.retainAll(supplierAuditList);
			if(null != alist && !alist.isEmpty()){
				return new JdcgResult(503, "选择中存在已审核，不可重复审核", null);
			}else{
				String auditFlag = "add";
				Date date = new Date();
				for (SupplierAudit audit2 : supplierAuditList) {
					audit2.setIsDeleted(0);
					List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(audit2, new Integer[]{0,2});
					if(auditList != null && auditList.size() > 0){
						audit2.setId(auditList.get(0).getId());
						auditFlag = "update";
					}
					audit2.setStatus(supplier.getStatus());
					audit2.setCreatedAt(date);
					audit2.setUserId(user.getId());
				}
				int i=supplierAuditService.saveAudit(supplierAuditList);
				if(i>0){
					// 审核完后更新审核人/审核状态
					updateSupplierAuditStatus(user, supplier);
					return new JdcgResult(500, "审核成功", auditFlag);
				}else{
					return new JdcgResult(502, "审核失败", null);
				}
			}
		}else{
			return new JdcgResult(504, "参数错误", null);
		}
	}
	
	/**
	 * 批量审核合同
	 * @param supplierId
	 * @param supplierTypeId
	 * @param suggest
	 * @param itemIds
	 * @return
	 */
	@RequestMapping("auditContractMuti")
	@ResponseBody
	public JdcgResult auditContractMuti(String supplierId, String supplierTypeId, String suggest, String itemIds){
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			return new JdcgResult(501, "登录超时", null);
		}
		Supplier supplier = supplierService.selectById(supplierId);
		/*if(supplier.getAuditor() != null && !supplier.getAuditor().equals(user.getRelName())){
			return new JdcgResult(501, "对不起，您没有审核权限！", null);
		}*/
		if(StringUtils.isBlank(supplierId) || StringUtils.isBlank(supplierTypeId) || StringUtils.isBlank(itemIds)){
			return new JdcgResult(504, "参数错误", null);
		}
		if(StringUtils.isBlank(suggest)){
			return new JdcgResult(504, "审核内容不能为空", null);
		}
		if(suggest.trim().length() > 900){
			return new JdcgResult(504, "审核内容长度过长", null);
		}
		JdcgResult result = supplierAuditService.auditContractMuti(user.getId(), supplierId, supplierTypeId, suggest, itemIds);
		if(result != null && result.getStatus() != null && result.getStatus() == 500){
			// 审核完后更新审核人/审核状态
			updateSupplierAuditStatus(user, supplier);
		}
		return result;
	}
	
	/**
	 * 更新审核人/审核状态
	 * @param user
	 * @param supplier
	 * @return
	 */
	private int updateSupplierAuditStatus(User user, Supplier supplier){
		Supplier updateSupplier = new Supplier();
		Date nowDate = new Date();
		updateSupplier.setId(supplier.getId());
		updateSupplier.setAuditDate(nowDate);
		updateSupplier.setUpdatedAt(nowDate);
		Integer auditTemporary = 0;
		Integer status = supplier.getStatus();
		if(status != null && (status == 0 || status == 9)){
			auditTemporary = 1;// 审核中
		}
		updateSupplier.setAuditTemporary(auditTemporary);
		if(StringUtils.isBlank(supplier.getAuditor())){
			updateSupplier.setAuditor(user.getRelName());
		}
		if(SupplierConstants.isStatusToAudit(status)){
			return supplierAuditService.updateStatus(updateSupplier);
		}
		return 0;
	}
	/*private int updateSupplierAuditStatus(User user, Supplier supplier){
		if(StringUtils.isBlank(supplier.getAuditor())){
			Supplier updateSupplier = new Supplier();
			Date nowDate = new Date();
			updateSupplier.setId(supplier.getId());
			updateSupplier.setAuditor(user.getRelName());
			updateSupplier.setAuditDate(nowDate);
			updateSupplier.setUpdatedAt(nowDate);
			Integer auditTemporary = 0;
			Integer status = supplier.getStatus();
			if(status != null && (status == 0 || status == 9)){
				auditTemporary = 1;// 审核中
			}
			updateSupplier.setAuditTemporary(auditTemporary);
			return supplierAuditService.updateStatus(updateSupplier);
		}else{
			//if(supplier.getAuditor().equals(user.getRelName()) && (supplier.getAuditTemporary() == null || supplier.getAuditTemporary() == 0)){
			if(supplier.getAuditTemporary() == null || supplier.getAuditTemporary() == 0){
				Supplier updateSupplier = new Supplier();
				Date nowDate = new Date();
				updateSupplier.setId(supplier.getId());
				updateSupplier.setAuditDate(nowDate);
				updateSupplier.setUpdatedAt(nowDate);
				Integer auditTemporary = 0;
				Integer status = supplier.getStatus();
				if(status != null && (status == 0 || status == 9)){
					auditTemporary = 1;// 审核中
				}
				updateSupplier.setAuditTemporary(auditTemporary);
				return supplierAuditService.updateStatus(updateSupplier);
			}
		}
		return 0;
	}*/
	
	/**
	 * @Title: reasonsList
	 * @author Xu Qing
	 * @date 2016-9-20 上午9:44:58  
	 * @Description: 审核问题汇总 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("reasonsList")
	public String reasonsList(Model model, HttpServletRequest request, SupplierAudit supplierAudit, Integer supplierStatus, Integer sign) {
		request.setAttribute("sign", sign);

		String supplierId = supplierAudit.getSupplierId();
		if(supplierId == null) {
			supplierId = (String) request.getSession().getAttribute("supplierId");
			supplierAudit.setSupplierId(supplierId);
		}
		//List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecordsWithSort(supplierAudit);
		request.setAttribute("reasonsList", reasonsList);
		
		int isAllTypeNotPass = 0;// 所有类型不通过
		int isAllItemNotPass = 0;// 类型下所有品目不通过
		
		JdcgResult vertifyResult = supplierAuditService.getTypeAndItemNotPass(supplierId);
		if(vertifyResult != null && vertifyResult.getStatus() == 1){
			isAllTypeNotPass = 1;
		}
		if(vertifyResult != null && vertifyResult.getStatus() == 2){
			isAllItemNotPass = 1;
		}

		//所有类型不通过
		request.setAttribute("isAllTypeNotPass", isAllTypeNotPass);
		request.setAttribute("isAllItemNotPass", isAllItemNotPass);
		
		//有信息就不让通过
		request.setAttribute("num", reasonsList.size());
		/*//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);*/

//		Supplier supplier = supplierAuditService.supplierById(supplierId);
		Supplier supplier = supplierService.selectById(supplierId);
		if(supplier != null){
			request.setAttribute("supplierStatus", supplier.getStatus());
		}

		// 查询最终审核意见
		SupplierAuditOpinion supplierAuditOpinion;
		if(sign == 1){
			// 初审意见
            supplierAuditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 0);
        }else {
			// 复审和复查意见
			supplierAuditOpinion = supplierAuditOpinionService.selectByExpertId(supplierId);
		}

		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.setAttribute("supplier", supplier);

		request.setAttribute("supplierId", supplierId);
		request.getSession().removeAttribute("supplierId");
		/*String opinion = "";
		if(supplierAuditOpinion != null){
			opinion = supplierAuditOpinion.getOpinion();
		}*/
		model.addAttribute("supplierAuditOpinion", supplierAuditOpinion);
		// 供应商类型map
		Map<String, String> typeMap = new HashMap<String, String>();
		List<String> supplierTypeList = supplierTypeRelateService.findTypeBySupplierId(supplierId);
		if(supplierTypeList != null){
			for(String supplierType : supplierTypeList){
				DictionaryData dd = DictionaryDataUtil.get(supplierType);
				typeMap.put(dd.getId(), supplierType);
			}
		}
		model.addAttribute("typeMap", typeMap);
		return "ses/sms/supplier_audit/audit_reasons";
	}

	@RequestMapping("showReasonsList")
	public String showReasonsList(HttpServletResponse reponse, Model model, SupplierAudit supplierAudit, String jsp) {
		List < SupplierAudit > list = supplierAuditService.findReason(supplierAudit);
		model.addAttribute("list", list);
		// super.writeJson(reponse, reasonsList);
		return "ses/sms/supplier_register/" + jsp;
	}

	/**
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-20 下午7:32:49  
	 * @Description: 根据供应商id更新审核状态
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("updateStatus")
	public String updateStatus(@CurrentUser User user, HttpServletRequest request, Supplier supplier, SupplierAudit supplierAudit, SupplierAuditOpinion supplierAuditOpinion) throws IOException {
		String supplierId = supplierAudit.getSupplierId();
		/*User user = (User) request.getSession().getAttribute("loginUser");*/
//		supplier = supplierAuditService.supplierById(supplierId);
		Supplier persistentSupplier = supplierService.selectById(supplierId);
		String supplierName = persistentSupplier.getSupplierName();
		
		// 更新状态
		Supplier updateSupplier = new Supplier();
		updateSupplier.setId(supplierId);
		// 取消审核暂存状态
		updateSupplier.setAuditTemporary(0);
		// 设置修改时间
		Date nowDate = new Date();
		updateSupplier.setUpdatedAt(nowDate);
		updateSupplier.setAuditDate(nowDate);
		if(StringUtils.isBlank(persistentSupplier.getAuditor())){
			// 审核人
			updateSupplier.setAuditor(user.getRelName());
			// 审核时间
			updateSupplier.setAuditDate(nowDate);
		}
		// 更新状态
		updateSupplier.setStatus(supplier.getStatus());
		supplierAuditService.updateStatus(updateSupplier);
		
		if(supplier.getStatus() != null && supplier.getStatus() == 1){
			// 供应商分级要素得分
	        supplier.setLevelScoreProduct(SupplierLevelUtil.getScore(supplier.getId(), "PRODUCT"));
	        supplier.setLevelScoreSales(SupplierLevelUtil.getScore(supplier.getId(), "SALES"));
	        supplier.setLevelScoreService(SupplierLevelUtil.getScore(supplier.getId(), "SERVICE"));
	        if(supplier.getProcurementDepId() != null){
	        	supplierService.updateSupplierProcurementDep(supplier);
	        }
		}
		
		// 持久化供应商信息
		supplier = supplierService.selectById(supplierId);
		
		//记录最终意见
		if(supplier != null && supplier.getStatus() != -2){
			supplierAuditOpinion.setOpinion(supplierAuditOpinion.getOpinion());
			supplierAuditOpinion.setSupplierId(supplierId);
			supplierAuditOpinion.setCreatedAt(new Date());
			supplierAuditOpinionService.insertSelective(supplierAuditOpinion);
		}

		//记录审核不通过的供应商
		if(supplier.getStatus() == 3){
			SupplierAuditNot supplierAuditNot = new SupplierAuditNot();
			supplierAuditNot.setCreditCode(supplier.getCreditCode());
			supplierAuditNot.setSupplierId(supplierId);
			supplierAuditNot.setCreatedAt(new Date());
			supplierAuditNotService.insertSelective(supplierAuditNot);
		}
		
		Todos todos = new Todos();
		/**
		 * 更新待办(已完成)
		 */
		if(supplier.getStatus() != null && supplier.getStatus() == -3 || supplier.getStatus() == 1 || supplier.getStatus() == 2 || supplier.getStatus() == 3 || supplier.getStatus() == 5 || supplier.getStatus() == 6 || supplier.getStatus() == 7 || supplier.getStatus() == 8) {
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId=" + supplierId);
		}

		/**
		 * 推送代办
		 */
		if(supplier.getStatus() == 5) {
			//推送者id
			todos.setSenderId(user.getId());
			//待办名称
			todos.setName("【" + supplierName + "】" + "供应商考察！");
			//机构id
			todos.setOrgId(supplier.getProcurementDepId());
			//权限id
			todos.setPowerId(PropUtil.getProperty("gyscs"));
			//url
			todos.setUrl("supplierAudit/essential.html?supplierId=" + supplierId);
			//类型
			todos.setUndoType((short) 1);

			todosService.insert(todos);
		}

		/*if(supplier.getStatus() == 1){
			todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商复核");
			todosService.updateByUrl(todos);
			
			//待办已完成
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId=" + supplierId);
			
			*/
		/**
		 * 推送代办
		 */
		/*
					//推送者id
					todos.setSenderId(user.getId());
					//待办名称
					todos.setName(supplierName+"供应商复核");
					//机构id
					todos.setOrgId(supplier.getProcurementDepId());
					//权限id
					todos.setPowerId(PropUtil.getProperty("gsyfs"));
					//url
					todos.setUrl("supplierAudit/essential.html?supplierId=" + supplierId);
					//类型
					todos.setUndoType((short) 1);
					
					todosService.insert(todos);
				}
				if(supplier.getStatus() == 2 || supplier.getStatus() == 3 || supplier.getStatus() == 4 ){
					// 待办已完成
					todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
					
					todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
					todos.setName("供应商审核");
					todosService.updateByUrl(todos);
				}
				if(supplier.getStatus() == 4 || supplier.getStatus() == 3){
					todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
					
					todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
					todos.setName("供应商复核");
					todosService.updateByUrl(todos);
				}
				
				if(supplier.getStatus() == 8){
					// 待办已完成
					todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
					*/
		/**
		 *  复核退回修改 ，推送代办
		 */
		/*
					//推送者id
					todos.setSenderId(user.getId());
					//待办名称
					todos.setName(supplierName+"供应商复核退回, 需审核 !");
					//机构id
					todos.setOrgId(supplier.getProcurementDepId());
					//权限id
					todos.setPowerId(PropUtil.getProperty("gyscs"));
					//url
					todos.setUrl("supplierAudit/essential.html?supplierId=" + supplierId);
					//类型
					todos.setUndoType((short) 1);
					todosService.insert(todos);
				}
				
				if(supplier.getStatus() == 7){
					// 待办已完成
					todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
					*/
		/**
		 * 审核退回修改 ，推送消息
		 */
		/*
					//推送者id
					todos.setSenderId(user.getId());
					//待办名字
					todos.setName(supplierName+"供应商信息有误,请修改！");
					
					List<User> receiverIdList= userServiceI.findByLoginName(supplier.getLoginName());
					if(receiverIdList.size()>0){
						String receiverId=  receiverIdList.get(0).getId();
						//接收用户id
						todos.setReceiverId(receiverId);
					}
					//url
					todos.setUrl("supplier/return_edit.html?id=" + supplierId);
					//类型
					todos.setUndoType((short) 1);
					todosService.insert(todos);
				}*/

		//审核完更新状态
		int countAudit = supplierAuditService.countAuditRecords(supplierAudit, null);
		if(countAudit != 0) {
			supplierAudit.setStatus(supplier.getStatus());
			supplierAudit.setSupplierId(supplierId);
			supplierAuditService.updateStatusById(supplierAudit);
		}

		if (supplier.getStatus() == 2) {
			// 删除之前的历史记录
			SupplierHistory supplierHistory = new SupplierHistory();
			supplierHistory.setSupplierId(supplierId);
			/*supplierHistoryService.delete(supplierHistory);*/
			supplierHistoryService.updateIsDeleteBySupplierId(supplierHistory);
			
			// 新增历史记录
		    supplierHistoryService.insertHistoryInfo(supplierId);
			
			//删除该供应商对比后的数据
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			/*supplierModifyService.delete(supplierModify);*/
			supplierModifyService.updateIsDeleteBySupplierId(supplierModify);
			
			
		}
		return "redirect:supplierAll.html";
	}

	/**
	 * 
	 * Description:公示操作
	 * 
	 * @author Easong
	 * @version 2017年6月26日
	 * @return
	 */
	@RequestMapping("/publicity")
	@ResponseBody
	public JdcgResult publicity(String ids[]){
		return supplierAuditService.updatePublicityStatus(ids);
	}
	
	/**
	 * @Title: temporaryAudit
	 * @date 2016-10-28 下午3:29:51  
	 * @Description: 暂存审核
	 * @param @param request
	 * @param @param supplier
	 * @param @param supplierAudit
	 * @return void
	 */
	@RequestMapping(value = "/temporaryAudit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String temporaryAudit(Model model, String supplierId){
		Supplier supplierInfo = supplierAuditService.supplierById(supplierId);
		Integer status = supplierInfo.getStatus();
		Supplier supplier = new Supplier();
		supplier.setId(supplierId);
		if(status == 0 || status == 9){
			//1：审核中
			supplier.setAuditTemporary(1);
		}else if(status == 1){
			//2：复核中
			supplier.setAuditTemporary(2);
		}else if(status == 5){
			//3：考察中
			supplier.setAuditTemporary(3);
		}else{
			return JSON.toJSONString("暂存失败");
		}
		int result = supplierAuditService.updateStatus(supplier);
		if(result > 0){
			return JSON.toJSONString("暂存成功");
		}
		return JSON.toJSONString("暂存失败");
	}

	/**
	 * @Title: setExpertBlackListUpload
	 * @author Xu Qing
	 * @date 2016-9-29 下午3:22:13  
	 * @Description:附件上传
	 * @param @param request
	 * @param @param expertBlackList
	 * @param @throws IOException      
	 * @return void
	 */
	public void setSuppliertUpload(HttpServletRequest request, SupplierAudit supplierAudit) throws IOException {
		Supplier supplier = new Supplier();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		if(multipartResolver.isMultipart(request)) { // 检查form中是否有enctype="multipart/form-data"
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request; // 将request变成多部分request
			Iterator < String > its = multiRequest.getFileNames(); // 获取multiRequest 中所有的文件名
			while(its.hasNext()) { // 循环遍历
				String str = its.next();
				MultipartFile file = multiRequest.getFile(str);
				String fileName = file.getOriginalFilename();
				if(file != null && file.getSize() > 0) {
					String path = super.getStashPath(request) + fileName; // 获取暂存路径
					file.transferTo(new File(path)); // 暂存
					FtpUtil.connectFtp(PropUtil.getProperty("file.upload.path.supplier")); // 连接 ftp 服务器
					String newfileName = FtpUtil.upload(new File(path)); // 上传到 ftp 服务器, 获取新的文件名
					FtpUtil.closeFtp(); // 关闭 ftp
					super.removeStash(request, fileName); // 移除暂存

					// 上面代码固定, 下面封装名字到对象
					if(str.equals("supplierInspectListFile")) {
						String id = supplierAudit.getSupplierId();
						supplier.setSupplierInspectList(newfileName);
						supplier.setId(id);
						supplierAuditService.updateSupplierInspectListById(supplier);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @Title: supplierInspectListFile
	 * @author Xu Qing
	 * @date 2016-9-29 下午3:30:01  
	 * @Description: 供应商考察附件上传
	 * @param @param request
	 * @param @param supplierAudit
	 * @param @throws IOException      
	 * @return void
	 */
	@RequestMapping("supplierFile")
	public String supplierInspectListFile(HttpServletRequest request, SupplierAudit supplierAudit) throws IOException {
		String supplierId = supplierAudit.getSupplierId();
		request.getSession().setAttribute("supplierId", supplierId);
		this.setSuppliertUpload(request, supplierAudit);
		return "redirect:reasonsList.html";
	}

	/**
	 * @Title: applicationForm
	 * @author Xu Qing
	 * @date 2016-9-29 下午7:12:37  
	 * @Description: 申请表
	 * @param @param request
	 * @param @param supplierAudit
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("applicationForm")
	public String applicationForm(@CurrentUser User user, HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier, Integer supplierStatus, Integer sign) throws IOException {
		request.setAttribute("sign", sign);
		
		String supplierId = supplierAudit.getSupplierId();
//		supplier = supplierAuditService.supplierById(supplierId);
		supplier = supplierService.selectById(supplierId);
		request.setAttribute("applicationForm", supplier);
		request.setAttribute("supplierId", supplierId);
		//文件
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		request.setAttribute("supplierStatus", supplierStatus);
		
		String loginName = user.getLoginName();
		
		//回显未通过字段
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("download_page");
//			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditField = new StringBuffer();
			StringBuffer unableField = new StringBuffer();
			if(reasonsList != null && !reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					auditField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableField.append(a.getAuditField() + ",");
					}
				}
			}
			request.setAttribute("auditField", auditField);
			request.setAttribute("unableField", unableField);
		}
		
		/**
		 * 退回修改后的附件
		 */
		if(SupplierConstants.isAudit(loginName, supplierStatus)){
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			if(!fileModify.isEmpty()){
				for(SupplierModify m : fileModify){
					fileModifyField.append(m.getBeforeField() + ",");
				}
				request.setAttribute("fileModifyField", fileModifyField);
			}
		}
		return "ses/sms/supplier_audit/application_form";
	}

	/**
	 * @Title: itemInformation
	 * @author Xu Qing
	 * @date 2016-10-8 上午10:34:24  
	 * @Description:品目信息
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("items")
	public String itemInformation(HttpServletResponse response, HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier, SupplierItem supplierItem, Integer pageNum , Integer supplierStatus) throws IOException {
		String supplierId = supplierAudit.getSupplierId();
		request.setAttribute("supplierStatus", supplierStatus);
		
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);

		supplier = supplierService.get(supplierId, 3);
		request.setAttribute("currSupplier", supplier);
		
		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("服务")) {
			lastUrl = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else if(supplierTypeName.contains("工程") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("销售") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("生产") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else {
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);

		return "ses/sms/supplier_audit/items";
	}
	/**
	 *〈简述〉异步获取所有已选中的节点
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierItem
	 * @return
	 */
	@RequestMapping("/getCategories")
	public String getCategoryList(SupplierItem supplierItem, Model model, Integer pageNum) {
		// 查询已选中的节点信息
		List < SupplierItem > listSupplierItems = supplierItemService.findCategoryList(supplierItem.getSupplierId(), supplierItem.getSupplierTypeRelateId(), pageNum == null ? 1 : pageNum);
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: listSupplierItems) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				allTreeList.add(cateTree);
			}
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(supplierItem.getSupplierTypeRelateId().equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierItem.getSupplierTypeRelateId().equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		model.addAttribute("supplierId", supplierItem.getSupplierId());
		model.addAttribute("supplierTypeRelateId", supplierItem.getSupplierTypeRelateId());
		model.addAttribute("result", new PageInfo < > (listSupplierItems));
		model.addAttribute("itemsList", allTreeList);
		
		//考察报告
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		
		//回显未通过字段
		SupplierAudit supplierAudit = new SupplierAudit();
		supplierAudit.setSupplierId(supplierItem.getSupplierId());
		supplierAudit.setAuditType("items_page");
		List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		StringBuffer passedField = new StringBuffer();
		for(SupplierAudit a : reasonsList){
			passedField.append(a.getAuditField() + ",");
		}
		model.addAttribute("passedField", passedField);
		return "ses/sms/supplier_audit/ajax_items";
	}
	
	
	/**
	 *〈简述〉查询品目信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 产品Id
	 * @return List<CategoryTree> tree对象List
	 */
	public SupplierCateTree getTreeListByCategoryId(String categoryId, SupplierItem item) {
		SupplierCateTree cateTree = new SupplierCateTree();
		// 递归获取所有父节点
		List < Category > parentNodeList = getAllParentNode(categoryId);
		// 加入根节点
		for(int i = 0; i < parentNodeList.size(); i++) {
			DictionaryData rootNode = DictionaryDataUtil.findById(parentNodeList.get(i).getId());
			if(rootNode != null) {
				cateTree.setRootNode(rootNode.getName());
			}
		}
		// 加入一级节点
		if(cateTree.getRootNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					DictionaryData rootNode = DictionaryDataUtil.findById(cate.getParentId());
					if(rootNode != null && cateTree.getRootNode().equals(rootNode.getName())) {
						cateTree.setFirstNode(cate.getName());
					}
				}
			}
		}
		// 加入二级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getFirstNode().equals(parentNode.getName())) {
						cateTree.setSecondNode(cate.getName());
					}
				}
			}
		}
		// 加入三级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getSecondNode().equals(parentNode.getName())) {
						cateTree.setThirdNode(cate.getName());
					}
				}
			}
		}
		// 加入末级节点
		if(cateTree.getRootNode() != null && cateTree.getFirstNode() != null && cateTree.getSecondNode() != null && cateTree.getThirdNode() != null) {
			for(int i = 0; i < parentNodeList.size(); i++) {
				Category cate = categoryService.findById(parentNodeList.get(i).getId());
				if(cate != null && cate.getParentId() != null) {
					Category parentNode = categoryService.findById(cate.getParentId());
					if(parentNode != null && cateTree.getThirdNode().equals(parentNode.getName())) {
						cateTree.setFourthNode(cate.getName());
					}
				}
			}
		}

		// 工程类等级
		if(item != null) {
			// 等级
			if(item != null && item.getLevel() != null) {
				DictionaryData data = DictionaryDataUtil.findById(item.getLevel());
				if(data!=null){
					cateTree.setLevel(data);
				}else{
					List<SupplierPorjectQua> projectData = supplierPorjectQuaService.queryByNameAndSupplierId(item.getQualificationType(), item.getSupplierId());
					   if(projectData!=null&&projectData.size()>0){
				        	DictionaryData dd=new DictionaryData();
				        	dd.setId(projectData.get(0).getCertLevel());
				        	dd.setName(projectData.get(0).getCertLevel());
				        	cateTree.setLevel(dd); 
				        }
					   
				}
				
			}
			// 证书编号
			if(item != null && item.getCertCode() != null) {
				cateTree.setCertCode(item.getCertCode());
			}
			// 资质等级
			if(item != null && item.getQualificationType() != null) {
				cateTree.setQualificationType(item.getQualificationType());
			}
			if(item != null && item.getProfessType()!= null) {
				cateTree.setProName(item.getProfessType());
			}
			
			
			// 所有等级List
			List < Category > cateList = new ArrayList < Category > ();
			cateList.add(categoryService.selectByPrimaryKey(categoryId));
			List < QualificationBean > type = supplierService.queryCategoyrId(cateList, 4);
			List < Qualification > typeList = new ArrayList < Qualification > ();
			if(type != null && type.size() > 0 && type.get(0).getList() != null && type.get(0).getList().size() > 0) {
				typeList = type.get(0).getList();
			}
			List<SupplierPorjectQua> supplierQua = supplierPorjectQuaService.queryByNameAndSupplierId(null, item.getSupplierId());
			for(SupplierPorjectQua qua:supplierQua){
				Qualification q=new Qualification();
				q.setId(qua.getName());
				q.setName(qua.getName());
				typeList.add(q);
			}
			
			cateTree.setTypeList(typeList);
		}
		return cateTree;
	}
	
	/**
	 *〈简述〉获取当前节点的所有父级节点(包括根节点)
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param categoryId 
	 * @return
	 */
	public List < Category > getAllParentNode(String categoryId) {
		List < Category > categoryList = new ArrayList < Category > ();
		while(true) {
			Category cate = categoryService.findById(categoryId);
			if(cate == null) {
				DictionaryData root = DictionaryDataUtil.findById(categoryId);
				Category rootNode = new Category();
				rootNode.setId(root.getId());
				rootNode.setName(root.getName());
				categoryList.add(rootNode);
				break;
			} else {
				categoryList.add(cate);
				categoryId = cate.getParentId();
			}
		}
		return categoryList;
	}
	
	@RequestMapping(value = "/category_type", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCategory(String id, String name, String code, String supplierId) {
		List < CategoryTree > categoryList = new ArrayList < CategoryTree > ();
		if(code != null) {
			DictionaryData type = DictionaryDataUtil.get(code);
			CategoryTree ct = new CategoryTree();
			ct.setName(type.getName());
			ct.setId(type.getId());
			ct.setIsParent("true");
			categoryList.add(ct);

			List < SupplierItem > item = supplierItemService.getSupplierId(supplierId);

			for(SupplierItem category: item) {
				String parentId = categoryService.selectByPrimaryKey(category.getCategoryId()).getParentId();
				if(parentId != null && parentId.equals(ct.getId())) {
					ct.setChecked(true);
				}
			}
			List < Category > child = getChild(type.getId());
			for(Category c: child) {
				CategoryTree ct1 = new CategoryTree();
				ct1.setName(c.getName());
				ct1.setParentId(c.getParentId());
				ct1.setId(c.getId());
				// 设置是否为父级
				if(!child.isEmpty()) {
					ct1.setIsParent("true");
				} else {
					ct1.setIsParent("false");
				}
				//                ct1.set
				//                ct1.set  
				//                }

				// 设置是否回显
				for(SupplierItem category: item) {
					if(category.getCategoryId() != null) {
						if(category.getCategoryId().equals(c.getId())) {
							ct1.setChecked(true);
						}
					}
				}
				categoryList.add(ct1);
			}
		}
		return JSON.toJSONString(categoryList);
	}

	public List < Category > getChild(String id) {
		List < Category > list = categoryService.findTreeByPid(id);
		List < Category > childList = new ArrayList < Category > ();
		childList.addAll(list);
		for(Category cate: list) {
			childList.addAll(getChild(cate.getId()));
		}
		return list;
	}

	/**
	 * @Title: productInformation
	 * @author Xu Qing
	 * @date 2016-10-8 下午1:53:27  
	 * @Description:产品信息
	 * @param @param request
	 * @param @param supplierAudit
	 * @param @param supplier
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("product")
	public String productInformation(HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier) {
		String supplierId = supplierAudit.getSupplierId();
		request.setAttribute("supplierId", supplierId);
		/*
		if(supplierId != null){
			List<SupplierItem> listItem= supplierService.get(supplierId).getListSupplierItems();
			request.setAttribute("listItem", listItem);
		}*/
		supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);

		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("服务")) {
			lastUrl = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		} else if(supplierTypeName.contains("工程") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		} else if(supplierTypeName.contains("销售") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		} else if(supplierTypeName.contains("生产") && lastUrl == null) {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		} else {
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}
		request.setAttribute("lastUrl", lastUrl);

		return "ses/sms/supplier_audit/product";
	}

	/**
	 * @Title: download
	 * @author Xu Qing
	 * @date 2016-10-8 下午14:57:27  
	 * @Description:文件下載  
	 * @return String
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
	 * @Title: supplierAll
	 * @author Xu Qing
	 * @date 2016-10-21 上午9:45:39  
	 * @Description: 全部供应商
	 * @param @param request
	 * @param @param supplier
	 * @param @param page
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "supplierAll")
	public String supplierAll(@CurrentUser User user, Model model, HttpServletRequest request, Supplier supplier, Integer page) {
		if(supplier.getSign() == null) {
			Integer sign = (Integer) request.getSession().getAttribute("signs");
			supplier.setSign(sign);
			request.getSession().removeAttribute("signs");
		}
		
		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}

		//获取登录人机构,1代表采购机构
		Orgnization org = null;
		if(null!=user){
			org = user.getOrg();
		}
		if(user != null && org != null && "1".equals(org.getTypeName()) && supplier.getSign() != null && supplier.getSign() == 1){
			//判断用户是否登陆，本部门内查询初审，
			PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(org.getId());//查询当前部门
			if(dep != null){
				supplier.setProcurementDepId(dep.getId());
				//抽取时的机构
				supplier.setExtractOrgid(dep.getId());
			}else{
				supplier.setProcurementDepId("");
				supplier.setExtractOrgid("");
			}
		}else if(user != null && org != null && "1".equals(org.getTypeName()) && supplier.getSign() != null && (supplier.getSign() == 2 || supplier.getSign() == 3)){
			//用户是否登陆  在所有部门查询，复审   因为ExtractOrgid初始为null，为防止注入可以手动
			supplier.setProcurementDepId(null);
			supplier.setExtractOrgid(null);
		}else{
			supplier.setProcurementDepId("");
			supplier.setExtractOrgid("");
		}

		Integer state = supplier.getStatus();
		// 审核暂存的状态
		if(supplier.getStatus() != null && supplier.getSign() != null){
			if(supplier.getSign() == 1){
				if(supplier.getStatus() == 100){
					supplier.setAuditTemporary(1);
					supplier.setStatus(null);
				}else{
					supplier.setAuditTemporary(0);
				}
			}
			if(supplier.getSign() == 2){
				if(supplier.getStatus() == 200){
					supplier.setAuditTemporary(2);
					supplier.setStatus(null);
				}else{
					supplier.setAuditTemporary(0);
				}
			}
			if(supplier.getSign() == 3){
				if(supplier.getStatus() == 300){
					supplier.setAuditTemporary(3);
					supplier.setStatus(null);
				}else{
					supplier.setAuditTemporary(0);
				}
			}
		}
		
		//回显
		request.setAttribute("supplierName", supplier.getSupplierName());
		request.setAttribute("state", state);
		request.setAttribute("businessNature", supplier.getBusinessNature());
		request.setAttribute("auditDate", supplier.getAuditDate());
		request.setAttribute("addressName", supplier.getAddressName());
		//审核、复核、实地考察的标识
		request.setAttribute("sign", supplier.getSign());
		request.getSession().setAttribute("signs", supplier.getSign());
		
		if(user != null && SupplierConstants.isAccountToAudit(user.getLoginName())){
			request.setAttribute("isAccountToAudit", 1);
			supplier.setProcurementDepId(null);
			supplier.setExtractOrgid(null);
			if("GET".equals(request.getMethod()) && state != null && state == 0){
				state = 2;
				request.setAttribute("state", state);
				supplier.setStatus(2);
			}
			if(state != null && state != 2){// 只返回退回修改的数据
				request.setAttribute("result", new PageInfo < Supplier > ());
				return "ses/sms/supplier_audit/supplier_all";
			}else{
				supplier.setStatus(2);
			}
		}
		
		//查询列表
		List < Supplier > supplierList = supplierAuditService.getAuditSupplierList(supplier, page);
		
		//企业性质
		List < DictionaryData > businessNatureList = DictionaryDataUtil.find(32);
		request.setAttribute("businessNatureList", businessNatureList);
        for(Supplier s : supplierList){
        	if(s.getBusinessNature() !=null ){
        		for(int i = 0; i < businessNatureList.size(); i++) {
        			if(s.getBusinessNature().equals(businessNatureList.get(i).getId())) {
      					String business = businessNatureList.get(i).getName();
      					s.setBusinessNature(business);
      				}
        		}
        	}
        }
		
		PageInfo < Supplier > pageInfo = new PageInfo < Supplier > (supplierList);
		request.setAttribute("result", getSupplierType(pageInfo));

		/*//回显
		request.setAttribute("supplierName", supplier.getSupplierName());
		request.setAttribute("state", state);
		request.setAttribute("businessNature", supplier.getBusinessNature());
		request.setAttribute("auditDate", supplier.getAuditDate());
		request.setAttribute("addressName", supplier.getAddressName());
		//审核、复核、实地考察的标识
		request.setAttribute("sign", supplier.getSign());
		request.getSession().setAttribute("signs", supplier.getSign());*/

		return "ses/sms/supplier_audit/supplier_all";
	}
	
	/**
	 * 
	 * @Title: fileUploadItem
	 * @Description: 获取文件上传配置
	 * @author Easong
	 * @param @param model 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void fileUploadItem(Model model) {
		// 供应商系统key文件上传key
		Integer sysKey = Constant.SUPPLIER_SYS_KEY;
		// 定义文件上传类型
		DictionaryData dictionaryData = DictionaryDataUtil
				.get(synchro.util.Constant.SUPPLIER_CHECK_ATTACHMENT);
		if (dictionaryData != null) {
			model.addAttribute("typeId", dictionaryData.getId());
		}
		model.addAttribute("sysKey", sysKey);
	}

	/**
	 * 
	 *〈简述〉获取供应商的企业类型
	 *〈详细描述〉
	 * @author myc
	 * @param pageInfo
	 * @return
	 */
	private PageInfo < Supplier > getSupplierType(PageInfo < Supplier > pageInfo) {
		List < Supplier > supplierList = new ArrayList < > ();
		List < Supplier > list = pageInfo.getList();
		for(Supplier supplier: list) {
			List < SupplierTypeRelate > relaList = supplierTypeRelateService.queryBySupplier(supplier.getId());
			String typeName = "";
			for(SupplierTypeRelate str: relaList) {
				DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
				if(dd != null) {
					typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
				}
			}
			if(typeName.contains(StaticVariables.COMMA_SPLLIT)) {
				typeName = typeName.substring(0, typeName.length() - 1);
			}
			supplier.setSupplierTypeNames(typeName);
			supplierList.add(supplier);
		}
		pageInfo.setList(supplierList);
		return pageInfo;
	}

	@RequestMapping(value = "deleteById")
	public void deleteById(HttpServletResponse response, String[] ids) {
		boolean whether = supplierAuditService.deleteById(ids);
		if(whether) {
			String msg = "{'msg':'yes'}";
			writeJson(response, msg);
		}
	}

	/**
	 * @Title: showModify
	 * @author XuQing 
	 * @date 2016-12-28 上午10:37:47  
	 * @Description:查询退回修改再次提交审核显示之前的信息
	 * @param @param supplierHistory
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/showModify", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showModify(SupplierModify supplierModify, HttpServletRequest request) throws ParseException {
		supplierModify = supplierModifyService.findBySupplierId(supplierModify);

		if(supplierModify == null){
			return null;
		}
		if(supplierModify.getModifyType().equals("basic_page") && supplierModify.getListType() == 0){
			//在数据字典里查询营业执照类型
			if(supplierModify.getBeforeField() != null && supplierModify.getBeforeField().equals("businessType")) {
				String showModify = "";
				String typeId = supplierModify.getBeforeContent();
				List < DictionaryData > list = DictionaryDataUtil.find(17);
				for(int i = 0; i < list.size(); i++) {
					if(typeId.equals(list.get(i).getId())) {
						showModify = list.get(i).getName();
						break;
					}
				}
				if(showModify ==""){ 
					return JSON.toJSONString(supplierModify.getBeforeContent());
				}else{
					return JSON.toJSONString(showModify);
				}
			}
			//在数据字典里查询企业性质
			if(supplierModify.getBeforeField() != null && supplierModify.getBeforeField().equals("businessNature")){
				String showModify = "";
				List < DictionaryData > businessList = DictionaryDataUtil.find(32);
				for(int i = 0; i < businessList.size(); i++) {
					if(businessList.get(i).getId().equals(supplierModify.getBeforeContent())) {
						showModify = businessList.get(i).getName();
						break;
					}
				}
				return JSON.toJSONString(showModify);
			}
			
			/**
			 * 查询地址
			 */
			List < Area > privnce = areaService.findRootArea();
			Area area = new Area();
			String sonAddress = "";
			String parentAddress = "";
			if(supplierModify.getBeforeField() != null && (supplierModify.getBeforeField().equals("address") || supplierModify.getBeforeField().equals("concatCity") || supplierModify.getBeforeField().equals("armyBuinessCity"))) {
				area = areaService.listById(supplierModify.getBeforeContent());
				sonAddress = area.getName();
				for(int i = 0; i < privnce.size(); i++) {
					if(area.getParentId().equals(privnce.get(i).getId())) {
						parentAddress = privnce.get(i).getName();
					}
				}
				return JSON.toJSONString(parentAddress + sonAddress);
			}

			// Wed Feb 01 00:00:00 CST 2017         String
			if(supplierModify.getBeforeField() != null && supplierModify.getBeforeField().equals("foundDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
				Date date = sdf.parse(supplierModify.getBeforeContent());
				String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
				return JSON.toJSONString(d);
			}
			
			//经营范围 
			if(supplierModify.getBeforeField() != null && supplierModify.getBeforeField().equals("businessStartDate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
				if(StringUtils.isBlank(supplierModify.getBeforeContent())){
					supplierModify.setBeforeContent("长期有效");
				}else{
					Date date = sdf.parse(supplierModify.getBeforeContent());
					String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
					return JSON.toJSONString(d);
				}
			}
		}
		
		//近三年内有无重大违法记录/是否有国家或军队保密工程业绩
		if(supplierModify.getBeforeField().equals("isIllegal") || ("isHavingConCert").equals(supplierModify.getBeforeField()) || supplierModify.getBeforeField().equals("isHavingConAchi")){
			if(supplierModify.getBeforeContent().equals("1")){
				supplierModify.setBeforeContent("有");
			}else{
				supplierModify.setBeforeContent("无");
			}
		}
		// 工程资质--资质类型
		if("mat_eng_page".equals(supplierModify.getModifyType()) && "certType".equals(supplierModify.getBeforeField()) && StringUtils.isNotBlank(supplierModify.getBeforeContent())){
			Qualification qua = qualificationService.getQualification(supplierModify.getBeforeContent());
			if(qua != null){
				return JSON.toJSONString(qua.getName());
			}else{
				return JSON.toJSONString("");
			}
		}
		// 工程资质--资质等级
		if("mat_eng_page".equals(supplierModify.getModifyType()) && "aptituteLevel".equals(supplierModify.getBeforeField()) && StringUtils.isNotBlank(supplierModify.getBeforeContent())){
			DictionaryData dd = DictionaryDataUtil.findById(supplierModify.getBeforeContent());
			if(dd != null){
				return JSON.toJSONString(dd.getName());
			} else {
				return JSON.toJSONString("");
			}
		}

		return JSON.toJSONString(supplierModify.getBeforeContent());
	}
	/**
	 *
	 * Description:页面跳转 产品类别及资质合同
	 *
	 * @author YangHongLiang
	 * @version 2017-6-23
	 * @param model
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("toPageAptitude")
	public String toPageAptitude(Model model,String supplierId,String supplierStatus,String sign){
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("sign", sign);
		model.addAttribute("supplierStatus", supplierStatus);
		//封装 目录分类 分别显示相关的数据
		if(StringUtils.isNotBlank(supplierId)){
			List<String> supplierTypes=supplierTypeRelateService.findTypeBySupplierId(supplierId);
			model.addAttribute("supplierTypes", StringUtils.join(supplierTypes,","));
		}
		return "ses/sms/supplier_audit/merge_aptitude";
	}
	/**
	 * 
	 * Description:封装 供应商审核（产品类别及资质合同）数据
	 * 产品目录：审核字段存储：目录末级节点ID
	 * 资质文件：--物资生产/物资销售/服务
     * 审核字段存储：三级节点ID关联的SupplierItem的ID
     *    --工程
     * 审核字段存储：末级节点ID关联的SupplierItem的ID
     * 销售合同：
     * --物资生产/物资销售/服务
     * 审核字段存储：末级节点ID关联的SupplierItem的ID_附件typeId
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-23
	 * @param model
	 * @param supplierId
	 * @param supplierStatus
	 * @param sign
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("overAptitude")
	@ResponseBody
	public JdcgResult overAptitude(Model model, String supplierId, String supplierType, Integer supplierStatus, Integer sign, Integer pageNum, String flag){
		if(pageNum==null){
			pageNum=1;
		}
		List<SupplierCateTree> cateTreeList = new ArrayList<>();
		// 查询已选中的节点信息
		List < SupplierItem > listSupplierItems = null;
		if(StringUtils.isEmpty(flag)){
            listSupplierItems = supplierItemService.findCategoryList(supplierId, supplierType, pageNum);
        }else {
            listSupplierItems = supplierItemService.selectPassItemByCond(supplierId, supplierType, pageNum);
        }
		SupplierCateTree cateTree=null;
		if(listSupplierItems != null && !listSupplierItems.isEmpty()){
            for (SupplierItem supplierItem : listSupplierItems) {
                cateTree=new SupplierCateTree();
                // 递归获取所有父节点
                List < Category > parentNodeList = categoryService.getAllParentNode(supplierItem.getCategoryId());
                // 加入根节点 物资
                cateTree=categoryService.addNode(parentNodeList);
                cateTree.setSupplierItemId(supplierItem.getId());
                cateTree.setCategoryId(supplierItem.getCategoryId());
                cateTree.setAuditType(getAuditTypeItemBySupplierType(supplierType));
                if(SupplierConstants.isStatusToAudit(supplierStatus)){// 审核状态下
                	// 工程类等级
                    if("工程".equals(cateTree.getRootNode())) {
                    	//--工程  	审核字段存储：末级节点ID关联的SupplierItem的ID
                    	cateTree=supplierAuditService.countEngCategoyrId(cateTree, supplierId);
                    }else{
    					//供应商物资 专业资质要求上传
    					//--物资生产/物资销售/服务 	审核字段存储：三级节点ID关联的SupplierItem的ID
    					cateTree=supplierAuditService.countCategoyrId(cateTree,supplierId,supplierType);
    					//是否有销售合同
    					cateTree=supplierService.contractCountCategoyrId(cateTree,supplierItem);
    					// 合同是否修改
    					cateTree.setIsContractModified(supplierAuditService.isContractModified(supplierItem.getSupplierId(), supplierItem.getId()) ? (byte)1 : (byte)0);
                    }
                    //封装 是否有审核 目录 和 销售 合同数据
                    cateTree=supplierAuditService.cateTreePotting(cateTree,supplierId);
                }
                cateTreeList.add(cateTree);
            }
        }
        PageInfo<SupplierItem> pageInfo = new PageInfo<>(listSupplierItems);
    	PageInfo<SupplierCateTree> listInfo = new PageInfo<>();
    	listInfo.setEndRow(pageInfo.getEndRow());
    	listInfo.setNavigatepageNums(pageInfo.getNavigatepageNums());
    	listInfo.setNavigatePages(pageInfo.getNavigatePages());
    	listInfo.setNextPage(pageInfo.getNextPage());
    	listInfo.setOrderBy(pageInfo.getOrderBy());
    	listInfo.setPageNum(pageInfo.getPageNum());
    	listInfo.setPageSize(pageInfo.getPageSize());
    	listInfo.setPrePage(pageInfo.getPrePage());
    	listInfo.setSize(pageInfo.getSize());
    	listInfo.setStartRow(pageInfo.getStartRow());
    	listInfo.setTotal(pageInfo.getTotal());
    	listInfo.setFirstPage(pageInfo.getFirstPage());
    	listInfo.setLastPage(pageInfo.getLastPage());
    	listInfo.setList(cateTreeList);
    	listInfo.setPages(pageInfo.getPages());
        return new JdcgResult(listInfo);
	}

	/**
	 *
	 * Description:查询公示供应商通过的产品品目集
	 *
	 * @author Easong
	 * @version 2017/8/1
	 * @param [supplierId]
	 * @param [supplierType]
	 * @param [pageNum]
	 * @since JDK1.7
	 */
	@RequestMapping("/sup_publicity_item")
	@ResponseBody
	public JdcgResult supPublicityItem(String supplierId, String supplierType, @RequestParam(defaultValue = "1") int pageNum){
		Map<String, Object> map = new HashMap<>();
		map.put("supplierId", supplierId);
		map.put("type", supplierType);
		map.put("pageNum", pageNum);
		return supplierItemService.selectSupPublicityItem(map);

	}

	/**
	 * 
	 * Description:销售合同
	 * 销售合同：--物资生产/物资销售/服务  审核字段存储：目录末级节点ID关联的SupplierItem的ID_附件typeId
	 * @author YangHongLiang
	 * @version 2017-6-28
	 * @param supplierId
	 * @param cateId
	 * @param itemId
	 * @param model
	 * @return
	 */
	@RequestMapping("showContract")
	public String showContract(Model model,String supplierId,String cateId,String itemId,Integer ind,String tablerId){
		if(StringUtils.isBlank(itemId)){
			return "ses/sms/supplier_audit/aptitude_contract_item";
		}
		
		List<SupplierCateTree> allTreeList=supplierAuditService.showContractData(supplierId, cateId, itemId);
		model.addAttribute("contract", allTreeList);
		// 年份
		List < Integer > years = supplierService.getThressYear();
		model.addAttribute("years", years);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("ind", ind-1);
		model.addAttribute("tablerId", tablerId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		Supplier supplier = supplierService.selectById(supplierId);
		// 退回修改附件
		if(SupplierConstants.isStatusToAudit(supplier.getStatus())){
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null && m.getBeforeField() != null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
				}
			}
			model.addAttribute("fileModifyField", fileModifyField);
		}
		// 设置审核字段
		setAuditField(model, supplier, getAuditTypeContract(tablerId));
		return "ses/sms/supplier_audit/aptitude_contract_item";
	}
	
	private String getAuditTypeContract(String tablerId){
		String auditType = "";
		if("content_2".equals(tablerId)){
			auditType = ses.util.Constant.CONTRACT_SALES_PAGE;
		}else{
			auditType = ses.util.Constant.CONTRACT_PRODUCT_PAGE;
		}
		return auditType;
	}
	private String getAuditTypeAptitude(String tablerId){
		String auditType = "";
		if("content_2".equals(tablerId)){
			auditType = ses.util.Constant.APTITUDE_SALES_PAGE;
		}else{
			auditType = ses.util.Constant.APTITUDE_PRODUCT_PAGE;
		}
		return auditType;
	}
	private String getAuditTypeItemBySupplierType(String supplierType){
		String auditType = "";
		if(ses.util.Constant.SUPPLIER_SALES.equals(supplierType)){
			auditType = ses.util.Constant.ITEMS_SALES_PAGE;
		}else{
			auditType = ses.util.Constant.ITEMS_PRODUCT_PAGE;
		}
		return auditType;
	}
	
	/**
	 * 
	 * Description:资质 文件查看
	 * 资质文件：物资生产/物资销售/服务  审核字段存储：目录三级节点ID关联的SupplierItem的ID
	 * --工程 审核字段存储：目录末级节点ID关联的SupplierItem的ID
	 * @author YangHongLiang
	 * @version 2017-6-26
	 * @param supplierId
	 * @param cateId
	 * @param itemId
	 * @return
	 */
	@RequestMapping("showQualifications")
	public String showQualifications(Model model,String supplierId,String cateId,String itemId,Integer ind,String tablerId){
		Set<QualificationBean> beanList=new HashSet<>();
		if(StringUtils.isBlank(itemId)){
			return "ses/sms/supplier_audit/aptitude_material_item";
		}
		if(StringUtils.isBlank(supplierId)){
			return "ses/sms/supplier_audit/aptitude_material_item";
		}
		if(StringUtils.isBlank(tablerId)){
			return "ses/sms/supplier_audit/aptitude_material_item";
		}
		
		// 供应商信息
		Supplier supplier = supplierService.selectById(supplierId);
		
		// 递归获取所有父节点
		List <Category > parentNodeList = categoryService.getAllParentNode(cateId);
		// 加入根节点 物资
		SupplierCateTree cateTree=categoryService.addNode(parentNodeList);
		cateTree.setCategoryId(cateId);
		cateTree.setSupplierItemId(itemId);
		
		QualificationBean bean=new QualificationBean();
		List<Qualification> list=new ArrayList<>();
		Integer sysKey=Constant.SUPPLIER_SYS_KEY;
		String typeId=null;
		// categoryQua type:4(工程) 3（销售） 2（生产）（服务 由于数据原因 服务2）
		//content_1 物资生产 content_2物资销售 content_3工程 content_4 服务
		if("content_3".equals(tablerId)){
			//封装 供应商id   工程
			cateTree.setRootNodeCode("PROJECT");
			sysKey= Constant.SUPPLIER_SYS_KEY;
			typeId=DictionaryDataUtil.getId(ses.util.Constant.SUPPLIER_ENG_CERT);
			List<SupplierCateTree> showProject=supplierAuditService.showProject(supplierId, cateTree, 4, typeId);
			model.addAttribute("sysKey", sysKey);
			model.addAttribute("typeId", typeId);
			model.addAttribute("showProject", showProject);
			model.addAttribute("supplierId", supplierId);
			model.addAttribute("ind", ind-1);
			model.addAttribute("tablerId", tablerId);
			// 设置修改字段
			setModifyField(model, supplier, cateTree, true);
			// 设置审核字段
			setAuditField(model, supplier, getAuditTypeAptitude(tablerId));
			return "ses/sms/supplier_audit/aptitude_project_item";
		}else if("content_4".equals(tablerId)){
			//封装 供应商id  服务
			cateTree.setRootNodeCode("SERVICE");
			typeId=DictionaryDataUtil.getId(ses.util.Constant.SUPPLIER_APTITUD);
			bean.setCategoryName(cateTree.getItemsName()+"专业资质要求");
			list= supplierAuditService.showQualifications(supplierId, cateTree, 2, typeId, 1);
			if(null!=list && !list.isEmpty()){
				bean.setCategoryId(list.get(0).getSupplierItemId());
				bean.setList(list);
				beanList.add(bean);
			}
		}else if("content_1".equals(tablerId)){
			cateTree.setRootNodeCode("PRODUCT");
			typeId=DictionaryDataUtil.getId(ses.util.Constant.SUPPLIER_APTITUD);
			bean.setCategoryName(cateTree.getItemsName()+"-生产专业资质要求");
			list= supplierAuditService.showQualifications(supplierId, cateTree, 2, typeId, 2);
			if(null!=list && !list.isEmpty()){
				bean.setCategoryId(list.get(0).getSupplierItemId());
				bean.setList(list);
				beanList.add(bean);
			}
		}else if("content_2".equals(tablerId)){
			//封装 供应商id
			cateTree.setRootNodeCode("SALES");
			typeId=DictionaryDataUtil.getId(ses.util.Constant.SUPPLIER_APTITUD);
			bean.setCategoryName(cateTree.getItemsName()+"-销售专业资质要求");
			list= supplierAuditService.showQualifications(supplierId, cateTree, 3, typeId, 3);
			if(null!=list && !list.isEmpty()){
				bean.setCategoryId(list.get(0).getSupplierItemId());
				bean.setList(list);
				beanList.add(bean);
			}
		}
		// 设置修改字段
		setModifyField(model, supplier, cateTree, false);
		// 设置审核字段
		setAuditField(model, supplier, getAuditTypeAptitude(tablerId));
		
		model.addAttribute("sysKey", sysKey);
		model.addAttribute("typeId", typeId);
		model.addAttribute("beanList", beanList);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("ind", ind-1);
		model.addAttribute("tablerId", tablerId);
		return "ses/sms/supplier_audit/aptitude_material_item";
	}
	
	/**
	 * 设置审核字段
	 * @param model
	 * @param supplier
	 * @param auditType
	 */
	private void setAuditField(Model model, Supplier supplier, String auditType){
		if(SupplierConstants.isStatusToAudit(supplier.getStatus())){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());
			supplierAudit.setAuditType(auditType);
			List < SupplierAudit > reasonsList = supplierAuditService.getAuditRecords(supplierAudit, SupplierConstants.AUDIT_RETURN_STATUS);
			StringBuffer auditField = new StringBuffer();
			StringBuffer unableField = new StringBuffer();
			if(reasonsList != null && !reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					auditField.append(a.getAuditField() + ",");
					if(a.getIsDeleted() != null && a.getIsDeleted() == 1){
						unableField.append(a.getAuditField() + ",");
					}
				}
			}
			model.addAttribute("auditField", auditField);
			model.addAttribute("unableField", unableField);
		}
	}
	
	/**
	 * 设置修改字段
	 * @param model
	 * @param supplierId
	 * @param cateTree
	 * @param isEng
	 * @return
	 */
	private void setModifyField(Model model, Supplier supplier, SupplierCateTree cateTree, boolean isEng){
		// 退回修改附件
		if(SupplierConstants.isStatusToAudit(supplier.getStatus())){
			SupplierModify supplierFileModify = new SupplierModify();
			supplierFileModify.setSupplierId(supplier.getId());
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(isEng){
					if(m.getRelationId() != null){
						fileModifyField.append(m.getRelationId() + ",");
					}
				}else{
					if(m.getBeforeField() != null){
						fileModifyField.append(m.getBeforeField() + ",");
					}
				}
			}
			if(isEng){
				SupplierModify supplierModify = new SupplierModify();
				supplierModify.setSupplierId(supplier.getId());
				supplierModify.setModifyType("mat_eng_page");
				supplierModify.setListType(9);// 工程资质
				if(cateTree.getSupplierAptitute() != null && StringUtils.isNotBlank(cateTree.getSupplierAptitute().getId())){
					supplierModify.setRelationId(cateTree.getSupplierAptitute().getId());
					List < SupplierModify > fieldList = supplierModifyService.selectBySupplierId(supplierModify);
					StringBuffer field = new StringBuffer();
					for(int i = 0; i < fieldList.size(); i++) {
						String beforeField = fieldList.get(i).getBeforeField();
						field.append(beforeField + ",");
					}
					model.addAttribute("field", field);
				}
			}
			model.addAttribute("fileModifyField", fileModifyField);
		}
	}
	
	/**
	 * @Title: aptitude
	 * @author XuQing 
	 * @date 2016-12-28 上午11:12:26  
	 * @Description:资质文件维护
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "aptitude")
	public String aptitude(Model model, String supplierId, Integer supplierStatus, Integer sign) {
		model.addAttribute("supplierStatus", supplierStatus);
		model.addAttribute("sign", sign);
		model.addAttribute("supplierId", supplierId);
		String supplierTypeIds = supplierTypeRelateService.findBySupplier(supplierId);

		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		model.addAttribute("supplierTypeNames", supplierTypeName);

		//查询所有的三级品目生产
		List < Category > list2 = getSupplier(supplierId, supplierTypeIds);
		if(!list2.isEmpty()){
			removeSame(list2);
		}
		

		//查询所有的三级品目生产
		List < Category > listPro = getSupplier(supplierId, supplierTypeIds);
		removeSame(listPro);
		//根据品目id查询所有的证书信息
		List < QualificationBean > list3 = supplierService.queryCategoyrId(listPro, 2);

		//查询所有的三级品目销售
		List < Category > listSlae = getSale(supplierId, supplierTypeIds);
		removeSame(listSlae);
		//根据品目id查询所有的证书信息
		List < QualificationBean > saleQua = supplierService.queryCategoyrId(listSlae, 3);

		//查询所有的三级品目服务
		List < Category > listService = getServer(supplierId, supplierTypeIds);
		removeSame(listService);
		//根据品目id查询所有的服务证书信息
		List < QualificationBean > serviceQua = supplierService.queryCategoyrId(listService, 1);

		//生产证书
		List < Qualification > qaList = new ArrayList < Qualification > ();
		List < Qualification > saleList = new ArrayList < Qualification > ();
		List < Qualification > serviceList = new ArrayList < Qualification > ();

		if(list3 != null && list3.size() > 0) {
			for(QualificationBean qb: list3) {
				qaList.addAll(qb.getList());
			}
		}
		//销售
		if(saleQua != null && saleQua.size() > 0) {
			for(QualificationBean qb: saleQua) {
				saleList.addAll(qb.getList());
			}
		}
		//服务
		if(serviceQua != null && serviceQua.size() > 0) {
			for(QualificationBean qb: serviceQua) {
				serviceList.addAll(qb.getList());
			}
		}

		//生产
		StringBuffer sbUp = new StringBuffer("");
		StringBuffer sbShow = new StringBuffer("");
		int len = qaList.size() + 1;
		for(int i = 1; i < len; i++) {
			sbUp.append("pUp" + i + ",");
			sbShow.append("pShow" + i + ",");

		}
		//销售
		int slaelen = saleList.size() + 1;
		for(int i = 1; i < slaelen; i++) {
			sbUp.append("saleUp" + i + ",");
			sbShow.append("saleShow" + i + ",");

		}

		if(serviceList != null && serviceList.size() > 0) {
			int serverlen = serviceList.size() + 1;
			for(int i = 1; i < serverlen; i++) {
				sbUp.append("serverUp" + i + ",");
				sbShow.append("serverShow" + i + ",");

			}
		}
		model.addAttribute("saleUp", sbUp.toString());
		model.addAttribute("saleShow", sbShow.toString());
		model.addAttribute("cateList", list3);
		model.addAttribute("saleQua", saleQua);
		model.addAttribute("serviceQua", serviceQua);
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("businessId", supplierId);
		 String id = DictionaryDataUtil.getId("SUPPLIER_APTITUD");
		model.addAttribute("typeId", id);

		// 工程
		String[] typeIds = supplierTypeIds.split(",");
		boolean isEng = false;
		for(String type: typeIds) {
			if(type.equals("PROJECT")) {
				isEng = true;
				break;
			}
		}
		if(isEng) {
			SupplierMatEng matEng = supplierMatEngService.getMatEng(supplierId);
			List < SupplierItem > listSupplierItems = getProject(supplierId, "PROJECT");
			List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
			for(SupplierItem item: listSupplierItems) {
				String categoryId = item.getCategoryId();
				SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, item);
				if(cateTree != null && cateTree.getRootNode() != null) {
					cateTree.setItemsId(item.getId());
					cateTree.setDiyLevel(item.getLevel());
					if(cateTree.getCertCode() != null && cateTree.getQualificationType() != null) {
					if(cateTree!=null&&cateTree.getProName()!=null){
						List<SupplierAptitute> certEng = supplierAptituteService.queryByCodeAndType(null,matEng.getId(), cateTree.getCertCode(), cateTree.getProName());
//								List < SupplierCertEng > certEng = supplierCertEngService.selectCertEngByCode(cateTree.getCertCode(), supId);
						if(certEng != null && certEng.size() > 0) {
//									String level = supplierCertEngService.getLevel(cateTree.getQualificationType(), cateTree.getCertCode(), supplierService.get(supId).getSupplierMatEng().getId());
//									if(level != null) {
								cateTree.setFileId(certEng.get(0).getId());
//									}
						}	
					}
					
					}
					allTreeList.add(cateTree);
				}
			}
			model.addAttribute("allTreeList", allTreeList);
			model.addAttribute("engTypeId", dictionaryDataServiceI.getSupplierDictionary().getSupplierEngCert());
		}
		
		//回显未通过字段
		if(supplierStatus == -3 || supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 1 || supplierStatus == 5 || supplierStatus == 9){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("aptitude_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					passedField.append(a.getAuditField() + ",");
				}
			}
			model.addAttribute("passedField", passedField);
		}
		
		if(supplierStatus == 0){
			/**
			 * 退回修改后的附件
			 */
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				fileModifyField.append(m.getBeforeField() + ",");
			}
			model.addAttribute("fileModifyField", fileModifyField);
		}
		
		return "ses/sms/supplier_audit/aptitude";
	}

	//生产所有的三级目录
	public List < Category > getSupplier(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();
		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("PRODUCT")) {
					/*categoryId = DictionaryDataUtil.getId("GOODS");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
                    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
				    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
						Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
						if (cate == null) {
						    cate = new Category();
						    DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
						    cate.setId(data.getId());
						    cate.setParentId(data.getId());
						    cate.setName(data.getName());
						} else {
							//供应商中间表的id和资质证书的id
						    cate.setParentId(c.getId());
						}
						categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}

	//销售
	public List < Category > getSale(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();

		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("SALES")) {
					/*categoryId = DictionaryDataUtil.getId("GOODS");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
				    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
                    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                        if (cate == null) {
                            cate = new Category();
                            DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                            cate.setId(data.getId());
                            cate.setParentId(data.getId());
                            cate.setName(data.getName());
                        } else {
                            cate.setParentId(c.getId());
                        }
                        categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}

	//工程
	public List < SupplierItem > getProject(String supplierId, String code) {
		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("PROJECT")) {
					categoryId = DictionaryDataUtil.getId("PROJECT");
                    return supplierItemService.getCategoryOther(supplierId, categoryId, s);
				}
			}

		}
		return null;
	}

	//服务
	public List < Category > getServer(String supplierId, String code) {
		List < Category > categoryList = new ArrayList < Category > ();

		String[] types = code.split(",");
		for(String s: types) {
			String categoryId = "";
			if(s != null) {
				if(s.equals("SERVICE")) {
					/*categoryId = DictionaryDataUtil.getId("SERVICE");
					List < SupplierItem > category = supplierItemService.getCategory(supplierId, categoryId, s);*/
				    Map<String, Object> searchMap = new HashMap<String, Object>();
                    searchMap.put("supplierId", supplierId);
                    searchMap.put("type", s);
                    List < SupplierItem > category = supplierItemService.findByMap(searchMap);
					for(SupplierItem c: category) {
					    Category cate = categoryService.selectByPrimaryKey(c.getCategoryId());
                        if (cate == null) {
                            cate = new Category();
                            DictionaryData data = DictionaryDataUtil.findById(c.getCategoryId());
                            cate.setId(data.getId());
                            cate.setParentId(data.getId());
                            cate.setName(data.getName());
                        } else {
                            cate.setParentId(c.getId());
                        }
                        categoryList.add(cate);
					}
				}
			}
		}
		return categoryList;
	}

	/**
	 * @Title: contract
	 * @author XuQing 
	 * @date 2016-12-28 上午11:12:33  
	 * @Description:品目合同
	 * @param @return      
	 * @return String
	 */
	/*	@RequestMapping(value = "contract")
		public String contract(Model model, String supplierId) {
			List<SupplierTypeRelate> typeIds= supplierTypeRelateService.queryBySupplier(supplierId);

			String supplierTypeIds = "";
			for(SupplierTypeRelate s : typeIds){
				supplierTypeIds += s.getSupplierTypeId()+ ",";
			}
			//勾选的供应商类型
			String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
			model.addAttribute("supplierTypeNames", supplierTypeName);
			List<ContractBean> contract = new LinkedList<ContractBean>();
			 
			 List<ContractBean> saleBean=new LinkedList<ContractBean>();
			 
			 List<ContractBean> projectBean=new LinkedList<ContractBean>();
			 List<ContractBean> serverBean=new LinkedList<ContractBean>();
			 
			 //合同
			 String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
			 String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
			 String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
			 //账单
			 String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
			 String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");		 
			 String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
			 int count=0;
			 StringBuffer sbUp=new StringBuffer("");
			 StringBuffer sbShow=new StringBuffer("");
			 String[] strs = supplierTypeIds.split(",");
			 List<Category> product=new ArrayList<Category>();
			 List<Category> sale=new ArrayList<Category>();
			 List<Category> project=new ArrayList<Category>();
			 List<Category> server=new ArrayList<Category>();
			  for(String type:strs){
				  if(type.equals("PRODUCT")){
					  List<Category> list = supplierItemService.getCategory(supplierId,"PRODUCT");
					  removeSame(list);
					  product.addAll(list);
				  }
				  if(type.equals("SALES")){
					  List<Category> list = supplierItemService.getCategory(supplierId,"SALES");
					  removeSame(list);
					  sale.addAll(list);
				  }
				  if(type.equals("PROJECT")){
					  List<Category> list = supplierItemService.getCategory(supplierId,"PROJECT");
					  removeSame(list);
					  project.addAll(list);
				  }
				  if(type.equals("SERVICE")){
					  List<Category> list = supplierItemService.getCategory(supplierId,"SERVICE");
					  removeSame(list);
					  server.addAll(list);
				  }
			  }
			
			 for(Category ca:product){
				 ContractBean con=new ContractBean();
				 con.setId(ca.getId());
				 con.setName(ca.getName());
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setOneContract(id1);
				 count++;
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setTwoContract(id2);
				 count++;
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setThreeContract(id3);
				 count++;
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setOneBil(id4);
				 count++;
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setTwoBil(id5);
				 count++;
				 
				 
				 sbUp.append("pUp"+count+",");
				 sbShow.append("pShow"+count+",");
				 con.setTwoBil(id6);
				 count++;
		 
				 contract.add(con);
			 }
			 
			 int sales=0;
			 for(Category ca:sale){
				 ContractBean con=new ContractBean();
				 con.setId(ca.getId());
				 con.setName(ca.getName());
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setOneContract(id1);
				 sales++;
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setTwoContract(id2);
				 sales++;
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setThreeContract(id3);
				 sales++;
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setOneBil(id4);
				 sales++;
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setTwoBil(id5);
				 sales++;
				 
				 
				 sbUp.append("saleUp"+sales+",");
				 sbShow.append("saleShow"+sales+",");
				 con.setTwoBil(id6);
				 sales++;
		 
				 saleBean.add(con);
			 }
			 
			 int projects=0;
			 for(Category ca:project){
				 ContractBean con=new ContractBean();
				 con.setId(ca.getId());
				 con.setName(ca.getName());
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setOneContract(id1);
				 projects++;
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setTwoContract(id2);
				 projects++;
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setThreeContract(id3);
				 projects++;
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setOneBil(id4);
				 projects++;
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setTwoBil(id5);
				 projects++;
				 
				 
				 sbUp.append("projectUp"+projects+",");
				 sbShow.append("projectShow"+projects+",");
				 con.setTwoBil(id6);
				 projects++;
		 
				 projectBean.add(con);
			 }
			 
			 int servers=0;
			 for(Category ca:server){
				 ContractBean con=new ContractBean();
				 con.setId(ca.getId());
				 con.setName(ca.getName());
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setOneContract(id1);
				 servers++;
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setTwoContract(id2);
				 servers++;
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setThreeContract(id3);
				 servers++;
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setOneBil(id4);
				 servers++;
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setTwoBil(id5);
				 servers++;
				 
				 
				 sbUp.append("serUp"+servers+",");
				 sbShow.append("serpShow"+servers+",");
				 con.setTwoBil(id6);
				 servers++;
		 
				 serverBean.add(con);
			 }
			 
			 
			 model.addAttribute("serverBean", serverBean);
			 model.addAttribute("projectBean", projectBean);
			 model.addAttribute("saleBean", saleBean); 
			 model.addAttribute("contract", contract);	
			 model.addAttribute("sbUp", sbUp);
			 model.addAttribute("sbShow", sbShow);
			 List<Integer> years = supplierService.getThressYear();
			 model.addAttribute("years", years);
			 model.addAttribute("supplierTypeIds", supplierTypeIds);
			 model.addAttribute("supplierId", supplierId);
			 model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
			return "ses/sms/supplier_audit/contract";
		}*/

	/**
	 * @Title: contractUp
	 * @author XuQing 
	 * @date 2017-1-17 下午5:46:54  
	 * @Description:加载点击标签下的合同
	 * @param @param supplierId
	 * @param @param model
	 * @param @param supplierTypeId
	 * @param @param pageNum
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/ajaxContract")
	public String contractUp(String supplierId, Model model, String supplierTypeId, Integer pageNum) {
		//合同
		String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
		String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
		String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
		//账单
		String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
		String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
		String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");

		/*List < Category > category = new ArrayList < Category > ();*/
		List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplierId, supplierTypeId, pageNum == null ? 1 : pageNum);
		/*for(SupplierItem item: itemsList) {
			Category cate = categoryService.findById(item.getCategoryId());
			cate.setId(item.getId());
			category.add(cate);
		}
		// 查询品目合同信息
		List < ContractBean > contract = supplierService.getContract(category);*/
		// 查询已选中的节点信息
		
		List < SupplierCateTree > allTreeList = new ArrayList < SupplierCateTree > ();
		for(SupplierItem item: itemsList) {
			String categoryId = item.getCategoryId();
			SupplierCateTree cateTree = getTreeListByCategoryId(categoryId, null);
			
			if(cateTree != null && cateTree.getRootNode() != null) {
				cateTree.setItemsId(item.getId());
				allTreeList.add(cateTree);
			}
			
			
		}
		for(SupplierCateTree item: allTreeList) {
			item.setOneContract(id1);
			item.setTwoContract(id2);
			item.setThreeContract(id3);
			item.setOneBil(id4);
			item.setTwoBil(id5);
			item.setThreeBil(id6);
		}
		for(SupplierCateTree cate: allTreeList) {
			cate.setRootNode(cate.getRootNode() == null ? "" : cate.getRootNode());
			cate.setFirstNode(cate.getFirstNode() == null ? "" : cate.getFirstNode());
			cate.setSecondNode(cate.getSecondNode() == null ? "" : cate.getSecondNode());
			cate.setThirdNode(cate.getThirdNode() == null ? "" : cate.getThirdNode());
			cate.setFourthNode(cate.getFourthNode() == null ? "" : cate.getFourthNode());
			String typeName = "";
			if(supplierTypeId.equals("PRODUCT")) {
				typeName = "生产";
			} else if(supplierTypeId.equals("SALES")) {
				typeName = "销售";
			}
			cate.setRootNode(cate.getRootNode() + typeName);
		}
		
		// 分页,pageSize == 10
		PageInfo < SupplierItem > pageInfo = new PageInfo < SupplierItem > (itemsList);
		model.addAttribute("result", pageInfo);
		model.addAttribute("contract", allTreeList);
		// 年份
		List < Integer > years = supplierService.getThressYear();
		model.addAttribute("years", years);
		model.addAttribute("supplierTypeId", supplierTypeId);
		model.addAttribute("supplierId", supplierId);
		// 供应商附件sysKey参数
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		
		if(supplier.getStatus() !=null && (supplier.getStatus() == -3 || supplier.getStatus() == -2 || supplier.getStatus() == 0 || supplier.getStatus() == 4 || supplier.getStatus() == 5 || supplier.getStatus() == 9)){
			//回显未通过字段
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("contract_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			for(SupplierAudit a : reasonsList){
				passedField.append(a.getAuditField() + ",");
			}
			model.addAttribute("passedField", passedField);
			
		}
		if(supplier.getStatus() !=null && supplier.getStatus() == 0){
			/**
			 * 退回修改后的附件
			 */
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
				}
			}
			model.addAttribute("fileModifyField", fileModifyField);
		}

		return "ses/sms/supplier_audit/ajax_contract";
	}

	/**
	 * @Title: contractUp
	 * @author XuQing 
	 * @date 2017-1-17 下午5:47:19  
	 * @Description:加载全部标签
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/contract")
	public String contractUp(String supplierId, Model model, Integer supplierStatus, Integer sign) {
		model.addAttribute("sign", sign);
		List < SupplierTypeRelate > typeIds = supplierTypeRelateService.queryBySupplier(supplierId);
		String supplierTypeIds = "";
		for(SupplierTypeRelate s: typeIds) {
			supplierTypeIds += s.getSupplierTypeId() + ",";
		}
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("supplierStatus", supplierStatus);
		return "ses/sms/supplier_audit/contract";
	}

	@ResponseBody
	@RequestMapping(value = "/getTree", produces = "application/json;charset=utf-8")
	public String getTree(String supplierId, String code) {
		List < Category > categoryList = supplierItemService.getCategoryShenhe(supplierId, code);
		// 最后加入根节点
		String typeId = null;
		if("PRODUCT".equals(code) || "SALES".equals(code)) {
			typeId = DictionaryDataUtil.getId("GOODS");
		} else {
			typeId = DictionaryDataUtil.getId(code);
		}
		DictionaryData data = DictionaryDataUtil.findById(typeId);
		Category root = new Category();
		root.setId(data.getId());
		if("PRODUCT".equals(code)) {
			data.setName(data.getName() + "生产");
		} else if("SALES".equals(code)) {
			data.setName(data.getName() + "销售");
		}
		root.setName(data.getName());
		categoryList.add(root);
		List < CategoryTree > treeList = new ArrayList < CategoryTree > ();
		for(Category cate: categoryList) {
			CategoryTree node = new CategoryTree();
			node.setId(cate.getId());
			node.setName(cate.getName());
			node.setParentId(cate.getParentId());
			// 判断是不是父级节点
			List < Category > parentTree = categoryService.findPublishTree(cate.getId(), null);
			if(parentTree != null && parentTree.size() > 0) {
				node.setIsParent("true");
			} else {
				node.setIsParent("false");
			}
			treeList.add(node);
		}

		return JSON.toJSONString(treeList);
	}

	/**
	 * @Title: removeSame
	 * @author XuQing 
	 * @date 2017-1-4 下午7:23:33  
	 * @Description:去重
	 * @param @param list      
	 * @return void
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
	 * @Title: recordNotPassed
	 * @author XuQing 
	 * @date 2017-1-11 下午1:53:18  
	 * @Description:记录审核未通过的
	 * @param @param supplierAuditNot      
	 * @return void
	 */
	@RequestMapping(value = "/recordNotPassed")
/*	@ResponseBody*/
	public void recordNotPassed(SupplierAuditNot supplierAuditNot) {
		Supplier supplier = supplierAuditService.supplierById(supplierAuditNot.getSupplierId());
		supplierAuditNot.setCreditCode(supplier.getCreditCode());
		supplierAuditNot.setCreatedAt(new Date());
		supplierAuditNotService.insertSelective(supplierAuditNot);
	}

	/**
	 * @Title: auditNotReason
	 * @author XuQing 
	 * @date 2017-1-11 下午7:13:44  
	 * @Description:查询未通过审核的记录
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/auditNotReason", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String auditNotReason(String supplierId, Model model) {
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		SupplierAuditNot supplierAuditNot = new SupplierAuditNot();
		if(supplier.getCreditCode() != null){
			supplierAuditNot.setCreditCode(supplier.getCreditCode());
			supplierAuditNot = supplierAuditNotService.selectByPrimaryKey(supplierAuditNot);
		}
		if(supplierAuditNot != null) {
			return supplierAuditNot.getReason();
		}
		return "";
	}
	
	/**
	 * @Title: publish
	 * @author XuQing 
	 * @date 2017-3-8 下午4:06:06  
	 * @Description:发布到门户名录上
	 * @param @param supplierId      
	 * @return String
	 */
	@RequestMapping(value = "/publish", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String publish(String supplierId){
		Supplier supplier = new Supplier();
		supplier = supplierAuditService.supplierById(supplierId);
		String msg = "";
		if(supplier.getIsPublish() != 1){
			supplier.setId(supplierId);
			supplier.setIsPublish(1);
			supplierAuditService.updateStatus(supplier);
			msg = "yes";
		}else{
			msg = "no";
		}
		return JSON.toJSONString(msg);
	}
	
	
	/**
	 * @Title: downloadTable
	 * @author XuQing 
	 * @date 2017-4-1 上午11:22:15  
	 * @Description:生成word
	 * @param @param expertId
	 * @param @param request
	 * @param @param response
	 * @param @param tableType
	 * @param @return
	 * @param @throws Exception      
	 * @return ResponseEntity<byte[]>
	 */
	@RequestMapping("downloadTable")
	public ResponseEntity < byte[] > downloadTable(String supplierId, HttpServletRequest request, HttpServletResponse response, String tableType, String opinion) throws Exception {
		//供应商信息
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String fileName = createWordMethod(supplier, request, tableType, opinion);
		// 下载后的文件名
		String downFileName = "";
		if(tableType.equals("1")){
			downFileName = new String("军队供应商实地考察记录表.doc".getBytes("UTF-8"), "iso-8859-1");
		}
		if(tableType.equals("2")){
			downFileName = new String("军队供应商实地考察廉政意见函.doc".getBytes("UTF-8"), "iso-8859-1"); 
		}
		if("3".equals(tableType) || "0".equals(tableType)){
			downFileName = new String("军队采购供应商审核表.doc".getBytes("UTF-8"), "iso-8859-1");
			downFileName = supplier.getSupplierName() + ".doc";
			downFileName = new String(downFileName.getBytes("UTF-8"), "iso-8859-1");
		}
		if("4".equals(tableType)){
			downFileName = new String("军队采购供应商审核表.doc".getBytes("UTF-8"), "iso-8859-1");
			downFileName = supplier.getSupplierName() + ".doc";
			downFileName = new String(downFileName.getBytes("UTF-8"), "iso-8859-1");
		}
		response.setContentType("application/x-download");
		return supplierAuditService.downloadFile(fileName, filePath, downFileName);
	}

    /**
     *
     * Description:封装供应商审核表中的供应商资质材料情况
     *
     * @author Easong
     * @version 2017/10/20
     * @param [materialName:资料名称列
     *                    content：注册信息与上传扫描件是否一致列
     *                    map：存储Map
     *                    newsList：存储列Map集合
     * @since JDK1.7
     */
	private Map<String, Object> packageTable(String materialName, String content, Map<String, Object> map, List<Map<String, Object>> newsList){
	    map.put("materialName", materialName);
	    map.put("content", content);
        newsList.add(map);
	    return new HashMap<>();
    }

	/**
	 * @Title: createWordMethod
	 * @author XuQing 
	 * @date 2016-12-27 下午2:34:36  
	 * @Description:生成word
	 * @param @param expert
	 * @param @param request
	 * @param @return
	 * @param @throws Exception      
	 * @return String
	 */
	private String createWordMethod(Supplier supplier, HttpServletRequest request, String tableType, String opinion) throws Exception {
		/** 用于组装word页面需要的数据 */
		Map < String, Object > dataMap = new HashMap < String, Object > ();
		SupplierSignature supplierSignature = new SupplierSignature();
		String newFileName = "";
		
		//日期
	    SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
		dataMap.put("date", format.format(supplier.getAuditDate()));

		Map<String, Object> selMap = new HashedMap();
		// 采购机构全称
		selMap.put("purchaseDepId", supplier.getProcurementDepId());
		String orgFullName = purChaseDepOrgService.selectOrgFullNameByPurchaseDepId(selMap);
		dataMap.put("orgFullName", orgFullName);

		//供应商名称
		dataMap.put("supplierName", supplier.getSupplierName() == null ? "" : supplier.getSupplierName());
		if(tableType.equals("1")){
			//办公地址
			dataMap.put("address", supplier.getContactAddress() == null ? "" : supplier.getContactAddress());
			//办公地址
			dataMap.put("officeAddress", "");
			//生产或经营场所详细地址
			dataMap.put("detailAddress", supplier.getDetailAddress() == null ? "" : supplier.getDetailAddress());
			
			
			//生产或经营场所详细地址
			List < Area > privnce = areaService.findRootArea();
			List < SupplierAddress > supplierAddress = supplierAddressService.queryBySupplierId(supplier.getId());
			for(Area a: privnce) {
				for(SupplierAddress s: supplierAddress) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
			dataMap.put("supplierAddress", supplierAddress);

			//考察组成员
			supplierSignature.setSupplierId(supplier.getId());
			List<SupplierSignature> supplierSignatureList = supplierSignatureService.selectBySupplierId(supplierSignature);
			dataMap.put("supplierList", supplierSignatureList);
			
			//生成word 返回文件名 
			newFileName = WordUtil.createWord(dataMap, "supplierInspection.ftl", "supplierInspection", request);
		}
		
		//意见函人数和名字
		if(tableType.equals("2")){
			supplierSignature.setSupplierId(supplier.getId());
			List<SupplierSignature> supplierSignatureList = supplierSignatureService.selectBySupplierId(supplierSignature);
			if(!supplierSignatureList.isEmpty() && supplierSignatureList.size() > 0){
				StringBuffer name = new StringBuffer();
				for(SupplierSignature s: supplierSignatureList ){
					name.append(s.getName() + ",");
				}		
				name.deleteCharAt(name.length() - 1);
				dataMap.put("num", supplierSignatureList.size());
				dataMap.put("name", name);
			}else{
				dataMap.put("num", "");
				dataMap.put("name", "");	
			}
			newFileName = WordUtil.createWord(dataMap, "supplierOpinionLetter.ftl", "supplierOpinionLetter", request);
		}

		/**
		 * 审核/复核表的数据
		 */
		if("3".equals(tableType) || "0".equals(tableType) || "4".equals(tableType)){
			//企业性质
			String businessNature = businessNature(supplier.getBusinessNature());
			dataMap.put("businessNature", businessNature);
			//营业执照类型
			String businessType = businessType(supplier.getBusinessType());
			dataMap.put("businessType", businessType);
			//统一社会信用代码
			dataMap.put("creditCode", supplier.getCreditCode() == null ? "":supplier.getCreditCode());
			//注册人姓名
			dataMap.put("legalName", supplier.getLegalName() == null ? "":supplier.getLegalName());
			//身份证号
			dataMap.put("legalIdCard", supplier.getLegalIdCard() == null ? "":supplier.getLegalIdCard());
			//供应商类型
			StringBuffer supplierType = new StringBuffer();
			List<String> typeList = supplierTypeRelateService.findTypeBySupplierId(supplier.getId());
			/*Map<String, Object> selectAuditMap = new HashMap<>();
            // 先排除不通过的类型
            selectAuditMap.put("supplierId", supplier.getId());
            List<SupplierAudit> supNoPassType = supplierAuditService.selectBySupIdAndType(selectAuditMap);
            // 依次查询通过的类型下所有的品目
            if(supNoPassType != null && !supNoPassType.isEmpty()){
                for(SupplierAudit supplierAudit : supNoPassType){
                    typeList.remove(supplierAudit.getType());
                }
            }*/

			if (typeList != null && typeList.size() > 0) {
				for (int i = 0; i < typeList.size(); i++) {
                    DictionaryData dictionaryData = DictionaryDataUtil.get(typeList.get(i));
                    if (dictionaryData != null) {
						supplierType.append(dictionaryData.getName()).append("、");
					}
				}
			}
			dataMap.put("supplierType", supplierType.toString().substring(0, supplierType.length() -1));

			/*查询附件审核结果*/
			SupplierAudit supplierAttach = new SupplierAudit();
			supplierAttach.setSupplierId(supplier.getId());

			// 定义供应商供应商资质材料情况
			List<Map<String, Object>> newsList = new ArrayList<>();
			// 定义封装基本数据Map
			Map<String, Object> materialMap = null;
			// 定义审核结果
            String content;
			//1 营业执照
            materialMap = new HashMap<>();
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierBusinessCert());
			List < SupplierAudit > businessCertList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(businessCertList != null && businessCertList.size() > 0){
                content = "否。原因："+businessCertList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("营业执照", content, materialMap, newsList);

            //2 法定代表人身份证
			StringBuffer supplierIdCard = new StringBuffer();
			boolean supplierIdCardflag = true;
			//正面
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierIdentityUp());
			List < SupplierAudit > supplierIdCardList1 = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(supplierIdCardList1 != null && supplierIdCardList1.size() > 0){
				supplierIdCardflag &= false;
				supplierIdCard.append("正面："+supplierIdCardList1.get(0).getSuggest());
			}
			//反面
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierIdentitydown());
			List < SupplierAudit > supplierIdCardList2 = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(supplierIdCardList2 != null && supplierIdCardList2.size() > 0){
				supplierIdCardflag &= false;
				supplierIdCard.append("反面："+supplierIdCardList2.get(0).getSuggest());
			}
			if(!supplierIdCardflag){
                content = "否。原因："+supplierIdCard.toString();
			}else{
                content = "是。";
			}
            materialMap = packageTable("法定代表人身份证", content, materialMap, newsList);

            //3 基本账户开户许可证
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierBank());
			List < SupplierAudit > supplierBankList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(supplierBankList != null && supplierBankList.size() > 0){
                content = "否。原因："+supplierBankList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("基本账户开户许可证", content, materialMap, newsList);

            //4 房产证明或租赁协议
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierHousePoperty());
			List < SupplierAudit > supplierHousePopertyList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(supplierHousePopertyList != null && supplierHousePopertyList.size() > 0){
			    content = "否。原因："+supplierHousePopertyList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("房产证明或租赁协议", content, materialMap, newsList);

            //5 近三个月完税凭证
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierTaxCert());
			List < SupplierAudit > taxCertList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(taxCertList != null && taxCertList.size() > 0){
                content = "否。原因："+taxCertList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("近三个月完税凭证", content, materialMap, newsList);

            //6 近三年银行基本账户年末对账单
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierBillCert());
			List < SupplierAudit > billCertList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(billCertList != null && billCertList.size() > 0){
                content = "否。原因："+billCertList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("近三年银行基本账户年末对账单", content, materialMap, newsList);

            //7 近三个月缴纳社会保险金凭证
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierSecurityCert());
			List < SupplierAudit > securityCertList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(securityCertList != null && securityCertList.size() > 0){
                content = "否。原因："+securityCertList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("近三个月缴纳社会保险金凭证", content, materialMap, newsList);

            //8 国家或军队保密资格证书（可无）
			supplierAttach.setAuditField(dictionaryDataServiceI.getSupplierDictionary().getSupplierBearchCert());
			List < SupplierAudit > supplierBearchCertList = supplierAuditService.selectByPrimaryKey(supplierAttach);
			if(supplierBearchCertList != null && supplierBearchCertList.size() > 0){
                content = "否。原因："+supplierBearchCertList.get(0).getSuggest();
			}else{
                content = "是。";
			}
            materialMap = packageTable("国家或军队保密资格证书（可无）", content, materialMap, newsList);

            //9-13 近三年财务信息
			List<SupplierFinance> supplierIdYearThreeList = supplierFinanceService.findBySupplierIdYearThree(supplier.getId());
			StringBuffer supplierFinanceReasons = new StringBuffer();
			boolean supplierFinanceflag = true;
			if(supplierIdYearThreeList != null && supplierIdYearThreeList.size() > 0){
				for (SupplierFinance sf : supplierIdYearThreeList) {
					supplierAttach.setAuditField(sf.getId()+"_file");
					List < SupplierAudit > supplierFinanceList = supplierAuditService.selectByPrimaryKey(supplierAttach);
					if(supplierFinanceList != null && supplierFinanceList.size() > 0){
						supplierFinanceflag &= false;
						supplierFinanceReasons.append(sf.getYear() +"年："+supplierFinanceList.get(0).getSuggest());
					}
				}
			}

			if(!supplierFinanceflag){
                content = "否。原因："+supplierFinanceReasons.toString();
			}else{
                content = "是。";
			}
            materialMap = packageTable("近三年审计报告书中的审计报告", content, materialMap, newsList);
            materialMap = packageTable("近三年资产负债表", content, materialMap, newsList);
            materialMap = packageTable("近三年财务利润表", content, materialMap, newsList);
            materialMap = packageTable("近三年现金流量表", content, materialMap, newsList);
            materialMap = packageTable("近三年所有者权益变动表（可无）", content, materialMap, newsList);

            // 复核表
			if("4".equals(tableType)){
				dataMap.put("newsListInfo",newsList);
				return WordUtil.createWord(dataMap, "supplierRecheck.ftl", "supplierRecheck", request);
			}

            String matProId = null;
			//14 质量管理体系认证证书（只有物资生产才有的）
            if(typeList != null && typeList.contains(ses.util.Constant.SUPPLIER_PRODUCT)){
                matProId = supplierMatProService.getMatProIdBySupplierId(supplier.getId());
                if(matProId != null){
                    String cerProId = supplierCertProService.findCertProByProIdAndName(matProId);
                    if(cerProId != null){
                        supplierAttach.setAuditField(cerProId);
                        List < SupplierAudit > supplierMatProList = supplierAuditService.selectByPrimaryKey(supplierAttach);
                        if(supplierMatProList != null && supplierMatProList.size() > 0){
                            content = "否。原因："+supplierMatProList.get(0).getSuggest();
                        }else{
                            content = "是。";
                        }
                    }else{
                        content = "无。";
                    }
                }else{
                    content = "无。";
                }
                materialMap = packageTable("质量管理体系认证证书", content, materialMap, newsList);
            }

			//15 保密工程业绩承包合同主要页及保密协议（可无）
            if(typeList != null && typeList.contains(ses.util.Constant.SUPPLIER_PROJECT)) {
                if (dictionaryDataServiceI.getSupplierDictionary().getSupplierConAch() == null) {
                    content = "无。";
                } else {
                    List<SupplierAudit> supplierConAchList = supplierAuditService.selectByPrimaryKey(supplierAttach);
                    if (supplierConAchList != null && supplierConAchList.size() > 0) {
                        content = "否。原因：" + supplierConAchList.get(0).getSuggest();
                    } else {
                        content = "是。";
                    }
                }
                materialMap = packageTable("保密工程业绩承包合同主要页及保密协议（可无）", content, materialMap, newsList);

                //16 承揽业务范围省级行政区对应合同主要页（工程特有  需要先判断是否为工程）
                StringBuffer supplierMatEngReasons = new StringBuffer();
                boolean supplierMatEngflag = true;
                SupplierMatEng eng = supplierMatEngService.getMatEng(supplier.getId());
                if (eng != null && eng.getBusinessScope() != null) {
                    String[] scopes = eng.getBusinessScope().split(",");
                    for (String str : scopes) {
                        Area area = areaService.listById(str);
                        if (area != null) {
                            supplierAttach.setAuditField(area.getName());
                            List<SupplierAudit> supplierMatEngList = supplierAuditService.selectByPrimaryKey(supplierAttach);
                            if (supplierMatEngList != null && supplierMatEngList.size() > 0) {
                                supplierMatEngflag &= false;
                                supplierMatEngReasons.append(area.getName() + ":" + supplierMatEngList.get(0).getSuggest());
                            }
                        }
                    }
                    if (!supplierMatEngflag) {
                        content = "否。原因：" + supplierMatEngReasons.toString();
                    } else {
                        content = "是。";
                    }
                } else {
                    content = "无。";
                }
                materialMap = packageTable("承揽业务范围省级行政区对应合同主要页", content, materialMap, newsList);
            }
			// 遍历该供应商类型
			if(typeList != null && !typeList.isEmpty()){
				for (int index = 0; index < typeList.size(); index++){
					String supType = typeList.get(index);
					/**
					 * 17物资生产资质证书
					 */
					String typeName = "";
					if(ses.util.Constant.SUPPLIER_PRODUCT.equals(supType)){
                        typeName = "物资生产资质证书";
						if(matProId != null){
							List<SupplierCertPro> wuziscList = supplierCertProService.queryByMatProId(matProId);
							StringBuffer wuziscReasons = new StringBuffer();
							boolean wuziscflag = true;
							if(wuziscList != null && wuziscList.size() > 0){
								for (SupplierCertPro supplierCertPro : wuziscList) {
									supplierAttach.setAuditField(supplierCertPro.getId());
									List < SupplierAudit > wuzisc = supplierAuditService.selectByPrimaryKey(supplierAttach);
									if(wuzisc != null && wuzisc.size() > 0){
										wuziscflag &= false;
										wuziscReasons.append(supplierCertPro.getName() + ":" + wuzisc.get(0).getSuggest());
									}
								}
								if(!wuziscflag){
                                    content = "否。原因：" + wuziscReasons.toString();
								}else {
                                    content = "是。";
								}
							}else{
                                content = "无。";
							}
						}else{
                            content = "无。";
						}
					}

					/**
					 * 18 物资销售资质证书
					 */
					if(ses.util.Constant.SUPPLIER_SALES.equals(supType)){
					    typeName = "物资销售资质证书";
						String matSellId = supplierMatSellService.getMatSellIdBySupplierId(supplier.getId());
						if(matSellId != null){
							List<SupplierCertSell> wuzixsList = supplierCertSellService.queryBySaleId(matSellId);
							StringBuffer wuzixsReasons = new StringBuffer();
							boolean wuzixsflag = true;
							if(wuzixsList != null && wuzixsList.size() > 0){
								for (SupplierCertSell supplierCertSell : wuzixsList) {
									supplierAttach.setAuditField(supplierCertSell.getId());
									List < SupplierAudit > wuzixs = supplierAuditService.selectByPrimaryKey(supplierAttach);
									if(wuzixs != null && wuzixs.size() > 0){
										wuzixsflag &= false;
										wuzixsReasons.append(supplierCertSell .getName() + ":" + wuzixs.get(0).getSuggest());
									}
								}
								if(!wuzixsflag){
                                    content = "否。原因：" + wuzixsReasons.toString();
								}else {
                                    content = "是。";
								}
							}else{
                                content =  "无。";
							}
						}else{
                            content = "无。";
						}
					}

					/**
					 * 19 工程资质证书
					 */
					if(ses.util.Constant.SUPPLIER_PROJECT.equals(supType)){
                        typeName = "工程资质证书";
						String engId = supplierMatEngService.getMatEngIdBySupplierId(supplier.getId());
						if(engId != null){
							List<SupplierAptitute> engCert = supplierAptituteService.queryByAptitute(engId);
							StringBuffer engReasons = new StringBuffer();
							boolean engflag = true;
							if(engCert != null && engCert.size() > 0){
								for (SupplierAptitute supplierAptitute : engCert) {
									supplierAttach.setAuditField(supplierAptitute.getId());
									List < SupplierAudit > engList = supplierAuditService.selectByPrimaryKey(supplierAttach);
									if(engList != null && engList.size() > 0){
										engflag &= false;
										engReasons.append(supplierAptitute.getCertName() + ":" + engList.get(0).getSuggest());
									}
								}
								if(!engflag){
                                    content = "否。原因：" + engReasons.toString();
								}else{
                                    content = "是。";
								}
							}else{
                                content = "无。";
							}
						}else{
                            content = "无。";
						}
					}
					/**
					 * 20 服务资质证书
					 */
					if(ses.util.Constant.SUPPLIER_SERVICE.equals(supType)){
                        typeName = "服务资质证书";
						SupplierMatServe matSeBySupplierId = supplierMatServeMapper.getMatSeBySupplierId(supplier.getId());
						if(matSeBySupplierId != null && matSeBySupplierId.getId() != null){
							List<SupplierCertServe> serveCert = supplierCertServeMapper.findCertSeByMatSeId(matSeBySupplierId.getId());
							StringBuffer serveReasons = new StringBuffer();
							boolean serveflag = true;
							if(serveCert != null && serveCert.size() > 0){
								for (SupplierCertServe supplierCertServe : serveCert) {
									supplierAttach.setAuditField(supplierCertServe.getId());
									List < SupplierAudit > serverList = supplierAuditService.selectByPrimaryKey(supplierAttach);
									if(serverList != null && serverList.size() > 0){
										serveflag &= false;
										serveReasons.append(supplierCertServe.getName() + ":" + serverList.get(0).getSuggest());
									}
								}
								if(!serveflag){
                                    content = "否。原因：" + serveReasons.toString();
								}else{
                                    content = "是。";
								}
							}else{
                                content =  "无。";
							}
						}else{
                            content = "无。";
						}
					}
                    materialMap = packageTable(typeName, content, materialMap, newsList);
                }
			}

			//21 近三年销售合同及银行汇款证明
			StringBuffer saleContractReasons = new StringBuffer();
			boolean saleContractflag = true;
			//合同
			String id1 = DictionaryDataUtil.getId("CATEGORY_ONE_YEAR");
			String id2 = DictionaryDataUtil.getId("CATEGORY_TWO_YEAR");
			String id3 = DictionaryDataUtil.getId("CATEGORY_THREE_YEAR");
			//账单
			String id4 = DictionaryDataUtil.getId("CTAEGORY_ONE_BIL");
			String id5 = DictionaryDataUtil.getId("CTAEGORY_TWO_BIL");
			String id6 = DictionaryDataUtil.getId("CATEGORY_THREE_BIL");
			List<String> idList = new ArrayList<>();
			idList.add(id1);
			idList.add(id2);
			idList.add(id3);
			idList.add(id4);
			idList.add(id5);
			idList.add(id6);
			for (String typeId : typeList) {
				List < SupplierItem > itemsList = supplierItemService.findCategoryList(supplier.getId(), typeId, null);
				if(itemsList != null && itemsList.size() > 0){
					for (SupplierItem supplierItem : itemsList) {
						if(typeId.equals(DictionaryDataUtil.getId("SALES"))){
							supplierAttach.setAuditType("contract_sales_page");
						}else{
							supplierAttach.setAuditType("contract_product_page");
						}
						for (String auditField : idList) {
							supplierAttach.setAuditField(supplierItem.getCategoryId()+"_"+auditField);
							List < SupplierAudit > saleContractList = supplierAuditService.selectByPrimaryKey(supplierAttach);
							if(saleContractList != null && saleContractList.size() > 0){
								saleContractflag &= false;
								saleContractReasons.append("  "+saleContractList.get(0).getSuggest());
							}
						}
					}
				}
			}
			supplierAttach.setAuditType("");
			if(!saleContractflag){
                content = "否。原因：" + saleContractReasons.toString();
			}else{
                content = "是。";
			}
            packageTable("近三年销售合同及银行汇款证明", content, materialMap, newsList);
			dataMap.put("newsListInfo",newsList);

			//基本信息不通过项
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());
			supplierAudit.setAuditType("basic_page");
			List < SupplierAudit > auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer auditReasons = new StringBuffer();
			if(!auditList.isEmpty() && auditList.size() > 0){
				dataMap.put("isData","yes");
				auditReasons.append("不通过。原因：");
				for (SupplierAudit supplierAudit2 : auditList) {
					auditReasons.append(supplierAudit2.getAuditFieldName()).append("：").append(supplierAudit2.getSuggest()).append("；");
				}
			}else{
				dataMap.put("isData","no");
				auditReasons.append("通过。");
			}
			//dataMap.put("auditList",auditList);
			dataMap.put("auditReasons", auditReasons.toString());
			if("0".equals(tableType)){
				//公示的最终意见
				dataMap.put("opinion",opinion == null ? "无" :opinion);
			}else if("3".equals(tableType) || "4".equals(tableType)){
				//最终意见
				SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
				supplierAuditOpinion.setSupplierId(supplier.getId());
				SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByPrimaryKey(supplierAuditOpinion);
				if(auditOpinion !=null){
					dataMap.put("opinion",auditOpinion.getOpinion() == null ? "无" : auditOpinion.getOpinion());
				}else{
					dataMap.put("opinion","无");
				}
			}else{
				dataMap.put("opinion","无");
			}
			
			// 获取供应商选择类型和目录关系
			List<SupplierCategoryOpinion> supplierCategoryList = new ArrayList<>();
			Integer num = 1;
	        Integer firstNode = 0;
	        Integer secondNode = 0;
	        Integer thirdNode = 0;
	        Integer fourthNode = 0;
			//PRODUCT 物资生产  SALES 物资销售
			//查询供应商类型
	        ExpertAuditController expertAuditController = new ExpertAuditController();
			List<String> supplierTypeRelateList = supplierTypeRelateService.findTypeBySupplierId(supplier.getId());
			//查询所有的产品类别
            // 定义小类全部不通过类型自然不同过（不通过类型封装）
            StringBuffer noPassTypeSB = new StringBuffer();
            Map<String ,Object> selectMap = new HashedMap();
			if(supplierTypeRelateList != null && !supplierTypeRelateList.isEmpty()){
				for (String str : supplierTypeRelateList) {
					String typeName = "";
					String typeId = "" ;
					if(str.equals("PRODUCT") || str.equals("SALES")){
						typeId = "GOODS";
					}else {
						typeId = str;
					}
					if(str.equals("PRODUCT")){
						typeName = "物资生产";
					}else if(str.equals("SALES")){
						typeName = "物资销售";
					}else if(str.equals("PROJECT")){
						typeName = "工程";
					}else if(str.equals("SERVICE")){
						typeName = "服务";
					}
					num ++;
					SupplierCategoryOpinion supplierCategoryOpinion = new SupplierCategoryOpinion();
					supplierCategoryOpinion.setCategoryName(expertAuditController.toChinese(num)+"、"+typeName);
					supplierCategoryOpinion.setCategoryId(DictionaryDataUtil.getId(str));
					supplierCategoryOpinion.setType(str);
					supplierCategoryList.add(supplierCategoryOpinion);
					Map<String, Object> map = new HashMap<>();
					map.put("supplierId", supplier.getId());
					map.put("supplierTypeRelateId",str);
					map.put("typeId", DictionaryDataUtil.getId(typeId));
					List<SupplierCategoryOpinion> supplierCList = supplierTypeRelateService.findSupplierCategoryByTypeId(map);
					for (SupplierCategoryOpinion sct : supplierCList) {
						sct.setType(str);
        				Map<String, Object> map22 = new HashMap<>();
        				map22.put("categoryId", sct.getCategoryId());
        				map22.put("typeId", DictionaryDataUtil.getId(typeId));
                		Integer sctCount = expertCategoryService.findCountParent(map22);
                		String string = "";
                		if(sctCount == 1){
                			firstNode ++;
                			string = "（"+expertAuditController.toChinese(firstNode)+"）";
                			secondNode = 0;
                			thirdNode = 0;
                			fourthNode = 0;
                		}else if(sctCount == 2){
                			secondNode ++;
                			string = secondNode+".";
                			thirdNode = 0;
                			fourthNode = 0;
                		}else if(sctCount == 3){
                			thirdNode ++;
                			fourthNode = 0;
                			string = "（"+thirdNode+"）";
                		}else if(sctCount == 4){
                			fourthNode ++;
                			string = toEnglish(fourthNode)+".";
                		}
                		sct.setCategoryName(string + sct.getCategoryName());
					}
        			firstNode = 0;
					supplierCategoryList.addAll(supplierCList);

					// 查询供应商选择的所有小类
                    // 定义不通过数量
                    int noPassCount = 0;
					selectMap.put("supplierId", supplier.getId());
					selectMap.put("type", str);
                    List<SupplierItem> supplierItems = supplierAuditService.selectSupplierItemByType(selectMap);
                    SupplierAudit supplierAudit1 = null;
                    List<SupplierAudit> suList;
                    if(supplierItems != null && !supplierItems.isEmpty()){
                        for (SupplierItem supplierItem : supplierItems){
                            // 遍历查询是否存在不同过的小类
                            supplierAudit1 = new SupplierAudit();
                            supplierAudit1.setSupplierId(supplier.getId());
                            supplierAudit1.setAuditField(supplierItem.getCategoryId());
                            if (str.equals("SALES")) {
                                supplierAudit1.setAuditType("items_sales_page");
                            } else{
                                supplierAudit1.setAuditType("items_product_page");
                            }
                            suList = supplierAuditService.selectByPrimaryKey(supplierAudit1);
                            if(suList != null && !suList.isEmpty()){
                                noPassCount ++;
                            }
                        }
                        if(noPassCount == supplierItems.size()){
                            noPassTypeSB.append(str + ",");
                        }
                    }
                    selectMap.clear();
                }
			}
			//拼接产品类别审核意见
			// 记录不通过的类型
			String noPassType = "";
			// 判断小类全部不通过时，大类也不通过，否则反之
			for (SupplierCategoryOpinion sco : supplierCategoryList) {
				SupplierAudit supplierAudit11 = new SupplierAudit();
				supplierAudit11.setSupplierId(supplier.getId());
				supplierAudit11.setAuditField(sco.getCategoryId());
				if(sco.getParentId() != null && sco.getType() != null && sco.getType().equals("PRODUCT")){
					supplierAudit11.setAuditType("items_product_page");
				}
				if(sco.getParentId() != null && sco.getType() != null && sco.getType().equals("SALES")){
					supplierAudit11.setAuditType("items_sales_page");
				}
                List<SupplierAudit> suList;
				// 小类全部不通过的情况
				if(noPassTypeSB.toString().contains(sco.getType())){
                    suList = supplierAuditService.selectByPrimaryKey(supplierAudit11);
                    if(suList != null && !suList.isEmpty()){
                        sco.setOpinion("不通过。原因：" + suList.get(0).getSuggest());
                    }else{
                        sco.setOpinion("不通过。");
                    }
                    continue;
                }
				suList = supplierAuditService.selectByPrimaryKey(supplierAudit11);
				// 如果不包含类型则说明是供应商类型
				if(StringUtils.isEmpty(sco.getParentId()) && suList != null && !suList.isEmpty()){
                    DictionaryData dictionaryData = DictionaryDataUtil.findById(sco.getCategoryId());
                    if(dictionaryData != null){
                        noPassType = dictionaryData.getCode();
                    }
                    // 设置类型不通过
                    sco.setOpinion("不通过。原因：" + suList.get(0).getSuggest());
                    continue;
				}
				if(noPassType.equals(sco.getType())){
                    sco.setOpinion("不通过。");
                }else if(suList != null && !suList.isEmpty()){
                    sco.setOpinion("不通过。原因：" + suList.get(0).getSuggest());
                } else {
                    sco.setOpinion("通过。");
                }
			}
			
			dataMap.put("supplierCategoryList",supplierCategoryList);
			dataMap.put("sc","1");
			
		}
		
		if("4".equals(tableType)){
			//企业性质
			String businessNature = businessNature(supplier.getBusinessNature());
			dataMap.put("businessNature", businessNature);
			//营业执照类型
			String businessType = businessType(supplier.getBusinessType());
			dataMap.put("businessType", businessType);
			//统一社会信用代码
			dataMap.put("creditCode", supplier.getCreditCode() == null ? "":supplier.getCreditCode());
			//注册人姓名
			dataMap.put("legalName", supplier.getLegalName() == null ? "":supplier.getLegalName());
			//身份证号
			dataMap.put("legalIdCard", supplier.getLegalIdCard() == null ? "":supplier.getLegalIdCard());
			
			//不通过项
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplier.getId());
			List < SupplierAudit > auditList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			if(!auditList.isEmpty() && auditList.size() > 0){
				dataMap.put("isData","yes");
			}else{
				dataMap.put("isData","no");
			}
			dataMap.put("auditList",auditList);
			
			if("0".equals(tableType)){
				//公示的最终意见
				dataMap.put("opinion",opinion == null ? "无" :opinion);
			}else if("3".equals(tableType) || "4".equals(tableType)){
				//最终意见
				SupplierAuditOpinion supplierAuditOpinion = new SupplierAuditOpinion();
				supplierAuditOpinion.setSupplierId(supplier.getId());
				SupplierAuditOpinion auditOpinion = supplierAuditOpinionService.selectByPrimaryKey(supplierAuditOpinion);
				if(auditOpinion !=null){
					dataMap.put("opinion",auditOpinion.getOpinion() == null ? "无" : auditOpinion.getOpinion());
				}else{
					dataMap.put("opinion","无");
				}
			}else{
				dataMap.put("opinion","无");
			}
		}
		//审核表
		if("3".equals(tableType) || "0".equals(tableType)){
			newFileName = WordUtil.createWord(dataMap, "supplierOneAudit.ftl", "supplierOneAudit", request);
		}
		//复核表
		if("4".equals(tableType)){
			newFileName = WordUtil.createWord(dataMap, "supplierTwoAudit.ftl", "supplierTwoAudit", request);
		}
		
		return newFileName;
	}
	
	/**
	 * @Title: businessNature
	 * @date 2017-5-24 下午5:02:12  
	 * @Description:在数据字典里查询营业执照类型
	 * @param @return      
	 * @return String
	 */
	public String businessType(String businessTypeId){
		String businessType="";
		List < DictionaryData > list = DictionaryDataUtil.find(17);
		if(businessTypeId !=null){
			for(int i = 0; i < list.size(); i++) {
				if(businessTypeId.equals(list.get(i).getId())) {
					businessType = list.get(i).getName();
					break;
				}
			}
		}
		if("".equals(businessType)){
			businessType = businessTypeId;
		}
		return businessType;
	}
	
	/**
	 * @Title: businessNature
	 * @date 2017-5-24 下午5:02:12  
	 * @Description:/在数据字典里查询企业性质
	 * @param @return      
	 * @return String
	 */
	public String businessNature(String businessNatureId){
		List < DictionaryData > businessList = DictionaryDataUtil.find(32);
		String businessNature = "";
		if(businessNatureId !=null){
			for(int i = 0; i < businessList.size(); i++) {
				if(businessNatureId.equals(businessList.get(i).getId())) {
					businessNature = businessList.get(i).getName();
					break;
				}
			}
		}
		return businessNature;
	}

	/**
	 * @Title: signature
	 * @author XuQing 
	 * @date 2017-4-3 下午12:18:11  
	 * @Description:添加签字人员校验唯一
	 * @param @param signature      
	 * @return void
	 */
	@RequestMapping(value = "/signature", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String signature(String ids, HttpServletResponse response) {
		 	List<String> list = new ArrayList<String>();
		 	String[] split = ids.split(",");
		 	list = Arrays.asList(split);
		   //唯一判断
			SupplierSignature supplierSignature = new SupplierSignature();
			for(String id : list){
				supplierSignature.setSupplierId(id);
				List<SupplierSignature> selectByExpertId = supplierSignatureService.selectBySupplierId(supplierSignature);
				if(!selectByExpertId.isEmpty()){
					Supplier supplier = supplierAuditService.supplierById(id);
				   return supplier.getSupplierName();
				   
				}
			}
		 return "yes";
	}
	
	
	/**
	 * @Title: signature
	 * @author XuQing 
	 * @date 2017-4-6 下午2:48:52  
	 * @Description:跳转添加签字人员页面
	 * @param @param ids
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/addSignature")
	public String signature(String ids, Model model) {
		model.addAttribute("ids", ids);
		return "ses/sms/supplier_audit/add_auditpersonnel";
	}
	
    
	/**
	 * @Title: saveSignature
	 * @author XuQing 
	 * @date 2017-4-6 下午1:17:20  
	 * @Description:添加签字人员
	 * @param @param purchaseRequiredFormBean
	 * @param @param batchNo
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "/saveSignature", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveSignature(PurchaseRequiredFormBean purchaseRequiredFormBean,String batchNo,String ids) {
		String[] strs = ids.split(",");
		List<SupplierSignature> list = purchaseRequiredFormBean.getSupplierSignatureList();
		if(list.size()>0){
			for(String id : strs){
				for(SupplierSignature ss:list){
					ss.setBatch(batchNo);
					ss.setSupplierId(id);
					ss.setCreatedAt(new Date());
					supplierSignatureService.add(ss);
				}
			}
		}
		return "";
	}
	
	
	/**
	 * @Title: auditOpinion
	 * @date 2017-6-9 下午3:20:35
	 * @Description:记录审核意见
	 * @param @param supplierAuditOpinion
	 * @return void
	 */
	/*public void auditOpinion(SupplierAuditOpinion supplierAuditOpinion) {
		supplierAuditOpinion.setCreatedAt(new Date());
		supplierAuditOpinionService.insertSelective(supplierAuditOpinion);
	}*/
	
	/**
	 * @Title: updateStatus
	 * @date 2016-9-20 下午7:32:49  
	 * @Description: 根据供应商id更新审核状态
	 * @param @param request
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("/updateStatusOfPublictity")
	@ResponseBody
	public JdcgResult updateStatusOfPublictity(Supplier supplier, SupplierAudit supplierAudit){
		String supplierId = supplierAudit.getSupplierId();
		//更新状态
		supplier.setId(supplierId);
		supplier.setAuditDate(new Date());
		//审核人
		// supplier.setAuditor(user.getRelName());
		//还原审核暂存状态
		supplier.setAuditTemporary(0);
		// 设置修改时间
		supplier.setUpdatedAt(new Date());
		supplierAuditService.updateStatus(supplier);
		return JdcgResult.ok();
	}


	/**
	 *
	 * Description:查询选择和未通过的产品类别
	 *
	 * @author Easong
	 * @version 2017/7/12
	 * @param [supplierPublicity]
	 * @since JDK1.7
	 */
	@RequestMapping("/selectChooseOrNoPassCate")
	@ResponseBody
	public SupplierPublicity selectChooseOrNoPassCate(SupplierPublicity supplierPublicity){
		return supplierAuditService.selectChooseOrNoPassCate(supplierPublicity);
	}

	@RequestMapping("/uploadApproveFile")
	public String uploadApproveFile(Model model, String supplierId, String sign){
	    /**
	     *
	     * Description:上传批准审核表
	     *
	     * @author Easong
	     * @version 2017/7/12
	     * @param [supplier]
	     * @param [model]
	     * @since JDK1.7
	     */
	    // 查询供应商
        Supplier supplier = supplierAuditService.supplierById(supplierId);
	    Integer supplierStatus = null;
        if(supplier != null){
            supplierStatus = supplier.getStatus();
        }
        model.addAttribute("supplierId", supplierId);
        model.addAttribute("supplierStatus", supplierStatus);
	    model.addAttribute("sign", sign);
	    model.addAttribute("supplier", supplier);
        // 设置文件上传项
        fileUploadItem(model);
	    return "ses/sms/supplier_audit/audit_attach_upload";
    }

    @RequestMapping("/saveAuditOpinion")
	@ResponseBody
	public JdcgResult auditOpinion(SupplierAuditOpinion supplierAuditOpinion, String vertifyFlag) {
		/**
		 *
		 * Description:记录审核意见
		 *
		 * @author Easong
		 * @version 2017/7/12
		 * @param [supplierAuditOpinion]
		 * @since JDK1.7
		 */
		return supplierAuditOpinionService.insertSelective(supplierAuditOpinion, vertifyFlag);
	}


    @RequestMapping("/updateStatusAjax")
    @ResponseBody
    public JdcgResult updateStatusAjax(Supplier supplier, SupplierAudit supplierAudit) {
	    /**
	     *
	     * Description:修改供应商状态-ajax
	     *
	     * @author Easong
	     * @version 2017/7/13
	     * @param [user, request, supplier, supplierAudit, supplierAuditOpinion]
	     * @since JDK1.7
	     */
        String supplierId = supplierAudit.getSupplierId();
        // 审核前判断是否有通过项和未通过项--是否符合通过要求
		// 查询供应商审核意见  判断点击审核结束按钮是否是审核通过或者审核不通过状态
        SupplierAuditOpinion supplierAuditOpinion = supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 0);
        // 选择审核通过
        if(supplierAuditOpinion != null && supplierAuditOpinion.getFlagAduit() != null){
            if(supplierAuditOpinion.getFlagAduit() == 1){
                // 审核通过，公示
                supplier.setStatus(-3);
            }else if(supplierAuditOpinion.getFlagAduit() == 0){
                // 审核未通过
                supplier.setStatus(3);
            }
        }else {
            return JdcgResult.build(500, "审核意见不能为空");
        }

        //Todos todos = new Todos();
        //更新状态
        supplier.setId(supplierId);
        supplier.setAuditDate(new Date());
        //审核人
        //supplier.setAuditor(user.getRelName());

        //还原审核暂存状态
        supplier.setAuditTemporary(0);
        // 设置修改时间
        supplier.setUpdatedAt(new Date());
        supplierAuditService.updateStatus(supplier);

        /*if(supplier.getStatus() != null && supplier.getStatus() == -3){
            // 供应商分级要素得分
            supplier.setLevelScoreProduct(SupplierLevelUtil.getScore(supplier.getId(), "PRODUCT"));
            supplier.setLevelScoreSales(SupplierLevelUtil.getScore(supplier.getId(), "SALES"));
            supplier.setLevelScoreService(SupplierLevelUtil.getScore(supplier.getId(), "SERVICE"));
            if(supplier.getProcurementDepId() != null){
                supplierService.updateSupplierProcurementDep(supplier);
            }
        }*/

        //更新待办
        supplier = supplierAuditService.supplierById(supplierId);
        //String supplierName = supplier.getSupplierName();

        //记录审核不通过的供应商
        if(supplier.getStatus() == 3){
            SupplierAuditNot supplierAuditNot = new SupplierAuditNot();
            supplierAuditNot.setCreditCode(supplier.getCreditCode());
            supplierAuditNot.setSupplierId(supplierId);
            supplierAuditNot.setCreatedAt(new Date());
            supplierAuditNotService.insertSelective(supplierAuditNot);
        }

        /**
         * 更新待办(已完成)
         */
        if(supplier.getStatus() != null && supplier.getStatus() == -3 || supplier.getStatus() == 3) {
            todosService.updateIsFinish("supplierAudit/essential.html?supplierId=" + supplierId);
        }

        //审核完更新状态
        List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
        if(reasonsList.size() != 0) {
            supplierAudit.setStatus(supplier.getStatus());
            supplierAudit.setSupplierId(supplierId);
            supplierAuditService.updateStatusById(supplierAudit);
        }
        return JdcgResult.ok();
    }

    @RequestMapping("/isHaveOpinion")
    @ResponseBody
    public JdcgResult isHaveOpinion(String supplierId){
		/**
		 *
		 * Description:校验审核意见
		 *
		 * @author Easong
		 * @version 2017/7/18
		 * @param [supplierId]
		 * @since JDK1.7
		 */
		return JdcgResult.ok(supplierAuditOpinionService.selectByExpertIdAndflagTime(supplierId, 0));
    }

	/**
	 * @deprecated: 点击审核通过复选框校验审核通过项
	 *
	 * @Author:Easong
	 * @Date:Created in 2017/7/22
	 * @param: [supplierId]
	 * @return: common.utils.JdcgResult
	 *
	 */
	@RequestMapping("/vertifyAuditItem")
	@ResponseBody
    public JdcgResult vertifyAuditItem(String supplierId){
		// 点击通过按钮时判断
		JdcgResult selectAndVertifyAuditItem = supplierAuditService.selectAndVertifyAuditItem(supplierId);
		if(selectAndVertifyAuditItem.getStatus() != 200) {
			//如果有错误信息则直接返回提示操作
			return selectAndVertifyAuditItem;
		}
		return JdcgResult.ok();
	}

	@RequestMapping("/vertifyAuditNoPassItem")
	@ResponseBody
	public JdcgResult vertifyAuditNoPassItem(String supplierId, String flag){
    	/**
    	 * @deprecated: 点击审核不通过复选框校验审核不通过项
		 * 是否为0，如果为0则提示没有审核不通过项
    	 *
    	 * @Author:Easong
    	 * @Date:Created in 2017/7/22
    	 * @param: [supplierId]
    	 * @return: common.utils.JdcgResult
    	 *
    	 */
		// 点击审核不通过复选框时判断
		return supplierAuditService.selectAuditNoPassItemCount(supplierId, flag);
	}
	
	/**
	 * 校验退回修改
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/vertifyReturnToModify")
	@ResponseBody
	public JdcgResult vertifyReturnToModify(String supplierId){
		return supplierAuditService.vertifyReturnToModify(supplierId);
	}
	
	/**
	 * 校验预审核
	 * @param supplierId
	 * @param flag
	 * @return
	 */
	@RequestMapping("/vertifyYushenhe")
	@ResponseBody
	public JdcgResult vertifyYushenhe(String supplierId, String flag){
		return supplierAuditService.vertifyYushenhe(supplierId, flag);
	}
	
	/**
	 * 更改审核记录状态
	 * @param ids
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateReturnStatus")
	@ResponseBody
	public JdcgResult updateReturnStatus(String ids, Integer status){
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			return new JdcgResult(501, "登录超时", null);
		}
		if(StringUtils.isBlank(ids) || status == null || (status != 1 && status != 3 && status != 4 && status != 5 && status != 6)){
			return new JdcgResult(504, "参数错误", null);
		}
		return supplierAuditService.updateReturnStatus(ids, status);
	}
	
	/**
	 * 撤销审核记录（单条）
	 * @param supplierAudit
	 * @return
	 */
	@RequestMapping("/cancelAudit")
	@ResponseBody
	public JdcgResult cancelAudit(SupplierAudit supplierAudit){
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			return new JdcgResult(501, "登录超时", null);
		}
		supplierAudit.setIsDeleted(0);
		List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,1,2});
		if(auditList != null && auditList.size() > 0){
			supplierAudit.setId(auditList.get(0).getId());
		}
		int result = 0;
		/*int status = 0;
		String auditType = supplierAudit.getAuditType();
		if("supplierType_page".equals(auditType) || auditType.startsWith("items_")){
			status = 6;
		}else{
			status = 5;
		}*/
		supplierAudit.setReturnStatus(5);
		supplierAudit.setUpdatedAt(new Date());
		result = supplierAuditService.updateByIdSelective(supplierAudit);
		if(result > 0){
			return new JdcgResult(500, "撤销成功", null);
		}else{
			return new JdcgResult(502, "撤销失败", null);
		}
	}
	
	/**
	 * 撤销审核记录（多条）
	 * @param supplierAuditList
	 * @return
	 */
	@RequestMapping("/cancelAuditMuti")
	@ResponseBody
	public JdcgResult cancelAuditMuti(@RequestBody List<SupplierAudit> supplierAuditList){
		User user = (User) request.getSession().getAttribute("loginUser");
		if(user == null){
			return new JdcgResult(501, "登录超时", null);
		}
		if(supplierAuditList != null && supplierAuditList.size() > 0){
			int result = 0;
			for(SupplierAudit supplierAudit : supplierAuditList){
				supplierAudit.setIsDeleted(0);
				List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,2});
				if(auditList != null && auditList.size() > 0){
					supplierAudit.setId(auditList.get(0).getId());
				}
				/*int status = 0;
				String auditType = supplierAudit.getAuditType();
				if("supplierType_page".equals(auditType) || auditType.startsWith("items_")){
					status = 6;
				}else{
					status = 5;
				}*/
				supplierAudit.setReturnStatus(5);
				supplierAudit.setUpdatedAt(new Date());
				result += supplierAuditService.updateByIdSelective(supplierAudit);
			}
			if(result > 0){
				return new JdcgResult(500, "撤销成功", null);
			}else{
				return new JdcgResult(502, "撤销失败", null);
			}
		}else{
			return new JdcgResult(504, "参数错误", null);
		}
	}
	
	/**
	 * 校验审核人
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/validateAuditor")
	@ResponseBody
	public JdcgResult validateAuditor(String supplierId){
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(0, "对不起，您没有登录或登录失效！", null);
		}
		Supplier supplier = supplierService.selectById(supplierId);
		User user = (User) loginUserSession;
		if(supplier.getAuditor() != null && !supplier.getAuditor().equals(user.getRelName())){
			return new JdcgResult(0, "对不起，您没有审核权限！", null);
		}
		return new JdcgResult(1, null, null);
	}
	
	/**
	 * 更新审核人
	 * @param supplierId
	 * @param auditor
	 * @return
	 */
	@RequestMapping("/updateAuditor")
	@ResponseBody
	public JdcgResult updateAuditor(String supplierId, String auditor){
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(0, "对不起，您没有登录或登录失效！", null);
		}
		Supplier supplier = supplierService.selectById(supplierId);
		if(StringUtils.isBlank(supplier.getAuditor()) && StringUtils.isNotBlank(auditor)){
			Supplier updateSupplier = new Supplier();
			updateSupplier.setId(supplierId);
			updateSupplier.setAuditor(auditor);
			supplierAuditService.updateStatus(updateSupplier);
		}
		return new JdcgResult(1, null, null);
	}
	
	/**
	 * 异步获取供应商信息
	 * @param supplierId
	 * @return
	 */
	@RequestMapping("/ajaxSupplier")
	@ResponseBody
	public JdcgResult ajaxSupplier(String supplierId){
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(0, "对不起，您没有登录或登录失效！", null);
		}
		Supplier supplier = supplierService.selectById(supplierId);
		return new JdcgResult(1, "OK", supplier);
	}
	
	/**
	 * 异步获取旧的审核记录
	 * @param supplierAudit
	 * @return
	 */
	@RequestMapping("/ajaxOldAudit")
	@ResponseBody
	public JdcgResult ajaxOldAudit(SupplierAudit supplierAudit){
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(-1, "对不起，您没有登录或登录失效！", null);
		}
		supplierAudit.setIsDeleted(1);
		int count = supplierAuditService.countAuditRecords(supplierAudit, new Integer[]{0,2,4});
		if(count > 0){
			return new JdcgResult(0, "该条信息已审核过并退回过！", null);
		}
		supplierAudit.setIsDeleted(0);
		List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,1,2});
		if(auditList != null && auditList.size() > 0){
			return new JdcgResult(1, "OK", auditList.get(0));
		}
		return null;
	}
	
	/**
	 * 异步获取旧的审核记录
	 * @param supplierAudit
	 * @return
	 */
	@RequestMapping("/ajaxOldAuditMuti")
	@ResponseBody
	public JdcgResult ajaxOldAuditMuti(@RequestBody List<SupplierAudit> supplierAuditList){
		// 获取登录用户
		Object loginUserSession = request.getSession().getAttribute("loginUser");
		if(loginUserSession == null){
			return new JdcgResult(-1, "对不起，您没有登录或登录失效！", null);
		}
		if(null != supplierAuditList && !supplierAuditList.isEmpty()){
			String suggest = "";
			Set<String> suggestSet = new HashSet<String>();
			for (SupplierAudit supplierAudit : supplierAuditList) {
				supplierAudit.setIsDeleted(1);
				int auditCount = supplierAuditService.countAuditRecords(supplierAudit, new Integer[]{0,2});
				if(auditCount > 0){
					return new JdcgResult(0, "选择中存在已审核并退回过的记录", null);
				}
				supplierAudit.setIsDeleted(0);
				List<SupplierAudit> auditList = supplierAuditService.getAuditRecords(supplierAudit, new Integer[]{0,2});
				if(auditList != null && auditList.size() > 0){
					suggest = auditList.get(0).getSuggest();
					suggestSet.add(suggest);
				}
			}
			if(suggestSet.size() == 1){
				return new JdcgResult(1, "OK", suggest);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Description: 数字转化字母
	 * 
	 * @author zhang shubin
	 * @data 2017年8月29日
	 * @param 
	 * @return
	 */
	public String toEnglish(Integer num){
    	if(num > 0){
	    	String a[] = {"","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	    	if(num <= 26){
	    		return a[num];
	    	}else if(num % 26 == 0){
	    		return a[num/26 - 1] + "z";
	    	}else if(num % 26 <= 26){
	    		return a[num/26] + a[num%26];
	    	}else {
	    		return "";
	    	}
    	}else{
    		return "";
    	}
    }
	
}