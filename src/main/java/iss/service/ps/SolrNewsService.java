package iss.service.ps;

import iss.model.ps.Article;

import java.util.Map;



/*
 *@Title:SolrNewsService
 *@Description:solr查询service层
 *@author QuJie
 *@date 2016-9-7下午6:28:37
 */
public interface SolrNewsService {
	/**
	 * 
	* @Title: addIndex
	* @author QuJie
	* @date 2016-5-24 下午2:31:54  
	* @Description: 添加索引 
	* @param @param article      
	* @return void
	 */
	public void addIndex(Article article);
	
	/**
	 * 
	* @Title: deleteIndex
	* @author QuJie
	* @date 2016-5-24 下午3:02:01  
	* @Description: 删除索引
	* @param @param id      
	* @return void
	 */
	public void deleteIndex(String id);
	
	/**
	 * 
	* @Title: updateIndex
	* @author QuJie
	* @date 2016-5-24 下午3:05:02  
	* @Description: 修改索引 
	* @param @param newsEntity      
	* @return void
	 */
	public void updateIndex(Article article);
	
	/**
	 * 
	* @Title: findByIndex
	* @author QuJie
	* @date 2016-5-24 下午3:32:22  
	* @Description: 根据输入查找 
	* @param @param condition
	* @param @return      
	* @return Pager<NewsEntity>
	 */
	public Map<String, Object> findByIndex(String condition,Integer page,Integer pageSize);
	
	/**
	 * 
	* @Title: initIndex
	* @author QuJie
	* @date 2016-5-25 上午10:50:54  
	* @Description: 初始化索引 
	* @param      
	* @return void
	 */
	public void initIndex();
	
	/**
	 * 
	* @Title: deleteAll
	* @author QuJie
	* @date 2016-5-26 上午10:14:48  
	* @Description: 删除所有索引 
	* @param       
	* @return void
	 */
	public void deleteAll();
}
