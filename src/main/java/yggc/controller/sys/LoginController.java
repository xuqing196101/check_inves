package yggc.controller.sys;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.User;
import yggc.service.UserServiceI;
import yggc.util.Encrypt;

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
	public String login(User user,HttpServletRequest req,Model model) {
		if(!"".equals(user.getPassword()) && user.getLoginName()!=null && !"".equals(user.getPassword()) && user.getPassword()!=null){
			User u = userService.getUserByLogin(user);
			if(u!=null){
				req.getSession().setAttribute("loginUser", u);
				logger.info("登录成功");
				return "redirect:index.do";
			}else{
				logger.error("验证失败");
				return "redirect:/";
			}
		}else{
			logger.error("请输入用户名或密码");
			return "redirect:/";
		}
	}
	
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	@RequestMapping("/home")
	public String home(){
		
		return "backend";
	}
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest req){
		req.getSession().removeAttribute("loginUser");
		return "redirect:/";
	}
	
}
