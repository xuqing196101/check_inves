package bss.controller.ppms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.ppms.Project;
import bss.service.ppms.ProjectService;


@Controller
@Scope("prototype")
@RequestMapping("/open_bidding")
public class OpenBiddingController {
	@Autowired
	private ProjectService projectService;
	
	@RequestMapping("/list")
	public String list(Integer page,Model model,Project project){
		List<Project> list = projectService.list(page==null?1:page, project);
		PageInfo<Project> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("projects", project);
		return "bss/ppms/excution/list";
	}
	
}
