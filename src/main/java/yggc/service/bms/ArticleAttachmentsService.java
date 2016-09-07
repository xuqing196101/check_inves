package yggc.service.bms;

import yggc.model.bms.ArticleAttachments;

/**
 * 
 *<p>Title:ArticleAttachmentsService</p>
 *<p>Description:文章信息附件接口service</p>
 *<p>Company:yggc</p>
 * @author QuJie
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
}
