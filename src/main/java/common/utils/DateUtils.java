package common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>时间工具类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class DateUtils {
    
    /** 日期格式 **/
    private final static String  DATE_PATTERN ="yyyy-MM-dd";
    
    /** 时间格式 **/
    private final static String  TIME_PATTERN ="yyyy-MM-dd HH:mm:ss";
    
    /**
     * 
     *〈简述〉获取当前日期
     *〈详细描述〉
     * @author myc
     * @return 当前日期,如:yyyy-MM-dd
     */
    public static String getCurrentDate(){
        SimpleDateFormat  format = new SimpleDateFormat(DATE_PATTERN);
        Calendar cal = Calendar.getInstance();
        return format.format(cal.getTime());
    }
    
    /**
     * 
     *〈简述〉将字符创转为时间格式
     *〈详细描述〉
     * @author myc
     * @param str 输入字符串
     * @param pattern  转换格式
     * @return
     */
    public static Date stringToDate(String str, String pattern){
        try {
            SimpleDateFormat  format = new SimpleDateFormat(pattern);
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 
     *〈简述〉字符串转时间
     *〈详细描述〉
     * @author myc
     * @param str
     * @return
     */
    public static Date stringToTime(String str){
        return stringToDate(str, TIME_PATTERN);
    }
    
    /**
     * 
     *〈简述〉将字符串转为时间格式字符串
     *〈详细描述〉
     * @author myc
     * @param str
     * @return
     */
    public static String dateString(String str){
        SimpleDateFormat  format = new SimpleDateFormat(TIME_PATTERN);
        Date date = stringToDate(str, TIME_PATTERN);
        if (date != null){
            return format.format(date);
        }
        return "";
    }
    /***
     * 根据 两个 日期 比较大 小
     * @param smdate
     * @param bdate
     * @return 1 DATE1 小  2 DATE1 大
     */
    public static int compareDate(Date dt1, Date dt2) {
		try {
			if (dt1.getTime() > dt2.getTime()) {
				// "dt1 在dt2前"
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				// "dt1在dt2后"
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
    	}
    /**
     * 
     *〈简述〉当前时间加上N分钟
     *〈详细描述〉
     * @author YangHongLiang
     * @param dateString
     * @return
     */
    public static Date getAddDate(Date date,int addMin){
        long dateLong = (date.getTime() + 1000*60*addMin);
        Date dated = new Date(dateLong);
        return dated;
    }
    
    /**
     * 
     *〈简述〉获取当前时间
     *〈详细描述〉
     * @author myc
     * @return
     */
    public static String getCurrentTime(){
        SimpleDateFormat  format = new SimpleDateFormat(TIME_PATTERN);
        Calendar cal = Calendar.getInstance();
        return format.format(cal.getTime());
    }
    
    /**
     * 
     *〈简述〉获取昨天的日期格式
     *〈详细描述〉
     * @author myc
     * @return 
     */
    public static String getYesterDay(){
        SimpleDateFormat  format = new SimpleDateFormat(DATE_PATTERN);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return format.format(cal.getTime());
    }
    
    /**
     * 
     *〈简述〉日期转string
     *〈详细描述〉
     * @author myc
     * @param date 
     * @return
     */
    public static String dateToString(Date date){
        SimpleDateFormat  format = new SimpleDateFormat(DATE_PATTERN);
        return format.format(date);
    }
    
    /**
     * 
     *〈简述〉当前时间减去一分钟
     *〈详细描述〉
     * @author myc
     * @param dateString
     * @return
     */
    public static String getCalcelDate(String dateString){
        long dateLong = (stringToTime(dateString).getTime() + 1000);
        Date date = new Date(dateLong);
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);
        return dateFormat.format(date);
    }
    
    /**
     * 
     *〈简述〉将long转为date
     *〈详细描述〉
     * @author myc
     * @param timeMillis
     * @return
     */
    public static Date longToDate(long timeMillis){
        SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_PATTERN);
        String date = dateFormat.format(timeMillis);
        return stringToTime(date);
    }
}
