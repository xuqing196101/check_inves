package yggc.dao.sms;

import yggc.model.sms.SupplierFinance;

public interface SupplierFinanceMapper {
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
    int insert(SupplierFinance record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierFinance record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierFinance selectByPrimaryKey(Long id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierFinance record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierFinance record);
}