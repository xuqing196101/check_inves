package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierCredit implements Serializable {
	private static final long serialVersionUID = -1934887360339767724L;

	/**
	 * <pre>
	 * ID 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 名称
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 0表示未启用 1表示启用
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT.STATUS
	 * </pre>
	 */
	private Integer status = 1;

	/**
	 * <pre>
	 * 创建时间
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间
	 * 表字段 : T_SES_SMS_SUPPLIER_CREDIT.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;
	
	private List<SupplierCreditCtnt> listSupplierCreditCtnts = new ArrayList<SupplierCreditCtnt>();
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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

	public List<SupplierCreditCtnt> getListSupplierCreditCtnts() {
		return listSupplierCreditCtnts;
	}

	public void setListSupplierCreditCtnts(List<SupplierCreditCtnt> listSupplierCreditCtnts) {
		this.listSupplierCreditCtnts = listSupplierCreditCtnts;
	}
}