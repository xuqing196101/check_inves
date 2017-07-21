package ses.controller.sys.sms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCredit;
import ses.model.sms.SupplierCreditCtnt;
import ses.service.sms.SupplierCreditCtntService;
import ses.service.sms.SupplierCreditService;
import ses.service.sms.SupplierLevelService;
import ses.util.DictionaryDataUtil;

import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_level")
public class SupplierLevelController extends BaseSupplierController {
	
	@Autowired
	private SupplierLevelService supplierLevelService;
	
	@Autowired
	private SupplierCreditService supplierCreditService;
	
	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
	
	@RequestMapping(value = "list")
	public String list(Model model, Supplier supplier, Integer page,@CurrentUser User user) {
		//权限验证 登陆状态 角色只能是资源服务中心
		if(null!=user && "4".equals(user.getTypeName())){
			List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
			model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
			model.addAttribute("supplierName", supplier.getSupplierName());
			model.addAttribute("level", supplier.getLevel());
			List<DictionaryData> data = DictionaryDataUtil.find(17);
			model.addAttribute("data", data);
		}
		return "ses/sms/supplier_level/list";
	}
	
	@RequestMapping(value = "indexList")
	public String indexList(Model model, Supplier supplier, Integer page) {
		List<Supplier> listSuppliers = supplierLevelService.findSupplier(supplier, page == null ? 1 : page);
		model.addAttribute("listSuppliers", new PageInfo<Supplier>(listSuppliers));
		model.addAttribute("supplierName", supplier.getSupplierName());
		model.addAttribute("level", supplier.getLevel());
		List<DictionaryData> data = DictionaryDataUtil.find(17);
		model.addAttribute("data", data);
		return "ses/sms/supplier_level/index_list";
	}
	
	@RequestMapping(value = "change_score")
	public String changeScore(Model model, Supplier supplier, HttpServletRequest request) throws Exception {
		// 解决中文乱码
		String encoding = request.getCharacterEncoding();
		if(supplier.getSupplierName() != null){
			if ("UTF-8".equals(encoding)){
				supplier.setSupplierName(supplier.getSupplierName());
			}
			if ("ISO8859-1".equals(encoding)) {
				supplier.setSupplierName(new String(supplier.getSupplierName().getBytes("ISO-8859-1"), "UTF-8"));
			}
		}
		List<SupplierCredit> listSupplierCredits = supplierCreditService.findSupplierCredit(new SupplierCredit());
		model.addAttribute("supplier", supplier);
		model.addAttribute("listSupplierCredits", listSupplierCredits);
		return "ses/sms/supplier_level/change_score";
	}

	@RequestMapping(value = "find_credit_ctnt_by_credit_id")
	public void findCreditCtntByCreditId(HttpServletResponse response, SupplierCreditCtnt supplierCreditCtnt) {
		List<SupplierCreditCtnt> listSupplierCreditCtnts = supplierCreditCtntService.findCreditCtntByCreditId(supplierCreditCtnt);
		super.writeJson(response, listSupplierCreditCtnts);
	}
	
	@RequestMapping(value = "update_score")
	public String updateScore(Supplier supplier, String scores) {
		supplierLevelService.updateScore(supplier, scores);
		return "redirect:list.html";
	}
}
