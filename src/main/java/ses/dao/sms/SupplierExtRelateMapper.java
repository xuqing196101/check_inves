package ses.dao.sms;

import ses.model.sms.SupplierExtRelate;

public interface SupplierExtRelateMapper {
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
    int insert(SupplierExtRelate record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtRelate record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtRelate selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtRelate record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtRelate record);
}