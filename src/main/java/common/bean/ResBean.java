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
    
    /** 是否成功 **/
    private boolean success;
    /** 文件路径 **/
    private String filePath;
    /** 提示消息 **/
    private String msg;
    /** 错误消息 **/
    private String error;
    /** 长度提示 **/
    private String lenTxt;
    /** 是否是图片 **/
    private boolean picture;
    /** 文件id **/
    private String fileIds;

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

    public String getLenTxt() {
        return lenTxt;
    }

    public void setLenTxt(String lenTxt) {
        this.lenTxt = lenTxt;
    }

    public boolean isPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }


    
}
