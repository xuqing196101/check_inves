package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
* @ClassName: OBResultSubtabulation 
* @Description: 竞价结果子表实体类
* @author Easong
* @date 2017年3月28日 下午8:15:10 
*
 */
public class OBResultSubtabulation {
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
	public String toString() {
		return "OBResultSubtabulation [proportion=" + proportion + ", status="
				+ status + ", ranking=" + ranking + ", id=" + id
				+ ", projectResultId=" + projectResultId + ", resultNumber="
				+ resultNumber + ", dealMoney=" + dealMoney + ", totalMoney="
				+ totalMoney + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", myOfferMoney=" + myOfferMoney
				+ ", supplierId=" + supplierId + ", projectId=" + projectId
				+ ", productId=" + productId + "]";
	}
    
}