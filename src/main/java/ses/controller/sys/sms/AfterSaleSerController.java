package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.dao.sms.AfterSaleSerMapper;
import ses.model.sms.AfterSaleSer;
import ses.service.sms.AfterSaleSerService;

@Controller
@Scope("prototype")
@RequestMapping(value ="/after_sale_ser")
public class AfterSaleSerController {
	
	@Autowired
	private AfterSaleSerMapper AfterSaleSerMapper; //售后服务
	
	@Autowired
	private AfterSaleSerService AfterSaleSerService; //售后服务
	
	@RequestMapping(value = "list")
	public String list(Model model) {
		/*List<AfterSaleSer> list = AfterSaleSerService.findAfterSaleSer();
		String str = "";
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				str += ",";
			}
			str += list.get(i).getRequiredId();
		}
		if (str.contains("1")) {
			model.addAttribute("status", "fail");
			model.addAttribute("status", "fail");
		}
		model.addAttribute("list", list);*/
		return "ses/sms/after_sale_ser/list";
		
	}
	
	@RequestMapping(value = "add")
	public String add(Model model, AfterSaleSer AfterSaleSer) {
		String id = AfterSaleSer.getId();
		if (id != null && !"".equals(id)) {
			AfterSaleSer = AfterSaleSerService.get(id);
			model.addAttribute("AfterSaleSer", AfterSaleSer);
		}
		return "ses/sms/after_sales/add";
	}
	
	@RequestMapping(value = "save_or_update_supplier_stars")
	public String saveOrUpdateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		AfterSaleSerService.saveOrUpdateAfterSaleSer(AfterSaleSer);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "update_status")
	public String updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		AfterSaleSerService.updateAfterSaleSer(AfterSaleSer);
		return "redirect:list.html";
	}

}
