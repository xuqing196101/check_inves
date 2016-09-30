package ses.controller.sys.bms;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.User;
import ses.model.bms.UserPreMenu;
import ses.model.bms.Userrole;
import ses.model.oms.Orgnization;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
 * Description: 用户管理控制类 
 * 
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserManageController {

	@Autowired
	private UserServiceI userService;

	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
	
	@Autowired
	private PreMenuServiceI preMenuService;

	private Logger logger = Logger.getLogger(UserManageController.class);

	/**
	 * Description: 查询列表
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param page
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/list")
	public String list(Model model, Integer page, User user) {
		List<User> users = userService.list(user, page == null ? 1 : page);
		model.addAttribute("list", new PageInfo<User>(users));
		logger.info(JSON.toJSONStringWithDateFormat(users,
				"yyyy-MM-dd HH:mm:ss"));
		return "ses/bms/user/list";
	}

	/**
	 * Description: 判断用户名是否存在
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param loginName
	 * @param response
	 * @exception IOException
	 */
	@RequestMapping("/findByLoginName")
	public void findByLoginName(String loginName, HttpServletResponse response) throws IOException {
		try {
			List<User> users = userService.findByLoginName(loginName);
			String msg = "";
			if (users != null && users.size() > 0) {
				msg = "该用户名已存在";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(
						"{\"success\": " + false + ", \"msg\": \"" + msg
								+ "\"}");
			} else {
				msg = "该用户名可用";
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
	 * Description: 跳转新增编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model) {
		List<Role> roles = roleService.find(null);
		model.addAttribute("roles", roles);
		String loginNameTip = (String) request.getSession().getAttribute(
				"userSaveTipMsg_loginName");
		String passwordTip = (String) request.getSession().getAttribute(
				"userSaveTipMsg_password");
		String password2Tip = (String) request.getSession().getAttribute(
				"userSaveTipMsg_password2");
		if (loginNameTip != null && !"".equals(loginNameTip)) {
			model.addAttribute("loginName_msg", loginNameTip);
		}
		if (passwordTip != null && !"".equals(passwordTip)) {
			model.addAttribute("password_msg", passwordTip);
		}
		if (password2Tip != null && !"".equals(password2Tip)) {
			model.addAttribute("password2_msg", password2Tip);
		}
		request.getSession().removeAttribute("userSaveTipMsg_loginName");
		request.getSession().removeAttribute("userSaveTipMsg_password");
		request.getSession().removeAttribute("userSaveTipMsg_password2");
		return "ses/bms/user/add";
	}

	/**
	 * Description: 保存新增信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param model
	 * @param user
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public String save(HttpServletRequest request, Model model, User user,
			String roleId, String orgId) {
		String loginName = user.getLoginName();
		List<User> users = userService.findByLoginName(user.getLoginName());
		String password2 = request.getParameter("password2");
		if (loginName == null || "".equals(loginName)) {
			request.getSession().setAttribute("userSaveTipMsg_loginName",
					"请输入用户名");
			if (user.getPassword() == null || "".equals(user.getPassword())) {
				request.getSession().setAttribute("userSaveTipMsg_password",
						"请输入密码");
			} else if (!user.getPassword().equals(password2)) {
				request.getSession().setAttribute("userSaveTipMsg_password2",
						"两次密码输入不一致");
			}
			return "redirect:add.html";
		} else if (users.size() > 0) {
			request.getSession().setAttribute("userSaveTipMsg_loginName",
					"用户名已存在");
			if (user.getPassword() == null || "".equals(user.getPassword())) {
				request.getSession().setAttribute("userSaveTipMsg_password",
						"请输入密码");
			} else if (!user.getPassword().equals(password2)) {
				request.getSession().setAttribute("userSaveTipMsg_password2",
						"两次密码输入不一致");
			}
			return "redirect:add.html";
		} else if (user.getPassword() == null || "".equals(user.getPassword())) {
			request.getSession().setAttribute("userSaveTipMsg_password",
					"请输入密码");
			return "redirect:add.html";
		} else if (!user.getPassword().equals(password2)) {
			request.getSession().setAttribute("userSaveTipMsg_password2",
					"两次密码输入不一致");
			return "redirect:add.html";
		} else {
			User currUser = (User) request.getSession().getAttribute(
					"loginUser");
			//机构
			if(orgId != null && !"".equals(orgId)){
				HashMap<String, Object> orgMap = new HashMap<String, Object>();
				orgMap.put("id", orgId);
				List<Orgnization> olist = orgnizationService.findOrgnizationList(orgMap);
				user.setOrg(olist.get(0));
			}else{
				user.setOrg(null);
			}
			userService.save(user, currUser);

			if(roleId != null && !"".equals(roleId)){
				String[] roleIds = roleId.split(",");
				for (int i = 0; i < roleIds.length; i++) {
					Userrole userrole = new Userrole();
					Role role = roleService.get(roleIds[i]);
					userrole.setRoleId(role);
					userrole.setUserId(user);
					//保存角色-用户关联信息
					userService.saveRelativity(userrole);
				}
				//保存用户与角色多对应权限的关联id
				List<String> mids = preMenuService.findByRids(roleIds);
				for (String mid : mids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(user);
					userService.saveUserMenu(userPreMenu);
				}
			}
			return "redirect:list.html";
		}
	}
	
	/**
	 * Description: 跳转修改编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param u
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/edit")
	public String edit(User u, Model model) {
		List<User> users = userService.find(u);
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			logger.info(JSON.toJSONStringWithDateFormat(user,
					"yyyy-MM-dd HH:mm:ss"));
			logger.info(JSON.toJSONStringWithDateFormat(user.getUser(),
					"yyyy-MM-dd HH:mm:ss"));
			List<Role> roles = roleService.find(null);
			String roleId = "";
			String roleName = "";
			List<Role> list = user.getRoles();
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 == list.size()) {
					roleId += list.get(i).getId();
					roleName += list.get(i).getName();
				} else {
					roleId += list.get(i).getId() + ",";
					roleName += list.get(i).getName() + ",";
				}
			}
			model.addAttribute("roleName", roleName);
			model.addAttribute("roleId", roleId);
			model.addAttribute("roles", roles);
			model.addAttribute("user", user);
		} else {

		}
		return "ses/bms/user/edit";
	}

	/**
	 * Description: 更新修改信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param request
	 * @param user
	 * @param roleId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/update")
	public String update(HttpServletRequest request, User u, String roleId, String orgId) {

		User temp = new User();
		temp.setId(u.getId());
		// 查询旧数据的关联关系
		List<User> users = userService.find(temp);
		if (users != null && users.size() > 0) {
			User olduser = users.get(0);

			// 先删除之前的与角色的关联关系
			List<Role> oldRole = olduser.getRoles();
			for (Role role : oldRole) {
				Userrole userrole = new Userrole();
				userrole.setUserId(olduser);
				userrole.setRoleId(role);
				roleService.deleteRoelUser(userrole);
			}
			//删除用户之前的与角色下权限菜单的关联关系
			if(oldRole.size() > 0){
				String[] oldrIds = new String[oldRole.size()];
				for (int i = 0; i < oldRole.size(); i++) {
					oldrIds[i] = oldRole.get(i).getId();
				}
				List<String> oldmids = preMenuService.findByRids(oldrIds);
				for (String mid : oldmids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(olduser);
					userService.deleteUserMenu(userPreMenu);
				}
			}
			
			//机构
			if(orgId != null && !"".equals(orgId)){
				HashMap<String, Object> orgMap = new HashMap<String, Object>();
				orgMap.put("id", orgId);
				List<Orgnization> olist = orgnizationService.findOrgnizationList(orgMap);
				u.setOrg(olist.get(0));
			}else{
				u.setOrg(null);
			}
			u.setCreatedAt(olduser.getCreatedAt());
			u.setUser(olduser.getUser());
			u.setUpdatedAt(new Date());
			userService.update(u);

			if(roleId != null && !"".equals(roleId)){
				
				String[] roleIds = roleId.split(",");
				for (int i = 0; i < roleIds.length; i++) {
					Userrole userrole = new Userrole();
					Role role = roleService.get(roleIds[i]);
					userrole.setRoleId(role);
					userrole.setUserId(u);
					userService.saveRelativity(userrole);
				}
				//保存用户与角色多对应权限的关联id
				List<String> mids = preMenuService.findByRids(roleIds);
				for (String mid : mids) {
					UserPreMenu userPreMenu = new UserPreMenu();
					PreMenu menu = preMenuService.get(mid);
					userPreMenu.setPreMenu(menu);
					userPreMenu.setUser(u);
					userService.saveUserMenu(userPreMenu);
				}
			}
			
		} else {

		}

		return "redirect:list.html";
	}

	/**
	 * Description: 删除用户信息，逻辑删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/delete_soft")
	public String delete_soft(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			User user = new User();
			user.setId(str);
			//删除用户-角色关联
			Userrole userrole = new Userrole();
			userrole.setUserId(user);
			roleService.deleteRoelUser(userrole);
			//删除用户-权限关联
			UserPreMenu userPreMenu = new UserPreMenu();
			userPreMenu.setUser(user);
			userService.deleteUserMenu(userPreMenu);
			//修改用户为删除状态
			userService.deleteByLogic(str);
		}
		return "redirect:list.html";
	}

	/**
	 * Description: 显示用户详细信息页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-13
	 * @param model
	 * @param user
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/show")
	public String show(Model model, User user) {
		List<User> ulist = userService.find(user);
		if (ulist != null && ulist.size() > 0) {
			User u = ulist.get(0);
			String roleName = "";
			List<Role> list = u.getRoles();
			for (int i = 0; i < list.size(); i++) {
				if (i + 1 == list.size()) {
					roleName += list.get(i).getName();
				} else {
					roleName += list.get(i).getName() + ",";
				}
			}
			model.addAttribute("roleName", roleName);
			model.addAttribute("user", u);
		} else {

		}
		return "ses/bms/user/view";
	}

	/**
	 * Description: 弹出权限分配页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param model
	 * @param id
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/openPreMenu")
	public String openPreMenu(Model model,String id){
		model.addAttribute("uid", id);
		return "ses/bms/user/addPreMenu";
	}
	
	/**
	 * Description: 保存用户-权限菜单关联
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-27
	 * @param request
	 * @param response
	 * @param userId
	 * @param ids
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/saveUserMenu")
	public void saveUserMenu(HttpServletRequest request,
			HttpServletResponse response, String userId, String ids)
			throws IOException {

		try {
			User user = userService.getUserById(userId);
			UserPreMenu um = new UserPreMenu();
			um.setUser(user);
			userService.deleteUserMenu(um);
			String[] mIds = ids.split(",");
			for (String str : mIds) {
				UserPreMenu up = new UserPreMenu();
				PreMenu preMenu = preMenuService.get(str);
				up.setPreMenu(preMenu);
				up.setUser(user);
				userService.saveUserMenu(up);
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("权限配置完成");
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}

	}
}
