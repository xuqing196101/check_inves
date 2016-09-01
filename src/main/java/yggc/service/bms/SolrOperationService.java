package yggc.service.bms;

import java.util.Map;

import yggc.model.bms.Article;

public interface SolrOperationService {
	/**
	 * 
	* @Title: addIndex
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:51:53  
	* @Description: 添加索引 
	* @param @param article      
	* @return void
	 */
	public void addIndex(Article article);
	
	/**
	 * 
	* @Title: deleteIndex
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:52:10  
	* @Description: 删除索引 
	* @param @param id      
	* @return void
	 */
	public void deleteIndex(Integer id);
	
	/**
	 * 
	* @Title: updateIndex
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:52:48  
	* @Description: 修改索引 
	* @param @param article      
	* @return void
	 */
	public void updateIndex(Article article);
	
	/**
	 * 
	* @Title: findByIndex
	* @author MRlovablee
	* @date 2016-5-24 下午3:32:22  
	* @Description: 根据输入查找 
	* @param @param condition
	* @param @return      
	* @return Pager<NewsEntity>
	 */
	public Map<String, Object> findByIndex(String condition);
	
	/**
	 * 
	* @Title: initIndex
	* @author MRlovablee
	* @date 2016-5-25 上午10:50:54  
	* @Description: 初始化索引 
	* @param      
	* @return void
	 */
	public void initIndex();
	
	/**
	 * 
	* @Title: deleteAll
	* @author MRlovablee
	* @date 2016-5-26 上午10:14:48  
	* @Description: 删除所有索引 
	* @param       
	* @return void
	 */
	public void deleteAll();
}
