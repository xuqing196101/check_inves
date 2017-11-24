package ses.dao.ems;

import ses.model.ems.ExpertBatchDetails;
import ses.model.ems.ExpertStatusRecord;

import java.util.List;

public interface ExpertBatchDetailsMapper {
	
	void insert(ExpertBatchDetails expertBatchDetails);
	List<ExpertBatchDetails> getExpertBatchDetails(ExpertBatchDetails expertBatchDetails);
	List<ExpertBatchDetails> getExpertBatchDetailsRecord(ExpertBatchDetails expertBatchDetails);
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
	void insertExpertStatusRecord(ExpertStatusRecord e);

	/**
	 *
	 * Description: 查询专家批次对应编号详情表
	 *
	 * @author Easong
	 * @version 2017/11/24
	 * @param
	 * @since JDK1.7
	 */
    List<ExpertBatchDetails> findExpertBatchDetailsList(ExpertBatchDetails expertBatchDetails);

    /**
     *
     * Description: 查询专家批次对应编号详情表返回一条
     *
     * @author Easong
     * @version 2017/11/24
     * @param
     * @since JDK1.7
     */
    ExpertBatchDetails findExpertBatchDetailsOfOne(ExpertBatchDetails expertBatchDetails);
}
