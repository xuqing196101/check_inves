package bss.model.sstps;

import java.util.Date;

/**
* @Title:AccessoriesCon 
* @Description: 原、辅材料工艺定额消耗明细
* @author Shen Zhenfei
* @date 2016-10-13下午2:18:01
 */
public class AccessoriesCon {
	
    private String id;

    private ContractProduct contractProduct;

    private String stuffName;

    private String norm;

    private String paperCode;

    private Integer workAmout;

    private Integer workWeight;

    private Integer workWeightTotal;

    private Integer workPrice;

    private Integer workMoney;

    private Integer consumeAmout;

    private Integer consumeWeight;

    private Integer consumeWeightTotal;

    private Integer consumePrice;

    private Integer consumeMoney;

    private Integer subtractMoney;

    private Integer checkMoney;

    private String supplyUnit;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

    private Integer productNature;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public ContractProduct getContractProduct() {
		return contractProduct;
	}

	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}

	public String getStuffName() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName = stuffName == null ? null : stuffName.trim();
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm == null ? null : norm.trim();
    }

    public String getPaperCode() {
        return paperCode;
    }

    public void setPaperCode(String paperCode) {
        this.paperCode = paperCode == null ? null : paperCode.trim();
    }

	public Integer getWorkAmout() {
		return workAmout;
	}

	public void setWorkAmout(Integer workAmout) {
		this.workAmout = workAmout;
	}

	public Integer getWorkWeight() {
		return workWeight;
	}

	public void setWorkWeight(Integer workWeight) {
		this.workWeight = workWeight;
	}

	public Integer getWorkWeightTotal() {
		return workWeightTotal;
	}

	public void setWorkWeightTotal(Integer workWeightTotal) {
		this.workWeightTotal = workWeightTotal;
	}

	public Integer getWorkPrice() {
		return workPrice;
	}

	public void setWorkPrice(Integer workPrice) {
		this.workPrice = workPrice;
	}

	public Integer getWorkMoney() {
		return workMoney;
	}

	public void setWorkMoney(Integer workMoney) {
		this.workMoney = workMoney;
	}

	public Integer getConsumeAmout() {
		return consumeAmout;
	}

	public void setConsumeAmout(Integer consumeAmout) {
		this.consumeAmout = consumeAmout;
	}

	public Integer getConsumeWeight() {
		return consumeWeight;
	}

	public void setConsumeWeight(Integer consumeWeight) {
		this.consumeWeight = consumeWeight;
	}

	public Integer getConsumeWeightTotal() {
		return consumeWeightTotal;
	}

	public void setConsumeWeightTotal(Integer consumeWeightTotal) {
		this.consumeWeightTotal = consumeWeightTotal;
	}

	public Integer getConsumePrice() {
		return consumePrice;
	}

	public void setConsumePrice(Integer consumePrice) {
		this.consumePrice = consumePrice;
	}

	public Integer getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(Integer consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public Integer getSubtractMoney() {
		return subtractMoney;
	}

	public void setSubtractMoney(Integer subtractMoney) {
		this.subtractMoney = subtractMoney;
	}

	public Integer getCheckMoney() {
		return checkMoney;
	}

	public void setCheckMoney(Integer checkMoney) {
		this.checkMoney = checkMoney;
	}

	public String getSupplyUnit() {
		return supplyUnit;
	}

	public void setSupplyUnit(String supplyUnit) {
		this.supplyUnit = supplyUnit;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getProductNature() {
		return productNature;
	}

	public void setProductNature(Integer productNature) {
		this.productNature = productNature;
	}
   
}