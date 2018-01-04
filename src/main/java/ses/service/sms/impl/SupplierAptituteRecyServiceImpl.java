package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteRecyExtMapper;
import ses.dao.sms.SupplierAptituteRecyMapper;
import ses.model.sms.SupplierAptituteRecy;
import ses.model.sms.SupplierAptituteRecyExample;
import ses.service.sms.SupplierAptituteRecyService;

@Service("supplierAptituteRecyService")
public class SupplierAptituteRecyServiceImpl implements SupplierAptituteRecyService {
	
	@Autowired
	private SupplierAptituteRecyMapper supplierAptituteRecyMapper;
	@Autowired
	private SupplierAptituteRecyExtMapper supplierAptituteRecyExtMapper;

	@Override
	public List<SupplierAptituteRecy> getBySupplierIdAtLast(String supplierId) {
		return supplierAptituteRecyExtMapper.selectBySupplierIdAtLast(supplierId);
	}

	@Override
	public int addSupplierAptituteRecy(SupplierAptituteRecy supplierAptituteRecy) {
		return supplierAptituteRecyMapper.insertSelective(supplierAptituteRecy);
	}

	@Override
	public int delSupplierAptituteRecyById(String id) {
		SupplierAptituteRecyExample example = new SupplierAptituteRecyExample();
		example.createCriteria().andIdEqualTo(id);
		return supplierAptituteRecyMapper.deleteByExample(example);
	}

}
