package yggc.controller.sys.iss.fs;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.iss.fs.Park;
import yggc.model.iss.fs.Post;
import yggc.model.iss.fs.Reply;
import yggc.model.iss.fs.Topic;
import yggc.service.iss.fs.ParkService;
import yggc.service.iss.fs.PostService;
import yggc.service.iss.fs.ReplyService;
import yggc.service.iss.fs.TopicService;
import yggc.util.DateUtil;

/**
 * <p>Title:PostManageController </p>
 * <p>Description:帖子管理控制类  </p>
 * <p>Company: yggc </p> 
 * @author junjunjun1993
 * @date 2016-8-10下午5:03:01
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
	* @author junjunjun1993
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取帖子列表跳转到后台界面
	* @param @param model
	* @param @param  post   
	* @return String     
	*/
	@RequestMapping("/getlist")
	public String getList(Model model,Post post){
		List<Post> list = postService.queryByList(post);
		model.addAttribute("list", list);
		return "forum/post/list";
	}	
	/**   
	* @Title: view
	* @author junjunjun1993
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
		return "forum/post/view";
	}
	
	/**   
	* @Title: add
	* @author junjunjun1993
	* @date 2016-8-10 下午19:58:43   
	* @Description: 跳转新增编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/add")
	public String add(Model model,HttpServletRequest request){
		List<Park> parks = parkService.queryByList(null);
		model.addAttribute("parks", parks);
		return "forum/post/add";
	}

	/**   
	* @Title: save
	* @author junjunjun1993
	* @date 2016-8-10 下午20:03:41   
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param post
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Post post){
		postService.insertSelective(post);
		return "redirect:getlist.do";
	}
	
	/**   
	* @Title: edit
	* @author junjunjun1993
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
		return "forum/post/edit";
	}
	
	/**   
	* @Title: update
	* @author junjunjun1993
	* @date 2016-8-10 下午20:03:41  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param post
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,Post post){
		postService.updateByPrimaryKeySelective(post);
		return "redirect:getlist.do";
	}
	
	/**   
	* @Title: delete
	* @author junjunjun1993
	* @date 2016-8-10 下午20:03:41 
	* @Description: 删除版块信息
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete(String id){
		postService.deleteByPrimaryKey(id);
		return "redirect:getlist.do";
	}
	/**   
	* @Title: getIndexList
	* @author junjunjun1993
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
		return "forum/list";
	}
	/**   
	* @Title: getIndexDetail
	* @author junjunjun1993
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
		return "forum/detail";
	}
	/**   
	* @Title: publish
	* @author junjunjun1993
	* @date 2016-8-31下午19:58:43   
	* @Description: 跳转发布编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/publish")
	public String indexpublish(Model model,HttpServletRequest request){
		List<Park> parks = parkService.queryByList(null);

		model.addAttribute("parks", parks);

		return "forum/publishpost";
	}
	/**   
	* @Title: indexsave
	* @author junjunjun1993
	* @date 2016-8-31下午19:58:43   
	* @Description: 获取帖子列表跳转到前台界面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/indexsave")
	public String indexsave(HttpServletRequest request,Post post){
		post.setPublishedTime((Timestamp)new Date());
		System.out.println(request.getParameter("parkId"));
		System.out.println(request.getParameter("topicId"));
		Park park= parkService.selectByPrimaryKey(request.getParameter("parkId"));
		Topic topic =topicService.selectByPrimaryKey(request.getParameter("topicId"));
		post.setPark(park);
		post.setTopic(topic);
		postService.insertSelective(post);
		
		return "redirect:/park/getIndex.do";
	}
	
	
}
