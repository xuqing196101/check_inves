package ses.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ses.dao.bms.UserMapper;
import ses.model.bms.User;
import ses.service.bms.UserServiceI;


public class Demo extends BaseTest {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserServiceI userServiceI;
	@Test
	public void test1(){

		User user = userMapper.queryById("154");
		System.out.println(user.getLoginName()+"-----");
	}
	@Test
	public void test2(){
		User user =new User ();
		user.setId("154");
		User user2 = userServiceI.getUserById(user);
		
		System.out.println(user2.getLoginName()+"*****************************");
	}
}
