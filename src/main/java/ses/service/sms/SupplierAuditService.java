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

public interface SupplierAuditService {
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 下午2:10:56  
	 * @Description: 供应商列表,可条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	 List<Supplier> supplierList(Supplier supplier,Integer page);
	
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
	 * @Description: 记录审核原因
	 * @param @param supplierAudit      
	 * @return void
	 */
	void auditReasons (SupplierAudit supplierAudit);
	
	/**
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */
    List<SupplierAudit> selectByPrimaryKey(String supplierId);
    
    /**
     * @Title: updateStatus
     * @author Xu Qing
     * @date 2016-9-20 下午7:24:46  
     * @Description: 根据供应商ID更新审核状态 
     * @param @param supplierId      
     * @return void
     */
    void updateStatus (Supplier supplier);
    
    /**
     * @Title: getCount
     * @author Xu Qing
     * @date 2016-9-21 上午10:14:27  
     * @Description:根据审核状态获取条数
     * @param @param supplier
     * @param @return      
     * @return Integer
     */
    Integer getCount(Supplier supplier);
}
