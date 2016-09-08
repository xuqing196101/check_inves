package ses.controller.sys.iss.fs;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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

import ses.model.bms.User;
import ses.model.iss.fs.Park;
import ses.model.iss.fs.Post;
import ses.model.iss.fs.Topic;
import ses.service.bms.UserServiceI;
import ses.service.iss.fs.ParkService;
import ses.service.iss.fs.PostService;
import ses.service.iss.fs.ReplyService;
import ses.service.iss.fs.TopicService;


/**
* @Title:TopicManageController 
* @Description: 主题管理控制类
* @author Peng Zhongjun
* @date 2016-9-7下午6:22:10
 */
@Controller
@Scope("prototype")
@RequestMapping("/topic")
public class TopicManageController {
	
	@Autowired
	private ParkService parkService;	
	@Autowired
	private PostService postService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private UserServiceI userService;
	
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
		for (Topic topic2 : list) {
			Post post = new Post();
			post.setTopic(topic2);
			BigDecimal postcount = postService.queryByCount(post);
			topic2.setPostcount(postcount);
			BigDecimal replycount = replyService.queryCountByParkId(topic2.getId());
			topic2.setReplycount(replycount);
		}
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
		Topic topic = topicService.selectByPrimaryKey(id);
		Post post = new Post();
		post.setTopic(topic);
		BigDecimal postcount = postService.queryByCount(post);
		topic.setPostcount(postcount);
		BigDecimal replycount = replyService.queryCountByParkId(id);
		topic.setReplycount(replycount);
		model.addAttribute("topic", topic);
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
	public String add(HttpServletRequest request,Model model){
		List<Park> parks = parkService.queryByList(null);
		model.addAttribute("parks", parks);
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
		Park park = parkService.selectByPrimaryKey(request.getParameter("parkId"));
		topic.setPark(park);
		User user = (User)request.getSession().getAttribute("loginUser");
		topic.setUser(user);
		Timestamp ts = new Timestamp(new Date().getTime());
		topic.setCreatedAt(ts);
		topicService.insertSelective(topic);
		return "redirect:getlist.html";
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
		List<Park> parks = parkService.queryByList(null);
		model.addAttribute("parks", parks);
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
		Park park = parkService.selectByPrimaryKey(request.getParameter("parkId"));
		topic.setPark(park);
		Timestamp ts = new Timestamp(new Date().getTime());
		topic.setUpdatedAt(ts);
		String topicId = request.getParameter("topicId");
		topic.setId(topicId);
		topicService.updateByPrimaryKeySelective(topic);
		return "redirect:getlist.html";
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
		return "redirect:getlist.html";
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
