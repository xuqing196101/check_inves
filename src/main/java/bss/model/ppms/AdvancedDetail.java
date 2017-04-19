package bss.model.ppms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import ses.model.oms.PurchaseDep;

/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   Administrator
 * @version  
 * @since
 * @see
 */
public class AdvancedDetail {
	private String id;

	private String serialNumber;

	private String department;

	private String goodsName;

	private String stand;

	private String qualitStand;

	private String item;

	private BigDecimal purchaseCount;

	private BigDecimal price;

	private BigDecimal budget;

	private String deliverDate;

	private String purchaseType;

	private String supplier;

	private String isFreeTax;

	private String goodsUse;

	private String useUnit;

	private String advancedProject;

	private PurchaseDep purchaseDep;
	
	private String packageId;
	
	private String memo;
	
	private String brand;
	
	private String parentId;
	
	private Date createdAt;
	
	private Date updateAt;
	
	private String status;
	
	private Integer position;
	
	private String requiredId;
	
	private AdvancedPackages packages;
	
	/**报价需要回显 -添加三个字段*/
	 /**
     * 包报价总金额
     */
    private BigDecimal total;
    
    /**
     * 成交时间
     */
    private String deliveryTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 单价
     */
    private BigDecimal quotePrice;
    
    /**
     * 供应商id
     */
    private String supplierId;
    
    private String planNo;
    
    private Integer isTurnUp;
    
    private String organization;
    
    private String uniqueId;
    
    private String progressBar;

	public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

	public AdvancedPackages getPackages() {
        return packages;
    }

