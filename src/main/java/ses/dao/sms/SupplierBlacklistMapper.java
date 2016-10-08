package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierBlacklist;

public interface SupplierBlacklistMapper {
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
    int insert(SupplierBlacklist record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierBlacklist record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierBlacklist selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierBlacklist record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierBlacklist record);
    
    List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist);
}