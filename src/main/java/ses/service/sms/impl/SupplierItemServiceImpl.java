package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

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
		String supplierTypeIds = supplier.getSupplierTypeIds();
		String[] itemIds = supplierItemIds.split(";");
		for (int i = 0; i < itemIds.length; i++) {
			for (String str : itemIds[i].split(",")) {
				SupplierItem supplierItem = new SupplierItem();
				supplierItem.setSupplierId(id);
				supplierItem.setCategoryId(str);
				supplierItem.setCreatedAt(new Date());
				supplierItem.setSupplierTypeRelateId(supplierTypeIds.split(",")[i]);
				supplierItemMapper.insertSelective(supplierItem);

			}
		}

	}

	@Override
	public List<String> getSupplierId() {
		return supplierItemMapper.getSupplierId();
	}

	@Override
	public List<String> getItemSupplierId() {
		return supplierItemMapper.getItemBySupplierId();
	}
}
