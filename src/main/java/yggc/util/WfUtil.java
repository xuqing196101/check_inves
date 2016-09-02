package yggc.util;

import java.util.UUID;

public class WfUtil {
	
	/**
	* @Title: createUUID
	* @author szf
	* @date 2016-9-2 下午2:55:16  
	* @Description: TODO UUID生成
	* @param @return      
	* @return String
	 */
	public static String createUUID(){
		String uuid = UUID.randomUUID().toString();
		String subid= uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
		return subid;
	}
}
