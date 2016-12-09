package bss.model.cs;

import java.util.Date;

public class Performance {
    private String id;
    
    private String deliverySchedule;//交货进度

    private String fundsPaid;//资金支付百分比

    private Date draftSignedAt;//合同草稿签订时间

    private Date formalSignedAt;//正式合同签订时间

    private Date delivery;//交付期

    private Short completedStatus;//合同执行状态

    private String contractId;//合同id
    
    private PurchaseContract contract;
    
	private String checkMass;//质量检验
	
	private Date createdAt;
	
	private Date updatedAt;

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

	public String getCheckMass() {
		return checkMass;
	}

	public void setCheckMass(String checkMass) {
		this.checkMass = checkMass;
	}
	
	public PurchaseContract getContract() {
		return contract;
	}

	public void setContract(PurchaseContract contract) {
		this.contract = contract;
	}
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDeliverySchedule() {
        return deliverySchedule;
    }

    public void setDeliverySchedule(String deliverySchedule) {
        this.deliverySchedule = deliverySchedule == null ? null : deliverySchedule.trim();
    }

    public String getFundsPaid() {
        return fundsPaid;
    }

    public void setFundsPaid(String fundsPaid) {
        this.fundsPaid = fundsPaid == null ? null : fundsPaid.trim();
    }

    public Date getDraftSignedAt() {
        return draftSignedAt;
    }

    public void setDraftSignedAt(Date draftSignedAt) {
        this.draftSignedAt = draftSignedAt;
    }

    public Date getFormalSignedAt() {
        return formalSignedAt;
    }

    public void setFormalSignedAt(Date formalSignedAt) {
        this.formalSignedAt = formalSignedAt;
    }

    public Date getDelivery() {
        return delivery;
    }

    public void setDelivery(Date delivery) {
        this.delivery = delivery;
    }

    public Short getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(Short completedStatus) {
        this.completedStatus = completedStatus;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }
}