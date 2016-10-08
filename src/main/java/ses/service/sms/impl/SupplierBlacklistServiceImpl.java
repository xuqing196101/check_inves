package ses.service.sms.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.SupplierBlacklistMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierBlacklist;
import ses.service.sms.SupplierBlacklistService;
import ses.util.PropertiesUtil;

@Service(value = "supplierBlacklistService")
public class SupplierBlacklistServiceImpl implements SupplierBlacklistService {
	
	@Autowired
	private SupplierMapper supplierMapper;
	
	@Autowired
	private SupplierBlacklistMapper supplierBlacklistMapper;
	
	@Override
	public List<Supplier> findSupplier(Supplier supplier, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Supplier> listSuppliers = supplierMapper.findSupplier(supplier);
		return listSuppliers;
	}

	@Override
	public void saveOrUpdateSupplierBlack(SupplierBlacklist supplierBlacklist) {
		Date startTime = supplierBlacklist.getStartTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startTime);
		if (supplierBlacklist.getTerm() != 0) {
			calendar.add(Calendar.MONTH, supplierBlacklist.getTerm());
		} else {
			calendar.add(Calendar.MONTH, 100 * 12);
		}
		supplierBlacklist.setEndTime(calendar.getTime());
		if (supplierBlacklist.getId() != null && !"".equals(supplierBlacklist.getId())) {
			supplierBlacklistMapper.updateByPrimaryKeySelective(supplierBlacklist);
		} else {
			supplierBlacklistMapper.insertSelective(supplierBlacklist);
		}
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
			if (endTime.before(new Date())) {
				supplierBlack.setStatus(1);
			} else {
				supplierBlack.setStatus(0);
			}
		}
		return listSupplierBlacklists;
	}

	@Override
	public SupplierBlacklist getSupplierBlacklist(String supplierBlacklistId) {
		SupplierBlacklist supplierBlacklist = supplierBlacklistMapper.selectByPrimaryKey(supplierBlacklistId);
		return supplierBlacklist;
	}

}
