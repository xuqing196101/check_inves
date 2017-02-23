package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierCertEng implements Serializable {
	private static final long serialVersionUID = 1571751347464699780L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 工程专业信息ID T_SES_SMS_SUPPLIER_MAT_ENG
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.MAT_ENG_ID
	 * </pre>
	 */
	private String matEngId;

	/**
	 * <pre>
	 * 资质资格类型
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.CERT_TYPE
	 * </pre>
	 */
	private String certType;

	/**
	 * <pre>
	 * 证书编号
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.CERT_CODE
	 * </pre>
	 */
	private String certCode;

	/**
	 * <pre>
	 * 证书最高等级
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.CERT_MAX_LEVEL
	 * </pre>
	 */
	private String certMaxLevel;

	/**
	 * <pre>
	 * 技术负责人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.TECH_NAME
	 * </pre>
	 */
	private String techName;

	/**
	 * <pre>
	 * 技术负责人职称
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.TECH_PT
	 * </pre>
	 */
	private String techPt;

	/**
	 * <pre>
	 * 技术负责人职务
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.TECH_JOP
	 * </pre>
	 */
	private String techJop;

	/**
	 * <pre>
	 * 单位负责人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.DEP_NAME
	 * </pre>
	 */
	private String depName;

	/**
	 * <pre>
	 * 单位负责人职称
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.DEP_PT
	 * </pre>
	 */
	private String depPt;

	/**
	 * <pre>
	 * 单位负责人职务
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.DEP_JOP
	 * </pre>
	 */
	private String depJop;

	/**
	 * <pre>
	 * 发证机关
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.LICENCE_AUTHORITH
	 * </pre>
	 */
	private String licenceAuthorith;

	/**
	 * <pre>
	 * 发证日期
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.EXP_START_DATE
	 * </pre>
	 */
	private Date expStartDate;

	/**
	 * <pre>
	 * 证书有效期截止时间
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.EXP_END_DATE
	 * </pre>
	 */
	private Date expEndDate;

	/**
	 * <pre>
	 * 证书状态 0代表无效 1代表有效
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.CERT_STATUS
	 * </pre>
	 */
	private String certStatus;

	/**
	 * <pre>
	 * 附件 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_ENG.ATTACH_CERT
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

	public String getCertMaxLevel() {
		return certMaxLevel;
	}

	public void setCertMaxLevel(String certMaxLevel) {
		this.certMaxLevel = certMaxLevel;
	}

	public String getTechName() {
		return techName;
	}

	public void setTechName(String techName) {
		this.techName = techName;
	}

	public String getTechPt() {
		return techPt;
	}

	public void setTechPt(String techPt) {
		this.techPt = techPt;
	}

	public String getTechJop() {
		return techJop;
	}

	public void setTechJop(String techJop) {
		this.techJop = techJop;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepPt() {
		return depPt;
	}

	public void setDepPt(String depPt) {
		this.depPt = depPt;
	}

	public String getDepJop() {
		return depJop;
	}

	public void setDepJop(String depJop) {
		this.depJop = depJop;
	}

	public String getLicenceAuthorith() {
		return licenceAuthorith;
	}

	public void setLicenceAuthorith(String licenceAuthorith) {
		this.licenceAuthorith = licenceAuthorith;
	}

	public Date getExpStartDate() {
		return expStartDate;
	}

	public void setExpStartDate(Date expStartDate) {
		this.expStartDate = expStartDate;
	}

	public Date getExpEndDate() {
		return expEndDate;
	}

	public void setExpEndDate(Date expEndDate) {
		this.expEndDate = expEndDate;
	}

	public String getCertStatus() {
		return certStatus;
	}

	public void setCertStatus(String certStatus) {
		this.certStatus = certStatus;
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