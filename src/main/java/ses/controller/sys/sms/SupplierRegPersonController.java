package ses.controller.sys.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierRegPerson;
import ses.service.bms.AreaServiceI;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierRegPersonService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_reg_person")
public class SupplierRegPersonController extends BaseController{
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierRegPersonService supplierRegPersonService;
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private AreaServiceI areaService;
	
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
	
//		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("currSupplier", supplier);
		Map<String, Object> map = validateRegPerson(supplierRegPerson);
		boolean bool = (boolean) map.get("bool");
		if(bool==true){
			supplierRegPersonService.saveOrUpdateRegPerson(supplierRegPerson);
			SupplierRegPerson person = supplierRegPersonService.queryById(supplierRegPerson.getId());
			map.put("person", person);
			
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

/*	@RequestMapping(value = "delete_reg_person")
	public String deleteRegPerson(Model model, String regPersonIds, String supplierId) {
		supplierRegPersonService.deleteRegPerson(regPersonIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-3");
		model.addAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "professional_info");
//		return "redirect:../supplier/page_jump.html";
        List<DictionaryData> list = DictionaryDataUtil.find(6);
        for(int i=0;i<list.size();i++){
            String code = list.get(i).getCode();
            if(code.equals("GOODS")){
                list.remove(list.get(i));
            }
        }
        model.addAttribute("supplieType", list);
        List<DictionaryData> wlist = DictionaryDataUtil.find(8);
        model.addAttribute("wlist", wlist);
        //初始化供应商注册附件类型
        model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
        model.addAttribute("sysKey",  Constant.SUPPLIER_SYS_KEY);
        model.addAttribute("rootArea", areaService.findRootArea());
		return "ses/sms/supplier_register/supplier_type";	
	}*/
	
	/**
	 * 异步删除注册资质人员信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete_reg_person")
	@ResponseBody
	public String deleteRegPerson(String ids) {
		boolean isOk = supplierRegPersonService.deleteRegPersonByIds(ids);
		if(isOk){
			return "ok";
		}
		return "fail";
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
