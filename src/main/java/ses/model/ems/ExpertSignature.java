package ses.model.ems;

import java.util.Date;

public class ExpertSignature{
	private String id;
	private String expertId;
	private String name;
	private String company;
	private String job;
	private Date createdAt;
	private Date updatedAt;
	private String signatoryId;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
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
	public String getSignatoryId() {
		return signatoryId;
	}
	public void setSignatoryId(String signatoryId) {
		this.signatoryId = signatoryId;
	}
	
	
}
