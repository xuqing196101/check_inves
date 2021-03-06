package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.sms.SupplierStars;
import ses.service.sms.SupplierStarsService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.utils.JdcgResult;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_stars")
public class SupplierStarsController {

	@Autowired
	private SupplierStarsService supplierStarsService;
	
	@RequestMapping(value = "list")
	public String list(Model model,@RequestParam(defaultValue = "1") Integer page,@CurrentUser User user) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		//权限验证 登陆状态 角色只能是资源服务中心
		if(null!=user && "4".equals(user.getTypeName())){
			List<SupplierStars> list = supplierStarsService.findSupplierStars(map);
			String str = "";
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					str += ",";
				}
				str += list.get(i).getStatus();
			}
			if (str.contains("1")) {
				model.addAttribute("status", "fail");
			}
			model.addAttribute("info", new PageInfo<SupplierStars>(list));
			return "ses/sms/supplier_stars/list";
		}
		return "redirect:/qualifyError.jsp";
	}
	
	@RequestMapping(value = "add")
	public String add(Model model, SupplierStars supplierStars) {
		String id = supplierStars.getId();
		if (id != null && !"".equals(id)) {
			supplierStars = supplierStarsService.get(id);
			model.addAttribute("supplierStars", supplierStars);
		}
		return "ses/sms/supplier_stars/add_stars";
	}
	
	@RequestMapping(value = "save_or_update_supplier_stars")
	@ResponseBody
	public JdcgResult saveOrUpdateSupplierStars(SupplierStars supplierStars) {
		return supplierStarsService.saveOrUpdateSupplierStars(supplierStars);
	}
	
	@RequestMapping(value = "update_status")
	public String updateStatus(SupplierStars supplierStars) {
		supplierStarsService.updateStatus(supplierStars);
		return "redirect:list.html";
	}
	
	@RequestMapping(value = "delete")
	public String delete(String ids) {
		supplierStarsService.delete(ids);
		return "redirect:list.html";
	}

}
