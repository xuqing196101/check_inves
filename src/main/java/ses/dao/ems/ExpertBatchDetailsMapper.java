package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertBatchDetails;

public interface ExpertBatchDetailsMapper {
	
	void insert(ExpertBatchDetails expertBatchDetails);
	List<ExpertBatchDetails> getExpertBatchDetails(ExpertBatchDetails expertBatchDetails);
	void updateExpertBatchDetailsGrouping(ExpertBatchDetails expertBatchDetails);
	int getExpertBatchDetailsCount(String groupId);
	ExpertBatchDetails findExpertBatchDetails(ExpertBatchDetails expertBatchDetails);
	
	/**
	 * 
	 * Description: 根据专家id查询审核组id
	 * 
	 * @author zhang shubin
	 * @data 2017年9月29日
	 * @param 
	 * @return
	 */
	List<String> selGroupIdByExpertId(String expertId);
}
