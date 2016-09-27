package bss.service.ppms;

import java.util.List;

import bss.model.ppms.TaskAttachments;

/**
 * 
* @Title:TaskAttachmentsService
* @Description: 任务管理业务逻辑接口
* @author FengTian
* @date 2016-9-21下午3:28:36
 */
public interface TaskAttachmentsService {
	/**
	 * 
	* @Title: save
	* @author FengTian
	* @date 2016-9-21 下午3:31:18  
	* @Description: 新增 
	* @param @param att      
	* @return void
	 */
	void save(TaskAttachments att);
	
	/**
	 * 
	* @Title: delete
	* @author FengTian
	* @date 2016-9-21 下午3:32:28  
	* @Description: 删除
	* @param @param id      
	* @return void
	 */
	void delete(String id);
	
	/**
	 * 
	* @Title: selectAttById
	* @author FengTian
	* @date 2016-9-21 下午3:34:06  
	* @Description: 根据TaskId查询所有信息附件  
	* @param @param id
	* @param @return      
	* @return List<TaskAttachments>
	 */
	List<TaskAttachments> selectAttById(String id);
	
	/**
	 * 
	* @Title: selectById
	* @author FengTian
	* @date 2016-9-21 下午3:35:52  
	* @Description: 根据id查询信息 
	* @param @param id
	* @param @return      
	* @return TaskAttachments
	 */
	TaskAttachments selectById(String id);
}
