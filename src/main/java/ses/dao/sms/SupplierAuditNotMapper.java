package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;

public interface SupplierAuditNotMapper {
    /**
     * @Title: insertSelective
     * @author XuQing 
     * @date 2017-1-10 下午4:19:35  
     * @Description:插入记录
     * @param @param record
     * @param @return      
     * @return int
     */
    int insertSelective(SupplierAuditNot record);
    
    /** 
     * @Title: selectByPrimaryKey
     * @author XuQing 
     * @date 2017-1-10 下午4:19:52  
     * @Description:查询
     * @param @param record
     * @param @return      
     * @return List<SupplierAudit>
     */
    List<SupplierAuditNot> selectByPrimaryKey(SupplierAuditNot record);
} 