package yggc.dao;

import java.util.List;

import yggc.model.Role;
import yggc.model.Userrole;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> queryByList(Role role);

	void deleteRoelUser(Userrole userrole);
}