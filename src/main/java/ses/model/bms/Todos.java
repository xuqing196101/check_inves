package ses.model.bms;

import java.sql.Timestamp;
import java.util.Date;

public class Todos {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_BMS_TODOS.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 创建日期 格式年月日时分秒
     * 表字段 : T_SES_BMS_TODOS.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 待办名称
     * 表字段 : T_SES_BMS_TODOS.NAME
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 删除标识 0：未删除，1：删除
     * 表字段 : T_SES_BMS_TODOS.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 执行路径
     * 表字段 : T_SES_BMS_TODOS.URL
     * </pre>
     */
    private String url;

    /**
     * <pre>
     * 完成标识 0：未完成，1：已完成
     * 表字段 : T_SES_BMS_TODOS.IS_FINISH
     * </pre>
     */
    private Short isFinish;

    /**
     * <pre>
     * 表单名称 表示关联的实体对象名称
     * 表字段 : T_SES_BMS_TODOS.CLASS_NAME
     * </pre>
     */
    private String className;

    /**
     * <pre>
     * 表单id
     * 表字段 : T_SES_BMS_TODOS.CLASS_ID
     * </pre>
     */
    private Long classId;

    /**
     * <pre>
     * 待办类型 1.供应商待办，2专家待办
     * 表字段 : T_SES_BMS_TODOS.UNDO_TYPE
     * </pre>
     */
    private Short undoType;

    /**
     * <pre>
     * 发送人ID
     * 表字段 : T_SES_BMS_TODOS.SENDER_ID
     * </pre>
     */
    private String senderId;

    /**
     * <pre>
     * 接收人ID
     * 表字段 : T_SES_BMS_TODOS.RECEIVER_ID
     * </pre>
     */
    private String receiverId;

    /**
     * <pre>
     * 权限id
     * 表字段 : T_SES_BMS_TODOS.POWER_ID
     * </pre>
     */
    private String powerId;

    /**
     * <pre>
     * 机构id
     * 表字段 : T_SES_BMS_TODOS.ORG_ID
     * </pre>
     */
    private String orgId;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_BMS_TODOS.ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.ID：主键
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_BMS_TODOS.ID
     * </pre>
     *
     * @param id
     *            T_SES_BMS_TODOS.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：创建日期 格式年月日时分秒
     * 表字段：T_SES_BMS_TODOS.CREATED_AT
     * </pre>
     *
     * @return T_SES_BMS_TODOS.CREATED_AT：创建日期 格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建日期 格式年月日时分秒
     * 表字段：T_SES_BMS_TODOS.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_BMS_TODOS.CREATED_AT：创建日期 格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：待办名称
     * 表字段：T_SES_BMS_TODOS.NAME
     * </pre>
     *
     * @return T_SES_BMS_TODOS.NAME：待办名称
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 设置：待办名称
     * 表字段：T_SES_BMS_TODOS.NAME
     * </pre>
     *
     * @param name
     *            T_SES_BMS_TODOS.NAME：待办名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * <pre>
     * 获取：删除标识 0：未删除，1：删除
     * 表字段：T_SES_BMS_TODOS.IS_DELETED
     * </pre>
     *
     * @return T_SES_BMS_TODOS.IS_DELETED：删除标识 0：未删除，1：删除
     */
    public Short getIsDeleted() {
        return isDeleted;
    }

    /**
     * <pre>
     * 设置：删除标识 0：未删除，1：删除
     * 表字段：T_SES_BMS_TODOS.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_BMS_TODOS.IS_DELETED：删除标识 0：未删除，1：删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：执行路径
     * 表字段：T_SES_BMS_TODOS.URL
     * </pre>
     *
     * @return T_SES_BMS_TODOS.URL：执行路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * <pre>
     * 设置：执行路径
     * 表字段：T_SES_BMS_TODOS.URL
     * </pre>
     *
     * @param url
     *            T_SES_BMS_TODOS.URL：执行路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * <pre>
     * 获取：完成标识 0：未完成，1：已完成
     * 表字段：T_SES_BMS_TODOS.IS_FINISH
     * </pre>
     *
     * @return T_SES_BMS_TODOS.IS_FINISH：完成标识 0：未完成，1：已完成
     */
    public Short getIsFinish() {
        return isFinish;
    }

