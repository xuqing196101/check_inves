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
		String id = supplierCertPro.getId();
		if (id != null && !"".equals(id)) {
			supplierCertProMapper.updateByPrimaryKeySelective(supplierCertPro);
		} else {
			supplierCertProMapper.insertSelective(supplierCertPro);
		}
	}

	@Override
	public void deleteCertPro(String certProIds) {
		for (String id : certProIds.split(",")) {
			supplierCertProMapper.deleteByPrimaryKey(id);
		}
	}

}
