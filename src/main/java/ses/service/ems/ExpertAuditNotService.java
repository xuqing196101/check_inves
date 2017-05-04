package ses.service.ems;

import ses.model.ems.ExpertAuditNot;

/**
 * <p>Title:ExpertAuditNotMapper </p>
 * <p>Description: 记录审核不通过的专家</p>
 * @author XuQing
 * @date 2017-5-3下午6:42:57
 */
public interface ExpertAuditNotService {
	
	void insertSelective(ExpertAuditNot expertAuditNot);
}
