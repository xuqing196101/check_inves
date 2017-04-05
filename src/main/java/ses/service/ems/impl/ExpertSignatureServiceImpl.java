package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertSignatureMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertSignature;
import ses.service.ems.ExpertSignatureService;

@Service("expertSignatureService")
public class ExpertSignatureServiceImpl implements ExpertSignatureService{

	@Autowired 
	private ExpertSignatureMapper mapper;
	
	@Autowired
	private ExpertMapper expertMapper;
	
	
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-4-3 下午5:39:10  
	 * @Description:插入数据
	 * @param @param expertAuditOpinionMapper      
	 * @return void
	 */
	@Override
	public void insertSelective(String[] ids, String expertId) {
		ExpertSignature expertSignature = new ExpertSignature();
		for(int i=0; i<ids.length; i++){
			Expert expert = expertMapper.selectByPrimaryKey(ids[i]);
			expertSignature.setSignatoryId(expert.getId());
			expertSignature.setExpertId(expertId);
			expertSignature.setName(expert.getRelName());
			mapper.insertSelective(expertSignature);
		}
	}

	/**
	 * @Title: selectByExpertId
	 * @author XuQing 
	 * @date 2017-4-3 下午1:55:55  
	 * @Description:查询数据
	 * @param @param expertSignature
	 * @param @return      
	 * @return List<ExpertAuditOpinion>
	 */
	@Override
	public List<ExpertSignature> selectByExpertId(ExpertSignature expertSignature) {
		
		return mapper.selectByExpertId(expertSignature);
	}

	@Override
	public void add(ExpertSignature expertSignature) {
		// TODO Auto-generated method stub
		mapper.insertSelective(expertSignature);
	}

}
