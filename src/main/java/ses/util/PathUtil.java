package ses.util;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 
* @Title:PathUtil
* @Description: 下载工具类
* @author ZhaoBo
* @date 2016-9-9下午4:01:31
 */
public class PathUtil {
	
	/**
	 * 
	* @Title: getProjectPath
	* @author ZhaoBo
	* @date 2016-9-9 下午4:02:12  
	* @Description: 获得工程里的path，到 class 目录下面 
	* @param @return      
	* @return String
	 */
	public static String getProjectPath(){
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		URL resource = cld.getResource("");
		String url = resource.getFile();
		url = URLDecoder.decode(url);
		return url; 
	}

	/**
	 * 
	* @Title: getWebInfPath
	* @author ZhaoBo
	* @date 2016-9-9 下午4:02:24  
	* @Description: 获取当前工程的web-inf路径 
	* @param @return      
	* @return String
	 */
	public static String getWebInfPath(){
	     String path = getProjectPath();
	     if (path.indexOf("WEB-INF") > 0) {
	      path = path.substring(0, path.indexOf("WEB-INF")+8);
	     } else {
	      //throw new IllegalAccessException("路径获取错误");
	     }
	     return path;
	}

	/**
	 * 
	* @Title: getWebRoot
	* @author ZhaoBo
	* @date 2016-9-9 下午4:02:35  
	* @Description: 获取当前工程路径 
	* @param @return      
	* @return String
	 */
	public static String getWebRoot(){
	     String path = getProjectPath();
	     if (path.indexOf("WEB-INF") > 0) {
	      path = path.substring(0, path.indexOf("WEB-INF/classes"));
	     } else {
	      //throw new IllegalAccessException("路径获取错误");
	     }
	     return path;
	}
	
	public static String getProjectPath(String path){
		return getProjectPath() + path;
	}
}
