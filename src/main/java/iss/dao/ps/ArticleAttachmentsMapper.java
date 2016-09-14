package iss.dao.ps;

import iss.model.ps.ArticleAttachments;

import java.util.List;


/*
 *@Title:ArticleAttachmentsMapper
 *@Description:信息附件功能Mapper接口
 *@author QuJie
 *@date 2016-8-25下午3:34:52
 */
public interface ArticleAttachmentsMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author QuJie 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insert
    * @author QuJie 
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
    * @author QuJie 
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
    * @author QuJie 
    * @date 2016-8-25 下午3:36:57  
    * @Description: 根据id查询信息 
    * @param @param id
    * @param @return      
    * @return ArticleAttachments
     */
    ArticleAttachments selectArticleAttaByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author QuJie 
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
    * @author QuJie 
    * @date 2016-8-25 下午3:37:43  
    * @Description: 修改一条信息
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(ArticleAttachments record);
    
    /**
     * 
    * @Title: selectAllArticleAttachments
    * @author QuJie 
    * @date 2016-9-8 上午8:45:03  
    * @Description: 根据articleId查询信息附件 
    * @param @param id
    * @param @return      
    * @return List<ArticleAttachments>
     */
    List<ArticleAttachments> selectAllArticleAttachments(String id);
    
    /**
     * 
    * @Title: softDeleteAtta
    * @author QuJie 
    * @date 2016-9-9 下午1:22:11  
    * @Description: 假删除附件 
    * @param @param id      
    * @return void
     */
    void softDeleteAtta(String id);
}