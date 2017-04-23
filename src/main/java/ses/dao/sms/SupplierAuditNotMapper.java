package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    SupplierAuditNot selectByPrimaryKey(SupplierAuditNot record);
    
    /** 
     * @Title: selectByPrimaryKey
     * @author 根据地id查询
     * @date 2017-1-10 下午4:19:52  
     * @Description:查询
     * @param @param record
     * @param @return      
     * @return List<SupplierAudit>
     */
    SupplierAuditNot selectById(@Param("id")String id);
    
    /**
     * 
    * @Title: selectQueryBySupplierId
    * @Description:根据供应商ID查询 
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @return     
    * @return List<SupplierAuditNot>     
    * @throws
     */
    List<SupplierAuditNot> selectQueryBySupplierId(@Param("supplierId")String supplierId);
} 