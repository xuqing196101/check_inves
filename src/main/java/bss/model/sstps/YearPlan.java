package bss.model.sstps;

import java.math.BigDecimal;
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

    private BigDecimal tyaHourUnit;

    private BigDecimal tyaInvestAcount;

    private BigDecimal tyaHourTotal;

    private BigDecimal oyaHourUnit;

    private BigDecimal oyaInvestAcount;

    private BigDecimal oyaHourTotal;

    private BigDecimal newHourUnit;

    private BigDecimal newInvestAcount;

    private BigDecimal newHourTotal;

    private BigDecimal approvedHourUnit;

    private BigDecimal approvedInvestAcount;

    private BigDecimal approvedHourTotal;

    private BigDecimal checkHourUnit;

    private BigDecimal checkInvestAcount;

    private BigDecimal checkHourTotal;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
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

	

	public BigDecimal getTyaHourUnit() {
		return tyaHourUnit;
	}

	public void setTyaHourUnit(BigDecimal tyaHourUnit) {
		this.tyaHourUnit = tyaHourUnit;
	}

	public BigDecimal getTyaHourTotal() {
		return tyaHourTotal;
	}

	public void setTyaHourTotal(BigDecimal tyaHourTotal) {
		this.tyaHourTotal = tyaHourTotal;
	}

	public BigDecimal getOyaHourUnit() {
		return oyaHourUnit;
	}

	public void setOyaHourUnit(BigDecimal oyaHourUnit) {
		this.oyaHourUnit = oyaHourUnit;
	}

	public BigDecimal getOyaInvestAcount() {
		return oyaInvestAcount;
	}

	public void setOyaInvestAcount(BigDecimal oyaInvestAcount) {
		this.oyaInvestAcount = oyaInvestAcount;
	}

	public BigDecimal getOyaHourTotal() {
		return oyaHourTotal;
	}

	public void setOyaHourTotal(BigDecimal oyaHourTotal) {
		this.oyaHourTotal = oyaHourTotal;
	}

	public BigDecimal getNewHourUnit() {
		return newHourUnit;
	}

	public void setNewHourUnit(BigDecimal newHourUnit) {
		this.newHourUnit = newHourUnit;
	}

	public BigDecimal getNewInvestAcount() {
		return newInvestAcount;
	}

	public void setNewInvestAcount(BigDecimal newInvestAcount) {
		this.newInvestAcount = newInvestAcount;
	}

	public BigDecimal getNewHourTotal() {
		return newHourTotal;
	}

	public void setNewHourTotal(BigDecimal newHourTotal) {
		this.newHourTotal = newHourTotal;
	}

	public BigDecimal getApprovedHourUnit() {
		return approvedHourUnit;
	}

	public void setApprovedHourUnit(BigDecimal approvedHourUnit) {
		this.approvedHourUnit = approvedHourUnit;
	}

	public BigDecimal getApprovedInvestAcount() {
		return approvedInvestAcount;
	}

	public void setApprovedInvestAcount(BigDecimal approvedInvestAcount) {
		this.approvedInvestAcount = approvedInvestAcount;
	}

	public BigDecimal getApprovedHourTotal() {
		return approvedHourTotal;
	}

	public void setApprovedHourTotal(BigDecimal approvedHourTotal) {
		this.approvedHourTotal = approvedHourTotal;
	}

	public BigDecimal getCheckHourUnit() {
		return checkHourUnit;
	}

	public void setCheckHourUnit(BigDecimal checkHourUnit) {
		this.checkHourUnit = checkHourUnit;
	}

	public BigDecimal getCheckInvestAcount() {
		return checkInvestAcount;
	}

	public void setCheckInvestAcount(BigDecimal checkInvestAcount) {
		this.checkInvestAcount = checkInvestAcount;
	}

	public BigDecimal getCheckHourTotal() {
		return checkHourTotal;
	}

	public void setCheckHourTotal(BigDecimal checkHourTotal) {
		this.checkHourTotal = checkHourTotal;
	}

	public void setTyaInvestAcount(BigDecimal tyaInvestAcount) {
		this.tyaInvestAcount = tyaInvestAcount;
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

	public BigDecimal getTyaInvestAcount() {
		return tyaInvestAcount;
	}

	
   
}