package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Area;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierDictionaryData;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierEditService;
import ses.service.sms.SupplierLevelService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;
import common.model.UploadFile;

@Controller
@Scope("prototype")
@RequestMapping("/supplierQuery")
public class SupplierQueryController extends BaseSupplierController{
	
	@Autowired
	private SupplierAuditService supplierAuditService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private SupplierLevelService supplierLevelService;
	
	@Autowired
	private SupplierEditService supplierEditService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AreaServiceI areaService;

	/**
	 * @Title: highmaps
	 * @author Song Biaowei
	 * @date 2016-9-22 上午10:21:18  
	 * @Description: 供应商分布
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/highmaps")
	public String highmaps(Supplier sup,Model model,Integer status,Integer judge,String supplierTypeIds,String supplierType,String categoryNames,String categoryIds,HttpServletRequest req){
		//调用供应商查询方法 List<Supplier>
		if(judge!=null){
			status=judge;
		}
		if(status!=null){
			sup.setStatus(status);
		}
		if(categoryIds!=null&&!"".equals(categoryIds)){
			List<String> listCategoryIds=Arrays.asList(categoryIds.split(","));
			sup.setItem(listCategoryIds);
		}
		if(supplierTypeIds!=null&&!"".equals(supplierTypeIds)){
			List<String> listSupplierTypeIds=Arrays.asList(supplierTypeIds.split(","));
			sup.setItemType(listSupplierTypeIds);
		}
		List<Supplier>  listSupplier=supplierAuditService.querySupplierbytypeAndCategoryIds(sup,null);
		//开始循环 判断地址是否
		Map<String,Integer> map= supplierEditService.getMap();
		Map<String,Object> mapProvince=supplierEditService.getAllProvince();
		for(Supplier supplier:listSupplier){
			for(Map.Entry<String, Object> entry:mapProvince.entrySet()){   
				//if(supplier.getAddress()!=null&&supplier.getAddress().indexOf(entry.getKey())!=-1){
				if(supplier.getAddress()!=null){
					String strAddress=areaService.listById(areaService.listById(supplier.getAddress()).getParentId()).getName();
					if(strAddress.indexOf(entry.getKey())!=-1){
						map.put((String)entry.getValue(),(Integer)map.get(entry.getValue())+1);
					}
				}
			}   
		}
		 String json = JSON.toJSONString(map);
		model.addAttribute("data", json);
		model.addAttribute("sup",sup);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("supplierType", supplierType);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("categoryIds", categoryIds);
		if(judge!=null&&judge==3){
			return "ses/sms/supplier_query/all_ruku_supplier";
		}else{
			return "ses/sms/supplier_query/all_supplier";
		}
		
	}
	
	/**
	 * @Title: findSupplierByPriovince
	 * @author Song Biaowei
	 * @date 2016-9-22 上午10:21:32  
	 * @Description: 每个省的供应商列表
	 * @param @param supplier
	 * @param @param model
	 * @param @return      
	 * @return String
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/findSupplierByPriovince")
	public String findSupplierByPriovince(HttpServletRequest req,Integer judge,Supplier sup,Integer page,Model model,String supplierTypeIds,String supplierType,String categoryNames,String categoryIds) throws UnsupportedEncodingException{
		if(judge!=null){
			sup.setStatus(judge);
		}
		model.addAttribute("address", sup.getAddress());
		String address=supplierEditService.getProvince(sup.getAddress());
		if("".equals(address)){
			if(address.length()>2){
				sup.setAddress(URLDecoder.decode(sup.getAddress(),"UTF-8").substring(0, 3).replace(",", ""));
			}else{
				sup.setAddress(URLDecoder.decode(sup.getAddress(),"UTF-8").substring(0, 2).replace(",", ""));
			}
		}else{
		    sup.setAddress(address);
		}
		if(categoryIds!=null&&!"".equals(categoryIds)){
			List<String> listCategoryIds=Arrays.asList(categoryIds.split(","));
			sup.setItem(listCategoryIds);
		}
		if(supplierTypeIds!=null&&!"".equals(supplierTypeIds)){
			List<String> listSupplierTypeIds=Arrays.asList(supplierTypeIds.split(","));
			sup.setItemType(listSupplierTypeIds);
		}
		List<Supplier>  listSupplier=supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page==null?1:page);
		this.getSupplierType(listSupplier);
		model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
		model.addAttribute("supplier", sup);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("supplierType", supplierType);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("categoryIds", categoryIds);
		//等于3说明是入库供应商
		if(judge!=null&&judge==3){
			return "ses/sms/supplier_query/select_ruku_supplier_by_province";
		}else{
			if(sup.getStatus()!=null&&sup.getStatus()==3){
				return "ses/sms/supplier_query/select_ruku_supplier_by_province";
			}else{
				return "ses/sms/supplier_query/select_supplier_by_province";
			}
		}
	}
	
	/**
	 * @Title: selectByCategory
	 * @author Song Biaowei
	 * @date 2016-9-27 上午11:19:28  
	 * @Description: 按照品目查询供应商 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/selectByCategory")
	public String selectByCategory(Supplier sup,Integer page,String categoryIds,Model model ){
		if(categoryIds!=null&&!"".equals(categoryIds)){
			List<String> listCategoryIds=Arrays.asList(categoryIds.split(","));
			sup.setItem(listCategoryIds);
		}
		List<Supplier>  listSupplier=supplierAuditService.querySupplierbytypeAndCategoryIds(sup, page==null?1:page);
		getSupplierType(listSupplier);
		model.addAttribute("listSupplier", new PageInfo<>(listSupplier));
		model.addAttribute("supplier", sup);
		model.addAttribute("categoryIds", categoryIds);
		return "ses/sms/supplier_query/select_by_category";
	}
	
	/**
	 * @Title: essentialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:36:49  
	 * @Description: 基本信息 
	 * @param @param request
	 * @param @param supplier
	 * @param @param supplierId
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/essential")
	public String essentialInformation(HttpServletRequest request,Integer isRuku,Supplier supplier,String supplierId,Integer person,Model model) {
		//这个是之前页面提交没有supplierId 现在有了，但是不敢去掉。以后有时间看仔细再去掉
		/*String supId=(String)request.getSession().getAttribute("supplierId");
		if(supId==null&&!"".equals(supplierId)){
			request.getSession().setAttribute("supplierId", supplierId);
		}
		if(supId!=null&&!"".equals(supplierId)){
			request.getSession().removeAttribute("supplierId");
			request.getSession().setAttribute("supplierId", supplierId);
		}
		if(supId!=null&&supplierId.equals("")){
			supplierId=supId;
		}*/
		User user=(User)request.getSession().getAttribute("loginUser");
		Integer ps=(Integer)request.getSession().getAttribute("ps");
		if(user.getTypeId()!=null&&ps!=null){
			person=ps;
		}
		if(user.getTypeId()!=null&&person!=null){
			request.getSession().setAttribute("ps",person);
			supplierId=user.getTypeId();
		}
		
