package ses.service.ems.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertAuditOpinionMapper;
import ses.model.ems.ExpertAuditOpinion;
import ses.service.ems.ExpertAuditOpinionService;

/**
 * <p>Title:ExpertAuditOpinionServiceImpl </p>
 * <p>Description:专家审核意见 </p>
 * @author XuQing
 * @date 2017-4-1下午5:48:39
 */
@Service("expertAuditOpinionService")
public class ExpertAuditOpinionServiceImpl implements ExpertAuditOpinionService{

	@Autowired 
	private ExpertAuditOpinionMapper mapper;
	
	@Override
	public void insertSelective(ExpertAuditOpinion expertAuditOpinion) {
		mapper.insertSelective(expertAuditOpinion);
		
	}

	@Override
	public ExpertAuditOpinion selectByPrimaryKey(ExpertAuditOpinion expertAuditOpinion) {
		return mapper.selectByPrimaryKey(expertAuditOpinion);
	}

	/**
	 * 
	 * Description:根据专家ID查询信息
	 * 
	 * @author Easong
	 * @version 2017年7月3日
	 * @param expertId
	 * @return
	 */
	@Override
	public ExpertAuditOpinion selectByExpertId(ExpertAuditOpinion expertAuditOpinion) {
		return mapper.selectByExpertId(expertAuditOpinion);
	}
	
}
