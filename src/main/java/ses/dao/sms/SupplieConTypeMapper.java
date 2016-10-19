package ses.dao.sms;

import ses.model.sms.SupplieConType;

public interface SupplieConTypeMapper {
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
    int insert(SupplieConType record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplieConType record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplieConType selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplieConType record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplieConType record);
}