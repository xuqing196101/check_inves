/**
 * 
 */
package ses.dao.ems;

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
	* @Title: select
	* @author ZhaoBo
	* @date 2016-9-8 下午1:09:51  
	* @Description: 查找数据库中的考试规则 
	* @param @return      
	* @return List<ExamRule>
	 */
	List<ExamRule> select();
	
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
}
