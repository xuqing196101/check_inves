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
    * @date 2016-9-7 上午10:27:27  
    * @Description: 新增用户成绩 
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
    * @Description: 根据ID更新用户成绩 
    * @param @param examUserScore
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectExpertResultByCondition
    * @author ZhaoBo
    * @date 2016-9-19 下午8:54:53  
    * @Description: 专家考试成绩按条件查询 
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectExpertResultByCondition(ExamUserScore examUserScore);
    
    List<ExamUserScore> selectExpertResultByTerm(ExamUserScore examUserScore);
}