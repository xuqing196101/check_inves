/**
 * 
 */
package ses.service.ems.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExamQuestionMapper;
import ses.model.bms.User;
import ses.model.ems.ExamQuestion;
import ses.model.ems.Expert;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExamQuestionServiceI;
import ses.service.ems.ExpertService;

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
	@Autowired
	private ExpertService expertService;
	@Autowired
	private UserServiceI userService;
	
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
	public List<ExamQuestion> selectPurchaserQuestionRandom(HashMap<String,Object> map) {
		return examQuestionMapper.selectPurchaserQuestionRandom(map);
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
	
	@Override
	public List<ExamQuestion> selectQuestionRandom(HashMap<String, Object> map) {
		return examQuestionMapper.selectQuestionRandom(map);
	}
	
	@Override
	public List<ExamQuestion> selectByTecTopic(HashMap<String, Object> map) {
		return examQuestionMapper.selectByTecTopic(map);
	}
	
	@Override
	public int queryPurchaserQuestionCount(HashMap<String, Object> map) {
		return examQuestionMapper.queryPurchaserQuestionCount(map);
	}
	
	@Override
	public List<ExamQuestion> findExpertQuestionList(HashMap<String, Object> map) {
		return examQuestionMapper.findExpertQuestionList(map);
	}
    /**
     * 删除方法 关于登陆超过三个月删除专家账号
     */
	
	@Override
	public boolean CheckState(User user){
		boolean result = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", 5);
		map.put("id", user.getTypeId());
		//List 集合 expert.get(0)获取集合里第一条集合数据再.getAuditAt第一条数据里的某个值
		List<Expert> expert = expertService.findAllExpert(map);   		
		Date auditTime = expert.get(0).getAuditAt();//这个是当前登陆用户的复审时间
		Calendar calendar = Calendar.getInstance();//当前时间
		calendar.setTime(auditTime);
		calendar.add(Calendar.MONTH,3);//三个月后的日期
		Date offTime = calendar.getTime();//三个月后的日期（Date类型）
			
		if(new Date().getTime() >=offTime.getTime())
			result = true;
		return result;
	}
	
	@Override
	public Integer delExpertById(User user) {				
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("status", 5);
//		map.put("id", user.getTypeId());
//		//List 集合 expert.get(0)获取集合里第一条集合数据再.getAuditAt第一条数据里的某个值
//		List<Expert> expert = expertService.findAllExpert(map);   
//		//String str = null;
//		Date auditTime = expert.get(0).getAuditAt();//这个是当前登陆用户的复审时间
//		Calendar calendar = Calendar.getInstance();//当前时间
//		calendar.setTime(auditTime);
//		calendar.add(Calendar.MONTH,3);//三个月后的日期
//		Date offTime = calendar.getTime();//三个月后的日期（Date类型）
	//	if(new Date().getTime()<=offTime.getTime()){
		//}
		Integer i=0;		
		i=expertService.deleteExpertsAccount(user.getTypeId());
		return i;
	}
}
