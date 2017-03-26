package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

public class OBResultsInfo {
    private String id;

    /**
     * 定型产品ID
     */
    private String productId;

    private OBProduct obProduct;
    
    private String biddingId;
    
    /**
     * 限价
     */
    private BigDecimal limitPrice;
    /**
     * 单个商品采购数量
     */
    private Integer resultsNumber;

    /**
     * 自报单价金额
     */
    private BigDecimal myOfferMoney;

    /**
     * 单个商品总价
     */
    private BigDecimal dealMoney;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

    /**
     * 所属供应商
     */
    private String supplierId;

    /**
     * 竞价标题信息
     */
    private String projectId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getBiddingId() {
        return biddingId;
    }

    public void setBiddingId(String biddingId) {
        this.biddingId = biddingId == null ? null : biddingId.trim();
    }

    public Integer getResultsNumber() {
        return resultsNumber;
    }

    public void setResultsNumber(Integer resultsNumber) {
        this.resultsNumber = resultsNumber;
    }

    public BigDecimal getMyOfferMoney() {
        return myOfferMoney;
    }

    public void setMyOfferMoney(BigDecimal myOfferMoney) {
        this.myOfferMoney = myOfferMoney;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

	public OBProduct getObProduct() {
		return obProduct;
	}

	public void setObProduct(OBProduct obProduct) {
		this.obProduct = obProduct;
	}

	public BigDecimal getLimitPrice() {
		return limitPrice;
	}

	public void setLimitPrice(BigDecimal limitPrice) {
		this.limitPrice = limitPrice;
	}
	
}