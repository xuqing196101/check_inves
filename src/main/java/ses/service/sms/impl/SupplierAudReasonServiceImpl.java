package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierReasonMapper;
import ses.model.sms.SupplierReason;
import ses.service.sms.SupplierAudReasonService;

@Service
public class SupplierAudReasonServiceImpl implements SupplierAudReasonService {
	@Autowired
	private SupplierReasonMapper supplierReasonMapper;
	
	@Override
	public void insertSelective(SupplierReason sar) {
		supplierReasonMapper.insertSelective(sar);
	}

	@Override
	public SupplierReason selectByPrimaryKey(String id) {
		return supplierReasonMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKey(SupplierReason sar) {
		supplierReasonMapper.updateByPrimaryKeySelective(sar);
	}

	@Override
	public List<SupplierReason> findAll(SupplierReason sar) {
		List<SupplierReason> sarList=supplierReasonMapper.getReason(sar);
		return sarList;
	}

	@Override
	public void delete(String id) {

	}
}
