package ses.dao.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertBatch;

public interface ExpertBatchMapper {
	
	List<ExpertBatch> getAllExpertBatch(Map<String,Object> e);
	void insert(ExpertBatch expertBatch);
	ExpertBatch getExpertBatchByKey(String batchId);
}
