package ses.model.ems;

import java.util.Date;
import java.util.List;

public class ProjectExtract {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目ID
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 专家抽取记录ID
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     */
    private String expertExtractRecordId;

    /**
     * <pre>
     * 专家ID
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID
     * </pre>
     */
    private String expertId;
    
    /**
     * <pre>
     * 专家集合
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID
     * </pre>
     */
    private Expert expert;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.OPERATING_TYPE
     * </pre>
     */
    private Short operatingType;

    /**
     * <pre>
     * 不参加原因
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.REASON
     * </pre>
     */
    private String reason;

    /**
     * <pre>
     * 表字段 : T_SES_EMS_PROJECT_EXTRACT.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.ID
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_PROJECT_EXTRACT.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.PROJECT_ID
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.PROJECT_ID：项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_EMS_PROJECT_EXTRACT.PROJECT_ID：项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：专家抽取记录ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.EXPERT_EXTRACT_RECORD_ID：专家抽取记录ID
     */
    public String getExpertExtractRecordId() {
        return expertExtractRecordId;
    }

    /**
     * <pre>
     * 设置：专家抽取记录ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     *
     * @param expertExtractRecordId
     *            T_SES_EMS_PROJECT_EXTRACT.EXPERT_EXTRACT_RECORD_ID：专家抽取记录ID
     */
    public void setExpertExtractRecordId(String expertExtractRecordId) {
        this.expertExtractRecordId = expertExtractRecordId == null ? null : expertExtractRecordId.trim();
    }

    /**
     * <pre>
     * 获取：专家ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID：专家ID
     */
    public String getExpertId() {
        return expertId;
    }

    /**
     * <pre>
     * 设置：专家ID
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID
     * </pre>
     *
     * @param expertId
     *            T_SES_EMS_PROJECT_EXTRACT.EXPERT_ID：专家ID
     */
    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.CREATED_AT
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_EMS_PROJECT_EXTRACT.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.UPDATED_AT
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.UPDATED_AT：更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_EMS_PROJECT_EXTRACT.UPDATED_AT：更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.OPERATING_TYPE
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.OPERATING_TYPE：null
     */
    public Short getOperatingType() {
        return operatingType;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.OPERATING_TYPE
     * </pre>
     *
     * @param operatingType
     *            T_SES_EMS_PROJECT_EXTRACT.OPERATING_TYPE：null
     */
    public void setOperatingType(Short operatingType) {
        this.operatingType = operatingType;
    }

    /**
     * <pre>
     * 获取：不参加原因
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.REASON
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.REASON：不参加原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * <pre>
     * 设置：不参加原因
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.REASON
     * </pre>
     *
     * @param reason
     *            T_SES_EMS_PROJECT_EXTRACT.REASON：不参加原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.IS_DELETED
     * </pre>
     *
     * @return T_SES_EMS_PROJECT_EXTRACT.IS_DELETED：null
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_PROJECT_EXTRACT.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_EMS_PROJECT_EXTRACT.IS_DELETED：null
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }


	public ProjectExtract(String expertExtractRecordId) {
		super();
		this.expertExtractRecordId = expertExtractRecordId;
	}

	public ProjectExtract() {
		super();
	}

	public ProjectExtract(String id, Short operatingType) {
		super();
		this.id = id;
		this.operatingType = operatingType;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

    
    
}