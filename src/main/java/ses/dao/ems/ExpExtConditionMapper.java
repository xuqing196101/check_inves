package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpExtCondition;

public interface ExpExtConditionMapper {
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
    int insert(ExpExtCondition record);

    /**
     *
     * @param record
     */
    int insertSelective(ExpExtCondition record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    ExpExtCondition selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(ExpExtCondition record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(ExpExtCondition record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:47:15  
     * @param @param record
     * @param @return      
     * @return List<ExpExtCondition>
     */
    List<ExpExtCondition> list(ExpExtCondition record);
}