package ses.dao.ems;

import ses.model.ems.ExpertBatchDetails;

import java.util.List;

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

	/**
	 *
	 * Description: 根据专家ID删除批次
	 *
	 * @author Easong
	 * @version 2017/11/8
	 * @param expertId
	 * @since JDK1.7
	 */
	int deleteByExpertId(String expertId);
}
