package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierFinanceMapper;
import ses.model.sms.SupplierFinance;
import ses.service.sms.SupplierFinanceService;

/**
 * @Title: SupplierFinanceServiceImpl
 * @Description: SupplierFinanceServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午10:04:10
 */
@Service(value = "supplierFinanceService")
public class SupplierFinanceServiceImpl implements SupplierFinanceService {

	@Autowired
	private SupplierFinanceMapper supplierFinanceMapper;

	@Override
	public void saveOrUpdateFinance(SupplierFinance supplierFinance) {
		String id = supplierFinance.getId();
		if (id != null && !"".equals(id)) {
			supplierFinanceMapper.updateByPrimaryKeySelective(supplierFinance);
		} else {
			supplierFinanceMapper.insertSelective(supplierFinance);
		}
		
	}

	@Override
	public void deleteFinance(String financeIds) {
		for (String id : financeIds.split(",")) {
			supplierFinanceMapper.deleteByPrimaryKey(id);
		}
	}


}
