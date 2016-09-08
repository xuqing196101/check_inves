package ses.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public class DateUtil implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");      
	        dateFormat.setLenient(false);      
	        try {      
	            return dateFormat.parse(source);      
	        } catch (Exception e) {      
	            e.printStackTrace();      
	        }   
	        
		return null;
	}

	 

}
