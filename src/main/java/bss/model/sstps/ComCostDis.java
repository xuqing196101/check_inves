package bss.model.sstps;

import java.util.Date;

/**
* @Title:ComCostDis 
* @Description: 综合费用分配计算明细
* @author Shen Zhenfei
* @date 2016-10-18下午2:46:53
 */
public class ComCostDis {
	
    private String id;

    private ContractProduct contractProduct;

    private String projectName;

    private Integer tyaAmount;

    private Integer tyaFee;

    private Integer oyaAmout;

    private Integer oyaFee;

    private Integer newAmount;

    private Integer newFee;

    private Integer subtractWentDutch;

    private Integer subtractFee;

    private Integer checkWentDutch;

    private Integer checkFee;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private Integer tyaActual;
    
    private Integer oyaActual;
    
    private Integer newActual;
    
    private Integer subtractActual;
    
    private Integer checkActual;
    
    private Integer status;
    
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

	public Integer getTyaAmount() {
		return tyaAmount;
	}

	public void setTyaAmount(Integer tyaAmount) {
		this.tyaAmount = tyaAmount;
	}

	public Integer getTyaFee() {
		return tyaFee;
	}

	public void setTyaFee(Integer tyaFee) {
		this.tyaFee = tyaFee;
	}

	public Integer getOyaAmout() {
		return oyaAmout;
	}

	public void setOyaAmout(Integer oyaAmout) {
		this.oyaAmout = oyaAmout;
	}

	public Integer getOyaFee() {
		return oyaFee;
	}

	public void setOyaFee(Integer oyaFee) {
		this.oyaFee = oyaFee;
	}

	public Integer getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(Integer newAmount) {
		this.newAmount = newAmount;
	}

	public Integer getNewFee() {
		return newFee;
	}

	public void setNewFee(Integer newFee) {
		this.newFee = newFee;
	}

	public Integer getSubtractWentDutch() {
		return subtractWentDutch;
	}

	public void setSubtractWentDutch(Integer subtractWentDutch) {
		this.subtractWentDutch = subtractWentDutch;
	}

	public Integer getSubtractFee() {
		return subtractFee;
	}

	public void setSubtractFee(Integer subtractFee) {
		this.subtractFee = subtractFee;
	}

	public Integer getCheckWentDutch() {
		return checkWentDutch;
	}

	public void setCheckWentDutch(Integer checkWentDutch) {
		this.checkWentDutch = checkWentDutch;
	}

	public Integer getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(Integer checkFee) {
		this.checkFee = checkFee;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

	public Integer getTyaActual() {
		return tyaActual;
	}

	public void setTyaActual(Integer tyaActual) {
		this.tyaActual = tyaActual;
	}

	public Integer getOyaActual() {
		return oyaActual;
	}

	public void setOyaActual(Integer oyaActual) {
		this.oyaActual = oyaActual;
	}

	public Integer getNewActual() {
		return newActual;
	}

	public void setNewActual(Integer newActual) {
		this.newActual = newActual;
	}

	public Integer getSubtractActual() {
		return subtractActual;
	}

	public void setSubtractActual(Integer subtractActual) {
		this.subtractActual = subtractActual;
	}

	public Integer getCheckActual() {
		return checkActual;
	}

	public void setCheckActual(Integer checkActual) {
		this.checkActual = checkActual;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}