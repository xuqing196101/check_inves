package ses.model.ems;

import java.util.Date;

public class ProExtSupervise {
	
	
    public ProExtSupervise() {
		super();
	}

	public ProExtSupervise(String projectId) {
		super();
		this.projectId = projectId;
	}

	/**
     * <pre>
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目ID
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 专家抽取记录ID
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     */
    private String expertExtractRecordId;

    /**
     * <pre>
     * 监督人员ID
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.SUPVISE_ID
     * </pre>
     */
    private String supviseId;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间
     * 表字段 : T_SES_EMS_PRO_EXT_SUPERVISE.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;
    
    private String relName;
    
    private String company;
    
    private String phone;
    
    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.ID
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_PRO_EXT_SUPERVISE.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.PROJECT_ID
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.PROJECT_ID：项目ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_EMS_PRO_EXT_SUPERVISE.PROJECT_ID：项目ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：专家抽取记录ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.EXPERT_EXTRACT_RECORD_ID：专家抽取记录ID
     */
    public String getExpertExtractRecordId() {
        return expertExtractRecordId;
    }

    /**
     * <pre>
     * 设置：专家抽取记录ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.EXPERT_EXTRACT_RECORD_ID
     * </pre>
     *
     * @param expertExtractRecordId
     *            T_SES_EMS_PRO_EXT_SUPERVISE.EXPERT_EXTRACT_RECORD_ID：专家抽取记录ID
     */
    public void setExpertExtractRecordId(String expertExtractRecordId) {
        this.expertExtractRecordId = expertExtractRecordId == null ? null : expertExtractRecordId.trim();
    }

    /**
     * <pre>
     * 获取：监督人员ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.SUPVISE_ID
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.SUPVISE_ID：监督人员ID
     */
    public String getSupviseId() {
        return supviseId;
    }

    /**
     * <pre>
     * 设置：监督人员ID
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.SUPVISE_ID
     * </pre>
     *
     * @param supviseId
     *            T_SES_EMS_PRO_EXT_SUPERVISE.SUPVISE_ID：监督人员ID
     */
    public void setSupviseId(String supviseId) {
        this.supviseId = supviseId == null ? null : supviseId.trim();
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.CREATED_AT
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_EMS_PRO_EXT_SUPERVISE.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.UPDATED_AT
     * </pre>
     *
     * @return T_SES_EMS_PRO_EXT_SUPERVISE.UPDATED_AT：更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间
     * 表字段：T_SES_EMS_PRO_EXT_SUPERVISE.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_EMS_PRO_EXT_SUPERVISE.UPDATED_AT：更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return Returns the relName.
     */
    public String getRelName() {
        return relName;
    }

    /**
     * @param relName The relName to set.
     */
    public void setRelName(String relName) {
        this.relName = relName;
    }

    /**
     * @return Returns the company.
     */
    public String getCompany() {
        return company;
    }

    /**
     * @param company The company to set.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * @return Returns the phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}