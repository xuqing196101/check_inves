/**
 * 
 */
package bss.service.dms;

import java.util.HashMap;
import java.util.List;

import bss.model.dms.ProbationaryArchive;

/**
 * @Title:ProbationaryArchiveServiceI
 * @Description: 
 * @author ZhaoBo
 * @date 2016-11-14上午9:28:06
 */
public interface ProbationaryArchiveServiceI {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:48  
	* @Description: 新增预备档案 
	* @param @param probationaryArchive
	* @param @return      
	* @return int
	 */
	int insertSelective(ProbationaryArchive probationaryArchive);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-10-19 上午9:00:52  
	* @Description: 更新预备档案 
	* @param @param probationaryArchive
	* @param @return      
	* @return 
	 */
	int updateByPrimaryKeySelective(ProbationaryArchive probationaryArchive);
	
	List<ProbationaryArchive> selectAll(HashMap<String,Object> map);
	
	ProbationaryArchive selectById(String id);
}
