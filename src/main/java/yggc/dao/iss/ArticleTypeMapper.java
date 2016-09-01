package yggc.dao.iss;

import java.util.List;

import yggc.model.iss.ArticleType;

/**
 * 
 *<p>Title:ArticleTypeMapper</p>
 *<p>Description:信息类型Mapper接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-25下午3:38:37
 */
public interface ArticleTypeMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:39:00  
	* @Description: 根据id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(Long id);
    
    /**
     * 
    * @Title: insert
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:39:21  
    * @Description: 新增信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(ArticleType record);
    
    /**
     * 
    * @Title: insertSelective
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:39:37  
    * @Description: 根据条件新增信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(ArticleType record);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:39:56  
    * @Description: 根据id删除信息 
    * @param @param id
    * @param @return      
    * @return ArticleType
     */
    ArticleType selectByPrimaryKey(Long id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:40:15  
    * @Description: 根据条件修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ArticleType record);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:40:32  
    * @Description: 修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(ArticleType record);
    
    /**
     * 
    * @Title: selectAllArticleType
    * @author Mrlovablee 
    * @date 2016-8-26 下午4:49:08  
    * @Description: 查询所有栏目类别 
    * @param @return      
    * @return List<ArticleType>
     */
    List<ArticleType> selectAllArticleType();
}