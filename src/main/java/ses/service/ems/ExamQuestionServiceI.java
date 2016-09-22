/**
 * 
 */
package ses.service.ems;

import java.util.List;

import ses.model.ems.ExamQuestion;



/**
 * 
* @Title:ExamPoolServiceI 
* @Description: 
* @author ZhaoBo
* @date 2016-9-7上午10:01:08
 */
public interface ExamQuestionServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-7 上午9:57:29  
	* @Description: 根据主键ID删除题库
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-7 上午9:57:49  
	* @Description: 插入题库 
	* @param @param examPool
	* @param @return      
	* @return int
	 */
    int insertSelective(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:02  
    * @Description: 根据主键ID查找题库 
    * @param @param id
    * @param @return      
    * @return ExamPool
     */
    ExamQuestion selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:20  
    * @Description:	根据主键ID更新题库 
    * @param @param examPool
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamQuestion examPool);
    
    /**
     * 
    * @Title: searchTecExpPool
    * @author zb
    * @date 2016-8-24 上午9:18:26  
    * @Description: 查询技术类专家题库
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> searchTecExpPool();
    
    /**
     * 
    * @Title: searchComExpPool
    * @author zb
    * @date 2016-8-24 上午9:44:05  
    * @Description: 查询商务类专家题库 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> searchComExpPool();
    
    /**
     * 
    * @Title: searchLawExpPool
    * @author zb
    * @date 2016-8-24 上午10:20:30  
    * @Description: 查询法律类专家题库 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> searchLawExpPool(ExamQuestion examPool,Integer pageNum);
    
    /**
     * 
    * @Title: selectTecRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从技术类题库中随机抽题 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectTecRandom(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectComRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从商务类题库中随机抽题 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectComRandom(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectLawRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从法律类题库中随机抽题 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectLawRandom(ExamQuestion examPool);
    
    /**
     * 
    * @Title: queryPurchaserByTerm
    * @author zb
    * @date 2016-8-31 下午5:34:43  
    * @Description: 采购人题库按条件查询 
    * @param @param examPool
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> queryPurchaserByTerm(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectSingleRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:16:28  
    * @Description: 采购人根据考卷题型随机查找单选题 
    * @param @param singleNum
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectSingleRandom(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectMultipleRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:18:03  
    * @Description: 采购人根据考卷题型随机查找多选题  
    * @param @param multipleNum
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectMultipleRandom(ExamQuestion examPool);
    
    /**
     * 
    * @Title: selectJudgeRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:18:52  
    * @Description: 采购人根据考卷题型随机查找判断题  
    * @param @param judgeNum
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectJudgeRandom(ExamQuestion examPool);
    
    List<ExamQuestion> selectAllContent();
    
    /**
     * 
    * @Title: getAllPurchaserQuestion
    * @author ZhaoBo
    * @date 2016-9-19 下午1:25:19  
    * @Description: 查询所有的采购人题目 
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> getAllPurchaserQuestion();
    
    /**
     * 
    * @Title: searchExpertPool
    * @author ZhaoBo
    * @date 2016-9-21 上午8:52:30  
    * @Description: 查询专家题库中有没有题目 
    * @param @return      
    * @return List<ExamQuestion>
     */
    List<ExamQuestion> searchExpertPool();
}
