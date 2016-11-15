package bss.controller.pms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.CollectPurchase;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
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
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
	
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
			purchaseRequired.setIsMaster(1);
//			purchaseRequired.setIsCollect(1);
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
		public String queryCollect(CollectPlan collectPlan,Integer page,Model model,String type){
			List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
			PageInfo<CollectPlan> info = new PageInfo<>(list);
 
			model.addAttribute("info", info);
			model.addAttribute("inf", collectPlan);
			model.addAttribute("type", type);
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
		public String queryCollect(CollectPlan collectPlan,String cno,String goodsType){
			PurchaseRequired p=new PurchaseRequired();
			List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
			Set<String> set=new HashSet<String>();
			if(collectPlan.getPlanNo()!=null){
				String[] plano = collectPlan.getPlanNo().split(",");
				for(String no:plano){
					p.setPlanNo(no);
					p.setIsMaster(1);
					List<PurchaseRequired> one = purchaseRequiredService.query(p, 1);
//					p.setIsCollect(2);//修改
					p.setStatus("5");//修改
					p.setIsMaster(null);
					purchaseRequiredService.updateStatus(p);
					list.addAll(one);
				}
			}
			if(collectPlan.getDepartment()!=null){
				String[] department = collectPlan.getDepartment().split(",");
				for(String dep:department){
						set.add(dep);
				}
			}
			if(set!=null&&set.size()>0){
				for(String dep:set){
					BigDecimal budget=BigDecimal.ZERO;
					for(PurchaseRequired pr:list){
						budget=budget.add(pr.getBudget());
					}
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					collectPlan.setId(id);
					collectPlan.setStatus(1);
					collectPlan.setBudget(budget);
					collectPlan.setCreatedAt(new Date());
					collectPlan.setDepartment(dep);
					Integer max = collectPlanService.getMax();
					if(max!=null){
						max+=1;
						collectPlan.setPosition(max);
					}else{
						collectPlan.setPosition(1);
						
					}
					String[] plano = collectPlan.getPlanNo().split(",");
					CollectPurchase c=new CollectPurchase();
					for(String no:plano){
						c.setCollectPlanId(id);
						c.setPlanNo(no);
						collectPurchaseService.add(c);
					}
					collectPlan.setPlanNo(cno);
					collectPlan.setGoodsType(goodsType);
					collectPlanService.add(collectPlan);
				}
			}
			
			
			
			
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
			String [] planNo = collectPlan.getPlanNo().split(",");
			List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
			CollectPurchase c=new CollectPurchase();
			PurchaseRequired p=new PurchaseRequired();
			for(String no:planNo){
				c.setCollectPlanId(collectPlan.getId());
				c.setPlanNo(no);
				collectPurchaseService.add(c);
				
				p.setPlanNo(no);
				p.setIsMaster(1);
				List<PurchaseRequired> one = purchaseRequiredService.query(p, 1);
//				p.setIsCollect(2);//修改
				p.setStatus("5");//修改
				p.setIsMaster(null);
				purchaseRequiredService.updateStatus(p);
				list.addAll(one);
			}
			BigDecimal budget=BigDecimal.ZERO;
			for(PurchaseRequired pr:list){
				budget=budget.add(pr.getBudget());
			}
			
			BigDecimal decimal = plan.getBudget();
			BigDecimal budget2=	decimal.add(budget);
			
			plan.setBudget(budget2);
			collectPlanService.update(plan);
			
//			PurchaseRequired p=new PurchaseRequired();
//			p.setPlanNo(planNo);
//			p.setIsCollect(2);//修改
//			p.setStatus("5");//修改
//			purchaseRequiredService.updateStatus(p);
			
			
			return "";
		}
		
}
