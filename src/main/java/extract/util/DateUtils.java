package extract.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtils {  
	
	  /** 日期格式 **/
    private final static String  DATE_PATTERN ="yyyy-MM-dd";
    
    /** 时间格式 **/
    private final static String  TIME_PATTERN ="yyyy-MM-dd HH:mm:ss";
      
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
    public static Date getCurrentDateStartTime() {
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
} 