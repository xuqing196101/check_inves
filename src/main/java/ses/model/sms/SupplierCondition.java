package ses.model.sms;

import java.util.Date;
import java.util.List;

import bss.echarts.Data;

public class SupplierCondition {


    public SupplierCondition() {
        super();
    }

    public SupplierCondition(String id, String s) {
        super();
        this.id = id;
    }



    public SupplierCondition(String id, Short status) {
        super();
        this.id = id;
        this.status = status;
    }


    public SupplierCondition(String projectId) {
        super();
        this.projectId = projectId;
    }

    /**
     * 关联供应商
     */
    private List<SupplierExtRelate> extRelatesList;


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
     * 供应商类型
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
     * 类型
     */
    private List<SupplierConType> conTypes;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.SUPPLIER_LEVEL
     * </pre>
     */
    private String supplierLevel;

    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     */
    private String extractAddress;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    /**
     * 限制地区理由
     */
    private String addressReason;
    
    /**
     * 品目
     */
    private String categoryName;
    
    /**
     * 品目id
     */
    private String categoryId;
    
    /**
     * 品目id
     */
    private String addressId;
    
    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     */
    private String[] addressSplit;

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
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM：null
     */
    public String getSupplierLevel() {
        return supplierLevel;
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
    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel == null ? null : supplierLevel.trim();
    }

  

    public List<SupplierConType> getConTypes() {
        return conTypes;
    }

    public void setConTypes(List<SupplierConType> conTypes) {
        this.conTypes = conTypes;
    }
    private String expertsFrom;

    public String getExpertsFrom() {
        return expertsFrom;
    }

    public void setExpertsFrom(String expertsFrom) {
        this.expertsFrom = expertsFrom;
    }

 

    /**
     * @return Returns the extRelatesList.
     */
    public List<SupplierExtRelate> getExtRelatesList() {
        return extRelatesList;
    }

    /**
     * @param extRelatesList The extRelatesList to set.
     */
    public void setExtRelatesList(List<SupplierExtRelate> extRelatesList) {
        this.extRelatesList = extRelatesList;
    }

    /**
     * @return Returns the addressSplit.
     */
    public String[] getAddressSplit() {
        return addressSplit;
    }

    /**
     * @param addressSplit The addressSplit to set.
     */
    public void setAddressSplit(String[] addressSplit) {
        this.addressSplit = addressSplit;
    }

    /**
     * @return Returns the createdAt.
     */
    public Date getCreatedAt() {
      return createdAt;
    }

    /**
     * @param createdAt The createdAt to set.
     */
    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
    }

    /**
     * @return Returns the addressReason.
     */
    public String getAddressReason() {
      return addressReason;
    }

    /**
     * @param addressReason The addressReason to set.
     */
    public void setAddressReason(String addressReason) {
      this.addressReason = addressReason;
    }

    /**
     * @return Returns the categoryName.
     */
    public String getCategoryName() {
      return categoryName;
    }

    /**
     * @param categoryName The categoryName to set.
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }

    /**
     * @return Returns the categoryId.
     */
    public String getCategoryId() {
      return categoryId;
    }

    /**
     * @param categoryId The categoryId to set.
     */
    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    /**
     * @return Returns the extractAddress.
     */
    public String getExtractAddress() {
      return extractAddress;
    }

    /**
     * @param extractAddress The extractAddress to set.
     */
    public void setExtractAddress(String extractAddress) {
      this.extractAddress = extractAddress;
    }

    /**
     * @return Returns the addressId.
     */
    public String getAddressId() {
      return addressId;
    }

    /**
     * @param addressId The addressId to set.
     */
    public void setAddressId(String addressId) {
      this.addressId = addressId;
    }

 



}