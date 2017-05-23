package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.pms.PurchaseDetail;
import bss.model.ppms.Task;

/**
 * 
* @Title:TaskService
* @Description: 任务管理业务逻辑接口  
* @author FengTian
* @date 2016-9-18下午4:11:40
 */
public interface TaskService {
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-18 下午3:52:53  
	* @Description: 添加方法
	* @param @param task      
	* @return void
	 */
	 void add(Task task);
	 /**
	  * 
	 * @Title: update
	 * @author FengTian
	 * @date 2016-9-18 下午4:01:11  
	 * @Description: 修改方法
	 * @param @param task      
	 * @return void
	  */
	 void update(Task task);
	 /**
	  * 
	 * @Title: selectById
	 * @author FengTian
	 * @date 2016-9-18 下午4:02:33  
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return      
	 * @return Task
	  */
	 Task selectById(String id);
	 /**
	  * 
	 * @Title: listAll
	 * @author FengTian
	 * @date 2016-9-18 下午4:05:58  
	 * @Description: 分页查询 
	 * @param @param page
	 * @param @return      
	 * @return List<Task>
	  */
	 List<Task> listAll(Integer page,Task task);
	 /**
	  * 
	 * @Title: softDelete
	 * @author FengTian
	 * @date 2016-9-22 上午10:34:19  
	 * @Description: 假删除 
	 * @param @param id      
	 * @return void
	  */
	 void softDelete(String id);
	 /**
	  * 
	 * @Title: startTask
	 * @author FengTian
	 * @date 2016-9-27 下午5:33:07  
	 * @Description: 启动 
	 * @param @param id      
	 * @return void
	  */
	 void startTask(String id);
	 /**
	  * 
	 * @Title: selectByProject
	 * @author FengTian
	 * @date 2016-9-30 上午9:31:10  
	 * @Description: 根据项目id查询任务 
	 * @param @param id
	 * @param @return      
	 * @return List<Task>
	  */
	 List<Task> selectByProject(String id,Integer page);
	 
	 List<Task> selectByProjectId(String id);
	 
	 List<Task> listByTask(Task task,Integer page);
	 
	 boolean verify(Task task);
	 
	 List<Task> likeByName(HashMap<String, Object> map);
	 
	 List<Task> listByProjectTask(HashMap<String,Object> map);
	 
	 Task selectByCollectId(String id);
	 
	 List<Task> listBycollect(HashMap<String, Object> map);
	 
	 /**
	  * 
	  *〈如果采购明细状态都不显示了，将这条任务也不显示〉
	  *〈详细描述〉
	  * @author FengTian
	  * @param list
	  * @param taskId
	  */
	 void updateDetail(List<PurchaseDetail> list, String taskId);
}
