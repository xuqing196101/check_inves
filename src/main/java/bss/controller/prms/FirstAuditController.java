package bss.controller.prms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.prms.FirstAudit;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.prms.FirstAuditService;

@Controller
@RequestMapping("/firstAudit")
public class FirstAuditController {
	@Autowired
	private FirstAuditService service;
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectService projectService;
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
			//项目分包信息
			HashMap<String,Object> pack = new HashMap<String,Object>();
			pack.put("projectId", projectId);
			List<Packages> packList = packageService.findPackageById(pack);
			if(packList.size()==0){
				Packages pg = new Packages();
				pg.setName("第一包");
				pg.setProjectId(projectId);
				packageService.insertSelective(pg);
				List<ProjectDetail> list = new ArrayList<ProjectDetail>();
				List<Packages> pk = packageService.findPackageById(pack);
				for(int i=0;i<list.size();i++){
					ProjectDetail pd = new ProjectDetail();
					pd.setId(list.get(i).getId());
					pd.setPackageId(pk.get(0).getId());
					detailService.update(pd);
				}
			}
			List<Packages> packages = packageService.findPackageById(pack);
			Map<String,Object> list = new HashMap<String,Object>();
			for(Packages ps:packages){
				list.put("pack"+ps.getId(),ps);
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("packageId", ps.getId());
				List<ProjectDetail> detailList = detailService.selectById(map);
				ps.setProjectDetails(detailList);
			}
			model.addAttribute("packageList", packages);
			Project project = projectService.selectById(projectId);
			model.addAttribute("project", project);
			//初审项信息
			List<FirstAudit> list2 = service.getListByProjectId(projectId);
			model.addAttribute("list", list2);
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
