package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PackageAdvice {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 项目ID
	 */
	private String projectId;
	
	/**
	 * 包ID
	 */
	private String packageId;
	
	/**
	 * 类型 1：中止，2：转竞谈
	 */
	private Integer type;
	
	/**
	 * 流程ID
	 */
	private String flowDefineId;
	
	/**
	 * 原因
	 */
	private String advice;
	
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
	 * 项目实体
	 */
	private Project project;
	
	/**
	 * 状态 1：未审核，2：审核中，3：审核通过，4：审核不通过
	 */
	private Integer status;
	
	/**
	 * 包名
	 */
	private String packageName;
	
	/**
	 * 审核标识
	 */
	private String code;
	
	/**
	 * 项目预算
	 */
	private BigDecimal budget;
	
	/**
	 * 审核意见
	 */
	private String reason;
	
	/**
	 * 包集合
	 */
	private List<Packages> packageList;
	
	/**
	 * 假删除
	 */
	private Integer isDeleted;
	
	/**
	 * 申请人
	 */
	private String proposer;

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

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getFlowDefineId() {
		return flowDefineId;
	}

	public void setFlowDefineId(String flowDefineId) {
		this.flowDefineId = flowDefineId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<Packages> getPackageList() {
		return packageList;
	}

	public void setPackageList(List<Packages> packageList) {
		this.packageList = packageList;
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
	
}
