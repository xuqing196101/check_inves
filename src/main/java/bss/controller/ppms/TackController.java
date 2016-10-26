package bss.controller.ppms;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.CollectPurchase;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;
import bss.model.ppms.TaskAttachments;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.TaskAttachmentsService;
import bss.service.ppms.TaskService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

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
	@Autowired
	private CollectPurchaseService conllectPurchaseService;
	 @Autowired
	private ProjectDetailService detailService;
	
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
			 Task task = taskservice.selectById(ide[i]);
			 List<String> list = conllectPurchaseService.getNo(task.getCollectId());
			 for (String s : list) {
			     Map<String,Object> map=new HashMap<String,Object>();
		            map.put("planNo", s);
		            List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
		            for (PurchaseRequired purchaseRequired : list2) {
		                purchaseRequired.setDetailStatus(1);
		                purchaseRequiredService.updateByPrimaryKeySelective(purchaseRequired);
		                HashMap<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("requiredId", purchaseRequired.getId());
                        List<ProjectDetail> detail = detailService.selectById(map1);
                        for (ProjectDetail projectDetail : detail) {
                            projectDetail.setStatus(String.valueOf(purchaseRequired.getDetailStatus()));
                            projectDetail.setTaskId(ide[i]);
                            detailService.update(projectDetail);
                        }
                    }
            }
			 task.setAcceptTime(new Date());
			 taskservice.update(task);
		}
		
	}
	
	@RequestMapping("/edit")
	public String edit(String id,Model model,HttpServletRequest request){
	    Task task = taskservice.selectById(id);
	    CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
	    List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
	    List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	    for(String s:list){
	        Map<String,Object> map=new HashMap<String,Object>();
	        map.put("planNo", s);
	        List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	        listp.addAll(list2);
	    }
	    model.addAttribute("task", task);
	    model.addAttribute("lists", listp);
	    model.addAttribute("queryById", queryById);
		return "bss/ppms/task/edit";
	}
	
	@RequestMapping("/viewIds")
    public void viewIds(HttpServletResponse response,String id) throws IOException {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
            String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
    }
	
	@RequestMapping("/view")
	public String view(String id,Model model){
		Task task = taskservice.selectById(id);
        CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
        List<String> list = conllectPurchaseService.getNo(task.getCollectId());
        for(String s:list){
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("planNo", s);
            List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
            listp.addAll(list2);
        }
        model.addAttribute("lists", listp);
        model.addAttribute("queryById", queryById);
		return "bss/ppms/task/view";
	}
	
	
	@RequestMapping("/update")
    public String updateById(@RequestParam("attach") MultipartFile[] attach, String ide, String fileName, String planNo,
                             Task task,PurchaseRequiredFormBean list, HttpServletRequest request){
	    upfile(attach, request, task);
	    CollectPlan collectPlan = collectPlanService.queryById(ide);
	    collectPlan.setFileName(fileName);
	    collectPlan.setPlanNo(planNo);
	    collectPlanService.update(collectPlan);
        if(list!=null){
            if(list.getList()!=null&&list.getList().size()>0){
                for( PurchaseRequired p:list.getList()){
                    if( p.getId()!=null){
                        purchaseRequiredService.updateByPrimaryKeySelective(p);
                    }else{
                        String id = UUID.randomUUID().toString().replaceAll("-", "");
                        p.setId(id);
                        purchaseRequiredService.add(p);
                    }
                
                    
                }
            }
        }
        return "redirect:list.html";
    }
}