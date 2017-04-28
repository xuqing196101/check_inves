package bss.model.ob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OBProductInfo implements Serializable {
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	//主键
    private String id;
    //产品id
    private String productId;
    //产品信息表
    private OBProduct obProduct;
    //限价（元）
    private BigDecimal limitedPrice;
   //采购数量
    private BigDecimal purchaseCount;
    //竞价项目id
    private String projectId;
    //创建时间
    private Date createdAt;
    //更新时间
    private Date updatedAt;

    /**备注信息**/
    private String remark;
    //创建人id
    private String createrId;
    //竞价信息表
    private OBProject obProject;
    
    //成交供应商 数量
    private Integer closingSupplier;
    //合格供应商数量
    private Integer qualifiedSupplier;
    
    /**单个商品的总金额=现价 *采购数量**/
	private BigDecimal totalMoney;
	
	/**显示￥100,000,00样式**/
	private String totalMoneyStr;
    
    public OBProject getObProject() {
		return obProject;
	}

	public void setObProject(OBProject obProject) {
		this.obProject = obProject;
	}

	public Integer getClosingSupplier() {
		return closingSupplier;
	}

	public void setClosingSupplier(Integer closingSupplier) {
		this.closingSupplier = closingSupplier;
	}

	public Integer getQualifiedSupplier() {
		return qualifiedSupplier;
	}

	public void setQualifiedSupplier(Integer qualifiedSupplier) {
		this.qualifiedSupplier = qualifiedSupplier;
	}

	
	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
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

    public BigDecimal getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(BigDecimal limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public BigDecimal getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(BigDecimal purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public OBProduct getObProduct() {
		return obProduct;
	}

	public void setObProduct(OBProduct obProduct) {
		this.obProduct = obProduct;
	}

	public BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getTotalMoneyStr() {
		return totalMoneyStr;
	}

	public void setTotalMoneyStr(String totalMoneyStr) {
		this.totalMoneyStr = totalMoneyStr;
	}

	@Override
	public String toString() {
		return "OBProductInfo [id=" + id + ", productId=" + productId
				+ ", obProduct=" + obProduct + ", limitedPrice=" + limitedPrice
				+ ", purchaseCount=" + purchaseCount + ", projectId="
				+ projectId + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", remark=" + remark + ", createrId=" + createrId
				+ ", obProject=" + obProject + ", closingSupplier="
				+ closingSupplier + ", qualifiedSupplier=" + qualifiedSupplier
				+ ", totalMoney=" + totalMoney + ", totalMoneyStr="
				+ totalMoneyStr + "]";
	}
    
}