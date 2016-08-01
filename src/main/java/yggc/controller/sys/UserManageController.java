package yggc.controller.sys;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import yggc.model.User;
import yggc.service.UserServiceI;

/**
* <p>Title:UserManageController </p>
* <p>Description:用户管理控制类 </p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-18上午9:36:56
*/
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserManageController{

	@Autowired
	private UserServiceI userService;
	
	private static Logger logger = Logger.getLogger(LoginController.class); 
	
	/**   
	* @Title: getAll
	* @author yyyml
	* @date 2016-7-27 下午4:47:32  
	* @Description: 获取用户列表 
	* @param @param model
	* @param @return      
	* @return String     
	*/
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<User> users=userService.getAll();
		model.addAttribute("list", users);
		logger.info(JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss"));
		return "user/list";
	}
	
	/**   
	* @Title: add
	* @author yyyml
	* @date 2016-7-27 下午4:48:23  
	* @Description: 跳转新增编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/add")
	public String add(HttpServletRequest request){
		return "user/add";
	}
	
	/**   
	* @Title: save
	* @author yyyml
	* @date 2016-7-27 下午4:48:41  
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,User user){
		user.setIsDeleted(0);
		user.setCreatedAt(new Date());
		User currUser=(User) request.getSession().getAttribute("loginUser");
		if(currUser!=null){
			user.setCreater(currUser);
		}else{
			
		}
		userService.save(user);
		return "redirect:getAll.do";
	}
	
	/**   
	* @Title: edit
	* @author yyyml
	* @date 2016-7-27 下午4:49:49  
	* @Description: 跳转修改编辑页面
	* @param @param u
	* @param @param model
	* @return String     
	*/
	@RequestMapping("/edit")
	public String edit(User u,Model model){
		User user=userService.getUserById(u);
		logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
		logger.info(JSON.toJSONStringWithDateFormat(user.getCreater(), "yyyy-MM-dd HH:mm:ss"));
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	/**   
	* @Title: update
	* @author yyyml
	* @date 2016-7-27 下午4:50:08  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,User user){
		User u=userService.getUserById(user);
		u.setLoginName(user.getLoginName());
		u.setRelName(user.getRelName());
		u.setUpdatedAt(new Date());
		userService.update(u);
		return "redirect:getAll.do";
	}
	
	/**   
	* @Title: delete
	* @author yyyml
	* @date 2016-7-27 下午4:50:32  
	* @Description: 删除用户信息，逻辑删除
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete(Integer id){
		userService.deleteByLogic(id);
		return "redirect:getAll.do";
	}
	
	/**   
	* @Title: view
	* @author yyyml
	* @date 2016-7-27 下午4:50:50  
	* @Description: 显示用户详细信息页面
	* @param @param model
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/view")
	public String view(Model model,User user){
		User u=userService.getUserById(user);
		model.addAttribute("user", u);
		return "user/view";
	}
}
