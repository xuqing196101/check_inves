package ses.service.bms;

import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.util.Ztree;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * Description: 保存用户 不需要传id
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	String save(User user, User currUser);

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
   *〈简述〉校验身份证号重复(仅校验后台用户)
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
	 /**
		 * @Title: selectByTypeId
		 * @Description根据类型id查
		 * @param @param typeId
		 * @param @return      
		 * @return List<String>
		 */
	List<String> findListByTypeId(String typeId);
	
	/**
	 * @Title: updateByTypeId
	 * @date 2017-5-8 下午6:10:28  
	 * @Description:假删除用户
	 * @param @param typeId      
	 * @return void
	 */
	void updateByTypeId (String typeId);
	/**
	 * 
	 * Description:查询 是否 是2017-06-05 之前修改的用户
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-5
	 * @param date
	 * @param loginName
	 * @return
	 */
	Integer isUpdateUser(String date,String loginName);
	
	/**
	 * 
	 * Description: 验证用户名唯一
	 * 
	 * @author zhang shubin
	 * @data 2017年6月27日
	 * @param 
	 * @return
	 */
	boolean yzLoginName(Map<String, Object> map);

  /**
   *〈简述〉校验用户军官证号唯一，仅校验后台用户
   *〈详细描述〉
   * @author Ye MaoLin
   * @param officerCertNo
   * @param id
   * @return
   */
  Boolean ajaxOfficerCertNo(String officerCertNo, String id);

  /**
   *〈简述〉获取机构树
   *〈详细描述〉
   * @author Ye MaoLin
   * @param orgType 
   * @param typeNameId 
   * @param user 
   * @return
   */
  List<Ztree> getOrgTree(User user, String typeNameId, String orgType);

  /**
   *〈简述〉查询后台用户列表(不包括专家和供应商)
   *〈详细描述〉
   * @author Ye MaoLin
   * @param user
   * @param i
   * @return
   */
  List<User> queryBackendUser(User user, int i);

  
  /**
   *〈简述〉查询同一机构的所有用户集合
   *〈详细描述〉
   * @param id
   * @return
   */
  List<String> findListByOrgId(String orgId);

  /**
   *〈简述〉更新用户登录密码错误失败次数
   *〈详细描述〉
   * @author Ye MaoLin
   * @param loginName
 * @return 
   */
  Integer updateUserLoginErrorNum(String loginName);

  /**
   *〈简述〉修改用户密码错误次数为0
   *〈详细描述〉
   * @author Ye MaoLin
   * @param ids
   * @param type 
   */
  boolean unlock(String ids, String type);

}
