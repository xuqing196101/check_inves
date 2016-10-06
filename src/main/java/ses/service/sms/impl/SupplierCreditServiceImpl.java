package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCreditMapper;
import ses.model.sms.SupplierCredit;
import ses.service.sms.SupplierCreditService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

@Service(value = "supplierCreditService")
public class SupplierCreditServiceImpl implements SupplierCreditService {
	
	@Autowired
	private SupplierCreditMapper supplierCreditMapper;

	@Override
	public List<SupplierCredit> findSupplierCredit(SupplierCredit supplierCredit, int page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		String name = supplierCredit.getName();
		if (name != null && !"".equals(name)) {
			supplierCredit.setName("%" + name + "%");
		}
		List<SupplierCredit> listSupplierCredit = supplierCreditMapper.findSupplierCredit(supplierCredit);
		return listSupplierCredit;
	}

	@Override
	public void saveOrUpdateSupplierCredit(SupplierCredit supplierCredit) {
		String id = supplierCredit.getId();
		if (id != null && !"".equals(id)) {
			supplierCredit.setUpdatedAt(new Date());
			supplierCreditMapper.updateByPrimaryKeySelective(supplierCredit);
		} else {
			supplierCredit.setCreatedAt(new Date());
			supplierCreditMapper.insertSelective(supplierCredit);
		}
	}

	@Override
	public void updateStatus(SupplierCredit supplierCredit) {
		supplierCreditMapper.updateStatus(supplierCredit);
	}

	@Override
	public void delete(String ids) {
		for(String id : ids.split(",")) {
			supplierCreditMapper.deleteByPrimaryKey(id);
		}
	}
}
