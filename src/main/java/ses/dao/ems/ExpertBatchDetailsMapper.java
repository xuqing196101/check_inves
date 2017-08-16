package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertBatchDetails;

public interface ExpertBatchDetailsMapper {
	
	void insert(ExpertBatchDetails expertBatchDetails);
	List<ExpertBatchDetails> getExpertBatchDetails(ExpertBatchDetails expertBatchDetails);
	void updateExpertBatchDetailsGrouping(ExpertBatchDetails expertBatchDetails);
}
