package ses.formbean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 *  ajax 请求相应
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class ResponseBean {
    
    /** 返回结果标识 */
    private boolean result;
    /** 错误信息提示 */
    private String errorMsg;
    /** 返回对象 */
    private Object obj;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    
}
