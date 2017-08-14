package ses.service.ems;

import java.util.Date;

import ses.model.ems.ExpertAgainAuditImg;

public interface ExpertAgainAuditService {
	ExpertAgainAuditImg addAgainAudit(String ids);
	ExpertAgainAuditImg createBatch(String batchName,String batchNumber,String ids);
	ExpertAgainAuditImg findBatch(String batchNumber,String batchName,Date createdAt, Integer pageNum);
}
