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
	/**审核意见  同意..../不同意**/
	private String cateResult;

	/**第几次审核意见标识 0：第一次(初审) 1：第二次(复审)**/
	private Integer flagTime;
	/**审核标识：0：审核不通过 1：审核通过**/
	/**审核标识：16：审核不通过 15：审核通过**/
	private Integer flagAudit;
	/**是否下载了入库复审表 1：已下载**/
	private Integer isDownLoadAttch;

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

	public Integer getFlagTime() {
		return flagTime;
	}

	public void setFlagTime(Integer flagTime) {
		this.flagTime = flagTime;
	}

	public Integer getFlagAudit() {
		return flagAudit;
	}

	public void setFlagAudit(Integer flagAudit) {
		this.flagAudit = flagAudit;
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
