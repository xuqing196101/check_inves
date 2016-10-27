package bss.model.cs;

import java.util.Date;

public class Performance {
    private String id;

    private String deliverySchedule;

    private String fundsPaid;

    private Date draftSignedAt;

    private Date formalSignedAt;

    private Date delivery;

    private Short completedStatus;

    private String contractId;
    
    private String checkMass;

    public String getCheckMass() {
		return checkMass;
	}

	public void setCheckMass(String checkMass) {
		this.checkMass = checkMass;
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