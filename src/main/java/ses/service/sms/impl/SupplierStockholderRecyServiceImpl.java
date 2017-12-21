package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierStockholderRecyExtMapper;
import ses.dao.sms.SupplierStockholderRecyMapper;
import ses.model.sms.SupplierStockholderRecy;
import ses.model.sms.SupplierStockholderRecyExample;
import ses.service.sms.SupplierStockholderRecyService;

@Service("supplierStockholderRecyService")
public class SupplierStockholderRecyServiceImpl implements SupplierStockholderRecyService {
	
	@Autowired
	private SupplierStockholderRecyMapper supplierStockholderRecyMapper;
	@Autowired
	private SupplierStockholderRecyExtMapper supplierStockholderRecyExtMapper;

	@Override
	public List<SupplierStockholderRecy> getBySupplierIdAtLast(String supplierId) {
		return supplierStockholderRecyExtMapper.selectBySupplierIdAtLast(supplierId);
	}

	@Override
	public int addSupplierStockholderRecy(
			SupplierStockholderRecy supplierStockholderRecy) {
		return supplierStockholderRecyMapper.insertSelective(supplierStockholderRecy);
	}

	@Override
	public int delSupplierStockholderRecyById(String id) {
		SupplierStockholderRecyExample example = new SupplierStockholderRecyExample();
		example.createCriteria().andIdEqualTo(id);
		return supplierStockholderRecyMapper.deleteByExample(example);
	}

}
