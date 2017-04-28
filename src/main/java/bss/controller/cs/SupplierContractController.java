package bss.controller.cs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.Role;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.RoleServiceI;
import ses.service.bms.UserServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import ses.util.ValidateUtils;
import bss.model.cs.PurchaseContract;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.service.cs.PurchaseContractService;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;

/* 
 *@Title:SupplierContractController
 *@Description:采购合同控制类
 *@author QuJie
 *@date 2016-9-23下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/supplierPurchaseContract")
public class SupplierContractController {
	@Autowired
    private RoleServiceI roleService;
	@Autowired
    private UserServiceI userService;
	@Autowired
    private PurchaseContractService purchaseContractService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@RequestMapping("/selectSupplierPuCon")
	public String selectAllPurchaseContract(Model model,Integer page,HttpServletRequest request,PurchaseContract purCon) throws Exception{
		 if(page==null){
	            page=1;
	        }
	        Map<String,Object> map = new HashMap<String, Object>();
	        map.put("page", page);
	        User user = (User) request.getSession().getAttribute("loginUser");
	        User userById = userService.getUserById(user.getId());
	        map.put("supplierId", userById.getTypeId());
	        if(purCon.getProjectName()!=null){
	            map.put("projectName", purCon.getProjectName());
	        }
	        if(purCon.getCode()!=null){
	            map.put("code", purCon.getCode());
	        }
	        if(purCon.getSupplierDepName()!=null){
	            map.put("supplierDepName", purCon.getSupplierDepName());
	        }
	        if(purCon.getPurchaseDepName()!=null){
	            map.put("purchaseDepName", purCon.getPurchaseDepName());
	        }
	        if(purCon.getDemandSector()!=null){
	            map.put("demandSector", purCon.getDemandSector());
	        }
	        if(purCon.getDocumentNumber()!=null){
	            map.put("documentNumber", purCon.getDocumentNumber());
	        }
	        if(purCon.getYear_string()!=null){
	            if(ValidateUtils.Integer(purCon.getYear_string())){
	                map.put("year", new BigDecimal(purCon.getYear_string()));
	            }else{
	                map.put("year", 1234);
	            }
	        }
	        if(purCon.getBudgetSubjectItem()!=null){
	            map.put("budgetSubjectItem", purCon.getBudgetSubjectItem());
	        }
	        BigDecimal contractSum = new BigDecimal(0);
	        
	        List<PurchaseContract> PurchaseContracts = purchaseContractService.selectAllContractBySupplierId(map);
	        if(PurchaseContracts.size()>0){
	            for(int i=0;i<PurchaseContracts.size();i++){
	            	Orgnization org = orgnizationServiceI.getOrgByPrimaryKey(PurchaseContracts.get(i).getPurchaseDepName());
	                if(org!=null){
	                	PurchaseContracts.get(i).setPurchaseDepName(org.getName());
	                }else{
	                	PurchaseContracts.get(i).setPurchaseDepName("");
	                }
	            	if(PurchaseContracts.get(i)!=null){
	                    if(PurchaseContracts.get(i).getMoney()!=null){
	                        contractSum = contractSum.add(PurchaseContracts.get(i).getMoney());
	                    }
	                }
	            }
	        }
	        PageInfo<PurchaseContract> list = new PageInfo<PurchaseContract>(PurchaseContracts);
	        model.addAttribute("list", list);
	        model.addAttribute("contractSum",contractSum);
	        model.addAttribute("purCon", purCon);
	        return "bss/cs/purchaseContract/supplierlist";
	}
	
}
