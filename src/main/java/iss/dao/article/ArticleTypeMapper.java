package iss.dao.article;

import iss.model.article.ArticleType;

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
    ArticleType selectByPrimaryKey(String id);
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
    * @Title: updateByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:40:32  
    * @Description: 修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    void updateByPrimaryKey(ArticleType record);
    
}