package common.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

/**
 * 工具类。方便对request对象进行操作
 * 
 * @author tianzhiqiang
 * @since 2017-4-1
 * @version 1.0
 */
public class RequestTool {

	/**
	 * 获取参数值，如果值为""，则返回null
	 */
	public static String getParam(HttpServletRequest request, String param) {
		return getParam(request, param, null);
	}

	/**
	 * 获取参数值，如果值为""，则返回缺省值
	 * 
	 * @param request
	 *            request对象
	 * @param param
	 *            参数名
	 * @param edefaultValuefaultValue
	 *            参数值为空时的缺省值
	 */
	public static String getParam(HttpServletRequest request, String param,
			String defaultValue) {
		String result = request.getParameter(param);
		if (null == result) {
			result = defaultValue;
		}
		if (null != result) {
			try {
				//统一转换编码
				result=new String(result.trim().getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result = defaultValue;
			}
		}
		return result;
	}

	/**
	 * 获取int形式的参数值，如果值为null或别的不能被转换成int的值，则返回缺省值
	 */
	public static int getParam(HttpServletRequest request, String param,
			int defaultValue) {
		String result = request.getParameter(param);
		int intResult = defaultValue;
		try {
			if (null != result) {
				result = result.trim();
				intResult = Integer.parseInt(result);
			}
		} catch (Exception e) {
			intResult = defaultValue;
		}
		return intResult;
	}

	/**
	 * 获取double形式的参数值，如果值为null或别的不能被转换成double的值，则返回缺省值
	 */
	public static double getParam(HttpServletRequest request, String param,
			double defaultValue) {
		String result = request.getParameter(param);
		double dResult = defaultValue;
		try {
			if (null != result) {
				result = result.trim();
				dResult = Double.parseDouble(result);
			}
		} catch (Exception e) {
			dResult = defaultValue;
		}
		return dResult;
	}

	/**
	 * 获取float形式的参数值，如果值为null或别的不能被转换成float的值，则返回缺省值
	 */
	public static float getParam(HttpServletRequest request, String param,
			float defaultValue) {
		String result = request.getParameter(param);
		float fResult = defaultValue;
		try {
			if (null != result) {
				result = result.trim();
				fResult = Float.parseFloat(result);
			}
		} catch (Exception e) {
			fResult = defaultValue;
		}
		return fResult;
	}
}