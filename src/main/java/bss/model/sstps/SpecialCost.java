package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:SpecialCost 
* @Description: 专项费用明细
* @author Shen Zhenfei
* @date 2016-10-14下午3:18:22
 */
public class SpecialCost {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @NotBlank(message = "项目明细不能为空")
    private String productDetal;

    @NotBlank(message = "名称不能为空")
    private String name;

    @NotBlank(message = "规格型号不能为空")
    private String norm;

    private String measuringUnit;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal money;

    private BigDecimal proportionAmout;

    private BigDecimal proportionPrice;

    private BigDecimal subtractMoney;

    private BigDecimal checkMoney;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private BigDecimal approvedMoney;
    
    private BigDecimal checkApprovedMoney;
    private String parentId;
    private String serialNumber;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getProportionAmout() {
		return proportionAmout;
	}

	public void setProportionAmout(BigDecimal proportionAmout) {
		this.proportionAmout = proportionAmout;
	}

	public BigDecimal getProportionPrice() {
		return proportionPrice;
	}

	public void setProportionPrice(BigDecimal proportionPrice) {
		this.proportionPrice = proportionPrice;
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

	

	public BigDecimal getApprovedMoney() {
		return approvedMoney;
	}

	public void setApprovedMoney(BigDecimal approvedMoney) {
		this.approvedMoney = approvedMoney;
	}

	public BigDecimal getCheckApprovedMoney() {
		return checkApprovedMoney;
	}

	public void setCheckApprovedMoney(BigDecimal checkApprovedMoney) {
		this.checkApprovedMoney = checkApprovedMoney;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

}