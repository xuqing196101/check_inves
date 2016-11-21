/**
 * 
 */
package bss.dao.dms;

import java.util.HashMap;
import java.util.List;

import bss.model.dms.ArchiveBorrow;


/**
 * @Title:ArchiveBorrow
 * @Description: 档案借阅接口
 * @author ZhaoBo
 * @date 2016-10-26下午4:34:47
 */
public interface ArchiveBorrowMapper {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-10-27 下午12:49:57  
	* @Description: 新增 
	* @param @param archiveBorrow
	* @param @return      
	* @return int
	 */
	int insertSelective(ArchiveBorrow archiveBorrow);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-10-27 下午12:50:00  
	* @Description: 更新 
	* @param @param archiveBorrow
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(ArchiveBorrow archiveBorrow);
	
	List<ArchiveBorrow> findArchiveById(HashMap<String,Object> map);
	
	List<ArchiveBorrow> findArchiveIdByUserId(String userId);
}
