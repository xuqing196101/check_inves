package bss.controller.pms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.StationMessageService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
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
	
	@Autowired
	private UserServiceI userServiceI;
	
	@Autowired
	private StationMessageService stationMessageService;
	
	@Autowired
	private OrgnizationServiceI orgnizationService;
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
		if(purchaseRequired.getStatus()==null){
			purchaseRequired.setStatus("2");
		} else if(purchaseRequired.getStatus().equals("3")){
			purchaseRequired.setSign("3");
			purchaseRequired.setStatus(null);
		}
		else if(purchaseRequired.getStatus().equals("total")){
			purchaseRequired.setSign("2");
			purchaseRequired.setStatus(null);
		}
		
		purchaseRequired.setIsMaster(1);
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired, page == null ? 1 : page);
		for (PurchaseRequired pur : list) {
		    pur.setUserId(userServiceI.getUserById(pur.getUserId()).getRelName());
		}
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("inf", purchaseRequired);
		Map<String,Object> map=new HashMap<String,Object>();
		List<Orgnization> requires = orgnizationService.findOrgPartByParam(map);
		model.addAttribute("requires", requires);
	
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
		p.setUniqueId(planNo);
		List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		model.addAttribute("planNo", list.get(0).getUniqueId());
		model.addAttribute("list", list);
		
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("typeName", 1);
		List<Orgnization> org = orgnizationServiceI.findOrgnizationList(map);
		model.addAttribute("org", org);
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = orgnizationService.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		
		
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
    public String submit(PurchaseRequiredFormBean list,String reason,HttpServletRequest request,String status){
    	
    	String id="";
    	User user = (User) request.getSession().getAttribute("loginUser");
    	if(list!=null){
    	  	List<PurchaseRequired> plist = list.getList();
    	  	id=	plist.get(0).getUserId();
    		if(plist!=null&&plist.size()>0){
    			if(reason!=null){
    				for(PurchaseRequired p:plist){
    					p.setReason(reason);
    					p.setStatus(status);
        				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}else{
    				for(PurchaseRequired p:plist){
    					p.setStatus(status);
         				purchaseRequiredService.updateByPrimaryKeySelective(p);	
        			}
    			}
    			
    		}
    	}
    	//推送消息
    	if(reason!=null){
    		User  maker = userServiceI.getUserById(id);
    		StationMessage sm =new StationMessage();
			String sid = UUID.randomUUID().toString().replaceAll("-", "");
			sm.setId(sid);
    		sm.setReceiverId(id);
    		sm.setName(maker.getRelName());
    		sm.setSenderId(user.getId());
    		stationMessageService.insertStationMessage(sm);
    	}
//    	purchaseRequiredService.updateStatus(p);
    	return "redirect:list.html";
    }
    
    
    
}
