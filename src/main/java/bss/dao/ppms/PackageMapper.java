/**
 * 
 */
package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.Packages;

/**
 * @Title:PackageMapper
 * @Description: 
 * @author ZhaoBo
 * @date 2016-10-9下午2:01:43
 */
public interface PackageMapper {
	/**
	 * 
	* @Title: insertSelective
	* @author ZhaoBo
	* @date 2016-10-9 下午2:13:01  
	* @Description: 新增分包 
	* @param @param pack
	* @param @return      
	* @return int
	 */
	int insertSelective(Packages pack);
	
	/**
	 * 
	* @Title: updateByPrimaryKeySelective
	* @author ZhaoBo
	* @date 2016-10-9 下午2:13:37  
	* @Description: 更新分包 
	* @param @param pack
	* @param @return      
	* @return int
	 */
	int updateByPrimaryKeySelective(Packages pack);
	
	/**
	 * 
	* @Title: findPackageById
	* @author ZhaoBo
	* @date 2016-10-9 下午2:25:15  
	* @Description: 根据项目Id查找包名 
	* @param @param map
	* @param @return      
	* @return List<Package>
	 */
	List<Packages> findPackageById(HashMap<String,Object> map);
	
	
	List<Packages> selectByPrimaryKey(HashMap<String,Object> map);
	
	/**
	 * @Title: findPackageByCondition
	 * @author Song Biaowei
	 * @date 2016-10-27 下午1:33:11  
	 * @Description: 根据项目Id查找包名  
	 * @param @param str
	 * @param @return      
	 * @return List<Packages>
	 */
	List<Packages> findPackageByCondition(String str);
	
	
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author ZhaoBo
	* @date 2016-10-18 下午3:04:26  
	* @Description: 根据Id删除 
	* @param @param id
	* @param @return      
	* @return int
	 */
	int deleteByPrimaryKey(String id);
}
