package ses.model.sms;

import java.io.Serializable;

public class SupplierCreditCtnt implements Serializable {
	private static final long serialVersionUID = -1388814691682659318L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT_CTNT.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 外键
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT_CTNT.SUPPLIER_CREDIT_ID
	 * </pre>
	 */
	private String supplierCreditId;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT_CTNT.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 分数
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT_CTNT.SCORE
	 * </pre>
	 */
	private Integer score;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierCreditId() {
		return supplierCreditId;
	}

	public void setSupplierCreditId(String supplierCreditId) {
		this.supplierCreditId = supplierCreditId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}