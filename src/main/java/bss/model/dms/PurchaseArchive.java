/**
 * 
 */
package bss.model.dms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title:PurchaseArchive
 * @Description: 采购档案实体类
 * @author ZhaoBo
 * @date 2016-10-18下午5:29:15
 */
public class PurchaseArchive {
	/**
     * @Fields id : 主键
     */
	private String id;
	
	/**
     * @Fields name : 档案名称
     */
	private String name;
	
	/**
     * @Fields code : 档案编号
     */
	private String code;
	
	/**
     * @Fields purchaseContractMode : 采购合同方式
     */
	private Integer purchaseContractMode;
	
	/**
     * @Fields purchaseContractId : 采购合同ID
     */
	private String purchaseContractId;
	
	/**
     * @Fields status : 档案状态
     */
	private Integer status;
	
	/**
     * @Fields createdAt : 创建时间
     */
	private Date createdAt;
	
	/**
     * @Fields updatedAt : 更新时间
     */
	private Date updatedAt;
	
	/**
     * @Fields contractCode : 合同编号
     */
	private String contractCode;
	
	/**
     * @Fields projectId : 项目ID
     */
	private String projectId;
	
	/**
     * @Fields projectCode : 项目编号
     */
	private String projectCode;
	
	/**
     * @Fields planCode : 计划文号
     */
	private String planCode;
	
	/**
     * @Fields planTime : 计划下达时间
     */
	private Date planTime;
	
	/**
     * @Fields year : 预算年度
     */
	private BigDecimal year;
	
	/**
     * @Fields purchaseDep : 采购机构
     */
	private String purchaseDep;
	
	/**
     * @Fields purchaseType : 采购方式
     */
	private String purchaseType;
	
	/**
     * @Fields productName : 产品名称
     */
	private String productName;
	
	/**
     * @Fields supplierName : 供应商名称
     */
	private String supplierName;
	
	/**
     * @Fields isDeleted : 是否删除
     */
	private Integer isDeleted;
	
	/**
     * @Fields type : 档案类型
     */
	private Integer type;
	
	/**
     * @Fields reason : 退回理由
     */
	private String reason;
	
	/**
     * @Fields contractId : 合同ID
     */
	private String contractId;
	
	/**
     * @Fields draftGitAt : 合同草案上报时间
     */
	private Date draftGitAt;
    
	/**
     * @Fields draftReviewedAt : 合同草案批复时间
     */
    private Date draftReviewedAt;
    
    /**
     * @Fields formalGitAt : 正式合同上报时间
     */
    private Date formalGitAt;
    
    /**
     * @Fields formalReviewedAt : 正式合同批复时间
     */
    private Date formalReviewedAt;
    
    /**
     * @Fields reportAt : 采购文件上报时间
     */
    private Date reportAt;
    
    /**
     * @Fields applyAt : 采购文件批复时间
     */
    private Date applyAt;
    
    /**
     * @Fields archiveId : 采购档案ID
     */
    private String archiveId;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPurchaseContractMode() {
		return purchaseContractMode;
	}

	public void setPurchaseContractMode(Integer purchaseContractMode) {
		this.purchaseContractMode = purchaseContractMode;
	}

	public String getPurchaseContractId() {
		return purchaseContractId;
	}

	public void setPurchaseContractId(String purchaseContractId) {
		this.purchaseContractId = purchaseContractId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public Date getPlanTime() {
		return planTime;
	}

	public void setPlanTime(Date planTime) {
		this.planTime = planTime;
	}

	public BigDecimal getYear() {
		return year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public String getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(String purchaseDep) {
		this.purchaseDep = purchaseDep;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getDraftGitAt() {
		return draftGitAt;
	}

	public void setDraftGitAt(Date draftGitAt) {
		this.draftGitAt = draftGitAt;
	}

	public Date getDraftReviewedAt() {
		return draftReviewedAt;
	}

	public void setDraftReviewedAt(Date draftReviewedAt) {
		this.draftReviewedAt = draftReviewedAt;
	}

	public Date getFormalGitAt() {
		return formalGitAt;
	}

	public void setFormalGitAt(Date formalGitAt) {
		this.formalGitAt = formalGitAt;
	}

	public Date getFormalReviewedAt() {
		return formalReviewedAt;
	}

	public void setFormalReviewedAt(Date formalReviewedAt) {
		this.formalReviewedAt = formalReviewedAt;
	}

	public Date getReportAt() {
		return reportAt;
	}

	public void setReportAt(Date reportAt) {
		this.reportAt = reportAt;
	}

	public Date getApplyAt() {
		return applyAt;
	}

	public void setApplyAt(Date applyAt) {
		this.applyAt = applyAt;
	}

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	
	
}
