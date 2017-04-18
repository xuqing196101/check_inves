package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:WagesPayable 
* @Description: 应付工资明细
* @author Shen Zhenfei
* @date 2016-10-17上午10:29:07
 */
public class WagesPayable {
	
    private String id;

    private ContractProduct contractProduct;
    
    @NotBlank(message = "部门不能为空")
    private String department;

    @NotBlank(message = "一级项目不能为空")
    private String firsetProduct;

    @NotBlank(message = "二级项目不能为空")
    private String secondProduct;

    private BigDecimal tyaProduceUser;

    private BigDecimal tyaWorkshopUser;

    private BigDecimal tyaManageUser;

    private BigDecimal tyaOtherUser;

    private BigDecimal tyaTotal;

    private BigDecimal oyaProduceUser;

    private BigDecimal oyaWorkshopUser;

    private BigDecimal oyaManageUser;

    private BigDecimal oyaOtherUser;

    private BigDecimal oyaTotal;

    private BigDecimal newProduceUser;

    private BigDecimal newWorkshopUser;

    private BigDecimal newManageUser;

    private BigDecimal newOtherUser;

    private BigDecimal newTotal;

    private BigDecimal approvedProduceUser;

    private BigDecimal approvedWorkshopUser;

    private BigDecimal approvedManageUser;

    private BigDecimal approvedOtherUser;

    private BigDecimal approvedTotal;

    private BigDecimal checkProduceUser;

    private BigDecimal checkWorkshopUser;

    private BigDecimal checkManageUser;

    private BigDecimal checkOtherUser;

    private BigDecimal checkTotal;

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

	public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getFirsetProduct() {
        return firsetProduct;
    }

    public void setFirsetProduct(String firsetProduct) {
        this.firsetProduct = firsetProduct == null ? null : firsetProduct.trim();
    }

    public String getSecondProduct() {
        return secondProduct;
    }

    public void setSecondProduct(String secondProduct) {
        this.secondProduct = secondProduct == null ? null : secondProduct.trim();
    }

	

	public BigDecimal getTyaProduceUser() {
		return tyaProduceUser;
	}

	public void setTyaProduceUser(BigDecimal tyaProduceUser) {
		this.tyaProduceUser = tyaProduceUser;
	}

	public BigDecimal getTyaWorkshopUser() {
		return tyaWorkshopUser;
	}

	public void setTyaWorkshopUser(BigDecimal tyaWorkshopUser) {
		this.tyaWorkshopUser = tyaWorkshopUser;
	}

	public BigDecimal getTyaManageUser() {
		return tyaManageUser;
	}

	public void setTyaManageUser(BigDecimal tyaManageUser) {
		this.tyaManageUser = tyaManageUser;
	}

	public BigDecimal getTyaOtherUser() {
		return tyaOtherUser;
	}

	public void setTyaOtherUser(BigDecimal tyaOtherUser) {
		this.tyaOtherUser = tyaOtherUser;
	}

	public BigDecimal getTyaTotal() {
		return tyaTotal;
	}

	public void setTyaTotal(BigDecimal tyaTotal) {
		this.tyaTotal = tyaTotal;
	}

	public BigDecimal getOyaProduceUser() {
		return oyaProduceUser;
	}

	public void setOyaProduceUser(BigDecimal oyaProduceUser) {
		this.oyaProduceUser = oyaProduceUser;
	}

	public BigDecimal getOyaWorkshopUser() {
		return oyaWorkshopUser;
	}

	public void setOyaWorkshopUser(BigDecimal oyaWorkshopUser) {
		this.oyaWorkshopUser = oyaWorkshopUser;
	}

	public BigDecimal getOyaManageUser() {
		return oyaManageUser;
	}

	public void setOyaManageUser(BigDecimal oyaManageUser) {
		this.oyaManageUser = oyaManageUser;
	}

	public BigDecimal getOyaOtherUser() {
		return oyaOtherUser;
	}

	public void setOyaOtherUser(BigDecimal oyaOtherUser) {
		this.oyaOtherUser = oyaOtherUser;
	}

	public BigDecimal getOyaTotal() {
		return oyaTotal;
	}

	public void setOyaTotal(BigDecimal oyaTotal) {
		this.oyaTotal = oyaTotal;
	}

	public BigDecimal getNewProduceUser() {
		return newProduceUser;
	}

	public void setNewProduceUser(BigDecimal newProduceUser) {
		this.newProduceUser = newProduceUser;
	}

	public BigDecimal getNewWorkshopUser() {
		return newWorkshopUser;
	}

	public void setNewWorkshopUser(BigDecimal newWorkshopUser) {
		this.newWorkshopUser = newWorkshopUser;
	}

	public BigDecimal getNewManageUser() {
		return newManageUser;
	}

	public void setNewManageUser(BigDecimal newManageUser) {
		this.newManageUser = newManageUser;
	}

	public BigDecimal getNewOtherUser() {
		return newOtherUser;
	}

	public void setNewOtherUser(BigDecimal newOtherUser) {
		this.newOtherUser = newOtherUser;
	}

	public BigDecimal getNewTotal() {
		return newTotal;
	}

	public void setNewTotal(BigDecimal newTotal) {
		this.newTotal = newTotal;
	}

	public BigDecimal getApprovedProduceUser() {
		return approvedProduceUser;
	}

	public void setApprovedProduceUser(BigDecimal approvedProduceUser) {
		this.approvedProduceUser = approvedProduceUser;
	}

	public BigDecimal getApprovedWorkshopUser() {
		return approvedWorkshopUser;
	}

	public void setApprovedWorkshopUser(BigDecimal approvedWorkshopUser) {
		this.approvedWorkshopUser = approvedWorkshopUser;
	}

	public BigDecimal getApprovedManageUser() {
		return approvedManageUser;
	}

	public void setApprovedManageUser(BigDecimal approvedManageUser) {
		this.approvedManageUser = approvedManageUser;
	}

	public BigDecimal getApprovedOtherUser() {
		return approvedOtherUser;
	}

	public void setApprovedOtherUser(BigDecimal approvedOtherUser) {
		this.approvedOtherUser = approvedOtherUser;
	}

	public BigDecimal getApprovedTotal() {
		return approvedTotal;
	}

	public void setApprovedTotal(BigDecimal approvedTotal) {
		this.approvedTotal = approvedTotal;
	}

	public BigDecimal getCheckProduceUser() {
		return checkProduceUser;
	}

	public void setCheckProduceUser(BigDecimal checkProduceUser) {
		this.checkProduceUser = checkProduceUser;
	}

	public BigDecimal getCheckWorkshopUser() {
		return checkWorkshopUser;
	}

	public void setCheckWorkshopUser(BigDecimal checkWorkshopUser) {
		this.checkWorkshopUser = checkWorkshopUser;
	}

	public BigDecimal getCheckManageUser() {
		return checkManageUser;
	}

	public void setCheckManageUser(BigDecimal checkManageUser) {
		this.checkManageUser = checkManageUser;
	}

	public BigDecimal getCheckOtherUser() {
		return checkOtherUser;
	}

	public void setCheckOtherUser(BigDecimal checkOtherUser) {
		this.checkOtherUser = checkOtherUser;
	}

	public BigDecimal getCheckTotal() {
		return checkTotal;
	}

	public void setCheckTotal(BigDecimal checkTotal) {
		this.checkTotal = checkTotal;
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

}