package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.ProductParamMapper;
import ses.dao.sms.SupplierProductsMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierProducts;
import ses.service.sms.SupplierProductsService;

@Service(value = "supplierProductsService")
public class SupplierProductsServiceImpl implements SupplierProductsService {

	@Autowired
	private SupplierProductsMapper supplierProductsMapper;
	
	@Autowired
	private ProductParamMapper productParamMapper;

	@Override
	public void checkProducts(Supplier supplier) {
		String supplierItemIds = supplier.getSupplierItemIds();
		supplierItemIds = supplierItemIds.replace(";", ",");
		String[] split = supplierItemIds.split(",");
		List<SupplierProducts> listSupplierProducts = supplierProductsMapper.findSupplierProductsBySupplierId(supplier.getId());

		if (listSupplierProducts != null && listSupplierProducts.size() > 0) {
			boolean flag = false;
			// 第一次遍历删除多余的品目类别
			for (SupplierProducts supplierProducts : listSupplierProducts) {
				String id = supplierProducts.getId();
				String categoryId = supplierProducts.getCategoryId();
				for (int i = 0; i < split.length; i++) {
					if (categoryId.equals(split[i])) {
						flag = false;
						break;
					} else {
						flag = true;
					}
					if (flag && i == split.length - 1) {
						supplierProductsMapper.deleteByPrimaryKey(id);
						productParamMapper.deleteByProductId(id);
					}
				}
			}
			/*listSupplierProducts = supplierProductsMapper.findSupplierProductsBySupplierId(supplier.getId());
			// 第二次遍历插入新的品目类别
			for (int i = 0; i < split.length; i++) {
				String str = split[i];
				for (int j = 0; j < listSupplierProducts.size(); j++) {
					String categoryId = listSupplierProducts.get(j).getCategoryId();
					if (str.equals(categoryId)) {
						flag = false;
						break;
					} else {
						flag = true;
					}
					if (flag && j == listSupplierProducts.size() - 1) {
						SupplierProducts sp = new SupplierProducts();
						sp.setCategoryId(str);
						sp.setSupplierId(supplier.getId());
						supplierProductsMapper.insertSelective(sp);
					}
				}
			}*/
		} else {
			/*for (int i = 0; i < split.length; i++) {
				SupplierProducts sp = new SupplierProducts();
				sp.setCategoryId(split[i]);
				sp.setSupplierId(supplier.getId());
				supplierProductsMapper.insertSelective(sp);
			}*/
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

	@Override
	public void deleteProducts(String proIds) {
		for (String str : proIds.split(",")) {
			productParamMapper.deleteByProductId(str);
			supplierProductsMapper.deleteByPrimaryKey(str);
		}
	}
}
