/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamUserScoreMapper;
import ses.model.ems.ExamUserScore;
import ses.service.ems.ExamUserScoreServiceI;

/**
 * @Title:ExamUserScoreServiceImpl 
 * @Description:用户成绩ServiceImpl类
 * @author zb
 * @date 2016-8-30下午1:31:49
 */
@Service("examUserScoreService")
public class ExamUserScoreServiceImpl implements ExamUserScoreServiceI {
	@Autowired
	private ExamUserScoreMapper examUserScoreMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examUserScoreMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(ExamUserScore examUserScore) {
		return examUserScoreMapper.insertSelective(examUserScore);
	}

	
	@Override
	public ExamUserScore selectByPrimaryKey(String id) {
		return examUserScoreMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ExamUserScore examUserScore) {
		return examUserScoreMapper.updateByPrimaryKeySelective(examUserScore);
	}

	
	@Override
	public List<ExamUserScore> expertQueryList(ExamUserScore examUserScore) {
		return examUserScoreMapper.expertQueryList(examUserScore);
	}


	@Override
	public List<ExamUserScore> queryListByName(ExamUserScore examUserScore) {
		return examUserScoreMapper.queryListByName(examUserScore);
	}


	
	@Override
	public List<ExamUserScore> queryListByDuty(ExamUserScore examUserScore) {
		return examUserScoreMapper.queryListByDuty(examUserScore);
	}


	
	@Override
	public List<ExamUserScore> queryListByState(ExamUserScore examUserScore) {
		return examUserScoreMapper.queryListByState(examUserScore);
	}
	
	
	@Override
	public List<ExamUserScore> queryListByDuSt(ExamUserScore examUserScore) {
		return examUserScoreMapper.queryListByDuSt(examUserScore);
	}

	
	@Override
	public List<ExamUserScore> selectExpertResultByTerm(ExamUserScore examUserScore) {
		return examUserScoreMapper.selectExpertResultByTerm(examUserScore);
	}


	
	
}
