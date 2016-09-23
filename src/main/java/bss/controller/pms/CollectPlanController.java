package bss.controller.pms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: CollectPlanController
 * @Description: 采购计划汇总管理 
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:21:24
 *
 */
@Controller
@RequestMapping("/collect")
public class CollectPlanController extends BaseController {

	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
		/**
		 * 
		* @Title: queryPlan
		* @Description: 条件查询分页需求计划
		* author: Li Xiaoxiao 
		* @param @param purchaseRequired
		* @param @return     
		* @return String     
		* @throws
		 */
		@RequestMapping("/list")
		public String queryPlan(PurchaseRequired purchaseRequired,Integer page,Model model){
			purchaseRequired.setIsMaster("1");
			purchaseRequired.setIsCollect(1);
			List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
			PageInfo<PurchaseRequired> info = new PageInfo<>(list);
			model.addAttribute("info", info);
			model.addAttribute("inf", purchaseRequired);
			return "bss/pms/collect/collectlist";
		}
		/**
		 * 
		* @Title: queryPlan
		* @Description: 条件查询分页汇总计划
		* author: Li Xiaoxiao 
		* @param @param purchaseRequired
		* @param @return     
		* @return String     
		* @throws
		 */
		@RequestMapping("/collectlist")
		public String queryCollect(CollectPlan collectPlan,Integer page,Model model){
			List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
			PageInfo<CollectPlan> info = new PageInfo<>(list);
 
			model.addAttribute("info", info);
			model.addAttribute("inf", collectPlan);
			return "bss/pms/collect/contentlist";
		}
		 /**
		  * 
		 * @Title: queryCollect
		 * @Description: 汇总,以及修改采购计划 
		 * author: Li Xiaoxiao 
		 * @param @param collectPlan
		 * @param @return     
		 * @return String     
		 * @throws
		  */
		@RequestMapping("/add")
		public String queryCollect(CollectPlan collectPlan){
	 
			String planNo = collectPlan.getPlanNo();
			PurchaseRequired p=new PurchaseRequired();
			p.setPlanNo(planNo);
			p.setIsMaster("1");
	
			List<PurchaseRequired> list = purchaseRequiredService.query(p, 10);
			BigDecimal budget=BigDecimal.ZERO;
			for(PurchaseRequired pr:list){
				
				budget=budget.add(pr.getBudget());
			}
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			collectPlan.setId(id);
			collectPlan.setStatus(1);
			collectPlan.setBudget(budget);
			collectPlan.setCreatedAt(new Date());
			collectPlan.setPosition(Long.valueOf(1));
			collectPlanService.add(collectPlan);
			
			p.setIsCollect(2);//修改
			p.setStatus("5");//修改
			purchaseRequiredService.updateStatus(p);
			
			return "redirect:list.html";
		}
		/**
		 * 
		* @Title: update
		* @Description: 汇入采购计划
		* author: Li Xiaoxiao 
		* @param @param collectPlan
		* @param @return     
		* @return String     
		* @throws
		 */
		@RequestMapping("/update")
		@ResponseBody
		public String update(CollectPlan collectPlan){
			CollectPlan plan = collectPlanService.queryById(collectPlan.getId());
			String planNo = collectPlan.getPlanNo();
			plan.getPlanNo().concat(planNo);
			collectPlanService.update(collectPlan);
			
			PurchaseRequired p=new PurchaseRequired();
			p.setPlanNo(planNo);
			p.setIsCollect(2);//修改
			p.setStatus("5");//修改
			purchaseRequiredService.updateStatus(p);
			
			
			return "";
		}
		
}
