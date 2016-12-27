package synchro.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
}
