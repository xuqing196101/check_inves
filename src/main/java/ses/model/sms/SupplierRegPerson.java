package ses.model.sms;

import java.io.Serializable;

public class SupplierRegPerson implements Serializable {
	private static final long serialVersionUID = -3665113996609230630L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_REG_PERSON.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 工程专业信息ID T_SES_SMS_SUPPLIER_MAT_ENG
	 * 表字段 : T_SES_SMS_SUPPLIER_REG_PERSON.MAT_ENG_ID
	 * </pre>
	 */
	private String matEngId;

	/**
	 * <pre>
	 * 注册类型
	 * 表字段 : T_SES_SMS_SUPPLIER_REG_PERSON.REG_TYPE
	 * </pre>
	 */
	private String regType;

	/**
	 * <pre>
	 * 注册人数
	 * 表字段 : T_SES_SMS_SUPPLIER_REG_PERSON.REG_NUMBER
	 * </pre>
	 */
	private Integer regNumber;

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

	public String getRegType() {
		return regType;
	}

	public void setRegType(String regType) {
		this.regType = regType;
	}

	public Integer getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(Integer regNumber) {
		this.regNumber = regNumber;
	}
}