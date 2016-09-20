package ses.controller.sys.bms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.formbean.UserTaskFormBean;
import ses.model.bms.UserTask;
import ses.service.bms.UserTaksService;







import com.alibaba.fastjson.JSON;
/**
 * 
 * @Title: UserMaskController 
 * @Description: 用户任务管理 
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:17:25
 *
 */
@Controller
@RequestMapping("/usertask")
public class UserMaskController {
	
	@Autowired
	private UserTaksService userTaksService;
	
	/**
	 * 
	* @Title: getMonth
	* @Description: 查询当前月的数据
	* author: Li Xiaoxiao 
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/getmonth")
	public String getMonth(Model model){
		Map<String,Object> map=new HashMap<String,Object>();
		List<UserTaskFormBean> list = userTaksService.getAl(map);
		String string = JSON.toJSONString(list);
		model.addAttribute("data", string);
		return "ses/bms/user/usertask";
	}
	/**
	 * @throws ParseException 
	 * 
	* @Title: addTask
	* @Description: 新增一个用户任务计划
	* author: Li Xiaoxiao 
	* @param @param userTask
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String addTask(UserTask userTask,String startDate,String endDate) throws ParseException{
		Date date = stingToDate(startDate);
		Date date2 = stingToDate(endDate);
		userTask.setStartDate(date);
		userTask.setEndDate(date2);
		userTask.setStatus("1");
		userTask.setCreatedAt(new Date());
		userTask.setUpdatedAt(new Date());
		userTaksService.add(userTask);
	return null;	
	}
	/**
	 * @throws ParseException 
	* @Title: updateTask
	* @Description: 修改任务
	* author: Li Xiaoxiao 
	* @param @param userTask
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateTask(UserTask userTask,String startDate,String endDate) throws ParseException{
		Date date = stingToDate(startDate);
		Date date2 = stingToDate(endDate);
		userTask.setStartDate(date);
		userTask.setEndDate(date2);
		userTaksService.update(userTask);
		return null;
	}
	 /**
	 * @Title: deleteTask
	 * @Description: 删除一个任务
	 * author: Li Xiaoxiao 
	 * @param @param id
	 * @param @return     
	 * @return String     
	 * @throws
	  */
	@RequestMapping("/delet")
	@ResponseBody
	public String deleteTask(String id){
		userTaksService.delete(id);
		return null;
	}
//	@RequestMapping("/month")
//	@ResponseBody
//	public String month(){
//		Map<String,Object> map=new HashMap<String,Object>();
//		List<UserTaskFormBean> list = userTaksService.getAl(map);
//		String string = JSON.toJSONString(list);
//		return string;
//	}
	
	
	public Date stingToDate(String str) throws ParseException{
		 String[] strs= str.split("\\+");
		 String ss= strs[0]+"+08:00";
		 SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
	     Date d = sf.parse(ss);
	     return d;
	}
	
}
