package ses.service.bms.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.PreMenuMapper;
import ses.dao.bms.RoleMapper;
import ses.dao.bms.UserMapper;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.service.bms.RoleServiceI;
import ses.util.PropertiesUtil;

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
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory; 
	
	@Autowired
	private PreMenuMapper preMenuMapper;
	
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

	@Override
	public List<Role> list(Role role, Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return roleMapper.find(role);
	}


	@Override
	public BigDecimal checkRolesByUserId(HashMap<String,Object> map) {

		return roleMapper.checkRolesByUserId(map);
	}

    @Override
    public void deleteBatch(Role r) {
        //删除角色与用户的关联
        Userrole userrole = new Userrole();
        userrole.setRoleId(r);
        roleMapper.deleteRoelUser(userrole);
        // 删除角色与权限的关联
        RolePreMenu rm = new RolePreMenu();
        rm.setRole(r);
        roleMapper.deleteRoelMenu(rm);
        //删除用户与该角色权限的关联
        List<Role> rlist = roleMapper.selectRole(r);
        List<UserPreMenu> ups = new ArrayList<UserPreMenu>();
        if(rlist != null && rlist.size() > 0){
            //该角色所有用户
            List<User> ulist = rlist.get(0).getUsers();
            for (User user : ulist) {
                UserPreMenu userPreMenu = new UserPreMenu();
                userPreMenu.setUser(user);
                //该角色所有权限菜单
                List<PreMenu> mlist = rlist.get(0).getPreMenus();
                for (PreMenu preMenu : mlist) {
                    userPreMenu.setPreMenu(preMenu);
                    ups.add(userPreMenu);
                }
            }
        }
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 50;//每批commit的个数
            for(int index = 0; index < ups.size();index++){
                UserPreMenu userPreMenu = ups.get(index);
                batchSqlSession.getMapper(UserMapper.class).deleteUserMenu(userPreMenu);
                if(index !=0 && index%batchCount == 0){
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(batchSqlSession != null){
                batchSqlSession.close();
            }
        }
        // 删除角色
        roleMapper.deleteByPrimaryKey(r.getId());
    }

    @Override
    public void saveRoleMenu(Role role, String ids) {
        //先删除该角色下用户的用户-权限菜单关联
        List<Role> rlist = roleMapper.selectRole(role);
        List<UserPreMenu> ups = new ArrayList<UserPreMenu>();
        if(rlist != null && rlist.size() > 0){
            //该角色所有用户
            List<User> ulist = rlist.get(0).getUsers();
            for (User user : ulist) {
                //该角色所有权限菜单
                List<PreMenu> mlist = rlist.get(0).getPreMenus();
                for (PreMenu preMenu : mlist) {
                    UserPreMenu userPreMenu = new UserPreMenu();
                    userPreMenu.setUser(user);
                    userPreMenu.setPreMenu(preMenu);
                    ups.add(userPreMenu);
                }
            }
        }
        SqlSession batchSqlSession = null;
        batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int batchCount = 50;//每批commit的个数
        try{
            for(int index = 0; index < ups.size();index++){
                UserPreMenu up = ups.get(index);
                batchSqlSession.getMapper(UserMapper.class).deleteUserMenu(up);
                if(index !=0 && index%batchCount == 0){
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
            //删除该角色的角色-权限菜单关联
            RolePreMenu rm = new RolePreMenu();
            rm.setRole(role);
            roleMapper.deleteRoelMenu(rm);
            //开始保存
            if (ids != null && !"".equals(ids)) {
                String[] pIds = ids.split(",");
                List<RolePreMenu> rolePreMenus = new ArrayList<RolePreMenu>();
                List<UserPreMenu> userPreMenus = new ArrayList<UserPreMenu>();
                for (String str : pIds) {
                    PreMenu preMenu = preMenuMapper.selectByPrimaryKey(str);
                    //保存角色-权限菜单关联
                    RolePreMenu rolePreMenu = new RolePreMenu();
                    rolePreMenu.setPreMenu(preMenu);
                    rolePreMenu.setRole(role);
                    rolePreMenus.add(rolePreMenu);
                    //保存该角色下用户的用户-权限菜单关联
                    if(rlist != null && rlist.size() > 0){
                        List<User> ulist = rlist.get(0).getUsers();
                        for (User user : ulist) {
                            UserPreMenu userPreMenu = new UserPreMenu();
                            userPreMenu.setPreMenu(preMenu);
                            userPreMenu.setUser(user);
                            userPreMenus.add(userPreMenu);
                        }
                    }
                }
                for(int index = 0; index < rolePreMenus.size();index++){
                    RolePreMenu rolePreMenu = rolePreMenus.get(index);
                    batchSqlSession.getMapper(RoleMapper.class).saveRelativity(rolePreMenu);
                    if(index !=0 && index%batchCount == 0){
                        batchSqlSession.commit();
                    }
                }
                for(int index = 0; index < userPreMenus.size();index++){
                    UserPreMenu userPreMenu = userPreMenus.get(index);
                    batchSqlSession.getMapper(UserMapper.class).saveUserMenu(userPreMenu);
                    if(index !=0 && index%batchCount == 0){
                        batchSqlSession.commit();
                    }
                }
            }
            batchSqlSession.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(batchSqlSession != null){
                batchSqlSession.close();
            }
        }
    }

    @Override
    public List<Role> selectByUserId(String userId) {
        return roleMapper.selectByUserId(userId);
    }

    @Override
    public List<Role> selectByUserIdCode(HashMap<String, Object> map) {
      return roleMapper.selectByUserIdCode(map);
    }

}
