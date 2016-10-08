/**
 * 
 */
package ses.service.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExamPaperUser;

/**
 * @Title:ExamPaperUserServiceI
 * @Description: 考卷参考人员Service
 * @author ZhaoBo
 * @date 2016-9-13下午2:12:01
 */
public interface ExamPaperUserServiceI {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-13 下午2:09:42  
	* @Description: 根据ID删除参考人员 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:02  
	* @Description: 新增参考人员 
	* @param @param examPaperUser
	* @param @return      
	* @return int
	 */
	int insertSelective(ExamPaperUser examPaperUser);
	
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:18  
	* @Description: 根据ID查询参考人员 
	* @param @param id
	* @param @return      
	* @return ExamPaperUser
	 */
	ExamPaperUser selectByPrimaryKey(String id);
	    
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-9-13 下午2:10:40  
	* @Description: 根据ID更新参考人员信息 
	* @param @param examPaperUser
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExamPaperUser examPaperUser);
	
	/**
	 * 
	* @Title: updateByPaperIdAndUserID
	* @author ZhaoBo
	* @date 2016-9-28 下午2:04:11  
	* @Description: 根据考卷ID和用户ID更新参考人员考试状态 
	* @param @param examPaperUser
	* @param @return      
	* @return int
	 */
	int updateByPaperIdAndUserID(ExamPaperUser examPaperUser);
	
	/**
	 * 
	* @Title: getAllByPaperId
	* @author ZhaoBo
	* @date 2016-9-21 上午10:59:57  
	* @Description: 根据考试编号查找参考人员 
	* @param @param examPaperUser
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> getAllByPaperId(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: getAllPaperByUserId
	* @author ZhaoBo
	* @date 2016-9-21 下午8:59:02  
	* @Description: 根据userId查找所参考的考卷 
	* @param @param examPaperUser
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> getAllPaperByUserId(ExamPaperUser examPaperUser);
	
	/**
	 * 
	* @Title: selectPurchaserYesReference
	* @author ZhaoBo
	* @date 2016-9-22 下午3:04:05  
	* @Description: 查看已考考卷的参考人员信息 
	* @param @param examPaperUser
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> selectPurchaserYesReference(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: selectPrintYesReference
	* @author ZhaoBo
	* @date 2016-9-24 上午11:14:52  
	* @Description: 打印预览已考考卷的参考人员信息 
	* @param @param examPaperUser
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> selectPrintYesReference(ExamPaperUser examPaperUser);
	
	/**
	 * 
	* @Title: findAll
	* @author ZhaoBo
	* @date 2016-9-25 下午8:41:55  
	* @Description: 查找所有考卷的参考人员 
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> findAll();
	
	/**
	 * 
	* @Title: findIsExamByCondition
	* @author ZhaoBo
	* @date 2016-10-8 上午9:27:17  
	* @Description: 根据userId和paperId判断用户可不可以再次登录 
	* @param @param map
	* @param @return      
	* @return List<ExamPaperUser>
	 */
	List<ExamPaperUser> findIsExamByCondition(HashMap<String,Object> map);
}
