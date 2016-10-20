package bss.model.sstps;

import java.util.Date;

/**
* @Title:WagesPayable 
* @Description: 应付工资明细
* @author Shen Zhenfei
* @date 2016-10-17上午10:29:07
 */
public class WagesPayable {
	
    private String id;

    private ContractProduct contractProduct;
    
    private String department;

    private String firsetProduct;

    private String secondProduct;

    private Integer tyaProduceUser;

    private Integer tyaWorkshopUser;

    private Integer tyaManageUser;

    private Integer tyaOtherUser;

    private Integer tyaTotal;

    private Integer oyaProduceUser;

    private Integer oyaWorkshopUser;

    private Integer oyaManageUser;

    private Integer oyaOtherUser;

    private Integer oyaTotal;

    private Integer newProduceUser;

    private Integer newWorkshopUser;

    private Integer newManageUser;

    private Integer newOtherUser;

    private Integer newTotal;

    private Integer approvedProduceUser;

    private Integer approvedWorkshopUser;

    private Integer approvedManageUser;

    private Integer approvedOtherUser;

    private Integer approvedTotal;

    private Integer checkProduceUser;

    private Integer checkWorkshopUser;

    private Integer checkManageUser;

    private Integer checkOtherUser;

    private Integer checkTotal;

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

	public Integer getTyaProduceUser() {
		return tyaProduceUser;
	}

	public void setTyaProduceUser(Integer tyaProduceUser) {
		this.tyaProduceUser = tyaProduceUser;
	}

	public Integer getTyaWorkshopUser() {
		return tyaWorkshopUser;
	}

	public void setTyaWorkshopUser(Integer tyaWorkshopUser) {
		this.tyaWorkshopUser = tyaWorkshopUser;
	}

	public Integer getTyaManageUser() {
		return tyaManageUser;
	}

	public void setTyaManageUser(Integer tyaManageUser) {
		this.tyaManageUser = tyaManageUser;
	}

	public Integer getTyaOtherUser() {
		return tyaOtherUser;
	}

	public void setTyaOtherUser(Integer tyaOtherUser) {
		this.tyaOtherUser = tyaOtherUser;
	}

	public Integer getTyaTotal() {
		return tyaTotal;
	}

	public void setTyaTotal(Integer tyaTotal) {
		this.tyaTotal = tyaTotal;
	}

	public Integer getOyaProduceUser() {
		return oyaProduceUser;
	}

	public void setOyaProduceUser(Integer oyaProduceUser) {
		this.oyaProduceUser = oyaProduceUser;
	}

	public Integer getOyaWorkshopUser() {
		return oyaWorkshopUser;
	}

	public void setOyaWorkshopUser(Integer oyaWorkshopUser) {
		this.oyaWorkshopUser = oyaWorkshopUser;
	}

	public Integer getOyaManageUser() {
		return oyaManageUser;
	}

	public void setOyaManageUser(Integer oyaManageUser) {
		this.oyaManageUser = oyaManageUser;
	}

	public Integer getOyaOtherUser() {
		return oyaOtherUser;
	}

	public void setOyaOtherUser(Integer oyaOtherUser) {
		this.oyaOtherUser = oyaOtherUser;
	}

	public Integer getOyaTotal() {
		return oyaTotal;
	}

	public void setOyaTotal(Integer oyaTotal) {
		this.oyaTotal = oyaTotal;
	}

	public Integer getNewProduceUser() {
		return newProduceUser;
	}

	public void setNewProduceUser(Integer newProduceUser) {
		this.newProduceUser = newProduceUser;
	}

	public Integer getNewWorkshopUser() {
		return newWorkshopUser;
	}

	public void setNewWorkshopUser(Integer newWorkshopUser) {
		this.newWorkshopUser = newWorkshopUser;
	}

	public Integer getNewManageUser() {
		return newManageUser;
	}

	public void setNewManageUser(Integer newManageUser) {
		this.newManageUser = newManageUser;
	}

	public Integer getNewOtherUser() {
		return newOtherUser;
	}

	public void setNewOtherUser(Integer newOtherUser) {
		this.newOtherUser = newOtherUser;
	}

	public Integer getNewTotal() {
		return newTotal;
	}

	public void setNewTotal(Integer newTotal) {
		this.newTotal = newTotal;
	}

	public Integer getApprovedProduceUser() {
		return approvedProduceUser;
	}

	public void setApprovedProduceUser(Integer approvedProduceUser) {
		this.approvedProduceUser = approvedProduceUser;
	}

	public Integer getApprovedWorkshopUser() {
		return approvedWorkshopUser;
	}

	public void setApprovedWorkshopUser(Integer approvedWorkshopUser) {
		this.approvedWorkshopUser = approvedWorkshopUser;
	}

	public Integer getApprovedManageUser() {
		return approvedManageUser;
	}

	public void setApprovedManageUser(Integer approvedManageUser) {
		this.approvedManageUser = approvedManageUser;
	}

	public Integer getApprovedOtherUser() {
		return approvedOtherUser;
	}

	public void setApprovedOtherUser(Integer approvedOtherUser) {
		this.approvedOtherUser = approvedOtherUser;
	}

	public Integer getApprovedTotal() {
		return approvedTotal;
	}

	public void setApprovedTotal(Integer approvedTotal) {
		this.approvedTotal = approvedTotal;
	}

	public Integer getCheckProduceUser() {
		return checkProduceUser;
	}

	public void setCheckProduceUser(Integer checkProduceUser) {
		this.checkProduceUser = checkProduceUser;
	}

	public Integer getCheckWorkshopUser() {
		return checkWorkshopUser;
	}

	public void setCheckWorkshopUser(Integer checkWorkshopUser) {
		this.checkWorkshopUser = checkWorkshopUser;
	}

	public Integer getCheckManageUser() {
		return checkManageUser;
	}

	public void setCheckManageUser(Integer checkManageUser) {
		this.checkManageUser = checkManageUser;
	}

	public Integer getCheckOtherUser() {
		return checkOtherUser;
	}

	public void setCheckOtherUser(Integer checkOtherUser) {
		this.checkOtherUser = checkOtherUser;
	}

	public Integer getCheckTotal() {
		return checkTotal;
	}

	public void setCheckTotal(Integer checkTotal) {
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

}