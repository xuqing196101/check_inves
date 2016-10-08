package bss.service.ppms;

import java.util.List;

import bss.model.ppms.Project;

/**
 * 
* @Title:ProjectService
* @Description: 项目管理业务逻辑接口 
* @author FengTian
* @date 2016-9-27上午10:19:28
 */
public interface ProjectService {
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-27 上午10:20:15  
	* @Description: 添加 
	* @param @param project      
	* @return void
	 */
		void add(Project project);
	/**
	 * 	
	* @Title: update
	* @author FengTian
	* @date 2016-9-27 上午10:21:04  
	* @Description: 修改 
	* @param @param project      
	* @return void
	 */
		void update(Project project);
	/**
	 * 	
	* @Title: delete
	* @author FengTian
	* @date 2016-9-27 上午10:21:47  
	* @Description: 删除 
	* @param @param id      
	* @return void
	 */
		void delete(String id);
	/**
	 * 	
	* @Title: selectById
	* @author FengTian
	* @date 2016-9-27 上午10:22:36  
	* @Description: 根据id查询 
	* @param @param id
	* @param @return      
	* @return Project
	 */
		Project selectById(String id);
	/**
	 * 	
	* @Title: list
	* @author FengTian
	* @date 2016-9-27 上午10:23:49  
	* @Description: 分页查询 
	* @param @param page
	* @param @param project
	* @param @return      
	* @return List<Project>
	 */
	   List<Project> list(Integer page,Project project);
	   
	   List<Project> selectSuccessProject(Integer page,Project project);
	   /**
	    * 
	   * @Title: selectByTask
	   * @author FengTian
	   * @date 2016-9-28 下午1:53:41  
	   * @Description: 查询任务 
	   * @param @param id
	   * @param @return      
	   * @return List<Project>
	    */
	   List<Project> selectByTask(String id);
}
