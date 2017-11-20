package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.PackageAdvice;

public interface PackageAdviceMapper {
	
	/**
	 * 
	* @Title: selectById
	* @author FengTian 
	* @date 2017-10-25 上午9:51:11  
	* @Description: 根据ID查询 
	* @param @param id
	* @param @return      
	* @return PackageAdvice
	 */
	PackageAdvice selectById(String id);
	
	/**
	 * 
	* @Title: findByList
	* @author FengTian 
	* @date 2017-10-25 上午9:52:34  
	* @Description: 条件查询 
	* @param @param map
	* @param @return      
	* @return List<PackageAdvice>
	 */
	List<PackageAdvice> findByList(HashMap<String, Object> map);
	
	/**
	 * 
	* @Title: insert
	* @author FengTian 
	* @date 2017-10-25 上午9:53:48  
	* @Description: 新增 
	* @param @param packageAdvice      
	* @return void
	 */
	void insert(PackageAdvice packageAdvice);
	
	/**
	 * 
	* @Title: update
	* @author FengTian 
	* @date 2017-10-25 上午9:53:55  
	* @Description: 修改 
	* @param @param packageAdvice      
	* @return void
	 */
	void update(PackageAdvice packageAdvice);
	
	/**
	 * 
	* @Title: findByProjectList
	* @author FengTian 
	* @date 2017-10-25 下午4:46:22  
	* @Description: 管理部门审核条件查询 
	* @param @param map
	* @param @return      
	* @return List<PackageAdvice>
	 */
	List<PackageAdvice> findByProjectList(HashMap<String, Object> map);
	
	
	List<PackageAdvice> selectByStatus(String projectId);
	
}