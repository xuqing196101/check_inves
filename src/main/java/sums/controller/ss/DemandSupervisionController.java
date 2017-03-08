package sums.controller.ss;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseOrg;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/* 
 *@Title:DemandSupervisionController
 *@Description:采购采购需求监督控制类
 *@author zhiqiang tian
 *@date 2017-03-06下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/supervision")
public class DemandSupervisionController{
    
    //需求计划服务
    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
    
    
    @Autowired
    private PurchaseOrgnizationServiceI purchserOrgnaztionService;
    @Autowired
    private OrgnizationMapper oargnizationMapper;
    
    @Autowired
    private PurChaseDepOrgService chaseDepOrgService;
    /**
     * 
    * 〈简述〉 〈详细描述〉
    * 
    * @author QuJie 
    * @date 2016-11-11 下午2:48:57  
    * @Description: 查询已完成的包 
    * @param @param model
    * @param @param 分页
    * @param @param request
    * @param @return
    * @param @throws Exception      
    * @return String
     */
    @RequestMapping("/demandSupervisionList")
    public String demandSupervisionList(@CurrentUser User user,PurchaseRequired purchaseRequired,Integer page,Model model) throws Exception{
        if(purchaseRequired.getStatus()==null){
//          purchaseRequired.setStatus("1");
            purchaseRequired.setStatus("total");
        }
        
        else if(purchaseRequired.getStatus().equals("5")){
            purchaseRequired.setSign("5");
        }
        if(purchaseRequired.getStatus().equals("total")){
            purchaseRequired.setStatus(null);
        }
        if (page == null ){
            page = StaticVariables.DEFAULT_PAGE;
        }
        List<Role> roles = user.getRoles();
        boolean bool=false;
        if(roles!=null&&roles.size()>0){
            for(Role r:roles){
                if(r.getCode().equals("NEED_M")){
                    bool=true;
                }
            }
        }
        if(bool!=true){
            purchaseRequired.setUserId(user.getId());
        } 
        List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page);
        model.addAttribute("info", new PageInfo<PurchaseRequired>(list));
        model.addAttribute("inf", purchaseRequired);
        
        Map<String,Object> map=new HashMap<String,Object>();
        List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
        model.addAttribute("requires", requires);
        
//      HashMap<String, Object> maps = new HashMap<String, Object>();
//      maps.put("typeName", StaticVariables.ORG_TYPE_MANAGE);
//      List<Orgnization> manages = orgnizationServiceI.findOrgnizationList(maps);
        List<PurchaseOrg> manages = purchserOrgnaztionService.get(user.getOrg().getId());
//      model.addAttribute("manages", manages);
        model.addAttribute("manages", manages.size());
        return "sums/ss/demandSupervision/list";
        //return "bss/cs/purchaseContract/list";
    }
    

    
    
    
    
    
}
