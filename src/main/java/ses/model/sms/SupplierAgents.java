package ses.model.sms;

import java.util.Date;

public class SupplierAgents {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.OPERATOR_ID
     * </pre>
     */
    private String operatorId;

    /**
     * <pre>
     * 代办人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.USERS_ID
     * </pre>
     */
    private String usersId;

    /**
     * <pre>
     * 供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 代办类型 
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.UNDO_TYPE
     * </pre>
     */
    private Short undoType;

    /**
     * <pre>
     * 结果
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.RESULT
     * </pre>
     */
    private Long result;

    /**
     * <pre>
     * 标题
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.TITLE
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 备注
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.REMARK
     * </pre>
     */
    private String remark;

    /**
     * <pre>
     * 逻辑删除
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 创建时间 格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 是否为催办, 0不是 1是
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.IS_REMINDERS
     * </pre>
     */
    private Short isReminders;
    
    /**
     * <pre>
     * 待办状态 0 未审核 1 已审核 2 审核中
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.UNDO_STATUS
     * </pre>
     */
    private Short undoStatus;
    
    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_AGENTS.CONTENT
     * </pre>
     */
    private String content;

    /**
     * @Fields usersName : 代办人姓名
     */
    private String usersName;
    
    /**
     * @Fields operatorName : 操作人姓名
     */
    private String operatorName;
    
    /**
     * @Fields supplierName : 供应商名称
     */
    private String supplierName;
    
    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_AGENTS.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.OPERATOR_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.OPERATOR_ID：操作人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <pre>
     * 设置：操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.OPERATOR_ID
     * </pre>
     *
     * @param operatorId
     *            T_SES_SMS_SUPPLIER_AGENTS.OPERATOR_ID：操作人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    /**
     * <pre>
     * 获取：代办人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.USERS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.USERS_ID：代办人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public String getUsersId() {
        return usersId;
    }

    /**
     * <pre>
     * 设置：代办人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.USERS_ID
     * </pre>
     *
     * @param usersId
     *            T_SES_SMS_SUPPLIER_AGENTS.USERS_ID：代办人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public void setUsersId(String usersId) {
        this.usersId = usersId == null ? null : usersId.trim();
    }

    /**
     * <pre>
     * 获取：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.SUPPLIER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_SES_SMS_SUPPLIER_AGENTS.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：代办类型 0 未审核 1 已审核 2 审核中
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.UNDO_TYPE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.UNDO_TYPE：代办类型 0 未审核 1 已审核 2 审核中
     */
    public Short getUndoType() {
        return undoType;
    }

    /**
     * <pre>
     * 设置：代办类型 0 未审核 1 已审核 2 审核中
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.UNDO_TYPE
     * </pre>
     *
     * @param undoType
     *            T_SES_SMS_SUPPLIER_AGENTS.UNDO_TYPE：代办类型 0 未审核 1 已审核 2 审核中
     */
    public void setUndoType(Short undoType) {
        this.undoType = undoType;
    }

    /**
     * <pre>
     * 获取：结果
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.RESULT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.RESULT：结果
     */
    public Long getResult() {
        return result;
    }

    /**
     * <pre>
     * 设置：结果
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.RESULT
     * </pre>
     *
     * @param result
     *            T_SES_SMS_SUPPLIER_AGENTS.RESULT：结果
     */
    public void setResult(Long result) {
        this.result = result;
    }

    /**
     * <pre>
     * 获取：标题
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.TITLE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.TITLE：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * <pre>
     * 设置：标题
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.TITLE
     * </pre>
     *
     * @param title
     *            T_SES_SMS_SUPPLIER_AGENTS.TITLE：标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * <pre>
     * 获取：备注
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.REMARK
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.REMARK：备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <pre>
     * 设置：备注
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.REMARK
     * </pre>
     *
     * @param remark
     *            T_SES_SMS_SUPPLIER_AGENTS.REMARK：备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * <pre>
     * 获取：逻辑删除
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.IS_DELETED
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.IS_DELETED：逻辑删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：逻辑删除
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_SMS_SUPPLIER_AGENTS.IS_DELETED：逻辑删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.CREATED_AT：创建时间 格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_AGENTS.CREATED_AT：创建时间 格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.UPDATED_AT：更新时间格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_AGENTS.UPDATED_AT：更新时间格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Short getIsReminders() {
		return isReminders;
	}

	public void setIsReminders(Short isReminders) {
		this.isReminders = isReminders;
	}
 
	
	public Short getUndoStatus() {
		return undoStatus;
	}
	public void setUndoStatus(Short undoStatus) {
		this.undoStatus = undoStatus;
	}

	/**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.CONTENT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_AGENTS.CONTENT：null
     */
    public String getContent() {
        return content;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_AGENTS.CONTENT
     * </pre>
     *
     * @param content
     *            T_SES_SMS_SUPPLIER_AGENTS.CONTENT：null
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getUsersName() {
		return usersName;
	}

	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}


	
	
   /**
    * 软删除
    * @param id
    * @param isDeleted 1删除
    */
	public SupplierAgents(String id, Short isDeleted) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
	}
	/**
	 * 代办|催办 区分
	 * @param isReminders
	 */
	public SupplierAgents(Short isReminders) {
		super();
		this.isReminders = isReminders;
	}
	

	/**
	 * 修改代办为催办
	 * @param id 
	 * @param updatedAt 修改时间
	 * @param isReminders 是否为催办 0不是 1是
	 */
	public SupplierAgents(String id, Date updatedAt, Short isReminders) {
		super();
		this.id = id;
		this.updatedAt = updatedAt;
		this.isReminders = isReminders;
	}
	public SupplierAgents() {
		
	}

	
    
}