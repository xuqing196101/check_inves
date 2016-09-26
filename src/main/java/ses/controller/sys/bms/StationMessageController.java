/**
 * 
 */
package ses.controller.sys.bms;

import java.util.List;








import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.service.bms.StationMessageService;

/**
 * @Description: 站内消息
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午4:56:52
 * @since JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/StationMessage")
public class StationMessageController {
	
	@Autowired
	private StationMessageService stationMessageService;
	
	
	/**
	 * @Description:插入站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:14:04  
	 * @param @param stationMessage     
	 * @return void
	 */
	@RequestMapping("/insertStationMessage")
	public void insertStationMessage(HttpServletRequest request,StationMessage stationMessage) {
		User user=(User) request.getSession().getAttribute("loginUser");
		if(user!=null){
			stationMessage.setUserId(user.getId());
		}
		stationMessageService.insertStationMessage(stationMessage);
		
	}

	/**
	 * @Description:修改|插入 站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:15  
	 * @param @param stationMessage      
	 * @return void
	 */
	@RequestMapping("/updateStationMessage")
	public String updateStationMessage(HttpServletRequest request,StationMessage stationMessage) {
		User user=(User) request.getSession().getAttribute("loginUser");
		if(user!=null){
			stationMessage.setUserId(user.getId());
		}
		stationMessageService.updateStationMessage(stationMessage);
		return "redirect:listStationMessage.html";
	}

	/**
	 * @Description:分页获取集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:51  
	 * @param @param stationMessage
	 * @param @return      
	 * @return List<StationMessage>
	 */
	@RequestMapping("/listStationMessage")
	public String listStationMessage(Model model, StationMessage stationMessage,String page) {
		//第几页
		stationMessage.setPageNum(page==null||"".equals(page)?1:Integer.parseInt(page));
		List<StationMessage> listStationMessage = stationMessageService.listStationMessage(stationMessage);
		model.addAttribute("listStationMessage", new PageInfo<StationMessage>(listStationMessage));
		return "ses/bms/station/list";
	}

	/**
	 * @Description:根据id获取单个消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:18:00  
	 * @param @param id      
	 * @return StationMessage
	 */
	@RequestMapping("/showStationMessage")
	public String showStationMessage(Model model,String id,String type) {
		StationMessage showStationMessage = stationMessageService.showStationMessage(id);
		model.addAttribute("StationMessage",showStationMessage);
		if("view".equals(type)){
			return "ses/bms/station/view";
		}else{
			model.addAttribute("operation", 2);
			return "ses/bms/station/edit";
		}
	}

	/**
	 * @Description:发布 or 撤回 消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:20:53  
	 * @param @param id      
	 * @return void
	 */
	@RequestMapping("/updateSMIsIssuance")
	public String updateSMIsIssuance(String id,String isIssuance) {
		
		stationMessageService.updateSMIsIssuance(id,isIssuance);
		
		return "redirect:listStationMessage.html";
	}

	/**
	 * @Description: 软删除一条记录
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:21:18  
	 * @param @param id      
	 * @return void
	 */
	@RequestMapping("/deleteSoftSMIsDelete")
	public String deleteSoftSMIsDelete(String ids) {
		String[] id=ids.split(",");
		for (String str : id) {
			stationMessageService.deleteSoftSMIsDelete(str);
		}
		return "redirect:listStationMessage.html";
	}
	
	/**
	 * @Description:打开添加页面
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月9日 下午2:41:00  
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("/showInsertSM")
	public String showInsertSM(Model model){
		model.addAttribute("operation", 1);
		return "ses/bms/station/edit";
		
	}
	
	
}
