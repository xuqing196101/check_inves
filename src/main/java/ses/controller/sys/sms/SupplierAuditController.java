package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierFinance;
import ses.service.sms.SupplierAuditServlice;

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
	@RequestMapping("daiBan")
	public String daiBan() {
		
		return "ses/sms/supplier_audit/daiban";
	}
	/**
	 * @Title: SupplierList
	 * @author Xu Qing
	 * @date 2016-9-12 下午5:19:07  
	 * @Description: 供应商列表 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("supplierList")
	public String supplierList(HttpServletRequest request) {
		List<Supplier> supplierList =supplierAuditServlice.supplierList();
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
		String id = request.getParameter("id");
		Supplier supplier = supplierAuditServlice.supplierById(id);
		request.setAttribute("supplier", supplier);
		request.getAttribute(id);
/*		model.addAttribute("id",id);*/
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
		String id = request.getParameter("id");
		List<SupplierFinance> supplierFinance = supplierAuditServlice.supplierFinanceByid(id);
		request.setAttribute("supplier", supplierFinance);
		return "ses/sms/supplier_audit/financial";
	}
	/**
	 * @Title: shareholderinformation
	 * @author Xu Qing
	 * @date 2016-9-13 上午11:19:37  
	 * @Description: 股东信息 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("shareholder")
	public String shareholderinformation() {
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
	
}
