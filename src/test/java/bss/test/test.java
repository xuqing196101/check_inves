package bss.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import bss.model.pms.PurchaseRequired;

public class test {
	public static void main(String[] args) {
		
//		List<String> list=new LinkedList<String>();
//		list.add("1");
//		list.add("2");
//		for (int i=0;i<list.size();i++ ) {
//			System.out.println(list.get(i));
//		}
		
//		String s="123.xlsx";
//		boolean a = !s.toLowerCase().endsWith(".xls");
//		System.out.println(a);
//		boolean b = !s.toLowerCase().endsWith(".xlsx");
//		System.out.println(b);
//		if(a ||b){
//			System.out.println("c");
//		}
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		PurchaseRequired p=new PurchaseRequired();
		p.setSeq("一");
		PurchaseRequired p1=new PurchaseRequired();
		p1.setSeq("（一）");
		PurchaseRequired p2=new PurchaseRequired();
		p2.setSeq("1");
		PurchaseRequired p3=new PurchaseRequired();
		p3.setSeq("（1）");
		
		PurchaseRequired p4=new PurchaseRequired();
		p4.setSeq("a");
		PurchaseRequired p5=new PurchaseRequired();
		p5.setSeq("（a）");
		PurchaseRequired p6=new PurchaseRequired();
		p6.setSeq("二");
		PurchaseRequired p7=new PurchaseRequired();
		p7.setSeq("（一）");
		PurchaseRequired p8=new PurchaseRequired();
		p8.setSeq("1");
		PurchaseRequired p9=new PurchaseRequired();
		p9.setSeq("（1）");
		
		PurchaseRequired p10=new PurchaseRequired();
		p10.setSeq("a");
		PurchaseRequired p11=new PurchaseRequired();
		p11.setSeq("（a）");
		PurchaseRequired p12=new PurchaseRequired();
		p12.setSeq("（b）");
		
		PurchaseRequired p13=new PurchaseRequired();
		p13.setSeq("（c）");
		
		PurchaseRequired p14=new PurchaseRequired();
		p14.setSeq("（d）");
		list.add(p);
		list.add(p14);
		list.add(p13);
		list.add(p12);
		list.add(p11);
		list.add(p10);
		list.add(p9);
		list.add(p8);
		list.add(p7);
		list.add(p6);
		list.add(p5);
		list.add(p4);
		list.add(p3);
		list.add(p2);
		list.add(p1);
		
		
		
		
		
		
		
		
		
	}

		@Test
		public void ss(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date today=new Date();
			String str = sdf.format(today);
			System.out.println(str);
			Date date2= addDate(new Date(),3,45);
			
			String str2 = sdf.format(date2);
			System.out.println( str2);
		     if(today.getTime()>date2.getTime()){
		    	 System.out.println(123);
		     }   
		        
		}
		
		public Date addDate(Date baseDate, int type, int num) {
			Date lastDate = null;
			Calendar cale = Calendar.getInstance();
			cale.setTime(baseDate);
			if (type == 1)
				cale.add(Calendar.YEAR, num);
			else if (type == 2)
				cale.add(Calendar.MONTH, num);
			else if (type == 3)
				cale.add(Calendar.DAY_OF_MONTH, num);
			else if(type == 4)
				cale.add(Calendar.HOUR, num);
			lastDate = cale.getTime();
			return lastDate;
		} 

		@Test
		public void test6(){
			String str="123123.0";
			String string = str.substring(0, str.lastIndexOf("."));
			System.out.print(string);
			
		}
		
}
