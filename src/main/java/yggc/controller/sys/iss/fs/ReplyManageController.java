/**
 * 
 */
package yggc.controller.sys.iss.fs;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.iss.fs.Reply;
import yggc.service.iss.fs.ReplyService;

/**
 * <p>Title:ReplyManageController </p>
 * <p>Description：回复管理控制类  </p>
 * <p>Company: yggc </p> 
 * @author junjunjun1993
 * @date 2016-8-10下午5:03:01
 */
@Controller
@Scope("prototype")
@RequestMapping("/reply")
public class ReplyManageController {
	@Autowired
	private ReplyService replyService;
	
	/**   
	* @Title: getList
	* @author junjunjun1993
	* @date 2016-8-10 下午19:47:32  
	* @Description: 获取回复列表 
	* @param @param model
	* @param @param  reply   
	* @return String     
	*/
	@RequestMapping("/getlist")
	public String getList(Model model,Reply reply){
		List<Reply> list = replyService.queryByList(reply);
		model.addAttribute("list", list);
		return "forum/reply/list";
	}
	
	/**   
	* @Title: view
	* @author junjunjun1993
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
		return "forum/reply/view";
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
	public String add(HttpServletRequest request){
		return "forum/reply/add";
	}
	
	/**   
	* @Title: save
	* @author junjunjun1993
	* @date 2016-8-10 下午20:03:41   
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param reply
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Reply reply){
		replyService.insertSelective(reply);
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
		Reply p = replyService.selectByPrimaryKey(id);
		model.addAttribute("reply", p);
		return "forum/reply/edit";
	}
	
	/**   
	* @Title: update
	* @author junjunjun1993
	* @date 2016-8-10 下午20:03:41  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param reply
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,Reply reply){
		replyService.updateByPrimaryKeySelective(reply);
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
		replyService.deleteByPrimaryKey(id);
		return "redirect:getlist.do";
	}
}
