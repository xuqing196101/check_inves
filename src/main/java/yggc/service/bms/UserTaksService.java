package yggc.service.bms;

import java.util.List;
import java.util.Map;

import yggc.formbean.UserTaskFormBean;
import yggc.model.bms.UserTask;
/**
 * 
 * <p>Title: UserTaksService</p>
 * <p>Description:用户任务管理service接口 </p> 
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:40:51
 *
 */
public interface UserTaksService {
	/**
	* @Title: add
	* @Description: 增加任务
	* author: Li Xiaoxiao 
	* @param @param userTask     
	* @return void     
	* @throws
	 */
	public void add(UserTask userTask);
	/**
	 * 修改任务
	* @Title: update
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param userTask     
	* @return void     
	* @throws
	 */
	public void update(UserTask userTask);
	/**
	 * 
	* @Title: delete
	* @Description: 删除任务
	* author: Li Xiaoxiao 
	* @param @param id     
	* @return void     
	* @throws
	 */
	public void delete(String id);
	/**
	 * 
	* @Title: getById
	* @Description: 根据id查询任务 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return UserTask     
	* @throws
	 */
	public UserTask getById(String id);
	/**
	* @Title: getAl
	* @Description: 查询当前月的所有任务 
	* author: Li Xiaoxiao 
	* @param @param map
	* @param @return     
	* @return List<UserTaskFormBean>     
	* @throws
	 */
	public List<UserTaskFormBean> getAl(Map<String,Object> map);
}
