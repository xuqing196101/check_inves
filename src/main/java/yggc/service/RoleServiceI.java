package yggc.service;

import java.util.List;

import yggc.model.Role;

public interface RoleServiceI {

	List<Role> getAll(Role r);
	
	void save(Role r);
	
	void update(Role r);
	
	Role get(Integer id);
	
	void delete(Integer id);
}
