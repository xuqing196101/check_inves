package common.service;

import common.model.LoginLog;

/**
 * 
* @ClassName: LoginLogService 
* @Description: 登录日志记录接口
* @author Easong
* @date 2017年5月4日 上午10:21:47 
*
 */
public interface LoginLogService {

	/**
	 * 
	* @Title: saveOnlineUser 
	* @Description: 保存登录用户
	* @author Easong
	* @param @param loginLog    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveOnlineUser(LoginLog loginLog);
	
	
	/**
	 * 
	* @Title: deleteOnlineUser 
	* @Description: 删除登录用户
	* @author Easong
	* @param @param id    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void deleteOnlineUser(String id);
}
