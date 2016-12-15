package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierHistoryMapper;
import ses.model.sms.SupplierHistory;
import ses.service.sms.SupplierHistoryService;

@Service
public class SupplierHistoryServiceImpl implements SupplierHistoryService{

	@Autowired
	private SupplierHistoryMapper supplierHistoryMapper;
	
	public void  add(SupplierHistory supplierHistory){
		supplierHistoryMapper.insertSelective(supplierHistory);
	}
}
