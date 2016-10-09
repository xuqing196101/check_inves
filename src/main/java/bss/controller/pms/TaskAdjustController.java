package bss.controller.pms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import bss.controller.base.BaseController;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.model.pms.UpdateFiled;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.UpdateFiledService;
/**
 * 
 * @Title: TaskAdjustController
 * @Description: 需求计划任务调整 
 * @author Li Xiaoxiao
 * @date  2016年10月8日,上午9:33:12
 *
 */
@Controller
@RequestMapping("/adjust")
public class TaskAdjustController extends BaseController{

	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private CollectPlanService collectPlanService;
	
	@Autowired
	private CollectPurchaseService collectPurchaseService;
 
	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;
	
	@Autowired
	private UpdateFiledService updateFiledService;
	
	/**
	 * 
	 * 
	* @Title: list
	* @Description: 查询采购计划列表
	* author: Li Xiaoxiao 
	* @param @param model
	* @param @param collectPlan
	* @param @param page
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String list(Model model,CollectPlan collectPlan,Integer page){
		List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
		PageInfo<CollectPlan> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", collectPlan);
		return "bss/pms/taskadjust/planlist";
		
	}
	/**
	 * 
	* @Title: requiredList
	* @Description: 查看所有需求计划
	* author: Li Xiaoxiao 
	* @param @param id
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/all")
	public String requiredList(String id,Model model){
		List<PurchaseRequired> purList=new LinkedList<PurchaseRequired>();
		List<String> list = collectPurchaseService.getNo(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		for(String str:list){
			map.put("isMaster", "1");
			map.put("planNo", str);
			List<PurchaseRequired> pur = purchaseRequiredService.getByMap(map);
			 purList.addAll(pur);
		}
		model.addAttribute("list", purList);
		return "bss/pms/taskadjust/purchaselist";
	}
	
	/**
	 * 
	* @Title: detail
	* @Description: 根据计划编号查询明细
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param model
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/detail")
	public String detail(String planNo,Model model){
		
		List<PurchaseRequired> list = purchaseRequiredMapper.queryByNo(planNo);
		model.addAttribute("list", list);
		model.addAttribute("planNo", planNo);
		return "bss/pms/taskadjust/edit";
	}
	
	/**
	 * 
	* @Title: updateById
	* @Description: 根据id修改 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list){
		
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for( PurchaseRequired p:list.getList()){
					if( p.getId()!=null){
						PurchaseRequired queryById = purchaseRequiredService.queryById(p.getId());
						Integer s=Integer.valueOf(purchaseRequiredService.queryByNo(p.getPlanNo()))+1;
						queryById.setHistoryStatus(String.valueOf(s));
						purchaseRequiredService.update(queryById);
						if(p.getParentId()!=null){
							p.setParentId(p.getParentId());
						}
						String id = UUID.randomUUID().toString().replaceAll("-", "");
						p.setId(id);
						p.setHistoryStatus("0");
						purchaseRequiredService.add(p);	
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
	/**
	 * 
	* @Title: filed
	* @Description: 查看字段是否允许修改 
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @param name
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/filed")
	@ResponseBody
	public String filed(String planNo,String name){
		String[] str = name.split("\\.");
		List<String> list = collectPurchaseService.getId(planNo);
		if(list.size()>0){
			List<UpdateFiled> filed = updateFiledService.query(list.get(0), null);
			for(UpdateFiled f:filed){
				if(f.getFiled().contains(str[1])){
					return "exit";
				}
			}
		}
		return null;
	}
}
