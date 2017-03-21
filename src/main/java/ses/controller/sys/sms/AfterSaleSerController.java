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
@RequestMapping("/after_sale_ser")
public class AfterSaleSerController {
	
	
	
	@Autowired
	private AfterSaleSerService  afterSaleSerService; //售后服务
	
	/**
	 * 
	 * @Title: getAll
	 * @author LiChenHao  
	 * @Description:获取售后服务信息列表
	 * @param:     
	 * @return:
	 */
	@RequestMapping(value="/list")
	public String getAll(Model model,Integer page){
		List<AfterSaleSer> AfterSaleSers = afterSaleSerService.getAll(page==null?1:page);
		model.addAttribute("list",new PageInfo<AfterSaleSer>(AfterSaleSers));
		return "ses/sms/after_sale_ser/list";
	}
	
	/**
	 * 
	 * @Title: add
	 * @author LiChenHao  
	 * @Description:新增售后服务信息
	 * @param:     
	 * @return:
	 */
	@RequestMapping(value = "add")
	public String add(Model model, AfterSaleSer afterSaleSer) {
		String id = afterSaleSer.getId();
		if (id != null && !"".equals(id)) {
			afterSaleSer = afterSaleSerService.get(id);
			model.addAttribute("AfterSaleSer",afterSaleSer);
		}
		return "ses/sms/after_sales_ser/add";
	}
	
	/**
	 * 
	 * @Title: saveOrUpdateAfterSaleSer
	 * @author LiChenHao  
	 * @Description:保存修改
	 * @param:     
	 * @return:
	 */
	@RequestMapping(value = "saveOrUpdateAfterSaleSer")
	public String saveOrUpdateAfterSaleSer(AfterSaleSer afterSaleSer) {
		afterSaleSerService.saveOrUpdateAfterSaleSer(afterSaleSer);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "update_status")
	public String updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		afterSaleSerService.saveOrUpdateAfterSaleSer(AfterSaleSer);
		return "redirect:list.html";
	}

}
