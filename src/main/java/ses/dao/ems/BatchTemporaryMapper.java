package ses.dao.ems;

import java.util.List;

import ses.model.ems.BatchTemporary;

public interface BatchTemporaryMapper {
	List<BatchTemporary> selectBatchTemporaryAll(String id);
	void deleteByPrimaryKey(String id);
	void addBatchTemporary(BatchTemporary batchTemporary);
}
