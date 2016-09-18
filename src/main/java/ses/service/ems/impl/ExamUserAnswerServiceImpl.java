/**
 * 
 */
package ses.service.ems.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamUserAnswerMapper;
import ses.model.ems.ExamUserAnswer;
import ses.service.ems.ExamUserAnswerServiceI;

/**
 * @Title:ExamUserAnswerServiceImpl
 * @Description: 用户答题记录ServiceImpl
 * @author ZhaoBo
 * @date 2016-9-14上午10:31:36
 */
@Service("examUserAnswerService")
public class ExamUserAnswerServiceImpl implements ExamUserAnswerServiceI {
	@Autowired
	private ExamUserAnswerMapper examUserAnswerMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examUserAnswerMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(ExamUserAnswer examUserAnswer) {
		return examUserAnswerMapper.insertSelective(examUserAnswer);
	}

	
	@Override
	public ExamUserAnswer selectByPrimaryKey(String id) {
		return examUserAnswerMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ExamUserAnswer examUserAnswer) {
		return examUserAnswerMapper.updateByPrimaryKeySelective(examUserAnswer);
	}

}
