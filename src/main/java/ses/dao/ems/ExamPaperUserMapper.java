/**
 * 
 */
package ses.dao.ems;

import ses.model.ems.ExamPaperUser;



/**
 * @Title:ExamPaperUserMapper
 * @Description: 参考人员接口
 * @author ZhaoBo
 * @date 2016-9-13下午2:08:24
 */
public interface ExamPaperUserMapper {
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
}
