package ses.dao.bms;


import java.util.List;

import ses.model.bms.Todos;

public interface TodosMapper {
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
    int insert(Todos record);

    /**
     *
     * @param record
     */
    int insertSelective(Todos record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    Todos selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Todos record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(Todos record);
    
    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    List<Todos> listTodos(Todos todos);
    
    /**
     * @Description:获取任务类型
     *
     * @author Wang Wenshuai
     * @date 2016年9月14日 下午1:58:02  
     * @param @return      
     * @return List<String>
     */
    List<String> listUndoType();
    
}