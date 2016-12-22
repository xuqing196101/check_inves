package ses.dao.ems;

import java.util.List;
import java.util.Map;

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

    /**
     * 
     *〈简述〉更具关联包id查询是否有未抽取的条件
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param id
     * @return
     */
    public Integer getCount(String packId);

    /**
     * 
     *〈简述〉抽取完成后删除未操作的信息
     *〈详细描述〉
     * @author Wang Wenshuai
     */
    public void deleteInfo(String conditionId);
}