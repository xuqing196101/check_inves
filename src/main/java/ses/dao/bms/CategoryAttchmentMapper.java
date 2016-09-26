package ses.dao.bms;

import java.util.List;


import ses.model.bms.CategoryAttchment;
/**
 *@Title:CategoryAttchmentMapper
 *@Description:采购目录附件功能Mapper接口
 *@author Zhang XueFeng
 */
public interface CategoryAttchmentMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author QuJie 
	* @date 2016-8-25 下午3:35:40  
	* @Description: 通过id删除信息 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insert
    * @author QuJie 
    * @date 2016-8-25 下午3:36:18  
    * @Description: 新增一条信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(CategoryAttchment attachment);
    
    /**
     * 
    * @Title: insertSelective
    * @author QuJie 
    * @date 2016-8-25 下午3:36:38  
    * @Description: 根据条件新增一条信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(CategoryAttchment attachment);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:36:57  
    * @Description: 根据id查询信息 
    * @param @param id
    * @param @return      
    * @return ArticleAttachments
     */
    CategoryAttchment selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author QuJie 
    * @date 2016-8-25 下午3:37:21  
    * @Description: 根据条件修改信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(CategoryAttchment attachment);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:37:43  
    * @Description: 修改一条信息
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(CategoryAttchment attachment);
    
    /**
     * 
    * @Title: selectAllArticleAttachments
    * @author QuJie 
    * @date 2016-9-8 上午8:45:03  
    * @Description: 根据articleId查询信息附件 
    * @param @param id
    * @param @return      
    * @return List<ArticleAttachments>
     */
    List<CategoryAttchment> selectAllCategoryAttchment(String id);
}