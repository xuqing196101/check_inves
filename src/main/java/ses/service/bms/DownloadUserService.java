package ses.service.bms;

import ses.model.bms.DownloadUser;

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
}
