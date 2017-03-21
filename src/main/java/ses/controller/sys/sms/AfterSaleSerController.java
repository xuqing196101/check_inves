package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.github.pagehelper.PageInfo;

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
	
	/**
	 * 
	 * @Title: getAll
	 * @author LiChenHao  
	 * @Description:获取售后服务信息列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping("/list")
	public String getAll(Model model,Integer page){
		List<AfterSaleSer> AfterSaleSers = AfterSaleSerService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<AfterSaleSer>(AfterSaleSers));
		return "ses/sms/after_sale_ser/list";
	}
	
	@RequestMapping(value = "add")
	public String add(Model model, AfterSaleSer AfterSaleSer) {
		String id = AfterSaleSer.getId();
		if (id != null && !"".equals(id)) {
			AfterSaleSer = AfterSaleSerService.get(id);
			model.addAttribute("AfterSaleSer", AfterSaleSer);
		}
		return "ses/sms/after_sales_ser/add";
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
