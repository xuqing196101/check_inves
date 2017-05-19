package system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import common.bean.ResponseBean;
import common.model.LoginLog;
import common.model.LoginLogVo;
import common.service.LoginLogService;

/**
 * 
 * @ClassName: LoginLogController
 * @Description:登录之日可控制类
 * @author Easong
 * @date 2017年5月19日 下午12:57:55
 * 
 */
@Controller
@RequestMapping("/loginlog")
public class LoginLogController {

	// 注入登录日志Service
	@Autowired
	private LoginLogService loginLogService;

	/**
	 * 
	 * @Title: init
	 * @Description:
	 * @author Easong 登录日志列表页显示
	 * @param @param model
	 * @param @param request
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/init")
	public String init(Model model, HttpServletRequest request) {
		String type = request.getParameter("type");
		model.addAttribute("type", type);
		return "/system/loginlog/list";
	}

	/**
	 * 
	 * @Title: list
	 * @Description: 根据参数查询
	 * @author Easong
	 * @param @param request
	 * @param @param page
	 * @param @return 设定文件
	 * @return ResponseBean 返回类型
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/list")
	public ResponseBean list(LoginLogVo loginLog,@RequestParam(defaultValue = "1") Integer page) {
		ResponseBean res = new ResponseBean();
		List<LoginLog> list = loginLogService.getListByParam(loginLog, page);
		PageInfo<LoginLog> pageInfo = new PageInfo<LoginLog>(list);
		res.setSuccess(true);
		res.setObj(pageInfo);
		return res;
	}
}
