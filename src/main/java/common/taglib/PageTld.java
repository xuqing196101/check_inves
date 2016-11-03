package common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * 版权：(C) 版权所有 <简述> 自定义标签 <详细描述> 自定义翻页标签
 * 
 * @author myc
 * @version
 * @since
 * @see
 */
public class PageTld extends TagSupport {

    private static final long serialVersionUID = 3130451108328639412L;
    /** Id */
    private String id;

    /** 支持class样式 **/
    private String pClass;
    /** 支持style样式 **/
    private String pStyle;
    /** 总页数 **/
    private String pPages;
    /** 当前页 **/
    private String pCurr;
    /** 总条数 **/
    private String pTotal;
    /** 开始行 **/
    private String pStart;
    /** 结束行 **/
    private String pEnd;
    /** url **/
    private String url;
    /** 支持样式 */
    private String align;

    /**
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {

        JspWriter out = pageContext.getOut();
        try {
            out.println("<input type='hidden' id='pageNum' value='" + pPages
                    + "' />");
            out.println("<input type='hidden' id='pageCurr' value='" + pCurr
                    + "' />");
            out.println("<input type='hidden' id='pTotal' value='" + pTotal
                    + "' />");
            out.println("<input type='hidden' id='pStart' value='" + pStart
                    + "' />");
            out.println("<input type='hidden' id='pEnd' value='" + pEnd
                    + "' />");
            out.println("<input type='hidden' id='url' value='" + url + "' />");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    /**
     * 
     * @see javax.servlet.jsp.tagext.TagSupport#release()
     */
    @Override
    public void release() {
        url = null;
        pPages = null;
        pCurr = null;
        pStart = null;
        pEnd = null;
        pTotal = null;
        super.release();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpClass() {
        return pClass;
    }

    public void setpClass(String pClass) {
        this.pClass = pClass;
    }

    public String getpStyle() {
        return pStyle;
    }

    public void setpStyle(String pStyle) {
        this.pStyle = pStyle;
    }

    public String getpPages() {
        return pPages;
    }

    public void setpPages(String pPages) {
        this.pPages = pPages;
    }

    public String getpCurr() {
        return pCurr;
    }

    public void setpCurr(String pCurr) {
        this.pCurr = pCurr;
    }

    public String getpTotal() {
        return pTotal;
    }

    public void setpTotal(String pTotal) {
        this.pTotal = pTotal;
    }

    public String getpStart() {
        return pStart;
    }

    public void setpStart(String pStart) {
        this.pStart = pStart;
    }

    public String getpEnd() {
        return pEnd;
    }

    public void setpEnd(String pEnd) {
        this.pEnd = pEnd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

}
