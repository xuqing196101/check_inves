/**
 * 
 */
package bss.model.dms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Title:ProbationaryArchive
 * @Description: 预备档案表
 * @author ZhaoBo
 * @date 2016-11-14上午9:11:50
 */
public class ProbationaryArchive {
	/**
     * @Fields id : 主键
     */
	private String id;
	
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
     * @Fields createdAt : 创建时间
     */
	private Date createdAt;
	
	/**
     * @Fields updatedAt : 更新时间
     */
	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
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
	
	
}
