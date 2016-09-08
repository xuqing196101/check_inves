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
import org.springframework.web.bind.annotation.RequestParam;

import ses.model.bms.PreMenu;
import ses.model.bms.Role;
import ses.model.bms.RolePreMenu;
import ses.service.bms.PreMenuServiceI;
import ses.service.bms.RoleServiceI;


import com.alibaba.fastjson.JSON;

/**
* <p>Title:RoleManageController </p>
* <p>Description: 角色管理控制类</p>
* <p>Company: ses </p> 
* @author yyyml
* @date 2016-8-2上午11:41:50
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
	* @Title: getAll
	* @author yyyml
	* @date 2016-8-30 下午3:12:37  
	* @Description: TODO 
	* @param @param model
	* @param @return      
	* @return String     
	*/
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<Role> roles=roleService.selectRoleUser(null);
		model.addAttribute("list", roles);
		logger.info(JSON.toJSONStringWithDateFormat(roles, "yyyy-MM-dd HH:mm:ss"));
		return "role/list";
	}
	
	/**   
	* @Title: toAdd
	* @author yyyml
	* @date 2016-8-30 下午3:13:49  
	* @Description: 跳转添加页面 
	* @return String     
	*/
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "role/add";
	}
	
	/**   
	* @Title: save
	* @author yyyml
	* @date 2016-8-30 下午3:14:04  
	* @Description: 保存角色
	* @param @param r
	* @return String     
	*/
	@RequestMapping("/save")
	public String save(Role r){
		r.setCreatedAt(new Date());
		r.setIsDeleted(0);
		roleService.save(r);
		return "redirect:getAll.do";
	}
	
	/**   
	* @Title: edit
	* @author yyyml
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
		return "role/edit";
	}
	
	/**   
	* @Title: update
	* @author yyyml
	* @date 2016-8-30 下午3:14:34  
	* @Description: 更新角色信息
	* @param @param r
	* @return String     
	*/
	@RequestMapping("/update")
	public String update(Role r){
		roleService.update(r);
		return "redirect:getAll.do";
	}
	
	/**   
	* @Title: delete
	* @author yyyml
	* @date 2016-8-30 下午3:14:44  
	* @Description:删除角色
	* @param @param r
	* @return String     
	*/
	@RequestMapping("/delete")
	public String delete(Role r){
		roleService.delete(r.getId());
		return "redirect:getAll.do";
	}
	
	@RequestMapping("/openPreMenu")
	public String openPreMenu(Model model,String id){
		model.addAttribute("rid", id);
		return "role/addPreMenu";
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
			// TODO: handle exception
		}
	  
	}
	
}
