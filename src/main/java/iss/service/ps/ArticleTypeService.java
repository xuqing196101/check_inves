package iss.service.ps;

import iss.model.ps.ArticleType;

import java.util.List;

/**
* @Title:ArticleTypeService 
* @Description: 栏目管理接口
* @author QuJie
* @date 2016-9-8下午5:21:14
 */
public interface ArticleTypeService {
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
    List<ArticleType> selectAllArticleType(Integer page);
      
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
    * @Title: selectAllArticleTypeForSolr
    * @author QuJie 
    * @date 2016-9-12 上午9:02:56  
    * @Description: 为首页查询所有文章类型 
    * @param @return      
    * @return List<ArticleType>
     */
    List<ArticleType> selectAllArticleTypeForSolr();
}
