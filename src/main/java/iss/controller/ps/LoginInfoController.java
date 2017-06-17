package iss.controller.ps;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;

import common.annotation.CurrentUser;
import common.utils.JdcgResult;

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
	public JdcgResult loginInfo(@CurrentUser User user) {
		// 获取登陆用户信息
		if (user != null) {
			return JdcgResult.build(200, user.getLoginName());
		}
		return JdcgResult.build(404, "用户未登陆");
	}
}
