package extract.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {  
	
    /** 时间格式 **/
    private final static String  TIME_PATTERN ="yyyy-MM-dd HH:mm:ss";
    private final static String  DATE_HOUR ="yyyy年MM月dd日HH时";
      
    /** 
     * 将Date类转换为XMLGregorianCalendar 
     * @param date 
     * @return  
     */  
    public static XMLGregorianCalendar dateToXmlDate(Date date){  
            Calendar cal = Calendar.getInstance();  
            cal.setTime(date);  
            DatatypeFactory dtf = null;  
             try {  
                dtf = DatatypeFactory.newInstance();  
            } catch (DatatypeConfigurationException e) {  
            }  
            XMLGregorianCalendar dateType = dtf.newXMLGregorianCalendar();  
            dateType.setYear(cal.get(Calendar.YEAR));  
            //由于Calendar.MONTH取值范围为0~11,需要加1  
            dateType.setMonth(cal.get(Calendar.MONTH)+1);  
            dateType.setDay(cal.get(Calendar.DAY_OF_MONTH));  
            dateType.setHour(cal.get(Calendar.HOUR_OF_DAY));  
            dateType.setMinute(cal.get(Calendar.MINUTE));  
            dateType.setSecond(cal.get(Calendar.SECOND));  
            return dateType;  
        }   
  
    /** 
     * 将XMLGregorianCalendar转换为Date 
     * @param cal 
     * @return  
     */  
    public static Date xmlDate2Date(XMLGregorianCalendar cal){  
        return cal.toGregorianCalendar().getTime();  
    }  
    
    
    /**
     * 获取今天0点时间
     * <简述> 
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-14上午11:49:51
     */
    public static Date getTodayZeroTime() {
    	 Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         calendar.set(Calendar.HOUR_OF_DAY, 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         return calendar.getTime();
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
        SimpleDateFormat  format = new SimpleDateFormat(TIME_PATTERN);
        return format.format(date);
    }
    
    
    /**
     * 
     *〈简述〉date转string 
     *〈详细描述〉日期转string(转换至小时)中文表示  例：  2018年1月1日10时
     * @author myc
     * @param date 
     * @return
     */
    public static String dateToZHString(Date date){
    	SimpleDateFormat  format = new SimpleDateFormat(DATE_HOUR);
    	return format.format(date);
    }
    
    /**
     * 获取今天0点时间返回字符串
     * <简述> 
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-14上午11:49:51
     */
    public static String getTodayZeroTimeToString() {
    	 Calendar calendar = Calendar.getInstance();
         calendar.setTime(new Date());
         calendar.set(Calendar.HOUR_OF_DAY, 0);
         calendar.set(Calendar.MINUTE, 0);
         calendar.set(Calendar.SECOND, 0);
         return dateToString(calendar.getTime());
	}
    
    
    /**
     * 获取前一天0点时间 Date类型
     * <简述> 
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-14上午11:49:51
     */
    public static Date getYesterdayZeroTime() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return calendar.getTime();
    }
    /**
     * 获取前一天0点时间返回字符串
     * <简述> 
     *
     * @author Jia Chengxiang
     * @dateTime 2017-11-14上午11:49:51
     */
    public static String getYesterdayZeroTimeToString() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.set(Calendar.DATE,calendar.get(Calendar.DATE)-1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	return dateToString(calendar.getTime());
    }
} 