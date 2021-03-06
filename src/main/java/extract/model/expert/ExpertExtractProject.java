package extract.model.expert;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 
 * Description: 专家抽取项目信息实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractProject {

    /**
     * 主键
     */
    private String id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编号
     */
    private String code;

    /**
     * 采购方式
     */
    private String purchaseWay;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 评审时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewTime;

    /**
     * 评审地点-省/直辖市
     */
    private String reviewProvince;

    /**
     * 项目类型
     */
    private String projectType;

    /**
     * 抽取地址
     */
    private String extractAddress;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactNum;

    /**
     * 备注（其他要求）
     */
    private String remark;

    /**
     * 删除标识
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 包id
     */
    private String packageId;

    /**
     * 评审天数
     */
    private String reviewDays;

    /**
     * 评审地点-地市/区
     */
    private String reviewAddress;

    /**
     * 抽取方式 1 自动抽取 0 人工抽取
     */
    private Short isAuto;

    /**
     * 采购机构
     */
    private String procurementDepId;

    /**
     * 抽取状态
     */
    private String status;
    
    /**
     * 抽取人员
     */
    private String extractPerson;
    
    /**
     * 评审详细地址
     */
    private String reviewSite;
    
    /**
     * 抽取人
     */
    private String createrId;
    
    /**
     * 建设单位名称
     */
    private String constructionName;
    
    /**
     * 联系固话
     */
    private String landline;
    
    public String getConstructionName() {
		return constructionName;
	}

	public void setConstructionName(String constructionName) {
		this.constructionName = constructionName;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getReviewSite() {
		return reviewSite;
	}

	public void setReviewSite(String reviewSite) {
		this.reviewSite = reviewSite == null ? null : reviewSite.trim();
	}

	public String getExtractPerson() {
        return extractPerson;
    }

    public void setExtractPerson(String extractPerson) {
        this.extractPerson = extractPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getReviewProvince() {
        return reviewProvince;
    }

    public void setReviewProvince(String reviewProvince) {
        this.reviewProvince = reviewProvince == null ? null : reviewProvince
                .trim();
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
        this.extractAddress = extractAddress == null ? null : extractAddress
                .trim();
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson
                .trim();
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

    public String getReviewDays() {
        return reviewDays;
    }

    public void setReviewDays(String reviewDays) {
        this.reviewDays = reviewDays == null ? null : reviewDays.trim();
    }

    public String getReviewAddress() {
        return reviewAddress;
    }

    public void setReviewAddress(String reviewAddress) {
        this.reviewAddress = reviewAddress == null ? null : reviewAddress.trim();
    }

    public Short getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Short isAuto) {
        this.isAuto = isAuto;
    }

	public String getProcurementDepId() {
		return procurementDepId;
	}

	public void setProcurementDepId(String procurementDepId) {
		this.procurementDepId = procurementDepId;
	}

}