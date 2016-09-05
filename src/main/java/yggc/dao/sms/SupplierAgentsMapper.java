package yggc.dao.sms;

import yggc.model.sms.SupplierAgents;

public interface SupplierAgentsMapper {
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
    int insert(SupplierAgents record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierAgents record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierAgents selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierAgents record);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeyWithBLOBs(SupplierAgents record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierAgents record);
}