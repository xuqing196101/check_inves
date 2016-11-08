package ses.service.bms;

import java.util.List;

import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;

/**
 * Description: 用户业务接口
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public interface UserServiceI {

	/**
	 * Description: 查询列表，分页
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @param pageNum
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> selectUser(User user, Integer pageNum);

	/**
	 * Description: 保存用户
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	void save(User user, User currUser);

	/**
	 * Description: 保存用户-角色关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param userrole
	 * @exception IOException
	 */
	void saveRelativity(Userrole userrole);

	/**
	 * Description: 修改用户信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param u
	 * @exception IOException
	 */
	void update(User u);

	/**
	 * Description: 逻辑删除用户
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param str
	 * @exception IOException
	 */
	void deleteByLogic(String id);

	/**
	 * Description: 查询用户（含关联数据）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param user
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> find(User user);
	
	/**
	 * Description: 根据查询用户
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @return User
	 * @exception IOException
	 */
	User getUserById(String id);

	/**
	 * Description: 根据用户名查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param loginName
	 * @return
	 * @exception IOException
	 */
	List<User> findByLoginName(String loginName);

	/**
	 * Description: 保存用户与角色多对应权限的关联id
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userPreMenu
	 * @exception IOException
	 */
	void saveUserMenu(UserPreMenu userPreMenu);

	/**
	 * Description: 删除用户-权限菜单的关联关系
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userPreMenu
	 * @exception IOException
	 */
	void deleteUserMenu(UserPreMenu userPreMenu);
	
	/**
	 * Description: 查询列表不分页
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-12
	 * @param user
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> queryByList(User user);

	/**
	 * Description: 列表分页
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param object
	 * @param i
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> list(User user, int i);

	/**
	 * Description: 根据用户名、密码查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-12
	 * @param user
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> queryByLogin(User user);
	/**
     * 
    * @Title: queryParkManagers
    * @author Peng Zhongjun
    * @date 2016-11-8 上午10:48:21  
    * @Description: 查询拥有版主权限的用户 
    * @param @return      
    * @return List<User>
     */
    List<User> queryParkManagers();
}
