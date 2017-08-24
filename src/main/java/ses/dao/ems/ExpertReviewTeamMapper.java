package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertReviewTeam;

public interface ExpertReviewTeamMapper {
	
	ExpertReviewTeam getExpertReviewTeam(String id);
	List<ExpertReviewTeam> getExpertReviewTeamList(ExpertReviewTeam expertReviewTeam);
	void insert(ExpertReviewTeam expertReviewTeam);
	void deleteByPrimaryKey(String id);
	ExpertReviewTeam findExpertReviewTeam(String userId);
} 
