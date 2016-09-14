package ses.service.bms.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.UserMapper;
import ses.model.bms.User;
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
	
	@Override
	public void save(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public void deleteByLogic(String id) {
		List<User> list=userMapper.selectByPrimaryKey(id);
		if(list != null && list.size()>0){
			User u = list.get(0);
			u.setIsDeleted(1);
			userMapper.updateByPrimaryKey(u);
		}else{
			
		}
	}

	@Override
	public void update(User u) {
		userMapper.updateByPrimaryKey(u);
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

}

