package ses.controller.sys.sms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import bss.dao.ppms.SaleTenderMapper;
import bss.model.ppms.SaleTender;

@Controller
@Scope("prototype")
@RequestMapping(value = "/mulQuo")
public class SupplierMultipleQuotesController {
	
	@Autowired
	private SaleTenderMapper saleTenderMapper;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest req,SaleTender st){
		User user=(User)req.getSession().getAttribute("loginUser");
		st.setSupplierId(user.getTypeId());
		saleTenderMapper.list(st);
		return "";
	}
}
