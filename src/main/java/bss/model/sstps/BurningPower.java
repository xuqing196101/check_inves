package bss.model.sstps;

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

    private Integer tyaAcount;

    private Integer tyaAvgPrice;

    private Integer tyaMoney;

    private Integer oyaAcount;

    private Integer oyaAvgPrice;

    private Integer oyaMoney;

    private Integer newAcount;

    private Integer newAvgPrice;

    private Integer newMoney;

    private Integer approvedMoney;

    private Integer checkMoney;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

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

	public Integer getTyaAcount() {
		return tyaAcount;
	}

	public void setTyaAcount(Integer tyaAcount) {
		this.tyaAcount = tyaAcount;
	}

	public Integer getTyaAvgPrice() {
		return tyaAvgPrice;
	}

	public void setTyaAvgPrice(Integer tyaAvgPrice) {
		this.tyaAvgPrice = tyaAvgPrice;
	}

	public Integer getTyaMoney() {
		return tyaMoney;
	}

	public void setTyaMoney(Integer tyaMoney) {
		this.tyaMoney = tyaMoney;
	}

	public Integer getOyaAcount() {
		return oyaAcount;
	}

	public void setOyaAcount(Integer oyaAcount) {
		this.oyaAcount = oyaAcount;
	}

	public Integer getOyaAvgPrice() {
		return oyaAvgPrice;
	}

	public void setOyaAvgPrice(Integer oyaAvgPrice) {
		this.oyaAvgPrice = oyaAvgPrice;
	}

	public Integer getOyaMoney() {
		return oyaMoney;
	}

	public void setOyaMoney(Integer oyaMoney) {
		this.oyaMoney = oyaMoney;
	}

	public Integer getNewAcount() {
		return newAcount;
	}

	public void setNewAcount(Integer newAcount) {
		this.newAcount = newAcount;
	}

	public Integer getNewAvgPrice() {
		return newAvgPrice;
	}

	public void setNewAvgPrice(Integer newAvgPrice) {
		this.newAvgPrice = newAvgPrice;
	}

	public Integer getNewMoney() {
		return newMoney;
	}

	public void setNewMoney(Integer newMoney) {
		this.newMoney = newMoney;
	}

	public Integer getApprovedMoney() {
		return approvedMoney;
	}

	public void setApprovedMoney(Integer approvedMoney) {
		this.approvedMoney = approvedMoney;
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

}