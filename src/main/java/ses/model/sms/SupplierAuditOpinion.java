package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierAuditOpinion implements Serializable{
	/**
	 * SupplierAuditOpinion.java
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String supplierId;
	private String opinion;
	private Date createdAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}
