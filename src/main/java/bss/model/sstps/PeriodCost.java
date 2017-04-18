package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Title:PeriodCost 
* @Description: 期间费用明细
* @author Shen Zhenfei
* @date 2016-10-18上午9:11:01
 */
public class PeriodCost {
	
    private String id;

    private ContractProduct contractProduct;

    private String projectName;

    private BigDecimal tyaQuoteprice;

    private BigDecimal oyaQuoteprice;

    private BigDecimal newQuoteprice;

    private BigDecimal auditApproval;

    private BigDecimal checkApproval;

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

	public BigDecimal getTyaQuoteprice() {
		return tyaQuoteprice;
	}

	public void setTyaQuoteprice(BigDecimal tyaQuoteprice) {
		this.tyaQuoteprice = tyaQuoteprice;
	}

	public BigDecimal getOyaQuoteprice() {
		return oyaQuoteprice;
	}

	public void setOyaQuoteprice(BigDecimal oyaQuoteprice) {
		this.oyaQuoteprice = oyaQuoteprice;
	}

	public BigDecimal getNewQuoteprice() {
		return newQuoteprice;
	}

	public void setNewQuoteprice(BigDecimal newQuoteprice) {
		this.newQuoteprice = newQuoteprice;
	}

	public BigDecimal getAuditApproval() {
		return auditApproval;
	}

	public void setAuditApproval(BigDecimal auditApproval) {
		this.auditApproval = auditApproval;
	}

	public BigDecimal getCheckApproval() {
		return checkApproval;
	}

	public void setCheckApproval(BigDecimal checkApproval) {
		this.checkApproval = checkApproval;
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