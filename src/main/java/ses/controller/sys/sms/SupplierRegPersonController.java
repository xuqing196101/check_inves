package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import bss.controller.base.BaseController;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierRegPerson;
import ses.service.sms.SupplierRegPersonService;
import ses.service.sms.SupplierService;
@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_reg_person")
public class SupplierRegPersonController extends BaseController{
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierRegPersonService supplierRegPersonService;
	
	@RequestMapping(value = "add_reg_person")
	public String addRegPerson(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("id", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		return "ses/sms/supplier_register/add_reg_person";
	}

	@RequestMapping(value = "save_or_update_reg_person",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateRegPerson(HttpServletRequest request, SupplierRegPerson supplierRegPerson, String supplierId,Model model) {
	
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("currSupplier", supplier);
		Map<String, Object> map = validateRegPerson(supplierRegPerson);
		boolean bool = (boolean) map.get("bool");
		if(bool==false){
			 
			supplierRegPersonService.saveOrUpdateRegPerson(supplierRegPerson);
		}
		return JSON.toJSONString(map);
	}
	@RequestMapping(value = "back_to_professional")
	public String backToSellfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}

	@RequestMapping(value = "delete_reg_person")
	public String deleteRegPerson(HttpServletRequest request, String regPersonIds, String supplierId) {
		supplierRegPersonService.deleteRegPerson(regPersonIds);
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "professional_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	public Map<String,Object>  validateRegPerson( SupplierRegPerson supplierRegPerson){
		Map<String,Object> map=new HashMap<String,Object>();

		boolean bool=true;
		if(supplierRegPerson.getRegType()==null){
			map.put("type", "不能为空");
			bool=false;
		}
		if(supplierRegPerson.getRegNumber()==null){
			map.put("regNum", "不能为空");
			bool=false;
		}
		if(supplierRegPerson.getRegNumber()!=null&&!(supplierRegPerson.getRegNumber().toString()).matches("[0-9]*$")){
			map.put("regNum", "格式不正确");
			bool=false;
		}
		map.put("bool", bool);
		return map;
	}
}
