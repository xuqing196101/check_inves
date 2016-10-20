package ses.service.sms.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.BlacklistLogMapper;
import ses.dao.sms.SupplierBlacklistMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.bms.User;
import ses.model.sms.BlacklistLog;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.service.sms.SupplierBlacklistService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

@Service(value = "supplierBlacklistService")
public class SupplierBlacklistServiceImpl implements SupplierBlacklistService {
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private SupplierBlacklistMapper supplierBlacklistMapper;
	
	@Autowired
	private BlacklistLogMapper blacklistLogMapper;
	
	@Override
	public List<Supplier> findSupplier(Supplier supplier, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		
		// 过滤到已经添加到黑名单的
		List<SupplierBlacklist> listSupplierBlacklists = supplierBlacklistMapper.findSupplierBlacklist(new SupplierBlacklist());
		if (listSupplierBlacklists != null && listSupplierBlacklists.size() > 0) {
			List<String> list = new ArrayList<String>();
			for (SupplierBlacklist blacklist : listSupplierBlacklists) {
				list.add(blacklist.getSupplierId());
			}
			supplier.setItem(list);
		}
		
		supplier.setStatus(3);
		List<Supplier> listSuppliers = supplierMapper.findSupplier(supplier);
		return listSuppliers;
	}

	@Override
	public void saveOrUpdateSupplierBlack(SupplierBlacklist supplierBlacklist, User user) {
		Date startTime = supplierBlacklist.getStartTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		if (supplierBlacklist.getTerm() != 0) {
			calendar.add(Calendar.MONTH, supplierBlacklist.getTerm());
		} else {
			calendar.add(Calendar.MONTH, 100 * 12);
		}
		supplierBlacklist.setEndTime(calendar.getTime());
		BlacklistLog blacklistLog = new BlacklistLog();
		BeanUtils.copyProperties(supplierBlacklist, blacklistLog, new String[] {"serialVersionUID", "id", "endTime", "status", "supplierName"});
		blacklistLog.setOperationDate(new Date());
		blacklistLog.setOperationId(user.getId());
		blacklistLog.setOperationName(user.getRelName());
		if (supplierBlacklist.getId() != null && !"".equals(supplierBlacklist.getId())) {
			supplierBlacklistMapper.updateByPrimaryKeySelective(supplierBlacklist);
			blacklistLog.setOperationType(1);
		} else {
			supplierBlacklistMapper.insertSelective(supplierBlacklist);
			blacklistLog.setOperationType(0);
		}
		blacklistLogMapper.insertSelective(blacklistLog);
	}

	@Override
	public List<SupplierBlacklist> findSupplierBlacklist(SupplierBlacklist supplierBlacklist, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		String supplierName = supplierBlacklist.getSupplierName();
		if (supplierName != null && !"".equals(supplierName)) {
			supplierBlacklist.setSupplierName("%" + supplierName + "%");
		}
		List<SupplierBlacklist> listSupplierBlacklists = supplierBlacklistMapper.findSupplierBlacklist(supplierBlacklist);
		for(SupplierBlacklist supplierBlack : listSupplierBlacklists) {
			Date endTime = supplierBlack.getEndTime();
			Integer status = supplierBlack.getStatus();
			if (status == null) {
				if (endTime.before(new Date())) {
					supplierBlack.setStatus(1);
				} else {
					supplierBlack.setStatus(0);
				}
			}
		}
		return listSupplierBlacklists;
	}

	@Override
	public SupplierBlacklist getSupplierBlacklist(String supplierBlacklistId) {
		SupplierBlacklist supplierBlacklist = supplierBlacklistMapper.selectByPrimaryKey(supplierBlacklistId);
		return supplierBlacklist;
	}

	@Override
	public void operatorRemove(String ids, User user) {
		String[] split = ids.split(",");
		for (String id : split) {
			SupplierBlacklist supplierBlacklist = new SupplierBlacklist();
			supplierBlacklist.setId(id);
			supplierBlacklist.setStatus(2);
			supplierBlacklistMapper.updateStatusById(supplierBlacklist);
			
			// 记录表
			SupplierBlacklist sbl = supplierBlacklistMapper.selectByPrimaryKey(id);
			BlacklistLog blacklistLog = new BlacklistLog();
			BeanUtils.copyProperties(sbl, blacklistLog, new String[] {"serialVersionUID", "id", "endTime", "status", "supplierName"});
			blacklistLog.setOperationDate(new Date());
			blacklistLog.setOperationId(user.getId());
			blacklistLog.setOperationName(user.getRelName());
			blacklistLog.setOperationType(2);
			blacklistLogMapper.insertSelective(blacklistLog);
		}
	}

}
