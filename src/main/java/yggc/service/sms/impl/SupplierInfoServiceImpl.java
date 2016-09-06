package yggc.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.sms.SupplierInfoMapper;
import yggc.model.sms.SupplierInfo;
import yggc.service.sms.SupplierInfoService;
import yggc.util.Encrypt;

@Service(value = "supplierInfoService")
public class SupplierInfoServiceImpl implements SupplierInfoService {

	@Autowired
	private SupplierInfoMapper supplierInfoMapper;
	
	@Override
	public void register(SupplierInfo supplierInfo) {
		supplierInfo.setPassword(Encrypt.e(supplierInfo.getPassword()));// 密码 md5 加密
		supplierInfoMapper.insertSelective(supplierInfo);
	}

	@Override
	public String selectLastInsertId() {
		return supplierInfoMapper.selectLastInsertId();
	}

}
