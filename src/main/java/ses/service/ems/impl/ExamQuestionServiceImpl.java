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
	public int insertSelective(ExamQuestion examPool) {
		return examQuestionMapper.insertSelective(examPool);
	}
	
	@Override
	public ExamQuestion selectByPrimaryKey(String id) {
		return examQuestionMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKeySelective(ExamQuestion examPool) {
		return examQuestionMapper.updateByPrimaryKeySelective(examPool);
	}
	
	@Override
	public List<ExamQuestion> searchTecExpPool(ExamQuestion examPool,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchTecExpPool(examPool,pageNum);
	}
	
	@Override
	public List<ExamQuestion> searchComExpPool(ExamQuestion examPool,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchComExpPool(examPool,pageNum);
	}
	
	@Override
	public List<ExamQuestion> searchLawExpPool(ExamQuestion examPool,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examQuestionMapper.searchLawExpPool(examPool, pageNum);
	}
	
	@Override
	public List<ExamQuestion> selectLawRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectLawRandom(examPool);
	}
	
	@Override
	public List<ExamQuestion> selectSingleRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectSingleRandom(examPool);
	}
	
	@Override
	public List<ExamQuestion> selectMultipleRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectMultipleRandom(examPool);
	}
	
	@Override
	public List<ExamQuestion> selectJudgeRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectJudgeRandom(examPool);
	}
	
	@Override
	public List<ExamQuestion> selectTecRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectTecRandom(examPool);
	}
	
	@Override
	public List<ExamQuestion> selectComRandom(ExamQuestion examPool) {
		return examQuestionMapper.selectComRandom(examPool);
	}
	
	
	@Override
	public List<ExamQuestion> queryPurchaserByTerm(HashMap<String, Object> map) {
		return examQuestionMapper.queryPurchaserByTerm(map);
	}

	
	@Override
	public List<ExamQuestion> searchExpertPool() {
		return examQuestionMapper.searchExpertPool();
	}

	
}
