package ses.service.ems;

import java.util.List;

import ses.model.ems.Expert;
import ses.model.ems.ExpertFinalInspect;

public interface ExpertFinalInspectService {
	void addFinalInspect(String expertId,String orgId);
	List<Expert> findExpertFinalInspectList(Expert expert,Integer pageNum);
	List<ExpertFinalInspect> findExpertFinalInspectList(ExpertFinalInspect e);
	ExpertFinalInspect getExpertFinalInspect(ExpertFinalInspect e);
	void insertExpertFinalInspect(ExpertFinalInspect e);
	void updateExpertFinalInspect(ExpertFinalInspect e);
	void updateExpertFinalInspectStatusIsNull(ExpertFinalInspect e);
}
