package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.formbean.ContractBean;
import ses.formbean.QualificationBean;
import ses.model.bms.Area;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAddress;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierBranch;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
import ses.service.bms.AreaServiceI;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.sms.SupplierAddressService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierBranchService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierHistoryService;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierTypeRelateService;
import ses.util.DictionaryDataUtil;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.constant.StaticVariables;

/**
 * <p>Title:SupplierAuditController </p>
 * <p>Description: 供应商审核控制类</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:14:36
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAudit")
public class SupplierAuditController extends BaseSupplierController{
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
	 * 用户
	 */
	@Autowired
	private UserServiceI userServiceI;
	
	/**
	 * 数据字典
	 */
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private SupplierExtRelateService  supplierExtRelateService;
	
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
	
	/**
	 * @Title: daiBan
	 * @author Xu Qing
	 * @date 2016-9-13 下午2:12:29  
	 * @Description: 待办
	 * @param @return      
	 * @return String
	 */
/*	@RequestMapping("daiban")
	public String daiBan(Supplier supplier,HttpServletRequest request) {
		//未审核条数（0审核）
		supplier.setStatus(0);
		int weishen = supplierAuditService.getCount(supplier);
		//审核中条数（1审核通过也是复核）
		supplier.setStatus(1);
		int shenhezhong =supplierAuditService.getCount(supplier);
		//已审核条数（3复核通过）
		supplier.setStatus(3);
		int yishen =supplierAuditService.getCount(supplier);

		request.setAttribute("weishen", weishen);
		request.setAttribute("shenhezhong", shenhezhong);
		request.setAttribute("yishen", yishen);
		
		return "ses/sms/supplier_audit/total";
	}*/
	
	
/*	@RequestMapping("saveDaiBan")
	public void saveDaiBan(String supplierId,HttpServletRequest request) {
		Todos todo=new Todos();
		todo.setCreatedAt(new Date());
		//待办类型 供应商
		todo.setUndoType((short)1);
		//逻辑删除 0未删除 1已删除
		todo.setIsDeleted((short)0);
		//执行路径
		todo.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
		//是否完成
		todo.setIsFinish((short)0);
		//标题
		todo.setName("供应商复核");
		User user=(User) request.getSession().getAttribute("loginUser");
		//发送人id
		todo.setSenderId(user.getId());
		//接收人id
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		todo.setReceiverId(supplier.getProcurementDepId());

		todosService.insert(todo);
	}*/
	
	
	/**
	 * @Title: SupplierList
	 * @author Xu Qing
	 * @date 2016-9-12 下午5:19:07  
	 * @Description: 根据审核状态（待办）查询供应商 
	 * @param @return      
	 * @return String
	 */
