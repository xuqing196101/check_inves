/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.ems.ExamQuestionType;

/**
 * @Title:ExamPoolTypeServiceI 
 * @Description:题型Service
 * @author zb
 * @date 2016-9-5下午1:19:07
 */
public interface ExamQuestionTypeServiceI {
	/**
	 * 
	* @Title: selectExpertAll
	* @author zb
	* @date 2016-9-5 下午1:17:51  
	* @Description: 查询专家的所有题目类型 
	* @param @return      
	* @return List<ExamPoolType>
	 */
    List<ExamQuestionType> selectExpertAll();
    
    /**
     * 
    * @Title: selectPurchaserAll
    * @author zb
    * @date 2016-9-5 下午1:18:25  
    * @Description: 查询采购人的所有题目类型 
    * @param @return      
    * @return List<ExamPoolType>
     */
    List<ExamQuestionType> selectPurchaserAll();
}