    /**
     * <pre>
     * 设置：完成标识 0：未完成，1：已完成
     * 表字段：T_SES_BMS_TODOS.IS_FINISH
     * </pre>
     *
     * @param isFinish
     *            T_SES_BMS_TODOS.IS_FINISH：完成标识 0：未完成，1：已完成
     */
    public void setIsFinish(Short isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * <pre>
     * 获取：表单名称 表示关联的实体对象名称
     * 表字段：T_SES_BMS_TODOS.CLASS_NAME
     * </pre>
     *
     * @return T_SES_BMS_TODOS.CLASS_NAME：表单名称 表示关联的实体对象名称
     */
    public String getClassName() {
        return className;
    }

    /**
     * <pre>
     * 设置：表单名称 表示关联的实体对象名称
     * 表字段：T_SES_BMS_TODOS.CLASS_NAME
     * </pre>
     *
     * @param className
     *            T_SES_BMS_TODOS.CLASS_NAME：表单名称 表示关联的实体对象名称
     */
    public void setClassName(String className) {
        this.className = className == null ? null : className.trim();
    }

    /**
     * <pre>
     * 获取：表单id
     * 表字段：T_SES_BMS_TODOS.CLASS_ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.CLASS_ID：表单id
     */
    public Long getClassId() {
        return classId;
    }

    /**
     * <pre>
     * 设置：表单id
     * 表字段：T_SES_BMS_TODOS.CLASS_ID
     * </pre>
     *
     * @param classId
     *            T_SES_BMS_TODOS.CLASS_ID：表单id
     */
    public void setClassId(Long classId) {
        this.classId = classId;
    }

    /**
     * <pre>
     * 获取：待办类型 1.供应商待办，2专家待办
     * 表字段：T_SES_BMS_TODOS.UNDO_TYPE
     * </pre>
     *
     * @return T_SES_BMS_TODOS.UNDO_TYPE：待办类型 1.供应商待办，2专家待办
     */
    public Short getUndoType() {
        return undoType;
    }

    /**
     * <pre>
     * 设置：待办类型 1.供应商待办，2专家待办
     * 表字段：T_SES_BMS_TODOS.UNDO_TYPE
     * </pre>
     *
     * @param undoType
     *            T_SES_BMS_TODOS.UNDO_TYPE：待办类型 1.供应商待办，2专家待办
     */
    public void setUndoType(Short undoType) {
        this.undoType = undoType;
    }

    /**
     * <pre>
     * 获取：发送人ID
     * 表字段：T_SES_BMS_TODOS.SENDER_ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.SENDER_ID：发送人ID
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * <pre>
     * 设置：发送人ID
     * 表字段：T_SES_BMS_TODOS.SENDER_ID
     * </pre>
     *
     * @param senderId
     *            T_SES_BMS_TODOS.SENDER_ID：发送人ID
     */
    public void setSenderId(String senderId) {
        this.senderId = senderId == null ? null : senderId.trim();
    }

    /**
     * <pre>
     * 获取：接收人ID
     * 表字段：T_SES_BMS_TODOS.RECEIVER_ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.RECEIVER_ID：接收人ID
     */
    public String getReceiverId() {
        return receiverId;
    }

    /**
     * <pre>
     * 设置：接收人ID
     * 表字段：T_SES_BMS_TODOS.RECEIVER_ID
     * </pre>
     *
     * @param receiverId
     *            T_SES_BMS_TODOS.RECEIVER_ID：接收人ID
     */
    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId == null ? null : receiverId.trim();
    }

    /**
     * <pre>
     * 获取：权限id
     * 表字段：T_SES_BMS_TODOS.POWER_ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.POWER_ID：权限id
     */
    public String getPowerId() {
        return powerId;
    }

    /**
     * <pre>
     * 设置：权限id
     * 表字段：T_SES_BMS_TODOS.POWER_ID
     * </pre>
     *
     * @param powerId
     *            T_SES_BMS_TODOS.POWER_ID：权限id
     */
    public void setPowerId(String powerId) {
        this.powerId = powerId == null ? null : powerId.trim();
    }

    /**
     * <pre>
     * 获取：机构id
     * 表字段：T_SES_BMS_TODOS.ORG_ID
     * </pre>
     *
     * @return T_SES_BMS_TODOS.ORG_ID：机构id
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * <pre>
     * 设置：机构id
     * 表字段：T_SES_BMS_TODOS.ORG_ID
     * </pre>
     *
     * @param orgId
     *            T_SES_BMS_TODOS.ORG_ID：机构id
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
	//-------------------------------------------------------------------
	
	/**
	 * 接收人
	 */
	private String receiverName;
	/**
	 * 发送人
	 */
	private String senderName;
	

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Todos() {
		super();
	}

	public Todos(Short isFinish) {
		super();
		this.isFinish = isFinish;
	}
	
	
	public Todos(String url) {
		super();
		this.url = url;
	}

    /**
     * 根据类型获取待办集合
     * @param isFinish
     * @param undoType
     */
	public Todos(Short isFinish, Short undoType) {
		super();
		this.isFinish = isFinish;
		this.undoType = undoType;
	}

    public Todos(Short isFinish, Short undoType, String receiverId,  String orgId) {
        super();
        this.isFinish = isFinish;
        this.undoType = undoType;
        this.receiverId = receiverId;
        this.orgId = orgId;
    }

	public Todos(Short isFinish, Short undoType, String receiverId) {
		super();
		this.isFinish = isFinish;
		this.undoType = undoType;
		this.receiverId = receiverId;
	}
	
}