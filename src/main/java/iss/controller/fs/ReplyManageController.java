/**
 * 
 */
package iss.controller.fs;

import iss.model.fs.Post;
import iss.model.fs.Reply;
import iss.service.fs.PostService;
import iss.service.fs.ReplyService;

import java.io.IOException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.service.bms.RoleServiceI;
import ses.service.bms.StationMessageService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



/**
* @Title:ReplyManageController 
* @Description: 回复管理控制类
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
	@Autowired
	private RoleServiceI roleService;
	@Autowired
	private StationMessageService stationMessageService;
	
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
		//如果是管理员 就获取所有帖子的回复，版主获取自己负责的版块下的帖子的回复
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
		/*String contentHtml = p.getContent();
        String content = contentHtml.replaceAll("<[^>]*>", "");*/
		model.addAttribute("reply", p);
	/*	model.addAttribute("content", content);*/
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
	public void save(HttpServletRequest request,HttpServletResponse response,Reply reply)throws IOException{	
		try {
            String msg = "";
            int count = 0;
            if ("".equals(reply.getContent()) || reply.getContent() == null) {
                msg += "请填写回复内容";
                count ++;
            }
            //校验失败
            if (count > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
            //检验成功
            if (count == 0) {
            	String postId = request.getParameter("postId");
    			String content = request.getParameter("content");
    			String replyId =request.getParameter("replyId");
    			Post post = postService.selectByPrimaryKey(postId);
    			Timestamp tsp = new Timestamp(new Date().getTime());
    			Timestamp tsu = new Timestamp(new Date().getTime());
    			User user = (User) request.getSession().getAttribute("loginUser");
    			reply.setUser(user);
    			//根据replyId来判断 是回复帖子，还是回复回复
    			if(replyId ==null || replyId == ""){			
    				BigDecimal replyCount =post.getReplycount();
    				BigDecimal haha = new BigDecimal(1);
    				post.setReplycount(replyCount.add(haha));
    				post.setLastReplyer(user);
    				post.setLastReplyedAt(tsp);
    				postService.updateByPrimaryKeySelective(post);			
    			}else{
    				Reply supReply = replyService.selectByPrimaryKey(replyId);
    				reply.setReply(supReply);		
    			}		
    			reply.setPost(post);
    			reply.setContent(content);
    			reply.setPublishedAt(tsp);
    			reply.setUpdatedAt(tsu);	
    			reply.setIsRead(0);
    			replyService.insertSelective(reply);
//    			StationMessage stationMessage = new StationMessage();
//    			String id = UUID.randomUUID().toString().toUpperCase().replace("-", "");
//    			stationMessage.setId(id);
//    			stationMessage.setCreatedAt(new Date());
//    			stationMessage.setIsDeleted((short)0);
//    			stationMessage.setName("【论坛】"+post.getName()+"有新的回复");
//    			stationMessage.setIsFinish((short)0);
//    			stationMessage.setUrl("post/getIndexDetail.html?postId="+postId);
//    			stationMessageService.insertStationMessage(stationMessage);
            	msg += "回复成功";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter()
                        .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                + "\"}");
            }
					
			response.getWriter().flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally{
	        response.getWriter().close();
	    }
	}
	
	/**
	 * 
	* @Title: saveReply
	* @author ZhaoBo
	* @date 2016-11-30 下午4:46:08  
	* @Description: 保存回复的回复信息 
	* @param @param request
	* @param @param response
	* @param @param reply
	* @param @throws IOException      
	* @return void
	 */
	@RequestMapping("/saveReply")
	@ResponseBody
	public void saveReply(HttpServletRequest request,HttpServletResponse response,Reply reply)throws IOException{	
		try {
            String msg = "";
            int count = 0;
            if ("".equals(reply.getContent()) || reply.getContent() == null) {
                msg += "请填写回复内容";
                count ++;
            }
            //校验失败
            if (count > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
            //检验成功
            if (count == 0) {
            	String postId = request.getParameter("postId");
    			String replyId =request.getParameter("replyId");
    			Post post = postService.selectByPrimaryKey(postId);
    			Timestamp tsp = new Timestamp(new Date().getTime());
    			Timestamp tsu = new Timestamp(new Date().getTime());
    			User user = (User) request.getSession().getAttribute("loginUser");
    			reply.setUser(user);
    			//根据replyId来判断 是回复帖子，还是回复回复
    			if(replyId ==null || replyId == ""){			
    				BigDecimal replyCount =post.getReplycount();
    				BigDecimal haha = new BigDecimal(1);
    				post.setReplycount(replyCount.add(haha));
    				post.setLastReplyer(user);
    				post.setLastReplyedAt(tsp);
    				postService.updateByPrimaryKeySelective(post);			
    			}else{
    				Reply supReply = replyService.selectByPrimaryKey(replyId);
    				reply.setReply(supReply);		
    			}		
    			reply.setPost(post);
    			reply.setContent(reply.getContent());
    			reply.setPublishedAt(tsp);
    			reply.setUpdatedAt(tsu);	
    			reply.setIsRead(0);
    			replyService.insertSelective(reply);
            	msg += "回复成功";
                response.setContentType("text/html;charset=utf-8");
                response.getWriter()
                        .print("{\"success\": " + true + ", \"msg\": \"" + msg
                                + "\"}");
            }
					
			response.getWriter().flush();
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally{
	        response.getWriter().close();
	    }
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
	public String update(@Valid Reply reply,BindingResult result,HttpServletRequest request, Model model){
		Boolean flag = true;
		String url = "";
		String id = request.getParameter("replyId");
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			flag = false;
		}
		if(flag == false){
			Reply p = replyService.selectByPrimaryKey(id);
			model.addAttribute("reply", p);
			url = "iss/forum/reply/edit";
		}else{
			Timestamp ts = new Timestamp(new Date().getTime());
			reply.setUpdatedAt(ts);		
			reply.setId(id);
			replyService.updateByPrimaryKeySelective(reply);
			url="redirect:getlist.html";
		}
		
		return url;
		
	}
	
	/**   
	* @Title: delete
	* @author Peng Zhongjun
	* @date 2016-8-10 下午20:03:41 
	* @Description: 删除回复信息
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(String id){
		String[] ids=id.split(",");
		for (String str : ids) {
			//回复量减1
			Post post = postService.selectByPrimaryKey(replyService.selectByPrimaryKey(str).getPost().getId());
			BigDecimal j = new BigDecimal(1);
			BigDecimal replycount = post.getReplycount().subtract(j);
			post.setReplycount(replycount);
			postService.updateByPrimaryKeySelective(post);
			replyService.deleteByPrimaryKey(str);
		}
	}
	
	/**
	 * 
	* @Title: backReply
	* @author ZhaoBo
	* @date 2016-11-29 下午3:07:33  
	* @Description: 返回到回复列表 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/backReply")
	public String backReply(){
		return "redirect:getlist.html";
	}
}
