package yggc.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yggc.dao.bms.UserMapper;
import yggc.model.bms.User;
import yggc.service.bms.UserServiceI;

public class Demo extends BaseTest {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserServiceI userServiceI;
	@Test
	public void test1(){

		User user = userMapper.queryById(154);
		System.out.println(user.getLoginName()+"-----");
	}
	@Test
	public void test2(){
		User user =new User ();
		user.setId(154);
		User user2 = userServiceI.getUserById(user);
		
		System.out.println(user2.getLoginName()+"*****************************");
	}
}
