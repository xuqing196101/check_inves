package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
 * Description: 角色管理控制类
 * 
 * @author Ye MaoLin
 * @version 2016-9-13
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/role")
public class RoleManageController {

	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private UserServiceI userService;

	@Autowired
	private PreMenuServiceI preMenuService;

	private static Logger logger = Logger.getLogger(RoleManageController.class);

	/**
	 * Description: 获取角色列表（包括关联数据）
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/list")
	public String list(Model model, Integer page) {
		List<Role> roles = roleService.list(null, page == null ? 1 : page);
		model.addAttribute("list", new PageInfo<Role>(roles));
		logger.info(JSON.toJSONStringWithDateFormat(roles,
				"yyyy-MM-dd HH:mm:ss"));
		return "ses/bms/role/list";
	}

	/**
	 * Description: 跳转添加页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/add")
	public String toAdd() {
		return "ses/bms/role/add";
	}

	/**
	 * Description: 保存角色
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param response
	 * @param r
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public void save(HttpServletResponse response, Role r) throws IOException {
		try {
			if ("".equals(r.getName()) || r.getName() == null) {
				String msg = "请填写角色名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(
						"{\"success\": " + false + ", \"msg\": \"" + msg
								+ "\"}");
			} else {
				r.setCreatedAt(new Date());
				r.setIsDeleted(0);
				roleService.save(r);
				String msg = "添加成功";
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
	 * Description: 跳转编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param r
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/edit")
	public String edit(Role r, Model model) {
		Role role = roleService.get(r.getId());
		model.addAttribute("role", role);
		return "ses/bms/role/edit";
	}

	/**
	 * Description: 更新角色信息
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param response
	 * @param r
	 * @exception IOException
	 */
	@RequestMapping("/update")
	public void update(HttpServletResponse response, Role r) throws IOException {
		try {
			if ("".equals(r.getName()) || r.getName() == null) {
				String msg = "请填写角色名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(
						"{\"success\": " + false + ", \"msg\": \"" + msg
								+ "\"}");
			} else {
				Role role = roleService.get(r.getId());
				role.setDescription(r.getDescription());
				role.setName(r.getName());
				roleService.update(role);
				String msg = "更新成功";
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
	 * Description: 删除角色，物理删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/delete")
	public String delete(String ids) {
		String[] idstr = ids.split(",");
		for (String id : idstr) {
			Role r = roleService.get(id);
			// 删除角色与用户的关联
			Userrole userrole = new Userrole();
			userrole.setRoleId(r);
			roleService.deleteRoelUser(userrole);
			// 删除角色与权限的关联
			RolePreMenu rm = new RolePreMenu();
			rm.setRole(r);
			roleService.deleteRoelMenu(rm);
			// 删除角色
			roleService.delete(id);
		}
		return "redirect:list.html";
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
	public String openPreMenu(Model model, String id) {
		model.addAttribute("rid", id);
		return "ses/bms/role/addPreMenu";
	}

	/**
	 * Description: 保存角色权限
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param request
	 * @param response
	 * @param roleId
	 * @param ids
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/saveRoleMenu")
	public void saveRoleMenu(HttpServletRequest request,
			HttpServletResponse response, String roleId, String ids)
			throws IOException {

		try {
			Role role = roleService.get(roleId);
			//先删除该角色下用户的用户-权限菜单关联
			List<Role> rlist = roleService.selectRole(role, null);
			if(rlist != null && rlist.size() > 0){
				//该角色所有用户
				List<User> ulist = rlist.get(0).getUsers();
				//该角色所有权限菜单
				List<PreMenu> mlist = rlist.get(0).getPreMenus();
				for (User user : ulist) {
					for (PreMenu preMenu : mlist) {
						UserPreMenu userPreMenu = new UserPreMenu();
						userPreMenu.setPreMenu(preMenu);
						userPreMenu.setUser(user);
						userService.deleteUserMenu(userPreMenu);
					}
				}
			}
			//删除该角色的角色-权限菜单关联
			RolePreMenu rm = new RolePreMenu();
			rm.setRole(role);
			roleService.deleteRoelMenu(rm);
			String[] pIds = ids.split(",");
			for (String str : pIds) {
				PreMenu preMenu = preMenuService.get(str);
				//保存角色-权限菜单关联
				RolePreMenu rolePreMenu = new RolePreMenu();
				rolePreMenu.setPreMenu(preMenu);
				rolePreMenu.setRole(role);
				roleService.saveRolePreMenu(rolePreMenu);
				//保存该角色下用户的用户-权限菜单关联
				if(rlist != null && rlist.size() > 0){
					List<User> ulist = rlist.get(0).getUsers();
					for (User user : ulist) {
						UserPreMenu userPreMenu = new UserPreMenu();
						userPreMenu.setPreMenu(preMenu);
						userPreMenu.setUser(user);
						userService.saveUserMenu(userPreMenu);
					}
				}
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

	
	/**
	 * Description: 获取角色树
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param request
	 * @param response
	 * @param model
	 * @param userId
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/roletree")
	public void roletree(HttpServletRequest request,
			HttpServletResponse response, Model model, String userId)
			throws IOException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		User u = new User();
		u.setId(userId);
		List<User> ulist = userService.find(u);
		List<Role> oldRoles = new ArrayList<Role>();
		if(ulist.size() > 0 && ulist != null){
			oldRoles = ulist.get(0).getRoles();
		}
		Role temp = new Role();
		temp.setStatus(0);
		List<Role> list = roleService.find(temp);
		for (int i = 0; i < list.size(); i++) {
			Role e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("pId",0);
			map.put("name", e.getName());
			for (Role r : oldRoles) {
				if (r.getId().equals(e.getId())) {
					map.put("checked", true);
				}
			}
			mapList.add(map);
		}
		try {
			String jsonstr = JSON.toJSONString(mapList);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(jsonstr);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}
	}
	
	/**
	 * Description: 启用或禁用角色
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-27
	 * @param response
	 * @param ids
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/opera")
	public void opera(HttpServletResponse response, String ids) throws IOException{
		try {
			String msg = "";
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				Role role = roleService.get(id);
				if(role.getStatus() == 0){
					role.setStatus(1);
					roleService.update(role);
					msg = "已禁用";
					//解除用户-角色关系
					
				} else if(role.getStatus() == 1) {
					role.setStatus(0);
					roleService.update(role);
					msg = "已启用";
				}
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}
}
