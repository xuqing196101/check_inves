package bss.dao.ppms;



import java.util.List;

import bss.model.ppms.ProjectAttachments;


/**
 * 
* @Title:ProjectAttachmentsMapper
* @Description: 任务管理持久层接口 
* @author FengTian
* @date 2016-9-21上午11:00:58
 */
public interface ProjectAttachmentsMapper {
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
    int insert(ProjectAttachments record);
    
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
    int insertSelective(ProjectAttachments record);
    
    /**
     * 
    * @Title: selectProjectAttaByPrimaryKey
    * @author FengTian
    * @date 2016-9-21 上午11:02:57  
    * @Description: 根据id查询 
    * @param @param id
    * @param @return      
    * @return ProjectAttachments
     */
    ProjectAttachments selectProjectAttaByPrimaryKey(String id);
    
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
    int updateByPrimaryKeySelective(ProjectAttachments record);
    
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
    int updateByPrimaryKey(ProjectAttachments record);
    
    /**
     * 
    * @Title: selectAllProjectAttachments
    * @author FengTian
    * @date 2016-9-21 上午11:04:10  
    * @Description: 根据articleId查询信息附件
    * @param @param id
    * @param @return      
    * @return List<ProjectAttachments>
     */
    List<ProjectAttachments> selectAllProjectAttachments(String id);
    
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