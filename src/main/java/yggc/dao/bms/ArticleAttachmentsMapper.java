package yggc.dao.bms;

import yggc.model.bms.ArticleAttachments;

/**
 * 
 *<p>Title:ArticleAttachmentsMapper</p>
 *<p>Description:信息附件功能Mapper接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-25下午3:34:52
 */
public interface ArticleAttachmentsMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(Integer id);
    
    /**
     * 
    * @Title: insert
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:36:18  
    * @Description: 新增一条信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(ArticleAttachments record);
    
    /**
     * 
    * @Title: insertSelective
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:36:38  
    * @Description: 根据条件新增一条信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(ArticleAttachments record);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:36:57  
    * @Description: 根据id查询信息 
    * @param @param id
    * @param @return      
    * @return ArticleAttachments
     */
    ArticleAttachments selectByPrimaryKey(Integer id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:37:21  
    * @Description: 根据条件修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ArticleAttachments record);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:37:43  
    * @Description: 修改一条信息
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(ArticleAttachments record);
}