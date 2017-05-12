package bss.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test3 {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		 SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		 Date date = sdf.parse("2017-05-04 19:20:00");
		 System.out.print(daysBetween(date));
	}
	 public static int daysBetween(Date date) throws ParseException {
	        // 获取当前时间
	        Date nowDate = new Date();
	        // SimpleDateFormat
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        date = sdf.parse(sdf.format(date));
	        nowDate = sdf.parse(sdf.format(nowDate));
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        long time1 = cal.getTimeInMillis();
	        cal.setTime(nowDate);
	        long time2 = cal.getTimeInMillis();
	        // 算出两个时间差,单位毫秒所以除以(1000*3600*24)
	        long betweenDays = (time2 - time1)/(1000*3600*24);
	        // 精确小数
	        return Integer.parseInt(String.valueOf(betweenDays));
	    }

}
