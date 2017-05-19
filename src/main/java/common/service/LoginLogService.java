package common.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ses.model.bms.User;

import common.model.LoginLog;
import common.model.LoginLogVo;

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
	public void saveOnlineUser(User user, HttpServletRequest req);
	
	
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
	
	/**
	 * 
	* @Title: getListByParam 
	* @Description: 登录日志列表
	* @author Easong
	* @param @param loginLog
	* @param @param pageNum
	* @param @return    设定文件 
	* @return List<LoginLog>    返回类型 
	* @throws
	 */
	public List<LoginLog> getListByParam(LoginLogVo loginLog, Integer pageNum);
}
