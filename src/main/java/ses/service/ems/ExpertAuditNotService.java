package ses.service.ems;

import ses.model.ems.ExpertAuditNot;

/**
 * <p>Title:ExpertAuditNotMapper </p>
 * <p>Description: 记录审核不通过的专家</p>
 * @author XuQing
 * @date 2017-5-3下午6:42:57
 */
public interface ExpertAuditNotService {
	
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-5-4 下午4:45:50  
	 * @Description:记录初审未通过的专家
	 * @param @param expertAuditNot      
	 * @return void
	 */
	void insertSelective(ExpertAuditNot expertAuditNot);
	
	/**
	 * @Title: selectByIdCard
	 * @author XuQing 
	 * @date 2017-5-4 下午4:45:31  
	 * @Description:根据身份证号查询初审未通过的专家
	 * @param @param idCard
	 * @param @return      
	 * @return ExpertAuditNot
	 */
	ExpertAuditNot selectByIdCard(String idCard);
}
