package ses.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import ses.model.bms.User;

public class SecurityFilter implements Filter {
	public FilterConfig config;

	public static boolean isContains(String container, String[] regx) {
		boolean result = false;

		for (int i = 0; i < regx.length; i++) {
			if (container.indexOf(regx[i]) != -1) {
				return true;
			}
		}
		return result;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(
				(HttpServletResponse) response);

		String path = hrequest.getServletContext().getContextPath();

		String ignore = config.getInitParameter("ignore");
		String[] ignores = ignore.split(";");
		if (this.isContains(hrequest.getRequestURI(), ignores)) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = hrequest.getSession();
		boolean register=false;
	if(	session.getAttribute("register") !=null){
		register=true;
	}
		if (!register) {
			wrapper.sendRedirect(path + "/index/sign.html");
		} else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void destroy() {
		this.config = null;
	}

}
