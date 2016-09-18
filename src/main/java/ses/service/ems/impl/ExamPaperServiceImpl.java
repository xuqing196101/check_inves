/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamPaperMapper;
import ses.model.ems.ExamPaper;
import ses.service.ems.ExamPaperServiceI;



/**
 * @Title:ExamPaperServiceImpl 
 * @Description: 考卷ServiceImpl类
 * @author ZhaoBo
 * @date 2016-9-5下午5:26:16
 */
@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperServiceI {
	@Autowired
	private ExamPaperMapper examPaperMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return examPaperMapper.deleteByPrimaryKey(id);
	}

	
	@Override
	public int insertSelective(ExamPaper examPaper) {
		return examPaperMapper.insertSelective(examPaper);
	}

	
	@Override
	public ExamPaper selectByPrimaryKey(String id) {
		return examPaperMapper.selectByPrimaryKey(id);
	}

	
	@Override
	public int updateByPrimaryKeySelective(ExamPaper examPaper) {
		return examPaperMapper.updateByPrimaryKeySelective(examPaper);
	}
	
	@Override
	public List<ExamPaper> queryAllPaper() {
		return examPaperMapper.queryAllPaper();
	}


	@Override
	public ExamPaper selectByPaperNo(String paperNo) {
		return examPaperMapper.selectByPaperNo(paperNo);
	}



}
