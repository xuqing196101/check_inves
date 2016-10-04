package ses.service.sms.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierItemMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierItem;
import ses.service.sms.SupplierItemService;

@Service(value = "supplierItemService")
public class SupplierItemServiceImpl implements SupplierItemService {
	
	@Autowired
	private SupplierItemMapper supplierItemMapper;

	@Override
	public void saveSupplierItem(Supplier supplier) {
		String id = supplier.getId();
		supplierItemMapper.deleteBySupplierId(id);
		String supplierItemIds = supplier.getSupplierItemIds();
		for (String str : supplierItemIds.split(",")) {
			SupplierItem supplierItem = new SupplierItem();
			supplierItem.setSupplierId(id);
			supplierItem.setCategoryId(str);
			supplierItem.setCreatedAt(new Date());
			supplierItemMapper.insertSelective(supplierItem);
		}
	}
}
