package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

public class OBResultsInfo {
    private String id;

    /**
     * 定型产品ID
     */
    private String productId;
    /**
     * 自定义 产品 名称
     * */
    private String productName;

    private OBProduct obProduct;
    
    /**
     * 标识1 第一次 竞价    2 第二次竞价
     */
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
    
    private String dealMoneyStr;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    //自定义 第二轮 剩余数量
    private Integer surplusNumber;

    /**
     * 所属供应商
     */
    private String supplierId;

    /**
     * 竞价标题信息
     */
    private String projectId;

    private BigDecimal dealTotalMoney;


    public void setDealTotalMoney(BigDecimal dealTotalMoney) {
        this.dealTotalMoney = dealTotalMoney;
    }

    public BigDecimal getDealTotalMoney() {
        return dealTotalMoney;
    }

    public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

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

	public String getDealMoneyStr() {
		return dealMoneyStr;
	}

	public void setDealMoneyStr(String dealMoneyStr) {
		this.dealMoneyStr = dealMoneyStr;
	}

	@Override
	public String toString() {
		return "OBResultsInfo [id=" + id + ", productId=" + productId
				+ ", productName=" + productName + ", obProduct=" + obProduct
				+ ", biddingId=" + biddingId + ", limitPrice=" + limitPrice
				+ ", resultsNumber=" + resultsNumber + ", myOfferMoney="
				+ myOfferMoney + ", dealMoney=" + dealMoney + ", dealMoneyStr="
				+ dealMoneyStr + ", remark=" + remark + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + ", supplierId="
				+ supplierId + ", projectId=" + projectId + "]";
	}
	
}