package bss.controller.pms;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.ppms.TaskService;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: TaskAssignController
 * @Description: 采购任务下达 
 * @author Li Xiaoxiao
 * @date  2016年9月29日,上午11:12:29
 *
 */
@Controller
@RequestMapping("/taskassgin")
public class TaskAssignController extends BaseController{

	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private TaskService taskservice;
	/**
	 * 
	* @Title: list
	* @Description:任务下达页面 
	* author: Li Xiaoxiao 
	* @param @param collectPlan
	* @param @param page
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String list(CollectPlan collectPlan,Integer page,Model model){
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", collectPlan); 
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		model.addAttribute("org",org);
		return "bss/pms/collect/taskassgin";
	}
	
	/**
	 * 
	* @Title: assgin
	* @Description: 任务下达
	* author: Li Xiaoxiao 
	* @param @param task
	* @param @param cid
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/add")
	public String assgin(Task task,String cid,HttpServletRequest request){
		User user = (User) request.getSession().getAttribute("loginUser");
		String[] ids = cid.split(",");
		for(int i=0;i<ids.length;i++){
			CollectPlan plan = collectPlanService.queryById(ids[i]);
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			task.setId(id);
			if(plan.getPurchaseType()!=null){
				task.setProcurementMethod(plan.getPurchaseType());
			}
			if(plan.getDepartment()!=null){
				task.setPurchaseRequiredId(plan.getDepartment());
			}
			/*if(user.getOrg()!=null){
				task.setPurchaseId(user.getOrg().getName());
			}*/
//			task.setYear(plan.getCreatedAt());
			task.setStatus(1);
			task.setIsDeleted(0);
			task.setGiveTime(new Date());
			if(plan.getGoodsType()!=null){
				task.setMaterialsType(plan.getGoodsType());
			}
			task.setCollectId(ids[i]);
			plan.setTaskId(id);
			plan.setStatus(3);
			 collectPlanService.update(plan);
			taskservice.add(task);
		}
		
		return "redirect:list.html";
	}
}
