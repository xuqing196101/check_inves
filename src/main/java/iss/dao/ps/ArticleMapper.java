package iss.dao.ps;

import iss.model.ps.Article;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



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
    * @Title: updateisPicShow
    * @author Shen Zhenfei 
    * @date 2016-11-14 上午10:44:24  
    * @Description: TODO 
    * @param @param isPicShow
    * @param @return      
    * @return int
     */
    int updateisPicShow(String isPicShow);
    
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
    List<Article> selectArticleByStatus(HashMap<String, Object> map); 
    
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
	
	/**
	* @Title: selectArticleByName
	* @author Shen Zhenfei 
	* @date 2016-9-18 下午1:55:48  
	* @Description: 根据标题查询列表
	* @param @param article
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectArticleByName(HashMap<String, Object> map);
	
	/**
	* @Title: selectPic
	* @author Shen Zhenfei 
	* @date 2016-9-18 下午1:55:48  
	* @Description: 查询首页图片
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectPic();
  
	/**
	 * Description: 根据项目查找
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-18
	 * @param article
	 * @return List<Article>
	 * @exception IOException
	 */
	List<Article> selectArticleByProjectId(Article article);
	
	/**
	* @Title: updateStatus
	* @author Shen Zhenfei 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 撤回、发布
	* @param @param article
	* @param @return      
	* @return int
	 */
	int updateStatus(Article article);
	
	/**
	* @Title: selectArticleByParId
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectArticleByParId(Map<String, Object> map);
	
	/**
	* @Title: selectArticleByParIdTwo
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectArticleByParIdTwo(Map<String, Object> map);
	
	/**
	* @Title: selectsumByParId
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectsumByParId(Map<String, Object> map);
	
	/**
	* @Title: selectArticleByArticleType
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectArticleByArticleType(Map<String, Object> map);
	
	/**
	* @Title: selectsumBynews
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectsumBynews(Map<String, Object> map);
	
	/**
	* @Title: selectsumBydanNews
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectsumBydanNews(Map<String, Object> map);
	
	/**
	* @Title: selectsumBydanNews
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectJob(Map<String, Object> map);
	
	/**
	* @Title: selectPics
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 根据父节点查找
	* @param @param article
	* @param @return      
	* @return int
	 */
	List<Article> selectPics();
	
	
	/**
	* @Title: selectByTimer
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 每天晚上11点-第二天8点
	* @param @param article
	* @param @return      
	* @return int
	 */
	BigDecimal selectByTimer(Map<String, Object> map);
	
	/**
	* @Title: selectByTimer
	* @author Qu Jie 
	* @date 2016-12-22 上午9:21:24  
	* @Description: 每天晚上11点-第二天8点
	* @param @param article
	* @param @return      
	* @return int
	 */
	BigDecimal selectByTimerByType(Map<String, Object> map);
	
	List<Article> selectAllByParId(Map<String, Object> map);
	
	List<Article> selectAllByArticleType(Map<String, Object> map);
	
	/**
	 * 
	 *〈简述〉根据标题查询
	 *〈详细描述〉
	 * @author myc
	 * @param name 文章标题
	 * @return
	 */
    List<Article> selectListByTitle(@Param("name")String name);
}