package bss.model.ob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import ses.model.sms.Supplier;

/**
 * 
* @ClassName: OBResultSubtabulation 
* @Description: 竞价结果子表实体类
* @author Easong
* @date 2017年3月28日 下午8:15:10 
*
 */
public class OBResultSubtabulation implements Comparable<OBResultSubtabulation>,Serializable{
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	//比例
	private Integer proportion;
	//状态
	private Integer status;
	//排名
	private Integer ranking;
	/**
	 * 主键
	 */
    private String id;

    /**
     * 竞价结果表id
     */
    private String projectResultId;

    /**
     * 竞价结果数量
     */
    private BigDecimal resultNumber;

    /**
     * 成交单价
     */
    private BigDecimal dealMoney;

    /**
     * 单价总额
     */
    private BigDecimal totalMoney;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;
    /**
     * 自报单价
     * @return
     */
    private BigDecimal myOfferMoney;
    /**
     * 供应商
     * @return
     */
    private String supplierId;
    /**
     * 竞价id
     * @return
     */
    private String projectId;
    /**
     * 产品id
     * @return
     */
    private String productId;
    
    /*
     * 供应商
     */
    private Supplier supplier;
    
    /**
     * 产品
     */
    private OBProduct product;
    
    
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public OBProduct getProduct() {
		return product;
	}

	public void setProduct(OBProduct product) {
		this.product = product;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getProportion() {
		return proportion;
	}

	public void setProportion(Integer proportion) {
		this.proportion = proportion;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public BigDecimal getMyOfferMoney() {
		return myOfferMoney;
	}

	public void setMyOfferMoney(BigDecimal myOfferMoney) {
		this.myOfferMoney = myOfferMoney;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectResultId() {
        return projectResultId;
    }

    public void setProjectResultId(String projectResultId) {
        this.projectResultId = projectResultId == null ? null : projectResultId.trim();
    }

    public BigDecimal getResultNumber() {
        return resultNumber;
    }

    public void setResultNumber(BigDecimal resultNumber) {
        this.resultNumber = resultNumber;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((dealMoney == null) ? 0 : dealMoney.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((myOfferMoney == null) ? 0 : myOfferMoney.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((projectId == null) ? 0 : projectId.hashCode());
		result = prime * result
				+ ((projectResultId == null) ? 0 : projectResultId.hashCode());
		result = prime * result
				+ ((proportion == null) ? 0 : proportion.hashCode());
		result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
		result = prime * result
				+ ((resultNumber == null) ? 0 : resultNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((supplierId == null) ? 0 : supplierId.hashCode());
		result = prime * result
				+ ((totalMoney == null) ? 0 : totalMoney.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OBResultSubtabulation other = (OBResultSubtabulation) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (dealMoney == null) {
			if (other.dealMoney != null)
				return false;
		} else if (!dealMoney.equals(other.dealMoney))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (myOfferMoney == null) {
			if (other.myOfferMoney != null)
				return false;
		} else if (!myOfferMoney.equals(other.myOfferMoney))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (projectId == null) {
			if (other.projectId != null)
				return false;
		} else if (!projectId.equals(other.projectId))
			return false;
		if (projectResultId == null) {
			if (other.projectResultId != null)
				return false;
		} else if (!projectResultId.equals(other.projectResultId))
			return false;
		if (proportion == null) {
			if (other.proportion != null)
				return false;
		} else if (!proportion.equals(other.proportion))
			return false;
		if (ranking == null) {
			if (other.ranking != null)
				return false;
		} else if (!ranking.equals(other.ranking))
			return false;
		if (resultNumber == null) {
			if (other.resultNumber != null)
				return false;
		} else if (!resultNumber.equals(other.resultNumber))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		if (totalMoney == null) {
			if (other.totalMoney != null)
				return false;
		} else if (!totalMoney.equals(other.totalMoney))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	@Override
	public int compareTo(OBResultSubtabulation o) {
		 int i = this.getRanking() - o.getRanking();//按照排名进行排序
	     return i;  
	}
    
}