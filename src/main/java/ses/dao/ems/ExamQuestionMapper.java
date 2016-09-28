package ses.dao.ems;

import java.util.HashMap;
import java.util.List;
import ses.model.ems.ExamQuestion;


/**
 * 
* @Title:ExamPoolMapper 
* @Description:题库接口类 
* @author ZhaoBo
* @date 2016-9-7上午9:57:13
 */
public interface ExamQuestionMapper {
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
	* @Description: 新增题目 
	* @param @param examQuestion
	* @param @return      
	* @return int
	 */
    int insertSelective(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:02  
    * @Description: 根据主键ID查找题库 
    * @param @param id
    * @param @return      
    * @return examQuestion
     */
    ExamQuestion selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author ZhaoBo
    * @date 2016-9-7 上午9:58:20  
    * @Description:	根据主键ID更新题库 
    * @param @param examQuestion
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: searchTecExpPool
    * @author zb
    * @date 2016-8-24 上午9:18:26  
    * @Description: 查询技术类专家题库
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> searchTecExpPool(ExamQuestion examQuestion,Integer pageNum);
    
    /**
     * 
    * @Title: searchComExpPool
    * @author zb
    * @date 2016-8-24 上午9:44:05  
    * @Description: 查询商务类专家题库 
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> searchComExpPool(ExamQuestion examQuestion,Integer pageNum);
    
    /**
     * 
    * @Title: searchLawExpPool
    * @author zb
    * @date 2016-8-24 上午10:20:30  
    * @Description: 查询法律类专家题库 
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> searchLawExpPool(ExamQuestion examQuestion,Integer pageNum);
    
    /**
     * 
    * @Title: selectTecRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从技术类题库中随机抽题 
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> selectTecRandom(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectComRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从商务类题库中随机抽题 
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> selectComRandom(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectLawRandom
    * @author zb
    * @date 2016-8-26 上午9:49:39  
    * @Description: 从法律类题库中随机抽题 
    * @param @return      
    * @return List<ExamPool>
     */
    List<ExamQuestion> selectLawRandom(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: queryPurchaserByTerm
    * @author zb
    * @date 2016-8-31 下午5:34:43  
    * @Description: 采购人题库按条件查询 
    * @param @param examPool
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> queryPurchaserByTerm(HashMap<String, Object> map);
    
    /**
     * 
    * @Title: selectSingleRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:16:28  
    * @Description: 采购人根据考卷题型随机查找单选题 
    * @param @param singleNum
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> selectSingleRandom(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectMultipleRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:18:03  
    * @Description: 采购人根据考卷题型随机查找多选题  
    * @param @param multipleNum
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> selectMultipleRandom(ExamQuestion examQuestion);
    
    /**
     * 
    * @Title: selectJudgeRandom
    * @author ZhaoBo
    * @date 2016-9-6 下午2:18:52  
    * @Description: 采购人根据考卷题型随机查找判断题  
    * @param @param judgeNum
    * @param @return      
    * @return List<examQuestion>
     */
    List<ExamQuestion> selectJudgeRandom(ExamQuestion examQuestion);
    
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