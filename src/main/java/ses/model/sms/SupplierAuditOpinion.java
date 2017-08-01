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
	/**审核标识：0：审核不通过 1：审核通过**/
	private Integer flagTime;
	/**第几次审核意见标识 0：第一次审核 1：第二次审核...**/
	private Integer flagAduit;
	/**是否下载了入库复审表 1：已下载**/
	private Integer isDownLoadAttch;
	/**审核意见  同意..../不同意**/
	private String cateResult;

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

	public Integer getFlagTime() {
		return flagTime;
	}

	public void setFlagTime(Integer flagTime) {
		this.flagTime = flagTime;
	}

	public Integer getFlagAduit() {
		return flagAduit;
	}

	public void setFlagAduit(Integer flagAduit) {
		this.flagAduit = flagAduit;
	}

    public Integer getIsDownLoadAttch() {
        return isDownLoadAttch;
    }

    public void setIsDownLoadAttch(Integer isDownLoadAttch) {
        this.isDownLoadAttch = isDownLoadAttch;
    }

	public String getCateResult() {
		return cateResult;
	}

	public void setCateResult(String cateResult) {
		this.cateResult = cateResult;
	}
}
