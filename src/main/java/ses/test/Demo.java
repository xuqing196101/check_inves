package ses.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.PurchaseRequired;
import ses.dao.bms.UserMapper;
import ses.model.bms.User;
import ses.model.bms.UserTask;
import ses.service.bms.UserServiceI;
import ses.service.bms.UserTaksService;


public class Demo {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private UserTaksService userTaksService;
	
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
	@Test
	public void test1(){
		List<String> test1=new LinkedList<String>();
		List<String> list=new LinkedList<String>();
		
		
		list.add("1");
//		list.add("1");
//		list.add("2");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		list.add("5");
//		System.out.println(System.currentTimeMillis()+"---------------");
//		test1.add(list.get(0));
//		System.out.println(System.currentTimeMillis()+"---------------");
		
		System.out.println(System.currentTimeMillis()+"---------------");
		test1.addAll(list);
		System.out.println(System.currentTimeMillis()+"---------------");
		
		
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
	
	@Test
	public void test3(){
//		PurchaseRequired p=new PurchaseRequired();
//		p.setPlanNo("A01");
//		List<PurchaseRequired> list = purchaseRequiredMapper.query(p);
		Calendar c=Calendar.getInstance();
		int i = c.get(Calendar.YEAR);
		int dayOfYear = Calendar.YEAR;
		System.out.println(dayOfYear+"---"+i);
//		List<PurchaseRequired> queryByNo = purchaseRequiredMapper.queryByNo("A01");
	}
	@Test
	public void test4(){
		
		List<String> list1 = new ArrayList();
		List<String> list2 = new ArrayList();
		List<String> list3 = new ArrayList();
		list1.add("aaa");
		list2.add("bbb");
		list3.addAll(list1);
		list3.addAll(list2);
		System.out.println(list3.toString());
	}
	@Test
	public void test5(){
		short i = 0;
		for(int a = 0;a<10;a++){
			
			System.out.println(i);
			i++;
		}
	}
}
