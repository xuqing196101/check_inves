package ses.dao.ems;

import java.util.HashMap;
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
    * @Title: updateIsMaxById
    * @author ZhaoBo
    * @date 2016-9-28 下午2:42:33  
    * @Description: 根据用户ID和考卷ID更新采购人考试成绩的ISMAx状态 
    * @param @param examUserScore
    * @param @return      
    * @return int
     */
    int updateIsMaxById(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: updateIsMaxByUserId
    * @author ZhaoBo
    * @date 2016-9-28 下午3:17:40  
    * @Description: 根据用户ID和考卷ID更新专家考试成绩的ISMAx状态 
    * @param @param examUserScore
    * @param @return      
    * @return int
     */
    int updateIsMaxByUserId(ExamUserScore examUserScore);
    
    /**
     * 
    * @Title: selectExpertResultByCondition
    * @author ZhaoBo
    * @date 2016-9-23 下午4:08:14  
    * @Description: 专家考试成绩按条件查询 
    * @param @param map
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectExpertResultByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: selectPurchaserResultByCondition
    * @author ZhaoBo
    * @date 2016-9-22 上午11:13:51  
    * @Description: 采购人考试成绩按条件查询 
    * @param @param map
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> selectPurchaserResultByCondition(HashMap<String,Object> map);
    
    /**
     * 
    * @Title: findByUserId
    * @author ZhaoBo
    * @date 2016-9-28 上午11:31:17  
    * @Description: 根据userId查找属于用户的成绩
    * @param @param examUserScore
    * @param @return      
    * @return List<ExamUserScore>
     */
    List<ExamUserScore> findByUserId(ExamUserScore examUserScore);
}