/*	@RequestMapping("supplierList")
	public String supplierList(HttpServletRequest request,Integer page,Supplier supplier) {
		//条件查询时status为空，把它存入session
		if(supplier.getStatus()!=null){
			request.getSession().setAttribute("status", supplier.getStatus());
		}
		int status =(int) request.getSession().getAttribute("status");
		if(supplier.getStatus()!=null){
			supplier.setStatus(supplier.getStatus());	
		}else{
			//条件查询的时status为空从session里取
			supplier.setStatus(status);
		}
		
		List<Supplier> supplierList =supplierAuditService.supplierList(supplier,page==null?1:page);
		request.setAttribute("result", new PageInfo<>(supplierList));
		request.setAttribute("supplierList", supplierList);
		
		//所有供应商类型
		List<SupplierType> supplierType= supplierAuditService.findSupplierType();
		request.setAttribute("supplierType", supplierType);
		//回显名字
		String supplierName = supplier.getSupplierName();
		request.setAttribute("supplierName", supplierName);
		return "ses/sms/supplier_audit/supplier_list";
	}*/
	
	/**
	 * @Title: essentialInformation
	 * @author Xu Qing
	 * @date 2016-9-12 下午7:14:09  
	 * @Description: 基本信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(HttpServletRequest request,Supplier supplier,String supplierId,Integer sign) {
		
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		
		//审核、复核的标识
		request.getSession().setAttribute("signs", sign);
	
		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		supplier = supplierAuditService.supplierById(supplierId);
		
		//在数据字典里查询营业执照类型
		List<DictionaryData> list=DictionaryDataUtil.find(17);
		for(int i=0; i<list.size(); i++){
			if(supplier.getBusinessType().equals(list.get(i).getId())){
				String businessType = list.get(i).getName();
				supplier.setBusinessType(businessType);
			}
		}
		request.setAttribute("suppliers", supplier);
		
		List<SupplierBranch> supplierBranchList= supplierBranchService.findSupplierBranch(supplierId);
		request.setAttribute("supplierBranchList", supplierBranchList);
		
		/**
		 * 查询地址
		 */
		List<Area> privnce = areaService.findRootArea();
		request.setAttribute("privnce", privnce);
		Area area = new Area();
		//地址信息里地址
		area = areaService.listById(supplier.getAddress());
		String sonAddress = area.getName();
		request.setAttribute("sonAddress", sonAddress);
		for(int i=0; i<privnce.size(); i++){
			if(area.getParentId().equals(privnce.get(i).getId())){
				String parentAddress = privnce.get(i).getName();
				request.setAttribute("parentAddress", parentAddress);
			}
		}
		
		//注册联系人里地址
		area = areaService.listById(supplier.getConcatCity());
		String sonConcatProvince = area.getName();
		request.setAttribute("sonConcatProvince", sonConcatProvince);
		for(int i=0; i<privnce.size(); i++){
			if(area.getParentId().equals(privnce.get(i).getId())){
				String parentConcatProvince = privnce.get(i).getName();
				request.setAttribute("parentConcatProvince", parentConcatProvince);
			}
		}
		
		//军队业务联系人里地址
		area = areaService.listById(supplier.getArmyBuinessCity());
		String sonArmyBuinessProvince = area.getName();
		request.setAttribute("sonArmyBuinessProvince", sonArmyBuinessProvince);
		for(int i=0; i<privnce.size(); i++){
			if(area.getParentId().equals(privnce.get(i).getId())){
				String parentArmyBuinessProvince = privnce.get(i).getName();
				request.setAttribute("parentArmyBuinessProvince", parentArmyBuinessProvince);
			}
		}
		
		//生产经营地址
		List<SupplierAddress> supplierAddress= supplierAddressService.queryBySupplierId(supplierId);
		for(Area a : privnce){
			for(SupplierAddress s : supplierAddress){
				if(a.getId().equals(s.getParentId())){
					s.setParentName(a.getName());
				}
			}	
		}
		request.setAttribute("supplierAddress", supplierAddress);

		//查出全部退回修改的信息
		if(supplier.getStatus() != null && supplier.getStatus() == 0){
			SupplierHistory supplierHistory = new SupplierHistory();
			supplierHistory.setSupplierId(supplierId);
			List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
			 StringBuffer field = new StringBuffer();
			for(int i=0; i<editList.size(); i++){
				String beforeField = editList.get(i).getBeforeField();
				field.append( beforeField + ",");	
			}
			request.setAttribute("field", field);
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
	public String financialInformation(HttpServletRequest request,String supplierId) {
		List<SupplierFinance> list = supplierAuditService.supplierFinanceBySupplierId(supplierId);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		
		//文件
		if(supplierId!=null){
			List<SupplierFinance> supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
			request.setAttribute("financial", supplierFinance);
		}
		
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);

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
	public String shareholderInformation(HttpServletRequest request,SupplierStockholder supplierStockholder) {
		String supplierId = supplierStockholder.getSupplierId();
		List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
		
		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("生产")){
			url=request.getContextPath()+"/supplierAudit/materialProduction.html";
		}else if(supplierTypeName.contains("销售") && url == null){
			url=request.getContextPath()+"/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("工程") && url == null){
			url=request.getContextPath()+"/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("服务") && url == null){
			url=request.getContextPath()+"/supplierAudit/serviceInformation.html";
		}else{
			url=request.getContextPath()+"/supplierAudit/items.html";
		}
		request.setAttribute("url", url);
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
	public String materialProduction(HttpServletRequest request,SupplierMatPro supplierMatPro) {
		String supplierId = supplierMatPro.getSupplierId();
		/*List<SupplierCertPro> materialProduction = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();*/
		//资质资格证书信息
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		//供应商组织机构人员,产品研发能力,产品生产能力,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);	
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);

		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
		 StringBuffer field = new StringBuffer();
		for(int i=0; i<editList.size(); i++){
			String beforeField = editList.get(i).getBeforeField();
			field.append( beforeField + ",");	
		}
		request.setAttribute("field", field);

		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("销售")){
			url = request.getContextPath()+"/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("工程") && url == null){
			url=request.getContextPath()+"/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("服务") && url == null){
			url=request.getContextPath()+"/supplierAudit/serviceInformation.html";
		}else{
			url=request.getContextPath()+"/supplierAudit/items.html";
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
	public String materialSales(HttpServletRequest request,SupplierMatSell supplierMatSell){
		String supplierId = supplierMatSell.getSupplierId();
		//资质资格证书
		List<SupplierCertSell> supplierCertSell=supplierAuditService.findCertSellBySupplierId(supplierId);
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
		List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
		 StringBuffer field = new StringBuffer();
		for(int i=0; i<editList.size(); i++){
			String beforeField = editList.get(i).getBeforeField();
			field.append( beforeField + ",");	
		}
		request.setAttribute("field", field);
		
		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("工程")){
			url=request.getContextPath()+"/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("服务") && url == null){
			url=request.getContextPath()+"/supplierAudit/serviceInformation.html";
		}else{
			url=request.getContextPath()+"/supplierAudit/items.html";
		}
		request.setAttribute("url", url);
		
		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("生产")){
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}else{
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
	public String engineeringInformation(HttpServletRequest request,SupplierMatEng supplierMatEng){
		String supplierId = supplierMatEng.getSupplierId();
		if(supplierId != null){
		//资质资格证书信息
		List<SupplierCertEng> supplierCertEng= supplierAuditService.findCertEngBySupplierId(supplierId);
		request.setAttribute("supplierCertEng", supplierCertEng);
		
		//资质资格信息
		List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
		request.setAttribute("supplierAptitutes", supplierAptitute);
		
		//组织结构
		supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
		request.setAttribute("supplierMatEngs",supplierMatEng);
		
		//注册人人员
		 List<SupplierRegPerson> listSupplierRegPersons = supplierService.get(supplierId).getSupplierMatEng().getListSupplierRegPersons();
		 request.setAttribute("listRegPerson", listSupplierRegPersons);
		}

		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		
		request.setAttribute("supplierId", supplierId);
		
		//查出全部修改的
		SupplierHistory supplierHistory = new SupplierHistory();
		supplierHistory.setSupplierId(supplierId);
		List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
		 StringBuffer field = new StringBuffer();
		for(int i=0; i<editList.size(); i++){
			String beforeField = editList.get(i).getBeforeField();
			field.append( beforeField + ",");	
		}
		request.setAttribute("field", field);
		
		//文件
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("服务")){
			url=request.getContextPath()+"/supplierAudit/serviceInformation.html";
		}else{
			url=request.getContextPath()+"/supplierAudit/items.html";
		}
		request.setAttribute("url", url);
		
		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("销售")){
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("生产") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}else{
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
	public String serviceInformation(HttpServletRequest request,SupplierMatServe supplierMatSe, SupplierMatSell supplierMatSell){
		String supplierId = supplierMatSe.getSupplierId();
		request.setAttribute("supplierId", supplierId);
		//资质证书信息
		List<SupplierCertServe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
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
		List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
		 StringBuffer field = new StringBuffer();
		for(int i=0; i<editList.size(); i++){
			String beforeField = editList.get(i).getBeforeField();
			field.append( beforeField + ",");	
		}
		request.setAttribute("field", field);
		
		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("工程")){
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("销售")  && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("生产") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}else{
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);
		
		return "ses/sms/supplier_audit/service_information";
	}
	
	@RequestMapping("supplierType")
	public String supplierType(HttpServletRequest request, SupplierMatSell supplierMatSell, SupplierMatPro supplierMatPro, SupplierMatEng supplierMatEng, SupplierMatServe supplierMatSe, String supplierId){
		//勾选的供应商类-型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);	
		/*//文件
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);*/
		
		//查出全部退回修改的信息
		Supplier supplier = supplierAuditService.supplierById(supplierId);
		
		if(supplier.getStatus() != null && supplier.getStatus() == 0){
			SupplierHistory supplierHistory = new SupplierHistory();
			supplierHistory.setSupplierId(supplierId);
			List<SupplierHistory> editList= supplierHistoryService.selectAllBySupplierId(supplierHistory);
			 StringBuffer field = new StringBuffer();
			for(int i=0; i<editList.size(); i++){
				String beforeField = editList.get(i).getBeforeField();
				field.append( beforeField + ",");	
			}
			request.setAttribute("field", field);
		}

		
				
		/**
		 * 生产
		 */
		//资质资格证书信息
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);

		
		/**
		 * 销售
		 */
		//资质资格证书
		List<SupplierCertSell> supplierCertSell=supplierAuditService.findCertSellBySupplierId(supplierId);
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
		
		/**
		 * 工程
		 */
		if(supplierId != null){
			//资质资格证书信息
			List<SupplierCertEng> supplierCertEng= supplierAuditService.findCertEngBySupplierId(supplierId);
			request.setAttribute("supplierCertEng", supplierCertEng);
			
			//资质资格信息
			List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
			request.setAttribute("supplierAptitutes", supplierAptitute);
			
			//组织结构
			supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
			request.setAttribute("supplierMatEngs",supplierMatEng);
			
			//注册人人员
			SupplierMatEng matEng = supplierService.get(supplierId).getSupplierMatEng();
			if(matEng != null){
				List<SupplierRegPerson> listSupplierRegPersons = matEng.getListSupplierRegPersons();
				 request.setAttribute("listRegPerson", listSupplierRegPersons);
				}
			}
		
		/**
		 * 服务
		 */
		//资质证书信息
		List<SupplierCertServe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierMatSes", supplierMatSe);	

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
	public void auditReasons(SupplierAudit supplierAudit,HttpServletRequest request,HttpServletResponse response,Supplier supplier) throws IOException{
		User user=(User) request.getSession().getAttribute("loginUser");
		
		String id=supplierAudit.getSupplierId();
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
		List<SupplierAudit> reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		boolean same= true;
		for(int i=0 ;i<reasonsList.size(); i++){
			if(reasonsList.get(i).getAuditField().equals(auditField ) && reasonsList.get(i).getAuditType().equals(auditType) && reasonsList.get(i).getAuditFieldName().equals(auditFieldName) && reasonsList.get(i).getAuditContent().equals(auditContent)){
				same = false; 
				break;
			}
		}
		if(same){
			supplierAuditService.auditReasons(supplierAudit);
		}else{
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
	public String reasonsList(HttpServletRequest request,SupplierAudit supplierAudit){
		String supplierId = supplierAudit.getSupplierId();
		if(supplierId==null){
			supplierId = (String) request.getSession().getAttribute("supplierId");
			supplierAudit.setSupplierId(supplierId);
		}
		List<SupplierAudit> reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		request.setAttribute("reasonsList", reasonsList);
		//有信息就不让通过
		request.setAttribute("num",reasonsList.size());
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);

		Supplier supplier = supplierAuditService.supplierById(supplierId);
		Integer status = supplier.getStatus();
		request.setAttribute("status", status);
		
		//文件
		request.setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.setAttribute("suppliers", supplier);	
		
		request.setAttribute("supplierId", supplierId);	
		request.getSession().removeAttribute("supplierId");
		return "ses/sms/supplier_audit/audit_reasons";
	}
	
	@RequestMapping("showReasonsList") 
	public String showReasonsList(HttpServletResponse reponse, Model model, SupplierAudit supplierAudit, String jsp){
		List<SupplierAudit> list = supplierAuditService.findReason(supplierAudit);
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
	public String updateStatus(HttpServletRequest request,Supplier supplier,SupplierAudit supplierAudit) throws IOException{
		String supplierId= supplierAudit.getSupplierId();
		Todos todos = new Todos();
		User user=(User) request.getSession().getAttribute("loginUser");
		//更新状态
		supplier.setId(supplierId);
		supplier.setAuditDate(new Date());
		supplier.setUpdatedAt(new Date());
		supplierAuditService.updateStatus(supplier);
		//更新待办
		supplier = supplierAuditService.supplierById(supplierId);
		String supplierName = supplier.getSupplierName();
		
		/**
		 * 更新待办(已完成)
		 */
		if(supplier.getStatus() != null && supplier.getStatus() == 1  || supplier.getStatus() == 2 || supplier.getStatus() == 3 || supplier.getStatus() == 5  || supplier.getStatus() == 6 || supplier.getStatus() == 7 || supplier.getStatus() == 8){
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId=" + supplierId);
		}
		
		/**
		 * 推送代办
		 */
		if(supplier.getStatus() == 5 ){
			//推送者id
			todos.setSenderId(user.getId());
			//待办名称
			todos.setName("【"+supplierName+"】"+"供应商考察！");
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
			
			*//**
			 * 推送代办
			 *//*
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
			*//**
			 *  复核退回修改 ，推送代办
			 *//*
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
			*//**
			 * 审核退回修改 ，推送消息
			 *//*
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
		List<SupplierAudit> reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		if(reasonsList.size() !=0 ){
			supplierAudit.setStatus(supplier.getStatus());
			supplierAudit.setSupplierId(supplierId);
			supplierAuditService.updateStatusById(supplierAudit);
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
					if (str.equals("supplierInspectListFile")) {
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
	public String applicationForm(HttpServletRequest request, SupplierAudit supplierAudit,Supplier supplier) throws IOException {
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
	public String itemInformation(HttpServletResponse response,HttpServletRequest request,SupplierAudit supplierAudit, Supplier supplier) throws IOException{
		String supplierId = supplierAudit.getSupplierId();
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		request.setAttribute("supplierId", supplierId);
		
		supplier = supplierService.get(supplierId);
		request.setAttribute("currSupplier", supplier);
		
		//上一步
		String lastUrl = null;
		if(supplierTypeName.contains("服务")){
			lastUrl = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		}else if(supplierTypeName.contains("工程") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("销售") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("生产") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}else{
			lastUrl = request.getContextPath() + "/supplierAudit/shareholder.html";
		}
		request.setAttribute("lastUrl", lastUrl);
		
		
		return "ses/sms/supplier_audit/items";
	}
	
	@RequestMapping(value="/category_type" ,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCategory(String id,String name,String code,String supplierId){
		List<CategoryTree> categoryList=new ArrayList<CategoryTree>();
        if(code != null) {
            DictionaryData type = DictionaryDataUtil.get(code);
            CategoryTree ct = new CategoryTree();
            ct.setName( type.getName());
            ct.setId(type.getId());
            ct.setIsParent("true");
            categoryList.add(ct);
            
            List<SupplierItem> item = supplierItemService.getSupplierId(supplierId);
            
            for (SupplierItem category : item) {
            	 String parentId = categoryService.selectByPrimaryKey(category.getCategoryId()).getParentId();
                 if (parentId != null && parentId.equals(ct.getId())) {
                     ct.setChecked(true);
                 }
            }
            List<Category> child = getChild(type.getId());
            for(Category c:child){
            	CategoryTree ct1 = new CategoryTree();
                ct1.setName(c.getName());
                ct1.setParentId(c.getParentId());
                ct1.setId(c.getId());
                // 设置是否为父级
                if (!child.isEmpty()) {
                    ct1.setIsParent("true");
                } else {
                		ct1.setIsParent("false");
                }
//                ct1.set
//                ct1.set  
//                }
                
                // 设置是否回显
                for (SupplierItem category : item) {
                    if (category.getCategoryId() != null) {
                        if (category.getCategoryId().equals(c.getId())) {
                            ct1.setChecked(true);
                        }
                    }
                }
                categoryList.add(ct1);
            }
        }
		return JSON.toJSONString(categoryList);
	}
	
	public List<Category> getChild(String id){
	     List<Category> list = categoryService.findTreeByPid(id);
	        List<Category> childList = new ArrayList<Category>();
	        childList.addAll(list);
	        for (Category cate : list) {
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
	public String productInformation(HttpServletRequest request, SupplierAudit supplierAudit, Supplier supplier){
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
		if(supplierTypeName.contains("服务")){
			lastUrl = request.getContextPath() + "/supplierAudit/serviceInformation.html";
		}else if(supplierTypeName.contains("工程") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/engineering.html";
		}else if(supplierTypeName.contains("销售") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialSales.html";
		}else if(supplierTypeName.contains("生产") && lastUrl == null){
			lastUrl = request.getContextPath() + "/supplierAudit/materialProduction.html";
		}else{
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
		if (fileName != null && !"".equals(fileName)) {
			super.download(request, response, fileName);
		} else {
			super.alert(request, response, "无附件下载 !",true);
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
	public String supplierAll(HttpServletRequest request,Supplier supplier,Integer page) {
		if(supplier.getSign() == null){
			Integer  sign = (Integer) request.getSession().getAttribute("signs");
			supplier.setSign(sign);
			request.getSession().removeAttribute("signs");
		}
		
		if (page == null){
		    page = StaticVariables.DEFAULT_PAGE;
		}
		
		//获取登录人机构id
		User user = (User) request.getSession().getAttribute("loginUser");
        String orgId = user.getOrg().getId();
        PurchaseDep depId = purchaseOrgnizationService.selectByOrgId(orgId);
        supplier.setProcurementDepId(depId.getId());
        
        //查询列表
		List<Supplier> supplierList = supplierAuditService.getAuditSupplierList(supplier, page);
		PageInfo<Supplier> pageInfo =  new PageInfo<Supplier>(supplierList);
		request.setAttribute("result", getSupplierType(pageInfo));
		
		//企业性质
		List<DictionaryData> enterpriseTypeList = DictionaryDataUtil.find(17);
		request.setAttribute("enterpriseTypeList", enterpriseTypeList);

		
		//回显
		String supplierName = supplier.getSupplierName();
		Integer status = supplier.getStatus();
		request.setAttribute("supplierName", supplierName);
		request.setAttribute("state", status);
		request.setAttribute("businessTypeId", supplier.getBusinessType());
		
		//审核、复核标识
		request.setAttribute("sign",supplier.getSign());
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
	private PageInfo<Supplier> getSupplierType(PageInfo<Supplier> pageInfo){
	    List<Supplier> supplierList = new ArrayList<>();
	    List<Supplier> list = pageInfo.getList();
        for (Supplier supplier: list){
            List<SupplierTypeRelate> relaList = supplierTypeRelateService.queryBySupplier(supplier.getId());
            String typeName="";
            for (SupplierTypeRelate str : relaList){
                DictionaryData dd = DictionaryDataUtil.get(str.getSupplierTypeId());
                if (dd != null){
                    typeName += dd.getName() + StaticVariables.COMMA_SPLLIT;
                }
            }
            if (typeName.contains(StaticVariables.COMMA_SPLLIT)){
                typeName = typeName.substring(0, typeName.length() -1);
            }
            supplier.setSupplierTypeNames(typeName);
            supplierList.add(supplier);
        }
        pageInfo.setList(supplierList);
        return pageInfo;
	}
	
	@RequestMapping(value = "deleteById")
	public void deleteById (HttpServletResponse response, String[] ids){
		boolean whether = supplierAuditService.deleteById(ids);
		if(whether){
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
	 */
	@RequestMapping(value = "/showModify",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String showModify(SupplierHistory supplierHistory, HttpServletRequest request) {
		supplierHistory = supplierHistoryService.findBySupplierId(supplierHistory);
		
		//在数据字典里查询营业执照类型
		if(supplierHistory.getBeforeField().equals("businessType") && supplierHistory.getBeforeField() != null){
			String showModify = "";
			String typeid = supplierHistory.getBeforeContent();
			List<DictionaryData> list=DictionaryDataUtil.find(17);
			for(int i=0; i<list.size(); i++){
				if(typeid.equals(list.get(i).getId())){
					showModify = list.get(i).getName();
				}
			}
			return JSON.toJSONString(showModify);
		}
		
		/**
		 * 查询地址
		 */
		List<Area> privnce = areaService.findRootArea();
		Area area = new Area();
		String sonAddress = "";
		String parentAddress = "";
		if(supplierHistory.getBeforeField() != null && (supplierHistory.getBeforeField().equals("address") || supplierHistory.getBeforeField().equals("concatCity") || supplierHistory.getBeforeField().equals("armyBuinessCity"))){
			area = areaService.listById(supplierHistory.getBeforeContent());
			sonAddress = area.getName();
			for(int i=0; i<privnce.size(); i++){
				if(area.getParentId().equals(privnce.get(i).getId())){
					parentAddress = privnce.get(i).getName();
				}
			}
			return JSON.toJSONString(parentAddress+sonAddress);
		}
		
		return JSON.toJSONString(supplierHistory.getBeforeContent());
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
	public String aptitude(Model model, String supplierId) {
		String supplierTypeIds= supplierTypeRelateService.findBySupplier(supplierId);
		
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		model.addAttribute("supplierTypeNames", supplierTypeName);

		//查询所有的三级品目生产
		List<Category> list2 = getSupplier(supplierId,supplierTypeIds);
		removeSame(list2);
		
		//根据品目id查询所有的证书信息
		List<QualificationBean> list3 = supplierService.queryCategoyrId(list2, 2);
	 
		//查询所有的三级品目销售
		List<Category> listSlae = getSale(supplierId,supplierTypeIds);
		removeSame(listSlae);
		
		//根据品目id查询所有的证书信息
	    List<QualificationBean> saleQua= supplierService.queryCategoyrId(listSlae, 3);
	    
	    //查询所有的三级目录工程
	    List<Category> listProject = getSale(supplierId,supplierTypeIds);
	    removeSame(listProject);
	    
	    //根据品目id查询所有的工证书
	    List<QualificationBean> projectQua= supplierService.queryCategoyrId(listProject, 1);
	   
	    //查询所有的三级品目服务
	    List<Category> listService = getSale(supplierId,supplierTypeIds);
	    removeSame(listService);
	    
		//根据品目id查询所有的服务证书信息
	    List<QualificationBean> serviceQua= supplierService.queryCategoyrId(listService, 1);
	   
	    //生产证书
	    List<Qualification> qaList=new ArrayList<Qualification>();
	    List<Qualification> saleList=new ArrayList<Qualification>();
	    List<Qualification> projectList=new ArrayList<Qualification>();
	    List<Qualification> serviceList=new ArrayList<Qualification>();
		   
	    if(list3!=null&&list3.size()>0){
	    	for(QualificationBean qb:list3){
	    		qaList.addAll(qb.getList());
		   	}
	    }
	    
	    //销售
	    if(saleQua!=null&&saleQua.size()>0){
	    	for(QualificationBean qb:saleQua){
	    		saleList.addAll(qb.getList());
	    	}
	    } 
	    
	    //工程
	    if(projectQua!=null&&projectQua.size()>0){
	    	for(QualificationBean qb:projectQua){
	    		projectList.addAll(qb.getList());
	    	}
	    } 
	    
	    //服务
	    if(serviceQua!=null&&serviceQua.size()>0){
	    	for(QualificationBean qb:serviceQua){
	    		serviceList.addAll(qb.getList());
	    	}
	    } 
	   
	    //生产
	    StringBuffer sbUp=new StringBuffer("");
	    StringBuffer sbShow=new StringBuffer("");
	    int len=qaList.size()+1;
	    for(int i=1;i<len;i++){
	    	sbUp.append("pUp"+i+",");
	        sbShow.append("pShow"+i+",");
		 
	    }
		   
	    //销售
	    int slaelen=saleList.size()+1;
	    for(int i=1;i<slaelen;i++){
	    	sbUp.append("saleUp"+i+",");
	    	sbShow.append("saleShow"+i+",");
	    }
	    if(projectList!=null&&projectList.size()>0){
	    	int projectlen=projectList.size()+1;
	    	for(int i=1;i<projectlen;i++){
	    		sbUp.append("projectUp"+i+",");
	    		sbShow.append("projectShow"+i+",");
	    	} 
	    }
		
	   if(serviceList!=null&&serviceList.size()>0){
		   int serverlen=serviceList.size()+1;
		   for(int i=1;i<serverlen;i++){
			   sbUp.append("serverUp"+i+",");
			   sbShow.append("serverShow"+i+",");
		   } 
	   }

	   model.addAttribute("saleUp", sbUp);
	   model.addAttribute("saleShow", sbShow);
	   model.addAttribute("cateList",  list3);
	   model.addAttribute("saleQua", saleQua);
	   model.addAttribute("projectQua", projectQua);
	   model.addAttribute("supplierId", supplierId);
	   model.addAttribute("typeId", DictionaryDataUtil.getId("SUPPLIER_APTITUD"));
	   model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
	   return "ses/sms/supplier_audit/aptitude";
	}
	
	//生产
	public List<Category> getSupplier(String supplierId,String code){
		List<Category> categoryList=new ArrayList<Category>();
		String[] types = code.split(",");
		for(String s:types){
			String   categoryId="";
			   if (s != null ) {
	               if(s.equals("PRODUCT")){
	                   categoryId = DictionaryDataUtil.getId("GOODS");
	                   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
	    		       for(SupplierItem c:category){
	    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	    		    	 cate.setParentId(c.getId());
	    		    	 categoryList.add(cate);
	    	             }
	                 }
		       }
		}
		  return  categoryList;
	}
	
	//销售
	public List<Category>  getSale(String supplierId,String code){
			List<Category> categoryList=new ArrayList<Category>();
			
			String[] types = code.split(",");
			for(String s:types){
				String   categoryId="";
				   if (s != null ) {
		               if(s.equals("SALES")){
		                   categoryId = DictionaryDataUtil.getId("GOODS");
		    		       List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
		    		       for(SupplierItem c:category){
		    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	                         cate.setParentId(c.getId());
		    		    	 categoryList.add(cate);
		    	            }
		              }
			      }
			}
			
			return categoryList;
		}
		
		
		//工程
		public List<Category>  getProject(String supplierId,String code){
			List<Category> categoryList=new ArrayList<Category>();
			
			String[] types = code.split(",");
			for(String s:types){
				String   categoryId="";
				   if (s != null ) {
		               if(s.equals("PROJECT") ){
		                   categoryId = DictionaryDataUtil.getId("PROJECT");
		                   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
		    		      for(SupplierItem c:category){
		    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	                         cate.setParentId(c.getId());
		    		    	 categoryList.add(cate);
		    		 
		    	            }
		             }
			    }
			
			}
			
			return categoryList;
		}
		
		//服务
		public List<Category>  getServer(String supplierId,String code){
			List<Category> categoryList=new ArrayList<Category>();
			
			String[] types = code.split(",");
			for(String s:types){
				String   categoryId="";
				   if (s != null ) {
		               if(s.equals("SERVICE")){
		    		   List<SupplierItem> category = supplierItemService.getCategory(supplierId, categoryId,s);
		    		     for(SupplierItem c:category){
		    		    	 Category cate= categoryService.selectByPrimaryKey(c.getCategoryId());
	                         cate.setParentId(c.getId());
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
	@RequestMapping(value = "contract")
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
	}
	
	@ResponseBody
	@RequestMapping(value = "/getTree", produces = "application/json;charset=utf-8")
	public String getTree (String supplierId, String code) {
	    List<Category> categoryList = supplierItemService.getCategoryShenhe(supplierId, code);
	    // 最后加入根节点
	    String typeId = null;
	    if ("PRODUCT".equals(code) || "SALES".equals(code)) {
	        typeId = DictionaryDataUtil.getId("GOODS");
	    } else {
	        typeId = DictionaryDataUtil.getId(code);
	    }
        DictionaryData data = DictionaryDataUtil.findById(typeId);
        Category root = new Category();
        root.setId(data.getId());
        if ("PRODUCT".equals(code)) {
            data.setName(data.getName() + "生产");
        } else if ("SALES".equals(code)) {
            data.setName(data.getName() + "销售");
        }
        root.setName(data.getName());
        categoryList.add(root);
	    List<CategoryTree> treeList = new ArrayList<CategoryTree>();
	    for (Category cate : categoryList) {
	        CategoryTree node = new CategoryTree();
	        node.setId(cate.getId());
	        node.setName(cate.getName());
	        node.setParentId(cate.getParentId());
	        // 判断是不是父级节点
	        List<Category> parentTree = categoryService.findPublishTree(cate.getId(), null);
	        if (parentTree != null && parentTree.size() > 0) {
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
   public void removeSame(List<Category> list) {
       for (int i = 0; i < list.size() - 1; i++) {
           for (int j = list.size() - 1; j > i; j--) {
               if (list.get(j).getId().equals(list.get(i).getId())) {
                   list.remove(j);
               }
           }
       }
    }
}
