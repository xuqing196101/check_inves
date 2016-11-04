package ses.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * @Title: PropUtil
 * @Description: Properties 工具类
 * @author: Wang Zhaohua
 * @date: 2016-5-26下午4:45:06
 */
public class PropUtil {
	
	private static Logger logger = Logger.getLogger(PropUtil.class);
	private static Properties prop = new Properties();
	
	/**
	 * @Title: getProperties
	 * @author: Wang Zhaohua
	 * @date: 2016-5-26 下午4:44:37
	 * @Description: 初始化 Properties
	 * @param: @param file
	 * @param: @return
	 * @return: Properties
	 */
	private static Properties getProperties(String file) {
		file = file == null ? "config.properties" : file;
		try {
			prop.load(PropUtil.class.getClassLoader().getResourceAsStream(file));
		} catch (IOException e) {
			logger.error(e);
			logger.error("初始化 Properties 失败");
		}
		return prop;
	}
	
	/**
	 * @Title: getProperty
	 * @author: Wang Zhaohua
	 * @date: 2016-5-26 下午4:40:26
	 * @Description: 获取默认配置文件(properties.properties)的属性值
	 * @param: @param key
	 * @param: @return
	 * @return: String
	 */
	public static String getProperty(String key) {
		Properties properties = PropUtil.getProperties(null);
		String property = properties.getProperty(key);
		return property;
	}
	
	/**
	 * @Title: getIntegerProperty
	 * @author: Wang Zhaohua
	 * @date: 2016-6-15 下午2:36:40
	 * @Description: 获取默认配置文件(properties.properties)的属性值, 转换为 Integer
	 * @param: @param key
	 * @param: @return
	 * @return: Integer
	 */
	public static Integer getIntegerProperty(String key) {
		Properties properties = PropUtil.getProperties(null);
		String property = properties.getProperty(key);
		return Integer.parseInt(property);
	}
	
	/**
	 * @Title: getProperty
	 * @author: Wang Zhaohua
	 * @date: 2016-5-26 下午4:44:03
	 * @Description: 获取指定文件下的属性值
	 * @param: @param file
	 * @param: @param key
	 * @param: @return
	 * @return: String
	 */
	public static String getProperty(String file, String key) {
		Properties properties = getProperties(file);
		String property = properties.getProperty(key);
		return property;
	}
	
	/**
	 * @Title: getIntergerProperty
	 * @author: Wang Zhaohua
	 * @date: 2016-10-10 上午10:04:38
	 * @Description: 获取指定文件下的属性 Integer 值
	 * @param: @param file
	 * @param: @param key
	 * @param: @return
	 * @return: Integer
	 */
	public static Integer getIntergerProperty(String file, String key) {
		Properties properties = getProperties(file);
		String property = properties.getProperty(key);
		return Integer.valueOf(property);
	}
}
