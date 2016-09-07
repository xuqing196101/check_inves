package yggc.dao.bms;

import yggc.model.bms.ArticleFile;

/*
 *@Title:ArticleFileMapper
 *@Description:信息附件mapper类
 *@author QuJie
 *@date 2016-9-7下午5:55:30
 */
public interface ArticleFileMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author QuJie 
	* @date 2016-9-7 下午5:56:02  
	* @Description: 根据id删除信息附件 
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insert
    * @author QuJie 
    * @date 2016-9-7 下午5:56:25  
    * @Description: 新增信息附件 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(ArticleFile record);
    
    /**
     * 
    * @Title: insertSelective
    * @author QuJie 
    * @date 2016-9-7 下午5:56:43  
    * @Description: 根据条件新增 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(ArticleFile record);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author QuJie 
    * @date 2016-9-7 下午5:57:08  
    * @Description: 根据id查询信息附件 
    * @param @param id
    * @param @return      
    * @return ArticleFile
     */
    ArticleFile selectByPrimaryKey(String id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author QuJie 
    * @date 2016-9-7 下午5:57:28  
    * @Description: 根据id修改信息附件（根据条件） 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(ArticleFile record);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author QuJie 
    * @date 2016-9-7 下午5:58:10  
    * @Description: 根据id修改信息附件 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(ArticleFile record);
}