package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierCertServe implements Serializable {
	private static final long serialVersionUID = -6858452147934345834L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 服务专业信息ID T_SES_SMS_SUPPLIER_MAT_SERVE
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.MAT_SERVE_ID
	 * </pre>
	 */
	private String matServeId;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 证书登记
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.LEVEL_CERT
	 * </pre>
	 */
	private String levelCert;

	/**
	 * <pre>
	 * 发证机关
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.LICENCE_AUTHORITH
	 * </pre>
	 */
	private String licenceAuthorith;

	/**
	 * <pre>
	 * 有效期起始日期
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.EXP_START_DATE
	 * </pre>
	 */
	private Date expStartDate;

	/**
	 * <pre>
	 * 有效期结束日期
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.EXP_END_DATE
	 * </pre>
	 */
	private Date expEndDate;

	/**
	 * <pre>
	 * 是否年检 0代表无 1代表是
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.MOT
	 * </pre>
	 */
	private Integer mot;

	/**
	 * <pre>
	 * 附件
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.ATTACH
	 * </pre>
	 */
	private String attach;
	
	private String attachId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatServeId() {
		return matServeId;
	}

	public void setMatServeId(String matServeId) {
		this.matServeId = matServeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevelCert() {
		return levelCert;
	}

	public void setLevelCert(String levelCert) {
		this.levelCert = levelCert;
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

	public Integer getMot() {
		return mot;
	}

	public void setMot(Integer mot) {
		this.mot = mot;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
	
}