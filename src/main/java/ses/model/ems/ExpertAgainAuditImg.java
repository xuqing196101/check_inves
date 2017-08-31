package ses.model.ems;

public class ExpertAgainAuditImg {
	//请求状态
	private boolean status;
	//回调信息
	private String message;
	//登录角色的类型
	private String userType;
	//回调详情
	private Object object;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	@Override
	public String toString() {
		return "ExpertAgainAuditImg [status=" + status + ", message=" + message + ", userType=" + userType + ", object="
				+ object + "]";
	}
	
	
}
