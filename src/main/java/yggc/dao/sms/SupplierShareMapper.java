package yggc.dao.sms;

import yggc.model.sms.SupplierShare;

public interface SupplierShareMapper {
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
    int insert(SupplierShare record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierShare record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierShare selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierShare record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierShare record);
}