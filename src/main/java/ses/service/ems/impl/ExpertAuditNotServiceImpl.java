package ses.service.ems.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditNotMapper;
import ses.model.ems.ExpertAuditNot;
import ses.service.ems.ExpertAuditNotService;


/**
 * <p>Title:ExpertAuditNotMapper </p>
 * <p>Description: 记录审核不通过的专家</p>
 * @author XuQing
 * @date 2017-5-3下午6:42:57
 */
@Service("expertAuditNotService")
public class ExpertAuditNotServiceImpl implements ExpertAuditNotService{

	@Autowired 
	private ExpertAuditNotMapper mapper;
	
	@Override
	public void insertSelective(ExpertAuditNot expertAuditNot) {
		
		mapper.insertSelective(expertAuditNot);
	}

	/**
	 * @Title: selectByIdCard
	 * @author XuQing 
	 * @date 2017-5-4 下午4:45:31  
	 * @Description:根据身份证号查询初审未通过的专家
	 * @param @param idCard
	 * @param @return      
	 * @return ExpertAuditNot
	 */
	@Override
	public ExpertAuditNot selectByIdCard(String idCard) {
		return mapper.selectByIdCard(idCard);
	}

}
