package ses.controller.sys.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.service.sms.SupplierCreditCtntService;

@Controller
@Scope("prototype")
@RequestMapping("/supplier")
public class SupplierCreditCtntController {
	
	@Autowired
	private SupplierCreditCtntService supplierCreditCtntService;
}
