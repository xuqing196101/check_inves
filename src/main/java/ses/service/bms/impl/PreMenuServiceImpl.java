package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import common.constant.StaticVariables;

import ses.controller.sys.bms.UserManageController;
import ses.dao.bms.PreMenuMapper;
import ses.dao.bms.RoleMapper;
import ses.dao.bms.UserMapper;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.service.bms.PreMenuServiceI;
import ses.util.PropUtil;

/**
 * Description: 权限菜单业务实现类
 * 
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
@Service("premenuService")
public class PreMenuServiceImpl implements PreMenuServiceI {

	@Autowired
	private PreMenuMapper preMenuMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserMapper userMapper;
	
	/** 导航权限编码初始值  */
	private final static String NAV_CODE = "1001";
	/** 折叠导航权限编码初始值  */
	private final static String ACC_CODE = "1001001";
	/** 菜单权限编码初始值  */
	private final static String MENU_CODE = "1001001001";
	/** 按钮权限编码初始值  */
	private final static String BUTTON_CODE = "10010010010031";
	
	/** 跟节点初始值  */
    private final static String ROOT_CODE = "1";
    /** 增长步长  */
    private final static String INC_ONE = "001";
    /** 增长步长  */
    private final static String INC_THIRTY_ONE = "0031"; 
	
	/** 导航类型 */
	private final static String NAV_TYPE = "navigation";
	/** 折叠导航类型 */
	private final static String ACC_TYPE = "accordion";
	/** 菜单类型 */
	private final static String MENU_TYPE = "menu";
	/** 按钮类型 */
	private final static String BUTTON_TYPE = "button";
	

	@Override
	public List<PreMenu> find(PreMenu preMenu) {
		return preMenuMapper.find(preMenu);
	}

	@Override
	public void save(PreMenu menu) {
	    genPerimissionCode(menu);
		preMenuMapper.insertSelective(menu);
	}

	@Override
	public PreMenu get(String id) {
		return preMenuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(PreMenu menu) {
		preMenuMapper.updateByPrimaryKeySelective(menu);
	}

	@Override
	public List<String> findByRids(String[] roleIds) {
		return preMenuMapper.findByRids(roleIds);
	}

	@Override
	public List<String> findByUids(String userId) {
	  //定义用户权限id集合
	  List<String> mIds = new ArrayList<String>();
	  //获取用户角色
	  List<Role> roles = roleMapper.selectByUserId(userId);
	  if (roles != null && roles.size() > 0) {
	    String[] roleArry = new String[roles.size()];
	    for (int i = 0; i < roles.size(); i++) {
	      roleArry[i] = roles.get(i).getId();
	    }
	    //获取用户所属角色权限
	    List<String> rPreMenuIds = preMenuMapper.findByRids(roleArry);
	    mIds.addAll(rPreMenuIds);
	    List<User> users = userMapper.selectByPrimaryKey(userId);
	    if (users != null && users.size() > 0) {
	      //获取用户权限增量
	      UserPreMenu userPreMenu1 = new UserPreMenu();
	      userPreMenu1.setUser(users.get(0));
	      userPreMenu1.setKind(0);
	      List<String> upPreMenuIds = preMenuMapper.findUserPre(userPreMenu1);
	      mIds.addAll(upPreMenuIds);
	      //获取用户权限减量
	      UserPreMenu userPreMenu2 = new UserPreMenu();
	      userPreMenu2.setUser(users.get(0));
	      userPreMenu2.setKind(1);
	      List<String> downPreMenuIds = preMenuMapper.findUserPre(userPreMenu2);
	      mIds.removeAll(downPreMenuIds);
	    }
    } 
    return mIds;
		//return preMenuMapper.findByUids(userIds);
	}
	
	@Override
  public List<PreMenu> getMenu(User u) {
	  List<String> mIds = findByUids(u.getId());
	  if (mIds != null && mIds.size() > 0) {
	    List<PreMenu> menus = preMenuMapper.findByMids(mIds);
	    return menus;
    } else {
      return null;
    }
  }

	@Override
	public void delete(String id) {
		preMenuMapper.deleteByPrimaryKey(id);
	}
	
	/**
     * 
     *〈简述〉
     *  生成权限编码
     *〈详细描述〉
     * @author myc
     * @param menu {@link PreMenu}
     */
    private void genPerimissionCode(PreMenu menu){
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            if (menu != null && menu.getParentId() != null){
                if (StringUtils.isNotBlank(menu.getParentId().getId())){
                    String code = preMenuMapper.getPermisssinCode(menu.getParentId().getId());
                    if (StringUtils.isNotBlank(code)) {
                        long codeInteger = Long.parseLong(code);
                        menu.setPermissionCode(Long.toString(codeInteger + 1));
                    } else {
                        PreMenu premenu = preMenuMapper.selectByPrimaryKey(menu.getParentId().getId());
                        //默认值
                        if (premenu == null) {
                            initPerCode(menu);
                        }
                        //获取父级值
                        if (premenu != null) {
                            if (StringUtils.isNotBlank(menu.getType())) {
                                if (menu.getType().equals(NAV_TYPE) || menu.getType().equals(ACC_TYPE)
                                        || menu.getType().equals(MENU_TYPE)) {
                                    menu.setPermissionCode(premenu.getPermissionCode() + INC_ONE);
                                }
                                if (menu.getType().equals(BUTTON_TYPE)) {
                                    menu.setPermissionCode(premenu.getPermissionCode() + INC_THIRTY_ONE);
                                }
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } finally{
            lock.unlock();
        }
    }
    
    /**
     * 
     *〈简述〉 初始化默认值
     *〈详细描述〉
     * @author myc
     * @param menu {@link PreMenu}
     */
    private void initPerCode(PreMenu menu){
        if (StringUtils.isBlank(menu.getType())){
            menu.setPermissionCode(ROOT_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(NAV_TYPE)) {
            menu.setPermissionCode(NAV_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(ACC_TYPE)) {
            menu.setPermissionCode(ACC_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(MENU_TYPE)) {
            menu.setPermissionCode(MENU_CODE);
        }
        if (StringUtils.isNotBlank(menu.getType()) && menu.getType().equals(BUTTON_TYPE)) {
            menu.setPermissionCode(BUTTON_CODE);
        }
    }

    @Override
    public List<User> getUserByMid(String permenuId, Integer page) {
    	if(page == null) {
			page = StaticVariables.DEFAULT_PAGE;
		}
    	PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
    	
        /*//查询拥有该菜单权限的角色id
        List<String> roleIds = roleMapper.getByMid(permenuId);
        
        for(int i = 0; i < roleIds.size() - 1; i++) {
			for(int j = roleIds.size() - 1; j > i; j--) {
				if(roleIds.equals(roleIds)) {
					roleIds.remove(j);
				}
			}
		}
        
        //查询拥有该菜单权限的用户id（用户自定义的菜单）
        List<String> getUserIdByPermenuId = roleMapper.getByPermenuId(permenuId);

        //查询拥有该角色的用户
        List<String> userIdList = new ArrayList<String>();
        for (String roleId : roleIds) {
            User user = new User();
            user.setRoleId(rId);
            List<String> rIds = new ArrayList<String>();
            rIds.add(rId);
            user.setRoleIdList(rIds);
            users = userMapper.findUserRole(user);
        	List<String> userId = roleMapper.getByRoleId(roleId);
        	userIdList.addAll(userId);
        	
        }
       
        *//**
         * 删除去掉菜单的用户
         *//*
        Iterator<String> itr = getUserIdByPermenuId.iterator();
        while(itr.hasNext()) {
        	String id = itr.next();
        	List<String> byUserId = roleMapper.getByUserId(id);
        	List<String> byUserId1 = roleMapper.getByKind(id);
        	if(byUserId.size() > 0 && byUserId1.isEmpty()){
                itr.remove();
              }
        }
        
        Iterator<String> itr1 = userIdList.iterator();
        while(itr1.hasNext()) {
        	String userId = itr1.next();
        	List<String> byUserId = roleMapper.getByUserId(userId);
        	if(byUserId.size() > 0){
                itr1.remove();
              }
        }
        
		 *//**
		  * 查询用户信息
		  *//*
        List<User> userList = new ArrayList<User>();
        
        for(String userId: userIdList){
           List<User> findById = userMapper.findById(userId);
           userList.addAll(findById);
        }
        for(String userId : getUserIdByPermenuId){
        	List<User> findById = userMapper.findById(userId);
        	userList.addAll(findById);
        }*/
        
    	List<User> userList = userMapper.findByPermenuId(permenuId);
        return userList;
    }

}