    public void setPackages(AdvancedPackages packages) {
        this.packages = packages;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStand() {
		return stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getQualitStand() {
		return qualitStand;
	}

	public void setQualitStand(String qualitStand) {
		this.qualitStand = qualitStand;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getIsFreeTax() {
		return isFreeTax;
	}

	public void setIsFreeTax(String isFreeTax) {
		this.isFreeTax = isFreeTax;
	}

	public String getGoodsUse() {
		return goodsUse;
	}

	public void setGoodsUse(String goodsUse) {
		this.goodsUse = goodsUse;
	}

	public String getUseUnit() {
		return useUnit;
	}

	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}


    public String getAdvancedProject() {
        return advancedProject;
    }

    public void setAdvancedProject(String advancedProject) {
        this.advancedProject = advancedProject;
    }

    public PurchaseDep getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(PurchaseDep purchaseDep) {
		this.purchaseDep = purchaseDep;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getRequiredId() {
        return requiredId;
    }

    public void setRequiredId(String requiredId) {
        this.requiredId = requiredId;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Integer getIsTurnUp() {
        return isTurnUp;
    }

    public void setIsTurnUp(Integer isTurnUp) {
        this.isTurnUp = isTurnUp;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public String getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(String progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advancedProject == null) ? 0 : advancedProject.hashCode());
        result = prime * result + ((brand == null) ? 0 : brand.hashCode());
        result = prime * result + ((budget == null) ? 0 : budget.hashCode());
        result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
        result = prime * result + ((deliverDate == null) ? 0 : deliverDate.hashCode());
        result = prime * result + ((deliveryTime == null) ? 0 : deliveryTime.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((goodsName == null) ? 0 : goodsName.hashCode());
        result = prime * result + ((goodsUse == null) ? 0 : goodsUse.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((isFreeTax == null) ? 0 : isFreeTax.hashCode());
        result = prime * result + ((isTurnUp == null) ? 0 : isTurnUp.hashCode());
        result = prime * result + ((item == null) ? 0 : item.hashCode());
        result = prime * result + ((memo == null) ? 0 : memo.hashCode());
        result = prime * result + ((organization == null) ? 0 : organization.hashCode());
        result = prime * result + ((packageId == null) ? 0 : packageId.hashCode());
        result = prime * result + ((packages == null) ? 0 : packages.hashCode());
        result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
        result = prime * result + ((planNo == null) ? 0 : planNo.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((purchaseCount == null) ? 0 : purchaseCount.hashCode());
        result = prime * result + ((purchaseDep == null) ? 0 : purchaseDep.hashCode());
        result = prime * result + ((purchaseType == null) ? 0 : purchaseType.hashCode());
        result = prime * result + ((qualitStand == null) ? 0 : qualitStand.hashCode());
        result = prime * result + ((quotePrice == null) ? 0 : quotePrice.hashCode());
        result = prime * result + ((remark == null) ? 0 : remark.hashCode());
        result = prime * result + ((requiredId == null) ? 0 : requiredId.hashCode());
        result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
        result = prime * result + ((stand == null) ? 0 : stand.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
        result = prime * result + ((supplierId == null) ? 0 : supplierId.hashCode());
        result = prime * result + ((total == null) ? 0 : total.hashCode());
        result = prime * result + ((uniqueId == null) ? 0 : uniqueId.hashCode());
        result = prime * result + ((updateAt == null) ? 0 : updateAt.hashCode());
        result = prime * result + ((useUnit == null) ? 0 : useUnit.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        AdvancedDetail other = (AdvancedDetail)obj;
        if (advancedProject == null) {
            if (other.advancedProject != null) return false;
        } else if (!advancedProject.equals(other.advancedProject)) return false;
        if (brand == null) {
            if (other.brand != null) return false;
        } else if (!brand.equals(other.brand)) return false;
        if (budget == null) {
            if (other.budget != null) return false;
        } else if (!budget.equals(other.budget)) return false;
        if (createdAt == null) {
            if (other.createdAt != null) return false;
        } else if (!createdAt.equals(other.createdAt)) return false;
        if (deliverDate == null) {
            if (other.deliverDate != null) return false;
        } else if (!deliverDate.equals(other.deliverDate)) return false;
        if (deliveryTime == null) {
            if (other.deliveryTime != null) return false;
        } else if (!deliveryTime.equals(other.deliveryTime)) return false;
        if (department == null) {
            if (other.department != null) return false;
        } else if (!department.equals(other.department)) return false;
        if (goodsName == null) {
            if (other.goodsName != null) return false;
        } else if (!goodsName.equals(other.goodsName)) return false;
        if (goodsUse == null) {
            if (other.goodsUse != null) return false;
        } else if (!goodsUse.equals(other.goodsUse)) return false;
        if (id == null) {
            if (other.id != null) return false;
        } else if (!id.equals(other.id)) return false;
        if (isFreeTax == null) {
            if (other.isFreeTax != null) return false;
        } else if (!isFreeTax.equals(other.isFreeTax)) return false;
        if (isTurnUp == null) {
            if (other.isTurnUp != null) return false;
        } else if (!isTurnUp.equals(other.isTurnUp)) return false;
        if (item == null) {
            if (other.item != null) return false;
        } else if (!item.equals(other.item)) return false;
        if (memo == null) {
            if (other.memo != null) return false;
        } else if (!memo.equals(other.memo)) return false;
        if (organization == null) {
            if (other.organization != null) return false;
        } else if (!organization.equals(other.organization)) return false;
        if (packageId == null) {
            if (other.packageId != null) return false;
        } else if (!packageId.equals(other.packageId)) return false;
        if (packages == null) {
            if (other.packages != null) return false;
        } else if (!packages.equals(other.packages)) return false;
        if (parentId == null) {
            if (other.parentId != null) return false;
        } else if (!parentId.equals(other.parentId)) return false;
        if (planNo == null) {
            if (other.planNo != null) return false;
        } else if (!planNo.equals(other.planNo)) return false;
        if (position == null) {
            if (other.position != null) return false;
        } else if (!position.equals(other.position)) return false;
        if (price == null) {
            if (other.price != null) return false;
        } else if (!price.equals(other.price)) return false;
        if (purchaseCount == null) {
            if (other.purchaseCount != null) return false;
        } else if (!purchaseCount.equals(other.purchaseCount)) return false;
        if (purchaseDep == null) {
            if (other.purchaseDep != null) return false;
        } else if (!purchaseDep.equals(other.purchaseDep)) return false;
        if (purchaseType == null) {
            if (other.purchaseType != null) return false;
        } else if (!purchaseType.equals(other.purchaseType)) return false;
        if (qualitStand == null) {
            if (other.qualitStand != null) return false;
        } else if (!qualitStand.equals(other.qualitStand)) return false;
        if (quotePrice == null) {
            if (other.quotePrice != null) return false;
        } else if (!quotePrice.equals(other.quotePrice)) return false;
        if (remark == null) {
            if (other.remark != null) return false;
        } else if (!remark.equals(other.remark)) return false;
        if (requiredId == null) {
            if (other.requiredId != null) return false;
        } else if (!requiredId.equals(other.requiredId)) return false;
        if (serialNumber == null) {
            if (other.serialNumber != null) return false;
        } else if (!serialNumber.equals(other.serialNumber)) return false;
        if (stand == null) {
            if (other.stand != null) return false;
        } else if (!stand.equals(other.stand)) return false;
        if (status == null) {
            if (other.status != null) return false;
        } else if (!status.equals(other.status)) return false;
        if (supplier == null) {
            if (other.supplier != null) return false;
        } else if (!supplier.equals(other.supplier)) return false;
        if (supplierId == null) {
            if (other.supplierId != null) return false;
        } else if (!supplierId.equals(other.supplierId)) return false;
        if (total == null) {
            if (other.total != null) return false;
        } else if (!total.equals(other.total)) return false;
        if (uniqueId == null) {
            if (other.uniqueId != null) return false;
        } else if (!uniqueId.equals(other.uniqueId)) return false;
        if (updateAt == null) {
            if (other.updateAt != null) return false;
        } else if (!updateAt.equals(other.updateAt)) return false;
        if (useUnit == null) {
            if (other.useUnit != null) return false;
        } else if (!useUnit.equals(other.useUnit)) return false;
        return true;
    }
    
    
}