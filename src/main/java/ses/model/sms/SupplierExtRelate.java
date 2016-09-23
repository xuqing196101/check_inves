package ses.model.sms;

import java.util.Date;

public class SupplierExtRelate {
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
     * 供应商集合
     */
    private Supplier suppliers; 
    /**
     * <pre>
     * 抽取id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.EXTRACTS_ID
     * </pre>
     */
    private String extractsId;

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

    
    
 
	public Supplier getSuppliers() {
		return suppliers;
	}

	public void setSuppliers(Supplier suppliers) {
		this.suppliers = suppliers;
	}

	/**
     * <pre>
     * 获取：抽取id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.EXTRACTS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.EXTRACTS_ID：抽取id
     */
    public String getExtractsId() {
        return extractsId;
    }

    /**
     * <pre>
     * 设置：抽取id
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.EXTRACTS_ID
     * </pre>
     *
     * @param extractsId
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.EXTRACTS_ID：抽取id
     */
    public void setExtractsId(String extractsId) {
        this.extractsId = extractsId == null ? null : extractsId.trim();
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
        return isDeleted==null?new Short("0"):isDeleted;
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

	public SupplierExtRelate() {
		super();
	}

	/**
	 * 修改操作状态
	 * @param id
	 * @param operatingType
	 */
	public SupplierExtRelate(String id, Short operatingType) {
		super();
		this.id = id;
		this.operatingType = operatingType;
	}

	public SupplierExtRelate(String supplierId, String extractsId) {
		super();
		this.supplierId = supplierId;
		this.extractsId = extractsId;
	}
	

}