package ses.dao.sms;

import ses.model.sms.SupplierExtUser;

public interface SupplierExtUserMapper {
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
    int insert(SupplierExtUser record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierExtUser record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierExtUser selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierExtUser record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierExtUser record);
}