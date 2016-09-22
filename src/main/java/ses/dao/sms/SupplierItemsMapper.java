package ses.dao.sms;

import ses.model.sms.SupplierItems;

public interface SupplierItemsMapper {
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
    int insert(SupplierItems record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierItems record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierItems selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierItems record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierItems record);
}