package iss.controller.fs;

import iss.model.fs.Park;
import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ParkService;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;
import iss.service.fs.TopicService;

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

import ses.model.bms.User;
import ses.service.bms.UserServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


/**
* @Title:ParkManageController 
* @Description: 版块管理控制类
* @author Peng Zhongjun
* @date 2016-9-7下午6:21:30
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
	public String getParkList(Model model, Park park,Integer page) {
		List<Park> parklist = parkService.queryByList(park,page==null?1:page);
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
		model.addAttribute("list", new PageInfo<Park>(parklist));
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
	public String add(Model model, HttpServletRequest request) {
		List<User> users = userService.find(null);
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
		User user = new User();
		user = userService.getUserById(request.getParameter("userId"));
		park.setUser(user);
		User creater = (User) request.getSession().getAttribute("loginUser");
		park.setCreater(creater);
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setCreatedAt(ts);
		Timestamp ts1 = new Timestamp(new Date().getTime());
		park.setUpdatedAt(ts1);
		parkService.insertSelective(park);
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
	public String edit(String id, Model model) {
		Park p = parkService.selectByPrimaryKey(id);
		model.addAttribute("park", p);
		List<User> users = userService.find(null);
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
	
		user = userService.getUserById(request.getParameter("userId"));
		park.setUser(user);
		Timestamp ts = new Timestamp(new Date().getTime());
		park.setUpdatedAt(ts);
		String parkId = request.getParameter("parkId");
		park.setId(parkId);
		parkService.updateByPrimaryKeySelective(park);
		return "redirect:getlist.html";
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
	public String delete(String ids) {	
		String[] id=ids.split(",");
		for (String str : id) {
			parkService.deleteByPrimaryKey(str);
			List<Topic> topics = topicService.selectByParkID(str);
			for (Topic topic : topics) {
				topicService.deleteByPrimaryKey(topic.getId());
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("parkId", str);
			List<Post> posts = postService.queryByList(map); 
			for (Post post : posts) {
				postService.deleteByPrimaryKey(post.getId());
				Map<String,Object> map1 = new HashMap<String, Object>();
				map1.put("postId", post.getId());
				List<Reply> replies = replyService.selectByPostID(map1);
				for (Reply reply : replies) {
					replyService.deleteByPrimaryKey(reply.getId());
				}
			}
		}
		return "redirect:getlist.html";
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
	public String getPostIndex(Model model) {
		Map<String, Object> forumIndexMapper = new HashMap<String, Object>();
		List<Park> parklist = parkService.getAll(null);
		for (Park park : parklist) {			
			forumIndexMapper.put("select"+park.getId()+"Park", park);	
			List<Post> posts = postService.selectByParkID(park.getId());
			park.setPosts(posts);
		}
		List<Post> hotPostList = postService.queryHotPost();
		//List<Topic> topicList = topicService.selectByParkID();
		model.addAttribute("forumIndexMapper", forumIndexMapper);
		model.addAttribute("hotPostList", hotPostList);
		model.addAttribute("list", parklist);
		return "iss/forum/forumIndex";
	}
	
	@RequestMapping("/parkManager")
	public String getFirst(Model model,HttpServletRequest request,Integer page)throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) request.getSession().getAttribute("loginUser");
		String userId = user.getId();
		map.put("userId", userId);
		if(page==null){
			page=1;
		}
		map.put("page",page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Park> list = parkService.selectParkListByUser(map);
		model.addAttribute("list",  new PageInfo<Park>(list));
		return "iss/forum/parkManager/manager";
	}
}
