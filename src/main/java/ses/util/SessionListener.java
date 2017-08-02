package ses.util;

import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import ses.model.bms.User;

/**
 * 
 * Description: session监听
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@SuppressWarnings("all")
public class SessionListener implements HttpSessionListener {
	public static HashMap<String, Object> sessionMap = new HashMap<>();
	public void sessionCreated(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
	}
	public void sessionDestroyed(HttpSessionEvent hse) {
		HttpSession session = hse.getSession();
		this.DelSession(session);
	}
	public static synchronized void DelSession(HttpSession session) {
		if (session != null) {
			// 删除单一登录中记录的变量
			if (session.getAttribute("users") != null) {
				User u = (User) session.getAttribute("loginUser");
				SessionListener.sessionMap.remove(u.getId());
			}
		}
	}
}
