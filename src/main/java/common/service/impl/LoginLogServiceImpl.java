package common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.dao.LoginLogMapper;
import common.model.LoginLog;
import common.service.LoginLogService;

/**
 * 
 * @ClassName: LoginLogServiceImpl
 * @Description: 登录日志记录接口实现类
 * @author Easong
 * @date 2017年5月4日 上午10:22:47
 * 
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

	// 注入登录日志Mapper
	@Autowired
	private LoginLogMapper loginLogMapper;

	/**
	 * 
	 * @Title: saveOnlineUser
	 * @Description: 保存登录用户
	 * @author Easong
	 * @param @param loginLog 设定文件
	 * @throws
	 */
	@Override
	public void saveOnlineUser(LoginLog loginLog) {
		// 保存登录信息
		loginLogMapper.insertSelective(loginLog);
	}

	/**
	 * 
	 * @Title: deleteOnlineUser
	 * @Description: 删除登录用户
	 * @author Easong
	 * @param @param userId 设定文件
	 * @throws
	 */
	@Override
	public void deleteOnlineUser(String loginId) {
		loginLogMapper.deleteByUserId(loginId);
	}

}
