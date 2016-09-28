package ses.controller.sys.sms;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAptitute;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierCertEng;
import ses.model.sms.SupplierCertPro;
import ses.model.sms.SupplierCertSe;
import ses.model.sms.SupplierCertSell;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierMatEng;
import ses.model.sms.SupplierMatPro;
import ses.model.sms.SupplierMatSe;
import ses.model.sms.SupplierMatSell;
import ses.model.sms.SupplierStockholder;
import ses.model.sms.SupplierType;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;

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
	private SupplierAuditService supplierAuditService;
	
	/**
	 * 供应商
	 */
	@Autowired
	private SupplierService supplierService;
	
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
		
		List<Supplier> supplierList =supplierAuditService.supplierList(supplier,page==null?1:page);
		request.setAttribute("result", new PageInfo<>(supplierList));
		request.setAttribute("supplierList", supplierList);
		
		//所有供应商类型
		List<SupplierType> supplierType= supplierAuditService.findSupplierType();
		
		request.setAttribute("supplierType", supplierType);
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
	public String essentialInformation(HttpServletRequest request,Supplier supplier,String supplierId) {
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
	public String financialInformation(HttpServletRequest request,SupplierFinance supplierFinance,Supplier supplier) {
		String supplierId = supplierFinance.getSupplierId();
		List<SupplierFinance> list = supplierAuditService.supplierFinanceBySupplierId(supplierId);
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("financial", list);

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
		request.setAttribute("supplierId", supplierId);
		request.setAttribute("shareholder", list);
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
		//供应商组织机构人员,产品研发能力,产品生产能里,质检测试登记信息
		/*supplierMatPro = supplierAuditService.findSupplierMatProBysupplierId(supplierId);*/
		supplierMatPro =supplierService.get(supplierId).getSupplierMatPro();
		
		request.setAttribute("supplierId", supplierId);	
		request.setAttribute("materialProduction",materialProduction);
		request.setAttribute("supplierMatPros", supplierMatPro);
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
		request.setAttribute("supplierCertSell", supplierCertSell);
		request.setAttribute("supplierMatSells", supplierMatSell);
		request.setAttribute("supplierId", supplierId);
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
	public String serviceInformation(HttpServletRequest request,SupplierMatSe supplierMatSe){
		String supplierId = supplierMatSe.getSupplierId();
		//资质证书信息
		List<SupplierCertSe> supplierCertSe = supplierAuditService.findCertSeBySupplierId(supplierId);
		//组织结构和人员
		supplierMatSe = supplierAuditService.findMatSeBySupplierId(supplierId);
		request.setAttribute("supplierCertSes", supplierCertSe);
		request.setAttribute("supplierMatSes", supplierMatSe);
		request.setAttribute("supplierId", supplierId);
		return "ses/sms/supplier_audit/service_information";
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
		int status = (int) request.getSession().getAttribute("status");
		supplierAudit.setStatus((short) status);
		supplierAudit.setCreatedAt(new Date());
		if(status==0){
			supplierAudit.setAuditType("初审");
		}
		if(status==1){
			supplierAudit.setAuditType("复审");
		}
		supplierAudit.setUserId("EDED66BAC3304F34B75EBCDB88AE427F");
		supplierAuditService.auditReasons(supplierAudit);
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
		List<SupplierAudit> reasonsList = supplierAuditService.selectByPrimaryKey(supplierId);
		request.getSession().getAttribute("status");
		request.setAttribute("supplierId", supplierId);
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
	public String updateStatus(HttpServletRequest request,Supplier supplier,SupplierAudit supplierAudit){
		String supplierId= supplierAudit.getSupplierId();
		supplier.setId(supplierId);
		supplierAuditService.updateStatus(supplier);
		return "redirect:supplierList.html";
	}

	
}
