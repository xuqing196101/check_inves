package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierFinance;
import ses.model.sms.SupplierStockholder;
/**
 * <p>Title:SupplierAuditServlice </p>
 * <p>Description: 供应商审核接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:02
 */

public interface SupplierAuditServlice {
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表 
	 * @param @return      
	 * @return List<Supplier>
	 */
	public List<Supplier> supplierList();
	
	/**
	 * @Title: supplierById
	 * @author Xu Qing
	 * @date 2016-9-14 下午3:43:26  
	 * @Description: 根据id查询供应商基本信息 
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	Supplier supplierById(String id);
	
	/**
	 * @Title: supplierFinanceByid
	 * @author Xu Qing
	 * @date 2016-9-14 下午5:30:21  
	 * @Description: 根据供应商id查询财务信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierFinance>
	 */
	List<SupplierFinance> supplierFinanceBySupplierId(String supplierId);
	
	/**
	 * @Title: ShareholderById
	 * @author Xu Qing
	 * @date 2016-9-18 上午9:51:00  
	 * @Description: 根据供应商id查询股东信息 
	 * @param @param supplierId
	 * @param @return      
	 * @return List<SupplierStockholder>
	 */
	List<SupplierStockholder> ShareholderBySupplierId(String supplierId);
	
	/**
	 * @Title: auditReasons
	 * @author Xu Qing
	 * @date 2016-9-18 下午5:51:55  
	 * @Description: 审核记录
	 * @param @param supplierAudit      
	 * @return void
	 */
	public void auditReasons (SupplierAudit supplierAudit);
}
