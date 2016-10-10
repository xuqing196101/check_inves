package ses.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyException implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
				// 添加自己的异常处理逻辑，如日志记录等
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("ex", ex);
				// 根据不同错误转向不同页面
				if (ex instanceof SQLException) {
					return new ModelAndView("error", model);
				} else if (ex instanceof IOException) {
					return new ModelAndView("error", model);
				} else {
					return new ModelAndView("error", model);
				}
	}


}
