package bss.controller.cs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.cs.PurchaseContract;
import bss.service.cs.PurchaseContractService;


/* 
 *@Title:PurchaseContractController
 *@Description:采购合同控制类
 *@author QuJie
 *@date 2016-9-23下午1:34:27
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaseContract")
public class PurchaseContractController {
	
	@Autowired
	private PurchaseContractService purchaseContractService;
	
	@RequestMapping("/selectAllPuCon")
	public String selectAllPurchaseContract() throws Exception{
		List<PurchaseContract> conList = purchaseContractService.selectAllPurchaseContract();
		return null;
	}
}
