package ses.dao.sms;

import ses.model.sms.SupplierMatSe;

public interface SupplierMatSeMapper {
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
    int insert(SupplierMatSe record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierMatSe record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierMatSe selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierMatSe record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierMatSe record);
    
    SupplierMatSe getMatSeBySupplierId(String supplierId);
}