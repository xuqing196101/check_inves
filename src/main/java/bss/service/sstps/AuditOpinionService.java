package bss.service.sstps;

import bss.model.sstps.AuditOpinion;

public interface AuditOpinionService {
	
	public void insert(AuditOpinion auditOpinion);
	
	public AuditOpinion selectProduct(AuditOpinion auditOpinion);
	
	public void update(AuditOpinion auditOpinion);

}
