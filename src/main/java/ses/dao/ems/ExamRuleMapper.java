/**
 * 
 */
package ses.dao.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExamRule;



/**
 * @Title:ExamRuleMapper
 * @Description: 专家考试规则管理接口
 * @author ZhaoBo
 * @date 2016-9-8上午10:49:20
 */
public interface ExamRuleMapper {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-8 上午10:52:51  
	* @Description: 新增考试规则 
	* @param @param examRule
	* @param @return      
	* @return int
	 */
	int insertSelective(ExamRule examRule);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-9-8 上午10:52:59  
	* @Description: 更新考试规则 
	* @param @param examRule
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExamRule examRule);
	
	/**
	 * 
	* @Title: selectById
	* @author ZhaoBo
	* @date 2016-11-16 下午12:27:30  
	* @Description: 按条件查找考试规则 
	* @param @param id
	* @param @return      
	* @return ExamRule
	 */
	List<ExamRule> selectById(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: selectInUseRule
	* @author ZhaoBo
	* @date 2016-11-27 下午4:38:15  
	* @Description: 查找使用中的考试规则 
	* @param @return      
	* @return List<ExamRule>
	 */
	List<ExamRule> selectInUseRule();
}
