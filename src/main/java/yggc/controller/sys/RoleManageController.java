package yggc.controller.sys;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import yggc.model.Role;
import yggc.service.RoleServiceI;

/**
* <p>Title:RoleManageController </p>
* <p>Description: 角色管理控制类</p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-8-2上午11:41:50
*/
@Controller
@Scope("prototype")
@RequestMapping("/role")
public class RoleManageController {

	@Autowired
	private RoleServiceI roleService;
	
	private static Logger logger = Logger.getLogger(RoleManageController.class);
	
	@RequestMapping("/getAll")
	public String getAll(Model model){
		List<Role> roles=roleService.selectRoleUser(null);
		model.addAttribute("list", roles);
		logger.info(JSON.toJSONStringWithDateFormat(roles, "yyyy-MM-dd HH:mm:ss"));
		return "role/list";
	}
	
	@RequestMapping("/toAdd")
	public String toAdd(){
		return "role/add";
	}
	
	@RequestMapping("/save")
	public String save(Role r){
		r.setCreatedAt(new Date());
		r.setIsDeleted(0);
		roleService.save(r);
		return "redirect:getAll.do";
	}
	
	@RequestMapping("/edit")
	public String edit(Role r,Model model){
		Role role=roleService.get(r.getId());
		model.addAttribute("role", role);
		return "role/edit";
	}
	
	@RequestMapping("/update")
	public String update(Role r){
		roleService.update(r);
		return "redirect:getAll.do";
	}
	
	@RequestMapping("/delete")
	public String delete(Role r){
		roleService.delete(r.getId());
		return "redirect:getAll.do";
	}
}
