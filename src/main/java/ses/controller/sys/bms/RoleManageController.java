package ses.controller.sys.bms;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
import ses.model.bms.Userrole;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;


import com.alibaba.fastjson.JSON;

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
	private PreMenuServiceI preMenuService;
	
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
	public String list(Model model){
		List<Role> roles=roleService.selectRole(null);
		model.addAttribute("list", roles);
		logger.info(JSON.toJSONStringWithDateFormat(roles, "yyyy-MM-dd HH:mm:ss"));
		return "ses/bms/role/list";
	}
	
	/**   
	* @Title: toAdd
	* @author Ye MaoLin
	* @date 2016-8-30 下午3:13:49  
	* @Description: 跳转添加页面 
	* @return String     
	*/
	@RequestMapping("/add")
	public String toAdd(){
		return "ses/bms/role/add";
	}
	
	/**   
	* @Title: save
	* @author Ye MaoLin
	* @date 2016-8-30 下午3:14:04  
	* @Description: 保存角色
	* @param @param r
	* @return String     
	 * @throws IOException 
	*/
	@RequestMapping("/save")
	public void save(HttpServletResponse response, Role r){
		try{
			if("".equals(r.getName()) || r.getName()==null){
				String msg="请填写角色名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": "+false+", \"msg\": \""+msg+"\"}");
			}else{
				r.setCreatedAt(new Date());
				r.setIsDeleted(0);
				roleService.save(r);
				String msg="添加成功";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": "+true+", \"msg\": \""+msg+"\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**   
	* @Title: edit
	* @author Ye MaoLin
	* @date 2016-8-30 下午3:14:18  
	* @Description: 跳转编辑页面 
	* @param @param r
	* @param @param model
	* @return String     
	*/
	@RequestMapping("/edit")
	public String edit(Role r,Model model){
		Role role=roleService.get(r.getId());
		model.addAttribute("role", role);
		return "ses/bms/role/edit";
	}
	
	/**   
	* @Title: update
	* @author Ye MaoLin
	* @date 2016-8-30 下午3:14:34  
	* @Description: 更新角色信息
	* @param @param r
	* @return String     
	*/
	@RequestMapping("/update")
	public void update(HttpServletResponse response, Role r){
		try {
			if("".equals(r.getName()) || r.getName()==null){
				String msg="请填写角色名称";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": "+false+", \"msg\": \""+msg+"\"}");
			}else{
				Role role=roleService.get(r.getId());
				role.setDescription(r.getDescription());
				role.setName(r.getName());
				roleService.update(role);
				String msg="更新成功";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print("{\"success\": "+true+", \"msg\": \""+msg+"\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**   
	* @Title: delete_soft
	* @author Ye MaoLin
	* @date 2016-8-30 下午3:14:44  
	* @Description:删除角色
	* @param @param r
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete_soft(String ids){
		String[] idstr=ids.split(",");
		for (String id : idstr) {
			Role r=roleService.get(id);
			//删除角色与用户的关联
			Userrole userrole=new Userrole();
			userrole.setRoleId(r);
			roleService.deleteRoelUser(userrole);
			//删除角色与权限的关联
			RolePreMenu rm=new RolePreMenu();
			rm.setRole(r);
			roleService.deleteRoelMenu(rm);
			//删除角色
			r.setIsDeleted(1);
			roleService.update(r);
		}
		return "redirect:getAll.html";
	}
	
	@RequestMapping("/openPreMenu")
	public String openPreMenu(Model model,String id){
		model.addAttribute("rid", id);
		return "ses/bms/role/addPreMenu";
	}
	
	@RequestMapping("/saveRoleMenu")
	public void saveRoleMenu(HttpServletRequest request,HttpServletResponse response, String roleId , String ids) throws IOException{
	
	   try {
		   Role role=roleService.get(roleId);
		   RolePreMenu rm=new RolePreMenu();
		   rm.setRole(role);
		   roleService.deleteRoelMenu(rm);
		   
		   String[] pIds=ids.split(",");
		   for (String str : pIds) {
			   RolePreMenu rolePreMenu=new RolePreMenu();
			   PreMenu preMenu=preMenuService.get(str);
			   rolePreMenu.setPreMenu(preMenu);
			   rolePreMenu.setRole(role);
			   roleService.saveRolePreMenu(rolePreMenu);
		   }
		   response.setContentType("text/html;charset=utf-8");
		   response.getWriter().print("权限配置完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	  
	}
	
}
