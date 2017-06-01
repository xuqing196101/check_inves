package common.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.model.sms.Supplier;
import ses.service.ems.ExpertService;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierService;
import ses.util.PropUtil;
import common.dao.LoginLogMapper;
import common.model.LoginLog;
import common.model.LoginLogVo;
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

	// 注入专家Service
	@Autowired
	private ExpertService expertService;
	
	// 注入供应商Service
	@Autowired
	private SupplierService supplierService;

	/**
	 * 
	 * @Title: saveOnlineUser
	 * @Description: 保存登录用户
	 * @author Easong
	 * @param @param loginLog 设定文件
	 * @throws
	 */
	@Override
	public void saveOnlineUser(User user, HttpServletRequest req) {
		if(user != null){
			Integer typeFlag = null;
			// 查询此用户所属类型 1：专家  2：供应商 3：后台管理员 **/
			Expert expertUser = null;
			Supplier supplierUser = null;
			if(user.getTypeId() != null){
				expertUser = expertService.selectByPrimaryKey(user.getTypeId());
				supplierUser = supplierService.selectById(user.getTypeId());
			}
			LoginLog loginLog = new LoginLog();
			if (expertUser != null) {
				// 专家登录
				typeFlag = 1;
				loginLog.setType(typeFlag);
			} else if (supplierUser != null) {
				// 供应商登录
				typeFlag = 2;
				loginLog.setType(typeFlag);
			} else {
				// 后台登录
				typeFlag = 3;
				loginLog.setType(typeFlag);
			}
			// 设置登录ID
			loginLog.setUserId(user.getId());
			// 设置登录名
			loginLog.setName(user.getLoginName());
			// 设置登录时间
			loginLog.setCreatedAt(new Date());
			// 设置登录ip
			loginLog.setIp(getIpAddress(req));
			// 保存登录信息
			// 保存登录信息
			loginLogMapper.insertSelective(loginLog);
		}
	}

	/**
	 * 
	 * @Title: getIpAddress
	 * @Description: 获取登录真实ip
	 * @author Easong
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getIpAddress(HttpServletRequest request) {
		// 获取代理ip
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")
					|| ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
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

	/**
	 * 
	* @Title: getListByParam 
	* @Description: 登录日志列表
	* @author Easong
	* @param @param loginLog
	* @param @param pageNum
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public List<LoginLog> getListByParam(LoginLogVo loginLog, Integer pageNum) {
		// 分页
		PageHelper.startPage(pageNum,Integer.parseInt(PropUtil.getProperty("pageSize")));
		// 查询
		return loginLogMapper.getListByParam(loginLog);
	}

}
