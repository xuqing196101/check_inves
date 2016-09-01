package yggc.dao.sms;

import yggc.model.sms.SupplierInfo;

public interface SupplierInfoMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(SupplierInfo record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierInfo record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierInfo selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierInfo record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierInfo record);
}