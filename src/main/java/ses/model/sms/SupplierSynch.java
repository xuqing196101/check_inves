package ses.model.sms;

import java.util.ArrayList;
import java.util.List;

import ses.model.sms.review.SupplierAttachAudit;
import ses.model.sms.review.SupplierAuditSign;
import ses.model.sms.review.SupplierCateAudit;
import ses.model.sms.review.SupplierInvesOther;

/**
 * <简述>供应商实地考察导出关联
 * <详细描述>
 * @author   Ye Maolin
 * @version  
 * @since
 * @see
 */
@SuppressWarnings("serial")
public class SupplierSynch extends Supplier{
	
	List<SupplierAttachAudit> attachAudits = new ArrayList<SupplierAttachAudit>();//附件审核表
	
	List<SupplierAuditOpinion> auditOpinions = new ArrayList<SupplierAuditOpinion>();//审核意见
	
	List<SupplierInvesOther> invesOthers = new ArrayList<SupplierInvesOther>();//供应商实地考察其他信息
    
	List<SupplierCateAudit> cateAudits = new ArrayList<SupplierCateAudit>();//产品类别审核表（供应商实地考察用）
	
	List<SupplierAuditSign> auditSigns = new ArrayList<SupplierAuditSign>();
	
	public List<SupplierAttachAudit> getAttachAudits() {
		return attachAudits;
	}

	public void setAttachAudits(List<SupplierAttachAudit> attachAudits) {
		this.attachAudits = attachAudits;
	}

	public List<SupplierAuditOpinion> getAuditOpinions() {
		return auditOpinions;
	}

	public void setAuditOpinions(List<SupplierAuditOpinion> auditOpinions) {
		this.auditOpinions = auditOpinions;
	}

	public List<SupplierInvesOther> getInvesOthers() {
		return invesOthers;
	}

	public void setInvesOthers(List<SupplierInvesOther> invesOthers) {
		this.invesOthers = invesOthers;
	}

	public List<SupplierCateAudit> getCateAudits() {
		return cateAudits;
	}

	public void setCateAudits(List<SupplierCateAudit> cateAudits) {
		this.cateAudits = cateAudits;
	}

	public List<SupplierAuditSign> getAuditSigns() {
		return auditSigns;
	}

	public void setAuditSigns(List<SupplierAuditSign> auditSigns) {
		this.auditSigns = auditSigns;
	}
	
}
