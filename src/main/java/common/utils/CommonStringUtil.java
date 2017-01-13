package common.utils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 字符串处理类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CommonStringUtil {
    
    /**
     * 
     *〈简述〉获取字符的拼接
     *〈详细描述〉
     * @author myc
     * @param chars 需要拼接的字符
     * @param len 拼接的个数
     * @return
     */
    public static  String getAppendString(String chars, int len){
        String str = "";
        if (len <= 0){
            return str;
        }
        StringBuffer sb = new StringBuffer();
        for (int i =0;i<len;i++){
            sb.append(chars);
        }
        str = sb.toString();
        return str;
    }
    
}
