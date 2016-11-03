package bss.service.pms;

import java.util.List;

import bss.model.pms.PurchaseAudit;

public interface PurchaseAuditService {

	void add(PurchaseAudit PurchaseAudit);
	
	PurchaseAudit query(PurchaseAudit purchaseAudit);
	
	List<PurchaseAudit> queryByPid(String pid);
	
	void update(PurchaseAudit purchaseAudit);
}
