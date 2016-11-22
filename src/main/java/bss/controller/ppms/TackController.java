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

import ses.model.bms.DictionaryData;
import ses.service.bms.DictionaryDataServiceI;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.CollectPurchase;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.Task;
import bss.model.ppms.TaskAttachments;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.PackageService;
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
	private CollectPlanService collectPlanService; 
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	@Autowired
	private CollectPurchaseService conllectPurchaseService;
	 @Autowired
	private DictionaryDataServiceI dictionaryDataService;
	
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
	public String delTask(Model model,String id){
	    if(id != null){
	        Task task = taskservice.selectById(id);
	        DictionaryData dictionaryData=new DictionaryData();
	        dictionaryData.setCode("CGJH_ADJUST");
	        String dataId = dictionaryDataService.find(dictionaryData).get(0).getId();
	        model.addAttribute("task", task);
	        model.addAttribute("dataId", dataId);
	    }
	    return "bss/ppms/task/upload";
	}
	
	@RequestMapping("/deleteTask")
    public String deleteTask(Model model,String id){
	    if(id != null){
	        Task task = taskservice.selectById(id);
	        task.setStatus(2);
	        taskservice.update(task); 
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
	    if(ids != null){
	        String[] ide = ids.split(",");
	        for (int i = 0; i < ide.length; i++) {
	             taskservice.startTask(ide[i]);
	             Task task = taskservice.selectById(ide[i]);
	             List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	             if(list != null && list.size()>0){
	                 for (String s : list) {
	                     Map<String,Object> map=new HashMap<String,Object>();
	                        map.put("planNo", s);
	                        List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	                        for (PurchaseRequired purchaseRequired : list2) {
	                            purchaseRequired.setDetailStatus(1);
	                            purchaseRequiredService.updateByPrimaryKeySelective(purchaseRequired);
	                        }
    	                 task.setAcceptTime(new Date());
    	                 taskservice.update(task);
	                 }
	             }
	        }
	    }
	}
	/**
	 * 
	 *〈跳转修改页面〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param id
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(String id,Model model,HttpServletRequest request){
	    if(id != null){
	        DictionaryData dictionaryData=new DictionaryData();
	        Task task = taskservice.selectById(id);
	        CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
	        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
	        List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	        if(list != null && list.size()>0){
	            for(String s:list){
	                Map<String,Object> map=new HashMap<String,Object>();
	                map.put("planNo", s);
	                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	                if(list2 != null && list2.size()>0){
	                    for (PurchaseRequired purchaseRequired : list2) {
	                        dictionaryData = dictionaryDataService.getDictionaryData(purchaseRequired.getPurchaseType());
	                        model.addAttribute("code", dictionaryData.getCode());
                        }
	                }
	                listp.addAll(list2);
	            }
	        }
	        List<DictionaryData> kind = dictionaryDataService.findByKind("5");
	        model.addAttribute("kind", kind);
	        dictionaryData.setCode("CGJH_ADJUST");
	        String dataId = dictionaryDataService.find(dictionaryData).get(0).getId();
	        model.addAttribute("dataId", dataId);
	       
	        model.addAttribute("task", task);
	        model.addAttribute("lists", listp);
	        model.addAttribute("queryById", queryById);
	    }
		return "bss/ppms/task/edit";
	}
	/**
	 * 
	 *〈递归查询〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param response
	 * @param id
	 * @throws IOException
	 */
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
	/**
	 * 
	 *〈跳转查看页面〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param id
	 * @param model
	 * @return
	 */
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
        List<DictionaryData> kind = dictionaryDataService.findByKind("5");
        model.addAttribute("kind", kind);
        model.addAttribute("lists", listp);
        model.addAttribute("queryById", queryById);
		return "bss/ppms/task/view";
	}
	
	/**
	 * 
	 *〈修改〉
	 *〈详细描述〉
	 * @author Administrator
	 * @param ide
	 * @param fileName
	 * @param planNo
	 * @param task
	 * @param list
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
    public String updateById(String ide, String fileName, String planNo,
                             Task task,PurchaseRequiredFormBean list, HttpServletRequest request){
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