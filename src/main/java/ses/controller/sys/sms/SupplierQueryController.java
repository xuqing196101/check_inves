package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierEdit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierProducts;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierTypeRelate;
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
	public String highmaps(Supplier sup,Model model,Integer status,String supplierTypeIds,String supplierType,String categoryNames,String categoryIds,HttpServletRequest req){
		//调用供应商查询方法 List<Supplier>
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
		Map<String,Integer> map= new HashMap<String,Integer>(40);
		map=getMap();
		Map<String,Object> mapProvince=getAllProvince();
		for(Supplier supplier:listSupplier){
			for(Map.Entry<String, Object> entry:mapProvince.entrySet()){   
				if(supplier.getAddress()!=null&&supplier.getAddress().indexOf(entry.getKey())!=-1){
						map.put((String)entry.getValue(),(Integer)map.get(entry.getValue())+1);
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
		if(status!=null&&status==3){
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
	public String findSupplierByPriovince(HttpServletRequest req,Supplier sup,Integer page,Model model,String supplierTypeIds,String supplierType,String categoryNames,String categoryIds) throws UnsupportedEncodingException{
		String address=this.getProvince(sup.getAddress());
		if("".equals(address)){
			sup.setAddress(URLDecoder.decode(sup.getAddress(),"UTF-8").substring(0, 3).replace(",", ""));
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
		model.addAttribute("address", sup.getAddress());
		model.addAttribute("supplier", sup);
		model.addAttribute("categoryNames", categoryNames);
		model.addAttribute("supplierType", supplierType);
		model.addAttribute("supplierTypeIds", supplierTypeIds);
		model.addAttribute("categoryIds", categoryIds);
		//等于3说明是入库供应商
		if(sup.getStatus()!=null&&sup.getStatus()==3){
			return "ses/sms/supplier_query/select_ruku_supplier_by_province";
		}else{
			return "ses/sms/supplier_query/select_supplier_by_province";
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
		/*List<SupplierCertPro> materialProduction = supplierService.get(supplierId).getSupplierMatPro().getListSupplierCertPros();*/
		//资质资格证书信息
		List<SupplierCertPro> materialProduction = supplierAuditService.findBySupplierId(supplierId);
		//供应商组织机构人员,产品研发能力,产品生产能里,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
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
		//资质资格证书信息
		List<SupplierCertEng> supplierCertEng= supplierAuditService.findCertEngBySupplierId(supplierId);
		//资质资格信息
		List<SupplierAptitute> supplierAptitute = supplierAuditService.findAptituteBySupplierId(supplierId);
		//组织结构和注册人人员
		supplierMatEng = supplierAuditService.findMatEngBySupplierId(supplierId);
		request.setAttribute("supplierCertEng", supplierCertEng);
		request.setAttribute("supplierAptitutes", supplierAptitute);
		request.setAttribute("supplierMatEngs",supplierMatEng);
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
		//产品
		List<SupplierProducts> productsList= supplierService.get(supplierId).getListSupplierProducts();
		request.setAttribute("productsList", productsList);
		//勾选的供应商类型
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
		List<String> list=new ArrayList<String>();
		SupplierEdit se=new SupplierEdit();
		se.setRecordId(supplierId);
		List<SupplierEdit> listEdit=supplierEditService.getAllRecord(se);
		for(int i=0;i<listEdit.size()-1;i++){
			StringBuffer sb=new StringBuffer("");
			if((listEdit.get(i).getSupplierName()==null&&listEdit.get(i+1).getSupplierName()!=null)){
				sb.append(" 供应商姓名:"+listEdit.get(i+1).getSupplierName()+",");
			}else if((listEdit.get(i).getSupplierName()==null&&listEdit.get(i+1).getSupplierName()==null)){
				
			}else if(!listEdit.get(i).getSupplierName().equals(listEdit.get(i+1).getSupplierName())){
				sb.append(" 供应商姓名:"+listEdit.get(i+1).getSupplierName()+",");
			}
			if((listEdit.get(i).getWebsite()==null&&listEdit.get(i+1).getWebsite()!=null)){
				sb.append(" 公司网址:"+listEdit.get(i+1).getWebsite()+",");
			}else if((listEdit.get(i).getWebsite()==null&&listEdit.get(i+1).getWebsite()==null)){
				
			}else if(!listEdit.get(i).getWebsite().equals(listEdit.get(i+1).getWebsite())){
				sb.append(" 公司网址:"+listEdit.get(i+1).getWebsite()+",");
			}
			if((listEdit.get(i).getFoundDate()==null&&listEdit.get(i+1).getFoundDate()!=null)){
				sb.append(" 成立日期:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getFoundDate())+",");
			}else if((listEdit.get(i).getFoundDate()==null&&listEdit.get(i+1).getFoundDate()==null)){
				
			}else if(!listEdit.get(i).getFoundDate().equals(listEdit.get(i+1).getFoundDate())){
				sb.append(" 成立日期:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getFoundDate())+",");
			}
			if((listEdit.get(i).getBusinessType()==null&&listEdit.get(i+1).getBusinessType()!=null)){
				sb.append(" 营业执照类型:"+listEdit.get(i+1).getBusinessType()+",");
			}else if((listEdit.get(i).getBusinessType()==null&&listEdit.get(i+1).getBusinessType()==null)){
				
			}else if(!listEdit.get(i).getBusinessType().equals(listEdit.get(i+1).getBusinessType())){
				sb.append(" 营业执照类型:"+listEdit.get(i+1).getBusinessType()+",");
			}
			if((listEdit.get(i).getAddress()==null&&listEdit.get(i+1).getAddress()!=null)){
				sb.append(" 企业地址:"+listEdit.get(i+1).getAddress()+",");
			}else if((listEdit.get(i).getAddress()==null&&listEdit.get(i+1).getAddress()==null)){
				
			}else if(!listEdit.get(i).getAddress().equals(listEdit.get(i+1).getAddress())){
				sb.append(" 企业地址:"+listEdit.get(i+1).getAddress()+",");
			}
			
			if((listEdit.get(i).getBankName()==null&&listEdit.get(i+1).getBankName()!=null)){
				sb.append(" 开户行名称:"+listEdit.get(i+1).getBankName()+",");
			}else if((listEdit.get(i).getBankName()==null&&listEdit.get(i+1).getBankName()==null)){
				
			}else if(!listEdit.get(i).getBankName().equals(listEdit.get(i+1).getBankName())){
				sb.append(" 开户行名称:"+listEdit.get(i+1).getBankName()+",");
			}
			
			if((listEdit.get(i).getBankAccount()==null&&listEdit.get(i+1).getBankAccount()!=null)){
				sb.append(" 开户行账户:"+listEdit.get(i+1).getBankAccount()+",");
			}else if((listEdit.get(i).getBankAccount()==null&&listEdit.get(i+1).getBankAccount()==null)){
				
			}else if(!listEdit.get(i).getBankAccount().equals(listEdit.get(i+1).getBankAccount())){
				sb.append(" 开户行账户:"+listEdit.get(i+1).getBankAccount()+",");
			}
			
			if((listEdit.get(i).getPostCode()==null&&listEdit.get(i+1).getPostCode()!=null)){
				sb.append(" 邮编:"+listEdit.get(i+1).getPostCode()+",");
			}else if((listEdit.get(i).getPostCode()==null&&listEdit.get(i+1).getPostCode()==null)){
				
			}else if(!listEdit.get(i).getPostCode().equals(listEdit.get(i+1).getPostCode())){
				sb.append(" 邮编:"+listEdit.get(i+1).getPostCode()+",");
			}
			//上传方式换了  不好比较
			/*if((listEdit.get(i).getTaxCert()==null&&listEdit.get(i+1).getTaxCert()!=null)){
				if(listEdit.get(i+1).getTaxCert().indexOf("_")!=-1){
					sb.append(" 完税凭证:"+listEdit.get(i+1).getTaxCert().substring(listEdit.get(i+1).getTaxCert().indexOf("_")+1,listEdit.get(i+1).getTaxCert().length())+",");
				}
			}else if((listEdit.get(i).getTaxCert()==null&&listEdit.get(i+1).getTaxCert()==null)){
				
			}else if(!listEdit.get(i).getTaxCert().equals(listEdit.get(i+1).getTaxCert())){
				if(listEdit.get(i+1).getTaxCert().indexOf("_")!=-1){
					sb.append(" 完税凭证:"+listEdit.get(i+1).getTaxCert().substring(listEdit.get(i+1).getTaxCert().indexOf("_")+1,listEdit.get(i+1).getTaxCert().length())+",");
				}
			}
			
			if((listEdit.get(i).getBillCert()==null&&listEdit.get(i+1).getBillCert()!=null)){
				if(listEdit.get(i+1).getBillCert().indexOf("_")!=-1){
					sb.append(" 银行账单:"+listEdit.get(i+1).getBillCert().substring(listEdit.get(i+1).getBillCert().indexOf("_")+1,listEdit.get(i+1).getBillCert().length())+",");
				}
			}else if((listEdit.get(i).getBillCert()==null&&listEdit.get(i+1).getBillCert()==null)){
				
			}else if(!listEdit.get(i).getBillCert().equals(listEdit.get(i+1).getBillCert())){
				if(listEdit.get(i+1).getBillCert().indexOf("_")!=-1){
					sb.append(" 银行账单:"+listEdit.get(i+1).getBillCert().substring(listEdit.get(i+1).getBillCert().indexOf("_")+1,listEdit.get(i+1).getBillCert().length())+",");
				}
			}
			if((listEdit.get(i).getSecurityCert()==null&&listEdit.get(i+1).getSecurityCert()!=null)){
				if(listEdit.get(i+1).getSecurityCert().indexOf("_")!=-1){
					sb.append(" 保险凭证:"+listEdit.get(i+1).getSecurityCert().substring(listEdit.get(i+1).getSecurityCert().indexOf("_")+1,listEdit.get(i+1).getSecurityCert().length())+",");
				}
			}else if((listEdit.get(i).getSecurityCert()==null&&listEdit.get(i+1).getSecurityCert()==null)){
				
			}else if(!listEdit.get(i).getSecurityCert().equals(listEdit.get(i+1).getSecurityCert())){
				if(listEdit.get(i+1).getSecurityCert().indexOf("_")!=-1){
					sb.append(" 保险凭证:"+listEdit.get(i+1).getSecurityCert().substring(listEdit.get(i+1).getSecurityCert().indexOf("_")+1,listEdit.get(i+1).getSecurityCert().length())+",");
				}
			}
			
			if((listEdit.get(i).getBreachCert()==null&&listEdit.get(i+1).getBreachCert()!=null)){
				if(listEdit.get(i+1).getBreachCert().indexOf("_")!=-1){
					sb.append(" 违法记录:"+listEdit.get(i+1).getBreachCert().substring(listEdit.get(i+1).getBreachCert().indexOf("_")+1,listEdit.get(i+1).getBreachCert().length())+",");
				}
			}else if((listEdit.get(i).getBreachCert()==null&&listEdit.get(i+1).getBreachCert()==null)){
				
			}else if(!listEdit.get(i).getBreachCert().equals(listEdit.get(i+1).getBreachCert())){
				if(listEdit.get(i+1).getBreachCert().indexOf("_")!=-1){
					sb.append(" 违法记录:"+listEdit.get(i+1).getBreachCert().substring(listEdit.get(i+1).getBreachCert().indexOf("_")+1,listEdit.get(i+1).getBreachCert().length())+",");
				}
			}*/
			if((listEdit.get(i).getLegalName()==null&&listEdit.get(i+1).getLegalName()!=null)){
				sb.append(" 法人姓名:"+listEdit.get(i+1).getLegalName()+",");
			}else if((listEdit.get(i).getLegalName()==null&&listEdit.get(i+1).getLegalName()==null)){
				
			}else if(!listEdit.get(i).getLegalName().equals(listEdit.get(i+1).getLegalName())){
				sb.append(" 法人姓名:"+listEdit.get(i+1).getLegalName()+",");
			}
			if((listEdit.get(i).getLegalIdCard()==null&&listEdit.get(i+1).getLegalIdCard()!=null)){
				sb.append(" 法人身份证:"+listEdit.get(i+1).getLegalIdCard()+",");
			}else if((listEdit.get(i).getLegalIdCard()==null&&listEdit.get(i+1).getLegalIdCard()==null)){
				
			}else if(!listEdit.get(i).getLegalIdCard().equals(listEdit.get(i+1).getLegalIdCard())){
				sb.append(" 法人身份证:"+listEdit.get(i+1).getLegalIdCard()+",");
			}
			
			if((listEdit.get(i).getLegalMobile()==null&&listEdit.get(i+1).getLegalMobile()!=null)){
				sb.append(" 法人手机:"+listEdit.get(i+1).getLegalMobile()+",");
			}else if((listEdit.get(i).getLegalMobile()==null&&listEdit.get(i+1).getLegalMobile()==null)){
				
			}else if(!listEdit.get(i).getLegalMobile().equals(listEdit.get(i+1).getLegalMobile())){
				sb.append(" 法人手机:"+listEdit.get(i+1).getLegalMobile()+",");
			}
			if((listEdit.get(i).getLegalTelephone()==null&&listEdit.get(i+1).getLegalTelephone()!=null)){
				sb.append(" 法人电话:"+listEdit.get(i+1).getLegalTelephone()+",");
			}else if((listEdit.get(i).getLegalTelephone()==null&&listEdit.get(i+1).getLegalTelephone()==null)){
				
			}else if(!listEdit.get(i).getLegalTelephone().equals(listEdit.get(i+1).getLegalTelephone())){
				sb.append(" 法人电话:"+listEdit.get(i+1).getLegalTelephone()+",");
			}
			if((listEdit.get(i).getContactName()==null&&listEdit.get(i+1).getContactName()!=null)){
				sb.append(" 联系人姓名:"+listEdit.get(i+1).getContactName()+",");
			}else if((listEdit.get(i).getContactName()==null&&listEdit.get(i+1).getContactName()==null)){
				
			}else if(!listEdit.get(i).getContactName().equals(listEdit.get(i+1).getContactName())){
				sb.append(" 联系人姓名:"+listEdit.get(i+1).getContactName()+",");
			}
			if((listEdit.get(i).getContactTelephone()==null&&listEdit.get(i+1).getContactTelephone()!=null)){
				sb.append(" 联系人手机:"+listEdit.get(i+1).getContactTelephone()+",");
			}else if((listEdit.get(i).getContactTelephone()==null&&listEdit.get(i+1).getContactTelephone()==null)){
				
			}else if(!listEdit.get(i).getContactTelephone().equals(listEdit.get(i+1).getContactTelephone())){
				sb.append(" 联系人手机:"+listEdit.get(i+1).getContactTelephone()+",");
			}
			if((listEdit.get(i).getContactMobile()==null&&listEdit.get(i+1).getContactMobile()!=null)){
				sb.append(" 联系人电话:"+listEdit.get(i+1).getContactMobile()+",");
			}else if((listEdit.get(i).getContactMobile()==null&&listEdit.get(i+1).getContactMobile()==null)){
				
			}else if(!listEdit.get(i).getContactMobile().equals(listEdit.get(i+1).getContactMobile())){
				sb.append(" 联系人电话:"+listEdit.get(i+1).getContactMobile()+",");
			}
			if((listEdit.get(i).getContactFax()==null&&listEdit.get(i+1).getContactFax()!=null)){
				sb.append(" 联系人传真:"+listEdit.get(i+1).getContactFax()+",");
			}else if((listEdit.get(i).getContactFax()==null&&listEdit.get(i+1).getContactFax()==null)){
				
			}else if(!listEdit.get(i).getContactFax().equals(listEdit.get(i+1).getContactFax())){
				sb.append(" 联系人传真:"+listEdit.get(i+1).getContactFax()+",");
			}
			if((listEdit.get(i).getContactEmail()==null&&listEdit.get(i+1).getContactEmail()!=null)){
				sb.append(" 联系人邮箱:"+listEdit.get(i+1).getContactEmail()+",");
			}else if((listEdit.get(i).getContactEmail()==null&&listEdit.get(i+1).getContactEmail()==null)){
				
			}else if(!listEdit.get(i).getContactEmail().equals(listEdit.get(i+1).getContactEmail())){
				sb.append(" 联系人邮箱:"+listEdit.get(i+1).getContactEmail()+",");
			}
			if((listEdit.get(i).getContactAddress()==null&&listEdit.get(i+1).getContactAddress()!=null)){
				sb.append(" 联系人地址:"+listEdit.get(i+1).getContactAddress()+",");
			}else if((listEdit.get(i).getContactAddress()==null&&listEdit.get(i+1).getContactAddress()==null)){
				
			}else if(!listEdit.get(i).getContactAddress().equals(listEdit.get(i+1).getContactAddress())){
				sb.append(" 联系人地址:"+listEdit.get(i+1).getContactAddress()+",");
			}
			if((listEdit.get(i).getCreditCode()==null&&listEdit.get(i+1).getCreditCode()!=null)){
				sb.append(" 社会信用码:"+listEdit.get(i+1).getContactAddress()+",");
			}else if((listEdit.get(i).getCreditCode()==null&&listEdit.get(i+1).getCreditCode()==null)){
				
			}else if(!listEdit.get(i).getCreditCode().equals(listEdit.get(i+1).getCreditCode())){
				sb.append(" 社会信用码:"+listEdit.get(i+1).getContactAddress()+",");
			}
			if((listEdit.get(i).getRegistAuthority()==null&&listEdit.get(i+1).getRegistAuthority()!=null)){
				sb.append(" 注册机关:"+listEdit.get(i+1).getContactAddress()+",");
			}else if((listEdit.get(i).getRegistAuthority()==null&&listEdit.get(i+1).getRegistAuthority()==null)){
				
			}else if(!listEdit.get(i).getRegistAuthority().equals(listEdit.get(i+1).getRegistAuthority())){
				sb.append(" 注册机关:"+listEdit.get(i+1).getContactAddress()+",");
			}
			if((listEdit.get(i).getRegistFund()==null&&listEdit.get(i+1).getRegistFund()!=null)){
				sb.append(" 注册资本:"+listEdit.get(i+1).getContactAddress()+",");
			}else if((listEdit.get(i).getRegistFund()==null&&listEdit.get(i+1).getRegistFund()==null)){
				
			}else if(!listEdit.get(i).getRegistFund().equals(listEdit.get(i+1).getRegistFund())){
				sb.append(" 注册资本:"+listEdit.get(i+1).getContactAddress()+",");
			}
			if((listEdit.get(i).getBusinessStartDate()==null&&listEdit.get(i+1).getBusinessStartDate()!=null)){
				sb.append(" 营业期限开始时间:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getBusinessStartDate())+",");
			}else if((listEdit.get(i).getBusinessStartDate()==null&&listEdit.get(i+1).getBusinessStartDate()==null)){
				
			}else if(!listEdit.get(i).getBusinessStartDate().equals(listEdit.get(i+1).getBusinessStartDate())){
				sb.append(" 营业期限开始时间:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getBusinessStartDate())+",");
			}
			if((listEdit.get(i).getBusinessEndDate()==null&&listEdit.get(i+1).getBusinessEndDate()!=null)){
				sb.append(" 营业期限结束时间:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getBusinessEndDate())+",");
			}else if((listEdit.get(i).getBusinessEndDate()==null&&listEdit.get(i+1).getBusinessEndDate()==null)){
				
			}else if(!listEdit.get(i).getBusinessEndDate().equals(listEdit.get(i+1).getBusinessEndDate())){
				sb.append(" 营业期限结束时间:"+new SimpleDateFormat("YYYY-MM-dd").format(listEdit.get(i+1).getBusinessEndDate())+",");
			}
			if((listEdit.get(i).getBusinessScope()==null&&listEdit.get(i+1).getBusinessScope()!=null)){
				sb.append(" 营业范围:"+listEdit.get(i+1).getBusinessScope()+",");
			}else if((listEdit.get(i).getBusinessScope()==null&&listEdit.get(i+1).getBusinessScope()==null)){
				
			}else if(!listEdit.get(i).getBusinessScope().equals(listEdit.get(i+1).getBusinessScope())){
				sb.append(" 营业范围:"+listEdit.get(i+1).getBusinessScope()+",");
			}
			if((listEdit.get(i).getBusinessPostCode()==null&&listEdit.get(i+1).getBusinessPostCode()!=null)){
				sb.append(" 境外分支邮编:"+listEdit.get(i+1).getBusinessPostCode()+",");
			}else if((listEdit.get(i).getBusinessPostCode()==null&&listEdit.get(i+1).getBusinessPostCode()==null)){
				
			}else if(!listEdit.get(i).getBusinessPostCode().equals(listEdit.get(i+1).getBusinessPostCode())){
				sb.append(" 境外分支邮编:"+listEdit.get(i+1).getBusinessPostCode()+",");
			}
			
			if((listEdit.get(i).getBusinessAddress()==null&&listEdit.get(i+1).getBusinessAddress()!=null)){
				sb.append(" 分支地址:"+listEdit.get(i+1).getBusinessAddress()+",");
			}else if((listEdit.get(i).getBusinessAddress()==null&&listEdit.get(i+1).getBusinessAddress()==null)){
				
			}else if(!listEdit.get(i).getBusinessAddress().equals(listEdit.get(i+1).getBusinessAddress())){
				sb.append(" 分支地址:"+listEdit.get(i+1).getBusinessAddress()+",");
			}
			if((listEdit.get(i).getOverseasBranch()==null&&listEdit.get(i+1).getOverseasBranch()!=null)){
				sb.append(" 是否是境外分支结构:"+listEdit.get(i+1).getOverseasBranch()+",");
			}else if((listEdit.get(i).getOverseasBranch()==null&&listEdit.get(i+1).getOverseasBranch()==null)){
				
			}else if(!listEdit.get(i).getOverseasBranch().equals(listEdit.get(i+1).getOverseasBranch())){
				sb.append(" 是否是境外分支结构:"+listEdit.get(i+1).getOverseasBranch()+",");
			}
			if((listEdit.get(i).getBranchCountry()==null&&listEdit.get(i+1).getBranchCountry()!=null)){
				sb.append(" 分支所在国家范围:"+listEdit.get(i+1).getBranchCountry()+",");
			}else if((listEdit.get(i).getBranchCountry()==null&&listEdit.get(i+1).getBranchCountry()==null)){
				
			}else if(!listEdit.get(i).getBranchCountry().equals(listEdit.get(i+1).getBranchCountry())){
				sb.append(" 分支所在国家范围:"+listEdit.get(i+1).getBranchCountry()+",");
			}
			if((listEdit.get(i).getBranchAddress()==null&&listEdit.get(i+1).getBranchAddress()!=null)){
				sb.append(" 详细地址:"+listEdit.get(i+1).getBranchAddress()+",");
			}else if((listEdit.get(i).getBranchAddress()==null&&listEdit.get(i+1).getBranchAddress()==null)){
				
			}else if(!listEdit.get(i).getBranchAddress().equals(listEdit.get(i+1).getBranchAddress())){
				sb.append(" 详细地址:"+listEdit.get(i+1).getBranchAddress()+",");
			}
			if((listEdit.get(i).getBranchBusinessScope()==null&&listEdit.get(i+1).getBranchBusinessScope()!=null)){
				sb.append(" 生产经营范围:"+listEdit.get(i+1).getBranchBusinessScope()+",");
			}else if((listEdit.get(i).getBranchBusinessScope()==null&&listEdit.get(i+1).getBranchBusinessScope()==null)){
				
			}else if(!listEdit.get(i).getBranchBusinessScope().equals(listEdit.get(i+1).getBranchBusinessScope())){
				sb.append(" 生产经营范围:"+listEdit.get(i+1).getBranchBusinessScope()+",");
			}
			/*if((listEdit.get(i).getBusinessCert()==null&&listEdit.get(i+1).getBusinessCert()!=null)){
				sb.append(" 营业执照:"+listEdit.get(i+1).getBusinessCert()+",");
			}else if((listEdit.get(i).getBusinessCert()==null&&listEdit.get(i+1).getBusinessCert()==null)){
			}else if(!listEdit.get(i).getBusinessCert().equals(listEdit.get(i+1).getBusinessCert())){
				sb.append(" 营业执照:"+listEdit.get(i+1).getBusinessCert()+",");
			}*/
			if((listEdit.get(i).getBranchName()==null&&listEdit.get(i+1).getBranchName()!=null)){
				sb.append(" 分支名称:"+listEdit.get(i+1).getBranchName()+",");
			}else if((listEdit.get(i).getBranchName()==null&&listEdit.get(i+1).getBranchName()==null)){
			}else if(!listEdit.get(i).getBranchName().equals(listEdit.get(i+1).getBranchName())){
				sb.append(" 分支名称:"+listEdit.get(i+1).getBranchName()+",");
			}
			String str=sb.toString();
			//大于5说明有修改否则无修改
			if(str.length()>5){
				list.add(str+"^-^"+new SimpleDateFormat("HH:mm:ss YYYY-MM-dd").format(listEdit.get(i+1).getCreateDate()));
			}
		}
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
	  
	 public String getProvince(String str){
		 String province="";
		 if("Guangxi".equals(str)){
			 province="广西";
		 }else if("Inner Mongol".equals(str)){
			 province="内蒙古";
		 }else if("Jiangsu".equals(str)){
			 province="江苏";
		 }else if("Chongqing".equals(str)){
			 province="重庆";
		 }else if("Guizhou".equals(str)){
			 province="贵州";
		 }else if("Fujian".equals(str)){
			 province="福建";
		 }else if("Gansu".equals(str)){
			 province="甘肃";
		 }else if("Henan".equals(str)){
			 province="河南";
		 }else if("Hebei".equals(str)){
			 province="河北";
		 }else if("Xinjiang".equals(str)){
			 province="新疆";
		}else if("Anhui".equals(str)){
			 province="安徽";
		 }else if("Hunan".equals(str)){
			 province="湖南";
		 }else if("Hubei".equals(str)){
			 province="湖北";
		 }else if("Jiangxi".equals(str)){
			 province="江西";
		 }else if("Qinghai".equals(str)){
			 province="青海";
		 }else if("Ningxia".equals(str)){
			 province="宁夏";
		 }else if("Taiwan".equals(str)){
			 province="台湾";
		 }else if("Hainan".equals(str)){
			 province="海南";
		 }else if("Sichuan".equals(str)){
			 province="四川";
		 }else if("Shaanxi".equals(str)){
			 province="陕西";
		 }else if("Xizang".equals(str)){
			 province="西藏";
		 }else if("Macau".equals(str)){
			 province="澳门";
		 }else if("Guangdong".equals(str)){
			 province="广东";
		 }else if("Beijing".equals(str)){
			 province="北京";
		 }else if("Shanghai".equals(str)){
			 province="上海";
		 }else if("Zhejiang".equals(str)){
			 province="浙江";
		 }else if("HongKong".equals(str)){
			 province="香港";
		 }else if("Liaoning".equals(str)){
			 province="辽宁";
		 }else if("Yunnan".equals(str)){
			 province="云南";
		 }else if("Heilongjiang".equals(str)){
			 province="黑龙江";
		}else if("Shanxi".equals(str)){
			 province="山西";
		 }else if("Shandong".equals(str)){
			 province="山东";
		 }else if("Tianjin".equals(str)){
			 province="天津";
		 }else if("Jilin".equals(str)){
			 province="吉林";
		 }
		return province;
		 
	 }
	 
	 public Map<String ,Object> getAllProvince(){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("安徽","an_hui");
			map.put("湖南","hu_nan");
			map.put("湖北","hu_bei");
			map.put("江西","jiang_xi" );
			map.put("青海","qing_hai" );
			map.put("宁夏","ning_xia" );
			map.put("台湾","tai_wan" );
			map.put("海南","hai_nan" );
			map.put("四川","si_chuan" );
			map.put("陕西","shan_xi_1" );//陕西
			
			map.put("西藏","xi_zang" );
			map.put("澳门","ao_men" );
			map.put("广东","guang_dong" );
			map.put("北京","bei_jing" );
			map.put("上海","shang_hai" );
			map.put("浙江","zhe_jiang" );
			map.put("香港","xiang_gang" );
			map.put("辽宁","liao_ning" );
			map.put("云南","yun_nan" );
			map.put("黑龙江","hei_long_jiang" );
			
			map.put("广西","guang_xi" );
			map.put("内蒙古","nei_meng_gu" );
			map.put("江苏","jiang_su" );
			map.put("重庆","chong_qing" );
			map.put("贵州","gui_zhou" );
			map.put("福建","fu_jian" );
			map.put("甘肃","gan_su" );
			map.put("河南","he_nan" );
			map.put("河北","he_bei");
			map.put("新疆","xin_jiang" );
			
			map.put("山西","shan_xi_2" );//山西
			map.put("山东","shan_dong");
			map.put("天津","tian_jin" );
			map.put("吉林","ji_lin" );
			return map;
		}
		public  Map<String ,Integer> getMap(){
			Map<String,Integer> map= new HashMap<String,Integer>(40);
			map.put("an_hui", 0);
			map.put("hu_nan", 0);
			map.put("hu_bei", 0);
			map.put("jiang_xi", 0);
			map.put("qing_hai", 0);
			map.put("ning_xia", 0);
			map.put("tai_wan", 0);
			map.put("hai_nan", 0);
			map.put("si_chuan", 0);
			map.put("shan_xi_1", 0);//陕西
			
			map.put("xi_zang", 0);
			map.put("ao_men", 0);
			map.put("guang_dong", 0);
			map.put("bei_jing", 0);
			map.put("shang_hai", 0);
			map.put("zhe_jiang", 0);
			map.put("xiang_gang", 0);
			map.put("liao_ning", 0);
			map.put("yun_nan", 0);
			map.put("hei_long_jiang", 0);
			
			map.put("guang_xi", 0);
			map.put("nei_meng_gu", 0);
			map.put("jiang_su", 0);
			map.put("chong_qing", 0);
			map.put("gui_zhou", 0);
			map.put("fu_jian", 0);
			map.put("gan_su", 0);
			map.put("he_nan", 0);
			map.put("he_bei",0);
			map.put("xin_jiang", 0);
			
			map.put("shan_xi_2", 0);//山西
			map.put("shan_dong",0);
			map.put("tian_jin", 0);
			map.put("ji_lin", 0);
			return map;
		}
	
	public void getSupplierType(List<Supplier> listSupplier){
		for(Supplier sup:listSupplier){
			String supplierTypes = supplierService.selectSupplierTypes(sup);
			sup.setSupplierType(supplierTypes);
		}
	}
	public void getSupplierType(Supplier supplier){
			String supplierTypes = supplierService.selectSupplierTypes(supplier);
			supplier.setSupplierType(supplierTypes);
	}
	
}
