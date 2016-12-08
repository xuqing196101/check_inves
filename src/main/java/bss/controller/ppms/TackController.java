package bss.controller.ppms;


import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.service.bms.DictionaryDataServiceI;
import ses.util.DictionaryDataUtil;

import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
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
	        model.addAttribute("task", task);
	        model.addAttribute("dataId", DictionaryDataUtil.getId("CGJH_ADJUST"));
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
	        Task task = taskservice.selectById(id);
	       // CollectPlan queryById = collectPlanService.queryById(task.getCollectId());
	        List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
	        List<String> list = conllectPurchaseService.getNo(task.getCollectId());
	        if(list != null && list.size()>0){
	            for(String s:list){
	                Map<String,Object> map=new HashMap<String,Object>();
	                map.put("planNo", s);
	                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
	                if(list2 != null && list2.size()>0){
	                listp.addAll(list2);
	                }
	            }
	        }
	        model.addAttribute("kind", DictionaryDataUtil.find(5));
	        model.addAttribute("dataId", DictionaryDataUtil.getId("CGJH_ADJUST"));
	        model.addAttribute("task", task);
	        model.addAttribute("lists", listp);
	       // model.addAttribute("queryById", queryById);
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
        model.addAttribute("kind", DictionaryDataUtil.find(5));
        model.addAttribute("lists", listp);
        model.addAttribute("task", task);
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
    public String updateById(@Valid Task task, BindingResult result, String id, PurchaseRequiredFormBean list, Model model){
	    List<PurchaseRequired> listp=new LinkedList<PurchaseRequired>();
        List<String> lists = conllectPurchaseService.getNo(task.getCollectId());
        if(list != null && lists.size()>0){
            for(String s:lists){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("planNo", s);
                List<PurchaseRequired> list2 = purchaseRequiredService.getByMap(map);
                if(list2 != null && list2.size()>0){
                listp.addAll(list2);
                }
            }
        }
	    if(result.hasErrors()){
	        List<FieldError> errors=result.getFieldErrors();
	        for(FieldError fieldError:errors){
                model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
            }
	        model.addAttribute("lists", listp);
	        model.addAttribute("task", task);
	        return "bss/ppms/task/edit";
	    }
	    if(task.getName().length()>12){
            model.addAttribute("ERR_name", "字符太大");
            model.addAttribute("lists", listp);
            model.addAttribute("task", task);
            return "bss/ppms/task/edit";
        }
	    if(task.getDocumentNumber().length()>12){
            model.addAttribute("ERR_documentNumber", "字符太大");
            model.addAttribute("lists", listp);
            model.addAttribute("task", task);
            return "bss/ppms/task/edit";
        }
	    taskservice.update(task);
        if(list!=null){
            if(list.getList()!=null&&list.getList().size()>0){
                for( PurchaseRequired p:list.getList()){
                    if( p.getId()!=null){
                        purchaseRequiredService.updateByPrimaryKeySelective(p);
                    }else{
                        String ids = UUID.randomUUID().toString().replaceAll("-", "");
                        p.setId(ids);
                        purchaseRequiredService.add(p);
                    }
                
                    
                }
            }
        }
        return "redirect:list.html";
    }
	
	
	
	@ResponseBody
	@RequestMapping("/verify")
	public String verify(String name, String documentNumber, Model model){
	    Task task = new Task();
	    task.setDocumentNumber(documentNumber);
	    Boolean flag = taskservice.verify(task);
        return JSON.toJSONString(flag);
	}
}