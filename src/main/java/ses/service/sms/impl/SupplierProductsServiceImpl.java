package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierProductsMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;
import ses.service.sms.SupplierProductsService;

@Service(value = "supplierProductsService")
public class SupplierProductsServiceImpl implements SupplierProductsService {

	@Autowired
	private SupplierProductsMapper supplierProductsMapper;

	@Override
	public void checkProducts(Supplier supplier) {
		supplierProductsMapper.deleteBySupplierId(supplier.getId());
		String supplierItemIds = supplier.getSupplierItemIds();
		for (String str : supplierItemIds.split(",")) {
			SupplierProducts supplierProducts = new SupplierProducts();
			supplierProducts.setCategoryId(str);
			supplierProducts.setSupplierId(supplier.getId());
			supplierProductsMapper.insertSelective(supplierProducts);
		}

	}

	@Override
	public void saveOrUpdateProducts(SupplierProducts supplierProducts) {
		String id = supplierProducts.getId();
		if (id != null && !"".equals(id)) {
			supplierProductsMapper.updateByPrimaryKeySelective(supplierProducts);
		} else {
			supplierProductsMapper.insertSelective(supplierProducts);
		}
		
	}

	@Override
	public SupplierProducts get(String id) {
		return supplierProductsMapper.selectByPrimaryKey(id);
	}
}
