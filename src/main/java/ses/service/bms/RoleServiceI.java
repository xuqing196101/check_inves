package ses.service.bms;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.Userrole;

public interface RoleServiceI {

	/**
	 * Description: 根据条件查询（不带关联数据）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @return List<Role>
	 * @exception IOException
	 */
	List<Role> find(Role r);

	/**
	 * Description: 保存角色
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @exception IOException
	 */
	void save(Role r);

	/**
	 * Description: 更新角色信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @exception IOException
	 */
	void update(Role r);

	/**
	 * Description: 根据id获取角色信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @return Role
	 * @exception IOException
	 */
	Role get(String id);

	/**
	 * Description: 删除角色，物理删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param id
	 * @exception IOException
	 */
	void delete(String id);

	/**
	 * Description: 删除角色-用户关联关系
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param userrole
	 * @exception IOException
	 */
	void deleteRoelUser(Userrole userrole);

	/**
	 * Description: 查询角色（包含关联数据）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @return List<Role>
	 * @exception IOException
	 */
	List<Role> selectRole(Role r, Integer pageNum);

	/**
	 * Description: 保存角色-菜单关联关系
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param rolePreMenu
	 * @exception IOException
	 */
	void saveRolePreMenu(RolePreMenu rolePreMenu);

	/**
	 * Description: 删除角色-菜单关联关系
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param rm
	 * @exception IOException
	 */
	void deleteRoelMenu(RolePreMenu rm);

	/**
	 * Description: 分页查询
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param object
	 * @param i
	 * @return
	 * @exception IOException
	 */
	List<Role> list(Role role, Integer pageNum);
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
     *〈简述〉删除角色
     *〈详细描述〉
     * @author Ye MaoLin
     * @param r
     */
    void deleteBatch(Role r);

    /**
     *〈简述〉保存角色勾选权限
     *〈详细描述〉
     * @author Ye MaoLin
     * @param role
     * @param ids
     */
    void saveRoleMenu(Role role, String ids);
    
    /**
     *〈简述〉查询用户角色
     *〈详细描述〉
     * @author Ye MaoLin
     * @param userId 用户id
     * @return 角色列表
     */
    List<Role> selectByUserId(String userId);
    
    /**
     *〈简述〉根据用户id和角色code查询用户角色
     *〈详细描述〉
     * @author Ye MaoLin
     * @param map 条件参数
     * @return 角色列表
     */
    List<Role> selectByUserIdCode(HashMap<String,Object> map);

    /**
     *〈简述〉更新角色序号
     *〈详细描述〉
     * @author Ye MaoLin
     * @param type 序号更新类型
     * @param position 新序号
     * @param oldPosition 修改之前序号
     */
    void updatePosition(Integer position, Integer oldPosition, Integer type);
    /***
     * 根据 权限name 查询 相关的用户id
     * @param roleId  权限id
     * @return
     */
    List<String> findByRoleName(String roleName);
    
}
