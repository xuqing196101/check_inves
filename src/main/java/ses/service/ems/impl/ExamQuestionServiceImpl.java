/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExamQuestionMapper;
import ses.model.ems.ExamQuestion;
import ses.service.ems.ExamQuestionServiceI;
import ses.util.PropertiesUtil;

/**
 * @Title:ExamQuestionServiceImpl 
 * @Description: 题目ServiceImpl类 
 * @author zb
 * @date 2016-8-24下午3:14:52
 */
@Service("examQuestionService")
public class ExamQuestionServiceImpl implements ExamQuestionServiceI {
	@Autowired
	private ExamQuestionMapper examQuestionMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examQuestionMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(ExamQuestion examQuestion) {
		return examQuestionMapper.insertSelective(examQuestion);
	}
	
	@Override
	public ExamQuestion selectByPrimaryKey(String id) {
		return examQuestionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKeySelective(ExamQuestion examQuestion) {
		return examQuestionMapper.updateByPrimaryKeySelective(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> searchTecExpPool(ExamQuestion examQuestion,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchTecExpPool(examQuestion,pageNum);
	}
	
	@Override
	public List<ExamQuestion> searchComExpPool(ExamQuestion examQuestion,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchComExpPool(examQuestion,pageNum);
	}
	
	@Override
	public List<ExamQuestion> searchLawExpPool(ExamQuestion examQuestion,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchLawExpPool(examQuestion, pageNum);
	}
	
	@Override
	public List<ExamQuestion> selectLawRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectLawRandom(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> selectSingleRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectSingleRandom(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> selectMultipleRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectMultipleRandom(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> selectJudgeRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectJudgeRandom(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> selectTecRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectTecRandom(examQuestion);
	}
	
	@Override
	public List<ExamQuestion> selectComRandom(ExamQuestion examQuestion) {
		return examQuestionMapper.selectComRandom(examQuestion);
	}
	
	
	@Override
	public List<ExamQuestion> queryPurchaserByTerm(HashMap<String, Object> map) {
		return examQuestionMapper.queryPurchaserByTerm(map);
	}

	
	@Override
	public List<ExamQuestion> searchExpertPool() {
		return examQuestionMapper.searchExpertPool();
	}

	
	@Override
	public List<ExamQuestion> selectByTopic(HashMap<String, Object> map) {
		return examQuestionMapper.selectByTopic(map);
	}

	
	@Override
	public int queryQuestionCount(HashMap<String, Object> map) {
		return examQuestionMapper.queryQuestionCount(map);
	}

	
}