		supplier = supplierAuditService.supplierById(supplierId);
		try {
			Area area=areaService.listById(areaService.listById(supplier.getAddress()).getParentId());
			supplier.setAddress(area.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		getSupplierType(supplier);
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		model.addAttribute("suppliers", supplier);
		//将状态是否入库isRuku存入session里面
		Integer irk=(Integer)request.getSession().getAttribute("irk");
	    //第一次进来的时候有值,session为null。
		if(irk==null&&isRuku!=null){
			request.getSession().setAttribute("irk",isRuku);
		}else if(irk!=null&&isRuku==null){
			isRuku=(Integer)request.getSession().getAttribute("irk");
		}else if(irk!=null&&isRuku!=null){
			Integer isRuku1=(Integer)request.getSession().getAttribute("irk");
			if(!isRuku.equals(isRuku1)){
				request.getSession().removeAttribute("irk");
				request.getSession().setAttribute("irk",isRuku);
			}
		}
		if(isRuku!=null&&isRuku==1){
			model.addAttribute("status", supplier.getStatus());
		}
		if(isRuku!=null&&isRuku==2){
			model.addAttribute("category", 1);
		}
		model.addAttribute("person", person);
		return "ses/sms/supplier_query/supplierInfo/essential";
	}
	
	/**
	 * @Title: financialInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:15  
	 * @Description: 财务信息 
	 * @param @param request
	 * @param @param supplierFinance
	 * @param @param supplier
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/financial")
	public String financialInformation(HttpServletRequest request,SupplierFinance supplierFinance,Supplier supplier) {
		String supplierId = supplierFinance.getSupplierId();
		List<SupplierFinance> list = supplierAuditService.supplierFinanceBySupplierId(supplierId);
		SupplierDictionaryData supplierDictionaryData = dictionaryDataServiceI.getSupplierDictionary();
		for (SupplierFinance sf : list) {
			List<UploadFile> listUploadFiles = sf.getListUploadFiles();
			for (UploadFile uf : listUploadFiles) {
				if (supplierDictionaryData.getSupplierProfit().equals(uf.getTypeId())) {
					sf.setProfitListId(uf.getId());
					sf.setProfitList(uf.getName());
					continue;
				}
				if (supplierDictionaryData.getSupplierAuditOpinion().equals(uf.getTypeId())) {
					sf.setAuditOpinionId(uf.getId());
					sf.setAuditOpinion(uf.getName());
					continue;
				}
				if (supplierDictionaryData.getSupplierLiabilities().equals(uf.getTypeId())) {
					sf.setLiabilitiesListId(uf.getId());
					sf.setLiabilitiesList(uf.getName());
					continue;
				}
				if (supplierDictionaryData.getSupplierCashFlow().equals(uf.getTypeId())) {
					sf.setCashFlowStatementId(uf.getId());
					sf.setCashFlowStatement(uf.getName());
					continue;
				}
				if (supplierDictionaryData.getSupplierOwnerChange().equals(uf.getTypeId())) {
					sf.setChangeListId(uf.getId());
					sf.setChangeList(uf.getName());
					continue;
				}
			}
		}
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("financial", list);
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/financial";
	}

	/**
	 * @Title: shareholderInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:37:50  
	 * @Description: 股东信息
	 * @param @param request
	 * @param @param supplierStockholder
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/shareholder")
	public String shareholderInformation(HttpServletRequest request,SupplierStockholder supplierStockholder) {
		String supplierId = supplierStockholder.getSupplierId();
		List<SupplierStockholder> list = supplierAuditService.ShareholderBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/shareholder";
	}
	
	/**
	 * @Title: materialProduction
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:38:07  
	 * @Description: 物资生产型专业信息  
	 * @param @param request
	 * @param @param supplierMatPro
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/materialProduction")
	public String materialProduction(HttpServletRequest request,SupplierMatPro supplierMatPro) {
		String supplierId = supplierMatPro.getSupplierId();
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		request.setAttribute("supplierId", supplierId);	
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/material_production";
	}
	
	/**
	 * @Title: materialSales
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:08  
	 * @Description: 物资销售专业信息 
	 * @param @param request
	 * @param @param supplierMatSell
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/materialSales")
	public String materialSales(HttpServletRequest request,SupplierMatSell supplierMatSell){
		String supplierId = supplierMatSell.getSupplierId();
		//资质资格证书
		List<SupplierCertSell> supplierCertSell=supplierAuditService.findCertSellBySupplierId(supplierId);
		//供应商组织机构和人员
		supplierMatSell = supplierService.get(supplierId).getSupplierMatSell();
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/material_sales";
	}
	
	/**
	 * @Title: engineeringInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:22  
	 * @Description: 工程专业信息  
	 * @param @param request
	 * @param @param supplierMatEng
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/engineering")
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
		request.setAttribute("supplierId", supplierId);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/engineering";
	}
	
	/**
	 * @Title: serviceInformation
	 * @author Song Biaowei
	 * @date 2016-9-29 上午11:39:43  
	 * @Description: 服务专业信息  
	 * @param @param request
	 * @param @param supplierMatSe
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/serviceInformation")
	public String serviceInformation(HttpServletRequest request,SupplierMatServe supplierMatSe){
		String supplierId = supplierMatSe.getSupplierId();
		//资质证书信息
		List<SupplierCertServe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		request.setAttribute("supplierMatSes", supplierMatSe);
		request.setAttribute("supplierId", supplierId);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/service_information";
	}
	
	/**
	 * @Title: productInformation
	 * @author Song Biaowei
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
		if(supplierId != null){
			List<SupplierItem> listItem= supplierService.get(supplierId).getListSupplierItems();
			request.setAttribute("listItem", listItem);
		}
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
		supplier.setId(supplierId);
		getSupplierType(supplier);
		request.setAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/product";
	}
	
	/**
	 * @Title: list
	 * @author Song Biaowei
	 * @date 2016-10-8 下午5:41:13  
	 * @Description: 诚信信息
	 * @param @param model
	 * @param @param supplier
	 * @param @param page
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value = "list")
	public String list(Model model, Supplier supplier,String supplierId, Integer page) {
		supplier.setId(supplierId);
		List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		model.addAttribute("supplierId", supplier.getId());
		supplier.setId(supplierId);
		getSupplierType(supplier);
		model.addAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/cheng_xin";
	}
	
	/**
	 * @Title: item
	 * @author Song Biaowei
	 * @date 2016-10-8 下午3:11:30  
	 * @Description: 品目信息 
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/item")
	public String item(String supplierId,Model model){
		model.addAttribute("id", supplierId);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		model.addAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/item";
	}	
	
	/**
	 * @Title: item
	 * @author Song Biaowei
	 * @date 2016-11-8 下午1:05:00  
	 * @Description: 显示历史纪录 
	 * @param @param supplierId
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showUpdateHistory")
	public String showUpdateHistory(String supplierId,Model model){
		SupplierEdit se=new SupplierEdit();
		se.setRecordId(supplierId);
		List<SupplierEdit> listEdit=supplierEditService.getAllRecord(se);
		List<String> list=supplierEditService.getList(listEdit);
		model.addAttribute("list", list);
		Supplier supplier=new Supplier();
		supplier.setId(supplierId);
		getSupplierType(supplier);
		model.addAttribute("suppliers", supplier);
		return "ses/sms/supplier_query/supplierInfo/show_update_history";
	}	
	
	/**
	 * @Title: downLoadFile
	 * @author Song Biaowei
	 * @date 2016-10-6 上午11:23:53  
	 * @Description: 附件下载查看
	 * @param @param fileName
	 * @param @param request
	 * @param @return
	 * @param @throws UnsupportedEncodingException      
	 * @return ResponseEntity<byte[]>
	 */
	 @RequestMapping("/downLoadFile")
		public void download(HttpServletRequest request, HttpServletResponse response, String fileName) {
			if("".equals(fileName)){
				super.alert(request, response, "无附件下载 !",true);
				return;
			}
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
	 * @Title: getSupplierType
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:50:32  
	 * @Description: 复制供应商类型 
	 * @param @param listSupplier      
	 * @return void
	 */
	public void getSupplierType(List<Supplier> listSupplier){
		for(Supplier sup:listSupplier){
			String supplierTypes = supplierService.selectSupplierTypes(sup);
			sup.setSupplierType(supplierTypes);
		}
	}
	
	/**
	 * @Title: getSupplierType
	 * @author Song Biaowei
	 * @date 2016-11-22 下午2:50:42  
	 * @Description: 复制供应商类型
	 * @param @param supplier      
	 * @return void
	 */
	public void getSupplierType(Supplier supplier){
			String supplierTypes = supplierService.selectSupplierTypes(supplier);
			supplier.setSupplierType(supplierTypes);
	}
	
}
