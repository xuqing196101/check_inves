/**
 * 
 */
package ses.dao.ems;

import java.util.HashMap;
import java.util.List;

import ses.model.ems.ExpertPaperUser;

/**
 * @Title:ExpertPaperUserMapper
 * @Description: 
 * @author ZhaoBo
 * @date 2016-11-17上午9:40:28
 */
public interface ExpertPaperUserMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-11-17 上午10:16:56  
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
	* @date 2016-11-17 上午10:17:45  
	* @Description: 新增参考人员 
	* @param @param expertPaperUser
	* @param @return      
	* @return int
	 */
	int insertSelective(ExpertPaperUser expertPaperUser);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-11-17 上午10:20:49  
	* @Description: 更新参考人员信息 
	* @param @param expertPaperUser
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ExpertPaperUser expertPaperUser);
	
	/**
	 * 
	* @Title: selectByPrimaryKey
	* @author ZhaoBo
	* @date 2016-11-17 上午10:19:57  
	* @Description: 根据ID查找专家参考人员 
	* @param @param id
	* @param @return      
	* @return ExpertPaperUser
	 */
	ExpertPaperUser selectByPrimaryKey(String id);
	
	/**
	 * 
	* @Title: updateById
	* @author ZhaoBo
	* @date 2016-11-17 下午4:13:54  
	* @Description: 更新信息 
	* @param @param expertPaperUser
	* @param @return      
	* @return int
	 */
	int updateById(ExpertPaperUser expertPaperUser);
	
	/**
	 * 
	* @Title: findAll
	* @author ZhaoBo
	* @date 2016-11-17 上午10:21:41  
	* @Description: 按条件查询参考人员 
	* @param @param map
	* @param @return      
	* @return List<ExpertPaperUser>
	 */
	List<ExpertPaperUser> findAll(HashMap<String,Object> map);
	
	/**
	 * 
	* @Title: findNoTest
	* @author ZhaoBo
	* @date 2016-11-17 下午4:49:57  
	* @Description: 查看未参考人员 
	* @param @param ruleId
	* @param @return      
	* @return List<ExpertPaperUser>
	 */
	List<ExpertPaperUser> findNoTest(String ruleId);
	
	/**
	 * 
	* @Title: findNoTest
	* @author ZhaoBo
	* @date 2016-11-17 下午4:49:57  
	* @Description: 查看自己未参考的
	* @param @param ruleId
	* @param @return      
	* @return List<ExpertPaperUser>
	 */
	List<ExpertPaperUser> findNoTestById(HashMap<String,Object> map);
}
