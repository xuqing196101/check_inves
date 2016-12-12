package common.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import common.annotation.CurrentUser;
import ses.model.bms.User;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>获取当前登录用户的信息
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class LoginedArgumentResolver implements HandlerMethodArgumentResolver {
    
    /**
     * 
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#supportsParameter(org.springframework.core.MethodParameter)
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        
        if (parameter.getParameterAnnotation(CurrentUser.class) != null
                && parameter.getParameterType() == User.class){
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request= (HttpServletRequest) webRequest.getNativeRequest();
        User user = (User)request.getSession().getAttribute("loginUser");
        return user;
    }

}
