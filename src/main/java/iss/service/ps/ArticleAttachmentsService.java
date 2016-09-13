package iss.service.ps;

import iss.model.ps.ArticleAttachments;

import java.util.List;


/*
 *@Title:ArticleAttachmentsService
 *@Description:文章信息附件接口service
 *@author QuJie
 *@date 2016-9-7上午10:08:06
 */
public interface ArticleAttachmentsService {
	
	/**
	 * 
	* @Title: insert
	* @author QuJie 
	* @date 2016-9-7 上午10:09:06  
	* @Description: 新增附件service接口 
	* @param @param record
	* @param @return      
	* @return int
	 */
	int insert(ArticleAttachments record);
	
	/**
	 * 
	* @Title: selectAllArticleAttachments
	* @author QuJie 
	* @date 2016-9-8 上午8:43:21  
	* @Description:根据articleId查询所有信息附件 
	* @param @return      
	* @return List<ArticleAttachments>
	 */
	List<ArticleAttachments> selectAllArticleAttachments(String id);
	
	/**
	 * 
	* @Title: selectArticleAttaById
	* @author QuJie 
	* @date 2016-9-8 上午9:16:24  
	* @Description: 根据id查询信息附件 
	* @param @param id
	* @param @return      
	* @return ArticleAttachments
	 */
	ArticleAttachments selectArticleAttaById(String id);
	
	/**
	 * 
	* @Title: softDeleteAtta
	* @author QuJie 
	* @date 2016-9-9 下午1:25:34  
	* @Description: 假删除 
	* @param @param id      
	* @return void
	 */
	void softDeleteAtta(String id);
}
