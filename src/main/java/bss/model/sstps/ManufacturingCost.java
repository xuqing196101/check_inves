package bss.model.sstps;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:ManufacturingCost 
* @Description: 制造费用明细
* @author Shen Zhenfei
* @date 2016-10-17下午2:40:59
 */
public class ManufacturingCost {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "项目名称不能为空")
    private String projectName;

    private Integer tyaQuoteprice;

    private Integer oyaQuoteprice;

    private Integer newQuoteprice;

    private Integer auditApproval;

    private Integer checkApproval;

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

	public Integer getTyaQuoteprice() {
		return tyaQuoteprice;
	}

	public void setTyaQuoteprice(Integer tyaQuoteprice) {
		this.tyaQuoteprice = tyaQuoteprice;
	}

	public Integer getOyaQuoteprice() {
		return oyaQuoteprice;
	}

	public void setOyaQuoteprice(Integer oyaQuoteprice) {
		this.oyaQuoteprice = oyaQuoteprice;
	}

	public Integer getNewQuoteprice() {
		return newQuoteprice;
	}

	public void setNewQuoteprice(Integer newQuoteprice) {
		this.newQuoteprice = newQuoteprice;
	}

	public Integer getAuditApproval() {
		return auditApproval;
	}

	public void setAuditApproval(Integer auditApproval) {
		this.auditApproval = auditApproval;
	}

	public Integer getCheckApproval() {
		return checkApproval;
	}

	public void setCheckApproval(Integer checkApproval) {
		this.checkApproval = checkApproval;
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