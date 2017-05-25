package ses.controller.sys.bms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Analyze;
import ses.model.bms.AnalyzeItem;
import ses.model.bms.User;
import ses.service.ems.AnalyzeService;

import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.utils.JdcgResult;

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
	* @Title: list 
	* @Description: 进入统计图页面
	* @author Easong
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/list")
	public String list(Model model){
		String ipAddressType = analyzeService.vertifyIpAddressType();
		model.addAttribute("ipAddressType", ipAddressType);
		return "system/analyze/analyze";
	}
	
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
	
	/**
	 * 
	* @Title: analyzeLoginCount 
	* @Description: 统计
	* @author Easong
	* @param @return    设定文件 
	* @return AnalyzeItem    返回类型 
	* @throws
	 */
	@SystemControllerLog(description="统计")
	@RequestMapping("/analyzeLoginCount")
	@ResponseBody
	public List<Analyze> analyzeLoginCount(@CurrentUser User user,Model model,String analyzeType, String analyzeTypeByCate, Integer analyzeTypeIntegerStart, Integer analyzeTypeIntegerEnd){
		if (user != null) {
			//资源服务信息中心 
			if("4".equals(user.getTypeName())){
				return analyzeService.analyzeLoginCount(analyzeType, analyzeTypeIntegerStart,  analyzeTypeIntegerEnd, analyzeTypeByCate);
			}
		}
		  return new ArrayList<Analyze>();
	}
	
	/**
	 * 
	 * Description:根据选择时间手动统计
	 * 
	 * @author Easong
	 * @version 2017年5月25日
	 * @param date
	 * @return
	 */
	@SystemControllerLog(description="统计")
	@RequestMapping("/handAnalyzeLogin")
	@ResponseBody
	public JdcgResult handAnalyzeLoginByDate(Date date){
		analyzeService.taskAnalyzeLogin(date);
		return JdcgResult.ok();
	}
	
	/**
	 * 
	 * Description:根据选择时间手动统计
	 * 
	 * @author Easong
	 * @version 2017年5月25日
	 * @param date
	 * @return
	 */
	@SystemControllerLog(description="统计")
	@RequestMapping("/handAnalyzeReg")
	@ResponseBody
	public JdcgResult handAnalyzeRegisterByDate(Date date){
		analyzeService.taskAnalyzeRegister(date);
		return JdcgResult.ok();
	}
	
	/**
	 * 
	 * Description:根据选择时间手动统计
	 * 
	 * @author Easong
	 * @version 2017年5月25日
	 * @param date
	 * @return
	 */
	@SystemControllerLog(description="统计")
	@RequestMapping("/handAnalyzeAtt")
	@ResponseBody
	public JdcgResult handAnalyzeAttByDate(Date date){
		analyzeService.taskAnalyzeAttUpload(date);
		return JdcgResult.ok();
	}
}
