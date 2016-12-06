package ses.dao.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.SupplierAudit;

public interface SupplierAuditMapper {
    int deleteByPrimaryKey(String ids);

    int insert(SupplierAudit record);

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
     * @Title: selectByPrimaryKey
     * @author Xu Qing
     * @date 2016-9-20 下午5:12:26  
     * @Description: 根据供应商id查询审核汇总 
     * @param @param id
     * @param @return      
     * @return List<SupplierAudit>
     */ 
    List<SupplierAudit> selectByPrimaryKey(SupplierAudit record);
    
    /**
     * @Title: updateBySupplierId
     * @author 插入文件
     * @date 2016-9-29 下午4:50:17  
     * @Description: TODO 
     * @param @param supplierId      
     * @return void
     */
    void updateBySupplierId (SupplierAudit record);
    
    List<SupplierAudit> findByMap(Map<String, Object> param);
    
    int updateByMap(Map<String, Object> param);
} 