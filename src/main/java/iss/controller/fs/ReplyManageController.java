/**
 * 
 */
package iss.controller.fs;

import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;

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
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
* @Title:ReplyManageController 
* @Description: 
* @author Peng Zhongjun
* @date 2016-9-7下午6:20:38
 */
@Controller
@Scope("prototype")
@RequestMapping("/reply")
public class ReplyManageController {
	@Autowired
	private ReplyService replyService;
	@Autowired
	private PostService postService;
	
	/**   
	* @Title: getList
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取回复列表 
	* @param @param model
	* @param @param  reply   
	* @return String     
	*/
	@RequestMapping("/getlist")
	public String getList(HttpServletRequest request,Model model,Integer page)throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		String replyCon = request.getParameter("replyCon");
		User user = (User)request.getSession().getAttribute("loginUser");
		String userId = user.getId();
		if(page==null){
			page=1;
		}
		if(replyCon !=null && replyCon!=""){
			map.put("replyCon", replyCon);
		}
		map.put("userId", userId);
		map.put("page",page.toString());
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Reply> list = replyService.queryByList(map);
		model.addAttribute("list", new PageInfo<Reply>(list));
		model.addAttribute("replyCon", replyCon);
		return "iss/forum/reply/list";
	}
	
	/**   
	* @Title: view
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:55:32  
	* @Description: 显示评论详细信息页面
	* @param @param model
	* @param @param reply
	* @return String     
	*/
	@RequestMapping("/view")
	public String view(Model model,String id){
		Reply p = replyService.selectByPrimaryKey(id);
		model.addAttribute("reply", p);
		return "iss/forum/reply/view";
	}
		
	/**   
	* @Title: save
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41   
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param reply
	* @return String     
	*/
	@RequestMapping("/save")
	@ResponseBody
	public void save(HttpServletRequest request,Reply reply){			
		String postId = request.getParameter("postId");
		String content = request.getParameter("content");
		String replyId =request.getParameter("replyId");
		Post post = postService.selectByPrimaryKey(postId);
		if(replyId ==null || replyId == ""){			
			BigDecimal replyCount =post.getReplycount();
			BigDecimal haha = new BigDecimal(1);
			post.setReplycount(replyCount.add(haha));
			postService.updateByPrimaryKeySelective(post);			
		}else{
			Reply supReply = replyService.selectByPrimaryKey(replyId);
			reply.setReply(supReply);		
		}		
		reply.setPost(post);
		reply.setContent(content);
		User user = (User) request.getSession().getAttribute("loginUser");
		reply.setUser(user);
		Timestamp tsp = new Timestamp(new Date().getTime());
		Timestamp tsu = new Timestamp(new Date().getTime());
		reply.setPublishedAt(tsp);
		reply.setUpdatedAt(tsu);
		replyService.insertSelective(reply);
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
		Reply p = replyService.selectByPrimaryKey(id);
		model.addAttribute("reply", p);
		return "iss/forum/reply/edit";
	}
	
	/**   
	* @Title: update
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param reply
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,Reply reply){
		Timestamp ts = new Timestamp(new Date().getTime());
		reply.setUpdatedAt(ts);
		String id = request.getParameter("replyId");
		reply.setId(id);
		replyService.updateByPrimaryKeySelective(reply);
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
			replyService.deleteByPrimaryKey(str);
		}
		return "redirect:getlist.html";
	}
}
