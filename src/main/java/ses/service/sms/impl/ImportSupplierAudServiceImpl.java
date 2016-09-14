package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ImportSupplierAudMapper;
import ses.model.sms.ImportSupplierAud;
import ses.service.sms.ImportSupplierAudService;

@Service
public class ImportSupplierAudServiceImpl implements ImportSupplierAudService {
	@Autowired
	private ImportSupplierAudMapper isaMapper;
	
	@Override
	public void register(ImportSupplierAud fir) {
		isaMapper.insertSelective(fir);

	}

	@Override
	public void updateRegisterInfo(ImportSupplierAud fir) {
		isaMapper.updateByPrimaryKeySelective(fir);
	}

	@Override
	public ImportSupplierAud findById(String id) {
		ImportSupplierAud fir=isaMapper.selectByPrimaryKey(id);
		return fir;
	}

}
