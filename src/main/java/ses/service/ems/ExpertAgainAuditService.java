package ses.service.ems;

import java.util.Date;

import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertReviewTeam;

public interface ExpertAgainAuditService {
	ExpertAgainAuditImg addAgainAudit(String ids);
	ExpertAgainAuditImg createBatch(String batchName,String batchNumber,String ids);
	ExpertAgainAuditImg findBatch(String batchNumber,String batchName,Date createdAt, Integer pageNum);
	ExpertAgainAuditImg findBatchDetails(String batchId,String status, Integer pageNum);
	ExpertAgainAuditImg expertGrouping(String batchId,String ids);
	ExpertAgainAuditImg getGroups(String batchId);
	ExpertAgainAuditImg expertAddGroup(String groupId,String ids);
	ExpertAgainAuditImg	findExpertGroupDetails(String batchId);
	ExpertAgainAuditImg delExpertGroupDetails(String ids);
	ExpertAgainAuditImg checkComplete(String batchId);
	ExpertAgainAuditImg findExpertReviewTeam(String groupId);
	ExpertAgainAuditImg addExpertReviewTeam(ExpertReviewTeam expertReviewTeam);
	ExpertAgainAuditImg deleteExpertReviewTeam(String ids);
	ExpertAgainAuditImg setUpPassword(String ids,String passWord);
	ExpertAgainAuditImg checkLoginName(String loginName);
	ExpertAgainAuditImg preservationExpertReviewTeam(String groupId);
	ExpertAgainAuditImg fingStayReviewExpertList(String userId,String batchName,Date createdAt, Integer pageNum);
	ExpertAgainAuditImg fingStayReviewExpertDetailsList(String userId,String batchId, Integer pageNum);
}
