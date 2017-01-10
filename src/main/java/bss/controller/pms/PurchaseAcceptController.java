package bss.controller.pms;

import java.util.ArrayList;
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
import ses.model.oms.PurchaseDep;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.StationMessageService;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;

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
	
	@Autowired
	private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
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
	public String queryPlan(@CurrentUser User user,PurchaseRequired purchaseRequired, Integer page, Model model,String status) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(purchaseRequired.getStatus()==null){
			map.put("status", "2");
			
		} else if(purchaseRequired.getStatus().equals("3")){
//			purchaseRequired.setSign("3");
			map.put("sign", "3");
			purchaseRequired.setStatus(null);
		}
		else if(purchaseRequired.getStatus().equals("total")){
//			purchaseRequired.setSign("2");
			map.put("sign", "2");
			purchaseRequired.setStatus(null);
		}else if(purchaseRequired.getStatus().equals("4")){
			map.put("status", status);
		}
		
		map.put("isMaster", "1");
		map.put("planName", purchaseRequired.getPlanName());
//		purchaseRequired.setIsMaster(1);
		//所有的需求部门
		List<PurchaseOrg> list2 = purchaseOrgnizationServiceI.get(user.getOrg().getId());
		List<String> listDep=new ArrayList<String>();
		for(PurchaseOrg p:list2){
			Orgnization dep= purchaseRequiredService.queryByDepId(p.getOrgId());
			listDep.add(dep.getName());
		}
		map.put("list", listDep);
		List<PurchaseRequired> list = purchaseRequiredService.queryByAuthority(map, page == null ? 1 : page);
		for (PurchaseRequired pur : list) {
		    pur.setUserId(userServiceI.getUserById(pur.getUserId()).getRelName());
		}
		PageInfo<PurchaseRequired> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		if(status==null){
//			purchaseRequired.setStatus("2");
			status="2";
		}
//		if(purchaseRequired.ge){
//			
//		}
		model.addAttribute("status", status);
		model.addAttribute("inf", purchaseRequired);
//		Map<String,Object> maps=new HashMap<String,Object>();
//		List<Orgnization> requires = orgnizationService.findOrgPartByParam(maps);
//		model.addAttribute("requires", requires);
	
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
	    List<PurchaseDep> org = purchaseOrgnizationServiceI.findPurchaseDepList(map);
		model.addAttribute("org", org);
		model.addAttribute("kind", DictionaryDataUtil.find(5));
		
		Map<String,Object> maps=new HashMap<String,Object>();
		List<Orgnization> requires = orgnizationService.findOrgPartByParam(maps);
		model.addAttribute("requires", requires);
		model.addAttribute("types", DictionaryDataUtil.find(6));
		
		String fileId = list.get(0).getFileId();
		String typeId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
		model.addAttribute("typeId", typeId);
		model.addAttribute("fileId", fileId);
		
		
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
