package bss.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		InputStream inputStream = null;
	    InputStreamReader streamReader = null;
		try {
			inputStream = PropUtil.class.getClassLoader().getResourceAsStream(file);
		    streamReader = new InputStreamReader(inputStream,"UTF-8");
			prop.load(streamReader);
		} catch (IOException e) {
			logger.error(e);
			logger.error("初始化 Properties 失败");
		} finally {
			try {
		        if (inputStream != null) {
		          inputStream.close();
		        }
		        if (streamReader != null) {
		          streamReader.close();
		        }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
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
}
