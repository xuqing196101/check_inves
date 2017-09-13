package ses.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * <p>
 * Title:PropertiesUtil
 * </p>
 * <p>
 * Description:读取配置文件工具类
 * </p>
 * <p>
 * Company: ses
 * </p>
 * 
 * @author tkf
 * @date 2016-4-19下午3:31:15
 */
public class PropertiesUtil implements Serializable {
	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(PropertiesUtil.class);

	private Properties cfg = new Properties();

	public PropertiesUtil(String file) {
		file = null;
		if (file != null) {
			InputStream inputStream = null;
			try {
				inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
				cfg.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getString(String key) {
		return cfg.getProperty(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(cfg.getProperty(key));
	}

	public double getDouble(String key) {
		return Double.parseDouble(cfg.getProperty(key));
	}
}
