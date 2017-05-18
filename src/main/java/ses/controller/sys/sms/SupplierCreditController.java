package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.sms.SupplierCredit;
import ses.service.sms.SupplierCreditService;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import common.utils.JdcgResult;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_credit")
public class SupplierCreditController {

	@Autowired
	private SupplierCreditService supplierCreditService;

	@RequestMapping(value = "list")
	public String list(Model model, SupplierCredit supplierCredit, Integer page) {
		String name = supplierCredit.getName();
		List<SupplierCredit> listSupplierCredits = supplierCreditService.findSupplierCredit(supplierCredit, page == null ? 0 : page);
		model.addAttribute("listSupplierCredits", new PageInfo<SupplierCredit>(listSupplierCredits));
		model.addAttribute("name", name);
		return "ses/sms/supplier_credit/list";
	}
	
	@RequestMapping(value = "add_credit")
	public String addCredit(Model model, SupplierCredit supplierCredit) throws Exception {
		// 解决中文乱码
		if(supplierCredit.getName() != null){
			supplierCredit.setName(new String(supplierCredit.getName().getBytes("ISO8859-1"), "UTF-8"));
		}
		if (supplierCredit.getId() != null && !"".equals(supplierCredit.getId())) {
			model.addAttribute("supplierCredit", supplierCredit);
		}
		return "ses/sms/supplier_credit/add_credit";
	}
	
	@RequestMapping(value = "save_or_update_supplier_credit")
	@ResponseBody
	public JdcgResult saveOrUpdateSupplierCredit(SupplierCredit supplierCredit,Model model) {
		if(StringUtils.isEmpty(supplierCredit.getName())){
			return JdcgResult.build(500, "请填写诚信形式名称");
		}
		supplierCreditService.saveOrUpdateSupplierCredit(supplierCredit);
		return JdcgResult.ok();
	}
	
	@RequestMapping(value = "update_status")
	public String updateStatus(SupplierCredit supplierCredit) {
		supplierCreditService.updateStatus(supplierCredit);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids) {
		supplierCreditService.delete(ids);
		return "redirect:list.html";
	}
	
}
