package iss.dao.ps;

import iss.model.ps.DownloadUser;

import java.util.List;
import java.util.Map;


/*
 *@Title:DownloadUserMapper
 *@Description:下载人信息Mapper接口
 *@author QuJie
 *@date 2016-8-25下午3:40:49
 */
public interface DownloadUserMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author QuJie 
	* @date 2016-8-25 下午3:41:09  
	* @Description: 根据id删除下载人信息
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(String id);
    
    /**
     * 
    * @Title: insert
    * @author QuJie 
    * @date 2016-8-25 下午3:41:27  
    * @Description: 新增一条下载人信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insert(DownloadUser record);
    
    /**
     * 
    * @Title: insertSelective
    * @author QuJie 
    * @date 2016-8-25 下午3:41:54  
    * @Description: 根据条件新增下载人信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int insertSelective(DownloadUser record);
    
    /**
     * 
    * @Title: selectByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:42:24  
    * @Description: 根据id查询下载人信息 
    * @param @param id
    * @param @return      
    * @return DownloadUser
     */
    DownloadUser selectDownloadByPrimaryKey(String id);
    
    /**
     * 
    * @Title: selectByArticleId
    * @author QuJie 
    * @date 2016-9-12 下午1:17:18  
    * @Description: 根据文章id查询下载人信息 
    * @param @param id
    * @param @return      
    * @return List<DownloadUser>
     */
    List<DownloadUser> selectByArticleId(Map<String,Object> map);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author QuJie 
    * @date 2016-8-25 下午3:42:45  
    * @Description: 根据条件修改下载人信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKeySelective(DownloadUser record);
    
    /**
     * 
    * @Title: updateByPrimaryKey
    * @author QuJie 
    * @date 2016-8-25 下午3:43:11  
    * @Description: 根据主键修改下载人信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(DownloadUser record);
    
    /**
     * 
    * @Title: selectDownloadUserByParam
    * @author QuJie 
    * @date 2016-9-13 下午3:36:46  
    * @Description:根据条件查询下载人 
    * @param @return      
    * @return List<DownloadUser>
     */
    List<DownloadUser> selectDownloadUserByParam(DownloadUser downloadUser);
    
    /**
     * 
    * @Title: selectDownloadUserCount
    * @author QuJie 
    * @date 2016-9-18 下午1:44:12  
    * @Description: 根据articleId查询下载人总数 
    * @param @param articleId
    * @param @return      
    * @return Integer
     */
    Integer selectDownloadUserCount(Map<String,Object> countMap);
}