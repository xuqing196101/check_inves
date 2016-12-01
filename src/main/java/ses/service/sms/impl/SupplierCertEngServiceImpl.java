package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertEngMapper;
import ses.model.sms.SupplierCertEng;
import ses.service.sms.SupplierCertEngService;

@Service(value = "supplierCertEngService")
public class SupplierCertEngServiceImpl implements SupplierCertEngService {

	@Autowired
	private SupplierCertEngMapper supplierCertEngMapper;

	@Override
	public void saveOrUpdateCertEng(SupplierCertEng supplierCertEng) {
//		String id = supplierCertEng.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertEngMapper.updateByPrimaryKeySelective(supplierCertEng);
//		} else {
			supplierCertEngMapper.insertSelective(supplierCertEng);
//		}
	}

	@Override
	public void deleteCertEng(String certEngIds) {
		for (String id : certEngIds.split(",")) {
			supplierCertEngMapper.deleteByPrimaryKey(id);
		}
	}

	public SupplierCertEng queryById(String id) {
		SupplierCertEng certEng = supplierCertEngMapper.selectByPrimaryKey(id);
		return certEng;
	}

}
