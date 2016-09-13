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
}
