package extract.model.supplier;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import ses.model.sms.SupplierExtRelate;

public class SupplierExtractResult extends SupplierExtRelate {
    
    /**
     * 供应商id
     */
	private String supplierId;
	
	/**
	 * 条件id
	 */
	private String conditionId;
	
	/**
	 * 记录id
	 */
	private String recordId;
	
	
	/**
	 * 结果id
	 */
	private String id;
	
	/**
	 * 是否参加
	 */
	private Short join;
	
	/**
	 * 不参加理由
	 */
	private String reason;
	
	private String updatedAt;
	
	/**
	 * 供应商类型代码
	 */
	private String supplierType;
	
	private Short isDeleted;
	
	/**
	 * 反馈时间
	 */
	private Date createdAt;
	
	private String projectType;

	private String packageId;
	
	private String[] packageIds;
	
	private String level;
	
	private String supplierName;
	
	private String supplierLevel;
	
	//联系人姓名
	private String contactName;
	
	//联系人电话
	private String contactTelephone;
	
	//联系人姓名
	private String armyBusinessName;
	
	//联系人固定电话
	private String armyBusinessMobile;
	
	//联系人电话
	private String armyBuinessTelephone;
	
	private Short status;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getJoin() {
		return join;
	}

	public void setJoin(Short join) {
		this.join = join;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
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

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	/*public String getPackageId() {
		return packageId;
	}*/

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String[] getPackageIds() {
		return StringUtils.isNotBlank(packageId)?packageId.split(","):null;
	}

	public void setPackageIds(String[] packageIds) {
		this.packageIds = packageIds;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getArmyBusinessName() {
		return armyBusinessName;
	}

	public void setArmyBusinessName(String armyBusinessName) {
		this.armyBusinessName = armyBusinessName;
	}

	public String getArmyBuinessTelephone() {
		return armyBuinessTelephone;
	}

	public void setArmyBuinessTelephone(String armyBuinessTelephone) {
		this.armyBuinessTelephone = armyBuinessTelephone;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public SupplierExtractResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SupplierExtractResult(String conditionId,String supplierId,
			String recordId, Short join,String supplierType,
			String projectType, String armyBuinessTelephone) {
		super();
		this.conditionId = conditionId;
		this.supplierId = supplierId;
		this.recordId = recordId;
		this.join = join;
		this.supplierType = supplierType;
		this.projectType = projectType;
		this.armyBuinessTelephone = armyBuinessTelephone;
	}

	public String getArmyBusinessMobile() {
		return armyBusinessMobile;
	}

	public void setArmyBusinessMobile(String armyBusinessMobile) {
		this.armyBusinessMobile = armyBusinessMobile;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(String supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

	
}