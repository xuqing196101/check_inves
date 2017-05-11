package ses.dao.bms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.Userrole;


public interface RoleMapper {
    
	/**
     * Description: 根据id查询
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param id
     * @return Role
     * @exception IOException
     */
    Role selectByPrimaryKey(String id);
	
	/**
	 * Description: 根据id删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @return int
	 * @exception IOException
	 */
	int deleteByPrimaryKey(String id);

    /**
     * Description: 插入数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return int
     * @exception IOException
     */
    int insert(Role record);

    /**
     * Description: 插入不为空数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return
     * @exception IOException
     */
    int insertSelective(Role record);

    /**
     * Description: 更新不为空数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return
     * @exception IOException
     */
    int updateByPrimaryKeySelective(Role record);

    /**
     * Description: 更新数据
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param record
     * @return
     * @exception IOException
     */
    int updateByPrimaryKey(Role record);
    
    /**
     * Description: 根据不为空的条件查询列表（不带关联数据）
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param role
     * @return List<Role>
     * @exception IOException
     */
    List<Role> find(Role role);

	/**
	 * Description: 删除角色与角色内成员之间的对应关系
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userrole
	 * @exception IOException
	 */
	void deleteRoelUser(Userrole userrole);

	/**
	 * Description: 根据角色条件查询角色信息（带出关联信息）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @return List<Role>
	 * @exception IOException
	 */
	List<Role> selectRole(Role r);

	/**
	 * Description: 保存权限和角色之间的关系信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param rolePreMenu
	 * @exception IOException
	 */
	void saveRelativity(RolePreMenu rolePreMenu);

	/**
	 * Description: deleteRoelMenu
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param rm
	 * @exception IOException
	 */
	void deleteRoelMenu(RolePreMenu rm);
	/**
	 * 
	* @Title: checkRolesByUserId
	* @author Peng Zhongjun
	* @date 2016-11-8 下午3:34:58  
	* @Description: 根据userId查询角色是否为超级管理员 
	* @param @param userId
	* @param @return      
	* @return BigDecimal
	 */
	BigDecimal checkRolesByUserId(HashMap<String,Object> map);
	
	/**
	 *〈简述〉查询用户角色
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param userId 用户id
	 * @return 角色列表
	 */
	List<Role> selectByUserId(String userId);
	
	/**
   *〈简述〉根据用户id或角色code查询角色
   *〈详细描述〉
   * @author Ye MaoLin
   * @param map 参数
   * @return 角色列表
   */
	List<Role> selectByUserIdCode(HashMap<String,Object> map);

	/**
   *〈简述〉更新角色序号
   *〈详细描述〉
   * @author Ye MaoLin
   */
  List<Role> findByPosition(HashMap<String, Object> map1);

  /**
   *〈简述〉查询拥有该菜单权限的角色
   *〈详细描述〉
   * @author Ye MaoLin
   * @param id
   * @return
   */
  List<String> getByMid(String id);
  
  /**
   *〈简述〉查询拥有该菜单权限的角色
   *〈详细描述〉角色id查询用户
   * @param roleId
   * @return
   */
  List<String> getByRoleId(String roleId);
  
  /**
   *〈简述〉查询拥有该菜单权限的角色
   *〈详细描述〉菜单id查询用户 
   * @param id
   * @return
   */
  List<String> getByPermenuId(String roleId);
  
  /**
   *〈简述〉查询拥有该菜单权限的角色
   *〈详细描述〉用户id查询减去的菜单
   * @param id
   * @return
   */
  List<String> getByUserId(String userId);
  
  /**
   *〈简述〉查询拥有该菜单权限的角色
   *〈详细描述〉菜单id查询用户 
   * @param 用户id查询增加的菜单
   * @return
   */
  List<String> getByKind(String userId);
  /**
   * 根据角色名称  查询该角色下的 用户集合 包含已删除 用户
   * @param roleName
   * @return
   */
  List<String> findByRoleName(@Param("roleName")String roleName);
}