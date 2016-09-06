package yggc.service.bms;

import java.util.List;

import yggc.model.bms.User;
import yggc.model.bms.Userrole;

/**
* <p>Title:UserServiceI </p>
* <p>Description: 用户服务接口</p>
* @author Ye MaoLin
* @date 2016-7-27下午4:35:44
*/
public interface UserServiceI {

	/**   
	* @Title: getUserById
	* @author Ye MaoLin
	* @date 2016-7-18 上午9:38:20  
	* @Description:根据id获取用户 
	* @param @param id
	* @return User     
	*/
	public User getUserById(User user);

	/**   
	* @Title: getUserByLogin
	* @author Ye MaoLin
	* @date 2016-7-18 上午9:38:43  
	* @Description: 根据用户名和密码进行登录验证
	* @param @param user
	* @return User     
	*/
	public User getUserByLogin(User user);

	/**   
	* @Title: getAll
	* @author Ye MaoLin
	* @date 2016-7-18 上午9:39:02  
	* @Description: 获取所有的用户 
	* @return void     
	*/
	public List<User> getAll();

	/**   
	* @Title: save
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:34:40  
	* @Description: 新增用户 
	* @param @param user      
	* @return void     
	*/
	public void save(User user);

	/**   
	* @Title: delete
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:34:53  
	* @Description: 删除用户 ，物理删除
	* @param @param id      
	* @return void     
	*/
	public void delete(String id);

	/**   
	* @Title: deleteByLogic
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:35:13  
	* @Description: 删除用户，逻辑删除
	* @param @param id      
	* @return void     
	*/
	public void deleteByLogic(String id);

	/**   
	* @Title: update
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:35:31  
	* @Description: 更新用户信息 
	* @param @param u      
	* @return void     
	*/
	public void update(User u);

	public void saveRelativity(Userrole userrole);

	public List<User> selectUserRole(User user);

}
