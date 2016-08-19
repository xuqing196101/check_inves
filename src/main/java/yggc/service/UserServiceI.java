package yggc.service;

import java.util.List;

import yggc.model.User;
import yggc.model.Userrole;

/**
* <p>Title:UserServiceI </p>
* <p>Description: 用户服务接口</p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-27下午4:35:44
*/
public interface UserServiceI {

	/**   
	* @Title: getUserById
	* @author yyyml
	* @date 2016-7-18 上午9:38:20  
	* @Description:根据id获取用户 
	* @param @param id
	* @return User     
	*/
	public User getUserById(User user);

	/**   
	* @Title: getUserByLogin
	* @author yyyml
	* @date 2016-7-18 上午9:38:43  
	* @Description: 根据用户名和密码进行登陆验证
	* @param @param user
	* @return User     
	*/
	public User getUserByLogin(User user);

	/**   
	* @Title: getAll
	* @author yyyml
	* @date 2016-7-18 上午9:39:02  
	* @Description: 获取所有的用户 
	* @return void     
	*/
	public List<User> getAll();

	/**   
	* @Title: save
	* @author yyyml
	* @date 2016-7-27 下午4:34:40  
	* @Description: 新增用户 
	* @param @param user      
	* @return void     
	*/
	public void save(User user);

	/**   
	* @Title: delete
	* @author yyyml
	* @date 2016-7-27 下午4:34:53  
	* @Description: 删除用户 ，物理删除
	* @param @param id      
	* @return void     
	*/
	public void delete(Integer id);

	/**   
	* @Title: deleteByLogic
	* @author yyyml
	* @date 2016-7-27 下午4:35:13  
	* @Description: 删除用户，逻辑删除
	* @param @param id      
	* @return void     
	*/
	public void deleteByLogic(Integer id);

	/**   
	* @Title: update
	* @author yyyml
	* @date 2016-7-27 下午4:35:31  
	* @Description: 更新用户信息 
	* @param @param u      
	* @return void     
	*/
	public void update(User u);

	public void saveRelativity(Userrole userrole);

	public User selectUserRole(Integer id);

}
