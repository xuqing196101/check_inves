package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;

@Controller
@Scope("prototype")
@RequestMapping("/project")
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectService projectService;
	
	/**
	 * 
	* @Title: list
	* @author FengTian
	* @date 2016-9-27 上午11:03:34  
	* @Description: 查询全部 
	* @param @param page
	* @param @param model
	* @param @param project
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(Integer page,Model model,Project project){
		List<Project> list = projectService.list(page==null?1:page, project);
		PageInfo<Project> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		return "bss/ppms/project/list";
	}
	

}
