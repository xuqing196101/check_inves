package ses.dao.ems;

import java.util.List;

import ses.model.ems.BatchTemporary;
import ses.model.ems.Expert;

public interface BatchTemporaryMapper {
	List<BatchTemporary> selectBatchTemporaryAll(Expert expert);
	void deleteByPrimaryKey();
	void addBatchTemporary(BatchTemporary batchTemporary);
	void deleteBatchTemporary(String batchExpertId);
}
