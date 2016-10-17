package bss.controller.ppms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseInfo;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.service.oms.PurchaseServiceI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectAttachmentsService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.ProjectTaskService;
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
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private PurchaseServiceI purchaseService;
	@Autowired
	private PurchaseOrgnizationServiceI orgnizationService;
	
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
		PageInfo<Project> info = new PageInfo<Project>(list);
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
	public String add(Integer page,Model model,PurchaseRequired purchaseRequired,HttpServletRequest request){
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page==null?1:page);
		PageInfo<PurchaseRequired> info = new PageInfo<PurchaseRequired>(list);
		model.addAttribute("info", info);
		model.addAttribute("purchaseRequired", purchaseRequired);
		//显示项目明细
		String idr = (String) request.getSession().getAttribute("idr");
		List<ProjectDetail> list2 = new ArrayList<>();
		if(idr != null){
			String[] ids = idr.split(",");
			for (int i = 0; i < ids.length; i++) {
				ProjectDetail detail = detailService.selectByPrimaryKey(ids[i]);
				list2.add(detail);
			}
			model.addAttribute("lists", list2);
			model.addAttribute("idr", idr);
			
		}
		
		
		return "bss/ppms/project/add";
	}
	@RequestMapping("/checkDeail")
	public void checkDeail(HttpServletResponse response, String id, Model model) throws IOException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(id);
		if("1".equals(purchaseRequired.getParentId())){
			map.put("id", purchaseRequired.getId());
			List<PurchaseRequired> list = purchaseRequiredService.selectByParentId(map);
			String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		}
		map.put("id", purchaseRequired.getId());
		List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		
		
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
	public String create(String id,Model model){
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
			
			model.addAttribute("purchaseRequired", purchaseRequired);
		}
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
	public String createProject(String name,String projectNumber,String purchaseId,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("orgId", purchaseId);
		ProjectTask projectTask = new ProjectTask();
		if(name != null && projectNumber != null){
			Project project = new Project();
			List<PurchaseDep> purchaseDeps = orgnizationService.findPurchaseDepList(map);
			for (PurchaseDep purchaseDep : purchaseDeps) {
			project.setName(name);
			project.setProjectNumber(projectNumber);
			project.setPurchaseDep(new PurchaseDep(purchaseId));
			project.setPurchaseDepName(purchaseDep.getName());
			project.setIpone(purchaseDep.getMobile());
			project.setCreateAt(new Date());
			project.setStatus(3);
			projectService.add(project);
			}
			String id = (String) request.getSession().getAttribute("idss");
			String ide = (String) request.getSession().getAttribute("idr");
			request.getSession().removeAttribute("idss");
			request.getSession().removeAttribute("idr");
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				projectTask.setTaskId(ids[i]);
				projectTask.setProjectId(project.getId());
				projectTaskService.insertSelective(projectTask);
			}
			String[] projectId = ide.split(",");
			for (int i = 0; i < projectId.length; i++) {
				ProjectDetail detail = detailService.selectByPrimaryKey(projectId[i]);
				detail.setProject(new Project(project.getId()));
				detailService.update(detail);
		}
		}
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: view
	* @author FengTian
	* @date 2016-9-29 下午5:11:38  
	* @Description: 查看项目下的任务
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/view")
	public String view(String id,Model model,Integer page,HttpServletRequest request){
		request.getSession().setAttribute("tt", id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", id);
		List<Task> lists = taskservice.listBy(null, page==null?1:page);
		List<ProjectTask> list = projectTaskService.queryByNo(map);
		for (ProjectTask projectTask : list) {
			Task task = taskservice.selectById(projectTask.getTaskId());
			lists.add(task);
		}
		PageInfo<Task> info = new PageInfo<Task>(lists);
		Project project = projectService.selectById(id);
		model.addAttribute("info", info);
		model.addAttribute("ject", project);
		return "bss/ppms/project/view";
	}
	/**
	 * 
	* @Title: edit
	* @author FengTian
	* @date 2016-10-12 上午9:12:00  
	* @Description: 根据项目id查询任务跳转修改页面 
	* @param @param id
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(String id,Model model,Integer page,HttpServletRequest request){
		request.getSession().setAttribute("rre", id);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", id);
		List<Task> lists = taskservice.listBy(null, page==null?1:page);
		List<ProjectTask> list = projectTaskService.queryByNo(map);
		for (ProjectTask projectTask : list) {
			Task task = taskservice.selectById(projectTask.getTaskId());
			lists.add(task);
		}
		PageInfo<Task> info = new PageInfo<Task>(lists);
		Project project = projectService.selectById(id);
		model.addAttribute("info", info);
		model.addAttribute("ject", project);
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
		HashMap<String,Object> map = new HashMap<String,Object>();
		Project project = projectService.selectById(id);
		map.put("purchaseDepName", project.getPurchaseDepName());
		List<PurchaseInfo> purchaseInfo = purchaseService.findPurchaseList(map);
		model.addAttribute("purchaseInfo", purchaseInfo);
		model.addAttribute("project", project);
		return "bss/ppms/project/upload";
	}
	/**
	 * 
	* @Title: start
	* @author FengTian
	* @date 2016-10-12 下午2:08:10  
	* @Description: 上传项目批文
	* @param @param attach
	* @param @param project
	* @param @param principal
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/start")
	public String start(@RequestParam("attach") MultipartFile[] attach,Project project,String principal,HttpServletRequest request){
		project.setPrincipal(principal);
		project.setStatus(1);
		project.setStartTime(new Date());
		projectService.update(project);
		upfile(attach, request, project);
		return "redirect:excute.html?id="+project.getId();
	}
	@RequestMapping("/mplement")
	public String starts(String id,Model model,Integer page){
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("projectId", id);
		Project project = projectService.selectById(id);
		List<Task> lists = taskservice.listBy(null, page==null?1:page);
		List<ProjectTask> list = projectTaskService.queryByNo(map);
		for (ProjectTask projectTask : list) {
			Task task = taskservice.selectById(projectTask.getTaskId());
			lists.add(task);
		}
		PageInfo<Task> info = new PageInfo<Task>(lists);
		model.addAttribute("info", info);
		model.addAttribute("project", project);
		return "bss/ppms/project/essential_information";
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
	public String viewDetail(String id,String ids,Integer page,Model model,HttpServletRequest request){
		if(ids == null){
			HashMap<String,Object> map = new HashMap<String,Object>();
			String tt = (String) request.getSession().getAttribute("tt");
			map.put("taskId", id);
			map.put("id", tt);
			List<ProjectDetail> detailList = detailService.selectById(map);
			model.addAttribute("lists", detailList);
			return "bss/ppms/project/viewDetail";
		}else{
			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("id", ids);
			List<ProjectDetail> detailList = detailService.selectById(map);
			Project project = projectService.selectById(ids);
			List<Task> lists = taskservice.listBy(null, page==null?1:page);
			List<ProjectTask> list = projectTaskService.queryByNo(map);
			for (ProjectTask projectTask : list) {
				Task task = taskservice.selectById(projectTask.getTaskId());
				lists.add(task);
			}
			PageInfo<Task> info = new PageInfo<Task>(lists);
			model.addAttribute("info", info);
			model.addAttribute("project", project);
				model.addAttribute("lists", detailList);
				model.addAttribute("ids", ids);
			return "bss/ppms/project/essential_information";
		}
		
	}
	/**
	 * 
	* @Title: update
	* @author FengTian
	* @date 2016-10-13 下午2:05:14  
	* @Description: 修改项目 
	* @param @param id
	* @param @param name
	* @param @param projectNumber
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/update")
	public String update(String id,String name,String projectNumber,Model model,HttpServletRequest request){
		Project project = projectService.selectById(id);
		project.setName(name);
		project.setProjectNumber(projectNumber);
		projectService.update(project);
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: viewDet
	* @author FengTian
	* @date 2016-10-12 上午9:13:15  
	* @Description: 新增查询采购明细 
	* @param @param id
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/viewDet")
	public String viewDet(String id,Model model,HttpServletRequest request){
		request.getSession().setAttribute("qq",id);
		String idss = (String) request.getSession().getAttribute("idss");
		if (idss != null) {
			idss = idss + "," + id;
			request.getSession().setAttribute("idss", idss);
		} else {
			request.getSession().setAttribute("idss",
					id);
		}
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		if(queryById != null){
		Map<String,Object> map = new HashMap<String,Object>();
		map.get(queryById);
		List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
		model.addAttribute("queryById", queryById);
		model.addAttribute("lists", list);
		}
		return "bss/ppms/project/saveDetail";
	}
	/**
	 * 
	* @Title: saveDetail
	* @author FengTian
	* @date 2016-10-12 上午9:13:40  
	* @Description: 新增项目明细 
	* @param @param id
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/saveDetail")
	public String saveDetail(String id,Model model,HttpServletRequest request){
		String[] ids = id.split(",");
		String ida = (String) request.getSession().getAttribute("qq");
		for (int i = 0; i < ids.length; i++) {
			PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
			ProjectDetail projectDetail = new ProjectDetail();
			projectDetail.setSerialNumber(purchaseRequired.getSeq());
			projectDetail.setDepartment(purchaseRequired.getDepartment());
			projectDetail.setGoodsName(purchaseRequired.getGoodsName());
			projectDetail.setStand(purchaseRequired.getStand());
			projectDetail.setQualitStand(purchaseRequired.getQualitStand());
			projectDetail.setItem(purchaseRequired.getItem());
			String purchaseCount = purchaseRequired.getPurchaseCount().toString();
			projectDetail.setPurchaseCount(Integer.valueOf(purchaseCount));
			projectDetail.setPrice(purchaseRequired.getPrice().doubleValue());
			projectDetail.setBudget(purchaseRequired.getBudget().doubleValue());
			projectDetail.setDeliverDate(purchaseRequired.getDeliverDate());
			projectDetail.setPurchaseType(purchaseRequired.getPurchaseType());
			projectDetail.setSupplier(purchaseRequired.getSupplier());
			projectDetail.setIsFreeTax(purchaseRequired.getIsFreeTax());
			projectDetail.setGoodsUse(purchaseRequired.getGoodsUse());
			projectDetail.setUseUnit(purchaseRequired.getUseUnit());
			projectDetail.setTaskId(ida);
			detailService.insert(projectDetail);
			String ide = projectDetail.getId();
			String idr = (String) request.getSession().getAttribute("idr");
			if (idr != null) {
				idr = idr + "," + ide;
				request.getSession().setAttribute("idr", idr);
			} else {
				request.getSession().setAttribute("idr",
						ide);
			}
		}
		return "redirect:add.html";
		 
	}
	/**
	 * 
	* @Title: editDet
	* @author FengTian
	* @date 2016-10-12 上午9:14:01  
	* @Description: 根据项目id查询任务跳转修改明细页面 
	* @param @param id
	* @param @param model
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editDet")
	public String editDet(String id,Model model,HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		String tt = (String) request.getSession().getAttribute("rre");
		request.getSession().removeAttribute("tt");
		map.put("status", id);
		map.put("id", tt);
		List<ProjectDetail> detailList = detailService.selectById(map);
		Project project = projectService.selectById(tt);
		model.addAttribute("lists", detailList);
		model.addAttribute("ject", project);
		return "bss/ppms/project/eidtDetail";
	}
	/**
	 * 
	* @Title: editDetail
	* @author FengTian
	* @date 2016-10-12 上午9:15:08  
	* @Description: 修改项目明细 
	* @param @param id
	* @param @param purchaseCount
	* @param @param price
	* @param @param purchaseType
	* @param @param model      
	* @return void
	 */
	@RequestMapping("/editDetail")
	@ResponseBody
	public void editDetail(String id,String purchaseCount,String price,String purchaseType,Model model){
		String[] idc = id.split(",");
		String[] ida = purchaseCount.split(",");
		String[] idb = price.split(",");
		String[] ide = purchaseType.split(",");
		for (int i = 0; i < idc.length; i++) {
			ProjectDetail qq = detailService.selectByPrimaryKey(idc[i]);
			qq.setPurchaseCount(Integer.valueOf(ida[i]));
			qq.setPrice(Double.valueOf(idb[i]));
			qq.setPurchaseType(ide[i]);
			qq.setBudget(Double.valueOf(idb[i])+Double.valueOf(ida[i]));
			detailService.update(qq);
		}
		
	}
	
	@RequestMapping("/print")
	public String print(String id,Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/print";
	}
	
	/**
	 * 
	* @Title: subPackage
	* @author ZhaoBo
	* @date 2016-10-8 下午4:08:11  
	* @Description: 项目分包页面 
	* @param @param request
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/subPackage")
	public String subPackage(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		HashMap<String,Object> pack = new HashMap<String,Object>();
		pack.put("projectId", id);
		List<Packages> packList = packageService.findPackageById(pack);
		if(packList.size()==0){
			Packages pg = new Packages();
			pg.setName("第一包");
			pg.setProjectId(id);
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
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/sub_package";
	}
	
	public List<PurchaseRequired> purList; 
	public String pId;
	
	/**
	 * 
	* @Title: select
	* @author ZhaoBo
	* @date 2016-10-9 下午6:42:25  
	* @Description: 选中效果 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/select")
	@ResponseBody
	public List<PurchaseRequired> select(HttpServletRequest request){
		String id = request.getParameter("id");
		recursion(id);
		List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
		list.addAll(purList);
		return list;
	}
	
	
	
	/**
	 * 
	* @Title: recursion
	* @author ZhaoBo
	* @date 2016-10-9 下午8:03:24  
	* @Description: 递归选中 
	* @param       
	* @return void
	 */
	public void recursion(String id){
		System.out.println(id);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parentId", id);
		List<PurchaseRequired> purchaseRequired = purchaseRequiredService.getByMap(map);
		purList.addAll(purchaseRequired);
		if(purchaseRequired.size()!=0){
			for(int i=0;i<purchaseRequired.size();i++){
				pId = purchaseRequired.get(i).getId();
				recursion(pId);
			}
		}
	}
	
	/**
	 * 
	* @Title: addPackage
	* @author ZhaoBo
	* @date 2016-10-10 上午9:05:18  
	* @Description: 添加分包 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPackage")
	@ResponseBody
	public List<PurchaseRequired> addPackage(HttpServletRequest request){
		List<PurchaseRequired> purchaseRequired = new ArrayList<PurchaseRequired>();
		String[] id = request.getParameter("id").split(",");
		for(int i=0;i<id.length;i++){
			PurchaseRequired pr = purchaseRequiredService.queryById(id[i]);
			purchaseRequired.add(pr);
		}
		return purchaseRequired;
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
	
	/**
	 * Description: 根据项目的采购方式进入不同的实施页面
	 * 
	 * @author Ye MaoLin
	 * @version 2016-10-13
	 * @param projectId
	 * @return String
	 * @exception IOException
	 */
	@RequestMapping("/excute")
	public String execute(String id, Model model, Integer page){
		Project project = projectService.selectById(id);
		if("公开招标".equals(project.getPurchaseType())){
			model.addAttribute("project", project);
			model.addAttribute("page", page);
			return "bss/ppms/open_bidding/main";
		} else if("邀请招标".equals(project.getPurchaseType())){
			model.addAttribute("project", project);
			model.addAttribute("page", page);
			return "bss/ppms/invite_bidding/main";
		} else if("询价".equals(project.getPurchaseType())){
			model.addAttribute("project", project);
			model.addAttribute("page", page);
			return "bss/ppms/enquiry/main";
		} else if("竞争性谈判".equals(project.getPurchaseType())){
			model.addAttribute("project", project);
			model.addAttribute("page", page);
			return "bss/ppms/competitive_negotiation/main";
		} else if("单一来源".equals(project.getPurchaseType())){
			model.addAttribute("project", project);
			model.addAttribute("page", page);
			return "bss/ppms/single_source/main";
		} else {
			return "error";
		}
	}

}
