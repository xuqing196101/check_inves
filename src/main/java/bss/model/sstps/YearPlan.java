package bss.model.sstps;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:YearPlan 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-17下午3:46:21
 */
public class YearPlan {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    @NotBlank(message = "产品单位不能为空")
    private String productName;

    @NotBlank(message = "计量单位不能为空")
    private String measuringUnit;

    private Integer tyaHourUnit;

    private Integer tyaInvestAcount;

    private Integer tyaHourTotal;

    private Integer oyaHourUnit;

    private Integer oyaInvestAcount;

    private Integer oyaHourTotal;

    private Integer newHourUnit;

    private Integer newInvestAcount;

    private Integer newHourTotal;

    private Integer approvedHourUnit;

    private Integer approvedInvestAcount;

    private Integer approvedHourTotal;

    private Integer checkHourUnit;

    private Integer checkInvestAcount;

    private Integer checkHourTotal;

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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public Integer getTyaHourUnit() {
		return tyaHourUnit;
	}

	public void setTyaHourUnit(Integer tyaHourUnit) {
		this.tyaHourUnit = tyaHourUnit;
	}

	public Integer getTyaHourTotal() {
		return tyaHourTotal;
	}

	public void setTyaHourTotal(Integer tyaHourTotal) {
		this.tyaHourTotal = tyaHourTotal;
	}

	public Integer getOyaHourUnit() {
		return oyaHourUnit;
	}

	public void setOyaHourUnit(Integer oyaHourUnit) {
		this.oyaHourUnit = oyaHourUnit;
	}

	public Integer getOyaInvestAcount() {
		return oyaInvestAcount;
	}

	public void setOyaInvestAcount(Integer oyaInvestAcount) {
		this.oyaInvestAcount = oyaInvestAcount;
	}

	public Integer getOyaHourTotal() {
		return oyaHourTotal;
	}

	public void setOyaHourTotal(Integer oyaHourTotal) {
		this.oyaHourTotal = oyaHourTotal;
	}

	public Integer getNewHourUnit() {
		return newHourUnit;
	}

	public void setNewHourUnit(Integer newHourUnit) {
		this.newHourUnit = newHourUnit;
	}

	public Integer getNewInvestAcount() {
		return newInvestAcount;
	}

	public void setNewInvestAcount(Integer newInvestAcount) {
		this.newInvestAcount = newInvestAcount;
	}

	public Integer getNewHourTotal() {
		return newHourTotal;
	}

	public void setNewHourTotal(Integer newHourTotal) {
		this.newHourTotal = newHourTotal;
	}

	public Integer getApprovedHourUnit() {
		return approvedHourUnit;
	}

	public void setApprovedHourUnit(Integer approvedHourUnit) {
		this.approvedHourUnit = approvedHourUnit;
	}

	public Integer getApprovedInvestAcount() {
		return approvedInvestAcount;
	}

	public void setApprovedInvestAcount(Integer approvedInvestAcount) {
		this.approvedInvestAcount = approvedInvestAcount;
	}

	public Integer getApprovedHourTotal() {
		return approvedHourTotal;
	}

	public void setApprovedHourTotal(Integer approvedHourTotal) {
		this.approvedHourTotal = approvedHourTotal;
	}

	public Integer getCheckHourUnit() {
		return checkHourUnit;
	}

	public void setCheckHourUnit(Integer checkHourUnit) {
		this.checkHourUnit = checkHourUnit;
	}

	public Integer getCheckInvestAcount() {
		return checkInvestAcount;
	}

	public void setCheckInvestAcount(Integer checkInvestAcount) {
		this.checkInvestAcount = checkInvestAcount;
	}

	public Integer getCheckHourTotal() {
		return checkHourTotal;
	}

	public void setCheckHourTotal(Integer checkHourTotal) {
		this.checkHourTotal = checkHourTotal;
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

	public Integer getTyaInvestAcount() {
		return tyaInvestAcount;
	}

	public void setTyaInvestAcount(Integer tyaInvestAcount) {
		this.tyaInvestAcount = tyaInvestAcount;
	}
	
   
}