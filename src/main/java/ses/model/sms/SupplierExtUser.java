package ses.model.sms;

import java.util.Date;

public class SupplierExtUser {
	
	
    public SupplierExtUser() {
		super();
	}

	public SupplierExtUser(String projectId) {
		super();
		this.projectId = projectId;
	}

	/**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 供应商抽取记录id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     */
    private String extractsId;

    /**
     * <pre>
     * 监督人id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     */
    private String userId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_EXT_USER.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：供应商抽取记录id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID：供应商抽取记录id
     */
    public String getExtractsId() {
        return extractsId;
    }

    /**
     * <pre>
     * 设置：供应商抽取记录id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     *
     * @param extractsId
     *            T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID：供应商抽取记录id
     */
    public void setExtractsId(String extractsId) {
        this.extractsId = extractsId == null ? null : extractsId.trim();
    }

    /**
     * <pre>
     * 获取：监督人id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.USER_ID：监督人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * <pre>
     * 设置：监督人id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     *
     * @param userId
     *            T_SES_SMS_SUPPLIER_EXT_USER.USER_ID：监督人id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT：null
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT：null
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.PROJECT_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_SMS_SUPPLIER_EXT_USER.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
}