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
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.service.bms.RoleServiceI;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;


/**
* @Title:TopicManageController 
* @Description: 主题管理控制类
* @author Peng Zhongjun
* @date 2016-9-7下午6:22:10
 */
@Controller
@Scope("prototype")
@RequestMapping("/topic")
public class TopicManageController extends BaseSupplierController {
	
	@Autowired
	private ParkService parkService;	
	@Autowired
	private PostService postService;
	@Autowired
	private TopicService topicService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private RoleServiceI roleService;
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
	public String getList(@CurrentUser User user,HttpServletRequest request,Model model,Integer page)throws Exception{
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			Map<String,Object> map = new HashMap<String, Object>();
			String describe = request.getParameter("condition");
			String parkId = request.getParameter("parkId");		
			if(page == null){
				page=1;
			}
			if(describe!=null && describe!=""){
				map.put("content", describe);
			}
			if(parkId !=null && parkId!=""){
				map.put("parkId", parkId);
			}
			if(!i.equals(new BigDecimal(1))){
				map.put("userId", user.getId());
			}
			map.put("page",page.toString());
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<Topic> list = topicService.queryByList(map);
			for (Topic topic2 : list) {
				Post post = new Post();
				post.setTopic(topic2);
				BigDecimal postcount = postService.queryByCount(post);
				topic2.setPostcount(postcount);
				BigDecimal replycount = replyService.queryCountByTopicId(topic2.getId());
				topic2.setReplycount(replycount);
			}
			List<Park> parks = parkService.getAll(null);
			model.addAttribute("parks", parks);
			model.addAttribute("list", new PageInfo<Topic>(list));
			model.addAttribute("describe", describe);
			model.addAttribute("parkId", parkId);
			model.addAttribute("admin", i);
			model.addAttribute("authType", authType);
        }
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
	public String view(@CurrentUser User user,Model model,String id){
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
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
		return "";
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
		User user = (User) request.getSession().getAttribute("loginUser");
		if(null != user && "4".equals(user.getTypeName())){
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			Map<String,Object> map = new HashMap<String,Object>();
			if(!i.equals(new BigDecimal(1))){
				map.put("userId", user.getId());
			}
			List<Park> parks = parkService.selectParkListByUser(map);
			model.addAttribute("parks", parks);
			return "iss/forum/topic/add";
		}
		return "";
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
	public String save(Topic topic,HttpServletRequest request, Model model){		
		Boolean flag = true;
		String url = "";
		String parkId = request.getParameter("parkId");
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("name", topic.getName());
		map.put("parkId", parkId);
		String name = request.getParameter("name");
		if(name.trim().equals("")||name.trim()==null){
			flag = false;
			model.addAttribute("ERR_name", "主题名称不能为空");
		}else{
			BigDecimal i = topicService.checkTopicName(map);
			BigDecimal j = new BigDecimal(0);
			if( !i .equals(j)){
				flag = false;
				model.addAttribute("ERR_name", "主题名称不能重复");
			}else{
				//int len = ValidateUtils.length(topic.getName());
				if(topic.getName().length()>10||topic.getName().length()<4){
					flag = false;
					model.addAttribute("ERR_name", "主题名称由4-10个字符组成");
				}
			}
		}
		if(parkId.equals(null) ||parkId.equals("") ){
			flag = false;
			model.addAttribute("ERR_park", "所属版块不能为空");
		}
		if(flag == false){
			User user = (User) request.getSession().getAttribute("loginUser");
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			Map<String,Object> parkMap = new HashMap<String,Object>();
			if(!i.equals(new BigDecimal(1))){
				parkMap.put("userId", user.getId());
			}
			List<Park> parks = parkService.selectParkListByUser(parkMap);
			model.addAttribute("parks", parks);
			if(!(parkId.equals(null)||parkId.equals(""))){
				Park park = parkService.selectByPrimaryKey(parkId);
				topic.setPark(park);
			}
			model.addAttribute("topic", topic);
			url="iss/forum/topic/add";
		}else{
			Park park = parkService.selectByPrimaryKey(parkId);
			topic.setPark(park);
			User user = (User)request.getSession().getAttribute("loginUser");
			topic.setUser(user);
			Timestamp ts = new Timestamp(new Date().getTime());
			topic.setCreatedAt(ts);
			Timestamp ts1 = new Timestamp(new Date().getTime());
			topic.setUpdatedAt(ts1);
			topicService.insertSelective(topic);
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
		User user = (User) request.getSession().getAttribute("loginUser");
		if(null != user && "4".equals(user.getTypeName())){
			Topic p = topicService.selectByPrimaryKey(id);
			model.addAttribute("topic", p);
			model.addAttribute("parkId", p.getPark().getId());
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			Map<String,Object> map = new HashMap<String,Object>();
			if(!i.equals(new BigDecimal(1))){
				map.put("userId", user.getId());
			}
			List<Park> parks = parkService.selectParkListByUser(map);
			model.addAttribute("parks", parks);
			return "iss/forum/topic/edit";
		}
		return "";
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
	public String update(Topic topic,HttpServletRequest request, Model model){
		Boolean flag = true;
		String url = "";
		String parkId = request.getParameter("parkId");
		String topicId = request.getParameter("topicId");
		String name = request.getParameter("name");
		if(name.trim().equals("")||name.trim()==null){
			flag = false;
			model.addAttribute("ERR_name", "主题名称不能为空");
		}else{
			String oldTopicName = topicService.selectByPrimaryKey(topicId).getName();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name", topic.getName());
			map.put("parkId", parkId);
			BigDecimal i = topicService.checkTopicName(map);
			BigDecimal j = new BigDecimal(0);
			if( !oldTopicName.equals(topic.getName())&&!i .equals(j)){
				flag = false;
				model.addAttribute("ERR_name", "主题名称不能重复");
			}else{
				//int len = ValidateUtils.length(topic.getName());
				if(topic.getName().length()>10||topic.getName().length()<4){
					flag = false;
					model.addAttribute("ERR_name", "主题名称由4-10个字符组成");
				}
			}
		}
		if(parkId.equals(null) ||parkId.equals("") ){
			flag = false;
			model.addAttribute("ERR_park", "所属版块不能为空");
		}
		if(flag == false){
			Topic p = topicService.selectByPrimaryKey(topicId);
			//校验回显
			p.setName(topic.getName());
			p.setContent(topic.getContent());
			if(!parkId.equals(null) && !parkId.equals("") ){
				Park park = parkService.selectByPrimaryKey(parkId);
				p.setPark(park);
			}
			model.addAttribute("topic", p);
			model.addAttribute("parkId", parkId);
			User user = (User) request.getSession().getAttribute("loginUser");
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			Map<String,Object> map = new HashMap<String,Object>();
			if(!i.equals(new BigDecimal(1))){
				map.put("userId", user.getId());
			}
			List<Park> parks = parkService.selectParkListByUser(map);
			model.addAttribute("parks", parks);
			url="iss/forum/topic/edit";
		}else{
			Park park = parkService.selectByPrimaryKey(parkId);
			topic.setPark(park);
			Timestamp ts = new Timestamp(new Date().getTime());
			topic.setUpdatedAt(ts);	
			if(topic.getContent()==null){
				topic.setContent("");
			}
			topic.setId(topicId);
			topicService.updateByPrimaryKeySelective(topic);
			url="redirect:getlist.html";
		}
		return url;
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
	public String delete(@CurrentUser User user,String id){
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
			String[] ids=id.split(",");
			for (String str : ids) {
				topicService.deleteByPrimaryKey(str);
				List<Post> posts = postService.selectByTopicID(str);
				for (Post post : posts) {
					postService.deleteByPrimaryKey(post.getId());
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("postId", post.getId());
					List<Reply> replies = replyService.selectByPostID(map);
					for (Reply reply : replies) {
						replyService.deleteByPrimaryKey(reply.getId());
					}
				}
			}		
			return "redirect:getlist.html";
		}
		return "";
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
	public void getListForSelect(HttpServletResponse response,String parkId) {
		List<Topic> topics = topicService.selectByParkID(parkId);
		super.writeJson(response, topics);
	}
	
	/**
	 * 
	* @Title: backTopic
	* @author ZhaoBo
	* @date 2016-11-29 下午2:43:12  
	* @Description: 返回到主题列表 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backTopic")
	public String backTopic(){
		return "redirect:getlist.html";
	}
	
	/**
	 * 
	* @Title: queryParkIdByTopicId
	* @author ZhaoBo
	* @date 2016-12-4 下午2:30:51  
	* @Description: 查找parkID 
	* @param @return      
	* @return Topic
	 */
	@RequestMapping("/queryParkIdByTopicId")
	@ResponseBody
	public Topic queryParkIdByTopicId(HttpServletRequest request){
		return topicService.selectByPrimaryKey(request.getParameter("id"));
	}
	
	/**
	 * 
	* @Title: viewPost
	* @author ZhaoBo
	* @date 2016-12-7 下午2:12:04  
	* @Description: 查看主题下的帖子 
	* @param @param request
	* @param @param page
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewPost")
	public String viewPost(HttpServletRequest request,Integer page,Model model){
		Map<String,Object> map = new HashMap<>();
		String topicId = request.getParameter("topicId");
		String name = request.getParameter("name");
		map.put("topicId", topicId);
		if(name != null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Post> list = postService.selectByTopicIdAndName(map);
		model.addAttribute("list", new PageInfo<Post>(list));
		model.addAttribute("name", name);
		model.addAttribute("topicId", topicId);
		Topic topic = topicService.selectByPrimaryKey(topicId);
		model.addAttribute("topic", topic);
		return "iss/forum/topic/topic_post";
	}
	
}
