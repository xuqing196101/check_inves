package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierAptituteMapper;
import ses.model.sms.SupplierAptitute;
import ses.service.sms.SupplierAptituteService;

@Service(value = "supplierAptituteService")
public class SupplierAptituteServiceImpl implements SupplierAptituteService {
	
	@Autowired
	private SupplierAptituteMapper supplierAptituteMapper;
	
	@Override
	public void saveOrUpdateAptitute(SupplierAptitute supplierAptitute) {
//		String id = supplierAptitute.getId();
//		if (id != null && !"".equals(id)) {
//			supplierAptituteMapper.updateByPrimaryKeySelective(supplierAptitute);
//		} else {
			supplierAptituteMapper.insertSelective(supplierAptitute);
//		}

	}

	@Override
	public void deleteAptitute(String aptituteIds) {
		for (String id : aptituteIds.split(",")) {
			supplierAptituteMapper.deleteByPrimaryKey(id);
		}
	}

}
