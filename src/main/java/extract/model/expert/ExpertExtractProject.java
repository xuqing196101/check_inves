package extract.model.expert;

import java.util.Date;

/**
 * 
 * Description: 专家抽取项目信息实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractProject {
	
	/*
	 * 主键
	 */
    private String id;
    
    /*
	 * 项目名称
	 */
    private String name;
    
    /*
	 * 项目编号
	 */
    private String code;
    
    /*
	 * 采购方式
	 */
    private String purchaseWay;
    
    /*
	 * 包名
	 */
    private String packageName;
    
    /*
	 * 评审时间
	 */
    private Date reviewTime;
    
    /*
	 * 评审地点
	 */
    private String reviewArea;
    
    /*
	 * 项目类型
	 */
    private String projectType;
    
    /*
	 * 抽取地址
	 */
    private String extractAddress;
    
    /*
	 * 联系人
	 */
    private String contactPerson;
    
    /*
	 * 联系电话
	 */
    private String contactNum;
    
    /*
	 * 备注（其他要求）
	 */
    private String remark;
    
    /*
	 * 删除标识 1 删除 0 未删除
	 */
    private Short isDeleted;
    
    /*
	 * 创建时间
	 */
    private Date createdAt;
    
    /*
	 * 修改时间
	 */
    private Date updatedAt;
    
    /*
	 * 项目id
	 */
    private String projectId;
    
    /*
	 * 包id
	 */
    private String packageId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay == null ? null : purchaseWay.trim();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewArea() {
        return reviewArea;
    }

    public void setReviewArea(String reviewArea) {
        this.reviewArea = reviewArea == null ? null : reviewArea.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getExtractAddress() {
        return extractAddress;
    }

    public void setExtractAddress(String extractAddress) {
        this.extractAddress = extractAddress == null ? null : extractAddress.trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum == null ? null : contactNum.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }
}