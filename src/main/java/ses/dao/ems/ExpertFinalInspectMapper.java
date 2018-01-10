package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertFinalInspect;

public interface ExpertFinalInspectMapper {
	List<ExpertFinalInspect> findExpertFinalInspectList(ExpertFinalInspect e);
	ExpertFinalInspect getExpertFinalInspect(ExpertFinalInspect e);
	void insertExpertFinalInspect(ExpertFinalInspect e);
	void updateExpertFinalInspect(ExpertFinalInspect e);
	void updateExpertFinalInspectStatusIsNull(ExpertFinalInspect e);
}
