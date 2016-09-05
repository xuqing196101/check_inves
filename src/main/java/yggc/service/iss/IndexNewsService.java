package yggc.service.iss;

import java.util.List;

import yggc.model.bms.Article;

/**
 * 
 *<p>Title:IndexNewsService</p>
 *<p>Description:首页信息展示Service接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-29上午9:03:06
 */
public interface IndexNewsService {
	
	/**
	 * 
	* @Title: selectIndexNews
	* @author Mrlovablee 
	* @date 2016-8-29 上午9:14:13  
	* @Description: 根据栏目id查询首页信息 
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsByArticleTypeId(String id);
}
