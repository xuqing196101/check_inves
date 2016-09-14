package ses.service.sms.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.SupplierStockholderMapper;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierStockholder;
import ses.service.sms.SupplierStockholderService;

/**
 * @Title: SupplierStockholderServiceImpl
 * @Description: SupplierStockholderServiceImpl 实现类
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:45:45
 */
@Service(value = "supplierStockholderService")
public class SupplierStockholderServiceImpl implements SupplierStockholderService {

	@Autowired
	private SupplierStockholderMapper supplierStockholderMapper;

	/**
	 * @Title: saveShare
	 * @author: Wang Zhaohua
	 * @date: 2016-9-8 上午11:46:28
	 * @Description: 保存股东信息
	 * @param: @param supplier
	 * @return: void
	 */
	@Override
	public void saveStockholder(Supplier supplier) {
		List<SupplierStockholder> listSupplierShares = supplier.getListSupplierStockholders();
		supplierStockholderMapper.deleteStockholderBySupplierId(supplier.getId());
		for (SupplierStockholder supplierStockholder : listSupplierShares) {
			if (supplierStockholder.getSupplierId() != null && !"".equals(supplierStockholder.getSupplierId())) {
				supplierStockholder.setCreatedAt(new Date());
				supplierStockholderMapper.insertSelective(supplierStockholder);
			}
		}
	}

}
