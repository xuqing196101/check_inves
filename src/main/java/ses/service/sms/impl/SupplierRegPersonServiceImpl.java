package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierRegPersonMapper;
import ses.model.sms.SupplierRegPerson;
import ses.service.sms.SupplierRegPersonService;

@Service(value = "supplierRegPersonService")
public class SupplierRegPersonServiceImpl implements SupplierRegPersonService {

	@Autowired
	private SupplierRegPersonMapper supplierRegPersonMapper;

	@Override
	public void saveOrUpdateRegPerson(SupplierRegPerson supplierRegPerson) {
//		String id = supplierRegPerson.getId();
//		if (id != null && !"".equals(id)) {
//			supplierRegPersonMapper.updateByPrimaryKeySelective(supplierRegPerson);
//		} else {
			supplierRegPersonMapper.insertSelective(supplierRegPerson);
//		}
	}

	@Override
	public void deleteRegPerson(String regPersonIds) {
		for (String id : regPersonIds.split(",")) {
			supplierRegPersonMapper.deleteByPrimaryKey(id);
		}

	}

	@Override
	public SupplierRegPerson queryById(String id) {
		SupplierRegPerson person = supplierRegPersonMapper.selectByPrimaryKey(id);
		return person;
	}

}
