package bss.service.pms;

import java.util.List;

import bss.model.pms.AuditParam;

public interface AuditParameService {


	AuditParam queryById(String id);
	
	void add(AuditParam auditParam);
	
	void update(AuditParam auditParam);
	
	List<AuditParam> query(AuditParam auditParam,Integer page);
	
	void delete(String id);
}
