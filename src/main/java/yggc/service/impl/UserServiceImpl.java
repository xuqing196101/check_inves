package yggc.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.UserMapper;
import yggc.model.User;
import yggc.service.UserServiceI;

/**
* <p>Title:UserServiceImpl </p>
* <p>Description: 用户服务接口实现类</p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-27下午4:34:05
*/
@Service("userService")
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User getUserById(User user) {
		return userMapper.queryById(user.getId());
	}

	@Override
	public void save(User user) {
		userMapper.insertSelective(user);
	}

	@Override
	public List<User> getAll() {
		List<User> list=userMapper.queryByList(null);
		Integer count = userMapper.queryByCount();
		return list;
	}

	@Override
	public User getUserByLogin(User user) {
		User u=userMapper.queryByList(user).get(0);
		return u;
	}

	@Override
	public void delete(Integer id) {
		userMapper.delete(id);
	}

	@Override
	public void deleteByLogic(Integer id) {
		User u=userMapper.queryById(id);
		u.setIsDeleted(1);
		userMapper.updateBySelective(u);
	}

	@Override
	public void update(User u) {
		userMapper.updateBySelective(u);
	}

}

