package ses.service.sms;

import ses.model.sms.SupplierAuditNot;
/**
 * <p>Title:SupplierAuditServlice </p>
 * <p>Description: 供应商审核接口</p>
 * @author Xu Qing
 * @date 2016-9-12下午5:12:02
 */

public interface SupplierAuditNotService {
    /**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-1-10 下午4:19:35  
     * @Description:插入记录
     * @param @param record
     * @param @return      
     * @return int
     */
    int insertSelective(SupplierAuditNot supplierAuditNot);
    
    /** 
     * @Title: selectByPrimaryKey
     * @author XuQing 
     * @date 2017-1-10 下午4:19:52  
     * @Description:查询
     * @param @param record
     * @param @return      
     * @return List<SupplierAudit>
     */
    SupplierAuditNot selectByPrimaryKey(SupplierAuditNot supplierAuditNot);
    
    /**
     * @Title: selectByCreditCode
     * @author XuQing 
     * @date 2017-5-8 下午4:51:38  
     * @Description:根据信用代码查询
     * @param @param creditCode
     * @param @return      
     * @return SupplierAuditNot
     */
    SupplierAuditNot selectByCreditCode(String creditCode);
	
} 
