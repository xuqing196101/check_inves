package bss.model.sstps;

import java.util.Date;

import bss.model.cs.ContractRequired;

/**
* @Title:ContractProduct 
* @Description: 审价产品表
* @author Shen Zhenfei
* @date 2016-10-10上午9:57:59
 */
public class ContractProduct {
	
    private String id;

    private AppraisalContract appraisalContract;

    private String name;

    private Date createdAt;

    private Date updatedAt;
    
    private Integer offer;
    
    private Integer auditOffer;
    private String requirdeId;
    
    private ContractRequired contractRequired;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public AppraisalContract getAppraisalContract() {
		return appraisalContract;
	}

	public void setAppraisalContract(AppraisalContract appraisalContract) {
		this.appraisalContract = appraisalContract;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

	public Integer getOffer() {
		return offer;
	}

	public void setOffer(Integer offer) {
		this.offer = offer;
	}

	public Integer getAuditOffer() {
		return auditOffer;
	}

	public void setAuditOffer(Integer auditOffer) {
		this.auditOffer = auditOffer;
	}

	public String getRequirdeId() {
		return requirdeId;
	}

	public void setRequirdeId(String requirdeId) {
		this.requirdeId = requirdeId;
	}

	public ContractRequired getContractRequired() {
		return contractRequired;
	}

	public void setContractRequired(ContractRequired contractRequired) {
		this.contractRequired = contractRequired;
	}
    
}