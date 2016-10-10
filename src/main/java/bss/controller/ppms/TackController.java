package bss.controller.ppms;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Task;
import bss.model.ppms.TaskAttachments;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.TaskAttachmentsService;
import bss.service.ppms.TaskService;

/**
 * 
* @Title:TackController
* @Description: 任务管理控制类
* @author FengTian
* @date 2016-9-18下午4:35:18
 */
@Controller
@Scope("prototype")
@RequestMapping("/task")
public class TackController extends BaseController{
	@Autowired
	private TaskService taskservice;
	@Autowired
	private TaskAttachmentsService taskAttachmentsService;
	@Autowired
	private CollectPlanService collectPlanService; 
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	
	/**
	 * 
	* @Title: listAll
	* @author FengTian
	* @date 2016-9-18 下午4:45:44  
	* @Description: 分页查询
	* @param @param page
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/list")
	public String list(Integer page,Model model,Task task){
		List<Task> list = taskservice.listAll(page==null?1:page, task);
		PageInfo<Task> info = new PageInfo<Task>(list);
		model.addAttribute("info", info);
		model.addAttribute("task", task);
		return "bss/ppms/task/list";
	}
	/**
	 * 
	* @Title: deleteTask
	* @author FengTian
	* @date 2016-9-21 下午5:48:42  
	* @Description: 跳转取消任务页面
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/deleteTask")
	public String deleteTask(String id,Model model){
		Task task = taskservice.selectById(id);
		model.addAttribute("task", task);
		return "bss/ppms/task/delTask";
	}
	/**
	 * 
	* @Title: addFile
	* @author FengTian
	* @date 2016-10-9 上午11:15:23  
	* @Description: 获取修改的内容 
	* @param @param qualitStand
	* @param @param purchaseCount
	* @param @param item
	* @param @param price
	* @param @param request
	* @param @param id
	* @param @param model
	* @param @return      
	* @return String
	 */
	@RequestMapping("/addFile")
	public String addFile(String qualitStand,String purchaseCount,String item,String price,HttpServletRequest request,String id,Model model){
		request.getSession().setAttribute("qualitStand", qualitStand);
		request.getSession().setAttribute("purchaseCount", purchaseCount);
		request.getSession().setAttribute("item", item);
		request.getSession().setAttribute("price", price);
		request.getSession().setAttribute("id", id);
		String ids = (String) request.getSession().getAttribute("ids");
		request.getSession().removeAttribute("ids");
		Task task = taskservice.selectById(ids);
		model.addAttribute("task", task);
		return "bss/ppms/task/addFile";
	}
	
	public void upfile( MultipartFile[] attach,
            HttpServletRequest request,Task task){
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
					TaskAttachments attachment=new TaskAttachments();
					attachment.setTask(new Task(task.getId()));
					attachment.setFileName(fileName);
					attachment.setCreatedAt(new Date());
					attachment.setUpdatedAt(new Date());
					attachment.setContentType(attach[i].getContentType());
					attachment.setFileSize((float)attach[i].getSize());
					attachment.setAttachmentPath(filePath);
					taskAttachmentsService.save(attachment);
				}
			}
		}
	
		
	}
	/**
	 * 
	* @Title: delTask
	* @author FengTian
	* @date 2016-9-27 下午6:15:00  
	* @Description: 取消任务 
	* @param @param attach
	* @param @param task
	* @param @param request
	* @param @param id
	* @param @return      
	* @return String
	 */
	@RequestMapping("/delTask")
	public String delTask(@RequestParam("attach") MultipartFile[] attach,Task task,HttpServletRequest request,String id){
    	taskservice.softDelete(id);
		upfile(attach, request, task);
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: editDetail
	* @author FengTian
	* @date 2016-10-9 上午11:14:54  
	* @Description: 需求明细调整 
	* @param @param attach
	* @param @param task
	* @param @param purchaseRequired
	* @param @param request
	* @param @return      
	* @return String
	 */
	@RequestMapping("/editDetail")
	public String editDetail(@RequestParam("attach") MultipartFile[] attach,Task task,PurchaseRequired purchaseRequired,HttpServletRequest request){
		String qualitStand = (String) request.getSession().getAttribute("qualitStand");
		String item = (String) request.getSession().getAttribute("item");
		String purchaseCount = (String) request.getSession().getAttribute("purchaseCount");
		String price = (String) request.getSession().getAttribute("price");
		String id = (String) request.getSession().getAttribute("id");
		request.getSession().removeAttribute("qualitStand");
		request.getSession().removeAttribute("item");
		request.getSession().removeAttribute("purchaseCount");
		request.getSession().removeAttribute("price");
		request.getSession().removeAttribute("id");
		upfile(attach, request, task);
		String[] idc = id.split(",");
		String[] ids = qualitStand.split(",");
		String[] ide = item.split(",");
		String[] ida = purchaseCount.split(",");
		String[] idb = price.split(",");
		for (int i = 0; i < idc.length; i++) {
			PurchaseRequired qq = purchaseRequiredService.queryById(idc[i]);
			qq.setQualitStand(ids[i]);
			qq.setItem(ide[i]);
			qq.setPurchaseCount(Long.valueOf(ida[i]));
			qq.setPrice(new BigDecimal(idb[i]));
			qq.setBudget(new BigDecimal(Long.valueOf(ida[i])).multiply(new BigDecimal(idb[i])));
			purchaseRequiredService.update(qq);
		}
		return "redirect:list.html";
	}
	/**
	 * 
	* @Title: startTask
	* @author FengTian
	* @date 2016-9-30 上午10:47:12  
	* @Description: 启动任务 
	* @param @param ids      
	* @return void
	 */
	@RequestMapping("/startTask")
	@ResponseBody
	public void startTask(String ids){
		String[] ide = ids.split(",");
		for (int i = 0; i < ide.length; i++) {
			 taskservice.startTask(ide[i]);
		}
		
	}
	
	@RequestMapping("/edit")
	public String edit(String id,Model model,HttpServletRequest request){
		request.getSession().setAttribute("ids", id);
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		if(queryById != null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.get(queryById);
			List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
			model.addAttribute("queryById", queryById);
			model.addAttribute("lists", list);
		}
	
		return "bss/ppms/task/edit";
	}

	@RequestMapping("/view")
	public String view(String id,Model model){
		Task task = taskservice.selectById(id);
		CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
		if(queryById != null){
			Map<String,Object> map = new HashMap<String,Object>();
			map.get(queryById);
			List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
			model.addAttribute("queryById", queryById);
			model.addAttribute("lists", list);
		}
		return "bss/ppms/task/view";
	}
}