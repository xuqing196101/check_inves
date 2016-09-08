package ses.dao.bms;

import java.util.List;
import java.util.Map;

import ses.model.bms.UserTask;

/**
 * 
 * @Title: UserTaskMapper
 * @Description:用户任务管理dao接口  
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:32:27
 *
 */
public interface UserTaskMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @Description: 根据id删除任务
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return int     
	* @throws
	 */
    int deleteByPrimaryKey(String id);
    /**
     * 
    * @Title: insert
    * @Description: 新增任务
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int insert(UserTask record);
    /**
     * 
    * @Title: insertSelective
    * @Description:  新增任务
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int insertSelective(UserTask record);
    /**
    * @Title: selectByPrimaryKey
    * @Description: 根据id查询一条任务
    * author: Li Xiaoxiao 
    * @param @param id
    * @param @return     
    * @return UserTask     
    * @throws
     */
    UserTask selectByPrimaryKey(String id);
    /**
    * @Title: updateByPrimaryKeySelective
    * @Description: 修改任务 
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int updateByPrimaryKeySelective(UserTask record);
    /**
     * 
    * @Title: updateByPrimaryKey
    * @Description: 修改任务
    * author: Li Xiaoxiao 
    * @param @param record
    * @param @return     
    * @return int     
    * @throws
     */
    int updateByPrimaryKey(UserTask record);
    /**
    * @Title: getByMonth
    * @Description:查询当前月的数据
    * author: Li Xiaoxiao 
    * @param @param map
    * @param @return     
    * @return List<UserTask>     
    * @throws
     */
    List<UserTask> getByMonth(Map<String,Object> map);
}