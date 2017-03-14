package sums.controller.ss;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import bss.model.pms.CollectPlan;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;

import common.annotation.CurrentUser;

import ses.model.bms.Role;
import ses.model.bms.User;
import ses.service.bms.UserServiceI;
import ses.util.DictionaryDataUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <采购计划监督>
 * <详细描述>
 * @author   Administrator
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping("/planSupervision")
public class PlanSupervisionController {
    
    @Autowired
    private UserServiceI userService;
    
    @Autowired
    private PurchaseDetailService detailService;
    
    @Autowired
    private CollectPlanService collectPlanService;
    
    
    @RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user, CollectPlan collectPlan, Integer page){
        if(user != null && user.getOrg() != null){
            if(collectPlan.getStatus() == null){
                collectPlan.setSign("0");
                collectPlan.setStatus(null);
            }else if(collectPlan.getStatus() == 8){
                collectPlan.setSign("8");
                collectPlan.setStatus(null);
            } else if(collectPlan.getStatus() == 12){
                collectPlan.setSign("12");
                collectPlan.setStatus(null);
            }
            if("".equals(collectPlan.getFileName())){
                collectPlan.setFileName(null);
            }
            List<Role> roles = user.getRoles();
            boolean bool=false;
            if(roles!=null&&roles.size()>0){
                for(Role r:roles){
                    if(r.getCode().equals("MANAGE_M")){
                        bool=true;
                    }
                }
            }
            if(bool!=true){
                collectPlan.setUserId(user.getId());
            }
            List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page==null?1:page);
            for (int i = 0; i < list.size(); i++ ) {
                try {
                    User users = userService.getUserById(list.get(i).getUserId());
                    list.get(i).setUserId(users.getRelName());
                } catch (Exception e) {
                    list.get(i).setUserId("");
                }
            }
            PageInfo<CollectPlan> info = new PageInfo<>(list);
            model.addAttribute("info", info);
            model.addAttribute("collectPlan", collectPlan);
        }
        return "sums/ss/planSupervision/list";
    }

}
