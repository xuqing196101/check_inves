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
    private String isHistory;
    //审核时间
    private Date auditAt;
    //是否删除0否  1是
    private Long isDelete;
    //审批类型
    private String suggestType;
    //审核字段
    private String auditField;
    //审批内容
    private String auditContent;
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

    public String getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory == null ? null : isHistory.trim();
    }

    public Long getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Long isDelete) {
        this.isDelete = isDelete;
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

	public void setAuditField(String auditField) {
		this.auditField = auditField;
	}

	public String getAuditContent() {
		return auditContent;
	}

	public void setAuditContent(String auditContent) {
		this.auditContent = auditContent;
	}
    
    
}