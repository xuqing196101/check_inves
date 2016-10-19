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

import ses.model.oms.PurchaseInfo;
import ses.service.oms.PurchaseServiceI;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectAttachments;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.Task;
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
	private PurchaseRequiredService purchaseRequiredService;
	@Autowired
	private ProjectDetailService detailService;
	@Autowired
	private PackageService packageService;
	@Autowired
	private ProjectTaskService projectTaskService;
	@Autowired
	private PurchaseServiceI purchaseService;
	
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
		PurchaseRequired p=new PurchaseRequired();
		List<PurchaseRequired> list = purchaseRequiredService.query(p,0);
		model.addAttribute("list", list);
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
	public String create(String id, Model model, HttpServletRequest request){
		ProjectDetail projectDetail = new ProjectDetail();
		String[] ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			PurchaseRequired purchaseRequired = purchaseRequiredService.queryById(ids[i]);
			projectDetail.setId(purchaseRequired.getId());
			projectDetail.setSerialNumber(purchaseRequired.getSeq());
			projectDetail.setDepartment(purchaseRequired.getDepartment());
			projectDetail.setGoodsName(purchaseRequired.getGoodsName());
			projectDetail.setStand(purchaseRequired.getStand());
			projectDetail.setQualitStand(purchaseRequired.getQualitStand());
			projectDetail.setItem(purchaseRequired.getItem());
			if(purchaseRequired.getPurchaseCount() != null){
				String purchaseCount = purchaseRequired.getPurchaseCount().toString();
				projectDetail.setPurchaseCount(Integer.valueOf(purchaseCount));
			}
			if(purchaseRequired.getPrice() != null){
				projectDetail.setPrice(purchaseRequired.getPrice().doubleValue());
			}
			if(purchaseRequired.getBudget() != null){
				projectDetail.setBudget(purchaseRequired.getBudget().doubleValue());
			}
			if(purchaseRequired.getDeliverDate() != null){
				projectDetail.setDeliverDate(purchaseRequired.getDeliverDate());
			}
			if(purchaseRequired.getPurchaseType() != null){
				projectDetail.setPurchaseType(purchaseRequired.getPurchaseType());
			}
			
			if(purchaseRequired.getSupplier() != null){
				projectDetail.setSupplier(purchaseRequired.getSupplier());
			}
			if(purchaseRequired.getIsFreeTax() != null){
				projectDetail.setIsFreeTax(purchaseRequired.getIsFreeTax());
			}
			if(purchaseRequired.getGoodsUse() != null){
				projectDetail.setGoodsUse(purchaseRequired.getGoodsUse());
			}
			if(purchaseRequired.getUseUnit() != null){
				projectDetail.setUseUnit(purchaseRequired.getUseUnit());
			}
			if(purchaseRequired.getParentId() != null){
				projectDetail.setParentId(purchaseRequired.getParentId());
			}
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
	public String createProject(String name,String projectNumber,String purchaseType,HttpServletRequest request){
	    Project project = new Project();
		if(name != null && projectNumber != null){
			project.setName(name);
			project.setProjectNumber(projectNumber);
			project.setPurchaseType(purchaseType);
			project.setCreateAt(new Date());
			project.setStatus(3);
			projectService.add(project);
		}
		String ide = (String) request.getSession().getAttribute("idr");
		request.getSession().removeAttribute("idr");
		String[] projectId = ide.split(",");
		for (int i = 0; i < projectId.length; i++) {
			ProjectDetail detail = detailService.selectByPrimaryKey(projectId[i]);
			detail.setProject(new Project(project.getId()));
			detailService.update(detail);
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
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		List<ProjectDetail> detail = detailService.selectById(map);
		model.addAttribute("lists", detail);
		return "bss/ppms/project/viewDetail";
	}
	/**
	 * 
	* @Title: edit
	* @author FengTian
	* @date 2016-10-12 上午9:12:00  
	* @Description: 根据项目id查询项目明细
	* @param @param id
	* @param @param model
	* @param @param page
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/edit")
	public String edit(String id, Model model, Integer page, HttpServletRequest request){
		HashMap<String,Object> map = new HashMap<String,Object>();
		Project project = projectService.selectById(id);
		map.put("id", id);
		List<ProjectDetail> detail = detailService.selectById(map);
		for (ProjectDetail projectDetail : detail) {
			if("1".equals(projectDetail.getParentId())){
				map.put("id", projectDetail.getId());
				List<ProjectDetail> detail1 = detailService.selectByParentId(map);
				model.addAttribute("lists", detail1);
			}
			
		}
		model.addAttribute("project", project);
		return "bss/ppms/project/editDetail";
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
	public void editDetail(String id,String purchaseCount,String price,String purchaseType,String budget,Model model){
		String[] idc = id.split(",");
		String[] ida = purchaseCount.split(",");
		String[] idb = price.split(",");
		String[] ide = purchaseType.split(",");
		String[] idf = budget.split(",");
		for (int i = 0; i < idc.length; i++) {
			ProjectDetail qq = detailService.selectByPrimaryKey(idc[i]);
			if(ida[i] != null && ida[i].trim().length() != 0){
				qq.setPurchaseCount(Integer.valueOf(ida[i]));
			}
			if(idb[i] != null && idb[i].trim().length() != 0){
				qq.setPrice(Double.valueOf(idb[i]));
			}
			qq.setPurchaseType(ide[i]);
			if(idf[i] != null && idf[i].trim().length() != 0){
				qq.setBudget(Double.valueOf(idf[i]));
			}
			detailService.update(qq);
		}
		
	}
	
	@RequestMapping("/print")
	public String print(String id,Model model){
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bss/ppms/project/print";
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
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		List<ProjectDetail> detail = detailService.selectById(map);
		model.addAttribute("lists", detail);
		HashMap<String,Object> pack = new HashMap<String,Object>();
		pack.put("projectId", id);
		List<Packages> packages = packageService.findPackageById(pack);
		if(packages.size()!=0){
			for(Packages ps:packages){
				HashMap<String,Object> packageId = new HashMap<String,Object>();
				packageId.put("packageId", ps.getId());
				List<ProjectDetail> detailList = detailService.selectById(packageId);
				ps.setProjectDetails(detailList);
			}
		}
		model.addAttribute("packageList", packages);
		Project project = projectService.selectById(id);
		model.addAttribute("project", project);
		return "bssms/project/sub_package";
	}
/**
	 * 
	* @Title: checkProjectDeail
	* @author ZhaoBo
	* @date 2016-10-18 上午10:01:35  
	* @Description: 递归选中 
	* @param @param response
	* @param @param id
	* @param @param model
	* @param @throws IOException      
	* @return void
	 */
	@RequestMapping("checkProjectDeail")
	public void checkProjectDeail(HttpServletResponse response,HttpServletRequest request) throws IOException{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ProjectDetail projectDetail = detailService.selectByPrimaryKey(request.getParameter("id"));
		if("1".equals(projectDetail.getParentId())){
			map.put("id", projectDetail.getId());
			List<ProjectDetail> list = detailService.selectByParentId(map);
			String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("textml;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		}
		map.put("id", projectDetail.getId());
		List<ProjectDetail> list = detailService.selectByParent(map);
		String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
		response.setContentType("textml;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
	}
/**
	 * 
	* @Title: addPack
	* @author ZhaoBo
	* @date 2016-10-18 下午2:42:15  
	* @Description: 添加分包 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addPack")
	@ResponseBody
	public String addPack(HttpServletRequest request){
		String[] id = request.getParameter("id").split(",");
		String projectId = request.getParameter("projectId");
		HashMap<String,Object> pack = new HashMap<String,Object>();
		pack.put("projectId",projectId);
		List<Packages> packList = packageService.findPackageById(pack);
		Packages pg = new Packages();
		pg.setName("第"+(packList.size()+1)+"包");
		pg.setProjectId(projectId);
		pg.setCreatedAt(new Date());
		packageService.insertSelective(pg);
		List<Packages> wantPackId = packageService.findPackageById(pack);
		for(int i=0;i<id.length;i++){
			ProjectDetail projectDetail = new ProjectDetail();
			projectDetail.setId(id[i]);
			projectDetail.setPackageId(wantPackId.get(0).getId());
			detailService.update(projectDetail);
		}
		return "1";
	}
/**
	 * 
	* @Title: editPackName
	* @author ZhaoBo
	* @date 2016-10-18 下午2:41:47  
	* @Description: 修改包名 
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editPackName")
	@ResponseBody
	public String editPackName(HttpServletRequest request){
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		Packages pk = new Packages();
		pk.setId(id);
		pk.setName(name);
		packageService.updateByPrimaryKeySelective(pk);
		return "1";
	}
/**
	 * 
	* @Title: deletePackageById
	* @author ZhaoBo
	* @date 2016-10-18 下午3:15:18  
	* @Description: 删除分包 
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/deletePackageById")
	@ResponseBody
	public String deletePackageById(HttpServletRequest request){
		String id = request.getParameter("id");
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("packageId", id);
		List<ProjectDetail> detail = detailService.selectById(map);
		for(int i=0;i<detail.size();i++){
			ProjectDetail projectDetail = new ProjectDetail();
			projectDetail.setId(detail.get(i).getId());
			projectDetail.setPackageId(" ");
			detailService.update(projectDetail);
		}
		packageService.deleteByPrimaryKey(id);
		return "1";
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
