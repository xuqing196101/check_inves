package common.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 
 * Description: 字符编码过滤
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
public class Encoding extends CharacterEncodingFilter {

	@Override
	public void setEncoding(String encoding) {
		super.setEncoding(encoding);
	}

	@Override
	public void setForceEncoding(boolean forceEncoding) {
		// TODO Auto-generated method stub
		super.setForceEncoding(forceEncoding);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String servletPath = request.getServletPath();
		if(servletPath!=null&&servletPath.contains("uplinkSms")){
			//字符编码过滤白名单
			super.setEncoding(null);
			super.setForceEncoding(false);
		}else{
			super.setEncoding("UTF-8");
			super.setForceEncoding(true);
		}
		
		super.doFilterInternal(request, response, filterChain);
	}
}
