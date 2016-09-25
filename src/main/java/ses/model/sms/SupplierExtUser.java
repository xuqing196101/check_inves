package ses.model.sms;

import ses.model.bms.User;

public class SupplierExtUser {
    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     */
    private String extractsId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     */
    private String userId;
    
    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     */
    private User user;

    /**
     * <pre>
     * sysdate
     * 表字段 : T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     */
    private String createdAt;

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
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID：null
     */
    public String getExtractsId() {
        return extractsId;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID
     * </pre>
     *
     * @param extractsId
     *            T_SES_SMS_SUPPLIER_EXT_USER.EXTRACTS_ID：null
     */
    public void setExtractsId(String extractsId) {
        this.extractsId = extractsId == null ? null : extractsId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.USER_ID：null
     */
    public String getUserId() {
        return userId;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.USER_ID
     * </pre>
     *
     * @param userId
     *            T_SES_SMS_SUPPLIER_EXT_USER.USER_ID：null
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * <pre>
     * 获取：sysdate
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT：sysdate
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：sysdate
     * 表字段：T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_EXT_USER.CREATED_AT：sysdate
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt == null ? null : createdAt.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}