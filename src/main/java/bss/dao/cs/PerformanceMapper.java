package bss.dao.cs;

import java.util.List;
import java.util.Map;

import bss.model.cs.Performance;
/**
 * 
 *@Title:PerformanceMapper
 *@Description:履约情况Mapper
 *@author QuJie
 *@date 2016-11-11下午3:14:57
 */
public interface PerformanceMapper {
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
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:15:26  
    * @Description: 新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(Performance record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:15:40  
    * @Description: 根据条件新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(Performance record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:16:01  
    * @Description: 根据条件查询 
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
    * @date 2016-11-11 下午3:16:18  
    * @Description: 根据条件修改 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(Performance record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:16:36  
    * @Description: 根据id修改 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(Performance record);
    
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午3:16:50  
    * @Description: 根据id查询
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
    * @date 2016-11-11 下午3:21:36  
    * @Description: 查询所有 
    * @param @return      
    * @return List<Performance>
     */
    List<Performance> selectAll(Map<String, Object> map);
}