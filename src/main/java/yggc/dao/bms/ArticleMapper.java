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
    Article selectByPrimaryKey(String id);
    
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
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:10:52  
    * @Description: 通过主键修改信息（不包含大文本类型） 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(Article record);
    
    /**
     * 
    * @Title: updateByPrimaryKeyWithBLOBs
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:11:25  
    * @Description: 通过主键修改信息（包含大文本类型）
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeyWithBLOBs(Article record);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-23 下午3:11:40  
    * @Description: 根据主键修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(Article record);
}