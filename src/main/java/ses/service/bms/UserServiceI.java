package ses.service.bms;

import java.io.IOException;
import java.util.HashMap;
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
    List<User> queryParkManagers(HashMap<String,Object> map);

    /**
     *〈简述〉重置密码
     *〈详细描述〉
     * @author Ye MaoLin
     * @param u
     */
    void resetPwd(User u);

    /**
     *〈简述〉批量插入用户-权限
     *〈详细描述〉
     * @author Ye MaoLin
     * @param userPreMenus
     */
    void saveUserMenuBatch(List<UserPreMenu> userPreMenus);

    /**
     *〈简述〉批量删除用户-权限
     *〈详细描述〉
     * @author Ye MaoLin
     * @param ups
     */
    void deleteUserMenuBatch(List<UserPreMenu> ups);
    
    /**
     * 
     *〈简述〉 根据类型Id查询用户
     *〈详细描述〉
     * @author myc
     * @param typeId
     * @return
     */
	User findByTypeId(String id);
	
	/**
	 *〈简述〉用户列表方法
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param user
	 * @return
	 */
	List<User> findUserRole(User user ,int page);
	
	/**
   *〈简述〉根据角色编码查询用户
   *〈详细描述〉
   * @author Ye Maolin
   * @param map 查询条件参数
   * @return List<User>
   */
  List<User> findByRole(HashMap<String, Object> map);
  
  /**
   * 
   *〈简述〉根据组织机构Id查询用户数量
   *〈详细描述〉
   * @author myc
   * @param orgId  组织机构Id
   * @return 存在用户
   */
  Long getUserCountByOrgId(String orgId);
  
  /**
   * 
   * @Title: listWithoutSupplier
   * @author Liyi 
   * @date 2016-12-26 下午2:25:49  
   * @Description:查询所有非供应商用户
   * @param:     
   * @return:
   */
  List<User> listWithoutSupplier(int pageNum);
  
  /**
   * 
   *〈简述〉保存用户
   *〈详细描述〉
   * @author myc
   * @param user 
   */
  void saveUser(User user);

  /**
   *〈简述〉校验身份证号重复
   *〈详细描述〉
   * @author Ye MaoLin
   * @param idNumber
   */
  Boolean ajaxIdNumber(String idNumber, String id);

  /**
   *〈简述〉校验手机号重复
   *〈详细描述〉
   * @author Ye MaoLin
   * @param mobile
   */
  Boolean ajaxMoblie(String mobile, String id);

  /**
   *〈简述〉确认旧密码
   *〈详细描述〉
   * @author Ye MaoLin
   * @param mobile
   */
  Boolean ajaxOldPassword(User u);

  /**
   *〈简述〉判断是地方用户还是军队用户
   *〈详细描述〉
   * @author baixudong
   * @param mobile
   */
  List<User> selectByArmyLocal(String userId);

  /**
   *〈简述〉根据角色查询供应商/专家/代理供应商用户
   *〈详细描述〉
   * @author Ye MaoLin
   * @param user
   * @param i
   * @return
   */
  List<User> findUserRoleOther(User user, int i);
  /**
   * 根据机构表的id集合 获取用户的id集合
   * @author YangHongLiang
   * @param OrgID
   * @return
   */
  List<String> getUserId(List<String> OrgID,String typeName);
  
  
  /**
	 * @Title: selectByTypeId
	 * @Description根据类型id查
	 * @param @param typeId
	 * @param @return      
	 * @return List<User>
	 */
	List<User> selectByTypeId(String typeId);
}
