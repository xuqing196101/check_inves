package ses.controller.sys.sms;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.sms.BlacklistLog;
import ses.service.sms.BlacklistLogService;

import com.github.pagehelper.PageInfo;

@Controller
@Scope("prototype")
@RequestMapping(value = "/blacklist_log")
public class BlacklistLogController {
	
	@Autowired
	private BlacklistLogService blacklistLogService;
	
	@RequestMapping(value = "list")
	public String list(Model model, String supplierId, Integer page) {
		List<BlacklistLog> listBlacklistLogs = blacklistLogService.findBlacklistLogBySupplierId(supplierId, page == null ? 1 : page);
		model.addAttribute("listBlacklistLogs", new PageInfo<BlacklistLog>(listBlacklistLogs));
		model.addAttribute("supplierId", supplierId);
		return "ses/sms/supplier_blacklist/blacklist_log";
	}
}
