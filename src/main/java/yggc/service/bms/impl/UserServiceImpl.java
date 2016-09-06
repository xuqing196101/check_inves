package yggc.service.bms.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.UserMapper;
import yggc.model.bms.User;
import yggc.model.bms.Userrole;
import yggc.service.bms.UserServiceI;
import yggc.util.Encrypt;

/**
* <p>Title:UserServiceImpl </p>
* <p>Description: 用户服务接口实现类</p>
* @author Ye MaoLin
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
		String psw=Encrypt.md5AndSha(user.getLoginName()+user.getPassword());
		user.setPassword(psw);
		List<User> ulist=userMapper.loginQuery(user);
		if(ulist.size()>0){
			User u=ulist.get(0);
			return u;
		}else{
			return null;
		}
	}

	@Override
	public void delete(String id) {
		userMapper.delete(id);
	}

	@Override
	public void deleteByLogic(String id) {
		User u=userMapper.queryById(id);
		u.setIsDeleted(1);
		userMapper.updateBySelective(u);
	}

	@Override
	public void update(User u) {
		userMapper.updateBySelective(u);
	}

	@Override
	public void saveRelativity(Userrole userrole) {
		userMapper.saveRelativity(userrole);
	}

	@Override
	public List<User> selectUserRole(User user) {
		return userMapper.selectUserRole(user);
	}

}

