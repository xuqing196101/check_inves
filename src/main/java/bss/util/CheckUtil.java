package bss.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/***
 * 验证信息
 * @author YanghongLiang
 *
 */
public class CheckUtil {
    /**
     * 检查 list 是否 有空
     * @return
     */
	public static boolean isList(List<String> list){
		for(String item:list){
			if(item==""||item==null){
				return true;
			}
		}
		return false;
	}
}
