/**
 * 
 */
package iss.controller.fs;

import iss.model.fs.Reply;
import iss.model.fs.Topic;
import iss.service.fs.ReplyService;

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
	public String getList(Model model,Reply reply,Integer page){
		List<Reply> list = replyService.queryByList(reply,page==null?1:page);
		model.addAttribute("list", new PageInfo<Reply>(list));
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
	* @Title: add
	* @author Peng Zhongjun
	* @date 2016-8-10 下午19:58:43   
	* @Description: 跳转新增编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		return "iss/forum/reply/add";
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
	public String save(HttpServletRequest request,Reply reply){
		replyService.insertSelective(reply);
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
