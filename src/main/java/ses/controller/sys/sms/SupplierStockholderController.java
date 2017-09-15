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
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierService;
import ses.service.sms.SupplierStockholderService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_stockholder")
public class SupplierStockholderController extends BaseController{
	
	@Autowired
	private SupplierService supplierService;// 供应商基本信息

	@Autowired
	private SupplierStockholderService supplierStockholderService;
	
	@RequestMapping(value = "add_stockholder")
	public String addCertEng(Model model, String supplierId) {
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("uuid", UUID.randomUUID().toString().toUpperCase().replace("-", ""));
		return "ses/sms/supplier_register/add_stockholder";
	}
	
	@RequestMapping(value = "save_or_update_stockholder",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierStockholder supplierStockholder, String supplierId,Model model) {
 
		Map<String, Object> map = valadateStock(supplierStockholder);
		boolean bool = (boolean) map.get("bool");
		if(bool==true){
			supplierStockholderService.saveOrUpdateStockholder(supplierStockholder);
			SupplierStockholder stock = supplierStockholderService.queryById(supplierStockholder.getId());
			map.put("stock", stock);
		} 
		return JSON.toJSONString(map);
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	/**
	 * 异步删除股东信息
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "delete_stockholder")
	@ResponseBody
	public String deleteCertEng(String ids) {
		boolean isOk = supplierStockholderService.deleteStockholderByIds(ids);
		if(isOk){
			return "ok";
		}
		return "fail";
	}
	
	public Map<String,Object> valadateStock(SupplierStockholder stockholder){
		Map<String,Object> map=new HashMap<String,Object>();
		boolean bool=true;
		if(stockholder.getName()==null||stockholder.getName().length()>12){
			map.put("name", "不能为空");
			bool=false;
		}
		if(stockholder.getNature()==null||stockholder.getNature().length()>12){
			map.put("nature", "不能为空");
			bool=false;
		}
		if(stockholder.getIdentity()==null){
			map.put("idCaerd", "不能为空");
			bool=false;
		}
//		if(stockholder.getIdentity()!=null){
//			if(stockholder.getIdentity().matches("^[0-9A-Z]{18}$")||stockholder.getIdentity().matches("^[1-9A-GY]{1}[1239]{1}[1-5]{1}[0-9]{5}[0-9A-Z]{10}$")){
//				bool=true;
//			}else{
//				map.put("idCaerd", "身份证编码不正确");
//				bool=false;
//			}
//		}
		if(stockholder.getShares()==null||stockholder.getShares().length()>19){
			bool=false;
			map.put("share", "不能为空字或是金额过大");
		}
		if(stockholder.getShares()!=null&&!stockholder.getShares().matches("^\\d+\\.?\\d*$")){
			map.put("share", "金额格式错误");
			bool=false;
		}
		if(stockholder.getProportion()==null||stockholder.getProportion().length()>35){
			bool=false;
			map.put("portion", "不能为空");
		}
		map.put("bool", bool);
		return map;
	}
	
}
