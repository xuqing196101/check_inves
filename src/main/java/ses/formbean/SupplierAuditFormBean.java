package ses.formbean;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierAudit;
import ses.model.sms.SupplierAuditNot;
import ses.model.sms.SupplierAuditOpinion;
import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;
import ses.model.sms.SupplierSignature;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierAuditFormBean implements Serializable{

	private static final long serialVersionUID = 7027388594237003141L;
	private String supplierId;
	
	private Integer status;
	
	private String auditOrgId;
	
	private Date auditDate;
	
	private List<SupplierAudit> supplierAudits=new ArrayList<SupplierAudit>();
	
	private List<SupplierModify>  supplierModify=new ArrayList<SupplierModify>();
	
	private List<SupplierHistory> supplierHistory=new ArrayList<SupplierHistory>();
	
	private List<SupplierAuditNot> supplierAuditNot=new ArrayList<SupplierAuditNot>();

	private List<SupplierSignature> supplierSignature=new ArrayList<SupplierSignature>();

	// 审核意见
	private SupplierAuditOpinion supplierAuditOpinions;
	
	private User user;

	private Supplier supplier;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<SupplierSignature> getSupplierSignature() {
		return supplierSignature;
	}

	public void setSupplierSignature(List<SupplierSignature> supplierSignature) {
		this.supplierSignature = supplierSignature;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAuditOrgId() {
		return auditOrgId;
	}

	public void setAuditOrgId(String auditOrgId) {
		this.auditOrgId = auditOrgId;
	}

	public List<SupplierAudit> getSupplierAudits() {
		return supplierAudits;
	}

	public void setSupplierAudits(List<SupplierAudit> supplierAudits) {
		this.supplierAudits = supplierAudits;
	}
	
	public List<SupplierModify> getSupplierModify() {
		return supplierModify;
	}

	public void setSupplierModify(List<SupplierModify> supplierModify) {
		this.supplierModify = supplierModify;
	}

	public List<SupplierHistory> getSupplierHistory() {
		return supplierHistory;
	}

	public void setSupplierHistory(List<SupplierHistory> supplierHistory) {
		this.supplierHistory = supplierHistory;
	}

	public List<SupplierAuditNot> getSupplierAuditNot() {
		return supplierAuditNot;
	}

	public void setSupplierAuditNot(List<SupplierAuditNot> supplierAuditNot) {
		this.supplierAuditNot = supplierAuditNot;
	}


    public SupplierAuditOpinion getSupplierAuditOpinions() {
        return supplierAuditOpinions;
    }

    public void setSupplierAuditOpinions(SupplierAuditOpinion supplierAuditOpinions) {
        this.supplierAuditOpinions = supplierAuditOpinions;
    }

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
}
