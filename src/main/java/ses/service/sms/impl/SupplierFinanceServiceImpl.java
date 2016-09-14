package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierFinanceMapper;
import ses.model.sms.Supplier;
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

	/**
	 * @Title: saveFinance
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午10:02:14
	 * @Description: 保存供应商财务信息
	 * @param: @param supplier
	 * @return: void
	 */
	@Override
	public void saveFinance(Supplier supplier) {
		List<SupplierFinance> listSupplierFinances = supplier.getListSupplierFinances();
		supplierFinanceMapper.deleteFinanceBySupplierId(supplier.getId());
		for (SupplierFinance supplierFinance : listSupplierFinances) {
			if (supplierFinance.getSupplierId() != null && !"".equals(supplierFinance.getSupplierId())) {
				supplierFinance.setCreatedAt(new Date());
				supplierFinanceMapper.insertSelective(supplierFinance);
			}
		}
	}

}
