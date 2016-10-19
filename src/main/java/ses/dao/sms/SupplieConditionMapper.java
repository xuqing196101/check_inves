package ses.dao.sms;

import ses.model.sms.SupplieCondition;

public interface SupplieConditionMapper {
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
    int insert(SupplieCondition record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplieCondition record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplieCondition selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplieCondition record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplieCondition record);
}