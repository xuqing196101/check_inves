package yggc.service.bms;

import java.util.List;

import yggc.model.bms.PreMenu;
import yggc.model.bms.Role;
import yggc.model.bms.RolePreMenu;
import yggc.model.bms.Userrole;

public interface RoleServiceI {

	List<Role> getAll(Role r);
	
	void save(Role r);
	
	void update(Role r);
	
	Role get(String id);
	
	void delete(String id);

	void deleteRoelUser(Userrole userrole);

	List<Role> selectRoleUser(Role r);

	List<Role> getRoleMenus(Role r);

	void saveRolePreMenu(RolePreMenu rolePreMenu);

	void deleteRoelMenu(RolePreMenu rm);
}
