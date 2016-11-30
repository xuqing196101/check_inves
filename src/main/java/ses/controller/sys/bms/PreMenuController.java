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
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.UserPreMenu;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
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
	private UserServiceI userService; 

	@Autowired
	private RoleServiceI roleService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataService;
	
	private final static String NAV_MENU = "0";

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
		//状态为可用的权限菜单
		PreMenu menu = new PreMenu();
		menu.setStatus(0);
		//如果是给角色配置权限
		if(userId != null && !"".equals(userId)){
		    ses.model.bms.User user = userService.getUserById(userId);
		    String typeNameCode = dictionaryDataService.getDictionaryData(user.getTypeName()).getCode();
		    if ("NEED_U".equals(typeNameCode) || "PURCHASER_U".equals(typeNameCode) || "PUR_MG_U".equals(typeNameCode) || "OTHER_U".equals(typeNameCode) || "SUPERVISER_U".equals(typeNameCode)) {
                menu.setKind(0);
            }
		    if ("SUPPLIER_U".equals(typeNameCode)) {
                menu.setKind(1);
            }
		    if ("EXPERT_U".equals(typeNameCode)) {
                menu.setKind(2);
            }
		    if ("IMP_SUPPLIER_U".equals(typeNameCode)) {
                menu.setKind(3);
            }
		    if ("IMP_AGENT_U".equals(typeNameCode)) {
                menu.setKind(4);
            }
			list = preMenuService.find(menu);
			//给用户配置权限
			String[] userIds = userId.split(",");
			menuIds = preMenuService.findByUids(userIds);
		} else if (r.getId() != null && !"".equals(r.getId())){
		    String rCode = dictionaryDataService.getDictionaryData(r.getKind()).getCode();
		    if ("PURCHASE_BACK".equals(rCode)) {
                menu.setKind(0);
            }
		    if ("SUPPLIER_BACK".equals(rCode)) {
                menu.setKind(1);
            }
            if ("EXPERT_BACK".equals(rCode)) {
                menu.setKind(2);
            }
            if ("IMPORT_SUPPLIER_BACK".equals(rCode)) {
                menu.setKind(3);
            }
            if ("IMPORT_AGENT_BACK".equals(rCode)) {
                menu.setKind(4);
            }
			list = preMenuService.find(menu);
			String[] roleIds = r.getId().split(",");
			menuIds = preMenuService.findByRids(roleIds);
		} else {
			list = preMenuService.find(null);
		}
		for (int i = 0; i < list.size(); i++) {
			PreMenu e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("pId", e.getParentId() != null ? e.getParentId().getId()
					: 0);
			map.put("name", e.getName());
			if(i==0){
				map.put("open", true);
			}
			for (String menuId : menuIds) {
				if (menuId.equals(e.getId())) {
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
	 * Description: 跳转添加页码
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-24
	 * @param request
	 * @param model
	 * @return
	 * @exception IOException
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, Model model){
		String pid = request.getParameter("pid");
		PreMenu pmenu = preMenuService.get(pid);
		model.addAttribute("pmenu",pmenu);
		return "ses/bms/menu/add";
	}

	/**
	 * Description: 保存新增数据
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-24
	 * @param response
	 * @param menu
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/save")
	public void save(HttpServletResponse response, PreMenu menu) throws IOException {
		try {
			if ("".equals(menu.getName()) || menu.getName() == null) {
				String msg = "请填写名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else {
				//获取父节点
				PreMenu pmenu = null;
				if(menu.getId() != null && !"".equals(menu.getId())){
					pmenu = preMenuService.get(menu.getId());
					menu.setMenulevel(pmenu.getMenulevel()+1);
				}else{
				    pmenu = new PreMenu();
				    pmenu.setId(NAV_MENU);
					menu.setMenulevel(1);
				}
				menu.setId(null);
				menu.setParentId(pmenu);
				menu.setCreatedAt(new Date());
				menu.setIsDeleted(0);
				menu.setKind(pmenu.getKind());
				preMenuService.save(menu);
				String msg = "添加成功";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}
	
	/**
	 * Description: 根据id查询菜单
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-12
	 * @param response
	 * @param id
	 * @exception IOException
	 */
	@RequestMapping("get")
	public void get(HttpServletResponse response, String id) throws IOException{
		try {
			PreMenu preMenu = preMenuService.get(id);
			net.sf.json.JSONArray json = new net.sf.json.JSONArray();
			JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class,
	                new JsonDateValueProcessor());
			String jsonStr = json.fromObject(preMenu,jsonConfig).toString();
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(jsonStr);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}
	}
	
	/**
	 * Description: 调整到编辑页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param model
	 * @param id
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/edit")
	private String edit(Model model, String id){
		PreMenu menu = preMenuService.get(id);
		model.addAttribute("menu",menu);
		return "ses/bms/menu/edit";
	}  
	
	/**
	 * Description: 更新菜单
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-25
	 * @param response
	 * @param request
	 * @param menu
	 * @throws IOException
	 * @exception IOException
	 */
	@RequestMapping("/update")
	private void update(HttpServletResponse response, HttpServletRequest request, PreMenu menu) throws IOException{
		try {
			if ("".equals(menu.getName()) || menu.getName() == null) {
				String msg = "请填写名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else {
				//获取父节点
				String pId = request.getParameter("pid");
				PreMenu pmenu = null;
				if(pId != null && !"".equals(pId)){
					pmenu = preMenuService.get(pId);
					menu.setMenulevel(pmenu.getMenulevel()+1);
				}else{
					menu.setMenulevel(1);
				}
				PreMenu old = preMenuService.get(menu.getId());
				menu.setCreatedAt(old.getCreatedAt());
				menu.setParentId(pmenu);
				menu.setKind(pmenu.getKind());
				menu.setUpdatedAt(new Date());
				preMenuService.update(menu);
				//如果是将可用改为暂停,删除相应的关联关系
				if(old.getStatus() == 0 && menu.getStatus() == 1){
					UserPreMenu userPreMenu = new UserPreMenu(); 
					userPreMenu.setPreMenu(menu);
					userService.deleteUserMenu(userPreMenu);
					RolePreMenu rolePreMenu = new RolePreMenu();
					rolePreMenu.setPreMenu(menu);
					roleService.deleteRoelMenu(rolePreMenu);
				}
				String msg = "更新成功";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + true + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			response.getWriter().close();
		}
	}
	
	/**
	 * Description: 删除菜单
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-18
	 * @param ids
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("delete")
	public String delete(String ids){
		String[] idarry = ids.split(",");
		for (String id : idarry) {
			preMenuService.delete(id);
			//同时删除用户-权限，角色-权限关联数据
			PreMenu menu = new PreMenu();
			menu.setId(id);
			UserPreMenu userPreMenu = new UserPreMenu(); 
			userPreMenu.setPreMenu(menu);
			userService.deleteUserMenu(userPreMenu);
			RolePreMenu rolePreMenu = new RolePreMenu();
			rolePreMenu.setPreMenu(menu);
			roleService.deleteRoelMenu(rolePreMenu);
		}
		return "redirect:list.html";
	}
	
	/**
	 *〈简述〉验证是否是根节点
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param response
	 * @param id 菜单id
	 * @throws IOException
	 */
	@RequestMapping("/validate")
	@ResponseBody
	public void validate(HttpServletResponse response, String id) throws IOException{
	    try {
	        String msg = "";
	        Boolean is_root = false;
	        PreMenu preMenu = preMenuService.get(id);
	        if (preMenu.getParentId() == null || "0".equals(preMenu.getParentId())) {
	            msg = "根节点不允许修改或删除";
	            is_root = true;
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("{\"is_root\": " + is_root + ", \"msg\": \"" + msg + "\"}");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
	    
	}
}
