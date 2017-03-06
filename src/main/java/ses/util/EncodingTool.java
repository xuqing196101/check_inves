package ses.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * <简述>中文乱码
 * <详细描述>
 * @author  LiWanLin
 */
public class EncodingTool {
	public static String encodeStr(String str) {  
        try {  
            return new String(str.getBytes("ISO-8859-1"), "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
}
