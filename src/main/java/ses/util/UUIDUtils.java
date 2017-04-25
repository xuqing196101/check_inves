package ses.util;

import java.util.UUID;

/**
 * 
 * @ClassName: UUIDUtils
 * @Description: 生成UUID工具了
 * @author Easong
 * @date 2017年4月24日 上午9:38:27
 * 
 */
public class UUIDUtils {

	/**
	 * 
	 * @Title: getUuid
	 * @Description: 生成32位
	 * @author Easong
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUUID32() {
		String uuidStr = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		return uuidStr;
	}
}
