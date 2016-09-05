package yggc.service.bms;

import java.util.List;

import yggc.model.bms.Article;

public interface ArticleService {
	/**
	 * 
	* @Title: addArticle
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:06:37  
	* @Description: 新增信息 
	* @param @param article      
	* @return void
	 */
	void addArticle(Article article);
	
	/**
	 * 
	* @Title: selectAllArticle
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:06:55  
	* @Description: 查询所有信息 
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectAllArticle();
	
	/**
	 * 
	* @Title: editArticle
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:07:10  
	* @Description: 修改信息 
	* @param @param article      
	* @return void
	 */
	void editArticle(Article article);
	
	/**
	 * 
	* @Title: delArticleById
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:07:23  
	* @Description: 根据id删除信息 
	* @param @param id      
	* @return void
	 */
	void delArticleById(String id);
	
	/**
	 * 
	* @Title: selectArticleById
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:08:03  
	* @Description: 根据id查询一条信息 
	* @param @param id
	* @param @return      
	* @return Article
	 */
	Article selectArticleById(String id);
}
