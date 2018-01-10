package bss.controller.cs;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.StaticVariables;

import bss.model.cs.ContractAdvice;
import bss.model.cs.PurchaseContract;
import bss.service.cs.ContractAdviceService;
import bss.service.cs.PurchaseContractService;
import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.User;
import ses.service.bms.UserServiceI;
import ses.util.DictionaryDataUtil;

@Controller
@Scope("prototype")
@RequestMapping("/contractAdvice")
public class ContractAdviceController extends BaseSupplierController {
	
	@Autowired
    private ContractAdviceService contractAdviceService;
	
	@Autowired
	private PurchaseContractService contractService;
	
	@Autowired
	private UserServiceI userService;
	
	@RequestMapping("/list")
	public String list(@CurrentUser User user, Model model, ContractAdvice contractAdvice, PurchaseContract contract, Integer page) {
		if (user != null && StringUtils.isNotBlank(user.getTypeName()) && user.getOrg() != null) {
			HashMap<String, Object> map = new HashMap<>();
			if (page == null) {
				page = 1;
			}
			map.put("page", page);
			map.put("orgId", user.getOrg().getId());
			if (contractAdvice != null) {
				if (contractAdvice.getStatus() == null) {
					map.put("status", 1);
					contractAdvice.setStatus(1);
				} else if (contractAdvice.getStatus() == 0) {
					
				} else {
					map.put("status", contractAdvice.getStatus());
				}
			}
			if (contract != null) {
				if (StringUtils.isNotBlank(contract.getProjectName())) {
					map.put("projectName", contract.getProjectName());
				}
				if (StringUtils.isNotBlank(contract.getCode())) {
					map.put("code", contract.getCode());
				}
				if (StringUtils.isNotBlank(contract.getName())) {
					map.put("name", contract.getName());
				}
			}
			List<ContractAdvice> list = contractAdviceService.list(map);
			model.addAttribute("info", new PageInfo<ContractAdvice>(list));
			model.addAttribute("contract", contract);
			model.addAttribute("contractAdvice", contractAdvice);
		}
		return "bss/cs/contractAdvice/list";
	}
	
	@RequestMapping("/audit")
	public String audit(Model model, String id, String status){
		if (StringUtils.isNotBlank(status) && StringUtils.isNotBlank(id)) {
			ContractAdvice contractAdvice = contractAdviceService.selectById(id);
			User user = userService.getUserById(contractAdvice.getProposer());
			contractAdvice.setProposer(user.getRelName());
			model.addAttribute("uploadId", DictionaryDataUtil.getId("DRAFT_REVIEWED"));
			model.addAttribute("auditId", DictionaryDataUtil.getId("CONTRACT_AUDIT"));
			model.addAttribute("advice", contractAdvice);
			model.addAttribute("status", status);
		}
		return "bss/cs/contractAdvice/audit";
	}
	
	@RequestMapping("/pass")
	@ResponseBody
	public String pass(@CurrentUser User user, String id, Integer status, String reason) {
		if (status != null && StringUtils.isNotBlank(id)) {
			ContractAdvice contractAdvice = contractAdviceService.selectById(id);
			if (StringUtils.isNotBlank(reason)) {
				contractAdvice.setReason(reason);
			}
			contractAdvice.setStatus(status);
			contractAdvice.setUserId(user.getId());
			contractAdvice.setAduitTime(new Date());
			contractAdviceService.update(contractAdvice);
			
			PurchaseContract contract = contractService.selectById(contractAdvice.getContractId());
			contract.setAuditStatus(status);
			contractService.updateByPrimaryKeySelective(contract);
			return StaticVariables.SUCCESS;
		} else {
			return StaticVariables.FAILED;
		}
		
	}

}
