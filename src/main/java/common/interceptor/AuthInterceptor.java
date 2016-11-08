package common.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.annotation.AuthValid;
import common.utils.AuthUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  拦截无权限的请求
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
       
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            
            AuthValid auth = ((HandlerMethod) handler).getMethodAnnotation(AuthValid.class);
            if (auth == null || auth.validate() == false) {
                return true;
            } else {
                return isPermission(request);
            }
            
        }
        return true;
    }
    
    /**
     * 
     *〈简述〉
     *  判断请求的url是否与数据库中的一直
     *〈详细描述〉
     * @author myc
     * @param request
     * @return
     */
    private boolean isPermission(HttpServletRequest request){
       Map<String,String> map = AuthUtil.authCodeMap;
       if (map != null && map.size() > 0) {
          String url =  request.getServletPath();
          for (Map.Entry<String, String> entry : map.entrySet()){
               String sourceUrl = entry.getValue();
               if (sourceUrl.indexOf(url) != -1){
                  return true;
               }
          }
       }
       return false;
    }
    
}
