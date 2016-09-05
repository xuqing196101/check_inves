package yggc.dao.bms;

import java.util.List;

import yggc.model.bms.Article;

/**
 * 
 *<p>Title:ArticlesMapper</p>
 *<p>Description:信息Mapper接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-25下午3:38:04
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
    List<Article> selectAllArticle();
    
    /**
    * @Title: update
    * @author szf
    * @date 2016-9-2 上午9:48:53  
    * @Description: TODO 修改信息
    * @param @param record
    * @param @return      
    * @return int
     */
    int update(Article record);
    
    /**
    * @Title: Isdelete
    * @author szf
    * @date 2016-9-2 上午10:50:39  
    * @Description: TODO 假删除信息
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
    
}