package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.model.UploadFile;

public class SupplierEngQua implements Serializable {
	
	private static final long serialVersionUID = -1813701088542024262L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 工程专业信息ID T_SES_SMS_SUPPLIER_MAT_ENG
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.MAT_SERVE_ID
	 * </pre>
	 */
	private String matEngId;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.NAME
	 * </pre>
	 */
	private String name;
	
	/**
	 * <pre>
	 * 编号
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.CODE
	 * </pre>
	 */
	private String code;

	/**
	 * <pre>
	 * 证书登记
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.LEVEL_CERT
	 * </pre>
	 */
	private String levelCert;

	/**
	 * <pre>
	 * 发证机关
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.LICENCE_AUTHORITH
	 * </pre>
	 */
	private String licenceAuthorith;

	/**
	 * <pre>
	 * 有效期起始日期
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.EXP_START_DATE
	 * </pre>
	 */
	private Date expStartDate;

	/**
	 * <pre>
	 * 有效期结束日期
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.EXP_END_DATE
	 * </pre>
	 */
	private Date expEndDate;

	/**
     * <pre>
     * 证书状态
     * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUALL.MOT
     * </pre>
     */
    private String mot;

	/**
	 * <pre>
	 * 附件
	 * 表字段 : T_SES_SMS_SUPPLIER_ENG_QUA.ATTACH
	 * </pre>
	 */
	private String attach;
	
	private String attachId;

	private List<UploadFile> fileList=new ArrayList<UploadFile>();
	
	public List<UploadFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<UploadFile> fileList) {
		this.fileList = fileList;
	}
	
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
