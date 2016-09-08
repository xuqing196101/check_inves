package ses.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class DateConvert implements WebBindingInitializer{

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// <3> 定义转换格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//true代表允许输入值为空
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}

}
