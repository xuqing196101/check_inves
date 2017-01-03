package bss.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

class Cbb{
	public Cbb(){
		System.out.println("helloA");
	}
	{
		System.out.println("i m A class");
	}
	static {
		System.out.println("static A");
	}
}
public class Aaa extends Cbb{

	public Aaa(){
		System.out.println("HelloB");
	}	
	{
		System.out.println("i m B class");
	}
	static {
		System.out.println("static B");
	}
	public static void main(String[] args) {
		//new Aaa();
		String s;
		//System.out.println(s);
		int aaa = Aaa(2);
		System.out.println(aaa);
	}
	public static int Aaa(int i){
		int result = 0;
		switch (i) {
		case 1:
			result=result+i;
		case 2:
			result=result+i*2;
		case 3:
			result=result+i*3;
		
	}
		return result;
	}
	
	
	@Test
	public void test5(){
//	     Pattern p=Pattern.compile("[\u4e00-\u9fa5]");
//			
//			 Matcher m=p.matcher(str); 
//			 boolean find = m.find();
//			if(str.contains("(")&&m.find()){
//				System.out.print("测试");
//			}
//	
//		String str="(一)";
//        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
//        Matcher m = p.matcher(str);
//        if(m.find()==true&&str.contains("(")){
//        	System.out.print("测试");
//        }
		String str="1";
//		String regex="/^-?[1-9]\\d*$/";
//		if(str.matches(regex)) {
//			System.out.print("数字类型");
//		}
//		System.out.print("测试");
//		if(str.matches("[\u4E00-\u9FA5]")&&!str.contains("（")){
//			System.out.print("测试");
//		}
		
		String regex="^[0-9]*$";
		boolean b = str.matches(regex);
		if(str.matches(regex)) {
			System.out.print("测试");
		}
		
		
	}
	@Test
	public void same(){
		String str[]={"1","1","2"};
		for(int i=0;i<str.length;i++){
			if(i<str.length){
				if(str[i]==str[i+1]){
						// list[i]的值第一个的几个子节点 返回值
					    //查询str[i+1]的值
						//list.get(i+1).seq((list[i]返回的字节的值加一));的值
					    // listget(i+1)的修改parent的值list[i]的id
					
					
				}
			}
			
		}
		
		
		}
	
	}
