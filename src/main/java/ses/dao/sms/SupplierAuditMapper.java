package ses.dao.sms;

import java.util.List;

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
     * @date 2016-9-20 下午2:15:44  
     * @Description: 插入审核记录 
     * @param @param record
     * @param @return      
     * @return int
     */
    int insertSelective(SupplierAudit record);
    
    /**
     * @Title: findAll
     * @author Xu Qing
     * @date 2016-9-20 下午2:16:33  
     * @Description:查询所有审核记录 
     * @param @return      
     * @return List<SupplierAudit>
     */
    List<SupplierAudit> findAll(); 
}