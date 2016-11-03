package bss.controller.pms;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title: PurchaseAcceptController
 * @Description: 采购计划受理管理
 * @author Li Xiaoxiao
 * @date 2016年9月20日,上午10:32:29
 * 
 */
@Controller
@RequestMapping("/accept")
public class PurchaseAcceptController extends BaseController{

	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	/**
	 * 
	 * @Title: queryPlan
	 * @Description: 条件查询分页 author: Li Xiaoxiao
	 * @param @param purchaseRequired
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(PurchaseRequired purchaseRequired, Integer page, Model model) {
//		purchaseRequired.setStatus("2");
		purchaseRequired.setIsMaster("1");
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page == null ? 1 : page);
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", purchaseRequired);
		
	
		return "bss/pms/collect/list";
	}
	
	  /**
     * 
    * @Title: submit
    * @Description:跳转到受理页面
    * author: Li Xiaoxiao 
    * @param @return     
    * @return String     
    * @throws
     */
    @RequestMapping("/submit")
    public String submit(String planNo,Model model){
    	PurchaseRequired p=new PurchaseRequired();
		p.setPlanNo(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.query(p,0);
		model.addAttribute("planNo", list.get(0).getPlanNo());
		model.addAttribute("list", list);
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		model.addAttribute("org", org);
		
    	return "bss/pms/collect/view";
    }
	
    /**
     * 
    * @Title: submit
    * @Description: 受理，退回
    * author: Li Xiaoxiao 
    * @param @return     
    * @return String     
    * @throws
     */
    @RequestMapping("/update")
    public String submit(PurchaseRequiredFormBean list,String reason){

  
    	if(list!=null){
    	  	List<PurchaseRequired> plist = list.getList();
    		if(plist!=null&&plist.size()>0){
    			if(reason!=null){
    				for(PurchaseRequired p:plist){
    					p.setReason(reason);
        				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}else{
    				for(PurchaseRequired p:plist){
         				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}
    			
    		}
    	}
//    	purchaseRequiredService.updateStatus(p);
    	return "redirect:list.html";
    }
    
    
    
}
