package ses.service.sms.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierProductsMapper;
import ses.model.sms.SupplierProducts;
import ses.service.sms.SupplierProductsService;

@Service(value = "supplierProductsService")
public class SupplierProductsServiceImpl implements SupplierProductsService {

	@Autowired
	private SupplierProductsMapper supplierProductsMapper;
	
	@Autowired
	private ProductParamMapper productParamMapper;


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

	@Override
	public void deleteProducts(String proIds) {
		for (String str : proIds.split(",")) {
			productParamMapper.deleteByProductId(str);
			supplierProductsMapper.deleteByPrimaryKey(str);
		}
	}
}
