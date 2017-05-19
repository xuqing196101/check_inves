package common.utils;

import java.text.DateFormat;
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
     * @return 1 DATE1 小  -1 DATE1 大  0相等
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
     *〈简述〉date时间加上N分钟
     *〈详细描述〉
     * @author YangHongLiang
     * @param addMin
     * @return
     */
    public static Date getAddDate(Date date,int addMin){
        long dateLong = (date.getTime() + 1000*60*addMin);
        Date dated = new Date(dateLong);
        return dated;
    }
    /**
     * date 日期加N天
     * @author YangHongLiang
     */
    public static Date addDayDate(Date date,int day){
    	Long oneDay = 1000 * 60 * 60 * 24l;
    	 long dateLong = (date.getTime() + oneDay*day);
         Date dated = new Date(dateLong);
         return dated;
    }
    
    /***
     * 根据当前日期 获取 周几
     * @author YangHongLiang
     * @parma Date
     * @return   1 ，2 ，3 ，4 ，5 ，6， 周日：0
     */
    public static int isWeek(Date date){
    	 Calendar calendar = Calendar.getInstance();
    	  calendar.setTime(date);
    	  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
    	 return intWeek;
    }
    
    /**
     * 
    * @Title: subDayDate 
    * @Description: 日期减N天
    * @author Easong
    * @param @param date
    * @param @param day
    * @param @return    设定文件 
    * @return Date    返回类型 
    * @throws
     */
    public static Date subDayDate(Date date,int day){
    	 Long oneDay = 1000 * 60 * 60 * 24l;
    	 long dateLong = (date.getTime() - oneDay*day);
         Date dated = new Date(dateLong);
         return dated;
    }
    
    /***
     * 根据当前日期 去掉时分秒
     * @author YangHongLiang
     * @parma Date
     * @return   1 ，2 ，3 ，4 ，5 ，6， 周日：0
     * @throws ParseException 
     */
    public static Date combinationDate(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	
    	 Calendar calendar = Calendar.getInstance();
    	  try {
			calendar.setTime(sdf.parse(sdf.format(date)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return calendar.getTime();
    }
    /***
     * 根据当前日期跳过 周六 周日 生产新日期
     * @author YangHongLiang
     * @return Date
     */
    public static Date ignoreWeekend(Date date){
    	//判断 周六
    	if(isWeek(date)==6){
    		return addDayDate(date, 2);
    	}
    	//判断周日
    	if(isWeek(date)==0){
    		return addDayDate(date, 1);
    	}
    	return date;
    }
    /**
     * date 日期 改变  日期年月日 加时分秒组合
     * @author YangHongLiang
     * @param ymd 年月日
     * @param yms 时分秒
     */
    public static Date changeDate(Date ymd,Date hms){
    	Calendar ymd_cal=Calendar.getInstance();
    	Calendar hms_cal=Calendar.getInstance();
    	Calendar chang_cal=Calendar.getInstance();
    	ymd_cal.setTime(ymd);
    	hms_cal.setTime(hms);
    	chang_cal.set(ymd_cal.get(Calendar.YEAR), ymd_cal.get(Calendar.MONTH), 
    			ymd_cal.get(Calendar.DAY_OF_MONTH), hms_cal.get(Calendar.HOUR_OF_DAY),
    			hms_cal.get(Calendar.MINUTE), hms_cal.get(Calendar.SECOND));
         return chang_cal.getTime();
    }
    
    /***
     * 根据时间段 获取当前日期的周六周日的天数
     * @author YangHongliang
     * @return INT
     */
    public static int isWeekend(Date statDate,Date endDate){
    	Calendar stat_cal = Calendar.getInstance();
    	Calendar end_cal = Calendar.getInstance();
    	stat_cal.setTime(statDate);
    	end_cal.setTime(endDate);
    	int i=0;
    	while(stat_cal.before(end_cal)){
    		if(stat_cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||stat_cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
    		  i++;
    		 }
    		stat_cal.add(Calendar.DAY_OF_MONTH, 1);
    	}
    	return i;
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
    
    /**
     * 计算N个月后的日期
    * @Title: getNMonthAfterDate 
    * @Description: 
    * @author Easong
    * @param @param startDate
    * @param @param amount
    * @param @return    设定文件 
    * @return Date    返回类型 
    * @throws
     */
    public static Date getNMonthAfterDate(Date startDate,Integer amount){
    	// 创建日历实例
    	Calendar calendar = Calendar.getInstance();
    	// 设置起始日期
    	calendar.setTime(startDate);
    	// 获取从起始日期后加上N个月后的日期
    	calendar.add(Calendar.MONTH, amount);
    	// 得到Date
    	return calendar.getTime();
    }
    
    /**
     * 
    * @Title: getDateFormat 
    * @Description: 通过格式化方式获取DateFormat
    * 如：yyyy   yyyy-MM  yyyy-MM-dd  yyyy-MM-dd hh:mm:ss
    * @author Easong
    * @param @param dateFormat
    * @param @return    设定文件 
    * @return DateFormat    返回类型 
    * @throws
     */
    public static DateFormat getDateFormat(String dateFormat){
    	return new SimpleDateFormat(dateFormat);
    }
    
    /**
     * 
    * @Title: getYear 
    * @Description: 获取当前所在年年
    * @author Easong
    * @param @param date
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
    public static String getYear(Date date) {
		// 获取年
		DateFormat dateFormatForyear = DateUtils.getDateFormat("yyyy");
		String year = dateFormatForyear.format(date);
		return year;
	}
    
    /**
     * 
    * @Title: getDayOfYear 
    * @Description: 获取当前所在天/月
    *  如：20170511(天)  201705(月)
    * @author Easong
    * @param @param date
    * @param @param dateFormat
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws
     */
    public static Integer getDayOfYear(Date date, DateFormat dateFormat) {
		String dayString = dateFormat.format(date).replace("-", "");
		Integer dayOrMonth = Integer.parseInt(dayString);
		return dayOrMonth;
	}
    
    /**
     * 
    * @Title: getWeekOfYear 
    * @Description: 获取当前年的第几周
    * @author Easong
    * @param @return    设定文件 
    * @return int    返回类型 
    * @throws
     */
    public static int getWeekOfYear() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.WEEK_OF_YEAR) + 1;
		return i;
	}
    
    
    /**
     * 
    * @Title: getDate 
    * @Description: 根据当前日期获取前N个月后的日期
    * @author Easong
    * @param @param month
    * @param @return    设定文件 
    * @return String    返回类型 
    * @throws
     */
	public static Date getDate(Date currDate, int month) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		cal.add(Calendar.MONTH, month);// 对月份进行计算
		return cal.getTime();
	}
}
