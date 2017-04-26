package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExpertAuditFileModify;

/**
 * <p>Title:ExpertAuditFileModifyMapper </p>
 * <p>Description: 专家附件修改记录</p>
 * @author XuQing
 * @date 2017-4-21下午6:25:26
 */
public interface ExpertAuditFileModifyMapper {
	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:27:57  
	 * @Description:查询附件修改记录
	 * @param @param expertId
	 * @param @return      
	 * @return List<ExpertAuditFileModify>
	 */
	List<ExpertAuditFileModify> selectByExpertId(ExpertAuditFileModify expertAuditFileModify);

	/**
	 * @Title: delByExpertId
	 * @author XuQing 
	 * @date 2017-4-21 下午6:28:24  
	 * @Description:删除附件修改记录
	 * @param @param expertId      
	 * @return void
	 */
	void delByExpertId (String expertId);
	
	/**
	 * @Title: insert
	 * @author XuQing 
	 * @date 2017-4-26 下午5:24:04  
	 * @Description:添加
	 * @param @param expertAuditFileModify      
	 * @return void
	 */
	void insert (ExpertAuditFileModify expertAuditFileModify);
}
