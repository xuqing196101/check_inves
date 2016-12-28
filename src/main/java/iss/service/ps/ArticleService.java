package iss.service.ps;

import iss.model.ps.Article;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.Packages;



/**
* @Title:ArticleService
* @Description: 信息发布接口
* @author Shen Zhenfei
* @date 2016-9-7下午6:02:30
 */
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
	List<Article> selectAllArticle(Article article,Integer pageNum);
	
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
	
	/**
	* @Title: update
	* @author szf
	* @date 2016-9-2 上午9:05:22  
	* @Description: 修改信息
	* @param @param article      
	* @return void
	 */
	void update(Article article);
	
	/**
	* @Title: delete
	* @author szf
	* @date 2016-9-2 上午10:51:10  
	* @Description: 假删除
	* @param @param id      
	* @return void
	 */
	void delete(String id);
	
	/**
	* @Title: updateisPicShow
	* @author Shen Zhenfei 
	* @date 2016-11-14 上午10:50:35  
	* @Description: 图片位置
	* @param @param isPicShow      
	* @return void
	 */
	void updateisPicShow(String isPicShow);
	
	/**
	* @Title: checkName
	* @author Shen Zhenfei
	* @date 2016-9-7 上午9:35:13  
	* @Description: 验证标题
	* @param @return      
	* @return List<Article>
	 */
	List<Article> checkName(Article article);
	
	
	/**
	* @Title: selectArticleByStatus
	* @author Shen Zhenfei
	* @date 2016-9-7 下午3:55:44  
	* @Description: 根据需求查询信息列表
	* @param @param article
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
	* @date 2016-9-18 下午1:56:57  
	* @Description: 根据标题查询列表
	* @param @param article
	* @param @param pageNum
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectArticleByName(HashMap<String, Object> map);
	
	/**
	 * Description: 根据项目查找公告
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-18
	 * @param article
	 * @return List<Article>
	 * @exception IOException
	 */
	List<Article> selectArticleByProjectId(Article article);
	
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
	 *〈简述〉获取默认模板内容
	 *〈详细描述〉
	 * @author Song Biaowei
	 * @param listPackages 包集合
	 * @return StringBuilder
	 */
	StringBuilder getContent(List<Packages> listPackages);
	
	/**
	* @Title: updateisPicShow
	* @author Shen Zhenfei 
	* @date 2016-12-22 上午9:18:32  
	* @Description: 首页信息发布、撤回
	* @param @param isPicShow      
	* @return void
	 */
	void updateStatus(Article article);
	
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
	
	StringBuilder getContents(List<AdvancedPackages> listPackages);
}
