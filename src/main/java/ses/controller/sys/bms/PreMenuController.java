package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.util.JsonDateValueProcessor;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

/**
 * Description: 权限菜单控制类
 * 
 * @author Ye MaoLin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/preMenu")
public class PreMenuController {

	@Autowired
	private PreMenuServiceI preMenuService;

	@Autowired
	private RoleServiceI roleService;

	private static Logger logger = Logger.getLogger(RoleManageController.class);

	@RequestMapping("/list")
	public String list() {
		return "ses/bms/menu/list";
	}

	/**
	 * Description: 权限树
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-7
	 * @param request
	 * @param response
	 * @param model
	 * @param r
	 * @exception IOException
	 */
	@RequestMapping("/treedata")
	public void menutree(HttpServletRequest request,
			HttpServletResponse response, Model model, Role r, String userId)
			throws IOException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<String> menuIds = new ArrayList<String>();
		List<PreMenu> list = preMenuService.find(null);
		
		//如果是给角色配置权限
		if(userId != null && !"".equals(userId)){
			//给用户配置权限
			String[] userIds = userId.split(",");
			menuIds = preMenuService.findByUids(userIds);
		}else if(r.getId() != null && !"".equals(r.getId())){
			String[] roleIds = {r.getId()};
			menuIds = preMenuService.findByRids(roleIds);
		}
		for (int i = 0; i < list.size(); i++) {
			PreMenu e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("pId", e.getParentId() != null ? e.getParentId().getId()
					: 0);
			map.put("name", e.getName());
			for (String menuId : menuIds) {
				if (menuId.equals(e.getId())) {
					map.put("checked", true);
				}
			}
			mapList.add(map);
		}
		String jsonstr = JSON.toJSONString(mapList);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonstr);
	}
	
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		String pid = request.getParameter("pid");
		PreMenu pmenu = preMenuService.get(pid);
		model.addAttribute("pmenu",pmenu);
		return "ses/bms/menu/add";
	}

	@RequestMapping("/save")
	public void save(HttpServletResponse response, PreMenu menu) {
		try {
			if ("".equals(menu.getName()) || menu.getName() == null) {
				String msg = "请填写名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
			} else {
				//获取父节点
				PreMenu pmenu = null;
				if(menu.getId() != null && !"".equals(menu.getId())){
					pmenu = preMenuService.get(menu.getId());
					menu.setMenulevel(pmenu.getMenulevel()+1);
				}else{
					menu.setMenulevel(1);
				}
				menu.setId(null);
				menu.setParentId(pmenu);
				menu.setCreatedAt(new Date());
				menu.setIsDeleted(0);
				preMenuService.save(menu);
				String msg = "添加成功";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Description: 根据父菜单查询子菜单
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-12
	 * @param response
	 * @param id
	 * @exception IOException
	 */
	@RequestMapping("findListByParent")
	public void findListByParent(HttpServletResponse response, String id){
		try {
			PreMenu parent = new PreMenu();
			parent.setId(id);
			PreMenu preMenu = new PreMenu();
			preMenu.setParentId(parent);
			List<PreMenu> list = preMenuService.find(preMenu);
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class,
	                new JsonDateValueProcessor());
			String jsonStr = json.fromObject(list,jsonConfig).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Description: 删除菜单，逻辑删除
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("delete_soft")
	public String delete_soft(String ids){
		String[] idarry = ids.split(",");
		for (String id : idarry) {
			PreMenu menu = preMenuService.get(id);
			menu.setIsDeleted(1);
			preMenuService.update(menu);
		}
		return "redirectlist.html";
	}
	
}
