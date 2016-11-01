package ses.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ses.service.sms.SupplierBlacklistService;

@Component(value = "myTask")
@Lazy(value = false)
public class MyTask {

	@Autowired
	private SupplierBlacklistService supplierBlacklistService;
	
	@Scheduled(cron = "0 59 23 * * ?")
	public void task() {
		supplierBlacklistService.updateStatusTask();
	}
}
