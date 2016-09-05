package yggc.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.sms.SupplierInfoMapper;
import yggc.model.sms.SupplierInfo;
import yggc.service.sms.SupplierInfoService;

@Service
public class SupplierInfoServiceImpl implements SupplierInfoService {
	
	@Autowired
	private SupplierInfoMapper supplierInfoMapper;
	
	@Override
	public String register(SupplierInfo supplierInfo) {
		supplierInfoMapper.insertSelective(supplierInfo);
		return supplierInfo.getId();
	}

}
