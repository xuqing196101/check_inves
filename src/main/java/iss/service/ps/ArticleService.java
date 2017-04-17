
package iss.service.ps;

import iss.model.ps.Article;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.ui.Model;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
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
	
	Article selectReleaseById(String id);
	
	
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
	
	//通过产品目录查找文件
	List<Article> findArtByCategory(Map<String, Object> map);
	
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
	
	BigDecimal selectDanByTimer(Map<String, Object> map);
	

	/**
	 * 
	 *〈简述〉根据文章标题进行查询
	 *〈详细描述〉
	 * @author myc
	 * @param title
	 * @return Article 集合
	 */
    List<Article>  selectListByTitle(String title, Integer page);
    
    Map<String, Object> topNews();
    
    List<Article> selectAllByTab(Map<String, Object> map);
    
    List<Article> selectAllByTabs(Map<String, Object> map);
    
    List<Article> selectAllByDanTab(Map<String, Object> map);
    
    List<Article> selectAllByDanTabs(Map<String, Object> map);
    
    
    /**
     * 
     *〈简述〉根据发布时间查询
     *〈详细描述〉
     * @author myc
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    List<Article> getListBypublishedTime(String startTime, String endTime);
    
    /**
     * 
     *〈简述〉获取数量
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return
     */
    Integer getArticleCount(String id);
    
    List<Article> selectByJurisDiction(Map<String,Object> map);
    
    List<Article> selectAllByTabss(Map<String, Object> map);
    
    BigDecimal selectAllByTimer(Map<String, Object> map);
    
    /**
     * 
     *〈简述〉更新article - 数据同步使用
     *〈详细描述〉
     * @author myc
     * @param article
     */
    void updateArticle(Article article);
    
    /**
     * 
     *〈简述〉插入数据-数据同步使用
     *〈详细描述〉
     * @author myc
     * @param article
     */
    void insertArticle(Article article);
    
    /**
     * 
     *〈简述〉获取取消的信息 - 数据同步使用
     *〈详细描述〉
     * @author myc
     * @param startTime 
     * @param endTime
     * @return
     */
    List<Article> getCancelNews(String startTime, String endTime);

    /**
     * 
     *〈简述〉根据栏目id获取信息
     *〈详细描述〉
     * @author Ye Maolin
     * @param map
     * @return
     */
    List<Article> selectAllByArticleTypeId(Map<String, Object> map);

    /**
     * 
     *〈简述〉获取一个月内信息数量
     *〈详细描述〉
     * @author Ye Maolin
     * @param timerMap
     * @return
     */
    BigDecimal selectByTypeIdTimer(HashMap<String, String> timerMap);

    /**
     *〈简述〉产品目录
     *〈详细描述〉
     * @author Ye MaoLin
     * @param parentId
     * @return
     */
    List<Category> getCategoryIsPublish(String parentId);

    /**
     *〈简述〉是否选中
     *〈详细描述〉
     * @author Ye MaoLin
     * @param articleId
     * @param id
     * @return
     */
    boolean isCheckCategory(String articleId, String id);

    /**
     *〈简述〉保存信息公告与产品目录关联
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @param categoryIds
     */
    void saveArtCategory(String id, String categoryIds);

    /**
     *〈简述〉查询关联品目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @param model 
     */
    void getArticleCategory(String id, Model model);

    /**
     *〈简述〉回显关联品目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param id
     * @param model
     */
    void backArtCategory(String id, Model model);

    /**
     *〈简述〉获取所有父节点
     *〈详细描述〉
     * @author Ye MaoLin
     * @param categoryId
     * @return
     */
    List<Category> getAllParent(String categoryId);

    /**
     *〈简述〉实时保存关联品目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param categoryNames 
     * @param articleId
     * @param articleId2 
     * @param jsonObj 
     * @param list
     */
    JSONObject saveArtCategory2(String categoryIds, String categoryNames, String articleId, List<Category> list);

    /**
     *〈简述〉取消关联品目
     *〈详细描述〉
     * @author Ye MaoLin
     * @param categoryId 
     * @param articleId 
     */
    JSONObject cancelArtCategory(String categoryIds, String categoryNames, String articleId, String categoryId);
    
    
    /**
  	 * 
  	* @Title: selectAllArticleByCondition 
  	* @Description: 条件查询所有文章
  	* @author Easong
  	* @param @param map
  	* @param @return    设定文件 
  	* @return List<Article>    返回类型 
  	* @throws
  	 */
  	List<Article> selectAllArticleByCondition(Map<String, Object> map);

    void searchCategory(List<CategoryTree> jList, String name, String rootCode);
}
