package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.service.sms.SupplierItemService;
import ses.service.sms.SupplierService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/supplier_item")
public class SupplierItemController {
	
	@Autowired
	private SupplierItemService supplierItemService;
	
	@Autowired
	private SupplierService supplierService;
	
	/**
	 * @Title: saveOrUpdate
	 * @author: Wang Zhaohua
	 * @date: 2016-11-2 下午3:27:32
	 * @Description: 保存或者更新品目
	 * @param: @param request
	 * @param: @param supplierItem
	 * @param: @param jsp
	 * @param: @param defaultPage
	 * @param: @return
	 * @return: String
	 */
	@RequestMapping(value = "save_or_update")
	public String saveOrUpdate(HttpServletRequest request, SupplierItem supplierItem, String jsp, String defaultPage) {
		supplierItemService.saveOrUpdate(supplierItem);
		Supplier supplier = supplierService.get(supplierItem.getSupplierId());
		
		if ("items".equals(jsp))
			request.getSession().setAttribute("defaultPage", defaultPage);
		else
			request.getSession().removeAttribute("defaultPage");
		
		request.getSession().setAttribute("currSupplier", supplier);
		request.getSession().setAttribute("jump.page", jsp);
		return "redirect:../supplier/page_jump.html";
	}
}
