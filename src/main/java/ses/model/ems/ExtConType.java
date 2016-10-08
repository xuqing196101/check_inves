package ses.model.ems;

import java.math.BigDecimal;
import java.util.Date;

public class ExtConType {
    /**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 专家类型
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_TYPE_ID
     * </pre>
     */
    private Short expertsTypeId;

    /**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_COUNT
     * </pre>
     */
    private Integer expertsCount;

    /**
     * <pre>
     * 执行资格
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_QUALIFICATION
     * </pre>
     */
    private String expertsQualification;

    /**
     * <pre>
     * 品目id
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_ID
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * 品目name
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_NAME
     * </pre>
     */
    private String categoryName;

    /**
     * <pre>
     * 条件表id
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.CONDITION_ID
     * </pre>
     */
    private String conditionId;

    /**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXT_CON_TYPE.IS_DELETE
     * </pre>
     */
    private Short isDelete;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_EXP_EXT_CON_TYPE.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：专家类型
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_TYPE_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_TYPE_ID：专家类型
     */
    public Short getExpertsTypeId() {
        return expertsTypeId;
    }

    /**
     * <pre>
     * 设置：专家类型
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_TYPE_ID
     * </pre>
     *
     * @param expertsTypeId
     *            T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_TYPE_ID：专家类型
     */
    public void setExpertsTypeId(Short expertsTypeId) {
        this.expertsTypeId = expertsTypeId;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_COUNT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_COUNT：null
     */
    public Integer getExpertsCount() {
        return expertsCount;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_COUNT
     * </pre>
     *
     * @param expertsCount
     *            T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_COUNT：null
     */
    public void setExpertsCount(Integer expertsCount) {
        this.expertsCount = expertsCount;
    }

    /**
     * <pre>
     * 获取：执行资格
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_QUALIFICATION
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_QUALIFICATION：执行资格
     */
    public String getExpertsQualification() {
        return expertsQualification;
    }

    /**
     * <pre>
     * 设置：执行资格
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_QUALIFICATION
     * </pre>
     *
     * @param expertsQualification
     *            T_SES_EMS_EXP_EXT_CON_TYPE.EXPERTS_QUALIFICATION：执行资格
     */
    public void setExpertsQualification(String expertsQualification) {
        this.expertsQualification = expertsQualification == null ? null : expertsQualification.trim();
    }

    /**
     * <pre>
     * 获取：品目id
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_ID：品目id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * <pre>
     * 设置：品目id
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_ID
     * </pre>
     *
     * @param categoryId
     *            T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_ID：品目id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * <pre>
     * 获取：品目name
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_NAME
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_NAME：品目name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * <pre>
     * 设置：品目name
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_NAME
     * </pre>
     *
     * @param categoryName
     *            T_SES_EMS_EXP_EXT_CON_TYPE.CATEGORY_NAME：品目name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * <pre>
     * 获取：条件表id
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CONDITION_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.CONDITION_ID：条件表id
     */
    public String getConditionId() {
        return conditionId;
    }

    /**
     * <pre>
     * 设置：条件表id
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CONDITION_ID
     * </pre>
     *
     * @param conditionId
     *            T_SES_EMS_EXP_EXT_CON_TYPE.CONDITION_ID：条件表id
     */
    public void setConditionId(String conditionId) {
        this.conditionId = conditionId == null ? null : conditionId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CREATED_AT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.CREATED_AT：null
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_EMS_EXP_EXT_CON_TYPE.CREATED_AT：null
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.IS_DELETE
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CON_TYPE.IS_DELETE：null
     */
    public Short getIsDelete() {
        return isDelete;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXT_CON_TYPE.IS_DELETE
     * </pre>
     *
     * @param isDelete
     *            T_SES_EMS_EXP_EXT_CON_TYPE.IS_DELETE：null
     */
    public void setIsDelete(Short isDelete) {
        this.isDelete = isDelete;
    }
}