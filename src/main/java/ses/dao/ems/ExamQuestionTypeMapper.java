package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExamQuestionType;


/**
 * 
* @Title:ExamPoolTypeMapper 
* @Description: 题型接口类
* @author ZhaoBo
* @date 2016-9-7上午10:16:12
 */
public interface ExamQuestionTypeMapper {
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