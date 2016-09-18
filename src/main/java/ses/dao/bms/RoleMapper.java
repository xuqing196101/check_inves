package ses.dao.bms;

import java.util.List;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.Userrole;


public interface RoleMapper {
    
	int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> queryByList(Role role);

	void deleteRoelUser(Userrole userrole);

	/**
	 * Description: 查询角色信息（包含关联信息）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @return List<Role>
	 * @exception IOException
	 */
	List<Role> selectRole(Role r);

	List<Role> selectRolePreMenu(Role r);

	void saveRelativity(RolePreMenu rolePreMenu);

	void deleteRoelMenu(RolePreMenu rm);
}