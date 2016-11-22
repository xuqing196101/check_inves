package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		return "ses/sms/supplier_register/add_stockholder";
	}
	
	@RequestMapping(value = "save_or_update_stockholder")
	@ResponseBody
	public String saveOrUpdateCertEng(HttpServletRequest request, SupplierStockholder supplierStockholder, String supplierId,Model model) {
		supplierStockholderService.saveOrUpdateStockholder(supplierStockholder);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "basic_info");
		boolean bool = valadateStock(request, supplierStockholder, supplierId, model);
//		if(bool==false){
//			return "0";
//		}else{
			return "1";
//		}
		
	}
	
	@RequestMapping(value = "back_to_basic_info")
	public String backToEngfessional(HttpServletRequest request, String supplierId) {
		Supplier supplier = supplierService.get(supplierId);
		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", "basic_info");
		return "redirect:../supplier/page_jump.html";
	}
	
	@RequestMapping(value = "delete_stockholder")
	public String deleteCertEng(HttpServletRequest request, String stockholderIds, String supplierId,Model model) {
		supplierStockholderService.deleteStockholder(stockholderIds);
		Supplier supplier = supplierService.get(supplierId);
//		request.getSession().setAttribute("defaultPage", "tab-3");
		request.getSession().setAttribute("currSupplier", supplier);
//		request.getSession().setAttribute("jump.page", "basic_info");
		
		return "redirect:../supplier/page_jump.html";
	}
	
	
	
	public boolean valadateStock(HttpServletRequest request, SupplierStockholder stockholder, String supplierId,Model model){
		boolean bool=true;
		if(stockholder.getName()==null){
			model.addAttribute("name", "不能为空");
			bool=false;
		}
		if(stockholder.getNature()==null){
			model.addAttribute("nature", "不能为空");
			bool=false;
		}
		if(stockholder.getIdentity()!=null&&stockholder.getIdentity().matches("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$")){
			model.addAttribute("idCaerd", "身份证编码不正确");
			bool=false;
		}
		if(stockholder.getShares()==null){
			model.addAttribute("share", "不能为空");
		}
		if(stockholder.getShares()!=null&&stockholder.getShares().matches("^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$")){
			model.addAttribute("share", "金额格式错误");
		}
		if(stockholder.getProportion()==null){
			model.addAttribute("portion", "不能为空");
		}
		
		return bool;
	}
	
}
