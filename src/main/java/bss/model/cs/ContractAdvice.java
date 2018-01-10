package bss.model.cs;

import java.util.Date;

import bss.model.ppms.Project;

/**
 * 
* <p>Title:ContractAdvice </p>
* <p>Description: 合同审核记录</p>
* @author FengTian
* @date 2018-1-2下午4:29:16
 */
public class ContractAdvice {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 项目ID
	 */
	private String projectId;
	
	/**
	 * 合同ID
	 */
	private String contractId;
	
	/**
	 * 审核意见
	 */
	private String reason;
	
	/**
	 * 审核人
	 */
	private String userId;
	
	/**
	 * 审核时间
	 */
	private Date aduitTime;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	/**
	 * 状态 1：未审核，2：审核中，3：审核通过，4：审核不通过
	 */
	private Integer status;
	
	/**
	 * 删除标示
	 */
	private Integer isDeleted;
	
	/**
	 * 申请人
	 */
	private String proposer;
	
	/**
	 * 项目实体
	 */
	private Project project;
	
	private PurchaseContract purchaseContract;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getAduitTime() {
		return aduitTime;
	}

	public void setAduitTime(Date aduitTime) {
		this.aduitTime = aduitTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getProposer() {
		return proposer;
	}

	public void setProposer(String proposer) {
		this.proposer = proposer;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public PurchaseContract getPurchaseContract() {
		return purchaseContract;
	}

	public void setPurchaseContract(PurchaseContract purchaseContract) {
		this.purchaseContract = purchaseContract;
	}
	
}
