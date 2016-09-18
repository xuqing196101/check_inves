/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.ems.ExamUserScore;


/**
 * 
* @Title:ExamUserScoreServiceI
* @Description:用户成绩Service
* @author ZhaoBo
* @date 2016-9-7上午10:25:44
 */
public interface ExamUserScoreServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 下午5:57:10  
	* @Description: 根据ID删除用户成绩 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-7 下午5:57:14  
	* @Description: 插入用户成绩 
	* @param @param record
	* @param @return      
	* @return int
	 */
    int insertSelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 下午5:57:20  
    * @Description: 根据ID更新用户成绩 
    * @param @param id
    * @param @return      
    * @return ExamUserScore
     */
    ExamUserScore selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 下午5:57:24  
    * @Description: 根据ID更新用户成绩 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: expertQueryList
    * @author zb
    * @date 2016-8-31 下午1:14:18  
    * @Description: 专家考试成绩查询(查3个条件)
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> expertQueryList(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: QueryListByName
    * @author zb
    * @date 2016-8-31 下午1:15:15  
    * @Description: 专家考试成绩查询(通过姓名查找) 
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> queryListByName(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: QueryListByDuty
    * @author zb
    * @date 2016-8-31 下午1:16:13  
    * @Description: 专家考试成绩查询(通过专家类型查找) 
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> queryListByDuty(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: QueryListByState
    * @author zb
    * @date 2016-8-31 下午1:17:26  
    * @Description: 专家考试成绩查询(通过考试状态查找) 
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> queryListByState(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: QueryListByDuSt
    * @author zb
    * @date 2016-8-31 下午1:19:33  
    * @Description: 通过考试状态及专家类型查询
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> queryListByDuSt(ExamUserScore examUserScore);
    
    List<ExamUserScore> selectExpertResultByTerm(ExamUserScore examUserScore);
}
