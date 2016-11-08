package common.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import ses.model.bms.PreMenu;
import ses.model.bms.User;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class AuthUtil {
    
    /** 权限 map */
    public static Map<String,String> authCodeMap;
    
    /**
     * 
     *〈简述〉设置权限
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     */
    public static void setAuth(HttpServletRequest request){
        authCodeMap = new ConcurrentHashMap<String, String>();
        User user = (User)request.getSession().getAttribute("loginUser");
        if (user != null) {
            List<PreMenu> menulist =  user.getMenus();
            for (PreMenu menu : menulist){
                 if (StringUtils.isNotBlank(menu.getUrl())){
                     if (StringUtils.isNotBlank(menu.getPermissionCode())){
                         authCodeMap.put(menu.getPermissionCode(), menu.getId());
                     }
                 }
            }
        }
    }
}
