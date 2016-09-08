package iss.dao.article;

import java.util.List;
import java.util.Map;

import ses.model.bms.Article;


/*
 *@Title:IndexNewsMapper
 *@Description:首页信息操作Mapper
 *@author QuJie
 *@date 2016-8-26下午4:28:37
 */
public interface IndexNewsMapper {
	/**
	 * 
	* @Title: selectIndexNews
	* @author QuJie 
	* @date 2016-8-26 下午4:29:43  
	* @Description: 查询所有首页信息 
	* @param @return      
	* @return Map<String,Object>
	 */
	List<Article> selectIndexNews();
	
	/**
	 * 
	* @Title: selectNewsByArticleTypeId
	* @author QuJie 
	* @date 2016-8-29 上午8:52:43  
	* @Description: 根据栏目类型id查询对应信息 
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsByArticleTypeId(String id);
}
