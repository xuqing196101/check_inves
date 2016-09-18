/**
 * 
 */
package ses.service.ems.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamPaperUserMapper;
import ses.model.ems.ExamPaperUser;
import ses.service.ems.ExamPaperUserServiceI;

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

}
