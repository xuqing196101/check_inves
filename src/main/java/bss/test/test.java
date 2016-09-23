package bss.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		
//		List<String> list=new LinkedList<String>();
//		list.add("1");
//		list.add("2");
//		for (int i=0;i<list.size();i++ ) {
//			System.out.println(list.get(i));
//		}
		
		String s="123.xlsx";
		boolean a = !s.toLowerCase().endsWith(".xls");
		System.out.println(a);
		boolean b = !s.toLowerCase().endsWith(".xlsx");
		System.out.println(b);
		if(a ||b){
			System.out.println("c");
		}
	}

}
