package bss.dao.sstps;

import bss.model.sstps.AuditOpinion;

public interface AuditOpinionMapper {
	
    int delete(String id);

    int insert(AuditOpinion record);

    AuditOpinion selectByPrimaryKey(String id);

    int update(AuditOpinion record);
    
    AuditOpinion selectProduct(AuditOpinion record);
    
}