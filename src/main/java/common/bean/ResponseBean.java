package common.bean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>json数据返回对象
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class ResponseBean {
    
    /** 是否成功 **/
    private boolean success = false;
    
    /** 对象 **/
    private Object obj;
    
    private String ids;

   
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
    
    
}
