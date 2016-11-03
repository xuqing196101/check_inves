package bss.model.sstps;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:OutproductCon 
* @Description: 外购成品件消耗定额明细
* @author Shen Zhenfei
* @date 2016-10-14上午9:42:26
 */
public class OutproductCon {
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "成品件名称不能为空")
    private String finishedName;

    @NotBlank(message = "规格型号不能为空")
    private String norm;

    @NotBlank(message = "图纸位置号(代号)不能为空")
    private String paperCode;

    private String workAmout;

    private Integer workWeight;

    private Integer workWeightTotal;

    private Integer workPrice;

    private Integer workMoney;

    private Integer consumeAmout;

    private Integer consumePrice;

    private Integer consumeMoney;

    private Integer subtractMoney;

    private Integer checkMoney;

    private String supplyUnit;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ContractProduct getContractProduct() {
		return contractProduct;
	}

	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}

	public String getFinishedName() {
		return finishedName;
	}

	public void setFinishedName(String finishedName) {
		this.finishedName = finishedName;
	}

	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	public String getPaperCode() {
		return paperCode;
	}

	public void setPaperCode(String paperCode) {
		this.paperCode = paperCode;
	}

	public String getWorkAmout() {
		return workAmout;
	}

	public void setWorkAmout(String workAmout) {
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

    
}