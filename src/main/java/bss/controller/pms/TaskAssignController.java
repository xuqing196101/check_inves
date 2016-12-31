package bss.controller.pms;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.ppms.TaskService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

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
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
	
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
	public String list(@CurrentUser User user,CollectPlan collectPlan,Integer page,Model model){
		collectPlan.setStatus(2);
		collectPlan.setUserId(user.getId());
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
		List<String>  list=new LinkedList<String>();
		String[] ids = cid.split(",");
		String purchaseType=null;
		String planType=null;
		for(int i=0;i<ids.length;i++){
			CollectPlan plan = collectPlanService.queryById(ids[i]);
			purchaseType=plan.getPurchaseType();
			planType=plan.getGoodsType();
			List<String> uid = collectPurchaseService.getNo(ids[i]);
			list.addAll(uid);
			plan.setStatus(2);
			collectPlanService.update(plan);
		
		}
		
		List<Map<String, Object>> org = purchaseRequiredService.queryOrg(list);
		for(Map<String,Object> map:org){
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				task.setId(id);
			
//			if(plan.getPurchaseType()!=null){
				task.setProcurementMethod(purchaseType);
//			}
//			if(plan.getDepartment()!=null){
//				task.setPurchaseRequiredId(plan.getDepartment());
//			}
				//采购管理部门
				task.setOrgId(user.getOrg().getId());
				//采购机构
				String str =(String) map.get("ORGANIZATIONID");
				task.setPurchaseId(str);
				task.setStatus(0);
				task.setIsDeleted(0);
				task.setGiveTime(new Date());
				task.setMaterialsType(planType);
				task.setNotDetail(0);
				task.setCollectId(ids[0]);
				task.setTaskNature(0);
				taskservice.add(task);
				
				
				
		}
		
		
//		String[] ids = cid.split(",");
//		for(int i=0;i<ids.length;i++){
//			CollectPlan plan = collectPlanService.queryById(ids[i]);
//			String id = UUID.randomUUID().toString().replaceAll("-", "");
//			task.setId(id);
//			if(plan.getPurchaseType()!=null){
//				task.setProcurementMethod(plan.getPurchaseType());
//			}
//			if(plan.getDepartment()!=null){
//				task.setPurchaseRequiredId(plan.getDepartment());
//			}
			/*if(user.getOrg()!=null){
				task.setPurchaseId(user.getOrg().getName());
			}*/
//			task.setYear(plan.getCreatedAt());
//			task.setStatus(0);
//			task.setIsDeleted(0);
//			task.setGiveTime(new Date());
//			task.setNotDetail(0);
//			if(plan.getGoodsType()!=null){
//				task.setMaterialsType(plan.getGoodsType());
//			}
//			task.setCollectId(ids[i]);
//			task.setTaskNature(0);
//			plan.setTaskId(id);
//			plan.setStatus(2);
//			 collectPlanService.update(plan);
//			taskservice.add(task);
//		}
		
		return "redirect:list.html";
	}
	
	public String getRandomString() { //length表示生成字符串的长度  
	    String chars = "abcdefghijklmnopqrstuvwxyz";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < 3; i++) {     
	        int number = random.nextInt(chars.length());     
	        sb.append(chars.charAt(number));     
	    } 
	    String num=" 0123456789";
	    for(int i=0;i<3;i++){
	    	 int number = random.nextInt(num.length());     
		        sb.append(chars.charAt(number));     
	    }
	   
	    
	    System.out.print(sb.toString());
	    return sb.toString();     
	 }   
	
	public static void main(String[]args){
		 String chars = "abcdefghijklmnopqrstuvwxyz";     
		    Random random = new Random();     
		    StringBuffer sb = new StringBuffer();     
		    for (int i = 0; i < 3; i++) {     
		        int number = random.nextInt(chars.length());     
		        sb.append(chars.charAt(number));     
		    } 
		    String num="0123456789";
		    for(int i=0;i<3;i++){
		    	 int number = random.nextInt(num.length());     
			        sb.append(num.charAt(number));     
		    }
		   
		    
		    System.out.print(sb.toString());
	}
	
}
