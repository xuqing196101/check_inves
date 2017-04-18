package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:BurningPower 
* @Description: 燃烧动力费用明细
* @author Shen Zhenfei
* @date 2016-10-14下午4:14:29
 */
public class BurningPower {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "一级项目不能为空")
    private String firsetProduct;

    @NotBlank(message = "二级项目不能为空")
    private String secondProduct;

    @NotBlank(message = "三级项目不能为空")
    private String thirdProduct;

    @NotBlank(message = "计量单位不能为空")
    private String unit;

    private BigDecimal tyaAcount;

    private BigDecimal tyaAvgPrice;

    private BigDecimal tyaMoney;

    private BigDecimal oyaAcount;

    private BigDecimal oyaAvgPrice;

    private BigDecimal oyaMoney;

    private BigDecimal newAcount;

    private BigDecimal newAvgPrice;

    private BigDecimal newMoney;

    private BigDecimal approvedMoney;

    private BigDecimal checkMoney;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    private Integer parentLevel;
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

	public String getFirsetProduct() {
		return firsetProduct;
	}

	public void setFirsetProduct(String firsetProduct) {
		this.firsetProduct = firsetProduct;
	}

	public String getSecondProduct() {
		return secondProduct;
	}

	public void setSecondProduct(String secondProduct) {
		this.secondProduct = secondProduct;
	}

	public String getThirdProduct() {
		return thirdProduct;
	}

	public void setThirdProduct(String thirdProduct) {
		this.thirdProduct = thirdProduct;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public BigDecimal getTyaAcount() {
		return tyaAcount;
	}

	public void setTyaAcount(BigDecimal tyaAcount) {
		this.tyaAcount = tyaAcount;
	}

	public BigDecimal getTyaAvgPrice() {
		return tyaAvgPrice;
	}

	public void setTyaAvgPrice(BigDecimal tyaAvgPrice) {
		this.tyaAvgPrice = tyaAvgPrice;
	}

	public BigDecimal getTyaMoney() {
		return tyaMoney;
	}

	public void setTyaMoney(BigDecimal tyaMoney) {
		this.tyaMoney = tyaMoney;
	}

	public BigDecimal getOyaAcount() {
		return oyaAcount;
	}

	public void setOyaAcount(BigDecimal oyaAcount) {
		this.oyaAcount = oyaAcount;
	}

	public BigDecimal getOyaAvgPrice() {
		return oyaAvgPrice;
	}

	public void setOyaAvgPrice(BigDecimal oyaAvgPrice) {
		this.oyaAvgPrice = oyaAvgPrice;
	}

	public BigDecimal getOyaMoney() {
		return oyaMoney;
	}

	public void setOyaMoney(BigDecimal oyaMoney) {
		this.oyaMoney = oyaMoney;
	}

	public BigDecimal getNewAcount() {
		return newAcount;
	}

	public void setNewAcount(BigDecimal newAcount) {
		this.newAcount = newAcount;
	}

	public BigDecimal getNewAvgPrice() {
		return newAvgPrice;
	}

	public void setNewAvgPrice(BigDecimal newAvgPrice) {
		this.newAvgPrice = newAvgPrice;
	}

	public BigDecimal getNewMoney() {
		return newMoney;
	}

	public void setNewMoney(BigDecimal newMoney) {
		this.newMoney = newMoney;
	}

	public BigDecimal getApprovedMoney() {
		return approvedMoney;
	}

	public void setApprovedMoney(BigDecimal approvedMoney) {
		this.approvedMoney = approvedMoney;
	}

	public BigDecimal getCheckMoney() {
		return checkMoney;
	}

	public void setCheckMoney(BigDecimal checkMoney) {
		this.checkMoney = checkMoney;
	}

	public Integer getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(Integer parentLevel) {
		this.parentLevel = parentLevel;
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