package common.taglib;

import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import common.utils.AuthUtil;

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
public class AuthTld extends TagSupport {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 8425881088266956591L;
    
    /** 权限编码 **/
    private String code;

    @Override
    public int doStartTag() throws JspException {
        boolean result = false;
        Map<String,String> map = AuthUtil.authCodeMap;
        if (map != null && map.size() > 0){
            if (map.containsKey(code)){
                result = true;
            }
        }
      
       return result? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    

}
