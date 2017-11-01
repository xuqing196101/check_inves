/**
 * 
 */
package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;


import ses.dao.ems.ExamPaperMapper;
import ses.model.ems.ExamPaper;
import ses.service.ems.ExamPaperServiceI;
import ses.util.PropertiesUtil;



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
	public List<ExamPaper> queryAllPaper(ExamPaper examPaper,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return examPaperMapper.queryAllPaper(examPaper);
	}


	@Override
	public List<ExamPaper> selectByPaperNo(HashMap<String,Object> map) {
		return examPaperMapper.selectByPaperNo(map);
	}



}
