package ses.dao.sms;

import ses.model.sms.SupplierMatSell;

public interface SupplierMatSellMapper {
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
    int insert(SupplierMatSell record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierMatSell record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatSell selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatSell record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatSell record);
    
    SupplierMatSell getMatSellBySupplierId(String supplierId);
}