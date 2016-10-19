package iss.service.fs;

import iss.model.fs.PostAttachments;

import java.util.List;


/**
* @Title:PostAttachmentsService 
* @Description: 帖子附件实现类接口
* @author Peng Zhongjun
* @date 2016-10-14下午4:36:52
 */
public interface PostAttachmentsService {
	
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
    void insertSelective(PostAttachments record);
    
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
    PostAttachments selectPostAttaByPrimaryKey(String id);
    
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
    int updateByPrimaryKeySelective(PostAttachments record);
    

    
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
    List<PostAttachments> selectAllPostAttachments(String id);
    
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
