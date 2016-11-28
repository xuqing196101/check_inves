package ses.util;

import org.apache.commons.lang3.StringUtils;


public class StringUtil {
    
    /**
     * 
     *〈简述〉是否为字母
     *〈详细描述〉
     * @author myc
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {   
        int k = 0x80;
        return c / k == 0 ? true : false; 
    }
    
    /**
     * 
     *〈简述〉
     *  判断字符串的长度
     *〈详细描述〉
     * @author myc
     * @param strParameter 字符串
     * @param limitLength  长度
     * @return 合法返回true,不合法返回false
     */
    public static boolean validateStrByLength(String strParameter , int limitLength)
    {
         int temp_int = 0;
         byte[] b = strParameter.getBytes();
      
         for(int i = 0 ; i < b.length ; i++){
            if(b[i] >= 0){
               temp_int=temp_int+1;
            } else {
               temp_int=temp_int+2;
               i++;
            }
         }
      
         if(temp_int > limitLength){
             return false;
          } else {
             return true;
         }
    }
    
    /**
     * 
     *〈简述〉
     *  截取字符串
     *〈详细描述〉
     * @author myc
     * @param value
     * @param limit
     * @return
     */
    public static String sub(String value, int limit){
        if (StringUtils.isNotBlank(value) && value.length() > limit) {
            return value.substring(0,limit) + "...";
        }
        return value;
    }
}
