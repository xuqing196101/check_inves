package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class ProductParam implements Serializable {
	private static final long serialVersionUID = 4572837409290047850L;

	/**
	 * <pre>
	 * 主键ID
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商产品ID
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.SUPPLIER_PRODUCTS_ID
	 * </pre>
	 */
	private String supplierProductsId;

	/**
	 * <pre>
	 * 产品参数ID
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.CATEGORY_PARAM_ID
	 * </pre>
	 */
	private String categoryParamId;

	/**
	 * <pre>
	 * 参数值
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.PARAM_VALUE
	 * </pre>
	 */
	private String paramValue;

	/**
	 * <pre>
	 * 删除状态
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.IS_DELETED
	 * </pre>
	 */
	private Short isDeleted;

	/**
	 * <pre>
	 * 创建时间
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间
	 * 表字段 : T_SES_SMS_PRODUCT_PARAM.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private String paramName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierProductsId() {
		return supplierProductsId;
	}

	public void setSupplierProductsId(String supplierProductsId) {
		this.supplierProductsId = supplierProductsId;
	}

	public String getCategoryParamId() {
		return categoryParamId;
	}

	public void setCategoryParamId(String categoryParamId) {
		this.categoryParamId = categoryParamId;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}