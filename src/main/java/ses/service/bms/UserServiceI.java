package ses.service.bms;

import java.util.List;

import ses.model.bms.User;
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
	 * Description: 查询列表
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
	void save(User user);

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

	List<User> find(User user);

}
