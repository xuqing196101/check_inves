package extract.model.supplier;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import ses.model.sms.Supplier;
import ses.util.MyAnnotation;

public class SupplierExtractResult {
    
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
	
	/**
	 * 供应商类型代码
	 */
	private String supplierType;
	
	/**
	 * 反馈时间
	 */
	private Date createdAt;
	
	private String projectType;

	private String packageId;
	
	private String[] packageIds;

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

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
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

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String[] getPackageIds() {
		
		return StringUtils.isNotBlank(packageId)?this.packageId.split(","):null;
	}

	public void setPackageIds(String[] packageIds) {
		this.packageIds = packageIds;
	}
  
	
}