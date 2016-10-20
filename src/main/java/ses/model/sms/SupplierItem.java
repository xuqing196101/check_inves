package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierItem implements Serializable {
	private static final long serialVersionUID = -6757464247201483546L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 品目ID
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.CATEGORY_ID
	 * </pre>
	 */
	private String categoryId;

	/**
	 * <pre>
	 * 供应商类型
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.SUPPLIER_TYPE_RELATE_ID
	 * </pre>
	 */
	private String supplierTypeRelateId;

	/**
	 * <pre>
	 * 状态
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.STATUS
	 * </pre>
	 */
	private Integer status;

	/**
	 * <pre>
	 * 创建时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private String categoryName;

	private List<SupplierProducts> listSupplierProducts = new ArrayList<SupplierProducts>();

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

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSupplierTypeRelateId() {
		return supplierTypeRelateId;
	}

	public void setSupplierTypeRelateId(String supplierTypeRelateId) {
		this.supplierTypeRelateId = supplierTypeRelateId;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<SupplierProducts> getListSupplierProducts() {
		return listSupplierProducts;
	}

	public void setListSupplierProducts(List<SupplierProducts> listSupplierProducts) {
		this.listSupplierProducts = listSupplierProducts;
	}
}