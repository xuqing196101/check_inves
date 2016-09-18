package ses.dao.ems;

import java.util.List;

import ses.model.ems.ExamUserScore;
/**
 * 
* @Title:ExamUserScoreMapper 
* @Description:用户考试成绩表
* @author ZhaoBo
* @date 2016-9-7上午10:21:16
 */
public interface ExamUserScoreMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 上午10:26:36  
	* @Description: TODO 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insertSelective
    * @author ZhaoBo
    * @date 2016-9-7 上午10:27:27  
    * @Description: TODO 
    * @param @param examUserScore
    * @param @return      
    * @return int
     */
    int insertSelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 上午10:27:31  
    * @Description: 根据主键ID查找用户成绩 
    * @param @param id
    * @param @return      
    * @return ExamUserScore
     */
    ExamUserScore selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 上午10:27:35  
    * @Description: TODO 
    * @param @param examUserScore
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
    * @Title: queryListByName
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
    * @Title: queryListByDuty
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
    * @Title: queryListByState
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
    * @Title: queryListByDuSt
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