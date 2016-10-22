package ses.model.sms;

import java.math.BigDecimal;
import java.util.Date;

public class SupplierConType {
    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 供应商类型
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_TYPE_ID
     * </pre>
     */
    private String supplieTypeId;

    /**
     * <pre>
     * 抽取数量
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_COUNT
     * </pre>
     */
    private Integer supplieCount;

    /**
     * <pre>
     * 执行资格
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_QUALIFICATION
     * </pre>
     */
    private String supplieQualification;

    /**
     * <pre>
     * 品目id
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_ID
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * 品目name
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_NAME
     * </pre>
     */
    private String categoryName;

    /**
     * <pre>
     * 条件表id
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.CONDITION_ID
     * </pre>
     */
    private String conditionId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.IS_DELETE
     * </pre>
     */
    private Short isDelete;

    /**
     * <pre>
     * 是否满足多个条件1满足多个 2不满足
     * 表字段 : T_SES_SMS_SUPPLIE_CON_TYPE.IS_MULTICONDITION
     * </pre>
     */
    private Short isMulticondition;

    
    private Integer expertsTypeId;
    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIE_CON_TYPE.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：供应商类型
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_TYPE_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_TYPE_ID：供应商类型
     */
    public String getSupplieTypeId() {
        return supplieTypeId;
    }

    /**
     * <pre>
     * 设置：供应商类型
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_TYPE_ID
     * </pre>
     *
     * @param supplieTypeId
     *            T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_TYPE_ID：供应商类型
     */
    public void setSupplieTypeId(String supplieTypeId) {
        this.supplieTypeId = supplieTypeId;
    }

    /**
     * <pre>
     * 获取：抽取数量
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_COUNT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_COUNT：抽取数量
     */
    public Integer getSupplieCount() {
        return supplieCount;
    }

    /**
     * <pre>
     * 设置：抽取数量
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_COUNT
     * </pre>
     *
     * @param supplieCount
     *            T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_COUNT：抽取数量
     */
    public void setSupplieCount(Integer supplieCount) {
        this.supplieCount = supplieCount;
    }

    /**
     * <pre>
     * 获取：执行资格
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_QUALIFICATION
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_QUALIFICATION：执行资格
     */
    public String getSupplieQualification() {
        return supplieQualification;
    }

    /**
     * <pre>
     * 设置：执行资格
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_QUALIFICATION
     * </pre>
     *
     * @param supplieQualification
     *            T_SES_SMS_SUPPLIE_CON_TYPE.SUPPLIE_QUALIFICATION：执行资格
     */
    public void setSupplieQualification(String supplieQualification) {
        this.supplieQualification = supplieQualification == null ? null : supplieQualification.trim();
    }

    /**
     * <pre>
     * 获取：品目id
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_ID：品目id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * <pre>
     * 设置：品目id
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_ID
     * </pre>
     *
     * @param categoryId
     *            T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_ID：品目id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * <pre>
     * 获取：品目name
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_NAME：品目name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * <pre>
     * 设置：品目name
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_NAME
     * </pre>
     *
     * @param categoryName
     *            T_SES_SMS_SUPPLIE_CON_TYPE.CATEGORY_NAME：品目name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * <pre>
     * 获取：条件表id
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CONDITION_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.CONDITION_ID：条件表id
     */
    public String getConditionId() {
        return conditionId;
    }

    /**
     * <pre>
     * 设置：条件表id
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CONDITION_ID
     * </pre>
     *
     * @param conditionId
     *            T_SES_SMS_SUPPLIE_CON_TYPE.CONDITION_ID：条件表id
     */
    public void setConditionId(String conditionId) {
        this.conditionId = conditionId == null ? null : conditionId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.CREATED_AT：null
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIE_CON_TYPE.CREATED_AT：null
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.IS_DELETE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.IS_DELETE：null
     */
    public Short getIsDelete() {
        return isDelete;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.IS_DELETE
     * </pre>
     *
     * @param isDelete
     *            T_SES_SMS_SUPPLIE_CON_TYPE.IS_DELETE：null
     */
    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * <pre>
     * 获取：是否满足多个条件1满足多个 2不满足
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.IS_MULTICONDITION
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CON_TYPE.IS_MULTICONDITION：是否满足多个条件1满足多个 2不满足
     */
    public Short getIsMulticondition() {
        return isMulticondition;
    }

    /**
     * <pre>
     * 设置：是否满足多个条件1满足多个 2不满足
     * 表字段：T_SES_SMS_SUPPLIE_CON_TYPE.IS_MULTICONDITION
     * </pre>
     *
     * @param isMulticondition
     *            T_SES_SMS_SUPPLIE_CON_TYPE.IS_MULTICONDITION：是否满足多个条件1满足多个 2不满足
     */
    public void setIsMulticondition(Short isMulticondition) {
        this.isMulticondition = isMulticondition;
    }

	public Integer getExpertsTypeId() {
		return expertsTypeId;
	}

	public void setExpertsTypeId(Integer expertsTypeId) {
		this.expertsTypeId = expertsTypeId;
	}
    
    
}