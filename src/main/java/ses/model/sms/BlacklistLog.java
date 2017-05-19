package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class BlacklistLog implements Serializable {
	private static final long serialVersionUID = -2457177137110721681L;

	/**
	 * <pre>
	 * 主键 ID
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 开始时间
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.START_TIME
	 * </pre>
	 */
	private Date startTime;

	/**
	 * <pre>
	 * 期限
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.TERM
	 * </pre>
	 */
	private Integer term;

	/**
	 * <pre>
	 * 处罚类型
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.PUNISH_TYPE
	 * </pre>
	 */
	private Integer punishType;

	/**
	 * <pre>
	 * 发布类型
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.RELEASE_TYPE
	 * </pre>
	 */
	private Integer releaseType;

	/**
	 * <pre>
	 * 原因
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.REASON
	 * </pre>
	 */
	private String reason;

	/**
	 * <pre>
	 * 操作人ID
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.OPERATION_ID
	 * </pre>
	 */
	private String operationId;

	/**
	 * <pre>
	 * 操作类型 0 新增 1 修改 2手动移除
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.OPERATION_TYPE
	 * </pre>
	 */
	private Integer operationType;

	/**
	 * <pre>
	 * 操作人姓名
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.OPERATION_NAME
	 * </pre>
	 */
	private String operationName;

	/**
	 * <pre>
	 * 操作时间
	 * 表字段 : T_SES_SMS_BLACKLIST_LOG.OPERATION_DATE
	 * </pre>
	 */
	private Date operationDate;

	private String supplierName;
	
	/**处罚截止时间**/
	private Date endTime;

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}