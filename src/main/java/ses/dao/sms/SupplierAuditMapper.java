package ses.dao.sms;

import org.apache.ibatis.annotations.Param;
import ses.model.sms.SupplierAudit;

import java.util.List;
import java.util.Map;

public interface SupplierAuditMapper {
    int deleteByPrimaryKey(String id);

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
     * 
     * Description:根据参数 查询 数据是否存在
     * 
     * @author YangHongLiang
     * @version 2017-6-28
     * @param record
     * @return
     */
    int countByPrimaryKey(SupplierAudit record);
    
    /**
     * 
    * @Title: inserActive
    * @Description: 无主键插入
    * author: Li Xiaoxiao 
    * @param @param record     
    * @return void     
    * @throws
     */
    void inserActive(SupplierAudit record);
    
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
    
    /**
     * @Title: deleteBySupplierId
     * @author XuQing 
     * @date 2017-2-14 下午4:59:14  
     * @Description:删除记录
     * @param @param supplierId      
     * @return void
     */
    void deleteBySupplierId(String supplierId);
    /**
     * 
    * @Title: selectById
    * @Description: 根据ID查询
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return SupplierAudit     
    * @throws
     */
    SupplierAudit selectById(@Param("id")String id);
    
    /**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param supplierHistory      
     * @return void
     */
    void updateIsDeleteBySupplierId(SupplierAudit supplierAudit);
    
    /**
     * 
     * Description:查询注册供应商不通过的小类
     * 
     * @author Easong
     * @version 2017年6月28日
     * @param map
     * @return
     */
    Integer selectRegSupCateCount(Map<String, Object> map);
    /**
     *
     * Description: 查询基本信息的审核项数量
     *
     * @author Easong
     * @version 2017/7/13
     * @param [map]
     * @since JDK1.7
     */
    Integer selectBasicInfoAuditItem(Map<String, Object> map);
} 