package ses.model.sms;

import java.util.Date;

public class SupplierExtRelate {
	
	
    public SupplierExtRelate() {
		super();
	}

	public SupplierExtRelate(String id, Short operatingType, String reason) {
		super();
		this.id = id;
		this.operatingType = operatingType;
		this.reason = reason;
	}

	public SupplierExtRelate(String id, Short operatingType) {
		super();
		this.id = id;
		this.operatingType = operatingType;
	}

	public SupplierExtRelate(String supplierConditionId) {
		super();
		this.supplierConditionId = supplierConditionId;
	}

	/**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 供应商id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 抽取条件id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID
     * </pre>
     */
    private String supplierConditionId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 操作类型1.同意 2.待定 3拒绝
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.OPERATING_TYPE
     * </pre>
     */
    private Short operatingType;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 不参加原因
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.REASON
     * </pre>
     */
    private String reason;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 更新
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.UPDATED_AT
     * </pre>
     */
    private String updatedAt;

    
    private Supplier supplier;
    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：供应商id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID：供应商id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID：供应商id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：抽取条件id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID：抽取条件id
     */
    public String getSupplierConditionId() {
        return supplierConditionId;
    }

    /**
     * <pre>
     * 设置：抽取条件id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID
     * </pre>
     *
     * @param supplierConditionId
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID：抽取条件id
     */
    public void setSupplierConditionId(String supplierConditionId) {
        this.supplierConditionId = supplierConditionId == null ? null : supplierConditionId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.CREATED_AT：null
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.CREATED_AT：null
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：操作类型1.同意 2.待定 3拒绝
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.OPERATING_TYPE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.OPERATING_TYPE：操作类型1.同意 2.待定 3拒绝
     */
    public Short getOperatingType() {
        return operatingType;
    }

    /**
     * <pre>
     * 设置：操作类型1.同意 2.待定 3拒绝
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.OPERATING_TYPE
     * </pre>
     *
     * @param operatingType
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.OPERATING_TYPE：操作类型1.同意 2.待定 3拒绝
     */
    public void setOperatingType(Short operatingType) {
        this.operatingType = operatingType;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.IS_DELETED
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.IS_DELETED：null
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.IS_DELETED：null
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：不参加原因
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.REASON
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.REASON：不参加原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * <pre>
     * 设置：不参加原因
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.REASON
     * </pre>
     *
     * @param reason
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.REASON：不参加原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.PROJECT_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：更新
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.UPDATED_AT：更新
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.UPDATED_AT：更新
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt == null ? null : updatedAt.trim();
    }

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
    
    
}