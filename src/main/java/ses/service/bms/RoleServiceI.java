package ses.service.bms;

import java.util.List;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.Userrole;


public interface RoleServiceI {

	List<Role> getAll(Role r);
	
	void save(Role r);
	
	void update(Role r);
	
	Role get(String id);
	
	void delete(String id);

	void deleteRoelUser(Userrole userrole);

	List<Role> selectRole(Role r);

	List<Role> getRoleMenus(Role r);

	void saveRolePreMenu(RolePreMenu rolePreMenu);

	void deleteRoelMenu(RolePreMenu rm);
}
