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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.model.bms.User;
import ses.model.bms.UserDataRule;
import ses.model.bms.UserPreMenu;
import ses.model.oms.Orgnization;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserDataRuleService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.JsonDateValueProcessor;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

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
	@Autowired
	private OrgnizationServiceI OrgnizationServiceI;
	
	@Autowired
	private UserDataRuleService UserDataRuleService;
	private final static String NAV_MENU = "0";

	private static Logger logger = Logger.getLogger(RoleManageController.class);

	@RequestMapping("/list")
	public String list(@CurrentUser User cuse, Model model) {
	  User user = userService.getUserById(cuse.getId());
	  if("4".equals(user.getTypeName())){
      model.addAttribute("menu", "show");
    }else{
      model.addAttribute("menu", "hidden");
      //权限不通过进入提示页面
      return "redirect:/qualifyError.jsp";
    }
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
		    //查询该用户的供应商角色
        HashMap<String, Object> supplierMap = new HashMap<String, Object>();
        supplierMap.put("userId", userId);
        supplierMap.put("code", "SUPPLIER_R");
        List<Role> srs = roleService.selectByUserIdCode(supplierMap);
        //查询该用户的专家角色
        HashMap<String, Object> expertMap = new HashMap<String, Object>();
        expertMap.put("userId", userId);
        expertMap.put("code", "EXPERT_R");
        List<Role> ers = roleService.selectByUserIdCode(expertMap);
        //查询该用户的进口代理商角色
        HashMap<String, Object> importAgentMap = new HashMap<String, Object>();
        importAgentMap.put("userId", userId);
        importAgentMap.put("code", "IMPORT_AGENT_R");
        List<Role> iars = roleService.selectByUserIdCode(importAgentMap);
		    if (srs != null && srs.size() > 0) {
		      //供应商后台菜单
		      menu.setKind(1);
        } else if (ers != null && ers.size() > 0) {
          //专家后台菜单
          menu.setKind(2);
        } else if (iars != null && iars.size() > 0){
          //进口代理商后台菜单
          menu.setKind(3);
        } else {
          //采购后台菜单
          menu.setKind(0);
        }
  			list = preMenuService.find(menu);
  			//给用户配置权限
  			//String[] userIds = userId.split(",");
  			//获取用户权限
  			menuIds = preMenuService.findByUids(userId);
		} else if (r.getId() != null && !"".equals(r.getId())){
		    String rCode = dictionaryDataService.getDictionaryData(r.getKind()).getCode();
		    //采购后台
		    if ("PURCHASE_BACK".equals(rCode)) {
            menu.setKind(0);
        }
		    //供应商后台
		    if ("SUPPLIER_BACK".equals(rCode)) {
            menu.setKind(1);
        }
		    //专家后台
        if ("EXPERT_BACK".equals(rCode)) {
            menu.setKind(2);
        }
        //进口代理商角色
        if ("IMPORT_AGENT_BACK".equals(rCode)) {
            menu.setKind(3);
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
	 * 数据权限数
	 * @author YangHongLiang
	 * @param request
	 * @param response
	 * @param model
	 * @param r
	 * @param userId
	 * @return
	 */
	@RequestMapping("dataTree")
	@ResponseBody
	public List<Map<String,Object>> dataTree(Model model, Role r,String userId){
		List<Map<String,Object>> list=new ArrayList<>();
		//获取 0:需求部门 1:采购机构 2:管理部门
		List<Orgnization> demandAndManage= OrgnizationServiceI.selectByType();
		List<Orgnization> purchase=OrgnizationServiceI.initOrgByType("1");
		Map<String,Object> map=null;
		//需求部门头
		DictionaryData demandT=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_DEMAND);
		//管理部门头
		DictionaryData manageT=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_MANAGER);
		//采购机构
		DictionaryData cgjg=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_JG_CGJG);
		//其他
		DictionaryData qt=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_OT);
		//管理头
		map=new HashMap<String, Object>();
		map.put("id", manageT.getId());
		map.put("pId", 0);
		map.put("name", "管理部门");
		map.put("checked", false);
		list.add(map);
		//需求 头
		map=new HashMap<String, Object>();
		map.put("id", demandT.getId());
		map.put("pId", 0);
		map.put("name", "需求部门");
		map.put("checked", false);
		list.add(map);
		// 采购 头
		map=new HashMap<String, Object>();
		map.put("id", cgjg.getId());
		map.put("pId", 0);
		map.put("name", "采购机构");
		map.put("checked", false);
		list.add(map);
		// 其他 头
		map=new HashMap<String, Object>();
		map.put("id", qt.getId());
		map.put("pId", 0);
		map.put("name", "其他");
		map.put("checked", false);
		list.add(map);
		for(Orgnization de:purchase){
			map=new HashMap<String, Object>();
			map.put("id", de.getId());
			map.put("pId", cgjg.getId());
			map.put("name", de.getName());
			map.put("checked", false);
			list.add(map);
		}
		for(Orgnization de:demandAndManage){
			map=new HashMap<String, Object>();
			map.put("id", de.getId());
			// 0:需求部门 1:采购机构 2:管理部门
			if(de.getTypeName().equals("0")){
				map.put("pId", de.getParentId() != null ? de.getParentId(): demandT.getId());
			}else if(de.getTypeName().equals("2")){
				map.put("pId", de.getParentId() != null ? de.getParentId(): manageT.getId());
			}else{
				map.put("pId", de.getParentId() != null ? de.getParentId():0);
			}
			map.put("name", de.getName());
			map.put("checked", false);
			list.add(map);
		}
		
		if(StringUtils.isNotBlank(userId)){
			List<UserDataRule> dataRuleList=UserDataRuleService.selectByUserId(userId);
			if(dataRuleList!=null&& dataRuleList.size()>0){
			for (UserDataRule userDataRule : dataRuleList) {
			for (Map<String, Object> maps : list) {
				if(userDataRule.getOrgId().equals(maps.get("id"))){
					maps.put("checked", true);
				}
			  }
			 }
			}
		}
		return list;
	}
	/**
	 *〈简述〉查询用户权限
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 */
	@RequestMapping("/viewTreedata")
  public void viewTreedata(HttpServletRequest request, HttpServletResponse response, String userId) throws IOException {
	    try {
	      List<String> menuIds = preMenuService.findByUids(userId);
	      List<Map<String, Object>> mapList  = new ArrayList<Map<String,Object>>();
	      for (String menuId : menuIds) {
	        PreMenu preMenu = preMenuService.get(menuId);
	        if (preMenu != null) {
	          Map<String, Object> map = new HashMap<String, Object>();
	          map.put("id", menuId);
	          map.put("pId", preMenu.getParentId() != null ? preMenu.getParentId().getId()
	              : 0);
	          map.put("name", preMenu.getName());
	          mapList.add(map);
	        }
	      }
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
	 *〈简述〉查询用户 数据权限
	 *〈详细描述〉
	 * @author YangHongLiang
	 * @param request
	 * @param response
	 * @param userId
	 * @throws IOException
	 */
	@RequestMapping("/dataViewTree")
	@ResponseBody
  public List<Map<String, Object>> dataViewTree(HttpServletRequest request, HttpServletResponse response, String userId){
	      List<String> menuIds = UserDataRuleService.getOrgID(userId);
	      List<Map<String, Object>> mapList  = new ArrayList<Map<String,Object>>();
	      int type0=0,type1=0,type2=0;
	      if(menuIds!=null&& menuIds.size()>0){
	      for (String menuId : menuIds) {
	        Orgnization org = OrgnizationServiceI.getOrgByPrimaryKey(menuId);
			Map<String, Object> map =null;
			//获取 0:需求部门 1:采购机构 2:管理部门     else 其他
	        if (org != null) {
	        	if(org.getTypeName().equals("0")&&type0==0){
	        		//需求部门头
	    			DictionaryData demandT=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_DEMAND);
	    			//需求 头
	    			map=new HashMap<String, Object>();
	    			map.put("id", demandT.getId());
	    			map.put("pId", 0);
	    			map.put("name", "需求部门");
	    			mapList.add(map);
	    			type0=2;
	        	}else if(org.getTypeName().equals("1")&&type1==0){
	        		//采购机构
	    			DictionaryData cgjg=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_JG_CGJG);
	    			// 采购 头
	    			map=new HashMap<String, Object>();
	    			map.put("id", cgjg.getId());
	    			map.put("pId", 0);
	    			map.put("name", "采购机构");
	    			mapList.add(map);
	    			type0=2;
	        	}else if(org.getTypeName().equals("2")&&type2==0){
	        		//管理部门头
	    			DictionaryData manageT=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_MANAGER);
	    			//管理头
	    			map=new HashMap<String, Object>();
	    			map.put("id", manageT.getId());
	    			map.put("pId", 0);
	    			map.put("name", "管理部门");
	    			mapList.add(map);
	    			type2=2;
	        	}
	          map= new HashMap<String, Object>();
	          map.put("id", org.getId());
	          map.put("pId", org.getParentId() != null ? org.getParentId() : 0);
	          map.put("name", org.getName());
	          mapList.add(map);
	        }else{
	        	//其他
	        	DictionaryData qt=  DictionaryDataUtil.get(StaticVariables.ORG_TYPE_OT);
	        	if(qt!=null&&menuId.equals(qt.getId())){
				// 其他 头
				map=new HashMap<String, Object>();
				map.put("id", qt.getId());
				map.put("pId", 0);
				map.put("name", "其他");
				mapList.add(map);
				
	        	}
	        }
	      }
	    }
	    return mapList;
	}
	
	/**
	 *〈简述〉用户查看权限的条件搜索
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/findForViewSelect" ) 
  @ResponseBody
  public List<PreMenu> findForViewSelect(String userId) {
      List<PreMenu> preMenus = new ArrayList<>();
      List<String> menuIds = preMenuService.findByUids(userId);
      for (String menuId : menuIds) {
          PreMenu preMenu = preMenuService.get(menuId);
          preMenus.add(preMenu);
      }
      return preMenus;
  }
	
	/**
	 *〈简述〉用户查看数据权限的条件搜索
	 *〈详细描述〉
	 * @author Ye MaoLin
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/findForViewDataSelect" ) 
  @ResponseBody
  public List<Orgnization> findForViewDataSelect(String userId) {
		List<Orgnization> list=new ArrayList<>();
		if(StringUtils.isNotBlank(userId)){
		List<String> menuIds = UserDataRuleService.getOrgID(userId);
	      if(menuIds!=null&& menuIds.size()>0){
	      for (String menuId : menuIds) {
	        Orgnization org = OrgnizationServiceI.getOrgByPrimaryKey(menuId);
	        if(org!=null){
	        	list.add(org);
	        }
	      }
	     }
		}
      return list;
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
			if ("".equals(menu.getId()) || menu.getId() == null) {
				String msg = "请选择上级节点";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if ("".equals(menu.getName()) || menu.getName() == null) {
				String msg = "请填写名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if ("".equals(menu.getType()) || menu.getType() == null) {
				String msg = "请选择节点类型";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if (menu.getPosition() == null) {
				String msg = "请填写菜单序号";
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
	 * @Title: getUserByMid
	 * @author XuQing 
	 * @date 2017-4-19 上午10:44:10  
	 * @Description:根据菜单id查询用户
	 * @param @param model
	 * @param @param page
	 * @param @param mid
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("getUserByMid")
	public String getUserByMid(Model model, Integer page, String mid){
	    List<User> users = preMenuService.getUserByMid(mid, page);
		PageInfo < User > pageInfo = new PageInfo < User > (users);
	    model.addAttribute("users", users);
	    model.addAttribute("result", pageInfo);
	    model.addAttribute("mid", mid);
	    return "ses/bms/menu/user_list";
	}
    
    /**
     *〈简述〉ajax获取权限关联用户
     *〈详细描述〉
     * @author Ye MaoLin
     * @param response
     * @param page
     * @param mid
     * @throws IOException
     */
    @RequestMapping("ajaxGetUserByMid")
    public void ajaxGetUserByMid(HttpServletResponse response, Integer page, String mid) throws IOException{
        List<User> users = preMenuService.getUserByMid(mid, page);
        for (User user : users) {
            String orgName = "";
            if (user.getTypeName() == "0") {
                if (user.getOrg() !=null && user.getOrg().getShortName() != null) {
                    orgName = user.getOrg().getShortName();
                } else if(user.getOrg() != null && (user.getOrg().getShortName() == null || "".equals(user.getOrg().getShortName()))){
                    orgName = user.getOrg().getName();    
                } else if(user.getOrg() == null){
                    orgName = user.getOrgName();
                }
            } else if(user.getTypeName() != "4" && user.getTypeName() != "5"){
                if (user.getOrg() != null && user.getOrg().getFullName() != null && !"".equals(user.getOrg().getFullName())) {
                    orgName = user.getOrg().getFullName();
                } else if(user.getOrg() != null && (user.getOrg().getFullName() == null || "".equals(user.getOrg().getFullName()))){
                    orgName = user.getOrg().getName();
                } else if(user.getOrg() == null){
                    orgName = user.getOrgName();             
                }
            } else {
                orgName = user.getOrgName();
            }
            user.setOrgName(orgName);
        }
        PageInfo < User > pageInfo = new PageInfo < User > (users);
        String jsonstr = JSON.toJSONString(pageInfo);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().print(jsonstr);
        response.getWriter().flush();
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
  public String edit(Model model, String id){
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
	public void update(HttpServletResponse response, HttpServletRequest request, PreMenu menu) throws IOException{
		try {
			if ("".equals(menu.getId()) || menu.getId() == null) {
				String msg = "请选择上级节点";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if ("".equals(menu.getName()) || menu.getName() == null) {
				String msg = "请填写名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if ("".equals(menu.getType()) || menu.getType() == null) {
				String msg = "请选择节点类型";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": " + false + ", \"msg\": \"" + msg + "\"}");
				response.getWriter().flush();
			} else if (menu.getPosition() == null) {
				String msg = "请填写菜单序号";
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
					menu.setKind(pmenu.getKind());
				}else{
					menu.setMenulevel(1);
				}
				PreMenu old = preMenuService.get(menu.getId());
				menu.setCreatedAt(old.getCreatedAt());
				menu.setParentId(pmenu);
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
  	        
  	        if ((preMenu.getParentId() == null || "0".equals(preMenu.getParentId())) && ("供应商后台".equals(preMenu.getName()) || "进口代理商后台".equals(preMenu.getName()) || "专家后台".equals(preMenu.getName()) || "采购管理后台".equals(preMenu.getName())) ) {
              msg = "此根节点不允许修改或删除";
              is_root = true;
            }
  	        
  	        /*if (preMenu.getParentId() == null || "0".equals(preMenu.getParentId())) {
	            msg = "根节点不允许修改或删除";
	            is_root = true;
            }*/
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("{\"is_root\": " + is_root + ", \"msg\": \"" + msg + "\"}");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            response.getWriter().close();
        }
	}
	
	@RequestMapping(value="/findForSelect" ) 
  @ResponseBody
  public List<PreMenu> getUserForSelect(String kind, String userId) {
      List<PreMenu> preMenus = new ArrayList<>();
      if(userId != null && !"".equals(userId)){
          //查询该用户的供应商角色
          HashMap<String, Object> supplierMap = new HashMap<String, Object>();
          supplierMap.put("userId", userId);
          supplierMap.put("code", "SUPPLIER_R");
          List<Role> srs = roleService.selectByUserIdCode(supplierMap);
          //查询该用户的专家角色
          HashMap<String, Object> expertMap = new HashMap<String, Object>();
          expertMap.put("userId", userId);
          expertMap.put("code", "EXPERT_R");
          List<Role> ers = roleService.selectByUserIdCode(expertMap);
          //查询该用户的进口代理商角色
          HashMap<String, Object> importAgentMap = new HashMap<String, Object>();
          importAgentMap.put("userId", userId);
          importAgentMap.put("code", "IMPORT_AGENT_R");
          List<Role> iars = roleService.selectByUserIdCode(importAgentMap);
          PreMenu menu = new PreMenu();
          if (srs != null && srs.size() > 0) {
            //供应商后台菜单
            menu.setKind(1);
          } else if (ers != null && ers.size() > 0) {
            //专家后台菜单
            menu.setKind(2);
          } else if (iars != null && iars.size() > 0){
            //进口代理商后台菜单
            menu.setKind(3);
          } else {
            //采购后台菜单
            menu.setKind(0);
          }
          preMenus = preMenuService.find(menu);
      } else if (kind != null && !"".equals(kind)){
          PreMenu menu = new PreMenu();
          menu.setStatus(0);
          String rCode = dictionaryDataService.getDictionaryData(kind).getCode();
          //采购后台
          if ("PURCHASE_BACK".equals(rCode)) {
              menu.setKind(0);
          }
          //供应商后台
          if ("SUPPLIER_BACK".equals(rCode)) {
              menu.setKind(1);
          }
          //专家后台
          if ("EXPERT_BACK".equals(rCode)) {
              menu.setKind(2);
          }
          //进口代理商角色
          if ("IMPORT_AGENT_BACK".equals(rCode)) {
              menu.setKind(3);
          }
          preMenus = preMenuService.find(menu);
      }
      return preMenus;
  }
	/**
	 * 获取组织机构数据
	 * @param userId
	 * @return
	 */
  @RequestMapping(value="/findDataSelect" ) 
  @ResponseBody
  public List<Orgnization> getDataTree(String userId){
	  
	    List<Orgnization> demand= OrgnizationServiceI.selectByType();
		List<Orgnization> purchase=OrgnizationServiceI.initOrgByType("1");
		demand.addAll(purchase);
	 return demand;
  }
}
