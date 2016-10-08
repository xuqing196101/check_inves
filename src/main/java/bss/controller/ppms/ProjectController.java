package bss.controller.ppms;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.Task;
import bss.model.ppms.TaskAttachments;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.TaskService;

@Controller
@Scope("prototype")
@RequestMapping("/project")
public class ProjectController extends BaseController{
	
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskservice;
	@Autowired
	private ProjectAttachmentsService attachmentsService;
	@Autowired
	private CollectPlanService collectPlanService; 
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
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
		model.addAttribute("projects", project);
		return "bss/ppms/project/list";
	}
	/**
	 * 
	* @Title: add
	* @author FengTian
	* @date 2016-9-28 上午10:23:30  
	* @Description: 跳转添加页面
	* @param @return      
	* @return String
	 */
	@RequestMapping("/add")
	public String add(Integer page,Model model,Task task){
		List<Task> list = taskservice.listAll(page==null?1:page, task);
		PageInfo<Task> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("task", task);
		return "bss/ppms/project/add";
	}
	/**
	 * 
	* @Title: create
	* @author FengTian
	* @date 2016-9-28 下午1:58:47  
	* @Description: 根据id查询出任务跳转新增项目页面 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/create")
	public String create(String id,HttpServletRequest request){
		request.getSession().setAttribute("ids", id);
		return "bss/ppms/project/addProject";
	}
	/**
	 * 
	* @Title: createProject
	* @author FengTian
	* @date 2016-9-28 下午1:59:36  
	* @Description: 添加新项目 
	* @param @param name
	* @param @param projectNumber
	* @param @param task
	* @param @return      
	* @return String
	 */
	@RequestMapping("/createProject")
	public String createProject(String name,String projectNumber,HttpServletRequest request){
		if(name != null && projectNumber != null){
			Project project = new Project();
			project.setName(name);
			project.setProjectNumber(projectNumber);
			project.setStatus(3);
			projectService.add(project);
			String id = (String) request.getSession().getAttribute("ids");
			request.getSession().removeAttribute("ids");
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				Task task = taskservice.selectById(ids[i]);
				task.setProject(new Project(project.getId()));
				taskservice.update(task);
			}
		}
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: view
	* @author FengTian
	* @date 2016-9-29 下午5:11:38  
	* @Description: 查看项目 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(String id,Model model,Integer page){
		List<Task> list = taskservice.selectByProject(id, page==null?1:page);
		Project ject = projectService.selectById(id);
		PageInfo<Task> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("ject", ject);
		return "bss/ppms/project/view";
	}
	
	@RequestMapping("edit")
	public String edit(String id,Model model,Integer page){
		List<Task> list = taskservice.selectByProject(id, page==null?1:page);
		Project ject = projectService.selectById(id);
		PageInfo<Task> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("ject", ject);
		return "bss/ppms/project/edit";
	}
	/**
	 * 
	* @Title: startProject
	* @author FengTian
	* @date 2016-10-8 下午2:17:39  
	* @Description: 启动项目 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/startProject")
	public String startProject(String id,Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/upload";
	}
	/**
	 * 
	* @Title: viewDetail
	* @author FengTian
	* @date 2016-10-8 下午2:16:44  
	* @Description: 查看明细 
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewDetail")
	public String viewDetail(String id,Model model){
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.get(queryById);
		List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
		model.addAttribute("queryById", queryById);
		model.addAttribute("lists", list);
		return "bss/ppms/project/viewDetail";
	}
	
	/**
	 * 
	* @Title: upfile
	* @author FengTian
	* @date 2016-10-8 下午2:18:09  
	* @Description: 上传 
	* @param @param attach
	* @param @param request
	* @param @param project      
	* @return void
	 */
	public void upfile( MultipartFile[] attach,
            HttpServletRequest request,Project project){
		if(attach!=null){
			for(int i=0;i<attach.length;i++){
				if(attach[i].getOriginalFilename()!=null && attach[i].getOriginalFilename()!=""){
			        String rootpath = (request.getSession().getServletContext().getRealPath("/")+"upload/").replace("\\", "/");
			        /** 创建文件夹 */
					File rootfile = new File(rootpath);
					if (!rootfile.exists()) {
						rootfile.mkdirs();
					}
			        String fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + attach[i].getOriginalFilename();
			        String filePath = rootpath+fileName;
			        File file = new File(filePath);
			        try {
			        	attach[i].transferTo(file);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					ProjectAttachments attachment=new ProjectAttachments();
					attachment.setProject(new Project(project.getId()));
					attachment.setFileName(fileName);
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setContentType(attach[i].getContentType());
					attachment.setFileSize((float)attach[i].getSize());
					attachment.setAttachmentPath(filePath);
					attachmentsService.save(attachment);
				}
			}
		}
	}
}
