package ses.dao.sms;

import ses.model.sms.SupplierAudit;

public interface SupplierAuditMapper {
    int deleteByPrimaryKey(String id);

    int insert(SupplierAudit record);

    SupplierAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SupplierAudit record);

    int updateByPrimaryKey(SupplierAudit record);
    
    /**
     * @Title: insertSelective
     * @author Xu Qing
     * @date 2016-9-20 上午9:53:44  
     * @Description: 插入审核原因 
     * @param @param record
     * @param @return      
     * @return int
     */
    int insertSelective(SupplierAudit record);
    
    
}