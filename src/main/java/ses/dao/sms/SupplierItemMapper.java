package ses.dao.sms;

import java.util.List;

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
}