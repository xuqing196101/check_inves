package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierCertServeMapper;
import ses.model.sms.SupplierCertServe;

@Service(value = "supplierCertSeService")
public class SupplierCertSeServiceImpl implements ses.service.sms.SupplierCertSeService {
	
	@Autowired
	private SupplierCertServeMapper supplierCertSeMapper;
	
	@Override
	public void saveOrUpdateCertSe(SupplierCertServe supplierCertSe) {
//		String id = supplierCertSe.getId();
//		if (id != null && !"".equals(id)) {
//			supplierCertSeMapper.updateByPrimaryKeySelective(supplierCertSe);
//		} else {
			supplierCertSeMapper.insertSelective(supplierCertSe);
//		}

	}

	@Override
	public void deleteCertSe(String certSeIds) {
		for (String id : certSeIds.split(",")) {
			supplierCertSeMapper.deleteById(id);
		}
	}

	@Override
	public SupplierCertServe queryById(String id) {
		SupplierCertServe server = supplierCertSeMapper.selectByPrimaryKey(id);
		return server;
	}

	@Override
	public List<SupplierCertServe> queryServerId(String serverId) {
		// TODO Auto-generated method stub
		return supplierCertSeMapper.findCertSeBySupplierMatSeId(serverId);
	}
}
