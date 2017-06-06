package iss.dao.ps;

import iss.model.ps.Article;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



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
	* @Description: 根据栏目类型id查询对应信息 (分页)
	* @param @param id
	* @param @return      
	* @return List<Article>
	 */
	List<Article> selectNewsByArticleTypeId(Map<String, Object> map);
	
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
  
}
