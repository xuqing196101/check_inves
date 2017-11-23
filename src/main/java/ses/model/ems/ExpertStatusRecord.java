package ses.model.ems;

import java.util.Date;

public class ExpertStatusRecord {
	private String expertId;
	private String status;
	private String expertStatus;
	private Date createAt;
	private Date updateAt;
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExpertStatus() {
		return expertStatus;
	}
	public void setExpertStatus(String expertStatus) {
		this.expertStatus = expertStatus;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
}
