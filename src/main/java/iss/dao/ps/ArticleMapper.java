package iss.dao.ps;

import iss.model.ps.Article;

import java.util.List;



/*
* @Title:ArticleMapper
* @Description: 信息发布接口
* @author Shen Zhenfei
* @date 2016-9-7下午6:00:09
 */
public interface ArticleMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Mrlovablee 
	* @date 2016-8-23 下午3:08:27  
	* @Description: 根据id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insert
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:09:51  
    * @Description: 新增信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(Article record);
    
    /**
     * 
    * @Title: insertSelective
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:10:03  
    * @Description: 根据条件新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(Article record);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:10:18  
    * @Description: 根据id查询信息 
    * @param @param id
    * @param @return      
    * @return Article
     */
    Article selectById(String id);
    
    /**
     * 
    * @Title: selectAllArticle
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:10:37  
    * @Description: 查询全部信息 
    * @param @return      
    * @return List<Article>
     */
    List<Article> selectAllArticle(Article record);
    
    /**
    * @Title: update
    * @author Shen Zhenfei
    * @date 2016-9-2 上午9:48:53  
    * @Description: 修改信息
    * @param @param record
    * @param @return      
    * @return int
     */
    int update(Article record);
    
    /**
    * @Title: Isdelete
    * @author Shen Zhenfei
    * @date 2016-9-7 下午6:01:16  
    * @Description: 假删除
    * @param @param id
    * @param @return      
    * @return int
     */
    int Isdelete(String id);
    
    /**
     * 
    * @Title: selectByIsIndex
    * @author Mrlovablee 
    * @date 2016-9-5 下午1:28:25  
    * @Description: 查找未索引的数据 
    * @param @return      
    * @return List<Article>
     */
    List<Article> selectByIsIndex();
    
    /**
    * @Title: checkName
    * @author Shen Zhenfei
    * @date 2016-9-7 上午9:31:01  
    * @Description: 查询标题
    * @param @param article
    * @param @return      
    * @return List<Article>
     */
    List<Article> checkName(Article article);
    
    /**
    * @Title: selectArticleByStatus
    * @author Shen Zhenfei
    * @date 2016-9-7 下午3:52:39  
    * @Description: 根据状态查询信息
    * @param @return      
    * @return List<Article>
     */
    List<Article> selectArticleByStatus(Article article); 
    
    /**
	 * 
	* @Title: selectAllArticleToSolr
	* @author QuJie 
	* @date 2016-9-9 下午3:58:23  
	* @Description: 为solr查询所有信息 
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectAllArticleToSolr();
    
}