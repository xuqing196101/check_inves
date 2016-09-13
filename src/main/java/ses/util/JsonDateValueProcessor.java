package ses.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Description: 时间处理器
 * 
 * @author Ye MaoLin
 * @version 2016-9-12
 * @since JDK1.7
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

	private String formatDateTime = "yyyy-MM-dd HH:mm:ss";
	private String formatDate = "yyyy-MM-dd";

	public Object processArrayValue(Object value, JsonConfig config) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig config) {
		return process(value);
	}

	private Object process(Object value) {

		if (value instanceof Date) {
			SimpleDateFormat sdf;
			if (value.toString().length() <= 11
					|| value.toString().indexOf("00:00:00.0") >= 0) {
				sdf = new SimpleDateFormat(formatDate, Locale.UK);
			} else {
				sdf = new SimpleDateFormat(formatDateTime, Locale.UK);
			}
			return sdf.format(value);
		}
		return value == null ? "" : value.toString();
	}
}