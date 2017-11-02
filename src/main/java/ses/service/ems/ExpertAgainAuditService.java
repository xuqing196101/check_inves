package ses.service.ems;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import ses.model.ems.ExpertAgainAuditImg;
import ses.model.ems.ExpertBatchDetails;

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
	ExpertAgainAuditImg findExpertReviewTeam(String groupId,Integer pageNum);
	ExpertAgainAuditImg addExpertReviewTeam(String userName,String password,List<Map<String, String>> expertReviewTeam);
	ExpertAgainAuditImg deleteExpertReviewTeam(String ids);
	ExpertAgainAuditImg setUpPassword(String groupId,String passWord);
	ExpertAgainAuditImg checkLoginName(String loginName);
	ExpertAgainAuditImg preservationExpertReviewTeam(String groupId);
	ExpertAgainAuditImg fingStayReviewExpertList(String userId,String batchName,Date createdAt, Integer pageNum);
	ExpertAgainAuditImg fingStayReviewExpertDetailsList(String userId,String batchId, Integer pageNum);
	ExpertAgainAuditImg checkGroupStatus(String expertId);
	void handleExpertReviewTeam(String expertId);
	ExpertAgainAuditImg automaticGrouping(String batchId,int count);
	ExpertAgainAuditImg selectReviewTeamAll();
	List<ExpertBatchDetails> findBatchDetailsList(String batchId);
}
