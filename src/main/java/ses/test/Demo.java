package ses.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ses.dao.bms.UserMapper;
import ses.model.bms.User;
import ses.model.bms.UserTask;
import ses.service.bms.UserServiceI;
import ses.service.bms.UserTaksService;


public class Demo extends BaseTest {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private UserTaksService userTaksService;
	@Test
	public void test1(){

//		User user = userMapper.queryById("154");
//		System.out.println(user.getLoginName()+"-----");
	}
	@Test
	public void test2(){
		 UserTask u=new UserTask();
		 u.setId("1");
		 Date date = new Date();
		 System.out.println(date);
		 u.setCreatedAt(date);
		 
		 userTaksService.add(u);
	}
	@Test
	public void testDate() throws ParseException{
//		  String s = "Sun Sep 02 2012 08:30:00 GMT+08:00";
		  String str="Sat Sep 10 2016 06:00:00 GMT+0800";
		  String[] strs= str.split("\\+");
		 String ss= strs[0]+"+08:00";
//	        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
//	        Date d = sf.parse(s);
		System.out.println(ss);
	}
}
