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

import yggc.model.iss.fs.Park;
import yggc.model.iss.fs.Post;
import yggc.service.iss.fs.ParkService;
import yggc.service.iss.fs.PostService;

/**
 * Title:ParkManageController 
 * Description:版块管理控制类  
 * Company: yggc 
 *  @author junjunjun1993 
 *  @date 2016-8-10下午5:03:01
 */
@Controller
@Scope("prototype")
@RequestMapping("/park")
public class ParkManageController {

	@Autowired
	private ParkService parkService;
	
	@Autowired
	private PostService postService;

	/**
	 * @Title: getParkList
	 * @author junjunjun1993
	 * @date 2016-8-10 下午19:47:32
	 * @Description: 获取版块列表跳转到后台管理
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getlist")
	public String getParkList(Model model, Park park) {
		List<Park> parklist = parkService.queryByList(park);
		model.addAttribute("list", parklist);
		return "forum/park/parklist";
	}


	
	
	/**
	 * @Title: view
	 * @author junjunjun1993
	 * @date 2016-8-10 下午19:55:32
	 * @Description: 显示版块详细信息页面
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/view")
	public String view(Model model, Integer id) {
		Park p = parkService.selectByPrimaryKey(id);
		model.addAttribute("park", p);
		return "forum/park/view";
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
	public String add(HttpServletRequest request) {
		return "forum/park/add";
	}

	/**
	 * @Title: save
	 * @author junjunjun1993
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 保存新增信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Park park) {
		parkService.insertSelective(park);
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
	public String edit(Integer id, Model model) {
		Park p = parkService.selectByPrimaryKey(id);
		model.addAttribute("park", p);
		return "forum/park/edit";
	}

	/**
	 * @Title: update
	 * @author junjunjun1993
	 * @date 2016-8-10 下午20:03:41
	 * @Description: 更新修改信息
	 * @param @param request
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, Park park) {
		parkService.updateByPrimaryKeySelective(park);
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
	public String delete(Integer id) {
		parkService.deleteByPrimaryKey(id);
		return "redirect:getlist.do";
	}
	/**
	 * @Title: getPark
	 * @author junjunjun1993
	 * @date 2016-8-24 下午13:41:32
	 * @Description: 获取版块列表跳转到前台
	 * @param @param model
	 * @param @param park
	 * @return String
	 */
	@RequestMapping("/getIndex")
	public String getPostIndex(Model model, Park park) {
		List<Park> parklist = parkService.queryByList(park);

		for (Park park2 : parklist) {
			List<Post> postlist = postService.selectByParkID(park2.getId());
			park2.setPosts(postlist);
		}
		model.addAttribute("list", parklist);
		
		return "forum/forumIndex2";
	}
}
