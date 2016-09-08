package ses.dao.bms;

import ses.model.bms.DownloadUser;

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
    DownloadUser selectByPrimaryKey(String id);
    
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
}