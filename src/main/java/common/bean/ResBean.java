package common.bean;
/**
 * 
 * 版权：(C) 版权所有 
 * <简述>结果bean
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class ResBean {

    private boolean success;
    
    private String filePath;
    
    private String msg;
    
    private String error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
}
