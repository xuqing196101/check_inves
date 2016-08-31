package yggc.service.bms;

import java.util.List;

import yggc.model.bms.Role;
import yggc.model.bms.Userrole;

public interface RoleServiceI {

	List<Role> getAll(Role r);
	
	void save(Role r);
	
	void update(Role r);
	
	Role get(Integer id);
	
	void delete(Integer id);

	void deleteRoelUser(Userrole userrole);

	List<Role> selectRoleUser(Role r);
}
