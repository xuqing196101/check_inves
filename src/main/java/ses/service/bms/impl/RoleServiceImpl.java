package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.RoleMapper;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.Userrole;
import ses.service.bms.RoleServiceI;

/**
 * Description: 角色业务实现类
 * 
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> find(Role r) {
		return roleMapper.find(r);
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
	public Role get(String id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void delete(String id) {
		roleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteRoelUser(Userrole userrole) {
		roleMapper.deleteRoelUser(userrole);
	}

	@Override
	public List<Role> selectRole(Role r, Integer pageNum) {
		//PropertiesUtil config = new PropertiesUtil("config.properties");
		//PageHelper.startPage(pageNum,10);
		return roleMapper.selectRole(r);
	}

	@Override
	public void saveRolePreMenu(RolePreMenu rolePreMenu) {
		roleMapper.saveRelativity(rolePreMenu);
	}

	@Override
	public void deleteRoelMenu(RolePreMenu rm) {
		roleMapper.deleteRoelMenu(rm);
	}

}
