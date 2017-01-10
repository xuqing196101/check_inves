package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAuditNotMapper;
import ses.model.sms.SupplierAuditNot;
import ses.service.sms.SupplierAuditNotService;

@Service
public class SupplierAuditNotServiceImpl implements SupplierAuditNotService {

	@Autowired
	private SupplierAuditNotMapper supplierAuditNotMapper;

	@Override
	public int insertSelective(SupplierAuditNot supplierAuditNot) {
		
		return supplierAuditNotMapper.insertSelective(supplierAuditNot);
	}

	@Override
	public List<SupplierAuditNot> selectByPrimaryKey(SupplierAuditNot supplierAuditNot) {
		return supplierAuditNotMapper.selectByPrimaryKey(supplierAuditNot);
	}

}
