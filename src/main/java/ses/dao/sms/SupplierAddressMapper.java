package ses.dao.sms;

import ses.model.sms.SupplierAddress;

public interface SupplierAddressMapper {
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
    int insert(SupplierAddress record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierAddress record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierAddress selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierAddress record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierAddress record);
}