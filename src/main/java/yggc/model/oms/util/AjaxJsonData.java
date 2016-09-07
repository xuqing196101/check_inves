package yggc.model.oms.util;


/**
 * 
* <p>Title:AjaxJsonData </p>
* <p>Description: </p>
* <p>Company: yggc </p> 
* @author tkf
* @date 2016-9-6下午5:52:37
 */
public class AjaxJsonData {

	private String message;
	
	private Object obj;
	
	private boolean success;
	
	private int flag;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	
}
