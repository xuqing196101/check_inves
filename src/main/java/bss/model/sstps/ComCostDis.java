package bss.model.sstps;

import java.math.BigDecimal;
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

    private BigDecimal tyaAmount;

    private BigDecimal tyaFee;

    private BigDecimal oyaAmout;

    private BigDecimal oyaFee;

    private BigDecimal newAmount;

    private BigDecimal newFee;

    private BigDecimal subtractWentDutch;

    private BigDecimal subtractFee;

    private BigDecimal checkWentDutch;

    private BigDecimal checkFee;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
    private BigDecimal tyaActual;
    
    private BigDecimal oyaActual;
    
    private BigDecimal newActual;
    
    private BigDecimal subtractActual;
    
    private BigDecimal checkActual;
    
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

	

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getTyaAmount() {
		return tyaAmount;
	}

	public void setTyaAmount(BigDecimal tyaAmount) {
		this.tyaAmount = tyaAmount;
	}

	public BigDecimal getTyaFee() {
		return tyaFee;
	}

	public void setTyaFee(BigDecimal tyaFee) {
		this.tyaFee = tyaFee;
	}

	public BigDecimal getOyaAmout() {
		return oyaAmout;
	}

	public void setOyaAmout(BigDecimal oyaAmout) {
		this.oyaAmout = oyaAmout;
	}

	public BigDecimal getOyaFee() {
		return oyaFee;
	}

	public void setOyaFee(BigDecimal oyaFee) {
		this.oyaFee = oyaFee;
	}

	public BigDecimal getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(BigDecimal newAmount) {
		this.newAmount = newAmount;
	}

	public BigDecimal getNewFee() {
		return newFee;
	}

	public void setNewFee(BigDecimal newFee) {
		this.newFee = newFee;
	}

	public BigDecimal getSubtractWentDutch() {
		return subtractWentDutch;
	}

	public void setSubtractWentDutch(BigDecimal subtractWentDutch) {
		this.subtractWentDutch = subtractWentDutch;
	}

	public BigDecimal getSubtractFee() {
		return subtractFee;
	}

	public void setSubtractFee(BigDecimal subtractFee) {
		this.subtractFee = subtractFee;
	}

	public BigDecimal getCheckWentDutch() {
		return checkWentDutch;
	}

	public void setCheckWentDutch(BigDecimal checkWentDutch) {
		this.checkWentDutch = checkWentDutch;
	}

	public BigDecimal getCheckFee() {
		return checkFee;
	}

	public void setCheckFee(BigDecimal checkFee) {
		this.checkFee = checkFee;
	}

	public BigDecimal getTyaActual() {
		return tyaActual;
	}

	public void setTyaActual(BigDecimal tyaActual) {
		this.tyaActual = tyaActual;
	}

	public BigDecimal getOyaActual() {
		return oyaActual;
	}

	public void setOyaActual(BigDecimal oyaActual) {
		this.oyaActual = oyaActual;
	}

	public BigDecimal getNewActual() {
		return newActual;
	}

	public void setNewActual(BigDecimal newActual) {
		this.newActual = newActual;
	}

	public BigDecimal getSubtractActual() {
		return subtractActual;
	}

	public void setSubtractActual(BigDecimal subtractActual) {
		this.subtractActual = subtractActual;
	}

	public BigDecimal getCheckActual() {
		return checkActual;
	}

	public void setCheckActual(BigDecimal checkActual) {
		this.checkActual = checkActual;
	}
    
}