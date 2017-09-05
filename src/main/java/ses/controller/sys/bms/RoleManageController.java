package ses.controller.sys.bms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

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
	private DictionaryDataServiceI dictionaryDataService;

	private static Logger logger = Logger.getLogger(RoleManageController.class);

	/**
	 * Description: 获取角色列表
	 * 
	 * @author Ye MaoLin
	 * @version 2016-9-14
	 * @param model
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/list")
	public String list(@CurrentUser User cuser,Model model, Integer page, Role role) {
		List<Role> roles = roleService.list(role, page == null ? 1 : page);
		for (Role role2 : roles) {
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("id", role2.getId());
      List<User> users = userService.findByRole(map);
      if (users != null) {
        role2.setUserNumber(users.size());
      } else {
        role2.setUserNumber(0);
      }
    }
		if("4".equals(cuser.getTypeName())){
      model.addAttribute("menu", "show");
    }else{
      model.addAttribute("menu", "hidden");
    }
		model.addAttribute("list", new PageInfo<Role>(roles));
		model.addAttribute("role", role);
		logger.info(JSON.toJSONStringWithDateFormat(roles,
				"yyyy-MM-dd HH:mm:ss"));
		List<DictionaryData> dds = dictionaryDataService.findByKind("16");
        model.addAttribute("dds", dds);
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
	public String toAdd(Model model) {
	    List<DictionaryData> dds = dictionaryDataService.findByKind("16");
	    model.addAttribute("dds", dds);
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
		    String msg = "";
		    int count = 0;
			if ("".equals(r.getName()) || r.getName() == null) {
				msg = "请填写角色名称";
				count ++;
			} 
			if ("".equals(r.getCode().trim()) || r.getCode() == null) {
        if (count > 0) {
                  msg += "、唯一编码";
                  count ++;
              } else {
                  msg = "请填写唯一编码";
                  count ++;
              }
          }
			if ("".equals(r.getKind()) || r.getKind() == null) {
			    if (count > 0) {
                    msg += "、选择所属后台";
                    count ++;
                } else {
                    msg = "请选择所属后台";
                    count ++;
                }
            }
			Role roleCondition = new Role();
			roleCondition.setCode(r.getCode());
			List<Role> roleList = roleService.find(roleCondition);
			if (!"".equals(r.getCode()) && roleList.size() >= 1) {
			    msg = "唯一编码不可以重复";
                count ++;
			}
			if ("".equals(r.getPosition()) || r.getPosition() == null) {
        if (count > 0) {
            msg += "、填写序号";
            count ++;
        } else {
            msg = "请填写序号";
            count ++;
        }
      }
			if (count > 0) {
			    response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
			if (count == 0) {
				r.setCreatedAt(new Date());
				r.setIsDeleted(0);
				//更新角色序号
				roleService.updatePosition(r.getPosition(), null, 0);
				roleService.save(r);
				msg = "添加成功";
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
		List<DictionaryData> dds = dictionaryDataService.findByKind("16");
        model.addAttribute("dds", dds);
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
		    String msg = "";
            int count = 0;
            if ("".equals(r.getName()) || r.getName() == null) {
                msg = "请填写角色名称";
                count ++;
            } 
            if ("".equals(r.getKind()) || r.getKind() == null) {
                if (count > 0) {
                    msg += "、选择所属后台";
                    count ++;
                } else {
                    msg = "请选择所属后台";
                    count ++;
                }
            }
            if ("".equals(r.getPosition()) || r.getPosition() == null) {
              if (count > 0) {
                  msg += "、填写序号";
                  count ++;
              } else {
                  msg = "请填写序号";
                  count ++;
              }
            }
            if (count > 0) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().print(
                        "{\"success\": " + false + ", \"msg\": \"" + msg
                                + "\"}");
            }
            if (count == 0) {
				Role role = roleService.get(r.getId());
				role.setDescription(r.getDescription());
				role.setName(r.getName());
				role.setKind(r.getKind());
				role.setStatus(r.getStatus());
				role.setUpdatedAt(new Date());
				roleService.updatePosition(r.getPosition(), role.getPosition(), 1);
				role.setPosition(r.getPosition());
				roleService.update(role);
				msg = "更新成功";
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
			roleService.deleteBatch(r);
			roleService.updatePosition(r.getPosition(), null, 2);
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
	public String openPreMenu(Model model, String id, String kind) {
		model.addAttribute("rid", id);
		model.addAttribute("kind", kind);
		return "ses/bms/role/add_menu";
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
			roleService.saveRoleMenu(role, ids);
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
		temp.setKind(DictionaryDataUtil.getId("PURCHASE_BACK"));
		List<Role> list = roleService.find(temp);
		for (int i = 0; i < list.size(); i++) {
			Role e = list.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", e.getId());
			map.put("pId",0);
			map.put("name", e.getName());
			if(oldRoles != null && oldRoles.size() > 0){
				for (Role r : oldRoles) {
					if (r.getId().equals(e.getId())) {
						map.put("checked", true);
					}
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
