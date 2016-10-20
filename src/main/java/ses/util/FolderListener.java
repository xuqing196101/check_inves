package ses.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class FolderListener implements ServletContextListener {
private static Properties prop = new Properties();
	
	
	/** 加载默认配置文件 */
	private static Properties getProperties(String file) {
		file = file == null ? "properties.properties" : file;
		try {
			prop.load(PropUtil.class.getClassLoader().getResourceAsStream(file));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return prop;
	}
	
	private static String getProperty(String key) {
		Properties properties = FolderListener.getProperties(null);
		String property = properties.getProperty(key);
		return property;
	}

	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	/** 创建文件夹 */
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ac = sce.getServletContext();
		String path1 = ac.getRealPath("/") + FolderListener.getProperty("file.stashPath") + "/";
		File file1 = new File(path1);
		if(!file1.exists()) {
			file1.mkdirs();
		}
	}
}
