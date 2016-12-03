package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertProMapper;
import ses.model.sms.SupplierCertPro;
import ses.service.sms.SupplierCertProService;

@Service(value = "supplierCertProService")
public class SupplierCertProServiceImpl implements SupplierCertProService {
	
	@Autowired
	private SupplierCertProMapper supplierCertProMapper;
	
	@Override
	public void saveOrUpdateCertPro(SupplierCertPro supplierCertPro) {
		Integer sign = supplierCertPro.getSign();
		if (sign == 2) {
			supplierCertProMapper.updateByPrimaryKeySelective(supplierCertPro);
		} else if (sign == 1) {
			supplierCertProMapper.insertSelective(supplierCertPro);
		}
	}

	@Override
	public void deleteCertPro(String certProIds) {
		for (String id : certProIds.split(",")) {
			supplierCertProMapper.deleteById(id);
		}
	}

	@Override
	public SupplierCertPro queryById(String id) {
		SupplierCertPro certPro = supplierCertProMapper.selectByPrimaryKey(id);
		return certPro;
	}
	

}
