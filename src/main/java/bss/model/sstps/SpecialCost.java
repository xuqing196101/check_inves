package bss.model.sstps;

import java.util.Date;

/**
* @Title:SpecialCost 
* @Description: 专项费用明细
* @author Shen Zhenfei
* @date 2016-10-14下午3:18:22
 */
public class SpecialCost {
	
    private String id;

    private ContractProduct contractProduct;

    private String projectName;

    private String productDetal;

    private String name;

    private String norm;

    private String measuringUnit;

    private Integer amount;

    private Integer price;

    private Integer money;

    private Integer proportionAmout;

    private Integer proportionPrice;

    private Integer subtractMoney;

    private Integer checkMoney;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private Integer approvedMoney;
    
    private Integer checkApprovedMoney;

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProductDetal() {
		return productDetal;
	}

	public void setProductDetal(String productDetal) {
		this.productDetal = productDetal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	public Integer getProportionAmout() {
		return proportionAmout;
	}

	public void setProportionAmout(Integer proportionAmout) {
		this.proportionAmout = proportionAmout;
	}

	public Integer getProportionPrice() {
		return proportionPrice;
	}

	public void setProportionPrice(Integer proportionPrice) {
		this.proportionPrice = proportionPrice;
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

	public Integer getApprovedMoney() {
		return approvedMoney;
	}

	public void setApprovedMoney(Integer approvedMoney) {
		this.approvedMoney = approvedMoney;
	}

	public Integer getCheckApprovedMoney() {
		return checkApprovedMoney;
	}

	public void setCheckApprovedMoney(Integer checkApprovedMoney) {
		this.checkApprovedMoney = checkApprovedMoney;
	}

}