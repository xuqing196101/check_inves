package bss.model.cs;

import java.math.BigDecimal;

public class ContractRequired {
    private String id;
    
    private String planNo;//编号

    private String goodsName;//物资名称

    private String brand;//品牌商标

    private String stand;//规格型号

    private String item;//计量单位

    private BigDecimal purchaseCount;//采购数量
    
    private String purchaseCount_string;

    private BigDecimal price;//单价
    
    private String price_string;

    private BigDecimal amount;//合计金额
    
    private String amount_string;

    private String deliverDate;//交付时间

    private String memo;//备注

    private String contractId;
    
    private Integer isDeleted;

    private String detailId;
    public String getPurchaseCount_string() {
		return purchaseCount_string;
	}

	public void setPurchaseCount_string(String purchaseCount_string) {
		this.purchaseCount_string = purchaseCount_string;
	}

	public String getPrice_string() {
		return price_string;
	}

	public void setPrice_string(String price_string) {
		this.price_string = price_string;
	}

	public String getAmount_string() {
		return amount_string;
	}

	public void setAmount_string(String amount_string) {
		this.amount_string = amount_string;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand == null ? null : stand.trim();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    
    public BigDecimal getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(BigDecimal purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	
}