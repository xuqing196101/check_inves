package iss.service.ps;

import iss.model.ps.Article;
import iss.model.ps.DownloadUser;

import java.util.List;


/*
 *@Title:DownloadUserService
 *@Description:下载人管理service层
 *@author QuJie
 *@date 2016-9-9上午8:57:56
 */
public interface DownloadUserService {
	
	/**
	 * 
	* @Title: addDownloadUser
	* @author QuJie 
	* @date 2016-9-9 上午8:58:23  
	* @Description: 新增下载人信息 
	* @param @param downloadUser      
	* @return void
	 */
	void addDownloadUser(DownloadUser downloadUser);
	
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
    List<DownloadUser> selectByArticleId(String id);
    
    /**
     * 
    * @Title: deleteDownloadUserById
    * @author QuJie 
    * @date 2016-9-13 上午11:21:27  
    * @Description: 根据id删除下载人信息 
    * @param @param id      
    * @return void
     */
    void deleteDownloadUserById(String id);
    
    /**
     * 
    * @Title: selectDownloadUserById
    * @author QuJie 
    * @date 2016-9-13 下午1:34:57  
    * @Description: 根据id查找下载人信息 
    * @param @param id
    * @param @return      
    * @return DownloadUser
     */
    DownloadUser selectDownloadUserById(String id);
    
    /**
     * 
    * @Title: selectDownloadUserByParam
    * @author QuJie 
    * @date 2016-9-13 下午3:49:55  
    * @Description: 根据条件查询
    * @param @param downloadUser
    * @param @return      
    * @return List<DownloadUser>
     */
    List<DownloadUser> selectDownloadUserByParam(DownloadUser downloadUser);
}
