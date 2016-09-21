package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierAuditServlice;

import com.github.pagehelper.PageInfo;

/**
 * <p>Title:SupplierAuditController </p>
 * <p>Description: 供应商审核控制类</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:14:36
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierAudit")
public class SupplierAuditController {
	@Autowired
	private SupplierAuditServlice supplierAuditServlice;
	
	/**
	 * @Title: daiBan
	 * @author Xu Qing
	 * @date 2016-9-13 下午2:12:29  
	 * @Description: 待办
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("daiban")
	public String daiBan(Supplier supplier,HttpServletRequest request) {
		//未审核条数（0初审）
		supplier.setStatus(0);
		int weishen = supplierAuditServlice.getCount(supplier);
		//审核中条数（1初审通过也是复审）
		supplier.setStatus(1);
		int shenhezhong =supplierAuditServlice.getCount(supplier);
		//已审核条数（3复审通过）
		supplier.setStatus(3);
		int yishen =supplierAuditServlice.getCount(supplier);
		
		request.setAttribute("weishen", weishen);
		request.setAttribute("shenhezhong", shenhezhong);
		request.setAttribute("yishen", yishen);
		
		return "ses/sms/supplier_audit/total";
	}
	
	/**
	 * @Title: SupplierList
	 * @author Xu Qing
	 * @date 2016-9-12 下午5:19:07  
	 * @Description: 根据审核状态（待办）查询供应商 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("supplierList")
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
		
		List<Supplier> supplierList =supplierAuditServlice.supplierList(supplier,page==null?1:page);
		request.setAttribute("result", new PageInfo<>(supplierList));
		request.setAttribute("supplierList", supplierList);
		return "ses/sms/supplier_audit/supplier_list";
	}
	
	/**
	 * @Title: essentialInformation
	 * @author Xu Qing
	 * @date 2016-9-12 下午7:14:09  
	 * @Description: 基本信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("essential")
	public String essentialInformation(HttpServletRequest request,Model model) {
		String supplierId = request.getParameter("supplierId");
		if(supplierId==null ){
			supplierId = (String) request.getSession().getAttribute("supplierId");
		}
		Supplier supplier = supplierAuditServlice.supplierById(supplierId);
		request.setAttribute("supplier", supplier);
		request.getSession().setAttribute("supplierId", supplierId);
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
	public String financialInformation(HttpServletRequest request) {
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		List<SupplierFinance> supplierFinance = supplierAuditServlice.supplierFinanceBySupplierId(supplierId);
		request.setAttribute("supplier", supplierFinance);
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
	public String shareholderInformation(HttpServletRequest request) {
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		List<SupplierStockholder> supplierStockholder = supplierAuditServlice.ShareholderBySupplierId(supplierId);
		request.setAttribute("shareholder", supplierStockholder);
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
	public String materialProduction() {
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
	public String materialSales(){
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
	public String engineeringInformation(){
		return "ses/sms/supplier_audit/engineering";
	}
	
	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:55:44  
	 * @Description: 记录审核原因
	 * @param @param supplierAudit      
	 * @return void
	 */
	@RequestMapping("auditReasons")
	public void auditReasons(SupplierAudit supplierAudit,HttpServletRequest request){
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		supplierAudit.setSupplierId(supplierId);
		supplierAudit.setUserId("EDED66BAC3304F34B75EBCDB88AE427F");
		supplierAuditServlice.auditReasons(supplierAudit);
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
	public String reasonsList(HttpServletRequest request){
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		List<SupplierAudit> reasonsList = supplierAuditServlice.selectByPrimaryKey(supplierId);
		request.getSession().getAttribute("status");
		System.out.println("ssssssssssssssssssssssssss="+request.getSession().getAttribute("status"));
		request.setAttribute("reasonsList", reasonsList);
		return "ses/sms/supplier_audit/audit_reasons";
	}
	
	/**
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-20 下午7:32:49  
	 * @Description: 根据供应商id更新审核状态
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("updateStatus")
	public String updateStatus(HttpServletRequest request,Supplier supplier){
		String supplierId = (String) request.getSession().getAttribute("supplierId");
		supplier.setId(supplierId);
		supplierAuditServlice.updateStatus(supplier);
		return "redirect:supplierList.html";
	}

}
