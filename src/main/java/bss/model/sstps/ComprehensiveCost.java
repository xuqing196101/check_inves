package bss.model.sstps;

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

    private Integer singleOffer;

    private String additResult;

    private Integer difference;

    private double reduce;

    private Integer checkResult;

    private Integer checkDifference;

    private Integer checkReduce;

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

    public Integer getSingleOffer() {
		return singleOffer;
	}

	public void setSingleOffer(Integer singleOffer) {
		this.singleOffer = singleOffer;
	}

	public String getAdditResult() {
		return additResult;
	}

	public void setAdditResult(String additResult) {
		this.additResult = additResult;
	}

	public Integer getDifference() {
		return difference;
	}

	public void setDifference(Integer difference) {
		this.difference = difference;
	}

	public double getReduce() {
		return reduce;
	}

	public void setReduce(double reduce) {
		this.reduce = reduce;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public Integer getCheckDifference() {
		return checkDifference;
	}

	public void setCheckDifference(Integer checkDifference) {
		this.checkDifference = checkDifference;
	}

	public Integer getCheckReduce() {
		return checkReduce;
	}

	public void setCheckReduce(Integer checkReduce) {
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