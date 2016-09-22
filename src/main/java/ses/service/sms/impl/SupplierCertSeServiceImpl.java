package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertSeMapper;
import ses.model.sms.SupplierCertSe;

@Service(value = "supplierCertSeService")
public class SupplierCertSeServiceImpl implements ses.service.sms.SupplierCertSeService {
	
	@Autowired
	private SupplierCertSeMapper supplierCertSeMapper;
	
	@Override
	public void saveOrUpdateCertSe(SupplierCertSe supplierCertSe) {
		String id = supplierCertSe.getId();
		if (id != null && !"".equals(id)) {
			supplierCertSeMapper.updateByPrimaryKeySelective(supplierCertSe);
		} else {
			supplierCertSeMapper.insertSelective(supplierCertSe);
		}

	}

	@Override
	public void deleteCertSe(String certSeIds) {
		for (String id : certSeIds.split(",")) {
			supplierCertSeMapper.deleteByPrimaryKey(id);
		}
	}
}
