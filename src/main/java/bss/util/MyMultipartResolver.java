package bss.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

 
/**
 * 
 * @Title: MyMultipartResolver
 * @Description: 解决Spring MultipartResolver 或者 ServletFileUpload 导致的冲突 
 * @author Li Xiaoxiao
 * @date  2016年11月11日,下午5:25:12
 *
 */
public class MyMultipartResolver extends CommonsMultipartResolver   {

	 private String excludeUrls;   
	 private String[] excludeUrlArray;
	    
		public String getExcludeUrls() {
	        return excludeUrls;
	    }
	
	
	    public void setExcludeUrls(String excludeUrls) {
	        this.excludeUrls = excludeUrls;
	        this.excludeUrlArray = excludeUrls.split(",");
	    }
	    public boolean isMultipart(HttpServletRequest request) {
	        for (String url: excludeUrlArray) {
	            if (request.getRequestURI().contains(url)) {
	                return false;
	            }
	        }
	        
	        return super.isMultipart(request);
	    }
}
