package ses.dao.ems;

import ses.model.ems.ExpertAuditOpinion;

/**
 * <p>ExpertAuditOpinionMapper </p>
 * <p>Description:专家审核意见 </p>
 * @author XuQing
 * @date 2017-4-1下午5:48:39
 */
public interface ExpertAuditOpinionMapper {
	
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-4-1 下午5:39:10  
	 * @Description:插入数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	void insertSelective (ExpertAuditOpinion expertAuditOpinion );
	
	/**
	 * @Title: selectByPrimaryKey
	 * @author XuQing 
	 * @date 2017-4-1 下午5:39:20  
	 * @Description:查询数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	ExpertAuditOpinion selectByPrimaryKey (ExpertAuditOpinion expertAuditOpinion );
}
