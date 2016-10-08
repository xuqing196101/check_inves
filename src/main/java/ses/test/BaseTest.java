package ses.test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import bss.dao.pms.CollectPlanMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class BaseTest {

	@Autowired
	private CollectPlanMapper collectPlanMapper;
	
	@Test
	public void test1() {
//	  Integer max = collectPlanMapper.getMax();
//	  if(max!=null){
//		  System.out.println("当前位置");
//	  }
		List<String> test1=new LinkedList<String>();
		List<String> list=new LinkedList<String>();
		
		
		list.add("1");
//		list.add("1");
//		list.add("2");
//		list.add("2");
//		list.add("3");
//		list.add("4");
//		list.add("5");
		System.out.println(System.currentTimeMillis()+"---------------");
		test1.add(list.get(0));
		System.out.println(System.currentTimeMillis()+"---------------");
		
//		System.out.println(System.currentTimeMillis()+"---------------");
//		test1.add(list.get(0));
//		System.out.println(System.currentTimeMillis()+"---------------");
		
		
//		Set<String> set=new HashSet<String>();
//		for(String s:list){
//			set.add(s);
//		}
//		
//		
//		for(String s:set){
//			System.out.println(s+"--");
//		}
	}
	
}