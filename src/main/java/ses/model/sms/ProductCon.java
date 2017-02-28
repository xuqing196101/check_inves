package ses.model.sms;

/**
 * Description: 供应商产品库管理实体类
 * 
 * @author Li ChenHao
 * @version 2017-2-28
 * @since JDK1.7
 */


public class ProductCon {
	/**
     * 主键
     */
	private String id;
	
	/**
     * 类型
     */
	private String productType;
	
	/**
     * 品牌
     */
	private String productTrademark;
	
	/**
     * 名称
     */
	private String productName;
	
	/**
     * 价格
     */
	private Integer productPrice;
	
	/**
     * 型号
     */
	private String productModel;
	
	/**
     * 协议价
     */
	private Integer productNegoPrice;
	
	/**
     * 库存
     */
	private Integer productInventory;
	
	/**
     * SKU
     */
	private String produtSKU;
	
	/**
     * 参数
     */
	private String productParameter;
	
	/**
     * 包装清单
     */
	private String productRepertoire;
	
	/**
     * 售后
     */
	private String afterSales;
	
	/**
     * 商品介绍
     */
	private String productPresentation;
	
	/**
     * 状态
     */
	private Integer productStatus;
	
	

	public Integer getProductStatus() {
		return productStatus;
	}

	public void setProductStatus(Integer productStatus) {
		this.productStatus = productStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductTrademark() {
		return productTrademark;
	}

	public void setProductTrademark(String productTrademark) {
		this.productTrademark = productTrademark;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public Integer getProductNegoPrice() {
		return productNegoPrice;
	}

	public void setProductNegoPrice(Integer productNegoPrice) {
		this.productNegoPrice = productNegoPrice;
	}

	public Integer getProductInventory() {
		return productInventory;
	}

	public void setProductInventory(Integer productInventory) {
		this.productInventory = productInventory;
	}

	public String getProdutSKU() {
		return produtSKU;
	}

	public void setProdutSKU(String produtSKU) {
		this.produtSKU = produtSKU;
	}

	public String getProductParameter() {
		return productParameter;
	}

	public void setProductParameter(String productParameter) {
		this.productParameter = productParameter;
	}

	public String getProductRepertoire() {
		return productRepertoire;
	}

	public void setProductRepertoire(String productRepertoire) {
		this.productRepertoire = productRepertoire;
	}

	public String getAfterSales() {
		return afterSales;
	}

	public void setAfterSales(String afterSales) {
		this.afterSales = afterSales;
	}

	public String getProductPresentation() {
		return productPresentation;
	}

	public void setProductPresentation(String productPresentation) {
		this.productPresentation = productPresentation;
	}
	
	

}
