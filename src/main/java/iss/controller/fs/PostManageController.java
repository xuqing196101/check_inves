package iss.controller.fs;

import iss.model.fs.Park;
import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ParkService;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;
import iss.service.fs.TopicService;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.RoleServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.constant.Constant;


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
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
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
	public String getList(HttpServletRequest request,Model model,Integer page)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String postName = request.getParameter("postName");
		String parkId = request.getParameter("parkId");	
		String topicId = request.getParameter("topicId");	
		User user = (User)request.getSession().getAttribute("loginUser");
		String userId = user.getId();
		if(page==null){
			page=1;
		}
		if(postName !=null && !postName.equals("")){
			map.put("postName", postName);
		}
		if(parkId != null && !parkId.equals("")){
			map.put("parkId", parkId);
		}
		if(topicId != null && !topicId.equals("")){
			map.put("topicId", topicId);
			String topicName = topicService.selectByPrimaryKey(topicId).getName();
			model.addAttribute("topicName", topicName);
		}
		//如果是管理员 就获取所有帖子，版主获取自己负责的版块下的帖子
		HashMap<String,Object> roleMap = new HashMap<String,Object>();
		roleMap.put("userId", userId);
		roleMap.put("code", "ADMIN_R");
		BigDecimal i = roleService.checkRolesByUserId(roleMap);
		BigDecimal j = new BigDecimal(0);
		if(i.equals(j)){	
			map.put("userId", userId);
		}
		map.put("page",page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Post> list = postService.queryByList(map);
		//如果是管理员获取所有版块，版主则获取自己负责的版块
		List<Park> parks = null;
		if(i.equals(j)){			
			parks = parkService.selectParkListByUser(map);
		}else{
			parks = parkService.getAll(null);
		}
		model.addAttribute("parks", parks);
		model.addAttribute("list", new PageInfo<Post>(list));
		model.addAttribute("postName", postName);
		model.addAttribute("parkId", parkId);
		model.addAttribute("topicId", topicId);
		
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
	public String view(Model model,String id,HttpServletRequest request){
		Post p = postService.selectByPrimaryKey(id);
		String contentHtml = p.getContent();
        String content = contentHtml.replaceAll("<[^>]*>", "");

		//附件信息
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("typeId", datas.get(0).getId());
		}

		model.addAttribute("post", p);
		model.addAttribute("content", content);
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
	public String add(Model model,HttpServletRequest request) throws IOException{
		//附件上传
		String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		Post post = new Post();
		post.setId(id);
		model.addAttribute("post", post);
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> list = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(list.size()>0){
			model.addAttribute("typeId", list.get(0).getId());
		}
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
	public String save(@Valid Post post,BindingResult result,HttpServletRequest request, Model model)throws IOException{
		Boolean flag = true;
		String url = "";
		String parkId = request.getParameter("parkId");
		String topicId = request.getParameter("topicId");
		if(parkId.equals(null) || parkId.equals("")){
			flag = false;
			model.addAttribute("ERR_park", "版块不能为空");			
		} 
		if(topicId.equals(null) || topicId.equals("")){
			flag = false;
			model.addAttribute("ERR_topic", "主题不能为空");
		}
				
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			List<Park> parks = parkService.getAll(null);
			model.addAttribute("parks", parks);
			if(!(parkId.equals(null) ||parkId.equals(""))){
				Park park = parkService.selectByPrimaryKey(parkId);
				post.setPark(park);
			}
			if(!(topicId.equals(null) ||topicId.equals(""))){
				Topic topic = topicService.selectByPrimaryKey(topicId);				
				post.setTopic(topic);
			}
			DictionaryData dd=new DictionaryData();
			dd.setCode("POST_ATTACHMENT");
			List<DictionaryData> list = dictionaryDataServiceI.find(dd);
			request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
			if(list.size()>0){
				model.addAttribute("typeId", list.get(0).getId());
			}
			model.addAttribute("post", post);
			url ="iss/forum/post/add";
		}else{
			Park park = parkService.selectByPrimaryKey(parkId);
			Topic topic = topicService.selectByPrimaryKey(topicId);
			Timestamp ts = new Timestamp(new Date().getTime());
			post.setPublishedAt(ts);		
			post.setPark(park);		
			post.setTopic(topic);
			User user = (User)request.getSession().getAttribute("loginUser");
			post.setUser(user);
			//帖子置顶
			if(post.getIsTop() == 1){
				//该版块下面的帖子状态更新
				List<Post> posts = postService.selectByParkID(parkId);
				for (Post post2 : posts) {
					post2.setIsTop(0);
					postService.updateByPrimaryKeySelective(post2);
				}
			}			
			postService.insertSelective(post);		
			
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
	public String edit(String id,Model model,HttpServletRequest request){
		Post p = postService.selectByPrimaryKey(id);
		model.addAttribute("post", p);
		List<Park> parks = parkService.getAll(null);
		model.addAttribute("parks", parks);
		List<Topic> topics = topicService.selectByParkID(p.getPark().getId());
		//附件信息
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		request.getSession().setAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("typeId", datas.get(0).getId());
		}
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
	public String update(@Valid Post post,BindingResult result,HttpServletRequest request, Model model){
		Boolean flag = true;
		String url = "";
		String postId= request.getParameter("postId");
		String parkId = request.getParameter("parkId");
		String topicId = request.getParameter("topicId");

		if(parkId ==null || parkId.equals("") ){
			flag = false;
			model.addAttribute("ERR_park", "版块不能为空");			
		}
		if(topicId ==null || topicId.equals("") ){
			flag = false;
			model.addAttribute("ERR_topic", "主题不能为空");
		}
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			Post p = postService.selectByPrimaryKey(postId);
			//校验回显
			p.setName(post.getName());
			p.setContent(post.getContent());
			p.setIsLocking(post.getIsLocking());
			p.setIsTop(post.getIsTop());
			if(parkId!=null &&!parkId.equals("")){
				Park park = parkService.selectByPrimaryKey(parkId);		
				p.setPark(park);
			}
			if(topicId!=null &&!topicId.equals("")){
				Topic topic = topicService.selectByPrimaryKey(topicId);
				p.setTopic(topic);
			}
			model.addAttribute("post", p);
			List<Park> parks = parkService.getAll(null);
			model.addAttribute("parks", parks);
			List<Topic> topics = topicService.selectByParkID(p.getPark().getId());
			model.addAttribute("topics", topics);
			
			url="iss/forum/post/edit";
			
		}else{
			Park park = parkService.selectByPrimaryKey(parkId);
			Topic topic = topicService.selectByPrimaryKey(topicId);
			post.setPark(park);		
			post.setTopic(topic);		
			post.setId(postId);			
			//每个版块置顶帖更新
			if(post.getIsTop() == 1){
				//该版块下面的帖子状态更新
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("parkId", parkId);
				List<Post> posts = postService.queryByList(map);
				posts.remove(post);
				for (Post post2 : posts) {
					post2.setIsTop(0);
					postService.updateByPrimaryKeySelective(post2);
				}
			}
			
			postService.updateByPrimaryKeySelective(post);
			url="redirect:getlist.html";
		}
				
		return url;
	}
	
	/**   
	* @Title: delete
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41 
	* @Description: 删除帖子信息
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){		
		String[] ids=id.split(",");
		for (String str : ids) {
			postService.deleteByPrimaryKey(str);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("postId", str);
			List<Reply> replies = replyService.selectByPostID(map);
			for (Reply reply : replies) {
				replyService.deleteByPrimaryKey(reply.getId());
			}
		}
	}
	
	/**   
	* @Title: getIndexList
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取帖子列表跳转到前台界面（二级页）
	* @param @param model
	* @param @param  request   
	* @return String     
	*/
	@RequestMapping("/getIndexlist")
	public String getIndexList(Model model,HttpServletRequest request, Integer page) throws Exception{	
		Map<String,Object> map = new HashMap<String, Object>();
		String parkId = request.getParameter("parkId");	
		String topicId = request.getParameter("topicId");
		String searchType = request.getParameter("searchType");
		if(page==null){
			page=1;
		}

		if(parkId != null && parkId!=""){
			map.put("parkId", parkId);
		}
		if(topicId != null && topicId!=""){
			map.put("topicId", topicId);
		}
		if(searchType == null || searchType=="" ||searchType.equals("pubtime")){
			map.put("searchType", "pubtime");
		}else if (searchType.equals("retime")) {
			map.put("searchType", "retime");
		}else if(searchType.equals("hot")){
			map.put("searchType", "hot");
		}

		map.put("page",page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Post> list = postService.queryByList(map);
		Park park = parkService.selectByPrimaryKey(parkId);
		List<Topic> topics = topicService.selectByParkID(parkId);
		
		//置顶帖子
		Post topPost = postService.selectParkTopPost(parkId);
		model.addAttribute("topPost", topPost);
		list.remove(topPost);
		
		model.addAttribute("topics", topics);
		model.addAttribute("park", park);
		model.addAttribute("list", new PageInfo<Post>(list));
		model.addAttribute("parkId", parkId);
		model.addAttribute("topicId", topicId);
		model.addAttribute("searchType", searchType);
		
		return "iss/forum/list";
	}
	
	/**
	* @Title: getHotList
	* @author Peng Zhongjun
	* @date 2016-10-4 上午10:05:43  
	* @Description: 查询所有热门帖子表 
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return
	* @param @throws Exception      
	* @return String
	 */
	@RequestMapping("/getHotlist")
	public String getHotList(Model model,HttpServletRequest request, Integer page) throws Exception{	
		if(page==null){
			page=1;
		}
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Post> list = postService.queryAllHotPost();
		model.addAttribute("list", new PageInfo<Post>(list));
		return "iss/forum/hot_post_list";
	}
	
	/**   
	* @Title: getIndexDetail
	* @author Peng Zhongjun
	* @date 2016-8-30 下午19:47:32  
	* @Description: 获取帖子详情跳转到前台界面
	* @param @param model
	* @param @param  request   
	* @return String     
	*/
	@RequestMapping("/getIndexDetail")
	public String getIndexDetail(Model model,HttpServletRequest request, Integer page) throws Exception{	
		Map<String,Object> map = new HashMap<String, Object>();
		String postId = request.getParameter("postId");
		if(page==null){
			page=1;
		}
		if(postId != null && postId!=""){
			map.put("postId", postId);
		}
		map.put("page",page.toString());
		
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));					
		List<Reply> list = replyService.selectByPostID(map);
		for (Reply reply : list) {
			Map<String,Object> map2 = new HashMap<String, Object>();
			map2.put("replyId", reply.getId());
			List<Reply> replies = replyService.selectByReplyId(map2);
			reply.setReplies(replies);
		}
		//附件信息
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> datas = dictionaryDataServiceI.find(dd);
		model.addAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(datas.size()>0){
			model.addAttribute("typeId", datas.get(0).getId());
		}
		Post post = postService.selectByPrimaryKey(postId);	
		
		model.addAttribute("post", post);
		model.addAttribute("list",  new PageInfo<Reply>(list));
		model.addAttribute("replyLength",  list.size());
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
		String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
		Post post = new Post();
		post.setId(uuid);
		model.addAttribute("post", post);
		DictionaryData dd=new DictionaryData();
		dd.setCode("POST_ATTACHMENT");
		List<DictionaryData> list = dictionaryDataServiceI.find(dd);
		model.addAttribute("sysKey", Constant.FORUM_SYS_KEY);
		if(list.size()>0){
			model.addAttribute("typeId", list.get(0).getId());
		}
		List<Park> parks = parkService.getAll(null);

		model.addAttribute("parks", parks);

		return "iss/forum/publish_post";
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
	public String indexsave(@Valid Post post,BindingResult result,HttpServletRequest request, Model model){
		Boolean flag = true;
		String url = "";
		String parkId = request.getParameter("parkId");
		String topicId = request.getParameter("topicId");
		if(parkId == null ||parkId=="" ){
			flag = false;
			model.addAttribute("ERR_park", "版块不能为空");			
		}
		if(topicId == null ||topicId=="" ){
			flag = false;
			model.addAttribute("ERR_topic", "主题不能为空");
		}				
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			List<Park> parks = parkService.getAll(null);
			model.addAttribute("parks", parks);
			model.addAttribute("parkId", parkId);
			model.addAttribute("topicId", topicId);
			model.addAttribute("post", post);
			url ="iss/forum/publish_post";
		}else{
			Timestamp ts = new Timestamp(new Date().getTime());
			post.setPublishedAt(ts);
			Park park= parkService.selectByPrimaryKey(parkId);
			Topic topic =topicService.selectByPrimaryKey(topicId);
			post.setPark(park);
			post.setTopic(topic);
			post.setIsLocking(0);
			post.setIsTop(0);
			User user = (User)request.getSession().getAttribute("loginUser");
			post.setUser(user);			
			postService.insertSelective(post);	
			url ="redirect:/park/getIndex.html";
		}
		return url;
	}

	/**   
	* @Title: publish
	* @author Peng Zhongjun
	* @date 2016-8-31下午19:58:43   
	* @Description:前台查询自己创建的帖子
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/mypost")
	public String mypost(HttpServletRequest request,Model model,Integer page)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();	
		User user = (User)request.getSession().getAttribute("loginUser");
		String userId = user.getId();
		if(page==null){
			page=1;
		}
		if(userId != null && !userId.equals("")){
			map.put("userId", userId);
		}
		map.put("page",page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Post> list = postService.queryMyPost(map);		
		model.addAttribute("list", new PageInfo<Post>(list));
		model.addAttribute("userId", userId);
		return "iss/forum/my_post_list";
	}
	
	/**
	 * 
	* @Title: delMyPost
	* @author ZhaoBo
	* @date 2016-11-24 下午3:01:06  
	* @Description: 删除当前登录人的帖子 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delMyPost")
	@ResponseBody
	public void delMyPost(HttpServletRequest request){
		String id = request.getParameter("id");		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("postId", id);
		List<Reply> replies = replyService.selectByPostID(map);
		for (Reply reply : replies) {
			replyService.deleteByPrimaryKey(reply.getId());
		}
		postService.deleteByPrimaryKey(id);
	}
	
	/**
	 * 
	* @Title: backPost
	* @author ZhaoBo
	* @date 2016-11-29 下午2:59:45  
	* @Description: 返回到帖子列表
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backPost")
	public String backPost(){
		return "redirect:getlist.html";
	}
}
