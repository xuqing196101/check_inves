package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ProjectAdvice;

public interface ProjectAdviceMapper {
	
	/**
	 * 
	* @Title: selectById
	* @author FengTian 
	* @date 2017-10-25 上午9:51:11  
	* @Description: 根据ID查询 
	* @param @param id
	* @param @return      
	* @return ProjectAdvice
	 */
	ProjectAdvice selectById(String id);
	
	/**
	 * 
	* @Title: findByList
	* @author FengTian 
	* @date 2017-10-25 上午9:52:34  
	* @Description: 条件查询 
	* @param @param map
	* @param @return      
	* @return List<ProjectAdvice>
	 */
	List<ProjectAdvice> findByList(HashMap<String, Object> map);
	
	/**
	 * 
	* @Title: insert
	* @author FengTian 
	* @date 2017-10-25 上午9:53:48  
	* @Description: 新增 
	* @param @param ProjectAdvice      
	* @return void
	 */
	void insert(ProjectAdvice ProjectAdvice);
	
	/**
	 * 
	* @Title: update
	* @author FengTian 
	* @date 2017-10-25 上午9:53:55  
	* @Description: 修改 
	* @param @param ProjectAdvice      
	* @return void
	 */
	void update(ProjectAdvice ProjectAdvice);
	
}