package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.review.SupplierInvesOtherMapper;
import ses.model.sms.review.SupplierInvesOther;
import ses.model.sms.review.SupplierInvesOtherExample;
import ses.service.sms.SupplierInvesOtherService;

@Service("supplierInvesOtherService")
public class SupplierInvesOtherServiceImpl implements SupplierInvesOtherService {
	@Autowired
	private SupplierInvesOtherMapper supplierInvesOtherMapper;

	@Override
	public SupplierInvesOther getBySupplierId(String supplierId) {
		SupplierInvesOtherExample example = new SupplierInvesOtherExample();
		example.createCriteria().andSupplierIdEqualTo(supplierId);
		List<SupplierInvesOther> list = supplierInvesOtherMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int add(SupplierInvesOther supplierInvesOther) {
		return supplierInvesOtherMapper.insert(supplierInvesOther);
	}

	@Override
	public int update(SupplierInvesOther supplierInvesOther) {
		return supplierInvesOtherMapper.updateByPrimaryKeySelective(supplierInvesOther);
	}
}
