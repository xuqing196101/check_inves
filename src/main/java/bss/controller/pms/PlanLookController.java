package bss.controller.pms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;
/**
 * 
 * @Title: PlanLookController
 * @Description: 计划信息查看 
 * @author Li Xiaoxiao
 * @date  2016年9月26日,上午10:17:17
 *
 */
@Controller
@RequestMapping("/look")
public class PlanLookController {
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
	
	
	/**
	 * 
	 * 
	* @Title: list
	* @Description: 采购计划信息查看 
	* author: Li Xiaoxiao 
	* @param @param collectPlan
	* @param @param page
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
		return "bss/pms/collect/planlist";
	}
	/**
	 * 
	* @Title: print
	* @Description: 打印
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/print")
	public String print(String id,Model model){
		List<String> no = collectPurchaseService.getNo(id);
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
		}
		model.addAttribute("list", list);
		return "bss/pms/collect/print";
	}
	
	/**
	 * 
	* @Title: queryOne
	* @Description: 审核页面 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/auditlook")
	public String auditlook(String id,Model model){
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		
//		CollectPlan plan = collectPlanService.queryById(id);
		List<String> no = collectPurchaseService.getNo(id);
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(no!=null&&no.size()>0){
			for(String s:no){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("org",org);
		model.addAttribute("id", id);
		return "bss/pms/collect/audit";
	}
	
	/**
	 * 
	* @Title: audit
	* @Description: 审核
	* author: Li Xiaoxiao 
	* @param @param list
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/audit")
	public String audit(PurchaseRequiredFormBean list,CollectPlan collectPlan){
		if(list!=null){
			if(list.getList()!=null){
				for(PurchaseRequired p:list.getList()){
					p.setStatus("6");
					purchaseRequiredService.update(p);
				}
			}
		}
//		collectPlan.setStatus(2);
//		collectPlanService.update(collectPlan);
		return "redirect:list.html";
	}
	
	/**
	 * 
	* @Title: report
	* @Description:生成评审报告页面 
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/report")
	public String report(String id,Model model){
		CollectPlan plan = collectPlanService.queryById(id);
		model.addAttribute("plan", plan);
		return "bss/pms/collect/pdf";
	}
}
