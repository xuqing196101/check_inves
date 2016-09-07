package yggc.controller.sys.bms;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import yggc.model.bms.Role;
import yggc.model.bms.User;
import yggc.model.bms.Userrole;
import yggc.service.bms.RoleServiceI;
import yggc.service.bms.UserServiceI;
import yggc.util.Encrypt;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
* <p>Title:UserManageController </p>
* <p>Description:用户管理控制类 </p>
* @author Ye MaoLin
* @date 2016-7-18上午9:36:56
*/
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserManageController{

	@Autowired
	private UserServiceI userService;
	
	@Autowired
	private RoleServiceI roleService;
	
	private Logger logger = Logger.getLogger(LoginController.class); 
	
	/**   
	* @Title: getAll
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:47:32  
	* @Description: 获取用户列表 
	* @param @param model
	* @param @return      
	* @return String     
	*/
	@RequestMapping("/getAll")
	public String getAll(Model model,Integer page){
		List<User> users=userService.selectUserRole(null, page==null?1:page);
		model.addAttribute("list", new PageInfo<User>(users));
		logger.info(JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss"));
		return "user/list";
	}
	/**   
	* @Title: add
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:48:23  
	* @Description: 跳转新增编辑页面
	* @param @param request
	* @return String     
	*/
	@RequestMapping("/add")
	public String add(HttpServletRequest request,Model model){
		List<Role> roles=roleService.getAll(null);
		model.addAttribute("roles",roles);
 		return "user/add";
	}
	
	/**   
	* @Title: save
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:48:41  
	* @Description: 保存新增信息 
	* @param @param request
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(HttpServletRequest request,User user,String roleId){
		user.setIsDeleted(0);
		user.setCreatedAt(new Date());
		User currUser=(User) request.getSession().getAttribute("loginUser");
		if(currUser!=null){
			user.setCreater(currUser);
		}else{
			
		}
		String psw=Encrypt.md5AndSha(user.getLoginName()+user.getPassword());
		user.setPassword(psw);
		userService.save(user);
		
		String[] roleIds=roleId.split(",");
		for (int i = 0; i < roleIds.length; i++) {
			Userrole userrole=new Userrole();
			Role role=roleService.get(roleIds[i]);
			userrole.setRoleId(role);
			userrole.setUserId(user);
			userService.saveRelativity(userrole);
		}
		return "redirect:getAll.html";
	}
	
	/**   
	* @Title: edit
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:49:49  
	* @Description: 跳转修改编辑页面
	* @param @param u
	* @param @param model
	* @return String     
	*/
	@RequestMapping("/edit")
	public String edit(User u,Model model){
		List<User> users=userService.selectUserRole(u, null);
		User user=users.get(0);
		logger.info(JSON.toJSONStringWithDateFormat(user, "yyyy-MM-dd HH:mm:ss"));
		logger.info(JSON.toJSONStringWithDateFormat(user.getCreater(), "yyyy-MM-dd HH:mm:ss"));
		List<Role> roles=roleService.getAll(null);
		String roleId="";
		String roleName="";
		List<Role> list=user.getRoles();
		for (int i = 0; i < list.size(); i++) {
			if(i+1==list.size()){
				roleId+=list.get(i).getId();
				roleName+=list.get(i).getName();
			}else {
				roleId+=list.get(i).getId()+",";
				roleName+=list.get(i).getName()+",";
			}
		}
		model.addAttribute("roleName",roleName);
		model.addAttribute("roleId",roleId);
		model.addAttribute("roles",roles);
		model.addAttribute("user", user);
		return "user/edit";
	}
	
	/**   
	* @Title: update
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:50:08  
	* @Description: 更新修改信息
	* @param @param request
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(HttpServletRequest request,User user,String roleId){
		List<User> users=userService.selectUserRole(user, null);
		User u=users.get(0);
		//先删除之前的与角色的关联关系
		List<Role> oldRole=u.getRoles();
		for (Role role : oldRole) {
			Userrole userrole=new Userrole();
			userrole.setUserId(u);
			userrole.setRoleId(role);
			roleService.deleteRoelUser(userrole);
		}
		
		u.setLoginName(user.getLoginName());
		u.setRelName(user.getRelName());
		u.setUpdatedAt(new Date());
		userService.update(u);
		
		String[] roleIds=roleId.split(",");
		for (int i = 0; i < roleIds.length; i++) {
			Userrole userrole=new Userrole();
			Role role=roleService.get(roleIds[i]);
			userrole.setRoleId(role);
			userrole.setUserId(user);
			userService.saveRelativity(userrole);
		}
		
		return "redirect:getAll.html";
	}
	
	/**   
	* @Title: delete
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:50:32  
	* @Description: 批量删除用户信息，逻辑删除
	* @param @param id
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete(String ids){
		String[] id=ids.split(",");
		for (String str : id) {
			userService.deleteByLogic(str);
		}
		return "redirect:getAll.html";
	}
	
	/**   
	* @Title: view
	* @author Ye MaoLin
	* @date 2016-7-27 下午4:50:50  
	* @Description: 显示用户详细信息页面
	* @param @param model
	* @param @param user
	* @return String     
	*/
	@RequestMapping("/view")
	public String view(Model model,User user){
		List<User> ulist=userService.selectUserRole(user, null);
		model.addAttribute("user", ulist.get(0));
		return "user/view";
	}
		
	public static void main(String[] args) {
		 for (int i = 0; i < 15; i++) {
			 UUID uuid = UUID.randomUUID();
			 System.out.println(uuid);
		} 
	}
}
