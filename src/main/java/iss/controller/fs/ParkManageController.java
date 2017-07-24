package iss.controller.fs;

import iss.model.fs.Park;
import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ParkService;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;
import iss.service.fs.TopicService;
import iss.service.ps.ArticleService;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;


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
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private ArticleService articleService;

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
	public String getParkList(@CurrentUser User user,HttpServletRequest request,Model model, Park park,Integer page) {
		//声明标识是否是资源服务中心
        String authType = null;
        if(null != user && "4".equals(user.getTypeName())){
            //判断是否 是资源服务中心 
            authType = "4";
			Map<String,Object> map = new HashMap<String, Object>();
			String parkNameForSerach = request.getParameter("parkNameForSerach");
			String parkContentForSerach = request.getParameter("parkContentForSerach");	
			HashMap<String,Object> roleMap = new HashMap<String,Object>();
			roleMap.put("userId", user.getId());
			roleMap.put("code", "ADMIN_R");
			BigDecimal i = roleService.checkRolesByUserId(roleMap);
			if(page == null){
				page=1;
			}
			if(parkNameForSerach!=null && parkNameForSerach!=""){
				map.put("parkNameForSerach", parkNameForSerach);
			}
			if(parkContentForSerach !=null && parkContentForSerach!=""){
				map.put("parkContentForSerach", parkContentForSerach);
			}
			if(!i.equals(new BigDecimal(1))){
				map.put("userId", user.getId());
			}
			map.put("page",page.toString());
			PropertiesUtil config = new PropertiesUtil("config.properties");
			PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
			List<Park> parklist = parkService.queryByList(map);
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
			model.addAttribute("parkNameForSerach", parkNameForSerach);
			model.addAttribute("parkContentForSerach", parkContentForSerach);
			model.addAttribute("admin", i);
			model.addAttribute("authType", authType);
        }
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
	public String view(@CurrentUser User user,Model model, String id) {
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
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
	public String add(@CurrentUser User user,Model model, HttpServletRequest request) {
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
			return "iss/forum/park/add";
		}
		return "";
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
	public String save(Park park,HttpServletRequest request, Model model) {
		Boolean flag = true;
		String url = "";
		String name = request.getParameter("name");
		if(name.trim().equals("")||name.trim()==null){
			flag = false;
			model.addAttribute("ERR_name", "版块名称不能为空");
		}else{
			BigDecimal i = parkService.checkParkName(park.getName());	
			BigDecimal j = new BigDecimal(0);
			if(i.compareTo(j) != 0){
				flag = false;
				model.addAttribute("ERR_name", "版块名称不能重复");
			}else{
				//int len = ValidateUtils.length(park.getName());
				if(park.getName().length()>10||park.getName().length()<4){
					flag = false;
					model.addAttribute("ERR_name", "版块名称由4-10个字符组成");
				}
			}
		}
		if(park.getIsHot() == null ||park.getIsHot().equals("")){
			park.setIsHot(0);
		}
		if(park.getIsHot() == 1){
			int k = parkService.queryHotParks().size();	
			if(!(k<4)){
				flag = false;
				model.addAttribute("ERR_isHot", "热门版块不能超过4个");	
			}
		}
		String parker = request.getParameter("parker");
		if(parker==null||parker.equals("")){
			flag = false;
			model.addAttribute("ERR_parker", "版主不能为空");	
		}
		if(flag == false){
			String userId = request.getParameter("userId");
			if(!(userId.equals(null) || userId.equals(""))){
				 User user = userService.getUserById(userId);			
				park.setUser(user);
			}
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
	public String edit(@CurrentUser User user,String id, Model model) {
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
			Park p = parkService.selectByPrimaryKey(id);
			model.addAttribute("park", p);
			return "iss/forum/park/edit";
		}
		return "";
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
	public String update(Park park,HttpServletRequest request, Model model) {	
		String parkId = request.getParameter("parkId");
		Boolean flag = true;
		String url = "";
		String name = request.getParameter("name");
		if(name.trim().equals("")||name.trim()==null){
			flag = false;
			model.addAttribute("ERR_name", "版块名称不能为空");	
		}else{
			BigDecimal i = parkService.checkParkName(park.getName());
			BigDecimal j = new BigDecimal(0);
			String oldParkName =request.getParameter("oldParkName");
			if(!oldParkName.equals(park.getName())&& i.compareTo(j) != 0){			
				flag = false;
				model.addAttribute("ERR_name", "版块名称不能重复");			
			}else{
				//int len = ValidateUtils.length(park.getName());
				if(park.getName().length()>10||park.getName().length()<4){
					flag = false;
					model.addAttribute("ERR_name", "版块名称由4-10个字符组成");
				}
			}
		}	
		String parker = request.getParameter("parker");
		if(parker==null||parker.equals("")){
			flag = false;
			model.addAttribute("ERR_parker", "版主不能为空");	
		}
		if(park.getIsHot() ==null ||park.getIsHot().equals("")){
			park.setIsHot(0);
		}
		Park p = parkService.selectByPrimaryKey(parkId);
		if(park.getIsHot() == 1){
			int k = parkService.queryHotParks().size();				
			if(p.getIsHot() == 0&&!(k < 4)){
				flag = false;
				model.addAttribute("ERR_isHot", "热门版块不能超过4个");	
			}
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
			if(park.getContent()==null){
				park.setContent("");
			}
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
	public String delete(@CurrentUser User user,String ids) {
		if(null != user && "4".equals(user.getTypeName())){
			//判断是否 是资源服务中心 
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
		return "";
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
			if(park.getIsHot() != null&&!(park.getIsHot().equals(""))){
				if(park.getIsHot() == 1){
					hotParks.add(park);
				}
			}
		}

		//去除重复的
		for (Park park : hotParks) {
			parklist.remove(park);
		}
		//如果热门版块不满4 根据版块下的回复量补充
		if(hotParks.size() < 4){
			int i = 4-hotParks.size();
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
			if(parklist.size()<i){
				if(parklist.size()!=0){
					for(int j=0;j<parklist.size();j++){
						hotParks.add(parklist.get(j));
					}
				}
			}else{
				for(int j=0;j<i;j++){
					hotParks.add(parklist.get(j));
				}
			}
		}
		model.addAttribute("hotParks", hotParks);
		List<Park> parklist2 = parkService.getAll(null);
		for (Park park : parklist2) {
			List<Post> posts = postService.selectByParkID(park.getId());
			park.setPosts(posts);
		}
		Map<String, Object> indexMapper = articleService.topNews();
		model.addAttribute("list", parklist2);
		model.addAttribute("indexMapper", indexMapper);
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
	@RequestMapping(value="/getUserForSelect" )	
	public void getUserForSelect(HttpServletResponse response) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		List<DictionaryData> list = DictionaryDataUtil.find(16);
		String kind = null;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getCode().equals("PURCHASE_BACK")){
				kind = list.get(i).getId();
				break;
			}
		}
		map.put("kind", kind);
		List<User> users = userService.queryParkManagers(map);		
		super.writeJson(response, users);
	}
	
	/**
	 * 
	* @Title: backPark
	* @author ZhaoBo
	* @date 2016-11-29 下午2:29:50  
	* @Description: 返回版块列表 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backPark")
	public String backPark(){
		return "redirect:getlist.html";
	}
	
	/**
	 * 
	* @Title: viewTopic
	* @author ZhaoBo
	* @date 2016-12-7 下午12:32:36  
	* @Description: 查看板块下面所有的主题 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewTopic")
	public String viewTopic(HttpServletRequest request,Integer page,Model model){
		Map<String,Object> map = new HashMap<>();
		String parkId = request.getParameter("parkId");
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		map.put("parkId", parkId);
		if(name != null && !name.equals("")){
			map.put("name", "%"+name+"%");
		}
		if(content != null && !content.equals("")){
			map.put("content", "%"+content+"%");
		}
		if(page==null){
			page = 1;
		}
		map.put("page", page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Topic> list = topicService.selectByParkIdAndName(map);
		for (Topic topic2 : list) {
			Post post = new Post();
			post.setTopic(topic2);
			BigDecimal postcount = postService.queryByCount(post);
			topic2.setPostcount(postcount);
			BigDecimal replycount = replyService.queryCountByTopicId(topic2.getId());
			topic2.setReplycount(replycount);
		}
		model.addAttribute("list", new PageInfo<Topic>(list));
		model.addAttribute("name", name);
		model.addAttribute("parkId", parkId);
		model.addAttribute("content", content);
		Park park = parkService.selectByPrimaryKey(parkId);
		model.addAttribute("park", park);
		return "iss/forum/park/park_topic";
	}
}
