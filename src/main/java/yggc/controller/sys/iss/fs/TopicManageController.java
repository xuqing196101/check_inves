package yggc.controller.sys.iss.fs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import yggc.model.iss.fs.Topic;
import yggc.service.iss.fs.TopicService;

/**
 * <p>Title:TopicManageController </p>
 * <p>Description:主题管理控制类  </p>
 * <p>Company: yggc </p> 
 * @author Peng Zhongjun
 * @date 2016-8-10下午5:03:01
 */
@Controller
@Scope("prototype")
@RequestMapping("/topic")
public class TopicManageController {
	
	@Autowired
	private TopicService topicService;
	
	/**   
	* @Title: getList
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取主题列表 
	* @param @param model
	* @param @param  topic   
	* @return String     
	*/
	@RequestMapping("/getlist")
	public String getList(Model model,Topic topic){
		List<Topic> list = topicService.queryByList(topic);
		model.addAttribute("list", list);
		return "iss/forum/topic/list";
	}
	
	/**   
	* @Title: view
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:55:32  
	* @Description: 显示主题详细信息页面
	* @param @param model
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/view")
	public String view(Model model,String id){
		Topic p = topicService.selectByPrimaryKey(id);
		model.addAttribute("topic", p);
		return "iss/forum/topic/view";
	}
	
	/**   
	* @Title: add
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:58:43   
	* @Description: 跳转新增编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		return "iss/forum/topic/add";
	}
	
	/**   
	* @Title: save
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41   
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param Topic
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Topic topic){
		topicService.insertSelective(topic);
		return "redirect:getlist.do";
	}
	
	
	/**   
	* @Title: edit
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41  
	* @Description: 跳转修改编辑页面
	* @param @param id
	* @param @param model
	* @return String     
	*/
	@RequestMapping("/edit")
	public String edit(String id,Model model){
		Topic p = topicService.selectByPrimaryKey(id);
		model.addAttribute("topic", p);
		return "iss/forum/topic/edit";
	}
	
	/**   
	* @Title: update
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param topic
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,Topic topic){
		topicService.updateByPrimaryKeySelective(topic);
		return "redirect:getlist.do";
	}
	
	/**   
	* @Title: delete
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41 
	* @Description: 删除主题信息
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete(String id){
		topicService.deleteByPrimaryKey(id);
		return "redirect:getlist.do";
	}
	
	/**   
	* @Title: getListForSelect
	* @author Peng Zhongjun
	* @date 2016-8-31 下午20:03:41 
	* @Description: 获得主题表
	* @param @param parkId
	* @return Map<String, Object>     
	*/
	@RequestMapping("/getListForSelect")
	@ResponseBody 
	public Map<String, Object> getListForSelect(@RequestParam(value= "parkId",required = true)String parkId) {
		Map<String,Object> modelMap = new HashMap<String, Object>();
		List<Topic> topics = topicService.selectByParkID(parkId);
		modelMap.put("topics", topics);
		return modelMap;

	}
}
