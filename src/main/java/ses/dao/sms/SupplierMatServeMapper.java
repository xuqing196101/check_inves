package ses.dao.sms;

import ses.model.sms.SupplierMatServe;

public interface SupplierMatServeMapper {
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
    int insert(SupplierMatServe record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierMatServe record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatServe selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatServe record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatServe record);
    
    SupplierMatServe getMatSeBySupplierId(String supplierId);
}