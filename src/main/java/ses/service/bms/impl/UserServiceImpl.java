package ses.service.bms.impl;


import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.service.bms.UserServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;


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
	
	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	@Override
	public void save(User user, User currUser) {
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
		userMapper.insertSelective(user);
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
		User user = users.get(0);
		return user;
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
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
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
	
}

