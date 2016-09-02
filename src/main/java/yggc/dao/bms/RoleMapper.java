package yggc.dao.bms;

import java.util.List;

import yggc.model.bms.PreMenu;
import yggc.model.bms.Role;
import yggc.model.bms.RolePreMenu;
import yggc.model.bms.Userrole;

public interface RoleMapper {
    
	int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> queryByList(Role role);

	void deleteRoelUser(Userrole userrole);

	List<Role> selectRoleUser(Role r);

	List<Role> selectRolePreMenu(Role r);

	void saveRelativity(RolePreMenu rolePreMenu);

	void deleteRoelMenu(RolePreMenu rm);
}