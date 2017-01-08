package ses.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierExtUser;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierService;
import common.constant.Constant;

/**
 * @Title: supplierController
 * @Description: 供应商信息 Controller
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午1:39:22
 */
@Controller
@Scope("prototype")
@RequestMapping("/my_test")
public class MyTestController extends BaseSupplierController {
	@Autowired
	private SupplierService supplierService;// 供应商基本信息
	
	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
  private SupplierExtUserServicel supplierExtUserServicel;
	
	@RequestMapping(value = "auto_write")
	public void autoWrite(HttpServletRequest request, HttpServletResponse response) {
		String code = (String) request.getSession().getAttribute("img-identity-code");
		super.writeJson(response, code);
	}
	
  @RequestMapping(value="downLoad",produces = "text/html;charset=UTF-8")
  public void downLoad(HttpServletRequest request, HttpServletResponse response) throws Exception {
	  supplierExtUserServicel.downLoadBiddingDoc(request, "420A828A27704F31AC15280BB09D91E0");
	  
  }
	
  @RequestMapping(value="freemarkerTest",produces = "text/html;charset=UTF-8")
  public String downLoad() throws Exception {
	  return "Invitebidding.ftl";
  }

/*	@RequestMapping("login")
	public String login(HttpServletRequest request, Model model) {
		Supplier supplier = supplierService.get("8BE39E5BF23846EC93EED74F57ACF1F4");
		model.addAttribute("currSupplier", supplier);
		request.getSession().setAttribute("supplierDictionaryData", dictionaryDataServiceI.getSupplierDictionary());
		request.getSession().setAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
		request.getSession().setAttribute("supplierId", supplier.getId());
		return "ses/sms/supplier_register/basic_info";
	}*/
}
