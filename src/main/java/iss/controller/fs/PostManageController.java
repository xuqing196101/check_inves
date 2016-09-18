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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.util.DateUtil;


/**
* @Title:PostManageController 
* @Description: 帖子管理控制类
* @author Peng Zhongjun
* @date 2016-9-7下午6:19:24
 */
@Controller
@Scope("prototype")
@RequestMapping("/post")
public class PostManageController {
	@Autowired
	private PostService postService;	
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ParkService parkService;	
	@Autowired
	private TopicService topicService;	
	
	/**   
	* @Title: getList
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取帖子列表跳转到后台界面
	* @param @param model
	* @param @param  post   
	* @return String     
	*/
	@RequestMapping("/getlist")
	public String getList(Model model,Post post,Integer page){
		List<Post> list = postService.queryByList(post,page==null?1:page);
		for (Post post2 : list) {
			Reply reply = new Reply();
			reply.setPost(post2);
			BigDecimal replycount = replyService.queryByCount(reply);
			post2.setReplycount(replycount);
		}
		model.addAttribute("list", new PageInfo<Post>(list));
		return "iss/forum/post/list";
	}	
	
	/**   
	* @Title: view
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:55:32  
	* @Description: 显示评论详细信息页面
	* @param @param model
	* @param @param post
	* @return String     
	*/
	@RequestMapping("/view")
	public String view(Model model,String id){
		Post p = postService.selectByPrimaryKey(id);		
		model.addAttribute("post", p);		
		return "iss/forum/post/view";
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
	public String add(Model model,HttpServletRequest request){
		List<Park> parks = parkService.getAll(null);
		model.addAttribute("parks", parks);
		return "iss/forum/post/add";
	}

	/**   
	* @Title: save
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41   
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param post
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Post post){
		Timestamp ts = new Timestamp(new Date().getTime());
		post.setPublishedTime(ts);
		String parkId = request.getParameter("parkId");
		Park park = parkService.selectByPrimaryKey(parkId);
		post.setPark(park);
		String topicId = request.getParameter("topicId");
		Topic topic = topicService.selectByPrimaryKey(topicId);
		post.setTopic(topic);
		User user = (User)request.getSession().getAttribute("loginUser");
		post.setUser(user);
		postService.insertSelective(post);
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
		Post p = postService.selectByPrimaryKey(id);
		model.addAttribute("post", p);
		List<Park> parks = parkService.getAll(null);
		model.addAttribute("parks", parks);
		List<Topic> topics = topicService.selectByParkID(p.getPark().getId());
		model.addAttribute("topics", topics);
		return "iss/forum/post/edit";
	}
	
	/**   
	* @Title: update
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param post
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,Post post){
		String parkId = request.getParameter("parkId");
		Park park = parkService.selectByPrimaryKey(parkId);
		post.setPark(park);
		String topicId = request.getParameter("topicId");
		Topic topic = topicService.selectByPrimaryKey(topicId);
		post.setTopic(topic);
		String postId= request.getParameter("postId");
		post.setId(postId);
		postService.updateByPrimaryKeySelective(post);
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
	public String delete(String id){		
		String[] ids=id.split(",");
		for (String str : ids) {
			postService.deleteByPrimaryKey(str);
		}
		return "redirect:getlist.html";
	}
	/**   
	* @Title: getIndexList
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取帖子列表跳转到前台界面
	* @param @param model
	* @param @param  request   
	* @return String     
	*/
	@RequestMapping("/getIndexlist")
	public String getIndexList(Model model,HttpServletRequest request){	
		System.out.println(request.getParameter("parkId"));
		List<Post> list = postService.selectListByParkID( request.getParameter("parkId"));
		model.addAttribute("list", list);
		return "iss/forum/list";
	}
	/**   
	* @Title: getIndexDetail
	* @author Peng Zhongjun
	* @date 2016-8-30 下午19:47:32  
	* @Description: 获取帖子列表跳转到前台界面
	* @param @param model
	* @param @param  request   
	* @return String     
	*/
	@RequestMapping("/getIndexDetail")
	public String getIndexDetail(Model model,HttpServletRequest request){		
		System.out.println((String) request.getParameter("postId"));
		Post post = postService.selectByPrimaryKey( request.getParameter("postId"));
		
		List<Reply> replies = replyService.selectByPostID(post.getId());
		post.setReplies(replies);
		model.addAttribute("post", post);
		return "iss/forum/detail";
	}
	/**   
	* @Title: publish
	* @author Peng Zhongjun
	* @date 2016-8-31下午19:58:43   
	* @Description: 跳转发布编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/publish")
	public String indexpublish(Model model,HttpServletRequest request){
		List<Park> parks = parkService.getAll(null);

		model.addAttribute("parks", parks);

		return "iss/forum/publishpost";
	}
	/**   
	* @Title: indexsave
	* @author Peng Zhongjun
	* @date 2016-8-31下午19:58:43   
	* @Description: 发布帖子
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/indexsave")
	public String indexsave(HttpServletRequest request,Post post){
		Timestamp ts = new Timestamp(new Date().getTime());
		post.setPublishedTime(ts);
		Park park= parkService.selectByPrimaryKey(request.getParameter("parkId"));
		Topic topic =topicService.selectByPrimaryKey(request.getParameter("topicId"));
		post.setPark(park);
		post.setTopic(topic);
		postService.insertSelective(post);	
		return "redirect:/park/getIndex.html";
	}
	
	
}
