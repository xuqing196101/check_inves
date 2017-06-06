package iss.dao.ps;

import iss.model.ps.ArticleType;

import java.util.List;


/**
* @Title:ArticleTypeMapper 
* @Description: 栏目管理持久化接口
* @author QuJie
* @date 2016-9-8下午4:53:06
 */
public interface ArticleTypeMapper {
	
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:39:56  
    * @Description: 根据id删除信息 
    * @param @param id
    * @param @return      
    * @return ArticleType
     */
    ArticleType selectTypeByPrimaryKey(String id);
    
    /**
     * 
    * @Title: selectAllArticleType
    * @author QuJie 
    * @date 2016-8-26 下午4:49:08  
    * @Description: 查询所有栏目类别 
    * @param @return      
    * @return List<ArticleType>
     */
    List<ArticleType> selectAllArticleType();
    
    /**
     * 
    * @Title: getAll
    * @author QuJie 
    * @date 2016-8-26 下午4:49:08  
    * @Description: 查询所有栏目类别 
    * @param @return      
    * @return List<ArticleType>
     */
    List<ArticleType> getAll();
    
    /**
     * 
    * @Title: selectAllArticleTypeForSolr
    * @author QuJie 
    * @date 2016-9-12 上午8:55:45  
    * @Description: 为solr查询所有文章类型 
    * @param @return      
    * @return List<ArticleType>
     */
    List<ArticleType> selectAllArticleTypeForSolr();
      
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:40:32  
    * @Description: 修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    void updateByPrimaryKey(ArticleType record);
    /**
     * 
    * @Title: selectArticleTypeByCode
    * @author Peng Zhongjun
    * @date 2016-11-7 下午3:47:57  
    * @Description: 根据编码获得栏目类型 
    * @param @param code
    * @param @return      
    * @return ArticleType
     */
    ArticleType selectArticleTypeByCode(String code);
    
    /**
    * @Title: selectByParentId
    * @author Shen Zhenfei 
    * @date 2016-12-26 上午10:32:56  
    * @Description: 根据parentId获取栏目
    * @param @param parentId
    * @param @return      
    * @return ArticleType
     */
    List<ArticleType> selectByParentId(String parentId);
    
    /**
     * @Title: updateShowNum
     * @author Shen Zhenfei 
     * @date 2016-12-26 上午10:32:56  
     * @Description: 根据parentId获取栏目
     * @param @param parentId
     * @param @return      
     * @return ArticleType
      */
     void updateShowNum();
     
     /**
      * @Title: articleTypeList
      * @author Shen Zhenfei 
      * @date 2016-12-26 上午10:32:56  
      * @Description: 根据parentId获取栏目
      * @param @param parentId
      * @param @return      
      * @return ArticleType
       */
     List<ArticleType> selectShowNumByParId(String parentId);
     
}