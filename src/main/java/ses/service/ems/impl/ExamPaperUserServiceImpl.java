/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExamPaperUserMapper;
import ses.model.ems.ExamPaperUser;
import ses.service.ems.ExamPaperUserServiceI;
import ses.util.PropertiesUtil;

/**
 * @Title:ExamPaperUserServiceImpl
 * @Description: 考卷参考人员实现类
 * @author ZhaoBo
 * @date 2016-9-13下午2:12:43
 */
@Service("examPaperUserService")
public class ExamPaperUserServiceImpl implements ExamPaperUserServiceI {
	@Autowired
	private ExamPaperUserMapper examPaperUserMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examPaperUserMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public int insertSelective(ExamPaperUser examPaperUser) {
		return examPaperUserMapper.insertSelective(examPaperUser);
	}
	
	@Override
	public ExamPaperUser selectByPrimaryKey(String id) {
		return examPaperUserMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByPrimaryKeySelective(ExamPaperUser examPaperUser) {
		return examPaperUserMapper.updateByPrimaryKeySelective(examPaperUser);
	}

	
	@Override
	public List<ExamPaperUser> getAllByPaperId(HashMap<String,Object> map) {
		return examPaperUserMapper.getAllByPaperId(map);
	}

	
	@Override
	public List<ExamPaperUser> getAllPaperByUserId(ExamPaperUser examPaperUser) {
		return examPaperUserMapper.getAllPaperByUserId(examPaperUser);
	}

	
	@Override
	public List<ExamPaperUser> selectPurchaserYesReference(HashMap<String,Object> map) {
		return examPaperUserMapper.selectPurchaserYesReference(map);
	}

	
	@Override
	public List<ExamPaperUser> selectPrintYesReference(ExamPaperUser examPaperUser) {
		return examPaperUserMapper.selectPrintYesReference(examPaperUser);
	}

	
	@Override
	public List<ExamPaperUser> findAll() {
		return examPaperUserMapper.findAll();
	}

	
	@Override
	public int updateByPaperIdAndUserID(ExamPaperUser examPaperUser) {
		return examPaperUserMapper.updateByPaperIdAndUserID(examPaperUser);
	}

	
	@Override
	public List<ExamPaperUser> findIsExamByCondition(HashMap<String, Object> map) {
		return examPaperUserMapper.findIsExamByCondition(map);
	}

	
	@Override
	public List<ExamPaperUser> findCurrentUserSchedule(HashMap<String, Object> map) {
		return examPaperUserMapper.findCurrentUserSchedule(map);
	}

}
