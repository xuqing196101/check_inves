package ses.controller.sys.bms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.sms.SupplierAgents;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierAgentsService;


/**
* <p>Title:LoginController </p>
* <p>Description: 用户登录</p>
* <p>Company: ses </p> 
* @author yyyml
* @date 2016-7-15下午2:52:15
*/
@Controller
@Scope("prototype")
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private SupplierAgentsService agentsService;
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
	 * @throws IOException 
	*/
	@RequestMapping("/login")
	public void login(User user,HttpServletRequest req,	HttpServletResponse response,Model model,String rqcode) throws IOException {
		PrintWriter out =response.getWriter();
		if(user.getLoginName()!=null && !"".equals(user.getPassword().trim()) && user.getPassword()!=null && !"".equals(user.getPassword().trim()) && rqcode!=null && !"".equals(rqcode.trim())){
			User u = userService.getUserByLogin(user);
			//获取验证码
			String code = (String) req.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(!rqcode.toUpperCase().equals(code)){
				logger.info("验证码输入有误");
				out.print("errorcode");
			}else if(u!=null){
				req.getSession().setAttribute("loginUser", u);
				logger.info("登录成功");
				out.print("scuesslogin");
			}else{
				logger.error("验证失败");
				out.print("errorlogin");
			}
		}else{
			logger.error("请输入用户名密码或者验证码");
			out.print("nullcontext");
		}
	}
	
	/**   
	* @Title: index
	* @author yyyml
	* @date 2016-8-30 下午3:01:15  
	* @Description: 跳转后台首页
	* @return String     
	*/
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	/**   
	* @Title: home
	* @author yyyml
	* @date 2016-8-30 下午3:04:14  
	* @Description: 后台默认内容
	* @return String     
	*/
	@RequestMapping("/home")
	public String home(HttpServletRequest req){
		//代办事项
		List<SupplierAgents> getListSupplier=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("0")));
		req.setAttribute("SupplierAgent", getListSupplier);
		//催办事项
		List<SupplierAgents> getListSupplierReminders=agentsService.getAllSupplierAgent(new SupplierAgents(new Short("1")));
		req.setAttribute("SupplierReminders", getListSupplierReminders);
		return "backend";
	}
	
	/**   
	* @Title: loginOut
	* @author yyyml
	* @date 2016-8-30 下午3:04:33  
	* @Description: 退出登录 
	* @param @param req
	* @return String     
	*/
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest req){
		req.getSession().removeAttribute("loginUser");
		return "redirect:/";
	}
	
}
