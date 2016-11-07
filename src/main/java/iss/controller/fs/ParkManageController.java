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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.service.bms.UserServiceI;

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
public class ParkManageController extends BaseSupplierController {

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
		return "iss/forum/park/list";
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
	public String save(@Valid Park park, BindingResult result,HttpServletRequest request, Model model) {
		
		BigDecimal i = parkService.checkParkName(park.getName());
		Boolean flag = true;
		String url = "";
		BigDecimal j = new BigDecimal(0);
		if(i.compareTo(j) != 0){
			flag = false;
			model.addAttribute("ERR_name", "版块名称不能重复");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			
			flag = false;
		}
		if(flag == false){

			model.addAttribute("park", park);
			url = "iss/forum/park/add";
			
		}else{		
			String userId = request.getParameter("userId");
			if(!(userId.equals(null) || userId.equals(""))){
				 User user = userService.getUserById(userId);			
				park.setUser(user);
			}
			User creater = (User) request.getSession().getAttribute("loginUser");
			park.setCreater(creater);
			Timestamp ts = new Timestamp(new Date().getTime());
			park.setCreatedAt(ts);
			Timestamp ts1 = new Timestamp(new Date().getTime());
			park.setUpdatedAt(ts1);
			parkService.insertSelective(park);
			url = "redirect:getlist.html";
		}
		return url;
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
	public String update(@Valid Park park, BindingResult result,HttpServletRequest request, Model model) {	
		String parkId = request.getParameter("parkId");
		BigDecimal i = parkService.checkParkName(park.getName());
		Boolean flag = true;
		String url = "";
		BigDecimal j = new BigDecimal(0);
		String oldParkName =request.getParameter("oldParkName");
		if(!oldParkName.equals(park.getName())&& i.compareTo(j) != 0){			
			flag = false;
			model.addAttribute("ERR_name", "版块名称不能重复");			
		}
		
		int k = parkService.queryHotParks().size();		
		Park p = parkService.selectByPrimaryKey(parkId);
		if(p.getIsHot() == 0&& !(k < 4)){
			flag = false;
			model.addAttribute("ERR_isHot", "热门版块不能超过4个");	
		}
		
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			p.setName(park.getName());
			String userId = request.getParameter("userId");
			if(!(userId.equals(null) || userId.equals(""))){
				User user = userService.getUserById(userId);
				park.setUser(user);
			}
			p.setIsHot(park.getIsHot());
			p.setContent(park.getContent());
			model.addAttribute("park", p);
			url = "iss/forum/park/edit";
			
		}else{
			String userId = request.getParameter("userId");
			if(!(userId.equals(null) || userId.equals(""))){
				User user = userService.getUserById(userId);		
				park.setUser(user);
			}
			Timestamp ts = new Timestamp(new Date().getTime());
			park.setUpdatedAt(ts);		
			park.setId(parkId);
			parkService.updateByPrimaryKeySelective(park);
			url="redirect:getlist.html";
		}
		
		return url;
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
		List<Park> hotParks = new ArrayList<Park>();
		List<Park> parklist = parkService.getAll(null);		
		for (Park park : parklist) {			
			BigDecimal replycount = replyService.queryCountByParkId(park.getId());
			park.setReplycount(replycount);
			Post post = new Post();
			post.setPark(park);
			BigDecimal postcount = postService.queryByCount(post);
			park.setPostcount(postcount);
			//筛选热门版块
			if(park.getIsHot() == 1){
				hotParks.add(park);
			}
		}

		//去除重复的
		for (Park park : hotParks) {
			parklist.remove(park);
		}
		//如果热门版块不满4 根据版块下的回复量补充
		if(hotParks.size() < 4){
			int i = 4 -hotParks.size();
			//根据回复量排序
			Collections.sort(parklist, new Comparator<Park>() {  				  
	            @Override  
	            public int compare(Park p1, Park p2) {  
	            	int i = p2.getReplycount().compareTo(p1.getReplycount());//先按照回复量排序
	       	      	if(i == 0){  
	       	            return p2.getPostcount().compareTo(p1.getPostcount());//如果回复量相同按照帖子数  
	       	        } 
	       	      	return i; 
	            }  
	        });  
			//添加
			for(int j=0;j<i;j++){
				hotParks.add(parklist.get(j));
			}
			
		}
		model.addAttribute("hotParks", hotParks);
		List<Park> parklist2 = parkService.getAll(null);
		for (Park park : parklist2) {
			List<Post> posts = postService.selectByParkID(park.getId());
			park.setPosts(posts);
		}
		model.addAttribute("list", parklist2);
		List<Post> hotPostList = postService.queryHotPost();
		model.addAttribute("hotPostList", hotPostList);

		return "iss/forum/forum_Index";
	}
	
	/**
	 * 
	* @Title: getUserForSelect
	* @author Peng Zhongjun
	* @date 2016-10-29 下午1:36:28  
	* @Description: 获取版主 
	* @param @param response
	* @param @param userName      
	* @return void
	 */
	@RequestMapping( value="/getUserForSelect" )	
	public void getUserForSelect(HttpServletResponse response) {
		List<User> users = userService.queryByList(null);
		super.writeJson(response, users);
	}
}
