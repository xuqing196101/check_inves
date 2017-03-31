package ses.dao.bms;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

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
     * @param 更新待办
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
    List<Todos> listTodos(Map map);
    
    /**
     * @Description:获取任务类型
     *
     * @author Wang Wenshuai
     * @date 2016年9月14日 下午1:58:02  
     * @param @return      
     * @return List<String>
     */
    List<String> listUndoType();
    
    /**
     * @Description:修改状态
     *
     * @author Wang Wenshuai
     * @version 2016年10月10日 下午8:17:37  
     * @param @return      
     * @return int
     */
    int updateIsFinish(Todos record);
    
    /**
	 * @Title: updateByUrl
	 * @author Xu Qing
	 * @date 2016-10-24 上午11:06:25  
	 * @Description: 更新待办
	 * @param @param todos      
	 * @return void
	 */
	void updateByUrl(Todos todos);
    
	/**
	 * 
	 *〈简述〉 查询登录用户是否有权限 
	 *〈详细描述〉
	 * @author Wang Wenshuai
	 * @param map 用户id 权限id 
	 * @return 返回权限id
	 */
	List<String> listUserPermission(Map map);
	
  /**
   * 
   *〈简述〉条件查询集合
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param todos
   * @return
   */
  List<Todos> listUrlTodo(Todos todos);
  /**
   * 
  * @Title: getTodos
  * @Description: 根据推送人ID查询对应的代办
  * author: Li Xiaoxiao 
  * @param @param userId
  * @param @return     
  * @return List<Todos>     
  * @throws
   */
  List<Todos> getTodos(@Param("userId")String userId);
  
	
	
}