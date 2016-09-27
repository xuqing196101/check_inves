package bss.dao.ppms;

import bss.model.ppms.TaskAttachments;

import java.util.List;


/**
 * 
* @Title:TaskAttachmentsMapper
* @Description: 任务管理持久层接口 
* @author FengTian
* @date 2016-9-21上午11:00:58
 */
public interface TaskAttachmentsMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author FengTian
	* @date 2016-9-21 上午11:01:41  
	* @Description: 根据id删除 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
  /**
   * 
  * @Title: insert
  * @author FengTian
  * @date 2016-9-21 上午11:02:08  
  * @Description: 新增一条数据 
  * @param @param record
  * @param @return      
  * @return int
   */
    int insert(TaskAttachments record);
    
   /**
    * 
   * @Title: insertSelective
   * @author FengTian
   * @date 2016-9-21 上午11:02:29  
   * @Description: 根据条件新增一条数据 
   * @param @param record
   * @param @return      
   * @return int
    */
    int insertSelective(TaskAttachments record);
    
    /**
     * 
    * @Title: selectTaskAttaByPrimaryKey
    * @author FengTian
    * @date 2016-9-21 上午11:02:57  
    * @Description: 根据id查询 
    * @param @param id
    * @param @return      
    * @return TaskAttachments
     */
    TaskAttachments selectTaskAttaByPrimaryKey(String id);
    
   /**
    * 
   * @Title: updateByPrimaryKeySelective
   * @author FengTian
   * @date 2016-9-21 上午11:03:28  
   * @Description: 根据条件修改信息
   * @param @param record
   * @param @return      
   * @return int
    */
    int updateByPrimaryKeySelective(TaskAttachments record);
    
  /**
   * 
  * @Title: updateByPrimaryKey
  * @author FengTian
  * @date 2016-9-21 上午11:03:43  
  * @Description: 修改信息
  * @param @param record
  * @param @return      
  * @return int
   */
    int updateByPrimaryKey(TaskAttachments record);
    
    /**
     * 
    * @Title: selectAllTaskAttachments
    * @author FengTian
    * @date 2016-9-21 上午11:04:10  
    * @Description: 根据articleId查询信息附件
    * @param @param id
    * @param @return      
    * @return List<TaskAttachments>
     */
    List<TaskAttachments> selectAllTaskAttachments(String id);
    
    /**
     * 
    * @Title: softDeleteAtta
    * @author FengTian
    * @date 2016-9-21 上午11:04:24  
    * @Description: 假删除附件 
    * @param @param id      
    * @return void
     */
    void softDeleteAtta(String id);
}