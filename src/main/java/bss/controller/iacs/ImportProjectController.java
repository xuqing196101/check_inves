package bss.controller.iacs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import bss.model.cs.ContractRequired;
import bss.model.cs.PurchaseContract;
import bss.service.cs.ContractRequiredService;
import bss.service.cs.PurchaseContractService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping("/import_project")
public class ImportProjectController {
	
	
	@Autowired
	private PurchaseContractService purchaseContractService;// 合同
	
	@Autowired
	private ContractRequiredService contractRequiredService;// 合同明细
	
	/**
	 * @Title: list
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午8:05:07
	 * @Description: 合同列表
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "list")
	public String list(Model model, PurchaseContract purchaseContract, Integer page) {
		List<PurchaseContract> list = purchaseContractService.findPurchaseContractByMap(purchaseContract, page == null ? 1 : page);
		model.addAttribute("pager", new PageInfo<PurchaseContract>(list));
		model.addAttribute("projectName", purchaseContract.getProjectName());
		model.addAttribute("isDeclare", purchaseContract.getIsDeclare());
		return "bss/iacs/import_project/list";
	}
	
	/**
	 * @Title: add
	 * @author: Wang Zhaohua
	 * @date: 2016-11-11 上午10:02:58
	 * @Description: TODO
	 * @param: @param model
	 * @param: @param ids
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "add")
	public String add(Model model, String ids) {
		List<ContractRequired> list = contractRequiredService.findContractRequiredByConId(ids);
		model.addAttribute("list", list);
		return "bss/iacs/import_project/goods";
	}
}
