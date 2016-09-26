package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatEngMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierMatEngService;

@Service(value = "supplierMatEngService")
public class SupplierMatEngServiceImpl implements SupplierMatEngService {

	@Autowired
	private SupplierMatEngMapper supplierMatEngMapper;

	@Override
	public void saveOrUpdateSupplierMatPro(Supplier supplier) {
		String id = supplier.getSupplierMatEng().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatEng().setUpdatedAt(new Date());
			supplierMatEngMapper.updateByPrimaryKeySelective(supplier.getSupplierMatEng());
		} else {
			supplier.getSupplierMatEng().setCreatedAt(new Date());
			supplierMatEngMapper.insertSelective(supplier.getSupplierMatEng());
		}
	}

}
