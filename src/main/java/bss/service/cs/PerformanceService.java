package bss.service.cs;

import java.util.List;
import java.util.Map;

import bss.model.cs.Performance;
/**
 * 
 *@Title:PerformanceService
 *@Description:履约service
 *@author QuJie
 *@date 2016-11-11下午3:27:14
 */
public interface PerformanceService {
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:27:29  
	* @Description: 根据条件新增
	* @param @param performance      
	* @return void
	 */
	void insertSelective(Performance performance);
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:27:58  
	* @Description: 根据id集合查询 
	* @param @param map
	* @param @return      
	* @return List<Performance>
	 */
	List<Performance> selectAllByidArray(Map<String, Object> map);
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:30:56  
	* @Description: 查询所有 
	* @param @param map
	* @param @return      
	* @return List<Performance>
	 */
	List<Performance> selectAll(Map<String, Object> map);
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:31:06  
	* @Description: 根据id查询 
	* @param @param id
	* @param @return      
	* @return Performance
	 */
	Performance selectByPrimaryKey(String id);
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:31:18  
	* @Description: 根据条件修改 
	* @param @param performance      
	* @return void
	 */
	void updateSelective(Performance performance);
	
	/**
	 * 
	* 〈简述〉 〈详细描述〉
	* 
	* @author QuJie 
	* @date 2016-11-11 下午3:14:53  
	* @Description: 根据id删除
	* @param @param id
	* @param @return      
	* @return int
	 */
    void deleteByPrimaryKey(String id);
}
