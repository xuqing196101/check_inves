package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * @Title: SupplierTypeRelate
 * @Description: 供应商类型关联
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:38:58
 */
public class SupplierTypeRelate implements Serializable {
	private static final long serialVersionUID = -5960945946278395413L;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE_RELATE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE_RELATE.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE_RELATE.SUPPLIER_TYPE_ID
	 * </pre>
	 */
	private String supplierTypeId;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE_RELATE.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_TYPE_RELATE.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

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

	public String getSupplierTypeId() {
		return supplierTypeId;
	}

	public void setSupplierTypeId(String supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
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
}