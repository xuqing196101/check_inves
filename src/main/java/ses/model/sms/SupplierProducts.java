package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierProducts implements Serializable {
	private static final long serialVersionUID = 1701778954157245386L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 所属类别 T_SES_BMS_CATEGORY
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.CATEGORY_ID
	 * </pre>
	 */
	private String itemId;

	/**
	 * <pre>
	 * 供应商ID T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 产品名称
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.NAME
	 * </pre>
	 */
	private String name;

	/**
	 * <pre>
	 * 品牌
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.BRAND
	 * </pre>
	 */
	private String brand;

	/**
	 * <pre>
	 * 规格型号
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.MODELS
	 * </pre>
	 */
	private String models;

	/**
	 * <pre>
	 * 尺寸
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.SIZE
	 * </pre>
	 */
	private String proSize;

	/**
	 * <pre>
	 * 生产产地
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.ORGIN
	 * </pre>
	 */
	private String orgin;

	/**
	 * <pre>
	 * 保质期
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.EXPIRATION_DATE
	 * </pre>
	 */
	private Date expirationDate;

	/**
	 * <pre>
	 * 生产商
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.PRODUCER
	 * </pre>
	 */
	private String producer;

	/**
	 * <pre>
	 * 参考价格
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.REFERENCE_PRICE
	 * </pre>
	 */
	private String referencePrice;

	/**
	 * <pre>
	 * 产品图片
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.PRODUCT_PIC
	 * </pre>
	 */
	private String productPic;

	/**
	 * <pre>
	 * 产品二维码
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.QR_CODE
	 * </pre>
	 */
	private String qrCode;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_PRODUCTS.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private List<ProductParam> listProductParams = new ArrayList<>();

	private String categoryName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public String getProSize() {
		return proSize;
	}

	public void setProSize(String proSize) {
		this.proSize = proSize;
	}

	public String getOrgin() {
		return orgin;
	}

	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getReferencePrice() {
		return referencePrice;
	}

	public void setReferencePrice(String referencePrice) {
		this.referencePrice = referencePrice;
	}

	public String getProductPic() {
		return productPic;
	}

	public void setProductPic(String productPic) {
		this.productPic = productPic;
	}

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
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

	public List<ProductParam> getListProductParams() {
		return listProductParams;
	}

	public void setListProductParams(List<ProductParam> listProductParams) {
		this.listProductParams = listProductParams;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}