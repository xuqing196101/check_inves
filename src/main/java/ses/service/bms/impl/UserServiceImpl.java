package ses.service.bms.impl;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.service.bms.UserDataRuleService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import common.constant.StaticVariables;


/**
 * Description: 用户业务接口实现类
 *
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
@Service("userService")
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory; 
	
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	@Autowired
	private UserDataRuleService userDataRuleService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Override
	public String save(User user, User currUser) {
		user.setIsDeleted(0);
		user.setCreatedAt(new Date());
		if (currUser != null) {
			user.setUser(currUser);
		} else {

		}
		//生成15位随机码
		String randomCode = generateString(15);
		
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(user.getPassword(), randomCode);
		user.setPassword(pwd);
		user.setRandomCode(randomCode);
		String ipAddressType = PropUtil.getProperty("ipAddressType"); 
		if ("0".equals(ipAddressType)) {
		    //内网用户
        user.setNetType(0);
    }
    if ("1".equals(ipAddressType)) {
        //外网用户
        user.setNetType(1);
    }
    //根据业务不同 判断 id是否为空 分别调用不同的方法
      if(StringUtils.isBlank(user.getId())){
    	  //id 为空时调用
    	  userMapper.insertSelective(user);
      }else{
    	  //id 不为空时调用
    	  userMapper.saveUser(user);
      }
		return user.getId();
	}
	
	/**
	 * 
	 * @see ses.service.bms.UserServiceI#saveUser(ses.model.bms.User)
	 */
	@Override
    public void saveUser(User user) {
	    userMapper.saveUser(user);
    }

    @Override
	public void deleteByLogic(String id) {
		List<User> list=userMapper.selectByPrimaryKey(id);
		if(list != null && list.size()>0){
			User u = list.get(0);
			u.setIsDeleted(1);
			userMapper.updateByPrimaryKeySelective(u);
		}else{
			
		}
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKeySelective(u);
	}

	@Override
	public void saveRelativity(Userrole userrole) {
		userMapper.saveRelativity(userrole);
	}

	@Override
	public List<User> selectUser(User user, Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return userMapper.selectUser(user);
	}

	@Override
	public List<User> find(User user) {
		List<User> users = userMapper.selectUser(user);
		return users;
	}
	@Override
	public User getUserById(String id) {
		List<User> users = userMapper.selectByPrimaryKey(id);
		if(users != null && users.size() > 0){
		    User user = users.get(0);
		    return user;
		}
		return null;
	}

	@Override
	public List<User> findByLoginName(String loginName) {
		List<User> users = userMapper.queryByLoginName(loginName);
		return users;
	} 
    
	/**
     * Description: 返回一个定长的随机字符串(只包含大小写字母、数字)
     * 
     * @author Ye MaoLin
     * @version 2016-9-14
     * @param length
     * @return String
     * @exception IOException
     */
    public String generateString(int length) {  
        StringBuffer sb = new StringBuffer();  
        Random random = new Random();  
        for (int i = 0; i < length; i++) {  
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
        }  
        return sb.toString();  
    }

	@Override
	public void saveUserMenu(UserPreMenu userPreMenu) {
		userMapper.saveUserMenu(userPreMenu);
	}

	@Override
	public void deleteUserMenu(UserPreMenu userPreMenu) {
		userMapper.deleteUserMenu(userPreMenu);
	}

	@Override
	public List<User> list(User user, int pageNum) {
	    PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
		List<User> users = userMapper.queryByList(user);
		return users;
	}

	public List<User> queryByList(User user) {
		return userMapper.queryByList(user);
	}

	@Override
	public List<User> queryByLogin(User user) {
		return userMapper.queryByLogin(user);
	}


	@Override
	public List<User> queryParkManagers(HashMap<String,Object> map) {
		return userMapper.queryParkManagers(map);
	}

    @Override
    public void resetPwd(User u) {
        List<User> users = userMapper.selectByPrimaryKey(u.getId());
        User user = users.get(0);
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
        // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
        md5.setEncodeHashAsBase64(false);     
        String pwd = md5.encodePassword(u.getPassword(), user.getRandomCode());
        user.setPassword(pwd);
        user.setUpdatedAt(new Date());
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void saveUserMenuBatch(List<UserPreMenu> userPreMenus) {
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 50;//每批commit的个数
            for(int index = 0; index < userPreMenus.size();index++){
                UserPreMenu userPreMenu = userPreMenus.get(index);
                batchSqlSession.getMapper(UserMapper.class).saveUserMenu(userPreMenu);
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
    }

    @Override
    public void deleteUserMenuBatch(List<UserPreMenu> ups) {
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
    }

	@Override
	public User findByTypeId(String id) {
		
		return userMapper.findUserByTypeId(id);
	}
    
	@Override
    public List<User> findUserRole(User user, int pageNum) {
        PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
        List<User> users = userMapper.findUserRole(user);
         if (users !=null && users.size()>0) {
			for (User pro : users) {
				if("4".equals(pro.getTypeName())||"5".equals(pro.getTypeName())){
					//id集合
					List<String> orgid= userDataRuleService.getOrgID(pro.getId());
					pro.setOrgId(StringUtils.join(orgid,","));
					List<String> orgName= orgnizationServiceI.findByUserid(pro.getId());
					pro.setOrgName(StringUtils.join(orgName,","));
				}
			}
		}
        return users;
    }

  @Override
  public List<User> findByRole(HashMap<String, Object> map) {
    return userMapper.findByRole(map);
  }
  
    /**
     * 
     * @see ses.service.bms.UserServiceI#getUserCountByOrgId(java.lang.String)
     */
    @Override
    public Long getUserCountByOrgId(String orgId) {
        
      return userMapper.getUserCountByOrgId(orgId);
    }


	@Override
	public List<User> listWithoutSupplier(int pageNum) {
	    PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
		List<User> users = userMapper.listWithoutSupplier();
		return users;
	}

  @Override
  public Boolean ajaxIdNumber(String idNumber, String id) {
    User user = new User();
    user.setId(id);
    user.setIdNumber(idNumber);
    List<User> users = userMapper.ajaxIdNumber(user);
    if (users != null && users.size() > 0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public Boolean ajaxMoblie(String mobile, String id) {
    User user = new User();
    user.setId(id);
    user.setMobile(mobile);
    List<User> users = userMapper.ajaxMoblie(user);
    if (users != null && users.size() > 0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public Boolean ajaxOldPassword(User u) {
      List<User> users = userMapper.selectByPrimaryKey(u.getId());
      if (users != null && users.size() > 0) {
          User user = users.get(0);
          Md5PasswordEncoder md5 = new Md5PasswordEncoder();     
          // false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true  表示：生成24位的Base64版     
          md5.setEncodeHashAsBase64(false);     
          String pwd = md5.encodePassword(u.getPassword(), user.getRandomCode());
          if (user.getPassword().equals(pwd)) {
              return true;
          } else {
              return false;
          }
      }
      return false;
  }

	@Override
	public List<User> selectByArmyLocal(String userId) {
		return userMapper.selectByArmyLocal(userId);
	}

  @Override
  public List<User> findUserRoleOther(User user, int pageNum) {
    PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
    List<User> users = userMapper.findUserRoleOther(user);
    return users;
  }
/**
 * 实现根据机构表的id集合 获取用户的id集合
 */
@Override
public List<String> getUserId(List<String> OrgID,String typeName) {
	// TODO Auto-generated method stub
	//判断 该用户是否是其他部门
	if(typeName.equals("3")){
		String qt=DictionaryDataUtil.getId(StaticVariables.ORG_TYPE_OT);
		if(StringUtils.isNotBlank(qt)){
			OrgID.add(qt);
		}
	}
	return userMapper.getUserId(OrgID);
 }

/**
 * @Title: selectByTypeId
 * @Description根据类型id查
 * @param @param typeId
 * @param @return      
 * @return List<User>
 */

@Override
public List<User> selectByTypeId(String typeId) {
	return userMapper.selectByTypeId(typeId);
}
}

