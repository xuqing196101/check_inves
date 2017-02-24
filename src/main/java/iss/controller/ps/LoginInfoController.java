package iss.controller.ps;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSON;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.utils.JdcgResult;

import ses.model.bms.User;

@Controller
@RequestMapping("/userInfo")
public class LoginInfoController {

	/**
	 * 
	 * @Title: loginInfo
	 * @Description: 登陆信息验证
	 * @author SongDong
	 * @date 2017年2月24日 下午12:37:22
	 * @param @param req
	 * @param @return
	 * @return User
	 * @throws
	 * @version 2017年2月24日
	 */
	@RequestMapping("/loginInfo")
	@ResponseBody
	public JdcgResult loginInfo(HttpServletRequest req) {
		// 获取登陆用户信息
		User existUser = (User) req.getSession().getAttribute("loginUser");
		if (existUser != null) {
			return JdcgResult.build(200, existUser.getLoginName());
		}
		return JdcgResult.build(404, "用户未登陆");
	}
}
