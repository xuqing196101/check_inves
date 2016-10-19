package ses.model.sms;

import java.util.Date;

public class SupplieCondition {
    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 状态1待抽取 2.已抽取
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.STATUS
     * </pre>
     */
    private Short status;

    /**
     * <pre>
     * 供应商所在地区
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * 专家类型
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     */
    private String expertsTypeId;

    /**
     * <pre>
     * 开标时间
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     */
    private Date tenderTime;

    /**
     * <pre>
     * 响应时间
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     */
    private String responseTime;

    /**
     * <pre>
     * 年龄
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.AGE
     * </pre>
     */
    private String age;

    /**
     * <pre>
     * 品目id
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.CATEGORY_ID
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * 专家id
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_ID
     * </pre>
     */
    private String supplietId;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     */
    private String supplietFrom;

    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     */
    private String addressId;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIE_CONDITION.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：状态1待抽取 2.已抽取
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.STATUS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public Short getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：状态1待抽取 2.已抽取
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.STATUS
     * </pre>
     *
     * @param status
     *            T_SES_SMS_SUPPLIE_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：供应商所在地区
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.ADDRESS：供应商所在地区
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：供应商所在地区
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
     * </pre>
     *
     * @param address
     *            T_SES_SMS_SUPPLIE_CONDITION.ADDRESS：供应商所在地区
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * <pre>
     * 获取：专家类型
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public String getExpertsTypeId() {
        return expertsTypeId;
    }

    /**
     * <pre>
     * 设置：专家类型
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @param expertsTypeId
     *            T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public void setExpertsTypeId(String expertsTypeId) {
        this.expertsTypeId = expertsTypeId == null ? null : expertsTypeId.trim();
    }

    /**
     * <pre>
     * 获取：开标时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME：开标时间
     */
    public Date getTenderTime() {
        return tenderTime;
    }

    /**
     * <pre>
     * 设置：开标时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     *
     * @param tenderTime
     *            T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME：开标时间
     */
    public void setTenderTime(Date tenderTime) {
        this.tenderTime = tenderTime;
    }

    /**
     * <pre>
     * 获取：响应时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME：响应时间
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * <pre>
     * 设置：响应时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @param responseTime
     *            T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME：响应时间
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime == null ? null : responseTime.trim();
    }

    /**
     * <pre>
     * 获取：年龄
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.AGE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.AGE：年龄
     */
    public String getAge() {
        return age;
    }

    /**
     * <pre>
     * 设置：年龄
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.AGE
     * </pre>
     *
     * @param age
     *            T_SES_SMS_SUPPLIE_CONDITION.AGE：年龄
     */
    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    /**
     * <pre>
     * 获取：品目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.CATEGORY_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.CATEGORY_ID：品目id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * <pre>
     * 设置：品目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.CATEGORY_ID
     * </pre>
     *
     * @param categoryId
     *            T_SES_SMS_SUPPLIE_CONDITION.CATEGORY_ID：品目id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * <pre>
     * 获取：专家id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_ID：专家id
     */
    public String getSupplietId() {
        return supplietId;
    }

    /**
     * <pre>
     * 设置：专家id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_ID
     * </pre>
     *
     * @param supplietId
     *            T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_ID：专家id
     */
    public void setSupplietId(String supplietId) {
        this.supplietId = supplietId == null ? null : supplietId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM：null
     */
    public String getSupplietFrom() {
        return supplietFrom;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     *
     * @param supplietFrom
     *            T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM：null
     */
    public void setSupplietFrom(String supplietFrom) {
        this.supplietFrom = supplietFrom == null ? null : supplietFrom.trim();
    }

    /**
     * <pre>
     * 获取：抽取地点
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID：抽取地点
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * <pre>
     * 设置：抽取地点
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     *
     * @param addressId
     *            T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID：抽取地点
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }
}