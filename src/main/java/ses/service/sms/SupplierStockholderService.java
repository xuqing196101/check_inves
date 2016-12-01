package ses.service.sms;

import ses.model.sms.SupplierStockholder;

/**
 * @Title: SupplierStockholderService
 * @Description: SupplierStockholderService 接口
 * @author: Wang Zhaohua
 * @date: 2016-9-8上午11:43:55
 */
public interface SupplierStockholderService {
	
	public void saveOrUpdateStockholder(SupplierStockholder supplierStockholder);

	public void deleteStockholder(String stockholderIds);
	
	SupplierStockholder queryById(String id);
}
