package yggc.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.User;
import yggc.service.UserServiceI;

/**
* <p>Title:LoginController </p>
* <p>Description: 用户登录</p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-15下午2:52:15
*/
@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserServiceI userService;
	
	private static Logger logger = Logger.getLogger(LoginController.class); 
	/**   
	* @Title: login
	* @author yyyml
	* @date 2016-7-15 下午2:52:38  
	* @Description: 用户登录 
	* @param @param user
	* @param @param req
	* @return String     
	*/
	@RequestMapping("/login")
	public String login(User user,HttpServletRequest req) {
		User u = userService.getUserByLogin(user);
		if(u!=null){
			req.getSession().setAttribute("loginUser", u);
			logger.info("登录成功");
		}else{
			logger.error("验证失败");
			return "error";
		}
		return "backend";
	}
	
}
