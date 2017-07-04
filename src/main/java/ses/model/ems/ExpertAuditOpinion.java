package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

public class ExpertAuditOpinion implements Serializable{
	/**
	 * ExpertAuditOpinion.java
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String expertId;
	private String opinion;
	private Date updatedAt;
	private Date createdAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
