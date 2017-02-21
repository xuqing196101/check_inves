package ses.util;

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
		try {
			cfg.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(file));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
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
