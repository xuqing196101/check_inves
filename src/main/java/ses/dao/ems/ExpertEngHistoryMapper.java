package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertEngHistory;

public interface ExpertEngHistoryMapper {
	void insertSelective (ExpertEngHistory expertEngHistory);
	
	List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory);
	
	void deleteByExpertId (ExpertEngHistory expertEngHistory);
}
