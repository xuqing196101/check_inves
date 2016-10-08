package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCreditCtntMapper;
import ses.service.sms.SupplierCreditCtntService;

@Service(value = "supplierCreditCtntService")
public class SupplierCreditCtntServiceImpl implements SupplierCreditCtntService {
	
	@Autowired
	private SupplierCreditCtntMapper supplierCreditCtntMapper;
}
