package yggc.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.RoleMapper;
import yggc.model.Role;

@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> getAll(Role r) {
		return roleMapper.queryByList(r);
	}

	@Override
	public void save(Role r) {
		roleMapper.insertSelective(r);
	}

	@Override
	public void update(Role r) {
		roleMapper.updateByPrimaryKeySelective(r);
	}

	@Override
	public Role get(Integer id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}

}

