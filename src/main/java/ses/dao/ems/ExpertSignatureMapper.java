package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertAuditOpinion;
import ses.model.ems.ExpertSignature;

public interface ExpertSignatureMapper {
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-4-3 下午5:39:10  
	 * @Description:插入数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	void insertSelective (ExpertSignature expertSignature );
	
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-3 下午1:55:55  
	 * @Description:查询数据
	 * @param @param expertSignature
	 * @param @return      
	 * @return List<ExpertAuditOpinion>
	 */
	List<ExpertSignature> selectByExpertId (ExpertSignature expertSignature );
}
