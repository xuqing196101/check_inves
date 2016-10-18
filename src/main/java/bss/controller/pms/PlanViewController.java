package bss.controller.pms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/view")
public class PlanViewController extends BaseController{

	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
 
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	/**
	 * 
	* @Title: list
	* @Description: 查看 
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
		return "bss/pms/collect/viewcollect";
	}
	
	
	/**
	 * 
	* @Title: detail
	* @Description: 查询明细 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/detail")
	public String detail(String id,Model model){
//		List<String> list=new ArrayList<String>();
//		List<PurchaseRequired> pr=new LinkedList<PurchaseRequired>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		if(id.trim().length()!=0){
//			String[] ids = id.split(",");
//	
//			for(String i:ids){
//				List<String> no = collectPurchaseService.getNo(i);
//				list.addAll(no);
//			}
//		}
//		List<List<PurchaseRequired>> lists=new ArrayList<List<PurchaseRequired>> ();
//		if(list!=null&&list.size()>0){
//			for(String no:list){
				List<PurchaseRequired> list = purchaseRequiredMapper.queryByNo(id);
//				map.put("list", queryByNo);
//				map.put("dep",queryByNo.get(0).getDepartment());
//			}
//		}
//		model.addAttribute("list", lists);
		model.addAttribute("list", list);
		return "bss/pms/collect/included";
	}
	
	/**
	 * 
	* @Title: updatePosition
	* @Description: 
	* author: Li Xiaoxiao 
	* @param @param sid
	* @param @param xid
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updatePosition(String sid,String xid,Integer position){
		CollectPlan plan = collectPlanService.queryById(sid);
		CollectPlan plan2 = collectPlanService.queryById(xid);
	 
		CollectPlan c=new CollectPlan();
		CollectPlan c2=new CollectPlan();
		c.setId(xid);
		c.setPosition(plan.getPosition());
		
		c2.setId(sid);
		c2.setPosition(plan2.getPosition());
		collectPlanService.update(c);
		collectPlanService.update(c2);
		return "redirect:list.html";
	}
}




