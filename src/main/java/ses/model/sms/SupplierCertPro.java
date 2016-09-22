package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierCertPro implements Serializable {
	private static final long serialVersionUID = 7944588278221788114L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 物资生产信息ID T_SES_SMS_SUPPLIER_MAT_PRO
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.MAT_PRO_ID
	 * </pre>
	 */
	private String matProId;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 证书登记
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.LEVEL_CERT
	 * </pre>
	 */
	private String levelCert;

	/**
	 * <pre>
	 * 发证机关
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.LICENCE_AUTHORITH
	 * </pre>
	 */
	private String licenceAuthorith;

	/**
	 * <pre>
	 * 有效期起始日期 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.EXP_START_DATE
	 * </pre>
	 */
	private Date expStartDate;

	/**
	 * <pre>
	 * 有效期结束日期格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.EXP_END_DATE
	 * </pre>
	 */
	private Date expEndDate;

	/**
	 * <pre>
	 * 是否年检 0代表无 1代表是
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.MOT
	 * </pre>
	 */
	private Integer mot;

	/**
	 * <pre>
	 * 附件 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_PRO.ATTACH
	 * </pre>
	 */
	private String attach;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMatProId() {
		return matProId;
	}

	public void setMatProId(String matProId) {
		this.matProId = matProId;
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
}