package ses.dao.bms;

import java.util.List;

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

}