package ses.service.iss;

import java.util.List;

import ses.model.bms.Article;


/*
 *@Title:IndexNewsService
 *@Description:首页信息展示Service接口
 *@author QuJie
 *@date 2016-8-29上午9:03:06
 */
public interface IndexNewsService {
	
	/**
	 * 
	* @Title: selectIndexNews
	* @author QuJie 
	* @date 2016-8-29 上午9:14:13  
	* @Description: 根据栏目id查询首页信息 
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsByArticleTypeId(String id);
}
