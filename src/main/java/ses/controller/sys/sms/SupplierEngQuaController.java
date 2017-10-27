package ses.controller.sys.sms;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierEngQuaService;
import ses.service.sms.SupplierService;

import common.constant.Constant;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_eng_qua")
public class SupplierEngQuaController extends BaseSupplierController {
	
	@SuppressWarnings("unused")
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierEngQuaService supplierEngQuaService;
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	/**
	 * 添加一条工程资质证书
	 * @param model
	 * @param matEngId
	 * @param supplierId
	 * @return
	 */
	@RequestMapping(value = "/add_eng_qua")
	public String addEngQua(Model model, String matEngId, String supplierId) {
		model.addAttribute("matEngId", matEngId);
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		model.addAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		return "ses/sms/supplier_register/add_eng_qua";
	}
	
	/**
	 * 异步删除工程资质证书
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete_eng_qua")
	@ResponseBody
	public String deleteEngQua(String ids) {
		boolean isOk = supplierEngQuaService.deleteEngQuaByIds(ids);
		if(isOk){
        	return "ok";
        }
        return "fail";
	}
	
}
