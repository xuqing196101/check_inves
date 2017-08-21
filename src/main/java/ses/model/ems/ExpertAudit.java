package ses.model.ems;

import java.io.Serializable;
import java.util.Date;

public class ExpertAudit implements Serializable{
    /**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    //专家id
    private String expertId;
    //审核人id
    private String auditUserId;
    //审核人姓名
    private String auditUserName;
    //审核理由
    private String auditReason;
    //审核结果；0未审核，1通过，2未通过,3退回修改
    private String auditResult;
    //是否为历史数据 0否  1是
    private String type;
    //审核时间
    private Date auditAt;
    //是否删除0否  1是
    private Integer isDeleted;
    //审批类型(StepNumber)
    private String suggestType;
    //审核字段
    private String auditField;
    //审批内容
    private String auditContent;
    
    private String auditFieldId;
    
    private String auditFieldName;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId == null ? null : auditUserId.trim();
    }

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUserName) {
        this.auditUserName = auditUserName == null ? null : auditUserName.trim();
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason == null ? null : auditReason.trim();
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult == null ? null : auditResult.trim();
    }

    public Date getAuditAt() {
        return auditAt;
    }

    public void setAuditAt(Date auditAt) {
        this.auditAt = auditAt;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type == null ? null : type.trim();
    }


	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getSuggestType() {
		return suggestType;
	}

	public void setSuggestType(String suggestType) {
		this.suggestType = suggestType;
	}

	public String getAuditField() {
		return auditField;
	}

	public void audit(String auditField) {
		this.auditField = auditField;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}

	public String getAuditFieldId() {
		return auditFieldId;
	}

	public void setAuditFieldId(String auditFieldId) {
		this.auditFieldId = auditFieldId;
	}

	public void setAuditField(String auditField) {
		this.auditField = auditField;
	}

	public String getAuditFieldName() {
		return auditFieldName;
	}

	public void setAuditFieldName(String auditFieldName) {
		this.auditFieldName = auditFieldName;
	}

	@Override
	public String toString() {
		return "ExpertAudit [id=" + id + ", expertId=" + expertId + ", auditUserId=" + auditUserId + ", auditUserName="
				+ auditUserName + ", auditReason=" + auditReason + ", auditResult=" + auditResult + ", type=" + type
				+ ", auditAt=" + auditAt + ", isDeleted=" + isDeleted + ", suggestType=" + suggestType + ", auditField="
				+ auditField + ", auditContent=" + auditContent + ", auditFieldId=" + auditFieldId + ", auditFieldName="
				+ auditFieldName + "]";
	}
	
}