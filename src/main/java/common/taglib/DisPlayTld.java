package common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>展示标签
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class DisPlayTld extends TagSupport {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = -3670107175836946079L;
    
    /** 业务Id */
    private String businessId;
    /** 业务类型Id */
    private String typeId;
    /** 系统key */
    private Integer sysKey;

    
    
    @Override
    public int doStartTag() throws JspException {
        
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
        
        try {
             out.println("<input id='businessId' type=\"hidden\"  value=" + businessId + " />");
             out.println("<input id='typeId'  type=\"hidden\"  value=" + typeId + " />");
             out.println("<input id='sysKeyId' type=\"hidden\"  value=" + sysKey + " />");
             out.println("<div><ul id='disFileId'></ul></div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        businessId = null;
        typeId = null;
        sysKey = null;
        super.release();
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public Integer getSysKey() {
        return sysKey;
    }

    public void setSysKey(Integer sysKey) {
        this.sysKey = sysKey;
    }
    
    
    

}
