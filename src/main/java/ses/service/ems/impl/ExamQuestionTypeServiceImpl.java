/**
 * 
 */
package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamQuestionTypeMapper;
import ses.model.ems.ExamQuestionType;
import ses.service.ems.ExamQuestionTypeServiceI;

/**
 * @Title:ExamQuestionTypeServiceImpl
 * @Description:
 * @author zb
 * @date 2016-9-5下午1:19:51
 */
@Service("examQuestionTypeService")
public class ExamQuestionTypeServiceImpl implements ExamQuestionTypeServiceI {
	@Autowired
	private ExamQuestionTypeMapper examQuestionTypeMapepr;
	
	@Override
	public List<ExamQuestionType> selectExpertAll() {
		return examQuestionTypeMapepr.selectExpertAll();
	}

	
	@Override
	public List<ExamQuestionType> selectPurchaserAll() {
		return examQuestionTypeMapepr.selectPurchaserAll();
	}

}
