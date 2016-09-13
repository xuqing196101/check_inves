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
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.util.JsonDateValueProcessor;


import com.alibaba.fastjson.JSON;

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
		return "premenu/list";
	}

	/**
	 * Description: 权限菜单控制类
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
			HttpServletResponse response, Model model, Role r)
			throws IOException {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<PreMenu> roleMenus = new ArrayList<PreMenu>();
		List<PreMenu> list = preMenuService.getAll(null);
		List<Role> roles = roleService.selectRoleUser(r);
		if (roles.size() == 0) {
			roleMenus = null;
		} else {
			roleMenus = roles.get(0).getPreMenus();
		}
		for (int i = 0; i < list.size(); i++) {
			PreMenu e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("pId", e.getParentId() != null ? e.getParentId().getId()
					: 0);
			map.put("name", e.getName());
			for (PreMenu roleMenu : roleMenus) {
				if (roleMenu.getId().equals(e.getId())) {
					map.put("checked", true);
				}
			}
			mapList.add(map);
		}
		String jsonstr = JSON.toJSONString(mapList);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(jsonstr);
	}

	@RequestMapping("/save")
	public String save() {
		for (int i = 1; i < 10; i++) {
			PreMenu preMenu = new PreMenu();
			preMenu.setName("菜单" + i);
			preMenu.setIsDeleted(0);
			preMenu.setMenulevel(1);
			preMenu.setOrderby(i);
			preMenu.setParentId(null);
			preMenu.setState(0);
			preMenu.setType("navigation");
			preMenu.setCreatedAt(new Date());
			preMenu.setUrl(null);
			preMenuService.save(preMenu);
			for (int j = 1; j < 6; j++) {
				PreMenu preMenu1 = new PreMenu();
				preMenu1.setName("菜单" + i + "-" + j);
				preMenu1.setIsDeleted(0);
				preMenu1.setMenulevel(2);
				preMenu1.setOrderby(j);
				preMenu1.setParentId(preMenu);
				preMenu1.setState(0);
				preMenu1.setType("accordion");
				preMenu1.setCreatedAt(new Date());
				preMenu1.setUrl(null);
				preMenuService.save(preMenu1);
				for (int k = 1; k < 6; k++) {
					PreMenu preMenu2 = new PreMenu();
					preMenu2.setName("菜单" + i + "-" + j + "-" + k);
					preMenu2.setIsDeleted(0);
					preMenu2.setMenulevel(3);
					preMenu2.setOrderby(k);
					preMenu2.setParentId(preMenu1);
					preMenu2.setState(0);
					preMenu2.setType("menu");
					preMenu2.setCreatedAt(new Date());
					preMenu2.setUrl(null);
					preMenuService.save(preMenu2);
				}
			}
		}
		return null;
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
	public void findListByParent(HttpServletResponse response,String id){
		try {
			PreMenu parent=new PreMenu();
			parent.setId(id);
			PreMenu preMenu=new PreMenu();
			preMenu.setParentId(parent);
			List<PreMenu> list=preMenuService.getAll(preMenu);
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class,
	                new JsonDateValueProcessor());
			String jsonStr=json.fromObject(list,jsonConfig).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("delete_soft")
	public String delete_soft(String ids){
		String[] idarry=ids.split(",");
		for (String id : idarry) {
			PreMenu menu=preMenuService.get(id);
			menu.setIsDeleted(1);
			preMenuService.update(menu);
		}
		return "redirectlist.html";
	}
}
