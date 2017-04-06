package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierSignatureMapper;
import ses.model.sms.SupplierSignature;
import ses.service.sms.SupplierSignatureService;

@Service("supplierSignatureService")
public class SupplierSignatureServiceImpl implements SupplierSignatureService{
	
	@Autowired
	private SupplierSignatureMapper supplierSignatureMapper;
	
	@Override
	public List<SupplierSignature> selectBySupplierId(SupplierSignature supplierSignature) {
		
		return supplierSignatureMapper.selectBySupplierId(supplierSignature);
	}

	@Override
	public void add(SupplierSignature supplierSignature) {
		supplierSignatureMapper.insertSelective(supplierSignature);
		
	}

}
