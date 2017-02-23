package iss.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
/**
 * 
* @Title: ResponseWrapper.java 
* @Package iss.filter 
* @Description: 缓存数据类
* @author SongDong   
* @date 2017年2月23日 下午1:09:08 
* @version 2017年2月23日
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

	private PrintWriter cachedWriter;
	private CharArrayWriter bufferedWriter;

	public ResponseWrapper(HttpServletResponse response) {
		super(response);
		// 保存返回结果定义
		bufferedWriter = new CharArrayWriter();
		// 这个是包装PrintWriter的，让所有结果通过这个PrintWriter写入到bufferedWriter中
		cachedWriter = new PrintWriter(bufferedWriter);
	}

	@Override
	public PrintWriter getWriter() {
		return cachedWriter;
	}

	/**
	 * 
	* @Title: getResult 
	* @Description: 获取原始的HTML页面内容。
	* @author SongDong   
	* @date 2017年2月23日 下午1:09:24 
	* @param @return
	* @return String
	* @throws 
	* @version 2017年2月23日
	 */
	public String getResult() {
		return bufferedWriter.toString();
	}

}
