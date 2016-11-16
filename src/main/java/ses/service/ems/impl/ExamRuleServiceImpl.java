/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamRuleMapper;
import ses.model.ems.ExamRule;
import ses.service.ems.ExamRuleServiceI;

/**
 * @Title:ExamRuleServiceImpl
 * @Description: 
 * @author ZhaoBo
 * @date 2016-9-8上午11:00:21
 */
@Service("examRuleService")
public class ExamRuleServiceImpl implements ExamRuleServiceI {
	@Autowired
	private ExamRuleMapper examRuleMapper;
	
	@Override
	public int insertSelective(ExamRule examRule) {
		return examRuleMapper.insertSelective(examRule);
	}
	
	@Override
	public List<ExamRule> select(HashMap<String,Object> map) {
		return examRuleMapper.select(map);
	}
	
	@Override
	public int updateByPrimaryKeySelective(ExamRule examRule) {
		return examRuleMapper.updateByPrimaryKeySelective(examRule);
	}





	
	

}
