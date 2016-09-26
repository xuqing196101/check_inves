package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatSeMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierMatSeService;

@Service(value = "supplierMatSeService")
public class SupplierMatSeServiceImpl implements SupplierMatSeService {

	@Autowired
	private SupplierMatSeMapper supplierMatSeMapper;

	@Override
	public void saveOrUpdateSupplierMatSe(Supplier supplier) {
		String id = supplier.getSupplierMatSe().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatSell().setUpdatedAt(new Date());
			supplierMatSeMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSe());
		} else {
			supplier.getSupplierMatPro().setCreatedAt(new Date());
			supplierMatSeMapper.insertSelective(supplier.getSupplierMatSe());
		}
	}

}
