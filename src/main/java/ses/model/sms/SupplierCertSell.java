package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierCertSell implements Serializable {
	private static final long serialVersionUID = 4358102102129301498L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 物资销售信息ID T_SES_SMS_SUPPLIER_MAT_SELL
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.MAT_SELL_ID
	 * </pre>
	 */
	private String matSellId;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.NAME
	 * </pre>
	 */
	private String name;
	   
    /**
     * <pre>
     * 编号
     * 表字段 : T_SES_SMS_SUPPLIER_CERT_SE.CODE
     * </pre>
     */
    private String code;

	/**
	 * <pre>
	 * 证书登记
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.LEVEL_CERT
	 * </pre>
	 */
	private String levelCert;

	/**
	 * <pre>
	 * 发证机关
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.LICENCE_AUTHORITH
	 * </pre>
	 */
	private String licenceAuthorith;

	/**
	 * <pre>
	 * 有效期起始日期 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.EXP_START_DATE
	 * </pre>
	 */
	private Date expStartDate;

	/**
	 * <pre>
	 *  有效期结束日期 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.EXP_END_DATE
	 * </pre>
	 */
	private Date expEndDate;

	/**
	 * <pre>
	 * 证书状态
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.MOT
	 * </pre>
	 */
	private String mot;

	/**
	 * <pre>
	 * 附件
	 * 表字段 : T_SES_SMS_SUPPLIER_CERT_SELL.ATTACH
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

	public String getMatSellId() {
		return matSellId;
	}

	public void setMatSellId(String matSellId) {
		this.matSellId = matSellId;
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

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
	
}