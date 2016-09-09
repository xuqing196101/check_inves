package ses.model.bms;

import java.util.Date;

import ses.util.PropertiesUtil;

public class StationMessage {
    /**
     * <pre>
     * 表字段 : T_SES_BMS_STATION_MESSAGE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段 : T_SES_BMS_STATION_MESSAGE.OPERATOR_ID
     * </pre>
     */
    private String operatorId;

    /**
     * <pre>
     * 标题
     * 表字段 : T_SES_BMS_STATION_MESSAGE.TITLE
     * </pre>
     */
    private String title;

    /**
     * <pre>
     * 内容
     * 表字段 : T_SES_BMS_STATION_MESSAGE.CONTEXT
     * </pre>
     */
    private String context;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_SES_BMS_STATION_MESSAGE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 是否删除 0不删除 1删除
     * 表字段 : T_SES_BMS_STATION_MESSAGE.IS_DELETED
     * </pre>
     */
    private Short isDeleted;

    /**
     * <pre>
     * 是否发布 0不发布 1发布
     * 表字段 : T_SES_BMS_STATION_MESSAGE.IS_ISSUANCE
     * </pre>
     */
    private Short isIssuance;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_BMS_STATION_MESSAGE.ID
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_BMS_STATION_MESSAGE.ID
     * </pre>
     *
     * @param id
     *            T_SES_BMS_STATION_MESSAGE.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_BMS_STATION_MESSAGE.OPERATOR_ID
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.OPERATOR_ID：操作人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <pre>
     * 设置：操作人ID T_SES_OMS_DEMAND_DEP_USER
     * 表字段：T_SES_BMS_STATION_MESSAGE.OPERATOR_ID
     * </pre>
     *
     * @param operatorId
     *            T_SES_BMS_STATION_MESSAGE.OPERATOR_ID：操作人ID T_SES_OMS_DEMAND_DEP_USER
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId == null ? null : operatorId.trim();
    }

    /**
     * <pre>
     * 获取：标题
     * 表字段：T_SES_BMS_STATION_MESSAGE.TITLE
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.TITLE：标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * <pre>
     * 设置：标题
     * 表字段：T_SES_BMS_STATION_MESSAGE.TITLE
     * </pre>
     *
     * @param title
     *            T_SES_BMS_STATION_MESSAGE.TITLE：标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * <pre>
     * 获取：内容
     * 表字段：T_SES_BMS_STATION_MESSAGE.CONTEXT
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.CONTEXT：内容
     */
    public String getContext() {
        return context;
    }

    /**
     * <pre>
     * 设置：内容
     * 表字段：T_SES_BMS_STATION_MESSAGE.CONTEXT
     * </pre>
     *
     * @param context
     *            T_SES_BMS_STATION_MESSAGE.CONTEXT：内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_SES_BMS_STATION_MESSAGE.CREATED_AT
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt==null||"".equals(createdAt)?new Date():createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_SES_BMS_STATION_MESSAGE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_BMS_STATION_MESSAGE.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：是否删除 0不删除 1删除
     * 表字段：T_SES_BMS_STATION_MESSAGE.IS_DELETED
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.IS_DELETED：是否删除 0不删除 1删除
     */
    public Short getIsDeleted() {
        return isDeleted==null?0:isDeleted;
    }

    /**
     * <pre>
     * 设置：是否删除 0不删除 1删除
     * 表字段：T_SES_BMS_STATION_MESSAGE.IS_DELETED
     * </pre>
     *
     * @param isDeleted
     *            T_SES_BMS_STATION_MESSAGE.IS_DELETED：是否删除 0不删除 1删除
     */
    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * <pre>
     * 获取：是否发布 0不发布 1发布
     * 表字段：T_SES_BMS_STATION_MESSAGE.IS_ISSUANCE
     * </pre>
     *
     * @return T_SES_BMS_STATION_MESSAGE.IS_ISSUANCE：是否发布 0不发布 1发布
     */
    public Short getIsIssuance() {
        return isIssuance==null?0:isIssuance;
    }

    /**
     * <pre>
     * 设置：是否发布 0不发布 1发布
     * 表字段：T_SES_BMS_STATION_MESSAGE.IS_ISSUANCE
     * </pre>
     *
     * @param isIssuance
     *            T_SES_BMS_STATION_MESSAGE.IS_ISSUANCE：是否发布 0不发布 1发布
     */
    public void setIsIssuance(Short isIssuance) {
        this.isIssuance = isIssuance;
    }


	//--------------手写------------------------
	
	/**
	 * 	 页码
	 */
	private Integer pageNum;
	
	/**
	 * 每页显示的数量
	 */
	private Integer pageSize;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		if(pageSize==null||pageSize==0){
			PropertiesUtil config = new PropertiesUtil("config.properties");	
			pageSize=Integer.parseInt(config.getString("pageSize"));
		}
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public StationMessage() {
		super();
	}
	/**
	 * 软删除
	 * @param id
	 * @param isDeleted
	 */
	public StationMessage(String id, Short isDeleted) {
		super();
		this.id = id;
		this.isDeleted = isDeleted;
	}
 
	/**
	 *  是否发布  0不发布 1发布
	 * @param id
	 * @param isIssuance
	 * @param str 无效参数
	 */
	public StationMessage(String id, Short isIssuance,String str) {
		super();
		this.id = id;
		this.isIssuance = isIssuance;
		str=null;
	}
	/**
	 * 
	 * @param pageNum 页码
	 * @param pageSize 每页显示的数量
	 */
	public StationMessage(Integer pageNum, Integer pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}
	
	
    
}