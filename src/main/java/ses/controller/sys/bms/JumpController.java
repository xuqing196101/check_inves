package ses.controller.sys.bms;

import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeItem;
import ses.model.bms.User;
import ses.service.ems.AnalyzeService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: AnalyzeController 
* @Description: 后台统计控制类
* @author Easong
* @date 2017年5月3日 下午2:55:48 
*
 */
@Controller
@RequestMapping("/jumppage")
public class JumpController {
	
	/**
	 * 
	* @Description: 进入页面跳转
	 */
	@RequestMapping("/jump")
	public String to(HttpServletRequest request,Model model){
	String returnUrl = request.getParameter("returnUrl");
		model.addAttribute("returnUrl",returnUrl);
		return "jumpPage";
	}

}
