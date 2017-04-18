package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:AccessoriesCon 
* @Description: 原、辅材料工艺定额消耗明细
* @author Shen Zhenfei
* @date 2016-10-13下午2:18:01
 */
public class AccessoriesCon {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "材料名称不能为空")
    private String stuffName;

    @NotBlank(message = "规格型号不能为空")
    private String norm;

    @NotBlank(message = "图纸位置号不能为空")
    private String paperCode;

    private BigDecimal workAmout;

    private BigDecimal workWeight;

    private BigDecimal workWeightTotal;

    private BigDecimal workPrice;

    private BigDecimal workMoney;

    private BigDecimal consumeAmout;

    private BigDecimal consumeWeight;

    private BigDecimal consumeWeightTotal;

    private BigDecimal consumePrice;

    private BigDecimal consumeMoney;

    private BigDecimal subtractMoney;

    private BigDecimal checkMoney;

    @NotBlank(message = "供货单位不能为空")
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

	

	public BigDecimal getWorkAmout() {
		return workAmout;
	}

	public void setWorkAmout(BigDecimal workAmout) {
		this.workAmout = workAmout;
	}

	public BigDecimal getWorkWeight() {
		return workWeight;
	}

	public void setWorkWeight(BigDecimal workWeight) {
		this.workWeight = workWeight;
	}

	public BigDecimal getWorkWeightTotal() {
		return workWeightTotal;
	}

	public void setWorkWeightTotal(BigDecimal workWeightTotal) {
		this.workWeightTotal = workWeightTotal;
	}

	public BigDecimal getWorkPrice() {
		return workPrice;
	}

	public void setWorkPrice(BigDecimal workPrice) {
		this.workPrice = workPrice;
	}

	public BigDecimal getWorkMoney() {
		return workMoney;
	}

	public void setWorkMoney(BigDecimal workMoney) {
		this.workMoney = workMoney;
	}

	public BigDecimal getConsumeAmout() {
		return consumeAmout;
	}

	public void setConsumeAmout(BigDecimal consumeAmout) {
		this.consumeAmout = consumeAmout;
	}

	public BigDecimal getConsumeWeight() {
		return consumeWeight;
	}

	public void setConsumeWeight(BigDecimal consumeWeight) {
		this.consumeWeight = consumeWeight;
	}

	public BigDecimal getConsumeWeightTotal() {
		return consumeWeightTotal;
	}

	public void setConsumeWeightTotal(BigDecimal consumeWeightTotal) {
		this.consumeWeightTotal = consumeWeightTotal;
	}

	public BigDecimal getConsumePrice() {
		return consumePrice;
	}

	public void setConsumePrice(BigDecimal consumePrice) {
		this.consumePrice = consumePrice;
	}

	public BigDecimal getConsumeMoney() {
		return consumeMoney;
	}

	public void setConsumeMoney(BigDecimal consumeMoney) {
		this.consumeMoney = consumeMoney;
	}

	public BigDecimal getSubtractMoney() {
		return subtractMoney;
	}

	public void setSubtractMoney(BigDecimal subtractMoney) {
		this.subtractMoney = subtractMoney;
	}

	public BigDecimal getCheckMoney() {
		return checkMoney;
	}

	public void setCheckMoney(BigDecimal checkMoney) {
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