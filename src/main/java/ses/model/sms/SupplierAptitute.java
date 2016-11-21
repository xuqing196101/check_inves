package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierAptitute implements Serializable {
	private static final long serialVersionUID = -7422250901873609937L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 工程专业信息ID T_SES_SMS_SUPPLIER_MAT_ENG
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.MAT_ENG_ID
	 * </pre>
	 */
	private String matEngId;

	/**
	 * <pre>
	 * 资质资格类型
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.CERT_TYPE
	 * </pre>
	 */
	private String certType;

	/**
	 * <pre>
	 * 证书编号
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.CERT_CODE
	 * </pre>
	 */
	private String certCode;

	/**
	 * <pre>
	 * 资质资格序列
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_SEQUENCE
	 * </pre>
	 */
	private String aptituteSequence;

	/**
	 * <pre>
	 * 专业类别
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.PROFESS_TYPE
	 * </pre>
	 */
	private String professType;

	/**
	 * <pre>
	 * 资质资格等级
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_ LEVEL
	 * </pre>
	 */
	private String aptituteLevel;

	/**
	 * <pre>
	 * 是否主项资金 0代表否 1代表是
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.IS_MAJOR_FUND
	 * </pre>
	 */
	private Integer isMajorFund;

	/**
	 * <pre>
	 * 批准资质资格内容
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_CONTENT
	 * </pre>
	 */
	private String aptituteContent;

	/**
	 * <pre>
	 * 首次批转资质资格文号
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_CODE
	 * </pre>
	 */
	private String aptituteCode;

	/**
	 * <pre>
	 * 首次批转资质资格日期
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_DATE
	 * </pre>
	 */
	private Date aptituteDate;

	/**
	 * <pre>
	 * 资质资格获取类型
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_WAY
	 * </pre>
	 */
	private String aptituteWay;

	/**
	 * <pre>
	 * 资质资格状态 0代表无效 1代表有效
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_STATUS
	 * </pre>
	 */
	private Integer aptituteStatus;

	/**
	 * <pre>
	 * 资质资格变更时间
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_CHANGE_AT
	 * </pre>
	 */
	private Date aptituteChangeAt;

	/**
	 * <pre>
	 * 资质资格变更原因
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.APTITUTE_CHANGE_REASON
	 * </pre>
	 */
	private String aptituteChangeReason;

	/**
	 * <pre>
	 * 附件 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_APTITUTE.ATTACH_CERT
	 * </pre>
	 */
	private String attachCert;
	
	private String attachCertId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatEngId() {
		return matEngId;
	}

	public void setMatEngId(String matEngId) {
		this.matEngId = matEngId;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}

	public String getAptituteSequence() {
		return aptituteSequence;
	}

	public void setAptituteSequence(String aptituteSequence) {
		this.aptituteSequence = aptituteSequence;
	}

	public String getProfessType() {
		return professType;
	}

	public void setProfessType(String professType) {
		this.professType = professType;
	}

	public String getAptituteLevel() {
		return aptituteLevel;
	}

	public void setAptituteLevel(String aptituteLevel) {
		this.aptituteLevel = aptituteLevel;
	}

	public Integer getIsMajorFund() {
		return isMajorFund;
	}

	public void setIsMajorFund(Integer isMajorFund) {
		this.isMajorFund = isMajorFund;
	}

	public String getAptituteContent() {
		return aptituteContent;
	}

	public void setAptituteContent(String aptituteContent) {
		this.aptituteContent = aptituteContent;
	}

	public String getAptituteCode() {
		return aptituteCode;
	}

	public void setAptituteCode(String aptituteCode) {
		this.aptituteCode = aptituteCode;
	}

	public Date getAptituteDate() {
		return aptituteDate;
	}

	public void setAptituteDate(Date aptituteDate) {
		this.aptituteDate = aptituteDate;
	}

	public String getAptituteWay() {
		return aptituteWay;
	}

	public void setAptituteWay(String aptituteWay) {
		this.aptituteWay = aptituteWay;
	}

	public Integer getAptituteStatus() {
		return aptituteStatus;
	}

	public void setAptituteStatus(Integer aptituteStatus) {
		this.aptituteStatus = aptituteStatus;
	}

	public Date getAptituteChangeAt() {
		return aptituteChangeAt;
	}

	public void setAptituteChangeAt(Date aptituteChangeAt) {
		this.aptituteChangeAt = aptituteChangeAt;
	}

	public String getAptituteChangeReason() {
		return aptituteChangeReason;
	}

	public void setAptituteChangeReason(String aptituteChangeReason) {
		this.aptituteChangeReason = aptituteChangeReason;
	}

	public String getAttachCert() {
		return attachCert;
	}

	public void setAttachCert(String attachCert) {
		this.attachCert = attachCert;
	}

	public String getAttachCertId() {
		return attachCertId;
	}

	public void setAttachCertId(String attachCertId) {
		this.attachCertId = attachCertId;
	}
	
}