package ses.model.ems;

import java.util.Date;

public class ExpertGroup {
	/**组ID*/
	private String groupId;
	/**批次ID*/
	private String batchId;
	/**组名*/
	private String groupName;
	/**组号*/
	private String count;
	/**创建时间*/
	private Date createdAt;
	/**修改时间*/
	private Date updatedAt;
	/**配置状态（1:待分配，2:分配中，3:分配完成）*/
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
