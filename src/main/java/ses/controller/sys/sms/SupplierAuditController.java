package ses.controller.sys.sms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierCertServe;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierItem;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierMatServe;
import ses.model.sms.SupplierRegPerson;
import ses.model.sms.SupplierStockholder;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;
import ses.util.FtpUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageInfo;
import common.constant.Constant;

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
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
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
		//未审核条数（0初审）
		supplier.setStatus(0);
		int weishen = supplierAuditService.getCount(supplier);
		//审核中条数（1初审通过也是复审）
		supplier.setStatus(1);
		int shenhezhong =supplierAuditService.getCount(supplier);
		//已审核条数（3复审通过）
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
		todo.setName("供应商复审");
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
		
		//初审、复审的标识
		request.getSession().setAttribute("signs", sign);
	
		//文件
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		supplier = supplierAuditService.supplierById(supplierId);
		request.setAttribute("suppliers", supplier);
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
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		if(supplierId!=null){
			List<SupplierFinance> supplierFinance = supplierService.get(supplierId).getListSupplierFinances();
			request.setAttribute("financial", supplierFinance);
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
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);

		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("销售")){
			url=request.getContextPath()+"/supplierAudit/materialSales.html";
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
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
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
		
		//文件
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		//下一步的跳转页面
		String url = null;
		if(supplierTypeName.contains("服务")){
			url=request.getContextPath()+"/supplierAudit/serviceInformation.html";
		}else{
			url=request.getContextPath()+"/supplierAudit/items.html";
		}
		request.setAttribute("url", url);
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
	public String serviceInformation(HttpServletRequest request,SupplierMatServe supplierMatSe){
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
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		
		return "ses/sms/supplier_audit/service_information";
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
			supplier.setStatus(2); //初审不通过
			supplierAuditService.updateStatus(supplier);
		}
		if(status==1){
			supplier.setStatus(4); //复审不通过
			supplierAuditService.updateStatus(supplier);
		}*/
		
		//唯一检验
		String auditField = supplierAudit.getAuditField();
		String auditType = supplierAudit.getAuditType();
		String auditFieldName = supplierAudit.getAuditFieldName();
		supplierAudit.setSupplierId(id);
		List<SupplierAudit> reasonsList = supplierAuditService.selectByPrimaryKey(supplierAudit);
		boolean same= true;
		for(int i=0 ;i<reasonsList.size(); i++){
			if(reasonsList.get(i).getAuditField().equals(auditField ) && reasonsList.get(i).getAuditType().equals(auditType) && reasonsList.get(i).getAuditFieldName().equals(auditFieldName)){
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
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
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
		supplierAuditService.updateStatus(supplier);
		//更新待办
		supplier = supplierAuditService.supplierById(supplierId);
		if(supplier.getStatus() == 1){
			/*todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商复审");
			todosService.updateByUrl(todos);*/
			
			//待办已完成
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId=" + supplierId);
			
			/**
			 * 推送代办
			 */
			//推送者id
			todos.setSenderId(user.getId());
			//待办名称
			todos.setName("供应商复审");
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
			
			/*todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商初审");
			todosService.updateByUrl(todos);*/
		}
		/*if(supplier.getStatus() == 4 || supplier.getStatus() == 3){
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
			
			todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商复审");
			todosService.updateByUrl(todos);
		}*/
		
		if(supplier.getStatus() == 8){
			// 待办已完成
			todosService.updateIsFinish("supplierAudit/essential.html?supplierId="+supplierId);
			/**
			 *  复审退回修改 ，推送代办
			 */
			//推送者id
			todos.setSenderId(user.getId());
			//待办名称
			todos.setName("供应商复审退回, 需初审 !");
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
			/**
			 * 初审退回修改 ，推送消息
			 */
			//推送者id
			todos.setSenderId(user.getId());
			//待办名字
			todos.setName("供应商信息有误,请修改！");
			
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
		}
		
		
		//审核完更新状态
		supplierAudit.setStatus(supplier.getStatus());
		supplierAudit.setId(supplierAudit.getId());
		supplierAuditService.updateStatusById(supplierAudit);
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
	public void temporaryAudit(HttpServletRequest request,HttpServletResponse response,Supplier supplier) throws IOException{
		String supplierId  = supplier.getId();
		supplier = supplierAuditService.supplierById(supplier.getId());
		Integer status = supplier.getStatus();
		//暂存初审（5：初审中）
		if(status == 0 || status == 5 || status == 8 && status != null){
			supplier.setStatus(5);
			supplierAuditService.updateStatus(supplier);
			
			//更新待办任务
			Todos todos = new Todos();
			todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商初审中");
			todosService.updateByUrl(todos);
			
			String msg = "{\"msg\":\"success\"}";
			super.writeJson(response, msg);
		}
		//暂存复审（6：复审中）
		if(status == 1 || status == 6 && status != null){
			supplier.setStatus(6);
			supplierAuditService.updateStatus(supplier);
			
			//更新待办任务
			Todos todos = new Todos();
			todos.setUrl("supplierAudit/essential.html?supplierId="+supplierId);
			todos.setName("供应商复审中");
			todosService.updateByUrl(todos);
			
			String msg = "{\"msg\":\"success\"}";
			super.writeJson(response, msg);
		}
		
		
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
		//查询品目
		/*List<SupplierTypeTree> listSupplierTypeTrees = categoryService.findCategoryByType(category, supplierId);
		String json = JSON.toJSONStringWithDateFormat(listSupplierTypeTrees, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		*/
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_audit/items";
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
		
		if(supplierId != null){
			List<SupplierItem> listItem= supplierService.get(supplierId).getListSupplierItems();
			request.setAttribute("listItem", listItem);
		}
		//勾选的供应商类型
		String supplierTypeName = supplierAuditService.findSupplierTypeNameBySupplierId(supplierId);
		request.setAttribute("supplierTypeNames", supplierTypeName);
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
		PageInfo<Supplier> result = supplierAuditService.supplierList(supplier);
		request.setAttribute("result", result);
		
		//回显名字
		String supplierName = supplier.getSupplierName();
		Integer status = supplier.getStatus();
		request.setAttribute("supplierName", supplierName);
		request.setAttribute("state", status);
		
		//初审、复审标识
		request.setAttribute("sign",supplier.getSign());
		request.getSession().getAttribute("sign");
		
		return "ses/sms/supplier_audit/supplier_all";
	}	
}
