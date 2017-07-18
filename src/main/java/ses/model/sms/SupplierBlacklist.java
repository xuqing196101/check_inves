package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierBlacklist implements Serializable {
	private static final long serialVersionUID = 8206603346790673441L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.SUPPLIERS_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 起始时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.START_TIME
	 * </pre>
	 */
	private Date startTime;

	/**
	 * <pre>
	 * 结束时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.END_TIME
	 * </pre>
	 */
	private Date endTime;

	/**
	 * <pre>
	 * 处罚类型 0警告 1不得参与 
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.PUNISH_TYPE
	 * </pre>
	 */
	private Integer punishType;

	/**
	 * <pre>
	 * 发布类型 0 内外网发布 1 内网发布 2 外网发布
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.RELEASE_TYPE
	 * </pre>
	 */
	private Integer releaseType;

	/**
	 * <pre>
	 * 状态 0 处罚中 1 过期 2 手动移除
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.STATUS
	 * </pre>
	 */
	private Integer status;

	/**
	 * <pre>
	 * 理由
	 * 表字段 : T_SES_SMS_SUPPLIER_BLACKLIST.REASON
	 * </pre>
	 */
	private String reason;

	private Integer term;

	private String supplierName;
	
	/**
     * <pre>
     * 创建时间
     * 表字段 : T_SUMS_OC_COMPLAINT.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_SUMS_OC_COMPLAINT.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getPunishType() {
		return punishType;
	}

	public void setPunishType(Integer punishType) {
		this.punishType = punishType;
	}

	public Integer getReleaseType() {
		return releaseType;
	}

	public void setReleaseType(Integer releaseType) {
		this.releaseType = releaseType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason.trim();
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}