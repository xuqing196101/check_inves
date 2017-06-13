package iss.service.ps;

import iss.model.ps.Article;

import java.util.List;
import java.util.Map;


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
	List<Article> selectNewsByArticleTypeId(Map<String, Object> map);
	
	/**
	 * 
	 * Description: 查询供应商处罚公告
	 * 
	 * @author zhang shubin
	 * @data 2017年6月13日
	 * @param 
	 * @return
	 */
	List<Article> selectSupplierAllNews(Map<String, Object> map);
	/**
	 * 
	* @Title: selectNews
	* @author QuJie 
	* @date 2016-9-20 下午1:56:03  
	* @Description: 根据栏目类型id查询对应信息 
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNews(String id);
	
	/**
	 * 
	* @Title: selectNewsForJob
	* @author QuJie 
	* @date 2016-9-20 下午1:56:03  
	* @Description: 查询工作动态的首页数据
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsForJob(String id);
	
	/**
	 * 
	* @Title: selectCount
	* @author QuJie 
	* @date 2016-9-20 下午3:10:50  
	* @Description: 二级页信息条数 
	* @param @param id
	* @param @return      
	* @return Integer
	 */
	Integer selectCount(Map<String,Object> countMap);
	
	List<Article> selectAllByName(Map<String,Object> map);

  List<Article> selectIndexChuFaNewsByTypeId(Map<String, Object> map);

  Integer selectChufaCount(Map<String, Object> countMap);
  	
  Integer selectSupplierCount(Map<String,Object> countMap);
}
