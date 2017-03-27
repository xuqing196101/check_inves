package sums.controller.ss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseOrg;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.controller.base.BaseController;
import bss.model.cs.PurchaseContract;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.ProjectTask;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.Task;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

/* 
 *@Title:DemandSupervisionController
 *@Description:采购采购需求监督控制类
 *@author tian zhiqiang 
 *@date 2017-03-06下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/supervision")
public class DemandSupervisionController extends BaseController{

	// 需求计划服务
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;

	@Autowired
	private PurchaseOrgnizationServiceI purchserOrgnaztionService;
	@Autowired
	private OrgnizationMapper oargnizationMapper;
	 @Autowired
	private OrgnizationServiceI orgnizationService;
	@Autowired
	private PurChaseDepOrgService chaseDepOrgService;
	
	@Autowired
    private UserServiceI userService;
    
    @Autowired
    private PurchaseDetailService detailService;
    
    @Autowired
    private CollectPlanService collectPlanService;

	/**
	 * 
	 * 〈简述〉 〈需求列表〉
	 * 
	 * @author tian zhiqiang
	 * @date 2016-11-11 下午2:48:57
	 * @Description: 查询需求列表
	 * @param @param model
	 * @param @param 分页
	 * @param @param request
	 * @param @return
	 * @param @throws Exception
	 * @return String
	 */
	@RequestMapping(value = "/demandSupervisionList", produces = "text/html;charset=UTF-8")
	public String demandSupervisionList(@CurrentUser User user,
			PurchaseRequired purchaseRequired, Integer page, Model model)
			throws Exception {
		//是否是详细，1是主要，不是1为明细
		purchaseRequired.setIsMaster(1);
		
		List<PurchaseRequired> list = purchaseRequiredService.query(
				purchaseRequired, page == null ? 1 : page);
		//获取用户的真实姓名
         for (int i = 0; i < list.size(); i++ ) {
             try {
                 User users = userService.getUserById(list.get(i).getUserId());
                 list.get(i).setUserName(users.getRelName());
             } catch (Exception e) {
                  list.get(i).setUserName("");
             }
         }
		model.addAttribute("list", new PageInfo<PurchaseRequired>(list));
		model.addAttribute("info", purchaseRequired);
		return "sums/ss/demandSupervision/list";
	}
	
	 /**
     * 〈查看需求进度页面〉
     * 
     * @author Tian zhiqiang
     * @param id
     * @param type
     * @param model
     * @return
     */
    @RequestMapping("/demandSupervisionView")
    public String view(String id, String type, Model model) {
    	PurchaseRequired purchaseRequired=new PurchaseRequired() ;
		if (id != null) {
			purchaseRequired = purchaseRequiredService.queryById(id);
		}
       
		model.addAttribute("purchaseRequired", purchaseRequired);
    	return "sums/ss/demandSupervision/demand_view";
    }
    
    
    /**
     * 
     *〈查看需求明细〉
     *〈详细描述〉
     * @author tian zhiqiang
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/demandDetailList")
    public String viewDetailList(String id,String type, Model model){
    	
		List<PurchaseRequired> list = purchaseRequiredService.getUnique(purchaseRequiredService.queryById(id).getUniqueId());
		model.addAttribute("list", list);
        return "sums/ss/demandSupervision/demand_detail_list";
    }
    

}
