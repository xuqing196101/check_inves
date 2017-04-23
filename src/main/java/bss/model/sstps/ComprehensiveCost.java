package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

/**
* @Title:ComprehensiveCost 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-20上午11:13:43
 */
public class ComprehensiveCost {
	
    private String id;

    private ContractProduct contractProduct;

    private String projectName;

    private String secondProject;

    private BigDecimal singleOffer;

    private BigDecimal additResult;

    private BigDecimal difference;

    private BigDecimal reduce;

    private BigDecimal checkResult;

    private BigDecimal checkDifference;

    private BigDecimal checkReduce;

    private String remark;

    private Date createdAt;

    private Date updatedAt;
    
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
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getSecondProject() {
        return secondProject;
    }

    public void setSecondProject(String secondProject) {
        this.secondProject = secondProject == null ? null : secondProject.trim();
    }


	public BigDecimal getReduce() {
		return reduce;
	}

	public void setReduce(BigDecimal reduce) {
		this.reduce = reduce;
	}

	

	public BigDecimal getSingleOffer() {
		return singleOffer;
	}

	public void setSingleOffer(BigDecimal singleOffer) {
		this.singleOffer = singleOffer;
	}

	public BigDecimal getAdditResult() {
		return additResult;
	}

	public void setAdditResult(BigDecimal additResult) {
		this.additResult = additResult;
	}

	public BigDecimal getDifference() {
		return difference;
	}

	public void setDifference(BigDecimal difference) {
		this.difference = difference;
	}

	public BigDecimal getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(BigDecimal checkResult) {
		this.checkResult = checkResult;
	}

	public BigDecimal getCheckDifference() {
		return checkDifference;
	}

	public void setCheckDifference(BigDecimal checkDifference) {
		this.checkDifference = checkDifference;
	}

	public BigDecimal getCheckReduce() {
		return checkReduce;
	}

	public void setCheckReduce(BigDecimal checkReduce) {
		this.checkReduce = checkReduce;
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
    
}