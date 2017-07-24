package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.sms.SupplierCredit;
import ses.service.sms.SupplierCreditService;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.utils.JdcgResult;

@Controller
@Scope("prototype")
@RequestMapping("/supplier_credit")
public class SupplierCreditController {

	@Autowired
	private SupplierCreditService supplierCreditService;

	@RequestMapping(value = "list")
	public String list(Model model, SupplierCredit supplierCredit, Integer page,@CurrentUser User user) {
		//权限验证 登陆状态 角色只能是资源服务中心
		if(null!=user && "4".equals(user.getTypeName())){
			String name = supplierCredit.getName();
			List<SupplierCredit> listSupplierCredits = supplierCreditService.findSupplierCredit(supplierCredit, page == null ? 0 : page);
			model.addAttribute("listSupplierCredits", new PageInfo<SupplierCredit>(listSupplierCredits));
			model.addAttribute("name", name);
			model.addAttribute("orgTypeName", user.getTypeName());
			return "ses/sms/supplier_credit/list";
		}
		return "redirect:/qualifyError.jsp"; 
	}
	
	@RequestMapping(value = "add_credit")
	public String addCredit(Model model, SupplierCredit supplierCredit, HttpServletRequest request) {
		// 解决中文乱码
		/*String encoding = request.getCharacterEncoding();
		if(supplierCredit.getName() != null){
			if ("UTF-8".equals(encoding)) {
				supplierCredit.setName(supplierCredit.getName());
			}
			if ("ISO8859-1".equals(encoding)){
				supplierCredit.setName(new String(supplierCredit.getName().getBytes("ISO8859-1"), "UTF-8"));
			}
		}*/
		String name = supplierCredit.getName();
		if(StringUtils.isNotBlank(name)){
			try {
				name = URLDecoder.decode(name, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		supplierCredit.setName(name);
		if (supplierCredit.getId() != null && !"".equals(supplierCredit.getId())) {
			model.addAttribute("supplierCredit", supplierCredit);
		}
		return "ses/sms/supplier_credit/add_credit";
	}
	
	@RequestMapping(value = "save_or_update_supplier_credit")
	@ResponseBody
	public JdcgResult saveOrUpdateSupplierCredit(SupplierCredit supplierCredit,Model model) {
		if(StringUtils.isBlank(supplierCredit.getName())){
			return JdcgResult.build(500, "请填写诚信形式名称");
		}else if(supplierCredit.getName().trim().length()==0 || supplierCredit.getName().trim().indexOf(' ') != -1){
			return JdcgResult.build(500, "名称中包含空格");
		}
		supplierCredit.setName(supplierCredit.getName().trim());
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
