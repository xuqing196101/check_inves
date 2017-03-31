package ses.dao.bms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;

/**
 * Description: 用户表持久化操作
 * 
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
public interface UserMapper {

	/**
	 * Description: 根据id删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param id
	 * @exception IOException
	 */
	void deleteByPrimaryKey(String id);

	/**
	 * Description: 插入数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	void insert(User user);

	/**
	 * Description: 插入不为空数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	void insertSelective(User user);

	/**
	 * Description: 保存用户和角色之间的关系信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param userrole
	 * @exception IOException
	 */
	void saveRelativity(Userrole userrole);
	
	/**
	 * Description: 更新不为空数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	void updateByPrimaryKeySelective(User user);

	/**
	 * Description: 更新数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @exception IOException
	 */
	void updateByPrimaryKey(User user);

	/**
	 * Description: 根据id查询(不带关联集合)
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param id
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> selectByPrimaryKey(String id);
	
	/**
	 * Description: 根据不为空的条件查询用户信息，不带关联集合
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param user
	 * @return List<User>
	 * @exception IOException
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
	 * Description: 根据不为空的条件查询用户信息（包含角色信息）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param u
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> selectUser(User u);

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
	 * Description: 根据用户名查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-12
	 * @param loginName
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> queryByLoginName(String loginName);
	
	/**
	 * Description: 用户名，密码登录查询
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
     * 
     *〈简述〉 根据typeId查询用户
     *〈详细描述〉
     * @author myc
     * @param typeId
     * @return
     */
    User findUserByTypeId(@Param("typeId")String typeId);
    
    /**
     *〈简述〉用户列表
     *〈详细描述〉
     * @author Song Biaowei
     * @param user 用户实体
     * @return List<User>
     */
    List<User> findUserRole(User user);
    
    /**
     *〈简述〉根据角色编码查询用户
     *〈详细描述〉
     * @author Song Biaowei
     * @param map 查询条件参数
     * @return List<User>
     */
    List<User> findByRole(HashMap<String, Object> map);
    
    /**
     * 
     *〈简述〉根据组织机构Id查询用户数量
     *〈详细描述〉
     * @author myc
     * @param orgId 组织机构ID
     * @return 如果存在返回大于0的数
     */
    Long getUserCountByOrgId(@Param("orgId")String orgId);
	
    /**
     * 
     * @Title: listWithoutSupplier
     * @author Liyi 
     * @date 2016-12-26 下午2:24:48  
     * @Description: 查询所有非供应商用户
     * @param:     
     * @return:
     */
    List<User> listWithoutSupplier();
    
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
    List<User> ajaxIdNumber(User user);

    /**
     *〈简述〉校验手机号重复
     *〈详细描述〉
     * @author Ye MaoLin
     * @param mobile
     */
    List<User> ajaxMoblie(User user);
    
	/**
	 * Description: 根据id查询(不带关联集合)
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param id
	 * @return List<User>
	 * @exception IOException
	 */
	List<User> selectByArmyLocal(String id);

  /**
   *〈简述〉根据角色查询供应商/专家/代理供应商用户
   *〈详细描述〉
   * @author Ye MaoLin
   * @param user
   * @return
   */
  List<User> findUserRoleOther(User user);
  
  
  /**
   * 
  * @Title: queryByUserId
  * @Description: 根据userID查询对应关联表
  * author: Li Xiaoxiao 
  * @param @param sserId
  * @param @return     
  * @return List<Userrole>     
  * @throws
   */
  List<Userrole> queryByUserId(@Param("userId")String userId,@Param("roleId")String roleId);
  
  /**
   * 
  * @Title: queryById
  * @Description: 根据ID查询是否存在
  * author: Li Xiaoxiao 
  * @param @param id
  * @param @return     
  * @return User     
  * @throws
   */
  User queryById(@Param("id")String id);
  
}