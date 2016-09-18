/**
 * 
 */
package ses.service.ems;

import ses.model.ems.ExamUserAnswer;

/**
 * @Title:ExamUserAnswerServiceI
 * @Description: 用户答题记录Service
 * @author ZhaoBo
 * @date 2016-9-14上午10:30:31
 */
public interface ExamUserAnswerServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-13 下午2:09:42  
	* @Description: 根据ID删除答题记录
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:02  
	* @Description: 新增答题记录
	* @param @param examPaperUser
	* @param @return      
	* @return int
	 */
	int insertSelective(ExamUserAnswer examUserAnswer);
	
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:18  
	* @Description: 根据ID查询答题记录 
	* @param @param id
	* @param @return      
	* @return ExamPaperUser
	 */
	ExamUserAnswer selectByPrimaryKey(String id);
	    
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:40  
	* @Description: 根据ID更新参考人员的答题记录 
	* @param @param examPaperUser
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExamUserAnswer examUserAnswer);
}
