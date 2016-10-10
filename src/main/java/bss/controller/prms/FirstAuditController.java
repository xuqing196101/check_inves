package bss.controller.prms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.prms.FirstAudit;
import bss.service.prms.FirstAuditService;

@Controller
@RequestMapping("/firstAudit")
public class FirstAuditController {
	@Autowired
	private FirstAuditService service;
	/**
	 * 
	  * @Title: toAdd
	  * @author ShaoYangYang
	  * @date 2016年10月9日 上午11:10:20  
	  * @Description: TODO 跳转到初审项定义页面
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("/toAdd")
	public String toAdd(String projectId,Model model ){
		try {
			List<FirstAudit> list = service.getListByProjectId(projectId);
			model.addAttribute("list", list);
			model.addAttribute("projectId", projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bss/prms/first_audit";
	}
	/**
	 * 
	  * @Title: add
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午1:49:47  
	  * @Description: TODO 新增初审项定义
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("add")
	public String add(FirstAudit firstAudit,Model model,RedirectAttributes attr){
		service.add(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: remove
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:38:47  
	  * @Description: TODO 删除
	  * @param @param id
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("remove")
	public String remove(String id,RedirectAttributes attr){
		service.delete(id);
		attr.addAttribute("projectId", id);
		return "redirect:toAdd.html";
	}
	/**
	 * 
	  * @Title: toEdit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:56:24  
	  * @Description: TODO 跳转到修改页面
	  * @param @param id
	  * @param @param model
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("toEdit")
	public String toEdit(String id,Model model){
		FirstAudit firstAudit = service.get(id);
		model.addAttribute("firstAudit", firstAudit);
		return "bss/prms/edit";
	}
	/**
	 * 
	  * @Title: edit
	  * @author ShaoYangYang
	  * @date 2016年10月9日 下午4:59:39  
	  * @Description: TODO 修改
	  * @param @param firstAudit
	  * @param @param attr
	  * @param @return      
	  * @return String
	 */
	@RequestMapping("edit")
	public String edit(FirstAudit firstAudit,RedirectAttributes attr){
		service.updateAll(firstAudit);
		attr.addAttribute("projectId", firstAudit.getProjectId());
		return "redirect:toAdd.html";
	}
}
