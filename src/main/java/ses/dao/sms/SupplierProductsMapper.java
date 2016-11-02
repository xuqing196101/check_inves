package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierProducts;

public interface SupplierProductsMapper {
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
    int insert(SupplierProducts record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierProducts record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierProducts selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierProducts record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierProducts record);
    
    List<SupplierProducts> findSupplierProductsBySupplierId(String supplierId);
    
    int deleteBySupplierId(String supplierId);
    
    int deleteByItemId(String itemId);
    
    List<SupplierProducts> findProductsByItemId(String itemId);
    
}