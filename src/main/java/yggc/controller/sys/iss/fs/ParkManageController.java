/**
 * 
 */
package yggc.controller.sys.iss.fs;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.bms.User;
import yggc.model.iss.fs.Park;
import yggc.model.iss.fs.Post;
import yggc.model.iss.fs.Topic;
import yggc.service.bms.UserServiceI;
import yggc.service.iss.fs.ParkService;
import yggc.service.iss.fs.PostService;
import yggc.service.iss.fs.ReplyService;
import yggc.service.iss.fs.TopicService;

/**
 * Title:ParkManageController 
 * Description:版块管理控制类  
 * Company: yggc 
 *  @author Peng Zhongjun 
 *  @date 2016-8-10下午5:03:01
 */
@Controller
@Scope("prototype")
@RequestMapping("/park")
public class ParkManageController {

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
	 * @Title: getParkList
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 获取版块列表跳转到后台管理
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getlist")
	public String getParkList(Model model, Park park) {
		List<Park> parklist = parkService.queryByList(park);
		for (Park park2 : parklist) {
			Topic topic = new Topic();
			topic.setPark(park2);
			BigDecimal topiccount = topicService.queryByCount(topic);
			Post post = new Post();
			post.setPark(park2);
			BigDecimal postcount = postService.queryByCount(post);
			BigDecimal replycount = replyService.queryCountByParkId(park2.getId());
			park2.setTopiccount(topiccount);
			park2.setPostcount(postcount);
			park2.setReplycount(replycount);
		}
		model.addAttribute("list", parklist);
		return "iss/forum/park/parklist";
	}
	
	/**
	 * @Title: view
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午19:55:32
	 * @Description: 显示版块详细信息页面
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/view")
	public String view(Model model, String id) {
		Park p = parkService.selectByPrimaryKey(id);
		
		Topic topic = new Topic();
		topic.setPark(p);
		BigDecimal topiccount = topicService.queryByCount(topic);
		Post post = new Post();
		post.setPark(p);
		BigDecimal postcount = postService.queryByCount(post);
		BigDecimal replycount = replyService.queryCountByParkId(p.getId());
		p.setTopiccount(topiccount);
		p.setPostcount(postcount);
		p.setReplycount(replycount);
		
		model.addAttribute("park", p);
		return "iss/forum/park/view";
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
	public String add(Model model,HttpServletRequest request) {
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		return "iss/forum/park/add";
	}

	/**
	 * @Title: save
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 保存新增信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Park park) {
		System.out.println((String)request.getParameter("userId"));
		User user = new User();
		user.setId((String)request.getParameter("userId"));
		user = userService.getUserById(user);
		park.setUser(user);
		User creater = (User)request.getSession().getAttribute("loginUser");
		park.setCreater(creater);
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setCreatedAt(ts);
		parkService.insertSelective(park);
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
	public String edit(String id, Model model) {
		Park p = parkService.selectByPrimaryKey(id);
		model.addAttribute("park", p);
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		return "iss/forum/park/edit";
	}

	/**
	 * @Title: update
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 更新修改信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Park park) {
		
		User user = new User();
		user.setId((String)request.getParameter("userId"));
		user = userService.getUserById(user);
		park.setUser(user);
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setUpdatedAt(ts);
		String parkId = request.getParameter("parkId");
		park.setId(parkId);
		parkService.updateByPrimaryKeySelective(park);
		return "redirect:getlist.do";
	}

	/**
	 * @Title: delete
	 * @author Peng Zhongjun
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 删除版块信息
	 * @param @param id
	 * @return String
	 */
	@RequestMapping("/delete")
	public String delete(String id) {
		parkService.deleteByPrimaryKey(id);
		return "redirect:getlist.do";
	}
	/**
	 * @Title: getPark
	 * @author Peng Zhongjun
	 * @date 2016-8-24 下午13:41:32
	 * @Description: 获取版块列表跳转到前台
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getIndex")
	public String getPostIndex(Model model, Park park) {
		List<Park> parklist = parkService.queryByList(park);

		for (Park park2 : parklist) {
			List<Post> postlist = postService.selectByParkID(park2.getId());
			park2.setPosts(postlist);
		}
		model.addAttribute("list", parklist);
		
		return "iss/forum/forumIndex";
	}
}
