package ses.service.bms;

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
}
