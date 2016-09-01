package yggc.dao.bms;

import yggc.model.bms.DownloadUser;

/**
 * 
 *<p>Title:DownloadUserMapper</p>
 *<p>Description:下载人信息Mapper接口</p>
 *<p>Company:yggc</p>
 * @author Mrlovablee
 *@date 2016-8-25下午3:40:49
 */
public interface DownloadUserMapper {
	/**
	 * 
	* @Title: deleteByPrimaryKey
	* @author Mrlovablee 
	* @date 2016-8-25 下午3:41:09  
	* @Description: 根据id删除下载人信息
	* @param @param id
	* @param @return      
	* @return int
	 */
    int deleteByPrimaryKey(Integer id);
    
    /**
     * 
    * @Title: insert
    * @author Mrlovablee 
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
    * @author Mrlovablee 
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
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:42:24  
    * @Description: 根据id查询下载人信息 
    * @param @param id
    * @param @return      
    * @return DownloadUser
     */
    DownloadUser selectByPrimaryKey(Integer id);
    
    /**
     * 
    * @Title: updateByPrimaryKeySelective
    * @author Mrlovablee 
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
    * @author Mrlovablee 
    * @date 2016-8-25 下午3:43:11  
    * @Description: 根据主键修改下载人信息 
    * @param @param record
    * @param @return      
    * @return int
     */
    int updateByPrimaryKey(DownloadUser record);
}