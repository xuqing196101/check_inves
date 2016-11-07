package ses.controller.sys.bms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;





import ses.model.bms.StationMessage;
import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.ems.Expert;
import ses.service.bms.StationMessageService;
import ses.service.bms.TodosService;
import ses.service.bms.UserServiceI;
import ses.service.ems.ExpertService;
import ses.service.sms.ImportSupplierService;


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
    /**
     * 代办事项
     */
    @Autowired
    private TodosService todosService;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private ImportSupplierService importSupplierService;

    @Autowired
    private ExpertService expertService;//专家
    /**
     * 站内消息
     */
    @Autowired
    private StationMessageService stationMessageService;

    private static Logger logger = Logger.getLogger(LoginController.class); 

    /**
     * Description: 用户登录
     * 
     * @author Ye MaoLin
     * @version 2016-9-18
     * @param user
     * @param req
     * @param response
     * @param model
     * @param rqcode
     * @throws IOException
     * @exception IOException
     */
	@RequestMapping("/login")
	public void login(User user, HttpServletRequest req, HttpServletResponse response, Model model, String rqcode) throws IOException {
		PrintWriter out = response.getWriter();
		if (user.getLoginName() != null && !"".equals(user.getPassword().trim()) && user.getPassword() != null && !"".equals(user.getPassword().trim()) && rqcode != null && !"".equals(rqcode.trim())) {
			// 根据用户名查找
			List<User> list = userService.findByLoginName(user.getLoginName());
			// 获取当前登录用户名的随机码
			String randomCode = "";
			if (list.size() > 0) {
				randomCode = list.get(0).getRandomCode();
			}
			// 根据随机码+密码加密
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			// false 表示：生成32位的Hex版, 这也是encodeHashAsBase64的, Acegi 默认配置; true 表示：生成24位的Base64版
			md5.setEncodeHashAsBase64(false);
			String pwd = md5.encodePassword(user.getPassword(), randomCode);
			user.setPassword(pwd);
			// 根据用户名、密码验证用户登录
			List<User> ulist = userService.queryByLogin(user);
			User u = null;
			if (ulist.size() > 0) {
				u = ulist.get(0);
			}

			// 获取验证码
			String code = (String) req.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (!rqcode.toUpperCase().equals(code)) {
				logger.info("验证码输入有误");
				out.print("errorcode");
			} else if (u != null) {
				if (u.getTypeName() == 5) {
					try {
						Map<String, Object> map = expertService.loginRedirect(u);
						Object object = map.get("expert");
						if (object != null) {
							// 拉黑 阻止登录
							if (object.equals("1")) {
								out.print("black");
							} else if (object.equals("2")) {
								// 信息为空 重新填写
								out.print("empty," + u.getId());
							} else if (object.equals("3")) {
								// 未审核
								out.print("audit");
							} else if (object instanceof Expert) {
								// 暂存 或者 退回
								// Expert expert = (Expert)object;
								out.print("reset," + u.getId());
							}
						} else {
							req.getSession().setAttribute("loginUser", u);
							req.getSession().setAttribute("resource", u.getMenus());
							out.print("scuesslogin");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					req.getSession().setAttribute("loginUser", u);
					req.getSession().setAttribute("resource", u.getMenus());
					out.print("scuesslogin");
				}

			} else {
				logger.error("验证失败");
				out.print("errorlogin");
			}
		} else {
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
    public String index(HttpServletRequest req,String type,String page,String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null && user.getOrg() != null && user.getOrg().getId() != null && !"".equals(user.getOrg().getId())){
            //站内
            List<StationMessage> listStationMessage = stationMessageService.listStationMessage(new StationMessage(new Short("0"),user.getId(), user.getOrg().getId()), 0);
            req.setAttribute("stationMessage", listStationMessage);
        }
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
    public String home(HttpServletRequest req,Model model,String type,String page,String id){
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user != null && user.getOrg() != null && user.getOrg().getId() != null && !"".equals(user.getOrg().getId())){
            //站内
            List<StationMessage> listStationMessage = stationMessageService.listStationMessage(new StationMessage(new Short("0"),user.getId(), user.getOrg().getId()), 0);
            req.setAttribute("stationMessage", listStationMessage);
        }
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
