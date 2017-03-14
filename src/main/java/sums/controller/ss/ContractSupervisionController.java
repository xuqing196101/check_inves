package sums.controller.ss;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.ValidateUtils;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Project;
import bss.service.cs.PurchaseContractService;
import common.annotation.CurrentUser;

/* 
 *@Title:ContractSupervisionController
 *@Description:采购合同监督
 *@author wanlin li
 *@date 2017-03-10下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/contractSupervision")
public class ContractSupervisionController {
	@Autowired
    private PurchaseContractService purchaseContractService;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@Autowired
	    private SupplierService supplierService;
	@RequestMapping(value="/list",produces = "text/html;charset=UTF-8")
    public String list(Model model, @CurrentUser User user,PurchaseContract purCon,Integer page){
		if(page==null){
            page=1;
        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("page", page);
        if(purCon.getProjectName()!=null&&!"".equals(purCon.getProjectName())){
            map.put("projectName", purCon.getProjectName());
        }
        if(purCon.getCode()!=null&&!"".equals(purCon.getCode())){
            map.put("code", purCon.getCode());
        }
        if(purCon.getSupplierDepName()!=null&&!"".equals(purCon.getSupplierDepName())){
            map.put("supplierDepName", purCon.getSupplierDepName());
        }
        if(purCon.getPurchaseDepName()!=null&&!"".equals(purCon.getPurchaseDepName())){
            map.put("purchaseDepName", purCon.getPurchaseDepName());
        }
        if(purCon.getDemandSector()!=null&&!"".equals(purCon.getDemandSector())){
            map.put("demandSector", purCon.getDemandSector());
        }
        if(purCon.getDocumentNumber()!=null&&!"".equals(purCon.getDocumentNumber())){
            map.put("documentNumber", purCon.getDocumentNumber());
        }
        if(purCon.getYear_string()!=null&&!"".equals(purCon.getYear_string())){
            if(ValidateUtils.Integer(purCon.getYear_string())){
                map.put("year", new BigDecimal(purCon.getYear_string()));
            }else{
                map.put("year", 1234);
            }
        }
        if(purCon.getBudgetSubjectItem()!=null){
            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
        }
        List<PurchaseContract> draftConList = purchaseContractService.selectAllContractByCode(map);
        for(PurchaseContract pur:draftConList){
        	Supplier su = null;
        	Orgnization org = null;
        	if(pur.getSupplierDepName()!=null){
        		su = supplierService.selectOne(pur.getSupplierDepName());
        	}
            //				PurchaseDep purdep = purchaseOrgnizationServiceI.selectPurchaseById(pur.getBingDepName());
        	if(pur.getPurchaseDepName()!=null){
        		org = orgnizationServiceI.getOrgByPrimaryKey(pur.getPurchaseDepName());
        	}
        	if(org!=null){
                if(org.getName()==null){
                    pur.setShowDemandSector("");
                }else{
                    pur.setShowDemandSector(org.getName());
                }
        	}
            if(su!=null){
                if(su.getSupplierName()!=null){
                    pur.setShowSupplierDepName(su.getSupplierName());
                }else{
                    pur.setShowSupplierDepName("");
                }
            }
            
        }
        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(draftConList);
        model.addAttribute("list", list);
        model.addAttribute("draftConList", draftConList);
        model.addAttribute("purCon", purCon);
		return "sums/ss/contractSupervision/draftlist";
	}
	@RequestMapping(value="/contSupervision",produces = "text/html;charset=UTF-8")
	public String detailContract(Model model, @CurrentUser User user,PurchaseContract purCon,Integer page){
		return "sums/ss/contractSupervision/contractSupervision";
	}
}
