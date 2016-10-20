package ses.service.sms;

import java.util.List;

import ses.model.sms.BlacklistLog;

public interface BlacklistLogService {
	public List<BlacklistLog> findBlacklistLogBySupplierId(String supplierId, int page);
}
