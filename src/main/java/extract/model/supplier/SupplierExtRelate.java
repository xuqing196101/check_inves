package extract.model.supplier;

import java.util.Date;
import java.util.List;

import ses.model.sms.Supplier;
import ses.util.MyAnnotation;

public class SupplierExtRelate {
    
    /**
     * 供应商id
     */
	private String supplierId;
	
	/**
	 * 条件id
	 */
	private String conditionId;
	
	/**
	 * 记录id
	 */
	private String recordId;
	
	
	/**
	 * 结果id
	 */
	private String id;
	
	/**
	 * 是否参加
	 */
	private Short attend;
	
	/**
	 * 不参加理由
	 */
	private String reason;
	
	/**
	 * 供应商类型代码
	 */
	private String reviewType;
	
	/**
	 * 反馈时间
	 */
	private Date createdAt;


	
    public SupplierExtRelate() {
        super();
    }

    public SupplierExtRelate(String id, Short operatingType, String reason,String[] packageId) {
        super();
        this.id = id;
        this.operatingType = operatingType;
        this.reason = reason;
        this.packageId = packageId;
    }

    public SupplierExtRelate(String id, Short operatingType,String[] packageId) {
        super();
        this.id = id;
        this.operatingType = operatingType;
        this.packageId = packageId;
    }

    public SupplierExtRelate(String supplierConditionId) {
        super();
        this.supplierConditionId = supplierConditionId;
    }

    private Supplier supplier;
    
    private List<SupplierConType> conType;
    
    private  String[] packageId;
    
    /**
     * 查询返回参数 供应商类型
     */
    private String supplierTypeId;
    
    
    /**
     * 查询返回参数 供应商类型名称
     */
    private String supplierTypeName;

    /**
     * @return Returns the supplierTypeId.
     */
    public String getSupplierTypeId() {
      return supplierTypeId;
    }

    /**
     * @param supplierTypeId The supplierTypeId to set.
     */
    public void setSupplierTypeId(String supplierTypeId) {
      this.supplierTypeId = supplierTypeId;
    }

    /**
     * <pre>
     * 供应商级别
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     */
    private String levelTypeId;
    
    /**
     * <pre>
     * 供应商级别
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     */
    private String levelTypeName;
    
    /**
	 * <pre>
	 * 联系人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_NAME
	 * </pre>
	 */
	@MyAnnotation("注册联系人姓名")
	private String contactName;
	
	/**
	 * <pre>
	 * 联系电话
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_TELEPHONE
	 * </pre>
	 */
	@MyAnnotation("注册联系人手机号")
	private String contactTelephone;
	
	/**
	 * <pre>
	 * 联系人手机
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_MOBILE
	 * </pre>
	 */
	@MyAnnotation("注册联系人固定电话")
	private String contactMobile;
	
    /**
     * <pre>
     * 供应商名称
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_ID
     * </pre>
     */
    private String supplierName;

    /**
     * <pre>
     * 抽取条件id
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.SUPPLIER_CONDITION_ID
     * </pre>
     */
    private String supplierConditionId;

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

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.CON_TYPE_ID
     * </pre>
     */
    private String conTypeId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_RELATE.STATUS_COUNT
     * </pre>
     */
    private Short statusCount;
    

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

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.CON_TYPE_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.CON_TYPE_ID：null
     */
    public String getConTypeId() {
        return conTypeId;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.CON_TYPE_ID
     * </pre>
     *
     * @param conTypeId
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.CON_TYPE_ID：null
     */
    public void setConTypeId(String conTypeId) {
        this.conTypeId = conTypeId == null ? null : conTypeId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.STATUS_COUNT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_RELATE.STATUS_COUNT：null
     */
    public Short getStatusCount() {
        return statusCount;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_RELATE.STATUS_COUNT
     * </pre>
     *
     * @param statusCount
     *            T_SES_SMS_SUPPLIER_EXT_RELATE.STATUS_COUNT：null
     */
    public void setStatusCount(Short statusCount) {
        this.statusCount = statusCount;
    }

    /**
     * @return Returns the supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier The supplier to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return Returns the conType.
     */
    public List<SupplierConType> getConType() {
        return conType;
    }

    /**
     * @param conType The conType to set.
     */
    public void setConType(List<SupplierConType> conType) {
        this.conType = conType;
    }

    /**
     * @return Returns the reviewType.
     */
    public String getReviewType() {
        return reviewType;
    }

    /**
     * @param reviewType The reviewType to set.
     */
    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    /**
     * @return Returns the packageId.
     */
    public String[] getPackageId() {
      return packageId;
    }

    /**
     * @param packageId The packageId to set.
     */
    public void setPackageId(String[] packageId) {
      this.packageId = packageId;
    }

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierTypeName() {
		return supplierTypeName;
	}

	public void setSupplierTypeName(String supplierTypeName) {
		this.supplierTypeName = supplierTypeName;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}


	public Short getAttend() {
		return attend;
	}

	public void setAttend(Short attend) {
		this.attend = attend;
	}

	public String getLevelTypeId() {
		return levelTypeId;
	}

	public void setLevelTypeId(String levelTypeId) {
		this.levelTypeId = levelTypeId;
	}

	public String getLevelTypeName() {
		return levelTypeName;
	}

	public void setLevelTypeName(String levelTypeName) {
		this.levelTypeName = levelTypeName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
}