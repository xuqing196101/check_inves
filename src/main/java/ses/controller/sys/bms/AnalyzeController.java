package ses.controller.sys.bms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.AnalyzeItem;
import ses.service.ems.AnalyzeService;

/**
 * 
* @ClassName: AnalyzeController 
* @Description: 后台统计控制类
* @author Easong
* @date 2017年5月3日 下午2:55:48 
*
 */
@Controller
@RequestMapping("/analyze")
public class AnalyzeController {
	
	@Autowired
	private AnalyzeService analyzeService;
	
	
	/**
	 * 
	* @Title: getFileCountByEmp 
	* @Description: 文件上传统计
	* @author Easong
	* @param @return    设定文件 
	* @return AnalyzeItem    返回类型 
	* @throws
	 */
	@RequestMapping("/getFileCountByEmp")
	@ResponseBody
	public AnalyzeItem getFileCountByEmp(){
		return analyzeService.getFileCountByEmp();
	}
	
	
	/**
	 * 
	 * @Title: getFileCountByEmp 
	 * @Description: 用户注册上传统计
	 * @author Easong
	 * @param @return    设定文件 
	 * @return AnalyzeItem    返回类型 
	 * @throws
	 */
	@RequestMapping("/getRegisterCountByEmp")
	@ResponseBody
	public AnalyzeItem getRegisterCountByEmp(){
		return analyzeService.getRegisterCountByEmp();
	}
	
	
	/**
	 * 
	 * @Title: getFileCountByEmp 
	 * @Description: 用户登录统计
	 * @author Easong
	 * @param @return    设定文件 
	 * @return AnalyzeItem    返回类型 
	 * @throws
	 */
	@RequestMapping("/getLoginCountByEmp")
	@ResponseBody
	public AnalyzeItem getLoginCountByEmp(){
		return analyzeService.getLoginCountByEmp();
	}
	
}
