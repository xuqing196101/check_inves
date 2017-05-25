package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

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
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCateTree;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierPorjectQua;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierSignature;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.QualificationService;
import ses.service.bms.TodosService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAptituteService;
import ses.service.sms.SupplierAuditNotService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierCertEngService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierMatEngService;
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
import bss.formbean.PurchaseRequiredFormBean;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import common.constant.StaticVariables;
import common.model.UploadFile;
import common.service.UploadService;

/**
 * <p>Title:SupplierAuditController </p>
 * <p>Description: 供应商审核控制类</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:14:36
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
	private SupplierCertEngService supplierCertEngService;
	
	@Autowired
	private  SupplierMatEngService supplierMatEngService;
	
	@Autowired
	private SupplierAptituteService supplierAptituteService;
	
	@Autowired
	private SupplierPorjectQuaService supplierPorjectQuaService;
	
	/**
	 * @Title: essentialInformation
	 * @author Xu Qing
	 * @date 2016-9-12 下午7:14:09  
	 * @Description: 基本信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(HttpServletRequest request, Supplier supplier, String supplierId, Integer sign) {
		request.setAttribute("sign", sign);
		
		SupplierModify supplierModify= new SupplierModify();
		supplierModify.setSupplierId(supplierId);
		//先删除对比的旧数据
		supplierModifyService.deleteByType(supplierModify);
		// 插入对比后的数据
		supplierModifyService.insertModifyRecord(supplierModify);
		
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		supplier = supplierAuditService.supplierById(supplierId);

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
		List < SupplierBranch > supplierBranchList = supplierBranchService.findSupplierBranch(supplierId);
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
		List < Area > privnce = areaService.findRootArea();
		request.setAttribute("privnce", privnce);
		Area area = new Area();
		//地址信息里地址
		area = areaService.listById(supplier.getAddress());
		String sonAddress = area.getName();
		request.setAttribute("sonAddress", sonAddress);
		for(int i = 0; i < privnce.size(); i++) {
			if(area.getParentId().equals(privnce.get(i).getId())) {
				String parentAddress = privnce.get(i).getName();
				request.setAttribute("parentAddress", parentAddress);
			}
		}

		//注册联系人里地址
		area = areaService.listById(supplier.getConcatCity());
		String sonConcatProvince = area.getName();
		request.setAttribute("sonConcatProvince", sonConcatProvince);
		for(int i = 0; i < privnce.size(); i++) {
			if(area.getParentId().equals(privnce.get(i).getId())) {
				String parentConcatProvince = privnce.get(i).getName();
				request.setAttribute("parentConcatProvince", parentConcatProvince);
			}
		}

		//军队业务联系人里地址
		area = areaService.listById(supplier.getArmyBuinessCity());
		String sonArmyBuinessProvince = area.getName();
		request.setAttribute("sonArmyBuinessProvince", sonArmyBuinessProvince);
		for(int i = 0; i < privnce.size(); i++) {
			if(privnce.get(i).getId() !=null && area.getParentId().equals(privnce.get(i).getId())) {
				String parentArmyBuinessProvince = privnce.get(i).getName();
				request.setAttribute("parentArmyBuinessProvince", parentArmyBuinessProvince);
			}
		}

		//生产经营地址
		List < SupplierAddress > supplierAddress = supplierAddressService.queryBySupplierId(supplierId);
		if(!supplierAddress.isEmpty() && supplierAddress.size() > 0 ){
			for(Area a: privnce) {
				for(SupplierAddress s: supplierAddress) {
					if(a.getId().equals(s.getParentId())) {
						s.setParentName(a.getName());
					}
				}
			}
		}
		
		request.setAttribute("supplierAddress", supplierAddress);
		
		//售后服务机构一览表
		List<SupplierAfterSaleDep> listSupplierAfterSaleDep = supplierService.get(supplierId).getListSupplierAfterSaleDep();
		request.setAttribute("listSupplierAfterSaleDep",listSupplierAfterSaleDep);
		
		/**
		 * 查出修改前的信息
		 */
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
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
			
			//input
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
			SupplierModify supplierFileModify= new SupplierModify();
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
		if(supplier.getStatus() == 0 || supplier.getStatus() == 4 || supplier.getStatus() == 5){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					passedField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedField", passedField);
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
	public String financialInformation(HttpServletRequest request, String supplierId, Integer supplierStatus, Integer sign) {
		request.setAttribute("sign", sign);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("supplierStatus", supplierStatus);
		
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		//文件
		if(supplierId != null) {
			List < SupplierFinance > supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
			request.setAttribute("financial", supplierFinance);
		}

		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		//查出财务修改前的信息
		if(supplierStatus != null && supplierStatus == 0) {
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
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					passedField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedField", passedField);
		}
		
		/**
		 * 退回修改后的附件
		 */
		if(supplierStatus == 0){
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null){
					if(m.getRelationId() !=null){
						fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
					}
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
	public String shareholderInformation(HttpServletRequest request, SupplierStockholder supplierStockholder , Integer supplierStatus, Integer sign) {
		String supplierId = supplierStockholder.getSupplierId();
		request.setAttribute("supplierStatus", supplierStatus);
		request.setAttribute("sign", sign);
		
		List < SupplierStockholder > list = supplierAuditService.ShareholderBySupplierId(supplierId);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
		
		/**
		 * 查出财务修改前的信息
		 */
		if(supplierStatus != null && supplierStatus == 0) {
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
		
		
			/**
			 * 退回修改后的附件
			 */
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() !=null){
					fileModifyField.append(m.getRelationId() + m.getBeforeField() + ",");
				}
			}
			request.setAttribute("fileModifyField", fileModifyField);
		}
		
		//下一步的跳转页面
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
		request.setAttribute("url", url);
		
		//回显未通过字段
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			SupplierAudit supplierAudit = new SupplierAudit();
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("basic_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					passedField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedField", passedField);
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
		supplierMatPro = supplierService.get(supplierId).getSupplierMatPro();
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
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
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
			List < SupplierRegPerson > listSupplierRegPersons = supplierService.get(supplierId).getSupplierMatEng().getListSupplierRegPersons();
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

	@RequestMapping("supplierType")
	public String supplierType(HttpServletRequest request, SupplierMatSell supplierMatSell, SupplierMatPro supplierMatPro, SupplierMatEng supplierMatEng, SupplierMatServe supplierMatSe, String supplierId, Integer supplierStatus, Integer sign) {
		request.setAttribute("supplierStatus", supplierStatus);
		request.setAttribute("sign", sign);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);

		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		/**
		 * 退回修改前的信息
		 */
		Supplier supplier = supplierAuditService.supplierById(supplierId);

		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_pro_page");
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			//产品研发能力
			StringBuffer fieldProOne = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getBeforeField();
				fieldProOne.append(beforeField + ",");
			}
			request.setAttribute("fieldProOne", fieldProOne);
			//资质证书
			StringBuffer fieldProTwo = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldProTwo.append(beforeField + ",");
			}
			request.setAttribute("fieldProTwo", fieldProTwo);
			
			
			//供应商类型
			supplierModify.setModifyType("supplier_type");
			StringBuffer fieldType = new StringBuffer();
			List<SupplierModify> typeList = supplierModifyService.selectBySupplierId(supplierModify);
			for(SupplierModify m : typeList){
				fieldType.append(m.getBeforeField() + ",");
			}
			request.setAttribute("fieldType", fieldType);
		}
		
		/**
		 * 供应商类型
		 */
		//供应商类code
		List < SupplierTypeRelate > typeIds = supplierTypeRelateService.queryBySupplier(supplierId);
		String supplierTypeCode = "";
		for(SupplierTypeRelate s: typeIds) {
			supplierTypeCode += s.getSupplierTypeId() + ",";
		}
		request.setAttribute("supplierTypeCode", supplierTypeCode);
		
		
		List < DictionaryData > list = DictionaryDataUtil.find(6);
		for(int i = 0; i < list.size(); i++) {
			String code = list.get(i).getCode();
			if(code.equals("GOODS")) {
				list.remove(list.get(i));
			}
		}
		request.setAttribute("supplieType", list);
		
		List < DictionaryData > wlist = DictionaryDataUtil.find(8);
		request.setAttribute("wlist", wlist);

		/**
		 * 生产
		 */
		//资质资格证书信息
		List < SupplierCertPro > materialProduction = supplierAuditService.findBySupplierId(supplierId);
		for(int i = 0; i < materialProduction.size() - 1; i++) {
			for(int j = materialProduction.size() - 1; j > i; j--) {
				if(materialProduction.get(j).getId().equals(materialProduction.get(i).getId())) {
					materialProduction.remove(j);
				}
			}
		}
		supplierMatPro = supplierService.get(supplierId).getSupplierMatPro();
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("materialProduction", materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);
		
		
		//回显未通过字段
		SupplierAudit supplierAudit = new SupplierAudit();
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("mat_pro_page");
			List < SupplierAudit > reasonsProList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedProField = new StringBuffer();
			if(!reasonsProList.isEmpty()){
				for(SupplierAudit a : reasonsProList){
					passedProField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedProField", passedProField);
		}
		

		/**
		 * 销售
		 */
		//资质资格证书
		List < SupplierCertSell > supplierCertSell = supplierAuditService.findCertSellBySupplierId(supplierId);
		for(int i = 0; i < supplierCertSell.size() - 1; i++) {
			for(int j = supplierCertSell.size() - 1; j > i; j--) {
				if(supplierCertSell.get(j).getId().equals(supplierCertSell.get(i).getId())) {
					supplierCertSell.remove(j);
				}
			}
		}
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
		
		//资质证书退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_sell_page");
			supplierModify.setListType(6);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldSell = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldSell.append(beforeField + ",");
			}
			request.setAttribute("fieldSell", fieldSell);
		}
		
		//回显未通过字段
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			supplierAudit.setAuditType("mat_sell_page");
			List < SupplierAudit > reasonsSellList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedSellField = new StringBuffer();
			if(!reasonsSellList.isEmpty()){
				for(SupplierAudit a : reasonsSellList){
					passedSellField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedSellField", passedSellField);
		}
		/**
		 * 工程
		 */
		if(supplierId != null) {
			//资质资格证书信息
			List < SupplierCertEng > supplierCertEng = supplierAuditService.findCertEngBySupplierId(supplierId);
			for(int i = 0; i < supplierCertEng.size() - 1; i++) {
				for(int j = supplierCertEng.size() - 1; j > i; j--) {
					if(supplierCertEng.get(j).getId().equals(supplierCertEng.get(i).getId())) {
						supplierCertEng.remove(j);
					}
				}
			}
			request.setAttribute("supplierCertEng", supplierCertEng);

			//资质资格信息
			List < SupplierAptitute > supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
			for(int i = 0; i < supplierAptitute.size() - 1; i++) {
				for(int j = supplierAptitute.size() - 1; j > i; j--) {
					if(supplierAptitute.get(j).getId().equals(supplierAptitute.get(i).getId())) {
						supplierAptitute.remove(j);
					}
				}
			}
			//资质登记
			List < DictionaryData > businessList = DictionaryDataUtil.find(31);
			for(DictionaryData data : businessList){
				for(SupplierAptitute a:supplierAptitute){
					if(data.getId().equals(a.getAptituteLevel())){
						a.setAptituteLevel(data.getName());
					}
				}
			}
			//资质类型
			request.setAttribute("typeList", qualificationService.findList(null, Integer.MAX_VALUE,null, 4));
			request.setAttribute("supplierAptitutes", supplierAptitute);

			//组织结构
			supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
			request.setAttribute("supplierMatEngs", supplierMatEng);

			//注册人人员
			SupplierMatEng matEng = supplierService.get(supplierId).getSupplierMatEng();
			if(matEng != null) {
				List < SupplierRegPerson > listSupplierRegPersons = matEng.getListSupplierRegPersons();
				request.setAttribute("listRegPerson", listSupplierRegPersons);
			}
			
			//承揽业务范围
			List<Area> listArea= areaService.findRootArea();
			SupplierDictionaryData dictionary = dictionaryDataServiceI.getSupplierDictionary();
			String typeId =  dictionary.getSupplierProContract();
			List<Area> existenceArea = new ArrayList<>();
			for(Area area : listArea){
				String businessId = supplierId + "_" + area.getId();
				List<UploadFile> listUpload = uploadService.getFilesOther(businessId, typeId, "1");
				if(!listUpload.isEmpty()){
					existenceArea.add(area);
				}
			}
			request.setAttribute("rootArea", existenceArea);
		}
		//保密工程业绩-退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_eng_page");
			supplierModify.setListType(5);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldSecrecy = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldSecrecy.append(beforeField + ",");
			}
			request.setAttribute("fieldSecrecy", fieldSecrecy);
		}
		
		//注册人员-退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_eng_page");
			supplierModify.setListType(7);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldRegPersons = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldRegPersons.append(beforeField + ",");
			}
			request.setAttribute("fieldRegPersons", fieldRegPersons);
		}
		
		//证书信息-退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_eng_page");
			supplierModify.setListType(8);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldCertEngs = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldCertEngs.append(beforeField + ",");
			}
			request.setAttribute("fieldCertEngs", fieldCertEngs);
		}
		
		//资质证书信息-退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_eng_page");
			supplierModify.setListType(9);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldAptitutes = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldAptitutes.append(beforeField + ",");
			}
			request.setAttribute("fieldAptitutes", fieldAptitutes);
		}
		
		//回显未通过字段
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			supplierAudit.setAuditType("mat_eng_page");
			List < SupplierAudit > reasonsEngList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedEngField = new StringBuffer();
			if(!reasonsEngList.isEmpty()){
				for(SupplierAudit a : reasonsEngList){
					passedEngField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedEngField", passedEngField);
		}
		
		/**
		 * 服务
		 */
		//资质证书信息
		List < SupplierCertServe > supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		for(int i = 0; i < supplierCertSe.size() - 1; i++) {
			for(int j = supplierCertSe.size() - 1; j > i; j--) {
				if(supplierCertSe.get(j).getId().equals(supplierCertSe.get(i).getId())) {
					supplierCertSe.remove(j);
				}
			}
		}
		request.setAttribute("supplierCertSes", supplierCertSe);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierMatSes", supplierMatSe);
		
		//证书信息-退回修改前的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierModify = new SupplierModify();
			supplierModify.setSupplierId(supplierId);
			supplierModify.setModifyType("mat_serve_page");
			supplierModify.setListType(10);
			List<SupplierModify> editList = supplierModifyService.selectBySupplierId(supplierModify);
			StringBuffer fieldServe = new StringBuffer();
			for(int i = 0; i < editList.size(); i++) {
				String beforeField = editList.get(i).getRelationId() +"_"+ editList.get(i).getBeforeField();
				fieldServe.append(beforeField + ",");
			}
			request.setAttribute("fieldServe", fieldServe);
		}
		
		//回显未通过字段
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			supplierAudit.setAuditType("mat_serve_page");
			List < SupplierAudit > reasonsServeList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedServeField = new StringBuffer();
			if(!reasonsServeList.isEmpty()){
				for(SupplierAudit a : reasonsServeList){
					passedServeField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedServeField", passedServeField);
		}
		supplierAudit.setSupplierId(supplierId);
		supplierAudit.setAuditType("supplierType_page");
		List < SupplierAudit > reasonstTypeList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		StringBuffer passedTypeField = new StringBuffer();
		if(!reasonstTypeList.isEmpty()){
			for(SupplierAudit a : reasonstTypeList){
				passedTypeField.append(a.getAuditField() + ",");
			}
			request.setAttribute("passedTypeField", passedTypeField);
		}

		/**
		 * 退回修改后的附件
		 */
		if(supplier.getStatus() != null && supplier.getStatus() == 0) {
			SupplierModify supplierFileModify= new SupplierModify();
			supplierFileModify.setSupplierId(supplierId);
			supplierFileModify.setModifyType("file");
			StringBuffer fileModifyField = new StringBuffer();
			List<SupplierModify> fileModify = supplierModifyService.selectBySupplierId(supplierFileModify);
			for(SupplierModify m : fileModify){
				if(m.getRelationId() != null){
					if(m.getRelationId() != null){
						fileModifyField.append(m.getRelationId() + m.getBeforeField() + "," );
					}
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
	public void auditReasons(SupplierAudit supplierAudit, HttpServletRequest request, HttpServletResponse response, Supplier supplier) throws IOException {
		User user = (User) request.getSession().getAttribute("loginUser");

		String id = supplierAudit.getSupplierId();
		supplier = supplierAuditService.supplierById(id);

		supplierAudit.setStatus(supplier.getStatus());
		supplierAudit.setCreatedAt(new Date());
		supplierAudit.setUserId(supplier.getProcurementDepId());
		supplierAudit.setUserId(user.getId());

		//审核时只要填写理由，就不通过
		/*		supplier.setId(id);
				if(status==0){
					supplier.setStatus(2); //审核不通过
					supplierAuditService.updateStatus(supplier);
				}
				if(status==1){
					supplier.setStatus(4); //复核不通过
					supplierAuditService.updateStatus(supplier);af
				}*/

		//唯一检验
		String auditField = supplierAudit.getAuditField();
		String auditType = supplierAudit.getAuditType();
		String auditFieldName = supplierAudit.getAuditFieldName();
		String auditContent = supplierAudit.getAuditContent();
		supplierAudit.setSupplierId(id);
		List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		boolean same = true;
		for(int i = 0; i < reasonsList.size(); i++) {
			if(reasonsList.get(i).getAuditField().equals(auditField) && reasonsList.get(i).getAuditType().equals(auditType) && reasonsList.get(i).getAuditFieldName().equals(auditFieldName) && reasonsList.get(i).getAuditContent().equals(auditContent)) {
				same = false;
				break;
			}
		}
		if(same) {
			supplierAuditService.auditReasons(supplierAudit);
		} else {
			String msg = "{\"msg\":\"fail\"}";
			super.writeJson(response, msg);
		}
	}

	/**
	 * @Title: reasonsList
	 * @author Xu Qing
	 * @date 2016-9-20 上午9:44:58  
	 * @Description: 审核问题汇总 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("reasonsList")
	public String reasonsList(HttpServletRequest request, SupplierAudit supplierAudit, Integer supplierStatus, Integer sign) {
		request.setAttribute("sign", sign);
		
		String supplierId = supplierAudit.getSupplierId();
		if(supplierId == null) {
			supplierId = (String) request.getSession().getAttribute("supplierId");
			supplierAudit.setSupplierId(supplierId);
		}
		List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		request.setAttribute("reasonsList", reasonsList);
		//有信息就不让通过
		request.setAttribute("num", reasonsList.size());
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		Supplier supplier = supplierAuditService.supplierById(supplierId);
		request.setAttribute("supplierStatus", supplierStatus);

		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.setAttribute("suppliers", supplier);

		request.setAttribute("supplierId", supplierId);
		request.getSession().removeAttribute("supplierId");
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
	public String updateStatus(HttpServletRequest request, Supplier supplier, SupplierAudit supplierAudit) throws IOException {
		String supplierId = supplierAudit.getSupplierId();
		Todos todos = new Todos();
		User user = (User) request.getSession().getAttribute("loginUser");
		//更新状态
		supplier.setId(supplierId);
		supplier.setAuditDate(new Date());
		supplierAuditService.updateStatus(supplier);
		
		if(supplier.getStatus() != null && supplier.getStatus() == 1){
			// 供应商分级要素得分
	        supplier.setLevelScoreProduct(SupplierLevelUtil.getScore(supplier.getId(), "PRODUCT"));
	        supplier.setLevelScoreSales(SupplierLevelUtil.getScore(supplier.getId(), "SALES"));
	        supplier.setLevelScoreService(SupplierLevelUtil.getScore(supplier.getId(), "SERVICE"));
	        if(supplier.getProcurementDepId() != null){
	        	supplierService.updateSupplierProcurementDep(supplier);
	        }
		}
		
		//更新待办
		supplier = supplierAuditService.supplierById(supplierId);
		String supplierName = supplier.getSupplierName();

		/**
		 * 更新待办(已完成)
		 */
		if(supplier.getStatus() != null && supplier.getStatus() == 1 || supplier.getStatus() == 2 || supplier.getStatus() == 3 || supplier.getStatus() == 5 || supplier.getStatus() == 6 || supplier.getStatus() == 7 || supplier.getStatus() == 8) {
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
		List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		if(reasonsList.size() != 0) {
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
	 * @Title: temporaryAudit
	 * @author Xu Qing
	 * @date 2016-10-28 下午3:29:51  
	 * @Description: 暂存审核
	 * @param @param request
	 * @param @param supplier
	 * @param @param supplierAudit
	 * @param @throws IOException      
	 * @return void
	 */
	@RequestMapping("temporaryAudit")
		/*	public void temporaryAudit(HttpServletRequest request,HttpServletResponse response,Supplier supplier) throws IOException{
				String supplierId  = supplier.getId();
				supplier = supplierAuditService.supplierById(supplier.getId());
				Integer status = supplier.getStatus();
				//暂存审核（5：审核中）
				if(status == 0 || status == 5 || status == 8 && status != null){
					supplier.setStatus(5);
					supplierAuditService.updateStatus(supplier);
					
					//更新待办任务
					Todos todos = new Todos();
					todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
					todos.setName("供应商审核中");
					todosService.updateByUrl(todos);
					
					String msg = "{\"msg\":\"success\"}";
					super.writeJson(response, msg);
				}
				//暂存复核（6：复核中）
				if(status == 1 || status == 6 && status != null){
					supplier.setStatus(6);
					supplierAuditService.updateStatus(supplier);
					
					//更新待办任务
					Todos todos = new Todos();
					todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
					todos.setName("供应商复核中");
					todosService.updateByUrl(todos);
					
					String msg = "{\"msg\":\"success\"}";
					super.writeJson(response, msg);
				}
				
				
			}*/

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
	public String applicationForm(HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier, Integer supplierStatus, Integer sign) throws IOException {
		request.setAttribute("sign", sign);
		
		String supplierId = supplierAudit.getSupplierId();
		supplier = supplierAuditService.supplierById(supplierId);
		request.setAttribute("applicationForm", supplier);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		//文件
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		request.setAttribute("supplierStatus", supplierStatus);
		
		//回显未通过字段
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
			supplierAudit.setSupplierId(supplierId);
			supplierAudit.setAuditType("download_page");
			List < SupplierAudit > reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
			StringBuffer passedField = new StringBuffer();
			if(!reasonsList.isEmpty()){
				for(SupplierAudit a : reasonsList){
					passedField.append(a.getAuditField() + ",");
				}
			}
			request.setAttribute("passedField", passedField);
		}
		
		/**
		 * 退回修改后的附件
		 */
		if(supplierStatus == 0){
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

		supplier = supplierService.get(supplierId);
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
	public String supplierAll(@CurrentUser User user, HttpServletRequest request, Supplier supplier, Integer page) {
		if(supplier.getSign() == null) {
			Integer sign = (Integer) request.getSession().getAttribute("signs");
			supplier.setSign(sign);
			request.getSession().removeAttribute("signs");
		}

		if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}

		//获取登录人机构,1代表机构
		Orgnization org = user.getOrg();
		if(user !=null && org !=null && "1".equals(org.getTypeName())){
			PurchaseDep dep = purchaseOrgnizationService.selectByOrgId(org.getId());
			if(dep !=null){
				supplier.setProcurementDepId(dep.getId());
				//抽取时的机构
				supplier.setExtractOrgid(dep.getId());
			}else{
				supplier.setProcurementDepId("");
				supplier.setExtractOrgid("");
			}
		}else{
			supplier.setProcurementDepId("");
			supplier.setExtractOrgid("");
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

		//回显
		String supplierName = supplier.getSupplierName();
		Integer status = supplier.getStatus();
		request.setAttribute("supplierName", supplierName);
		request.setAttribute("state", status);
		request.setAttribute("businessNature", supplier.getBusinessNature());
		request.setAttribute("auditDate", supplier.getAuditDate());

		//审核、复核、实地考察的标识
		request.setAttribute("sign", supplier.getSign());
		request.getSession().setAttribute("signs", supplier.getSign());
		request.getSession().getAttribute("sign");

		return "ses/sms/supplier_audit/supplier_all";
	}

	/**
	 * 
	 *〈简述〉获取供应商的企业类型
	 *〈详细描述〉
	 * @author myc
	 * @param list
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
				Date date = sdf.parse(supplierModify.getBeforeContent());
				String d = new SimpleDateFormat("yyyy-MM-dd").format(date);
				return JSON.toJSONString(d);
			}
		}
		
		//近三年内有无重大违法记录/是否有国家或军队保密工程业绩
		if(supplierModify.getBeforeField().equals("isIllegal") || supplierModify.getBeforeField().equals("isHavingConAchi")){
			if(supplierModify.getBeforeContent().equals("1")){
				supplierModify.setBeforeContent("有");
			}else{
				supplierModify.setBeforeContent("无");
			}
		}
		
		return JSON.toJSONString(supplierModify.getBeforeContent());
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
		if(supplierStatus == 0 || supplierStatus == 4 || supplierStatus == 5){
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
		
		if(supplier.getStatus() !=null && (supplier.getStatus() == 0 || supplier.getStatus() == 4 || supplier.getStatus() == 5)){
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
	@ResponseBody
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
	public ResponseEntity < byte[] > downloadTable(String supplierId, HttpServletRequest request, HttpServletResponse response, String tableType) throws Exception {
		//供应商信息
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		// 文件存储地址
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		// 文件名称
		String fileName = createWordMethod(supplier, request, tableType);
		// 下载后的文件名
		String downFileName = "";
		if(tableType.equals("1")){
			downFileName = new String("军队供应商实地考察记录表.doc".getBytes("UTF-8"), "iso-8859-1");
		}
		if(tableType.equals("2")){
			downFileName = new String("军队供应商实地考察廉政意见函.doc".getBytes("UTF-8"), "iso-8859-1"); 
		}
		if("3".equals(tableType)){
			downFileName = new String("军队采购供应商审核表.doc".getBytes("UTF-8"), "iso-8859-1");
		}
		response.setContentType("application/x-download");
		return supplierAuditService.downloadFile(fileName, filePath, downFileName);
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
	private String createWordMethod(Supplier supplier, HttpServletRequest request, String tableType) throws Exception {
		/** 用于组装word页面需要的数据 */
		Map < String, Object > dataMap = new HashMap < String, Object > ();
		SupplierSignature supplierSignature = new SupplierSignature();
		//供应商名称
		dataMap.put("supplierName", supplier.getSupplierName() == null ? "" : supplier.getSupplierName());
		if(tableType.equals("1")){
			//办公地址
			dataMap.put("address", supplier.getContactAddress() == null ? "" : supplier.getContactAddress());
			//办公地址
			dataMap.put("officeAddress", "");
			//生产或经营场所详细地址
			dataMap.put("detailAddress", supplier.getDetailAddress() == null ? "" : supplier.getDetailAddress());
			//实地考察时间
			Date date = new Date();
		    SimpleDateFormat format = new SimpleDateFormat("yyyy 年 MM 月 dd 日");
			dataMap.put("date", format.format(date));
			
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
		}
		
		//意见函人数和名字
		if(tableType.equals("2")){
			supplierSignature.setSupplierId(supplier.getId());
			List<SupplierSignature> supplierSignatureList = supplierSignatureService.selectBySupplierId(supplierSignature);
			StringBuffer name = new StringBuffer();
			for(SupplierSignature s: supplierSignatureList ){
				name.append(s.getName() + ",");
			}
			name.deleteCharAt(name.length() - 1);
			dataMap.put("num", supplierSignatureList.size());
			dataMap.put("name", name);
		}
				
		/**
		 * 审核/复核表的数据
		 */
		if("3".equals(tableType) || "4".equals(tableType)){
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
		}
		
		/** 生成word 返回文件名 */
		String newFileName = "";
		String fileName = "";		
		if(tableType.equals("1")){
			newFileName = WordUtil.createWord(dataMap, "supplierInspection.ftl", fileName, request);
		}
		if(tableType.equals("2")){
			newFileName = WordUtil.createWord(dataMap, "supplierOpinionLetter.ftl", fileName, request);
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
}