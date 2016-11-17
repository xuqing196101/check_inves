/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertPaperUserMapper;
import ses.model.ems.ExpertPaperUser;
import ses.service.ems.ExpertPaperUserServiceI;

/**
 * @Title:ExpertPaperUserServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-11-17上午9:41:24
 */
@Service("expertPaperUserService")
public class ExpertPaperUserServiceImpl implements ExpertPaperUserServiceI{
	
	@Autowired
	private ExpertPaperUserMapper expertPaperUserMapper;
	
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return expertPaperUserMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(ExpertPaperUser expertPaperUser) {
		return expertPaperUserMapper.insertSelective(expertPaperUser);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ExpertPaperUser expertPaperUser) {
		return expertPaperUserMapper.updateByPrimaryKeySelective(expertPaperUser);
	}

	
	@Override
	public ExpertPaperUser selectByPrimaryKey(String id) {
		return expertPaperUserMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public List<ExpertPaperUser> findAll(HashMap<String, Object> map) {
		return expertPaperUserMapper.findAll(map);
	}


	
	@Override
	public int updateById(ExpertPaperUser expertPaperUser) {
		return expertPaperUserMapper.updateById(expertPaperUser);
	}


	
	@Override
	public List<ExpertPaperUser> findNoTest(String ruleId) {
		return expertPaperUserMapper.findNoTest(ruleId);
	}


	
	@Override
	public List<ExpertPaperUser> findNoTestById(HashMap<String, Object> map) {
		return expertPaperUserMapper.findNoTestById(map);
	}

}
