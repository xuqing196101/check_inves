package ses.dao.sms;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierItem;

public interface SupplierItemMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierItem record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierItem record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierItem selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierItem record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierItem record);
    
    List<SupplierItem> findSupplierItemBySupplierId(String supplierId);
    
    List<SupplierItem> findSupplierItemBySupplierIdAndType(SupplierItem supplierItem);
    
    int deleteBySupplierId(String supplierId);
    
    List<String> getSupplierId();
    List<String> getItemBySupplierId();
    
    List<SupplierItem> findByMap(Map<String, Object> param);
    
    int deleteByMap(Map<String, String> param);
    
    List<SupplierItem> getSupplierItem(@Param("supplierId")String supplierId);
    
    /**
     * 
    * @Title: delete
    * @Description: 根据类型id删除有的关联
    * author: Li Xiaoxiao 
    * @param @param relateId     
    * @return void     
    * @throws
     */
    void deleteRelate(@Param("relateId")String relateId ,@Param("supplierId")String supplierId);
    
}