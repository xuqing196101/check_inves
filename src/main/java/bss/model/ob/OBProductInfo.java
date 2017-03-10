package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

public class OBProductInfo {
    private String id;

    private String productId;

    private OBProduct obProduct;
    
    private BigDecimal limitedPrice;

    private Integer purchaseCount;

    private String projectId;

    private Date createdAt;

    private Date updatedAt;

    private String remark;
    
    private String createrId;
    
    private OBProject obProject;
    
    //成交供应商 数量
    private Integer closingSupplier;
    //合格供应商数量
    private Integer qualifiedSupplier;
    
    
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

	public OBProject getObProject() {
		return obProject;
	}

	public void setObProject(OBProject obProject) {
		this.obProject = obProject;
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

    public Integer getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(Integer purchaseCount) {
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
    
}