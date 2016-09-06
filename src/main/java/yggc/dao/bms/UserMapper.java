package yggc.dao.bms;

import java.util.List;

import yggc.model.bms.User;
import yggc.model.bms.Userrole;

/**
* <p>Title:UserMapper </p>
* <p>Description: 用户持久化接口</p>
* @author Ye MaoLin
* @date 2016-7-27下午4:51:54
*/
public interface UserMapper {

	/**   
	* @Title: insertSelective
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:52:29  
	* @Description: 新增用户信息
	* @param @param user
	*/
	void insertSelective(User user);

	/**   
	* @Title: queryByList
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:55:58  
	* @Description: 条件查询
	* @return List<User>     
	*/
	List<User> queryByList(User user);

	/**   
	* @Title: queryByCount
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:57:10  
	* @Description: 查询记录数
	* @return Integer     
	*/
	Integer queryByCount();

	/**   
	* @Title: delete
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:57:40  
	* @Description: 根据id删除 
	* @param @param id      
	* @return void     
	*/
	void delete(String id);

	/**   
	* @Title: queryById
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:57:51  
	* @Description: 根据id查询 
	* @param @param id
	* @return User     
	*/
	User queryById(String id);

	/**   
	* @Title: updateBySelective
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:58:26  
	* @Description:更新用户信息
	* @return void     
	*/
	void updateBySelective(User u);

	void saveRelativity(Userrole userrole);

	List<User> selectUserRole(User u);

	List<User> loginQuery(User user);

}