package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import common.utils.DateUtils;

import ses.dao.sms.BlacklistLogMapper;
import ses.model.sms.BlacklistLog;
import ses.service.sms.BlacklistLogService;
import ses.util.PropertiesUtil;

@Service(value = "blacklistLogService")
public class BlacklistLogServiceImpl implements BlacklistLogService {
	
	@Autowired
	private BlacklistLogMapper blacklistLogMapper;
	
	@Override
	public List<BlacklistLog> findBlacklistLogBySupplierId(String supplierId, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<BlacklistLog> listBlacklistLogs = blacklistLogMapper.findBlacklistLogBySupplierId(supplierId);
		// 封装处罚截止时间
		if(listBlacklistLogs != null && listBlacklistLogs.size() > 0){
			for (BlacklistLog blacklistLog : listBlacklistLogs) {
				// 计算n各月的日期
				blacklistLog.setEndTime(DateUtils.getDate(blacklistLog.getStartTime(), blacklistLog.getTerm()));
			}
		}
		return listBlacklistLogs;
	}
}
