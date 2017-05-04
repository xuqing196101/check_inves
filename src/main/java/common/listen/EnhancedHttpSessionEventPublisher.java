package common.listen;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.ems.ExpertService;
import ses.service.sms.impl.SupplierAuditServiceImpl;

import common.model.LoginLog;
import common.service.LoginLogService;

/**
 * 
 * @ClassName: EnhancedHttpSessionEventPublisher
 * @Description: 扩展HttpSessionListener 在线人数统计
 * @author Easong
 * @date 2017年5月4日 上午10:01:05
 * 
 */
public class EnhancedHttpSessionEventPublisher extends
		HttpSessionEventPublisher {

	/**
	 * 
	 * @Title: sessionCreated
	 * @Description: 重写登录创建session方法
	 * @author Easong
	 * @param @param event 设定文件
	 * @throws
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// 调用此方法加入用户到在线用户列表中
		saveOrDeleteOnlineUser(event, Type.SAVE);
		super.sessionCreated(event);
	}

	/**
	 * 
	 * @Title: sessionDestroyed
	 * @Description: 重写销毁session方法
	 * @author Easong
	 * @param @param event 设定文件
	 * @throws
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// 调用此方法删除用户
		saveOrDeleteOnlineUser(event, Type.DELETE);
		super.sessionDestroyed(event);
	}

	/**
	 * 
	 * @Title: saveOrDeleteOnlineUser
	 * @Description: 保存或删除用户
	 * @author Easong
	 * @param @param event
	 * @param @param type 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void saveOrDeleteOnlineUser(HttpSessionEvent event, Type type) {
		// 获取登录信息
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			// 获取登录人
			Object principal = auth.getPrincipal();
			if (principal instanceof User) {
				User user = (User) principal;
				// 获取Spring容器
				WebApplicationContext wac = WebApplicationContextUtils
						.getRequiredWebApplicationContext(event.getSession()
								.getServletContext());

				// 创建登录日志Service对象
				LoginLogService loginUserService = (LoginLogService) wac
						.getBean("loginLogService");
				// 创建专家Service对象
				ExpertService expertService = (ExpertService) wac
						.getBean("expertService");
				// 创建供应商Service对象
				SupplierAuditServiceImpl supplierService = (SupplierAuditServiceImpl) wac
						.getBean("supplierAuditServiceImpl");
				Integer typeFlag = null;
				switch (type) {
				case SAVE:
					// 查询此用户所属类型 /** 1：后台 2：供应商 3：专家 **/
					User expertUser = expertService.getUserById(user
							.getTypeId());
					Supplier supplierUser = supplierService.supplierById(user
							.getTypeId());
					LoginLog loginLog = new LoginLog();
					if (expertUser != null) {
						// 专家登录
						typeFlag = 3;
						loginLog.setType(typeFlag);
					} else if (supplierUser != null) {
						// 供应商登录
						typeFlag = 2;
						loginLog.setType(typeFlag);
					} else {
						// 后台登录
						typeFlag = 1;
						loginLog.setType(typeFlag);
					}
					// 设置登录ID
					loginLog.setLoginId(user.getId());
					// 设置登录名
					loginLog.setName(user.getLoginName());
					// 设置登录时间
					loginLog.setLoginAt(new Date());
					// 保存登录信息
					loginUserService.saveOnlineUser(loginLog);
					break;
				case DELETE:
					loginUserService.deleteOnlineUser(user.getId());
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @ClassName: Type
	 * @Description: 定义内部枚举
	 * @author Easong
	 * @date 2017年5月4日 上午10:27:38
	 * 
	 */
	private static enum Type {
		SAVE, DELETE;
	}
}
