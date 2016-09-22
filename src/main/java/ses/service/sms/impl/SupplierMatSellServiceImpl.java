package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierMatSellMapper;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierMatSellService;

@Service(value = "supplierMatSellService")
public class SupplierMatSellServiceImpl implements SupplierMatSellService {

	@Autowired
	private SupplierMatSellMapper supplierMatSellMapper;

	@Override
	public void saveOrUpdateSupplierMatSell(Supplier supplier) {
		String id = supplier.getSupplierMatSell().getId();
		if (id != null && !"".equals(id)) {
			supplier.getSupplierMatSell().setUpdatedAt(new Date());
			supplierMatSellMapper.updateByPrimaryKeySelective(supplier.getSupplierMatSell());
		} else {
			supplier.getSupplierMatPro().setCreatedAt(new Date());
			supplierMatSellMapper.insertSelective(supplier.getSupplierMatSell());
		}
	}

}
