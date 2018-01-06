package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.review.SupplierAuditSignMapper;
import ses.model.sms.review.SupplierAuditSign;
import ses.model.sms.review.SupplierAuditSignExample;
import ses.service.sms.SupplierAuditSignService;

@Service("supplierAuditSignService")
public class SupplierAuditSignServiceImpl implements SupplierAuditSignService {
	@Autowired
	private SupplierAuditSignMapper supplierAuditSignMapper;

	@Override
	public List<SupplierAuditSign> getBySupplierId(String supplierId) {
		SupplierAuditSignExample example = new SupplierAuditSignExample();
		example.createCriteria().andSupplierIdEqualTo(supplierId);
		return supplierAuditSignMapper.selectByExample(example);
	}

	@Override
	public int add(SupplierAuditSign sign) {
		return supplierAuditSignMapper.insertSelective(sign);
	}

	@Override
	public int update(SupplierAuditSign sign) {
		return supplierAuditSignMapper.updateByPrimaryKeySelective(sign);
	}

	@Override
	public int del(String id) {
		return supplierAuditSignMapper.deleteByPrimaryKey(id);
	}

}
