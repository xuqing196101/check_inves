package bss.controller.pms;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
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
//		CollectPlan collectPlan = collectPlanService.queryById(id);
		CollectPlan collectPlan=new CollectPlan();
		collectPlan.setPlanNo("001");
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		if(collectPlan.getPlanNo()!=null){
			String[] str = collectPlan.getPlanNo().split(",");
			for(String s:str){
				List<PurchaseRequired> pur = purchaseRequiredMapper.queryByNo(s);
				list.addAll(pur);
			}
		}
		model.addAttribute("list", list);
		return "bss/pms/collect/print";
	}
	
}
