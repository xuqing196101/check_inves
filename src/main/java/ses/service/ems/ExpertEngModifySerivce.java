package ses.service.ems;

import java.util.List;

import ses.model.ems.ExpertEngHistory;

public interface ExpertEngModifySerivce {
	void insertSelective (ExpertEngHistory expertEngHistory);
	
	List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory);
	
	void deleteByExpertId (ExpertEngHistory expertEngHistory);
}
