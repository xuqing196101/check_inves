package iss.controller.fs;

import iss.model.fs.Park;
import iss.model.fs.Post;
import iss.model.fs.PostAttachments;
import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ParkService;
import iss.service.fs.PostAttachmentsService;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;
import iss.service.fs.TopicService;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.User;
import ses.service.bms.RoleServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


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
	private PostAttachmentsService postAttachmentsService;
	@Autowired
	private RoleServiceI roleService;
	
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
		if(postName !=null && postName!=""){
			map.put("postName", postName);
		}
		if(parkId != null && parkId!=""){
			map.put("parkId", parkId);
		}
		if(topicId != null && topicId!=""){
			map.put("topicId", topicId);
			String topicName = topicService.selectByPrimaryKey(topicId).getName();
			model.addAttribute("topicName", topicName);
		}
		//如果是管理员 就获取所有帖子，版主获取自己负责的版块下的帖子
		BigDecimal i = roleService.checkRolesByUserId(userId);
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
	public String view(Model model,String id){
		Post p = postService.selectByPrimaryKey(id);	
		Reply reply = new Reply();
		reply.setPost(p);
		BigDecimal replycount = replyService.queryByCount(reply);
		p.setReplycount(replycount);
		List<PostAttachments> postAttachments = postAttachmentsService.selectAllPostAttachments(id);
		p.setPostAttachments(postAttachments);
		model.addAttribute("post", p);		
		System.out.println(p.getContent());
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
	public String save(@RequestParam("attaattach") MultipartFile[] attaattach, @Valid Post post,BindingResult result,HttpServletRequest request, Model model)throws IOException{
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
			uploadFile(post, request, attaattach);
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
	public String edit(String id,Model model){
		Post p = postService.selectByPrimaryKey(id);
		List<PostAttachments> postAttachments = postAttachmentsService.selectAllPostAttachments(id);
		p.setPostAttachments(postAttachments);
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
	public String update(@RequestParam("attaattach") MultipartFile[] attaattach,@Valid Post post,BindingResult result,HttpServletRequest request, Model model){
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
			String ids = request.getParameter("ids");
			if(ids!=null && ids!=""){
				String[] attaids = ids.split(",");
				for(String id : attaids){
					postAttachmentsService.softDeleteAtta(id);
				}
			}
			uploadFile(post, request, attaattach);
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
	public String delete(String id){		
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
		return "redirect:getlist.html";
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
		Post post = postService.selectByPrimaryKey(postId);	
		List<PostAttachments> postAttachments = postAttachmentsService.selectAllPostAttachments(postId);
		post.setPostAttachments(postAttachments);
		model.addAttribute("post", post);
		model.addAttribute("list",  new PageInfo<Reply>(list));
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
	public String indexsave(@RequestParam("attaattach") MultipartFile[] attaattach,@Valid Post post,BindingResult result,HttpServletRequest request, Model model){
		Boolean flag = true;
		String url = "";
		String parkId = request.getParameter("parkId");
		String topicId = request.getParameter("topicId");

		if(parkId == null ||parkId=="" ){
			flag = false;
			model.addAttribute("ERR_park", "版块不能为空");			
		}
		if(topicId == null ||topicId=="" ){
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

			url ="iss/forum/publish_post";
		}else{
			Timestamp ts = new Timestamp(new Date().getTime());
			post.setPublishedAt(ts);
			Park park= parkService.selectByPrimaryKey(parkId);
			Topic topic =topicService.selectByPrimaryKey(topicId);
			post.setPark(park);
			post.setTopic(topic);
			postService.insertSelective(post);	
			uploadFile(post, request, attaattach);
			
			url ="redirect:/park/getIndex.html";
		}
		return url;
	}
	/**
	 * 
	* @Title: uploadFile
	* @author QuJie 
	* @date 2016-9-9 下午1:36:34  
	* @Description: 上传的公共方法 
	* @param @param article
	* @param @param request
	* @param @param attaattach      
	* @return void
	 */
	public void uploadFile(Post post,HttpServletRequest request,MultipartFile[] attaattach){
		if(attaattach!=null){
			for(int i=0;i<attaattach.length;i++){
				if(attaattach[i].getOriginalFilename()!=null && attaattach[i].getOriginalFilename()!=""){
			        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
			        /** 创建文件夹 */
					File rootfile = new File(rootpath);
					if (!rootfile.exists()) {
						rootfile.mkdirs();
					}
			        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attaattach[i].getOriginalFilename();
			        String filePath = rootpath+fileName;
			        File file = new File(filePath);
			        try {
						attaattach[i].transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					PostAttachments attachment=new PostAttachments();
					attachment.setPost(new Post(post.getId()));
					attachment.setName(fileName);
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setType(attaattach[i].getContentType());
					attachment.setFileSize((float)attaattach[i].getSize());
					attachment.setPath(filePath);
					postAttachmentsService.insertSelective(attachment);
				}
			}
		}
	}
	/**
	 * 
	* @Title: downloadArticleAtta
	* @author QuJie 
	* @date 2016-9-8 上午9:05:53  
	* @Description: 详情页下载附件 
	* @param @throws Exception      
	* @return void
	 */
	@RequestMapping("/downloadPostAtta")
	public void downloadPostAtta(PostAttachments postAttachments,HttpServletResponse response) throws Exception{
		PostAttachments postAtta = postAttachmentsService.selectPostAttaByPrimaryKey(postAttachments.getId());
		String filePath = postAtta.getPath();
		File file = new File(filePath);
		if(file == null || !file.exists()){
			return;
		}
		String fileName = (postAtta.getName().split("_"))[1];
		response.reset();
		response.setContentType(postAtta.getType()+"; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		OutputStream out = response.getOutputStream();
		out.write(FileUtils.readFileToByteArray(file));
		out.flush();

		if(out !=  null){
			out.close();
		}
	}
}
